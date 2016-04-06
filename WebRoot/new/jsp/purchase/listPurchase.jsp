<%@ page import="com.brilliantreform.sc.user.mgrpo.Role" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/new/jsp/include/resource.jsp" %>
<html>

<head>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
  <div class="row">
    <div class="col-sm-12">
      <div class="ibox float-e-margins">
        <div class="ibox-title">
          <h5>进货单列表</h5>
        </div>
        <div class="ibox-content">
          <form role="form" class="form-horizontal">
              <div class="form-group">
                  <label class="col-sm-1 control-label text-right">提交时间</label>
                  <div class="col-sm-3">
                      <div class="input-daterange input-group">
                          <input type="text" class="form-control" id="starttime"/>
                          <span class="input-group-addon">到</span>
                          <input type="text" class="form-control" id="endtime"/>
                      </div>
                  </div>
                  <label for="community_id" class="col-sm-1 control-label text-right">门店</label>
                  <div class="col-sm-2">
                      <ui:simpleCommunitySelect id="community_id" header="全部"/>
                  </div>
                  <div class="col-sm-2">
                      <button class="btn btn-primary" id="searchBtn" type="button" onclick="search()">查询</button>
                  </div>
              </div>
          </form>
        </div>
      </div>
      <div class="ibox float-e-margins">
        <div class="ibox-content">
          <div id="purchaseHeaderTable" class="dt-grid-container"></div>
          <div id="purchaseHeaderToolBar" class="dt-grid-toolbar-container"></div>
        </div>
      </div>
    </div>
  </div>
</div>

<script type="text/javascript">
	var gridColumn = [
	{id:'purchase_header_no', title:'订货单号', type:'string', columnClass:'text-center', hideType:'xs'},
    {id:'community_name', title:'门店', type:'string', columnClass:'text-center'},
	{id:'createTime', title:'提交日期', columnClass:'text-center',type:'date', format:'yyyy-MM-dd hh:mm'},
	{id:'purchase_price', title:'预计付款/元', type:'string', columnClass:'text-center'},
	{id:'purchase_real_price', title:'实际付款/元', type:'string', columnClass:'text-center'},
    {id:'total_amount', title:'全部商品数量', type:'string', columnClass:'text-center'},
    {id:'total_notsend_amount', title:'未发货商品数量', type:'string', columnClass:'text-center'},
    {id:'total_notarrive_amount', title:'未到货商品数量', type:'string', columnClass:'text-center'},
	{id:'',title:'操作',type:'string', columnClass:'text-center',resolution:function (value, record, column, grid, dataNo, columnNo) {
        var content = ""
        <c:if test="${!empty who}">
            <c:if test="${who == 'buy'}">
                + "<button class='btn btn-primary' onclick='confirmPurchase("+record.purchase_header_id+")'>到货确认</button>";
            </c:if>
            <c:if test="${who == 'sell'}">
                + "<button class='btn btn-primary' onclick='editPurchase("+record.purchase_header_id+")'>发货</button>"
            </c:if>
            <c:if test="${who == 'admin'}">
                + "<button class='btn btn-primary' onclick='editPurchase("+record.purchase_header_id+")'>发货</button>"
                + "  <button class='btn btn-primary' onclick='confirmPurchase("+record.purchase_header_id+")'>到货确认</button>";
            </c:if>
        </c:if>
		return content;
	}}
	 ];
  var gridOption = {
    ajaxLoad : true,
    loadURL : '${ctx}/purchase.do?method=getPurchaseHeader',
    columns : gridColumn,
    gridContainer : 'purchaseHeaderTable',
    toolbarContainer : 'purchaseHeaderToolBar',
    tools : ''
  };
   var grid = $.fn.DtGrid.init(gridOption);
   $(function(){
   	 	grid.load();
        $(".input-daterange").datepicker({autoclose : true, todayHighlight : true});
	});

	//查询
   function search() {
   		var begintime = $("#starttime").val();
   		var endtime = $("#endtime").val();
        var community_id = $('#community_id').val();
   		grid.parameters = new Object();
   		grid.parameters['begintime'] = begintime;
   		grid.parameters['endtime'] = endtime;
        grid.parameters['community_id'] = community_id;
        grid.loadToFirst();
   }

    function editPurchase(header_id) {
        if(window.top == window.self) {
            window.open('${ctx}/purchase.do?method=showEditPurchasePage&header_id=' + header_id);
        } else {
            window.parent.openTab('${ctx}/purchase.do?method=showEditPurchasePage&header_id=' + header_id, '编辑订货单');
        }
    }

    function confirmPurchase(header_id) {
        if(window.top == window.self) {
            window.open('${ctx}/purchase.do?method=showConfirmPurchasePage&header_id=' + header_id);
        } else {
            window.parent.openTab('${ctx}/purchase.do?method=showConfirmPurchasePage&header_id=' + header_id, '订单到货确认');
        }
    }
</script>