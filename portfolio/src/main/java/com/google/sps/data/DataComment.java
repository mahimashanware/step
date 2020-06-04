
package com.google.sps.data;

import java.util.ArrayList;
import java.util.List;

/** Class containing server statistics. */
public class DataComment {

  private final List<String> comments = new ArrayList<>();

  public void addComment(String text) {
      comments.add(text);
  }

}
