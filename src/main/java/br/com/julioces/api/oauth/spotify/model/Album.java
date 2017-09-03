package br.com.julioces.api.oauth.spotify.model;

import java.util.List;

public class Album {

	private List<Image> images;
	private String name;
	
	
	public List<Image> getImages() {
		return images;
	}
	public void setImages(List<Image> images) {
		this.images = images;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
		
		
}
