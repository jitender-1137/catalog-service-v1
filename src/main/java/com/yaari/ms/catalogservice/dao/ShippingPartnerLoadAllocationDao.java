package com.yaari.ms.catalogservice.dao;

import com.yaari.ms.catalogservice.domains.ShippingPartnerLoadAllocation;
import com.yaari.ms.catalogservice.dto.ComparativeRelationAndValue;
import com.yaari.ms.catalogservice.dto.EntryItem;
import com.yaari.ms.catalogservice.dto.GenericSearchFilter;
import com.yaari.ms.catalogservice.enums.QueriesCombinationType;
import com.yaari.ms.catalogservice.jparepositories.ShippingPartnerLoadAllocationRepository;
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
public class ShippingPartnerLoadAllocationDao {
	@Autowired
	ShippingPartnerLoadAllocationRepository shippingPartnerLoadAllocationRepository;
	@PersistenceContext
	EntityManager entityManager;

	public ShippingPartnerLoadAllocation save(ShippingPartnerLoadAllocation shippingPartnerLoadAllocation) {
		return shippingPartnerLoadAllocationRepository.save(shippingPartnerLoadAllocation);
	}

	public List<ShippingPartnerLoadAllocation> save(List<ShippingPartnerLoadAllocation> shippingPartnerLoadAllocations) {
		return shippingPartnerLoadAllocationRepository.saveAll(shippingPartnerLoadAllocations);
	}

	public EntryItem<ShippingPartnerLoadAllocation> findByCriteriaFields(GenericSearchFilter genericSearchFilter,
																Integer pageNumber, Integer pageSize) {
		List<ShippingPartnerLoadAllocation> shippingPartnerLoadAllocations;
		Map<String, ComparativeRelationAndValue> searchParams = genericSearchFilter.getSearchParams();
		log.info("Finding by criteria fields in dao as passed");
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<ShippingPartnerLoadAllocation> cq = cb.createQuery(ShippingPartnerLoadAllocation.class);
		Root<ShippingPartnerLoadAllocation> shippingPartnerLoadAllocationRoot = cq.from(ShippingPartnerLoadAllocation.class);
		CriteriaQuery<Long> countCQ = cb.createQuery(Long.class);
		countCQ.select(cb.count(countCQ.from(ShippingPartnerLoadAllocation.class)));
		List<Predicate> predicates = new LinkedList<>();
		for (Map.Entry<String, ComparativeRelationAndValue> entry : searchParams.entrySet()) {
			UtilityMethods.fillCriteriaQuery(cb, entry, shippingPartnerLoadAllocationRoot, predicates);
		}
		processQueriesCombination(genericSearchFilter.getCombinationType(), predicates, cb, cq, countCQ);
		List<Order> orderBys = UtilityMethods.getOrderBys(cb, shippingPartnerLoadAllocationRoot, genericSearchFilter.getOrderBy());
		cq.orderBy(orderBys);
		TypedQuery<ShippingPartnerLoadAllocation> query = entityManager.createQuery(cq);
		Long count = entityManager.createQuery(countCQ).getSingleResult();
		if (pageNumber == null) pageNumber = 0;
		if (pageSize == null || pageSize == 0) pageSize = 10;
		int offset = pageNumber * pageSize;
		shippingPartnerLoadAllocations = query.setFirstResult(offset).setMaxResults(pageSize).getResultList();
		return new EntryItem<>(count, shippingPartnerLoadAllocations);
	}

	private void processQueriesCombination(QueriesCombinationType combinationType, List<Predicate> predicates, CriteriaBuilder cb, CriteriaQuery<ShippingPartnerLoadAllocation> cq, CriteriaQuery<Long> countCQ) {

		if (combinationType== QueriesCombinationType.all_and) {
			cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
			countCQ.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		} else {
			cq.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
			countCQ.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
		}
	}


	public ShippingPartnerLoadAllocation findById(Long id) {
		return shippingPartnerLoadAllocationRepository.findById(id).orElse(null);
	}
}
