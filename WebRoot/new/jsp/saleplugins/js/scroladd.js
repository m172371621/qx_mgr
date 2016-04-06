;
(function($) {
	var doc = document,
		win = window,
		$bd = $('body');

	function Scroladd(el, options) {
		this.$element = $(el); //waterfall wrapper
		this.$loading = $('#' + options.loading_id); //loading
		this.$page = $('#' + options.page_id); //分页
		this.datatype = options.datatype,
			this.diff = options.diff; //加载线
		this.api = options.api; //请求url
		this.params = options.params; //请求参数
		this.template = options.tpl; //模板
		this.max_page = options.max_page; //最多显示多少页
		this.page = options.params.page; //当前请求第几页
		this.per_page = options.params.per_page; //每次请求加载条数
		this.auto_load = options.auto_load; //是否自动加载
		this.data_last = false; //记示是否到最后一页
		this.load_data = options.load_data; //
		this.no_data = options.no_data;
		this.ishowno = options.ishowno,
			//console.log(Object.prototype.toString.call(options.tpl));
			this.init();
	};

	/*
	 * Public method definition
	 */
	Scroladd.prototype = {
		constructor: 'Scroladd',
		/**
		 * 请求api数据
		 */
		_requestData: function() {
			var self = this,
				api = self.api,
				q = self.query,
				timestamp = new Date().getTime(),
				params = self.params;
			params.q = q;
			params.timestamp = timestamp; //api增加时间戳
			this._showLoading(); // 加载数据前显示loading
			//如果是最后一页，返回
			if (this.data_last) {
				return;
			}
			$.ajax({
				url: api,
				data: params,
				dataType: self.datatype,
				async: false, //同步请求
				success: function(result) {
					self._handleResponse(result);
				},
				error: function(a, b, c) {
					self._hideLoading();
				}
			});
		},
		/**
		 * 处理返回的请求数据
		 * @param {Object} data
		 */
		_handleResponse: function(re) {
			var html = this._renderContent(re),
				count = re.count;
			if (count > 0) {
				this._fillContent(html); //this._hideLoading();
				this._updatePage(count);
			} else {
				this._hideLoading();
				this._updatePage(count);
			}
		},

		/**
		 * 渲染内容，将data数据处理为html
		 * @param {Object} data
		 */
		_renderContent: function(a) {
			var content = '';
			if (this.datatype == 'html') {
				content = data;
			} else {
				content = template(this.template, a);
			}
			return content;
		},
		/**
		 * 填充内容,将html插入提示层
		 * @param {Object} $html
		 */
		_fillContent: function($html) {
			this.$element.append($html);
		},

		/**
		 * 更新page
		 * @param {Number} count
		 */
		_updatePage: function(count) {

			if (count >= this.per_page) {
				this.params.page += 1;
			} else {
				this.data_last = true;
				this._noData();
			}
		},

		/**
		 * 显示分页
		 */
		_showPage: function() {
			this.$page.show();
		},

		/**
		 * 没有数据处理
		 */
		_noData: function() {
			var $no_data = $(this.no_data);
			if (this.ishowno) {
					this.$element.after($no_data);
			}
			this.$loading.hide();

		},

		/**
		 * 显示loading
		 */
		_showLoading: function() {
			this.$loading.show();
		},

		/**
		 * 隐藏loading
		 */
		_hideLoading: function() {
			this.$loading.hide();
		},
		/**
		 * 页面滚动
		 */
		_scroll: function() {
			var max_page = this.max_page,
				diff = this.diff, //为正时可以看到瀑布流底部
				load_line, //预加载线
				min_col_height,
				next_page = this.params.page;

			if (next_page <= max_page) {
				load_line = $(win).scrollTop() + $(win).height() - this.$element.offset().top - diff;
				min_col_height = this.$element.height();
				if (load_line > min_col_height && this.data_last === false) {
					this._requestData();
				}
			} else if (next_page > max_page) {
				if (!this.$page.is(":visible")) {
					this._showPage();
				}
			}
		},

		/**
		 * 页面滚动
		 */
		_doScroll: function() {
			var timer, _self = this
			$(win).off('scroll');
			$(win).on('scroll', function() {
				clearTimeout(timer);
				timer = setTimeout(function() {
					_self._scroll();
				}, 100);
			});
		},



		/**
		 * init
		 */
		init: function() {
			//第一屏是否自动加载数据
			if (this.auto_load) {
				this._requestData();
			}
			this._doScroll();
		}
	};

	/*
	 * Wataerfall plugin definition
	 */
	$.fn.scroladd = function(option) {
			var options = $.extend({}, $.fn.scroladd.defaults, option);
			return this.each(function() {
				var scroladd = new Scroladd(this, options);
				//向外暴露waterfall方法
				$.scroladd = scroladd;
			});
		}
		/**
		 * Default options
		 */
	$.fn.scroladd.defaults = {
		loading_id: 'scrolladd-loading',
		page_id: 'page',
		no_data: '<div id="scrolladd-no-data" class="ptb txt_center">没有更多了...</div>',
		max_page: 8,
		diff: -100,
		datatype: 'json',
		auto_load: true,
		tpl: '',
		api: '',
		ishowno: false,
		params: {
			page: 1,
			per_page: 20
		}
	}

}(jQuery));