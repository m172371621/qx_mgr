<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="java.text.*" %>
<%@ page language="java" import="com.brilliantreform.sc.user.mgrpo.MgrUser" %>
<%@ page language="java" import="com.brilliantreform.sc.community.po.Community" %>
<%@ page language="java" import="com.brilliantreform.sc.order.po.*" %>

<script type="text/javascript">  
	function selectChageCom(){
		//切换小区的时候清除curr_menu cookie
		delCookie('curr_menu');

		var cid=$("#comm").val();
		$.post("${pageContext.request.contextPath }/login.do?method=selectCommunity",{"cid":cid},function(data){
			 window.location.href="${pageContext.request.contextPath }/jsp/common/main.jsp"
		}, "text");
	
	}
</script>

<div id="qxm_header" class="c">
			<a href="" class="fl h_logo"><img src="${pageContext.request.contextPath }/jsp/images/logo_small.png" alt="" /> 产品服务管理后台</a>
			<div class="fr">
						<!-- TODO -->
						
								  <span><a target="_blank"href="http://mgr.qxit.com.cn/qx_mgr/explain.html">说明手册3.0</a></span> 
								  <span>当前小区：<select  id="comm" class="i_text" onchange="selectChageCom()">  
								 <!-- <span>当前小区：<select name="cid1"  id="comm" class="i_text" > -->
									<%-- <%if(session.getAttribute("user_isAdmin")!= null){ %><option value="0"  >全部</option><%} %> --%>
									<%
									   int cid =((Community) session.getAttribute("selectCommunity")).getCommunity_id();
									   List<Community> clist = (List<Community>)session.getAttribute("user_community");
									   for(Community c : clist){
									%>
									<option value="<%= c.getCommunity_id()%>" <% if(c.getCommunity_id() == cid) {out.print("selected");  }%> ><%= c.getCommunity_name() %></option>
									<% } %>
								</select>
								</span> 
				<span><% 
				String format = "yyyy年MM月dd日   HH:mm";
				DateFormat sf = new SimpleDateFormat(format);
				out.print(sf.format(new Date()));
				%>  </span>
				<a href=""><i class="icf">&#xf012d;</i> <%= ((MgrUser)session.getAttribute("user_info")).getLoginname() %></a>
				<a href="${pageContext.request.contextPath }/login.do?method=logout"><i class="icf">&#xf0204;</i> 退出</a>
			</div>
			<!-- iframe src="order/order_neworder.jsp"></iframe -->
</div>