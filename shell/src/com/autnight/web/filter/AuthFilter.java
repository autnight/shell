package com.autnight.web.filter;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet Filter implementation class AuthFilter
 */
public class AuthFilter implements Filter {
	private Logger logger = LoggerFactory.getLogger(AuthFilter.class);
	private Map<String, String> authMap = new HashMap<String, String>();
	private String Login_page = null;

	/**
	 * Default constructor.
	 */
	public AuthFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		if (isLegal(request)) {
			chain.doFilter(request, response);
		} else {

			response.sendRedirect(request.getContextPath() + this.Login_page);
		}

	}

	/**
	 * 
	 * @name 中文名称
	 * @description 访问是否合法
	 * @time 创建时间:2014年9月22日下午10:34:35
	 * @param reqsp
	 * @return
	 * @author autnight
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	private boolean isLegal(HttpServletRequest request) {
		logger.info("ServletPath()={}",request.getServletPath());
		String reqsp = request.getServletPath().split("\\/")[1];
		//
		if (authMap.containsKey(reqsp)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		initServletContext(fConfig);

	}

	private void initServletContext(FilterConfig fConfig) {
		Properties prop = new Properties();
		InputStream in = getClass().getResourceAsStream("/system.properties");
		try {
			prop.load(in);
			// 匿名访问资源
			String[] sAuth = ((String) prop.get("comm_path")).split(",");
			for (String s : sAuth) {
				authMap.put(s, "");
			}
			fConfig.getServletContext().setAttribute("auth_access", authMap);
			// 登录页面
			this.Login_page = (String) prop.get("login_page");
			fConfig.getServletContext().setAttribute("login_page",
					this.Login_page);

			logger.info("[INIT] init system properties successful ");
		} catch (IOException e) {
			logger.error("[INIT] init system properties error {} ",
					e.getMessage());
			e.printStackTrace();
		}
	}

}
