package com.office.grabber.frontend.configuration.event;


import com.office.grabber.frontend.configuration.AbstractEditEntityForm;

public class DeleteEvent extends AbstractFormEvent {

  public DeleteEvent(AbstractEditEntityForm<?> source, Object entity) {
    super(source, entity);
  }
}
