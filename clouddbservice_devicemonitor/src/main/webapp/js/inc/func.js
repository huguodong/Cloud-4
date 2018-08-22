var matrixRegex,matches=[];
var scaleZoom = 1;
$(function(){
	/*Img Max-width*/
	$(".edittext img").each(function(){
		if($(this).width() > $(this).parents('.edittext').width()){
			$(this).width("100%");
			$(this).height("auto");
		}
	});

	$('.hoverlist li, .hovertable tbody tr').mouseover(function(){
		$(this).addClass('cur');
	}).mouseout(function(){
		$(this).removeClass('cur');
	});

	/*Img Loading*/
	//$('.productsort .img, .productlist .img img').attr("src", function() { return this.name });
	
	
	if($('.pbody_out').css('transform') != null){
		matrixRegex = /matrix\((-?\d*\.?\d+),\s*0,\s*0,\s*(-?\d*\.?\d+),\s*0,\s*0\)/
		matches = $('.pbody_out').css('transform').match(matrixRegex)
		scaleZoom = matches[1];
	}

	var resizeFunc = function(){
		if(($(window).width()/$(window).height()) > (1920/1080)){
			$('body').css({
				'background-size':'100% auto, 100% auto'
			});
		}else{
			$('body').css({
				'background-size':'100% auto, auto 100%'
			});
		};
		$('.pbody_out').each(function(){
			$(this).css({
				//'margin-left':-$(this).width()/2
			})
		})
		$('.pbody').css({'min-height':$(window).height()-70});

		$('.table_1_body').height($(window).height() - 70 - $('.pagehead').outerHeight(true)*scaleZoom - $('.detailhead').outerHeight(true)*scaleZoom - $('.subbox').outerHeight(true)*scaleZoom - $('.table_1_head').height()*scaleZoom - $('.titlebox_1').outerHeight(true)*scaleZoom - $('.table_tips').outerHeight(true)*scaleZoom)
		$('.booklist').height($(window).height() - 70 - $('.pagehead').outerHeight(true)*scaleZoom - $('.detailhead').outerHeight(true)*scaleZoom - $('.subbox').outerHeight(true)*scaleZoom - $('.search_result').outerHeight(true)*scaleZoom - $('.titlebox_1').outerHeight(true)*scaleZoom - $('.table_tips').outerHeight(true)*scaleZoom)

		
	};
	resizeFunc();
	
	$(window).resize(function(){
		resizeFunc();
	});







	setTimeout(function(){
		$('body').removeClass('body-loading');
	},3000);

	/*$('a:not(.no_loading)[href!="#"]').each(function(){
		$(this).click(function(){
			$('body').addClass('body-loading');
			var href = $(this).attr('href')
			setTimeout(function(){
				window.location.href = href;
			},500);
			setTimeout(function(){
				$('body').removeClass('body-loading');
			},2000);
			return false;
		})
	});*/
	$('body').removeClass('body-loading');
})



