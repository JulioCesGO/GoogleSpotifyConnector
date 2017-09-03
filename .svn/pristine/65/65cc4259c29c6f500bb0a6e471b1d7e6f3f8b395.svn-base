package br.com.julioces.controller;

import java.io.IOException;
import java.net.MalformedURLException;
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
import br.com.julioces.api.Spotify;
import br.com.julioces.api.oauth.spotify.ISpotifyWEBAPI;
import br.com.julioces.service.ISpotifyService;

@Controller
@RequestMapping("/Spotify")
public class SpotifyController implements ControllerAPI{

	@Autowired
	private ISpotifyService spotifyService;
	@Autowired
	private ISpotifyWEBAPI spotifyWEBAPI;
	
	private Logger logger = LoggerFactory.getLogger(SpotifyController.class);
	
	private Spotify spotify;
	
	/**
	 * Funcao que recebe o 'code', gerado pela autorizacao do Spotify. A informacao e guardada no
	 * objeto Spotify e armazenada na sessao.
	 * 
	 */
	@RequestMapping(value="/callback",method=RequestMethod.GET)
	public String callback(
			@RequestParam(required=true) String code, 
			@RequestParam(required=false) String state, HttpSession httpSession)
	{
		spotify = (Spotify) httpSession.getAttribute("spotify");
		spotify.setCode(code);
		spotify.setState(state);
		return "redirect:/Spotify/getAccessToken";		
	}
	
	
	/**
	 * Metodo que gera o 'Access Token' , atualiza o objeto Spotify na sessão e redireciona a pagina inicial
	 * 
	 */
	@RequestMapping(value="/getAccessToken",method=RequestMethod.GET)
	public String getAccessToken(HttpSession httpSession)
	{
		spotify = (Spotify) httpSession.getAttribute("spotify");
		spotify.setGrant_type("authorization_code");
		try {
			spotify = spotifyService.ExchangeRefreshOrAccessToken(spotify);
		} catch (Exception e) {
			
			logger.info("Objeto SPOTIFY não atualizado, veja detalhe: ",e);
		}
		httpSession.setAttribute("spotify", spotify);
		return "redirect:/";		
	}
	
	/**
	 * Metodo que atualiza o objeto Spotify para null e atualiza na sessao.
	 * 
	 */
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public String Logout(HttpSession httpSession)
	{		
		httpSession.setAttribute("spotify", null);
		return "redirect:/";		
	}
	
	@ResponseBody
	@RequestMapping(value="/getCurrentUsersSavedTracks",method=RequestMethod.GET)
	public Map<String,Object> getCurrentUsersSavedTrack(
			@RequestParam(required=false) String urlnext, HttpSession httpSession)
	{
		spotify = (Spotify) httpSession.getAttribute("spotify");
		return spotifyWEBAPI.getCurrentUsersSavedTrack(spotify,urlnext);
		
	}
	
	@ResponseBody
	@RequestMapping(value="/getCurrentPlayingTrack",method=RequestMethod.GET)
	public Map<String,Object> getCurrentPlayingTrack(HttpSession httpSession)
	{
		spotify = (Spotify) httpSession.getAttribute("spotify");
		return spotifyWEBAPI.getCurrentPlayingTrack(spotify);
		
	}
	
	@ResponseBody
	@RequestMapping(value="/play",method=RequestMethod.GET)
	public Map<String,Object> putPlay(
			@RequestParam(required=false) String uri,
			HttpSession httpSession)
	{
		spotify = (Spotify) httpSession.getAttribute("spotify");
		return spotifyWEBAPI.putPlay(spotify, uri);		
		
		
	}
	//
	
}
