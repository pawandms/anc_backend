package com.vod.vo;

import java.io.Serializable;

public class HlsMediaRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6390527203082217946L;
	
	private String mediaId;
	private String mediaPath;
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	public String getMediaPath() {
		return mediaPath;
	}
	public void setMediaPath(String mediaPath) {
		this.mediaPath = mediaPath;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mediaId == null) ? 0 : mediaId.hashCode());
		result = prime * result + ((mediaPath == null) ? 0 : mediaPath.hashCode());
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
		HlsMediaRequest other = (HlsMediaRequest) obj;
		if (mediaId == null) {
			if (other.mediaId != null)
				return false;
		} else if (!mediaId.equals(other.mediaId))
			return false;
		if (mediaPath == null) {
			if (other.mediaPath != null)
				return false;
		} else if (!mediaPath.equals(other.mediaPath))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "HlsMediaRequest [mediaId=" + mediaId + ", mediaPath=" + mediaPath + "]";
	}
	
	
	
}
