package com.vod.importer.model;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vod.model.Genre;

public class GenreImport {

	@JsonProperty("genres")
	private Collection<Genre> genre_list;

	public Collection<Genre> getGenre_list() {
		return genre_list;
	}

	public void setGenre_list(Collection<Genre> genre_list) {
		this.genre_list = genre_list;
	}
	
	
}
