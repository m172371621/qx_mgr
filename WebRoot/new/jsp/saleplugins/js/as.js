/*! 70c v3.4.0 2015-06-30 | (c) 2014-2015 70c.com */
function mobilecheck() {
	var a = !1;
	return function(b) { (/(android|bb\d+|meego).+mobile|avantgo|bada\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|iris|kindle|lge |maemo|midp|mmp|mobile.+firefox|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\/|plucker|pocket|psp|series(4|6)0|symbian|treo|up\.(browser|link)|vodafone|wap|windows (ce|phone)|xda|xiino/i.test(b) || /1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\-(n|u)|c55\/|capi|ccwa|cdm\-|cell|chtm|cldc|cmd\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\-s|devi|dica|dmob|do(c|p)o|ds(12|\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\-|_)|g1 u|g560|gene|gf\-5|g\-mo|go(\.w|od)|gr(ad|un)|haie|hcit|hd\-(m|p|t)|hei\-|hi(pt|ta)|hp( i|ip)|hs\-c|ht(c(\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\-(20|go|ma)|i230|iac( |\-|\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\/)|klon|kpt |kwc\-|kyo(c|k)|le(no|xi)|lg( g|\/(k|l|u)|50|54|\-[a-w])|libw|lynx|m1\-w|m3ga|m50\/|ma(te|ui|xo)|mc(01|21|ca)|m\-cr|me(rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\-2|po(ck|rt|se)|prox|psio|pt\-g|qa\-a|qc(07|12|21|32|60|\-[2-7]|i\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\-|oo|p\-)|sdk\/|se(c(\-|0|1)|47|mc|nd|ri)|sgh\-|shar|sie(\-|m)|sk\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\-|v\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\-|tdg\-|tel(i|m)|tim\-|t\-mo|to(pl|sh)|ts(70|m\-|m3|m5)|tx\-9|up(\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|yas\-|your|zeto|zte\-/i.test(b.substr(0, 4))) && (a = !0)
	} (navigator.userAgent || navigator.vendor || window.opera),
	a
}
function isAndroid() {
	var a = navigator.userAgent,
	b = (navigator.appVersion, a.indexOf("Android") > -1 || a.indexOf("Linux") > -1);
	return b
}
function isWeixin() {
	var a = navigator.userAgent.toLowerCase();
	return "micromessenger" == a.match(/MicroMessenger/i) ? !0 : !1
}
function countCharacters(a) {
	for (var b = 0,
	c = 0; c < a.length; c++) {
		var d = a.charCodeAt(c);
		d >= 1 && 126 >= d || d >= 65376 && 65439 >= d ? b++:b += 2
	}
	return b
}
function playVideo(a) {
	if (a && a.bgAudio) {
		var b = $("#media"),
		c = $("#audio_btn"),
		d = $("#yinfu"),
		e = "1" == a.bgAudio.type ? a.bgAudio.url: PREFIX_FILE_HOST + a.bgAudio.url;
		b.attr("src", e),
		c.addClass("video_exist"),
		b.bind("loadstart",
		function() {
			c.removeClass("off").addClass("loading_background"),
			d.addClass("loading_yinfu")
		}).bind("loadeddata",
		function() {
			c.removeClass("loading_background").addClass("off"),
			d.removeClass("loading_yinfu")
		}).bind("canplay",
		function() {
			b.get(0).play(),
			$(window).off("touchstart.fixautoplay")
		}).bind("play",
		function() {
			c.removeClass("off").addClass("play_yinfu"),
			d.addClass("rotate")
		}).bind("pause",
		function() {
			c.removeClass("play_yinfu").addClass("off"),
			d.removeClass("rotate")
		}),
		c.show().click(function(a) {
			a.stopPropagation(),
			$(this).hasClass("off") ? (b.get(0).play(), utilSound.pause()) : b.get(0).pause()
		}),
		$(window).one("touchstart.fixautoplay",
		function() {
			b.get(0).play()
		})
	}
	sayGood(a)
}
function getSceneIdByCode(a) {
	var b = a.replace(/A/g, "0").replace(/C/g, "1").replace(/F/g, "2").replace(/Z/g, "3").replace(/W/g, "4").replace(/G/g, "5").replace(/J/g, "6").replace(/Q/g, "7").replace(/Y/g, "8").replace(/S/g, "9");
	return parseInt(b)
}
function getCodeBySceneId(a) {
	var b = a.replace(/0/g, "A").replace(/1/g, "C").replace(/2/g, "F").replace(/3/g, "Z").replace(/4/g, "W").replace(/5/g, "G").replace(/6/g, "J").replace(/7/g, "Q").replace(/8/g, "Y").replace(/9/g, "S");
	return b
}
function sayGood(a) {
	return ! 1
}
function xinShouZhiYin() {
	var a = sessionStorage.getItem("xszySCode");
	null == a || -1 === window.location.href.indexOf("w/" + a);
	var b, c, d, e, f, g, h = sessionStorage.getItem("xszy"),
	i = $("#xszyTishi");
	if (0 === i.length && ("8" == h || "k" == h)) {
		var j = function() {
			if (g = $("#btnsetproduct"), 0 == g.length) return ! 1;
			b = g,
			b.addClass("xszy-shanshuo"),
			c = "亲，就差最后一步了呀……",
			d = 21 * c.length,
			e = b.offset().top + 40,
			$("#xszyTishi").addClass("top"),
			f = b.offset().left;
			var a = '<p class="triangle-border top" id="xszyTishi" style="width:' + d + "px; top:" + parseInt(e) + "px; left:" + parseInt(f) + 'px;position: absolute;">' + c + "</p>";
			$("body").append(a),
			$("#xszyTishi").animate({
				opacity: "0"
			},
			6e3),
			$("#btnsetproduct").on("mouseenter",
			function() {
				$("#xszyTishi").animate({
					opacity: "0.8"
				},
				1e3)
			}).on("mouseleave",
			function() {
				$("#xszyTishi").animate({
					opacity: "0"
				},
				1e3)
			})
		};
		setTimeout(j, 1e3)
	}
}
function renderPage(a, b, c) {
	a.templateParser("jsonParser").parse({
		def: c[b - 1],
		appendTo: "#page" + b,
		mode: "view"
	});
	var d, e, f = 1,
	g = $(".z-current").width(),
	h = $(".z-current").height();
	if (imageWidth = $(".m-img").width(), imageHeight = $(".m-img").height(), g / h >= 320 / 486 ? (f = h / 486, d = (g / f - 320) / 2) : (f = g / 320, e = (h / f - 486) / 2), e && $(".edit_area").css({
		marginTop: e
	}), d && $(".edit_area").css({
		marginLeft: d
	}), a.templateParser("jsonParser").getTplCount() == c.length && ($("#eqMobileViewport").attr("content", "width=320, initial-scale=" + f + ", maximum-scale=" + f + ", user-scalable=no"), 320 != clientWidth && clientWidth == document.documentElement.clientWidth || isWeixin() && (navigator.userAgent.indexOf("Android") > -1 || navigator.userAgent.indexOf("Linux") > -1))) {
		var i = 320 / g,
		j = 486 / h,
		k = Math.max(i, j);
		k = k > 1 ? k: 160 * k,
		k = parseInt(k),
		$("#eqMobileViewport").attr("content", "width=320, target-densitydpi=" + k)
	}
}
function GetQueryString(a) {
	var b = new RegExp("(^|&)" + a + "=([^&]*)(&|$)", "i"),
	c = window.location.search.substr(1).match(b);
	return null != c ? unescape(c[2]) : null
}
function getSceneIdByCode(a) {
	var b = a.replace(/A/g, "0").replace(/C/g, "1").replace(/F/g, "2").replace(/Z/g, "3").replace(/W/g, "4").replace(/G/g, "5").replace(/J/g, "6").replace(/Q/g, "7").replace(/Y/g, "8").replace(/S/g, "9");
	return parseInt(b)
}
function initWeixinShare() {
	id || (id = getSceneIdByCode(window.location.href.split("/w/")[1].split("?")[0])),
	$.ajax({
		type: "GET",
		url: PREFIX_URL + "As/weixinShare?id=" + id,
		xhrFields: {
			withCredentials: !0
		},
		crossDomain: !0,
		success: function(a) {
			if (200 == a.code) {
				var b = "";
				if ("操作成功" === a.msg) {
					var c = a.obj.appId,
					d = a.obj.timestamp,
					e = a.obj.nonceStr,
					f = a.obj.signature;
					b = a.obj.title || "70度产品营销推广专家";
					var g = a.obj.link,
					h = a.obj.imgsrc,
					i = a.obj.desc || "创建属于自己的故事－70度";
					StoreID = a.obj.StoreID,
					wx.config({
						debug: !1,
						appId: c,
						timestamp: d,
						nonceStr: e,
						signature: f,
						jsApiList: ["checkJsApi", "onMenuShareTimeline", "onMenuShareAppMessage"]
					});
					wx.ready(function() {
						wx.onMenuShareTimeline({
							title: b,
							link: g,
							imgUrl: h,
							fail: function(a) {},
							success: function(a) {}
						}),
						wx.onMenuShareAppMessage({
							title: b,
							desc: i,
							link: g,
							imgUrl: h,
							trigger: function(a) {},
							success: function(a) {},
							cancel: function(a) {},
							fail: function(a) {}
						})
					}),
					wx.error(function(a) {
						alret(a.errMsg)
					})
				} else StoreID = a.StoreID,
				b = a.SceneName;
				stat(b)
			}
		}
	})
}
function stat(a) {
	if (!id) {
		var b = window.location.href.split("/w/")[1].split("?")[0];
		id = getSceneIdByCode(b)
	}
	if ("" === StoreID || void 0 === StoreID || -1 == StoreID) return ! 1;
	var c = {
		screenwidth: screen.width,
		screenheight: screen.height,
		StoreID: StoreID,
		SceneID: id,
		BatchID: 0,
		cookie: getUserInfo(),
		OS: getOS(),
		SceneName: encodeURI(a)
	},
	d = new Image;
	d.src = WSDSTAT + "stat/stat?" + $.param(c)
}
function getOS() {
	var a = navigator.userAgent;
	return a.indexOf("Android") > -1 || a.indexOf("Linux") > -1 ? "Android": a.indexOf("iPhone") > -1 ? "iPhone": a.indexOf("Windows Phone") > -1 ? "winPhone": "other"
}
function guidGenerator() {
	var a = function() {
		return (65536 * (1 + Math.random()) | 0).toString(16).substring(1)
	};
	return a() + a() + "-" + a() + "-" + a() + "-" + a() + "-" + a() + a() + a()
}
function getUserInfo() {
	return localStorage.getItem("uinfo") || localStorage.setItem("uinfo", guidGenerator()),
	localStorage.getItem("uinfo")
} !
function(a) {
	"use strict";
	a.fn.swipeSlide = function(b, c) {
		function d(a, b) {
			a.css({
				"-webkit-transition": "all " + b + "s " + C.transitionType,
				transition: "all " + b + "s " + C.transitionType
			})
		}
		function e(a, b) {
			a.css(C.axisX ? {
				"-webkit-transform": "translate3d(" + b + "px,0,0)",
				transform: "translate3d(" + b + "px,0,0)"
			}: {
				"-webkit-transform": "translate3d(0," + b + "px,0)",
				transform: "translate3d(0," + b + "px,0)"
			})
		}
		function f(a) {
			if (C.lazyLoad) {
				var b = C.ul.find("[data-src]");
				if (b.length > 0) {
					var c = b.eq(a);
					c.data("src") && (c.is("img") ? c.attr("src", c.data("src")).data("src", "") : c.css({
						"background-image": "url(" + c.data("src") + ")"
					}).data("src", ""))
				}
			}
		}
		function g(a) {
			a.touches || (a.touches = a.originalEvent.touches)
		}
		function h(a) {
			r = a.touches[0].pageX,
			s = a.touches[0].pageY
		}
		function i(a) {
			if (a.preventDefault(), C.autoSwipe && p && clearInterval(p), w = a.touches[0].pageX, x = a.touches[0].pageY, t = w - r, u = x - s, d(C.ul, 0), C.axisX) {
				if (!C.continuousScroll) {
					if (0 == q && t > 0) return t = 0,
					o();
					if (q + 1 >= F && 0 > t) return t = 0,
					o()
				}
				e(C.ul, -(D * parseInt(q) - t))
			} else {
				if (!C.continuousScroll) {
					if (0 == q && u > 0) return u = 0,
					o();
					if (q + 1 >= F && 0 > u) return u = 0,
					o()
				}
				e(C.ul, -(E * parseInt(q) - u))
			}
		}
		function j() {
			v = C.axisX ? t: u,
			Math.abs(v) <= y ? k(.3) : v > y ? n() : -y > v && m(),
			o(),
			t = 0,
			u = 0
		}
		function k(a) {
			d(C.ul, a),
			C.axisX ? e(C.ul, -q * D) : e(C.ul, -q * E)
		}
		function l() {
			C.continuousScroll ? q >= F ? (k(.3), q = 0, setTimeout(function() {
				k(0)
			},
			300)) : 0 > q ? (k(.3), q = F - 1, setTimeout(function() {
				k(0)
			},
			300)) : k(.3) : (q >= F ? q = 0 : 0 > q && (q = F - 1), k(.3)),
			c(q)
		}
		function m() {
			q++,
			l(),
			C.lazyLoad && f(C.continuousScroll ? q + 2 : q + 1)
		}
		function n() {
			if (q--, l(), A && C.lazyLoad) {
				var a = F - 1;
				for (a; F + 1 >= a; a++) f(a);
				return void(A = !1)
			} ! A && C.lazyLoad && f(q)
		}
		function o() {
			C.autoSwipe && (p = setInterval(function() {
				m()
			},
			C.speed))
		}
		var p, q = 0,
		r = 0,
		s = 0,
		t = 0,
		u = 0,
		v = 0,
		w = 0,
		x = 0,
		y = 50,
		z = 0,
		A = !0,
		B = a(this),
		C = a.extend({},
		{
			ul: B.children("ul"),
			li: B.children().children("li"),
			continuousScroll: !1,
			autoSwipe: !0,
			speed: 4e3,
			axisX: !0,
			transitionType: "ease",
			lazyLoad: !1,
			clone: !0,
			width: 0,
			length: 0
		},
		b || {}),
		D = C.width || C.li.width(),
		E = C.li.height(),
		F = C.length || C.li.length;
		c = c ||
		function() {},
		function() {
			if (C.continuousScroll && (C.clone && C.ul.prepend(C.li.last().clone()).append(C.li.first().clone()), C.axisX ? (e(C.ul.children().first(), -1 * D), e(C.ul.children().last(), D * F)) : (e(C.ul.children().first(), -1 * E), e(C.ul.children().last(), E * F))), C.lazyLoad) {
				var b = 0;
				for (z = C.continuousScroll ? 3 : 2, b; z > b; b++) f(b)
			}
			C.li.each(C.axisX ?
			function(b) {
				e(a(this), D * b)
			}: function(b) {
				e(a(this), E * b)
			}),
			o(),
			c(q, p),
			B.on("touchstart",
			function(a) {
				a.stopPropagation(),
				g(a),
				h(a)
			}),
			B.on("touchmove",
			function(a) {
				a.stopPropagation(),
				g(a),
				i(a)
			}),
			B.on("touchend",
			function(a) {
				a.stopPropagation(),
				j()
			})
		} ()
	}
} (window.Zepto || window.jQuery),
!
function(a, b) {
	function c() {
		var a, c, d = [];
		this.addAudio = function(a, e) {
			var f = new Audio;
			f.src = e,
			d.push({
				elem: a,
				audio: f
			}),
			b(f).bind("ended",
			function() {
				c = !1
			})
		},
		this.play = function(b) {
			for (var e, f = 0; f < d.length; f++) d[f].elem == b && (e = d[f].audio);
			a == e && c ? (e.pause(), c = !1) : a != e || c ? (a && a.pause(), e.currentTime = 0, e.play(), c = !0, a = e) : (e.play(), c = !0)
		},
		this.pause = function() {
			a && (a.pause(), c = !1)
		}
	}
	a.utilSound = new c
} (window, jQuery),
!
function(a) {
	function b() {
		var a = {
			CLICK: {
				name: "click",
				value: 1
			}
		},
		b = {
			SHOW: {
				name: "show",
				value: 1
			}
		};
		this.getSendType = function(b) {
			if (void 0 === b) return a;
			for (var c in a) if (b === a[c].value) return a[c];
			return null
		},
		this.getHandleType = function(a) {
			if (void 0 === a) return b;
			for (var c in b) if (a === b[c].value) return b[c];
			return null
		}
	}
	a.utilTrigger = new b
} (window),
!
function(a) {
	function b(a) {
		function b(a, b, c) {
			return a[b] || (a[b] = c())
		}
		var c = b(a, "eqShow", Object);
		return b(c, "templateParser",
		function() {
			var a = {};
			return function(c, d) {
				if ("hasOwnProperty" === c) throw new Error("hasOwnProperty is not a valid name");
				return d && a.hasOwnProperty(c) && (a[c] = null),
				b(a, c, d)
			}
		})
	}
	function c() {
		templateParser = b(a)
	}
	var d = a.eqShow || (a.eqShow = {});
	c(d)
} (window),
!
function(a) {
	function b(a, b) {
		if (b.trigger) {
			var c = $(a);
			b.trigger.sends && b.trigger.sends.length && $.each(b.trigger.sends,
			function(a, b) {
				c.bind(utilTrigger.getSendType(b.type).name,
				function() {
					$.each(b.handles,
					function(a, b) {
						var c = utilTrigger.getHandleType(b.type).name;
						$.each(b.ids,
						function(a, b) {
							$("#inside_" + b).trigger(c)
						})
					})
				})
			}),
			b.trigger.receives && b.trigger.receives.length && b.trigger.receives[0].ids.length && $.each(b.trigger.receives,
			function(a, b) {
				var d = utilTrigger.getHandleType(b.type).name;
				"show" == d && c.hide(),
				c.bind(d,
				function() {
					"show" == d && $(this).show()
				})
			})
		}
	}
	function c(a, b) {
		if (b.sound) {
			var c = $(a);
			utilSound.addAudio(a, PREFIX_FILE_HOST + b.sound.src),
			c.click(function() {
				utilSound.play(a);
				var b = $("#media");
				b.length && $("#media").get(0).pause()
			})
		}
	}
	a.templateParser("jsonParser",
	function() {
		function a(a) {
			return function(b, c) {
				a[b] = c
			}
		}
		function d(a, b) {
			var c = k[("" + a.type).charAt(0)](a);
			if (c) {
				var d = $('<li comp-drag comp-rotate class="comp-resize comp-rotate inside" id="inside_' + c.id + '" num="' + a.num + '" ctype="' + a.type + '"></li>');
				3 != ("" + a.type).charAt(0) && 1 != ("" + a.type).charAt(0) && d.attr("comp-resize", ""),
				"p" == ("" + a.type).charAt(0) && d.removeAttr("comp-rotate"),
				1 == ("" + a.type).charAt(0) && d.removeAttr("comp-drag"),
				2 == ("" + a.type).charAt(0) && d.addClass("wsite-text"),
				4 == ("" + a.type).charAt(0) && (a.properties.imgStyle && $(c).css(a.properties.imgStyle), d.addClass("wsite-image")),
				5 == ("" + a.type).charAt(0) && d.addClass("wsite-input"),
				6 == ("" + a.type).charAt(0) && d.addClass("wsite-button"),
				8 == ("" + a.type).charAt(0) && d.addClass("wsite-button"),
				"t" == ("" + a.type).charAt(0) && d.removeAttr("comp-rotate"),
				"e" == ("" + a.type).charAt(0) && d.addClass("wsite-text"),
				"c" == ("" + a.type).charAt(0) && d.addClass("wsite-text"),
				"r" == ("" + a.type).charAt(0) && d.addClass("wsite-text"),
				"x" == ("" + a.type).charAt(0) && d.addClass("wsite-button"),
				"y" == ("" + a.type).charAt(0) && d.addClass("wsite-button"),
				"q" == ("" + a.type).charAt(0) && d.addClass("wsite-button"),
				"m" == ("" + a.type).charAt(0) && d.addClass("wsite-map"),
				"l" == ("" + a.type).charAt(0) && d.addClass("wsite-lottery"),
				"v" == ("" + a.type).charAt(0) && d.addClass("wsite-video"),
				d.mouseenter(function() {
					$(this).addClass("inside-hover")
				}),
				d.mouseleave(function() {
					$(this).removeClass("inside-hover")
				});
				var e = $('<div class="element-box">').append($('<div class="element-box-contents">').append(c));
				return d.append(e),
				5 != ("" + a.type).charAt(0) && 6 != ("" + a.type).charAt(0) || "edit" != b || $(c).before($('<div class="element" style="position: absolute; height: 100%; width: 100%;">')),
				a.css && (d.css({
					width: 320 - parseInt(a.css.left)
				}), d.css({
					width: a.css.width,
					height: a.css.height,
					left: a.css.left,
					top: a.css.top,
					zIndex: a.css.zIndex,
					bottom: a.css.bottom,
					transform: a.css.transform
				}), e.css(a.css).css({
					width: "100%",
					height: "100%",
					transform: "none"
				}), e.children(".element-box-contents").css({
					width: "100%",
					height: "100%"
				}), 4 != ("" + a.type).charAt(0) && "p" != ("" + a.type).charAt(0) && $(c).css({
					width: a.css.width,
					height: a.css.height
				})),
				d
			}
		}
		function e(a) {
			for (var b = 0; b < a.length - 1; b++) for (var c = b + 1; c < a.length; c++) if (parseInt(a[b].css.zIndex, 10) > parseInt(a[c].css.zIndex, 10)) {
				var d = a[b];
				a[b] = a[c],
				a[c] = d
			}
			for (var e = 0; e < a.length; e++) a[e].css.zIndex = e + 1 + "";
			return a
		}
		function f(a, f, g) {
			f = f.find(".edit_area");
			var h, i = a.elements;
			if (i) for (i = e(i), h = 0; h < i.length; h++) if (3 == i[h].type) {
				var j = k[("" + i[h].type).charAt(0)](i[h]);
				"edit" == g && l[("" + i[h].type).charAt(0)] && l[("" + i[h].type).charAt(0)](j, i[h])
			} else {
				var o = d(i[h], g);
				if (!o) continue;
				f.append(o);
				for (var p = 0; p < n.length; p++) n[p](o, i[h], g);
				m[("" + i[h].type).charAt(0)] && (m[("" + i[h].type).charAt(0)](o, i[h]), "view" == g && (b(o, i[h]), c(o, i[h]))),
				"edit" == g && l[("" + i[h].type).charAt(0)] && l[("" + i[h].type).charAt(0)](o, i[h])
			}
		}
		function g() {
			return l
		}
		function h() {
			return k
		}
		function i(a) {
			n.push(a)
		}
		function j() {
			return n
		}
		var k = {},
		l = {},
		m = {},
		n = [],
		o = containerWidth = 320,
		p = containerHeight = 486,
		q = 1,
		r = 1,
		s = 0,
		t = {
			getComponents: h,
			getEventHandlers: g,
			addComponent: a(k),
			bindEditEvent: a(l),
			bindAfterRenderEvent: a(m),
			addInterceptor: i,
			getInterceptors: j,
			wrapComp: d,
			mode: "view",
			getTplCount: function() {
				return s
			},
			parse: function(a) {
				var b = $('<div class="edit_wrapper"><ul eqx-edit-destroy id="edit_area' + a.def.id + '" comp-droppable paste-element class="edit_area weebly-content-area weebly-area-active"></div>'),
				c = this.mode = a.mode;
				this.def = a.def,
				"view" == c && s++;
				var d = $(a.appendTo);
				return containerWidth = d.width(),
				containerHeight = d.height(),
				q = o / containerWidth,
				r = p / containerHeight,
				f(a.def, b.appendTo($(a.appendTo)), c)
			}
		};
		return t
	})
} (window.eqShow),
!
function(a) {
	a.qsdCommon = function() {
		var a = function(a) {
			var b, c, d = a.type;
			return 0 === d && (b = "fadeIn"),
			1 === d && (c = a.direction, 0 === c && (b = "fadeInLeft"), 1 === c && (b = "fadeInDown"), 2 === c && (b = "fadeInRight"), 3 === c && (b = "fadeInUp")),
			6 === d && (b = "wobble"),
			5 === d && (b = "rubberBand"),
			7 === d && (b = "rotateIn"),
			8 === d && (b = "flip"),
			9 === d && (b = "swing"),
			2 === d && (c = a.direction, 0 === c && (b = "bounceInLeft"), 1 === c && (b = "bounceInDown"), 2 === c && (b = "bounceInRight"), 3 === c && (b = "bounceInUp")),
			3 === d && (b = "bounceIn"),
			4 === d && (b = "zoomIn"),
			10 === d && (b = "fadeOut"),
			11 === d && (b = "flipOutY"),
			12 === d && (b = "rollIn"),
			13 === d && (b = "lightSpeedIn"),
			14 === d && (b = "bounceOut"),
			15 === d && (b = "rollOut"),
			16 === d && (b = "lightSpeedOut"),
			17 === d && (c = a.direction, 0 === c && (b = "fadeOutRight"), 1 === c && (b = "fadeOutDown"), 2 === c && (b = "fadeOutLeft"), 3 === c && (b = "fadeOutUp")),
			18 === d && (b = "zoomOut"),
			19 === d && (c = a.direction, 0 === c && (b = "bounceOutRight"), 1 === c && (b = "bounceOutDown"), 2 === c && (b = "bounceOutLeft"), 3 === c && (b = "bounceOutUp")),
			20 === d && (b = "flipInY"),
			21 === d && (b = "tada"),
			22 === d && (b = "jello"),
			b
		},
		b = function(a, b, c) {
			function d(a, b, f) {
				if (f.length && e < f.length) {
					a.css("animation", "");
					var g = a.get(0);
					g.offsetWidth = g.offsetWidth,
					a.css("animation", b[e] + " " + f[e].duration + "s ease " + f[e].delay + "s " + (f[e].countNum ? f[e].countNum: "")),
					"view" == c ? (f[e].count && e == f.length - 1 && a.css("animation-iteration-count", "infinite"), a.css("animation-fill-mode", "both")) : (a.css("animation-iteration-count", "1"), a.css("animation-fill-mode", "backwards")),
					f[e].linear && a.css("animation-timing-function", "linear"),
					a.one("webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend",
					function() {
						e++,
						d(a, b, f)
					})
				}
			}
			var e = 0;
			if (b.properties && b.properties.anim) {
				var f = [];
				b.properties.anim.length ? f = b.properties.anim: f.push(b.properties.anim);
				var g = $(".element-box", a);
				g.attr("element-anim", "");
				for (var h, i = [], j = [], k = 0, l = f.length; l > k; k++) null != f[k].type && -1 != f[k].type && (h = qsdCommon.convertType(f[k]), i.push(h), j.push(f[k]));
				b.properties.anim.trigger ? a.click(function() {
					d(g, h, b.properties.anim)
				}) : d(g, i, j)
			}
		};
		return {
			convertType: a,
			animation: b
		}
	} ()
} (window),
!
function(a) {
	function b(a, b, c, d) {
		var e = {},
		f = a / b,
		g = c / d;
		return f > g ? (e.width = c, e.height = c / f) : (e.height = d, e.width = d * f),
		e
	}
	var c = a.templateParser("jsonParser");
	c.addInterceptor(function(a, b, c) {
		qsdCommon.animation(a, b, c)
	}),
	c.addComponent("1",
	function(a) {
		var b = document.createElement("div");
		if (b.id = a.id, b.setAttribute("class", "element comp_title"), a.content && (b.textContent = a.content), a.css) {
			var c, d = a.css;
			for (c in d) b.style[c] = d[c]
		}
		if (a.properties.labels) for (var e = a.properties.labels,
		f = 0; f < e.length; f++) $('<a class = "label_content" style = "display: inline-block;">').appendTo($(b)).html(e[f].title).css(e[f].color).css("width", 100 / e.length + "%");
		return b
	}),
	c.addComponent("2",
	function(a) {
		var b = document.createElement("div");
		return b.id = a.id,
		b.setAttribute("ctype", a.type),
		b.setAttribute("class", "element comp_paragraph editable-text"),
		a.content && (b.innerHTML = a.content),
		a.properties.For && b.setAttribute("for", a.properties.For),
		b.style.cursor = "default",
		b
	}),
	c.addComponent("3",
	function(a) {
		var b = $("#nr .edit_area")[0];
		"view" == c.mode && (b = document.getElementById("edit_area" + c.def.id)),
		b = $(b).parent()[0];
		var d;
		return a.properties.imgSrc && (d = a.properties.imgSrc, /^http.*/.test(d) || (d = PREFIX_FILE_HOST + "/" + d), (new Image).src = d, b.style.backgroundImage = "url(" + d + ")", b.style.backgroundOrigin = "element content-box", b.style.backgroundSize = "cover", b.style.backgroundPosition = "50% 50%"),
		a.properties.bgColor && (b.style.backgroundColor = a.properties.bgColor),
		b
	}),
	c.addComponent("4",
	function(a) {
		var b = document.createElement("img");
		return b.id = a.id,
		b.setAttribute("ctype", a.type),
		b.setAttribute("class", "element comp_image editable-image"),
		b.src = /^http.*/.test(a.properties.src) ? a.properties.src: PREFIX_FILE_HOST + a.properties.src,
		b
	}),
	c.addComponent("v",
	function(a) {
		var b = document.createElement("a");
		return b.setAttribute("class", "element video_area"),
		b.id = a.id,
		b.setAttribute("ctype", a.type),
		a.properties.src && b.setAttribute("videourl", a.properties.src),
		b
	}),
	c.addComponent("5",
	function(a) {
		var b = document.createElement("textarea");
		return b.id = a.id,
		b.setAttribute("ctype", a.type),
		b.setAttribute("class", "element comp_input editable-text"),
		a.properties.required && "required" == a.properties.required && b.setAttribute("required", a.properties.required),
		a.properties.placeholder && b.setAttribute("placeholder", a.properties.placeholder),
		a.properties.isshowmsg && b.setAttribute("isshowmsg", a.properties.isshowmsg),
		b.setAttribute("name", "eq[f_" + a.id + "]"),
		b.style.width = "100%",
		b
	}),
	c.addComponent("p",
	function(a) {
		if (a.properties && a.properties.children) {
			var c = 320,
			d = 160,
			e = a.css.width || c,
			f = a.css.height || d,
			g = $('<div id="' + a.id + '" class="slide element" ctype="' + a.type + '"></div>'),
			h = $("<ul>").appendTo(g),
			i = $('<div class="dot">').appendTo(g);
			for (var j in a.properties.children) {
				var k = b(a.properties.children[j].width, a.properties.children[j].height, e, f),
				l = $('<img data-src="' + PREFIX_FILE_HOST + a.properties.children[j].src + '" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsQAAA7EAZUrDhsAAAANSURBVBhXYzh8+PB/AAffA0nNPuCLAAAAAElFTkSuQmCC">');
				l.css({
					width: k.width,
					height: k.height
				});
				var m = $("<li>").css({
					lineHeight: f + "px"
				});
				m.append(l),
				h.append(m),
				i.append($("<span>"))
			}
			return INTERVAL_OBJ[a.id] && (clearInterval(INTERVAL_OBJ[a.id]), delete INTERVAL_OBJ[a.id]),
			g.attr("length", a.properties.children.length).attr("autoscroll", a.properties.autoPlay).attr("interval", a.properties.interval),
			g.swipeSlide({
				autoSwipe: a.properties.autoPlay,
				continuousScroll: !0,
				speed: a.properties.interval,
				transitionType: "cubic-bezier(0.22, 0.69, 0.72, 0.88)",
				lazyLoad: !0,
				width: e
			},
			function(b, c) {
				i.children().eq(b).addClass("cur").siblings().removeClass("cur"),
				c && (INTERVAL_OBJ[a.id] = c)
			}),
			g.get(0)
		}
	}),
	c.addComponent("6",
	function(a) {
		var b = document.createElement("button");
		if (b.id = a.id, b.setAttribute("ctype", a.type), b.setAttribute("class", "element comp_button editable-text"), a.properties.title) {
			var c = a.properties.title.replace(/ /g, "&nbsp;");
			b.innerHTML = c
		}
		return b.style.width = "100%",
		b
	}),
	c.addComponent("8",
	function(a) {
		var b = document.createElement("a");
		if (b.id = a.id, b.setAttribute("ctype", a.type), b.setAttribute("class", "element comp_anchor editable-text"), a.properties.title) {
			var d = a.properties.title.replace(/ /g, "&nbsp;");
			$(b).html(d),
			"view" == c.mode && $(b).attr("href", "tel:" + a.properties.number)
		}
		return b.style.cursor = "default",
		b.style.width = "100%",
		b
	}),
	c.addComponent("7",
	function(a) {
		var b = document.createElement("div");
		if (b.id = "map_" + a.id, b.setAttribute("class", "element comp_map_wrapper"), a.content && (b.textContent = a.content), a.css) {
			var c, d = a.css;
			for (c in d) b.style[c] = d[c]
		}
		return b.style.height = "250px",
		b
	}),
	c.bindAfterRenderEvent("1",
	function(a, b) {
		if (a = $("div", a)[0], "view" == c.mode && 1 == b.type) {
			var d = b.properties.labels;
			for (key in d) !
			function(b) {
				$($(a).find(".label_content")[b]).on("click",
				function() {
					pageScroll(d[b])
				})
			} (key)
		}
	}),
	c.bindAfterRenderEvent("8",
	function(a, b) {}),
	c.bindAfterRenderEvent("4",
	function(a, b) {
		"view" == c.mode && b.properties.url && $(a).click(function(a) {
			var c = b.properties.url;
			isNaN(c) ? window.open(c) : eqxiu.pageScroll(c)
		})
	}),
	c.bindAfterRenderEvent("v",
	function(a, b) {
		"view" == c.mode && $(a).click(function() {
			$(a).hide(),
			$("#audio_btn").hasClass("video_exist") && ($("#audio_btn").hide(), $("#media")[0].pause()),
			utilSound.pause(),
			$('<div class="video_mask page_effect lock" id="mask_' + b.id + '"></div>').appendTo($(a).closest(".m-img")),
			$('<a class="close_mask" id="close_' + b.id + '"></a>').appendTo($(a).closest(".m-img")),
			$(b.properties.src).appendTo($("#mask_" + b.id)).attr("style", "position: absolute;top:0; min-height: 45%; max-height: 100%; top: 20%;").attr("width", "100%").removeAttr("height"),
			$("#close_" + b.id).bind("click",
			function() {
				$(a).show(),
				$("#mask_" + b.id).remove(),
				$("#close_" + b.id).remove(),
				$("#audio_btn").hasClass("video_exist") && $("#audio_btn").show(function() {
					$(this).hasClass("off") || $("#media")[0].play()
				})
			})
		})
	}),
	c.bindAfterRenderEvent("2",
	function(a, b) {
		for (var d = $(a).find("a[data]"), e = 0; e < d.length; e++) if (d[e] && "view" == c.mode) {
			$(d[e]).css("cursor", "pointer");
			var f = $(d[e]).attr("data"); !
			function(a) {
				$(d[e]).click(function(b) {
					eqxiu.pageScroll(a)
				})
			} (f)
		}
	}),
	c.bindAfterRenderEvent("6",
	function(a, b) {
		if (a = $("button", a)[0], "view" == c.mode) {
			var d = function(b, c) {
				var d = !0,
				e = $(a).parents(".nr"),
				f = {};
				$("textarea", e).each(function() {
					if (d) {
						if ("required" == $(this).attr("required") && "" == $(this).val().trim()) return alert($(this).attr("placeholder") + "为必填项"),
						void(d = !1);
						if ("" != $(this).val().trim()) {
							if ("502" == $(this).attr("ctype")) {
								var a = new RegExp(/(\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$/g);
								if (!a.test($(this).val())) return alert("手机号码格式错误"),
								void(d = !1)
							}
							if ("503" == $(this).attr("ctype")) {
								var b = new RegExp(/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/g);
								if (!b.test($(this).val())) return alert("邮箱格式错误"),
								void(d = !1)
							}
						}
						f[$(this).attr("name")] = $(this).val()
					}
				}),
				d && $.ajax({
					cache: !0,
					type: "POST",
					url: PREFIX_URL + "As/setforminfo?sceneId=" + c,
					data: $.param(f),
					async: !1,
					error: function(a) {
						alert("Connection error")
					},
					success: function(a) {
						$(b).unbind("click").click(function() {
							alert("请不要重复提交")
						}),
						alert("提交成功")
					}
				})
			},
			e = c.def.sceneId;
			$(a).bind("click",
			function() {
				d(this, e)
			})
		}
	}),
	c.bindAfterRenderEvent("7",
	function(a, b) {
		var c = new BMap.Map("map_" + b.id, {
			enableMapClick: !1
		}),
		d = new BMap.Point(b.properties.x, b.properties.y),
		e = new BMap.Marker(d);
		c.addOverlay(e);
		var f = new BMap.Label(b.properties.markTitle, {
			offset: new BMap.Size(20, -10)
		});
		e.setLabel(f),
		c.disableDoubleClickZoom(),
		c.centerAndZoom(d, 15)
	})
} (window.eqShow),
!
function(a) {
	var b = a.templateParser("jsonParser");
	b.addComponent("m",
	function(a) {
		var b = document.createElement("a");
		return b.setAttribute("class", "element map_area"),
		b.id = a.id,
		b.setAttribute("ctype", a.type),
		a.properties.src && b.setAttribute("mapurl", a.properties.src),
		b.innerHTML = "<img src='" + PREFIX_FILE_HOST + "lib/app/img/map.png' alt='地图' border='0'/><div class='after'></div>",
		b
	}),
	b.bindAfterRenderEvent("m",
	function(a, c) {
		"view" == b.mode && $(a).click(function() {
			$(a).hide(),
			$("#map_btn").hasClass("map_exist") && $("#map_btn").hide(),
			$('<div class="map_mask page_effect lock" id="mask_' + c.id + '"></div>').appendTo($(a).closest(".m-img")),
			$('<a class = "close_mask" style="left: 15px;" id="close_' + c.id + '"></a>').appendTo($(a).closest(".m-img")),
			$(c.properties.src).appendTo($("#mask_" + c.id)).attr("style", "position: absolute;top:0; min-height: 80%; max-height: 100%; top: 10%;z-index:9999").attr("width", "100%").removeAttr("height"),
			$("#close_" + c.id).bind("click",
			function() {
				$(a).show(),
				$("#mask_" + c.id).remove(),
				$("#close_" + c.id).remove(),
				$("#map_btn").hasClass("map_exist") && $("#map_btn").show(function() {
					$(this).hasClass("off")
				})
			})
		})
	}),
	b.addComponent("q",
	function(a) {
		var b = document.createElement("button");
		if (b.id = a.id, b.setAttribute("ctype", a.type), b.setAttribute("class", "element comp_button editable-text"), a.properties.title) {
			var c = a.properties.title.replace(/ /g, "&nbsp;");
			b.innerHTML = c
		}
		return b.style.width = "100%",
		b
	}),
	b.bindAfterRenderEvent("q",
	function(a, c) {
		"view" == b.mode && $(a).click(function() {
			var b = '<iframe height=698 width=510 src="' + PREFIX_URL + "all/fromshow.htm?sceneid=" + c.sceneId + '" frameborder=0 allowfullscreen></iframe>';
			$(a).hide(),
			$("#map_btn").hasClass("map_exist") && $("#map_btn").hide(),
			$('<div class="map_mask page_effect lock" id="mask_' + c.id + '" ></div>').appendTo($(a).closest(".m-img")),
			$('<a class = "close_mask"  style="left: 15px;" id="close_' + c.id + '"></a>').appendTo($(a).closest(".m-img")),
			$(b).appendTo($("#mask_" + c.id)).attr("style", "position: absolute; min-height: 80%; max-height: 80%; top: 10%;z-index:9999").attr("width", "100%").removeAttr("height"),
			$("#close_" + c.id).bind("click",
			function() {
				$(a).show(),
				$("#mask_" + c.id).remove(),
				$("#close_" + c.id).remove(),
				$("#map_btn").hasClass("map_exist") && $("#map_btn").show(function() {
					$(this).hasClass("off")
				})
			})
		})
	}),
	b.addComponent("t",
	function(a) {
		var b = document.createElement("div");
		return b.id = a.id,
		b.setAttribute("ctype", a.type),
		b.setAttribute("class", "element comp_paragraph editable-text"),
		a.content && (b.innerHTML = a.content),
		b.style.cursor = "default",
		b
	}),
	b.addComponent("x",
	function(a) {
		var b = document.createElement("button");
		return b.setAttribute("class", "element toupiao_area"),
		b.id = a.id,
		b.setAttribute("ctype", a.type),
		b.setAttribute("class", "element comp_button editable-text"),
		b.innerHTML = "投票",
		b
	}),
	b.bindAfterRenderEvent("x",
	function(a, c) {
		if (a = $("button", a)[0], "view" == b.mode) {
			var d = function() {
				if (localStorage.getItem("vote_" + c.pageId)) return alert("请不要重复投票！"),
				void(e = !1);
				for (var d = this,
				e = !0,
				f = ($(a).parents("ul"), 0), g = $("#edit_area" + c.pageId).find("input[name^='toupiao_']").attr("name"), h = document.getElementsByName(g), i = "", j = 0; j < h.length; j++) h[j].checked && (i += h[j].value + ",", f += 1);
				var k = c.properties.choosebutton;
				if ("c" === k.Type && parseInt(k.CheckNum) < f) return alert("最多只能选" + k.CheckNum + "个选项！"),
				void(e = !1);
				if (0 === f) return alert("至少要选1个选项才能投票！"),
				void(e = !1);
				if (k.STime !== k.ETime) {
					var l = new Date(k.STime),
					m = new Date(k.ETime),
					n = new Date;
					if (parseInt(n - m) > 0) return alert("该投票在" + k.ETime + "已经结束！"),
					void(e = !1);
					if (parseInt(l - n) > 0) return alert("该投票在" + k.STime + "才正式开始，敬请期待！"),
					void(e = !1)
				}
				i = i.substr(0, i.length - 1),
				e && $.ajax({
					cache: !0,
					type: "POST",
					url: PREFIX_URL + "vote/SubmitVote",
					data: {
						SceneID: c.sceneId,
						ObjID: c.pageId,
						ElemID: i
					},
					error: function() {},
					success: function(a) {
						if (a.success) {
							d.removeEventListener("click", b),
							localStorage.setItem("vote_" + c.pageId, "1");
							var e = $("#edit_area" + c.pageId).find("a[ctype='y']").attr("id");
							document.getElementById("" + e).click()
						} else alert(a.msg)
					}
				})
			};
			a.addEventListener("click", d)
		}
	}),
	b.addComponent("y",
	function(a) {
		var b = document.createElement("a");
		return b.setAttribute("class", "element toupiao_area"),
		b.id = a.id,
		b.setAttribute("ctype", a.type),
		b.setAttribute("class", "element comp_button editable-text"),
		b.innerHTML = "查看结果",
		b
	}),
	b.bindAfterRenderEvent("y",
	function(a, c) {
		"view" == b.mode && $(a).click(function() {
			if (!localStorage.getItem("vote_" + c.pageId)) return alert("请先投票后再查看结果！"),
			void(e = !1);
			var b = '<iframe height=698 width=510 src="' + PREFIX_URL + "all/vote.htm?pageId=" + c.pageId + '" frameborder=0 allowfullscreen></iframe>';
			$(a).hide(),
			$("#map_btn").hasClass("map_exist") && $("#map_btn").hide(),
			$('<div class="map_mask page_effect lock" id="mask_' + c.id + '" style="height:80%;overflow:auto;" ></div>').appendTo($(a).closest(".m-img")),
			$('<a class = "close_mask"  style="left: 15px;" id="close_' + c.id + '"></a>').appendTo($(a).closest(".m-img")),
			$(b).appendTo($("#mask_" + c.id)).attr("style", "position: absolute; min-height: 80%; max-height: 80%; top: 10%;z-index:9999").attr("width", "100%").removeAttr("height"),
			$("#close_" + c.id).bind("click",
			function() {
				$(a).show(),
				$("#mask_" + c.id).remove(),
				$("#close_" + c.id).remove(),
				$("#map_btn").hasClass("map_exist") && $("#map_btn").show(function() {
					$(this).hasClass("off")
				})
			})
		})
	}),
	b.addComponent("r",
	function(a) {
		var b = document.createElement("div"),
		c = a.content && a.content.split("radio_").length > 0 ? a.content.split("radio_")[1].split('"')[0] : a.id;
		return b.id = a.id,
		b.setAttribute("ctype", a.type),
		b.setAttribute("class", "element editable-radio"),
		b.setAttribute("id", "" + a.id),
		a.properties.placeholder && b.setAttribute("placeholder", a.properties.placeholder),
		a.content && (b.innerHTML = a.content.replace('value="' + c + '"', 'value="' + a.id + '"')),
		b.style.cursor = "default",
		b
	}),
	b.bindAfterRenderEvent("r",
	function(a, c) {
		"view" == b.mode && $(a).click(function() {
			var b = $(a).find('[type="radio"]')[0];
			b && b.focus()
		})
	}),
	b.addComponent("c",
	function(a) {
		var b = document.createElement("div"),
		c = a.content && a.content.split("checkbox_").length > 0 ? a.content.split("checkbox_")[1].split('"')[0] : a.id;
		return b.id = a.id,
		b.setAttribute("ctype", a.type),
		b.setAttribute("class", "element editable-checkbox"),
		b.setAttribute("id", "" + a.id),
		a.properties.placeholder && b.setAttribute("placeholder", a.properties.placeholder),
		a.content && (b.innerHTML = a.content.replace('value="' + c + '"', 'value="' + a.id + '"')),
		b.style.cursor = "default",
		b
	}),
	b.bindAfterRenderEvent("c",
	function(a, c) {
		"view" == b.mode && $(a).click(function() {
			var b = $(a).find('[type="checkbox"]')[0];
			b && b.focus()
		})
	}),
	b.addComponent("l",
	function(a) {
		var b = document.createElement("a");
		return b.setAttribute("class", "element lottery_area"),
		b.id = a.id,
		b.setAttribute("ctype", a.type),
		a.properties.src && b.setAttribute("mapurl", a.properties.src),
		b
	}),
	b.bindAfterRenderEvent("l",
	function(a, c) {
		"view" == b.mode && $(a).click(function() {
			window.location.href = c.properties.src
		})
	})
} (window.eqShow),
!
function() {
	function a(a) {
		resources.loaded = !0,
		a instanceof Array ? a.forEach(function(a) {
			b(a)
		}) : b(a)
	}
	function b(a) {
		if ("loading" != f[a.url]) {
			if (f[a.url]) return f[a.url];
			if (f[a.url] = !1, "image" == a.type) {
				var b = new Image;
				f[a.url] = "loading",
				b.onload = function() {
					f[a.url] = b,
					d() && g.forEach(function(a) {
						a()
					})
				},
				b.src = a.url
			} else "js" == a.type && (f[a.url] = "loading", $.getScript(a.url,
			function() {
				f[a.url] = !0,
				d() && g.forEach(function(a) {
					a()
				})
			}))
		}
	}
	function c(a) {
		return f[a]
	}
	function d() {
		var a = !0;
		for (var b in f) if (f.hasOwnProperty(b) && (!f[b] || "loading" == f[b])) return ! 1;
		return a
	}
	function e(a) {
		g.push(a)
	}
	var f = {},
	g = [];
	window.resources = {
		load: a,
		get: c,
		onReady: e,
		isReady: d
	}
} (),
!
function() {
	window.eqx = {},
	window.eqx.money = {
		config: {
			mode: 3,
			effectCallback: "editMoney",
			imageCallback: "countMoney",
			resources: [{
				url: CLIENT_CDN + "view/js/countMoney.js",
				type: "js"
			},
			{
				url: CLIENT_CDN + "assets/images/money/moneybg.png",
				type: "image"
			},
			{
				url: CLIENT_CDN + "assets/images/money/moremoney.png",
				type: "image"
			},
			{
				url: CLIENT_CDN + "assets/images/money/flymoney.png",
				type: "image"
			},
			{
				url: CLIENT_CDN + "assets/images/money/float.png",
				type: "image"
			},
			{
				url: CLIENT_CDN + "assets/images/money/float2.png",
				type: "image"
			},
			{
				url: CLIENT_CDN + "assets/images/money/float3.png",
				type: "image"
			}]
		}
	}
} (),
!
function(a, b) {
	a.completeEffect = function(a) {
		return a.find(".page_effect.lock").get(0) ? !1 : !0
	}
} (window, jQuery),
!
function(a, b) {
	function c(a, c) {
		completeEffect(b(".z-current")) && (C = "started", B.length || (B = l.find(".main-page")), c || (z ? (a = event, o = {
			x: a.touches[0].pageX - l.offset().left,
			y: a.touches[0].pageY - l.offset().top
		}) : o = {
			x: a.pageX - l.offset().left,
			y: a.pageY - l.offset().top
		}))
	}
	function d(a, c) {
		if (C = "turning", U.obj.property && U.obj.property.autoFlip && U.obj.property.autoFlipTime && k(), c || (z ? (a = event, p = {
			x: a.touches[0].pageX - l.offset().left,
			y: a.touches[0].pageY - l.offset().top
		}) : p = {
			x: a.pageX - l.offset().left,
			y: a.pageY - l.offset().top
		}), q = p.x - o.x, 0 > q) {
			if (v) {
				v = !1,
				z && n ? t = !0 : o.y >= l.height() / 2 ? r = !0 : o.y < l.height() / 2 && (s = !0),
				w = b(".z-current").get(0);
				var d = b(w).find(".m-img").attr("id").substring(4);
				m = b("#flip" + d),
				X || (b(w).parent(".flip-mask").get(0).nextElementSibling && b(b(w).parent(".flip-mask").get(0).nextElementSibling).find(".main-page").get(0) ? x = b(b(w).parent(".flip-mask").get(0).nextElementSibling).find(".main-page").get(0) : (x = B.first().get(0), y = !0)),
				b(x).find(".m-img").attr("id").substring(4),
				b(w).parent(".flip-mask").css({
					zIndex: 100,
					display: "block"
				}),
				b(x).addClass("z-active").parent(".flip-mask").css({
					zIndex: 99,
					display: "block"
				}),
				i(x),
				completeEffect(b(x)) || b("#audio_btn").css("opacity", 0),
				r ? (b(".z-current").css({
					top: m.height() - l.height() + "px",
					left: "0"
				}), m.css({
					top: "-" + (m.height() - l.height()) + "px"
				}), b(".turning").css({
					transformOrigin: "0% 100% 0px",
					left: l.width() + "px",
					top: l.height() + "px"
				})) : s ? b(".turning").css({
					top: "0",
					left: l.width() + "px",
					transformOrigin: "0% 0% 0px"
				}) : t && (b(".z-current").css({
					top: 0,
					left: b(this).width() - l.width() + "px"
				}), m.css({
					top: 0,
					left: -(m.width() - l.width()) + "px"
				}), b(".turning").css({
					transformOrigin: "0% 100% 0px",
					left: l.width() + "px",
					top: 0
				}))
			}
		} else if (q > 0 && v) {
			v = !1,
			u = !0,
			w = b(".z-current").get(0);
			var d = b(w).find(".m-img").attr("id").substr(4);
			m = b("#flip" + d),
			X || (b(w).parent(".flip-mask").get(0).previousElementSibling && b(b(w).parent(".flip-mask").get(0).previousElementSibling).find(".main-page").get(0) ? x = b(b(w).parent(".flip-mask").get(0).previousElementSibling).find(".main-page").get(0) : (x = B.last().get(0), y = !0)),
			i(x),
			completeEffect(b(x)) || b("#audio_btn").css("opacity", 0),
			b(w).parent(".flip-mask").css({
				display: "block"
			}),
			b(x).addClass("z-active").parent(".flip-mask").css({
				zIndex: 99,
				display: "block"
			}),
			b(".turning").css({
				top: "0",
				left: "0",
				transformOrigin: "0% 0% 0px"
			})
		}
		f(p)
	}
	function e(a, b) {
		C = "leaving";
		var c, d, e, g;
		b ? (c = a.x, d = a.y) : z ? (c = p.x - l.offset().left, d = p.y - l.offset().top) : (c = a.pageX - l.offset().left, d = a.pageY - l.offset().top),
		r ? (D = 16, e = -l.width(), g = l.height(), A = setInterval(function() {
			c = D >= c - e ? c: c - D,
			d = D >= g - d ? d: d + D,
			f({
				x: c,
				y: d
			}),
			D >= c - e && D >= g - d && (clearInterval(A), h())
		},
		10)) : s ? (D = 16, e = -l.width(), g = 0, A = setInterval(function() {
			c = D >= c - e ? c: c - D,
			d = D >= d - g ? d: d - D,
			f({
				x: c,
				y: d
			}),
			D >= c - e && D >= d - g && (clearInterval(A), h())
		},
		1)) : t ? (D = 5, e = -l.width(), A = setInterval(function() {
			c = D >= c - e ? c: c - D,
			f({
				x: c,
				y: d
			}),
			D >= c - e && (clearInterval(A), h())
		},
		1)) : u && (D = 3, e = l.width(), g = 0, A = setInterval(function() {
			c = D >= e - c ? c: c + D,
			f({
				x: c,
				y: d
			}),
			D >= e - c && (clearInterval(A), h())
		},
		1))
	}
	function f(a) {
		r || s ? (F = l.width() - a.x, r ? G = Math.abs(l.height() - a.y) : s && (G = a.y), H = G / F, I = G / Math.sqrt(F * F + G * G), J = Math.sqrt(1 - I * I), K = Math.sqrt(F * F + G * G) / 2, L = K * H, M = Math.sqrt(L * L + K * K), N = M / H, E = 180 * Math.atan(H) / Math.PI > 0 ? 180 * Math.atan(H) / Math.PI: 0, O = (l.width() - M) * J, P = (l.width() - M) * I * J, Q = (l.width() - M) * (1 - I * I), O >= 1 && (r ? (E > 1 ? b(".turning").css({
			width: M + "px",
			height: N + "px",
			backgroundColor: "#ff0000",
			background: "-webkit-linear-gradient(" + (180 - E) + "deg, #fff 10%, #d1cfc7 40%, #f2eee2 50%, transparent 50%, transparent 100%)",
			transform: "translateX(-" + (M - 3) + "px) translateY(-" + (N - 3) + "px) rotate(" + 2 * E + "deg) scaleX(-1)"
		}) : g(a), R = "0% 100% 0px", S = "rotate(-" + (90 - E) + "deg) translateY(" + O + "px)", T = "rotate(" + (90 - E) + "deg) translateY(-" + P + "px) translateX(-" + Q + "px)") : s && (E > 1 ? b(".turning").css({
			width: M + "px",
			height: N + "px",
			backgroundColor: "#000",
			background: "-webkit-linear-gradient(-" + (180 - E) + "deg, #fff 10%, #d1cfc7 40%, #f2eee2 50%, transparent 50%, transparent 100%)",
			transform: "translateX(-" + (M - 2) + "px) rotate(-" + 2 * E + "deg) scaleX(-1)"
		}) : g(a), R = "0% 0% 0px", S = "rotate(" + (90 - E) + "deg) translateY(-" + O + "px)", T = "rotate(-" + (90 - E) + "deg) translateY(" + P + "px) translateX(-" + Q + "px)"), m.css({
			zIndex: 100,
			transformOrigin: R,
			transform: S
		}), b(x).parent(".flip-mask").css({
			zIndex: 99,
			display: "block"
		}), b(x).css({
			zIndex: 1e3
		}), b(w).css({
			transformOrigin: R,
			transform: T
		}))) : t ? (b(".turning").css({
			width: (l.width() - a.x) / 2 + "px",
			height: l.height() + "px",
			left: a.x + "px",
			background: "-webkit-linear-gradient(left, #fff 0% , #d1cfc7 15%, #f2eee2 85%, #fff 100%)"
		}), m.css({
			transformOrigin: "0% 50% 0px",
			left: 0,
			transform: "translateX(-" + (m.width() - a.x) + "px)"
		}), b(w).css({
			transformOrigin: "0% 50% 0px",
			transform: "translateX(" + (m.width() - a.x) + "px)"
		})) : u && (m.css({
			zIndex: 100,
			transformOrigin: "0% 50% 0px",
			transform: "translateX(" + a.x + "px)"
		}), b(w).css({
			transformOrigin: "0% 50% 0px",
			transform: "translateX(-" + a.x + "px)"
		}), b(".turning").css({
			width: l.width() - a.x + "px",
			height: l.height() + "px",
			left: -(l.width() - 2 * a.x) + "px",
			background: "-webkit-linear-gradient(left, #fff 0% , #d1cfc7 15%, #f2eee2 85%, #fff 100%)"
		}))
	}
	function g(a) {
		b(".turning").css({
			width: (l.width() - a.x + 6) / 2 + "px",
			height: l.height() + "px",
			top: 0,
			left: a.x + 2 + "px",
			background: "-webkit-linear-gradient(left, #fff 0% , #d1cfc7 10%, #f2eee2 90%, #fff 100%)",
			transform: "",
			border: 0
		})
	}
	function h() {
		U.obj.property && U.obj.property.autoFlip && U.obj.property.autoFlipTime && j(),
		utilSound.pause();
		var a = b("#report0");
		a.length && a.remove(),
		C = "feeling",
		r = !1,
		s = !1,
		t = !1,
		u = !1,
		v = !0,
		q = 0,
		b(".flip-mask").css({
			transform: "",
			top: 0,
			left: 0,
			zIndex: 0
		}),
		b(w).removeClass("z-current").css({
			transform: "",
			top: 0,
			left: 0
		}),
		b(x).removeClass("z-active").addClass("z-current").css({
			transform: ""
		}),
		b(".turning").css({
			width: 0,
			height: 0,
			top: 0,
			left: 0,
			transform: "",
			background: "none"
		}),
		w = x;
		var c = b(x).find(".m-img").attr("id").substring(4);
		b("#flip" + c).css({
			zIndex: 100
		}),
		b("#audio_btn").css("opacity", 1),
		x = null
	}
	function i(a) {
		if (a) {
			var c = b(a).find(".m-img").attr("id").charAt(4);
			b(a).find("li").each(function(a) {
				for (var d = 0; d < U.list[c - 1].elements.length; d++) U.list[c - 1].elements[d].id == parseInt(b(this).attr("id").substring(7), 10) && qsdCommon.animation(b(this), U.list[c - 1].elements[d], "view")
			})
		}
	}
	function j() {
		Y = setInterval(function() {
			return completeEffect(b(".z-current")) ? void a.turnBookNextPage() : void k()
		},
		1e3 * V)
	}
	function k() {
		clearInterval(Y)
	}
	var l = b(".nr"),
	m = null,
	n = isAndroid(),
	o = {},
	p = {},
	q = 0,
	r = !1,
	s = !1,
	t = !1,
	u = !1,
	v = !0,
	w = null,
	x = null,
	y = !0,
	z = mobilecheck(),
	A = null,
	B = [],
	C = "feeling",
	D = 0,
	E = 0,
	F = 0,
	G = 0,
	H = 0,
	I = 0,
	J = 0,
	K = 0,
	L = 0,
	M = 0,
	N = 0,
	O = 0,
	P = 0,
	Q = 0,
	R = 0,
	S = 0,
	T = 0,
	U = null,
	V = 0,
	W = "",
	X = !1;
	a.turnBook = function(a) {
		U = a,
		U.obj.property && U.obj.property.autoFlip && U.obj.property.autoFlipTime && (V = U.obj.property.autoFlipTime, j()),
		b('<div class="turning"></div>').appendTo(".nr"),
		b(".main-page").css({
			width: b(".nr").width() + "px",
			height: b(".nr").height() + "px"
		}),
		l.on(z ? "touchstart": "mousedown",
		function(a) {
			"feeling" == C && (c(a), b(".main-page").css({
				width: l.width() + "px",
				height: l.height() + "px"
			}))
		}).on(z ? "touchmove": "mousemove",
		function(a) { ("started" == C || "turning" == C) && d(a)
		}).on(z ? "touchend": "mouseup mouseleave",
		function(a) {
			return X = !1,
			b(".z-current").get(0) ? 0 == q ? (v = !0, void(C = "feeling")) : void("turning" == C && e(a)) : void 0
		})
	};
	var Y;
	a.turnBookPrePage = function() {
		"turning" != C && "leaving" != C && (o = {
			x: 0,
			y: l.height()
		},
		c(o, "mock"), C = "turning", b(".main-page").css({
			width: l.width() + "px",
			height: l.height() + "px"
		}), p = {
			x: 0,
			y: l.height()
		},
		u = !0, A = setInterval(function() {
			p.x++,
			d(p, "mock"),
			p.x <= 250 && (clearInterval(A), e(p, "mock"))
		},
		1))
	},
	a.flipBookScroll = function(a) {
		X = !0;
		for (var c, d = 0,
		e = U.list.length; e > d; d++) a == U.list[d].id && (c = U.list[d].num);
		c || (c = a),
		w = b(".z-current").get(0);
		var f = b(w).find(".m-img").attr("id").charAt(4),
		g = b(w).parent(".flip-mask").siblings(".flip-mask").find(".main-page").find("#page" + c);
		g && (x = b(g).parent(".main-page").get(0), f > c ? turnBookPrePage() : c > f && turnBookNextPage())
	},
	a.turnBookNextPage = function() {
		"turning" != C && "leaving" != C && (o = {
			x: l.width(),
			y: l.height()
		},
		c(o, "mock"), C = "turning", b(".main-page").css({
			width: l.width() + "px",
			height: l.height() + "px"
		}), p = {
			x: l.width(),
			y: l.height()
		},
		z && n ? t = !0 : r = !0, W = setInterval(function() {
			p.x -= 5,
			p.y -= 5,
			d(p, "mock"),
			p.x <= 200 && (clearInterval(W), e(p, "mock"))
		},
		1))
	}
} (window, jQuery);
var eqxiu = function() {
	function a(a) {
		if ("10" != e._scrollMode) {
			o = !0;
			for (var d, f = 0,
			g = e._pageData.length; g > f; f++) a == e._pageData[f].id && (d = e._pageData[f].num);
			d || (d = a);
			var h = $(e.$currentPage).find(".m-img").attr("id").charAt(4),
			i = $(e.$currentPage).siblings(".main-page").find("#page" + d);
			if (!i) return;
			e.$activePage = $(i).parent(".main-page").get(0),
			h > d ? b() : d > h && c()
		} else flipBookScroll(a)
	}
	function b() {
		if ("10" != e._scrollMode) {
			var a = 0;
			f();
			var b = setInterval(function() {
				a += 2,
				"0" == e._scrollMode || "1" == e._scrollMode || "2" == e._scrollMode || "6" == e._scrollMode || "7" == e._scrollMode || "8" == e._scrollMode || "11" == e._scrollMode ? t = a: ("3" == e._scrollMode || "4" == e._scrollMode || "5" == e._scrollMode || "9" == e._scrollMode) && (s = a),
				g(),
				a >= 21 && (clearInterval(b), h())
			},
			1)
		} else turnBookPrePage()
	}
	function c() {
		if ("10" != e._scrollMode) {
			k = !1;
			var a = 0;
			f();
			var b = setInterval(function() {
				a -= 2,
				"0" == e._scrollMode || "1" == e._scrollMode || "2" == e._scrollMode || "6" == e._scrollMode || "7" == e._scrollMode || "8" == e._scrollMode || "11" == e._scrollMode ? t = a: ("3" == e._scrollMode || "4" == e._scrollMode || "5" == e._scrollMode || "9" == e._scrollMode) && (s = a),
				g(),
				-21 >= a && (clearInterval(b), h())
			},
			1)
		} else turnBookNextPage()
	}
	function d() {
		setInterval(function() {
			$(document.activeElement).is("input, textarea") || "10" != e._scrollMode && c()
		},
		l)
	}
	var e, f, g, h, i, j, k, l, m = $(window),
	n = !0,
	o = !1,
	p = mobilecheck(),
	q = 0,
	r = 0,
	s = 0,
	t = 0,
	u = !1,
	v = !1,
	w = !0,
	x = 500,
	y = .4,
	z = function(a, b, c, k) {
		function z(a, b, c) {
			for (var d = ["", "webkit", "moz"], e = 0, f = d.length; f > e; e++) {
				0 != e || mobilecheck() || (b = b.substring(0, 1).toLowerCase() + b.substring(1, b.length));
				var g = c instanceof Array ? c[e] : c,
				h = d[e] + b;
				a[h] = g
			}
		}
		function A(a, b, c) {
			for (var d = ["", "-webkit-", "-moz-"], e = 0; e < d.length; e++) a.css(d[e] + b, c)
		}
		function B() {
			var a;
			v && (z(e.$currentPage.style, "Transform", "scale(1)"), "0" == b || "1" == b || "2" == b || "6" == b || "11" == b ? (a = t > 0 ? "": "-", z(e.$activePage.style, "Transform", "translateY(" + a + "100%)")) : (a = s > 0 ? "": "-", z(e.$activePage.style, "Transform", "translateX(" + a + "100%)"))),
			setTimeout(function() {
				e.$activePage.classList.remove("z-active"),
				e.$activePage.classList.remove("z-move"),
				e._isDisableFlipPage = !1
			},
			500)
		}
		function C() {
			if (Math.abs(t) > Math.abs(s) && completeEffect($(e.$currentPage))) if (t > 0) {
				if (e._isDisableFlipPrevPage) return;
				v || w ? (v = !1, w = !1, S(!0), T(!0, "bottom center", "translateY", j)) : U(!0, "translateY", j, t, e._scrollMode)
			} else if (0 > t) {
				if (e._isDisableFlipNextPage) return; ! v || w ? (v = !0, w = !1, S(!1), T(!1, "top center", "translateY", j)) : U(!1, "translateY", j, t, e._scrollMode)
			}
		}
		function D() {
			Math.abs(t) > Math.abs(s) && Math.abs(t) > 20 ? (V("translateY", t, j, e._scrollMode), $(document).trigger("flipend")) : (e._isDisableFlipPage = !1, B())
		}
		function E() {
			if (Math.abs(s) > Math.abs(t) && completeEffect($(e.$currentPage))) if (s > 0) {
				if (e._isDisableFlipPrevPage) return;
				v || w ? (v = !1, w = !1, S(!0), T(!0, "center right", "translateX", i)) : U(!0, "translateX", i, s, e._scrollMode)
			} else if (0 > s) {
				if (e._isDisableFlipNextPage) return; ! v || w ? (v = !0, w = !1, S(!1), T(!1, "center left", "translateX", i)) : U(!1, "translateX", i, s, e._scrollMode)
			}
		}
		function F() {
			Math.abs(s) > Math.abs(t) && Math.abs(s) > 20 ? (V("translateX", s, i, e._scrollMode), $(document).trigger("flipend")) : (e._isDisableFlipPage = !1, B())
		}
		function G() {
			if (Math.abs(s) > Math.abs(t) && completeEffect($(e.$currentPage))) if (s > 0) {
				if (e._isDisableFlipPrevPage) return;
				v || w ? (v = !1, w = !1, S(!0), i = p ? window.innerWidth: $(".nr").width(), T(!0, "", "translateX", i)) : U(!0, "translateX", i, s, e._scrollMode)
			} else if (0 > s) {
				if (e._isDisableFlipNextPage) return; ! v || w ? (v = !0, w = !1, S(!1), i = p ? window.innerWidth: $(".nr").width(), T(!1, "", "translateX", i)) : U(!1, "translateX", i, s, e._scrollMode)
			}
		}
		function H() {
			Math.abs(s) > Math.abs(t) && Math.abs(s) > 20 ? (V("translateX", s, i, e._scrollMode), $(document).trigger("flipend")) : (e._isDisableFlipPage = !1, B())
		}
		function I() {
			if (Math.abs(t) > Math.abs(s) && completeEffect($(e.$currentPage))) if (t > 0) {
				if (e._isDisableFlipPrevPage) return;
				v || w ? (v = !1, w = !1, S(!0), j = p ? window.innerHeight: $(".nr").height(), T(!0, "", "translateY", j)) : U(!0, "translateY", j, t, e._scrollMode)
			} else if (0 > t) {
				if (e._isDisableFlipNextPage) return; ! v || w ? (v = !0, w = !1, S(!1), j = p ? window.innerHeight: $(".nr").height(), T(!1, "", "translateY", j)) : U(!1, "translateY", j, t, e._scrollMode)
			}
		}
		function J() {
			Math.abs(t) > Math.abs(s) && Math.abs(t) > 20 ? (V("translateY", t, j, e._scrollMode), $(document).trigger("flipend")) : (e._isDisableFlipPage = !1, B())
		}
		function K() {
			if (Math.abs(t) > Math.abs(s) && completeEffect($(e.$currentPage))) if (t > 0) {
				if (e._isDisableFlipNextPage) return; ! v || w ? (v = !0, w = !1, e.$activePage && $(e.$activePage).removeClass("z-move z-active"), S(!0), z(e.$activePage.style, "Transform", "rotateX(90deg) translateY(-" + j / 2 + "px) translateZ(" + j / 2 + "px)"), z(W.get(0).style, "Perspective", "700px"), z(W.get(0).style, "TransformStyle", "preserve-3d")) : n = !0,
				e.$activePage && e.$activePage.classList.contains("main-page") && ($(e.$activePage).addClass("z-active z-move").trigger("active").css("zIndex", 1), z(e.$currentPage.style, "Transform", "rotateX(-" + t / j * 90 + "deg) translateY(" + t / 2 + "px) translateZ(" + t / 2 + "px)"), z(e.$activePage.style, "Transform", "rotateX(" + (90 - t / j * 90) + "deg) translateY(" + ( - (j / 2) + t / 2) + "px) translateZ(" + (j / 2 - t / 2) + "px)"))
			} else if (0 > t) {
				if (e._isDisableFlipNextPage) return; ! v || w ? (v = !0, w = !1, e.$activePage && $(e.$activePage).removeClass("z-move z-active"), S(!1), z(e.$activePage.style, "Transform", "rotateX(-90deg) translateY(-" + j / 2 + "px) translateZ(-" + j / 2 + "px)"), z(W.get(0).style, "Perspective", "700px"), z(W.get(0).style, "TransformStyle", "preserve-3d")) : n = !0,
				e.$activePage && e.$activePage.classList.contains("main-page") ? ($(e.$activePage).addClass("z-active z-move").trigger("active").css("zIndex", 0), z(e.$currentPage.style, "Transform", "rotateX(" + -t / j * 90 + "deg) translateY(" + t / 2 + "px) translateZ(" + -t / 2 + "px)"), z(e.$activePage.style, "Transform", "rotateX(" + ( - 90 - t / j * 90) + "deg) translateY(" + (j / 2 + t / 2) + "px) translateZ(" + (j / 2 + t / 2) + "px)")) : (z(e.$currentPage.style, "Transform", "translateX(0px) scale(1)"), e.$activePage = null)
			}
		}
		function L() {
			Math.abs(t) > Math.abs(s) && Math.abs(t) > 20 ? (t > 0 ? z(e.$currentPage.style, "Transform", "rotateX(-90deg) translateY(" + j / 2 + "px) translateZ(" + j / 2 + "px)") : z(e.$currentPage.style, "Transform", "rotateX(90deg) translateY(-" + j / 2 + "px) translateZ(" + j / 2 + "px)"), z(e.$currentPage.style, "zIndex", "0"), z(e.$activePage.style, "Transform", "rotateX(0deg) translateY(0px) translateZ(0px)"), z(e.$activePage.style, "zIndex", "2"), $(document).trigger("flipend")) : (z(e.$currentPage.style, "Transition", "none"), z(e.$activePage.style, "Transition", "none"), e._isDisableFlipPage = !1, B())
		}
		function M() {
			if (Math.abs(t) > Math.abs(s) && completeEffect($(e.$currentPage))) if (t > 0) {
				if (e._isDisableFlipNextPage) return; ! v || w ? (v = !0, w = !1, e.$activePage && $(e.$activePage).removeClass("z-move z-active"), S(!0), z(W.get(0).style, "Perspective", "700px"), z(W.get(0).style, "TransformStyle", "preserve-3d"), z(e.$activePage.style, "TransformOrigin", "top"), z(e.$activePage.style, "Transform", "rotateX(90deg)")) : n = !0,
				e.$activePage && e.$activePage.classList.contains("main-page") && ($(e.$activePage).addClass("z-active z-move").trigger("active"), z(e.$activePage.style, "Transform", "rotateX(" + (90 - t / j * 90) + "deg) "))
			} else if (0 > t) {
				if (e._isDisableFlipNextPage) return; ! v || w ? (v = !0, w = !1, e.$activePage && $(e.$activePage).removeClass("z-move z-active"), S(!1), z(e.$activePage.style, "TransformOrigin", "bottom"), z(e.$activePage.style, "Transform", "rotateX(-90deg)"), z(W.get(0).style, "Perspective", "700px"), z(W.get(0).style, "TransformStyle", "preserve-3d")) : n = !0,
				e.$activePage && e.$activePage.classList.contains("main-page") ? ($(e.$activePage).addClass("z-active z-move").trigger("active"), z(e.$activePage.style, "Transform", "rotateX(" + ( - 90 - t / j * 90) + "deg) ")) : (z(e.$currentPage.style, "Transform", "translateX(0px) scale(1)"), e.$activePage = null)
			}
		}
		function N() {
			Math.abs(t) > Math.abs(s) && Math.abs(t) > 20 ? (t > 0 ? z(e.$activePage.style, "Transform", "rotateX(0deg)") : z(e.$activePage.style, "Transform", "rotateX(0deg)"), $(document).trigger("flipend")) : (z(e.$currentPage.style, "Transition", "none"), z(e.$activePage.style, "Transition", "none"), e._isDisableFlipPage = !1, B())
		}
		function O() {
			if (Math.abs(t) > Math.abs(s) && completeEffect($(e.$currentPage))) {
				if (t > 0) {
					if (e._isDisableFlipPrevPage) return; (v || w) && (v = !1, w = !1, e.$activePage && (e.$activePage.classList.remove("z-active"), e.$activePage.classList.remove("z-move")), S(!0), e.$activePage.style.zIndex = 2, e.$activePage && e.$activePage.classList.contains("main-page") && (e.$activePage.classList.add("z-active"), e.$activePage.classList.add("z-move")), e.$activePage.style.opacity = 0)
				} else if (0 > t) {
					if (e._isDisableFlipNextPage) return; (!v || w) && (v = !0, w = !1, e.$activePage && (e.$activePage.classList.remove("z-active"), e.$activePage.classList.remove("z-move")), S(!1), e.$activePage.style.zIndex = 2, e.$activePage && e.$activePage.classList.contains("main-page") && (e.$activePage.classList.add("z-active"), e.$activePage.classList.add("z-move")), e.$activePage.style.opacity = 0)
				}
				var a = Math.abs(t) / j * 1.3;
				e.$activePage.style.opacity = a.toFixed(1),
				a.toFixed(3) <= 1 && A($(e.$activePage), "transform", "scale(" + a.toFixed(3) + ")")
			}
		}
		function P() {
			Math.abs(t) > Math.abs(s) && Math.abs(t) > 20 ? (A($(e.$activePage), "transform", "scale(1)"), e.$activePage.style.opacity = 1, $(document).trigger("flipend")) : (z(e.$currentPage.style, "Transition", "none"), z(e.$activePage.style, "Transition", "none"), e._isDisableFlipPage = !1, B())
		}
		function Q() {
			if (Math.abs(s) > Math.abs(t) && completeEffect($(e.$currentPage))) if (s > 0) {
				if (e._isDisableFlipPrevPage) return;
				v || w ? (v = !1, w = !1, e.$activePage && (e.$activePage.classList.remove("z-active"), e.$activePage.classList.remove("z-move")), S(!0), e.$activePage && e.$activePage.classList.contains("main-page") && (e.$activePage.classList.add("z-active"), e.$activePage.classList.add("z-move")), z(W.get(0).style, "Perspective", "1000px"), A($(e.$activePage), "Transform", "scale(0.3) translateX(0) translateZ(-" + j + "px) rotateY(45deg)"), e.$activePage.style.zIndex = "0", e.$currentPage.style.zIndex = "100") : (i / 4 >= s ? A($(e.$currentPage), "Transform", "translateX(" + s + "px)") : A($(e.$currentPage), "Transform", "translateX(" + 1.5 * s + "px) scale(" + ((5 * i / 4 - s) / i).toFixed(3) + ") rotateY(" + s / i * 45 + "deg) translateZ(-" + (s - i / 4) + "px)"), A($(e.$activePage), "Transform", "scale(" + (.3 + (s - i / 4) / i).toFixed(3) + ") translateX(" + (s - i / 4 - 300) + "px) translateZ(" + (s - 3 * i / 4) + "px) rotateY(" + (45 - (s - i / 4) / i * 45) + "deg)"))
			} else if (0 > s) {
				if (e._isDisableFlipNextPage) return; ! v || w ? (v = !0, w = !1, e.$activePage && (e.$activePage.classList.remove("z-active"), e.$activePage.classList.remove("z-move")), S(!1), e.$activePage && e.$activePage.classList.contains("main-page") && (e.$activePage.classList.add("z-active"), e.$activePage.classList.add("z-move")), z(W.get(0).style, "Perspective", "1000px"), A($(e.$activePage), "Transform", "scale(0.3) translateX(" + (i + 300) + "px) translateZ(-" + j + "px) rotateY(-45deg)"), e.$activePage.style.zIndex = "0", e.$currentPage.style.zIndex = "100") : (s >= -i / 4 ? A($(e.$currentPage), "Transform", "translateX(" + s + "px)") : A($(e.$currentPage), "Transform", "translateX(" + 1.5 * s + "px) scale(" + ((5 * i / 4 + s) / i).toFixed(3) + ") rotateY(" + s / i * 45 + "deg) translateZ(" + (s + i / 4) + "px)"), A($(e.$activePage), "Transform", "scale(" + (.3 - (s + i / 4) / i).toFixed(3) + ") translateX(" + ( - s - i / 4 + 200) + "px) translateZ(" + ( - s - 3 * i / 4) + "px) rotateY(-" + (45 + (s + i / 4) / i * 45) + "deg)"))
			}
		}
		function R() {
			Math.abs(s) > Math.abs(t) && Math.abs(s) > 20 ? (s > 0 ? (e.$currentPage.style.webkitTransformOrigin = "left", e.$currentPage.style.webkitTransform = "translateX(0) translateZ(-" + j + "px) rotateY(0) scale(0.2)", e.$activePage.style.webkitTransform = "translateX(0) translateZ(0) rotateY(0) scale(1)", e.$currentPage.style.zIndex = "0", e.$activePage.style.zIndex = "1") : (e.$currentPage.style.webkitTransformOrigin = "right", e.$currentPage.style.webkitTransform = "translateX(" + i + "px) translateZ(-" + j + "px) rotateY(0) scale(0.2)", e.$activePage.style.webkitTransform = "translateX(0) translateZ(0) rotateY(0) scale(1)", e.$activePage.style.zIndex = "1", e.$currentPage.style.zIndex = "0"), $(document).trigger("flipend")) : (z(e.$currentPage.style, "Transition", "none"), z(e.$activePage.style, "Transition", "none"), e._isDisableFlipPage = !1, B())
		}
		function S(a) {
			o ? n = !0 : a ? e.$currentPage.previousElementSibling && e.$currentPage.previousElementSibling.classList.contains("main-page") ? e.$activePage = e.$currentPage.previousElementSibling: (e.$activePage = e._$pages.last().get(0), n = !0) : e.$currentPage.nextElementSibling && e.$currentPage.nextElementSibling.classList.contains("main-page") ? e.$activePage = e.$currentPage.nextElementSibling: (e.$activePage = e._$pages.first().get(0), n = !0)
		}
		function T(a, b, c, d) {
			if (e.$activePage && (e.$activePage.classList.remove("z-active"), e.$activePage.classList.remove("z-move")), e.$activePage && e.$activePage.classList.contains("main-page")) {
				e.$activePage.classList.add("z-active"),
				e.$activePage.classList.add("z-move");
				var f = a ? "-": "";
				e.$activePage.style.webkitTransition = "none",
				e.$activePage.style.webkitTransform = c + "(" + f + d + "px)",
				e.$activePage.style.mozTransition = "none",
				e.$activePage.style.mozTransform = c + "(" + f + d + "px)",
				e.$activePage.style.transition = "none",
				e.$activePage.style.transform = c + "(" + f + d + "px)",
				$(e.$activePage).trigger("active"),
				b && A($(e.$currentPage), "transform-origin", b)
			} else z(e.$currentPage.style, "Transform", c + "(0px) scale(1)")
		}
		function U(a, b, c, d, f) {
			var g = a ? "-": "";
			A($(e.$activePage), "transform", b + "(" + g + (c - Math.abs(d)) + "px)"),
			"1" == f || "3" == f ? A($(e.$currentPage), "transform", "scale(" + ((c - Math.abs(d)) / j).toFixed(3) + ")") : ("5" == f || "11" == f) && A($(e.$currentPage), "transform", b + "(" + d + "px)")
		}
		function V(a, b, c, d) {
			if ("1" == d || "3" == d) A($(e.$currentPage), "transform", "scale(0.2)");
			else if ("5" == d || "11" == d) {
				var f = b > 0 ? "": "-";
				A($(e.$currentPage), "transform", a + "(" + f + c + "px)")
			} else A($(e.$currentPage), "transform", "scale(1)");
			A($(e.$activePage), "transform", a + "(0px)")
		}
		this._$app = a,
		this._$pages = this._$app.find(".main-page"),
		this.$currentPage = this._$pages.eq(0),
		this.$activePage = null,
		this._isInitComplete = !1,
		this._isDisableFlipPage = !1,
		this._isDisableFlipPrevPage = !1,
		this._isDisableFlipNextPage = !1,
		this._scrollMode = b,
		this._pageData = c,
		this.pageData = k,
		b = b,
		e = this,
		i = p ? window.innerWidth: a.width(),
		j = p ? window.innerHeight: a.height();
		var W = $("#con"),
		X = !1; ("8" == b || "9" == b) && (y = .7, x = 800),
		k.obj.property && k.obj.property.autoFlip && (l = 1e3 * k.obj.property.autoFlipTime, d()),
		function() {
			m.on("scroll.elasticity",
			function(a) {
				a.preventDefault()
			}).on("touchmove.elasticity",
			function(a) {
				a.preventDefault()
			}),
			m.delegate("img", "mousemove",
			function(a) {
				a.preventDefault()
			})
		} (),
		"10" != b ? e._$app.on("mousedown touchstart",
		function(a) {
			f(a)
		}).on("mousemove touchmove",
		function(a) {
			g(a)
		}).on("mouseup touchend mouseleave",
		function(a) {
			h(a)
		}) : turnBook(k),
		f = function(a) {
			X = !1,
			p && a && (a = event),
			e._isDisableFlipPage || (e.$currentPage = e._$pages.filter(".z-current").get(0), o || (e.$activePage = null), e.$currentPage && completeEffect($(e.$currentPage)) && (u = !0, v = !1, w = !0, s = 0, t = 0, a && "mousedown" == a.type ? (q = a.pageX, r = a.pageY) : a && "touchstart" == a.type && (q = a.touches[0].pageX, r = a.touches[0].pageY), e.$currentPage.classList.add("z-move"), z(e.$currentPage.style, "Transition", "none")))
		},
		g = function(a) {
			if (p && a && (a = event), u && e._$pages.length > 1) {
				if (a && "mousemove" == a.type ? (s = a.pageX - q, t = a.pageY - r) : a && "touchmove" == a.type && (s = a.touches[0].pageX - q, t = a.touches[0].pageY - r), !X && (Math.abs(s) > 20 || Math.abs(t) > 20)) {
					if (e.$activePage) {
						var d = $(e.$activePage).find(".m-img").attr("id").charAt(4);
						$(e.$activePage).find("li").each(function(a) {
							if (e._pageData[d - 1].elements) for (var b = 0; b < e._pageData[d - 1].elements.length; b++) $(this).attr("id") && e._pageData[d - 1].elements[b].id == parseInt($(this).attr("id").substring(7), 10) && qsdCommon.animation($(this), c[d - 1].elements[b], "view")
						})
					}
					X = !0
				}
				"0" == b || "2" == b || "1" == b ? C() : "4" == b || "3" == b ? E() : "5" == b ? G() : "6" == b ? K() : "7" == b ? M() : "8" == b ? O() : "9" == b ? Q() : "11" == b && I()
			}
		},
		h = function(a) {
			if (u && completeEffect($(e.$currentPage))) if (u = !1, e.$activePage) {
				e._isDisableFlipPage = !0;
				var c;
				c = "6" == b || "7" == b ? "cubic-bezier(0,0,0.99,1)": "linear",
				e.$currentPage.style.webkitTransition = "-webkit-transform " + y + "s " + c,
				e.$activePage.style.webkitTransition = "-webkit-transform " + y + "s " + c,
				e.$currentPage.style.mozTransition = "-webkit-transform " + y + "s " + c,
				e.$activePage.style.mozTransition = "-webkit-transform " + y + "s " + c,
				e.$currentPage.style.transition = "-webkit-transform " + y + "s " + c,
				e.$activePage.style.transition = "-webkit-transform " + y + "s " + c,
				"0" == b || "2" == b || "1" == b ? D() : "4" == b || "3" == b ? F() : "5" == b ? H() : "6" == b ? L() : "7" == b ? N() : "8" == b ? P() : "9" == b ? R() : "11" == b && J()
			} else e.$currentPage.classList.remove("z-move");
			o = !1
		},
		$(document).on("flipend",
		function(a) {
			completeEffect($(e.$currentPage)) || $("#audio_btn").css("opacity", 0);
			var c = $("#report0");
			setTimeout(function() {
				z(e.$currentPage.style, "Transition", "none"),
				$(e.$activePage).removeClass("z-active z-move").addClass("z-current"),
				$(e.$currentPage).removeClass("z-current z-move"),
				e._isDisableFlipPage = !1,
				e.$currentPage = $(e.$activePage).trigger("current"),
				$(e.$currentPage).trigger("hide"),
				utilSound.pause(),
				c.length && c.remove(),
				("8" == b || "9" == b) && ($(e.$currentPage).css("z-index", "1"), $(".main-page").attr("style", ""))
			},
			x)
		})
	};
	return {
		pageScroll: a,
		nextPage: c,
		prePage: b,
		app: z
	}
} (); !
function(a, b) {
	function c(a, c) {
		if (!b("#report0").length) {
			var e = ['<div id="report0" class="page_effect lock">', '<div id="report1">', '<div id="report2"><p class="report-hd"></p><h1>请选择举报原因</h1></div>', '<div id="report3"><ul id="reportList"></ul></div>', '<div id="report4"><a id="reportSubmit">提交举报</a></div>', '<a class="close_mask" style="left: 10px;top: 10px;"></a>', "</div>", "</div>"];
			b(e.join("")).appendTo("#page" + a),
			d(c)
		}
	}
	function d(a) {
		if (g.length) return void e(g, a);
		var c = PREFIX_URL + "complaints/getComplaintsType";
		b.ajax({
			type: "POST",
			url: c,
			xhrFields: {
				withCredentials: !0
			},
			crossDomain: !0
		}).then(function(b) {
			b.success && (g = b.list, e(g, a))
		})
	}
	function e(a, c) {
		for (var d, e = 0,
		g = a.length; g > e; e++) {
			var h = '<li value="' + a[e].Value + '"><span>' + a[e].Name + "</span></li>";
			b(h).appendTo("#reportList")
		}
		d = b("#reportList").find("li").eq(0).addClass("active").val(),
		b("#reportList").find("li").click(function(a) {
			b(this).siblings().removeClass("active"),
			b(this).addClass("active"),
			d = b(this).val()
		}),
		b("#reportSubmit").click(function(a) {
			f(c, d)
		}),
		b("#report0").parent(".m-img").click(function(a) {
			b("#report0").remove()
		}),
		b("#report0").find(".close_mask").click(function(a) {
			b("#report0").remove()
		}),
		b("#report0").click(function(a) {
			a.stopPropagation()
		})
	}
	function f(a, c) {
		var d = PREFIX_URL + "complaints/createComplaints",
		e = {
			sceneId: a,
			type: c
		};
		b.ajax({
			type: "POST",
			url: d,
			data: b.param(e),
			xhrFields: {
				withCredentials: !0
			},
			crossDomain: !0
		}).then(function(a) {
			a.success && (b("#report0").remove(), alert("举报成功！"))
		},
		function(a) {
			b("#report0").remove()
		})
	}
	a.addReport = function(a, d) {
		var e = '<li comp-drag="" comp-rotate="" class="comp-resize comp-rotate inside wsite-text" id="inside_439881" num="1" ctype="2" comp-resize="" style="width: 320px; height: 36px; left: -250px; top: 412px; z-index: 999;"><div class="element-box" style="height: 100%; z-index: 3; width: 100%; transform: none;-webkit-animation: fadeIn 3s ease 1s both;-webkit-animation-play-state: initial;"><div class="element-box-contents" style="width: 100%; height: 100%;"><div id="439881" ctype="2" class="element comp_paragraph editable-text" style="cursor: default; width: 320px; height: 36px;"><div style="text-align: right;"><span style="line-height: 1; background-color: initial;"><font class="report-text" size="2" color="#888888"><b>举报</b></font></span></div></div></div></div></li>',
		f = b("#page" + a).find(".edit_wrapper").find("ul");
		b(e).appendTo(f).click(function(b) {
			b.stopPropagation(),
			c(a, d)
		})
	};
	var g = []
} (window, jQuery),
!
function($) {
	function bindWeixin() {}
	function getRequestUrl() {
		var a;
		return a = preview ? PREFIX_URL + "m/scene/preview/" + sceneId: PREFIX_URL + "As/getAs?" + param.split("?")[1] + "&objID=" + param.split("&")[1] + "&time=" + (new Date).getTime()
	}
	function bindShare(data) {
		if (mobilecheck());
		else with(window._bd_share_config = {
			common: {
				bdSnsKey: {},
				bdText: data.obj.name,
				bdDesc: data.obj.description,
				bdSign: "on",
				bdPic: $("#ppitest>img").attr("src"),
				bdStyle: "0",
				bdSize: "32"
			},
			share: {}
		},
		document) 0[(getElementsByTagName("head")[0] || body).appendChild(createElement("script")).src = "http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion=" + ~ ( - new Date / 36e5)]
	}
	function parseJson(a) {
		a.success || (window.location.href = "http://www.70c.com/w/QZSJWZ"),
		a.obj.name ? document.title = a.obj.name.replace(/<[^>]+>/g, "") : document.title = "70C产品营销推广专家",
		$("#metaDescription").attr("content", a.obj.name + ", " + a.obj.description),
		$(".scene_title").text(document.title),
		pageMode = a.obj.pageMode,
		$("#hidePageType").attr("code", a.obj.code),
		$("#hidePageType").attr("isa", a.obj.accessCode),
		$("#ppitest").html('<img src="' + PREFIX_FILE_HOST + a.obj.image.imgSrc + '" >'),
		a.obj.image && a.obj.image.imgSrc && $("#loading .loadlogo").css("background-image", "url(" + PREFIX_FILE_HOST + a.obj.image.imgSrc + ")");
		var b = [];
		b = a.list,
		b.length <= 0 ? (alert("此产品不存在!"), window.location.href = "http://70c.com") : (parsePage(b, a), $(".loading").css("opacity", .9), setTimeout(function() {
			$(".loading").hide()
		},
		1e3), bindShare(a))
	}
	function parsePage(a, b) {
		for (var c = [], d = !1, e = b.obj.image, f = 1; f <= a.length; f++) $('<section class="main-page" ><div class="m-img" id="page' + f + '"></div></section>').appendTo(".nr"),
		10 == pageMode && ($("#page" + f).parent(".main-page").wrap('<div class="flip-mask" id="flip' + f + '"></div>'), $(".main-page").css({
			width: $(".nr").width() + "px",
			height: $(".nr").height() + "px"
		})),
		a.length > 1 && (0 == pageMode || 1 == pageMode || 2 == pageMode || 6 == pageMode || 7 == pageMode || 8 == pageMode || 11 == pageMode ? $('<section class="u-arrow-bottom"><img src="' + CLIENT_CDN + 'scene-view/img/btn01_arrow.png" /></section>').appendTo("#page" + f) : (3 == pageMode || 4 == pageMode || 5 == pageMode || 9 == pageMode || 10 == pageMode) && $('<section class="u-arrow-right"><img src="' + CLIENT_CDN + 'scene-view/img/btn01_arrow_right.png" /></section>').appendTo("#page" + f)),
		1 == f && $(".main-page").eq(0).addClass("z-current"),
		a[f - 1].properties && !$.isEmptyObject(a[f - 1].properties) ? a[f - 1].properties.image || a[f - 1].properties.scratch ? scriptLoaded.scratch ? addScratchEffect(a, f) : !
		function(b) {
			$.getScript(CLIENT_CDN + "scene-view/js/scratch_effect.js",
			function(c, d, f) {
				scriptLoaded.scratch = !0,
				addScratchEffect(e, a, b)
			})
		} (f) : a[f - 1].properties.finger ? (c.push({
			num: f,
			finger: a[f - 1].properties.finger
		}), d || (d = !0, $.getScript(CLIENT_CDN + "scene-view/js/lock_effect.js",
		function(b, d, f) {
			test(e, a, c, $(".m-img").width(), $(".m-img").height())
		}))) : a[f - 1].properties.fallingObject ? scriptLoaded.fallingObject ? fallingObject(a, f) : !
		function(b) {
			$.getScript(CLIENT_CDN + "as/view/js/falling_object.js",
			function(c, d, f) {
				scriptLoaded.fallingObject = !0,
				fallingObject(a, b),
				1 == b && playVideo(e)
			})
		} (f) : a[f - 1].properties.effect ? !
		function(b) {
			resources.load(window.eqx[a[b - 1].properties.effect.name].config.resources),
			resources.onReady(function() {
				window[a[b - 1].properties.effect.name].doEffect(e, b, a, renderPage)
			})
		} (f) : renderPage(eqShow, f, a) : (renderPage(eqShow, f, a), 1 == f && playVideo(e)),
		f == a.length && (eqxiu.app($(".nr"), b.obj.pageMode, a, b), $("img").on("dragstart",
		function(a) {
			a.preventDefault()
		}), !preview);
		addReport(a.length, b.obj.id)
	}
	function tryAddInterest(a) {
		var b = $("#hidePageType").attr("isa");
		if (b) if ("1" == b || "5" == b) $(".phone_panel, #code").css("transition", "all 0.5s"),
		$(".phone_panel").css({
			left: "35%"
		}),
		$("#code").css({
			right: "30%"
		});
		else {
			var c = document.createElement("iframe");
			c.src = PREFIX_URL + "index/interest?sceneid=" + a,
			c.className = "iframe-interst",
			document.body.appendChild(c),
			setTimeout(function() {
				c.style.right = 0
			},
			50)
		} else setTimeout(function() {
			tryAddInterest(a)
		},
		500)
	}
	var url, preview, mobileview, pageMode, branchid, sceneId, scriptLoaded = {};
	if (window.location.href.split("/w/")[1]) {
		var code = window.location.href.split("/w/")[1].split("?")[0];
		sceneId = getSceneIdByCode(code)
	} else {
		var reg = new RegExp("(^|&)sceneid=([^&]*)(&|$)", "i"),
		r = window.location.search.substr(1).match(reg);
		sceneId = r[2]
	}
	if (url = /[http|https]:\/\/.*\/all\//.test(window.location.href) ? window.location.href.split("/all/")[1] : window.location.href.split("?sceneId=")[1], JosnStr) param = "";
	else if (url) var scenedate = url.split("#")[0].split("&")[0].split("?")[0].split(".")[0],
	param = url.split(scenedate)[1];
	else param = "";
	param.indexOf("preview=preview") > 0 && (preview = !0),
	param.indexOf("branchid=") > 0 && (branchid = param.substring(param.indexOf("branchid=") + 9)),
	param.indexOf("mobileview=mobileview") > 0 && (mobileview = !0),
	isWeixin() && bindWeixin(),
	mobilecheck() ? mobilecheck() && $(".nr").css({
		width: "100%",
		height: "100%"
	}) : (addElmentsForPc = function(a) {
		var b = document.getElementsByTagName("head")[0],
		c = document.createElement("link"),
		d = $("#hidePageType").attr("code"),
		e = ($("#hidePageType").attr("isa"), "复制该模板"),
		f = "anchorBL",
		g = function(a) {
			var b = window.event || a,
			c = b.target.id;
			if ("btnselectshow" === c) {
				var d = $("#hidePageType").attr("isa");
				return "1" == d ? window.location.href = "/sceneedit.htm#/scene/create/" + sceneId + "?pageId=1": "2" == d ? window.location.href = "http://www.70c.com/login.html": "3" == d ? ($("#btnselectshow").unbind("click"), $("#btnselectshow").text("复制中..."), $.ajax({
					type: "POST",
					url: "/as/copyscene",
					data: "SceneId=" + sceneId,
					error: function(a) {
						alert("复制失败，请重新登录再试或联系管理员")
					},
					success: function(a) {
						"1" == a.result ? window.location.href = "/sceneedit.htm#/scene/create/" + a.Id + "?pageId=1": alert(a.message)
					}
				})) : "4" == d ? alert("对不起，您的用户信息还未审核通过，请稍后再试") : "5" == d && alert("您的账号已过期！"),
				!1
			}
			"btnsetproduct" === c && ("8" == sessionStorage.getItem("xszy") ? sessionStorage.setItem("xszy", "9") : "k" == sessionStorage.getItem("xszy") && sessionStorage.setItem("xszy", "l"), window.location.href = "/index/appview?Id=" + sceneId)
		};
		if (!d) {
			d = "http://qr.liantu.com/api.php?text=" + b.baseURI,
			$("#codeImg").prop("src", d);
			var h = 1,
			i = function() {
				if (xinShouZhiYin(), h++, d = $("#hidePageType").attr("code"), !d && 8 > h) setTimeout(i, 600);
				else {
					var a = $("#hidePageType").attr("isa");
					"1" == a || "5" == a ? (f = "t02", $("#btnselectshow").text("我要编辑"), $("#view_reg").attr("class", f), $("#view_reg").append("<span id='btnsetproduct'>设置封面</span>")) : "6" == a ? (f = "t01", $("#btnselectshow").text("该产品未开放复制功能"), $("#view_reg").attr("class", f).css({
						"background-color": "#aaa",
						cursor: "default"
					})) : (f = "t01", $("#btnselectshow").text("复制该模板"), $("#view_reg").attr("class", f)),
					$("#view_reg").click(g); {
						$("#codeImg").prop("src")
					}
					if (window.location.href.split("/w/")[1]) $("#codeImg").prop("src", d);
					else {
						var b = "http://qr.liantu.com/api.php?text=" + PREFIX_URL + "w/" + getCodeBySceneId(sceneId);
						$("#codeImg").prop("src", b)
					}
				}
			};
			setTimeout(i, 1e3)
		}
		c.href = CLIENT_CDN + "scene-view/pcviewer.css",
		c.rel = "stylesheet",
		b.appendChild(c),
		window != window.top ? $("body").css("background-image", "none") : ($(".phoneBox").css({}), $("#con").before('<div id="code"><div class="sharetoall">扫一扫，分享给朋友！</div><img id="codeImg" src="' + d + '" width="240" height="240" alt=""/><div id="view_share" class="bdsharebuttonbox"><a href="#" class="bds_weixin" data-cmd="weixin" title="分享到微信"></a><a href="#" class="bds_tsina" data-cmd="tsina" title="分享到新浪微博"></a><a href="#" class="bds_sqq" data-cmd="sqq" title="分享到QQ好友"></a><a href="#" class="bds_tqq" data-cmd="tqq" title="分享到腾讯微博"></a><a href="#" class="bds_qzone" data-cmd="qzone" title="分享到QQ空间"></a><a href="#" class="bds_more" data-cmd="more"></a></div><div id="view_reg" class="' + f + '"><span id="btnselectshow" >' + e + "</span></div></div>"), $(".p-index").wrap('<div class = "phone_panel"></div>'), $('<div class = "ctrl_panel"></div>').appendTo($(".phone_panel")), $('<a id = "pre_page" type = "button" class = "pre_btn btn" onclick = "eqxiu.prePage()">上一页</a>').prependTo($(".ctrl_panel")), $('<a id = "next_page" type = "button" class = "next_btn btn" onclick = "eqxiu.nextPage()">下一页</a>').appendTo($(".ctrl_panel")), tryAddInterest(a))
	},
	addElmentsForPc(sceneId));
	var code, sceneId, requestUrl;
	if (requestUrl = window.location.href.split("/w/")[1] ? preview ? a = PREFIX_URL + "m/scene/preview/" + sceneId: a = PREFIX_URL + "As/getAs?sceneid=" + sceneId + "&objID=" + param.split("&")[1] + "&time=" + (new Date).getTime() : getRequestUrl(), requestUrl = requestUrl.split("&")[0], window.location.search.indexOf("objID") > 0) {
		var reg = new RegExp("(^|&)objID=([^&]*)(&|$)", "i"),
		r = window.location.search.substr(1).match(reg),
		objID = r[2];
		objID && (requestUrl = requestUrl + "&objID=" + objID)
	} else requestUrl += "&objID=";
	requestUrl = requestUrl + "&p=" + +mobilecheck(),
	jQuery.support.cors = !0,
	$.ajax({
		type: "GET",
		url: requestUrl,
		xhrFields: {
			withCredentials: !0
		},
		crossDomain: !0
	}).done(function(a) {
		parseJson(a)
	})
} (jQuery),
$(".main").show();
var id = GetQueryString("sceneid") || GetQueryString("sceneId");
initWeixinShare(),
$(function() {
	function a(a) {
		var b = new RegExp("(^|&)" + a + "=([^&]*)(&|$)", "i"),
		c = window.location.search.substr(1).match(b);
		return null != c ? unescape(c[2]) : null
	}
	function b(a) {
		var b = a.replace(/A/g, "0").replace(/C/g, "1").replace(/F/g, "2").replace(/Z/g, "3").replace(/W/g, "4").replace(/G/g, "5").replace(/J/g, "6").replace(/Q/g, "7").replace(/Y/g, "8").replace(/S/g, "9");
		return parseInt(b)
	}
	var c = (location.href, a("sceneid"));
	if (!c) {
		var d = window.location.href.split("/w/")[1];
		c = b(d)
	}
	if ("569565" == c || "650815" == c) {
		var e = document.createElement("script");
		e.src = "//hm.baidu.com/hm.js?a487fc33e0eea88c8f3fc62ad6ff5177";
		var f = document.getElementsByTagName("script")[0];
		f.parentNode.insertBefore(e, f);
		var g = document.createElement("script");
		g.src = "http://s4.cnzz.com/z_stat.php?id=1254004615&web_id=1254004615",
		g.type = "text/javascript";
		var h = document.getElementsByTagName("HEAD").item(0);
		h.appendChild(g)
	}
	if ("569295" == c) {
		var e = document.createElement("script");
		e.src = "//hm.baidu.com/hm.js?865d7754c57c59ec938de2d07952d19d";
		var f = document.getElementsByTagName("script")[0];
		f.parentNode.insertBefore(e, f)
	}
	if ("569376" == c) {
		var e = document.createElement("script");
		e.src = "//hm.baidu.com/hm.js?78254ddde953f48a40fa20cc92cff51c";
		var f = document.getElementsByTagName("script")[0];
		f.parentNode.insertBefore(e, f)
	}
});