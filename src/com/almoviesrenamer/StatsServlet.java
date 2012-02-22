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

@SuppressWarnings("serial")
public class StatsServlet extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		String rule = req.getParameter("rule");
		String duration = req.getParameter("duration");
		String language = req.getParameter("language");
		String separator = req.getParameter("separator");

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		if (rule != null && duration != null && language != null
				&& separator != null) {
			Entity stat = new Entity("stat");
			stat.setProperty("rule", rule);
			stat.setProperty("duration", duration);
			stat.setProperty("language", language);
			stat.setProperty("separator", separator);
			datastore.put(stat);
		}
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		String reset = req.getParameter("reset");

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		if (reset != null && reset.equals("yes")) {
			Query q = new Query("stat");
			PreparedQuery pq = datastore.prepare(q);
			for (Entity result : pq.asIterable()) {
				datastore.delete(result.getKey());
			}
		} else {
			Query q = new Query("stat");
			PreparedQuery pq = datastore.prepare(q);
			resp.setContentType("text/plain");
			for (Entity result : pq.asIterable()) {
				String rule = (String) result.getProperty("rule");
				String duration = (String) result.getProperty("duration");
				String language = (String) result.getProperty("language");
				String separator = (String) result.getProperty("separator");
				String stat = rule + "&" + duration + "&" + language + "&"
						+ separator;
				resp.getWriter().println(stat);
			}
		}
	}
}
