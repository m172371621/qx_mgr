<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<% 
String pageIndex = request.getParameter("pageIndex");
String pageCount = request.getParameter("pageCount");

%>
<div class="page">				 	
						<span>
							<a href="javascript:toPage(1)"><<</a>
							<a href="javascript:toPage(<%= pageIndex %> - 1)">上一页</a>
							<a href="javascript:toPage(<%= pageIndex %> + 1)">下一页</a>
							<a href="javascript:toPage(<%= pageCount %> )">>></a>
						</span>
						<span>跳转到<input type="text" id="toPageNumber" value="<%= pageIndex %>"/>/<%= pageCount %> <a href="javascript:toPage(parseInt($('#toPageNumber').val()))">Go</a> </span>
						<script type="text/javascript">
						function toPage(number)
						{
							if(isNaN(number) || 0 == <%= pageCount %>)
							{
								return;
							}

							if(number <= 0)
								return;

							if(number == <%= pageIndex %>)
								return;
							
							if(number > <%= pageCount %>)
								number = <%= pageCount %>;


							$('#pageIndex').val(number.toString());	

							$('#searchForm').submit();
						
						}
						</script>			
</div>
