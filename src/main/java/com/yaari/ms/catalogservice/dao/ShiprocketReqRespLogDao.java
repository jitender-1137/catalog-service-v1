package com.yaari.ms.catalogservice.dao;

import com.yaari.ms.catalogservice.domains.ShiprocketReqRespLog;
import com.yaari.ms.catalogservice.dto.ComparativeRelationAndValue;
import com.yaari.ms.catalogservice.dto.EntryItem;
import com.yaari.ms.catalogservice.dto.GenericSearchFilter;
import com.yaari.ms.catalogservice.enums.QueriesCombinationType;
import com.yaari.ms.catalogservice.jparepositories.ShiprocketReqRespLogRepository;
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
public class ShiprocketReqRespLogDao {
	@Autowired
	ShiprocketReqRespLogRepository shiprocketReqRespLogRepository;
	@PersistenceContext
	EntityManager entityManager;

	public ShiprocketReqRespLog save(ShiprocketReqRespLog shiprocketReqRespLog) {
		return shiprocketReqRespLogRepository.save(shiprocketReqRespLog);
	}

	public List<ShiprocketReqRespLog> save(List<ShiprocketReqRespLog> shiprocketReqRespLogs) {
		return shiprocketReqRespLogRepository.saveAll(shiprocketReqRespLogs);
	}

	public EntryItem<ShiprocketReqRespLog> findByCriteriaFields(GenericSearchFilter genericSearchFilter,
																Integer pageNumber, Integer pageSize) {
		List<ShiprocketReqRespLog> shiprocketReqRespLogs;
		Map<String, ComparativeRelationAndValue> searchParams = genericSearchFilter.getSearchParams();
		log.info("Finding by criteria fields in dao as passed");
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<ShiprocketReqRespLog> cq = cb.createQuery(ShiprocketReqRespLog.class);
		Root<ShiprocketReqRespLog> shiprocketReqRespLogRoot = cq.from(ShiprocketReqRespLog.class);
		CriteriaQuery<Long> countCQ = cb.createQuery(Long.class);
		countCQ.select(cb.count(countCQ.from(ShiprocketReqRespLog.class)));
		List<Predicate> predicates = new LinkedList<>();
		for (Map.Entry<String, ComparativeRelationAndValue> entry : searchParams.entrySet()) {
			UtilityMethods.fillCriteriaQuery(cb, entry, shiprocketReqRespLogRoot, predicates);
		}
		processQueriesCombination(genericSearchFilter.getCombinationType(), predicates, cb, cq, countCQ);
		List<Order> orderBys = UtilityMethods.getOrderBys(cb, shiprocketReqRespLogRoot, genericSearchFilter.getOrderBy());
		cq.orderBy(orderBys);
		TypedQuery<ShiprocketReqRespLog> query = entityManager.createQuery(cq);
		Long count = entityManager.createQuery(countCQ).getSingleResult();
		if (pageNumber == null) pageNumber = 0;
		if (pageSize == null || pageSize == 0) pageSize = 10;
		int offset = pageNumber * pageSize;
		shiprocketReqRespLogs = query.setFirstResult(offset).setMaxResults(pageSize).getResultList();
		return new EntryItem<>(count, shiprocketReqRespLogs);
	}

	private void processQueriesCombination(QueriesCombinationType combinationType, List<Predicate> predicates, CriteriaBuilder cb, CriteriaQuery<ShiprocketReqRespLog> cq, CriteriaQuery<Long> countCQ) {

		if (combinationType== QueriesCombinationType.all_and) {
			cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
			countCQ.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		} else {
			cq.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
			countCQ.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
		}
	}


	public ShiprocketReqRespLog findById(Long id) {
		return shiprocketReqRespLogRepository.findById(id).orElse(null);
	}
}
