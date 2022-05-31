package com.yaari.ms.catalogservice.dao;

import com.yaari.ms.catalogservice.domains.AlembicVersion;
import com.yaari.ms.catalogservice.dto.ComparativeRelationAndValue;
import com.yaari.ms.catalogservice.dto.EntryItem;
import com.yaari.ms.catalogservice.dto.GenericSearchFilter;
import com.yaari.ms.catalogservice.enums.QueriesCombinationType;
import com.yaari.ms.catalogservice.jparepositories.AlembicVersionRepository;
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
public class AlembicVersionDao {
	@Autowired
	AlembicVersionRepository alembicVersionRepository;
	@PersistenceContext
	EntityManager entityManager;

	public AlembicVersion save(AlembicVersion alembicVersion) {
		return alembicVersionRepository.save(alembicVersion);
	}

	public List<AlembicVersion> save(List<AlembicVersion> alembicVersions) {
		return alembicVersionRepository.saveAll(alembicVersions);
	}

	public EntryItem<AlembicVersion> findByCriteriaFields(GenericSearchFilter genericSearchFilter,
																Integer pageNumber, Integer pageSize) {
		List<AlembicVersion> alembicVersions;
		Map<String, ComparativeRelationAndValue> searchParams = genericSearchFilter.getSearchParams();
		log.info("Finding by criteria fields in dao as passed");
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<AlembicVersion> cq = cb.createQuery(AlembicVersion.class);
		Root<AlembicVersion> alembicVersionRoot = cq.from(AlembicVersion.class);
		CriteriaQuery<Long> countCQ = cb.createQuery(Long.class);
		countCQ.select(cb.count(countCQ.from(AlembicVersion.class)));
		List<Predicate> predicates = new LinkedList<>();
		for (Map.Entry<String, ComparativeRelationAndValue> entry : searchParams.entrySet()) {
			UtilityMethods.fillCriteriaQuery(cb, entry, alembicVersionRoot, predicates);
		}
		processQueriesCombination(genericSearchFilter.getCombinationType(), predicates, cb, cq, countCQ);
		List<Order> orderBys = UtilityMethods.getOrderBys(cb, alembicVersionRoot, genericSearchFilter.getOrderBy());
		cq.orderBy(orderBys);
		TypedQuery<AlembicVersion> query = entityManager.createQuery(cq);
		Long count = entityManager.createQuery(countCQ).getSingleResult();
		if (pageNumber == null) pageNumber = 0;
		if (pageSize == null || pageSize == 0) pageSize = 10;
		int offset = pageNumber * pageSize;
		alembicVersions = query.setFirstResult(offset).setMaxResults(pageSize).getResultList();
		return new EntryItem<>(count, alembicVersions);
	}

	private void processQueriesCombination(QueriesCombinationType combinationType, List<Predicate> predicates, CriteriaBuilder cb, CriteriaQuery<AlembicVersion> cq, CriteriaQuery<Long> countCQ) {

		if (combinationType== QueriesCombinationType.all_and) {
			cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
			countCQ.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		} else {
			cq.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
			countCQ.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
		}
	}


	public AlembicVersion findById(Long id) {
		return alembicVersionRepository.findById(id).orElse(null);
	}
}
