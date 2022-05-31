package com.yaari.ms.catalogservice.dao;

import com.yaari.ms.catalogservice.domains.PincodesServicable;
import com.yaari.ms.catalogservice.dto.ComparativeRelationAndValue;
import com.yaari.ms.catalogservice.dto.EntryItem;
import com.yaari.ms.catalogservice.dto.GenericSearchFilter;
import com.yaari.ms.catalogservice.enums.QueriesCombinationType;
import com.yaari.ms.catalogservice.jparepositories.PincodesServicableRepository;
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
public class PincodesServicableDao {
	@Autowired
	PincodesServicableRepository pincodesServicableRepository;
	@PersistenceContext
	EntityManager entityManager;

	public PincodesServicable save(PincodesServicable pincodesServicable) {
		return pincodesServicableRepository.save(pincodesServicable);
	}

	public List<PincodesServicable> save(List<PincodesServicable> pincodesServicables) {
		return pincodesServicableRepository.saveAll(pincodesServicables);
	}

	public EntryItem<PincodesServicable> findByCriteriaFields(GenericSearchFilter genericSearchFilter,
																Integer pageNumber, Integer pageSize) {
		List<PincodesServicable> pincodesServicables;
		Map<String, ComparativeRelationAndValue> searchParams = genericSearchFilter.getSearchParams();
		log.info("Finding by criteria fields in dao as passed");
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<PincodesServicable> cq = cb.createQuery(PincodesServicable.class);
		Root<PincodesServicable> pincodesServicableRoot = cq.from(PincodesServicable.class);
		CriteriaQuery<Long> countCQ = cb.createQuery(Long.class);
		countCQ.select(cb.count(countCQ.from(PincodesServicable.class)));
		List<Predicate> predicates = new LinkedList<>();
		for (Map.Entry<String, ComparativeRelationAndValue> entry : searchParams.entrySet()) {
			UtilityMethods.fillCriteriaQuery(cb, entry, pincodesServicableRoot, predicates);
		}
		processQueriesCombination(genericSearchFilter.getCombinationType(), predicates, cb, cq, countCQ);
		List<Order> orderBys = UtilityMethods.getOrderBys(cb, pincodesServicableRoot, genericSearchFilter.getOrderBy());
		cq.orderBy(orderBys);
		TypedQuery<PincodesServicable> query = entityManager.createQuery(cq);
		Long count = entityManager.createQuery(countCQ).getSingleResult();
		if (pageNumber == null) pageNumber = 0;
		if (pageSize == null || pageSize == 0) pageSize = 10;
		int offset = pageNumber * pageSize;
		pincodesServicables = query.setFirstResult(offset).setMaxResults(pageSize).getResultList();
		return new EntryItem<>(count, pincodesServicables);
	}

	private void processQueriesCombination(QueriesCombinationType combinationType, List<Predicate> predicates, CriteriaBuilder cb, CriteriaQuery<PincodesServicable> cq, CriteriaQuery<Long> countCQ) {

		if (combinationType== QueriesCombinationType.all_and) {
			cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
			countCQ.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		} else {
			cq.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
			countCQ.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
		}
	}


	public PincodesServicable findById(Long id) {
		return pincodesServicableRepository.findById(id).orElse(null);
	}
}
