package com.office.grabber.frontend.configuration.event;

import com.office.grabber.frontend.configuration.AbstractEditEntityForm;
import com.vaadin.flow.component.ComponentEvent;

public abstract class AbstractFormEvent extends ComponentEvent<AbstractEditEntityForm<?>> {

  private final Object entity;

  protected AbstractFormEvent(AbstractEditEntityForm<?> source, Object entity) {
    super(source, false);
    this.entity = entity;
  }

  public Object getEntity() {
    return entity;
  }
}
