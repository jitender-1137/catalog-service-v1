package com.yaari.ms.catalogservice.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yaari.ms.catalogservice.dto.CategoryDto;

@JsonIgnoreProperties({"reviewedAt",})
public class CategoryResponse extends CategoryDto {
}
