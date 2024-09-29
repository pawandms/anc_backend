package com.vod.vo;

import java.io.Serializable;

import com.vod.hls.model.Hls_PlayList;
import com.vod.model.hls.HlsMasterPlayList;

public class HlsMediaResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8259677365586025092L;

	private String mediaId;
	private HlsMasterPlayList hlsPlayList;
	
	
	public HlsMediaResponse(String mediaId) {
		super();
		this.mediaId = mediaId;
	}


	


	public HlsMasterPlayList getHlsPlayList() {
		return hlsPlayList;
	}





	public void setHlsPlayList(HlsMasterPlayList hlsPlayList) {
		this.hlsPlayList = hlsPlayList;
	}





	public String getMediaId() {
		return mediaId;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mediaId == null) ? 0 : mediaId.hashCode());
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
		HlsMediaResponse other = (HlsMediaResponse) obj;
		if (mediaId == null) {
			if (other.mediaId != null)
				return false;
		} else if (!mediaId.equals(other.mediaId))
			return false;
		return true;
	}
	
	
	
	
}
