package br.com.julioces.Controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
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

import br.com.julioces.Service.IGoogleService;
import br.com.julioces.Service.IGooleService;
import br.com.julioces.Service.IService;
import br.com.julioces.Service.ISpotifyService;
import br.com.julioces.api.Google;
import br.com.julioces.api.Spotify;
import br.com.julioces.api.oauth.google.GoogleProfile;
import br.com.julioces.api.oauth.google.UserProfile;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	private static String codeGoogle = null;
	
	
	private static Google google;
	
	private static Spotify spotify;
	
	
	@Autowired
	private IGooleService googleService;
	
	@Autowired
	private ISpotifyService spotifyService;
	
	private GoogleProfile googleProfile = new GoogleProfile();
	
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
		
		
		return "redirect:" + googleService.GerarURLAuthenticacao(null);
		
	}
	
	@ResponseBody
	@RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
	public UserProfile getUserInfo(Model model)
	{
		
		return googleProfile.getProfile(google);
		
	}
	
	@RequestMapping(value = "/google", method = RequestMethod.GET)
	public String google()
	{
		return "google";
	}
}
