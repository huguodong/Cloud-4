var dataSourceVal = "";//数据源
var gTreeArr = null;//区域树
var topHits = 10;
$(function(){
	var location = (window.location+'').split('/');  
	var basePath = location[0]+'//'+location[2]+'/'+location[3];  
	
	var searchArray = new Array();
	var rsearchArray = new Array();
	var hsearchArray;
	var postData = {};
	var template_idx=$("#statistics_tpl_idx").val();
	var template_name="";
	postData["statistics_tpl_idx"] = template_idx;
	$.ajax({
		url: "../statisticsTemplate/findStatisticsTemplateById",
		method: "POST",
		data: {"req":JSON.stringify(postData)},
		async:false,
		timeout:8000,
		error: function () 
		{
			
		},
		success: function (data, textStatus, jqXHR) 
		{
		   var wData = JSON.parse(data.result.statistics_tpl_value);
//		   console.log(wData);
		   var tsearchArray = jQuery.makeArray(wData.searchinfo);
		   for(var t1=0;t1<tsearchArray.length;t1++){
			   searchArray.push(tsearchArray[t1].toString().replace(/^"|"$/g, ""));
		   }
//		   for(var o=0;o<searchArray.length;o++){
//			   var o1 = JSON.parse(searchArray[o]);
//			   if(o1["statisticsVal"].length<4){
//				   var pData = {};
//				   pData["maintype_idx"] = o1["rstatistics"];
//				   $.ajax({
//						url: "../statisticsTemplate/selectBySql",
//						method: "POST",
//						data: {"req":JSON.stringify(pData)},
//						async:false,
//						timeout:8000,
//						error: function () 
//						{
//							
//						},
//						success: function (data, textStatus, jqXHR) 
//						{
//							var tA = data.result;
//							var sgroupArr = new Array();
//							for(var c=0;c<tA.length;c++){
//								var c1 = tA[c];
//								sgroupArr[c] = c1["key"]+"|"+c1["code"];
//							}
//							o1["statisticsVal"] = JSON.stringify(sgroupArr);
//							searchArray[o] = JSON.stringify(o1);
//						}
//					});
//			   }
//		   }
		   var trsearchArray = jQuery.makeArray(wData.resultinfo);
		   for(var t2=0;t2<trsearchArray.length;t2++){
			   rsearchArray.push(trsearchArray[t2].toString().replace(/^"|"$/g, ""));
		   }
		   
		   hsearchArray = wData.headerData;
		   /*for(var t3=0;t3<thsearchArray.length;t3++){
			   hsearchArray.push(thsearchArray[t3].toString().replace(/^"|"$/g, ""));
		   }*/
		   //var title = wData.headerData.templateName;
		   template_name=wData.headerData.templateName;
		   dataSourceVal = wData.headerData.dataSource;//获取数据源
		   var isOpen = wData.headerData.data_output;//0启用1禁用
		   if(isOpen==0){
			   var isShowStr = $.trim(wData.headerData.checkbox_format.toString());
			   if(isShowStr!=null&&isShowStr.length>0){
				   var isShowArr = isShowStr.split(",");
				   for(var i=0;i<isShowArr.length;i++){
					   if(isShowArr[i]=="Excel"){//显示Excel
						   $("#exportExcel").show();
					   }else if(isShowArr[i]=="Txt"){//显示Txt
						   $("#exportTxt").show();
						   
					   }
				   }
			   }
			   
		   }
		  // $("#dialogTitle").html("");//填充
		  // $("#dialogTitle").append(title);//填充
		   var shoucang="&nbsp;&nbsp;<img id='shoucang' src='../static/images/star-add2.png' style='width:20px;height:20px;cursor:pointer' title='添加到常用菜单'/>"
		   $("#dialogTitle").empty().append(template_name+shoucang);//填充
		}
	});
	
	//添加到常用菜单
	$("#shoucang").on("click", function() {
		var param={
			"setting_url" : "",
			"setting_desc" : template_name,
			"operator_idx": operator_idx,
			"service_idx" : template_idx
		};
		alert(JSON.stringify(param));
		$.ajax({
			 url:basePath+"/personalSetting/addPersonalSetting",
		 	 type:"POST",
		 	 data:{"req":JSON.stringify(param)},
		     success:function(data){
			     if(data.state){
			     	 GlobalShowMsg({showText:"添加成功,请刷新页面",status:true});
			     }else{
			    	 GlobalShowMsg({showText:data.message,status:false});
			     }
		     }
	 	 });
	});
	
	var initTree = function(data, len) {
	    return $('#tgtree').treeview({
	        //nodeIcon: 'glyphicon glyphicon-bookmark',
	        data: JSON.parse(data),
	        onNodeSelected: function(event, node) {
	        	var nodestr = node.id.replace(/;/gi,',').replace(/\s/gi,'');
	        	var pattern_char = /;/g;
	        	var count_char = 0;
	        	if(nodestr.match(pattern_char)!=null){
	        		count_char = nodestr.match(pattern_char).length;
	        		if(len>1&&(len-1)>count_char){
	        			nodestr += ",";
	        		}
	        	}
	        	var selecteVal = nodestr;
	        	if((len-1)==count_char){
	        		selecteVal += "\]";
	        	}
	        	$("#horizontalTab2").find("tbody tr").removeClass("grayTr");
	        	$("#horizontalTab2>tbody>tr[name^='["+selecteVal+"']").removeClass("whiteTr").addClass("grayTr");
				var firstIndex = $("tr[name^='["+selecteVal+"']").index();
				var size= $('#pagesize option:selected').text();
				var page = (firstIndex+1)%size==0?(firstIndex+1)/size:parseInt((firstIndex+1)/size)+1;
				if(page ==0){
					page=1;
				}
				$.select(page,size);
				scrollToLocation();
	        }
		});
	};
	
	var dataIDXAndDataName={};
	 $.ajax({
			url: "../statisticsTemplate/selectBySqls",
			method: "POST",
			async : false,
			data: {"req":"{}"},
			error: function () 
			{
				
			},
			success: function (data){
				var res = data.result;
				if(res!=null){
					for(var i=0;i<res.length;i++){
						var idx = res[i].maintype_idx;
						if(idx==21||idx==22||idx==23||idx==24){
							idx = 21;
						}
						dataIDXAndDataName[idx+"|"+res[i].key]={"name":res[i].code};
					}
				}
			}
		});
	
	//对照表
	var mainfieldExtend={};
    $.ajax({
        url: "../mfextend/selectByMfid",
        method: "POST",
        async : false,
        data: {"req":"{}"},
        success: function (data){
            var res = data.result;
            if(res!=null){
                for(var i=0;i<res.length;i++){
                    mainfieldExtend[res[i].mainfield_id]={"value":res[i].extend_id};
                }
            }
        }
    });

	
	(function mainHeightController(){
		var winH = $(window).height();
		var headerH = $(".g-header").outerHeight();
		var pageTitleBar = $(".page-title-bar").outerHeight();
		$(".template-view-content").css({
			"min-height":winH - headerH - pageTitleBar - 40
		});
	})();
	//标签页切换
	$(".panel-title li").on(
	"click",
	function() {
		var _this = $(this);
		if (!_this.hasClass("active")) {
			_this.addClass("active").siblings().removeClass(
					"active");
			var panel = _this.parents(".panel-title").next()
					.find(".panel");
			panel.hide();
			panel.eq(_this.index()).show();
		}
	});
	$("#templateCancel").click(function (){
		returnMain();
	});
	//高级查询
//	$(".button-more-search").on("click",function(){
//		$(this).toggleClass("button-more-search-active");
//		var childrens = $("#stmpMethod").find("div[class*='senior']").length;
//		var schildrens = $("#tmpMethod").find("div[class*='senior']").length;
//		if(childrens>2||schildrens>2){
//			if($(this).hasClass("button-more-search-active")){
//				$("#more-search input").val("");
//			}
//			$("#moreSearch div[class*='senior']:gt(1)").toggle();
//			if(schildrens>0&&schildrens<3){//当查询方式大于两行时，隐藏
//				$("#colTmp").toggle();
//			}
//		}
//	});
	//高级查询
	$(".button-more-search").on("click",function(){	
		//var childrens = $("#tmpMethod").find("div[class*='senior']").length;
		//if(childrens>0){//统计方式有子元素时，显示统计方式
			//$("#colTmp").toggle();
			//$("#gcolTmp").toggle();
		//}
		//if($(this).hasClass("button-more-search-active")){
//			$("#more-search input").val("");
		//}
		//$(this).toggleClass("button-more-search-active");
//		$("#more-search").toggle();
		
		//$("#stmpMethod div[class*='senior']:gt(1)").tF$oggle();
		
		$(".form-dialog-fix-search").fadeIn(600);
		$(".form-dialog-fix-search").find(".form-dialog").animate({
			"right" : 0
		});
		
		var winH = $(window).height();
		var headerH = $(".form-dialog-fix-search").find(".title").outerHeight();
		
		$(".form-dialog-fix-search").find(".form-wrap").parent().height(winH - headerH);
	});

	//重置
	$(".button-reset").on("click", function() {
		$(".search-template-md input[type='text']").val("");
		$(".search-template-md select").each(function() {
			$(this).find("option").eq(0).prop("selected", true);
		});
		$(".search-template-md textarea").val("");
		
		$(".form-dialog input[type='text']").val("");
		$(".form-dialog select").each(function() {
			$(this).find("option").eq(0).prop("selected", true);
		});
		$(".form-dialog textarea").val("");
	});
	
	
	var allType = {};// 获取统计类型
	$.ajax({
		url : '../statisticsTemplate/takeDataSource',
		method : 'POST',
		contentType : "application/json",
		data : "",
		dataType : 'json',
		async:false,
		error : function() {
			// alert("error");
		},
		success : function(data, textStatus, jqXHR) {
			var JsonResult = data.result;
			if (JsonResult != null) {
				for (var i = 0; i < JsonResult.length; i++) {
					allType[JsonResult[i].mainfield_table] = JsonResult[i].field;
				}
			}
		}
	});
	
	var mainFieldList = {};// 获取统计类型
  	// 填充统计数据源
  	  $.ajax({
		url : '../mainField/selectMainFieldList',
		method : 'POST',
		data : {"req":"{}"},
		async:false,
		error : function() {
		},
		success : function(data) {
			var JsonResult = data.result;
			for (var i = 0; i < JsonResult.length; i++) {
				var json=JsonResult[i];
				var sid=json.mainfield_field+"|"+json.mainfield_field_desc;
				if(json.ref_statistics !=null && json.ref_statistics!=""){
					mainFieldList[sid]={"ref_statistics":json.ref_statistics};
				}else{
					mainFieldList[sid]={"ref_statistics":"null"};
				}
			}
		}
	});
  	  
  	var mainType = new Array();//获取统计主类型实体
 	//填充统计的查询或结果数据源
      $.ajax({
         url: '../statisticsTemplate/queryStatisticsMaintypeList',
         method: 'POST',
         contentType: "application/json",
         data: "",
         dataType: 'json',
         async:false,
         error: function () 
         {
             //alert("error");
         },
         success: function (data, textStatus, jqXHR) {
            var JsonResult=data.result;
            if (JsonResult != null) {
            	for(var i=0;i<JsonResult.length;i++){
            		mainType[i] = JsonResult[i];
            	}
            }
         }
      });
      var staticsType = new Array();//获取统计主类型实体
      $.ajax({
 	         url: '../statisticsTemplate/selectStaticsType',
 	         method: 'POST',
 	         contentType: "application/json",
 	         data: "",
 	         dataType: 'json',
 	         async:false,
 	         error: function () 
 	         {
 	             //alert("error");
 	         },
 	         success: function (data, textStatus, jqXHR) {
 	            var JsonResult=data.result;
 	            if (JsonResult != null) {
 	            	for(var i=0;i<JsonResult.length;i++){
 	            		staticsType[i] = JsonResult[i];
 	            	}
 	            }
 	         }
 	      });
      
      //导出excel表格
      $("#exportExcel").on("click",function(){
    	startLoading();
    	var postArr = allType[dataSourceVal].split("#");//将数据源获取的数据转为数组
    	var pData = {};//得到查询条件
  		gainData(postArr,pData);
  		var rpData = {};//得到查询组条件
		rgainData(postArr,rpData,mainfieldExtend);
		var hData = new Array;//组的数据
		var cData = new Array();//不是组的数据
		var hDataIndex = new Array();//组数据的索引值
		var cNameData = new Array();
		var groupAlias = new Array();
		for(var h1=0;h1<rtreeArr.length;h1++){
			var htmp = JSON.parse(rtreeArr[h1]);
			var v = htmp.rcSubCountType;
			groupAlias.push(htmp.rcAttrAlias);
			var idx = v.indexOf('|');
			if (idx > 0){
				v = v.toString().split('|')[0];
			}
			//hData.push(v);
			//console.log(v);
			if($.trim(v)=="area"){
				$("#tmpMethod").find(".gclass").eq(h1).each(function(i){
					var $that=$(this).siblings(".value:eq("+h1+")").children();
					$that.each(function(i){
						if($(this).children().hasClass("rcountry")||$(this).children().hasClass("rprovince")||$(this).children().hasClass("rcity")||$(this).children().hasClass("rarea")){
							if($(this).hasClass("on")){
								var clazz = $(this).children().attr("class").replace("noReload","").trim().replace("r","");
								clazz = typeof(mainfieldExtend.clazz)=="undefined"?clazz:mainfieldExtend.clazz.value;
								var childrenVal = $(this).children().val();
								if(childrenVal!=null){
									hDataIndex.push(childrenVal.split("_")[0]);
								}else{
									hDataIndex.push("-1");
								}
								hData.push(clazz);
							}
						}
  					});
  				});
			}else{
				if(htmp.dataType!=null){
					hDataIndex.push(htmp.dataType.split("|")[0]);
				}else{
					hDataIndex.push("-1");
				}
				v = typeof(mainfieldExtend[v])=="undefined"?v:mainfieldExtend[v].value;
				hData.push(v);
			}
		}
		
		for(var h1=0;h1<ceilArr.length;h1++){
			var htmp = JSON.parse(ceilArr[h1]);
			var v = htmp.rcSubCountType;
			var idx = v.indexOf('|');
			if (idx > 0){
				v = v.toString().split('|')[0]+"|"+htmp.rFieldVal+"|"+htmp.subFun+"|"+htmp.subFunVal;
				cData.push(v);
				cNameData.push(v.toString().split('|')[0]+"|"+htmp.rcAttrAlias);
			}
		}
    	var pd = {};
  		pd["datasource"] = dataSourceVal;
  		pd["condition"] = pData;
  		pd["gcondition"] = JSON.stringify(rpData);
  		pd["group"] = hData+"";
  		pd["nogroup"] = cData+"";
  		pd["nogroupAlias"] = cNameData+"";
  		pd["groupAlias"] = groupAlias+"";
  		pd["fileName"] = "流通统计";
  		pd["dataIDXAndDataName"] = JSON.stringify(dataIDXAndDataName);
  		pd["dataTypeAndDataDesc"] = JSON.stringify(dataTypeAndDataDesc);
  		pd["hDataIndex"] = hDataIndex;
  		pd["gTreeArr"] = gTreeArr+"";//传递树的组合
  		$.post("../elasticsearchstatistics/checkStatistics",{"reqName":JSON.stringify(pd)},function (data){
  			if(data!=null){
  				if(data.result==null){
  					layer.alert("当前条件下，elachseach没有数据返回，不能打印数据");
  				}else{
  					$("form").remove("#form1");
  					var html = "<form action='../elasticsearchstatistics/exportStatistic' method='post' id='form1' name='form1' style='display:none'>";  
  					html += "<input type='hidden' name='reqName' value='"+JSON.stringify(pd)+"'/>";  
  					html += "</form>";
  					$("body").append(html);
  					$("#form1").submit();
  				}
  			}
  		});
//  		window.setTimeout('clock()',100);
//  		window.location.href="../login/main";
//  		location.href = "../elasticsearchstatistics/exportStatistic?req='"+eval(req)+"'";
//  		$.ajax({
//			url : '../elasticsearchstatistics/exportStatistic',
//			method : 'POST',
//			data : {"req":JSON.stringify(pd)},
//			async:false,
//			error : function() {
//				// alert("error");
//			},
//			success : function(data) {
//				
//			}
//		});
  		endLoading(2);
      });
      //导出txt表格
      $("#exportTxt").on("click",function(){
    	  startLoading();
    	  var postArr = allType[dataSourceVal].split("#");//将数据源获取的数据转为数组
    	  var pData = {};//得到查询条件
    	  gainData(postArr,pData);
    	  var rpData = {};//得到查询组条件
    	  rgainData(postArr,rpData,mainfieldExtend);
    	  var hData = new Array;//组的数据
    	  var cData = new Array();//不是组的数据
    	  var hDataIndex = new Array();//组数据的索引值
    	  var cNameData = new Array();
    	  var groupAlias = new Array();
    	  for(var h1=0;h1<rtreeArr.length;h1++){
  			var htmp = JSON.parse(rtreeArr[h1]);
  			var v = htmp.rcSubCountType;
  			groupAlias.push(htmp.rcAttrAlias);
  			var idx = v.indexOf('|');
  			if (idx > 0){
  				v = v.toString().split('|')[0];
  			}
  			
  			//hData.push(v);
  			//console.log(v);
  			if($.trim(v)=="area"){
  				$("#tmpMethod").find(".gclass").eq(h1).each(function(i){
  					var $that=$(this).siblings(".value:eq("+h1+")").children();
  					$that.each(function(i){
  						if($(this).children().hasClass("rcountry")||$(this).children().hasClass("rprovince")||$(this).children().hasClass("rcity")||$(this).children().hasClass("rarea")){
  							if($(this).hasClass("on")){
  								var clazz = $(this).children().attr("class").replace("noReload","").trim().replace("r","");
  								clazz = typeof(mainfieldExtend.clazz)=="undefined"?clazz:mainfieldExtend.clazz.value;
  								var childrenVal = $(this).children().val();
								if(childrenVal!=null){
									hDataIndex.push(childrenVal.split("_")[0]);
								}else{
									hDataIndex.push("-1");
								}
  								hData.push(clazz);
  							}
  						}
    					});
    				});
  			}else{
  				if(htmp.dataType!=null){
					hDataIndex.push(htmp.dataType.split("|")[0]);
				}else{
					hDataIndex.push("-1");
				}
  				v = typeof(mainfieldExtend[v])=="undefined"?v:mainfieldExtend[v].value;
  				hData.push(v);
  			}
  		}
    	  for(var h1=0;h1<ceilArr.length;h1++){
    		  var htmp = JSON.parse(ceilArr[h1]);
    		  var v = htmp.rcSubCountType;
    		  var idx = v.indexOf('|');
    		  if (idx > 0){
    			  v = v.toString().split('|')[0]+"|"+htmp.rFieldVal+"|"+htmp.subFun+"|"+htmp.subFunVal;
    			  cData.push(v);
    			  cNameData.push(v.toString().split('|')[0]+"|"+htmp.rcAttrAlias);
    		  }
    	  }
    	  
    	  var pd = {};
    	  pd["datasource"] = dataSourceVal;
    	  pd["condition"] = pData;
    	  pd["gcondition"] = JSON.stringify(rpData);
    	  pd["group"] = hData+"";
    	  pd["nogroup"] = cData+"";
    	  pd["nogroupAlias"] = cNameData+"";
    	  pd["groupAlias"] = groupAlias+"";
    	  pd["fileName"] = "流通统计";
    	  pd["dataIDXAndDataName"] = JSON.stringify(dataIDXAndDataName);
    	  pd["dataTypeAndDataDesc"] = JSON.stringify(dataTypeAndDataDesc);
    	  pd["hDataIndex"] = hDataIndex;
    	  pd["gTreeArr"] = gTreeArr+"";//传递树的组合
    		$.post("../elasticsearchstatistics/checkStatistics",{"reqName":JSON.stringify(pd)},function (data){
      			if(data!=null){
      				if(data.result==null){
      					layer.alert("当前条件下，elachseach没有数据返回，不能打印数据");
      				}else{
      					$("form").remove("#form4");
      					var html = "<form action='../elasticsearchstatistics/exportTxtStatistic' method='post' id='form4' name='form4' style='display:none'>";  
      					html += "<input type='hidden' name='req' value='"+JSON.stringify(pd)+"'/>";  
      					html += "</form>";
      					$("body").append(html);
      					$("#form4").submit();
      				}
      			}
      		});
//    	  $.ajax({
//  			url : '../elasticsearchstatistics/exportTxtStatistic',
//  			method : 'POST',
//  			data : {"req":JSON.stringify(pd)},
//  			async:false,
//  			error : function() {
//  				// alert("error");
//  			},
//  			success : function(data) {
//  				if(data!=null&&data.state){
//  					layer.alert("导出txt文件成功");
//  				}
//  			}
//  		});
	  		endLoading(2);
      });

	//查询
	var resultArr = null;//查询得到的结果值
	var total = 0;//总条数
	$(".button-search, .button-search2").on("click", function() {
//		console.log(dataIDXAndDataName);//////
		startLoading();
		var postArr = allType[dataSourceVal].split("#");//将数据源获取的数据转为数组
		var pDatas = gainData2(postArr);
		var pDataArr = pDatas.split(";");
		var t=0;
		$(".error-msg").remove();
		for(var p=0;p<pDataArr.length;p++){
			var pa = pDataArr[p].split(",");
			var cal="."+pa[0];
			if(typeof(regular[pa[0]])!="undefined"){
				if(pa[1] !=null && pa[1].length>0){
					var re = eval(regular[pa[0]].key);
					if(!re.test(pa[1])){
						$(cal).siblings().remove();
						$(cal).after('<span class="error-msg">'+regular[pa[0]].value+'</span>');
						t+=1;
					}else{
						$(cal).siblings().remove();
					}
				}
			}
		}
		
		if(t >0){
			return;
		}
		var k=0;
		for(var p=0;p<pDataArr.length;p++){
			var pa = pDataArr[p].split(",");
			var cal="."+pa[0];
			if(typeof(digitalText[pa[0]])!="undefined"){
				if(pa[1] !=null && pa[1].length>0){
					var re = /^-?[0-9]+(.[0-9]+)?$/;
					var numarr =  pa[1].split(".");
					var nule=0;
					if(numarr.length >1){
						nule =numarr[1].length;
					}
					if(!re.test(pa[1])){
						$(cal).siblings().remove();
						$(cal).after('<span class="error-msg">只能输入数字，请重新输入</span>');
						k+=1;
					}else if(nule >digitalText[pa[0]].numUnit){
						if(digitalText[pa[0]].numUnit.length >0){
							$(cal).siblings().remove();
							$(cal).after('<span class="error-msg">小数位数超过了设定值，请重新输入</span>');
							k+=1;
						}
					}else if(digitalText[pa[0]].numMinVal.length >0 || digitalText[pa[0]].numMaxVal.length >0){
						var panumber = parseFloat(pa[1]);
						if(digitalText[pa[0]].numMinVal.length <0){
							if(parseFloat(digitalText[pa[0]].numMaxVal) <panumber){
								$(cal).siblings().remove();
								$(cal).after('<span class="error-msg">输入值大于设定值，请重新输入</span>');
								k+=1;
							}else{
								$(cal).siblings().remove();
							}
						}else if(digitalText[pa[0]].numMaxVal.length <0){
							if(parseFloat(digitalText[pa[0]].numMinVal) >panumber){
								$(cal).siblings().remove();
								$(cal).after('<span class="error-msg">输入值小于设定值，请重新输入</span>');
								k+=1;
							}else{
								$(cal).siblings().remove();
							}
						}else{
							if(parseFloat(digitalText[pa[0]].numMaxVal) <panumber){
								$(cal).siblings().remove();
								$(cal).after('<span class="error-msg">输入值大于设定值，请重新输入</span>');
								k+=1;
							}else if(parseFloat(digitalText[pa[0]].numMinVal) >panumber){
								$(cal).siblings().remove();
								$(cal).after('<span class="error-msg">输入值小于设定值，请重新输入</span>');
								k+=1;
							}
							else{
								$(cal).siblings().remove();
							}
						}
					}else{
						$(cal).siblings().remove();
					}
				}
			}
		}
		if(k >0){
			return;
		}
		
		var m=0;
		for(var p=0;p<pDataArr.length;p++){
			var pa = pDataArr[p].split(",");
			var cal="."+pa[0];
			if(typeof(characterLength[pa[0]])!="undefined"){
				if(characterLength[pa[0]].max.length >0 || characterLength[pa[0]].min.length >0){
					if(characterLength[pa[0]].max ==null || characterLength[pa[0]].max.length<=0){
						if(pa[1].length < characterLength[pa[0]].min){
							$(cal).siblings().remove();
							$(cal).after('<span class="error-msg">长度过短，请重新输入</span>');
							m+=1;
						}else{
							$(cal).siblings().remove();
						}
					}else if(characterLength[pa[0]].min ==null || characterLength[pa[0]].min.length<=0){
						if(pa[1].length >characterLength[pa[0]].max){
							$(cal).siblings().remove();
							$(cal).after('<span class="error-msg">长度超出范围，请重新输入</span>');
							m+=1;
						}else{
							$(cal).siblings().remove();
						}
					}else{
						if(pa[1].length >characterLength[pa[0]].max){
							$(cal).siblings().remove();
							$(cal).after('<span class="error-msg">长度超出范围，请重新输入</span>');
							m+=1;
						}else if(pa[1].length < characterLength[pa[0]].min){
							$(cal).siblings().remove();
							$(cal).after('<span class="error-msg">长度过短，请重新输入</span>');
							m+=1;
						}else{
							$(cal).siblings().remove();
						}
					}
				}
			}
		}
		if(m >0){
			return;
		}
		var pData = {};//得到查询条件
		gainData(postArr,pData);
		var rpData = {};//得到查询组条件
		rgainData(postArr,rpData,mainfieldExtend);
		
		var hData = new Array;//组的数据
		var cData = new Array();//不是组的数据
		for(var h1=0;h1<rtreeArr.length;h1++){
			var htmp = JSON.parse(rtreeArr[h1]);
			var v = htmp.rcSubCountType;
			var idx = v.indexOf('|');
			if (idx > 0){
				v = v.toString().split('|')[0];
			}
			//hData.push(v);
			//console.log(v);
			if($.trim(v)=="area"){
				$("#tmpMethod").find(".gclass").eq(h1).each(function(i){
					var $that=$(this).siblings(".value:eq("+h1+")").children();
					$that.each(function(i){
						if($(this).children().hasClass("rcountry")||$(this).children().hasClass("rprovince")||$(this).children().hasClass("rcity")||$(this).children().hasClass("rarea")){
							if($(this).hasClass("on")){
								var clazz = $(this).children().attr("class").replace("noReload","").trim().replace("r","");
								clazz = typeof(mainfieldExtend.clazz)=="undefined"?clazz:mainfieldExtend.clazz.value;
								hData.push(clazz);
							}
						}
  					});
  				});
			}else{
				v = typeof(mainfieldExtend[v])=="undefined"?v:mainfieldExtend[v].value;
				hData.push(v);
			}
		}
		for(var h1=0;h1<ceilArr.length;h1++){
			var htmp = JSON.parse(ceilArr[h1]);
			var v = htmp.rcSubCountType;
			var idx = v.indexOf('|');
			if (idx > 0){
				v = v.toString().split('|')[0]+"|"+htmp.rFieldVal+"|"+htmp.subFun+"|"+htmp.subFunVal;
			}
			cData.push(v);
		}
//		console.log(cData+"");
		
		var pd = {};
		pd["datasource"] = dataSourceVal;
		pd["condition"] = pData;
		pd["gcondition"] = JSON.stringify(rpData);
		pd["group"] = hData+"";
		pd["nogroup"] = cData+"";
		
		var pd2 = {};
		pd2["datasource"] = dataSourceVal;
		pd2["group"] = hData+"";
		pd2["gcondition"] = JSON.stringify(rpData)+"";		
		if(hsearchArray.data_range)
	    	pd2["isContainSubLib"] = hsearchArray.data_range;
		if(hsearchArray.topHits){
			topHits = parseInt(hsearchArray.topHits);
			pd2["topHits"] = parseInt(hsearchArray.topHits);
		}
		$.ajax({
			url : '../elasticsearchstatistics/gtree',
			method : 'POST',
			async : false,
			data : {"req":JSON.stringify(pd2)},
			error : function() {
				// alert("error");
			},
			success : function(data) {
				var groupStr = data.result;
				if(groupStr!=null){
					groupArray = groupStr.split(";");
				}
//				alert(groupArray);
			}
		});
		gTreeArr = new Array();
		var treeStructure = null;
		var len = rtreeArr.length;

		if(len!=0){
			var rtmp = JSON.parse(rtreeArr[0]);
			var rsct = rtmp.rcSubCountType;//得到统计子类型
			var ras = rtmp.rcAttrSort;//得到排序
			if(!(typeof(rsct)=="undefined")){
				var idx = rsct.indexOf('|');
				if (idx > 0)
					rsct = rsct.toString().split('|')[1];
			}
			var dataTypeStr="";
			for(var i=0;i<rsearchArray.length;i++){
				var rs = JSON.parse(rsearchArray[i]);
				if(rs.rGroup ==1){
					if(rtmp.rcSubCountType == rs.rcSubCountType){
						dataTypeStr = rs.dataType;
					}
				}
			}
			var dataTypearr= dataTypeStr.split("|");
			dataTypeStr = dataTypearr[0];
			var parentVal = checkDate();
			if(dataTypeStr==21||dataTypeStr==22||dataTypeStr==23||dataTypeStr==24){
				dataTypeStr = 21;
			}
			if(len==1){
				var garr = new Array;
				if(groupArray[0]!=null){
					garr = groupArray[0].split(",");
				}
				if(dateBoolean(garr[0])){
					if(typeof(parentVal)!="undefined"){
						dataTypeStr=parentVal.split("_")[0];
					}
				}
				if(garr[1] !=null && garr[1].length >0) {
                    treeStructure = '[';
                    for (var i = 1; i < garr.length; i++) {
                        if (garr[i].indexOf("_") != 0) {
                            var libarr = garr[i].split("_");
                            garr[i] = libarr[libarr.length - 1];
                        }
                        var nameData = garr[i];
                        if (typeof(dataTypeAndDataDesc[dataTypeStr + "|" + garr[i]]) == "undefined") {
                            if (typeof(dataIDXAndDataName[dataTypeStr + "|" + garr[i]]) != "undefined") {
                                nameData = dataIDXAndDataName[dataTypeStr + "|" + garr[i]].name;
                                if (dataTypeStr == 21) {
                                    nameData = "";
                                    for (var code = 2; code <= garr[i].length; code += 2) {
                                        nameData += dataIDXAndDataName[dataTypeStr + "|" + garr[i].substring(0, code)].name;
                                    }
                                }
                            }
                        } else {
                            nameData = dataTypeAndDataDesc[dataTypeStr + "|" + garr[i]].data_desc;
                        }
                        treeStructure += '{' +
                            '"id": "' + garr[i] + '",' +
                            '"text": "' + nameData + '"' +
                            '},';
                        gTreeArr.push(garr[i]);
                    }
                    treeStructure = treeStructure.substring(0, treeStructure.length - 1);
                    treeStructure += ']';
                }
			}else if(len>1) {
                var garr = new Array;
                if (groupArray[0] != null) {
                    garr = groupArray[0].split(",");
                }
                if (dateBoolean(garr[0])) {
                    if (typeof(parentVal) != "undefined") {
                        dataTypeStr = parentVal.split("_")[0];
                    }
                }
                if (garr[1] != null && garr[1].length > 0) {
                    treeStructure = '[';
                    for (var i = 1; i < garr.length; i++) {
                        var childrens = "";
                        if (groupArray[1]!=null && groupArray[1].split(",")[1] != null && groupArray[1].split(",")[1].length > 0) {
                            childrens = "," + treeChild(1, rtreeArr, groupArray, garr[i], rsearchArray, dataTypeAndDataDesc, dataIDXAndDataName);
                        }
                        if (garr[i].indexOf("_") != 0) {
                            var libarr = garr[i].split("_");
                            garr[i] = libarr[libarr.length - 1];
                        }
                        var nameData = garr[i];
                        if (typeof(dataTypeAndDataDesc[dataTypeStr + "|" + garr[i]]) == "undefined") {
                            if (typeof(dataIDXAndDataName[dataTypeStr + "|" + garr[i]]) != "undefined") {
                                nameData = dataIDXAndDataName[dataTypeStr + "|" + garr[i]].name;
                                if (dataTypeStr == 21) {
                                    nameData = "";
                                    for (var code = 2; code <= garr[i].length; code += 2) {
                                        nameData += dataIDXAndDataName[dataTypeStr + "|" + garr[i].substring(0, code)].name;
                                    }
                                }
                            }
                        } else {
                            nameData = dataTypeAndDataDesc[dataTypeStr + "|" + garr[i]].data_desc;
                        }
//					console.log(nameData.data_desc);
                        treeStructure += '{' +
                            '"id": "' + garr[i] + '",' +
                            '"text": "' + nameData + '"' + childrens +
                            '},';
                    }

                    var treeStructureend = treeStructure.substring(treeStructure.length - 1);
                    if (treeStructureend == ",") {
                        treeStructure = treeStructure.substring(0, treeStructure.length - 1);
                    }
                    treeStructure += ']';
                }
            }
		}
		if(treeStructure=="]"){
			treeStructure = "[]";
		}
		
		//$('#tree').jstree(true).settings.core.data=JSON.parse(treeStructure);  
		//$('#tree').jstree(true).refresh();  
		/*$('#tree').treeview({
	          //nodeIcon: 'glyphicon glyphicon-bookmark',
	          data: JSON.parse(treeStructure)
        });*/
		initTree(treeStructure, len);
		
		//alert("treeStructure:"+treeStructure);
//		$('#tree').jstree({
//			'core' : {
//				'data' : JSON.parse(treeStructure)
//			}
//		});
		//console.log(JSON.stringify(pd));
		pd["gTreeArr"] = gTreeArr+"";//传递树的组合
		if(hsearchArray.data_range)
	    	pd["isContainSubLib"] = hsearchArray.data_range;
		if(hsearchArray.topHits)
	    	pd2["topHits"] = parseInt(hsearchArray.topHits);
		$.ajax({
			url : '../elasticsearchstatistics/statisticselasticsearch',
			method : 'POST',
			data : {"req":JSON.stringify(pd)},
//			async:false,
			error : function() {
				// alert("error");
			},
			success : function(data) {
				var horizontalHtml = "";
				$("#horizontalTab2").find("tbody").html(horizontalHtml);//初始化
				var dArr = data.result;
				resultArr = data.result;	
				if(dArr==null){
					layer.alert("该模板没有返回值，请重新设置模板");
				}else{
					for(var j=0;j<dArr.length;j++){
						var dataArr =dArr[j];
//						console.log(dataArr);
//						var dealMethod = $("#horizontalTab2").find("thead>tr>td").eq(j).prop("class").split("|");
//						var method = dealMethod[0];//函数
//						var methodVal = dealMethod[1];//函数值
						if(dataArr==null){//add by huanghuang 20170601
							layer.alert("该模板没有返回值，请重新设置模板");
							return;
						}
						for(var i=0;i<dataArr.length;i++){
							$.each(dataArr[i],function(k,v){  
								 k = k + "";
								 v = v + "";
								 if(j==0){
									 $("#horizontalTab2").find("tbody").append("<tr name='"+k.replace(/;/gi,',').replace(/\s/gi,'')+"'><td width='"+percentage(parseInt(dArr.length+1),10)+"'>"+v+"</td></tr>");
								 }else{
									 $("#horizontalTab2").find("tbody tr").eq(i).append("<td width='"+percentage(parseInt(dArr.length+1),10)+"'>"+v+"</td>");
								 }
							 });
							//var resultVal = dataArr[i].value;
//							if(method==="add"){//加
//								resultVal = parseInt(dataArr[i].value)+parseInt(methodVal);
//							}else if(method==="subtract"){//减
//								resultVal = parseInt(dataArr[i].value)-parseInt(methodVal)>-1?parseInt(dataArr[i].value)-parseInt(methodVal):0;
//							}else if(method==="multiply"){//乘
//								resultVal = parseInt(dataArr[i].value)*parseInt(methodVal);
//							}else if(method==="divide"){//除
//								resultVal = parseInt(dataArr[i].value)/parseInt(methodVal);
//							}
							/*if(j==0){
								$("#horizontalTab2").find("tbody").append("<tr name='"+dataArr[i].key.replace(/,/gi,'').replace(/\s/gi,'')+"'><td width='"+percentage(parseInt(dArr.length+1),10)+"'>"+resultVal+"</td></tr>");
							}else{
								$("#horizontalTab2").find("tbody tr").eq(i).append("<td width='"+percentage(parseInt(dArr.length+1),10)+"'>"+resultVal+"</td>");
							}*/
							
						}
						
					}
					var dataArr1 =dArr[0];
					for(var d=0;dataArr1!=null&&d<dataArr1.length;d++){
						var count = 0;
						$("#horizontalTab2").find("tbody tr").eq(d).find("td").each(function(){
							count += parseInt($(this).text());
						});
						$("#horizontalTab2").find("tbody tr").eq(d).append("<td width='"+percentage(parseInt(dArr.length+1),10)+"'>"+count+"</td>");
					}
					var len = $("#horizontalTab2").find("thead tr").eq(0).children().length-1;
					$("#horizontalTab2 thead th.number").eq(len).click();
					$("#horizontalTab2 thead th.number").eq(len).click();
					initFirstChart(resultArr,rtreeArr,rsearchArray,groupArray,dataTypeAndDataDesc,dataIDXAndDataName);//初始化第一组图表
					initChart(resultArr,rtreeArr,rsearchArray,groupArray,dataTypeAndDataDesc,dataIDXAndDataName);//初始化第二组图表
					var size= $('#pagesize option:selected').text();
					total = $("#horizontalTab2").find("tbody tr").length;
					$.select(1,size);
					$(".form-dialog-fix-search").fadeOut(600);
					$(".form-dialog-fix-search").find(".form-dialog").animate({
						"right" : -600
					});
				}
			}
		}).always(function(){
			endLoading(2);
		});
	});
	
	jQuery.select=function(pageNo,pageSize){
		var pageinfo = {};
		pageinfo["page"] = pageNo;
		pageinfo["pageSize"] = pageSize;
		pageinfo["total"] = total;
		var s = (pageNo-1)*pageSize==0?0:(pageNo-1)*pageSize-1;
		var e = pageNo*pageSize>total?total:pageNo*pageSize;
		$("#horizontalTab2").find("tbody tr").hide();
		$.pagination(pageinfo);
		if(s==0){//第一次加载的时候
			$("#horizontalTab2").find("tbody>tr:lt("+e+")").show();
		}else{
			$("#horizontalTab2").find("tbody>tr:lt("+e+")").filter(":gt("+s+")").show();
		}
	};
	//上一页操作
	$("#page").on("click",".prev-page",function(){
		var currentpage = parseInt($(".paging-bar li.active").eq(0).text());
		var page = Number(currentpage)-1;
		var size= parseInt($('#pagesize option:selected').text());
		$.select(page,size);
	});
	
	//下一页操作
	$("#page").on("click",".next-page",function(){
		var currentpage = parseInt($(".paging-bar li.active").eq(0).text());
		page = Number(currentpage) + 1;
		var size= parseInt($('#pagesize option:selected').text());
		$.select(page,size);
		
	});
	//具体页数
	$("#page").on("click","li",function(){
		if($(this).hasClass("active"))
			return;
		var size= parseInt($('#pagesize option:selected').text());
		var page = parseInt($(this).html());
		if(page=="...") return;
		$.select(page,size);
	});
	//选择页数
	/**
	每页显示的条目数切换
	**/
	$("select#pagesize").on("change",function(){
		var size= parseInt($('#pagesize option:selected').text());
		$.select(1,size);
	});
//	$("#dialogTitle").html("");//填充
//	$("#dialogTitle").append("待写入");//填充
	//填充搜索开始
	$("#tmpMethod").html("");
	var singleArr = new Array();
	var doubleArr = new Array();
	var timeDoubleArr = new Array();
	var threeArr = new Array();
	var tenArr = new Array();
//	var stenArr = new Array();
//	var sdoubleArr = new Array();
//	var sthreeArr = new Array();
	var regular={};
	var characterLength={};
	var digitalText={};
	var moreQuery="";
	for(var s=0;s<searchArray.length;s++){
		var tmp = JSON.parse(searchArray[s]);
		var data_source_select = tmp.data_source_select.split("|");		
		var query_type=tmp.queryType;
		if(!query_type)query_type=0;		
		if(query_type==0){				
			if(tmp.dataRegular !=null && tmp.dataRegular.length >0){
				regular[data_source_select[0]]={"key":tmp.dataRegular,"value":tmp.regularResult};
			}
			characterLength[data_source_select[0]]={"min":tmp.minVal,"max":tmp.maxVal};
			if(tmp.cOptionType=="number"){
				digitalText[data_source_select[0]]={"numMinVal":tmp.numMinVal,"numMaxVal":tmp.numMaxVal,"numUnit":tmp.numUnit};
			}
			
			var ct = tmp.cOptionType;
			var sct = tmp.data_source_select;//得到统计子类型
			if(!(typeof(sct)=="undefined")){
				var idx = sct.indexOf('|');
				if (idx > 0)
					sct = sct.toString().split('|')[0];
				if(ct=="text"){//单行文本
					if(tmp.textCount==2){
						var strs= new Array();
						strs = tmp.textDefalutVal.split(",");
						var str = "";
						if(strs.length >=2){
							str=strs[1];
						}
						
						doubleArr.push("<div class='item'><div class='attr'>"+tmp.cAttrAlias+"</div><div class='value'><div class='inline-block'>"
								+"<input type='text' value='"+strs[0]+"' placeholder='请输入值' class='g-input d"+sct+" "+sct+"'><span class='icon'></span></div>"
								+"<span class='inline-block'>&nbsp;-&nbsp;</span><div class='inline-block'><input type='text' value='"+str+"' placeholder='请输入值' class='g-input'>"
								+"<span class='icon'></span></div></div></div>");
					}else{
						threeArr.push("<div class='item'><div class='attr'>"+tmp.cAttrAlias+"</div><div class='value'><input type='text' name='' id='' class='g-input "+sct+"' value='"+tmp.textDefalutVal+"' placeholder='请输入值'></div></div>");
					}
				}else if(ct=="multiple-text"){//多行文本
					singleArr.push("<div class='item'><div class='attr'>"+tmp.cAttrAlias+"</div><div class='value'><textarea class='g-textarea "+sct+"'>"+tmp.MulTextDefVal+"</textarea></div></div>");
				}else if(ct=="select"){//选项
					if(tmp.checked_type==="checked_type"){//单选按钮
	//					if(tmp.rstatistics===""){
							tenArr.push(searchArray[s]);
	//					}
	//					else{
	//						stenArr.push(searchArray[s]);
	//					}
					}else if(tmp.checked_type==="checked_type_2"){//复选框
	//					if(tmp.rstatistics===""){
							tenArr.push(searchArray[s]);
	//					}
	//					else{
	//						stenArr.push(searchArray[s]);
	//					}
					}else if(tmp.checked_type==="checked_type_3"){//下拉框
	//					if(tmp.rstatistics===""){
							var selectHtml = "<div class='item'><div class='attr'>"+tmp.cAttrAlias+"</div><div class=' value g-select'><select class='"+sct+"'>";
	//						var statisticsValStr = tmp.statisticsVal.toString().replace("[","").replace("]","");
	//						var statisticsValArr = statisticsValStr.split(",");
							var statisticsValStr = tmp.selectList.replace(/(\s+)/g,";");
							var statisticsValArr = statisticsValStr.split(";");
							for(var ta=0;ta<statisticsValArr.length;ta++){
								var tmpArr = statisticsValArr[ta].toString().replace("'","").replace("'","").split("|");
								var name=tmpArr[1]+"#box";
								if(tmp.format_output == "format_output"){
									name=tmpArr[0]+"#box";
								}
	                            if(tmpArr[0] == null || tmpArr[0].length<=0){
	                                name="";
	                            }
								if(tmpArr[0] == tmp.selectDefVal){
									selectHtml += "<option value='"+name+"' selected>"+tmpArr[1]+"</option>";
								}else{
									selectHtml += "<option value='"+name+"'>"+tmpArr[1]+"</option>";
								}
							}
							selectHtml += "</select><span class='arr-icon'></span></div></div>";
							threeArr.push(selectHtml);
	//					}
	//					else{
	//						var selectStr = "";
	//						if(tmp["sDefault"]==1){
	//							selectStr = "class='on g-radio'";
	//						}else{
	//							selectStr = "class='g-radio'";
	//						}
	//						var selectHtml = "<div class='item'><div class='attr'><div "+selectStr+"><input type='radio' name='type_tongji' id='type_tongji1' value='"+sct+"'></div>"+tmp.cAttrAlias+"</div><div class=' value g-select'><select>";
	//						var statisticsValStr = tmp.statisticsVal.toString().replace("[","").replace("]","");
	//						var statisticsValArr = statisticsValStr.split(",");
	//						for(var ta=0;ta<statisticsValArr.length;ta++){
	//							var tmpArr = statisticsValArr[ta].toString().replace("'","").replace("'","").split("|");
	//							var name=tmpArr[1];
	//							if(tmp.format_output == "format_output"){
	//								name=tmpArr[0];
	//							}
	//							if(tmpArr[0] == tmp.selectDefVal){
	//								selectHtml += "<option value='"+tmpArr[0]+"' selected>"+name+"</option>";
	//							}else{
	//								selectHtml += "<option value='"+tmpArr[0]+"'>"+name+"</option>";
	//							}
	//						}
	//						selectHtml += "</select><span class='arr-icon'></span></div></div>";
	//						sthreeArr.push(selectHtml);
	//					}
					}else if(tmp.checked_type==="checked_type_4"){//多选列表
	//					if(tmp.rstatistics===""){
							var selectHtml = "<div class='item'><div class='attr'>"+tmp.cAttrAlias+"</div><div class='value'><select name='' class='"+sct+"' id='mulSelected_"+s+"' multiple='multiple' style='width:400px;'>";
	//						var statisticsValStr = tmp.statisticsVal.toString().replace("[","").replace("]","");
	//						var statisticsValArr = statisticsValStr.split(",");
							var statisticsValStr = tmp.selectList.replace(/(\s+)/g,";");
							var statisticsValArr = statisticsValStr.split(";");
							for(var ta=0;ta<statisticsValArr.length;ta++){
								var tmpArr = statisticsValArr[ta].toString().replace("'","").replace("'","").split("|");
								var name=tmpArr[1];
								if(tmp.format_output == "format_output"){
									name=tmpArr[0];
								}
	                            if(tmpArr[0] == null || tmpArr[0].length<=0){
	                                name="";
	                            }
								var strs= new Array();
								strs = tmp.selectDefVal.split(",");
								var t=0;
								for(var i=0;i<strs.length;i++){
									if(strs[i] == tmpArr[0]){
										t=1;
									}
								}
								if(t == 1){
									selectHtml += "<option value='"+name+"' selected>"+tmpArr[1]+"</option>";
								}else{
									selectHtml += "<option value='"+name+"'>"+tmpArr[1]+"</option>";
								}
							}
							selectHtml += "</select><span class='arr-icon'></span></div></div>";
							doubleArr.push(selectHtml);
	//					}
	//					else{
	//						var selectStr = "";
	//						if(tmp["sDefault"]==1){
	//							selectStr = "class='on g-radio'";
	//						}else{
	//							selectStr = "class='g-radio'";
	//						}
	//						var selectHtml = "<div class='item'><div class='attr'><div "+selectStr+"><input type='radio' name='type_tongji' id='type_tongji1' value='"+sct+"'></div>"+tmp.cAttrAlias+"</div><div class='value'><select name='' id='mulSelected_"+s+"' multiple='multiple' style='width:400px;'>";
	//						var statisticsValStr = tmp.statisticsVal.toString().replace("[","").replace("]","");
	//						var statisticsValArr = statisticsValStr.split(",");
	//						for(var ta=0;ta<statisticsValArr.length;ta++){
	//							var tmpArr = statisticsValArr[ta].toString().replace("'","").replace("'","").split("|");
	//							var name=tmpArr[1];
	//							if(tmp.format_output == "format_output"){
	//								name=tmpArr[0];
	//							}
	//							var strs= new Array();
	//							strs = tmp.selectDefVal.split(",");
	//							var t=0;
	//							for(var i=0;i<strs.length;i++){
	//								if(strs[i] == tmpArr[0]){
	//									t=1;
	//								}
	//							}
	//							if(t == 1){
	//								selectHtml += "<option value='"+tmpArr[0]+"' selected>"+name+"</option>";
	//							}else{
	//								selectHtml += "<option value='"+tmpArr[0]+"'>"+name+"</option>";
	//							}
	//						}
	//						selectHtml += "</select><span class='arr-icon'></span></div></div>";
	//						sdoubleArr.push(selectHtml);
	//					}
					}
				}else if(ct=="number"){//数字
					if(tmp.numCount==2){
						doubleArr.push("<div class='item'><div class='attr'>"+tmp.cAttrAlias+"</div><div class='value'><div class='inline-block'>"
								+"<input type='text' placeholder='请输入数字' class='g-input d"+sct+" "+sct+"'><span class='icon'></span></div>"
								+"<span class='inline-block'>&nbsp;-&nbsp;</span><div class='inline-block'><input type='text' placeholder='请输入数字' class='g-input' '>"
								+"<span class='icon'></span></div></div></div>");
					}else{
						threeArr.push("<div class='item'><div class='attr'>"+tmp.cAttrAlias+"</div><div class='value'><input type='text' name='' id='' class='g-input "+sct+"' placeholder='请输入数字' '></div></div>");
					}
				}else if(ct=="date-time"){//日期和时间
					if(tmp.dateCount==="1"){
						threeArr.push("<div class='item'><div class='attr'>"+tmp.cAttrAlias+"</div><div class='value'><div class='g-inputdate inline-block'>"
								+"<input id='oneDatepicker' type='text' placeholder='时间' class='g-input datepicker "+sct+"'><span class='icon'></span></div>"
								+"</div></div>");
					}else if(tmp.dateCount==="2"){
						timeDoubleArr.push("<div class='item'><div class='attr'>"+tmp.cAttrAlias+"</div><div class='value'><div class='g-inputdate inline-block'>"
								+"<input id='startDatepicker' type='text' placeholder='起始时间' class='g-input datepicker d"+sct+" "+sct+"'><span class='icon'></span></div>"
								+"<span class='inline-block'>&nbsp;-&nbsp;</span><div class='g-inputdate inline-block'><input id='endDatepicker' type='text' placeholder='结束时间' class='g-input datepicker'>"
								+"<span class='icon'></span></div></div></div>");
					}
	
				}
			}
		}else{//高级查询
			if(tmp.dataRegular !=null && tmp.dataRegular.length >0){
				regular[data_source_select[0]]={"key":tmp.dataRegular,"value":tmp.regularResult};
			}
			characterLength[data_source_select[0]]={"min":tmp.minVal,"max":tmp.maxVal};
			if(tmp.cOptionType=="number"){
				digitalText[data_source_select[0]]={"numMinVal":tmp.numMinVal,"numMaxVal":tmp.numMaxVal,"numUnit":tmp.numUnit};
			}
			
			var ct = tmp.cOptionType;
			var sct = tmp.data_source_select;//得到统计子类型
			if(!(typeof(sct)=="undefined")){
				var idx = sct.indexOf('|');
				if (idx > 0)
					sct = sct.toString().split('|')[0];
				if(ct=="text"){//单行文本
					if(tmp.textCount==2){
						var strs= new Array();
						strs = tmp.textDefalutVal.split(",");
						var str = "";
						if(strs.length >=2){
							str=strs[1];
						}
						
						moreQuery+="<li><div class='left attr'>"+tmp.cAttrAlias+"</div><div class='right value'>"
								+"<div class='inline-block'><input type='text' value='"+strs[0]+"' placeholder='请输入值' class='g-input d"+sct+" "+sct+"' style='width:150px'><span class='icon'></span></div>"
								+"<div class='inline-block'>&nbsp;-&nbsp;</div>"
								+"<div class='inline-block'><input type='text' value='"+str+"' placeholder='请输入值' class='g-input' style='width:150px'><span class='icon'></span></div>"
								+"</div></li>";
					}else{
						moreQuery+="<li><div class='left attr'>"+tmp.cAttrAlias+"</div><div class='right value'><input type='text' name='' id='' class='g-input "+sct+"' value='"+tmp.textDefalutVal+"' placeholder='请输入值'></div></li>";
					}
				}else if(ct=="multiple-text"){//多行文本
					moreQuery+="<li><div class='left attr'>"+tmp.cAttrAlias+"</div><div class='right value'><textarea class='g-textarea "+sct+"'>"+tmp.MulTextDefVal+"</textarea></div></li>";
				}else if(ct=="select"){//选项
					if(tmp.checked_type==="checked_type"){//单选按钮
						//tenArr.push(searchArray[s]);
						var statisticsValStr = tmp.selectList.replace(/(\s+)/g,";");
						var statisticsValArr = statisticsValStr.split(";");
						var showHtml = "<li><div class='left attr'>"+tmp.cAttrAlias+"</div><div class='right value'>";
						var sct = tmp.data_source_select;//得到统计子类型
						var sctCode = "";
						if (sct&&sct.indexOf('|') > 0){
							sctCode = sct.toString().split('|')[0];
						}
						for(var ta=0;ta<statisticsValArr.length;ta++){
							var tmpArr = statisticsValArr[ta].toString().replace(/'/g,"").split("|");
							var isON="";
							if(tmpArr[0] == tmp.selectDefVal){
								isON="on";
							}
							showHtml += "<div class='g-radio "+isON+"'><input type='radio' class='"+sctCode+"' name='data-output' value='"+tmpArr[0]+"' id=''></div><label for='data-output2'>"+tmpArr[1]+"</label>";
						}
						showHtml += "</div></li>";
						moreQuery+=showHtml;
					}else if(tmp.checked_type==="checked_type_2"){//复选框
						//tenArr.push(searchArray[s]);
						var statisticsValStr = tmp.selectList.replace(/(\s+)/g,";");
						var statisticsValArr = statisticsValStr.split(";");
						var showHtml = "<li><div class='left attr'>"+tmp.cAttrAlias+"</div><div class='right value'>";
						var sct = tmp.data_source_select;//得到统计子类型
						var sctCode = "";
						if (sct&&sct.indexOf('|') > 0){
							sctCode = sct.toString().split('|')[0];
						}
						for(var ta=0;ta<statisticsValArr.length;ta++){
							var tmpArr = statisticsValArr[ta].toString().replace(/'/g,"").split("|");
							var isON="";
							if(tmpArr[0] == tmp.selectDefVal){
								isON="on";
							}
							showHtml += "<div class='g-checkbox'><input type='checkbox' class='"+sctCode+"' name='data-output' value='"+tmpArr[0]+"' id=''></div><label for='checkbox-format-1'>"+tmpArr[1]+"</label>";
						}
						showHtml += "</div></li>";
						moreQuery+=showHtml;
					}else if(tmp.checked_type==="checked_type_3"){//下拉框
						var selectHtml = "<li><div class='left attr'>"+tmp.cAttrAlias+"</div><div class='right value g-select'><select class='"+sct+"'>";
						var statisticsValStr = tmp.selectList.replace(/(\s+)/g,";");
						var statisticsValArr = statisticsValStr.split(";");
						for(var ta=0;ta<statisticsValArr.length;ta++){
							var tmpArr = statisticsValArr[ta].toString().replace(/'/g,"").split("|");
							var name=tmpArr[1]+"#box";
							if(tmp.format_output == "format_output"){
								name=tmpArr[0]+"#box";
							}
							if(tmpArr[0] == null || tmpArr[0].length<=0){
								name="";
							}
							if(tmpArr[0] == tmp.selectDefVal){
								selectHtml += "<option value='"+name+"' selected>"+tmpArr[1]+"</option>";
							}else{
								selectHtml += "<option value='"+name+"'>"+tmpArr[1]+"</option>";
							}
						}
						selectHtml += "</select><span class='arr-icon'></span></div></li>";
						moreQuery+=selectHtml;
					}else if(tmp.checked_type==="checked_type_4"){//多选列表
						var selectHtml = "<li><div class='left attr'>"+tmp.cAttrAlias+"</div><div class='right value'><select id='mulSelected_"+s+"' multiple='multiple' style='width:350px;' class='"+sct+"'>";
						var statisticsValStr = tmp.selectList.replace(/(\s+)/g,";");
						var statisticsValArr = statisticsValStr.split(";");
						for(var ta=0;ta<statisticsValArr.length;ta++){
							var tmpArr = statisticsValArr[ta].toString().replace(/'/g,"").split("|");
							var name=tmpArr[1];
							if(tmp.format_output == "format_output"){
								name=tmpArr[0];
							}
							if(tmpArr[0] == null || tmpArr[0].length<=0){
								name="";
							}
							var strs= new Array();
							strs = tmp.selectDefVal.split(",");
							var t=0;
							for(var i=0;i<strs.length;i++){
								if(strs[i] == tmpArr[0]){
									t=1;
								}
							}
							if(t == 1){
								selectHtml += "<option value='"+name+"' selected>"+tmpArr[1]+"</option>";
							}else{
								selectHtml += "<option value='"+name+"'>"+tmpArr[1]+"</option>";
							}
						}
						selectHtml += "</select><span class='arr-icon'></span></div></li>";
						moreQuery+=selectHtml;
					}
				}else if(ct=="number"){//数字
					if(tmp.numCount==2){
						moreQuery+="<li><div class='left attr'>"+tmp.cAttrAlias+"</div><div class='right value'>"
								+"<div class='inline-block'><input type='text' placeholder='请输入数字' class='g-input d"+sct+" "+sct+"' style='width:150px''><span class='icon'></span></div>"
								+"<div class='inline-block'>&nbsp;-&nbsp;</div>"
								+"<div class='inline-block'><input type='text' placeholder='请输入数字' class='g-input' style='width:150px'><span class='icon'></span></div>"
								+"</div></li>";
					}else{
						moreQuery+="<li><div class='left attr'>"+tmp.cAttrAlias+"</div><div class='right value'><input type='text' name='' id='' class='g-input "+sct+"' placeholder='请输入数字' '></div></li>";
					}
				}else if(ct=="date-time"){//日期和时间
					if(tmp.dateCount==="1"){
						moreQuery+="<li><div class='left attr'>"+tmp.cAttrAlias+"</div><div class='right value'><div class='g-inputdate inline-block'>"
								+"<input id='oneDatepicker' type='text' placeholder='时间' class='g-input datepicker "+sct+"'><span class='icon'></span></div>"
								+"</div></li>";
					}else if(tmp.dateCount==="2"){
						moreQuery+="<li><div class='left attr'>"+tmp.cAttrAlias+"</div><div class='right value'>"
								+"<div class='inline-block'><div class='g-inputdate'><input id='startDatepicker' type='text' placeholder='起始时间' class='g-input datepicker d"+sct+" "+sct+"' style='width:150px'><span class='icon'></span></div></div>"
								+"<div class='inline-block'>&nbsp;-&nbsp;</div>"
								+"<div class='inline-block'><div class='g-inputdate'><input id='endDatepicker' type='text' placeholder='结束时间' class='g-input datepicker' style='width:150px'><span class='icon'></span></div></div>"
								+"</div></li>";
					}

				}
			}
		}
	}
	$(".form-dialog-fix-search>.form-dialog").find("ul").html(moreQuery);
	//填充结果开始
	var aTmpArr = new Array();
	var rtreeArr = new Array();//组
	var ceilArr = new Array();//不是组
	var sortTmp = new Array();
	for(var s=0;s<rsearchArray.length;s++){
		var sa = JSON.parse(rsearchArray[s]);
		var sortIndex = pad(sa["rcAttrSort"],5);
		sortTmp.push(sortIndex);
	}
	sortTmp.sort();//数组排序
	for(var i=0;i<sortTmp.length;i++){
		for(var r=0;r<rsearchArray.length;r++){
			var sa = JSON.parse(rsearchArray[r]);
			if(sortTmp[i]===pad(sa["rcAttrSort"],5)){
				aTmpArr[i] = rsearchArray[r];
			}
		}
	}
	for(var r=0;r<aTmpArr.length;r++){
		var retmp = JSON.parse(aTmpArr[r]);
		if(retmp.rGroup==1){
			rtreeArr.push(aTmpArr[r]);
		}else{
			ceilArr.push(aTmpArr[r]);
		}
	}
    var hData2 = new Array;//组的数据
    for(var h1=0;h1<rtreeArr.length;h1++){
        var htmp = JSON.parse(rtreeArr[h1]);
        var v = htmp.rcSubCountType;
        var idx = v.indexOf('|');
        if (idx > 0){
            v = v.toString().split('|')[0];
        }
        if($.trim(v)=="area"){
            var area = htmp.dataType;
            var areaIdx = area.indexOf('|');
            var areaRv = "";
            if (areaIdx > 0){
                areaRv = area.toString().split('|')[0];
            }
            //console.log(areaRv);
            switch (areaRv) {
                case "21":
                    hData2.push("country");
                    break;
                case "22":
                    hData2.push("province");
                    break;
                case "23":
                    hData2.push("city");
                    break;
                case "24":
                    hData2.push("area");
                    break;
                default:
                    v = typeof(mainfieldExtend[v])=="undefined"?v:mainfieldExtend[v].value;
                    hData2.push(v);
                    break;
            }
        }else{
            v = typeof(mainfieldExtend[v])=="undefined"?v:mainfieldExtend[v].value;
            hData2.push(v);
        }
        //hData2.push(v);
    }
    var postArr = allType[dataSourceVal].split("#");//将数据源获取的数据转为数组
    var rpData = {};//得到查询组条件
    rgainData(postArr,rpData,mainfieldExtend);
    var groupArray = new Array;
    var pd2 = {};
    pd2["datasource"] = dataSourceVal;
    pd2["group"] = hData2+"";
    pd2["gcondition"] = rpData;
    if(hsearchArray.data_range)
    	pd2["isContainSubLib"] = hsearchArray.data_range;
    if(hsearchArray.topHits)
    	pd2["topHits"] = parseInt(hsearchArray.topHits);
    var libArr = new Array();
    $.ajax({
        url : '../elasticsearchstatistics/libArr',
        method : 'POST',
        async : false,
        data : {"req":JSON.stringify(pd2)},
        success : function(data) {
            //console.log(data);
            libArr = data.result;
        }
    });
    var devArr = new Array();
    $.ajax({
        url : '../elasticsearchstatistics/devArr',
        method : 'POST',
        async : false,
        data : {"req":JSON.stringify(pd2)},
        success : function(data) {
            devArr = data.result;
        }
    });
	//001自定义002默认值003系统默认值
    var gsHtml = "<li><div class='left attr' id='gcolTmp'>对应分组</div><div class='right value' id='tmpMethod'>";
	for(var rt=0;rt<rtreeArr.length;rt++){
		var group = JSON.parse(rtreeArr[rt]);
		var alias = "";
		var code = "";
		if(group.dataType!=null&&group.dataType.indexOf("|")>-1){
			code = group.dataType.split("|")[0];
			alias = group.dataType.split("|")[1];
		}
		var rcSubCountType = group.rcSubCountType;
		var JsonResult=mainFieldList[rcSubCountType];
		if(group.dtRadio=="001"){
			if(group.dataType!=-1){
				var clazzName = group.rcSubCountType.split("|")[0];
				var clazzFlag = $.trim(group.dataType)!=""&&group.dataType!=null&&group.dataType.indexOf("|")>-1;
				if(clazzFlag){
					var areaVal = group.dataType.split("|")[0];
					switch (areaVal) {
					case "21":
						clazzName = "country";
						break;
					case "22":
						clazzName = "province";
						break;
					case "23":
						clazzName = "city";
						break;
					case "24":
						clazzName = "area";
						break;
					default:
						break;
					}
				}
				gsHtml += "<div class='attr gclass'>"+group.rcSubCountType.split("|")[1]+"</div><div class='value'>";
				gsHtml += "<div class='g-radio on'><input type='radio' class='r"+clazzName+" noReload' name='data-output"+rt+"' value='"+code+'_001'+"' id='zd1_"+rt+"'></div><label for='data-output1'>"+alias+"</label>";
				gsHtml += "</div>";
			}
		}else if(group.dtRadio=="002"){//默认值不能为空，所以不用判断-1
			if(typeof(JsonResult)!="undefined"){
				var val = JsonResult.ref_statistics.split("#");
				if(JsonResult.ref_statistics!="null"){
					gsHtml += "<div class='attr gclass'>"+group.rcSubCountType.split("|")[1]+"</div><div class='value'>";
					for(var i=0;i<mainType.length;i++){
						if($.inArray(mainType[i].maintype_idx+"",val)>-1){
							var clazzName = group.rcSubCountType.split("|")[0];
							var clazzFlag = $.trim(group.dataType)!=""&&group.dataType!=null&&group.dataType.indexOf("|")>-1;
							if(clazzFlag){
								//var areaVal = group.dataType.split("|")[0];
								var areaVal = mainType[i].maintype_idx;
								switch (areaVal) {
								case 21:
									clazzName = "country";
									break;
								case 22:
									clazzName = "province";
									break;
								case 23:
									clazzName = "city";
									break;
								case 24:
									clazzName = "area";
									break;
								default:
									break;
								}
							}
							if(mainType[i].maintype_idx==code){
								gsHtml += "<div class='g-radio on'><input type='radio' class='r"+clazzName+" noReload' name='data-output"+rt+"' value='"+mainType[i].maintype_idx+'_002'+"' id='zd2_"+i+rt+"'></div><label for='data-output2'>"+mainType[i].type_value+"</label>";
							}else{
								gsHtml += "<div class='g-radio'><input type='radio' class='r"+clazzName+" noReload' name='data-output"+rt+"' value='"+mainType[i].maintype_idx+'_002'+"' id='zd2_"+i+rt+"'></div><label for='data-output2'>"+mainType[i].type_value+"</label>";
							}
						}
					}
					gsHtml += "</div>";
				}
			}
		}else if(group.dtRadio=="003"){
			if(typeof(JsonResult)!="undefined"){
				var val = JsonResult.ref_statistics.split("#");
				if(JsonResult.ref_statistics!="null"){
					gsHtml += "<div class='attr gclass'>"+group.rcSubCountType.split("|")[1]+"</div><div class='value'>";
					for(var i=0;i<mainType.length;i++){
						if($.inArray(mainType[i].maintype_idx+"",val)>-1){
							var clazzName = group.rcSubCountType.split("|")[0];
							var clazzFlag = $.trim(group.dataType)!=""&&group.dataType!=null&&group.dataType.indexOf("|")>-1;
							if(clazzFlag){
								//var areaVal = group.dataType.split("|")[0];
								var areaVal = mainType[i].maintype_idx;
								switch (areaVal) {
								case 21:
									clazzName = "country";
									break;
								case 22:
									clazzName = "province";
									break;
								case 23:
									clazzName = "city";
									break;
								case 24:
									clazzName = "area";
									break;
								default:
									break;
								}
							}
							if(mainType[i].maintype_idx==code){
								gsHtml += "<div class='g-radio on'><input type='radio' class='r"+clazzName+" noReload' name='data-output"+rt+"' value='"+mainType[i].maintype_idx+'_003'+"' id='zd3_"+i+rt+"'></div><label for='data-output3'>"+mainType[i].type_value+"</label>";
							}else{
								gsHtml += "<div class='g-radio'><input type='radio' class='r"+clazzName+" noReload' name='data-output"+rt+"' value='"+mainType[i].maintype_idx+'_0031'+"' id='zd3_"+i+rt+"'></div><label for='data-output3'>"+mainType[i].type_value+"</label>";
							}
						}
					}
					gsHtml += "</div>";
				}
			}
		}
	}
	gsHtml += "</div></li>";
	
	//$("#tmpMethod").append(gsHtml);
	$(".form-dialog-fix-search>.form-dialog").find("ul").append(gsHtml);
	
	
	var isLeft=0;
	$("input[id*=zd]").off();//绑定分组对应
	$("input[id*=zd]").on("click",function(){
		//if($(this).is(":checked")||$(this).parent().hasClass("on")){
			//$(this).click();//触发一次数据类型
			
			var sysVal = $(this).val();
			var sysValArr = null;
			if(sysVal!=null){
				sysValArr = sysVal.split("_");
			}
			if(sysValArr!=null&&sysValArr.length>1){
				if(sysValArr[1]=="001"||sysValArr[1]=="002"||sysValArr[1]=="003"){
					var index = sysValArr[0];
					var thisId = $(this).prop("id");
					var ini = 0;
					var writegArr = new Array();//获取分组对应里每个组对应的数组
					for(var h=0;h<staticsType.length;h++){
						if(index==staticsType[h].data_type){
							writegArr[ini] = staticsType[h].data_key+"|"+staticsType[h].data_desc;
							ini = ini+1;
						}
					}
					var of = "";
					var checkAllorReverse = "";
					var zdHtml = "";
					var length=writegArr.length;
					if(length>5){
						of = "style='height:80px; overflow:auto'";
						zdHtml +=  "<div><div class='g-btn' id='chooseAll_"+thisId+"' style='margin:10px 0 5px 0;'>全选</div>&nbsp;<div id='noChooseAll_"+thisId+"' class='g-btn' style='margin:10px 0 5px 0;'>反选</div>";
						isLeft++;
					}else if(length!=0){
						zdHtml +=  "<div>";
					}
					writegArr.sort();//结果排序
					if(length!=0){
						zdHtml += "<div id='result_"+thisId+"' class='senior divBorder' "+of+">"
						for(var ta=0;ta<length;ta++){
							var w = writegArr[ta].split("|");
							zdHtml += "<div class='g-checkbox on'><input type='checkbox' name='' class='noReload' value='"+w[0]+"' id=''></div> <label for='checkbox-format-1'>"+w[1]+"</label>";
						}
						zdHtml += "</div></div></div>";
					}else{
						var pData = {};
						pData["maintype_idx"] = index;
						if(index==16){//按年处理
							var mydate = new Date();
							var year = mydate.getFullYear()-9;
							var sgroupArr = new Array();
							for(var c=0;c<10;c++){
								var y = year+c;
								sgroupArr[c] = y+"|"+y;
							}
							var length=sgroupArr.length;
							if(length>5){
								of = "style='height:80px; overflow:auto'";
								zdHtml +=  "<div><div class='g-btn' id='chooseAll_"+thisId+"' style='margin:10px 0 5px 0;'>全选</div>&nbsp;<div id='noChooseAll_"+thisId+"' class='g-btn' style='margin:10px 0 5px 0;'>反选</div>";
								isLeft++;
							}else if(length!=0){
								zdHtml +=  "<div style='marign-left:-30px;'>";
							}
							if(length!=0){
								zdHtml += "<div id='result_"+thisId+"' class='senior divBorder' "+of+"><div>"
								for(var ta=0;ta<length;ta++){
									var w = sgroupArr[ta].split("|");
									zdHtml += "<div class='g-checkbox on'><input type='checkbox' name='' class='noReload' value='"+w[0]+"' id=''>"
									+"</div> <label for='checkbox-format-1'>"+w[1]+"</label>";
									if(ta == length - 1){
										zdHtml += "<a href='javascript:void(0)' id='addYears'><img src='../static/images/unfold.png' class='increase_time'/></a>";
									}
								}
								zdHtml += "</div></div></div></div>";
							}
						}else if(index==18){//按日处理
//						   var mydate = new Date();
//						   var year = mydate.getFullYear();
//						   var month = mydate.getMonth();
//						   month = parseInt(month, 10);
//						   var d= new Date(year, month, 0);
//						   var date = d.getDate();
							var sgroupArr = new Array();
							for(var c=1;c<=31;c++){
								var index = c-1;
								sgroupArr[index] = pad(c,2)+"|"+c+"日";
							}
							var length=sgroupArr.length;
							if(length>5){
								of = "style='height:80px; overflow:auto'";
								zdHtml +=  "<div><div class='g-btn' id='chooseAll_"+thisId+"' style='margin:10px 0 5px 0;'>全选</div>&nbsp;<div id='noChooseAll_"+thisId+"' class='g-btn' style='margin:10px 0 5px 0;'>反选</div>";
								isLeft++;
							}else if(length!=0){
								zdHtml +=  "<div style='marign-left:-30px;'>";
							}
							if(length!=0){
								zdHtml += "<div id='result_"+thisId+"' class='senior divBorder' "+of+">"
								for(var ta=0;ta<length;ta++){
									var w = sgroupArr[ta].split("|");
									zdHtml += "<div class='g-checkbox on'><input type='checkbox' name='' class='noReload' value='"+w[0]+"' id=''>"
									+"</div> <label for='checkbox-format-1'>"+w[1]+"</label>";
								}
								zdHtml += "</div></div></div>";
							}
						}else{
							$.ajax({
								url: "../statisticsTemplate/selectBySql",
								method: "POST",
								data: {"req":JSON.stringify(pData)},
								async:false,
								timeout:8000,
								error: function (){

								},
								success: function (data, textStatus, jqXHR){
									var tA = data.result;
									if(tA!=null){
										var sgroupArr = new Array();
										for(var c=0;c<tA.length;c++){
											var c1 = tA[c];
	                                        if(pData.maintype_idx == 15){
	                                            for(var r=0;devArr!=null&&r<devArr.length;r++){
	                                               if(c1["key"] == devArr[r]){
	                                                   sgroupArr.push(c1["key"]+"|"+c1["code"]);
	                                               }
	                                            }
	                                        }else if(pData.maintype_idx == 20){
	                                            for(var r=0;libArr!=null&&r<libArr.length;r++){
	                                                if(c1["key"] == libArr[r]){
	                                                    sgroupArr.push(c1["key"]+"|"+c1["code"]);
	                                                }
	                                            }
	                                        }else{
	                                            sgroupArr.push(c1["key"]+"|"+c1["code"]);
	                                        }
										}
										var length=sgroupArr.length;
										if(length>5){
											of = "style='height:80px; overflow:auto'";
											zdHtml +=  "<div><div class='g-btn' id='chooseAll_"+thisId+"' style='margin:10px 0 5px 0;'>全选</div>&nbsp;<div id='noChooseAll_"+thisId+"' class='g-btn' style='margin:10px 0 5px 0;'>反选</div>";
											isLeft++;
										}else if(length!=0){
											zdHtml +=  "<div style='marign-left:-30px;'>";
										}
										if(length!=0){
											zdHtml += "<div id='result_"+thisId+"' class='senior divBorder' "+of+">"
											for(var ta=0;ta<length;ta++){
												var w = sgroupArr[ta].split("|");
												zdHtml += "<div class='g-checkbox on'><input type='checkbox' name='' class='noReload' value='"+w[0]+"' id=''>"
												+"</div> <label for='checkbox-format-1'>"+w[1]+"</label>";
											}
											zdHtml += "</div></div></div>";
										}
									}else{
										zdHtml += "<font color='red'>该选项下没有数据，暂不能显示</font></div></div>";
									}

								}
							});
						}
//					zdHtml += "<class='item10'><font color='red'>该信息是随图书馆变化的，暂不能显示</font></div></div></div>";
					}
					
					var undef=$(this).parent().parent().next().eq(0).find("[id*='result']").html();
					if(typeof(undef)!="undefined"){
						$(this).parent().parent().next().eq(0).remove();
					}
					$(this).parent().parent().after(zdHtml);
					
					$("#addYears").off();
					var removeYearIndex = 1;
					isLeft = 0;
					var year = 0;
					var mydate = new Date();
					$("#addYears").on("click",function(){//处理年份，往前增加十年
						var htm = $(this).parent().children().first().html();
						var tv = $(htm).val();
						if(typeof(tv)=="undefined"){
							year = mydate.getFullYear()-19;
						}else{
							if(year == 0 || typeof(year) == "undefined"){
								year = tv - 10;
							}else{
								year = year - 10;
							}
						}
						var sgroupArr = new Array();
						if(year){
							for(var c=0;c<10;c++){
								var y = year+c;
								sgroupArr[c] = y+"|"+y;
							}
						}
						var zdHtml = "<div>";
						if(sgroupArr.length!=0){
							for(var ta=0;ta<sgroupArr.length;ta++){
								var w = sgroupArr[ta].split("|");
								zdHtml += "<div class='g-checkbox on'><input type='checkbox' name='' class='noReload' value='"+w[0]+"' id=''>"
								+"</div> <label for='checkbox-format-1'>"+w[1]+"</label>";
								if(ta == sgroupArr.length-1){
									zdHtml += "<a href='javascript:void(0)' id='removeYears"+removeYearIndex+"'><img src='../static/images/unfold1.png' class='increase_time'/></a>";
								}
							}
						}
						zdHtml += "</div>";
						
						$(this).parent().parent().children().first().before(zdHtml);
						removeYearIndex = removeYearIndex+1;
						$("a[id^='removeYears']").off();//减去十年
						$("a[id^='removeYears']").on("click",function(){
							$(this).parent().remove();
							htm = $("#addYears").parent().siblings("div").children().first().html();
							year = $(htm).val();
						});
					});
				}else{
					var index = sysValArr[0];
					var thisId = $(this).prop("id");
					var ini = 0;
					var writegArr = new Array();//获取分组对应里每个组对应的数组
					for(var h=0;h<staticsType.length;h++){
						if(index==staticsType[h].data_type){
							writegArr[ini] = staticsType[h].data_key+"|"+staticsType[h].data_desc;
							ini = ini+1;
						}
					}
					var of = "";
					var checkAllorReverse = "";
					var zdHtml = "";
					var length=writegArr.length;
					if(length>5){
						of = "style='height:80px; overflow:scroll'";
						zdHtml +=  "<div><div class='g-btn' id='chooseAll_"+thisId+"' style='margin:10px 0 5px 0;'>全选</div>&nbsp;<div id='noChooseAll_"+thisId+"' class='g-btn' style='margin:10px 0 5px 0;'>反选</div>";
						isLeft++;
					}else if(length!=0){
						zdHtml +=  "<div style='marign-left:-30px;'>";
					}
					writegArr.sort();//结果排序
					if(length!=0){
						zdHtml += "<div id='result_"+thisId+"' class='senior divBorder' "+of+">"
						for(var ta=0;ta<length;ta++){
							var w = writegArr[ta].split("|");
							zdHtml += "<div class='g-checkbox on'><input type='checkbox' name='' class='noReload' value='"+w[0]+"' id=''></div><label for='checkbox-format-1'>"+w[0]+"</label>";
						}
						zdHtml += "</div></div></div>";
					}else{
						var pData = {};
						pData["maintype_idx"] = index;
						if(index==16){//按年处理
							var mydate = new Date();
							var year = mydate.getFullYear()-9;
							var sgroupArr = new Array();
							for(var c=0;c<10;c++){
								var y = year+c;
								sgroupArr[c] = y+"|"+y;
							}
							var length=sgroupArr.length;
							if(length>5){
								of = "style='height:80px; overflow:auto'";
								zdHtml += "<div><div class='g-btn' id='chooseAll_"+thisId+"' style='margin:10px 0 5px 0;'>全选</div>&nbsp;<div id='noChooseAll_"+thisId+"' class='g-btn' style='margin:10px 0 5px 0;'>反选</div>";
								isLeft++;
							}else if(length!=0){
								zdHtml +=  "<div style='marign-left:-30px;'>";
							}
							if(length!=0){
								zdHtml += "<div id='result_"+thisId+"' class='senior divBorder' "+of+"><div>"
								for(var ta=0;ta<length;ta++){
									var w = sgroupArr[ta].split("|");
									zdHtml += "<div class='g-checkbox on'><input type='checkbox' name='' class='noReload' value='"+w[0]+"' id=''>"
									+"</div> <label for='checkbox-format-1'>"+w[1]+"</label>";
									if (ta == length - 1) {
										zdHtml += "<a href='javascript:void(0)' id='addYears'><img src='../static/images/unfold.png' class='increase_time'/></a>";
									}
								}
								zdHtml += "</div></div></div></div>";
							}
						}else if(index==18){//按日处理
							// var mydate = new Date();
							// var year = mydate.getFullYear();
							// var month = mydate.getMonth();
							// month = parseInt(month, 10);
							// var d= new Date(year, month, 0);
							// var date = d.getDate();
							var sgroupArr = new Array();
							for(var c=1;c<=31;c++){
								var index = c-1;
								sgroupArr[index] = pad(c,2)+"|"+c+"日";
							}
							var length=sgroupArr.length;
							if(length>5){
								of = "style='height:80px; overflow:auto'";
								zdHtml +=  "<div><div class='g-btn' id='chooseAll_"+thisId+"' style='margin:10px 0 5px 0;'>全选</div>&nbsp;<div id='noChooseAll_"+thisId+"' class='g-btn' style='margin:10px 0 5px 0;'>反选</div>";
								isLeft++;
							}else if(length!=0){
								zdHtml +=  "<div style='marign-left:-30px;'>";
							}
							if(length!=0){
								zdHtml += "<div id='result_"+thisId+"' class='senior divBorder' "+of+">";
								for(var ta=0;ta<length;ta++){
									var w = sgroupArr[ta].split("|");
									zdHtml += "<div class='g-checkbox on'><input type='checkbox' name='' class='noReload' value='"+w[0]+"' id=''></div> <label for='checkbox-format-1'>"+w[1]+"</label>";
								}
								zdHtml += "</div></div></div>";
							}	
						}else{
							$.ajax({
								url: "../statisticsTemplate/selectBySql",
								method: "POST",
								data: {"req":JSON.stringify(pData)},
								async:false,
								timeout:8000,
								error: function ()
								{

								},
								success: function (data, textStatus, jqXHR)
								{
									var tA = data.result;
									if(tA!=null){
										var sgroupArr = new Array();
										for(var c=0;c<tA.length;c++){
											var c1 = tA[c];
											if(pData.maintype_idx == 15){
												for(var r=0;r<devArr.length;r++){
													if(c1["key"] == devArr[r]){
														sgroupArr.push(c1["key"]+"|"+c1["code"]);
													}
												}
											}else if(pData.maintype_idx == 20){
												for(var r=0;r<libArr.length;r++){
													if(c1["key"] == libArr[r]){
														sgroupArr.push(c1["key"]+"|"+c1["code"]);
													}
												}
											}else{
												sgroupArr.push(c1["key"]+"|"+c1["code"]);
											}
										}
										var length=sgroupArr.length;
										if(length>5){
											of = "style='height:80px; overflow:auto'";
											zdHtml +=  "<div><div class='g-btn' id='chooseAll_"+thisId+"' style='margin:10px 0 5px 0;'>全选</div>&nbsp;<div id='noChooseAll_"+thisId+"' class='g-btn' style='margin:10px 0 5px 0;'>反选</div>";
											isLeft++;
										}else if(length!=0){
											zdHtml +=  "<div style='marign-left:-30px;'>";
										}
										if(length!=0){
											zdHtml += "<div id='result_"+thisId+"' class='senior divBorder' "+of+">"
											for(var ta=0;ta<length;ta++){
												var w = sgroupArr[ta].split("|");
												zdHtml += "<div class='g-checkbox on'><input type='checkbox' name='' class='noReload' value='"+w[0]+"' id=''>"
												+"</div> <label for='checkbox-format-1'>"+w[1]+"</label>";
											}
											zdHtml += "</div></div></div>";
										}
									}else{
										zdHtml += "<font color='red'>该选项下没有数据，暂不能显示</font></div></div>";
									}
								}
							});
						}
//					zdHtml += "<class='item10'><font color='red'>该信息是随图书馆变化的，暂不能显示</font></div></div></div>";
					}
					
					var undef=$(this).parent().parent().next().eq(0).find("[id*='result']").html();
					if(typeof(undef)!="undefined"){
						$(this).parent().parent().next().eq(0).remove();
					}
					$(this).parent().parent().after(zdHtml);
					
					$("#addYears").off();
					var removeYearIndex = 1;
					
					var year = 0;
					var mydate = new Date();
					$("#addYears").on("click",function(){//处理年份，往前增加十年
						var htm = $(this).parent().children().first().html();
						var tv = $(htm).val();
						if(typeof(tv)=="undefined"){
							year = mydate.getFullYear()-19;
						}else{
							if(year==0 || typeof(year)=="undefined"){
								year = tv - 10;
							}else{
								year = year - 10;
							}
						}
						var sgroupArr = new Array();
						if(year){
							for(var c=0;c<10;c++){
								var y = year+c;
								sgroupArr[c] = y+"|"+y;
							}
						}
						var zdHtml = "<div>";
						if(sgroupArr.length!=0){
							for(var ta=0;ta<sgroupArr.length;ta++){
								var w = sgroupArr[ta].split("|");
								zdHtml += "<div class='g-checkbox on'><input type='checkbox' name='' class='noReload' value='"+w[0]+"' id=''>"
								+"</div> <label for='checkbox-format-1'>"+w[1]+"</label>";
								if(ta == sgroupArr.length-1){
									zdHtml += "<a href='javascript:void(0)' id='removeYears"+removeYearIndex+"'><img src='../static/images/unfold1.png' class='increase_time'/></a>";
								}
							}
						}
						zdHtml += "</div>";
						
						var old_html=$(this).parent().parent().html();
						
						zdHtml="<p>"+zdHtml+"</p><p>"+old_html+"</p>";
						$(this).parent().empty().html(zdHtml);
						removeYearIndex = removeYearIndex+1;
						htm = $(this).parent().siblings("div").children().first().html();
						$("a[id^='removeYears']").off();//减去十年
						$("a[id^='removeYears']").on("click",function(){
							$(this).parent().parent().empty().html(old_html);
							htm = $("#addYears").parent().siblings("div").children().first().html();
							year = $(htm).val();
						});
					});
				}
			}
			
			$("div[id^='chooseAll']").off();//全选
			$("div[id^='chooseAll']").on("click",function(){
				$(this).parent().find("input[type='checkbox']").each(function(){
					if(!$(this).is(":checked")){
						$(this).prop("checked",true).parents(".g-checkbox").addClass("on").parents(".item").addClass("active");
					}
				});
			});
			$("div[id^='noChooseAll']").off();//反选
			$("div[id^='noChooseAll']").on("click",function(){
				$(this).parent().find("input[type='checkbox']").each(function(){
					if(!$(this).parent().hasClass("on")){
						$(this).prop("checked",true).parents(".g-checkbox").addClass("on").parents(".item").addClass("active");
					}else{
						$(this).removeAttr("checked").parents(".g-checkbox").removeClass("on").parents(".item").removeClass("active");
					}
				});
			});
			
		//}
	
	});
	
	$("input[id*='zd']").each(function (){
		if($(this).is(":checked")||$(this).parent().hasClass("on")){
			$(this).click();//触发一次数据类型
		}
	});
	
	var hData2 = new Array;//组的数据
	for(var h1=0;h1<rtreeArr.length;h1++){
		var htmp = JSON.parse(rtreeArr[h1]);
		var v = htmp.rcSubCountType;
		var idx = v.indexOf('|');
		if (idx > 0){
			v = v.toString().split('|')[0];
		}
		if($.trim(v)=="area"){
			var area = htmp.dataType;
			var areaIdx = area.indexOf('|');
			var areaRv = "";
			if (areaIdx > 0){
				areaRv = area.toString().split('|')[0];
			}
			//console.log(areaRv);
			switch (areaRv) {
			case "21":
				hData2.push("country");
				break;
			case "22":
				hData2.push("province");
				break;
			case "23":
				hData2.push("city");
				break;
			case "24":
				hData2.push("area");
				break;
			default:
				v = typeof(mainfieldExtend[v])=="undefined"?v:mainfieldExtend[v].value;
				hData2.push(v);
				break;
			}
		}else{
			v = typeof(mainfieldExtend[v])=="undefined"?v:mainfieldExtend[v].value;
			hData2.push(v);
		}
	}
	var postArr2 = allType[dataSourceVal].split("#");//将数据源获取的数据转为数组
	var rpData2 = {};//得到查询组条件
	rgainData(postArr2,rpData2,mainfieldExtend);
//	console.log(rpData2);
	var groupArray = new Array;
	var pd2 = {};
	pd2["datasource"] = dataSourceVal;
	pd2["group"] = hData2+"";
	pd2["gcondition"] = rpData2;
	if(hsearchArray.data_range)
    	pd2["isContainSubLib"] = hsearchArray.data_range;
	if(hsearchArray.topHits)
    	pd2["topHits"] = parseInt(hsearchArray.topHits);
	$.ajax({
		url : '../elasticsearchstatistics/gtree',
		method : 'POST',
		async : false,
		data : {"req":JSON.stringify(pd2)},
		error : function() {
			// alert("error");
		},
		success : function(data) {
			var groupStr = data.result;
			//console.info("groupStr-->"+groupStr);
			if(groupStr!=null){
				groupArray = groupStr.split(";");
			}
		}
	});
	gTreeArr = new Array();
	var dataTypeAndDataDesc={};
	$.ajax({
		url : '../statisticsTemplate/selectStaticsType',
		method : 'POST',
		async : false,
		data : "",
		error : function() {
			// alert("error");
		},
		success : function(data) {
			var json = data.result;
			for(var i=0;json!=null&&i<json.length;i++){
				var sdata = json[i].data_type+"|"+json[i].data_key;
				dataTypeAndDataDesc[sdata]={"data_desc":json[i].data_desc};
			}
		}
	});
//	console.log(dataTypeAndDataDesc);	
	var horizontalHtml = "<tr>";
	for(var ce=0;ce<ceilArr.length;ce++){
		var cetmp = JSON.parse(ceilArr[ce]);
		horizontalHtml += "<th class='number "+cetmp.subFun+"|"+cetmp.subFunVal+"' width='"+percentage(parseInt(ceilArr.length+1),10)+"'><span>"+cetmp.rcAttrAlias+"</span></th>";
	}
	if(ceilArr.length>0)
		horizontalHtml += "<th class='number' width='"+percentage(parseInt(ceilArr.length+1),10)+"'><span>合计</span></th>";
	horizontalHtml +="</tr>"
	$("#horizontalTab2 thead").empty().html(horizontalHtml);
	
	
	var treeStructure = null;
	var len = rtreeArr.length;
	if(len!=0){
		var rtmp = JSON.parse(rtreeArr[0]);
		var rsct = rtmp.rcSubCountType;//得到统计子类型
		var ras = rtmp.rcAttrSort;//得到排序
		if(!(typeof(rsct)=="undefined")){
			var idx = rsct.indexOf('|');
			if (idx > 0)
				rsct = rsct.toString().split('|')[1];
		}
		var dataTypeStr="";
		for(var i=0;i<rsearchArray.length;i++){
			var rs = JSON.parse(rsearchArray[i]);
			if(rs.rGroup ==1){
				//alert(JSON.parse(rsearchArray[i]).rcSubCountType+"|"+JSON.parse(rsearchArray[i]).dataType);
				if(rtmp.rcSubCountType == rs.rcSubCountType){
					dataTypeStr = rs.dataType;
				}
			}
		}
		var dataTypearr = "";
		if(dataTypeStr!=null){
			dataTypearr = dataTypeStr.split("|");
			dataTypeStr = dataTypearr[0];
			if(dataTypeStr==21||dataTypeStr==22||dataTypeStr==23||dataTypeStr==24){
				dataTypeStr = 21;
			}
			if(len==1){
				var garr = new Array;
				if(groupArray[0]!=null){
					garr = groupArray[0].split(",");
				}
				if(dateBoolean(garr[0])){
					if(typeof(parentVal)!="undefined"){
						dataTypeStr=parentVal.split("_")[0];
					}
				}
                if(garr[1] !=null && garr[1].length >0) {
                    treeStructure = '[';
                    for (var i = 1; i < garr.length; i++) {
                        if (garr[i].indexOf("_") != 0) {
                            var libarr = garr[i].split("_");
                            garr[i] = libarr[libarr.length - 1];
                        }
                        var nameData = garr[i];
                        if (typeof(dataTypeAndDataDesc[dataTypeStr + "|" + garr[i]]) == "undefined") {
                            if (typeof(dataIDXAndDataName[dataTypeStr + "|" + garr[i]]) != "undefined") {
                                nameData = dataIDXAndDataName[dataTypeStr + "|" + garr[i]].name;
                                if (dataTypeStr == 21) {
                                    nameData = "";
                                    for (var code = 2; code <= garr[i].length; code += 2) {
                                        nameData += dataIDXAndDataName[dataTypeStr + "|" + garr[i].substring(0, code)].name;
                                    }
                                }
                            }
                        } else {
                            nameData = dataTypeAndDataDesc[dataTypeStr + "|" + garr[i]].data_desc;
                        }
                        treeStructure += '{' +
                            '"id": "' + garr[i] + '",' +
                            '"text": "' + nameData + '"' +
                            '},';
                        gTreeArr.push(garr[i]);
                    }
                    var treeStructureend = treeStructure.substring(treeStructure.length - 1);
                    if (treeStructureend == ",") {
                        treeStructure = treeStructure.substring(0, treeStructure.length - 1);
                    }
                    treeStructure += ']';
                }
			}else if(len>1) {

				var garr = new Array;
                if (groupArray[0] != null) {
                    garr = groupArray[0].split(",");
                }
                if (dateBoolean(garr[0])) {
                    if (typeof(parentVal) != "undefined") {
                        dataTypeStr = parentVal.split("_")[0];
                    }
                }
		//alert(garr+"+++");
                if (garr[1] != null && garr[1].length > 0) {
                    treeStructure = '[';
                    for (var i = 1; i < garr.length; i++) {
                        var childrens = "";
                        if (groupArray[1] && groupArray[1].split(",")[1] != null && groupArray[1].split(",")[1].length > 0) {
                            childrens = "," + treeChild(1, rtreeArr, groupArray, garr[i], rsearchArray, dataTypeAndDataDesc, dataIDXAndDataName);
                        }
                        if (garr[i].indexOf("_") != 0) {
                            var libarr = garr[i].split("_");
                            garr[i] = libarr[libarr.length - 1];
                        }
                        var nameData = garr[i];
                        if (typeof(dataTypeAndDataDesc[dataTypeStr + "|" + garr[i]]) == "undefined") {
                            if (typeof(dataIDXAndDataName[dataTypeStr + "|" + garr[i]]) != "undefined") {
                                nameData = dataIDXAndDataName[dataTypeStr + "|" + garr[i]].name;
                                if (dataTypeStr == 21) {
                                    nameData = "";
                                    for (var code = 2; code <= garr[i].length; code += 2) {
                                        nameData += dataIDXAndDataName[dataTypeStr + "|" + garr[i].substring(0, code)].name;
                                    }
                                }
                            }
                        } else {
                            nameData = dataTypeAndDataDesc[dataTypeStr + "|" + garr[i]].data_desc;
                        }
                        treeStructure += '{' +
                            '"id": "' + garr[i] + '",' +
                            '"text": "' + nameData + '"' + childrens +
                            '},';
                    }
                    var treeStructureend = treeStructure.substring(treeStructure.length - 1);
                    if (treeStructureend == ",") {
                        treeStructure = treeStructure.substring(0, treeStructure.length - 1);
                    }
                    treeStructure += ']';
                }
            }
		}
//	jsonArr.push(JSON.parse(treeStructure));
		//加载jtree树
	}
    //$('#tree').data('jstree', false).empty();//清空树
	/*$('#tree').jstree({
		'core' : {
			'data' : JSON.parse(treeStructure)
		}
	});*/
	initTree(treeStructure, len);
	
	/*var nodestr=null;
	$('#tree').on("changed.jstree", function (e, data) {
		if(typeof(data.node)!="undefined"){
			//获取选中节点数据
			nodestr = data.node.id;
			$("#horizontalTab2").find("tbody tr").removeClass("grayTr");
			$("#horizontalTab2>tbody>tr[name^='["+nodestr+"']").removeClass("whiteTr").addClass("grayTr");
			var firstIndex = $("tr[name^='["+nodestr+"']").index();
//			alert(firstIndex);
			var size= $('#pagesize option:selected').text();
			var page = (firstIndex+1)%size==0?(firstIndex+1)/size:parseInt((firstIndex+1)/size)+1;
			if(page ==0){
				page=1;
			}
			$.select(page,size);
			scrollToLocation();
		}
	});*/
	//填充结果结束
	var showHtml = "";
	$("#stmpMethod").empty();
//	$("#tmpMethod").html(showHtml);
	//填充timeDoubleArr
	var t = 2;
	var sdLen = Math.ceil(timeDoubleArr.length/t);
	for(var mt=1;mt<=sdLen;mt++){
		showHtml += "<div class='col-4 senior'>";
		for(var m=t*(mt-1);m<t*mt;m++){
			if(mt<sdLen){
				showHtml += timeDoubleArr[m];
				if((m+1)%t==0){
					showHtml += "</div>";
				}
			}else{
				if(mt==sdLen&&m<timeDoubleArr.length){
					showHtml += timeDoubleArr[m];
					if((m+1)%t==0){
						showHtml += "</div>";
					}
				}
			}
		}
	}
//	showHtml += "</div>";
//	$("#stmpMethod").append(showHtml);
//	showHtml = "";
	if(timeDoubleArr.length%t==1&&threeArr.length!=0){
		showHtml += threeArr[0];
		threeArr.splice(0, 1);
		showHtml += "</div>";
	}else if(timeDoubleArr.length%t==1&&threeArr.length==0&&doubleArr.length!=0){
		showHtml += doubleArr[0];
		doubleArr.splice(0, 1);
		showHtml += "</div>";
	}else if(timeDoubleArr.length%t==1&&threeArr.length==0&&doubleArr.length==0){
		showHtml += "</div>";
	}
	$("#stmpMethod").append(showHtml);
	
	showHtml = "";
	t = 3;
	sdLen = Math.ceil(threeArr.length/t);
	for(var mt=1;mt<=sdLen;mt++){
		if(mt<sdLen){
			showHtml += "<div class='col-6 senior'>";
		}else{
			if(threeArr.length%t==0||threeArr.length%t==2){
				showHtml += "<div class='col-6 senior'>";
			}else if(threeArr.length%t==1&&doubleArr.length!=0){
				showHtml += "<div class='col-4 senior'>";
			}else{
				showHtml += "<div class='col-6 senior'>";
			}
		}
		for(var m=t*(mt-1);m<t*mt;m++){
			if(mt<sdLen){
				showHtml += threeArr[m];
				if((m+1)%t==0){
					showHtml += "</div>";
				}
			}else{
				if(mt==sdLen&&m<threeArr.length){
					showHtml += threeArr[m];
					if((m+1)%t==0){
						showHtml += "</div>";
					}
				}
			}
		}
	}
	
	if(threeArr.length%t==1&&doubleArr.length!=0){
		showHtml += doubleArr[0];
		doubleArr.splice(0, 1);
		showHtml += "</div>";
	}

	showHtml += "</div>";
	$("#stmpMethod").append(showHtml);

	
	showHtml = "";
	t = 2;
	sdLen = Math.ceil(doubleArr.length/t);
	for(var mt=1;mt<=sdLen;mt++){
		showHtml += "<div class='col-4 senior'>";
		for(var m=t*(mt-1);m<t*mt;m++){
			if(mt<sdLen){
				showHtml += doubleArr[m];
				if((m+1)%t==0){
					showHtml += "</div>";
				}
			}else{
				if(mt==sdLen&&m<doubleArr.length){
					showHtml += doubleArr[m];
					if((m+1)%t==0){
						showHtml += "</div>";
					}
				}
			}
		}
	}
	showHtml += "</div>";
	$("#stmpMethod").append(showHtml);
	
	showHtml = "";
	t = 10;
	var sof = "";
	var scheckAllorReverse = "";
	for(var t1=0;t1<tenArr.length;t1++){
		sof = "";
		scheckAllorReverse = "";
		var cv = 0;
		var tenStr = JSON.parse(tenArr[t1]);
		var sct = tenStr.data_source_select;//得到统计子类型
		if(!(typeof(sct)=="undefined")){
			var idx = sct.indexOf('|');
			if (idx > 0)
				sct = sct.toString().split('|')[0];
		}
		var tenStr = JSON.parse(tenArr[t1]);
//		var statisticsValStr = tenStr.statisticsVal.toString().replace("[","").replace("]","");
//		var statisticsValArr = statisticsValStr.split(",");
		var newS = tenStr.selectList.replace(/(\s+)/g,";");
		var statisticsValArr = newS.split(";");
		cv = cv+1;
		if(statisticsValArr.length>29){
			sof = "style='height:80px; overflow:scroll'";
			if(tenStr.checked_type==="checked_type_2"){
				scheckAllorReverse = "<div class='col-4'><div class='item10'>&nbsp;</div><div class='item10'>&nbsp;</div><div class='item10'>&nbsp;</div><div class='item10'>&nbsp;</div><div class='item10'><div class='g-btn' id='chooseAll_s"+t1+"'>全选</div></div>"
				+"<div class='item10'><div id='noChooseAll_s"+t1+"' class='g-btn'>反选</div></div><div class='item10'>&nbsp;</div><div class='item10'>&nbsp;</div><div class='item10'>&nbsp;</div><div class='item10'>&nbsp;</div></div>";
			}
		}
		showHtml += "<div id='result_s"+t1+"' "+sof+" class='senior'><div class='col-4'>"; 
		showHtml += "<div class='item10'>"+tenStr.cAttrAlias+"</div>";
		for(var ta=0;ta<statisticsValArr.length;ta++){
			var sct = tenStr.data_source_select;//得到统计子类型
			var sctCode = "";
			if(!(typeof(sct)=="undefined")){
				var idx = sct.indexOf('|');
				if (idx > 0)
					sctCode = sct.toString().split('|')[0];
					sct = sct.toString().split('|')[1];
			}
			var tmpArr = statisticsValArr[ta].toString().replace("'","").replace("'","").split("|");
			if(tenStr.checked_type==="checked_type"){//单选
				var name=tmpArr[1];
				if(tenStr.format_output == "format_output"){
					name=tmpArr[0];
				}
                if(tmpArr[0] == null || tmpArr[0].length<=0){
                    name="";
                }
				if(tmpArr[0] == tenStr.selectDefVal){
					showHtml += "<div class='item10'><div class='g-radio on'><input type='radio' class='"+sctCode+"' name='data-output' value='"+name+"' id=''>" +
					"</div> <label for='data-output2'>"+tmpArr[1]+"</label></div>";
				}else{
					showHtml += "<div class='item10'><div class='g-radio'><input type='radio' class='"+sctCode+"' name='data-output' value='"+name+"' id=''>" +
					"</div> <label for='data-output2'>"+tmpArr[1]+"</label></div>";
				}
			}else if(tenStr.checked_type==="checked_type_2"){//复选
				var name=tmpArr[1];
				if(tenStr.format_output == "format_output"){
					name=tmpArr[0];
				}
                if(tmpArr[0] == null || tmpArr[0].length<=0){
                    name="";
                }
				var strs= new Array();
				strs = tenStr.selectDefVal.split(",");
				var t=0;
				for(var i=0;i<strs.length;i++){
					if(strs[i] == tmpArr[0]){
						t=1;
					}
				}
				if(t == 1){
					showHtml += "<div class='item10'><div class='g-checkbox on'><input checked='checked' type='checkbox' class='"+sctCode+"' name='' value='"+name+"' id=''>"
					+"</div> <label for='checkbox-format-1'>"+tmpArr[1]+"</label></div>";
				}else{
					showHtml += "<div class='item10'><div class='g-checkbox'><input type='checkbox' class='"+sctCode+"' name='' value='"+name+"' id=''>"
					+"</div> <label for='checkbox-format-1'>"+tmpArr[1]+"</label></div>";
				}
			}
			cv = cv+1;
			if(cv%10==0){
				showHtml += "</div><div class='col-4'>";
			}
		}
		showHtml += "</div>"+scheckAllorReverse+"</div>";
	}
	$("#stmpMethod").append(showHtml);//组织查询条件
	
	showHtml = "";
	for(var mt=0;mt<singleArr.length;mt++){
		showHtml += "<div class='col-2 senior'>"+singleArr[mt]+"</div>";
	}
	$("#stmpMethod").append(showHtml);
	initDatepicker();//初始化时间控件
	$("select[id^='mulSelected']").select2();//初始化下拉多选列表
	//$("#stmpMethod div[class*='senior']:gt(1)").hide();//查询方式的行数超过两行时，隐藏多余的行
	
	
	//显示图表
	var chartHtml = "";
	$("#gchart").html(chartHtml);
	var gLen = rtreeArr.length;
	if(gLen==1){
		var charStr = JSON.parse(rtreeArr[0]);
		var cStr = charStr.chartVal.toString().replace("[","").replace("]","");
		if(cStr!=""){
			var cArr = cStr.split(",");
			for(var c=0;c<cArr.length;c++){
				var ct = cArr[c].replace(/"/ig,"");
				if(ct=="chart_1"){
					chartHtml += "<a href='javascript:;' class='switch-label-hd' id='0'>("+charStr.rcAttrAlias+")柱状图</a>";
				}else if(ct=="chart_2"){
					chartHtml += "<a href='javascript:;' class='switch-label-hd' id='1'>("+charStr.rcAttrAlias+")圆饼图</a>";
				}else if(ct=="chart_3"){
					chartHtml += "<a href='javascript:;' class='switch-label-hd' id='2'>("+charStr.rcAttrAlias+")曲线图</a>";
				}else if(ct=="chart_4"){
					chartHtml += "<a href='javascript:;' class='switch-label-hd' id='3'>("+charStr.rcAttrAlias+")Top排名</a>";
				}
			}
		}
	}else if(gLen>1){
		var charStr = JSON.parse(rtreeArr[0]);
		var cStr = charStr.chartVal.toString().replace("[","").replace("]","");
		if(cStr!=""){
			var cArr = cStr.split(",");
			for(var c=0;c<cArr.length;c++){
				var ct = cArr[c].replace(/"/ig,"");
				if(ct=="chart_1"){
					chartHtml += "<a href='javascript:;' class='switch-label-hd' id='0'>("+charStr.rcAttrAlias+")柱状图</a>";
				}else if(ct=="chart_2"){
					chartHtml += "<a href='javascript:;' class='switch-label-hd' id='1'>("+charStr.rcAttrAlias+")圆饼图</a>";
				}else if(ct=="chart_3"){
					chartHtml += "<a href='javascript:;' class='switch-label-hd' id='2'>("+charStr.rcAttrAlias+")曲线图</a>";
				}else if(ct=="chart_4"){
					chartHtml += "<a href='javascript:;' class='switch-label-hd' id='3'>("+charStr.rcAttrAlias+")Top排名</a>";
				}
			}
		}
		var charStr1 = JSON.parse(rtreeArr[1]);
		var cStr1 = charStr1.chartVal.toString().replace("[","").replace("]","");
		if(cStr1!=""){
			var cArr = cStr1.split(",");
			for(var c=0;c<cArr.length;c++){
				var ct = cArr[c].replace(/"/ig,"");
				if(ct=="chart_1"){
					chartHtml += "<a href='javascript:;' class='switch-label-hd' id='4'>("+charStr1.rcAttrAlias+")柱状图</a>";
				}else if(ct=="chart_2"){
					chartHtml += "<a href='javascript:;' class='switch-label-hd' id='5'>("+charStr1.rcAttrAlias+")圆饼图</a>";
				}else if(ct=="chart_3"){
					chartHtml += "<a href='javascript:;' class='switch-label-hd' id='6'>("+charStr1.rcAttrAlias+")曲线图</a>";
				}else if(ct=="chart_4"){
					chartHtml += "<a href='javascript:;' class='switch-label-hd' id='7'>("+charStr1.rcAttrAlias+")Top排名</a>";
				}
			}
		}
		/*for(var h=0;h<gLen;h++){
			var charStr = JSON.parse(rtreeArr[h]);
			if(charStr["chartVal"].length>2){
				var cStr = charStr.chartVal.toString().replace("[","").replace("]","");
				if(cStr!=""){
					var cArr = cStr.split(",");
					for(var c=0;c<cArr.length;c++){
						var ct = cArr[c].replace(/"/ig,"");
						if((ct=="chart_1")&&(chartHtml.indexOf("<a href='javascript:;' class='switch-label-hd' id='0'>柱状图</a>")<0)){
							chartHtml += "<a href='javascript:;' class='switch-label-hd' id='0'>柱状图</a>";
						}else if((ct=="chart_2")&&(chartHtml.indexOf("<a href='javascript:;' class='switch-label-hd' id='1'>圆饼图</a>")<0)){
							chartHtml += "<a href='javascript:;' class='switch-label-hd' id='1'>圆饼图</a>";
						}else if((ct=="chart_3")&&(chartHtml.indexOf("<a href='javascript:;' class='switch-label-hd' id='2'>曲线图</a>")<0)){
							chartHtml += "<a href='javascript:;' class='switch-label-hd' id='2'>曲线图</a>";
						}else if((ct=="chart_4")&&(chartHtml.indexOf("<a href='javascript:;' class='switch-label-hd' id='3'>Top排名</a>")<0)){
							chartHtml += "<a href='javascript:;' class='switch-label-hd' id='3'>Top排名</a>";
						}
					}
				}
			}
		}*/
	}
	$("#gchart").append(chartHtml);
	$(".switch-label-panel").each(function(){
		$(this).hide();
	});
	var childrens = $("#gchart").children();
	if(childrens.length>0){
		childrens.eq(0).addClass("active");
		var cid = childrens.eq(0).attr("id");
		$(".switch-label-panel").eq(cid).show();
	}
	
	$(".button-search").click();//触发一次数据类型
});


function treeChild(len,arr,groupArray,ids,rsearchArray,dataTypeAndDataDesc,dataIDXAndDataName){
    var tem=0;
    for(var al=0;al<arr.length;al++){
        var vrs = JSON.parse(arr[al]).rcSubCountType;
        if(vrs.split("|")[0] == "area"){
            tem = 1;
            break;
        }
    }
    var meiosis=1;
    if(tem ==1){
        for(var up=len;up>0;up--){
//            console.log(up);
            var uparr = groupArray[up-1].split(",");
            if(uparr[0] == "device_idx" || uparr[0] == "library_idx" || uparr[0] == "country" || uparr[0] == "province" || uparr[0] == "city" || uparr[0] == "area"){
                break;
            }
            meiosis +=1;
        }
    }
	var data_type="";
	var tArr = JSON.parse(arr[len]);
	var t = tArr.rcSubCountType;//得到统计子类型
	var tc = "";//得到统计子类型的code
	if(!(typeof(t)=="undefined")){
		var idx = t.indexOf('|');
		if (idx > 0)
			tc = t.toString().split('|')[0];
			t = t.toString().split('|')[1];
	}
	var garr = new Array;
	if(groupArray[len]){
		garr = groupArray[len].split(",");
		if(garr[0] != "device_idx" && garr[0] != "library_idx" && garr[0] != "country" && garr[0] != "province" && garr[0] != "city" && garr[0] != "area"){
			tem=0;
		}
	}
	for(var i=0;i<rsearchArray.length;i++){
		var rs = JSON.parse(rsearchArray[i]);
		if(rs.rGroup ==1){
			//alert(JSON.parse(rsearchArray[i]).rcSubCountType+"|"+JSON.parse(rsearchArray[i]).dataType);
			if(tArr.rcSubCountType == rs.rcSubCountType){
				data_type = rs.dataType;
			}
		}
	}
	var data_typearr = data_type.split("|");
	data_type = data_typearr[0];
	if(data_type==21||data_type==22||data_type==23||data_type==24){
		data_type = 21;
	}
	var parentVal = checkDate();
	if(dateBoolean(garr[0])){
		if(typeof(parentVal)!="undefined"){
			data_type=parentVal.split("_")[0];
		}
	}
    var one=0;
    if((len-1) == 0){
        var garrone = new Array;
        garrone = groupArray[0].split(",");
        if(garrone[0] != "device_idx" && garrone[0] != "library_idx" && garrone[0] != "country" && garrone[0] != "province" && garrone[0] != "city" && garrone[0] != "area"){
            meiosis=0;
            one = -1;
            tem=0;
        }
    }
    var idsarr = ids.split(";");
    var upwardId = idsarr[idsarr.length-meiosis];
	if(len+1<arr.length){
		var gstr = '"children": [';
		for(var i=1;i<garr.length;i++){
            var indexs = garr[i];
            var treeName = "";
            if(indexs.indexOf("_") != -1){
                var codearr = garr[i].split("_");
                indexs = codearr[codearr.length-2];
                if(indexs !=idsarr){
                	continue;
                }
                treeName = codearr[codearr.length-1];
            }
            var garrStr = garr[i];
            if(indexs.indexOf(upwardId) == 0 || tem==0){
                if(garr[i].indexOf("_") != 0){
                    var libarr = garrStr.split("_");
                    garrStr = libarr[libarr.length-1];
                }
                var nameData=garr[i];
                if(typeof(dataTypeAndDataDesc[data_type+"|"+garrStr])=="undefined"){
                    if(typeof(dataIDXAndDataName[data_type+"|"+garrStr])!="undefined"){
                        nameData = dataIDXAndDataName[data_type+"|"+garrStr].name;
                        if(data_type == 21){
                            var gas = groupArray[len-meiosis].split(",")[0];
                            if(gas== "country" || gas == "province" || gas == "city" || gas == "area"){
                                if(one == -1){
                                    nameData="";
                                    for(var code=2;code<=garrStr.length;code+=2){
                                        nameData+=dataIDXAndDataName[data_type+"|"+garrStr.substring(0,code)].name;
                                    }
                                }else{
                                    var lt1 = upwardId.length;
                                    var lt2 = garrStr.length;
                                    if((lt2-lt1) > 2){
                                        nameData="";
                                        for(var code=(lt1+2);code<=lt2;code+=2){
                                            nameData+=dataIDXAndDataName[data_type+"|"+garrStr.substring(0,code)].name;
                                        }
                                    }
                                }

                            }else{
                                nameData="";
                                for(var code=2;code<=lt2;code+=2){
                                    nameData+=dataIDXAndDataName[data_type+"|"+garrStr.substring(0,code)].name;
                                }
                            }
                        }else if(data_type == 15){
                       	 	var gas = groupArray[len-meiosis].split(",")[0];
                            if(gas== "library_idx" || gas == "device_idx"){
                            	var libStr=ids;
                             	if(libStr.indexOf(";")!=-1){
                             		libStr=libStr.split(";")[1];
                             	}
     	                       	if(garr[i].indexOf(libStr)==-1){
     	                       		continue;
     	                       	}
                           }
                        }
                    }else{
                    	nameData = treeName ;
                    	
                    }
                    
                }else{
                    nameData=dataTypeAndDataDesc[data_type+"|"+garrStr].data_desc;
                }
                var s = treeChild(len+1,arr,groupArray,ids+";"+garrStr,rsearchArray,dataTypeAndDataDesc,dataIDXAndDataName);
                
                
                gstr += '{'+
                	'"id": "'+ids+";"+garrStr+'",'+
                    '"text": "'+nameData+'",'+s+
                    '},';
                //add by huanghuang  20170919
                if(groupArray[len+1]){
                	var newGarr = groupArray[len+1].split(",");
                	if(newGarr!=null&&newGarr.length<2){
                		gTreeArr.push(ids+";"+garrStr);
                	}
                }
               
            }

		}
		var gstrend = gstr.substring(gstr.length-1);
		if(gstrend==","){
			gstr = gstr.substring(0,gstr.length-1);
		}
		gstr += ']';
		return gstr;
	}
	else if(len+1==arr.length){
		var gstr = '"children": [';
		for(var i=1;i<garr.length;i++){
            var indexs = garr[i];
            var treeName = "";
            if(indexs.indexOf("_") != -1){
                var codearr = garr[i].split("_");
                indexs = codearr[codearr.length-2];
                treeName = codearr[codearr.length-1];
               var preNode = idsarr.join("_");
               var nextPreNode =garr[i].substring(0,garr[i].lastIndexOf("_"));
               if(preNode != nextPreNode){
            	   continue;
               }
               
            }
            var garrStr = garr[i];
            if(indexs.indexOf(upwardId) == 0 || tem==0){
                if(garrStr.indexOf("_") != 0){
                    var libarr = garr[i].split("_");
                    garrStr = libarr[libarr.length-1];
                }
                var nameData=garr[i];
                if(typeof(dataTypeAndDataDesc[data_type+"|"+garrStr])=="undefined"){
                    if(typeof(dataIDXAndDataName[data_type+"|"+garrStr])!="undefined"){
                        nameData = dataIDXAndDataName[data_type+"|"+garrStr].name;
                        if(data_type == 21){
                            var gas = groupArray[len-meiosis].split(",")[0];
                            if(gas== "country" || gas == "province" || gas == "city" || gas == "area"){
                                if(one == -1){
                                    nameData="";
                                    for(var code=2;code<=garrStr.length;code+=2){
                                        nameData+=dataIDXAndDataName[data_type+"|"+garrStr.substring(0,code)].name;
                                    }
                                }else{
                                    var lt1 = upwardId.length;
                                    var lt2 = garrStr.length;
                                    if((lt2-lt1) > 2){
                                        nameData="";
                                        for(var code=(lt1+2);code<=lt2;code+=2){
                                            nameData+=dataIDXAndDataName[data_type+"|"+garrStr.substring(0,code)].name;
                                        }
                                    }
                                }
                            }else{
                                nameData="";
                                for(var code=2;code<=lt2;code+=2){
                                    nameData+=dataIDXAndDataName[data_type+"|"+garrStr.substring(0,code)].name;
                                }
                            }
                        }else if(data_type == 15){
                        	var gas = groupArray[len-meiosis].split(",")[0];
                            if(gas== "library_idx" || gas == "device_idx"){
                            	var libStr=ids;
                             	if(libStr.indexOf(";")!=-1){
                             		libStr=libStr.split(";")[1];
                             	}
     	                       	if(garr[i].indexOf(libStr)==-1){
     	                       		continue;
     	                       	}
                            }
                        }
                    }else{
                    	nameData = treeName ;
                    }
                    
                }else{
                    nameData=dataTypeAndDataDesc[data_type+"|"+garrStr].data_desc;
                }
                var gas = groupArray[len-meiosis].split(",")[0];
                if(gas== "country" || gas == "province" || gas == "city" || gas == "area"){
                	garrStr=garr[i];
                }
                gstr += '{'+
                    '"id": "'+ids+";"+garrStr+'",'+
                    '"text": "'+nameData+'"'+
                    '},';
                gTreeArr.push(ids+";"+garrStr);
               
            }
		}
		var gstrend = gstr.substring(gstr.length-1);
		if(gstrend==","){
			gstr = gstr.substring(0,gstr.length-1);
		}
		gstr += ']';
		return gstr;
	}
}

function initDatepicker(){
	$("#oneDatepicker").datepicker(
		{
		numberOfMonths : 1,//显示几个月  
		showButtonPanel : false,//是否显示按钮面板  
		dateFormat : 'yy-mm-dd',//日期格式  
		clearText : "清除",//清除日期的按钮名称  
		closeText : "关闭",//关闭选择框的按钮名称  
		yearSuffix : '', //年的后缀  
		showMonthAfterYear : true,//是否把月放在年的后面  
		defaultDate : nowtime(),//默认日期  
		//minDate:'2011-03-05',//最小日期  
		//maxDate:'2011-03-20',//最大日期  
		monthNames : [ '一月', '二月', '三月', '四月', '五月', '六月','七月', '八月', '九月', '十月', '十一月', '十二月'],
		dayNames : [ '星期日', '星期一', '星期二', '星期三', '星期四', '星期五','星期六' ],
		dayNamesShort : [ '周日', '周一', '周二', '周三', '周四', '周五','周六' ],
		dayNamesMin : [ '日', '一', '二', '三', '四', '五', '六' ],
        onSelect : function(selectedDate) {//选择日期后执行的操作  
        	  
        }
	});
 
	$("#startDatepicker").datepicker(
		{
		numberOfMonths : 1,//显示几个月  
		showButtonPanel : false,//是否显示按钮面板  
		dateFormat : 'yy-mm-dd',//日期格式  
		clearText : "清除",//清除日期的按钮名称  
		closeText : "关闭",//关闭选择框的按钮名称  
		yearSuffix : '', //年的后缀  
		showMonthAfterYear : true,//是否把月放在年的后面  
		defaultDate : nowtime(),//默认日期 
	    
		//minDate:'2011-03-05',//最小日期  
		//maxDate:'2011-03-20',//最大日期  
		monthNames : [ '一月', '二月', '三月', '四月', '五月', '六月','七月', '八月', '九月', '十月', '十一月', '十二月'],
		dayNames : [ '星期日', '星期一', '星期二', '星期三', '星期四', '星期五','星期六' ],
		dayNamesShort : [ '周日', '周一', '周二', '周三', '周四', '周五','周六' ],
		dayNamesMin : [ '日', '一', '二', '三', '四', '五', '六' ],
        onSelect : function( startDate ) {
	        var $startDate = $( "#startDatepicker" );
	        var $endDate = $('#endDatepicker');
	        var endDate = $endDate.datepicker( 'getDate' );
	        if(endDate < startDate){
	            $endDate.datepicker('setDate', startDate - 3600*1000*24);
	        }
	        $endDate.datepicker( "option", "minDate", startDate );
	    }
	});
	
	$("#endDatepicker").datepicker(
		{
		numberOfMonths : 1,//显示几个月  
		showButtonPanel : false,//是否显示按钮面板  
		dateFormat : 'yy-mm-dd',//日期格式  
		clearText : "清除",//清除日期的按钮名称  
		closeText : "关闭",//关闭选择框的按钮名称  
		yearSuffix : '', //年的后缀  
		showMonthAfterYear : true,//是否把月放在年的后面  
		defaultDate : nowtime(),//默认日期 
		//minDate:'2011-03-05',//最小日期  
		//maxDate:'2011-03-20',//最大日期  
		monthNames : [ '一月', '二月', '三月', '四月', '五月', '六月','七月', '八月', '九月', '十月', '十一月', '十二月'],
		dayNames : [ '星期日', '星期一', '星期二', '星期三', '星期四', '星期五','星期六' ],
		dayNamesShort : [ '周日', '周一', '周二', '周三', '周四', '周五','周六' ],
		dayNamesMin : [ '日', '一', '二', '三', '四', '五', '六' ],
		onSelect : function( endDate ) {
	        var $startDate = $( "#startDatepicker" );
	        var $endDate = $('#endDatepicker');
	        var startDate = $startDate.datepicker( "getDate" );
	        if(endDate < startDate){
	            $startDate.datepicker('setDate', startDate + 3600*1000*24);
	        }
	        $startDate.datepicker( "option", "maxDate", endDate );
	    }
	});
}

function returnMain(){
	$("#mainframe").load("../templatemanagement/main","",function(){
		$(".form-dialog-fix .form-dialog").each(function(){
			$(this).attr("date-right",$(this).css("right"));
		});
	});
}

function pieCount(piev,index){
	piev = piev+"";
	var parr = piev.split(",");
	return parseInt(parr[index]);
}

function percentage(num, total) { 
    return (Math.round(total / num * 1000) / 100.00 + "%");// 小数点后两位百分比
}

function pad(num,count) {//不足的补0
	if ((num + "").length >= count) 
		return num;   
	return pad("0" + num, count);   
}

function nowtime(){//将当前时间转换成yyyy-mm-dd格式
    var mydate = new Date();
    var time = new Array(3);
    var year = mydate.getFullYear();
    time[0] = year;
    var month = mydate.getMonth()+1;
    if(month < 10){
    	month = "0" + month;
    }
    time[1] = month;
    var day = mydate.getDate();
    if(day < 10){
    	day= "0" + day;
    }
    time[2] = day;
    return time.join('-');
}

function startLoading(){
	$(".g-loading").stop();
	$(".g-loading").fadeIn();
}

function endLoading(val){
	if(val=="1"){
		$(".g-loading").hide();
	}else{
		$(".g-loading").fadeOut();
	}
}

function scrollToLocation(){
	if(typeof($(".grayTr").attr("style"))!="undefined"){
		var mainContainer = $("#horizontalTab2 tbody");
		scrollToContainer = mainContainer.find(".grayTr:eq(0)");
		mainContainer.animate({scrollTop:scrollToContainer.offset().top - mainContainer.offset().top + mainContainer.scrollTop()});
	}
}

function initFirstChart(resultArr,rtreeArr,rsearchArray,groupArray,dataTypeAndDataDesc,dataIDXAndDataName){
	var chartArr = new Array();//0柱状图1圆饼图2曲线图3top排名
	$("#gchart").find("a").each(function (){
		chartArr.push($(this).prop("id"));
	});
	var titleArr = new Array();//不是分组的数据
	$("#horizontalTab2").find("thead th").each(function(){
		titleArr.push($(this).text());
	});
	if(titleArr.length>1){
		titleArr.splice(titleArr.length-1, 1);
	}
	var newEndMap = {};//得到数据
	var newEndArr = [];//得到分组值
	for(var j=0;j<resultArr.length;j++){
		var dataArr =resultArr[j];
		var oldVal = "";
		var newVal = "";
		var countVal = 0;
		for(var i=0;i<dataArr.length;i++){
			var da = dataArr[i];
			$.each(da,function(k,v){  
				 k = k + "";
				 v = v + "";
				 var dk = k.replace(/\[/g,"").replace(/\]/g,"").split(",");
					if(dk.length==1){//当组的个数为1时
						newEndMap[dk[0]] = (typeof(newEndMap[dk[0]])=="undefined")?parseInt(v)+"":(newEndMap[dk[0]]+","+parseInt(v));
						if(j==0){
							newEndArr.push(dk[0]);
						}
//						newEndMap[dk[0]] = parseInt(da.value);
					}else if(dk.length==2){//当组的个数为2时
						newEndMap[dk[0]+dk[1]] = (typeof(newEndMap[dk[0]+dk[1]])=="undefined")?parseInt(v)+"":(newEndMap[dk[0]+dk[1]]+","+parseInt(v));
						if(j==0){
							newEndArr.push(dk[0]+dk[1]);
						}
					}else if(dk.length>2){//当组的个数超过两个时
//						console.log(dk[0]+dk[1]+":"+da.value);
						if(i==0){
							oldVal = dk[0]+dk[1];
						}
						newVal = dk[0]+dk[1];
						if(oldVal == newVal){
							countVal += parseInt(v);
							if(i==dataArr.length-1){
								newEndMap[oldVal] = (typeof(newEndMap[oldVal])=="undefined")?countVal+"":(newEndMap[oldVal]+","+countVal);
								if(j==0){
									newEndArr.push(oldVal);
								}
//								countVal = 0;
//								oldVal = newVal;
							}
						}else{
							newEndMap[oldVal] = (typeof(newEndMap[oldVal])=="undefined")?countVal+"":(newEndMap[oldVal]+","+countVal);
							if(j==0){
								newEndArr.push(oldVal);
							}
							countVal = parseInt(v);
							oldVal = newVal;
							if(i==dataArr.length-1){
								newEndMap[oldVal] = (typeof(newEndMap[oldVal])=="undefined")?countVal+"":(newEndMap[oldVal]+","+countVal);
								if(j==0){
									newEndArr.push(oldVal);
								}
//								countVal = 0;
//								oldVal = newVal;//////
							}
						}
					}
			 });
		}
	}
	var endMap = newEndMap;//得到数据
	var endArr = newEndArr;//得到分组值
//	$.each(newEndMap,function(key,values){
//		var keyArr = key.split(" ");
//		var valueArr = values.split(",");
//		if($.inArray(keyArr[0], endArr)==-1){
//			endArr.push(keyArr[0]);
//		}
//		if(typeof(endMap[keyArr[0]])=="undefined"){
//			for(var i=0;valueArr!=null&&i<valueArr.length;i++){
//				if(typeof(endMap[keyArr[0]])!="undefined"){
//					if(i==valueArr.length-1){
//						endMap[keyArr[0]] += valueArr[i];
//					}else{
//						endMap[keyArr[0]] += valueArr[i]+",";
//					}
//				}else{
//					if(i==valueArr.length-1){
//						endMap[keyArr[0]] = valueArr[i];
//					}else{
//						endMap[keyArr[0]] = valueArr[i]+",";
//					}
//				}
//			}
//		}else{
//			var val = endMap[keyArr[0]]+"";
//			if(val.indexOf(",")>-1){
//				val = endMap[keyArr[0]].split(",");
//			}
//			var v = "";
//			var toStr = endMap[keyArr[0]]+"";
//			for(var i=0;valueArr!=null&&i<valueArr.length;i++){
//				if(toStr.indexOf(",")>-1){
//					if(i==0){
//						v = (parseInt(val[i])+parseInt(valueArr[i]))+",";
//					}else if(i==valueArr.length-1){
//						v += (parseInt(val[i])+parseInt(valueArr[i]));
//					}else{
//						v += (parseInt(val[i])+parseInt(valueArr[i]))+",";
//					}
//				}else{
//					v = (parseInt(val)+parseInt(valueArr[i]));
//				}
//			}
//			endMap[keyArr[0]] = v;
//		}
//	});
	//配置图表容器长宽
	var parCount = 0;
	if(endArr!=null){
		parCount = endArr.length;
	}
	if(parCount<10){
		parCount = 11;
	}
	$(".special").css({
		width : parCount*100,
		height : 480
	});
	
	$(".top").css({
		width : 1160,
		height : 480
	});
	// 基于准备好的dom，初始化echarts实例
	var chart_1 = echarts.init(document.getElementById('chart-1'));
	var chart_2 = echarts.init(document.getElementById('chart-2'));
	var chart_3 = echarts.init(document.getElementById('chart-3'));
    var chart_4 = echarts.init(document.getElementById('chart-4'));
    var colors = new Array('#c23531','#2f4554', '#61a0a8', '#d48265', '#91c7ae','#749f83',  '#ca8622', '#bda29a','#6e7074', '#546570', '#c4ccd3');//颜色表
    for(var c=0;c<chartArr.length;c++){//遍历选中的图标
    	if(chartArr[c]=="0"){//0柱状图
    		var $bar = $("#chart-1");
    		var chartVal = "chart-1";
    		drawBar(rsearchArray,groupArray,rtreeArr,titleArr,endMap,endArr,chart_1,$bar,colors,dataTypeAndDataDesc,dataIDXAndDataName,chartVal);
    	}else if(chartArr[c]=="1"){//1圆饼图
    		var $pie = $("#chart-2");
    		var chartVal = "chart-2";
    		drawPie(rsearchArray,groupArray,rtreeArr,titleArr,endMap,endArr,chart_2,$pie,colors,dataTypeAndDataDesc,dataIDXAndDataName,chartVal);
    	}else if(chartArr[c]=="2"){//2曲线图
    		var $line = $("#chart-3");
    		var chartVal = "chart-3";
    		drawLine(rsearchArray,groupArray,rtreeArr,titleArr,endMap,endArr,chart_3,$line,colors,dataTypeAndDataDesc,dataIDXAndDataName,chartVal);
    	}else if(chartArr[c]=="3"){//3top排名
    		drawTop(rsearchArray,groupArray,rtreeArr,titleArr,endMap,endArr,chart_4,colors,dataTypeAndDataDesc,dataIDXAndDataName);
    	}
    }

    /*$(window).resize(function() {
    	$(".chart-md").css({width : $(".content-md").width()-2,height : 500});
		chart_1.resize({
			width : "auto"
		});
		chart_2.resize({
			width : "auto"
		});
		chart_3.resize({
			width : "auto"
		});
		chart_4.resize({
			width : "auto"
		});
	});*/
	//图表切换
	$(".switch-label-md a").on(
			"click",
			function() {
		var _this = $(this);
		if (!_this.hasClass("active")) {
			_this.addClass("active").siblings().removeClass(
					"active");
			var panel = _this.parents(".switch-label-md").find(
					".switch-label-panel");
			panel.hide();
			panel.eq(_this.attr("id")).show();
		}
	});
}

function initChart(resultArr,rtreeArr,rsearchArray,groupArray,dataTypeAndDataDesc,dataIDXAndDataName){
	var chartArr = new Array();//4柱状图5圆饼图6曲线图7top排名
	$("#gchart").find("a").each(function (){
		chartArr.push($(this).prop("id"));
	});
	var titleArr = new Array();//不是分组的数据
	$("#horizontalTab2").find("thead th").each(function(){
		titleArr.push($(this).text());
	});
	if(titleArr.length>1){
		titleArr.splice(titleArr.length-1, 1);
	}
	var newEndMap = {};//得到数据
	var newEndArr = [];//得到分组值
	for(var j=0;j<resultArr.length;j++){
		var dataArr =resultArr[j];
		var oldVal = "";
		var newVal = "";
		var countVal = 0;
		for(var i=0;i<dataArr.length;i++){
			var da = dataArr[i];
			$.each(da,function(k,v){  
				 k = k + "";
				 v = v + "";
				 var dk = k.replace(/\[/g,"").replace(/\]/g,"").split(",");
					if(dk.length==1){//当组的个数为1时
						newEndMap[dk[0]] = (typeof(newEndMap[dk[0]])=="undefined")?parseInt(v)+"":(newEndMap[dk[0]]+","+parseInt(v));
						if(j==0){
							newEndArr.push(dk[0]);
						}
//						newEndMap[dk[0]] = parseInt(da.value);
					}else if(dk.length==2){//当组的个数为2时
						newEndMap[dk[0]+dk[1]] = (typeof(newEndMap[dk[0]+dk[1]])=="undefined")?parseInt(v)+"":(newEndMap[dk[0]+dk[1]]+","+parseInt(v));
						if(j==0){
							newEndArr.push(dk[0]+dk[1]);
						}
					}else if(dk.length>2){//当组的个数超过两个时
//						console.log(dk[0]+dk[1]+":"+da.value);
						if(i==0){
							oldVal = dk[0]+dk[1];
						}
						newVal = dk[0]+dk[1];
						if(oldVal == newVal){
							countVal += parseInt(v);
							if(i==dataArr.length-1){
								newEndMap[oldVal] = (typeof(newEndMap[oldVal])=="undefined")?countVal+"":(newEndMap[oldVal]+","+countVal);
								if(j==0){
									newEndArr.push(oldVal);
								}
//								countVal = 0;
//								oldVal = newVal;
							}
						}else{
							newEndMap[oldVal] = (typeof(newEndMap[oldVal])=="undefined")?countVal+"":(newEndMap[oldVal]+","+countVal);
							if(j==0){
								newEndArr.push(oldVal);
							}
							countVal = parseInt(v);
							oldVal = newVal;
							if(i==dataArr.length-1){
								newEndMap[oldVal] = (typeof(newEndMap[oldVal])=="undefined")?countVal+"":(newEndMap[oldVal]+","+countVal);
								if(j==0){
									newEndArr.push(oldVal);
								}
//								countVal = 0;
//								oldVal = newVal;//////
							}
						}
					}
			 });
		}
	}
	var valArr = new Array();//用来存储第二个分组的各个值
	var dataArr = new Array();//用来存储第二个分组的各个数据
	var endArr = new Array();
	$.each(newEndMap,function(key,values){
		var keyArr = key.split(" ");
		if($.inArray(keyArr[0], endArr)==-1){
			endArr.push(keyArr[0]);
		}
	});
	if(endArr!=null&&endArr.length>0){
		for(var e=0;e<endArr.length;e++){
			var arr = new Array();
			var map = {};
			for(var na=0;newEndArr!=null&&na<newEndArr.length;na++){
				var naArr = newEndArr[na].split(" ");
				if(endArr[e]==naArr[0]){
					map[newEndArr[na]] = newEndMap[newEndArr[na]];
					arr.push(newEndArr[na]);
				}
			}
			valArr.push(map);
			dataArr.push(arr);
		}
	}
	$("#chart-5").parent().children().not(":first").remove();
	$("#chart-6").parent().children().not(":first").remove();
	$("#chart-7").parent().children().not(":first").remove();
	var existed = valArr!=null&&valArr.length>1&&dataArr!=null&&dataArr.length>1&&($("#chart-5").parent().children().length==1);
	if(existed){
		var len = valArr.length;
		for(var v=1;v<len;v++){
			$("#chart-5").parent().last().append("<div id='chart-5"+v+"' class='chart-md'></div>");
			$("#chart-6").parent().last().append("<div id='chart-6"+v+"' class='chart-md'></div>");
			$("#chart-7").parent().last().append("<div id='chart-7"+v+"' class='chart-md'></div>");
		}
	}
	//配置图表容器长宽
	var parCount = 0;
	if(valArr!=null){
		parCount = Object.getOwnPropertyNames(valArr[0]).length;
	}
	if(parCount<10){
		parCount = 11;
	}
	$(".chart-md").css({
		width : parCount*100,
		height : 480
	});
	$(".top").css({
		width : 1160,
		height : 480
	});
	// 基于准备好的dom，初始化echarts实例
	var chart_1 = echarts.init(document.getElementById('chart-5'));
	var chart_2 = echarts.init(document.getElementById('chart-6'));
	var chart_3 = echarts.init(document.getElementById('chart-7'));
	var chart_4 = echarts.init(document.getElementById('chart-8'));
    
    var colors = new Array('#c23531','#2f4554', '#61a0a8', '#d48265', '#91c7ae','#749f83',  '#ca8622', '#bda29a','#6e7074', '#546570', '#c4ccd3');//颜色表
    for(var c=0;c<chartArr.length;c++){//遍历选中的图标
    	if(chartArr[c]=="4"){//4柱状图5圆饼图6曲线图7top排名
    		var len = valArr.length;
    		var $bar = $("#chart-5");
    		for(var b=0;b<len;b++){
    			var endMap = valArr[b];//得到数据
    			var endArr = dataArr[b];//得到分组值
    			var chartVal = "chart-5";
    			if(b!=0){
    				chartVal = "chart-5"+b;
    				var t = "#chart-5"+b;
    				$bar = $(t);
    				chart_1 = echarts.init(document.getElementById(chartVal));
    			}
    			drawBar(rsearchArray,groupArray,rtreeArr,titleArr,endMap,endArr,chart_1,$bar,colors,dataTypeAndDataDesc,dataIDXAndDataName,chartVal);
    		}
    	}else if(chartArr[c]=="5"){
    		var len = valArr.length;
    		var $pie = $("#chart-6");
    		for(var b=0;b<len;b++){
    			var endMap = valArr[b];//得到数据
    			var endArr = dataArr[b];//得到分组值
    			var chartVal = "chart-6";
    			if(b!=0){
    				chartVal = "chart-6"+b;
    				var t = "#chart-6"+b;
    				$pie = $(t);
    				chart_2 = echarts.init(document.getElementById(chartVal));
    			}
    			drawPie(rsearchArray,groupArray,rtreeArr,titleArr,endMap,endArr,chart_2,$pie,colors,dataTypeAndDataDesc,dataIDXAndDataName,chartVal);
    		}
    	}else if(chartArr[c]=="6"){
    		var len = valArr.length;
    		var $line = $("#chart-7");
    		for(var b=0;b<len;b++){
    			var endMap = valArr[b];//得到数据
    			var endArr = dataArr[b];//得到分组值
    			var chartVal = "chart-7";
    			if(b!=0){
    				chartVal = "chart-7"+b;
    				var t = "#chart-7"+b;
    				$line = $(t);
    				chart_3 = echarts.init(document.getElementById(chartVal));
    			}
    			drawLine(rsearchArray,groupArray,rtreeArr,titleArr,endMap,endArr,chart_3,$line,colors,dataTypeAndDataDesc,dataIDXAndDataName,chartVal);
    		}
    	}else if(chartArr[c]=="7"){
    		var endMap = newEndMap;//得到数据
			var endArr = newEndArr;//得到分组值
    		drawTop(rsearchArray,groupArray,rtreeArr,titleArr,endMap,endArr,chart_4,colors,dataTypeAndDataDesc,dataIDXAndDataName);
    	}
    }

   /* $(window).resize(function() {
    	$(".chart-md").css({width : $(".content-md").width()-2,height : 500});
		chart_1.resize({
			width : "auto"
		});
		chart_2.resize({
			width : "auto"
		});
		chart_3.resize({
			width : "auto"
		});
		chart_4.resize({
			width : "auto"
		});
	});*/
	//图表切换
	$(".switch-label-md a").on(
			"click",
			function() {
		var _this = $(this);
		if (!_this.hasClass("active")) {
			_this.addClass("active").siblings().removeClass(
					"active");
			var panel = _this.parents(".switch-label-md").find(
					".switch-label-panel");
			panel.hide();
			panel.eq(_this.attr("id")).show();
		}
	});
}

function drawBar(rsearchArray,groupArray,rtreeArr,titleArr,endMap,endArr,chart_1,$bar,colors,dataTypeAndDataDesc,dataIDXAndDataName,chartVal){//画柱状图
	var cArr = new Array();//横轴
    var tcArr = new Array();//横轴
    var cData = {};//横轴数据
//    console.log(endMap);
    var fFlag = false;
    var sFlag = false;
    if(rtreeArr.length==1){
    	fFlag = true;
    }else if(rtreeArr.length>1){
    	fFlag = true;
    	sFlag = true;
    }
    var firstMap = {};
    var secondMap = {};
    var firstStr = "";
    var secondStr = "";
    if(fFlag){
    	var dataTypeStr="";
    	var rtmp = JSON.parse(rtreeArr[0]);
    	firstStr = rtmp.rcSubCountType;
    	for(var i=0;i<rsearchArray.length;i++){
    		var rs = JSON.parse(rsearchArray[i]);
    		if(rs.rGroup ==1){
    			if(rtmp.rcSubCountType == rs.rcSubCountType){
    				dataTypeStr = rs.dataType;
    			}
    		}
    	}
    	var dataTypearr= dataTypeStr.split("|");
    	var parentVal = checkDate();
    	dataTypeStr = dataTypearr[0];
    	if(dataTypeStr==21||dataTypeStr==22||dataTypeStr==23||dataTypeStr==24){
			dataTypeStr = 21;
		}
    	var garr = new Array;
    	if(groupArray[0]){
    		garr = groupArray[0].split(",");
        	if(dateBoolean(garr[0])){
        		if(typeof(parentVal)!="undefined"){
        			dataTypeStr=parentVal.split("_")[0];
        		}
        	}
    	}
    	for(var i=1;i<garr.length;i++){
    		var nameData=garr[i];
    		var newG = garr[i];
    		var nlen = -1;
    		if(garr[i]!=null){
    			nlen = garr[i].indexOf("_");
    			if(nlen>-1){
    				newG = garr[i].substr(nlen+1);
    			}
    		}
    		if(typeof(dataTypeAndDataDesc[dataTypeStr+"|"+newG])=="undefined"){
				if(typeof(dataIDXAndDataName[dataTypeStr+"|"+newG])!="undefined"){
                    nameData = dataIDXAndDataName[dataTypeStr+"|"+newG].name;
                    if(dataTypeStr == 21){
                        nameData="";
                        for(var code=2;code<=garr[i].length;code+=2){
                            nameData+=dataIDXAndDataName[dataTypeStr+"|"+newG.substring(0,code)].name;
                        }
                    }
				}
			}else{
				nameData=dataTypeAndDataDesc[dataTypeStr+"|"+newG].data_desc;
			}
    		firstMap[newG]=nameData;
    	}
    }
    if(sFlag){
    	var dataTypeStr="";
    	var rtmp = JSON.parse(rtreeArr[1]);
    	secondStr = rtmp.rcSubCountType;
    	for(var i=0;i<rsearchArray.length;i++){
    		var rs = JSON.parse(rsearchArray[i]);
    		if(rs.rGroup ==1){
    			if(rtmp.rcSubCountType == rs.rcSubCountType){
    				dataTypeStr = rs.dataType;
    			}
    		}
    	}
    	var dataTypearr= dataTypeStr.split("|");
    	dataTypeStr = dataTypearr[0];
    	if(dataTypeStr==21||dataTypeStr==22||dataTypeStr==23||dataTypeStr==24){
			dataTypeStr = 21;
		}
    	var garr = new Array;
    	if(groupArray[1]){
    		garr = groupArray[1].split(",");
    	}
    	var parentVal = checkDate();
    	if(dateBoolean(garr[0])){
    		if(typeof(parentVal)!="undefined"){
    			dataTypeStr=parentVal.split("_")[0];
    		}
    	}
    	for(var i=1;i<garr.length;i++){
    		var nameData=garr[i];
    		var newG = garr[i];
    		var nlen = -1;
    		if(garr[i]!=null){
    			nlen = garr[i].indexOf("_");
    			if(nlen>-1){
    				newG = garr[i].substr(nlen+1);
    			}
    		}
    		if(typeof(dataTypeAndDataDesc[dataTypeStr+"|"+newG])=="undefined"){
				if(typeof(dataIDXAndDataName[dataTypeStr+"|"+newG])!="undefined"){
                    nameData = dataIDXAndDataName[dataTypeStr+"|"+newG].name;
                    if(dataTypeStr == 21){
                        nameData="";
                        for(var code=2;code<=garr[i].length;code+=2){
                            nameData+=dataIDXAndDataName[dataTypeStr+"|"+newG.substring(0,code)].name;
                        }
                    }
				}
			}else{
				nameData=dataTypeAndDataDesc[dataTypeStr+"|"+newG].data_desc;
			}
    		secondMap[newG]=nameData;
    	}
    }
    for(var ea in endArr){
    	$.each(endMap,function(key,values){
    		if(endArr[ea]==key){//为了解决map无序的问题，加了个endArr
    			var keyArr = key.toString().replace("[","").replace("]","").split(" ");
    			values = values+"";
    			if(keyArr.length==1){
    				cArr.push(firstMap[keyArr[0]]);
    			}else if(keyArr.length==2){
    				if(firstStr.indexOf("area")>-1&&secondStr.indexOf("area")>-1){
    					cArr.push(secondMap[keyArr[1]]);
    				}else{
    					cArr.push(firstMap[keyArr[0]]+secondMap[keyArr[1]]);
    				}
    			}
    			tcArr.push(key);
    			var v = values.split(",");
    			for(var vIndex=0;vIndex<v.length;vIndex++){
    				var countVal = v[vIndex];
    				cData[vIndex] = (typeof(cData[vIndex])=="undefined")?countVal+"":(cData[vIndex]+","+countVal);
    			}
    		}
    	});
    }
	//柱状图start
    var seriesArr = "";
    for(var ser=0;ser<titleArr.length;ser++){
//    	var r = Math.round((Math.random()*255)).toString(16);
//    	var g = Math.round((Math.random()*255)).toString(16);
//    	var b = Math.round((Math.random()*255)).toString(16);
//    	var color = "#"+r+g+b;//颜色随机生成
    	seriesArr += '{'+
			'"name": "'+titleArr[ser]+'",'+
			'"type": "bar",'+
			'"data": ['+cData[ser]+'],'+
			'"barWidth": 30,'+ //柱图宽度
			'"itemStyle": {'+
			'"normal":{'+
			'"color": "'+colors[(ser%11)]+'"' +
					'}'+
				'}'+
		'}'+",";
    }
    seriesArr = "["+seriesArr.substring(0, seriesArr.length-1)+"]";
//    console.log(cArr);
//    console.log("seriesArr:"+seriesArr);
    //柱状图end
    var option_1 = {
		legend : {
			data : titleArr,
			left : 20,
			itemGap : 30
		},
		tooltip : {
			trigger : 'axis'
		},
	    grid: {
	        left: '0.2%',
	        right: '4%',
	        bottom: '3%',
	        containLabel: true
	    },
		xAxis : {
			axisLabel : {
				interval: 0  
			},
			data : cArr
		},
		yAxis : {
			splitLine : {
				show : false
			}
		},
		series : JSON.parse(seriesArr)
	};
    //判断数据多于10条的时候
    if(option_1.xAxis.data.length > 8){
    	var clen = option_1.xAxis.data[0].length*60;
//    	console.log("clen"+(1000+((option_1.xAxis.data.length-10)*clen)));
    	$bar.width(1000+((option_1.xAxis.data.length-8)*clen)).parent().css("overflow-x","auto");
    	chart_1 = echarts.init(document.getElementById(chartVal));
    }
	chart_1.setOption(option_1);
}

function drawPie(rsearchArray,groupArray,rtreeArr,titleArr,endMap,endArr,chart_2,$pie,colors,dataTypeAndDataDesc,dataIDXAndDataName,chartVal){//饼状图
	var cArr = new Array();//横轴
    var tcArr = new Array();//横轴
    var cData = {};//横轴数据
//    console.log(endMap);
    var fFlag = false;
    var sFlag = false;
    if(rtreeArr.length==1){
    	fFlag = true;
    }else if(rtreeArr.length>1){
    	fFlag = true;
    	sFlag = true;
    }
    var firstMap = {};
    var secondMap = {};
    var firstStr = "";
    var secondStr = "";
    if(fFlag){
    	var dataTypeStr="";
    	var rtmp = JSON.parse(rtreeArr[0]);
    	firstStr = rtmp.rcSubCountType;
    	for(var i=0;i<rsearchArray.length;i++){
    		var rs = JSON.parse(rsearchArray[i]);
    		if(rs.rGroup ==1){
    			if(rtmp.rcSubCountType == rs.rcSubCountType){
    				dataTypeStr = rs.dataType;
    			}
    		}
    	}
    	var dataTypearr= dataTypeStr.split("|");
    	var parentVal = checkDate();
    	dataTypeStr = dataTypearr[0];
    	if(dataTypeStr==21||dataTypeStr==22||dataTypeStr==23||dataTypeStr==24){
			dataTypeStr = 21;
		}
    	var garr = new Array;
    	if(groupArray[0]){
    		garr = groupArray[0].split(",");
    		if(dateBoolean(garr[0])){
        		if(typeof(parentVal)!="undefined"){
        			dataTypeStr=parentVal.split("_")[0];
        		}
        	}
    	}
    	for(var i=1;i<garr.length;i++){
    		var nameData=garr[i];
    		var newG = garr[i];
    		var nlen = -1;
    		if(garr[i]!=null){
    			nlen = garr[i].indexOf("_");
    			if(nlen>-1){
    				newG = garr[i].substr(nlen+1);
    			}
    		}
    		if(typeof(dataTypeAndDataDesc[dataTypeStr+"|"+newG])=="undefined"){
				if(typeof(dataIDXAndDataName[dataTypeStr+"|"+newG])!="undefined"){
                    nameData = dataIDXAndDataName[dataTypeStr+"|"+newG].name;
                    if(dataTypeStr == 21){
                        nameData="";
                        for(var code=2;code<=garr[i].length;code+=2){
                            nameData+=dataIDXAndDataName[dataTypeStr+"|"+newG.substring(0,code)].name;
                        }
                    }
				}
			}else{
				nameData=dataTypeAndDataDesc[dataTypeStr+"|"+newG].data_desc;
			}
    		firstMap[newG]=nameData;
    	}
    }
    if(sFlag){
    	var dataTypeStr="";
    	var rtmp = JSON.parse(rtreeArr[1]);
    	secondStr = rtmp.rcSubCountType;
    	for(var i=0;i<rsearchArray.length;i++){
    		var rs = JSON.parse(rsearchArray[i]);
    		if(rs.rGroup ==1){
    			if(rtmp.rcSubCountType == rs.rcSubCountType){
    				dataTypeStr = rs.dataType;
    			}
    		}
    	}
    	var dataTypearr= dataTypeStr.split("|");
    	dataTypeStr = dataTypearr[0];
    	if(dataTypeStr==21||dataTypeStr==22||dataTypeStr==23||dataTypeStr==24){
			dataTypeStr = 21;
		}
    	var garr = new Array;
    	if(groupArray[1]){
    		garr = groupArray[1].split(",");
    	}
    	var parentVal = checkDate();
    	if(dateBoolean(garr[0])){
    		if(typeof(parentVal)!="undefined"){
    			dataTypeStr=parentVal.split("_")[0];
    		}
    	}
    	for(var i=1;i<garr.length;i++){
    		var nameData=garr[i];
    		var newG = garr[i];
    		var nlen = -1;
    		if(garr[i]!=null){
    			nlen = garr[i].indexOf("_");
    			if(nlen>-1){
    				newG = garr[i].substr(nlen+1);
    			}
    		}
    		if(typeof(dataTypeAndDataDesc[dataTypeStr+"|"+newG])=="undefined"){
				if(typeof(dataIDXAndDataName[dataTypeStr+"|"+newG])!="undefined"){
                    nameData = dataIDXAndDataName[dataTypeStr+"|"+newG].name;
                    if(dataTypeStr == 21){
                        nameData="";
                        for(var code=2;code<=garr[i].length;code+=2){
                            nameData+=dataIDXAndDataName[dataTypeStr+"|"+newG.substring(0,code)].name;
                        }
                    }
				}
			}else{
				nameData=dataTypeAndDataDesc[dataTypeStr+"|"+newG].data_desc;
			}
    		secondMap[newG]=nameData;
    	}
    }
    for(var ea in endArr){
    	$.each(endMap,function(key,values){
    		if(endArr[ea]==key){//为了解决map无序的问题，加了个endArr
    			var keyArr = key.toString().replace("[","").replace("]","").split(" ");
    			values = values+"";
    			if(keyArr.length==1){
    				cArr.push(firstMap[keyArr[0]]);
    			}else if(keyArr.length==2){
    				if(firstStr.indexOf("area")>-1&&secondStr.indexOf("area")>-1){
    					cArr.push(secondMap[keyArr[1]]);
    				}else{
    					cArr.push(firstMap[keyArr[0]]+secondMap[keyArr[1]]);
    				}
    			}
    			tcArr.push(key);
    			var v = values.split(",");
    			for(var vIndex=0;vIndex<v.length;vIndex++){
    				var countVal = v[vIndex];
    				cData[vIndex] = (typeof(cData[vIndex])=="undefined")?countVal+"":(cData[vIndex]+","+countVal);
    			}
    		}
    	});
    }
  //圆饼图start
    var pieColors = new Array();
    var pieValue = "";
    var pieData = new Array();
    var colori = 0;
    for(var pi=0;pi<titleArr.length;pi++){
    	for(var p=0;p<cArr.length;p++){
    		pieData.push(cArr[p]+":"+titleArr[pi]);
//    		var r = Math.round((Math.random()*255)).toString(16);
//    		var g = Math.round((Math.random()*255)).toString(16);
//    		var b = Math.round((Math.random()*255)).toString(16);
//    		var color = "#"+r+g+b;//颜色随机生成
//    		alert(((pi-1==-1?0:pi)*(cArr.length-1)+(pi-1==-1?p:(p+1)))%11);
//    		console.log("index:"+colori);
    		pieColors.push(colors[colori%11]);
    		colori = colori+1;
//    		pieColors.push(colors[((pi-1==-1?0:pi)*(cArr.length-1)+(pi-1==-1?p:(p+1)))%11]);
    		pieValue += '{'+
    		'"value": '+pieCount(endMap[tcArr[p]],pi)+','+
    		'"name": "'+cArr[p]+":"+titleArr[pi]+'"'+
    		'}'+",";
    	}
    }
//    console.log(pieColors);
    pieValue = "["+pieValue.substring(0, pieValue.length-1)+"]";
//    console.log(pieValue);
    var option_2 = {
			tooltip : {
		        trigger: 'item',
		        formatter: "{b} : {c} ({d}%)"
		    },
		    legend: {
		    	orient: 'vertical',
		        left: 'left',
		        data:pieData,
		    },
		    color:pieColors,
		    series : [
		        {
		            type: 'pie',
		            radius : '55%',
		            center: ['50%', '60%'],
		            data: JSON.parse(pieValue), 
		            itemStyle:{ 
		            normal:{ 
		                  label:{ 
		                    color : "#fff"
		                  },
		                  labelLine :{show:false} 
		                } 
		            } 
		        }
		    ]
		};
    //圆饼图end
    //判断数据多于30条的时候
    if(pieData.length > 25){
    	$pie.height(500+((pieData.length-25)*70));
    	chart_2 = echarts.init(document.getElementById(chartVal));
    }
	chart_2.setOption(option_2);
//	$pie.attr("style","height: 500px;width:1000px; overflow-x: auto;");
}


function drawLine(rsearchArray,groupArray,rtreeArr,titleArr,endMap,endArr,chart_3,$line,colors,dataTypeAndDataDesc,dataIDXAndDataName,chartVal){//线性
	var cArr = new Array();//横轴
    var tcArr = new Array();//横轴
    var cData = {};//横轴数据
//    console.log(endMap);
    var fFlag = false;
    var sFlag = false;
    if(rtreeArr.length==1){
    	fFlag = true;
    }else if(rtreeArr.length>1){
    	fFlag = true;
    	sFlag = true;
    }
    var firstMap = {};
    var secondMap = {};
    var firstStr = "";
    var secondStr = "";
    if(fFlag){
    	var dataTypeStr="";
    	var rtmp = JSON.parse(rtreeArr[0]);
    	firstStr = rtmp.rcSubCountType;
    	for(var i=0;i<rsearchArray.length;i++){
    		var rs = JSON.parse(rsearchArray[i]);
    		if(rs.rGroup ==1){
    			if(rtmp.rcSubCountType == rs.rcSubCountType){
    				dataTypeStr = rs.dataType;
    			}
    		}
    	}
    	var dataTypearr= dataTypeStr.split("|");
    	var parentVal = checkDate();
    	dataTypeStr = dataTypearr[0];
    	if(dataTypeStr==21||dataTypeStr==22||dataTypeStr==23||dataTypeStr==24){
			dataTypeStr = 21;
		}
    	var garr = new Array;
    	garr = groupArray[0].split(",");
    	if(dateBoolean(garr[0])){
    		if(typeof(parentVal)!="undefined"){
    			dataTypeStr=parentVal.split("_")[0];
    		}
    	}
    	for(var i=1;i<garr.length;i++){
    		var nameData=garr[i];
    		var newG = garr[i];
    		var nlen = -1;
    		if(garr[i]!=null){
    			nlen = garr[i].indexOf("_");
    			if(nlen>-1){
    				newG = garr[i].substr(nlen+1);
    			}
    		}
    		if(typeof(dataTypeAndDataDesc[dataTypeStr+"|"+newG])=="undefined"){
				if(typeof(dataIDXAndDataName[dataTypeStr+"|"+newG])!="undefined"){
                    nameData = dataIDXAndDataName[dataTypeStr+"|"+newG].name;
                    if(dataTypeStr == 21){
                        nameData="";
                        for(var code=2;code<=garr[i].length;code+=2){
                            nameData+=dataIDXAndDataName[dataTypeStr+"|"+newG.substring(0,code)].name;
                        }
                    }
				}
			}else{
				nameData=dataTypeAndDataDesc[dataTypeStr+"|"+newG].data_desc;
			}
    		firstMap[newG]=nameData;
    	}
    }
    if(sFlag){
    	var dataTypeStr="";
    	var rtmp = JSON.parse(rtreeArr[1]);
    	secondStr = rtmp.rcSubCountType;
    	for(var i=0;i<rsearchArray.length;i++){
    		var rs = JSON.parse(rsearchArray[i]);
    		if(rs.rGroup ==1){
    			if(rtmp.rcSubCountType == rs.rcSubCountType){
    				dataTypeStr = rs.dataType;
    			}
    		}
    	}
    	var dataTypearr= dataTypeStr.split("|");
    	dataTypeStr = dataTypearr[0];
    	if(dataTypeStr==21||dataTypeStr==22||dataTypeStr==23||dataTypeStr==24){
			dataTypeStr = 21;
		}
    	var garr = new Array;
    	if(groupArray[1]){
    		garr = groupArray[1].split(",");
    	}
    	var parentVal = checkDate();
    	if(dateBoolean(garr[0])){
    		if(typeof(parentVal)!="undefined"){
    			dataTypeStr=parentVal.split("_")[0];
    		}
    	}
    	for(var i=1;i<garr.length;i++){
    		var nameData=garr[i];
    		var newG = garr[i];
    		var nlen = -1;
    		if(garr[i]!=null){
    			nlen = garr[i].indexOf("_");
    			if(nlen>-1){
    				newG = garr[i].substr(nlen+1);
    			}
    		}
    		if(typeof(dataTypeAndDataDesc[dataTypeStr+"|"+newG])=="undefined"){
				if(typeof(dataIDXAndDataName[dataTypeStr+"|"+newG])!="undefined"){
                    nameData = dataIDXAndDataName[dataTypeStr+"|"+newG].name;
                    if(dataTypeStr == 21){
                        nameData="";
                        for(var code=2;code<=garr[i].length;code+=2){
                            nameData+=dataIDXAndDataName[dataTypeStr+"|"+newG.substring(0,code)].name;
                        }
                    }
				}
			}else{
				nameData=dataTypeAndDataDesc[dataTypeStr+"|"+newG].data_desc;
			}
    		secondMap[newG]=nameData;
    	}
    }
    for(var ea in endArr){
    	$.each(endMap,function(key,values){
    		if(endArr[ea]==key){//为了解决map无序的问题，加了个endArr
    			var keyArr = key.toString().replace("[","").replace("]","").split(" ");
    			values = values+"";
    			if(keyArr.length==1){
    				cArr.push(firstMap[keyArr[0]]);
    			}else if(keyArr.length==2){
    				if(firstStr.indexOf("area")>-1&&secondStr.indexOf("area")>-1){
    					cArr.push(secondMap[keyArr[1]]);
    				}else{
    					cArr.push(firstMap[keyArr[0]]+secondMap[keyArr[1]]);
    				}
    			}
    			tcArr.push(key);
    			var v = values.split(",");
    			for(var vIndex=0;vIndex<v.length;vIndex++){
    				var countVal = v[vIndex];
    				cData[vIndex] = (typeof(cData[vIndex])=="undefined")?countVal+"":(cData[vIndex]+","+countVal);
    			}
    		}
    	});
    }
    //线性图start
    var lineArr = "";
    for(var lin=0;lin<titleArr.length;lin++){
//    	var r = Math.round((Math.random()*255)).toString(16);
//    	var g = Math.round((Math.random()*255)).toString(16);
//    	var b = Math.round((Math.random()*255)).toString(16);
//    	var color = "#"+r+g+b;//颜色随机生成
    	lineArr += '{'+
			'"name": "'+titleArr[lin]+'",'+
			'"type": "line",'+
			'"data": ['+cData[lin]+'],'+
			'"itemStyle": {'+
			'"normal":{'+
			'"color": "'+colors[(lin%11)]+'"' +
					'}'+
				'}'+
		'}'+",";
    }
    lineArr = "["+lineArr.substring(0, lineArr.length-1)+"]";
    var option_3 = {
			legend : {
				data : titleArr,
				left : 20,
				itemGap : 30
			},
			tooltip : {
				trigger : 'axis'
			},
			grid: {
		        left: '0.2%',
		        right: '4%',
		        bottom: '3%',
		        containLabel: true
		    },
			xAxis : {
				axisLabel : {
					interval: 0  
				},
				data : cArr
			},
			yAxis : {
				splitLine : {
					show : false
				}
			},
			series : JSON.parse(lineArr)
		};
    //判断数据多于10条的时候
    if(option_3.xAxis.data.length > 8){
    	var clen2 = option_3.xAxis.data[0].length*60;
//    	console.log("clen2"+(1000+((option_3.xAxis.data.length-10)*clen2)));
    	$line.width(1000+((option_3.xAxis.data.length-8)*clen2)).parent().css("overflow-x","auto");
    	chart_3 = echarts.init(document.getElementById(chartVal));
    }
    //线性end
	chart_3.setOption(option_3);
}

function drawTop(rsearchArray,groupArray,rtreeArr,titleArr,endMap,endArr,chart_4,colors,dataTypeAndDataDesc,dataIDXAndDataName){//top排行
	var cArr = new Array();//横轴
    var tcArr = new Array();//横轴
    var cData = {};//横轴数据
//    console.log(endMap);
    var fFlag = false;
    var sFlag = false;
    if(rtreeArr.length==1){
    	fFlag = true;
    }else if(rtreeArr.length>1){
    	fFlag = true;
    	sFlag = true;
    }
    var firstMap = {};
    var secondMap = {};
    var firstStr = "";
    var secondStr = "";
    if(fFlag){
    	var dataTypeStr="";
    	var rtmp = JSON.parse(rtreeArr[0]);
    	firstStr = rtmp.rcSubCountType;
    	for(var i=0;i<rsearchArray.length;i++){
    		var rs = JSON.parse(rsearchArray[i]);
    		if(rs.rGroup ==1){
    			if(rtmp.rcSubCountType == rs.rcSubCountType){
    				dataTypeStr = rs.dataType;
    			}
    		}
    	}
    	var dataTypearr= dataTypeStr.split("|");
    	var parentVal = checkDate();
    	dataTypeStr = dataTypearr[0];
    	if(dataTypeStr==21||dataTypeStr==22||dataTypeStr==23||dataTypeStr==24){
			dataTypeStr = 21;
		}
    	var garr = new Array;
    	garr = groupArray[0].split(",");
    	if(dateBoolean(garr[0])){
    		if(typeof(parentVal)!="undefined"){
    			dataTypeStr=parentVal.split("_")[0];
    		}
    	}
    	for(var i=1;i<garr.length;i++){
    		var nameData=garr[i];
    		var newG = garr[i];
    		var nlen = -1;
    		if(garr[i]!=null){
    			nlen = garr[i].indexOf("_");
    			if(nlen>-1){
    				newG = garr[i].substr(nlen+1);
    			}
    		}
    		if(typeof(dataTypeAndDataDesc[dataTypeStr+"|"+newG])=="undefined"){
				if(typeof(dataIDXAndDataName[dataTypeStr+"|"+newG])!="undefined"){
                    nameData = dataIDXAndDataName[dataTypeStr+"|"+newG].name;
                    if(dataTypeStr == 21){
                        nameData="";
                        for(var code=2;code<=garr[i].length;code+=2){
                            nameData+=dataIDXAndDataName[dataTypeStr+"|"+newG.substring(0,code)].name;
                        }
                    }
				}
			}else{
				nameData=dataTypeAndDataDesc[dataTypeStr+"|"+newG].data_desc;
			}
    		firstMap[newG]=nameData;
    	}
    }
    if(sFlag){
    	var dataTypeStr="";
    	var rtmp = JSON.parse(rtreeArr[1]);
    	secondStr = rtmp.rcSubCountType;
    	for(var i=0;i<rsearchArray.length;i++){
    		var rs = JSON.parse(rsearchArray[i]);
    		if(rs.rGroup ==1){
    			if(rtmp.rcSubCountType == rs.rcSubCountType){
    				dataTypeStr = rs.dataType;
    			}
    		}
    	}
    	var dataTypearr= dataTypeStr.split("|");
    	dataTypeStr = dataTypearr[0];
    	if(dataTypeStr==21||dataTypeStr==22||dataTypeStr==23||dataTypeStr==24){
			dataTypeStr = 21;
		}
    	var garr = new Array;
    	if(groupArray[1]){
    		garr = groupArray[1].split(",");
    	}
    	var parentVal = checkDate();
    	if(dateBoolean(garr[0])){
    		if(typeof(parentVal)!="undefined"){
    			dataTypeStr=parentVal.split("_")[0];
    		}
    	}
    	for(var i=1;i<garr.length;i++){
    		var nameData=garr[i];
    		var newG = garr[i];
    		var nlen = -1;
    		if(garr[i]!=null){
    			nlen = garr[i].indexOf("_");
    			if(nlen>-1){
    				newG = garr[i].substr(nlen+1);
    			}
    		}
    		if(typeof(dataTypeAndDataDesc[dataTypeStr+"|"+newG])=="undefined"){
				if(typeof(dataIDXAndDataName[dataTypeStr+"|"+newG])!="undefined"){
                    nameData = dataIDXAndDataName[dataTypeStr+"|"+newG].name;
                    if(dataTypeStr == 21){
                        nameData="";
                        for(var code=2;code<=garr[i].length;code+=2){
                            nameData+=dataIDXAndDataName[dataTypeStr+"|"+newG.substring(0,code)].name;
                        }
                    }
				}
			}else{
				nameData=dataTypeAndDataDesc[dataTypeStr+"|"+newG].data_desc;
			}
    		secondMap[newG]=nameData;
    	}
    }
    var dataType= dataTypeStr.split("|");
	var dataType = dataTypearr[0];
	
    for(var ea in endArr){
    	$.each(endMap,function(key,values){
    		if(endArr[ea]==key){//为了解决map无序的问题，加了个endArr
//    			var keyArr = key.toString().replace("[","").replace("]","").split(" ");
//    			values = values+"";
//    			if(keyArr.length==1){
//    				cArr.push(firstMap[keyArr[0]]);
//    			}else if(keyArr.length==2){
//    				if(firstStr.indexOf("area")>-1&&secondStr.indexOf("area")>-1){
//    					cArr.push(secondMap[keyArr[1]]);
//    				}else{
//    					cArr.push(firstMap[keyArr[0]]+secondMap[keyArr[1]]);
//    				}
//    			}
    			cArr.push(key);
    			tcArr.push(key);
    			var v = values.split(",");
    			for(var vIndex=0;vIndex<v.length;vIndex++){
    				var countVal = v[vIndex];
    				cData[vIndex] = (typeof(cData[vIndex])=="undefined")?countVal+"":(cData[vIndex]+","+countVal);
    			}
    		}
    	});
    }
    var rMap = {};//得到所有的可能数组的值
	for(var t=0;t<titleArr.length;t++){
		for(var t1=0;t1<cArr.length;t1++){
			rMap[cArr[t1]+":"+titleArr[t]] = cData[t].split(",")[t1];
		}
	}
	 var topSort = new Array();
	 $.each(rMap,function(key,values){
		 topSort.push(pad(values,8));
	 });
	 topSort.sort();//数组排序
	 topSort.reverse();//数组反转
	 var top10 = new Array();
	 for(var ts=0;ts<topSort.length;ts++){
		 var tmp = topSort[ts].replace(/\b(0{1,7})/gi,"");
		 top10.push(tmp);
	 }
	 var lastMap = {};
	 var flag = 0;
	 for(var t3=0;t3<top10.length;t3++){
		 $.each(rMap,function(key,values){
//			 console.log($.inArray(values, top10[t3]));
			 if(values===top10[t3]){
				 flag = flag+1;
				 lastMap[""+key+""] = values;
				 rMap[key] = null;//top排名获取后，则置为空
			 }
		 });
	 }
//	 console.log(rMap);
	 var titleArr2 = new Array();
	 var keyArr = new Array();
	 $.each(lastMap,function(key,values){
		 titleArr2.push(values);
		 keyArr.push(key);
	 });
	var topHtml = "";
	var barWidth = 600/keyArr.length;
	for(var i1=0;i1<keyArr.length;i1++){
		topHtml += '{'+
		'"name": "'+keyArr[i1]+'",'+
		'"type": "bar",'+
		'"barWidth": "'+barWidth+'",'+ //柱图宽度
		'"data": ['+titleArr2[i1]+'],'+
		'"itemStyle": {'+
		'"normal":{'+
		'"color": "'+colors[(i1%(topHits+1))]+'"' + 
		'}'+
		'}'+
		'}'+",";
	}
	topHtml = "["+topHtml.substring(0, topHtml.length-1)+"]";
//	console.log(topHtml);
	chart_4.setOption({
        legend: {
        	type: 'scroll',
            //bottom: 10,
            data:keyArr,
            formatter: function (keyArr) {
                return echarts.format.truncateText(keyArr, 1000, '14px Microsoft Yahei', '…');
            },
            tooltip: {
                show: true
            }
        },
        tooltip: {
	       //trigger: 'axis'
        	 //trigger: 'item'
	    },
        xAxis: {
            data: ["top排名前"+topHits+"的数据"]
        },
        yAxis: {
        	splitLine: {
                show: false
            }
        },
        series: JSON.parse(topHtml)
	});
}

function checkDate(){
	return $(".ropertime:checked").val()||$(".rTransDate:checked").val()||$(".rItemLoanDate:checked").val()||$(".rItemRenewDate:checked").val();
}

function dateBoolean(gFirst){
	return gFirst =="opertime"||gFirst =="TransDate"||gFirst =="ItemLoanDate"||gFirst =="ItemRenewDate";
}
