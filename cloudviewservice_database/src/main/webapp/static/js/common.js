// JavaScript Document
$(function(e) {
	var location = (window.location+'').split('/');  
	var basePath = location[0]+'//'+location[2]+'/'+location[3];  
	$(document).on("change"," .g-checkbox input",function(){
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
			$container.find("tbody input[type='checkbox']").each(function(){
				if(!$(this).is(":checked")){
					$(this).prop("checked",true).parents(".g-checkbox").addClass("on").parents(".item").addClass("active");
				}
			});
		}else{
			$container.find("tbody input[type='checkbox']").each(function(){	
				if(!$(this).is(":checked")){
					$(this).prop("checked",true).parents(".g-checkbox").addClass("on").parents(".item").addClass("active");
				}else{
					$(this).removeAttr("checked").parents(".g-checkbox").removeClass("on").parents(".item").removeClass("active");
				}
			});
		}
		/*操作结束后进行计算当前选中的个数*/
		calculateInputNum();
	}

	/*底部计数功能*/
	function calculateInputNum(){
		var checkBoxLen = $(".g-table tbody [type='checkbox']:checked").length;
		$(".total-choosen-num").text(checkBoxLen);
		return  checkBoxLen;
	}

	$(document).on("click",".g-table [type='checkbox']",function(){
		calculateInputNum();
	});


	/*检查页面全部的checkbox元素，是选中状态的，就给父元素添加选中状态更换背景*/
	$("input[type='checkbox']").each(function(){
		if($(this).is(":checked")){
			$(this).prop("checked",true).parents(".g-checkbox").addClass("on");
		}
	});

	/* 勾选全选 */
	$(document).on("change",".col1:eq(0) .g-checkbox input",function(){
		if(!$(this).is(":checked")){
			$("tbody input[type='checkbox']").each(function(){		
				$(this).removeAttr("checked").parents(".g-checkbox").removeClass("on").parents(".item").removeClass("active");
			});
			
		}else{
			$("tbody input[type='checkbox']").each(function(){
				$(this).prop("checked",true).parents(".g-checkbox").addClass("on").parents(".item").addClass("active");
			});
		}
		calculateInputNum();
	});

	/*侧边弹出动画部分*/
	$(".form-dialog-fix .form-dialog").each(function(){
		$(this).attr("date-right",$(this).css("right"));
	});

	$(document).on("click",".form-dialog-fix .g-cancel2",function(){

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

	//后退按钮
//	$(document).on("click",".page-title-bar .back-btn",function(){
//		history.back(-1);
//		return false;
//	});

	//查询按钮
//	$(document).on("click",".page-title-bar .search",function(){
		/*示例用，及时删除*/
//		$(".g-loading").fadeIn();
//		setTimeout(function(){
//			$(".g-loading").fadeOut();
//		},1000);
//	});
	
	//选择显示页码
//	$(document).on("change",".refresh select",function(){
		/*示例用，及时删除*/
//		$(".g-loading").fadeIn();
//		setTimeout(function(){
//			$(".g-loading").fadeOut();
//		},1000);
//	});

	$(document).on("click",".exit",function(){
		/*临时方法，及时删除*/
		//window.location.href = "index.html";
	});

	$(document).on("click",".control-btn",function(){
		$(this).parents(".main-content").toggleClass("unfold");
		$(".left-side .item").removeClass("active").find("ul").slideUp(200);
	});
	$(document).on("click",".left-side .item .t",function(){
		var that = $(this);
		var $mainContent = $(this).parents(".main-content");
		
		if(!$mainContent.hasClass("unfold")){
			that.parents(".item").find(".item2").slideToggle(200);
			that.parents(".item").toggleClass("active");
		}
		$mainContent.removeClass("unfold");
	});
	
	$(document).on("click",".left-side .item .item2 .t2",function(){
		var that = $(this);
		var $mainContent = $(this).parents(".main-content");
		
		if(!$mainContent.hasClass("unfold")){
			that.parents(".item2").toggleClass("active");
		}
		$mainContent.removeClass("unfold");
	});

	$(".add-li").on("click",function(){
		var bodyShade = '<div class="body-shade"></div>';
		if($(".body-shade").length){
			return;
		}
		$(this).append(bodyShade);
	});
	$(document).on("click",".li",function(){
		$(".body-shade").remove();
	})
	$(document).on("click",".body-shade",function(){
		$(this).remove();
		$(".li-list").hide(); 
	});
	$(document).on("click",".delete-li",function(){
		$(this).parents("li").remove();
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
	//全局的ajax设置
	$.ajaxSetup({
	   complete:function(XMLHttpRequest,textStatus){
	         if(textStatus=="error"&&XMLHttpRequest.status==401){
	              //alert('提示信息', "登陆超时！请重新登陆！");
	              window.location.href = basePath+'/index.html';
	         }
	    }
	});
	
});
/**
 * 新增 修改 删除 成功操作之后 弹窗 使用
 * FUNCTION GlobalShowMsg
 * 参数：
 * {
 * 	 showText:"",
 *   timeout:3000
 *   status:true/false
 * }
 * **/
function GlobalShowMsg(option){
	var defaults = {
		timeout : option.timeout || 1000,
		showText : option.showText || '添加配置成功',
		backgroundColor : option.status === true ? "#2ab65b" :"#ff3d3d",
		callback:function(){

		}
	};
	defaults = $.extend(defaults,option);
	layer.msg(defaults.showText, {
		area:["240px"],
		offset:["110px"],
	  	time: defaults.timeout,
	  	success: function(layero, index){
	  	    $(".layui-layer-hui").css("background-color",defaults.backgroundColor);
	  	}
	}, function(){
		defaults.callback();
	});  
}
/**
 * ajax操作屏蔽 避免因网络原因重复提交
 * **/
function GlobalGLoading(){
	$(".g-loading").fadeIn();
	setTimeout(function(){
		$(".g-loading").fadeOut();
	},1000);
}
/**如果有括号括号去掉括号**/
function showRetMessage(retMessage){
	var reg=new RegExp("^[a-zA-Z0-9_]+[\(（][\s\S]*[\)）]异常","gi");
	layer.alert(retMessage.replace(reg,""));
}

/*下拉框icon 点击触发select*/
$(document).on("click",".arr-icon",function(e){
	openSelect($(this).siblings("select"));
});
/*日期icon 点击触发日期面板*/
$(document).on("click","span.icon",function(e){
	openDataPicker($(this).siblings("input"));
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
function openDataPicker(elem){
	if(document.createEvent){
		var e=document.createEvent("Event");
		e.initEvent("focus", true, true);
		elem[0].dispatchEvent(e);
	} else if (document.fireEvent) {
	    elem[0].fireEvent("onfocus");
	}
}
/**返回顶部**/
$(document).on("click","#topcontrol",function(){
	 $('html,body').animate({scrollTop:0},"fast");  
});
/*下拉框icon 点击触发select*/
//var recordStStatus = 0;
//$(document).on("click",".g-select",function(e){
//	console.log($(this).is(":focus"));
//	openSelect($(this).find("select"),recordStStatus);
//	recordStStatus = recordStStatus?0:1;
//});
//$(document).on("mousedown",".g-select select",function(){
//	return false;
//});
//
//function openSelect(elem,selectStatus) {
//	/*假 select状态 0 为收起 ,1 为展开*/
//	if (document.createEvent) {
//	    var e = document.createEvent("MouseEvents");
//	    selectStatus === 0?e.initMouseEvent("mousedown") : e.initMouseEvent("mouseUp");
//	   	elem[0].dispatchEvent(e);
//	} else if (element.fireEvent) {
//		selectStatus === 0?elem[0].fireEvent("onmousedown") : elem[0].fireEvent("onmouseUp");
//	}
//}

/* *
 *  格式化日期, data为 timestamp类型 
 *  参数：
 * 	time：长整形的时间 1970开始
 *  format：不填时， 默认格式为yyyy-MM-dd hh:mm:ss
 * */
function formatDate(time,format){
  if(!time) return "";
  var paddNum = function(num){
    num += "";
    return num.replace(/^(\d)$/,"0$1");
  }
  var date=new Date(time);
  /* 指定格式字符 */
  var cfg = {
     yyyy : date.getFullYear() //年 : 4位
    ,yy : date.getFullYear().toString().substring(2)//年 : 2位
    ,M  : date.getMonth() + 1  //月 : 如果1位的时候不补0
    ,MM : paddNum(date.getMonth() + 1) //月 : 如果1位的时候补0
    ,d  : date.getDate()   //日 : 如果1位的时候不补0
    ,dd : paddNum(date.getDate())//日 : 如果1位的时候补0
    ,hh : date.getHours()  //时
    ,mm : date.getMinutes() //分
    ,ss : date.getSeconds() //秒
  }
  format || (format = "yyyy-MM-dd hh:mm:ss");
  return format.replace(/([a-z])(\1)*/ig,function(m){return cfg[m];});
} 

//只有一个 error-msg 的 情况下
function showErrorTips($dom,msg){
	console.log(msg);
	$dom.siblings().find("span.error-msg").html(msg)
	console.log($dom.siblings().find("span.error-msg").html())
	//$dom.after('<span class="error-msg">'+msg+'</span>');
	$dom.parents("li").addClass("error");
}
//select 下拉框 error-msg
function showErrorSelectTips($dom,msg){
	$dom.parent().siblings().remove();
	$dom.parent().after('<span class="error-msg">'+msg+'</span>');
	$dom.parents("li").addClass("error");
}


