package com.brilliantreform.sc.utils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.request.RequestContextHolder;

public class ResonseWriteUtil {

	public static void writeJson(HttpServletResponse response, String jsonstr) {
		// response.setContentType("text/html; charset=UTF-8");
		// response.setHeader("Cache-Control", "no-cache"); // 取消浏览器缓存
		RequestContextHolder.getRequestAttributes().setAttribute("PrintWriterOut", jsonstr, 0);
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.print(jsonstr);
		out.flush();
		out.close();
	}

}
