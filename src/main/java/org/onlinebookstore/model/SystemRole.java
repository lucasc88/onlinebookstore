package org.onlinebookstore.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class SystemRole {

	@Id
	private String name;

	public SystemRole() {
	}

	public SystemRole(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
