package com.yaari.ms.catalogservice.dao;

import com.yaari.ms.catalogservice.domains.Zone;
import com.yaari.ms.catalogservice.dto.ComparativeRelationAndValue;
import com.yaari.ms.catalogservice.dto.EntryItem;
import com.yaari.ms.catalogservice.dto.GenericSearchFilter;
import com.yaari.ms.catalogservice.enums.QueriesCombinationType;
import com.yaari.ms.catalogservice.jparepositories.ZoneRepository;
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
public class ZoneDao {
	@Autowired
	ZoneRepository zoneRepository;
	@PersistenceContext
	EntityManager entityManager;

	public Zone save(Zone zone) {
		return zoneRepository.save(zone);
	}

	public List<Zone> save(List<Zone> zones) {
		return zoneRepository.saveAll(zones);
	}

	public EntryItem<Zone> findByCriteriaFields(GenericSearchFilter genericSearchFilter,
																Integer pageNumber, Integer pageSize) {
		List<Zone> zones;
		Map<String, ComparativeRelationAndValue> searchParams = genericSearchFilter.getSearchParams();
		log.info("Finding by criteria fields in dao as passed");
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Zone> cq = cb.createQuery(Zone.class);
		Root<Zone> zoneRoot = cq.from(Zone.class);
		CriteriaQuery<Long> countCQ = cb.createQuery(Long.class);
		countCQ.select(cb.count(countCQ.from(Zone.class)));
		List<Predicate> predicates = new LinkedList<>();
		for (Map.Entry<String, ComparativeRelationAndValue> entry : searchParams.entrySet()) {
			UtilityMethods.fillCriteriaQuery(cb, entry, zoneRoot, predicates);
		}
		processQueriesCombination(genericSearchFilter.getCombinationType(), predicates, cb, cq, countCQ);
		List<Order> orderBys = UtilityMethods.getOrderBys(cb, zoneRoot, genericSearchFilter.getOrderBy());
		cq.orderBy(orderBys);
		TypedQuery<Zone> query = entityManager.createQuery(cq);
		Long count = entityManager.createQuery(countCQ).getSingleResult();
		if (pageNumber == null) pageNumber = 0;
		if (pageSize == null || pageSize == 0) pageSize = 10;
		int offset = pageNumber * pageSize;
		zones = query.setFirstResult(offset).setMaxResults(pageSize).getResultList();
		return new EntryItem<>(count, zones);
	}

	private void processQueriesCombination(QueriesCombinationType combinationType, List<Predicate> predicates, CriteriaBuilder cb, CriteriaQuery<Zone> cq, CriteriaQuery<Long> countCQ) {

		if (combinationType== QueriesCombinationType.all_and) {
			cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
			countCQ.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		} else {
			cq.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
			countCQ.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
		}
	}


	public Zone findById(Long id) {
		return zoneRepository.findById(id).orElse(null);
	}
}
