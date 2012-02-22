package com.almoviesrenamer;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class CheckNewVersionServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		/*
		 * XXX ritornare valori diversi a seconda della versione diversa, es: if
		 * parameter.version=3.0 then return ...
		 */

		String version = req.getParameter("version");
		String lastVersion = "2.1";

		resp.setContentType("text/plain");
		if (version.equals(lastVersion))
			resp.getWriter().println("ok");
		else
			resp.getWriter().println("new");
	}
}
