package com.yaari.ms.catalogservice.services;

import com.yaari.ms.catalogservice.dao.CategoryDaoExtended;
import com.yaari.ms.catalogservice.data.co.ApproveRejectCategoryCO;
import com.yaari.ms.catalogservice.data.co.CategoryCO;
import com.yaari.ms.catalogservice.data.co.CategoryLevel;
import com.yaari.ms.catalogservice.data.co.UpdateCategoryCO;
import com.yaari.ms.catalogservice.utility.CommonUtil;
import com.yaari.ms.catalogservice.validation.category.CategoryValidator;
import com.yaari.ms.catalogservice.domains.Category;
import com.yaari.ms.catalogservice.dto.CategoryDto;
import com.yaari.ms.catalogservice.dto.EntryItem;
import com.yaari.ms.catalogservice.enums.ActivityStatus;
import com.yaari.ms.catalogservice.enums.CategoryStatus;
import com.yaari.ms.catalogservice.exceptions.ServiceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.MessageSource;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceImplTest {
	@InjectMocks
	CategoryServiceImpl categoryService;

	private CategoryDaoExtended categoryDao = mock(CategoryDaoExtended.class, Mockito.RETURNS_DEEP_STUBS);
	@Mock
	private ModelMapper modelMapper;
	@Mock
	private MessageSource messageSource;
	private CacheManager cacheManager = mock(RedisCacheManager.class);
	private Cache cache = Mockito.mock(RedisCache.class);

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testSaveCategory() {
		CategoryCO categoryCO = new CategoryCO();
		categoryCO.setName("name");
		categoryCO.setHomepageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setDescription("Description");
		categoryCO.setCreatedByUsername("username");
		categoryCO.setLevel(CategoryLevel.L1.name());

		Category category = new Category();
		category.setName("name");

		EntryItem<Category> entryItem = new EntryItem<>(1L, new ArrayList<>());
		Mockito.when(categoryDao.findByCriteriaFields(any(), any(), any())).thenReturn(entryItem);
		Mockito.when(categoryDao.save(any(Category.class))).thenReturn(category);
		Mockito.when(cacheManager.getCache(Mockito.anyString())).thenReturn(cache);

		CategoryDto categoryDto = categoryService.addCategory(categoryCO);

		Mockito.verify(categoryDao, Mockito.times(1)).save(any(Category.class));
		Assertions.assertNotNull(categoryDto);
		Assertions.assertEquals(category.getName(), categoryDto.getName());
	}

	@Test
	public void testSaveL1CategoryWithParentId() {
		CategoryCO categoryCO = new CategoryCO();
		categoryCO.setName("name");
		categoryCO.setHomepageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setDescription("Description");
		categoryCO.setCreatedByUsername("username");
		categoryCO.setLevel(CategoryLevel.L1.name());
		categoryCO.setParentCategoryId(1l);

		Category category = new Category();
		category.setName("name");

		EntryItem<Category> entryItem = new EntryItem<>(1L, new ArrayList<>());
		Mockito.when(categoryDao.findByCriteriaFields(any(), any(), any())).thenReturn(entryItem);
		Mockito.when(categoryDao.save(any(Category.class))).thenReturn(category);
		Mockito.when(cacheManager.getCache(Mockito.anyString())).thenReturn(cache);
		ServiceException thrown = Assertions.assertThrows(ServiceException.class, () -> {
			categoryService.addCategory(categoryCO);
		});
		Assertions.assertEquals("CS_23", thrown.getErrorCode());
	}

	@Test
	public void testSaveL2CategoryWithoutParentId() {
		CategoryCO categoryCO = new CategoryCO();
		categoryCO.setName("name");
		categoryCO.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setDescription("Description");
		categoryCO.setCreatedByUsername("username");
		categoryCO.setLevel(CategoryLevel.L2.name());

		Category category = new Category();
		category.setName("name");

		EntryItem<Category> entryItem = new EntryItem<>(1L, new ArrayList<>());
		Mockito.when(categoryDao.findByCriteriaFields(any(), any(), any())).thenReturn(entryItem);

		Mockito.when(categoryDao.save(any(Category.class))).thenReturn(category);

		Mockito.when(cacheManager.getCache(Mockito.anyString())).thenReturn(cache);
		ServiceException thrown = Assertions.assertThrows(ServiceException.class, () -> {
			categoryService.addCategory(categoryCO);
		});
		Assertions.assertEquals("CS_24", thrown.getErrorCode());
	}

	@Test
	public void testSaveL3CategoryWithoutParentId() {
		CategoryCO categoryCO = new CategoryCO();
		categoryCO.setName("name");
		categoryCO.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setDescription("Description");
		categoryCO.setCreatedByUsername("username");
		categoryCO.setLevel(CategoryLevel.L3.name());
		categoryCO.setParentCategoryId(1l);

		Category category = new Category();
		category.setName("name");

		EntryItem<Category> entryItem = new EntryItem<>(1L, new ArrayList<>());
		Mockito.when(categoryDao.findByCriteriaFields(any(), any(), any())).thenReturn(entryItem);

		Mockito.when(categoryDao.save(any(Category.class))).thenReturn(category);

		Mockito.when(cacheManager.getCache(Mockito.anyString())).thenReturn(cache);
		ServiceException thrown = Assertions.assertThrows(ServiceException.class, () -> {
			categoryService.addCategory(categoryCO);
		});
		Assertions.assertEquals("CS_25", thrown.getErrorCode());
	}

	@Test
	public void testUpdateWhenStatusIsPending() {
		Long categoryId = 1l;
		UpdateCategoryCO categoryCO = new UpdateCategoryCO();
		categoryCO.setHomepageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setDescription("Description");
		categoryCO.setUpdatedByUsername("username");
		categoryCO.setStatus(ActivityStatus.ACTIVE.name());

		Category category = new Category();
		category.setLevel("L1");
		category.setName("name");
		category.setHomepageBannerImage("http://localhost:8081/v1/image.jpg");
		category.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		category.setStatus(ActivityStatus.ACTIVE.name());
		category.setCheckerStatus(CategoryStatus.PENDING.name());

		Mockito.when(categoryDao.findByIdUncached(categoryId)).thenReturn(category);
		Mockito.when(cacheManager.getCache(Mockito.anyString())).thenReturn(cache);

		ServiceException thrown = Assertions.assertThrows(ServiceException.class, () -> {
			categoryService.updateCategory(categoryId, categoryCO);
		});
		Assertions.assertEquals("CS_30", thrown.getErrorCode());
	}

	@Test
	public void testUpdateWhenInvalidHomePageBannerImage() {
		Long categoryId = 1l;
		UpdateCategoryCO categoryCO = new UpdateCategoryCO();
		categoryCO.setHomepageBannerImage("htt://localhost:8081/v1/image.jpg");
		categoryCO.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setDescription("Description");
		categoryCO.setUpdatedByUsername("username");
		categoryCO.setStatus(ActivityStatus.ACTIVE.name());

		Category category = new Category();
		category.setLevel("L1");
		category.setName("name");
		category.setHomepageBannerImage("http://localhost:8081/v1/image.jpg");
		category.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		category.setStatus(ActivityStatus.ACTIVE.name());

		Mockito.when(categoryDao.findByIdUncached(categoryId)).thenReturn(category);
		Mockito.when(cacheManager.getCache(Mockito.anyString())).thenReturn(cache);

		ServiceException thrown = Assertions.assertThrows(ServiceException.class, () -> {
			categoryService.updateCategory(categoryId, categoryCO);
		});
		Assertions.assertEquals("CS_17", thrown.getErrorCode());
	}

	@Test
	public void testUpdateWhenInvalidCategoryPageBannerImage() {
		Long categoryId = 1l;
		UpdateCategoryCO categoryCO = new UpdateCategoryCO();
		categoryCO.setHomepageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setCategoryPageBannerImage("htt://localhost:8081/v1/image.jpg");
		categoryCO.setDescription("Description");
		categoryCO.setUpdatedByUsername("username");
		categoryCO.setStatus(ActivityStatus.ACTIVE.name());

		Category category = new Category();
		category.setLevel("L1");
		category.setName("name");
		category.setHomepageBannerImage("http://localhost:8081/v1/image.jpg");
		category.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		category.setStatus(ActivityStatus.ACTIVE.name());

		Mockito.when(categoryDao.findByIdUncached(categoryId)).thenReturn(category);
		Mockito.when(cacheManager.getCache(Mockito.anyString())).thenReturn(cache);

		ServiceException thrown = Assertions.assertThrows(ServiceException.class, () -> {
			categoryService.updateCategory(categoryId, categoryCO);
		});
		Assertions.assertEquals("CS_17", thrown.getErrorCode());
	}

	@Test
	public void testUpdateWhenNoUpdatedByUsername() {
		Long categoryId = 1l;
		UpdateCategoryCO categoryCO = new UpdateCategoryCO();
		categoryCO.setHomepageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setDescription("Description");
		categoryCO.setStatus(ActivityStatus.ACTIVE.name());

		Category category = new Category();
		category.setLevel("L1");
		category.setName("name");
		category.setHomepageBannerImage("http://localhost:8081/v1/image.jpg");
		category.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		category.setStatus(ActivityStatus.ACTIVE.name());
		category.setCheckerStatus(CategoryStatus.APPROVED.name());

		Mockito.when(categoryDao.findByIdUncached(categoryId)).thenReturn(category);
		Mockito.when(cacheManager.getCache(Mockito.anyString())).thenReturn(cache);

		ServiceException thrown = Assertions.assertThrows(ServiceException.class, () -> {
			categoryService.updateCategory(categoryId, categoryCO);
		});
		Assertions.assertEquals("CS_08", thrown.getErrorCode());
	}

	@Test
	public void testUpdateWhenInvalidStatus() {
		Long categoryId = 1l;
		UpdateCategoryCO categoryCO = new UpdateCategoryCO();
		categoryCO.setHomepageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setUpdatedByUsername("username");
		categoryCO.setDescription("Description");

		Category category = new Category();
		category.setLevel("L1");
		category.setName("name");
		category.setHomepageBannerImage("http://localhost:8081/v1/image.jpg");
		category.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		category.setStatus(ActivityStatus.ACTIVE.name());
		category.setCheckerStatus(CategoryStatus.APPROVED.name());

		Mockito.when(categoryDao.findByIdUncached(categoryId)).thenReturn(category);
		Mockito.when(cacheManager.getCache(Mockito.anyString())).thenReturn(cache);

		ServiceException thrown = Assertions.assertThrows(ServiceException.class, () -> {
			categoryService.updateCategory(categoryId, categoryCO);
		});
		Assertions.assertEquals("CS_06", thrown.getErrorCode());
	}

	@Test
	public void testUpdateL2WhenInvalidCategoryPageBannerImage() {
		Long categoryId = 2l;
		UpdateCategoryCO categoryCO = new UpdateCategoryCO();
		categoryCO.setCategoryPageBannerImage("htt://localhost:8081/v1/image.jpg");
		categoryCO.setDescription("Description");
		categoryCO.setUpdatedByUsername("username");
		categoryCO.setStatus(ActivityStatus.ACTIVE.name());

		Category category = new Category();
		category.setLevel("L2");
		category.setName("name");
		category.setParentCategoryId(1l);
		category.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		category.setStatus(ActivityStatus.ACTIVE.name());
		category.setCheckerStatus(CategoryStatus.APPROVED.name());

		Mockito.when(categoryDao.findByIdUncached(categoryId)).thenReturn(category);
		Mockito.when(cacheManager.getCache(Mockito.anyString())).thenReturn(cache);

		ServiceException thrown = Assertions.assertThrows(ServiceException.class, () -> {
			categoryService.updateCategory(categoryId, categoryCO);
		});
		Assertions.assertEquals("CS_17", thrown.getErrorCode());
	}

	@Test
	public void testUpdateL2WhenNoUpdatedByUsername() {
		Long categoryId = 1l;
		UpdateCategoryCO categoryCO = new UpdateCategoryCO();
		categoryCO.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setDescription("Description");
		categoryCO.setStatus(ActivityStatus.ACTIVE.name());

		Category category = new Category();
		category.setLevel("L2");
		category.setName("name");
		category.setHomepageBannerImage("http://localhost:8081/v1/image.jpg");
		category.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		category.setStatus(ActivityStatus.ACTIVE.name());
		category.setCheckerStatus(CategoryStatus.APPROVED.name());

		Mockito.when(categoryDao.findByIdUncached(categoryId)).thenReturn(category);
		Mockito.when(cacheManager.getCache(Mockito.anyString())).thenReturn(cache);

		ServiceException thrown = Assertions.assertThrows(ServiceException.class, () -> {
			categoryService.updateCategory(categoryId, categoryCO);
		});
		Assertions.assertEquals("CS_08", thrown.getErrorCode());
	}

	@Test
	public void testUpdateL2WhenInvalidStatus() {
		Long categoryId = 2l;
		UpdateCategoryCO categoryCO = new UpdateCategoryCO();
		categoryCO.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setDescription("Description");
		categoryCO.setUpdatedByUsername("username");

		Category category = new Category();
		category.setLevel("L2");
		category.setName("name");
		category.setParentCategoryId(1l);
		category.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		category.setStatus(ActivityStatus.ACTIVE.name());
		category.setCheckerStatus(CategoryStatus.APPROVED.name());

		Mockito.when(categoryDao.findByIdUncached(categoryId)).thenReturn(category);
		Mockito.when(cacheManager.getCache(Mockito.anyString())).thenReturn(cache);

		ServiceException thrown = Assertions.assertThrows(ServiceException.class, () -> {
			categoryService.updateCategory(categoryId, categoryCO);
		});
		Assertions.assertEquals("CS_06", thrown.getErrorCode());
	}

	@Test
	public void testUpdateL2WhenCheckerStatusPending() {
		Long categoryId = 2l;
		UpdateCategoryCO categoryCO = new UpdateCategoryCO();
		categoryCO.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setDescription("Description");
		categoryCO.setUpdatedByUsername("username");
		categoryCO.setStatus(ActivityStatus.ACTIVE.name());

		Category category = new Category();
		category.setLevel("L2");
		category.setName("name");
		category.setParentCategoryId(1l);
		category.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		category.setStatus(ActivityStatus.ACTIVE.name());
		category.setCheckerStatus(CategoryStatus.PENDING.name());

		Mockito.when(categoryDao.findByIdUncached(categoryId)).thenReturn(category);
		Mockito.when(cacheManager.getCache(Mockito.anyString())).thenReturn(cache);

		ServiceException thrown = Assertions.assertThrows(ServiceException.class, () -> {
			categoryService.updateCategory(categoryId, categoryCO);
		});
		Assertions.assertEquals("CS_30", thrown.getErrorCode());
	}

	@Test
	public void testUpdateL3WhenCheckerStatusPending() {
		Long categoryId = 3l;
		UpdateCategoryCO categoryCO = new UpdateCategoryCO();
		categoryCO.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setDescription("Description");
		categoryCO.setUpdatedByUsername("username");
		categoryCO.setStatus(ActivityStatus.ACTIVE.name());
		categoryCO.setTemplateFileUrl("http://localhost-template-url-file");

		Category category = new Category();
		category.setName("name");
		category.setLevel("L3");
		category.setParentCategoryId(2l);
		category.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		category.setStatus(ActivityStatus.ACTIVE.name());
		category.setCheckerStatus(CategoryStatus.PENDING.name());
		category.setTemplateFileUrl("http://localhost-template-url-file");

		Mockito.when(categoryDao.findByIdUncached(categoryId)).thenReturn(category);
		Mockito.when(cacheManager.getCache(Mockito.anyString())).thenReturn(cache);

		ServiceException thrown = Assertions.assertThrows(ServiceException.class, () -> {
			categoryService.updateCategory(categoryId, categoryCO);
		});
		Assertions.assertEquals("CS_30", thrown.getErrorCode());
	}

	@Test
	public void testUpdateL3WhenInvalidCategoryPageBannerImage() {
		Long categoryId = 3l;
		UpdateCategoryCO categoryCO = new UpdateCategoryCO();
		categoryCO.setCategoryPageBannerImage("htt://localhost:8081/v1/image.jpg");
		categoryCO.setDescription("Description");
		categoryCO.setUpdatedByUsername("username");
		categoryCO.setStatus(ActivityStatus.ACTIVE.name());
		categoryCO.setTemplateFileUrl("http://localhost-template-url-file");

		Category category = new Category();
		category.setName("name");
		category.setLevel("L3");
		category.setParentCategoryId(2l);
		category.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		category.setStatus(ActivityStatus.ACTIVE.name());
		category.setCheckerStatus(CategoryStatus.APPROVED.name());
		category.setTemplateFileUrl("http://localhost-template-url-file");

		Mockito.when(categoryDao.findByIdUncached(categoryId)).thenReturn(category);
		Mockito.when(cacheManager.getCache(Mockito.anyString())).thenReturn(cache);

		ServiceException thrown = Assertions.assertThrows(ServiceException.class, () -> {
			categoryService.updateCategory(categoryId, categoryCO);
		});
		Assertions.assertEquals("CS_17", thrown.getErrorCode());
	}

	@Test
	public void testUpdateL3WhenNoUpdatedByUsername() {
		Long categoryId = 3l;
		UpdateCategoryCO categoryCO = new UpdateCategoryCO();
		categoryCO.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setDescription("Description");
		categoryCO.setStatus(ActivityStatus.ACTIVE.name());
		categoryCO.setTemplateFileUrl("http://localhost-template-url-file");

		Category category = new Category();
		category.setName("name");
		category.setLevel("L3");
		category.setParentCategoryId(2l);
		category.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		category.setStatus(ActivityStatus.ACTIVE.name());
		category.setCheckerStatus(CategoryStatus.APPROVED.name());
		category.setTemplateFileUrl("http://localhost-template-url-file");

		Mockito.when(categoryDao.findByIdUncached(categoryId)).thenReturn(category);
		Mockito.when(cacheManager.getCache(Mockito.anyString())).thenReturn(cache);

		ServiceException thrown = Assertions.assertThrows(ServiceException.class, () -> {
			categoryService.updateCategory(categoryId, categoryCO);
		});
		Assertions.assertEquals("CS_08", thrown.getErrorCode());
	}

	@Test
	public void testUpdateL3WhenInvalidStatus() {
		Long categoryId = 3l;
		UpdateCategoryCO categoryCO = new UpdateCategoryCO();
		categoryCO.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setDescription("Description");
		categoryCO.setUpdatedByUsername("username");
		categoryCO.setTemplateFileUrl("http://localhost-template-url-file");

		Category category = new Category();
		category.setName("name");
		category.setLevel("L3");
		category.setParentCategoryId(2l);
		category.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		category.setStatus(ActivityStatus.ACTIVE.name());
		category.setCheckerStatus(CategoryStatus.APPROVED.name());
		category.setTemplateFileUrl("http://localhost-template-url-file");

		Mockito.when(categoryDao.findByIdUncached(categoryId)).thenReturn(category);
		Mockito.when(cacheManager.getCache(Mockito.anyString())).thenReturn(cache);

		ServiceException thrown = Assertions.assertThrows(ServiceException.class, () -> {
			categoryService.updateCategory(categoryId, categoryCO);
		});
		Assertions.assertEquals("CS_06", thrown.getErrorCode());
	}

	@Test
	public void testUpdateL3WhenInvalidTemplateFileUrl() {
		Long categoryId = 3l;
		UpdateCategoryCO categoryCO = new UpdateCategoryCO();
		categoryCO.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setDescription("Description");
		categoryCO.setUpdatedByUsername("username");
		categoryCO.setStatus(ActivityStatus.ACTIVE.name());
		categoryCO.setTemplateFileUrl("htt://localhost-template-url-file");

		Category category = new Category();
		category.setName("name");
		category.setLevel("L3");
		category.setParentCategoryId(2l);
		category.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		category.setStatus(ActivityStatus.ACTIVE.name());
		category.setCheckerStatus(CategoryStatus.APPROVED.name());
		category.setTemplateFileUrl("http://localhost-template-url-file");

		Mockito.when(categoryDao.findByIdUncached(categoryId)).thenReturn(category);
		Mockito.when(cacheManager.getCache(Mockito.anyString())).thenReturn(cache);

		ServiceException thrown = Assertions.assertThrows(ServiceException.class, () -> {
			categoryService.updateCategory(categoryId, categoryCO);
		});
		Assertions.assertEquals("CS_17", thrown.getErrorCode());
	}

	@Test
	public void testAddCategoryFailWhenParentCheckerStatusDraft() {
		CategoryCO categoryCO = new CategoryCO();
		categoryCO.setName("Name");
		categoryCO.setParentCategoryId(2l);
		categoryCO.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setDescription("Description");
		categoryCO.setCategoryPageBannerImage("username");
		categoryCO.setStatus(ActivityStatus.ACTIVE.name());
		categoryCO.setLevel("L2");

		Category parentCategory = new Category();
		parentCategory.setId(1l);
		parentCategory.setStatus(ActivityStatus.ACTIVE.name());
		parentCategory.setCheckerStatus(CategoryStatus.DRAFT.name());

		Mockito.when(categoryDao.findByIdUncached(categoryCO.getParentCategoryId())).thenReturn(parentCategory);
		ServiceException thrown = Assertions.assertThrows(ServiceException.class, () -> {
			categoryService.addCategory(categoryCO);
		});
		Assertions.assertEquals("CS_38", thrown.getErrorCode());
	}

	@Test
	public void testAddCategoryFailWhenParentStatusInactive() {
		CategoryCO categoryCO = new CategoryCO();
		categoryCO.setName("Name");
		categoryCO.setParentCategoryId(2l);
		categoryCO.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setDescription("Description");
		categoryCO.setCategoryPageBannerImage("username");
		categoryCO.setStatus(ActivityStatus.ACTIVE.name());
		categoryCO.setLevel("L2");

		Category parentCategory = new Category();
		parentCategory.setId(2l);
		parentCategory.setStatus(ActivityStatus.INACTIVE.name());

		Mockito.when(categoryDao.findByIdUncached(categoryCO.getParentCategoryId())).thenReturn(parentCategory);
		ServiceException thrown = Assertions.assertThrows(ServiceException.class, () -> {
			categoryService.addCategory(categoryCO);
		});
		Assertions.assertEquals("CS_39", thrown.getErrorCode());
	}


	@Test
	public void testValidateCategoryNameWhenDuplicateWithoutParent() {
		String categoryName = "Fashion";
		String level = "L1";
		Long parentCategoryId = null;

		CategoryCO categoryCO =  getCategoryCOWithNameLevelParentCategoryId(categoryName, level, parentCategoryId);

		List<Category> mockData = new ArrayList<>();
		mockData.add(getDummyCategory(categoryName, null, level));
		EntryItem<Category> entryItem = new EntryItem<>(1L, mockData);

		Mockito.when(categoryDao.findByCriteriaFields(any(), any(), any())).thenReturn(entryItem);

		ServiceException thrown = Assertions.assertThrows(ServiceException.class, () -> {
			categoryService.addCategory(categoryCO);
		});

		Assertions.assertEquals("CS_20", thrown.getErrorCode());
	}

	@Test
	public void testValidateCategoryNameWhenNoDuplicateWithoutParent() {
		String categoryName = "Fashion";
		String level = "L1";
		Long parentCategoryId = null;

		CategoryCO categoryCO = getCategoryCOWithNameLevelParentCategoryId(categoryName, level, parentCategoryId);

		Category category = getDummyCategory(categoryName, null, level);

		EntryItem<Category> entryItem = new EntryItem<>(0L, new ArrayList<>());
		Mockito.when(categoryDao.findByCriteriaFields(any(), any(), any())).thenReturn(entryItem);
		Mockito.when(categoryDao.save(any(Category.class))).thenReturn(category);
		Mockito.when(cacheManager.getCache(Mockito.anyString())).thenReturn(cache);

		Assertions.assertDoesNotThrow(() -> {
			categoryService.addCategory(categoryCO);
		});
	}

	@Test
	public void testValidateCategoryNameWithSimilarNameUnderSameParent() {

		String categoryName = "Jeans";
		String level = "L3";
		Long parentCategoryId = 121L;

		CategoryCO categoryCO = getCategoryCOWithNameLevelParentCategoryId(categoryName, level, parentCategoryId);

		List<Category> mockData = new ArrayList<>();
		mockData.add(getDummyCategory(categoryName, null, level));
		EntryItem<Category> entryItem = new EntryItem<>(1L, mockData);

		Category parentCategory = getDummyCategory("Men", parentCategoryId, "L2");

		Mockito.when(categoryDao.findByCriteriaFields(any(), any(), any())).thenReturn(entryItem);
		Mockito.when(cacheManager.getCache(Mockito.anyString())).thenReturn(cache);
		Mockito.when(categoryDao.findByIdUncached(parentCategoryId)).thenReturn(parentCategory);

		ServiceException thrown = Assertions.assertThrows(ServiceException.class, () -> {
			categoryService.addCategory(categoryCO);
		});

		Assertions.assertEquals("CS_20", thrown.getErrorCode());
	}

	@Test
	public void testValidateCategoryNameWithoutSimilarNameUnderSameParent() {
		String categoryName = "Jeans";
		String level = "L3";
		Long parentCategoryId = 121L;

		CategoryCO categoryCO = getCategoryCOWithNameLevelParentCategoryId(categoryName, level, parentCategoryId);
		Category parentCategory = getDummyCategory("Men", parentCategoryId, "L2");
		Category category = getDummyCategory(categoryName, parentCategoryId, level);

		EntryItem<Category> entryItem = new EntryItem<>(0L, new ArrayList<>());
		Mockito.when(cacheManager.getCache(Mockito.anyString())).thenReturn(cache);
		Mockito.when(categoryDao.findByCriteriaFields(any(), any(), any())).thenReturn(entryItem);
		Mockito.when(categoryDao.findByIdUncached(parentCategoryId)).thenReturn(parentCategory);
		Mockito.when(categoryDao.save(any(Category.class))).thenReturn(category);

		Assertions.assertDoesNotThrow(() -> {
			categoryService.addCategory(categoryCO);
		});
	}

	private Category getDummyCategory(String name, Long parentCategoryId, String level) {
		Category category = new Category();
		category.setName(name);
		category.setParseName(CommonUtil.removeWhiteSpace(name));
		category.setParentCategoryId(parentCategoryId);
		category.setLevel(level);
		return category;
	}

	private CategoryCO getCategoryCOWithDefaultValues() {
		CategoryCO categoryCO = new CategoryCO();
		categoryCO.setName("name");
		categoryCO.setHomepageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setDescription("Description");
		categoryCO.setCreatedByUsername("username");
		categoryCO.setLevel(CategoryLevel.L1.name());
		return categoryCO;
	}

	private CategoryCO getCategoryCOWithNameLevelParentCategoryId(String categoryName, String level,
																  Long parentCategoryId) {
		CategoryCO categoryCO = getCategoryCOWithDefaultValues();
		categoryCO.setName(categoryName);
		categoryCO.setLevel(level);
		categoryCO.setParentCategoryId(parentCategoryId);
		return categoryCO;
	}






	@Test
	public void testUpdateHomePageBannerImageForL2L3(){
		Long categoryId = 1l;
		UpdateCategoryCO categoryCO = new UpdateCategoryCO();
		categoryCO.setHomepageBannerImage("htt://localhost:8081/v1/image.jpg");
		categoryCO.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setDescription("Description");
		categoryCO.setUpdatedByUsername("username");
		categoryCO.setStatus(ActivityStatus.ACTIVE.name());

		Category category= new Category();
		category.setLevel("L2");
		category.setName("name");
		category.setHomepageBannerImage("http://localhost:8081/v1/image.jpg");
		category.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		category.setStatus(ActivityStatus.ACTIVE.name());
		category.setCheckerStatus(CategoryStatus.APPROVED.name());

		Mockito.when(categoryDao.findByIdUncached(categoryId)).thenReturn(category);
		Mockito.when(cacheManager.getCache(Mockito.anyString())).thenReturn(cache);

		ServiceException thrown = Assertions.assertThrows(ServiceException.class, () -> {
			categoryService.updateCategory(categoryId,categoryCO);
		});
		Assertions.assertEquals("CS_28", thrown.getErrorCode());
	}

	@Test
	public void testUpdateWhenInvalidUpdatedByUsername(){
		Long categoryId = 1l;
		UpdateCategoryCO categoryCO = new UpdateCategoryCO();
		categoryCO.setHomepageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setDescription("Description");
		categoryCO.setStatus(ActivityStatus.ACTIVE.name());

		Category category= new Category();
		category.setLevel("L1");
		category.setName("name");
		category.setHomepageBannerImage("http://localhost:8081/v1/image.jpg");
		category.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		category.setStatus(ActivityStatus.ACTIVE.name());
		category.setCheckerStatus(CategoryStatus.APPROVED.name());

		Mockito.when(categoryDao.findByIdUncached(categoryId)).thenReturn(category);
		Mockito.when(cacheManager.getCache(Mockito.anyString())).thenReturn(cache);

		ServiceException thrown = Assertions.assertThrows(ServiceException.class, () -> {
			categoryService.updateCategory(categoryId,categoryCO);
		});
		Assertions.assertEquals("CS_08", thrown.getErrorCode());
	}

	@Test
	public void testUpdateL2WhenInvalidUpdatedByUsername(){
		Long categoryId = 1l;
		UpdateCategoryCO categoryCO = new UpdateCategoryCO();
		categoryCO.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setDescription("Description");
		categoryCO.setStatus(ActivityStatus.ACTIVE.name());

		Category category= new Category();
		category.setLevel("L2");
		category.setName("name");
		category.setHomepageBannerImage("http://localhost:8081/v1/image.jpg");
		category.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		category.setStatus(ActivityStatus.ACTIVE.name());
		category.setCheckerStatus(CategoryStatus.APPROVED.name());

		Mockito.when(categoryDao.findByIdUncached(categoryId)).thenReturn(category);
		Mockito.when(cacheManager.getCache(Mockito.anyString())).thenReturn(cache);

		ServiceException thrown = Assertions.assertThrows(ServiceException.class, () -> {
			categoryService.updateCategory(categoryId,categoryCO);
		});
		Assertions.assertEquals("CS_08", thrown.getErrorCode());
	}

	@Test
	public void testUpdateL3WhenCategoryPageBannerImage(){
		Long categoryId = 3l;
		UpdateCategoryCO categoryCO = new UpdateCategoryCO();
		categoryCO.setCategoryPageBannerImage("htt://localhost:8081/v1/image.jpg");
		categoryCO.setDescription("Description");
		categoryCO.setUpdatedByUsername("username");
		categoryCO.setStatus(ActivityStatus.ACTIVE.name());
		categoryCO.setTemplateFileUrl("http://localhost-template-url-file");

		Category category = new Category();
		category.setName("name");
		category.setLevel("L3");
		category.setParentCategoryId(2l);
		category.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		category.setStatus(ActivityStatus.ACTIVE.name());
		category.setCheckerStatus(CategoryStatus.APPROVED.name());
		category.setTemplateFileUrl("http://localhost-template-url-file");

		Mockito.when(categoryDao.findByIdUncached(categoryId)).thenReturn(category);
		Mockito.when(cacheManager.getCache(Mockito.anyString())).thenReturn(cache);

		ServiceException thrown = Assertions.assertThrows(ServiceException.class, () ->{
			categoryService.updateCategory(categoryId, categoryCO);
		});
		Assertions.assertEquals("CS_17", thrown.getErrorCode());
	}

	@Test
	public void testUpdateL3WhenUpdatedByUsername(){
		Long categoryId = 3l;
		UpdateCategoryCO categoryCO = new UpdateCategoryCO();
		categoryCO.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setDescription("Description");
		categoryCO.setStatus(ActivityStatus.ACTIVE.name());
		categoryCO.setTemplateFileUrl("http://localhost-template-url-file");

		Category category = new Category();
		category.setName("name");
		category.setLevel("L3");
		category.setParentCategoryId(2l);
		category.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		category.setStatus(ActivityStatus.ACTIVE.name());
		category.setCheckerStatus(CategoryStatus.APPROVED.name());
		category.setTemplateFileUrl("http://localhost-template-url-file");

		Mockito.when(categoryDao.findByIdUncached(categoryId)).thenReturn(category);
		Mockito.when(cacheManager.getCache(Mockito.anyString())).thenReturn(cache);

		ServiceException thrown = Assertions.assertThrows(ServiceException.class, () ->{
			categoryService.updateCategory(categoryId, categoryCO);
		});
		Assertions.assertEquals("CS_08", thrown.getErrorCode());
	}

	@Test
	public void testUpdateL3WhenStatus(){
		Long categoryId = 3l;
		UpdateCategoryCO categoryCO = new UpdateCategoryCO();
		categoryCO.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setDescription("Description");
		categoryCO.setUpdatedByUsername("username");
		categoryCO.setTemplateFileUrl("http://localhost-template-url-file");

		Category category = new Category();
		category.setName("name");
		category.setLevel("L3");
		category.setParentCategoryId(2l);
		category.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		category.setStatus(ActivityStatus.ACTIVE.name());
		category.setCheckerStatus(CategoryStatus.APPROVED.name());
		category.setTemplateFileUrl("http://localhost-template-url-file");

		Mockito.when(categoryDao.findByIdUncached(categoryId)).thenReturn(category);
		Mockito.when(cacheManager.getCache(Mockito.anyString())).thenReturn(cache);

		ServiceException thrown = Assertions.assertThrows(ServiceException.class, () ->{
			categoryService.updateCategory(categoryId, categoryCO);
		});
		Assertions.assertEquals("CS_06", thrown.getErrorCode());
	}

	@Test
	public void testUpdateL3WhenTemplateFileUrlWrong(){
		Long categoryId = 3l;
		UpdateCategoryCO categoryCO = new UpdateCategoryCO();
		categoryCO.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setDescription("Description");
		categoryCO.setUpdatedByUsername("username");
		categoryCO.setStatus(ActivityStatus.ACTIVE.name());
		categoryCO.setTemplateFileUrl("htt://localhost-template-url-file");

		Category category = new Category();
		category.setName("name");
		category.setLevel("L3");
		category.setParentCategoryId(2l);
		category.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		category.setStatus(ActivityStatus.ACTIVE.name());
		category.setCheckerStatus(CategoryStatus.APPROVED.name());
		category.setTemplateFileUrl("http://localhost-template-url-file");

		Mockito.when(categoryDao.findByIdUncached(categoryId)).thenReturn(category);
		Mockito.when(cacheManager.getCache(Mockito.anyString())).thenReturn(cache);

		ServiceException thrown = Assertions.assertThrows(ServiceException.class, () ->{
			categoryService.updateCategory(categoryId, categoryCO);
		});
		Assertions.assertEquals("CS_17", thrown.getErrorCode());
	}

	@Test
	public void testUpdateL1L2WithTemplateFileUrl(){
		Long categoryId = 3l;
		UpdateCategoryCO categoryCO = new UpdateCategoryCO();
		categoryCO.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setDescription("Description");
		categoryCO.setUpdatedByUsername("username");
		categoryCO.setStatus(ActivityStatus.ACTIVE.name());
		categoryCO.setTemplateFileUrl("http://templatefile");

		Category category = new Category();
		category.setName("name");
		category.setLevel("L2");
		category.setParentCategoryId(2l);
		category.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		category.setStatus(ActivityStatus.ACTIVE.name());
		category.setCheckerStatus(CategoryStatus.APPROVED.name());
		category.setTemplateFileUrl("http://localhost-template-url-file");

		Mockito.when(categoryDao.findByIdUncached(categoryId)).thenReturn(category);
		Mockito.when(cacheManager.getCache(Mockito.anyString())).thenReturn(cache);

		ServiceException thrown = Assertions.assertThrows(ServiceException.class, () ->{
			categoryService.updateCategory(categoryId, categoryCO);
		});
		Assertions.assertEquals("CS_29", thrown.getErrorCode());
	}

	@Test
	public void testUpdateL1CategoryWithDraftCheckerStatus() {
		Long categoryId = 1l;
		UpdateCategoryCO categoryCO = new UpdateCategoryCO();
		categoryCO.setHomepageBannerImage("https://homepage");
		categoryCO.setCategoryPageBannerImage("http://categoryimage");
		categoryCO.setDescription("description");
		categoryCO.setUpdatedByUsername("username");
		categoryCO.setStatus("ACTIVE");

		Category category = new Category();
		category.setName("name");
		category.setHomepageBannerImage("https://home");
		category.setCategoryPageBannerImage("https://category");
		category.setCheckerStatus(CategoryStatus.DRAFT.name());
		category.setLevel("L1");

		Mockito.when(categoryDao.findByIdUncached(categoryId)).thenReturn(category);
		Mockito.when(cacheManager.getCache(anyString())).thenReturn(cache);

		ServiceException thrown = Assertions.assertThrows(ServiceException.class, () ->{
			categoryService.updateCategory(categoryId, categoryCO);
		});
		Assertions.assertEquals("CS_30", thrown.getErrorCode());
	}

	@Test
	public void testUpdateL2CategoryWithDraftCheckerStatus() {
		Long categoryId = 1l;
		UpdateCategoryCO categoryCO = new UpdateCategoryCO();
		categoryCO.setCategoryPageBannerImage("http://categoryimage");
		categoryCO.setDescription("description");
		categoryCO.setUpdatedByUsername("username");
		categoryCO.setStatus("ACTIVE");

		Category category = new Category();
		category.setName("name");
		category.setParentCategoryId(2l);
		category.setCategoryPageBannerImage("https://category");
		category.setCheckerStatus(CategoryStatus.DRAFT.name());
		category.setLevel("L2");

		Mockito.when(categoryDao.findByIdUncached(categoryId)).thenReturn(category);
		Mockito.when(cacheManager.getCache(anyString())).thenReturn(cache);

		ServiceException thrown = Assertions.assertThrows(ServiceException.class, () ->{
			categoryService.updateCategory(categoryId, categoryCO);
		});
		Assertions.assertEquals("CS_30", thrown.getErrorCode());
	}

	@Test
	public void testUpdateL3CategoryWithDraftCheckerStatus() {
		Long categoryId = 1l;
		UpdateCategoryCO categoryCO = new UpdateCategoryCO();
		categoryCO.setCategoryPageBannerImage("http://categoryimage");
		categoryCO.setDescription("description");
		categoryCO.setUpdatedByUsername("username");
		categoryCO.setStatus("ACTIVE");
		categoryCO.setTemplateFileUrl("http://template");

		Category category = new Category();
		category.setName("name");
		category.setParentCategoryId(2l);
		category.setCategoryPageBannerImage("https://category");
		category.setCheckerStatus(CategoryStatus.DRAFT.name());
		category.setLevel("L3");

		Mockito.when(categoryDao.findByIdUncached(categoryId)).thenReturn(category);
		Mockito.when(cacheManager.getCache(anyString())).thenReturn(cache);

		ServiceException thrown = Assertions.assertThrows(ServiceException.class, () ->{
			categoryService.updateCategory(categoryId, categoryCO);
		});
		Assertions.assertEquals("CS_30", thrown.getErrorCode());
	}

	@Test
	public void testApproveCategoryOfNonPendingStatus() {
		ApproveRejectCategoryCO categoryCO =new ApproveRejectCategoryCO();
		categoryCO.setId(3l);
		categoryCO.setCheckerStatus(CategoryStatus.APPROVED.name());
		categoryCO.setReviewedBy("reviewer");

		Category category = new Category();
		category.setId(3l);
		category.setName("name3");
		category.setUpdatedByUsername("username");
		category.setStatus(ActivityStatus.INACTIVE.name());
		category.setCheckerStatus(CategoryStatus.APPROVED.name());

		Mockito.when(categoryDao.findByIdUncached(categoryCO.getId())).thenReturn(category);
		Mockito.when(cacheManager.getCache(anyString())).thenReturn(cache);

		ServiceException thrown = Assertions.assertThrows(ServiceException.class, () ->{
			CategoryValidator.validateUpdateAll(categoryCO, category);
		});
		Assertions.assertEquals("CS_04", thrown.getErrorCode());
	}

	@Test
	public void testApproveCategoryOfPendingStatus() {
		List<ApproveRejectCategoryCO> categoryCOList = new ArrayList<>();
		ApproveRejectCategoryCO categoryCO =new ApproveRejectCategoryCO();
		categoryCO.setId(1l);
		categoryCO.setCheckerStatus(CategoryStatus.APPROVED.name());
		categoryCO.setReviewedBy("reviewer");

		categoryCOList.add(categoryCO);

		Category category = new Category();
		category.setId(1l);
		category.setName("name");
		category.setUpdatedByUsername("username");
		category.setCheckerStatus(CategoryStatus.PENDING.name());

		Mockito.when(categoryDao.findByIdUncached(categoryCO.getId())).thenReturn(category);
		Mockito.when(cacheManager.getCache(anyString())).thenReturn(cache);

		Map<String, Map> result = categoryService.approveRejectCategories(categoryCOList);
		System.out.println(result);
	}

	@Test
	public void testRejectCategoryOfDraftStatus() {
		List<ApproveRejectCategoryCO> categoryCOList = new ArrayList<>();
		ApproveRejectCategoryCO categoryCO =new ApproveRejectCategoryCO();
		categoryCO.setId(3l);
		categoryCO.setCheckerStatus(CategoryStatus.REJECTED.name());
		categoryCO.setReviewedBy("reviewer");
		categoryCO.setRejectReason("reason");

		categoryCOList.add(categoryCO);

		Category category = new Category();
		category.setId(3l);
		category.setName("name3");
		category.setUpdatedByUsername("username");
		category.setStatus(ActivityStatus.ACTIVE.name());
		category.setCheckerStatus(CategoryStatus.DRAFT.name());

		Mockito.when(categoryDao.findByIdUncached(categoryCO.getId())).thenReturn(category);
		Mockito.when(cacheManager.getCache(anyString())).thenReturn(cache);

		Map<String, Map> result = categoryService.approveRejectCategories(categoryCOList);
		System.out.println(result);
	}

	@Test
	public void testTryingToRejectNonPendingCategory() {
		ApproveRejectCategoryCO categoryCO =new ApproveRejectCategoryCO();
		categoryCO.setId(3l);
		categoryCO.setCheckerStatus(CategoryStatus.REJECTED.name());
		categoryCO.setReviewedBy("reviewer");
		categoryCO.setRejectReason("reason");

		Category category = new Category();
		category.setId(3l);
		category.setName("name3");
		category.setUpdatedByUsername("username");
		category.setStatus(ActivityStatus.ACTIVE.name());
		category.setCheckerStatus(CategoryStatus.APPROVED.name());

		Mockito.when(categoryDao.findByIdUncached(categoryCO.getId())).thenReturn(category);
		Mockito.when(cacheManager.getCache(anyString())).thenReturn(cache);

		ServiceException thrown = Assertions.assertThrows(ServiceException.class, () ->{
			CategoryValidator.validateUpdateAll(categoryCO, category);
		});
		Assertions.assertEquals("CS_04", thrown.getErrorCode());
	}

	@Test
	public void testTryingToRejectWithoutReviewedBy() {
		ApproveRejectCategoryCO categoryCO =new ApproveRejectCategoryCO();
		categoryCO.setId(3l);
		categoryCO.setCheckerStatus(CategoryStatus.REJECTED.name());
		categoryCO.setReviewedBy(null);
		categoryCO.setRejectReason("reason");

		Category category = new Category();
		category.setId(3l);
		category.setName("name3");
		category.setUpdatedByUsername("username");
		category.setStatus(ActivityStatus.ACTIVE.name());
		category.setCheckerStatus(CategoryStatus.PENDING.name());

		Mockito.when(categoryDao.findByIdUncached(categoryCO.getId())).thenReturn(category);
		Mockito.when(cacheManager.getCache(anyString())).thenReturn(cache);

		ServiceException thrown = Assertions.assertThrows(ServiceException.class, () ->{
			CategoryValidator.validateUpdateAll(categoryCO, category);
		});
		Assertions.assertEquals("CS_09", thrown.getErrorCode());
	}

	@Test
	public void testTryingToRejectWithoutRejectReason() {
		ApproveRejectCategoryCO categoryCO =new ApproveRejectCategoryCO();
		categoryCO.setId(3l);
		categoryCO.setCheckerStatus(CategoryStatus.REJECTED.name());
		categoryCO.setReviewedBy("reviewer");

		Category category = new Category();
		category.setId(3l);
		category.setName("name3");
		category.setUpdatedByUsername("username");
		category.setStatus(ActivityStatus.ACTIVE.name());
		category.setCheckerStatus(CategoryStatus.PENDING.name());

		Mockito.when(categoryDao.findByIdUncached(categoryCO.getId())).thenReturn(category);
		Mockito.when(cacheManager.getCache(anyString())).thenReturn(cache);

		ServiceException thrown = Assertions.assertThrows(ServiceException.class, () ->{
			CategoryValidator.validateUpdateAll(categoryCO, category);
		});
		Assertions.assertEquals("CS_11", thrown.getErrorCode());
	}

	@Test
	public void testTryingToApproveWithRejectReason() {
		ApproveRejectCategoryCO categoryCO =new ApproveRejectCategoryCO();
		categoryCO.setId(3l);
		categoryCO.setCheckerStatus(CategoryStatus.APPROVED.name());
		categoryCO.setReviewedBy("reviewer");
		categoryCO.setRejectReason("reason");

		Category category = new Category();
		category.setId(3l);
		category.setName("name3");
		category.setUpdatedByUsername("username");
		category.setStatus(ActivityStatus.ACTIVE.name());
		category.setCheckerStatus(CategoryStatus.PENDING.name());

		Mockito.when(categoryDao.findByIdUncached(categoryCO.getId())).thenReturn(category);
		Mockito.when(cacheManager.getCache(anyString())).thenReturn(cache);

		ServiceException thrown = Assertions.assertThrows(ServiceException.class, () ->{
			CategoryValidator.validateUpdateAll(categoryCO, category);
		});
		Assertions.assertEquals("CS_15", thrown.getErrorCode());
	}

	@Test
	public void testPassRejectReasonWithoutCheckerStatus() {
		ApproveRejectCategoryCO categoryCO =new ApproveRejectCategoryCO();
		categoryCO.setId(3l);
		categoryCO.setCheckerStatus(null);
		categoryCO.setReviewedBy("reviewer");
		categoryCO.setRejectReason("reason");

		Category category = new Category();
		category.setId(3l);
		category.setName("name3");
		category.setUpdatedByUsername("username");
		category.setStatus(ActivityStatus.ACTIVE.name());
		category.setCheckerStatus(CategoryStatus.PENDING.name());

		Mockito.when(categoryDao.findByIdUncached(categoryCO.getId())).thenReturn(category);
		Mockito.when(cacheManager.getCache(anyString())).thenReturn(cache);

		ServiceException thrown = Assertions.assertThrows(ServiceException.class, () ->{
			CategoryValidator.validateUpdateAll(categoryCO, category);
		});
		Assertions.assertEquals("CS_42", thrown.getErrorCode());
	}

	@Test
	public void testUpdateWithWrongCheckerStatus() {
		ApproveRejectCategoryCO categoryCO =new ApproveRejectCategoryCO();
		categoryCO.setId(3l);
		categoryCO.setCheckerStatus("1233");
		categoryCO.setReviewedBy("reviewer");

		Category category = new Category();
		category.setId(3l);
		category.setName("name3");
		category.setUpdatedByUsername("username");
		category.setStatus(ActivityStatus.ACTIVE.name());
		category.setCheckerStatus(CategoryStatus.PENDING.name());

		Mockito.when(categoryDao.findByIdUncached(categoryCO.getId())).thenReturn(category);
		Mockito.when(cacheManager.getCache(anyString())).thenReturn(cache);

		ServiceException thrown = Assertions.assertThrows(ServiceException.class, () ->{
			CategoryValidator.validateUpdateAll(categoryCO, category);
		});
		Assertions.assertEquals("CS_16", thrown.getErrorCode());
	}

	@Test
	public void testCreatedByAndReviewedBySameName() {
		ApproveRejectCategoryCO categoryCO =new ApproveRejectCategoryCO();
		categoryCO.setId(3l);
		categoryCO.setCheckerStatus(CategoryStatus.APPROVED.name());
		categoryCO.setReviewedBy("username");

		Category category = new Category();
		category.setId(3l);
		category.setName("name3");
		category.setCreatedByUsername("username");
		category.setStatus(ActivityStatus.ACTIVE.name());
		category.setCheckerStatus(CategoryStatus.DRAFT.name());

		Mockito.when(categoryDao.findByIdUncached(categoryCO.getId())).thenReturn(category);
		Mockito.when(cacheManager.getCache(anyString())).thenReturn(cache);

		ServiceException thrown = Assertions.assertThrows(ServiceException.class, () ->{
			CategoryValidator.validateUpdateAll(categoryCO, category);
		});
		Assertions.assertEquals("CS_34", thrown.getErrorCode());
	}

	@Test
	public void testUpdatedByAndReviewedBySameName() {
		ApproveRejectCategoryCO categoryCO =new ApproveRejectCategoryCO();
		categoryCO.setId(3l);
		categoryCO.setCheckerStatus(CategoryStatus.APPROVED.name());
		categoryCO.setReviewedBy("username");

		Category category = new Category();
		category.setId(3l);
		category.setName("name3");
		category.setUpdatedByUsername("username");
		category.setStatus(ActivityStatus.ACTIVE.name());
		category.setCheckerStatus(CategoryStatus.PENDING.name());

		Mockito.when(categoryDao.findByIdUncached(categoryCO.getId())).thenReturn(category);
		Mockito.when(cacheManager.getCache(anyString())).thenReturn(cache);

		ServiceException thrown = Assertions.assertThrows(ServiceException.class, () ->{
			CategoryValidator.validateUpdateAll(categoryCO, category);
		});
		Assertions.assertEquals("CS_34", thrown.getErrorCode());
	}

	@Test
	public void testUpdateWithoutCheckerStatus() {
		ApproveRejectCategoryCO categoryCO =new ApproveRejectCategoryCO();
		categoryCO.setId(3l);
		categoryCO.setCheckerStatus(null);
		categoryCO.setReviewedBy("reviewer");

		Category category = new Category();
		category.setId(3l);
		category.setName("name3");
		category.setUpdatedByUsername("username");
		category.setStatus(ActivityStatus.ACTIVE.name());
		category.setCheckerStatus(CategoryStatus.PENDING.name());

		Mockito.when(categoryDao.findByIdUncached(categoryCO.getId())).thenReturn(category);
		Mockito.when(cacheManager.getCache(anyString())).thenReturn(cache);

		ServiceException thrown = Assertions.assertThrows(ServiceException.class, () ->{
			CategoryValidator.validateUpdateAll(categoryCO, category);
		});
		Assertions.assertEquals("CS_42", thrown.getErrorCode());
	}

	@Test
	public void testUpdateWithEmptyCheckerStatus() {
		ApproveRejectCategoryCO categoryCO =new ApproveRejectCategoryCO();
		categoryCO.setId(3l);
		categoryCO.setCheckerStatus("");
		categoryCO.setReviewedBy("reviewer");

		Category category = new Category();
		category.setId(3l);
		category.setName("name3");
		category.setUpdatedByUsername("username");
		category.setStatus(ActivityStatus.ACTIVE.name());
		category.setCheckerStatus(CategoryStatus.PENDING.name());

		Mockito.when(categoryDao.findByIdUncached(categoryCO.getId())).thenReturn(category);
		Mockito.when(cacheManager.getCache(anyString())).thenReturn(cache);

		ServiceException thrown = Assertions.assertThrows(ServiceException.class, () ->{
			CategoryValidator.validateUpdateAll(categoryCO, category);
		});
		Assertions.assertEquals("CS_16", thrown.getErrorCode());
	}

	@Test
	public void testTryingToApproveWithoutReviewedBy() {
		ApproveRejectCategoryCO categoryCO =new ApproveRejectCategoryCO();
		categoryCO.setId(3l);
		categoryCO.setCheckerStatus(CategoryStatus.APPROVED.name());
		categoryCO.setReviewedBy(null);

		Category category = new Category();
		category.setId(3l);
		category.setName("name3");
		category.setUpdatedByUsername("username");
		category.setStatus(ActivityStatus.ACTIVE.name());
		category.setCheckerStatus(CategoryStatus.PENDING.name());

		Mockito.when(categoryDao.findByIdUncached(categoryCO.getId())).thenReturn(category);
		Mockito.when(cacheManager.getCache(anyString())).thenReturn(cache);

		ServiceException thrown = Assertions.assertThrows(ServiceException.class, () ->{
			CategoryValidator.validateUpdateAll(categoryCO, category);
		});
		Assertions.assertEquals("CS_09", thrown.getErrorCode());
	}
}

