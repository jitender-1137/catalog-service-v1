package com.yaari.ms.catalogservice.dao;

import com.yaari.ms.catalogservice.domains.CategoryProperty;
import com.yaari.ms.catalogservice.dto.ComparativeRelationAndValue;
import com.yaari.ms.catalogservice.dto.EntryItem;
import com.yaari.ms.catalogservice.dto.GenericSearchFilter;
import com.yaari.ms.catalogservice.enums.QueriesCombinationType;
import com.yaari.ms.catalogservice.jparepositories.CategoryPropertyRepository;
import com.yaari.ms.catalogservice.utility.UtilityMethods;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class CategoryPropertyDao {
	@Autowired
	CategoryPropertyRepository categoryPropertyRepository;
	@PersistenceContext
	EntityManager entityManager;

	public CategoryProperty save(CategoryProperty categoryProperty) {
		return categoryPropertyRepository.save(categoryProperty);
	}

	public List<CategoryProperty> save(List<CategoryProperty> categoryPropertys) {
		return categoryPropertyRepository.saveAll(categoryPropertys);
	}

	public EntryItem<CategoryProperty> findByCriteriaFields(GenericSearchFilter genericSearchFilter,
																Integer pageNumber, Integer pageSize) {
		List<CategoryProperty> categoryPropertys;
		Map<String, ComparativeRelationAndValue> searchParams = genericSearchFilter.getSearchParams();
		log.info("Finding by criteria fields in dao as passed");
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<CategoryProperty> cq = cb.createQuery(CategoryProperty.class);
		Root<CategoryProperty> categoryPropertyRoot = cq.from(CategoryProperty.class);
		CriteriaQuery<Long> countCQ = cb.createQuery(Long.class);
		countCQ.select(cb.count(countCQ.from(CategoryProperty.class)));
		List<Predicate> predicates = new LinkedList<>();
		for (Map.Entry<String, ComparativeRelationAndValue> entry : searchParams.entrySet()) {
			UtilityMethods.fillCriteriaQuery(cb, entry, categoryPropertyRoot, predicates);
		}
		processQueriesCombination(genericSearchFilter.getCombinationType(), predicates, cb, cq, countCQ);
		List<Order> orderBys = UtilityMethods.getOrderBys(cb, categoryPropertyRoot, genericSearchFilter.getOrderBy());
		cq.orderBy(orderBys);
		TypedQuery<CategoryProperty> query = entityManager.createQuery(cq);
		Long count = entityManager.createQuery(countCQ).getSingleResult();
		if (pageNumber == null) pageNumber = 0;
		if (pageSize == null || pageSize == 0) pageSize = 10;
		int offset = pageNumber * pageSize;
		categoryPropertys = query.setFirstResult(offset).setMaxResults(pageSize).getResultList();
		return new EntryItem<>(count, categoryPropertys);
	}

	private void processQueriesCombination(QueriesCombinationType combinationType, List<Predicate> predicates, CriteriaBuilder cb, CriteriaQuery<CategoryProperty> cq, CriteriaQuery<Long> countCQ) {

		if (combinationType== QueriesCombinationType.all_and) {
			cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
			countCQ.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		} else {
			cq.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
			countCQ.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
		}
	}


	public CategoryProperty findById(Long id) {
		return categoryPropertyRepository.findById(id).orElse(null);
	}
}
