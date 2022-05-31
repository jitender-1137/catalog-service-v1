package com.yaari.ms.catalogservice.dao;

import com.yaari.ms.catalogservice.domains.SupplierBlacklist;
import com.yaari.ms.catalogservice.dto.ComparativeRelationAndValue;
import com.yaari.ms.catalogservice.dto.EntryItem;
import com.yaari.ms.catalogservice.dto.GenericSearchFilter;
import com.yaari.ms.catalogservice.enums.QueriesCombinationType;
import com.yaari.ms.catalogservice.jparepositories.SupplierBlacklistRepository;
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
public class SupplierBlacklistDao {
	@Autowired
	SupplierBlacklistRepository supplierBlacklistRepository;
	@PersistenceContext
	EntityManager entityManager;

	public SupplierBlacklist save(SupplierBlacklist supplierBlacklist) {
		return supplierBlacklistRepository.save(supplierBlacklist);
	}

	public List<SupplierBlacklist> save(List<SupplierBlacklist> supplierBlacklists) {
		return supplierBlacklistRepository.saveAll(supplierBlacklists);
	}

	public EntryItem<SupplierBlacklist> findByCriteriaFields(GenericSearchFilter genericSearchFilter,
																Integer pageNumber, Integer pageSize) {
		List<SupplierBlacklist> supplierBlacklists;
		Map<String, ComparativeRelationAndValue> searchParams = genericSearchFilter.getSearchParams();
		log.info("Finding by criteria fields in dao as passed");
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<SupplierBlacklist> cq = cb.createQuery(SupplierBlacklist.class);
		Root<SupplierBlacklist> supplierBlacklistRoot = cq.from(SupplierBlacklist.class);
		CriteriaQuery<Long> countCQ = cb.createQuery(Long.class);
		countCQ.select(cb.count(countCQ.from(SupplierBlacklist.class)));
		List<Predicate> predicates = new LinkedList<>();
		for (Map.Entry<String, ComparativeRelationAndValue> entry : searchParams.entrySet()) {
			UtilityMethods.fillCriteriaQuery(cb, entry, supplierBlacklistRoot, predicates);
		}
		processQueriesCombination(genericSearchFilter.getCombinationType(), predicates, cb, cq, countCQ);
		List<Order> orderBys = UtilityMethods.getOrderBys(cb, supplierBlacklistRoot, genericSearchFilter.getOrderBy());
		cq.orderBy(orderBys);
		TypedQuery<SupplierBlacklist> query = entityManager.createQuery(cq);
		Long count = entityManager.createQuery(countCQ).getSingleResult();
		if (pageNumber == null) pageNumber = 0;
		if (pageSize == null || pageSize == 0) pageSize = 10;
		int offset = pageNumber * pageSize;
		supplierBlacklists = query.setFirstResult(offset).setMaxResults(pageSize).getResultList();
		return new EntryItem<>(count, supplierBlacklists);
	}

	private void processQueriesCombination(QueriesCombinationType combinationType, List<Predicate> predicates, CriteriaBuilder cb, CriteriaQuery<SupplierBlacklist> cq, CriteriaQuery<Long> countCQ) {

		if (combinationType== QueriesCombinationType.all_and) {
			cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
			countCQ.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		} else {
			cq.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
			countCQ.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
		}
	}


	public SupplierBlacklist findById(Long id) {
		return supplierBlacklistRepository.findById(id).orElse(null);
	}
}
