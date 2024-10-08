package io.ugochukwu.vulnerablevault.dao;

import io.ugochukwu.vulnerablevault.entity.User;

public interface UserDAO {
	
	public void updateUser(User user);

	public int insertUser(User user);

	public boolean areCredentialsCorrect(String username, String password);

	public boolean existsUser(String username);
	
	public User retrieveUser(String username);

	public User retrieveUserByID(int userId);

}