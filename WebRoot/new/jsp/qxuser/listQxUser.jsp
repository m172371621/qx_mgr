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
          <h5>区享用户查询</h5>
        </div>
        <div class="ibox-content">
          <form role="form" class="form-horizontal">
            <div class="form-group">
              <label for="phone" class="col-sm-1 control-label text-right">手机号</label>
              <div class="col-sm-2">
                <input type="text" id="phone" class="form-control">
              </div>
              <label for="loginname" class="col-sm-1 control-label text-right">用户名</label>
              <div class="col-sm-2">
                <input type="text" id="loginname" class="form-control">
              </div>
                <label for="nick" class="col-sm-1 control-label text-right">昵称</label>
                <div class="col-sm-2">
                    <input type="text" id="nick" class="form-control">
                </div>
            </div>
              <div class="form-group">
              <label for="community_id" class="col-sm-1 control-label text-right">门店</label>
              <div class="col-sm-2">
                  <ui:simpleCommunitySelect id="community_id" header="全部"/>
              </div>
              <div class="col-sm-2">
                <button class="btn btn-primary" type="button" onclick="search()">查询</button>
              </div>
            </div>
          </form>
        </div>
      </div>
      <div class="ibox float-e-margins">
        <div class="ibox-content">
            <div class="btn-group">
              <button type="button" class="btn btn-outline btn-default" onclick="showAddWin()">
                <i class="glyphicon glyphicon-plus" aria-hidden="true"></i>
              </button>
            </div>
          <div id="qxUserTable" class="dt-grid-container"></div>
          <div id="qxUserToolBar" class="dt-grid-toolbar-container"></div>

        </div>
      </div>
    </div>
  </div>
</div>

<!-- 更改用户小区模态窗口 -->
<div class="modal fade" id="editWin" tabindex="-1" role="dialog" >
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">更改用户门店</h4>
      </div>
      <div class="modal-body">
        <form role="form" class="form-horizontal" id="editForm">
          <input type="hidden" id="user_id_edit">
          <input type="hidden" id="cid_before_edit">
          <div class="form-group">
            <label for="loginname_edit" class="col-md-4 control-label text-right">用户名</label>
            <div class="col-md-5">
              <p class="form-control-static" id="loginname_edit"/>
            </div>
          </div>
          <div class="form-group">
            <label for="cname_edit" class="col-md-4 control-label text-right">原门店</label>
            <div class="col-md-5">
              <p class="form-control-static" id="cname_edit"/>
            </div>
            </div>
          <div class="form-group">
            <label for="cid_after_edit" class="col-md-4 control-label text-right">修改门店</label>
            <div class="col-md-5">
                <ui:simpleCommunitySelect id="cid_after_edit"/>
            </div>
          </div>
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" onclick="editUser()">保存</button>
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
      </div>
    </div>
  </div>
</div>

<!-- 新增用户模态窗口 -->
<div class="modal fade" id="addWin" tabindex="-1" role="dialog" >
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">新增用户</h4>
      </div>
      <div class="modal-body">
        <form role="form" class="form-horizontal" id="addForm">
          <div class="form-group">
            <label for="loginname_add" class="col-md-4 control-label text-right">手机号</label>
            <div class="col-md-5">
              <input type="text" class="form-control" id="loginname_add" data-rule="手机号:required;">
            </div>
          </div>
          <div class="form-group">
            <label for="cid_add" class="col-md-4 control-label text-right">门店</label>
            <div class="col-md-5">
                <ui:simpleCommunitySelect id="cid_add"/>
            </div>
          </div>
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" onclick="addUser()">保存</button>
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
      </div>
    </div>
  </div>
</div>

<script type="text/javascript">
  var gridColumn = [
    {id:'user_id', title:'用户ID', type:'string', columnClass:'text-center'},
    {id:'loginname', title:'用户名', type:'string', columnClass:'text-center'},
    {id:'phone', title:'手机号', type:'string', columnClass:'text-center', hideType:'xs'},
    {id:'addr', title:'住址', type:'string', columnClass:'text-center', hideType:'xs'},
    {id:'createTime', title:'注册时间', type:'date', format:'yyyy-MM-dd hh:mm', columnClass:'text-center', hideType:'xs'},
    {id:'sub_cname', title:'门店', type:'string', columnClass:'text-center', hideType:'xs'},
    {id:'operation', title:'操作', type:'string', columnClass:'text-center', hideType:'xs',
      resolution:function(value, record, column, grid, dataNo, columnNo){
      return '<button class="btn btn-primary" onclick="showEditWin(' + record.user_id + ',\'' + record.loginname + '\',' + record.sub_cid + ',\'' + record.sub_cname + '\')">更改门店</button>';
    }}

  ];
  var gridOption = {
    ajaxLoad : true,
    loadURL : '${ctx}/qxuser.do?method=listV4',
    columns : gridColumn,
    gridContainer : 'qxUserTable',
    toolbarContainer : 'qxUserToolBar',
    tools : ''
  };

  var grid = $.fn.DtGrid.init(gridOption);

  $(function(){
    grid.load();
  });

  //自定义查询
  function search(){
    var community_id = $('#community_id').val();
    var phone = $('#phone').val();
    var loginname = $('#loginname').val();
    var nick = $('#nick').val();
    grid.parameters = new Object();
    grid.parameters['community_id'] = community_id;
    grid.parameters['phone'] = phone;
    grid.parameters['loginname'] = loginname;
    grid.parameters['nick'] = nick;
    grid.loadToFirst();
  }

  function showEditWin(user_id, loginname, cid, cname) {
    $('#user_id_edit').val(user_id);
    $('#loginname_edit').text(loginname);
    $('#cname_edit').text(cname);
    $('#cid_before_edit').val(cid);
    $('#cid_after_edit').val(cid);
    $('#editWin').modal({
      backdrop: 'static'  //点击窗体外不会关闭窗口
    })
  }

  function showAddWin() {
    $('#user_id_add').val('');
    $('#addWin').modal({
      backdrop: 'static'  //点击窗体外不会关闭窗口
    })
  }

  function editUser() {
    var user_id = $('#user_id_edit').val();
    var cid_before = $('#cid_before_edit').val();
    var cid_after = $('#cid_after_edit').val();

    if(user_id != null && user_id != '' && cid_before != null && cid_after != '' && cid_after != null && cid_after != '') {
      if(cid_before == cid_after) {
        layer.alert("用户门店未改变！");
      } else {
        var i = layer.load();
        $.post('${ctx}/qxuser.do?method=changeCommunity', {user_id : user_id, cid : cid_after}, function(data) {
          layer.close(i);
          if(data != null && data == 'ok') {
            layer.alert("修改成功！");
            $('#editWin').modal('hide');
            grid.load();
          } else {
            layer.alert("修改失败！");
          }
        });
      }
    } else {
      layer.alert("参数缺失，保存失败！");
    }
  }

  function addUser() {
    var loginname = $('#loginname_add').val();
    var cid = $('#cid_add').val();

    if($('#addForm').isValid()) {
      if(layer.confirm("确定要新增用户吗？", function(index) {
            var i = layer.load();
            $.post('${ctx}/qxuser.do?method=createUserV4', {username : loginname, cid : cid}, function (data) {
              layer.close(i);
              if(data != null && data != '') {
                var json = eval('(' + data + ')');
                if(json.result_code == 0) {
                  layer.alert("新增成功");
                  $('#addWin').modal('hide');
                  grid.load();
                } else {
                  layer.alert(json.result_dec);
                }
              }
            });
      }));
    }
  }
</script>

</body>

</html>