package com.google.sps.servlets;

import java.io.IOException;
import com.google.sps.data.DataComment;
import javax.servlet.http.HttpServlet;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;

/** Servlet responsible for deleting all comments. */
@WebServlet("/delete-data")
public class DeleteDataServlet extends HttpServlet {

    private final DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Query query = new Query("Comment");
        PreparedQuery results = datastore.prepare(query);

        for (Entity entity : results.asIterable()) {
            Key commentKey = entity.getKey();
            datastore.delete(commentKey);
        }

        response.setContentType("text/html;");
        response.getWriter().println("Deleted all comments.");
    }

}
