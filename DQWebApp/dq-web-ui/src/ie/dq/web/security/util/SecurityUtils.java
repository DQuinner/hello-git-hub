package ie.dq.web.security.util;

import ie.dq.dao.UserDao;
import ie.dq.dao.model.User;
import ie.dq.web.model.UserUiRole;

import java.util.LinkedList;
import java.util.List;

public class SecurityUtils {

	UserDao userDao;
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public String getUserPassword(String username){
		User user = userDao.getByUsername(username);
		if(user!=null){
			return user.getPassword();
		}else{
			return null;
		}
	}
	
	public List<UserUiRole> getUserAuthorities(String username){
		
		List<UserUiRole> authorities = new LinkedList();
				
		for(String authority : userDao.getUserAuthorities(username)){
			authorities.add(new UserUiRole(authority));
		}
		return authorities;
	}
	
}
