$(function(){
/*
	//上一页操作
	$("#page").on("click",".prev-page",function(){
		var currentpage = $("li.active").text();
		var page=Number(currentpage)-1;
		var device_type= $("#select").val();
		if($("#select").val()==-1) 
			device_type=null;
		var param={"device_type":device_type,};
		var Page ={"page":page,};
		//调用页面的查询ajax
		$.select(param,Page);
	});
	//下一页操作
	$("#page").on("click",".next-page",function(){
		var currentpage = $("li.active").text();
		page = Number(currentpage) + 1;
		var device_type= $("#select").val();
		if($("#select").val()==-1) 
			device_type=null;
		var param={"device_type":device_type,};
		var Page ={"page":page,};
		//调用页面的查询ajax
		$.select(param,Page);
	});
	
	$("#page").on("click","li",function(){
		if($(this).hasClass("active"))
			return;
		var page = $(this).html();
		if(page=="...") return;
		var device_type= $("#select").val();
		if($("#select").val()==-1) 
			device_type=null;
		var param={"device_type":device_type,};
		var Page ={"page":page,};
		//调用页面的查询ajax
		$.select(param,Page);
		
	});*/
	/**
	动态分页控件
	**/
	jQuery.pagination=function(pageinfo){
		 var total = pageinfo.total;
		 var currentpage = pageinfo.page;
		 var pagesize = pageinfo.pageSize;
		 //var beginIndex = pageinfo.beginIndex;
		 var totalpage = 0;
		 if(total>0){
			 totalpage = (total%pagesize==0?total/pagesize:parseInt(total/pagesize)+1);
			 $(".total-page.fr").html("&nbsp;&nbsp;共"+totalpage+"页");
		 }
		 $(".total-num.fr").html("共"+total+"条&nbsp;&nbsp;");
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



});
	