package io.ugochukwu.vulnerablevault.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Roles")
public class Role {

	public static final String ADMIN_ROLE = "ADMIN";
	public static final String TELLER_ROLE = "TELLER";
	public static final String CUSTOMER_ROLE = "CUSTOMER";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "RoleID")
	protected int id;

	@Column(name = "RoleName")
	protected String name;

	public Role() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "role = [id: " + getId() + ", name: " + getName() + "]";
	}
}
