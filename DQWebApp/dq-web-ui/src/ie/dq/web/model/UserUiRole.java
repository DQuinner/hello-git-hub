package ie.dq.web.model;

import org.springframework.security.core.GrantedAuthority;

public class UserUiRole implements GrantedAuthority{

	private static final long serialVersionUID = 1L;

	private String authority;
	
	public UserUiRole(String authority){
		this.authority = authority;
	}

	@Override
	public String getAuthority() {
		return this.authority;
	}

}
