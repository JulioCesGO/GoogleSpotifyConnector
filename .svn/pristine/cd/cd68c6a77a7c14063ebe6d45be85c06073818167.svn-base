package br.com.julioces.service;

import java.io.IOException;

import java.io.InputStream;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.com.julioces.api.Spotify;
import br.com.julioces.api.oauth.google.WebApiUtil;

/*
 * Servico responsavel pela pela autenticacao e requisicao do token do servico Spotify
 */
@Service
public class SpotifyService extends WebApiUtil implements ISpotifyService {

	private static Logger logger = LoggerFactory.getLogger(SpotifyService.class);
	private static String REFRESH_TOKEN = "refresh_token";

	/*
	 * Funcao responsavel por solicitar novo token ou atualiza-lo, dependendo do parametro
	 * 'grant_type' informado. Caso aconteca algum erro, sera informado na propriedade 'error' do 
	 * objeto Spotify.
	 * 
	 * @see br.com.julioces.service.IService#ExchangeRefreshOrAccessToken(java.lang.Object)
	 * 
	 * @param entity objeto spotify, contendo todos os dados para conexao
	 */
	@Override
	public Spotify ExchangeRefreshOrAccessToken(Spotify entity) throws ServiceAccessTokenException  {
		String retorno = null;
		CloseableHttpClient httpClient= null;
		try {
			String url1 = "https://accounts.spotify.com/api/token";
			
			httpClient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(url1);
						
			String basic = String.format("%s:%s", entity.getClient_id(),entity.getClient_secret());
			byte[] authEncBytes = Base64.encodeBase64(basic.getBytes());
			String basicEnc = new String(authEncBytes);
			httpPost.addHeader("Authorization", "Basic " + basicEnc );
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			
			params.add(new BasicNameValuePair("grant_type",entity.getGrant_type()));
			if (entity.getGrant_type().equalsIgnoreCase(REFRESH_TOKEN) )
			{
				params.add(new BasicNameValuePair(REFRESH_TOKEN,entity.getRefresh_token()));
			}
			else
			{
				params.add(new BasicNameValuePair("redirect_uri", entity.getRedirect_uri()));
				params.add(new BasicNameValuePair("code", entity.getCode()));
			}
			httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
			
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity httpEntity = response.getEntity();
			
			if (httpEntity != null)
			{
				InputStream instream = httpEntity.getContent();
				try
				{
					StringWriter writer = new StringWriter();
					org.apache.commons.io.IOUtils.copy(instream, writer);
					retorno = writer.toString();
					logger.info(retorno);
					JsonNode rootNode = new ObjectMapper().readTree(retorno);
					if (rootNode.get("error")== null)
					{
						entity.setAccess_token( rootNode.get("access_token").getTextValue() );
						entity.setScope(rootNode.get("scope").getTextValue());
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(new Date());
						calendar.add(Calendar.SECOND, rootNode.get("expires_in").getIntValue() );
						entity.setExpire_in(calendar.getTime());
						if (!entity.getGrant_type().equalsIgnoreCase(REFRESH_TOKEN) )
						{
							entity.setRefresh_token( rootNode.get(REFRESH_TOKEN).getTextValue() );
						}
						
						entity.setError("");
					}
					else
					{
						String error = String.format("Error: %s , Descricao: %s",  rootNode.get("error").getTextValue() , rootNode.get("error_description").getTextValue() );
						logger.error(error);
						entity.setError(error);
					}
				}
				finally {
					instream.close();
				}
			}		
			
		} catch (Exception e) {
			//logger.error("",e );
			throw new ServiceAccessTokenException(e);
		}
		finally {
			FecharConexaoHttpClient(httpClient);
		}
		
		return entity;
	}

	/*
	 * Funcao que retorna a URL para autorização na conta do Spotify
	 * 
	 * @see br.com.julioces.service.IService#GerarURLAuthenticacao(java.lang.Object)
	 * @param entity objeto spotify, contendo todos os dados para conexao
	 */
	@Override
	public String GerarURLAuthenticacao(Spotify entity) {
		try {
			String url ="https://accounts.spotify.com/authorize";
			//HttpClient httpClient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(url);
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			
			params.add(new BasicNameValuePair("client_id", entity.getClient_id()));
			params.add(new BasicNameValuePair("redirect_uri", entity.getRedirect_uri()));
			params.add(new BasicNameValuePair("response_type",entity.getResponse_type()));
			params.add(new BasicNameValuePair("scope", entity.getScope()));
			params.add(new BasicNameValuePair("show_dialog", entity.getShow_dialog()));
			params.add(new BasicNameValuePair("state", entity.getState()));
			
			URI uri = new URIBuilder(httpGet.getURI()).addParameters(params).build();

			return uri.toString();
		} catch (URISyntaxException e) {
			logger.error("",e);
		}
				
		return "";
	}

}
