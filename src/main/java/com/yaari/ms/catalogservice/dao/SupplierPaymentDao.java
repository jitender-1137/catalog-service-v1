package com.yaari.ms.catalogservice.dao;

import com.yaari.ms.catalogservice.domains.SupplierPayment;
import com.yaari.ms.catalogservice.dto.ComparativeRelationAndValue;
import com.yaari.ms.catalogservice.dto.EntryItem;
import com.yaari.ms.catalogservice.dto.GenericSearchFilter;
import com.yaari.ms.catalogservice.enums.QueriesCombinationType;
import com.yaari.ms.catalogservice.jparepositories.SupplierPaymentRepository;
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
public class SupplierPaymentDao {
	@Autowired
	SupplierPaymentRepository supplierPaymentRepository;
	@PersistenceContext
	EntityManager entityManager;

	public SupplierPayment save(SupplierPayment supplierPayment) {
		return supplierPaymentRepository.save(supplierPayment);
	}

	public List<SupplierPayment> save(List<SupplierPayment> supplierPayments) {
		return supplierPaymentRepository.saveAll(supplierPayments);
	}

	public EntryItem<SupplierPayment> findByCriteriaFields(GenericSearchFilter genericSearchFilter,
																Integer pageNumber, Integer pageSize) {
		List<SupplierPayment> supplierPayments;
		Map<String, ComparativeRelationAndValue> searchParams = genericSearchFilter.getSearchParams();
		log.info("Finding by criteria fields in dao as passed");
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<SupplierPayment> cq = cb.createQuery(SupplierPayment.class);
		Root<SupplierPayment> supplierPaymentRoot = cq.from(SupplierPayment.class);
		CriteriaQuery<Long> countCQ = cb.createQuery(Long.class);
		countCQ.select(cb.count(countCQ.from(SupplierPayment.class)));
		List<Predicate> predicates = new LinkedList<>();
		for (Map.Entry<String, ComparativeRelationAndValue> entry : searchParams.entrySet()) {
			UtilityMethods.fillCriteriaQuery(cb, entry, supplierPaymentRoot, predicates);
		}
		processQueriesCombination(genericSearchFilter.getCombinationType(), predicates, cb, cq, countCQ);
		List<Order> orderBys = UtilityMethods.getOrderBys(cb, supplierPaymentRoot, genericSearchFilter.getOrderBy());
		cq.orderBy(orderBys);
		TypedQuery<SupplierPayment> query = entityManager.createQuery(cq);
		Long count = entityManager.createQuery(countCQ).getSingleResult();
		if (pageNumber == null) pageNumber = 0;
		if (pageSize == null || pageSize == 0) pageSize = 10;
		int offset = pageNumber * pageSize;
		supplierPayments = query.setFirstResult(offset).setMaxResults(pageSize).getResultList();
		return new EntryItem<>(count, supplierPayments);
	}

	private void processQueriesCombination(QueriesCombinationType combinationType, List<Predicate> predicates, CriteriaBuilder cb, CriteriaQuery<SupplierPayment> cq, CriteriaQuery<Long> countCQ) {

		if (combinationType== QueriesCombinationType.all_and) {
			cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
			countCQ.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		} else {
			cq.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
			countCQ.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
		}
	}


	public SupplierPayment findById(Long id) {
		return supplierPaymentRepository.findById(id).orElse(null);
	}
}
