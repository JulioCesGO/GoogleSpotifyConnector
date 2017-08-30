package br.com.julioces;

import static org.junit.Assert.*;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.junit.Test;

import com.google.api.client.googleapis.compute.ComputeCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

public class conexaoGoogleAPITest {

	
	
	@Test
	public void test() {
		try {
			JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
			HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
			
			ComputeCredential credential = new ComputeCredential.Builder(httpTransport, JSON_FACTORY)
				    .build();
			
			
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
