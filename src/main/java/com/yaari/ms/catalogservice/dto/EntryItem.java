package com.yaari.ms.catalogservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class EntryItem<T> {
	List<T> items;
	Long totalItemsCount;

	public EntryItem(Long count, List<T> categorys) {
		this.items = categorys;
		this.totalItemsCount = count;
	}
}
