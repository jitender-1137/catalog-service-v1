package com.yaari.ms.catalogservice.controllers;

import com.yaari.ms.catalogservice.data.co.ApproveRejectCategoryCO;
import com.yaari.ms.catalogservice.data.co.CategoryCO;
import com.yaari.ms.catalogservice.data.co.UpdateCategoryCO;
import com.yaari.ms.catalogservice.services.CatergoryService;
import com.yaari.ms.catalogservice.dto.*;
import com.yaari.ms.catalogservice.exceptions.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@Validated
@RequestMapping("/v1/categories")
public class CategoryController {

	private final CatergoryService categoryService;

	public CategoryController(CatergoryService categoryService) {
		this.categoryService = categoryService;
	}

	@PostMapping("/search")
	@Secured("ROLE_VIEW_CATEGORY")
	public ResponseDto getCategorys(@RequestBody GenericSearchFilter genericSearchFilter,
												 @RequestParam(required = false) Integer pageNumber,
												 @RequestParam(required = false) Integer pageSize) {
		EntryItem<CategoryDto> categoryDtosEntryItem =
				categoryService.getCategories(genericSearchFilter, pageNumber, pageSize);
		return new SuccessResponseDto(categoryDtosEntryItem.getItems(), "Categories Successfully fetched", categoryDtosEntryItem.getTotalItemsCount());
	}

	@PostMapping
//	@Secured("ROLE_ADD_CATEGORY")
	public ResponseDto addCategory(@RequestBody @Valid CategoryCO categoryCO) {
		CategoryDto addCategoryResponse = categoryService.addCategory(categoryCO);
		return new SuccessResponseDto(addCategoryResponse, "Category(s) Successfully added");
	}

	@PutMapping
//	@Secured("ROLE_APPROVE_CATEGORY")
	public ResponseDto updateCategory(@RequestBody List<ApproveRejectCategoryCO> categories) {
		if (CollectionUtils.isEmpty(categories))
			throw new ServiceException("CS_02");
		Map<String, Map> updateResult = categoryService.approveRejectCategories(categories);
		return new SuccessResponseDto(updateResult, "Details of updates in response data");
	}

	@PutMapping("/{id}")
//	@Secured("ROLE_EDIT_CATEGORY")
	public ResponseDto update(@PathVariable("id") Long categoryId, @RequestBody @Valid UpdateCategoryCO categories) {
		CategoryDto updateCategory = categoryService.updateCategory(categoryId, categories);
		return new SuccessResponseDto(updateCategory, "Details of updates in response data");
	}

	@DeleteMapping("/{category-id}")
//	@Secured("ROLE_DELETE_CATEGORY")
	public ResponseDto softDeleteCategory(@PathVariable("category-id") Long categoryId, @RequestHeader("username") String username) {
		if (categoryId == null || categoryId == 0)
			throw new ServiceException("CS_03");
		return new SuccessResponseDto(categoryService.doDeactivation(categoryId, username), "Category Successfully Soft Deleted");
	}

}
