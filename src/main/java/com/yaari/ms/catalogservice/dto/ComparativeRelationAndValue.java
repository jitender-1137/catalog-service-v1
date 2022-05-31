package com.yaari.ms.catalogservice.dto;

import com.yaari.ms.catalogservice.enums.ComparativeRelation;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ComparativeRelationAndValue {
	private Object value;
	private ComparativeRelation comparativeRelation;
}
