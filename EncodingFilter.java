package com.itheima.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

public class SetAllCharacterEncodingFilter implements Filter {
	private FilterConfig filteConfig;
	public void destroy() {

	}

	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request;
		HttpServletResponse response;
		try{
			request = (HttpServletRequest)req;
			response = (HttpServletResponse)resp;
		}catch (Exception e) {
			throw new RuntimeException("non-http request");
		}
		
		String encoding = "UTF-8";//默认的
		String value = filteConfig.getInitParameter("encoding");
		if(value!=null)
			encoding = value;
		
		//设置请求：POST和GET方式编码，响应编码
		request.setCharacterEncoding(encoding);
		response.setCharacterEncoding(encoding);
		response.setContentType("text/html;charset="+encoding);
		
		MyHttpServletRequest mrequest = new MyHttpServletRequest(request);
		chain.doFilter(mrequest, response);
		
	}

	public void init(FilterConfig filteConfig) throws ServletException {
		this.filteConfig = filteConfig;
	}

}
class MyHttpServletRequest extends HttpServletRequestWrapper{
	private HttpServletRequest request;
	public MyHttpServletRequest(HttpServletRequest request){
		super(request);
		this.request = request;
	}
	public String getParameter(String name) {
		String value = request.getParameter(name);
		if(value==null)
			return null;
		String method = request.getMethod();
		if("get".equalsIgnoreCase(method)){
			try {
				value = new String(value.getBytes("ISO-8859-1"),request.getCharacterEncoding());
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return value;
	}
	
	
	
}
