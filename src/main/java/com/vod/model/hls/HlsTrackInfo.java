package com.vod.model.hls;

public class HlsTrackInfo {

	public float duration;
    public String title;
	
    public HlsTrackInfo(float duration, String title) {
		super();
		this.duration = duration;
		this.title = title;
	}

	public float getDuration() {
		return duration;
	}

	public String getTitle() {
		return title;
	}

    
    
}
