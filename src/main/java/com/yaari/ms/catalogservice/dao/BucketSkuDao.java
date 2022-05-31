package com.yaari.ms.catalogservice.dao;

import com.yaari.ms.catalogservice.domains.BucketSku;
import com.yaari.ms.catalogservice.dto.ComparativeRelationAndValue;
import com.yaari.ms.catalogservice.dto.EntryItem;
import com.yaari.ms.catalogservice.dto.GenericSearchFilter;
import com.yaari.ms.catalogservice.enums.QueriesCombinationType;
import com.yaari.ms.catalogservice.jparepositories.BucketSkuRepository;
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
public class BucketSkuDao {
	@Autowired
	BucketSkuRepository bucketSkuRepository;
	@PersistenceContext
	EntityManager entityManager;

	public BucketSku save(BucketSku bucketSku) {
		return bucketSkuRepository.save(bucketSku);
	}

	public List<BucketSku> save(List<BucketSku> bucketSkus) {
		return bucketSkuRepository.saveAll(bucketSkus);
	}

	public EntryItem<BucketSku> findByCriteriaFields(GenericSearchFilter genericSearchFilter,
													 Integer pageNumber, Integer pageSize) {
		List<BucketSku> bucketSkus;
		Map<String, ComparativeRelationAndValue> searchParams = genericSearchFilter.getSearchParams();
		log.info("Finding by criteria fields in dao as passed");
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<BucketSku> cq = cb.createQuery(BucketSku.class);
		Root<BucketSku> bucketSkuRoot = cq.from(BucketSku.class);
		CriteriaQuery<Long> countCQ = cb.createQuery(Long.class);
		countCQ.select(cb.count(countCQ.from(BucketSku.class)));
		List<Predicate> predicates = new LinkedList<>();
		for (Map.Entry<String, ComparativeRelationAndValue> entry : searchParams.entrySet()) {
			UtilityMethods.fillCriteriaQuery(cb, entry, bucketSkuRoot, predicates);
		}
		processQueriesCombination(genericSearchFilter.getCombinationType(), predicates, cb, cq, countCQ);
		List<Order> orderBys = UtilityMethods.getOrderBys(cb, bucketSkuRoot, genericSearchFilter.getOrderBy());
		cq.orderBy(orderBys);
		TypedQuery<BucketSku> query = entityManager.createQuery(cq);
		Long count = entityManager.createQuery(countCQ).getSingleResult();
		if (pageNumber == null) pageNumber = 0;
		if (pageSize == null || pageSize == 0) pageSize = 10;
		int offset = pageNumber * pageSize;
		bucketSkus = query.setFirstResult(offset).setMaxResults(pageSize).getResultList();
		return new EntryItem<>(count, bucketSkus);
	}

	private void processQueriesCombination(QueriesCombinationType combinationType, List<Predicate> predicates, CriteriaBuilder cb, CriteriaQuery<BucketSku> cq, CriteriaQuery<Long> countCQ) {

		if (combinationType== QueriesCombinationType.all_and) {
			cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
			countCQ.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		} else {
			cq.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
			countCQ.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
		}
	}


	public BucketSku findById(Long id) {
		return bucketSkuRepository.findById(id).orElse(null);
	}
}
