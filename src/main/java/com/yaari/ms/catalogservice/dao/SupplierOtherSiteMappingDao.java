package com.yaari.ms.catalogservice.dao;

import com.yaari.ms.catalogservice.domains.SupplierOtherSiteMapping;
import com.yaari.ms.catalogservice.dto.ComparativeRelationAndValue;
import com.yaari.ms.catalogservice.dto.EntryItem;
import com.yaari.ms.catalogservice.dto.GenericSearchFilter;
import com.yaari.ms.catalogservice.enums.QueriesCombinationType;
import com.yaari.ms.catalogservice.jparepositories.SupplierOtherSiteMappingRepository;
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
public class SupplierOtherSiteMappingDao {
	@Autowired
	SupplierOtherSiteMappingRepository supplierOtherSiteMappingRepository;
	@PersistenceContext
	EntityManager entityManager;

	public SupplierOtherSiteMapping save(SupplierOtherSiteMapping supplierOtherSiteMapping) {
		return supplierOtherSiteMappingRepository.save(supplierOtherSiteMapping);
	}

	public List<SupplierOtherSiteMapping> save(List<SupplierOtherSiteMapping> supplierOtherSiteMappings) {
		return supplierOtherSiteMappingRepository.saveAll(supplierOtherSiteMappings);
	}

	public EntryItem<SupplierOtherSiteMapping> findByCriteriaFields(GenericSearchFilter genericSearchFilter,
																Integer pageNumber, Integer pageSize) {
		List<SupplierOtherSiteMapping> supplierOtherSiteMappings;
		Map<String, ComparativeRelationAndValue> searchParams = genericSearchFilter.getSearchParams();
		log.info("Finding by criteria fields in dao as passed");
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<SupplierOtherSiteMapping> cq = cb.createQuery(SupplierOtherSiteMapping.class);
		Root<SupplierOtherSiteMapping> supplierOtherSiteMappingRoot = cq.from(SupplierOtherSiteMapping.class);
		CriteriaQuery<Long> countCQ = cb.createQuery(Long.class);
		countCQ.select(cb.count(countCQ.from(SupplierOtherSiteMapping.class)));
		List<Predicate> predicates = new LinkedList<>();
		for (Map.Entry<String, ComparativeRelationAndValue> entry : searchParams.entrySet()) {
			UtilityMethods.fillCriteriaQuery(cb, entry, supplierOtherSiteMappingRoot, predicates);
		}
		processQueriesCombination(genericSearchFilter.getCombinationType(), predicates, cb, cq, countCQ);
		List<Order> orderBys = UtilityMethods.getOrderBys(cb, supplierOtherSiteMappingRoot, genericSearchFilter.getOrderBy());
		cq.orderBy(orderBys);
		TypedQuery<SupplierOtherSiteMapping> query = entityManager.createQuery(cq);
		Long count = entityManager.createQuery(countCQ).getSingleResult();
		if (pageNumber == null) pageNumber = 0;
		if (pageSize == null || pageSize == 0) pageSize = 10;
		int offset = pageNumber * pageSize;
		supplierOtherSiteMappings = query.setFirstResult(offset).setMaxResults(pageSize).getResultList();
		return new EntryItem<>(count, supplierOtherSiteMappings);
	}

	private void processQueriesCombination(QueriesCombinationType combinationType, List<Predicate> predicates, CriteriaBuilder cb, CriteriaQuery<SupplierOtherSiteMapping> cq, CriteriaQuery<Long> countCQ) {

		if (combinationType== QueriesCombinationType.all_and) {
			cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
			countCQ.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		} else {
			cq.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
			countCQ.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
		}
	}


	public SupplierOtherSiteMapping findById(Long id) {
		return supplierOtherSiteMappingRepository.findById(id).orElse(null);
	}
}
