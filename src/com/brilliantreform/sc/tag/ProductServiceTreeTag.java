package com.brilliantreform.sc.tag;

import com.brilliantreform.sc.community.po.Community;
import com.brilliantreform.sc.sys.utils.CacheUtil;
import com.brilliantreform.sc.utils.CommonUtil;
import net.sf.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 商品类别Tag
 * Created by shangwq on 2015/12/16.
 */
public class ProductServiceTreeTag extends BodyTagSupport {

    /**
     * 类型：all 所有小区  user 当前用户所属小区 (默认user)
     * */
    private String type = "user";

    private boolean selectParent = true;

    private String id = "";

    private String name = "";

    private String event = "";

    private String header = "全部";

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
        List<Map> communityTree = CacheUtil.findAllCommunityTree();
        List<Map> productServiceTree = CacheUtil.findAllProductServiceTree();

        if("user".equals(type)) {
            List<Community> ulist = (List<Community>)pageContext.getSession().getAttribute("user_community");
            Set<Integer> cids = CommonUtil.findAllUserCommunity(ulist);
            List<Map> new_communityTree = new ArrayList<Map>();
            for(Map treeNode : communityTree) {
                String cidstr = treeNode.get("id") + "";
                Integer cid = CommonUtil.safeToInteger(cidstr.replace("c", ""), null);
                if(cid != null && cids.contains(cid)) {
                    new_communityTree.add(treeNode);
                }
            }
            communityTree = new_communityTree;
        }

        List<Map> list = new ArrayList<Map>();
        list.addAll(communityTree);
        list.addAll(productServiceTree);

        try {
            pageContext.getRequest().setAttribute("productServiceTreeTag_type", type);
            pageContext.getRequest().setAttribute("productServiceTreeTag_id", id);
            pageContext.getRequest().setAttribute("productServiceTreeTag_name", name);
            pageContext.getRequest().setAttribute("productServiceTreeTag_event", event);
            pageContext.getRequest().setAttribute("productServiceTreeTag_header", header);
            pageContext.getRequest().setAttribute("productServiceTreeTag_value", value);
            pageContext.getRequest().setAttribute("productServiceTreeTag_selectParent", selectParent);

            pageContext.getRequest().setAttribute("productServiceTreeTag_list", JSONArray.fromObject(list).toString());
            pageContext.include("/new/jsp/tag/productServiceTree.jsp");
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return TagSupport.EVAL_BODY_INCLUDE;
    }
}
