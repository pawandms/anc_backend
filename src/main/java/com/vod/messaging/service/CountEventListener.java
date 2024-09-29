package com.vod.messaging.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter.SseEventBuilder;

import com.vod.messaging.repository.InMemoryDataStreamRepository;

@Component
class CountEventListener {

  private InMemoryDataStreamRepository repository;

  @EventListener
  public void onNewCountEvent(final CountEvent countEvent) {
    repository.getAll().forEach(dataStream -> {
      SseEmitter emitter = dataStream.getEmitter();

      SseEventBuilder event = SseEmitter.event()
          .data(countEvent.getNewCount())
          .id("count-" + countEvent.getNewCount())
          .name("count");

      try {
        emitter.send(event);
      } catch (Exception ex) {
        emitter.completeWithError(ex);
      }
    });
  }

  @Autowired
  void setRepository(InMemoryDataStreamRepository repository) {
    this.repository = repository;
  }
}
