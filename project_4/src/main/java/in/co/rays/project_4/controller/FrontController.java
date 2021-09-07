
package in.co.rays.project_4.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import in.co.rays.project_4.util.ServletUtility;

@WebFilter(urlPatterns = { "/ctl/*", "/doc/*" })
public class FrontController implements Filter {

	/**
	 * The method init().
	 */
	public void init(FilterConfig conf) throws ServletException {
	}

	/**
	 * The method doFilter.
	 */
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

		HttpSession session = request.getSession(true);

		if (session.getAttribute("user") == null) {

			String uri = request.getRequestURI();
			request.setAttribute("URI", uri);
request.setAttribute("message","your session has been expired..!!");
			ServletUtility.forward(ORSView.LOGIN_VIEW, request, response);
			System.out.println("if in dofilter uri "+uri);
			return;
		} else {
			chain.doFilter(req, resp);
		}
	}

	/**
	 * The method destroy
	 */
	public void destroy() {

	}

}
