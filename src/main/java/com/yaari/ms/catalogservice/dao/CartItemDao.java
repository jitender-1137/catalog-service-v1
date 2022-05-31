package com.yaari.ms.catalogservice.dao;

import com.yaari.ms.catalogservice.domains.CartItem;
import com.yaari.ms.catalogservice.dto.ComparativeRelationAndValue;
import com.yaari.ms.catalogservice.dto.EntryItem;
import com.yaari.ms.catalogservice.dto.GenericSearchFilter;
import com.yaari.ms.catalogservice.enums.QueriesCombinationType;
import com.yaari.ms.catalogservice.jparepositories.CartItemRepository;
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
public class CartItemDao {
	@Autowired
	CartItemRepository cartItemRepository;
	@PersistenceContext
	EntityManager entityManager;

	public CartItem save(CartItem cartItem) {
		return cartItemRepository.save(cartItem);
	}

	public List<CartItem> save(List<CartItem> cartItems) {
		return cartItemRepository.saveAll(cartItems);
	}

	public EntryItem<CartItem> findByCriteriaFields(GenericSearchFilter genericSearchFilter,
																Integer pageNumber, Integer pageSize) {
		List<CartItem> cartItems;
		Map<String, ComparativeRelationAndValue> searchParams = genericSearchFilter.getSearchParams();
		log.info("Finding by criteria fields in dao as passed");
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<CartItem> cq = cb.createQuery(CartItem.class);
		Root<CartItem> cartItemRoot = cq.from(CartItem.class);
		CriteriaQuery<Long> countCQ = cb.createQuery(Long.class);
		countCQ.select(cb.count(countCQ.from(CartItem.class)));
		List<Predicate> predicates = new LinkedList<>();
		for (Map.Entry<String, ComparativeRelationAndValue> entry : searchParams.entrySet()) {
			UtilityMethods.fillCriteriaQuery(cb, entry, cartItemRoot, predicates);
		}
		processQueriesCombination(genericSearchFilter.getCombinationType(), predicates, cb, cq, countCQ);
		List<Order> orderBys = UtilityMethods.getOrderBys(cb, cartItemRoot, genericSearchFilter.getOrderBy());
		cq.orderBy(orderBys);
		TypedQuery<CartItem> query = entityManager.createQuery(cq);
		Long count = entityManager.createQuery(countCQ).getSingleResult();
		if (pageNumber == null) pageNumber = 0;
		if (pageSize == null || pageSize == 0) pageSize = 10;
		int offset = pageNumber * pageSize;
		cartItems = query.setFirstResult(offset).setMaxResults(pageSize).getResultList();
		return new EntryItem<>(count, cartItems);
	}

	private void processQueriesCombination(QueriesCombinationType combinationType, List<Predicate> predicates, CriteriaBuilder cb, CriteriaQuery<CartItem> cq, CriteriaQuery<Long> countCQ) {

		if (combinationType== QueriesCombinationType.all_and) {
			cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
			countCQ.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		} else {
			cq.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
			countCQ.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
		}
	}


	public CartItem findById(Long id) {
		return cartItemRepository.findById(id).orElse(null);
	}
}
