package com.brilliantreform.sc.tag;

import com.brilliantreform.sc.community.service.CommunityService;
import com.brilliantreform.sc.system.SpringContextHolder;
import com.brilliantreform.sc.user.mgrpo.MgrUser;

import javax.servlet.ServletException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 简单门店下拉框Tag，不包含上层
 */
public class SimpleCommunityTag extends BodyTagSupport {

    /**
     * 类型：all 所有小区  user 当前用户所属小区 (默认user)
     * */
    private String type = "user";

    private String id = "";

    private String name = "";

    private String css = "form-control";

    private String event = "";

    private String header = "";

    private String value;

    private String showTotal = "false";     //是否显示总部option

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

    public String getCss() {
        return css;
    }

    public void setCss(String css) {
        this.css = css;
    }

    public String getShowTotal() {
        return showTotal;
    }

    public void setShowTotal(String showTotal) {
        this.showTotal = showTotal;
    }

    public int doStartTag() throws JspException {
        try {
            CommunityService communityService = SpringContextHolder.getBean("communityService");
            MgrUser mgrUser = (MgrUser)pageContext.getSession().getAttribute("user_info");
            Boolean user_isAdmin = (Boolean)pageContext.getSession().getAttribute("user_isAdmin");
            List<Map> communityList = new ArrayList<Map>();
            if(user_isAdmin != null && user_isAdmin) {
                communityList = communityService.findSecondAdminCommunity();
            } else {
                communityList = communityService.findSecondUserCommunity(mgrUser.getUser_id());
            }


            pageContext.getRequest().setAttribute("simpleCommunityTag_id", id);
            pageContext.getRequest().setAttribute("simpleCommunityTag_name", name);
            pageContext.getRequest().setAttribute("simpleCommunityTag_event", event);
            pageContext.getRequest().setAttribute("simpleCommunityTag_header", header);
            pageContext.getRequest().setAttribute("simpleCommunityTag_value", value);
            pageContext.getRequest().setAttribute("simpleCommunityTag_css", css);
            pageContext.getRequest().setAttribute("simpleCommunityTag_showTotal", showTotal);

            pageContext.getRequest().setAttribute("simpleCommunityTag_list", communityList);
            pageContext.include("/new/jsp/tag/simpleCommunityTag.jsp");
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return TagSupport.EVAL_BODY_INCLUDE;
    }
}
