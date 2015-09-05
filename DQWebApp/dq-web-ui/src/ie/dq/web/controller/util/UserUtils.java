/**
 * 
 */
package ie.dq.web.controller.util;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ie.dq.dao.UserDao;
import ie.dq.dao.model.User;
import ie.dq.web.model.UserUI;

/**
 * @author Donal
 *
 */
@Component
public class UserUtils {
	
	@Autowired
	private UserDao userDao;
	
	public String addUser(UserUI userUI){
		
		return userDao.addUser(mapToDao(userUI));
		
	}
	
	public UserUI getUserDetails(String userName){
		
		User user = userDao.getByUsername(userName);
		
		if(user!=null){
			return mapToUI(user);
		}else{
			return null;
		}
	}
	
	public List<UserUI> getAllUsers(){
		
		List<UserUI> allUsers = new LinkedList();
		
		for(User user : userDao.getUsers()){
			allUsers.add(mapToUI(user));
		}
		return allUsers;
	}
	
	private User mapToDao(UserUI userUI){
		User user = new User();
		user.setUsername(userUI.getUsername());
		user.setPassword(userUI.getPassword());
		user.setEmail(userUI.getEmail());
		user.setForename(userUI.getForename());
		user.setSurname(userUI.getSurname());
		return user;
	}
	
	private UserUI mapToUI(User user){
		UserUI userUI = new UserUI();
		userUI.setUsername(user.getUsername());
		userUI.setPassword(user.getPassword());
		userUI.setEmail(user.getEmail());
		userUI.setForename(user.getForename());
		userUI.setSurname(user.getSurname());
		return userUI;
	}
}
