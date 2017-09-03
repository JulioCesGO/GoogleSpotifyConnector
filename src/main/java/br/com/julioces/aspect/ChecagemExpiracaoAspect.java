package br.com.julioces.aspect;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.julioces.api.Google;
import br.com.julioces.api.Spotify;
import br.com.julioces.service.IGoogleService;
import br.com.julioces.service.ISpotifyService;

@Aspect
@Component
public class ChecagemExpiracaoAspect {
	
	@Autowired
	private IGoogleService googleService;
	@Autowired
	private ISpotifyService spotifyService;
	@Autowired
	private HttpSession httpSession;

	
	private static Logger logger = LoggerFactory.getLogger(ChecagemExpiracaoAspect.class);
	
	
	@Around("execution(* br.com.julioces.api.oauth.google.GoogleWEBAPI.*(..)) && args(google,..)")
	public Object checarGoogle(ProceedingJoinPoint joinpoint,Google google) throws Throwable
	{
		Google google1 =google;
		if (google1.getExpire_in().after(new Date()) )
		{
			logger.info("REVALIDAR CHAVE TOKEN DA API DO GOOGLE");
			google1.setGrant_type("refresh_token");
			google1 = googleService.ExchangeRefreshOrAccessToken(google1);
			
			httpSession.setAttribute("session", google1);
			
			return joinpoint.proceed(new Object[] { google1 });
		} 
		else
		{
			logger.info("AOP: EXECUTAR METODO");
			
		}
		
		
		return joinpoint.proceed();
	}
	
	@Around("execution(* br.com.julioces.api.oauth.spotify.SpotifyWEBAPI.*(..)) && args(spotify,..)")
	public Object checarSpotify(ProceedingJoinPoint joinpoint,Spotify spotify) throws Throwable
	{
		Spotify s = spotify;
		if (s.getExpire_in().after(new Date()) )
		{
			logger.info("REVALIDAR CHAVE TOKEN DA API DO GOOGLE");
			s.setGrant_type("refresh_token");
			s = spotifyService.ExchangeRefreshOrAccessToken(s);
			httpSession.setAttribute("session", s);
			if (s.getError()!= null && !s.getError().trim().isEmpty())
			{
				Map<String,Object> retorno = new HashMap<String, Object>();
				retorno.put("error", s.getError());
				return retorno;
			}
			Object[] args = joinpoint.getArgs();
			for (int i = 0; i < args.length; i++) {
				if (args[i] instanceof Spotify ) 
				{
					args[i] = s;
				}
			}
			return joinpoint.proceed(args);
		} 
		else
		{
			logger.info("AOP: EXECUTAR METODO");
			
		}
		
		
		return joinpoint.proceed();
	}
	
	
}
