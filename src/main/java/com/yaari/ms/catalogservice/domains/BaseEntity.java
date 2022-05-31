package com.yaari.ms.catalogservice.domains;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {

	@Column(name = "created_at", nullable = false, updatable = false)
	private Long createdAt;

	@Column(name = "updated_at")
	private Long updatedAt;

	@Version
	@Column(name = "version")
	private Long version;

	@PrePersist
	private void prePersist() {
		this.updatedAt = System.currentTimeMillis();
		this.createdAt = System.currentTimeMillis();
		this.onCreate();
	}

	@PreUpdate
	private void preUpdate() {
		this.updatedAt = System.currentTimeMillis();
		this.onUpdate();
	}

	protected abstract void onCreate();
	protected abstract void onUpdate();
}
