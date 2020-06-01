
package com.google.sps.data;

import java.util.Date;

/** Class containing server statistics. */
public final class DataComment {

  private final String author;
  private final String text;

  public DataComment(String author, String text) {
    this.author = author;
    this.text = text;
  }

}
