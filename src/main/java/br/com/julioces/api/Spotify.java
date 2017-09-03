package br.com.julioces.api;

import java.io.Serializable;

/*
 * Classe responsavel por armazenar todas informacoes para autenticacao aos Servidos do Google
 * @autor Julio Cesar
 * 
 */
public class Spotify extends Basico implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String show_dialog;


	public String getShow_dialog() {
		return show_dialog;
	}

	public void setShow_dialog(String show_dialog) {
		this.show_dialog = show_dialog;
	}
	
	
}
