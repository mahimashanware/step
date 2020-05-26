// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public final class DataServlet extends HttpServlet {

  private List<String> datas;

  @Override
  public void init() {
    datas = new ArrayList<>();
    datas.add(
        "I love coffee and my favorite chain is Philz!");
    datas.add("My birthday is on July 19.");
    datas.add("My favorite show is Game of Thrones.");
    datas.add("I went to the Grace Hopper Conference last year!");
    datas.add("My favorite class at CMU was 15-112 Fundamentals of Programming!");
    datas.add("My favorite book is Catcher in the Rye.");
    datas.add("One thing I want to learn more about is machine learning.");
    datas.add("My ideal job is working on accessibility software for self-driving cars.");
    datas.add("My favorite place at CMU to study is the Tepper Quad.");
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String data = datas.get((int) (Math.random() * datas.size()));

    response.setContentType("text/html;");
    response.getWriter().println(data);
  }
}
