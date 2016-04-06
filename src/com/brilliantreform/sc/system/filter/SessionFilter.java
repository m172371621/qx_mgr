package com.brilliantreform.sc.system.filter;

import com.brilliantreform.sc.sys.po.SysMenu;
import com.brilliantreform.sc.utils.CommonUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 登录过滤
 * 
 */
public class SessionFilter extends OncePerRequestFilter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.filter.OncePerRequestFilter#doFilterInternal(
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
	 */
	/*@SuppressWarnings("unchecked")
	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// 不过滤的uri
		String[] notFilter = new String[] { "login.jsp", "login.do" ,"main.jsp","error.jsp","order_neworder.jsp", "alipay.do","communityRegisterCtrl.do" };

		// 请求的uri
		String uri = request.getRequestURI();
		String path = request.getContextPath();
		String method = request.getParameter("method");

		System.out.println(path +" "+  uri+" "+  method);
		
		// 是否过滤
		boolean doFilter = false;
		for (String s : notFilter) {
			if (uri.indexOf(s) != -1) {
				// 如果uri中包含不过滤的uri，则不进行过滤
				doFilter = false;
				break;
			}
		}
		
		if(uri.equals(path+"/"))
		{
			doFilter = false;
		}

		if (doFilter) {
			// 执行过滤

			int status = 0;

			Object obj = request.getSession().getAttribute("user_info");
			if (null == obj) {
				status = 1;

			} else {
				List<Right> user_right = (List<Right>) request.getSession()
						.getAttribute("user_right");
				status = 2;
				if (user_right != null && user_right.size() > 0) {
					for (Right right : user_right) {
						if (uri.indexOf(right.getUri()) != -1) {
							if (StringUtils.isBlank(right.getMethod())) {
								status = 0;
							} else {
								if (right.getMethod().equalsIgnoreCase(method)) {
									status = 0;
								}
							}
						}
					}
				} else {
					status = 2;
				}

			}

			if (status != 0) {
				// 如果session中不存在登录者实体，则弹出框提示重新登录
				// 设置request和response的字符集，防止乱码
			    path = request.getContextPath();
				String basePath = request.getScheme() + "://"
						+ request.getServerName() + ":"
						+ request.getServerPort() + path + "/";

				String msg = "";
				String url = basePath;
				if (status == 1) {
					msg = "网页过期，请重新登录！";
					url = basePath + "jsp/login.jsp";
				} else if (status == 2) {
					msg = "您无权限访问该页面";
					url = basePath + "jsp/common/main.jsp";
				}

				request.setCharacterEncoding("UTF-8");
				response.setCharacterEncoding("UTF-8");

				PrintWriter out = response.getWriter();
				StringBuilder builder = new StringBuilder();
				builder.append("<html><head><meta charset=\"utf-8\" /></head><body></body><html>");
				builder.append("<script type=\"text/javascript\">");
				builder.append("alert('" + msg + "');");
				builder.append("window.top.location.href='");
				builder.append(url);
				builder.append("';");
				builder.append("</script>");
				out.print(builder.toString());
				builder.append("</script>");
				out.print(builder.toString());

			} else {
				// 如果session中存在登录者实体，则继续
				filterChain.doFilter(request, response);
			}

		} else {
			// 如果不执行过滤，则继续
			filterChain.doFilter(request, response);
		}
	}*/


    private boolean matchRequest(HttpServletRequest request, String[] ignores) {
        /*int matchType = 1;  //匹配类型.1 = url, 2 = uri
        String _requestURI = request.getRequestURI();
        String _requestURL = matchType == 1 ? (_requestURI + (request.getQueryString() != null ? ("?" + request.getQueryString()) : "")) : _requestURI;
        int i = request.getContextPath().length();
        if (i > 0) {
            _requestURL = _requestURL.substring(i);
        }
        if (_requestURI.startsWith("//")) {
            _requestURI = _requestURI.substring(1);
        }
        if (_requestURL.startsWith("//")) {
            _requestURL = _requestURL.substring(1);
        }

        boolean _result = false;
        if (!_requestURI.equals("/")) {
            for (String matchUrl : ignores) {
                Pattern _pattern = Pattern.compile(matchUrl);
                _result |= _pattern.matcher(_requestURL).matches();
                if (_result) break;
            }
        }*/

        boolean result = false;
        String requestURI = request.getRequestURI();
        String path = request.getContextPath();
        for(String ignore : ignores) {
            if(requestURI.indexOf(ignore) != -1) {
                result = true;
                break;
            }
        }
        if(requestURI.equals(path + "/")) {
            result = true;
        }
        return result;
    }

    private boolean checkRight(HttpServletRequest request) {
        boolean hasRight = false;
        String requestURI = request.getRequestURI();
        //只针对.do进行权限控制
        if(requestURI.endsWith(".do")) {
            String method = request.getParameter("method");
            List<SysMenu> menuList = (List<SysMenu>) request.getSession().getAttribute("user_right");
            for(SysMenu menu : menuList) {
                if(menu != null && StringUtils.isNotBlank(menu.getUrl()) && menu.getUrl().startsWith(requestURI + "?method=" + method)) {
                    hasRight = true;
                    break;
                }
            }
        } else {
            hasRight = true;
        }

        return hasRight;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //权限验证只验证.do，但是排除列表外的所有请求都要校验session

        // 排除列表
        // 例如： /logon.jsp.*   ^.*\\.js    /alipay.do.*   /org/searchorg/.*    /user/.*//*.*.do.*
        String[] ignores = new String[] { "login.jsp", "login.do" ,"main.jsp", "error.jsp","order_neworder.jsp",
                "alipay.do", "notify.jsp", "communityRegisterCtrl.do", "404.jsp", "500.jsp", "qxxCtrl.do","/jsp/newQxx","weixinqxx.do", "weixinpay.do", "doWeixinInterative.do", "/phoneorderctrl","/weighProduct","/postSan" };

        boolean _ignored = matchRequest(request, ignores);
        String msg = "";

        if(!_ignored) {
            //首先验证session是否有效
            Object obj = request.getSession().getAttribute("user_info");
            if(obj == null) {
                msg = "网页过期，请重新登录！";
            } else {
                //权限校验
                //todo 暂时注释掉
                /*if(!checkRight(request)) {
                    msg = "您无权限访问该页面！";
                }*/
            }
        }

        if(StringUtils.isNotBlank(msg)) {
            String basePath = request.getScheme() + "://"
                    + request.getServerName() + ":"
                    + request.getServerPort() + request.getContextPath() + "/";

            String url = basePath + "new/jsp/login.jsp";

            StringBuffer sb = new StringBuffer();
            sb.append("<html><head><meta charset=\"utf-8\" />");
            sb.append("<script type=\"text/javascript\">");
            sb.append("alert('" + msg + "');");
            sb.append("window.top.location.href='");
            sb.append(url);
            sb.append("';");
            sb.append("</script>");
            sb.append("</head><body></body></html>");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            CommonUtil.outToWeb(response, sb.toString());
        } else {
            filterChain.doFilter(request, response);
        }
    }
}