package com.yaari.ms.catalogservice.dao;

import com.yaari.ms.catalogservice.domains.DummySuppliers;
import com.yaari.ms.catalogservice.dto.ComparativeRelationAndValue;
import com.yaari.ms.catalogservice.dto.EntryItem;
import com.yaari.ms.catalogservice.dto.GenericSearchFilter;
import com.yaari.ms.catalogservice.enums.QueriesCombinationType;
import com.yaari.ms.catalogservice.jparepositories.DummySuppliersRepository;
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
public class DummySuppliersDao {
	@Autowired
	DummySuppliersRepository dummySuppliersRepository;
	@PersistenceContext
	EntityManager entityManager;

	public DummySuppliers save(DummySuppliers dummySuppliers) {
		return dummySuppliersRepository.save(dummySuppliers);
	}

	public List<DummySuppliers> save(List<DummySuppliers> dummySupplierss) {
		return dummySuppliersRepository.saveAll(dummySupplierss);
	}

	public EntryItem<DummySuppliers> findByCriteriaFields(GenericSearchFilter genericSearchFilter,
																Integer pageNumber, Integer pageSize) {
		List<DummySuppliers> dummySupplierss;
		Map<String, ComparativeRelationAndValue> searchParams = genericSearchFilter.getSearchParams();
		log.info("Finding by criteria fields in dao as passed");
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<DummySuppliers> cq = cb.createQuery(DummySuppliers.class);
		Root<DummySuppliers> dummySuppliersRoot = cq.from(DummySuppliers.class);
		CriteriaQuery<Long> countCQ = cb.createQuery(Long.class);
		countCQ.select(cb.count(countCQ.from(DummySuppliers.class)));
		List<Predicate> predicates = new LinkedList<>();
		for (Map.Entry<String, ComparativeRelationAndValue> entry : searchParams.entrySet()) {
			UtilityMethods.fillCriteriaQuery(cb, entry, dummySuppliersRoot, predicates);
		}
		processQueriesCombination(genericSearchFilter.getCombinationType(), predicates, cb, cq, countCQ);
		List<Order> orderBys = UtilityMethods.getOrderBys(cb, dummySuppliersRoot, genericSearchFilter.getOrderBy());
		cq.orderBy(orderBys);
		TypedQuery<DummySuppliers> query = entityManager.createQuery(cq);
		Long count = entityManager.createQuery(countCQ).getSingleResult();
		if (pageNumber == null) pageNumber = 0;
		if (pageSize == null || pageSize == 0) pageSize = 10;
		int offset = pageNumber * pageSize;
		dummySupplierss = query.setFirstResult(offset).setMaxResults(pageSize).getResultList();
		return new EntryItem<>(count, dummySupplierss);
	}

	private void processQueriesCombination(QueriesCombinationType combinationType, List<Predicate> predicates, CriteriaBuilder cb, CriteriaQuery<DummySuppliers> cq, CriteriaQuery<Long> countCQ) {

		if (combinationType== QueriesCombinationType.all_and) {
			cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
			countCQ.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		} else {
			cq.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
			countCQ.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
		}
	}


	public DummySuppliers findById(Long id) {
		return dummySuppliersRepository.findById(id).orElse(null);
	}
}
