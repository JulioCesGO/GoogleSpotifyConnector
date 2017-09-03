package br.com.julioces.api.oauth.spotify;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import java.util.HashMap;

import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Service;

import br.com.julioces.api.Spotify;
import br.com.julioces.api.oauth.google.WebApiUtil;
import br.com.julioces.api.oauth.spotify.model.CurrentPlayingTrack;
import br.com.julioces.api.oauth.spotify.model.CurrentUsersSavedTracks;
import br.com.julioces.api.oauth.spotify.model.SpotifyErro;
import br.com.julioces.service.ServiceAccessTokenException;

/*
 * Classe que representa acesso a serviços do Spotify
 */
@Service
public class SpotifyWEBAPI extends WebApiUtil implements ISpotifyWEBAPI{
	
	private static Logger logger = LoggerFactory.getLogger(SpotifyWEBAPI.class);
	
	/*
	 * Funcao que busca as ultimas musicas salvas na Conta do usuario. Se o resultado der certo retornara
	 * um Map com uma key chamada "currentUsersSavedTracks" contendo as faixas , caso der algum erro no 
	 * processo, retornara um Map com uma key chamada "error", contendo a descricao do erro.
	 * 
	 * @see br.com.julioces.api.oauth.spotify.ISpotifyWEBAPI#getCurrentUsersSavedTrack(br.com.julioces.api.Spotify, java.lang.String)
	 * 
	 * @param spotify 
	 * @param urlnext link chamada de requisicao
	 */
	@Override
	public Map<String,Object>  getCurrentUsersSavedTrack(Spotify spotify, String urlnext)  {
		Map<String,Object> mapretorno = new HashMap<String, Object>();
		CloseableHttpClient httpClient= null;
		try {
			
			String url = null;
			if (urlnext!=null && !urlnext.isEmpty())
				url = urlnext;
			else
				url ="https://api.spotify.com/v1/me/tracks";
			httpClient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(url);
			PreencherCabecalho(spotify, httpGet,null);
			
			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			
			if (entity != null)
			{
				InputStream instream = entity.getContent();
				ProcessarListadeMusicas( mapretorno, instream);
			}		
			
		
		} catch (Exception e1) {
			//throw new ServiceAccessTokenException(e1);
			mapretorno.put("error", e1);
		} finally {
			FecharConexaoHttpClient(httpClient);
		}
				
		return mapretorno;
	}


	private void PreencherCabecalho(Spotify spotify, HttpGet httpGet, HttpPut httpPut) {
		if (httpGet!=null)
			httpGet.addHeader(autorizacao, String.format("%s %s", bearer, spotify.getAccess_token() ));
		if (httpPut!=null)
			httpPut.addHeader(autorizacao, String.format("%s %s", bearer, spotify.getAccess_token() ));
	}

	
	private void ProcessarListadeMusicas(Map<String, Object> mapretorno, InputStream instream) throws IOException {
		String retorno;
		SpotifyErro spotifyErro = null;
		CurrentUsersSavedTracks currentTracks = null;
		try
		{
			StringWriter writer = new StringWriter();
			org.apache.commons.io.IOUtils.copy(instream, writer);
			retorno = writer.toString();
			
			JsonNode node = new ObjectMapper().readTree(retorno);
			ObjectMapper mapper = new ObjectMapper();
			if (node.get(errorKey)!= null )
			{
				mapper.configure(Feature.UNWRAP_ROOT_VALUE, false);
				spotifyErro = mapper.readValue(retorno, SpotifyErro.class);						
			}
			else
			{						
				mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				currentTracks = mapper.readValue(retorno, CurrentUsersSavedTracks.class);
			}
			mapretorno.put("currentListTrack", currentTracks);
			mapretorno.put(errorKey, spotifyErro);
		} catch (IOException e) {
			mapretorno.put(errorKey, e);
			
		}
		finally {
			instream.close();
		}
	}

	/*
	 * Funcao que busca a musica que esta tocando atualmente na conta do usuario. Se o resultado der certo retornara
	 * um Map com uma key chamada "currentPlayingTrack" contendo a musica(CurrentPlayingTrack)  , caso de algum erro no 
	 * processo, retornara um Map com uma key chamada "error", contendo a descricao do erro.
	 * 
	 * @see br.com.julioces.api.oauth.spotify.ISpotifyWEBAPI#getCurrentUsersSavedTrack(br.com.julioces.api.Spotify, java.lang.String)
	 * 
	 * @param spotify 
	 * 
	 */
	@Override
	public Map<String,Object>  getCurrentPlayingTrack(Spotify spotify)  {
		Map<String,Object> mapretorno = new HashMap<String, Object>();
		CloseableHttpClient httpClient = null;
		try {
			String url = null;
			url ="https://api.spotify.com/v1/me/player/currently-playing";
			httpClient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(url);
			PreencherCabecalho(spotify, httpGet,null);
			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			
			if (entity != null)
			{
				InputStream instream = entity.getContent();
				ProcessarCurrentPlayingTrack( mapretorno, instream);
			}		
			
		
		} catch (Exception e1) {
			//throw new ServiceAccessTokenException(e1);
			mapretorno.put("error", e1);
		} finally {
			FecharConexaoHttpClient(httpClient);
		}
				
		return mapretorno;
	}


	private void ProcessarCurrentPlayingTrack(	Map<String, Object> mapretorno, InputStream instream) throws IOException {
		String retorno;
		CurrentPlayingTrack currentPlayingTrack = null;
		SpotifyErro spotifyErro = null;
		try
		{
			StringWriter writer = new StringWriter();
			org.apache.commons.io.IOUtils.copy(instream, writer);
			retorno = writer.toString();
			ObjectMapper mapper = new ObjectMapper();
			
			JsonNode node = new ObjectMapper().readTree(retorno);
			if (node.get("error")!= null )
			{
				mapper.configure(Feature.UNWRAP_ROOT_VALUE, false);
				spotifyErro = mapper.readValue(retorno, SpotifyErro.class);
				
			}
			else
			{
				mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				currentPlayingTrack = mapper.readValue(retorno, CurrentPlayingTrack.class);
			}
			mapretorno.put(errorKey, spotifyErro);
			mapretorno.put("currentPlayingTrack", currentPlayingTrack);
			
		} catch (IOException e) {
			//logger.error("Erro na conexao:",e);
			mapretorno.put("error", e);
		}
		finally {
			instream.close();
		}
	}

	/*
	 * Função responsavel por reproduzir 'Track' no dispositivo atual - Endereco: https://api.spotify.com/v1/me/player/play
	 * retornara um Map com a key 'status' ,contendo a resposta da requisicao. Caso de erro, retornara o Map com key 'error'
	 * contendo a excecao disparada. 
	 * 
	 * @see br.com.julioces.api.oauth.spotify.ISpotifyWEBAPI#putPlay(br.com.julioces.api.Spotify, java.lang.String)
	 * 
	 * @param spotify
	 * @param uri codigo da 'track' no spotify
	 */
	@Override
	public Map<String, Object> putPlay(Spotify spotify, String uri) {
		
		Map<String,Object> mapretorno = new HashMap<String, Object>();
		CloseableHttpClient httpClient = null;
		try {
			String url = "https://api.spotify.com/v1/me/player/play";
						
			httpClient = HttpClients.createDefault();
			HttpPut httpPut = new HttpPut(url);
			PreencherCabecalho(spotify, null,httpPut);
			if (uri!= null && uri.length()>0)
				httpPut.setEntity(new StringEntity("{\"uris\": [\""+ uri + "\"]}"));
			
			HttpResponse response = httpClient.execute(httpPut);
			if (response!=null)
			{
				mapretorno.put("status", response.getStatusLine());
				logger.info("Resposta Servidor: ", response.getStatusLine().toString());
			}
		
		} catch (Exception e1) {
			mapretorno.put(errorKey, e1);
		} finally {
			FecharConexaoHttpClient(httpClient);
		}
				
		return mapretorno;
	}

}
