package com.office.grabber.frontend.configuration.event;


import com.office.grabber.frontend.configuration.AbstractEditEntityForm;

public class SaveEvent extends AbstractFormEvent {


  public SaveEvent(AbstractEditEntityForm<?> source, Object entity) {
    super(source, entity);
  }

}
