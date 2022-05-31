package com.yaari.ms.catalogservice.dao;

import com.yaari.ms.catalogservice.domains.ShiprocketPickupLocations;
import com.yaari.ms.catalogservice.dto.ComparativeRelationAndValue;
import com.yaari.ms.catalogservice.dto.EntryItem;
import com.yaari.ms.catalogservice.dto.GenericSearchFilter;
import com.yaari.ms.catalogservice.enums.QueriesCombinationType;
import com.yaari.ms.catalogservice.jparepositories.ShiprocketPickupLocationsRepository;
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
public class ShiprocketPickupLocationsDao {
	@Autowired
	ShiprocketPickupLocationsRepository shiprocketPickupLocationsRepository;
	@PersistenceContext
	EntityManager entityManager;

	public ShiprocketPickupLocations save(ShiprocketPickupLocations shiprocketPickupLocations) {
		return shiprocketPickupLocationsRepository.save(shiprocketPickupLocations);
	}

	public List<ShiprocketPickupLocations> save(List<ShiprocketPickupLocations> shiprocketPickupLocationss) {
		return shiprocketPickupLocationsRepository.saveAll(shiprocketPickupLocationss);
	}

	public EntryItem<ShiprocketPickupLocations> findByCriteriaFields(GenericSearchFilter genericSearchFilter,
																Integer pageNumber, Integer pageSize) {
		List<ShiprocketPickupLocations> shiprocketPickupLocationss;
		Map<String, ComparativeRelationAndValue> searchParams = genericSearchFilter.getSearchParams();
		log.info("Finding by criteria fields in dao as passed");
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<ShiprocketPickupLocations> cq = cb.createQuery(ShiprocketPickupLocations.class);
		Root<ShiprocketPickupLocations> shiprocketPickupLocationsRoot = cq.from(ShiprocketPickupLocations.class);
		CriteriaQuery<Long> countCQ = cb.createQuery(Long.class);
		countCQ.select(cb.count(countCQ.from(ShiprocketPickupLocations.class)));
		List<Predicate> predicates = new LinkedList<>();
		for (Map.Entry<String, ComparativeRelationAndValue> entry : searchParams.entrySet()) {
			UtilityMethods.fillCriteriaQuery(cb, entry, shiprocketPickupLocationsRoot, predicates);
		}
		processQueriesCombination(genericSearchFilter.getCombinationType(), predicates, cb, cq, countCQ);
		List<Order> orderBys = UtilityMethods.getOrderBys(cb, shiprocketPickupLocationsRoot, genericSearchFilter.getOrderBy());
		cq.orderBy(orderBys);
		TypedQuery<ShiprocketPickupLocations> query = entityManager.createQuery(cq);
		Long count = entityManager.createQuery(countCQ).getSingleResult();
		if (pageNumber == null) pageNumber = 0;
		if (pageSize == null || pageSize == 0) pageSize = 10;
		int offset = pageNumber * pageSize;
		shiprocketPickupLocationss = query.setFirstResult(offset).setMaxResults(pageSize).getResultList();
		return new EntryItem<>(count, shiprocketPickupLocationss);
	}

	private void processQueriesCombination(QueriesCombinationType combinationType, List<Predicate> predicates, CriteriaBuilder cb, CriteriaQuery<ShiprocketPickupLocations> cq, CriteriaQuery<Long> countCQ) {

		if (combinationType== QueriesCombinationType.all_and) {
			cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
			countCQ.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		} else {
			cq.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
			countCQ.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
		}
	}


	public ShiprocketPickupLocations findById(Long id) {
		return shiprocketPickupLocationsRepository.findById(id).orElse(null);
	}
}
