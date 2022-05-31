package com.yaari.ms.catalogservice.dao;

import com.yaari.ms.catalogservice.domains.BankValidationFailedLog;
import com.yaari.ms.catalogservice.dto.ComparativeRelationAndValue;
import com.yaari.ms.catalogservice.dto.EntryItem;
import com.yaari.ms.catalogservice.dto.GenericSearchFilter;
import com.yaari.ms.catalogservice.enums.QueriesCombinationType;
import com.yaari.ms.catalogservice.jparepositories.BankValidationFailedLogRepository;
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
public class BankValidationFailedLogDao {
	@Autowired
	BankValidationFailedLogRepository bankValidationFailedLogRepository;
	@PersistenceContext
	EntityManager entityManager;

	public BankValidationFailedLog save(BankValidationFailedLog bankValidationFailedLog) {
		return bankValidationFailedLogRepository.save(bankValidationFailedLog);
	}

	public List<BankValidationFailedLog> save(List<BankValidationFailedLog> bankValidationFailedLogs) {
		return bankValidationFailedLogRepository.saveAll(bankValidationFailedLogs);
	}

	public EntryItem<BankValidationFailedLog> findByCriteriaFields(GenericSearchFilter genericSearchFilter,
																Integer pageNumber, Integer pageSize) {
		List<BankValidationFailedLog> bankValidationFailedLogs;
		Map<String, ComparativeRelationAndValue> searchParams = genericSearchFilter.getSearchParams();
		log.info("Finding by criteria fields in dao as passed");
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<BankValidationFailedLog> cq = cb.createQuery(BankValidationFailedLog.class);
		Root<BankValidationFailedLog> bankValidationFailedLogRoot = cq.from(BankValidationFailedLog.class);
		CriteriaQuery<Long> countCQ = cb.createQuery(Long.class);
		countCQ.select(cb.count(countCQ.from(BankValidationFailedLog.class)));
		List<Predicate> predicates = new LinkedList<>();
		for (Map.Entry<String, ComparativeRelationAndValue> entry : searchParams.entrySet()) {
			UtilityMethods.fillCriteriaQuery(cb, entry, bankValidationFailedLogRoot, predicates);
		}
		processQueriesCombination(genericSearchFilter.getCombinationType(), predicates, cb, cq, countCQ);
		List<Order> orderBys = UtilityMethods.getOrderBys(cb, bankValidationFailedLogRoot, genericSearchFilter.getOrderBy());
		cq.orderBy(orderBys);
		TypedQuery<BankValidationFailedLog> query = entityManager.createQuery(cq);
		Long count = entityManager.createQuery(countCQ).getSingleResult();
		if (pageNumber == null) pageNumber = 0;
		if (pageSize == null || pageSize == 0) pageSize = 10;
		int offset = pageNumber * pageSize;
		bankValidationFailedLogs = query.setFirstResult(offset).setMaxResults(pageSize).getResultList();
		return new EntryItem<>(count, bankValidationFailedLogs);
	}

	private void processQueriesCombination(QueriesCombinationType combinationType, List<Predicate> predicates, CriteriaBuilder cb, CriteriaQuery<BankValidationFailedLog> cq, CriteriaQuery<Long> countCQ) {

		if (combinationType== QueriesCombinationType.all_and) {
			cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
			countCQ.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		} else {
			cq.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
			countCQ.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
		}
	}


	public BankValidationFailedLog findById(Long id) {
		return bankValidationFailedLogRepository.findById(id).orElse(null);
	}
}
