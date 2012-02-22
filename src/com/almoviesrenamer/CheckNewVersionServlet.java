package com.almoviesrenamer;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class CheckNewVersionServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		String version = req.getParameter("version");
		String lastVersion = "2.1";

		resp.setContentType("text/plain");
		if (version.equals(lastVersion)) {
			resp.getWriter().println("ok");
		} else if (version.charAt(0) == '2') {
			resp.getWriter().println("new");
		} else {
			resp.getWriter().println("new_version_available");
		}
	}
}
