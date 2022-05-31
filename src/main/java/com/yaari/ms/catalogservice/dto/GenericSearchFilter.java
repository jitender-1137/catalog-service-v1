package com.yaari.ms.catalogservice.dto;

import com.yaari.ms.catalogservice.enums.OrderByType;
import com.yaari.ms.catalogservice.enums.QueriesCombinationType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@Setter
@ToString
public class GenericSearchFilter {
	private QueriesCombinationType combinationType = QueriesCombinationType.all_and;
	private Map<String, ComparativeRelationAndValue> searchParams;
	private Map<String, OrderByType> orderBy;
}
