package com.yaari.ms.catalogservice.dao;

import com.yaari.ms.catalogservice.domains.ProductSkuImage;
import com.yaari.ms.catalogservice.dto.ComparativeRelationAndValue;
import com.yaari.ms.catalogservice.dto.EntryItem;
import com.yaari.ms.catalogservice.dto.GenericSearchFilter;
import com.yaari.ms.catalogservice.enums.QueriesCombinationType;
import com.yaari.ms.catalogservice.jparepositories.ProductSkuImageRepository;
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
public class ProductSkuImageDao {
	@Autowired
	ProductSkuImageRepository productSkuImageRepository;
	@PersistenceContext
	EntityManager entityManager;

	public ProductSkuImage save(ProductSkuImage productSkuImage) {
		return productSkuImageRepository.save(productSkuImage);
	}

	public List<ProductSkuImage> save(List<ProductSkuImage> productSkuImages) {
		return productSkuImageRepository.saveAll(productSkuImages);
	}

	public EntryItem<ProductSkuImage> findByCriteriaFields(GenericSearchFilter genericSearchFilter,
																Integer pageNumber, Integer pageSize) {
		List<ProductSkuImage> productSkuImages;
		Map<String, ComparativeRelationAndValue> searchParams = genericSearchFilter.getSearchParams();
		log.info("Finding by criteria fields in dao as passed");
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<ProductSkuImage> cq = cb.createQuery(ProductSkuImage.class);
		Root<ProductSkuImage> productSkuImageRoot = cq.from(ProductSkuImage.class);
		CriteriaQuery<Long> countCQ = cb.createQuery(Long.class);
		countCQ.select(cb.count(countCQ.from(ProductSkuImage.class)));
		List<Predicate> predicates = new LinkedList<>();
		for (Map.Entry<String, ComparativeRelationAndValue> entry : searchParams.entrySet()) {
			UtilityMethods.fillCriteriaQuery(cb, entry, productSkuImageRoot, predicates);
		}
		processQueriesCombination(genericSearchFilter.getCombinationType(), predicates, cb, cq, countCQ);
		List<Order> orderBys = UtilityMethods.getOrderBys(cb, productSkuImageRoot, genericSearchFilter.getOrderBy());
		cq.orderBy(orderBys);
		TypedQuery<ProductSkuImage> query = entityManager.createQuery(cq);
		Long count = entityManager.createQuery(countCQ).getSingleResult();
		if (pageNumber == null) pageNumber = 0;
		if (pageSize == null || pageSize == 0) pageSize = 10;
		int offset = pageNumber * pageSize;
		productSkuImages = query.setFirstResult(offset).setMaxResults(pageSize).getResultList();
		return new EntryItem<>(count, productSkuImages);
	}

	private void processQueriesCombination(QueriesCombinationType combinationType, List<Predicate> predicates, CriteriaBuilder cb, CriteriaQuery<ProductSkuImage> cq, CriteriaQuery<Long> countCQ) {

		if (combinationType== QueriesCombinationType.all_and) {
			cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
			countCQ.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		} else {
			cq.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
			countCQ.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
		}
	}


	public ProductSkuImage findById(Long id) {
		return productSkuImageRepository.findById(id).orElse(null);
	}
}
