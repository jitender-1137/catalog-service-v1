package com.yaari.ms.catalogservice.dao;

import com.yaari.ms.catalogservice.domains.ReturnImageUrls;
import com.yaari.ms.catalogservice.dto.ComparativeRelationAndValue;
import com.yaari.ms.catalogservice.dto.EntryItem;
import com.yaari.ms.catalogservice.dto.GenericSearchFilter;
import com.yaari.ms.catalogservice.enums.QueriesCombinationType;
import com.yaari.ms.catalogservice.jparepositories.ReturnImageUrlsRepository;
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
public class ReturnImageUrlsDao {
	@Autowired
	ReturnImageUrlsRepository returnImageUrlsRepository;
	@PersistenceContext
	EntityManager entityManager;

	public ReturnImageUrls save(ReturnImageUrls returnImageUrls) {
		return returnImageUrlsRepository.save(returnImageUrls);
	}

	public List<ReturnImageUrls> save(List<ReturnImageUrls> returnImageUrlss) {
		return returnImageUrlsRepository.saveAll(returnImageUrlss);
	}

	public EntryItem<ReturnImageUrls> findByCriteriaFields(GenericSearchFilter genericSearchFilter,
																Integer pageNumber, Integer pageSize) {
		List<ReturnImageUrls> returnImageUrlss;
		Map<String, ComparativeRelationAndValue> searchParams = genericSearchFilter.getSearchParams();
		log.info("Finding by criteria fields in dao as passed");
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<ReturnImageUrls> cq = cb.createQuery(ReturnImageUrls.class);
		Root<ReturnImageUrls> returnImageUrlsRoot = cq.from(ReturnImageUrls.class);
		CriteriaQuery<Long> countCQ = cb.createQuery(Long.class);
		countCQ.select(cb.count(countCQ.from(ReturnImageUrls.class)));
		List<Predicate> predicates = new LinkedList<>();
		for (Map.Entry<String, ComparativeRelationAndValue> entry : searchParams.entrySet()) {
			UtilityMethods.fillCriteriaQuery(cb, entry, returnImageUrlsRoot, predicates);
		}
		processQueriesCombination(genericSearchFilter.getCombinationType(), predicates, cb, cq, countCQ);
		List<Order> orderBys = UtilityMethods.getOrderBys(cb, returnImageUrlsRoot, genericSearchFilter.getOrderBy());
		cq.orderBy(orderBys);
		TypedQuery<ReturnImageUrls> query = entityManager.createQuery(cq);
		Long count = entityManager.createQuery(countCQ).getSingleResult();
		if (pageNumber == null) pageNumber = 0;
		if (pageSize == null || pageSize == 0) pageSize = 10;
		int offset = pageNumber * pageSize;
		returnImageUrlss = query.setFirstResult(offset).setMaxResults(pageSize).getResultList();
		return new EntryItem<>(count, returnImageUrlss);
	}

	private void processQueriesCombination(QueriesCombinationType combinationType, List<Predicate> predicates, CriteriaBuilder cb, CriteriaQuery<ReturnImageUrls> cq, CriteriaQuery<Long> countCQ) {

		if (combinationType== QueriesCombinationType.all_and) {
			cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
			countCQ.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		} else {
			cq.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
			countCQ.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
		}
	}


	public ReturnImageUrls findById(Long id) {
		return returnImageUrlsRepository.findById(id).orElse(null);
	}
}
