var myApp = new Framework7({

    // 如果你开发web app（而不是通过PhoneGap封装的混合应用)，那么这个功能将很有用（浏览器的URL将会看上去像这样 "http://my-webapp.com/#/about.html")。用户可以通过浏览器默认的前进后退按钮来操作。
    pushState: false,
    // 开启/关闭滑动返回上一页功能。
    swipeBackPage: false,
    //侧栏滑动开启 不写默认关闭
    swipePanel: 'right',
    // 默认的标题 (Alert, Confirm, Prompt)
    modalTitle: '提示',
    // 确定按钮的默认文案
    modalButtonOk: '确定',
    // 取消按钮的默认文案
    modalButtonCancel: '取消',
    // 	默认的加载提示文案
    modalPreloaderTitle: '加载中...',
    // 设置为true，那么当页面滚动到底部的时候会自动显示出被隐藏的导航栏和工具栏。
    showBarsOnPageScrollEnd: true,
    // 设置为true，那么当页面向下滚动的时候，导航栏会自动隐藏；向上滚动的时候会自动出现。
    hideNavbarOnPageScroll: true,
    // 设置为true，那么当页面向下滚动的时候，工具栏会自动隐藏；向上滚动的时候会自动出现。
    hideToolbarOnPageScroll: true,
    // 页面滚动时，自动隐藏带图标的工具栏（tabbar)
    // hideTabbarOnPageScroll: true,

    // 当启用这个功能时，动态导航栏中的 back-link 图标的动画会更接近iOS的风格。只有当你使用动态导航栏并且使用了默认的 back-link 图标时才应该把这个值设置为 "sliding"
    animateNavBackIcon: true,
    // 页面导航栏中的返回按钮的文案
    smartSelectBackText: '返回',
    // 所有的 Smart Selects 打开/关闭搜索功能。
    smartSelectSearchbar: true,
    // 预加载下一页
    preloadPreviousPage: true,
    // 当Ajax请求开始的时候调用，这个函数会传递一个 xhr 对象作为参数。
    onAjaxStart: function (xhr) {
        myApp.showIndicator();
    },
    // Ajax 请求结束的时候调用。会传递一个xhr对象作为参数。
    onAjaxComplete: function (xhr) {
        myApp.hideIndicator();
    },
    // 回调函数，当页面全部初始化完成之后并且准备好开始做动画的时候会调用此回调
    onPageBeforeAnimation: function () {
    },
    // 这个回调函数可以用来阻止路由器默认的 加载/返回 行为，你可以自己去加载其他页面，重定向，或者做任意你需要的操作。
    preroute: function (view, options) {
        return true;
    },
    // Template7 来渲染 ajax或者动态生成的页面
    //template7Pages: true,
    //  自动编译所有的 Template7 模板
    precompileTemplates: true
});

var $$ = Dom7;

/* Initialize views */
var mainView = myApp.addView('.view-main', {
    dynamicNavbar: true
});

//myApp.addView('.view-right', {
//    dynamicNavbar: true
//});

var mySearchbar = null;
/* Page handler */
$$(document).on('pageInit', function (e) {
    var page = e.detail.page;
    if (page.name == 'deliveryOrder') { //配送中列表
        pageData(page);
    } else if (page.name == 'notOrder') { // 未接单列表
        pageData(page);
    } else if (page.name == 'finishOrder') { //完成订单
        pageData(page);
    } else if (page.name == 'notWeigh') { //未称重
        pageData(page);
    } else if (page.name == 'weighing') { // 称重中列表
        pageData(page);
    } else if (page.name == 'finishWeigh') { // 称重完成列表
        pageData(page);
    } else if (page.name == 'quHuoOrder') {
        pageData(page);
    } else if (page.name == 'services') {
        // 初始化 searchbar
        mySearchbar = myApp.searchbar('.searchbar', {
            searchList: '.list-block',
            found: 'searchbar-found',
            nofund: 'searchbar-not-found',
            onSearch: function () { // 搜索栏 异步加载所搜内容
                var url = ctx + '/qxxCtrl.do?method=searchPage';
                var val = mySearchbar.input.val();
                if (val == "") return;
                myApp.showIndicator();
                var success = function (response) {
                    console.log(response);
                    var context = eval("(" + response + ")").data;
                    var template = $$('#searchbarData').html();
                    var compiledListTemplate = Template7.compile(template);
                    var html = compiledListTemplate(context);
                    $$('#searchbarUl').html(html);
                    myApp.hideIndicator();
                };
                $$.post(url, {val: val}, success);
            },
            onClear: function () {
                //alert('1');
            },
            onDisable: function () {
                var str = '<li class="item-content">' +
                    '<div class="item-inner">' +
                    '<div class="item-title">1231</div>' +
                    '</div>' +
                    '</li>';
                $$('#searchbarUl').html(str);
            },
            onEnable: function() {
                alert('onEnable');
            }
        });
    } else {

    }
});

//底部工具栏点击切换试图
$$('#bottomTool .toolbar-inner a').on('click', function () {
    $$(this).parent().children('a').removeClass('active');
    $$(this).addClass('active');
});

$$('.popup').on('closed', function () {
    bounds = null;
    map.clearMap();  // 清除地图覆盖物
});

// 登陆成功后 关闭登陆屏(关闭时)监听事件
$$('.login-screen').on('close', function () {
    getWorkbenchCount();
});

// 登陆
$$('#loginqxx').on('click', function () {
    var code = $$('#code').val();
    var username = $$('.login-screen input[name="username"]').val();
    var password = $$('.login-screen input[name="password"]').val();
    if (username == '' || password == '') {
        myApp.alert("用户名和密码不能为空！");
        return false;
    } else {
        $.ajax({
            type: 'POST',
            url: ctx + '/qxxCtrl.do?method=login_validate2',
            data: {username: username, password: password, code: code},
            dataType: 'json',
            success: function (data) {
                if (data.result_dec == 'OK') {
                    mainView.router.loadPage(ctx + '/new/jsp/newQxx/workbench.jsp');
                    myApp.closeModal('.login-screen');
                    try {
                        mapInit();
                        getCurrentPosition();
                        setInterval("getCurrentPosition()", 1000 * 60 * 10);//1000为1秒钟
                    } catch (error) {
                        myApp.alert("定位初始化失败!");
                        mapInit();
                        getCurrentPosition();
                    } finally {
                        setInterval("getCurrentPosition()", 1000 * 60 * 10);//1000为1秒钟
                    }

                } else {
                    myApp.alert(data.data);
                }
            },
            error: function () {
                myApp.alert("登陆失败！");
            }
        });
    }
});
// 展开去称重的表单
myApp.onPageAfterAnimation('weighing', function () {
    // 展开去称重
    if ($$('#weighingls .card').length > 0) {
        myApp.accordionOpen('.accordion-item');
    }
});

// 工作台页面
myApp.onPageInit('workbench', function (page) {
    $$('#workbench').addClass('active');
    getWorkbenchCount();
});
// 未接单详情
myApp.onPageInit('notOrderList', function (page) {
    xq(page);
});
// 配送中详情
myApp.onPageInit('deliveryOrderList', function (page) {
    xq(page);
});

// 取货详情
myApp.onPageInit('quHuoOrderList', function (page) {
    xq(page);
});

// 已完成详情
myApp.onPageInit('finishOrderList', function (page) {
    xq(page);
});

/*  通用数据加载 下拉刷新 无限加载 */
var pageData = function (page) {
    // 注册'infinite'事件处理函数
    $$('.infinite-scroll').on('infinite', function () {
        notOrderList(page);
    });
    // 下拉刷新页面 添加'refresh'监听器
    $$('.pull-to-refresh-content').on('refresh', function (e) {
        var name = '#' + page.name + 'ls .card';
        setTimeout(function () {
            $$(name).remove();
            //$$('#finishWeighls .card').remove();
            List(page);
            // 加载完毕需要重置
            myApp.pullToRefreshDone();
            myApp.attachInfiniteScroll($$('.infinite-scroll'));
        }, 1000);
    });
    List(page);
};

// 配送坐标
function popupDelivery(data) {
    var address = dataAMap(data);
    addressCconversion(mapArray(address), address);
    myApp.popup('.orderMap');
}

// 取货坐标
function popupQuHuo(data) {
    var address = dataAMap(data);
    addressCconversion(mapArray(address), address);
    myApp.popup('.orderMap');
}

function mapArray(date) {
    var array = [];
    for (var i in date) {
        array.push(date[i]['delivery_addr']);
    }
    return array;
}