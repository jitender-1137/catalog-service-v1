package com.yaari.ms.catalogservice.dao;

import com.yaari.ms.catalogservice.domains.ShipmentHistory;
import com.yaari.ms.catalogservice.dto.ComparativeRelationAndValue;
import com.yaari.ms.catalogservice.dto.EntryItem;
import com.yaari.ms.catalogservice.dto.GenericSearchFilter;
import com.yaari.ms.catalogservice.enums.QueriesCombinationType;
import com.yaari.ms.catalogservice.jparepositories.ShipmentHistoryRepository;
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
public class ShipmentHistoryDao {
	@Autowired
	ShipmentHistoryRepository shipmentHistoryRepository;
	@PersistenceContext
	EntityManager entityManager;

	public ShipmentHistory save(ShipmentHistory shipmentHistory) {
		return shipmentHistoryRepository.save(shipmentHistory);
	}

	public List<ShipmentHistory> save(List<ShipmentHistory> shipmentHistorys) {
		return shipmentHistoryRepository.saveAll(shipmentHistorys);
	}

	public EntryItem<ShipmentHistory> findByCriteriaFields(GenericSearchFilter genericSearchFilter,
																Integer pageNumber, Integer pageSize) {
		List<ShipmentHistory> shipmentHistorys;
		Map<String, ComparativeRelationAndValue> searchParams = genericSearchFilter.getSearchParams();
		log.info("Finding by criteria fields in dao as passed");
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<ShipmentHistory> cq = cb.createQuery(ShipmentHistory.class);
		Root<ShipmentHistory> shipmentHistoryRoot = cq.from(ShipmentHistory.class);
		CriteriaQuery<Long> countCQ = cb.createQuery(Long.class);
		countCQ.select(cb.count(countCQ.from(ShipmentHistory.class)));
		List<Predicate> predicates = new LinkedList<>();
		for (Map.Entry<String, ComparativeRelationAndValue> entry : searchParams.entrySet()) {
			UtilityMethods.fillCriteriaQuery(cb, entry, shipmentHistoryRoot, predicates);
		}
		processQueriesCombination(genericSearchFilter.getCombinationType(), predicates, cb, cq, countCQ);
		List<Order> orderBys = UtilityMethods.getOrderBys(cb, shipmentHistoryRoot, genericSearchFilter.getOrderBy());
		cq.orderBy(orderBys);
		TypedQuery<ShipmentHistory> query = entityManager.createQuery(cq);
		Long count = entityManager.createQuery(countCQ).getSingleResult();
		if (pageNumber == null) pageNumber = 0;
		if (pageSize == null || pageSize == 0) pageSize = 10;
		int offset = pageNumber * pageSize;
		shipmentHistorys = query.setFirstResult(offset).setMaxResults(pageSize).getResultList();
		return new EntryItem<>(count, shipmentHistorys);
	}

	private void processQueriesCombination(QueriesCombinationType combinationType, List<Predicate> predicates, CriteriaBuilder cb, CriteriaQuery<ShipmentHistory> cq, CriteriaQuery<Long> countCQ) {

		if (combinationType== QueriesCombinationType.all_and) {
			cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
			countCQ.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		} else {
			cq.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
			countCQ.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
		}
	}


	public ShipmentHistory findById(Long id) {
		return shipmentHistoryRepository.findById(id).orElse(null);
	}
}
