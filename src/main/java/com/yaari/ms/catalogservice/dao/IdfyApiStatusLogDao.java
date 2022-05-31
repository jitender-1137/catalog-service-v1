package com.yaari.ms.catalogservice.dao;

import com.yaari.ms.catalogservice.domains.IdfyApiStatusLog;
import com.yaari.ms.catalogservice.dto.ComparativeRelationAndValue;
import com.yaari.ms.catalogservice.dto.EntryItem;
import com.yaari.ms.catalogservice.dto.GenericSearchFilter;
import com.yaari.ms.catalogservice.enums.QueriesCombinationType;
import com.yaari.ms.catalogservice.jparepositories.IdfyApiStatusLogRepository;
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
public class IdfyApiStatusLogDao {
	@Autowired
	IdfyApiStatusLogRepository idfyApiStatusLogRepository;
	@PersistenceContext
	EntityManager entityManager;

	public IdfyApiStatusLog save(IdfyApiStatusLog idfyApiStatusLog) {
		return idfyApiStatusLogRepository.save(idfyApiStatusLog);
	}

	public List<IdfyApiStatusLog> save(List<IdfyApiStatusLog> idfyApiStatusLogs) {
		return idfyApiStatusLogRepository.saveAll(idfyApiStatusLogs);
	}

	public EntryItem<IdfyApiStatusLog> findByCriteriaFields(GenericSearchFilter genericSearchFilter,
																Integer pageNumber, Integer pageSize) {
		List<IdfyApiStatusLog> idfyApiStatusLogs;
		Map<String, ComparativeRelationAndValue> searchParams = genericSearchFilter.getSearchParams();
		log.info("Finding by criteria fields in dao as passed");
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<IdfyApiStatusLog> cq = cb.createQuery(IdfyApiStatusLog.class);
		Root<IdfyApiStatusLog> idfyApiStatusLogRoot = cq.from(IdfyApiStatusLog.class);
		CriteriaQuery<Long> countCQ = cb.createQuery(Long.class);
		countCQ.select(cb.count(countCQ.from(IdfyApiStatusLog.class)));
		List<Predicate> predicates = new LinkedList<>();
		for (Map.Entry<String, ComparativeRelationAndValue> entry : searchParams.entrySet()) {
			UtilityMethods.fillCriteriaQuery(cb, entry, idfyApiStatusLogRoot, predicates);
		}
		processQueriesCombination(genericSearchFilter.getCombinationType(), predicates, cb, cq, countCQ);
		List<Order> orderBys = UtilityMethods.getOrderBys(cb, idfyApiStatusLogRoot, genericSearchFilter.getOrderBy());
		cq.orderBy(orderBys);
		TypedQuery<IdfyApiStatusLog> query = entityManager.createQuery(cq);
		Long count = entityManager.createQuery(countCQ).getSingleResult();
		if (pageNumber == null) pageNumber = 0;
		if (pageSize == null || pageSize == 0) pageSize = 10;
		int offset = pageNumber * pageSize;
		idfyApiStatusLogs = query.setFirstResult(offset).setMaxResults(pageSize).getResultList();
		return new EntryItem<>(count, idfyApiStatusLogs);
	}

	private void processQueriesCombination(QueriesCombinationType combinationType, List<Predicate> predicates, CriteriaBuilder cb, CriteriaQuery<IdfyApiStatusLog> cq, CriteriaQuery<Long> countCQ) {

		if (combinationType== QueriesCombinationType.all_and) {
			cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
			countCQ.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		} else {
			cq.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
			countCQ.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
		}
	}


	public IdfyApiStatusLog findById(Long id) {
		return idfyApiStatusLogRepository.findById(id).orElse(null);
	}
}
