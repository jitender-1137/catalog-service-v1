package com.yaari.ms.catalogservice.services;

import com.yaari.ms.catalogservice.data.co.ApproveRejectCategoryCO;
import com.yaari.ms.catalogservice.data.co.CategoryCO;
import com.yaari.ms.catalogservice.data.co.UpdateCategoryCO;
import com.yaari.ms.catalogservice.domains.Category;
import com.yaari.ms.catalogservice.dto.CategoryDto;
import com.yaari.ms.catalogservice.dto.EntryItem;
import com.yaari.ms.catalogservice.dto.GenericSearchFilter;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;


public interface CatergoryService {
	EntryItem<CategoryDto> getCategories(GenericSearchFilter genericSearchFilter,
										 Integer pageNumber, Integer pageSize);

	Map<String, Map> approveRejectCategories(List<ApproveRejectCategoryCO> categories);

	Category findById(Long tableId);

	String doDeactivation(Long categoryId, String username);

	CategoryDto addCategory(@Valid CategoryCO categoriesCO);

	CategoryDto updateCategory(Long categoryId, UpdateCategoryCO categories);
}
