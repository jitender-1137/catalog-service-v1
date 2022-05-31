package com.yaari.ms.catalogservice.domains;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "video")
@NoArgsConstructor
@Getter
@Setter
public class Video {
	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "title")
	private String title;

	@Column(name = "url")
	private String url;

	@Column(name = "view_count")
	private Long viewCount;

	@Column(name = "category")
	private String category;

	@Column(name = "precedence")
	private Long precedence;


}
