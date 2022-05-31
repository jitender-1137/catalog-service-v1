package com.yaari.ms.catalogservice.dao;

import com.yaari.ms.catalogservice.domains.OrderItemReturnMapping;
import com.yaari.ms.catalogservice.dto.ComparativeRelationAndValue;
import com.yaari.ms.catalogservice.dto.EntryItem;
import com.yaari.ms.catalogservice.dto.GenericSearchFilter;
import com.yaari.ms.catalogservice.enums.QueriesCombinationType;
import com.yaari.ms.catalogservice.jparepositories.OrderItemReturnMappingRepository;
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
public class OrderItemReturnMappingDao {
	@Autowired
	OrderItemReturnMappingRepository orderItemReturnMappingRepository;
	@PersistenceContext
	EntityManager entityManager;

	public OrderItemReturnMapping save(OrderItemReturnMapping orderItemReturnMapping) {
		return orderItemReturnMappingRepository.save(orderItemReturnMapping);
	}

	public List<OrderItemReturnMapping> save(List<OrderItemReturnMapping> orderItemReturnMappings) {
		return orderItemReturnMappingRepository.saveAll(orderItemReturnMappings);
	}

	public EntryItem<OrderItemReturnMapping> findByCriteriaFields(GenericSearchFilter genericSearchFilter,
																Integer pageNumber, Integer pageSize) {
		List<OrderItemReturnMapping> orderItemReturnMappings;
		Map<String, ComparativeRelationAndValue> searchParams = genericSearchFilter.getSearchParams();
		log.info("Finding by criteria fields in dao as passed");
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<OrderItemReturnMapping> cq = cb.createQuery(OrderItemReturnMapping.class);
		Root<OrderItemReturnMapping> orderItemReturnMappingRoot = cq.from(OrderItemReturnMapping.class);
		CriteriaQuery<Long> countCQ = cb.createQuery(Long.class);
		countCQ.select(cb.count(countCQ.from(OrderItemReturnMapping.class)));
		List<Predicate> predicates = new LinkedList<>();
		for (Map.Entry<String, ComparativeRelationAndValue> entry : searchParams.entrySet()) {
			UtilityMethods.fillCriteriaQuery(cb, entry, orderItemReturnMappingRoot, predicates);
		}
		processQueriesCombination(genericSearchFilter.getCombinationType(), predicates, cb, cq, countCQ);
		List<Order> orderBys = UtilityMethods.getOrderBys(cb, orderItemReturnMappingRoot, genericSearchFilter.getOrderBy());
		cq.orderBy(orderBys);
		TypedQuery<OrderItemReturnMapping> query = entityManager.createQuery(cq);
		Long count = entityManager.createQuery(countCQ).getSingleResult();
		if (pageNumber == null) pageNumber = 0;
		if (pageSize == null || pageSize == 0) pageSize = 10;
		int offset = pageNumber * pageSize;
		orderItemReturnMappings = query.setFirstResult(offset).setMaxResults(pageSize).getResultList();
		return new EntryItem<>(count, orderItemReturnMappings);
	}

	private void processQueriesCombination(QueriesCombinationType combinationType, List<Predicate> predicates, CriteriaBuilder cb, CriteriaQuery<OrderItemReturnMapping> cq, CriteriaQuery<Long> countCQ) {

		if (combinationType== QueriesCombinationType.all_and) {
			cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
			countCQ.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		} else {
			cq.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
			countCQ.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
		}
	}


	public OrderItemReturnMapping findById(Long id) {
		return orderItemReturnMappingRepository.findById(id).orElse(null);
	}
}
