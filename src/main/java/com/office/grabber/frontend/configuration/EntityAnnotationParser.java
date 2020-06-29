package com.office.grabber.frontend.configuration;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class EntityAnnotationParser {

  @Value("${admin.view.annotation.entity.field.grid.column.class}")
  private String annotationGrid;
  @Value("${admin.view.annotation.entity.field.grid.column.order.method}")
  private String gridOrderMethod;

  @Value("${admin.view.annotation.entity.field.edit.form.column.order.method}")
  private String editFormOrderMethod;
  @Value("${admin.view.annotation.entity.field.grid.column.class}")
  private String editFormAnnotationGrid;


  @Bean
  public <T> Class<T> annotationGridColumn() {
    try {
      return (Class<T>) Class.forName(annotationGrid);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
      throw new RuntimeException("can`t load grid annotation, not found [" + annotationGrid + ".class]");
    }
  }


  @Bean
  public <T> Class<T> annotationEditFormColumn() {
    try {
      return (Class<T>) Class.forName(editFormAnnotationGrid);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
      throw new RuntimeException("can`t load grid annotation, not found [" + editFormAnnotationGrid + ".class]");
    }
  }


  public Object getGridOrderByAnnotation(Annotation annotation) {
    try {
      return annotation.annotationType().getMethod(gridOrderMethod).invoke(annotation);
    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
      e.printStackTrace();
    }
    return null;
  }

  public Object getEditFormOrderByAnnotation(Annotation annotation) {
    try {
      return annotation.annotationType().getMethod(editFormOrderMethod).invoke(annotation);
    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
      e.printStackTrace();
    }
    return null;
  }

}
