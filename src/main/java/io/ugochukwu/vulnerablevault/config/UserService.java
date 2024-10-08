package io.ugochukwu.vulnerablevault.config;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.ugochukwu.vulnerablevault.dao.RoleDAO;
import io.ugochukwu.vulnerablevault.dao.RoleDAOImpl;
import io.ugochukwu.vulnerablevault.dao.UserDAO;
import io.ugochukwu.vulnerablevault.dao.UserDAOImpl;
import io.ugochukwu.vulnerablevault.entity.Role;
import io.ugochukwu.vulnerablevault.entity.User;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserDAOImpl userDao;

	@Autowired
	private RoleDAOImpl roleDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.retrieveUser(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}

		Role userRole = roleDao.retrieveRoleById(user.getRoleID());

		System.out.println("Role is " + userRole.getName());
		UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				getRoles(userRole));
		return userDetails;
	}

	public Collection<? extends GrantedAuthority> getRoles(Role role) {
		return Collections.singletonList(new SimpleGrantedAuthority(role.getName().toUpperCase()));
	}
}