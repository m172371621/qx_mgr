package com.brilliantreform.sc.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Lm on 2015/12/18.
 */
public class QxxUtil {

    /**
     *
     * @param sessionName Ïú»ÙµÄsessionÃû³Æ
     * @param request     request
     * @param response    response
     * @return   boolean
     */
    public static boolean  validateSession (String sessionName,HttpServletRequest request,HttpServletResponse response) {
        boolean result = false;
        if(request.getSession().getAttribute(sessionName) == null){
            request.getSession().removeAttribute(sessionName);
            result = true;
        }
        return result;
    }
}
