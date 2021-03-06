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
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.com.julioces.api.Google;
import br.com.julioces.api.oauth.google.WebApiUtil;

/*
 * Servico responsavel pela pela autenticacao e requisicao do token do servico Google 
 */
@Service
@Scope
public class GoogleService extends WebApiUtil implements IGoogleService  {

	private static Logger logger = LoggerFactory.getLogger(GoogleService.class);
	

	/*
	 * Funcao que retorna a URL para autorização na conta do Google
	 * 
	 * @see br.com.julioces.service.IService#GerarURLAuthenticacao(java.lang.Object)
	 * @param entity objeto Google, contendo todos os dados para conexao
	 */
	@Override
	public String GerarURLAuthenticacao(Google entity) {
		
		try {
			String url ="https://accounts.google.com/o/oauth2/v2/auth";
			//HttpClient httpClient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(url);
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			
			params.add(new BasicNameValuePair("client_id", entity.getClient_id()));
			params.add(new BasicNameValuePair("redirect_uri", entity.getRedirect_uri()));
			params.add(new BasicNameValuePair("response_type", entity.getResponse_type()));
			params.add(new BasicNameValuePair("scope", entity.getScope()));
			params.add(new BasicNameValuePair("access_type", entity.getAccess_type()));
			params.add(new BasicNameValuePair("include_granted_scopes","true"));
			params.add(new BasicNameValuePair("prompt", "consent"));
			
			URI uri = new URIBuilder(httpGet.getURI()).addParameters(params).build();

			return uri.toString();
		} catch (URISyntaxException e) {
			logger.error("",e);
		}
				
		return "";
	}

	/*
	 * Funcao responsavel por solicitar novo token ou atualiza-lo, dependendo do parametro
	 * 'grant_type' informado. Caso aconteca algum erro, sera informado na propriedade 'error' do 
	 * objeto Google.
	 * 
	 * @see br.com.julioces.service.IService#ExchangeRefreshOrAccessToken(java.lang.Object)
	 * 
	 * @param entity objeto Google, contendo todos os dados para conexao
	 */
	@Override
	public Google ExchangeRefreshOrAccessToken(Google entity) throws ServiceAccessTokenException {
		
		String retorno = null;
		CloseableHttpClient httpClient= null;
		try {
			String _url1 = "https://www.googleapis.com/oauth2/v4/token";
			
			httpClient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(_url1);
			
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			
			
			params.add(new BasicNameValuePair("client_id", entity.getClient_id()));
			params.add(new BasicNameValuePair("client_secret",entity.getClient_secret()));
			
			params.add(new BasicNameValuePair("grant_type",entity.getGrant_type()));
			if (entity.getGrant_type().equalsIgnoreCase(refreshToken) )
			{
				params.add(new BasicNameValuePair(refreshToken,entity.getRefresh_token()));
			}
			else
			{
				params.add(new BasicNameValuePair("redirect_uri", entity.getRedirect_uri()));
				params.add(new BasicNameValuePair("code", entity.getCode()));
			}
			httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
			
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity Httpentity = response.getEntity();
			
			
				InputStream instream = Httpentity.getContent();
				PreencherGoogle(entity, instream);
					
			
		} catch (Exception e) {
			//logger.error("mal formad URL error: " ,e);
			throw new ServiceAccessTokenException(e);
		}	
		finally {
			FecharConexaoHttpClient(httpClient);
		}
		
		return entity;
		
	}

	private void PreencherGoogle(Google entity, InputStream instream) throws IOException, JsonProcessingException {
		String retorno;
		try
		{
			StringWriter writer = new StringWriter();
			org.apache.commons.io.IOUtils.copy(instream, writer);
			retorno = writer.toString();
			System.out.println(retorno);
			JsonNode rootNode = new ObjectMapper().readTree(retorno);
			if (rootNode.get("error")== null)
			{
				entity.setAccess_token( rootNode.get("access_token").getTextValue() );
				//System.out.println( rootNode.get("token_type").getTextValue() );
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(new Date());
				calendar.add(Calendar.SECOND, rootNode.get("expires_in").getIntValue() );
				entity.setExpire_in(calendar.getTime());
				entity.setId_type( rootNode.get("id_token").getTextValue() );
				if (!entity.getGrant_type().equalsIgnoreCase(refreshToken) )
				{
					entity.setRefresh_token( rootNode.get(refreshToken).getTextValue() );
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
}
