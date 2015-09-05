package ie.dq.web.controller;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;

import ie.dq.web.common.model.UserUI;
import ie.dq.web.exception.UserFormException;
import ie.dq.web.facade.UserFacade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriTemplate;

@Controller
public class UserAdminController {
	
	private static final String USER_ADDED = "USER_ADDED";
	
	@Autowired
	private UserFacade userFacade;
	
	@RequestMapping(value = "/user-admin**", method = RequestMethod.GET)
	public ModelAndView userAdmin() {
		ModelAndView modelAndView = new ModelAndView("user-admin");
		modelAndView.addObject("allUsers", getAllUsers());
		return modelAndView;
	}
	
	/**
	 * Provide the details of a user with the given username in JSON.
	 */
	@RequestMapping(value = "/user-admin/{username}", method = RequestMethod.GET)
	public @ResponseBody UserUI userDetails(@PathVariable("username") String username) {
		return userFacade.getUserDetails(username);
	}
	
	@RequestMapping(value = "/user-admin/get-all-users", method = RequestMethod.GET)
	public @ResponseBody List<UserUI> getAllUsers() {
		return userFacade.getAllUsers();
	}
	
	/**
	 * Testing method not working
	 */
	/*@RequestMapping(value = "/test-create-user", method = RequestMethod.GET)
	public @ResponseBody UserUI testCreateUser() {
		
		RestTemplate template = new RestTemplate();
		UserUI user = new UserUI();
		user.setUsername("rester");
		user.setPassword("12345678");
		user.setForename("Rest");
		user.setSurname("Test");
		user.setEmail("rest@test.com");
		
		//return template.getForObject("http://localhost:8081/dq-web-ui/create-user", UserUI.class, user);
		
		//return template.postForObject("http://localhost:8081/dq-web-ui/create-user", user, UserUI.class);
		//URI newUserLocation = template.postForLocation("http://localhost:8081/dq-web-ui/create-user", user);
		
		return user;
	}*/

	/**
	 * Creates a new User, setting its URL as the Location header on the
	 * response. 2DO Not Working
	 */
	@RequestMapping(value = "/create-user", method = RequestMethod.POST, consumes = {"application/xml", "application/json"})
	@ResponseStatus(HttpStatus.CREATED)
	public HttpEntity<String> createUser(@RequestBody UserUI userUI,
			@Value("#{request.requestURL}") StringBuffer url) {
		
		String result = userFacade.addUser(userUI);
		System.out.println("createUser result = "+result);
		return entityWithLocation(url, userUI.getUsername());
	}
	
	//need to call from ajax or redesign form
	@RequestMapping(value = "/add-user**", method = RequestMethod.POST)
	public @ResponseBody UserUI addUser(@ModelAttribute(value="user")UserUI user, BindingResult result) {
	
		List<String> inputErrors = new LinkedList<String>();
		
		if(user.getUsername().isEmpty() || "".equals(user.getUsername())){
			inputErrors.add("You must supply a username");
		}
		
		if(!validPassword(user.getPassword())){
			inputErrors.add("Password must have more than 7 characters");
		}
		
		if(!validEmail(user.getEmail())){
			inputErrors.add(String.format("%s is not a valid email address", user.getEmail()));
		}
		
		if(user.getForename().isEmpty() || "".equals(user.getForename())){
			inputErrors.add("You must supply a forename");
		}
		
		if(user.getSurname().isEmpty() || "".equals(user.getSurname())){
			inputErrors.add("You must supply a surname");
		}
		
		if(!inputErrors.isEmpty()){
			//return USER_ERROR;
			throw new UserFormException(inputErrors);
		}
		
		if(USER_ADDED.equalsIgnoreCase(userFacade.addUser(user))){
			return user;
		}else{
			inputErrors.add("Error adding to database");
			throw new UserFormException(inputErrors);
		}
	}
	
	private boolean validEmail(String password){
		
		if(!password.isEmpty() && password.contains("@") && password.contains(".")){
			return true;
		}else{
			return false;
		}
	}
	
	private boolean validPassword(String password){
		
		if(!password.isEmpty() && password.length()>7){
			return true;
		}else{
			return false;
		}
	}
	
	private HttpEntity<String> entityWithLocation(StringBuffer url,
			Object resourceId) {
		// Configure and return an HttpEntity object - it will be used to build
		// the HttpServletResponse
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(getLocationForChildResource(url, resourceId));
		return new HttpEntity<String>(headers);
	}

	/**
	 * determines URL of child resource based on the full URL of the given
	 * request, appending the path info with the given childIdentifier using a
	 * UriTemplate.
	 */
	private URI getLocationForChildResource(StringBuffer url,
			Object childIdentifier) {
		UriTemplate template = new UriTemplate(url.append("/{childId}")
				.toString());
		return template.expand(childIdentifier);
	}
	
}
