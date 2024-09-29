package com.vod.sequencer.service;

import com.vod.sequencer.model.Sequance;

public interface SequencerService {
	
	
	/**
	 * Save Sequeance
	 * @param seq
	 * @return
	 */
	public Sequance saveSequence(Sequance seq);
	
	/**
	 * Get Sequence by Name
	 * @param name
	 * @return
	 */
	public Sequance getSequencebyName(String name);

}
