(function($) {
	$.fn.cartPscroll = function(options) {
		return this.each(function() {
			var $this = $(this);
			var scrollsize = $this.find('.cartpl_scroll li').length;
			console.log(scrollsize);
			var scrollP = $this.find('.cartpl_scroll');
			var scrollL = $this.find('.cartpl_scroll ul')
			var arrow = $this.find('.arrow');
			var liw = (parseInt($this.width()) - 5) / 4;
			scrollP.height(liw + 22).find('li').width(liw).parents('ul').width(liw * scrollsize);
			var scrollbar = $this.find('.cart_scrollbar span');
			scrollbar.css('width', 4 / scrollsize * 100 + '%');
			var hascrollnum = 0
			if (scrollsize <= 4) {
				$('.cart_scrollbar').hide();
				arrow.hide();
				return;
			}
			arrow.each(function() {
				$(this).on('click', function() {
					if ($(this).hasClass('fl')) {
						hascrollnum = hascrollnum - 4;
						hascrollnum = hascrollnum <= (4 - scrollsize) ? (4 - scrollsize) : hascrollnum;
					} else {
						hascrollnum = hascrollnum + 4;
						hascrollnum = hascrollnum >= 0 ? 0 : hascrollnum;
					}
					scrollL.animate({
						'left': hascrollnum * liw
					}, 500);
					scrollbar.animate({
						'left': -hascrollnum / scrollsize * 100 + '%'
					}, 500)
				});
			})
		})
	}
})(jQuery);