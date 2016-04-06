<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/new/jsp/include/resource.jsp" %>
<html>

<head>
    <link href="${ctx}/new/js/plugins/shearphoto_common/css/ShearPhoto.css" rel="stylesheet" type="text/css" media="all">
    <script type="text/javascript" src="${ctx}/new/js/plugins/shearphoto_common/js/ShearPhoto.js" ></script>
    <!--设置和处理对象方法的JS文件，要修改设置，请进入这个文件-->
    <%--<script type="text/javascript" src="${ctx}/new/js/plugins/shearphoto_common/js/handle.js" ></script>--%>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>照片裁剪DEMO</h5>
                </div>
                <div class="ibox-content">
                    <form role="form" class="form-horizontal">
                        <div class="form-group">
                            <label for="ad_name" class="col-sm-1 control-label text-right">名称</label>
                            <div class="col-sm-2">
                                <input type="text" id="ad_name" class="form-control">
                            </div>
                            <label for="service_type" class="col-sm-1 control-label text-right">类型</label>
                            <div class="col-sm-2">
                                <select id="service_type" class="form-control">
                                    <option value="">全部</option>
                                    <option value="11">商品广告</option>
                                    <option value="12">订单广告</option>
                                </select>
                            </div>
                            <label class="col-sm-1 control-label text-right">门店</label>
                            <div class="col-sm-2">
                                <ui:simpleCommunitySelect id="community_id" header="全部"/>
                            </div>
                            <div class="col-sm-2">
                                <button class="btn btn-primary" type="button" id="queryBtn" onclick="openWin()">上传</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>

            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <div class="btn-group">
                        <button type="button" class="btn btn-outline btn-default" onclick="addAd()">
                            <i class="glyphicon glyphicon-plus" aria-hidden="true"></i>
                        </button>
                        <button type="button" class="btn btn-outline btn-default" onclick="removeAdBatch()">
                            <i class="glyphicon glyphicon-trash" aria-hidden="true"></i>
                        </button>
                    </div>
                    <div id="table" class="dt-grid-container"></div>
                    <div id="toolBar" class="dt-grid-toolbar-container"></div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="editWin" tabindex="-1" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="title_edit">上传图片</h4>
            </div>
            <div class="modal-body">
                <!--主功能部份 主功能部份的标签请勿随意删除，除非你对shearphoto的原理了如指掌，否则JS找不到DOM对象，会给你抱出错误-->
                <div id="shearphoto_loading">程序加载中......</div><!--这是2.2版本加入的缓冲效果，JS方法加载前显示的等待效果-->
                <div id="shearphoto_main">
                    <!--primary范围开始-->
                    <div class="primary">
                        <!--main范围开始-->
                        <div id="main">
                            <div class="point">
                            </div>
                            <!--选择加载图片方式开始-->
                            <div id="SelectBox">
                                <form id="ShearPhotoForm" enctype="multipart/form-data" method="post"  target="POSTiframe">
                                    <input name="shearphoto" type="hidden" value="我要传参数" autocomplete="off"> <!--示例传参数到服务端，后端文件UPLOAD.php用$_POST['shearphoto']接收,注意：HTML5切图时，这个参数是不会传的-->
                                    <a href="javascript:;" id="selectImage"><input type="file"  name="UpFile" autocomplete="off"/></a>
                                </form>
                                <%--<a href="javascript:;" id="PhotoLoading"></a>
                                <a href="javascript:;" id="camerasImage"></a>--%>
                            </div>
                            <!--选择加载图片方式结束--->
                            <div id="relat">
                                <div id="black">
                                </div>
                                <div id="movebox">
                                    <div id="smallbox">
                                        <img src="shearphoto_common/images/default.gif" class="MoveImg" /><!--截框上的小图-->
                                    </div>
                                    <!--动态边框开始-->
                                    <i id="borderTop">
                                    </i>

                                    <i id="borderLeft">
                                    </i>

                                    <i id="borderRight">
                                    </i>

                                    <i id="borderBottom">
                                    </i>
                                    <!--动态边框结束-->
                                    <i id="BottomRight">
                                    </i>
                                    <i id="TopRight">
                                    </i>
                                    <i id="Bottomleft">
                                    </i>
                                    <i id="Topleft">
                                    </i>
                                    <i id="Topmiddle">
                                    </i>
                                    <i id="leftmiddle">
                                    </i>
                                    <i id="Rightmiddle">
                                    </i>
                                    <i id="Bottommiddle">
                                    </i>
                                </div>
                                <img src="shearphoto_common/images/default.gif" class="BigImg" /><!--MAIN上的大图-->
                            </div>
                        </div>
                        <!--main范围结束-->
                        <div style="clear: both"></div>
                        <!--工具条开始-->
                        <div id="Shearbar">
                            <a id="LeftRotate" href="javascript:;">
                                <em>
                                </em>
                                向左旋转
                            </a>
                            <em class="hint L">
                            </em>
                            <div class="ZoomDist" id="ZoomDist">
                                <div id="Zoomcentre">
                                </div>
                                <div id="ZoomBar">
                                </div>
                        <span class="progress">
                        </span>
                            </div>
                            <em class="hint R">
                            </em>
                            <a id="RightRotate" href="javascript:;">
                                向右旋转
                                <em>
                                </em>
                            </a>
                            <p class="Psava">
                                <a id="againIMG"  href="javascript:;">重新选择</a>
                                <a id="saveShear" href="javascript:;">保存截图</a>
                            </p>
                        </div>
                        <!--工具条结束-->
                    </div>
                    <!--primary范围结束-->
                    <div style="clear: both"></div>
                </div>
                <!--shearphoto_main范围结束-->

                <!--主功能部份 主功能部份的标签请勿随意删除，除非你对shearphoto的原理了如指掌，否则JS找不到DOM对象，会给你抱出错误-->
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="saveUser()">保存</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    function openWin() {
        $('#editWin').modal();
    }

    window.ShearPhoto.MINGGE(function() {
        var relativeUrl= "shearphoto_common";
        relativeUrl = relativeUrl.replace(/(^\s*)|(\s*$)/g, "");//去掉相对路径的所有空格
        relativeUrl === "" || (relativeUrl += "/");//在相对地址后面加斜框，不需要用户自己加
        var publicRelat= document.getElementById("relat");     //"relat"对像
        var publicRelatImg=publicRelat.getElementsByTagName("img");  //"relat"下的两张图片对像
        var Shear = new ShearPhoto;
        Shear.config({
            /*---------------用户设置部份开始-----------------------------------------------------------------------*/
            relativeUrl:relativeUrl,  //取回相对路径，不懂原理的话，你不要改动哦，否则你又鸡巴痛了
            traverse:true,//可选 true,false 。 是否在拖动或拉伸时允许历遍全图（是否让大图动呢）,
            translate3d:false,
            HTML5:true,
            HTML5MAX:500, //默认请设0 (最大尺寸做事)， HTML上传截图最大宽度， 宽度越大，HTML5截出来的图片容量越大，服务器压力就大，截图就更清淅！ 设得越小 HTML5截出来的图片容量越小.但是造成一定程序的不清淅，请适量设置 当然开启HTML5切图，该设置才有效
            HTML5Quality:0.9,
            HTML5FilesSize:50,
            HTML5Effects:false,
            HTML5ZIP:[900,0.9],
            preview:false,
            url:"${ctx}/order.do?method=upload",   //后端处理地址，保证正确哦，这是常识，连这个地址都能写错，你就是菜B，已经在本版本中帮你加入相对路径，你基本不用改这里了

            scopeWidth:500,                 //可拖动范围宽  也就是"main"对象的初始大小(整数型，禁止含小数点) 宽和高的值最好能一致

            scopeHeight:500,                //可拖动范围高  也就是"main"对象的初始大小(整数型，禁止含小数点) 宽和高的值最好能一致

            proportional:[1/1,               <!--截框的宽高比例（宽除以高的比例值，这个设置其实就是1，对！你可以直接写1  如填3/4 那么就是0.75的比例,不设比例请设为0，注意更改比例后，后端也要进行相应设置，否则系统会给你抱出错误-->
                /*
                 2.3版本加了一个新API ，动态修改比例！接口示例：Shear.SetProportional(3/4);  意思就是：动态修改比例为3/4;
                 */
                100,                      //必须整数！启动后的截框初始宽度(整数型，禁止含小数点)

                133                       //比例设置后，这个高度无效，由宽和比例来决定(整数型，禁止含小数点)
            ],

            Min:50,                 //截框拉伸或拖拽不能少于多少PX(整数型，禁止含小数点)

            Max:500,                //一开始启动时，图片的宽和高，有时候图片会很大的，必须要设置一下(整数型，禁止含小数点)，尽可能和scopeWidth值 一致

            backgroundColor:"#000",   //遮层色

            backgroundOpacity:0.6, //遮层透明度-数字0-1 可选

            Border:0,               //截框的边框大小 0代表动态边框。大于0表示静态边框，大于0时也代表静态边框的粗细值

            BorderStyle:"solid",    //只作用于静态边框，截框的边框类型，其实是引入CSS的border属性，和CSS2的border属性是一样的

            BorderColor:"#09F",  //只作用于静态边框，截框的边框色彩
            /*---------------用户设置截图功能部份..还没结束----------------------页面下面还有一些细节设置，去看一下-------------------------------------------------*/
            relat:publicRelat,              //请查看 id:"relat"对象
            scope:document.getElementById("main"),//main范围对象
            ImgDom:publicRelatImg[0],         //截图图片对象（小）
            ImgMain:publicRelatImg[1],         //截图图片对象（大）
            black:document.getElementById("black"),//黑色遮层对象
            form:document.getElementById("smallbox"),//截框对象
            ZoomDist:document.getElementById("ZoomDist"),//放大工具条,可从HTML查看此对象，不作详细解释了
            ZoomBar:document.getElementById("ZoomBar"), //放大工具条，可从HTML查看此对象
            to:{
                BottomRight:document.getElementById("BottomRight"),//拉伸点中右
                TopRight:document.getElementById("TopRight"),//拉伸点上右，下面如此类推，一共8点进行拉伸,下面不再作解释
                Bottomleft:document.getElementById("Bottomleft"),
                Topleft:document.getElementById("Topleft"),
                Topmiddle:document.getElementById("Topmiddle"),
                leftmiddle:document.getElementById("leftmiddle"),
                Rightmiddle:document.getElementById("Rightmiddle"),
                Bottommiddle:document.getElementById("Bottommiddle")
            },
            Effects:document.getElementById("shearphoto_Effects") || false,
            DynamicBorder:[document.getElementById("borderTop"),document.getElementById("borderLeft"),document.getElementById("borderRight"),document.getElementById("borderBottom")],
            SelectBox:document.getElementById("SelectBox"),         //选择图片方式的对象
            Shearbar:document.getElementById("Shearbar"),          //截图工具条对象
            UpFun:function() {                   //鼠标健松开时执行函数
                Shear.MoveDiv.DivWHFun();   //把截框现时的宽高告诉JS
            }

        });
        /*--------------------------------------------------------------截图成功后，返回来的callback-------------------------*/
        Shear.complete=function(serverdata) {//截图成功完成时，由shearphoto.php返回数据过来的成功包
            // alert(serverdata);//你可以调试一下这个返回包
            var point = this.arg.scope.childNodes[0];
            point.className === "point" && this.arg.scope.removeChild(point);
            var complete = document.createElement("div");
            complete.className = "complete";
            complete.style.height = this.arg.scopeHeight + "px";
            this.arg.scope.insertBefore(complete, this.arg.scope.childNodes[0]);
            var length = serverdata.length,creatImg;
            for (var i = 0; i < length; i++) {
                creatImg = document.createElement("img");
                complete.appendChild(creatImg);
                creatImg.src=this.arg.relativeUrl + serverdata[i]["ImgUrl"];
            }
            this.HTML5.EffectsReturn();
            this.HTML5.BOLBID	&&   this.HTML5.URL.revokeObjectURL(this.HTML5.BOLBID);
            creatImg = document.createElement("DIV");
            creatImg.className=	"completeTxt";
            creatImg.innerHTML='<strong><i></i>恭喜你！截图成功</strong> <p>以上是你图片的' + length + '种尺寸</p><a href="javascript:;" id="completeA">完成</a>';
            complete.appendChild(creatImg);
            var completeA = document.getElementById("completeA");
            var this_ = this;
            this_.preview.close_();
            completeA.onclick || (completeA.onclick = function() {
                completeA.onclick = null;
                this_.arg.scope.removeChild(complete);
                this_.again();
                this_.pointhandle(3e3, 10, "截图完成！已返回！", 2, "#fbeb61", "#3a414c");
            });
        }
        /*--------------------------------------------------------------截图成功后，返回来的callback-------------------------*/
        var photoalbum = document.getElementById("photoalbum");//相册对象


        /*.................................................选择图片上传的设置...............................................................*/

        var ShearPhotoForm = document.getElementById("ShearPhotoForm");//FORM对象
        ShearPhotoForm.UpFile.onclick=function(){return false}//一开始时先不让用户点免得事件阻塞
        var up = new ShearPhoto.frameUpImg({

            url:relativeUrl+"php/upload.php",            //HTML5切图时，不会用到该文件，后端处理地址，保证正确哦，这是常识，连这个地址都能写错，你就是菜B，已经在本版本中帮你加入相对路径，你基本不用改这里了

            FORM:ShearPhotoForm,                         //FORM对象传到设置

            UpType:new Array("jpg", "jpeg", "png", "gif"),//图片类限制，上传的一定是图片，你就不要更改了

            FilesSize:2,                             //选择的图片不能超过 单位M（注意：是非HTML5时哦）

            HTML5:Shear.HTML5,                       //切匆改动这句，不然你他妈又问为什么出错

            HTML5FilesSize:Shear.arg.HTML5FilesSize,//切匆改动这句 如果是HTML5切图时，选择的图片不能超过 单位M，设太大话，如果客户端HTML5加截超大图片时，会卡爆的

            HTML5ZIP:Shear.arg.HTML5ZIP,      //切匆改动这句, 把压缩设置转移到这里

            erro:function(msg) {
                Shear.pointhandle(3e3, 10, msg, 0, "#f82373", "#fff");
            },
            fileClick:function(){//先择图片被点击时，触发的事件
                Shear.pointhandle(-1);//关闭提示，防止线程阻塞事件冒泡
            },
            preced:function(fun) { //点击选择图，载入图片时的事件
                try{
                    photoalbum.style.display = "none"; //什么情况下都关了相册
                    camClose.onclick(); //什么情况下都关了视频
                }catch (e){console.log("在加载图片时，发现相册或拍照的对象检测不到，错误代码："+e);}
                Shear.pointhandle(0, 10, "正在为你加载图片，请你稍等哦......", 2, "#307ff6", "#fff",fun);
            }
        });

        up.run(function(data,True) {//upload.php成功返回数据后
            //alert(data);你可以调试一下这个返回包
            True ||  (data = ShearPhoto.JsonString.StringToJson(data));
            if (data === false) {
                Shear.SendUserMsg("错误：请保证后端环境运行正常", 5e3, 0, "#f4102b", "#fff",  true,true);
                return;
            }
            if (data["erro"]) {
                Shear.SendUserMsg("错误：" + data["erro"], 5e3, 0, "#f4102b", "#fff",  true,true);
                return;
            }
            Shear.run(data["success"],true);
        });
        /*.................................................选择图片上传的设置结束...............................................................*/

        /*............................截图，左旋，右旋，重新选择..................开始.........看好怎么调用截图，左旋，右旋，重新选择..........................................*/
        Shear.addEvent(document.getElementById("saveShear"), "click", function() { //按下截图事件，提交到后端的shearphoto.php接收
            Shear.SendPHP({shearphoto:"我要传参数到服端",mingge:"我要传第二个参数到服务器"});//我们示例截图并且传参数，后端文件shearphoto.php用 示例：$_POST["shearphoto"] 接收参数，不需要传参数请清空Shear.SendPHP里面的参数示例 Shear.SendPHP();

        });

        Shear.addEvent(document.getElementById("LeftRotate"), "click", function() {//向左旋转事件
            Shear.Rotate("left");
        });

        Shear.addEvent(document.getElementById("RightRotate"), "click", function() { //向右旋转事件
            Shear.Rotate("right");
        });

        Shear.addEvent(document.getElementById("againIMG"), "click", function() {     //重新选择事件
            Shear.preview.close_();
            Shear.again();
            Shear.HTML5.EffectsReturn();
            Shear.HTML5.BOLBID	&&   Shear.HTML5.URL.revokeObjectURL(Shear.HTML5.BOLBID);
            Shear.pointhandle(3e3, 10, "已取消！重新选择", 2, "#fbeb61", "#3a414c");
        });

        /*............................截图，左旋，右旋，重新选择.................................................结束....................*/

        /*...........2.2加入的缓冲效果............................*/
        var shearphoto_loading=document.getElementById("shearphoto_loading");
        var shearphoto_main=document.getElementById("shearphoto_main");
        shearphoto_loading && shearphoto_loading.parentNode.removeChild(shearphoto_loading);
        shearphoto_main.style.visibility="visible";
        /*................2.2加入的缓冲效果结束..................*/
    });
</script>

</body>

</html>