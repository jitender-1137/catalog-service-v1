package com.yaari.ms.catalogservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yaari.ms.catalogservice.data.co.CategoryCO;
import com.yaari.ms.catalogservice.data.co.CategoryLevel;
import com.yaari.ms.catalogservice.data.co.UpdateCategoryCO;
import com.yaari.ms.catalogservice.dto.CategoryDto;
import com.yaari.ms.catalogservice.enums.ActivityStatus;
import com.yaari.ms.catalogservice.services.CategoryServiceImpl;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CategoryServiceImpl categoryService;

	private static ObjectMapper mapper = new ObjectMapper();

	@Test
	@WithMockUser(roles = "ADD_CATEGORY")
	public void saveL1Category() throws Exception {
		CategoryCO categoryCO = new CategoryCO();
		categoryCO.setName("name");
		categoryCO.setHomepageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setDescription("Description");
		categoryCO.setCreatedByUsername("username");
		categoryCO.setLevel(CategoryLevel.L1.name());
		categoryCO.setStatus(ActivityStatus.INACTIVE.name());

		String json = mapper.writeValueAsString(categoryCO);

		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setName("name");
		Mockito.when(categoryService.addCategory(ArgumentMatchers.any())).thenReturn(categoryDto);

		mockMvc.perform(MockMvcRequestBuilders.post("/v1/categories")
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("utf-8")
						.content(json)
						.accept(MediaType.APPLICATION_JSON)
						.header("client-id", "node-supplier-admin-service"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.name", Matchers.equalTo("name")));
	}


	@Test
	@WithMockUser
	public void saveL1CategoryWithoutLevel() throws Exception {
		CategoryCO categoryCO = new CategoryCO();
		categoryCO.setName("name");
		categoryCO.setHomepageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setDescription("Description");
		categoryCO.setCreatedByUsername("username");
		categoryCO.setStatus(ActivityStatus.INACTIVE.name());

		String json = mapper.writeValueAsString(categoryCO);

		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setName("name");
		Mockito.when(categoryService.addCategory(ArgumentMatchers.any())).thenReturn(categoryDto);

		mockMvc.perform(MockMvcRequestBuilders.post("/v1/categories")
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("utf-8")
						.content(json)
						.accept(MediaType.APPLICATION_JSON)
						.header("client-id", "node-supplier-admin-service"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", containsInAnyOrder("level : Invalid level type")));
	}

	@Test
	@WithMockUser
	public void saveL1CategoryWithWrongLevel() throws Exception {
		CategoryCO categoryCO = new CategoryCO();
		categoryCO.setName("name");
		categoryCO.setHomepageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setDescription("Description");
		categoryCO.setCreatedByUsername("username");
		categoryCO.setLevel("L5");
		categoryCO.setStatus(ActivityStatus.INACTIVE.name());

		String json = mapper.writeValueAsString(categoryCO);

		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setName("name");
		Mockito.when(categoryService.addCategory(ArgumentMatchers.any())).thenReturn(categoryDto);

		mockMvc.perform(MockMvcRequestBuilders.post("/v1/categories")
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("utf-8")
						.content(json)
						.accept(MediaType.APPLICATION_JSON)
						.header("client-id", "node-supplier-admin-service"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", containsInAnyOrder("level : Invalid level type")));
	}

	@Test
	@WithMockUser
	public void saveL1CategoryWithWrongStatus() throws Exception {
		CategoryCO categoryCO = new CategoryCO();
		categoryCO.setName("name");
		categoryCO.setHomepageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setDescription("Description");
		categoryCO.setCreatedByUsername("username");
		categoryCO.setLevel(CategoryLevel.L1.name());
		categoryCO.setStatus("INACTIVEEE");

		String json = mapper.writeValueAsString(categoryCO);

		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setName("name");
		Mockito.when(categoryService.addCategory(ArgumentMatchers.any())).thenReturn(categoryDto);

		mockMvc.perform(MockMvcRequestBuilders.post("/v1/categories")
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("utf-8")
						.content(json)
						.accept(MediaType.APPLICATION_JSON)
						.header("client-id", "node-supplier-admin-service"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", containsInAnyOrder("status : Validation failed on activity status. Allowed values ACTIVE, INACTIVE")));
	}

	@Test
	@WithMockUser
	public void saveL1CategoryWithNullStatus() throws Exception {
		CategoryCO categoryCO = new CategoryCO();
		categoryCO.setName("name");
		categoryCO.setHomepageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setDescription("Description");
		categoryCO.setCreatedByUsername("username");
		categoryCO.setLevel(CategoryLevel.L1.name());

		String json = mapper.writeValueAsString(categoryCO);

		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setName("name");
		Mockito.when(categoryService.addCategory(ArgumentMatchers.any())).thenReturn(categoryDto);

		mockMvc.perform(MockMvcRequestBuilders.post("/v1/categories")
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("utf-8")
						.content(json)
						.accept(MediaType.APPLICATION_JSON)
						.header("client-id", "node-supplier-admin-service"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", containsInAnyOrder("status : Status can not be null or blank")));
	}

	@Test
	@WithMockUser
	public void saveL1CategoryFailWithNullName() throws Exception {
		CategoryCO categoryCO = new CategoryCO();
		categoryCO.setName(null);
		categoryCO.setHomepageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setDescription("Description");
		categoryCO.setCreatedByUsername("username");
		categoryCO.setLevel(CategoryLevel.L1.name());
		categoryCO.setStatus(ActivityStatus.INACTIVE.name());

		String json = mapper.writeValueAsString(categoryCO);

		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setName("name");
		Mockito.when(categoryService.addCategory(ArgumentMatchers.any())).thenReturn(categoryDto);

		mockMvc.perform(MockMvcRequestBuilders.post("/v1/categories")
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("utf-8")
						.content(json)
						.accept(MediaType.APPLICATION_JSON)
						.header("client-id", "node-supplier-admin-service"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", containsInAnyOrder("name : must not be blank")));
	}

	@Test
	@WithMockUser
	public void saveL1CategoryWithEmptyName() throws Exception {
		CategoryCO categoryCO = new CategoryCO();
		categoryCO.setName("   ");
		categoryCO.setHomepageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setDescription("Description");
		categoryCO.setCreatedByUsername("username");
		categoryCO.setLevel(CategoryLevel.L1.name());
		categoryCO.setStatus(ActivityStatus.INACTIVE.name());

		String json = mapper.writeValueAsString(categoryCO);

		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setName("name");
		Mockito.when(categoryService.addCategory(ArgumentMatchers.any())).thenReturn(categoryDto);

		mockMvc.perform(MockMvcRequestBuilders.post("/v1/categories")
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("utf-8")
						.content(json)
						.accept(MediaType.APPLICATION_JSON)
						.header("client-id", "node-supplier-admin-service"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", containsInAnyOrder("name : must not be blank")));
	}

	@Test
	@WithMockUser
	public void saveL1CategoryWithEmptyUserName() throws Exception {
		CategoryCO categoryCO = new CategoryCO();
		categoryCO.setName("name");
		categoryCO.setHomepageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setDescription("Description");
		categoryCO.setCreatedByUsername("  ");
		categoryCO.setLevel(CategoryLevel.L1.name());
		categoryCO.setStatus(ActivityStatus.INACTIVE.name());

		String json = mapper.writeValueAsString(categoryCO);

		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setName("name");
		Mockito.when(categoryService.addCategory(ArgumentMatchers.any())).thenReturn(categoryDto);

		mockMvc.perform(MockMvcRequestBuilders.post("/v1/categories")
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("utf-8")
						.content(json)
						.accept(MediaType.APPLICATION_JSON)
						.header("client-id", "node-supplier-admin-service"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", containsInAnyOrder("createdByUsername : must not be blank")));
	}

	@Test
	@WithMockUser
	public void saveL1CategoryFailWithWrongImage() throws Exception {
		CategoryCO categoryCO = new CategoryCO();
		categoryCO.setName("name");
		categoryCO.setHomepageBannerImage("image");
		categoryCO.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setDescription("Description");
		categoryCO.setCreatedByUsername("username");
		categoryCO.setLevel(CategoryLevel.L1.name());
		categoryCO.setStatus(ActivityStatus.INACTIVE.name());

		String json = mapper.writeValueAsString(categoryCO);

		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setName("name");
		Mockito.when(categoryService.addCategory(ArgumentMatchers.any())).thenReturn(categoryDto);

		mockMvc.perform(MockMvcRequestBuilders.post("/v1/categories")
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("utf-8")
						.content(json)
						.accept(MediaType.APPLICATION_JSON)
						.header("client-id", "node-supplier-admin-service"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", containsInAnyOrder("homepageBannerImage : Invalid url")));
	}

	@Test
	@WithMockUser
	public void saveL1CategoryFailWithTemplateUrl() throws Exception {
		CategoryCO categoryCO = new CategoryCO();
		categoryCO.setName("name");
		categoryCO.setHomepageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setDescription("Description");
		categoryCO.setCreatedByUsername("username");
		categoryCO.setLevel(CategoryLevel.L1.name());
		categoryCO.setTemplateFileUrl("http://localhost:8081/v1/image.jpg");
		categoryCO.setStatus(ActivityStatus.INACTIVE.name());

		String json = mapper.writeValueAsString(categoryCO);

		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setName("name");
		Mockito.when(categoryService.addCategory(ArgumentMatchers.any())).thenReturn(categoryDto);

		mockMvc.perform(MockMvcRequestBuilders.post("/v1/categories")
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("utf-8")
						.content(json)
						.accept(MediaType.APPLICATION_JSON)
						.header("client-id", "node-supplier-admin-service"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", containsInAnyOrder("templateFileUrl : File template can not be uploaded for L1 and L2 category")));
	}

	@Test
	@WithMockUser(roles = "ADD_CATEGORY")
	public void saveL2Category() throws Exception {
		CategoryCO categoryCO = new CategoryCO();
		categoryCO.setName("name");
		categoryCO.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setParentCategoryId(1l);
		categoryCO.setDescription("Description");
		categoryCO.setCreatedByUsername("username");
		categoryCO.setLevel(CategoryLevel.L2.name());
		categoryCO.setStatus(ActivityStatus.INACTIVE.name());

		String json = mapper.writeValueAsString(categoryCO);

		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setName("name");
		Mockito.when(categoryService.addCategory(ArgumentMatchers.any())).thenReturn(categoryDto);

		mockMvc.perform(MockMvcRequestBuilders.post("/v1/categories")
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("utf-8")
						.content(json)
						.accept(MediaType.APPLICATION_JSON)
						.header("client-id", "node-supplier-admin-service"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.name", Matchers.equalTo("name")));
	}

	@Test
	@WithMockUser
	public void saveL2CategoryWithNullName() throws Exception {
		CategoryCO categoryCO = new CategoryCO();
		categoryCO.setName(null);
		categoryCO.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setParentCategoryId(1l);
		categoryCO.setDescription("Description");
		categoryCO.setCreatedByUsername("username");
		categoryCO.setLevel(CategoryLevel.L2.name());
		categoryCO.setStatus(ActivityStatus.INACTIVE.name());

		String json = mapper.writeValueAsString(categoryCO);

		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setName("name");
		Mockito.when(categoryService.addCategory(ArgumentMatchers.any())).thenReturn(categoryDto);

		mockMvc.perform(MockMvcRequestBuilders.post("/v1/categories")
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("utf-8")
						.content(json)
						.accept(MediaType.APPLICATION_JSON)
						.header("client-id", "node-supplier-admin-service"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", containsInAnyOrder("name : must not be blank")));
	}

	@Test
	@WithMockUser
	public void saveL2CategoryWithEmptyName() throws Exception {
		CategoryCO categoryCO = new CategoryCO();
		categoryCO.setName("  ");
		categoryCO.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setParentCategoryId(1l);
		categoryCO.setDescription("Description");
		categoryCO.setCreatedByUsername("username");
		categoryCO.setLevel(CategoryLevel.L2.name());
		categoryCO.setStatus(ActivityStatus.INACTIVE.name());

		String json = mapper.writeValueAsString(categoryCO);

		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setName("name");
		Mockito.when(categoryService.addCategory(ArgumentMatchers.any())).thenReturn(categoryDto);

		mockMvc.perform(MockMvcRequestBuilders.post("/v1/categories")
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("utf-8")
						.content(json)
						.accept(MediaType.APPLICATION_JSON)
						.header("client-id", "node-supplier-admin-service"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", containsInAnyOrder("name : must not be blank")));
	}

	@Test
	@WithMockUser
	public void saveL2CategoryWithNullCategoryPageBannerImage() throws Exception {
		CategoryCO categoryCO = new CategoryCO();
		categoryCO.setName("name");
		categoryCO.setCategoryPageBannerImage(null);
		categoryCO.setParentCategoryId(1l);
		categoryCO.setDescription("Description");
		categoryCO.setCreatedByUsername("username");
		categoryCO.setLevel(CategoryLevel.L2.name());
		categoryCO.setStatus(ActivityStatus.INACTIVE.name());

		String json = mapper.writeValueAsString(categoryCO);

		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setName("name");
		Mockito.when(categoryService.addCategory(ArgumentMatchers.any())).thenReturn(categoryDto);

		mockMvc.perform(MockMvcRequestBuilders.post("/v1/categories")
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("utf-8")
						.content(json)
						.accept(MediaType.APPLICATION_JSON)
						.header("client-id", "node-supplier-admin-service"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", containsInAnyOrder("categoryPageBannerImage : must not be blank")));
	}

	@Test
	@WithMockUser
	public void saveL2CategoryWithWrongUrlCategoryPageBannerImage() throws Exception {
		CategoryCO categoryCO = new CategoryCO();
		categoryCO.setName("name");
		categoryCO.setCategoryPageBannerImage("ht://localhost:8081/v1/image.jpg");
		categoryCO.setParentCategoryId(1l);
		categoryCO.setDescription("Description");
		categoryCO.setCreatedByUsername("username");
		categoryCO.setLevel(CategoryLevel.L2.name());
		categoryCO.setStatus(ActivityStatus.INACTIVE.name());

		String json = mapper.writeValueAsString(categoryCO);

		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setName("name");
		Mockito.when(categoryService.addCategory(ArgumentMatchers.any())).thenReturn(categoryDto);

		mockMvc.perform(MockMvcRequestBuilders.post("/v1/categories")
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("utf-8")
						.content(json)
						.accept(MediaType.APPLICATION_JSON)
						.header("client-id", "node-supplier-admin-service"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", containsInAnyOrder("categoryPageBannerImage : Invalid url")));
	}

	@Test
	@WithMockUser
	public void saveL2CategoryWithoutPatentCategoryId() throws Exception {
		CategoryCO categoryCO = new CategoryCO();
		categoryCO.setName("name");
		categoryCO.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setParentCategoryId(null);
		categoryCO.setDescription("Description");
		categoryCO.setCreatedByUsername("username");
		categoryCO.setLevel(CategoryLevel.L2.name());
		categoryCO.setStatus(ActivityStatus.INACTIVE.name());

		String json = mapper.writeValueAsString(categoryCO);

		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setName("name");
		Mockito.when(categoryService.addCategory(ArgumentMatchers.any())).thenReturn(categoryDto);

		mockMvc.perform(MockMvcRequestBuilders.post("/v1/categories")
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("utf-8")
						.content(json)
						.accept(MediaType.APPLICATION_JSON)
						.header("client-id", "node-supplier-admin-service"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", containsInAnyOrder("parentCategoryId : L2 and L3 category can not have null parent id")));
	}

	@Test
	@WithMockUser
	public void saveL2CategoryWithEmptyCreatedByUsername() throws Exception {
		CategoryCO categoryCO = new CategoryCO();
		categoryCO.setName("name");
		categoryCO.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setParentCategoryId(2l);
		categoryCO.setDescription("Description");
		categoryCO.setCreatedByUsername(" ");
		categoryCO.setLevel(CategoryLevel.L2.name());
		categoryCO.setStatus(ActivityStatus.INACTIVE.name());

		String json = mapper.writeValueAsString(categoryCO);

		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setName("name");
		Mockito.when(categoryService.addCategory(ArgumentMatchers.any())).thenReturn(categoryDto);

		mockMvc.perform(MockMvcRequestBuilders.post("/v1/categories")
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("utf-8")
						.content(json)
						.accept(MediaType.APPLICATION_JSON)
						.header("client-id", "node-supplier-admin-service"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", containsInAnyOrder("createdByUsername : must not be blank")));
	}

	@Test
	@WithMockUser
	public void saveL2CategoryWithTemplateFileUrl() throws Exception {
		CategoryCO categoryCO = new CategoryCO();
		categoryCO.setName("name");
		categoryCO.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setParentCategoryId(2l);
		categoryCO.setDescription("Description");
		categoryCO.setCreatedByUsername("Username");
		categoryCO.setTemplateFileUrl("http://template.com");
		categoryCO.setLevel(CategoryLevel.L2.name());
		categoryCO.setStatus(ActivityStatus.INACTIVE.name());

		String json = mapper.writeValueAsString(categoryCO);

		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setName("name");
		Mockito.when(categoryService.addCategory(ArgumentMatchers.any())).thenReturn(categoryDto);

		mockMvc.perform(MockMvcRequestBuilders.post("/v1/categories")
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("utf-8")
						.content(json)
						.accept(MediaType.APPLICATION_JSON)
						.header("client-id", "node-supplier-admin-service"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", containsInAnyOrder("templateFileUrl : File template can not be uploaded for L1 and L2 category")));
	}

	@Test
	@WithMockUser
	public void saveL2CategoryWithWrongStatus() throws Exception {
		CategoryCO categoryCO = new CategoryCO();
		categoryCO.setName("name");
		categoryCO.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setParentCategoryId(2l);
		categoryCO.setDescription("Description");
		categoryCO.setCreatedByUsername("Username");
		categoryCO.setTemplateFileUrl("http://template.com");
		categoryCO.setLevel(CategoryLevel.L2.name());
		categoryCO.setStatus("active");

		String json = mapper.writeValueAsString(categoryCO);

		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setName("name");
		Mockito.when(categoryService.addCategory(ArgumentMatchers.any())).thenReturn(categoryDto);

		mockMvc.perform(MockMvcRequestBuilders.post("/v1/categories")
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("utf-8")
						.content(json)
						.accept(MediaType.APPLICATION_JSON)
						.header("client-id", "node-supplier-admin-service"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", containsInAnyOrder("status : Validation failed on activity status. Allowed values ACTIVE, INACTIVE")));
	}

	@Test
	@WithMockUser
	public void saveL2CategoryWithHomepageBannerImage() throws Exception {
		CategoryCO categoryCO = new CategoryCO();
		categoryCO.setName("name");
		categoryCO.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setHomepageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setParentCategoryId(2l);
		categoryCO.setDescription("Description");
		categoryCO.setCreatedByUsername("username");
		categoryCO.setLevel(CategoryLevel.L2.name());
		categoryCO.setStatus(ActivityStatus.INACTIVE.name());

		String json = mapper.writeValueAsString(categoryCO);

		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setName("name");
		Mockito.when(categoryService.addCategory(ArgumentMatchers.any())).thenReturn(categoryDto);

		mockMvc.perform(MockMvcRequestBuilders.post("/v1/categories")
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("utf-8")
						.content(json)
						.accept(MediaType.APPLICATION_JSON)
						.header("client-id", "node-supplier-admin-service"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", containsInAnyOrder("homepageBannerImage : HomepageBannerImage can not upload for L2 and L3")));
	}

	@Test
	@WithMockUser
	public void saveL2CategoryWithNullLevel() throws Exception {
		CategoryCO categoryCO = new CategoryCO();
		categoryCO.setName("name");
		categoryCO.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setParentCategoryId(2l);
		categoryCO.setDescription("Description");
		categoryCO.setCreatedByUsername("username");
		categoryCO.setLevel(null);
		categoryCO.setStatus(ActivityStatus.INACTIVE.name());

		String json = mapper.writeValueAsString(categoryCO);

		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setName("name");
		Mockito.when(categoryService.addCategory(ArgumentMatchers.any())).thenReturn(categoryDto);

		mockMvc.perform(MockMvcRequestBuilders.post("/v1/categories")
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("utf-8")
						.content(json)
						.accept(MediaType.APPLICATION_JSON)
						.header("client-id", "node-supplier-admin-service"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", containsInAnyOrder("level : Invalid level type")));
	}

	@Test
	@WithMockUser(roles = "ADD_CATEGORY")
	public void saveL3Category() throws Exception {
		CategoryCO categoryCO = new CategoryCO();
		categoryCO.setName("name");
		categoryCO.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setParentCategoryId(1l);
		categoryCO.setDescription("Description");
		categoryCO.setCreatedByUsername("username");
		categoryCO.setLevel(CategoryLevel.L3.name());
		categoryCO.setTemplateFileUrl("http://localhost:8081/v1/image.jpg");
		categoryCO.setStatus(ActivityStatus.INACTIVE.name());

		String json = mapper.writeValueAsString(categoryCO);

		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setName("name");
		Mockito.when(categoryService.addCategory(ArgumentMatchers.any())).thenReturn(categoryDto);

		mockMvc.perform(MockMvcRequestBuilders.post("/v1/categories")
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("utf-8")
						.content(json)
						.accept(MediaType.APPLICATION_JSON)
						.header("client-id", "node-supplier-admin-service"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.name", Matchers.equalTo("name")));
	}

	@Test
	@WithMockUser
	public void saveL3CategoryWithoutParentId() throws Exception {
		CategoryCO categoryCO = new CategoryCO();
		categoryCO.setName("name");
		categoryCO.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setParentCategoryId(null);
		categoryCO.setDescription("Description");
		categoryCO.setCreatedByUsername("username");
		categoryCO.setLevel(CategoryLevel.L3.name());
		categoryCO.setTemplateFileUrl("http://localhost:8081/v1/image.jpg");
		categoryCO.setStatus(ActivityStatus.INACTIVE.name());

		String json = mapper.writeValueAsString(categoryCO);

		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setName("name");
		Mockito.when(categoryService.addCategory(ArgumentMatchers.any())).thenReturn(categoryDto);

		mockMvc.perform(MockMvcRequestBuilders.post("/v1/categories")
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("utf-8")
						.content(json)
						.accept(MediaType.APPLICATION_JSON)
						.header("client-id", "node-supplier-admin-service"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", containsInAnyOrder("parentCategoryId : L2 and L3 category can not have null parent id")));
	}


	@Test
	@WithMockUser
	public void saveL3CategoryWithHomepageBannerImage() throws Exception {
		CategoryCO categoryCO = new CategoryCO();
		categoryCO.setName("name");
		categoryCO.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setHomepageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setParentCategoryId(2l);
		categoryCO.setDescription("Description");
		categoryCO.setCreatedByUsername("username");
		categoryCO.setLevel(CategoryLevel.L3.name());
		categoryCO.setTemplateFileUrl("http://localhost:8081/v1/image.jpg");
		categoryCO.setStatus(ActivityStatus.INACTIVE.name());

		String json = mapper.writeValueAsString(categoryCO);

		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setName("name");
		Mockito.when(categoryService.addCategory(ArgumentMatchers.any())).thenReturn(categoryDto);

		mockMvc.perform(MockMvcRequestBuilders.post("/v1/categories")
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("utf-8")
						.content(json)
						.accept(MediaType.APPLICATION_JSON)
						.header("client-id", "node-supplier-admin-service"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", containsInAnyOrder("homepageBannerImage : HomepageBannerImage can not upload for L2 and L3")));
	}

	@Test
	@WithMockUser
	public void saveL3CategoryWithoutTemplateUrl() throws Exception {
		CategoryCO categoryCO = new CategoryCO();
		categoryCO.setName("name");
		categoryCO.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setParentCategoryId(1l);
		categoryCO.setDescription("Description");
		categoryCO.setCreatedByUsername("username");
		categoryCO.setLevel(CategoryLevel.L3.name());
		categoryCO.setStatus(ActivityStatus.INACTIVE.name());

		String json = mapper.writeValueAsString(categoryCO);

		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setName("name");
		Mockito.when(categoryService.addCategory(ArgumentMatchers.any())).thenReturn(categoryDto);

		mockMvc.perform(MockMvcRequestBuilders.post("/v1/categories")
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("utf-8")
						.content(json)
						.accept(MediaType.APPLICATION_JSON)
						.header("client-id", "node-supplier-admin-service"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", containsInAnyOrder("templateFileUrl : L3 category has template file url mandatory")));
	}

	@Test
	@WithMockUser
	public void saveL3CategoryWithoutCategoryPageBannerImage() throws Exception {
		CategoryCO categoryCO = new CategoryCO();
		categoryCO.setName("name");
		categoryCO.setCategoryPageBannerImage(null);
		categoryCO.setParentCategoryId(2l);
		categoryCO.setDescription("Description");
		categoryCO.setCreatedByUsername("username");
		categoryCO.setLevel(CategoryLevel.L3.name());
		categoryCO.setTemplateFileUrl("http://localhost:8081/v1/image.jpg");
		categoryCO.setStatus(ActivityStatus.INACTIVE.name());

		String json = mapper.writeValueAsString(categoryCO);

		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setName("name");
		Mockito.when(categoryService.addCategory(ArgumentMatchers.any())).thenReturn(categoryDto);

		mockMvc.perform(MockMvcRequestBuilders.post("/v1/categories")
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("utf-8")
						.content(json)
						.accept(MediaType.APPLICATION_JSON)
						.header("client-id", "node-supplier-admin-service"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", containsInAnyOrder("categoryPageBannerImage : must not be blank")));
	}

	@Test
	@WithMockUser
	public void saveL3CategoryWithWrongCategoryPageBannerImageUrl() throws Exception {
		CategoryCO categoryCO = new CategoryCO();
		categoryCO.setName("name");
		categoryCO.setCategoryPageBannerImage("ht://localhost:8081/v1/image.jpg");
		categoryCO.setParentCategoryId(2l);
		categoryCO.setDescription("Description");
		categoryCO.setCreatedByUsername("username");
		categoryCO.setLevel(CategoryLevel.L3.name());
		categoryCO.setTemplateFileUrl("http://localhost:8081/v1/image.jpg");
		categoryCO.setStatus(ActivityStatus.INACTIVE.name());

		String json = mapper.writeValueAsString(categoryCO);

		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setName("name");
		Mockito.when(categoryService.addCategory(ArgumentMatchers.any())).thenReturn(categoryDto);

		mockMvc.perform(MockMvcRequestBuilders.post("/v1/categories")
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("utf-8")
						.content(json)
						.accept(MediaType.APPLICATION_JSON)
						.header("client-id", "node-supplier-admin-service"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", containsInAnyOrder("categoryPageBannerImage : Invalid url")));
	}

	@Test
	@WithMockUser
	public void saveL3CategoryWithNullName() throws Exception {
		CategoryCO categoryCO = new CategoryCO();
		categoryCO.setName(null);
		categoryCO.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setParentCategoryId(2l);
		categoryCO.setDescription("Description");
		categoryCO.setCreatedByUsername("username");
		categoryCO.setLevel(CategoryLevel.L3.name());
		categoryCO.setTemplateFileUrl("http://localhost:8081/v1/image.jpg");
		categoryCO.setStatus(ActivityStatus.INACTIVE.name());

		String json = mapper.writeValueAsString(categoryCO);

		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setName("name");
		Mockito.when(categoryService.addCategory(ArgumentMatchers.any())).thenReturn(categoryDto);

		mockMvc.perform(MockMvcRequestBuilders.post("/v1/categories")
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("utf-8")
						.content(json)
						.accept(MediaType.APPLICATION_JSON)
						.header("client-id", "node-supplier-admin-service"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", containsInAnyOrder("name : must not be blank")));
	}

	@Test
	@WithMockUser
	public void saveL3CategoryWithEmptyName() throws Exception {
		CategoryCO categoryCO = new CategoryCO();
		categoryCO.setName("  ");
		categoryCO.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setParentCategoryId(2l);
		categoryCO.setDescription("Description");
		categoryCO.setCreatedByUsername("username");
		categoryCO.setLevel(CategoryLevel.L3.name());
		categoryCO.setTemplateFileUrl("http://localhost:8081/v1/image.jpg");
		categoryCO.setStatus(ActivityStatus.INACTIVE.name());

		String json = mapper.writeValueAsString(categoryCO);

		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setName("name");
		Mockito.when(categoryService.addCategory(ArgumentMatchers.any())).thenReturn(categoryDto);

		mockMvc.perform(MockMvcRequestBuilders.post("/v1/categories")
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("utf-8")
						.content(json)
						.accept(MediaType.APPLICATION_JSON)
						.header("client-id", "node-supplier-admin-service"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", containsInAnyOrder("name : must not be blank")));
	}

	@Test
	@WithMockUser
	public void saveL3CategoryWithoutCreatedByUsername() throws Exception {
		CategoryCO categoryCO = new CategoryCO();
		categoryCO.setName("name");
		categoryCO.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setParentCategoryId(2l);
		categoryCO.setDescription("Description");
		categoryCO.setCreatedByUsername(null);
		categoryCO.setLevel(CategoryLevel.L3.name());
		categoryCO.setTemplateFileUrl("http://localhost:8081/v1/image.jpg");
		categoryCO.setStatus(ActivityStatus.INACTIVE.name());

		String json = mapper.writeValueAsString(categoryCO);

		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setName("name");
		Mockito.when(categoryService.addCategory(ArgumentMatchers.any())).thenReturn(categoryDto);

		mockMvc.perform(MockMvcRequestBuilders.post("/v1/categories")
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("utf-8")
						.content(json)
						.accept(MediaType.APPLICATION_JSON)
						.header("client-id", "node-supplier-admin-service"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", containsInAnyOrder("createdByUsername : must not be blank")));
	}

	@Test
	@WithMockUser
	public void saveL3CategoryWithoutLevel() throws Exception {
		CategoryCO categoryCO = new CategoryCO();
		categoryCO.setName("name");
		categoryCO.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setParentCategoryId(2l);
		categoryCO.setDescription("Description");
		categoryCO.setCreatedByUsername("username");
		categoryCO.setLevel(null);
		categoryCO.setTemplateFileUrl("http://localhost:8081/v1/image.jpg");
		categoryCO.setStatus(ActivityStatus.INACTIVE.name());

		String json = mapper.writeValueAsString(categoryCO);

		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setName("name");
		Mockito.when(categoryService.addCategory(ArgumentMatchers.any())).thenReturn(categoryDto);

		mockMvc.perform(MockMvcRequestBuilders.post("/v1/categories")
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("utf-8")
						.content(json)
						.accept(MediaType.APPLICATION_JSON)
						.header("client-id", "node-supplier-admin-service"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", containsInAnyOrder("level : Invalid level type")));
	}

	@Test
	@WithMockUser
	public void saveL3CategoryWithWrongTemplateFileUrl() throws Exception {
		CategoryCO categoryCO = new CategoryCO();
		categoryCO.setName("name");
		categoryCO.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setParentCategoryId(2l);
		categoryCO.setDescription("Description");
		categoryCO.setCreatedByUsername("username");
		categoryCO.setLevel(CategoryLevel.L3.name());
		categoryCO.setTemplateFileUrl("ht://localhost:8081/v1/image.jpg");
		categoryCO.setStatus(ActivityStatus.INACTIVE.name());

		String json = mapper.writeValueAsString(categoryCO);

		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setName("name");
		Mockito.when(categoryService.addCategory(ArgumentMatchers.any())).thenReturn(categoryDto);

		mockMvc.perform(MockMvcRequestBuilders.post("/v1/categories")
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("utf-8")
						.content(json)
						.accept(MediaType.APPLICATION_JSON)
						.header("client-id", "node-supplier-admin-service"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", containsInAnyOrder("templateFileUrl : Template url is not in valid format")));
	}

	@Test
	@WithMockUser
	public void saveL3CategoryWithoutStatus() throws Exception {
		CategoryCO categoryCO = new CategoryCO();
		categoryCO.setName("name");
		categoryCO.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setParentCategoryId(null);
		categoryCO.setDescription("Description");
		categoryCO.setCreatedByUsername("username");
		categoryCO.setLevel(CategoryLevel.L3.name());
		categoryCO.setTemplateFileUrl("http://localhost:8081/v1/image.jpg");
		categoryCO.setStatus(null);

		String json = mapper.writeValueAsString(categoryCO);

		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setName("name");
		Mockito.when(categoryService.addCategory(ArgumentMatchers.any())).thenReturn(categoryDto);

		mockMvc.perform(MockMvcRequestBuilders.post("/v1/categories")
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("utf-8")
						.content(json)
						.accept(MediaType.APPLICATION_JSON)
						.header("client-id", "node-supplier-admin-service"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", containsInAnyOrder("status : Status can not be null or blank")));
	}

	@Test
	@WithMockUser
	public void saveL3CategoryWithWrongStatus() throws Exception {
		CategoryCO categoryCO = new CategoryCO();
		categoryCO.setName("name");
		categoryCO.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setParentCategoryId(2l);
		categoryCO.setDescription("Description");
		categoryCO.setCreatedByUsername("username");
		categoryCO.setLevel(CategoryLevel.L3.name());
		categoryCO.setTemplateFileUrl("http://localhost:8081/v1/image.jpg");
		categoryCO.setStatus("inactive");

		String json = mapper.writeValueAsString(categoryCO);

		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setName("name");
		Mockito.when(categoryService.addCategory(ArgumentMatchers.any())).thenReturn(categoryDto);

		mockMvc.perform(MockMvcRequestBuilders.post("/v1/categories")
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("utf-8")
						.content(json)
						.accept(MediaType.APPLICATION_JSON)
						.header("client-id", "node-supplier-admin-service"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", containsInAnyOrder("status : Validation failed on activity status. Allowed values ACTIVE, INACTIVE")));
	}


	@Test
	@WithMockUser(roles = "EDIT_CATEGORY")
	public void testUpdateSuccess() throws Exception {
		UpdateCategoryCO categoryCO = new UpdateCategoryCO();
		categoryCO.setHomepageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setCategoryPageBannerImage("http://localhost:8081/v1/image.jpg");
		categoryCO.setDescription("Description");
		categoryCO.setUpdatedByUsername("username");
		categoryCO.setStatus(ActivityStatus.ACTIVE.name());

		String json = mapper.writeValueAsString(categoryCO);

		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setName("name");
		categoryDto.setHomepageBannerImage("http://localhost:8081/v1/image.jpg");
		Mockito.when(categoryService.updateCategory(Mockito.any(), Mockito.any())).thenReturn(categoryDto);

		mockMvc.perform(MockMvcRequestBuilders.put("/v1/categories/1")
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("utf-8")
						.content(json)
						.accept(MediaType.APPLICATION_JSON)
						.header("client-id", "node-supplier-admin-service"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.name", Matchers.equalTo("name")));
	}

	@Test
	@WithMockUser
	public void testUpdateWithWrongCategoryPageBannerImage() throws Exception {
		UpdateCategoryCO categoryCO = new UpdateCategoryCO();
		categoryCO.setHomepageBannerImage("htt://localhost:8081/v1/image.jpg");
		categoryCO.setCategoryPageBannerImage("htt://localhost:8081/v1/image.jpg");
		categoryCO.setDescription("Description");
		categoryCO.setUpdatedByUsername(null);
		String json = mapper.writeValueAsString(categoryCO);

		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setName("name");
		categoryDto.setHomepageBannerImage("http://localhost:8081/v1/image.jpg");
		Mockito.when(categoryService.updateCategory(Mockito.any(), Mockito.any())).thenReturn(categoryDto);

		mockMvc.perform(MockMvcRequestBuilders.put("/v1/categories/1")
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("utf-8")
						.content(json)
						.accept(MediaType.APPLICATION_JSON)
						.header("client-id", "node-supplier-admin-service"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", containsInAnyOrder("updatedByUsername : must not be blank",
						"categoryPageBannerImage : Invalid url",
						"status : must not be blank",
						"homepageBannerImage : Invalid url")));
	}
}
