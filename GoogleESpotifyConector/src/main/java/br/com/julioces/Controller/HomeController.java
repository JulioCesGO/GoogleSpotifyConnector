package br.com.julioces.Controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.google.api.oauth2.UserInfo;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	private static String codeGoogle = null;
	
	@Autowired
	private IGoogleService googleService;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(@RequestParam(required=false,value="code") String code, Model model) {
		
		if (code!= null && !code.isEmpty())
		{
			model.addAttribute("code", code);
			codeGoogle = code;
		}
		
		return "home";
	}
	
	@RequestMapping(value = "/logarFacebook", method = RequestMethod.GET)
	public String logarFace(Model model)
	{
		
		return "login";
		
	}
	
	@RequestMapping(value = "/logarGoogle", method = RequestMethod.GET)
	public String logarGoogle(Model model)
	{
		
		
		return "redirect:" + googleService.authorizeURL();
		
	}
	
	@ResponseBody
	@RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
	public UserInfo getUserInfo(Model model)
	{
		/*
		GoogleConnectionFactory googleConnection =
		OAuth2Operations oauth2Operation = 
		OAuth2Parameters params = new OAuth2Parameters();
		params.setScope("profile");
		params.setRedirectUri("http://localhost:8080/julioces/");
		String authorizeURL = oauth2Operation.buildAuthenticateUrl(params);
		*/
		return googleService.getUserInfo(codeGoogle);
		
	}
	
	@RequestMapping(value = "/google", method = RequestMethod.GET)
	public String google()
	{
		return "google";
	}
}
