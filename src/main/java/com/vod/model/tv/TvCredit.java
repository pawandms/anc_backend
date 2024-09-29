package com.vod.model.tv;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.vod.model.Cast;
import com.vod.model.Crew;

@Document(collection= "tvCredit")
public class TvCredit {

	@Id
	private String id;
	private String tmdbId;
	private String name;
	private String tvId;
	private int seasonCount;
	private int episodeCount;
	
	private List<Cast> castList;
	private List<Crew> crewList;

	private String createdBy;
	private Date createdOn;
	private String modifiedBy;
	private Date modifiedOn;

	
	public TvCredit() {
		super();
		castList = new ArrayList<Cast>();
		crewList = new ArrayList<Crew>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTmdbId() {
		return tmdbId;
	}

	public void setTmdbId(String tmdbId) {
		this.tmdbId = tmdbId;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getTvId() {
		return tvId;
	}

	public void setTvId(String tvId) {
		this.tvId = tvId;
	}

	public int getSeasonCount() {
		return seasonCount;
	}

	public void setSeasonCount(int seasonCount) {
		this.seasonCount = seasonCount;
	}

	public int getEpisodeCount() {
		return episodeCount;
	}

	public void setEpisodeCount(int episodeCount) {
		this.episodeCount = episodeCount;
	}

	public List<Cast> getCastList() {
		return castList;
	}

	public List<Crew> getCrewList() {
		return crewList;
	}
	
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TvCredit other = (TvCredit) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
	
}
