package br.com.julioces.api.oauth.google;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.com.julioces.api.Google;
import br.com.julioces.api.oauth.google.model.UserProfile;
import br.com.julioces.service.ServiceAccessTokenException;

/*
 * Classe que representa acesso a servi�os do google 
 */
@Service
public class GoogleWEBAPI extends WebApiUtil implements IGoogleWEBAPI {

	private static Logger logger = LoggerFactory.getLogger(GoogleWEBAPI.class);
			
			
	/*
	 * Funcao que gera o acesso ao endereco : https://www.googleapis.com/oauth2/v2/userinfo e converte o resultado
	 * em um objeto UserProfile. Caso tenha algum erro no parametro Google, dispara uma IllegalargumentException.
	 * 
	 * @see br.com.julioces.api.oauth.google.IGoogleWEBAPI#getProfile(br.com.julioces.api.Google)
	 * @param google classe com todas as informacoes de autenticacao
	 * @throw IIlegalArgumentException
	 */
	@Override
	public UserProfile getProfile(Google google) throws ServiceAccessTokenException{
		UserProfile userprofile = null;
		if (!google.getError().isEmpty())
			throw new IllegalArgumentException(google.getError());
		CloseableHttpClient  httpClient = null;
		try {
			String url = "https://www.googleapis.com/oauth2/v2/userinfo";
			httpClient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(url);
			
			httpGet.addHeader("Authorization","Bearer " + google.getAccess_token());
			
			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			
			// Caso tenha obtido sucesso entity nao sera null
			if (entity != null)
			{
				InputStream instream = entity.getContent();
				try
				{
					StringWriter writer = new StringWriter();
					org.apache.commons.io.IOUtils.copy(instream, writer);
					String retorno = writer.toString();
					logger.info("retorno: ", retorno);
					ObjectMapper mapper = new ObjectMapper();
					userprofile = mapper.readValue(retorno, UserProfile.class);
				}
				finally {
					instream.close();
				}
			}
		} catch (Exception e) {
			logger.error("Erro no processamento: ", e.getMessage());
			throw new ServiceAccessTokenException(e);
		}  finally {
			FecharConexaoHttpClient(httpClient);
		}
		
		return userprofile;
		
	}


	
}
