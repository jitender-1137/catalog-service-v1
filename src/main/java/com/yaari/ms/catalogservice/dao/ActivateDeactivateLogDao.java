package com.yaari.ms.catalogservice.dao;

import com.yaari.ms.catalogservice.domains.ActivateDeactivateLog;
import com.yaari.ms.catalogservice.dto.ComparativeRelationAndValue;
import com.yaari.ms.catalogservice.dto.EntryItem;
import com.yaari.ms.catalogservice.dto.GenericSearchFilter;
import com.yaari.ms.catalogservice.enums.QueriesCombinationType;
import com.yaari.ms.catalogservice.jparepositories.ActivateDeactivateLogRepository;
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
public class ActivateDeactivateLogDao {
	@Autowired
	ActivateDeactivateLogRepository activateDeactivateLogRepository;
	@PersistenceContext
	EntityManager entityManager;

	public ActivateDeactivateLog save(ActivateDeactivateLog activateDeactivateLog) {
		return activateDeactivateLogRepository.save(activateDeactivateLog);
	}

	public List<ActivateDeactivateLog> save(List<ActivateDeactivateLog> activateDeactivateLogs) {
		return activateDeactivateLogRepository.saveAll(activateDeactivateLogs);
	}

	public EntryItem<ActivateDeactivateLog> findByCriteriaFields(GenericSearchFilter genericSearchFilter,
																 Integer pageNumber, Integer pageSize) {
		List<ActivateDeactivateLog> activateDeactivateLogs;
		Map<String, ComparativeRelationAndValue> searchParams = genericSearchFilter.getSearchParams();
		log.info("Finding by criteria fields in dao as passed");
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<ActivateDeactivateLog> cq = cb.createQuery(ActivateDeactivateLog.class);
		Root<ActivateDeactivateLog> activateDeactivateLogRoot = cq.from(ActivateDeactivateLog.class);
		CriteriaQuery<Long> countCQ = cb.createQuery(Long.class);
		countCQ.select(cb.count(countCQ.from(ActivateDeactivateLog.class)));
		List<Predicate> predicates = new LinkedList<>();
		for (Map.Entry<String, ComparativeRelationAndValue> entry : searchParams.entrySet()) {
			UtilityMethods.fillCriteriaQuery(cb, entry, activateDeactivateLogRoot, predicates);
		}
		processQueriesCombination(genericSearchFilter.getCombinationType(), predicates, cb, cq, countCQ);
		List<Order> orderBys = UtilityMethods.getOrderBys(cb, activateDeactivateLogRoot, genericSearchFilter.getOrderBy());
		cq.orderBy(orderBys);
		TypedQuery<ActivateDeactivateLog> query = entityManager.createQuery(cq);
		Long count = entityManager.createQuery(countCQ).getSingleResult();
		if (pageNumber == null) pageNumber = 0;
		if (pageSize == null || pageSize == 0) pageSize = 10;
		int offset = pageNumber * pageSize;
		activateDeactivateLogs = query.setFirstResult(offset).setMaxResults(pageSize).getResultList();
		return new EntryItem<>(count, activateDeactivateLogs);
	}

	private void processQueriesCombination(QueriesCombinationType combinationType, List<Predicate> predicates, CriteriaBuilder cb, CriteriaQuery<ActivateDeactivateLog> cq, CriteriaQuery<Long> countCQ) {

		if (combinationType== QueriesCombinationType.all_and) {
			cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
			countCQ.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		} else {
			cq.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
			countCQ.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
		}
	}


	public ActivateDeactivateLog findById(Long id) {
		return activateDeactivateLogRepository.findById(id).orElse(null);
	}
}
