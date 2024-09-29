package com.vod.media.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vod.exception.MetaDataServiceException;
import com.vod.importer.model.vo.MetaDataVo;
import com.vod.media.model.Media;
import com.vod.media.model.MediaMetaData;

public interface MetaDataService {
	
	public void saveMetaData(MetaDataVo metadata) throws MetaDataServiceException;

	public Page<Media> searchMediabyText(String txt, Pageable pageable) throws MetaDataServiceException;

	/**
	 * Get MetaData Tags for Respective Media ID
	 * @param mediaId
	 * @return
	 * @throws MetaDataServiceException
	 */
	public List<MediaMetaData> getMediaMetaData(String mediaId)throws MetaDataServiceException;
}
