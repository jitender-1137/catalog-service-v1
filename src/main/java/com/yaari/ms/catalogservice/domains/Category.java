package com.yaari.ms.catalogservice.domains;


import com.vladmihalcea.hibernate.type.json.JsonType;
import com.yaari.ms.catalogservice.enums.CategoryStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

@Entity
@Table(name = "category")
@NoArgsConstructor
@Getter
@Setter
@TypeDef(
		name = "json",
		typeClass = JsonType.class
)
public class Category extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name", updatable = false)
	private String name;

	@Column(name = "parse_name")
	private String parseName;

	@Column(name = "parent_category_id")
	private Long parentCategoryId;

	@Column(name = "icon_image")
	private String homepageBannerImage;

	@Column(name = "bg_image")
	private String categoryPageBannerImage;

	@Column(name = "product_phrases")
	private String description;

	@Column(name = "priority")
	private Long priority;

	@Column(name = "slug")
	private String slug;

	@Column(name = "created_by_name")
	private String createdByName;

	@Column(name = "created_by_username")
	private String createdByUsername;

	@Column(name = "updated_by_name")
	private String updatedByName;

	@Column(name = "updated_by_username")
	private String updatedByUsername;

	@Column(name = "status")
	private String status;

	@Column(name = "checker_status")
//	@Enumerated(EnumType.STRING)
//	private ApproveReject checkerStatus;
	private String checkerStatus;

	@Column(name = "level")
	private String level;

	@Column(name = "reviewed_by")
	private String reviewedBy;

	@Column(name = "reviewed_at")
	private Long reviewedAt;

	@Column(name = "reject_reason")
	private String rejectReason;

	@Column(name = "template_file_url")
	private String templateFileUrl;

	@Type(type = "json")
	@Column(name = "draft", columnDefinition = "jsonb")
	private String draft;

	@Override
	protected void onCreate() {
		this.checkerStatus = CategoryStatus.DRAFT.name();
	}

	@Override
	protected void onUpdate() {

	}
}
