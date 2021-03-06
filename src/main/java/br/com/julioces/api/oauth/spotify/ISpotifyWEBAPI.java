package br.com.julioces.api.oauth.spotify;

import java.util.Map;

import br.com.julioces.api.Spotify;
import br.com.julioces.service.ServiceAccessTokenException;

public interface ISpotifyWEBAPI {

	/*
	 * Acesso ao WEBAPI GET https://api.spotify.com/v1/me/player/currently-playing
	 */
	Map<String, Object> getCurrentPlayingTrack(Spotify spotify); 

	Map<String, Object> getCurrentUsersSavedTrack(Spotify spotify, String urlnext);
	/*
	 * Acesso ao WEBAPI PUT https://api.spotify.com/v1/me/player/play
	 */
	Map<String,Object> putPlay(Spotify spotify, String uri);
}
