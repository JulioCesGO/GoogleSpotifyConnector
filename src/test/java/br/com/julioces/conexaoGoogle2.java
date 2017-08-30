package br.com.julioces;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.stream.StreamSource;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.util.IOUtils;

import br.com.julioces.Controller.HomeController;

public class conexaoGoogle2 {

	private static String CLIENT_ID = "750857276898-1nrl3bt409oohokl0dlsni89q5ruig12.apps.googleusercontent.com";
	private static String REDIRECT_URI = "http://localhost:8080/julioces/";
	private static String API_KEY = "AIzaSyAuhkeREPdlVTQJdaHdto94wfqq-zzBjn0";
	private static String SECRECT_CLIENT = "DDKEUk48i7weOsMB_hwJYf_8";
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	//@Test
	public void gerarURL() 
	{
		String url = GerarURLParaAutenticacaoGoogle(CLIENT_ID,REDIRECT_URI,"code","profile","offline","state_parameter_passthrough_value");
		
		System.out.println(url);
	
	}
	
	//@Test
	public void gerarToken() 
	{
		String code = "4/riPE6PDammqW78t9yWhWANPRAF41G5Y9B3AvM8QBOJw#";
		String retorno = ExchangeRefreshOrAccessToken(code,CLIENT_ID,SECRECT_CLIENT,REDIRECT_URI,"authorization_code");
		
		System.out.println(retorno);
	
	}
	
	@Test
	public void consultarService() 
	{
		String access_toke = "ya29.Gly3BPdJ57O-FwkEWZS396f2xd2qYENIj8Ff7Y1skbvGbsQ9T601fz8qpIJat1_x3ZFsj2lMTuqKTwFCdPjn8lIKM_RWxhN3sRgq-7p90a3J_5zXGBQSFB1LOAnI8A";
		
		String retorno = UserInfo(access_toke);
		
		System.out.println(retorno);
	
	}

	String GerarURLParaAutenticacaoGoogle(String client_id, String redirect_uri, String response_type,
			String scope, String access_type, String state)
	{
		try {
			String url ="https://accounts.google.com/o/oauth2/v2/auth";
			//HttpClient httpClient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(url);
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			
			params.add(new BasicNameValuePair("client_id", client_id));
			params.add(new BasicNameValuePair("redirect_uri", redirect_uri));
			params.add(new BasicNameValuePair("response_type",response_type));
			params.add(new BasicNameValuePair("scope", scope));
			params.add(new BasicNameValuePair("access_type", access_type));
			//params.add(new BasicNameValuePair("state", state));
			
			URI uri = new URIBuilder(httpGet.getURI()).addParameters(params).build();

			return uri.toString();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return "";
	}
	
	String AtualizaAccessToken()
	{
		return "";
	}
	
	String ExchangeRefreshOrAccessToken(
			String code, 
			String client_id, 
			String client_secret, String redirect_url, String grant_type)
	{
		String retorno = null;
		try {
			String _url1 = "https://www.googleapis.com/oauth2/v4/token";
			
			HttpClient httpClient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(_url1);
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("code",code));
			params.add(new BasicNameValuePair("client_id", client_id));
			params.add(new BasicNameValuePair("client_secret",client_secret));
			params.add(new BasicNameValuePair("redirect_uri", redirect_url));
			params.add(new BasicNameValuePair("grant_type",grant_type));
			httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
			
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			
			if (entity != null)
			{
				InputStream instream = entity.getContent();
				try
				{
					StringWriter writer = new StringWriter();
					org.apache.commons.io.IOUtils.copy(instream, writer);
					retorno = writer.toString();
					
				}
				finally {
					instream.close();
				}
			}		
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		return retorno;

	}

	String UserInfo(String API_KEY)
	{
	
		String retorno = null;
		try {
			String url = "https://www.googleapis.com/oauth2/v2/userinfo";
			HttpClient httpClient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(url);
			
			httpGet.addHeader("Authorization","Bearer " + API_KEY);
			
			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			
			if (entity != null)
			{
				InputStream instream = entity.getContent();
				try
				{
					StringWriter writer = new StringWriter();
					org.apache.commons.io.IOUtils.copy(instream, writer);
					retorno = writer.toString();
				}
				finally {
					instream.close();
				}
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return retorno;
	}
	
	
}

