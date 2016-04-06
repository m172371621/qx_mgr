<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/new/jsp/include/resource.jsp" %>
<html>
<head>
    <title></title>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>加盟申请查询</h5>
                </div>
                <div class="ibox-content">
                    <form role="form" class="form-horizontal">
                        <div class="form-group">
                            <label for="order_serial" class="col-sm-1 control-label text-right">状态</label>

                            <div class="col-sm-2">
                                <select class="form-control" id="cache_id">
                                    <option value="">全部</option>
                                    <option value="1">未审核</option>
                                    <option value="2">通过</option>
                                    <option value="3">未通过</option>
                                </select>
                            </div>
                            <div class="col-sm-2">
                                <button class="btn btn-primary" type="button" id="selStore">搜索</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <div id="storeTable" class="dt-grid-container"></div>
                    <div id="storeToolBar" class="dt-grid-toolbar-container"></div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="editWin" tabindex="-1" role="dialog" >
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">加盟审核</h4>
      </div>
      <div class="modal-body">
        <form role="form" class="form-horizontal" id="editForm">
          <input type="hidden" id="community_register_id1" name="community_register_id1"
												class="form-control"  >
          <div class="form-group">
            <label for="loginname_edit" class="col-md-4 control-label text-right">申请人姓名：</label>
            <div class="col-md-5">
              <input type="text" id="community_person_name1" name="community_person_name1"
												class="form-control" >
            </div>
          </div>
          <div class="form-group">
            <label for="cname_edit" class="col-md-4 control-label text-right">申请人号码：</label>
            <div class="col-md-5">
              <input type="text" id="community_person_phone1" name="community_person_phone1"
												class="form-control" >
            </div>
            </div>
           <div class="form-group">
            <label for="cname_edit" class="col-md-4 control-label text-right">申请地址：</label>
            <div class="col-md-5">
              <input type="text" id="community_person_addr1" name="community_person_addr1"
												class="form-control" >
            </div>
            </div>
           <div class="form-group">
            <label for="cname_edit" class="col-md-4 control-label text-right">组织名称：</label>
            <div class="col-md-5">
              <input type="text" id="community_name" name="community_name"
												class="form-control" >
            </div>
            </div>
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" onclick="addOrg(2)">通过</button>
        <button type="button" class="btn btn-primary" onclick="addOrg(3)">不通过</button>
      </div>
    </div>
  </div>
</div>
</body>
<script type="text/javascript">
    var gridColumn = [
        {id: 'community_register_id', type: 'string', columnClass: 'text-center', hide: true},
        {id: 'community_person_name', title: '申请人姓名', type: 'string', columnClass: 'text-center', hideType: 'xs'},
        {id: 'community_person_phone', title: '申请人号码', type: 'string', columnClass: 'text-center'},
        {
            id: 'community_person_addr', title: '申请人地址', type: 'string', columnClass: 'text-center', type: 'string'
        },
        {id: 'create_time', title: '申请时间', type: 'date', columnClass: 'text-center'},
        {
            id: 'community_register_status',
            title: '审核情况',
            type: 'string',
            columnClass: 'text-center',
            resolution: function (value, record, column, grid, dataNo, columnNo) {
                if (record.community_register_status == 1)
                    return '<span style="background:#00a2ca;padding:2px 10px;color:white;">未审核</span>';
                if (record.community_register_status == 2)
                    return '<span style="background:#00a2ca;padding:2px 10px;color:white;">通过</span>';
                if (record.community_register_status == 3)
                    return '<span style="background:#00a2ca;padding:2px 10px;color:white;">不通过</span>';
            }
        },
        {
            id:'operation',
            title: '操作',
            type: 'string',
            columnClass: 'text-center',
            resolution: function (value, record, column, grid, dataNo, columnNo) {
              var str="<button class='btn' style='background-color: #cccccc);' onclick='showMod(\"ID\",\"NAME\",\"PHONE\",\"ADDR\")'>审核</button>";  
              str=str.replace("ID",record.community_register_id).replace("NAME",record.community_person_name)
                    .replace("PHONE",record.community_person_phone).replace("ADDR",record.community_person_addr);  
              console.log(str);    
              if (record.community_register_status == 1)
                    return str;
                else
                    return "";
            }
        }
    ];

    var gridOption = {
        ajaxLoad: true,
        loadURL: '${ctx}/communityRegisterCtrl.do?method=queryRegisterList',
        columns: gridColumn,
        gridContainer: 'storeTable',
        toolbarContainer: 'storeToolBar',
        tools: ''
    };

    var grid = $.fn.DtGrid.init(gridOption);
    $(function () {
        grid.load();
        //$("#selStore").click(search);
        //$("#cache_id").change(search);
    });
    function search() {
        var community_register_status = $('#community_register_status').val();
        grid.parameters     = new Object();
        grid.parameters['community_register_status'] = community_register_status;
        grid.refresh(true);
    }
    function auditRegister(community_register_id,community_name,community_register_status){
       var retObj={};
	    retObj.community_register_id=community_register_id;
	    retObj.community_name=community_name;
	    retObj.community_register_status=community_register_status;
	    var retStr=JSON.stringify(retObj);
       $.ajax({
				type:	"post",
				url:	ctx + "/communityRegisterCtrl.do?method=auditRegister",
				data:	{retObj:retStr},
				success:	function(data){
				     search();
				     $('#editWin').modal('hide');
				},
					error:	function(err){
				}
			})
    }
    function showMod(id,community_person_name,community_person_phone,community_person_addr){
        $("#community_register_id1").val(id);
        $("#community_person_name1").val(community_person_name);
	    $("#community_person_phone1").val(community_person_phone);
	    $("#community_person_addr1").val(community_person_addr);
	    $("#community_name").val("");
	    $('#editWin').modal({
              backdrop: 'static'  //点击窗体外不会关闭窗口
        })
	}
	function addOrg(community_register_status){
	   var community_register_id=$("#community_register_id1").val();
	   var community_name=$("#community_name").val();
	   auditRegister(community_register_id,community_name,community_register_status);
	}
</script>
</html>
