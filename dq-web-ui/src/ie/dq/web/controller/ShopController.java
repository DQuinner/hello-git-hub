package ie.dq.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ShopController {
	
	@RequestMapping(value = "/shop**", method = RequestMethod.GET)
	public String shop(){
		return "shop";
	}
	
	@RequestMapping(value = "/cart**", method = RequestMethod.GET)
	public String cart(){
		return "cart";
	}
	
	@RequestMapping(value = "/checkout**", method = RequestMethod.GET)
	public String checkout(){
		return "checkout";
	}

}
