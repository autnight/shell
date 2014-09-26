package com.autnight.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 *
 * @project 项目
 * @package com.autnight.web.filter
 * @file CharacterEncodingFilter.java 创建时间:2014年9月14日下午9:24:01
 * @title 标题
 * @description 编码转换过滤器
 * @copyright Copyright (c) 2014 autnight
 * @company autnight
 * @module 模块:
 * @author autnight
 * @reviewer 审核人
 * @version
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 *
 */
public class CharacterEncodingFilter implements Filter {
	private String encoding = null;
	private Logger logger = LoggerFactory
			.getLogger(CharacterEncodingFilter.class);

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if (this.encoding != null) {
			request.setCharacterEncoding(encoding);
			response.setCharacterEncoding(encoding);
		}
		chain.doFilter(request, response);

	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.encoding = filterConfig.getInitParameter("encoding");
		if (this.encoding != null) {
			logger.info("[INIT] request setCharacterEncoding is {} ",
					this.encoding);
		}
	}

}
