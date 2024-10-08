package io.ugochukwu.vulnerablevault.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import io.ugochukwu.vulnerablevault.entity.Role;
import io.ugochukwu.vulnerablevault.entity.User;

@Repository("userDao")
public class UserDAOImpl implements UserDAO {
	
	@Autowired
    private PasswordEncoder passwordEncoder;

	@Autowired
    private SessionFactory sessionFactory;
	
	protected Session getSession(){
        return sessionFactory.getCurrentSession();
    }

	public void updateUser(User user) {
		Session session = getSession();
		try {
			session.beginTransaction();
			session.update(user);
			session.getTransaction().commit();
		} finally {
			session.close();
		}
	}

	public int insertUser(User user) {
		Session session = getSession();
		int id;
		try {
			session.beginTransaction();
	        user.setPassword(passwordEncoder.encode(user.getPassword()));
			session.save(user);
			
			id = ((Integer) session.getIdentifier(user)).intValue();
			
			session.getTransaction().commit();
		} finally {
			session.close();
		}
		
		return id;
	}

	/*
	 * If the user exists in the database, this method returns the persistent user.
	 * Otherwise, it inserts a new user with the name and age provided as an
	 * argument.
	 */
	public User retrieveUser(String email) {
		Session session = getSession();
		try {
			// use the session object to check for a user
			// start a transaction
			session.beginTransaction();

			String hql = "from User where email = :email";
			Query<User> query = session.createQuery(hql);
			query.setParameter("email", email);
			List<User> users = query.getResultList();
			if (users.isEmpty()) {
				throw new RuntimeException(
						"Broken precondition: " + "credentials should be have been verified earlier");
			} else if (users.size() > 1) {
				throw new RuntimeException("More than one user exists");
			} else {
				return users.get(0);
			}
		} catch (Exception e) {
			System.out.println("exception::: " + e.getMessage());
			return null;
		} finally {
			session.close();
		}
	}

	public User retrieveUserByID(int id) {
		Session session = getSession();
		try {
			// use the session object to check for a user
			// start a transaction
			session.beginTransaction();

			// create the query
			return session.get(User.class, id);
		} finally {
			session.close();
		}
	}

	/*
	 * 
	 * Returns true if the user exists in the database, false otherwise.
	 */
	public boolean existsUser(String email) {
		Session session = getSession();
		try {
			session.beginTransaction();
			// create the query
			String hql = "from User where email = :email";
			Query<User> query = session.createQuery(hql);
			query.setParameter("email", email);
			// Obtain the query results
			List<User> users = query.getResultList();
			return !users.isEmpty();
		} finally {
			session.close();
		}
	}

	/*
	 * 
	 * Returns true if a user with the credentials passed as argument exists.
	 */
	public boolean areCredentialsCorrect(String email, String password) {
		Session session = getSession();
		try {
			session.beginTransaction();
//			String hql = "from User where email = :email and password = :password";
			String hql = "from User where email = '" + email + "' and password = '" + password + "'";
	        Query<User> query = session.createQuery(hql);
//			query.setParameter("email", email);
//			query.setParameter("password", password);
			// Obtain the query results
			List<User> users = query.getResultList();
			return !users.isEmpty();
		} finally {
			session.close();
		}
	}

	@Override
	protected void finalize() throws Throwable {
		// Close session factory before destroying the object
//		sessionFactory.close();
	}

}