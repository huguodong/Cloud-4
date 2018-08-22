var checkboxType = function(){
	$('.checkbox_2').iCheck({
		checkboxClass: 'icheckbox_flat-blue',
	});
	
	$(".checkbox_2").removeAttr("checked");
	$('.checkbox_2').iCheck('uncheck', function(){
		$(".table_1_head table td").each(function(i){
			$(this).width($('.table_1_body tr:eq(0) td').eq(i).width())
		});
	});

	$('.checkbox_2_all').on('ifClicked', function(event){
		if($('.checkbox_2_all').attr('checked')){
			$('.checkbox_2').iCheck('uncheck');
			$(".checkbox_2").removeAttr("checked");
		}else{
			$('.checkbox_2').iCheck('check');
			$(".checkbox_2").attr("checked",'true');
		}
	});

	$('.checkbox_2').on('ifClicked', function(){
		if($(this).attr('checked')){
			$(this).removeAttr("checked");
			$('.checkbox_2_all').iCheck('uncheck');
			$(".checkbox_2_all").removeAttr("checked");
		}else{
			$(this).attr("checked",'true');
		}
	})
}


$(function(){
	$('.table_1').each(function(){
		$(this).before( $('<div class="table_1_head"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_1"></table></div>'));
		$(this).after( $('<div class="table_1_body"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_1"></table></div>'));
		$(this).find('thead').clone().prependTo(".table_1_head .table_1");
		$(this).find('tbody').clone().prependTo(".table_1_body .table_1");

		$('.table_1_body').height($(window).height() - 70 - $('.pagehead').outerHeight(true) - $('.detailhead').outerHeight(true) - $('.subbox').outerHeight(true) - $('.table_1_head').height() - $('.titlebox_1').outerHeight(true) - $('.table_tips').outerHeight(true))
		$(this).remove();
	});


	$(".table_1_head table td").each(function(i){
		$(this).width($('.table_1_body tr:eq(0) td').eq(i).width())
	});

	/*var tableScroll = $('.table_1_body').jScrollPane({
		autoReinitialise: true,
		mouseWheelSpeed: 60,
		verticalGutter : 10
	}).data('jsp');

	var pageresize = function(){
		if(tableScroll.getIsScrollableV()){
			$('.table_1_head').css({'padding-right':'28px'})
		}else{
			$('.table_1_head').css({'padding-right':'0'})
		}
		

	}
	pageresize();
	$(window).resize(function(){
		pageresize();
	});*/

	
	checkboxType()



	

})



