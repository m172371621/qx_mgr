/**
 * 加法
 * */
function add(a, b) {
    var c, d, e;
    try {
        c = a.toString().split(".")[1].length;
    } catch (f) {
        c = 0;
    }
    try {
        d = b.toString().split(".")[1].length;
    } catch (f) {
        d = 0;
    }
    return e = Math.pow(10, Math.max(c, d)), (mul(a, e) + mul(b, e)) / e;
}

/**
 * 减法
 * */
function sub(a, b) {
    var c, d, e;
    try {
        c = a.toString().split(".")[1].length;
    } catch (f) {
        c = 0;
    }
    try {
        d = b.toString().split(".")[1].length;
    } catch (f) {
        d = 0;
    }
    return e = Math.pow(10, Math.max(c, d)), (mul(a, e) - mul(b, e)) / e;
}

/**
 * 乘法
 * */
function mul(a, b) {
    var c = 0,
        d = a.toString(),
        e = b.toString();
    try {
        c += d.split(".")[1].length;
    } catch (f) {}
    try {
        c += e.split(".")[1].length;
    } catch (f) {}
    return Number(d.replace(".", "")) * Number(e.replace(".", "")) / Math.pow(10, c);
}

/**
 * 除法
 * */
function div(a, b) {
    var c, d, e = 0,
        f = 0;
    try {
        e = a.toString().split(".")[1].length;
    } catch (g) {}
    try {
        f = b.toString().split(".")[1].length;
    } catch (g) {}
    return c = Number(a.toString().replace(".", "")), d = Number(b.toString().replace(".", "")), mul(c / d, Math.pow(10, f - e));
}


/**
 * 图片上传
 * */
function jq_upload(path,file_id,hidden_id) {
    var url = pic_url;
    $.ajaxFileUpload({
        url:url,            //需要链接到服务器地址
        secureuri:url,
        fileElementId:file_id,  //文件选择框的id属性
        data:{to_path:path},
        success: function(data, status) {
            var results = $(data).text();
            results = results.substring(results.indexOf('{'));
            var obj = eval("("+results+")");
            $("#"+hidden_id).val(obj.data.new_file);
            layer.msg('文件上传成功！');
        },
        error: function (data, status, e){
            layer.msg('文件上传失败！');
        }
    });
}

function uploadBase64(data, to_path, hidden_id) {
    var i = layer.load();
    $.ajax({
        url : 'http://www.qxit.com.cn/scframe/common/uploadBase64/',
        type : 'post',
        data : {data : data, to_path : to_path},
        success : function(data) {
            var results = $(data).text();
            results = results.substring(results.indexOf('{'));
            var obj = eval("("+results+")");
            $("#"+hidden_id).val(obj.data.new_file);
            layer.msg('图片上传成功！');
        },
        error: function (data, status, e){
            layer.msg('图片上传失败！');
        },
        complete : function(data) {
            layer.close(i);
        }
    });
}
