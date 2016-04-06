package com.brilliantreform.sc.sys.controller;

import com.brilliantreform.sc.common.Pager;
import com.brilliantreform.sc.common.RtnResult;
import com.brilliantreform.sc.sys.enumerate.JobStatusEnum;
import com.brilliantreform.sc.sys.po.SysJob;
import com.brilliantreform.sc.sys.service.SysJobService;
import com.brilliantreform.sc.sys.service.SysService;
import com.brilliantreform.sc.sys.utils.JobUtil;
import com.brilliantreform.sc.utils.CommonUtil;
import com.brilliantreform.sc.utils.GridUtil;
import com.brilliantreform.sc.utils.LogerUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("sysJob.do")
public class SysJobCtrl {

	private static Logger LOGGER = Logger.getLogger(SysJobCtrl.class);

	@Autowired
	private SysJobService sysJobService;

    @Autowired
    private SysService sysService;

    @RequestMapping(params = "method=showListSysJobPage")
    public String showListSysJobPage(HttpServletRequest request, HttpServletResponse response) {
        return "/new/jsp/sys/listSysJob";
    }

	@RequestMapping(params = "method=searchSysJob")
	public void searchSysJob(String dtGridPager, HttpServletRequest request, HttpServletResponse response) {
		Pager pager = new Pager();
		try {
			LogerUtil.logRequest(request, LOGGER, "searchSysJob");

			pager = GridUtil.toPager(JSONObject.fromObject(dtGridPager));

			Map param = GridUtil.parseGridPager(pager);

			List<Map> list = sysJobService.searchSysJob(param);
			int count = sysJobService.searchSysJobCount(param);

            //实时获取定时任务的执行状态
            for(Map map : list) {
                Integer objid = CommonUtil.safeToInteger(map.get("objid"), null);
                if(objid != null) {
                    map.put("status_name", sysJobService.getJobStatus(objid).name());
                }
            }

			pager = GridUtil.setPagerResult(pager, list, count);

		} catch (Exception e) {
			LOGGER.error("searchSysJob error", e);
			pager.setIsSuccess(false);
		}
		CommonUtil.outToWeb(response, JSONObject.fromObject(pager).toString());
	}

    @RequestMapping(params = "method=getJobById")
    public void getJobById(HttpServletRequest request, HttpServletResponse response) {
        try {
            LogerUtil.logRequest(request, LOGGER, "getJobById");

            Integer objid = CommonUtil.safeToInteger(request.getParameter("objid"), null);
            if(objid != null) {
                SysJob job = sysService.getJobById(objid);
                if(job != null) {
                    CommonUtil.outToWeb(response, JSONObject.fromObject(job).toString());
                }
            }
        } catch (Exception e) {
            LOGGER.error("getJobById error", e);
        }
    }

    @RequestMapping(params = "method=saveJob")
    public void saveJob(HttpServletRequest request, HttpServletResponse response) {
        RtnResult result = new RtnResult(false);
        try {
            LogerUtil.logRequest(request, LOGGER, "saveJob");

            Integer objid = CommonUtil.safeToInteger(request.getParameter("objid"), null);
            String name = request.getParameter("name");
            String cronExpression = request.getParameter("cronExpression");
            Integer isSync = CommonUtil.safeToInteger(request.getParameter("isSync"), null);
            String beanId = request.getParameter("beanId");
            String clazz = request.getParameter("clazz");
            String method = request.getParameter("job_method");
            String remark = request.getParameter("remark");
            Integer status = CommonUtil.safeToInteger(request.getParameter("status"), null);

            if(StringUtils.isNotBlank(name) && StringUtils.isNotBlank(cronExpression) && isSync != null
                    && StringUtils.isNotBlank(beanId) && StringUtils.isNotBlank(method) && status != null) {
                SysJob job = null;
                if(objid != null) {
                    job = sysService.getJobById(objid);
                } else {
                    job = new SysJob();
                }

                if(job != null) {
                    job.setName(name);
                    job.setCronExpression(cronExpression);
                    job.setIsSync(isSync);
                    job.setBeanId(beanId);
                    job.setClazz(clazz);
                    job.setMethod(method);
                    job.setRemark(remark);
                    job.setStatus(status);
                    sysService.saveJob(job);
                }

                if(objid == null) {
                    //新增，将该任务添加到任务列表中
                    sysJobService.addJob(job);
                } else {
                    //修改, 更新任务列表，先删除，再添加
                    sysJobService.removeJob(objid, false);
                    sysJobService.addJob(job);
                }
                result.setResult(true);
            }
        } catch (Exception e) {
            LOGGER.error("saveJob error", e);
        }
        CommonUtil.outToWeb(response, JSONObject.fromObject(result).toString());
    }

    @RequestMapping(params = "method=removeJob")
    public void removeJob(HttpServletRequest request, HttpServletResponse response) {
        RtnResult result = new RtnResult(false);
        try {
            LogerUtil.logRequest(request, LOGGER, "removeJob");

            String ids = request.getParameter("ids");
            if(StringUtils.isNotBlank(ids)) {
                String[] ids_array = ids.split(",");
                for(String id : ids_array) {
                    Integer objid = CommonUtil.safeToInteger(id, null);
                    if(objid != null) {
                        sysJobService.removeJob(objid, true);
                    }
                }
                result.setResult(true);
            }
        } catch (Exception e) {
            LOGGER.error("removeJob error", e);
        }
        CommonUtil.outToWeb(response, JSONObject.fromObject(result).toString());
    }

    @RequestMapping(params = "method=runOnce")
    public void runOnce(HttpServletRequest request, HttpServletResponse response) {
        RtnResult result = new RtnResult(false);
        try {
            LogerUtil.logRequest(request, LOGGER, "runOnce");

            Integer objid = CommonUtil.safeToInteger(request.getParameter("objid"), null);
            if(objid != null) {
                SysJob job = sysService.getJobById(objid);
                if(job != null) {
                    JobUtil.invokMethod(job);
                    result.setResult(true);
                }
            }
        } catch (Exception e) {
            LOGGER.error("runOnce error", e);
        }
        CommonUtil.outToWeb(response, JSONObject.fromObject(result).toString());
    }

    /**
     * 启动一个任务
     * */
    @RequestMapping(params = "method=startJob")
    public void startJob(HttpServletRequest request, HttpServletResponse response) {
        RtnResult result = new RtnResult(false);
        try {
            LogerUtil.logRequest(request, LOGGER, "startJob");

            String ids = request.getParameter("ids");
            if(StringUtils.isNotBlank(ids)) {
                String[] id_array = ids.split(",");
                for(String id : id_array) {
                    Integer objid = CommonUtil.safeToInteger(id, null);
                    if(objid != null) {
                        SysJob job = sysService.getJobById(objid);
                        if(job != null) {
                            if(job.getStatus().intValue() != JobStatusEnum.ENABLE.getValue()) {
                                job.setStatus(JobStatusEnum.ENABLE.getValue());
                                sysService.saveJob(job);
                                sysJobService.removeJob(objid, false);
                                sysJobService.addJob(job);
                            }
                        }
                    }
                }
                result.setResult(true);
            }
        } catch (Exception e) {
            LOGGER.error("startJob error", e);
        }
        CommonUtil.outToWeb(response, JSONObject.fromObject(result).toString());
    }

    /**
     * 停止暂停一个任务
     * */
    @RequestMapping(params = "method=stopJob")
    public void stopJob(HttpServletRequest request, HttpServletResponse response) {
        RtnResult result = new RtnResult(false);
        try {
            LogerUtil.logRequest(request, LOGGER, "stopJob");

            String ids = request.getParameter("ids");
            if(StringUtils.isNotBlank(ids)) {
                String[] id_array = ids.split(",");
                for(String id : id_array) {
                    Integer objid = CommonUtil.safeToInteger(id, null);
                    if(objid != null) {
                        SysJob job = sysService.getJobById(objid);
                        if(job != null) {
                            if(job.getStatus().intValue() != JobStatusEnum.DISABLE.getValue()) {
                                job.setStatus(JobStatusEnum.DISABLE.getValue());
                                sysService.saveJob(job);
                                sysJobService.removeJob(objid, false);
                            }
                        }
                    }
                }
                result.setResult(true);
            }
        } catch (Exception e) {
            LOGGER.error("stopJob error", e);
        }
        CommonUtil.outToWeb(response, JSONObject.fromObject(result).toString());
    }

}
