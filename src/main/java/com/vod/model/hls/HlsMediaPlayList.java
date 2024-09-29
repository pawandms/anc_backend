package com.vod.model.hls;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.vod.enums.ExtensionType;

@Document(collection= "hls_media_playlist")
public class HlsMediaPlayList {

	@Id
	private String id;
	private String mediaId;
	private String name;
	private ExtensionType extension;
	private String fullName;
	
}
