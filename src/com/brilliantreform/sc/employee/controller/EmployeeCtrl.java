package com.brilliantreform.sc.employee.controller;

import com.brilliantreform.sc.common.Pager;
import com.brilliantreform.sc.community.po.Community;
import com.brilliantreform.sc.employee.service.EmployeeService;
import com.brilliantreform.sc.utils.CommonUtil;
import com.brilliantreform.sc.utils.GridUtil;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.brilliantreform.sc.utils.LogerUtil.logRequest;

/**
 * Created by Lm on 2015/11/16 0016.
 */
@Controller("employee.do")
public class EmployeeCtrl {
    private static Logger logger = Logger.getLogger(EmployeeCtrl.class);

    @Autowired
    private EmployeeService employeeService;

    /**
     * 跳转到listEmployee.jsp 页面
     *
     * @return
     */
    @RequestMapping(params = "method=listEmployeePage", method = {RequestMethod.POST, RequestMethod.GET})
    public String listEmployeePage(HttpServletRequest request) {
        try {
            logRequest(request, logger, "listEmployeePage");
            return "new/jsp/employee/listEmployee";
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    /**
     * 跳转到editEmployee.jsp 页面
     *
     * @return
     */
    @RequestMapping(params = "method=editEmployeePage", method = {RequestMethod.POST, RequestMethod.GET})
    public String editEmployeePage(HttpServletRequest request) {
        try {
            logRequest(request, logger, "editEmployeePage");
            String type = request.getParameter("type");
            String user_id = request.getParameter("user_id");
            Integer typeid = CommonUtil.safeToInteger(type, null);
            Integer user = CommonUtil.safeToInteger(user_id, null);
            request.setAttribute("type", type);
            request.setAttribute("list", employeeService.selMgrRole());
            if (user != null) {
                request.setAttribute("employee", employeeService.selEmployee(user));
                List<Integer> role_ids = employeeService.selMgrUserRole(user);
                StringBuffer sb = new StringBuffer();
                for (Integer i : role_ids) {
                    sb.append(i);
                    sb.append(",");
                }
                String temp = sb.toString().substring(0, sb.length() - 1);
                request.setAttribute("mgrur", temp);
            }
            if (typeid == 1) {//新增用户
                return "new/jsp/employee/editEmployee";
            } else if (typeid == 2) {//查看用户
                return "new/jsp/employee/editEmployee";
            } else if (typeid == 3) {//修改用户
                request.setAttribute("employee", employeeService.selEmployee(user));
                return "new/jsp/employee/updateEmployee";
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    /**
     * 查询 列表信息
     *
     * @return
     */
    @RequestMapping(params = "method=listEmployee", method = {RequestMethod.POST, RequestMethod.GET})
    public void listEmployee(String dtGridPager, HttpServletRequest request, HttpServletResponse response) {
        Pager pager = new Pager();
        try {
            logRequest(request, logger, "listEmployee");
            pager = GridUtil.toPager(JSONObject.fromObject(dtGridPager));
            Community c = (Community) request.getSession().getAttribute("selectCommunity");
            int cid = c.getCommunity_id();
            Map param = GridUtil.parseGridPager(pager);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("start", param.get("begin"));
            map.put("size", param.get("size"));
            map.put("communiyt_id", param.get("communiyt_id"));
            map.put("authenticate", param.get("authenticate"));
            map.put("cid", cid);
            List<Map> list = employeeService.selEmployeeList(map);
            int count = employeeService.selEmployeeCount(map);
            pager = GridUtil.setPagerResult(pager, list, count);
            List<Map> queryChildrenList = employeeService.queryChildrenList(cid);
            request.getSession().removeAttribute("queryChildrenList");
            request.getSession().setAttribute("queryChildrenList", queryChildrenList);
        } catch (Exception e) {
            logger.error(e.getMessage());
            pager.setIsSuccess(false);
        }
        CommonUtil.outToWeb(response, JSONObject.fromObject(pager).toString());
    }

    /**
     * 添加员工,关联小区,关联职位
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "method=addElement", method = {RequestMethod.POST, RequestMethod.GET})
    public void addElement(HttpServletRequest request, HttpServletResponse response) {
        try {
            logRequest(request, logger, "addElement");
            Map<String, Object> map = new HashMap<String, Object>();
            Enumeration employee = request.getParameterNames();
            while (employee.hasMoreElements()) {
                String name = (String) employee.nextElement();
                String value = request.getParameter(name);
                map.put(name, value);
            }
            map.put("activation", 1);
            map.put("type", 1);
            map.put("authenticate", 1);
            //插入 mgr_user 新员工 返回user_id
            int userid = employeeService.insertEmployee(map);
            String[] str = request.getParameterValues("zhiwei");
            //插入mgr_user_role 员工关联多个职位
            for (String s : str) {
                map.put("user_id", userid);
                map.put("role_id", Integer.parseInt(s));
                employeeService.insertMgrUserRole(map);
            }
            map.put("community_id", Integer.parseInt(request.getParameter("community_id")));
            //插入小区与员工的关联 mgr_user_community
            employeeService.insertMgrUserCommunity(map);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 更新员工
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "method=updateEmployee", method = {RequestMethod.POST, RequestMethod.GET})
    public void updateEmployee(HttpServletRequest request, HttpServletResponse response) {
        try {
            logRequest(request, logger, "updateEmployee");
            Enumeration enumeration = request.getParameterNames();
            Map<String, Object> map = new HashMap<String, Object>();
            String password = request.getParameter("password");
            Integer age = CommonUtil.safeToInteger(request.getParameter("age"), null);
            Integer sex = CommonUtil.safeToInteger(request.getParameter("sex"), null);
            String phone = request.getParameter("phone");
            Integer user_id = CommonUtil.safeToInteger(request.getParameter("user_id"), null);
            String community_id = request.getParameter("community_id");
            map.put("password", password);
            map.put("age", age);
            map.put("sex", sex);
            map.put("phone", phone);
            map.put("user_id", user_id);
            map.put("community_id", Integer.parseInt(community_id));
            //更新 mgr_user
            employeeService.updateEmployee(map);
            //更新小区
            employeeService.updateMgrUserCommunity(map);
            //更新职位
            String[] str = request.getParameterValues("zhiwei");//职位编号
            if (str != null) {
                //删除 user_id 下的职位，在插入新的职位。
                employeeService.delMgrUserRole(user_id);
                //在插入新的职位
                for (String s : str) {
                    map.put("user_id", user_id);
                    map.put("role_id", Integer.parseInt(s));
                    employeeService.insertMgrUserRole(map);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @RequestMapping(params = "method=liZhiEmployee", method = {RequestMethod.POST, RequestMethod.GET})
    public void liZhiEmployee(HttpServletRequest request, HttpServletResponse response) {
        try {
            logRequest(request, logger, "updateEmployee");
            Integer user_id = CommonUtil.safeToInteger(request.getParameter("user_id"), null);
            employeeService.liZhiEmployee(user_id);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
