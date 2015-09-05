package ie.dq.web.security;

import ie.dq.web.security.util.SecurityUtils;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {

	private SecurityUtils securityUtils;
	
	public void setSecurityUtils(SecurityUtils securityUtils) {
		this.securityUtils = securityUtils;
	}

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		
		String password = securityUtils.getUserPassword(username);
		if(password!=null){
			return new User(username, password, true, true, true, true, 
					securityUtils.getUserAuthorities(username));
		}else{
			throw new UsernameNotFoundException(username);
		}
	}	
}
