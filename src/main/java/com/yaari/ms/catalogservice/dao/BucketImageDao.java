package com.yaari.ms.catalogservice.dao;

import com.yaari.ms.catalogservice.domains.BucketImage;
import com.yaari.ms.catalogservice.dto.ComparativeRelationAndValue;
import com.yaari.ms.catalogservice.dto.EntryItem;
import com.yaari.ms.catalogservice.dto.GenericSearchFilter;
import com.yaari.ms.catalogservice.enums.QueriesCombinationType;
import com.yaari.ms.catalogservice.jparepositories.BucketImageRepository;
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
public class BucketImageDao {
	@Autowired
	BucketImageRepository bucketImageRepository;
	@PersistenceContext
	EntityManager entityManager;

	public BucketImage save(BucketImage bucketImage) {
		return bucketImageRepository.save(bucketImage);
	}

	public List<BucketImage> save(List<BucketImage> bucketImages) {
		return bucketImageRepository.saveAll(bucketImages);
	}

	public EntryItem<BucketImage> findByCriteriaFields(GenericSearchFilter genericSearchFilter,
																Integer pageNumber, Integer pageSize) {
		List<BucketImage> bucketImages;
		Map<String, ComparativeRelationAndValue> searchParams = genericSearchFilter.getSearchParams();
		log.info("Finding by criteria fields in dao as passed");
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<BucketImage> cq = cb.createQuery(BucketImage.class);
		Root<BucketImage> bucketImageRoot = cq.from(BucketImage.class);
		CriteriaQuery<Long> countCQ = cb.createQuery(Long.class);
		countCQ.select(cb.count(countCQ.from(BucketImage.class)));
		List<Predicate> predicates = new LinkedList<>();
		for (Map.Entry<String, ComparativeRelationAndValue> entry : searchParams.entrySet()) {
			UtilityMethods.fillCriteriaQuery(cb, entry, bucketImageRoot, predicates);
		}
		processQueriesCombination(genericSearchFilter.getCombinationType(), predicates, cb, cq, countCQ);
		List<Order> orderBys = UtilityMethods.getOrderBys(cb, bucketImageRoot, genericSearchFilter.getOrderBy());
		cq.orderBy(orderBys);
		TypedQuery<BucketImage> query = entityManager.createQuery(cq);
		Long count = entityManager.createQuery(countCQ).getSingleResult();
		if (pageNumber == null) pageNumber = 0;
		if (pageSize == null || pageSize == 0) pageSize = 10;
		int offset = pageNumber * pageSize;
		bucketImages = query.setFirstResult(offset).setMaxResults(pageSize).getResultList();
		return new EntryItem<>(count, bucketImages);
	}

	private void processQueriesCombination(QueriesCombinationType combinationType, List<Predicate> predicates, CriteriaBuilder cb, CriteriaQuery<BucketImage> cq, CriteriaQuery<Long> countCQ) {

		if (combinationType== QueriesCombinationType.all_and) {
			cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
			countCQ.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		} else {
			cq.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
			countCQ.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
		}
	}


	public BucketImage findById(Long id) {
		return bucketImageRepository.findById(id).orElse(null);
	}
}
