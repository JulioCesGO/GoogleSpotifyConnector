package br.com.julioces;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;

public class ConexaoSpotify {

	private static String CLIENT_ID = "630159fe1f484e329f7715e33e7c5a51";		//Client Id
	private static String CLIENT_SECRET = "420193c163774c719a8a2d545f8896ee";	//Secrect
	private static String REDIRECT_URL = "http://localhost:8000/callback";		//Endereco de redirecionamento
	
	private static String CODE="AQBk_ELeKi1B8PhJuzwaOiI-iRevX-o4mvC9Tcu99HonZ9ymMXamf9RMHvZxXr2Fk0NXPco8NkZ4d3abzdRBv_Zqwj5rwy5GF6yBsRN9dZKBMRxDC3ZfxGaCI76Y2jgpM6iS0rvOEqNNPweGInf9bh9IMIPXKBJxPDLlbwDXuTz_8UxhaFDIMzLYnYwhHzNoEqLeP8IFgMPt4d-qXaJehzl24_EE9BI";
	private static String TOKEN = "BQBbTFYTmf45X27hKWl796kYkLEb4giYLSAO_YtD7bSnDCl6iGWY2IA3L0lukoKy6Epf0yX_DcMcYOYfJHayiLN9m-KlX8MmsSgi0JBdShHK8XCNpD6vgGVbRQvYyDV9k2SKIXgnBqb89Ae7901TWkI";
	
	//@Test
	public void testURL() {
		String url = GerarURLParaAutenticacar(CLIENT_ID, REDIRECT_URL, "code", "user-library-read", null, "state");
		System.out.println(url);
	}

	//@Test
	public void testExchangeRegresh() {
		String url = ExchangeRefreshOrAccessToken(CODE,"state",CLIENT_ID,CLIENT_SECRET,REDIRECT_URL,"authorization_code");
		System.out.println(url);
	}
	
	@Test
	public void testUserInfo() {
		String url = UserInfo(TOKEN);
		System.out.println(url);
		
		url = MeAlbums(TOKEN);
		System.out.println(url);
	}
	
	
	String GerarURLParaAutenticacar(String client_id, String redirect_uri, String response_type,
			String scope, String show_dialog, String state)
	{
		try {
			String url ="https://accounts.spotify.com/authorize";
			//HttpClient httpClient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(url);
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			
			params.add(new BasicNameValuePair("client_id", client_id));
			params.add(new BasicNameValuePair("redirect_uri", redirect_uri));
			params.add(new BasicNameValuePair("response_type",response_type));
			if (scope!= null)
				params.add(new BasicNameValuePair("scope", scope));
			params.add(new BasicNameValuePair("show_dialog", show_dialog));
			params.add(new BasicNameValuePair("state", state));
			
			URI uri = new URIBuilder(httpGet.getURI()).addParameters(params).build();

			return uri.toString();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return "";
	}
	
	String ExchangeRefreshOrAccessToken(
			String code, String state,
			String client_id, 
			String client_secret, String redirect_url, String grant_type)
	{
		String retorno = null;
		try {
			String _url1 = "https://accounts.spotify.com/api/token";
			
			HttpClient httpClient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(_url1);
			String basic = String.format("%s:%s", CLIENT_ID,CLIENT_SECRET);			
			byte[] authEncBytes = Base64.encodeBase64(basic.getBytes());
			String basicEnc = new String(authEncBytes);
			httpPost.addHeader("Authorization", "Basic " + basicEnc );
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("code",code));
			
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
		String retorno= null;
		try {
			String url ="https://api.spotify.com/v1/me";
			HttpClient httpClient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(url);
			httpGet.addHeader("Authorization", "Bearer  " + API_KEY );
			
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
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally {
					instream.close();
				}
			}		
			
		
		} catch (IllegalStateException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
				
		return retorno;
	}
	
	String PlayList(String API_KEY)
	{
		String retorno= null;
		try {
			String url ="https://api.spotify.com/v1/browse/featured-playlists";
			HttpClient httpClient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(url);
			httpGet.addHeader("Authorization", "Bearer  " + API_KEY );
			
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
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally {
					instream.close();
				}
			}		
			
		
		} catch (IllegalStateException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
				
		return retorno;
	}
	
	//https://api.spotify.com/v1/me/albums
	String MeAlbums(String API_KEY)
	{
		String retorno= null;
		try {
			String url ="https://api.spotify.com/v1/me/albums";
			HttpClient httpClient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(url);
			httpGet.addHeader("Authorization", "Bearer  " + API_KEY );
			
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
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally {
					instream.close();
				}
			}		
			
		
		} catch (IllegalStateException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
				
		return retorno;
	}
	
	//https://api.spotify.com/v1/me/tracks
	String GetCurrentUsersSavedTrack(String API_KEY,String urlnext)
	{
		String retorno= null;
		try {
			String url = null;
			if (urlnext!=null && !urlnext.isEmpty())
				url = urlnext;
			else
				url ="https://api.spotify.com/v1/me/tracks";
			HttpClient httpClient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(url);
			httpGet.addHeader("Authorization", "Bearer  " + API_KEY );
			
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
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally {
					instream.close();
				}
			}		
			
		
		} catch (IllegalStateException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
				
		return retorno;
	}
	
}
