package in.co.jurist.filter;

import in.co.jurist.util.Constants;
import in.co.jurist.util.Constants.Attribute;
import in.co.jurist.util.Constants.Delimiter;
import in.co.jurist.util.Constants.InitParam;
import in.co.jurist.util.Constants.InitValue;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RequestInterceptor implements Filter {

	private Set<String> restrictedPages;

	private Set<String> logoutPages;

	private String referer;

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain filterChain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

		System.out.println("Request URL: " + request.getRequestURL());

		setReferer(request.getHeader(Constants.REFERER_PAGE));

		boolean isRestrctedPageRequest = isRestrctedPageRequest(request);
		/*System.out.println("isRestrctedPageRequest: "
				+ (isRestrctedPageRequest == true ? "Yes" : "NO"));*/

		boolean isSessionValid = isSessionValid(request);

		if (isRestrctedPageRequest) {
			if (getReferer() != null && !getReferer().isEmpty()) {
				System.out.println("Referer is valid. Forward request.");
				if (isSessionValid) {
					boolean isLogoutPageRequest = isLogoutPageRequest(request);
					if (isLogoutPageRequest) {
						logout(request, response);
					}
				}
				filterChain.doFilter(request, response);
			} else {
				System.out.println("Referer is not valid. Block request.");
				/*response.sendError(HttpServletResponse.SC_FORBIDDEN);*/
				/*response.sendRedirect(request.getContextPath() + "/403.html");*/
				response.sendRedirect("http://localhost:9090/jurist/404.html");
			}
		} else {
			filterChain.doFilter(request, response);
		}

	}

	private void logout(HttpServletRequest request, HttpServletResponse response) {
		HttpSession httpSession = request.getSession();
		httpSession.removeAttribute(Attribute.USERNAME.get());
		httpSession.setMaxInactiveInterval(InitValue.ZERO.get());
		httpSession.invalidate();
		System.out.println("Session 1 killed!");
	}

	private boolean isLogoutPageRequest(HttpServletRequest request) {
		String page = getRequestedPageName(request);
		if (getLogoutPages().contains(page)) {
			return true;
		}
		return false;
	}

	private boolean isSessionValid(HttpServletRequest request) {
		HttpSession httpSession = request.getSession();
		if (httpSession != null && httpSession.getAttribute("") != null) {
			return true;
		}
		return false;
	}

	private boolean isRestrctedPageRequest(HttpServletRequest request) {
		String page = getRequestedPageName(request);
		if (getRestrictedPages().contains(page)) {
			return true;
		}
		return false;
	}

	private String getRequestedPageName(HttpServletRequest request) {
		String servletPath = request.getServletPath();
		String page = servletPath.substring(servletPath.indexOf(Delimiter.SLASH
				.get()) + 1);
		return page;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		initRestrictedPages(filterConfig);

		initLogoutPages(filterConfig);
	}

	private void initLogoutPages(FilterConfig filterConfig) {
		String logoutPages = filterConfig
				.getInitParameter(InitParam.LOGOUT_PAGES.get());

		if (logoutPages != null && !logoutPages.isEmpty()) {
			this.logoutPages = new HashSet<String>();
			StringTokenizer tokens = new StringTokenizer(logoutPages,
					Delimiter.COMMA.get());
			while (tokens.hasMoreElements()) {
				String token = tokens.nextToken();
				this.logoutPages.add(token);
			}
		}
	}

	private void initRestrictedPages(FilterConfig filterConfig) {
		String restrictedPages = filterConfig
				.getInitParameter(InitParam.RESTRICTED_PAGES.get());

		if (restrictedPages != null && !restrictedPages.isEmpty()) {
			this.restrictedPages = new HashSet<String>();
			StringTokenizer tokens = new StringTokenizer(restrictedPages,
					Delimiter.COMMA.get());
			while (tokens.hasMoreElements()) {
				String token = tokens.nextToken();
				this.restrictedPages.add(token);
			}
			System.out.println("restrictedPages size: "
					+ this.restrictedPages.size());
		}
	}

	public Set<String> getRestrictedPages() {
		return restrictedPages;
	}

	public void setRestrictedPages(Set<String> restrictedPages) {
		this.restrictedPages = restrictedPages;
	}

	public Set<String> getLogoutPages() {
		return logoutPages;
	}

	public void setLogoutPages(Set<String> logoutPages) {
		this.logoutPages = logoutPages;
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}

}
