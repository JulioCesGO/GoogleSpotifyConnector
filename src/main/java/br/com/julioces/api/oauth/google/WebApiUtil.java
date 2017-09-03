package br.com.julioces.api.oauth.google;

import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/*
 * Classe auxiliar para conexoes a API diversas
 */
public class WebApiUtil {
	
	private static Logger logger = LoggerFactory.getLogger(WebApiUtil.class);

	protected static String autorizacao = "Authorization";
	protected static String bearer = "Bearer";
	protected static String errorKey = "error";
	protected static String refreshToken = "refresh_token";

	/*
	 * Fecha conexao passada como paramtro
	 * 
	 * @param httpClient
	 */
	protected void FecharConexaoHttpClient(CloseableHttpClient httpClient) {
		try
		{
			if (httpClient != null)
				httpClient.close();
		} catch (Exception e) {
			logger.error("Erro ao fechar a conexao: ",e);
		}
	}
}
