package com.yaari.ms.catalogservice.dao;

import com.yaari.ms.catalogservice.domains.RatingReviewImage;
import com.yaari.ms.catalogservice.dto.ComparativeRelationAndValue;
import com.yaari.ms.catalogservice.dto.EntryItem;
import com.yaari.ms.catalogservice.dto.GenericSearchFilter;
import com.yaari.ms.catalogservice.enums.QueriesCombinationType;
import com.yaari.ms.catalogservice.jparepositories.RatingReviewImageRepository;
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
public class RatingReviewImageDao {
	@Autowired
	RatingReviewImageRepository ratingReviewImageRepository;
	@PersistenceContext
	EntityManager entityManager;

	public RatingReviewImage save(RatingReviewImage ratingReviewImage) {
		return ratingReviewImageRepository.save(ratingReviewImage);
	}

	public List<RatingReviewImage> save(List<RatingReviewImage> ratingReviewImages) {
		return ratingReviewImageRepository.saveAll(ratingReviewImages);
	}

	public EntryItem<RatingReviewImage> findByCriteriaFields(GenericSearchFilter genericSearchFilter,
																Integer pageNumber, Integer pageSize) {
		List<RatingReviewImage> ratingReviewImages;
		Map<String, ComparativeRelationAndValue> searchParams = genericSearchFilter.getSearchParams();
		log.info("Finding by criteria fields in dao as passed");
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<RatingReviewImage> cq = cb.createQuery(RatingReviewImage.class);
		Root<RatingReviewImage> ratingReviewImageRoot = cq.from(RatingReviewImage.class);
		CriteriaQuery<Long> countCQ = cb.createQuery(Long.class);
		countCQ.select(cb.count(countCQ.from(RatingReviewImage.class)));
		List<Predicate> predicates = new LinkedList<>();
		for (Map.Entry<String, ComparativeRelationAndValue> entry : searchParams.entrySet()) {
			UtilityMethods.fillCriteriaQuery(cb, entry, ratingReviewImageRoot, predicates);
		}
		processQueriesCombination(genericSearchFilter.getCombinationType(), predicates, cb, cq, countCQ);
		List<Order> orderBys = UtilityMethods.getOrderBys(cb, ratingReviewImageRoot, genericSearchFilter.getOrderBy());
		cq.orderBy(orderBys);
		TypedQuery<RatingReviewImage> query = entityManager.createQuery(cq);
		Long count = entityManager.createQuery(countCQ).getSingleResult();
		if (pageNumber == null) pageNumber = 0;
		if (pageSize == null || pageSize == 0) pageSize = 10;
		int offset = pageNumber * pageSize;
		ratingReviewImages = query.setFirstResult(offset).setMaxResults(pageSize).getResultList();
		return new EntryItem<>(count, ratingReviewImages);
	}

	private void processQueriesCombination(QueriesCombinationType combinationType, List<Predicate> predicates, CriteriaBuilder cb, CriteriaQuery<RatingReviewImage> cq, CriteriaQuery<Long> countCQ) {

		if (combinationType== QueriesCombinationType.all_and) {
			cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
			countCQ.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		} else {
			cq.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
			countCQ.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
		}
	}


	public RatingReviewImage findById(Long id) {
		return ratingReviewImageRepository.findById(id).orElse(null);
	}
}
