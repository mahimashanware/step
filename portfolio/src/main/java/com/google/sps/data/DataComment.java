package com.google.sps.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a comment.
 *
 * <p>Note: The private variables in this class are converted into JSON.
 */
public class DataComment {

  /** List of comments */
  private final List<String> commentHistory = new ArrayList<>();

  public void makeComment(String text) {
    commentHistory.add(text);
  }



}
