<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/new/jsp/include/resource.jsp" %>
<html>

<head>
    <link href="http://netdna.bootstrapcdn.com/twitter-bootstrap/2.2.2/css/bootstrap-combined.min.css" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
  <div class="row">
    <div class="col-sm-12">
      <div class="ibox float-e-margins">
        <div class="ibox-title">
          <h5>进货单汇总</h5>
        </div>
        <div class="ibox-content">
          <form role="form" class="form-horizontal">
              <div id="datetimepicker" class="input-append date">
                  <label class="col-sm-1 control-label text-right">开始时间</label>
                  <input type="text" id="starttime" data-format="yyyy-MM-dd" value="" class="" />
                  <span class="add-on">
                    <i data-time-icon="icon-time" data-date-icon="icon-calendar"></i>
                  </span>s
              </div>
              <div id="datetimepicker2" class="input-append date">
                  <label class="col-sm-1 control-label text-right">结束时间</label>
                  <input type="text" id="endtime"/>
                  <span class="add-on">
                    <i data-time-icon="icon-time" data-date-icon="icon-calendar"></i>
                  </span>
                  <button class="btn btn-primary" type="button" id="selPurchase" style="margin-left:20px;">查询</button>
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
    {id:'createbycid', title:'门店', type:'string', columnClass:'text-center',resolution:function(value, record, column, grid, dataNo, columnNo) {
        if(record.createbycid==2) return "精锐SOHO";
        if(record.createbycid==3) return "泽天大厦";
        if(record.createbycid==4) return "来凤小区";
        if(record.createbycid==5) return "恒天财富";
        if(record.createbycid==6) return "依云城邦/智谷";
        if(record.createbycid==7) return "紫金南苑";
        if(record.createbycid==8) return "翠屏国际 ";
        if(record.createbycid==9) return "水晶城/绿杨新苑";
        if(record.createbycid==10) return "保集半岛";
        if(record.createbycid==11) return "华建雅筑";
        if(record.createbycid==12) return "成都锦江区";
        if(record.createbycid==13) return "小卫街";
    }},
	{id:'createTime', title:'提交日期', type:'date', columnClass:'text-center',type:'date', format:'yyyy-MM-dd hh:mm', otype:'string', oformat:'yyyy-MM-dd hh:mm'},
	{id:'purchase_price', title:'预计付款/元', type:'string', columnClass:'text-center'},
	{id:'purchase_real_price', title:'实际付款/元', type:'string', columnClass:'text-center'},
	{id:'status', title:'状态', type:'string', columnClass:'text-center',resolution:function(value, record, column, grid, dataNo, columnNo) {
		var content ="";
		if(record.status == 1) {
			content = "<span style='color: red'>审核中</span>";
		} else if (record.status == 2) {
			content = " ";
			content = "<span style='color: blue'>确认中</span>";
		}else if (record.status == 3) {
			content = "<span style='color: green'>已确认</span>";
		}
	  	return content;
		}
	},
	{id:'',title:'操作',type:'string', columnClass:'text-center',resolution:function (value, record, column, grid, dataNo, columnNo) {
		return "<button class='btn btn-blue' onclick='see("+record.purchase_header_id+")'>查看</button>";
	}}
	 ];
  var gridOption = {
    ajaxLoad : true,
    loadURL : '${ctx}/purchaseCtrl.do?method=getPurchaseHeader',
    columns : gridColumn,
    gridContainer : 'purchaseHeaderTable',
    toolbarContainer : 'purchaseHeaderToolBar',
    tools : ''
  };
   var grid = $.fn.DtGrid.init(gridOption);
   $(function(){
   	 	grid.load();
   	 	$("#selPurchase").click(search);
	});
    $('#datetimepicker').datetimepicker({
        format: 'yyyy-MM-dd',
        language: 'zh-CN',
        pickDate: true,
        pickTime: true,
        inputMask: true
    });
    $('#datetimepicker2').datetimepicker({
        format: 'yyyy-MM-dd',
        language: 'zh-CN',
        pickDate: true,
        pickTime: true,
        inputMask: true
    });
	//查询
   function search() {
   		var begintime = $("#starttime").val();
   		var endtime = $("#endtime").val();
   		grid.parameters = new Object();
   		grid.parameters['begintime'] = begintime;
   		grid.parameters['endtime'] = endtime;
   		grid.refresh(true);
   }
   //查看
   function see(id) {
   	alert(id);
   }
</script>