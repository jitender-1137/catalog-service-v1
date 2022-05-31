package com.yaari.ms.catalogservice.services;

import com.yaari.ms.catalogservice.dao.CategoryDaoExtended;
import com.yaari.ms.catalogservice.data.co.ApproveRejectCategoryCO;
import com.yaari.ms.catalogservice.data.co.CategoryCO;
import com.yaari.ms.catalogservice.data.co.CategoryLevel;
import com.yaari.ms.catalogservice.data.co.UpdateCategoryCO;
import com.yaari.ms.catalogservice.data.dto.CategoryResponse;
import com.yaari.ms.catalogservice.domains.Category;
import com.yaari.ms.catalogservice.dto.*;
import com.yaari.ms.catalogservice.enums.ActivityStatus;
import com.yaari.ms.catalogservice.enums.CategoryStatus;
import com.yaari.ms.catalogservice.enums.ComparativeRelation;
import com.yaari.ms.catalogservice.exceptions.ServiceException;
import com.yaari.ms.catalogservice.utility.ApplicationConstants;
import com.yaari.ms.catalogservice.utility.CommonUtil;
import com.yaari.ms.catalogservice.validation.category.CategoryValidator;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.validation.Valid;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

import static com.yaari.ms.catalogservice.utility.CommonUtil.getGenericSearchFilterWithAllEqualsComparison;
import static com.yaari.ms.catalogservice.utility.CommonUtil.getGenericSearchFilterWithDifferentComparison;

@Slf4j
@Service
public class CategoryServiceImpl implements CatergoryService {

	private final CategoryDaoExtended categoryDao;
	private final ModelMapper modelMapper;
	private final MessageSource messageSource;
	private final CacheManager cacheManager;

	public CategoryServiceImpl(CategoryDaoExtended categoryDao, ModelMapper modelMapper, MessageSource messageSource, CacheManager cacheManager) {
		this.categoryDao = categoryDao;
		this.modelMapper = modelMapper;
		this.messageSource = messageSource;
		this.cacheManager = cacheManager;
	}

	@Override
	@Cacheable(value = "categories", key = "#genericSearchFilter.toString() + #pageNumber + #pageSize")
	public EntryItem<CategoryDto> getCategories(GenericSearchFilter genericSearchFilter,
												Integer pageNumber, Integer pageSize) {
		Map<String, ComparativeRelationAndValue> searchParams = genericSearchFilter.getSearchParams();
		log.info("Fetching Categories as per following queries : {}", searchParams);
		List<CategoryDto> categoryDtoList = new LinkedList<>();
		long start = System.currentTimeMillis();
		EntryItem<Category> categoriesEntryItem =
				categoryDao.findByCriteriaFields(genericSearchFilter, pageNumber, pageSize);
		log.info("Time taken to get categories from catalogservice /  cache= {} ms", (System.currentTimeMillis() - start));
		if (!CollectionUtils.isEmpty(categoriesEntryItem.getItems())) {
			categoriesEntryItem.getItems().forEach(category -> {
				CategoryDto categoryDto = modelMapper.map(category, CategoryDto.class);
				fillL1L2Parent(categoryDto);
				categoryDtoList.add(categoryDto);
			});
		}
		return new EntryItem<>(categoriesEntryItem.getTotalItemsCount(), categoryDtoList);
	}

	public void fillL1L2Parent(CategoryDto categoryDto) {
		long start = System.currentTimeMillis();

		CategoryMiniDto immediateParentDetail = categoryDao.getParentMiniDto(categoryDto.getId());
		if (Objects.nonNull(immediateParentDetail)) {
			CategoryMiniDto parentOfParent = categoryDao.getParentMiniDto(immediateParentDetail.getId());
			if (Objects.nonNull(parentOfParent)) {
				categoryDto.setL1Parent(parentOfParent);
				categoryDto.setL2Parent(immediateParentDetail);
			} else {
				categoryDto.setL1Parent(immediateParentDetail);
			}
		}
		log.info("time taken in filling parent details for category id {} = {} ms", categoryDto.getId(), (System.currentTimeMillis() - start));
	}

	@Override
	public Map<String, Map> approveRejectCategories(List<ApproveRejectCategoryCO> categories) {
		List<Long> idsNotInPassedObjects = categories.stream().map(ApproveRejectCategoryCO::getId).filter(Objects::isNull).collect(Collectors.toList());
		if (!CollectionUtils.isEmpty(idsNotInPassedObjects)) {
			log.error("Category ids not passed in objects for creation: {}", idsNotInPassedObjects);
			throw new ServiceException("CS_14");
		}
		Map<String, Map> updateResult = new HashMap<>();
		categories.forEach(category -> {
			updateCategoryAndResult(category, updateResult);
		});
		removeAllCategorySearchCacheByCacheManager();
		return updateResult;
	}

	@Override
	public CategoryDto updateCategory(Long categoryId, @Valid UpdateCategoryCO passedCategory) {
		Category existingCategory = categoryDao.findByIdUncached(categoryId);
		CategoryValidator.validate(passedCategory, existingCategory);
		Category category = CommonUtil.convert(passedCategory, Category.class);
		existingCategory.setCheckerStatus(CategoryStatus.PENDING.name());
		existingCategory.setUpdatedByName(getNameFromUsername(passedCategory.getUpdatedByUsername()));
		existingCategory.setDraft(CommonUtil.convertObjectToString(category,CategoryResponse.class));
		existingCategory = categoryDao.save(existingCategory);
		categoryDao.removeCategoryCache(existingCategory);
		removeAllCategorySearchCacheByCacheManager();
		return CommonUtil.convert(existingCategory, CategoryResponse.class);
	}

	private void approveReject(ApproveRejectCategoryCO passedCategoryObject, Category existingCategory) {
		existingCategory.setReviewedAt(System.currentTimeMillis());
		existingCategory.setReviewedBy(passedCategoryObject.getReviewedBy());
		if (CategoryStatus.DRAFT.name().equals(existingCategory.getCheckerStatus())){
			handleApproveRejectForDraftCategory(passedCategoryObject, existingCategory);
		}else {
			handleApproveRejectForPendingCategory(passedCategoryObject, existingCategory);
		}
		categoryDao.save(existingCategory);
		categoryDao.removeCategoryCache(existingCategory);
		removeAllCategorySearchCacheByCacheManager();
	}

	private void handleApproveRejectForPendingCategory(ApproveRejectCategoryCO passedCategoryObject, Category existingCategory) {
		if (CategoryStatus.APPROVED.name().equals(passedCategoryObject.getCheckerStatus())) {
			existingCategory.setCheckerStatus(passedCategoryObject.getCheckerStatus());
			existingCategory.setRejectReason(null);
			String draft = existingCategory.getDraft();
			Category category = CommonUtil.convertToObject(draft, Category.class);
			fillNonNullValues(category, existingCategory, Category.class);
		}
		if (CategoryStatus.REJECTED.name().equals(passedCategoryObject.getCheckerStatus())) {
			existingCategory.setCheckerStatus(CategoryStatus.APPROVED.name());
			String draft = existingCategory.getDraft();
			CategoryResponse categoryResponse = CommonUtil.convertToObject(draft,CategoryResponse.class);
			categoryResponse.setRejectReason(passedCategoryObject.getRejectReason());
			categoryResponse.setCheckerStatus(CategoryStatus.REJECTED.name());
			existingCategory.setDraft(CommonUtil.convertObjectToString(categoryResponse,CategoryResponse.class));
		}
	}

	private void handleApproveRejectForDraftCategory(ApproveRejectCategoryCO passedCategoryObject, Category existingCategory) {
		if (CategoryStatus.REJECTED.name().equals(passedCategoryObject.getCheckerStatus())) {
			existingCategory.setStatus(ActivityStatus.INACTIVE.name());
			existingCategory.setRejectReason(passedCategoryObject.getRejectReason());
			existingCategory.setCheckerStatus(CategoryStatus.REJECTED.name());
			existingCategory.setParseName(getInactiveNameAfterReject(existingCategory.getParseName()));
		} else {
			existingCategory.setCheckerStatus(CategoryStatus.APPROVED.name());
		}
	}

	private String getInactiveNameAfterReject(String name) {
		return name + "_" + System.currentTimeMillis();
	}

	public void removeAllCategorySearchCacheByCacheManager() {
		cacheManager.getCache("categories").clear();
	}

	private void updateCategoryAndResult(ApproveRejectCategoryCO passedCategoryObject, Map<String, Map> updateResult) {
		Category existingCategory = categoryDao.findByIdUncached(passedCategoryObject.getId());
		boolean validationResult = validateUpdateList(passedCategoryObject, existingCategory, updateResult);
		if (validationResult) {
			Category category = CommonUtil.convert(passedCategoryObject, Category.class);
			try {
				approveReject(passedCategoryObject, existingCategory);
				updateResult.put(existingCategory.getName(), populateDetailsMap(ApplicationConstants.UPDATE_SUCCESS_STR, "Success"));
			} catch (Exception e) {
				log.error("Error in updating : ", e);
				updateResult.put(existingCategory.getName(), populateDetailsMap(messageSource.getMessage(String.valueOf(e), null, null), "Error"));
			}
		}
	}

	private boolean validateUpdateList(ApproveRejectCategoryCO passedCategoryObject, Category existingCategoryObject, Map<String, Map> updateResult) {
		boolean validationResult = false;
		try {
			CategoryValidator.validateUpdateAll(passedCategoryObject, existingCategoryObject);
			validationResult = true;
		} catch (ServiceException e) {
			log.error("Error in validation: {}, {}", passedCategoryObject.getId(), existingCategoryObject);
			updateResult.put(passedCategoryObject.getId().toString(), populateDetailsMap(messageSource.getMessage(e.getErrorCode(), null, null), "Error"));
		}
		return validationResult;
	}

	private Map<String, String> populateDetailsMap(String message, String status) {
		Map<String, String> map = new HashMap<>();
		map.put("message", message);
		map.put("status", status);
		return map;
	}

	public <T> void fillNonNullValues(T passedObject, T existingObject, Class<T> objectClass) {
		Field[] classFields = objectClass.getDeclaredFields();
		for (Field classField : classFields) {
			classField.setAccessible(true);
			try {
				Object passedObjectFieldValue = classField.get(passedObject);
				if (passedObjectFieldValue != null) {
					if (areValuesZeroNegativeNumbers(passedObjectFieldValue)) {
						continue;
					}
					log.debug("{} is passed as {} in update object. hence updating catalogservice object with this value ", classField.getName(), passedObjectFieldValue);
					classField.set(existingObject, passedObjectFieldValue);
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		log.info("Updated with passed values");
	}

	private boolean areValuesZeroNegativeNumbers(Object passedObjectFieldValue) {
		boolean longCondition = passedObjectFieldValue instanceof Long && (long) passedObjectFieldValue <= 0;
		boolean intCondition = passedObjectFieldValue instanceof Integer && (int) passedObjectFieldValue <= 0;
		boolean doubleCondition = passedObjectFieldValue instanceof Double && (double) passedObjectFieldValue <= 0;
		return longCondition || intCondition || doubleCondition;
	}

	@Override
	public Category findById(Long categoryId) {
		return categoryDao.findById(categoryId);
	}

	@Override
	public String doDeactivation(Long categoryId, String username) {
		validateUpdaterName(username);
		Category category = categoryDao.findById(categoryId);
		if (category.getStatus().equals(ActivityStatus.INACTIVE.name())) {
			throw new ServiceException("CS_13");
		}
		category.setStatus(ActivityStatus.INACTIVE.name());
		category.setUpdatedByName(username);
		category.setUpdatedByUsername(username);
		categoryDao.save(category);
		categoryDao.removeCategoryCache(category);
		return ApplicationConstants.DEACTIVATION_SUCCESS_STR;
	}

	private void validateUpdaterName(String username) {
		if (!StringUtils.hasLength(username))
			throw new ServiceException("CS_12");
	}

	private void validateParentId(CategoryLevel requestLevel, Long requestParentId) {
		if (requestLevel.equals(CategoryLevel.L1) && requestParentId != null) {
			log.error("L1 category can not have parent category");
			throw new ServiceException("CS_23");
		}
		if (requestLevel.equals(CategoryLevel.L2) || requestLevel.equals(CategoryLevel.L3)) {
			Category parentCategory = categoryDao.findByIdUncached(requestParentId);
			if (parentCategory == null) {
				log.error("parent category does not exist");
				throw new ServiceException("CS_27");
			}
			if (CategoryStatus.DRAFT.name().equals(parentCategory.getCheckerStatus())) {
				log.error("Can not attach as Parent category id, this parent category is not approved yet");
				throw new ServiceException("CS_38");
			}
			if (ActivityStatus.INACTIVE.name().equals(parentCategory.getStatus())) {
				log.error("This parent category is not active, can not attach it");
				throw new ServiceException("CS_39");
			}
			if (CategoryLevel.L2.equals(requestLevel) && !CategoryLevel.l1Name().equals(parentCategory.getLevel())) {
				log.error("L2 category can not have parent other than L1 category");
				throw new ServiceException("CS_24");
			}
			if (CategoryLevel.L3.equals(requestLevel) && !CategoryLevel.l2Name().equals(parentCategory.getLevel())) {
				log.error("L3 category can not have parent other than L2 category");
				throw new ServiceException("CS_25");
			}
		}
	}

	@Override
	public CategoryDto addCategory(@Valid CategoryCO categoryCO) {
		validateParentId(categoryCO.getLevel(), categoryCO.getParentCategoryId());
		Category category = CommonUtil.convert(categoryCO, Category.class);
		category.setParseName(CommonUtil.removeWhiteSpace(categoryCO.getName()));
		validateCategoryName(category.getParseName(), category.getLevel(), category.getParentCategoryId());
		category.setCreatedByName(getNameFromUsername(categoryCO.getCreatedByUsername()));
		removeAllCategorySearchCacheByCacheManager();
		category = categoryDao.save(category);
		return CommonUtil.convert(category, CategoryResponse.class);
	}

	private String getNameFromUsername(String createdByUsername) {
		return createdByUsername;
	}

	/**
	 *	Category should not be duplicated within its parent.
	 * 	If no parent, then it should not be duplicate at that level [l1].
	 *
	 * @param categoryParseName
	 * @param level
	 * @param parentCategoryId
	 */
	private void validateCategoryName(String categoryParseName, String level, Long parentCategoryId) {
		if (!StringUtils.hasLength(categoryParseName)) return;

		Map<String, Object> criteriaMap = new HashMap<>();
		criteriaMap.put("parseName", categoryParseName);	// Applicable for below both cases.

		Map<String, ComparativeRelationAndValue> criteriaMapWithRelation = new HashMap<>();
		criteriaMapWithRelation.put("parseName", createComparativeRelationAndValue(categoryParseName, ComparativeRelation.eq));

		if(parentCategoryId != null) {
			criteriaMapWithRelation.put("level", createComparativeRelationAndValue(level, ComparativeRelation.ne));
			if (categoryExistsWithDifferentCriteria(criteriaMapWithRelation)) {
				log.error(("Category exists with similar name"));
				throw new ServiceException("CS_20");
			}

			criteriaMap.put("parentCategoryId", parentCategoryId);
			if (categoryExistsWithSameCriteria(criteriaMap)) {
				log.error(("Category exists with similar name in given parent category"));
				throw new ServiceException("CS_40");
			}
		} else if(CategoryLevel.L1.name().equals(level)) {
			if(categoryExistsWithSameCriteria(criteriaMap)) {
				log.error(("Category exists with similar name"));
				throw new ServiceException("CS_20");
			}
		}
	}

	private boolean categoryExistsWithSameCriteria(Map<String, Object> criteriaMap) {
		if(criteriaMap == null || criteriaMap.isEmpty()) {
			log.error("No criteria passed to search.");
			return false;
		}
		GenericSearchFilter genericSearchFilter = getGenericSearchFilterWithAllEqualsComparison(criteriaMap);
		log.debug("genericSearchFilter: {}, based on criteriaMap: {}", genericSearchFilter, criteriaMap);
		EntryItem<Category> categoriesEntryItem = categoryDao.findByCriteriaFields(genericSearchFilter, 0, Integer.MAX_VALUE);
		return categoriesEntryItem.getItems().size() > 0;
	}

	private boolean categoryExistsWithDifferentCriteria(Map<String, ComparativeRelationAndValue> criteriaMap) {
		if(criteriaMap == null || criteriaMap.isEmpty()) {
			log.error("No criteria passed to search.");
			return false;
		}
		GenericSearchFilter genericSearchFilter = getGenericSearchFilterWithDifferentComparison(criteriaMap);
		log.debug("genericSearchFilter: {}, based on criteriaMap: {}", genericSearchFilter, criteriaMap);
		EntryItem<Category> categoriesEntryItem = categoryDao.findByCriteriaFields(genericSearchFilter, 0, Integer.MAX_VALUE);

		return categoriesEntryItem.getItems().size() > 0;
	}

	private ComparativeRelationAndValue createComparativeRelationAndValue(String categoryParseName, ComparativeRelation comparativeRelation){
		ComparativeRelationAndValue comparativeRelationAndValue = new ComparativeRelationAndValue();
		comparativeRelationAndValue.setValue(categoryParseName);
		comparativeRelationAndValue.setComparativeRelation(comparativeRelation);

		return comparativeRelationAndValue;
	}
}
