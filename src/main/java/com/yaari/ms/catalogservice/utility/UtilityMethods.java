package com.yaari.ms.catalogservice.utility;


import com.yaari.ms.catalogservice.dto.ComparativeRelationAndValue;
import com.yaari.ms.catalogservice.enums.ComparativeRelation;
import com.yaari.ms.catalogservice.enums.OrderByType;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class UtilityMethods {


	public static <T> void fillCriteriaQuery(CriteriaBuilder cb, Map.Entry<String, ComparativeRelationAndValue> entry, Root<T> categoryRoot, List<Predicate> predicates) {
		ComparativeRelationAndValue comparativeRelationAndValue = entry.getValue();
		// AND Query
		if (comparativeRelationAndValue.getComparativeRelation() == ComparativeRelation.eq)
			if (comparativeRelationAndValue.getValue() == null) {
				predicates.add(cb.isNull(categoryRoot.get(entry.getKey())));
			} else {
				predicates.add(cb.equal(categoryRoot.get(entry.getKey()), comparativeRelationAndValue.getValue()));
			}
		if (comparativeRelationAndValue.getComparativeRelation() == ComparativeRelation.ne)
			predicates.add(cb.notEqual(categoryRoot.get(entry.getKey()), comparativeRelationAndValue.getValue()));
		if (comparativeRelationAndValue.getComparativeRelation() == ComparativeRelation.gt)
			predicates.add(cb.greaterThan(categoryRoot.get(entry.getKey()), (Integer) comparativeRelationAndValue.getValue()));
		if (comparativeRelationAndValue.getComparativeRelation() == ComparativeRelation.lt)
			predicates.add(cb.lessThan(categoryRoot.get(entry.getKey()), (Integer) comparativeRelationAndValue.getValue()));
		if (comparativeRelationAndValue.getComparativeRelation() == ComparativeRelation.gte)
			predicates.add(cb.greaterThanOrEqualTo(categoryRoot.get(entry.getKey()), (Integer) comparativeRelationAndValue.getValue()));
		if (comparativeRelationAndValue.getComparativeRelation() == ComparativeRelation.lte)
			predicates.add(cb.lessThanOrEqualTo(categoryRoot.get(entry.getKey()), (Integer) comparativeRelationAndValue.getValue()));
		if (comparativeRelationAndValue.getComparativeRelation() == ComparativeRelation.like)
			predicates.add(cb.like(categoryRoot.get(entry.getKey()), "%" + comparativeRelationAndValue.getValue() + "%"));
		if (comparativeRelationAndValue.getComparativeRelation() == ComparativeRelation.ilike)
			predicates.add(cb.like(cb.lower(categoryRoot.get(entry.getKey())), "%" + ((String) comparativeRelationAndValue.getValue()).toLowerCase() + "%"));

	}

	public static <T> List<Order> getOrderBys(CriteriaBuilder criteriaBuilder, Root<T> categoryRoot, Map<String, OrderByType> orderBy) {
		List<Order> orderBys = new LinkedList<>();
		if (CollectionUtils.isEmpty(orderBy))
			return orderBys;
		for (String field : orderBy.keySet()) {
			if (orderBy.get(field) == OrderByType.desc) {
				orderBys.add(criteriaBuilder.desc(categoryRoot.get(field)));
			}
			if (orderBy.get(field) == OrderByType.asc) {
				orderBys.add(criteriaBuilder.asc(categoryRoot.get(field)));
			}
		}
		return orderBys;
	}
}
