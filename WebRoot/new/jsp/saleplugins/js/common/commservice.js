angular.module('app.service', ['ngTouch'])
	.factory('orderProlist', function($http) {
		//获取订单中商品列表服务
		return {
			getlist: function(orderid) {
				return $http({
					method: 'get',
					url: '../js/common/prolist.json', //url
					params: {
						userid: userid,
						orderid: orderid
					}
				})
			}
		}
	}).factory('updateCart', function($http) {
		//更新购物车
		var updateCart = function(proid, pronum) {
			return $http({
				method: 'get',
				url: '',
				params: {
					userid: userid,
					proid: proid,
					pronum: pronum
				}
			})
		};
		return {
			//商品详情
			proinfo: function(proid, proname) {
				artDialog.load('/qxv3/common.html?proid=' + proid, {
					title: proname,
					width: '92.5%',
					padding: 10,
					height:'80%'
				})
			},
			//添加到购物车
			addTcart: function(pro) {
				pro.pronum++;
				var up = updateCart(pro.proid, pro.pronum);
				up.success(function(data) {}).error(function(a, b, c, d) {
					pro.pronum--;
				})
			},
			//从购物车中删除
			removeFcart: function(pro, del) {
				if (pro.propronum == 0) return;
				!del ? pro.pronum-- : pro.pronum = 0;
				var up = updateCart(pro.proid, pro.pronum);
				if (del) {
					return up;
				} else {
					up.success(function(data) {}).error(function(a, b, c, d) {
						pro.pronum++;
					})
				}

			}
		}
	}).directive('cartprolist', ['$http', 'updateCart',
		function($http, updateCart) {
			//购物车商品列表指令
			return {
				restrict: 'EA',
				scope: {
					plist: "=plist",
					protypesize: "@pronum"
				},
				templateUrl: '/qxv3/tpl/cartprolist.html', //购物车商品列表模板
				link: function(scope, element, attr) {
					scope.needel=attr.needel;
					scope.scrollStep = 0;
					scope.plst = attr.plist;
					scope.minsize = 4;
					scope.lih=(element.parents('.cart').width()-5) / scope.minsize;
					scope.sbwidth = (element.parents('.cart').width()) / scope.minsize;
					scope.$watch('protypesize', function() {
							scope.scrollStep = scope.scrollStep < (4 - scope.protypesize) ? (4 - scope.protypesize) : scope.scrollStep;
							scope.scrollStep = scope.scrollStep >= 0 ? 0 : scope.scrollStep;
						})
						//向左滚动
					scope.prosL = function(pronum) {
							scope.scrollStep -= scope.minsize;
							scope.scrollStep = scope.scrollStep < (scope.minsize - scope.protypesize) ? (scope.minsize - scope.protypesize) : scope.scrollStep
						}
						//向右滚动
					scope.prosR = function(pronum) {
						scope.scrollStep += scope.minsize;
						scope.scrollStep = scope.scrollStep > 0 ? 0 : scope.scrollStep
					};
					//商品详情
					scope.proinfo = function(proid, proname) {
						updateCart.proinfo(proid, proname);
					};
					//删减选中的商品
					scope.removeFcart = function(pro) {
						updateCart.removeFcart(pro)
					};
				}

			}
		}
	]).directive('prolist', function($http, updateCart) {
		//商品列表指令
		return {
			restrict: 'EA',
			scope: {
				prolist: "=plst",
				navcur: "@navcur" //父类id，用于根据父id筛选显示商品
			},
			templateUrl: '/qxv3/tpl/prolist.html', //商品列表模板
			link: function(scope, element, attr) {
				//商品详情
				scope.proinfo = function(proid, proname) {
					updateCart.proinfo(proid, proname);
				};
				//添加到购物车
				scope.addTocart = function(pro) {
					updateCart.addTcart(pro)
				};
				//删减选中的商品
				scope.removeFcart = function(pro) {
					updateCart.removeFcart(pro)
				}
			}

		}
	}).directive('radio', function() {
		//radio单选
		return {
			restrict: 'EA',
			scope: {
				radiovalue: '=radiovalue',
				truev: '@truev' //选中时的值
			},
			templateUrl: '/qxv3/tpl/radio.html'
		}
	}).directive('checkbox', function() {
		//checkbox多选
		return {
			restrict: 'EA',
			scope: {
				selected: '=selected', //
				disvalue: '@disvalue' //禁用的条件,不禁用不传值
			},
			templateUrl: '/qxv3/tpl/checkbox.html'
		}
	}).directive('boxouter', function() {
		//暂时用于include静态内容，嵌入指令有问题，稍后研究
		//boxouter
		return {
			restrict: 'EA',
			transclude: true,
			scope:{
				title:"@boxtitle",
				title_txt:'@titletxt',
				needtoggle:'@needtoggle'
			},
			templateUrl: '/qxv3/tpl/boxouter.html',
			link: function(scope, element, attr) {
				scope.toggle=true
				scope.togglebox = function() {
					scope.toggle = !scope.toggle;
				}

			}
		}
	});



