// JavaScript Document
$(function(e) {


	$(".form-dialog-fix .form-dialog").each(function(){
		$(this).attr("date-right",$(this).css("right"));
	});

	$(".form-dialog-fix .shade,.form-dialog-fix .g-cancel2").on("click",function(){

		var thisRight =  $(this).parents(".form-dialog-fix").find(".form-dialog").attr("date-right");

		$(this).parents(".form-dialog-fix").find(".form-dialog").animate({
			"right":thisRight
		},function(){
			$(this).parents(".form-dialog-fix").fadeOut(100);
		});
		$("body").css("overflow","visible");
	});

/*	$(".yunxing.form-dialog-fix .shade,.yunxing.form-dialog-fix .g-cancel2").on("click",function(){
		alert("yunxing")
		$(this).parents(".form-dialog-fix").find(".form-dialog").css({
			"right":-900
		},function(){
			$(this).parents(".form-dialog-fix").fadeOut(100);
		});
		
	});*/



});
