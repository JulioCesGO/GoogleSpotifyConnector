package br.com.julioces.api.oauth.spotify.model;

import java.util.List;

public class CurrentUsersSavedTracks {

	private List<ItemCurrentUsersSavedTracks> items;
	private int limit;
	private String next;
	
	
	public List<ItemCurrentUsersSavedTracks> getItems() {
		return items;
	}
	public void setItems(List<ItemCurrentUsersSavedTracks> items) {
		this.items = items;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public String getNext() {
		return next;
	}
	public void setNext(String next) {
		this.next = next;
	}
	
	
}
