$(function(){
	/*底部计数功能*/
	function calculateInputNum(){
		var checkBoxLen = $("input[type='checkbox']:checked").length;
		console.log("checkBoxLen",checkBoxLen);
		$("#ChooseNum").find(".total-choosen-num").html(checkBoxLen);
		return  checkBoxLen;
	}
	/**
	动态分页控件,机器监控分页专用
	**/
	jQuery.diviceMainPagination=function(pageinfo){
		 console.log("pageinfo",pageinfo);
		 var total = pageinfo.total;
		 var currentpage = pageinfo.page;
		 //var pagesize = pageinfo.pageSize;
		 //var beginIndex = pageinfo.beginIndex;
		 //var totalpage =(total%pagesize==0?total/pagesize:parseInt(total/pagesize)+1);
		 var totalpage=total;//总页数
		 var totalpageNum=pageinfo.device_num;
		// console.log("底部页数:"+totalpageNum);
		 calculateInputNum();/*底部计数功能*/
		 $(".total-page.fr").html("共"+totalpage+"页");
		 $(".total-num.fr").html("共"+totalpageNum+"条&nbsp;");
		 var str =null;
		 if(currentpage>1){
			str = "<div class='prev-page'>上一页</div><ul>";
		 }else{
		 	str = "<ul>";
		 }
		 $("#page").html("");
		 if(totalpage == 1){
		 	str+="<li class='active'>1</li></ul>";
		 	$("#page").html(str);
		 } 
		 if(totalpage>1 && totalpage<=8){
		 	for(var count=1;count<=totalpage;count++){
		 		if(currentpage == count){
		 			str+="<li class='active'>"+count+"</li>";
		 		}else{
		 			str+="<li>"+count+"</li>";
		 		}
		 	}
		 	if(currentpage == totalpage){
		 		str+="</ul>";
		 	}else{
		 		str+="</ul><div class='next-page'>下一页</div>";
		 	}
		 	$("#page").html(str);
		 }
		 if(totalpage>8){
		 	if(currentpage<=3){
		 		for(var count=1;count<=5;count++){
		 			if(currentpage == count){
		 			str+="<li class='active'>"+count+"</li>";
		 			}else{
		 				str+="<li>"+count+"</li>";
		 			}
		 		}
		 		str+="<li>...</li>";
		 		str+="<li>"+(totalpage-1)+"</li>";
		 		str+="<li>"+totalpage+"</li>";
		 		str+="</ul><div class='next-page'>下一页</div>";
		 		$("#page").html(str);
		 	}
		 	if(currentpage>3 &&currentpage<=5){
		 		for(var count=1;count<=currentpage;count++){
		 			if(currentpage == count){
		 			str+="<li class='active'>"+count+"</li>";
		 			}else{
		 				str+="<li>"+count+"</li>";
		 			}
		 		}
		 		str+="<li>"+(currentpage+1)+"</li>";
		 		str+="<li>"+(currentpage+2)+"</li>";
		 		str+="<li>...</li>";
		 		str+="<li>"+(totalpage-1)+"</li>";
		 		str+="<li>"+totalpage+"</li>";
		 		str+="</ul><div class='next-page'>下一页</div>";
		 		$("#page").html(str);
		 	}
		 	if(currentpage>5 && currentpage<totalpage-4){
		 		str+="<li>1</li>";
		 		str+="<li>2</li>";
		 		str+="<li>...</li>";
		 		for(var count=currentpage-2;count<=currentpage+2;count++){
		 			if(count ==currentpage){
		 				str+="<li class='active'>"+count+"</li>";
		 			}
		 			else{
		 				str+="<li>"+count+"</li>";
		 			}
		 		}
		 		str+="<li>...</li>";
		 		str+="<li>"+(totalpage-1)+"</li>";
		 		str+="<li>"+totalpage+"</li>";
		 		str+="</ul><div class='next-page'>下一页</div>";
		 		$("#page").html(str);
		 	}
		 	if(currentpage>= totalpage-4 && currentpage< totalpage-2){
		 		str+="<li>1</li>";
		 		str+="<li>2</li>";
		 		str+="<li>...</li>";
		 		for(var count=currentpage-2; count <= totalpage; count++){
		 			if(count ==currentpage){
		 				str+="<li class='active'>"+count+"</li>";
		 			}
		 			else{
		 				str+="<li>"+count+"</li>";
		 			}
		 		}
		 		str+="</ul><div class='next-page'>下一页</div>";
		 		$("#page").html(str);
		 	}
		 	if(currentpage>=totalpage-2 && currentpage <totalpage){
		 		str+="<li>1</li>";
		 		str+="<li>2</li>";
		 		str+="<li>...</li>";
		 		for(var count =4;count>=0;count--){
		 			if(currentpage== (totalpage-count)){
		 				str+="<li class='active'>"+(totalpage-count)+"</li>";
		 			}else{
		 				str+="<li>"+(totalpage-count)+"</li>";
		 			}
		 		}
		 		str+="</ul><div class='next-page'>下一页</div>";
		 		$("#page").html(str);
		 	}
		 	if(currentpage==totalpage){
		 		str+="<li>1</li>";
		 		str+="<li>2</li>";
		 		str+="<li>...</li>";
		 		for(var count =4;count>=0;count--){
		 			if(currentpage== (totalpage-count)){
		 				str+="<li class='active'>"+(totalpage-count)+"</li>";
		 			}else{
		 				str+="<li>"+(totalpage-count)+"</li>";
		 			}
		 		}
		 		str+="</ul>";
		 		$("#page").html(str);
		 	}
		 }
	}; 
	
	/*$(".g-checkbox input").on("change",function(){
		$(this).parents(".g-checkbox").toggleClass("on");
	});
	$(".g-radio input").on("change",function(){
		var thisName = $(this).attr("name");
		
		$("[name='"+thisName+"']").removeAttr("checked").parents(".g-radio").removeClass("on");
		$(this).prop("checked",true).parents(".g-radio").addClass("on");
	});
	
	
	
	//全选和反选
	$(".g-chooseAll").on("click",function(){
		allInputEach($(document),true);
	});
	$(".g-noChooseAll").on("click",function(){
		allInputEach($(document),false);
	});
	function allInputEach(container,toChecked){
		var $container = container || $(document);
		//全选
		if(toChecked){
			$container.find("input[type='checkbox']").each(function(){
				if(!$(this).is(":checked")){
					$(this).prop("checked",true).parents(".g-checkbox").addClass("on").parents(".item").addClass("active");
				}
			});
		//反选改正
		}else{
			$container.find("input[type='checkbox']").each(function(){
				if($(this).is(":checked")){
					$(this).removeAttr("checked").parents(".g-checkbox").removeClass("on").parents(".item").removeClass("active");
				}else{
					//is not checked
					$(this).prop("checked",true).parents(".g-checkbox").addClass("on").parents(".item").addClass("active");
				}
			});
		}
	}*/
	
	
	
	
})