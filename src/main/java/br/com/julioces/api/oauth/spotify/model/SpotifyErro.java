package br.com.julioces.api.oauth.spotify.model;

import org.codehaus.jackson.annotate.JsonProperty;

public class SpotifyErro {

	@JsonProperty(value= "error")
	private Erro error;

	public Erro getErro() {
		return error;
	}

	public void setErro(Erro error) {
		this.error = error;
	}
	
	
	
	
}
