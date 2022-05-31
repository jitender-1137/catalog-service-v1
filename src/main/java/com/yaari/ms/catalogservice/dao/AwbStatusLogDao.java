package com.yaari.ms.catalogservice.dao;

import com.yaari.ms.catalogservice.domains.AwbStatusLog;
import com.yaari.ms.catalogservice.dto.ComparativeRelationAndValue;
import com.yaari.ms.catalogservice.dto.EntryItem;
import com.yaari.ms.catalogservice.dto.GenericSearchFilter;
import com.yaari.ms.catalogservice.enums.QueriesCombinationType;
import com.yaari.ms.catalogservice.jparepositories.AwbStatusLogRepository;
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
public class AwbStatusLogDao {
	@Autowired
	AwbStatusLogRepository awbStatusLogRepository;
	@PersistenceContext
	EntityManager entityManager;

	public AwbStatusLog save(AwbStatusLog awbStatusLog) {
		return awbStatusLogRepository.save(awbStatusLog);
	}

	public List<AwbStatusLog> save(List<AwbStatusLog> awbStatusLogs) {
		return awbStatusLogRepository.saveAll(awbStatusLogs);
	}

	public EntryItem<AwbStatusLog> findByCriteriaFields(GenericSearchFilter genericSearchFilter,
																Integer pageNumber, Integer pageSize) {
		List<AwbStatusLog> awbStatusLogs;
		Map<String, ComparativeRelationAndValue> searchParams = genericSearchFilter.getSearchParams();
		log.info("Finding by criteria fields in dao as passed");
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<AwbStatusLog> cq = cb.createQuery(AwbStatusLog.class);
		Root<AwbStatusLog> awbStatusLogRoot = cq.from(AwbStatusLog.class);
		CriteriaQuery<Long> countCQ = cb.createQuery(Long.class);
		countCQ.select(cb.count(countCQ.from(AwbStatusLog.class)));
		List<Predicate> predicates = new LinkedList<>();
		for (Map.Entry<String, ComparativeRelationAndValue> entry : searchParams.entrySet()) {
			UtilityMethods.fillCriteriaQuery(cb, entry, awbStatusLogRoot, predicates);
		}
		processQueriesCombination(genericSearchFilter.getCombinationType(), predicates, cb, cq, countCQ);
		List<Order> orderBys = UtilityMethods.getOrderBys(cb, awbStatusLogRoot, genericSearchFilter.getOrderBy());
		cq.orderBy(orderBys);
		TypedQuery<AwbStatusLog> query = entityManager.createQuery(cq);
		Long count = entityManager.createQuery(countCQ).getSingleResult();
		if (pageNumber == null) pageNumber = 0;
		if (pageSize == null || pageSize == 0) pageSize = 10;
		int offset = pageNumber * pageSize;
		awbStatusLogs = query.setFirstResult(offset).setMaxResults(pageSize).getResultList();
		return new EntryItem<>(count, awbStatusLogs);
	}

	private void processQueriesCombination(QueriesCombinationType combinationType, List<Predicate> predicates, CriteriaBuilder cb, CriteriaQuery<AwbStatusLog> cq, CriteriaQuery<Long> countCQ) {

		if (combinationType== QueriesCombinationType.all_and) {
			cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
			countCQ.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		} else {
			cq.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
			countCQ.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
		}
	}


	public AwbStatusLog findById(Long id) {
		return awbStatusLogRepository.findById(id).orElse(null);
	}
}
