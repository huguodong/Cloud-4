// JavaScript Document
$(function(e) {
	$(document).on("change",".g-checkbox input",function(){
//		$(this).parents(".g-checkbox").toggleClass("on");
		if($(this).prop("checked")){
			$(this).parents(".g-checkbox").addClass("on");
		}else{
			$(this).parents(".g-checkbox").removeClass("on");
		}
	});
	$(document).on("change",".g-radio input",function(){
		var thisName = $(this).attr("name");
		
		$("[name='"+thisName+"']").removeAttr("checked").parents(".g-radio").removeClass("on");
		$(this).prop("checked",true).parents(".g-radio").addClass("on");
	});

	$(document).on("click",".g-chooseAll",function(){
		/*全选*/
		allInputEach($(document),true);
	});
	$(document).on("click",".g-noChooseAll",function(){
		/*反选*/
		allInputEach($(document),false);
	});


	function allInputEach(container,toChecked){
		var $container = container || $(document);

		if(toChecked){
			$container.find("input[type='checkbox']").each(function(){
				if(!$(this).is(":checked")){
					$(this).prop("checked",true).parents(".g-checkbox").addClass("on").parents(".item").addClass("active");
				}
			});
		}else{
			$container.find("input[type='checkbox']").each(function(){	
				if(!$(this).is(":checked")){
					$(this).prop("checked",true).parents(".g-checkbox").addClass("on").parents(".item").addClass("active");
				}else{
					$(this).removeAttr("checked").parents(".g-checkbox").removeClass("on").parents(".item").removeClass("active");
				}
			});
		}

	}


	/*检查页面全部的checkbox元素，是选中状态的，就给父元素添加选中状态更换背景*/
	$("input[type='checkbox']").each(function(){
		if($(this).is(":checked")){
			$(this).prop("checked",true).parents(".g-checkbox").addClass("on");
		}
	});

	$(document).on("change",".col1:eq(0) .g-checkbox input",function(){
		if(!$(this).is(":checked")){
			$("input[type='checkbox']").each(function(){		
				$(this).removeAttr("checked").parents(".g-checkbox").removeClass("on").parents(".item").removeClass("active");
			});
			
		}else{
			$("input[type='checkbox']").each(function(){
				$(this).prop("checked",true).parents(".g-checkbox").addClass("on").parents(".item").addClass("active");
			});
		}
		
	});


	$(".form-dialog-fix .form-dialog").each(function(){
		$(this).attr("date-right",$(this).css("right"));
	});

	$(document).on("click",".form-dialog-fix .shade,.form-dialog-fix .g-cancel2",function(){

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

/*	$(document).on("click",".page-title-bar .back-btn",function(){
		history.back(-1);
		return false;
	}); */

	$(document).on("click",".page-title-bar .search",function(){
		/*示例用，及时删除*/
		$(".g-loading").fadeIn();
		setTimeout(function(){
			$(".g-loading").fadeOut();
		},1000);
	});

	$(document).on("change",".refresh select",function(){
		/*示例用，及时删除*/
		$(".g-loading").fadeIn();
		setTimeout(function(){
			$(".g-loading").fadeOut();
		},1000);
	});

	$(document).on("click",".exit",function(){
		/*临时方法，及时删除*/
		window.location.href = "index.html";
	});

	$(".control-btn").on("click",function(){
		$(this).parents(".main-content").toggleClass("unfold");
		$(".left-side .item").removeClass("active").find("ul").slideUp(200);
	});
	$(".left-side .item .t").on("click",function(){
		var that = $(this);
		var $mainContent = $(this).parents(".main-content");

		if(!$mainContent.hasClass("unfold")){
			that.parents(".item").siblings(".item").removeClass("active").find("ul").slideUp(200);
			that.siblings("ul").slideToggle(200,function(){
				that.parents(".item").toggleClass("active");
			});	
		}
		$mainContent.removeClass("unfold");
	});
	/*$(".left-side .item").on("click",function(){
		$(this).toggleClass("active");
	});*/

	/*常用公共方法*/
	/*$("img").on("error",function(){
		console.log("error--"+$(this));
		var thisSrc = $(this).attr("src");
		
		setTimeout(function(){
			$(this).attr("src","images/default.jpg");
			$(this).attr("src",thisSrc+"?t="+Math.random());
		},1000);
	});*/
	function getBodySrollTop(){
		return document.documentElement.scrollTop || window.pageYOffset || document.body.scrollTop;
	}
	function throttle(fn, delay){
		/*节流*/
	 	var timer = null;
	 	return function(){
	 		var context = this, args = arguments;
	 		clearTimeout(timer);
	 		timer = setTimeout(function(){
	 			fn.apply(context, args);
	 		}, delay);
	 	};
	};
	/*对不支持数组indexof的浏览器添加此方法*/
	if (!Array.prototype.indexOf){  
        Array.prototype.indexOf = function(elt /*, from*/){  
        var len = this.length >>> 0;  
        var from = Number(arguments[1]) || 0;  
        from = (from < 0)  
             ? Math.ceil(from)  
             : Math.floor(from);  
        if (from < 0)  
          from += len;  
        for (; from < len; from++)  
        {  
          if (from in this &&  
              this[from] === elt)  
            return from;  
        }  
        return -1;  
      };  
    }  
	
	/*下拉框icon 点击触发select*/
	$(document).on("click",".g-select .arr-icon",function(e){
		openSelect($(this).siblings("select"));
	});

	function openSelect(elem) {
		if (document.createEvent) {
		    var e = document.createEvent("MouseEvents");
		    e.initMouseEvent("mousedown");
		    elem[0].dispatchEvent(e);
		} else if (element.fireEvent) {
		    elem[0].fireEvent("onmousedown");
		}
	}
	
	
});
