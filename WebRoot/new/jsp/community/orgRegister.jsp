<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <meta http-equiv="X-UA-Compatible" content="IE=9" />
    <title>区享-区域共赢,享你所想 </title>

    <!-- Bootstrap Core CSS -->
    <link href="${pageContext.request.contextPath}/new/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/new/css/responsive.css" rel="stylesheet"> 

    <link href="${pageContext.request.contextPath}/new/css/index.css" rel="stylesheet">


    <!--[if lt IE 9]>
  <script src="js/html5shiv.min.js"></script>
<![endif]-->



</head>

<body id="page-top" data-spy="scroll" data-target=".navbar-fixed-top">

<!--   右侧广告栏部分-->
    <div class="adv">
        <div class="qq-z clearfix"><a href=""><span class="icon"></span><span>在线咨询</span></a></div>
        <div class="wx-z"><span><img src="${pageContext.request.contextPath}/new/img/icon-ww.png" ></span><span>扫描微信二维码</span></div>
        <div class="ph-z clearfix"><a href=""><span class="icon"></span><span>025-52414055</span></a></div>


    </div>

    <!-- Navigation -->
    <nav class="navbar navbar-custom navbar-fixed-top" role="navigation">

        <div class="container">
            <div class="navbar-header">

                <a class="navbar-brand page-scroll" href="#page-top">
                    <img src="${pageContext.request.contextPath}/new/img/logo.png">
                </a>

                <button data-target=".navbar-collapse" data-toggle="collapse" class="navbar-toggle" type="button">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse navbar-right navbar-main-collapse">
                <ul class="nav navbar-nav">
                    <!-- Hidden li included to remove active class from about link when scrolled up past about section -->
                    <li class="hidden">
                        <a href="#page-top"></a>
                    </li>
                    <li>
                        <a class="page-scroll" href="#first">首页</a>
                    </li>
                    <li>
                        <a class="page-scroll" href="#about">关于区享</a>
                    </li>
                    <li>
                        <a class="page-scroll" href="#league">加盟区享</a>
                    </li>
                    <li>
                        <a class="page-scroll" href="#case">成功案例</a>
                    </li>

                    <li>
                        <a class="page-scroll" href="#join">加入我们</a>
                    </li>
                </ul>
            </div>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container -->
    </nav>

    <!-- first banner部分 -->
    <section id="first">
        <div class="pic-1 ">
            <img src="${pageContext.request.contextPath}/new/img/banner1.jpg">
        </div>
    </section>
<!--    加入区享部分-->
    <section class="add-us">
        <div class="add-us-con">
            <div class="container">
                <div class="add-us-in ">
                    <p>预计月收入<span>3000-8000</span>元不等！</p>
                    <p>现在报名平台、技术、服务<span>全部免费</span></p>
                </div>
                <div class="add-us-out">
                    <div class="form-bg">
                        <form action="${pageContext.request.contextPath}/communityRegisterCtrl.do?method=register" method="post"><span class="clearfix"><em>姓名</em><input type="text"  placeholder="王小胖" name="community_person_name"> </span>
                            <span class="clearfix"><em>电话</em><input type="text" placeholder="18908880666" name="community_person_phone"> </span>
                            <span class="clearfix"><em>地址</em><input type="text" placeholder="南京市秦淮区中山南路501号通服大厦" name="community_person_addr"> </span>

                            <div class="submit">
                                <input type="submit" value="提交">
                            </div>

                        </form>
                    </div>
                </div>
            </div>

    </section>
    
    <!-- About Section -->
    <section id="about" class="container about content-section text-center">
        <div class="about">
            <h1>一分钟了解区享</h1>
            <div class="row box-view">
                 <video src="${pageContext.request.contextPath}/new/video/qx.mp4" width="100%" height="100%" controls preload></video>




            </div>
            <div class="row qx-what">
                <span class="qx-pic"><img src="${pageContext.request.contextPath}/new/img/xia1.png"></span>
                <span class="qx-con">
               <div class="qx-nei">
                <h2><em>区享侠</em>是什么？</h2>
                <p>区享侠是一全国范围内的小区创业社群，旨在为小区小微创业者们构建一个在家开店经营、服务邻里街坊、劳动收获报酬的创业平台。</p>
</div>
            </span>
            </div>
        </div>
    </section>



    <!-- league Section -->
    <section id="league" class=" content-section text-center">
        <div class="league-in">
            <div class="qxit-pic"> <img src="${pageContext.request.contextPath}/new/img/pic01.jpg"></div>

            <div class="qxit-pic"> <img src="${pageContext.request.contextPath}/new/img/pic02.jpg"></div>
            <div class="qxit-pic"> <img src="${pageContext.request.contextPath}/new/img/pic03.jpg"></div>
        </div>
    </section>

    <!-- Contact Section -->
    <section id="contact" class="   text-center">
        <div class="contact">
            <div class="container">
                <div class="row">

                    <div class="anim-2">
                        <div class="cont-1 col-md-4 bg-1">
                            <h2>创业成本可控</h2>
                            <p>无店租、小成本，开业之前仅需少量商品库存（1000-2000元）；后期根据区享后台大数据分析，合理安排进货量，资金压力小。</p>
                        </div>
                        <div class="cont-2 col-md-4 bg-2">
                            <h2>多重营销支持，</br>保障长期稳定收益</h2>
                            <p>为了保证区享侠收入，区享平台对每位“区享侠”进行多重营销支持。</p>
                        </div>
                        <div class="cont-3 col-md-4 bg-1">
                            <h2>完成目标，</br>获得丰厚奖励补贴</h2>
                            <p>完成目标订单数 获得1000-25000元/月固定补贴</p>
                        </div>
                        <div class="cont-4 col-md-4 bg-2">
                            <h2>定期爆款特卖，</br>迅速引爆小区市场</h2>
                            <p>公司定期组织“区享侠”在小区内开展爆款特卖活动，经实践证明，可迅速引爆小区市场，获得大量客源。</p>
                        </div>
                        <div class="cont-4 col-md-4 bg-1">
                            <h2>定期爆款特卖，</br>迅速引爆小区市场</h2>
                            <p>公司定期组织“区享侠”在小区内开展爆款特卖活动，经实践证明，可迅速引爆小区市场，获得大量客源。</p>
                        </div>
                        <div class="cont-4 col-md-4 bg-2">
                            <h2>定期爆款特卖，</br>迅速引爆小区市场</h2>
                            <p>公司定期组织“区享侠”在小区内开展爆款特卖活动，经实践证明，可迅速引爆小区市场，获得大量客源。</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <!-- case Section -->
    <section id="case" class="   text-center">
        <div class="case">
            <div class="container">
                <div class="row">
                    <h1>区享店面展示</h1>
                    <div class="anim-2">
                        <div class="col-md-4 ">
                            <a href="">
                                <div class="pic"><img src="${pageContext.request.contextPath}/new/img/pic1.jpg"></div>
                                <h2>紫金南苑（南京）</h2>
                                <p>地址：南京市玄武区紫金东路2号紫金南苑12栋101</p>
                                <p>联系电话：025-52413882</p>
                            </a>
                        </div>
                        <div class="col-md-4 ">
                            <a href="">
                                <div class="pic"><img src="${pageContext.request.contextPath}/new/img/pic2.jpg"></div>
                                <h2>翠屏国际（南京）</h2>
                                <p>地址：南京市江宁区将军大道20号翠屏国际城水杉苑12栋101</p>
                                <p> 联系电话：025-84146869</p>
                            </a>
                        </div>
                        <div class="col-md-4 ">
                            <a href="">
                                <div class="pic"><img src="${pageContext.request.contextPath}/new/img/pic3.jpg"></div>
                                <h2>小卫街（南京电信营业厅合作）</h2>
                                <p>地址：南京市玄武区小卫街20号，中国电信营业厅</p>
                                <p>联系电话：025-52235100</p>
                            </a>
                        </div>
                        <div class="col-md-4 ">
                            <a href="">
                                <div class="pic"><img src="${pageContext.request.contextPath}/new/img/pic4.jpg"></div>
                                <h2>依云城邦（扬州）</h2>
                                <p>地址：扬州市邗江区依云城邦一期25栋101</p>
                                <p>联系电话：0514-82893556</p>
                            </a>
                        </div>
                        <div class="col-md-4 ">
                            <a href="">
                                <div class="pic"><img src="${pageContext.request.contextPath}/new/img/pic5.jpg"></div>
                                <h2>水晶城（扬州）</h2>
                                <p>地址：扬州市邗江区博物馆路299号</p>
                                <p>联系电话：0514-82893559</p>
                            </a>
                        </div>
                        <div class="col-md-4 ">
                            <a href="">
                                <div class="pic"><img src="${pageContext.request.contextPath}/new/img/pic6.jpg"></div>
                                <h2>保集半岛（扬州）</h2>
                                <p>地址：扬州市文汇西路292号保集半岛小区南大门</p>
                                <p>联系电话：0514-82890115</p>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <!-- Join Section -->
    <section id="join" class=" join  text-center">
        <div class="container">
            <div class="row">
                <div class="anim-2">
                    <div class="col-join">
                        <p>预计月收入<span>3000-8000</span>元不等！</p>
                        <p>现在报名，平台、技术、服务<span>全部免费</span></p>
                        <form action="${pageContext.request.contextPath}/communityRegisterCtrl.do?method=register" method="post">
                            <span class="clearfix"><em>姓名：</em> <input type="text" name="community_person_name"></span>
                            <span class="clearfix"><em>电话：</em> <input type="text" name="community_person_phone"></span>
                            <span class="clearfix"><em>地址：</em> <input type="text" name="community_person_addr"></span>
                            <span class="submit"><em></em><input type="submit" value="加入我们"></span>
                        </form>



                    </div>
                </div>
            </div>
        </div>
    </section>




    <!-- Footer -->
    <footer>
        <div class="container text-center">
            <div class="foot-main clearfix">
                <div class="col-md-3 col-sm-3"><img src="${pageContext.request.contextPath}/new/img/di_logo.png"></div>
                <div class="col-md-2 col-sm-3"><img src="${pageContext.request.contextPath}/new/img/wei1.jpg"></div>
                <div class="col-md-2  col-sm-3"><img src="${pageContext.request.contextPath}/new/img/wei2.jpg"></div>
                <div class="col-md-5 clear-1">
                    <p>联系电话：025-52414055</p>
                    <p>公司地址：南京区享网络科技有限公司</p>
                </div>

            </div>
            <p class="copyright">Copyright © 2014 南京区享网络科技有限公司 苏ICP备14025245</p>
        </div>
    </footer>

    <!-- jQuery -->
    <script src="${pageContext.request.contextPath}/new/js/jquery.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="${pageContext.request.contextPath}/new/js/bootstrap.min.js"></script>

    <!-- Plugin JavaScript -->
    <script src="${pageContext.request.contextPath}/new/js/jquery.easing.min.js"></script>



    <!-- Custom Theme JavaScript -->
    <script src="${pageContext.request.contextPath}/new/js/index.js"></script>



</body>

</html>
</html>