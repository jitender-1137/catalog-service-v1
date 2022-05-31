package com.yaari.ms.catalogservice.dao;

import com.yaari.ms.catalogservice.domains.Category;
import com.yaari.ms.catalogservice.dto.CategoryMiniDto;
import com.yaari.ms.catalogservice.dto.EntryItem;
import com.yaari.ms.catalogservice.dto.GenericSearchFilter;
import com.yaari.ms.catalogservice.jparepositories.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CategoryDaoExtended extends CategoryDao {

    private final CategoryRepository categoryRepository;
    private final ApplicationContext applicationContext;

    public CategoryDaoExtended(CategoryRepository categoryRepository, ApplicationContext applicationContext) {
        this.categoryRepository = categoryRepository;
        this.applicationContext = applicationContext;
    }


    @Override
    public EntryItem<Category> findByCriteriaFields(GenericSearchFilter genericSearchFilter, Integer pageNumber, Integer pageSize) {
        log.info("cache key = {}", (genericSearchFilter.toString() + pageNumber + pageSize));
        EntryItem<Category> categoriesEntryItemFromOtherParams = super.findByCriteriaFields(genericSearchFilter, pageNumber, pageSize);
        return categoriesEntryItemFromOtherParams;
    }

    @Override
    @Cacheable(value = "category", key = "'categoryById' + #id", unless = "#result == null")
    public Category findById(Long id) {
        return super.findById(id);
    }

    // todo improvise...brute force logic to save time, Also moved it here from service to ...
    // ... manage cacheable. ideally it should be in service
    @Cacheable(value = "categoryMiniDto", key = "'parentOfCategoryById' + #categoryId", unless = "#result == null")
    public CategoryMiniDto getParentMiniDto(Long categoryId) {
        if (categoryId == null) {
            return null;
        }
        Category category = findById(categoryId);
        if (category == null) {
            return null;
        }
        if (category.getParentCategoryId() == null) {
            return null;
        }
        Category parentCategory = findById(category.getParentCategoryId());
        CategoryMiniDto categoryMiniDto = new CategoryMiniDto();
        if (parentCategory != null) {
            categoryMiniDto.setId(parentCategory.getId());
            categoryMiniDto.setName(parentCategory.getName());
        }
        return categoryMiniDto;
    }

    @CacheEvict(value = "category", key = "'categoryById' + #existingCategory.id")
    public void removeCategoryCache(Category existingCategory) {
        log.info("removing cache for key {}", existingCategory.getId());
        removeParentMiniDtoCache(existingCategory.getId());
    }

    @CacheEvict(value = "categoryMiniDto", key = "'parentOfCategoryById' + #categoryId")
    public void removeParentMiniDtoCache(Long categoryId) {
        log.info("removing cache for dto for category id {}", categoryId);
    }

    public Category findByIdUncached(Long id) {
        return super.findById(id);
    }
}
