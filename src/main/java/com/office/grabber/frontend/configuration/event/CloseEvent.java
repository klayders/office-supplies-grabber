package com.office.grabber.frontend.configuration.event;


import com.office.grabber.frontend.configuration.AbstractEditEntityForm;

public class CloseEvent extends AbstractFormEvent {

  public CloseEvent(AbstractEditEntityForm source) {
    super(source, null);
  }
}