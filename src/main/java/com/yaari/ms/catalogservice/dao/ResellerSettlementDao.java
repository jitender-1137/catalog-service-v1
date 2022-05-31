package com.yaari.ms.catalogservice.dao;

import com.yaari.ms.catalogservice.domains.ResellerSettlement;
import com.yaari.ms.catalogservice.dto.ComparativeRelationAndValue;
import com.yaari.ms.catalogservice.dto.EntryItem;
import com.yaari.ms.catalogservice.dto.GenericSearchFilter;
import com.yaari.ms.catalogservice.enums.QueriesCombinationType;
import com.yaari.ms.catalogservice.jparepositories.ResellerSettlementRepository;
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
public class ResellerSettlementDao {
	@Autowired
	ResellerSettlementRepository resellerSettlementRepository;
	@PersistenceContext
	EntityManager entityManager;

	public ResellerSettlement save(ResellerSettlement resellerSettlement) {
		return resellerSettlementRepository.save(resellerSettlement);
	}

	public List<ResellerSettlement> save(List<ResellerSettlement> resellerSettlements) {
		return resellerSettlementRepository.saveAll(resellerSettlements);
	}

	public EntryItem<ResellerSettlement> findByCriteriaFields(GenericSearchFilter genericSearchFilter,
																Integer pageNumber, Integer pageSize) {
		List<ResellerSettlement> resellerSettlements;
		Map<String, ComparativeRelationAndValue> searchParams = genericSearchFilter.getSearchParams();
		log.info("Finding by criteria fields in dao as passed");
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<ResellerSettlement> cq = cb.createQuery(ResellerSettlement.class);
		Root<ResellerSettlement> resellerSettlementRoot = cq.from(ResellerSettlement.class);
		CriteriaQuery<Long> countCQ = cb.createQuery(Long.class);
		countCQ.select(cb.count(countCQ.from(ResellerSettlement.class)));
		List<Predicate> predicates = new LinkedList<>();
		for (Map.Entry<String, ComparativeRelationAndValue> entry : searchParams.entrySet()) {
			UtilityMethods.fillCriteriaQuery(cb, entry, resellerSettlementRoot, predicates);
		}
		processQueriesCombination(genericSearchFilter.getCombinationType(), predicates, cb, cq, countCQ);
		List<Order> orderBys = UtilityMethods.getOrderBys(cb, resellerSettlementRoot, genericSearchFilter.getOrderBy());
		cq.orderBy(orderBys);
		TypedQuery<ResellerSettlement> query = entityManager.createQuery(cq);
		Long count = entityManager.createQuery(countCQ).getSingleResult();
		if (pageNumber == null) pageNumber = 0;
		if (pageSize == null || pageSize == 0) pageSize = 10;
		int offset = pageNumber * pageSize;
		resellerSettlements = query.setFirstResult(offset).setMaxResults(pageSize).getResultList();
		return new EntryItem<>(count, resellerSettlements);
	}

	private void processQueriesCombination(QueriesCombinationType combinationType, List<Predicate> predicates, CriteriaBuilder cb, CriteriaQuery<ResellerSettlement> cq, CriteriaQuery<Long> countCQ) {

		if (combinationType== QueriesCombinationType.all_and) {
			cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
			countCQ.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		} else {
			cq.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
			countCQ.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
		}
	}


	public ResellerSettlement findById(Long id) {
		return resellerSettlementRepository.findById(id).orElse(null);
	}
}
