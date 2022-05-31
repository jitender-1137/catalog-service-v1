package com.yaari.ms.catalogservice.dao;

import com.yaari.ms.catalogservice.domains.SupplierComplaint;
import com.yaari.ms.catalogservice.dto.ComparativeRelationAndValue;
import com.yaari.ms.catalogservice.dto.EntryItem;
import com.yaari.ms.catalogservice.dto.GenericSearchFilter;
import com.yaari.ms.catalogservice.enums.QueriesCombinationType;
import com.yaari.ms.catalogservice.jparepositories.SupplierComplaintRepository;
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
public class SupplierComplaintDao {
	@Autowired
	SupplierComplaintRepository supplierComplaintRepository;
	@PersistenceContext
	EntityManager entityManager;

	public SupplierComplaint save(SupplierComplaint supplierComplaint) {
		return supplierComplaintRepository.save(supplierComplaint);
	}

	public List<SupplierComplaint> save(List<SupplierComplaint> supplierComplaints) {
		return supplierComplaintRepository.saveAll(supplierComplaints);
	}

	public EntryItem<SupplierComplaint> findByCriteriaFields(GenericSearchFilter genericSearchFilter,
																Integer pageNumber, Integer pageSize) {
		List<SupplierComplaint> supplierComplaints;
		Map<String, ComparativeRelationAndValue> searchParams = genericSearchFilter.getSearchParams();
		log.info("Finding by criteria fields in dao as passed");
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<SupplierComplaint> cq = cb.createQuery(SupplierComplaint.class);
		Root<SupplierComplaint> supplierComplaintRoot = cq.from(SupplierComplaint.class);
		CriteriaQuery<Long> countCQ = cb.createQuery(Long.class);
		countCQ.select(cb.count(countCQ.from(SupplierComplaint.class)));
		List<Predicate> predicates = new LinkedList<>();
		for (Map.Entry<String, ComparativeRelationAndValue> entry : searchParams.entrySet()) {
			UtilityMethods.fillCriteriaQuery(cb, entry, supplierComplaintRoot, predicates);
		}
		processQueriesCombination(genericSearchFilter.getCombinationType(), predicates, cb, cq, countCQ);
		List<Order> orderBys = UtilityMethods.getOrderBys(cb, supplierComplaintRoot, genericSearchFilter.getOrderBy());
		cq.orderBy(orderBys);
		TypedQuery<SupplierComplaint> query = entityManager.createQuery(cq);
		Long count = entityManager.createQuery(countCQ).getSingleResult();
		if (pageNumber == null) pageNumber = 0;
		if (pageSize == null || pageSize == 0) pageSize = 10;
		int offset = pageNumber * pageSize;
		supplierComplaints = query.setFirstResult(offset).setMaxResults(pageSize).getResultList();
		return new EntryItem<>(count, supplierComplaints);
	}

	private void processQueriesCombination(QueriesCombinationType combinationType, List<Predicate> predicates, CriteriaBuilder cb, CriteriaQuery<SupplierComplaint> cq, CriteriaQuery<Long> countCQ) {

		if (combinationType== QueriesCombinationType.all_and) {
			cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
			countCQ.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		} else {
			cq.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
			countCQ.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
		}
	}


	public SupplierComplaint findById(Long id) {
		return supplierComplaintRepository.findById(id).orElse(null);
	}
}
