package io.ugochukwu.vulnerablevault.dao;

import io.ugochukwu.vulnerablevault.entity.Role;

public interface RoleDAO {

	public Role retrieveCustomerRole();

	public Role retrieveTellerRole();

	public Role retrieveAdminRole();

	public Role retrieveRoleById(int id);

}