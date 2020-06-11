
package com.google.sps.data;

import java.util.ArrayList;
import java.util.List;

/** Class containing server statistics. */
public class DataComment {

  public final String comment;
  public final String email;

  public DataComment(String comment, String email) {
    this.comment = comment;
    this.email = email;
  }

  public String getEmail() {
        return this.email;
  }

}
