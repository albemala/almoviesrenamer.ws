package com.almoviesrenamer;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Text;

@SuppressWarnings("serial")
public class ExceptionsServlet extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		Text exception_info = new Text(req.getParameter("exception"));

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		if (exception_info != null) {
			Entity exception = new Entity("exception");
			exception.setProperty("info", exception_info);
			datastore.put(exception);
		}
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		String reset = req.getParameter("reset");

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		if (reset != null && reset.equals("yes")) {
			Query q = new Query("exception");
			PreparedQuery pq = datastore.prepare(q);
			for (Entity result : pq.asIterable()) {
				datastore.delete(result.getKey());
			}
		} else {
			Query q = new Query("exception");
			PreparedQuery pq = datastore.prepare(q);
			resp.setContentType("text/plain");
			for (Entity result : pq.asIterable()) {
				Text info = (Text) result.getProperty("info");
				resp.getWriter().println(info.getValue());
			}
		}
	}
}
