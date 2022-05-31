package com.yaari.ms.catalogservice.dao;

import com.yaari.ms.catalogservice.domains.ReturnDelivery;
import com.yaari.ms.catalogservice.dto.ComparativeRelationAndValue;
import com.yaari.ms.catalogservice.dto.EntryItem;
import com.yaari.ms.catalogservice.dto.GenericSearchFilter;
import com.yaari.ms.catalogservice.enums.QueriesCombinationType;
import com.yaari.ms.catalogservice.jparepositories.ReturnDeliveryRepository;
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
public class ReturnDeliveryDao {
	@Autowired
	ReturnDeliveryRepository returnDeliveryRepository;
	@PersistenceContext
	EntityManager entityManager;

	public ReturnDelivery save(ReturnDelivery returnDelivery) {
		return returnDeliveryRepository.save(returnDelivery);
	}

	public List<ReturnDelivery> save(List<ReturnDelivery> returnDeliverys) {
		return returnDeliveryRepository.saveAll(returnDeliverys);
	}

	public EntryItem<ReturnDelivery> findByCriteriaFields(GenericSearchFilter genericSearchFilter,
																Integer pageNumber, Integer pageSize) {
		List<ReturnDelivery> returnDeliverys;
		Map<String, ComparativeRelationAndValue> searchParams = genericSearchFilter.getSearchParams();
		log.info("Finding by criteria fields in dao as passed");
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<ReturnDelivery> cq = cb.createQuery(ReturnDelivery.class);
		Root<ReturnDelivery> returnDeliveryRoot = cq.from(ReturnDelivery.class);
		CriteriaQuery<Long> countCQ = cb.createQuery(Long.class);
		countCQ.select(cb.count(countCQ.from(ReturnDelivery.class)));
		List<Predicate> predicates = new LinkedList<>();
		for (Map.Entry<String, ComparativeRelationAndValue> entry : searchParams.entrySet()) {
			UtilityMethods.fillCriteriaQuery(cb, entry, returnDeliveryRoot, predicates);
		}
		processQueriesCombination(genericSearchFilter.getCombinationType(), predicates, cb, cq, countCQ);
		List<Order> orderBys = UtilityMethods.getOrderBys(cb, returnDeliveryRoot, genericSearchFilter.getOrderBy());
		cq.orderBy(orderBys);
		TypedQuery<ReturnDelivery> query = entityManager.createQuery(cq);
		Long count = entityManager.createQuery(countCQ).getSingleResult();
		if (pageNumber == null) pageNumber = 0;
		if (pageSize == null || pageSize == 0) pageSize = 10;
		int offset = pageNumber * pageSize;
		returnDeliverys = query.setFirstResult(offset).setMaxResults(pageSize).getResultList();
		return new EntryItem<>(count, returnDeliverys);
	}

	private void processQueriesCombination(QueriesCombinationType combinationType, List<Predicate> predicates, CriteriaBuilder cb, CriteriaQuery<ReturnDelivery> cq, CriteriaQuery<Long> countCQ) {

		if (combinationType== QueriesCombinationType.all_and) {
			cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
			countCQ.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		} else {
			cq.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
			countCQ.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
		}
	}


	public ReturnDelivery findById(Long id) {
		return returnDeliveryRepository.findById(id).orElse(null);
	}
}
