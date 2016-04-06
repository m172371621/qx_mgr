package com.brilliantreform.sc.tag;

import com.brilliantreform.sc.community.po.Community;
import com.brilliantreform.sc.community.service.CommunityService;
import com.brilliantreform.sc.sys.utils.CacheUtil;
import com.brilliantreform.sc.system.SpringContextHolder;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.RequestScope;

import javax.servlet.ServletException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 经营组织下拉框Tag
 * Created by shangwq on 2015/10/16.
 */
public class CommunitySelectTag extends BodyTagSupport {

    /**
     * 类型：all 所有小区  user 当前用户所属小区 (默认user)
     * */
    private String type;

    private boolean selectParent = true;

    private String id = "";

    private String name = "";

    private String event = "";

    private String header = "";

    private String value;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setSelectParent(boolean selectParent) {
        this.selectParent = selectParent;
    }

    public boolean isSelectParent() {
        return selectParent;
    }

    public int doStartTag() throws JspException {
        List<Community> communityList = new ArrayList<Community>();
        CommunityService communityService = SpringContextHolder.getBean("communityService");
        if(StringUtils.isNotBlank(type) && "all".equals(type)) {
            communityList = communityService.getCommunityList();
        } else {
            communityList = (List)pageContext.getSession().getAttribute("user_community");
        }

        List<Map> list = CacheUtil.findCommunityByParam(communityList);

        try {
            pageContext.getRequest().setAttribute("communitySelectTag_type", type);
            pageContext.getRequest().setAttribute("communitySelectTag_id", id);
            pageContext.getRequest().setAttribute("communitySelectTag_name", name);
            pageContext.getRequest().setAttribute("communitySelectTag_event", event);
            pageContext.getRequest().setAttribute("communitySelectTag_header", header);
            pageContext.getRequest().setAttribute("communitySelectTag_value", value);
            pageContext.getRequest().setAttribute("communitySelectTag_selectParent", selectParent);

            pageContext.getRequest().setAttribute("communitySelectTag_list", list);
            pageContext.include("/new/jsp/tag/communityTag.jsp");
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return TagSupport.EVAL_BODY_INCLUDE;
    }
}
