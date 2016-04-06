/* 通用 数据列表 */
var List = function (page) {
    var name = page.name;
    var temp = 'get' + name.replace(name[0], name[0].toUpperCase()) + "List";
    var url = ctx + "/qxxCtrl.do?method=" + temp;
    var success = function (response) {
        var obj = JSON.parse(response);
        if (obj.data.model.length > 0 && obj.result_dec == 'OK') {
            var context = context = eval("(" + response + ")").data;
            for (var obj in context.model) {
                if (name == 'notWeigh' || name == 'weighing' || name == 'finishWeigh') {
                    if (context.model[obj]['createTime'] != null) {
                        context.model[obj]['createTime'] = moment(context.model[obj].createTime['time']).format('YYYY-MM-DD HH:mm:ss');
                    }
                } else {
                    if (context.model[obj]['order_time'] != null) {
                        context.model[obj]['order_time'] = moment(context.model[obj].order_time['time']).format('YYYY-MM-DD HH:mm:ss');
                    }
                }
            }
            if ($$('.centered').html() != 'undefined') $$('.centered').remove();
            var template = $$('#' + name).html();
            var compiledListTemplate = Template7.compile(template);
            var html = compiledListTemplate(context);
            $$('#' + name + 'ls').append(html);
        } else if (obj.result_dec == 'ERROR') {
            var str = '<div style="text-align: center;" class="conten-block centered">' +
                '<img src="new/jsp/newQxx/ico/no-cart.ico"/><br/>' +
                '数据加载错误！' +
                '</div>';
            $$('#' + name + 'ls').append(str);
        } else {
            var str2 = '<div style="text-align: center;" class="content-block centered">' +
                '<img src="new/jsp/newQxx/ico/no-cart.ico"/><br/>' +
                '没有内容哦！' +
                '</div>';
            $$('#' + name + 'ls').append(str2);
        }
    };
    $$.post(url, null, success);
};

/*  通用 无限加载数据 */
function notOrderList(page) {
    var name = page.name;
    var loading = false;
    var lastLoadedIndex = $$('#' + name + 'ls' + ' .card').length;
    var url = "get" + name.replace(/(\w)/, function (v) {
            return v.toUpperCase()
        }) + "List";
    if (loading) return;    // 如果正在加载，则退出
    loading = true; // 设置flag
    $.ajax({    // 1s的加载过程
        type: 'POST',
        url: ctx + '/qxxCtrl.do?method=' + url,
        data: {leftIndex: lastLoadedIndex},
        dataType: 'json',
        async: false,
        success: function (data) {
            loading = false;// 重置加载flag
            if (data.result_dec == 'OK') {
                if (data.data.model.length == 0) {
                    myApp.detachInfiniteScroll($$('.infinite-scroll'));  // 加载完毕，则注销无限加载事件，以防不必要的加载
                    $$('.infinite-scroll-preloader').remove();   // 删除加载提示符
                    myApp.hidePreloader();
                    return false;
                }
                else {
                    var context = data.data;
                    for (var obj in context.model) {
                        if (name == 'notWeigh' || name == 'weighing' || name == 'finishWeigh') {
                            context.model[obj]['createTime'] = moment(context.model[obj].createTime['time']).format('YYYY-MM-DD HH:mm:ss');
                        } else {
                            context.model[obj]['order_time'] = moment(context.model[obj].order_time['time']).format('YYYY-MM-DD HH:mm:ss');
                        }
                    }
                    var template = $$('#' + name).html();
                    var compiledListTemplate = Template7.compile(template);
                    var html = compiledListTemplate(context);
                    $$('#' + name + 'ls').append(html);
                    lastLoadedIndex = $$('#' + name + 'ls' + ' .card').length;
                }
            }
        },
        error: function () {
            myApp.alert("加载失败！");
        }
    });
}

/* 通用详情数据获取 */
var xq = function (page) {
    var name = page.name;
    var order_serial = page.query.order_serial;
    var url = ctx + "/qxxCtrl.do?method=orderXq";
    var success = function (response) {
        var context = eval("(" + response + ")").data;
        var template = $$('#' + name.replace('List', '') + 'Xq').html();
        var compiledListTemplate = Template7.compile(template);
        var html = compiledListTemplate(context);
        $$('#' + name + 'Info').html(html);
    };
    $$.post(url, {order_serial: order_serial}, success);
};

// 工作台数量 数据获取
var getWorkbenchCount = function () {
    var url = ctx + "/qxxCtrl.do?method=getWorkbenchCount";
    var success = function (response) {
        var context = eval("(" + response + ")").data;
        var template = $$('#workbenchTemplate').html();
        var compiledListTemplate = Template7.compile(template);
        var html = compiledListTemplate(context);
        $$('#model').html(html);
    };
    $$.post(url, null, success);
};

/* 提货 自提 */
function set(params) {
    var str = params.split(',')[1];
    params = params.split(',')[0];
    var content = null;
    if (str == '1')
        content = '自提商品订单';
    else
        content = '确定配送完成';
    var buttonOk = [
        {
            text: content,
            label: true
        },
        {
            text: '确定',
            color: 'red',
            onClick: function () {
                myApp.showIndicator();
                $$.ajax({
                    type: 'post',
                    url: ctx + '/qxxCtrl.do?method=updateOrderStatus',
                    data: {params: params},
                    dataType: 'json',
                    success: function (data) {
                        myApp.alert("成功！", function () {
                            myApp.hideIndicator();
                            mainView.router.back();
                            myApp.pullToRefreshTrigger();
                        });

                    },
                    error: function (error) {
                        myApp.hideIndicator();
                        myApp.alert("失败");
                    }
                });
            }
        }
    ];
    var buttonNo = [
        {
            text: '取消',
            bold: true
        }
    ];
    myApp.actions([buttonOk, buttonNo]);
}

/* 送货 生成配送单 */
function acceptOrder(params) {
    var buttonOK = [
        {
            text: '生成配送单',
            label: true
        },
        {
            text: '确定',
            color: 'red',
            onClick: function () {
                myApp.showIndicator();
                $$.ajax({
                    type: 'post',
                    url: ctx + '/qxxCtrl.do?method=acceptOrder',
                    data: {params: params},
                    dataType: 'json',
                    success: function (data) {
                        myApp.hideIndicator();
                        if (data.result_code == 0) {
                            myApp.alert("接单成功！", function () {
                                mainView.router.back();
                                myApp.pullToRefreshTrigger();
                            });

                        }
                    },
                    error: function (error) {
                        myApp.hideIndicator();
                        myApp.alert("失败");
                    }
                });
            }
        }
    ];

    var buttonNo = [
        {
            text: '取消',
            bold: true
        }
    ];
    myApp.actions([buttonOK, buttonNo]);
}

/* 称重总价计算 */
function price(val, price) {
    $$('#zj').text("¥" + val.value * price);
}

/* 取消 称重 */
function weighCancel(params) {
    var buttonOK = [
        {
            text: '取消称重的商品',
            label: true
        },
        {
            text: '确定',
            color: 'red',
            onClick: function () {
                myApp.showIndicator();
                $$.ajax({
                    type: 'post',
                    url: ctx + '/qxxCtrl.do?method=weighCancel',
                    data: {params: params},
                    dataType: 'json',
                    success: function (data) {
                        if (data.data == '取消成功' && data.result_dec == 'OK') {
                            myApp.hideIndicator();
                            myApp.alert("取消成功!");
                            myApp.pullToRefreshTrigger();
                        }
                    },
                    error: function (error) {
                        myApp.hideIndicator();
                        myApp.alert("失败");
                    }
                });
            }
        }
    ];
    var buttonNo = [
        {
            text: "取消"
        }
    ];
    myApp.actions([buttonOK, buttonNo]);
}

/* 提交 称重 */
function weighOk(params) {
    var price = $$('#zj').html();
    var buttonOk = [
        {
            text: '提交',
            color: 'red',
            onClick: function () {
                var size = $$('#size').val();
                if (!/^(([0-9]+[\.]?[0-9]{1,2})|[1-9])$/.test(size) || size <= 0) {
                    myApp.alert('请准确输入称重斤两');
                } else {
                    myApp.showIndicator();
                    $$.ajax({
                        type: 'post',
                        url: ctx + '/qxxCtrl.do?method=weighOk',
                        data: {params: params, size: size},
                        dataType: 'json',
                        success: function (data) {
                            myApp.hideIndicator();
                            myApp.pullToRefreshTrigger();
                        },
                        error: function (error) {
                            myApp.hideIndicator();
                            myApp.alert("失败");
                        }
                    });
                }
            }
        }
    ];
    var buttonNo = [
        {
            text: '取消',
            bold: true
        }
    ];
    myApp.actions([buttonOk, buttonNo]);
}

/* 退出 */
var login_out = function () {
    myApp.confirm('', '确定退出吗?', function () {
        var url = ctx + '/qxxCtrl.do?method=login_out';
        var success = function (response) {
            myApp.loginScreen();
        };
        $$.post(url, null, success);
    });
};

var quHuo = function (params) {
    var buttonOk = [
        {
            text: ' 确定',
            color: 'red',
            onClick: function () {
                myApp.showIndicator();
                $$.ajax({
                    type: 'post',
                    url: ctx + '/qxxCtrl.do?method=quHuoOK',
                    data: {params: params},
                    dataType: 'json',
                    success: function (data) {
                        myApp.alert("成功！", function () {
                            myApp.hideIndicator();
                            mainView.router.back();
                            myApp.pullToRefreshTrigger();
                        });
                    },
                    error: function (error) {
                        myApp.hideIndicator();
                        myApp.alert("失败");
                    }
                });
            }
        }
    ];
    var buttonNo = [
        {
            text: '取消',
            bold: true
        }
    ];
    myApp.actions([buttonOk, buttonNo]);
};

function dataAMap(date) {
    var result = null;
    var url = ctx + '/qxxCtrl.do?method=selAMap';
    $$.ajax({
        async: false,
        data: {distri_staus: date},
        url: url,
        success: function (response) {
            var res = eval('('+response+')');
            if(res.data.length > 0 && res.result_dec == 'OK') {
                result = res.data;
            }
        }
    });
    return result;
}