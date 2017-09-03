package br.com.julioces.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.julioces.api.ControllerAPI;
import br.com.julioces.api.Google;
import br.com.julioces.api.oauth.google.IGoogleWEBAPI;
import br.com.julioces.service.IGoogleService;

@Controller
@RequestMapping("/Google")
public class GoogleController implements ControllerAPI {

	private Logger logger = LoggerFactory.getLogger(GoogleController.class);

	private Google google;
	
	@Autowired
	private IGoogleService googleService;
	@Autowired
	private IGoogleWEBAPI googleWEBAPI;
	
	
	/**
	 * Funcao que recebe o 'code', gerado pela autorizacao do Google. A informacao e guardada no
	 * objeto Google e armazenada na sessao.
	 * 
	 */
	@RequestMapping(value="/callback",method=RequestMethod.GET)
	public String callback(
			@RequestParam(required=true) String code, 
			@RequestParam(required=false) String state, HttpSession httpSession)
	{
		google = (Google) httpSession.getAttribute("google");
		google.setCode(code);
		google.setState(state);
		return "redirect:/Google/getAccessToken";		
	}
	
	
	/**
	 * Metodo que gera o 'Access Token' , atualiza o objeto Google na sessão e redireciona a pagina inicial
	 * 
	 */
	@RequestMapping(value="/getAccessToken",method=RequestMethod.GET)
	public String getAccessToken(HttpSession httpSession)
	{
		google = (Google) httpSession.getAttribute("google");
		google.setGrant_type("authorization_code");
		try {
			google = googleService.ExchangeRefreshOrAccessToken(google);
		} catch (Exception e) {
			logger.error("Objeto GOOGLE não atualizado, detalhe: ",e);
		}
		httpSession.setAttribute("google", google);
		return "redirect:/";		
	}
	
	/**
	 * Metodo que atualiza o objeto Google para null e atualiza na sessao.
	 * 
	 */
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public String Logout(HttpSession httpSession)
	{
		
		httpSession.setAttribute("google", null);
		return "redirect:/";		
	}
	
	@ResponseBody
	@RequestMapping(value="/getuser",method=RequestMethod.GET)
	public Map<String,Object> getUser(Google google)
	{
		Map<String,Object> retorno = new HashMap<String,Object>();
		try {
			retorno.put("profile", googleWEBAPI.getProfile(google));
		} catch (Exception e) {
			retorno.put("error", e.getMessage());
		} 
		return retorno;
	}
}
