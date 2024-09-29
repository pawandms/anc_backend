package com.vod.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vod.enums.SequenceType;
import com.vod.exception.CollectionException;
import com.vod.exception.SequencerException;
import com.vod.model.MovieCollection;
import com.vod.repository.MovieCollectionRepository;
import com.vod.service.MediaCollectionService;
import com.vod.util.HelperBean;

@Service
public class MediaCollectionServiceImpl implements MediaCollectionService {

	@Autowired
	private MovieCollectionRepository collectionRep;
	
	@Autowired
	private HelperBean helper;
	
	@Override
	public MovieCollection addCollection(MovieCollection col) throws CollectionException {
		MovieCollection result = null;
		
		try {
			col.setId(helper.getSequanceNo(SequenceType.COLLECTION));
			collectionRep.save(col);	
		}
		catch(SequencerException e)
		{
			throw new CollectionException(e.getMessage(), e);
		}
		
		return result;
	}
	
	

}
