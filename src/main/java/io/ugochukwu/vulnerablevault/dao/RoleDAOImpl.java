package io.ugochukwu.vulnerablevault.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import io.ugochukwu.vulnerablevault.entity.Role;

@Repository("roleDao")
public class RoleDAOImpl implements RoleDAO {

	@Autowired
	private SessionFactory sessionFactory;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public Role retrieveCustomerRole() {
		Session session = getSession();
		try {
			session.beginTransaction();

			// create the query
			String hql = "from Role where name = 'Customer'";
			Query<Role> query = session.createQuery(hql);
			List<Role> roles = query.getResultList();
			if (roles.isEmpty()) {
				throw new RuntimeException("Broken precondition: " + "role should be have been created earlier");
			} else if (roles.size() > 1) {
				throw new RuntimeException("Roles must be unique");
			} else {
				return roles.get(0);
			}
		} finally {
			session.close();
		}
	}

	@Override
	public Role retrieveTellerRole() {
		Session session = getSession();
		try {
			session.beginTransaction();

			// create the query
			String hql = "from Role where name = 'Teller'";
			Query<Role> query = session.createQuery(hql);
			List<Role> roles = query.getResultList();
			if (roles.isEmpty()) {
				throw new RuntimeException("Broken precondition: " + "role should be have been created earlier");
			} else if (roles.size() > 1) {
				throw new RuntimeException("Roles must be unique");
			} else {
				return roles.get(0);
			}
		} finally {
			session.close();
		}
	}

	@Override
	public Role retrieveAdminRole() {
		Session session = getSession();
		try {
			session.beginTransaction();

			// create the query
			String hql = "from Role where name = 'Admin'";
			Query<Role> query = session.createQuery(hql);
			List<Role> roles = query.getResultList();
			if (roles.isEmpty()) {
				throw new RuntimeException("Broken precondition: " + "role should be have been created earlier");
			} else if (roles.size() > 1) {
				throw new RuntimeException("Roles must be unique");
			} else {
				return roles.get(0);
			}
		} finally {
			session.close();
		}
	}

	@Override
	public Role retrieveRoleById(int id) {
		Session session = getSession();
		try {
			session.beginTransaction();
			return session.get(Role.class, id);
		} finally {
			session.close();
		}
	}
}