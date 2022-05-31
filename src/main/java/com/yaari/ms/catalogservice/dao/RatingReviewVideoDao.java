package com.yaari.ms.catalogservice.dao;

import com.yaari.ms.catalogservice.domains.RatingReviewVideo;
import com.yaari.ms.catalogservice.dto.ComparativeRelationAndValue;
import com.yaari.ms.catalogservice.dto.EntryItem;
import com.yaari.ms.catalogservice.dto.GenericSearchFilter;
import com.yaari.ms.catalogservice.enums.QueriesCombinationType;
import com.yaari.ms.catalogservice.jparepositories.RatingReviewVideoRepository;
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
public class RatingReviewVideoDao {
	@Autowired
	RatingReviewVideoRepository ratingReviewVideoRepository;
	@PersistenceContext
	EntityManager entityManager;

	public RatingReviewVideo save(RatingReviewVideo ratingReviewVideo) {
		return ratingReviewVideoRepository.save(ratingReviewVideo);
	}

	public List<RatingReviewVideo> save(List<RatingReviewVideo> ratingReviewVideos) {
		return ratingReviewVideoRepository.saveAll(ratingReviewVideos);
	}

	public EntryItem<RatingReviewVideo> findByCriteriaFields(GenericSearchFilter genericSearchFilter,
																Integer pageNumber, Integer pageSize) {
		List<RatingReviewVideo> ratingReviewVideos;
		Map<String, ComparativeRelationAndValue> searchParams = genericSearchFilter.getSearchParams();
		log.info("Finding by criteria fields in dao as passed");
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<RatingReviewVideo> cq = cb.createQuery(RatingReviewVideo.class);
		Root<RatingReviewVideo> ratingReviewVideoRoot = cq.from(RatingReviewVideo.class);
		CriteriaQuery<Long> countCQ = cb.createQuery(Long.class);
		countCQ.select(cb.count(countCQ.from(RatingReviewVideo.class)));
		List<Predicate> predicates = new LinkedList<>();
		for (Map.Entry<String, ComparativeRelationAndValue> entry : searchParams.entrySet()) {
			UtilityMethods.fillCriteriaQuery(cb, entry, ratingReviewVideoRoot, predicates);
		}
		processQueriesCombination(genericSearchFilter.getCombinationType(), predicates, cb, cq, countCQ);
		List<Order> orderBys = UtilityMethods.getOrderBys(cb, ratingReviewVideoRoot, genericSearchFilter.getOrderBy());
		cq.orderBy(orderBys);
		TypedQuery<RatingReviewVideo> query = entityManager.createQuery(cq);
		Long count = entityManager.createQuery(countCQ).getSingleResult();
		if (pageNumber == null) pageNumber = 0;
		if (pageSize == null || pageSize == 0) pageSize = 10;
		int offset = pageNumber * pageSize;
		ratingReviewVideos = query.setFirstResult(offset).setMaxResults(pageSize).getResultList();
		return new EntryItem<>(count, ratingReviewVideos);
	}

	private void processQueriesCombination(QueriesCombinationType combinationType, List<Predicate> predicates, CriteriaBuilder cb, CriteriaQuery<RatingReviewVideo> cq, CriteriaQuery<Long> countCQ) {

		if (combinationType== QueriesCombinationType.all_and) {
			cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
			countCQ.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		} else {
			cq.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
			countCQ.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
		}
	}


	public RatingReviewVideo findById(Long id) {
		return ratingReviewVideoRepository.findById(id).orElse(null);
	}
}
