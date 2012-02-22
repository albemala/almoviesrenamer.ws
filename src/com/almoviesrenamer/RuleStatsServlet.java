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
public class RuleStatsServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		String rule = req.getParameter("addrule");
		String reset = req.getParameter("reset");
		String getrules = req.getParameter("getrules");

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		if (rule != null) {
			Entity ruleEntity = new Entity("rule");
			ruleEntity.setProperty("rule", rule);
			datastore.put(ruleEntity);
		}
		if (getrules != null && getrules.equals("yes")) {
			Query q = new Query("rule");
			PreparedQuery pq = datastore.prepare(q);
			resp.setContentType("text/plain");
			for (Entity result : pq.asIterable()) {
				String resultRule = (String) result.getProperty("rule");
				resp.getWriter().println(resultRule);
			}
		}
		if (reset != null && reset.equals("yes")) {
			Query q = new Query("rule");
			PreparedQuery pq = datastore.prepare(q);
			for (Entity result : pq.asIterable()) {
				datastore.delete(result.getKey());
			}
		}
	}
}
