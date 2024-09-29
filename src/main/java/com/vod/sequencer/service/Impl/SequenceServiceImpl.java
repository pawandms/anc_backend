package com.vod.sequencer.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vod.sequencer.model.Sequance;
import com.vod.sequencer.repository.SequanceRepository;
import com.vod.sequencer.service.SequencerService;

@Service
public class SequenceServiceImpl implements SequencerService {
	
	@Autowired
	private SequanceRepository seqRep;
	

	@Override
	public Sequance saveSequence(Sequance seq) {
		return seqRep.save(seq);
	}


	@Override
	public Sequance getSequencebyName(String name) {
		// TODO Auto-generated method stub
		return seqRep.findByName(name);
	}

}
