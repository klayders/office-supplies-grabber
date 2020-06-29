package com.office.grabber.frontend.utils.cglib;

import org.springframework.cglib.core.NamingPolicy;
import org.springframework.cglib.core.Predicate;

public class SimpleNaming implements NamingPolicy {

    private final String entityClassName;

  public SimpleNaming(String entityClassName) {
    this.entityClassName = entityClassName;
  }

  @Override
  public String getClassName(String prefix, String source, Object key, Predicate names) {
    if (prefix == null) {
      prefix = "org.springframework.cglib.empty.Object";
    } else if (prefix.startsWith("java")) {
      prefix = "$" + prefix;
    }

    //                      String base = prefix + "$$" + source.substring(source.lastIndexOf(46) + 1) + "ByCGLIB" + "$$" + new Random().nextInt(100);
    String base = prefix + "$$" + source.substring(source.lastIndexOf(46) + 1) + entityClassName;
    String attempt = base;

    for(int var7 = 2; names.evaluate(attempt); attempt = base + "_" + var7++) {
    }

    return attempt;
  }
}
