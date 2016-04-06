/**
 * 定位服务
 *
 * */
var map = null;// 地图
var geolocation = null;//定位
var geocoder = null;//地图坐标转码
var advancedInfoWindow = null; //高级信息窗体
var bounds = null; //当前地图视图范围

function mapInit() {
    //默认定位：初始化加载地图时，center及level属性缺省，地图默认显示用户所在城市范围
    map = new AMap.Map('container', {
        zoom: 14
    });
    var mapCity = "0516";
    map.getCity(function (result) {
        console.info(result.citycode);
        mapCity = result.citycode;
    });
    map.plugin(['AMap.Geolocation', 'AMap.Geocoder', 'AMap.AdvancedInfoWindow'], function () {
        geolocation = new AMap.Geolocation({
            enableHighAccuracy: true,//是否使用高精度定位，默认:true
            timeout: 10000,          //超过10秒后停止定位，默认：无穷大
            maximumAge: 0,           //定位结果缓存0毫秒，默认：0
            convert: true,           //自动偏移坐标，偏移后的坐标为高德坐标，默认：true
            showButton: true,    //显示定位按钮，默认：true
            buttonOffset: new AMap.Pixel(10, 20)
        });

        geocoder = new AMap.Geocoder({
            city: "025",
            radius: 1000,
            extensions: "all"
        });
        advancedInfoWindow = new AMap.AdvancedInfoWindow({
            offset: new AMap.Pixel(0, -28)
        });
        AMap.event.addListener(geolocation, 'complete', onComplete);//返回定位信息
        AMap.event.addListener(geolocation, 'error', onError);      //返回定位出错信息
    });
    map.addControl(geolocation);
}

/**
 *获取当前位置信息
 */
function getCurrentPosition() {
    geolocation.getCurrentPosition();
}

/**
 *解析定位结果
 */
function onComplete(data) {
    lng = data.position.getLng();
    lat = data.position.getLat();
    var lnglatXY = [lng, lat];
    var url = ctx + "/qxxCtrl.do?method=insertMgruserdistri_worker_addr";
    //逆地理编码
    geocoder.getAddress(lnglatXY, function (status, result) {
        if (status === 'complete' && result.info === 'OK') {
            var address = result.regeocode.formattedAddress;
            $$.ajax({
                type: 'post',
                url: url,
                data: {lng: lng, lat: lat, address: address},
                success: function () {
                    console.log('ok');
                }
            });
        }
    });
}

/**
 *地理编码,返回地理编码结果
 */
function addressCconversion(data, params) {
    for (var i in data) {
        geocoder.getLocation(data[i], function (status, result) {
            AMap.event.addListener(geolocation, 'complete', geocoder_CallBack(status, result, params)); //监听成功事件
        });
    }
}

function geocoder_CallBack(status, result, params) {
    var resultStr = [];
    if (status === 'complete' && result.info === 'OK') {
        //地理编码结果数组
        var geocode = result.geocodes;
        for (var i = 0; i < geocode.length; i++) {
            //拼接坐标
            resultStr.push([geocode[i].location.getLng(), geocode[i].location.getLat()]);
        }
        orderMap(resultStr, params);
    }
}

/**
 *解析定位错误信息
 */
function onError(data) {
    var str = '<p>定位失败</p>';
    str += '<p>错误信息：';
    switch (data.info) {
        case 'PERMISSION_DENIED':
            str += '浏览器阻止了定位操作';
            break;
        case 'POSITION_UNAVAILBLE':
            str += '无法获得当前位置';
            break;
        case 'TIMEOUT':
            str += '定位超时';
            break;
        default:
            str += '未知错误';
            break;
    }
    str += '</p>';
    myApp.alert(str);
}
var markers = null;
function orderMap(params, data) {
    var LngLat = params;
    bounds = map.getBounds(); //获取当前地图视图范围
    if (LngLat != null && LngLat.length > 0) {
        for (var i = 0; i < LngLat.length; i++) {
            var markersPosition = LngLat[i];
            markers = new AMap.Marker({
                position: markersPosition
            });
            markers.setMap(map);
            markers.content =
                '<div class="info-title">区享</div><div class="info-content">' +
                '<img src="">' + '订单号:' + data[i]['order_serial'] + '<br/>' + '昵称:' + data[i]['nick'] + '<br/>' + '手机号' + data[i]['phone'] +
                '</div>';
            markers.on('click', markerClick);
            //markers.emit('click', {target: markers});
        }
    }
    function markerClick(e) {
        advancedInfoWindow.setContent(e.target.content);
        advancedInfoWindow.open(map, e.target.getPosition());
    }

    map.setFitView();
}