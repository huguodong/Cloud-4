var dataSourceVal = "";//数据源
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
		   if(data.result ==null){
			   return;
		   }
		   var wData = JSON.parse(data.result.statistics_tpl_value);
		  // console.info("wData:",wData);
		   var tsearchArray = jQuery.makeArray(wData.searchinfo);
		   for(var t1=0;t1<tsearchArray.length;t1++){
			   searchArray.push(tsearchArray[t1].toString().replace(/^"|"$/g, ""));
		   }
		  // console.info("searchArray:",searchArray);
		   var trsearchArray = jQuery.makeArray(wData.resultinfo);
		   for(var t2=0;t2<trsearchArray.length;t2++){
			   rsearchArray.push(trsearchArray[t2].toString().replace(/^"|"$/g, ""));
		   }
		  // console.info("rsearchArray:",rsearchArray);
		   
		   hsearchArray = wData.headerData;
		   /*for(var t3=0;t3<thsearchArray.length;t3++){
			   hsearchArray.push(thsearchArray[t3].toString().replace(/^"|"$/g, ""));
		   }*/
		  // console.info("hsearchArray.data_range:",hsearchArray.data_range);
		  // console.info("hsearchArray:",hsearchArray);
		  // var title = wData.headerData.templateName;
		   template_name=wData.headerData.templateName;
		   dataSourceVal = wData.headerData.dataSource;//获取数据源
		   var isOpen = wData.headerData.data_output;//0启用1禁用
		   if(isOpen==0){
			   var isShowStr = $.trim(wData.headerData.checkbox_format.toString());
			   if(isShowStr!=null&&isShowStr.length>0){
				   var isShowArr = isShowStr.split(",");
				   for(var i=0;i<isShowArr.length;i++){
					   if(isShowArr[i]=="Excel"){//显示Excel
						   $("#exportExcel2").show();
					   }else if(isShowArr[i]=="Txt"){//显示Txt
						   $("#exportTxt2").show();
						   
					   }
				   }
			   }
			   
		   }
		   var shoucang="&nbsp;&nbsp;<img id='shoucang' src='../static/images/star-add2.png' style='width:20px;height:20px;cursor:pointer' title='添加到常用菜单'/>"
		   $("#dialogTitle").empty().append(template_name+shoucang);//填充
		}
	});
	
	//alert(operator_idx);
	//添加到常用菜单
	$("#shoucang").on("click", function() {
		var param={
			"setting_url" : "",
			"setting_desc" : template_name,
			"operator_idx": operator_idx,
			"service_idx" : template_idx
		};
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
	$(".panel-title li").on("click",function() {
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
	$(".button-more-search").on("click",function(){
		/*var childrens = $("#tmpMethod").find("div[class*='senior']").length;
		if(childrens>0){//统计方式有子元素时，显示统计方式
			$("#colTmp").toggle();
			$("#gcolTmp").toggle();
		}*/
		//if($(this).hasClass("button-more-search-active")){
//			$("#more-search input").val("");
		//}
		//$(this).toggleClass("button-more-search-active");
//		$("#more-search").toggle();
		
		//$("#stmpMethod div[class*='senior']:gt(1)").toggle();
		
		$(".form-dialog-fix-search").fadeIn(600);
		$(".form-dialog-fix-search").find(".form-dialog").animate({
			"right" : 0
		});
		
		var winH = $(window).height();
		var headerH = $(".form-dialog-fix-search").find(".title").outerHeight();
		/*$(".form-dialog-fix-search").find(".form-wrap").parent().css({
			 "overflow-y":"auto",
			"min-height":winH - headerH
		});*/
		
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
			if (JsonResult) {
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
			if(JsonResult){
				for (var i = 0; i < JsonResult.length; i++) {
					var json=JsonResult[i];
					var sid=json.mainfield_field+"|"+json.mainfield_field_desc;
					if(json.ref_statistics !=null && json.ref_statistics!=""){
						mainFieldList[sid]={"ref_statistics":json.ref_statistics};
					}else{
						mainFieldList[sid]={"ref_statistics":"null"};
					}
				}
				//console.log(mainFieldList);
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
            if (JsonResult) {
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
 	            if (JsonResult) {
 	            	for(var i=0;i<JsonResult.length;i++){
 	            		staticsType[i] = JsonResult[i];
 	            	}
 	            }
 	         }
 	      });
    $("#exportExcel2").on("click",function(){
        var rdata = checkSelect (allType,mainfieldExtend,rtreeArr,ceilArr,functions);
        var totalpage =0;
        if(rdata.result != null && rdata.result!="{}"){
            var total = JSON.parse(rdata.result).total.total;
            var size=1000;
            totalpage =(total%size==0?total/size:parseInt(total/size)+1);
        }
        $(".exc1").html(totalpage);
        delLayer = layer.open({
            type: 1,
            shade: false,
            title: false, //不显示标题
            scrollbar :false,
            closeBtn :0,
            shade:0.5,
            shadeClose :false,
            area:["400px"],
            offset :["195px"],
            content: $('.exc'), //捕获的元素
            cancel: function(index){
                layer.close(curLayer);
                this.content.hide();
            }
        });
    });
    $(".form-btn.cancel").on("click",function(){
        if (delLayer) {
            layer.close(delLayer);
        }
    });
      //导出excel表格
      $(".form-btn.export").on("click",function(){
        var exportpage = $("#exportpage").val();
          var re = /^-?[0-9]+(.[0-9]+)?$/;
          if(!re.test(exportpage)){
              layer.alert("页数只能是数字！");
              return;
          }
        var exportnum = $(".exc1").html();
          if(parseInt(exportpage) > parseInt(exportnum) || exportnum==0 || exportpage<=0){
              layer.alert("页数不正确！");
              return;
          }
          layer.close(delLayer);
          startLoading();
          var postArr = {};
      	if(allType[dataSourceVal]){
      		postArr = allType[dataSourceVal].split("#");//将数据源获取的数据转为数组
      	}
      	//var postArr = allType[dataSourceVal].split("#");//将数据源获取的数据转为数组
    	var pData = {};//得到查询条件
  		gainData(postArr,pData);
  		var rpData = {};//得到查询组条件
		rgainData(postArr,rpData,mainfieldExtend);
		var hData = new Array;//组的数据
		var cData = new Array();//不是组的数据
		var hDataIndex = new Array();//组数据的索引值
		var groupAlias = new Array();
		var cNameData = new Array();
		for(var h1=0;h1<rtreeArr.length;h1++){
			var htmp = JSON.parse(rtreeArr[h1]);
			var v = htmp.rcSubCountType;
			groupAlias.push(htmp.rcAttrAlias);
			var idx = v.indexOf('|');
			if (idx > 0){
				v = v.toString().split('|')[0];
			}
			//hData.push(v);
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
				v = v.toString().split('|')[0];
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
  		pd["fileName"] = "流通查询";
  		pd["dataIDXAndDataName"] = JSON.stringify(dataIDXAndDataName);
  		pd["dataTypeAndDataDesc"] = JSON.stringify(dataTypeAndDataDesc);
  		pd["hDataIndex"] = hDataIndex;
  		pd["functions"] = functions;
  		pd["exportpage"] = exportpage;
  		$.post("../elasticsearchstatistics/checkSelect",{"reqName":JSON.stringify(pd)},function (data){
//  			console.log(data);
  			if(data!=null){
  				if(data.result==null){
  					layer.alert("当前条件下，elachseach没有数据返回，不能打印数据");
  				}else{
			  		$("form").remove("#form2");
			  		var html = "<form action='../elasticsearchstatistics/exportSelect' method='post' id='form2' name='form2' style='display:none'>";  
			  		html += "<input type='hidden' name='reqName' value='"+JSON.stringify(pd)+"'/>";  
			  		html += "</form>";
			  		$("body").append(html);
			  		$("#form2").submit();
  				}
  			}
  			endLoading();
  		});
      });
    $("#exportTxt2").on("click",function(){
        var rdata = checkSelect (allType,mainfieldExtend,rtreeArr,ceilArr,functions);
        var totalpage =0;
        if(rdata.result != null && rdata.result!="{}"){
            var total = JSON.parse(rdata.result).total.total;
            var size=1000;
            totalpage =(total%size==0?total/size:parseInt(total/size)+1);
        }
        $(".exc2").html(totalpage);
        delLayer = layer.open({
            type: 1,
            shade: false,
            title: false, //不显示标题
            scrollbar :false,
            closeBtn :0,
            shade:0.5,
            shadeClose :false,
            area:["400px"],
            offset :["195px"],
            content: $('.txt'), //捕获的元素
            cancel: function(index){
                layer.close(curLayer);
                this.content.hide();
            }
        });
    });
      //导出excel表格
      $(".form-btn.exportTxt").on("click",function(){
          var exportpage = $("#exportpageTxt").val();
          var re = /^-?[0-9]+(.[0-9]+)?$/;
          if(!re.test(exportpage)){
              layer.alert("页数只能是数字！");
              return;
          }
          var exportnum = $(".exc2").html();
          if(parseInt(exportpage) > parseInt(exportnum) || exportnum==0 || exportpage<=0){
              layer.alert("页数不正确！");
              return;
          }
          layer.close(delLayer);
          startLoading();
          var postArr = {};
    	if(allType[dataSourceVal]){
    		postArr = allType[dataSourceVal].split("#");//将数据源获取的数据转为数组
    	}
    	//var postArr = allType[dataSourceVal].split("#");//将数据源获取的数据转为数组
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
    			  v = v.toString().split('|')[0];
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
    	  pd["fileName"] = "流通查询";
    	  pd["dataIDXAndDataName"] = JSON.stringify(dataIDXAndDataName);
    	  pd["dataTypeAndDataDesc"] = JSON.stringify(dataTypeAndDataDesc);
    	  pd["hDataIndex"] = hDataIndex;
    	  pd["functions"] = functions;
          pd["exportpage"] = exportpage;
			$.post("../elasticsearchstatistics/checkSelect",{"reqName":JSON.stringify(pd)},function (data){
//				console.log(data);
	  			if(data!=null){
	  				if(data.result==null){
	  					layer.alert("当前条件下，elachseach没有数据返回，不能打印数据");
	  				}else{
	  					$("form").remove("#form3");
	  					var html = "<form action='../elasticsearchstatistics/exportTxtSelect' method='post' id='form3' name='form3' style='display:none'>";  
	  					html += "<input type='hidden' name='req' value='"+JSON.stringify(pd)+"'/>";  
	  					html += "</form>";
	  					$("body").append(html);
	  					$("#form3").submit();
	  				}
	  			}
	  			endLoading();
	  		});
//    	  $.ajax({
//    			url : '../elasticsearchstatistics/exportTxtSelect',
//    			method : 'POST',
//    			data : {"req":JSON.stringify(pd)},
//    			async:false,
//    			error : function() {
//    				// alert("error");
//    			},
//    			success : function(data) {
//    				if(data!=null&&data.state){
//    					layer.alert("导出txt文件成功");
//    				}
//    			}
//    		});
      });
	//查询
	//$(".button-search").on("click", function() {		
	$(document).on('click', '.button-search, .button-search2', function (){
		startLoading();
		nodestr=null;
		var postArr = {};
      	if(allType[dataSourceVal]){
      		postArr = allType[dataSourceVal].split("#");//将数据源获取的数据转为数组
      	}
      	//var postArr = allType[dataSourceVal].split("#");//将数据源获取的数据转为数组
		var pData = gainData2(postArr);
		var pDataArr = pData.split(";");
		var t=0;
		$(".error-msg").remove();
		for(var p=0;p<pDataArr.length;p++){
			var pa = pDataArr[p].split(",");
			var cal="."+pa[0];
			if(typeof(regular[pa[0]])!="undefined"){
				if(pa[1] !=null && pa[1].length>0){
					var parr = pa[1].split("~");
					for(var ar=0;ar<parr.length;ar++){
						var pr = parr[ar];
						var re = eval(regular[pa[0]].key);
						if(pr.length >0){
							if(!re.test(pr)){
								$(cal).siblings().remove();
								$(cal).after('<span class="error-msg">'+regular[pa[0]].value+'</span>');
								t+=1;
								break;
							}else{
								$(cal).siblings().remove();
							}
						}
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
				var parr = pa[1].split("~");
				for(var ar=0;ar<parr.length;ar++){
				var pr = parr[ar];
				if(pr !=null && pr.length>0){
						var re = /^-?[0-9]+(.[0-9]+)?$/;
						var numarr =  pr.split(".");
						var nule=0;
						if(numarr.length >1){
							nule =numarr[1].length;
						}
						if(!re.test(pr)){
							$(cal).siblings().remove();
							$(cal).after('<span class="error-msg">只能输入数字，请重新输入</span>');
							k+=1;
							break;
						}else if(nule >digitalText[pa[0]].numUnit){
							if(digitalText[pa[0]].numUnit.length >0){
								$(cal).siblings().remove();
								$(cal).after('<span class="error-msg">小数位数超过了设定值，请重新输入</span>');
								k+=1;
								break;
							}
						}else if(digitalText[pa[0]].numMinVal.length >0 || digitalText[pa[0]].numMaxVal.length >0){
							var panumber = parseFloat(pr);
							if(digitalText[pa[0]].numMinVal.length <0){
								if(parseFloat(digitalText[pa[0]].numMaxVal) <panumber){
									$(cal).siblings().remove();
									$(cal).after('<span class="error-msg">输入值大于设定值，请重新输入</span>');
									k+=1;
									break;
								}else{
									$(cal).siblings().remove();
								}
							}else if(digitalText[pa[0]].numMaxVal.length <0){
								if(parseFloat(digitalText[pa[0]].numMinVal) >panumber){
									$(cal).siblings().remove();
									$(cal).after('<span class="error-msg">输入值小于设定值，请重新输入</span>');
									k+=1;
									break;
								}else{
									$(cal).siblings().remove();
								}
							}else{
								if(parseFloat(digitalText[pa[0]].numMaxVal) <panumber){
									$(cal).siblings().remove();
									$(cal).after('<span class="error-msg">输入值大于设定值，请重新输入</span>');
									k+=1;
									break;
								}else if(parseFloat(digitalText[pa[0]].numMinVal) >panumber){
									$(cal).siblings().remove();
									$(cal).after('<span class="error-msg">输入值小于设定值，请重新输入</span>');
									k+=1;
									break;
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
					var parr = pa[1].split("~");
					for(var ar=0;ar<parr.length;ar++){
						var pr = parr[ar];
						if(pr.length>0){
							if(characterLength[pa[0]].max ==null || characterLength[pa[0]].max.length<=0){
								if(pr.length < characterLength[pa[0]].min){
									$(cal).siblings().remove();
									$(cal).after('<span class="error-msg">长度过短，请重新输入</span>');
									m+=1;
									break;
								}else{
									$(cal).siblings().remove();
								}
							}else if(characterLength[pa[0]].min ==null || characterLength[pa[0]].min.length<=0){
								if(pr.length >characterLength[pa[0]].max){
									$(cal).siblings().remove();
									$(cal).after('<span class="error-msg">长度超出范围，请重新输入</span>');
									m+=1;
									break;
								}else{
									$(cal).siblings().remove();
								}
							}else{
								if(pr.length >characterLength[pa[0]].max){
									$(cal).siblings().remove();
									$(cal).after('<span class="error-msg">长度超出范围，请重新输入</span>');
									m+=1;
									break;
								}else if(pr.length < characterLength[pa[0]].min){
									$(cal).siblings().remove();
									$(cal).after('<span class="error-msg">长度过短，请重新输入</span>');
									m+=1;
									break;
								}else{
									$(cal).siblings().remove();
								}
							}
						}
					}
					
				}
			}
		}
		if(m >0){
			return;
		}
		var rpData = {};//得到查询组条件
		rgainData(postArr,rpData,mainfieldExtend);
		var hData = new Array;//组的数据
		//alert("694:"+rtreeArr.length);
		for(var h1=0;h1<rtreeArr.length;h1++){
			var htmp = JSON.parse(rtreeArr[h1]);
			var v = htmp.rcSubCountType;
			var idx = v.indexOf('|');
			if (idx > 0){
				v = v.toString().split('|')[0];
			}
			//hData.push(v);
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
		var pd2 = {};
		pd2["datasource"] = dataSourceVal;
		pd2["group"] = hData+"";
		pd2["gcondition"] = rpData;
		pd2["functions"] = functions;
		if(hsearchArray.data_range)
	    	pd2["isContainSubLib"] = hsearchArray.data_range;
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
				if(groupStr !=null && groupStr.length>0){
					groupArray = groupStr.split(";");
				}
				//alert("741:"+groupArray);
				//console.info(groupArray);
			}
		});


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
//			console.log(dataTypeAndDataDesc);
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
			var parentVal = checkDate();
			if(len==1){
				var garr = new Array;
				if(groupArray[0].length > 0){
                    garr = groupArray[0].split(",");
                }
				if(dateBoolean(garr[0])){
					if(typeof(parentVal)!="undefined"){
						dataTypeStr=parentVal.split("_")[0];
					}
				}
				if(garr[1] !=null && garr[1].length >0){
					treeStructure = '[';
					for(var i=1;i<garr.length;i++){
                        if(garr[i].indexOf("_") != 0){
                            var libarr = garr[i].split("_");
                            garr[i] = libarr[libarr.length-1];
                        }
						var nameData=garr[i];
							if(typeof(dataTypeAndDataDesc[dataTypeStr+"|"+garr[i]])=="undefined"){
								if(typeof(dataIDXAndDataName[dataTypeStr+"|"+garr[i]])!="undefined"){
                                    nameData = dataIDXAndDataName[dataTypeStr+"|"+garr[i]].name;
                                    if(dataTypeStr == 21){
                                        nameData="";
                                        for(var code=2;code<=garr[i].length;code+=2){
                                            nameData+=dataIDXAndDataName[dataTypeStr+"|"+garr[i].substring(0,code)].name;
                                        }
                                    }
								}
							}else{
								nameData=dataTypeAndDataDesc[dataTypeStr+"|"+garr[i]].data_desc;
							}
						treeStructure += '{'+
						'"id": "'+garr[i]+'",'+
						'"text": "'+nameData+'"'+
				     '},';
					}
					treeStructure = treeStructure.substring(0,treeStructure.length-1);
					treeStructure += ']';
				}
			}else if(len>1){
				var garr = new Array;
				if(groupArray[0] !=null){
					garr = groupArray[0].split(",");
				}
				if(dateBoolean(garr[0])){
					if(typeof(parentVal)!="undefined"){
						dataTypeStr=parentVal.split("_")[0];
					}
				}
				if(garr[1] !=null && garr[1].length >0){
					treeStructure = '[';
					for(var i=1;i<garr.length;i++){
						var childrens = "";
						if(groupArray[1]&&groupArray[1].split(",")[1] !=null && groupArray[1].split(",")[1].length >0){
							childrens = ","+treeChild(1,rtreeArr,groupArray,garr[i],rsearchArray,dataTypeAndDataDesc,dataIDXAndDataName);
						}
                        if(garr[i].indexOf("_") != 0){
                            var libarr = garr[i].split("_");
                            garr[i] = libarr[libarr.length-1];
                        }
						var nameData=garr[i];
							if(typeof(dataTypeAndDataDesc[dataTypeStr+"|"+garr[i]])=="undefined"){
								if(typeof(dataIDXAndDataName[dataTypeStr+"|"+garr[i]])!="undefined"){
                                    nameData = dataIDXAndDataName[dataTypeStr+"|"+garr[i]].name;
                                    if(dataTypeStr == 21){
                                        nameData="";
                                        for(var code=2;code<=garr[i].length;code+=2){
                                            nameData+=dataIDXAndDataName[dataTypeStr+"|"+garr[i].substring(0,code)].name;
                                        }
                                    }
								}
							}else{
								nameData=dataTypeAndDataDesc[dataTypeStr+"|"+garr[i]].data_desc;
							}
						treeStructure += '{'+
						'"id": "'+garr[i]+'",'+
						'"text": "'+nameData+'"'+childrens+
				     '},';
					}
					var treeStructureend = treeStructure.substring(treeStructure.length-1);
					if(treeStructureend ==","){
						treeStructure = treeStructure.substring(0,treeStructure.length-1);
					}
					treeStructure += ']';
				}
			}
			//$('#tree').jstree(true).settings.core.data=JSON.parse(treeStructure);  
			//$('#tree').jstree(true).refresh();
			initTree();
		 }
		var size= $('#pagesize option:selected').text();
		$.select(1,size);
		$(".form-dialog-fix-search").fadeOut(600);
		$(".form-dialog-fix-search").find(".form-dialog").animate({
			"right" : -600
		});
	});
	
	var count =0;
	//封装查询ajax
	jQuery.select=function(pageNo,pageSize,scroll){
        count =0;
		//$("#horizontalTab2").find("tbody").empty();
		var postArr = {};
      	if(allType[dataSourceVal]){
      		postArr = allType[dataSourceVal].split("#");//将数据源获取的数据转为数组
      	}
      	
      	//var postArr = allType[dataSourceVal].split("#");//将数据源获取的数据转为数组
		var pData = {};//得到查询条件
		gainData(postArr,pData);
		var rpData = {};//得到查询组条件
		rgainData(postArr,rpData,mainfieldExtend);
//		console.log(pData);
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
//		console.log(hData+"");
		for(var h1=0;h1<ceilArr.length;h1++){
			var htmp = JSON.parse(ceilArr[h1]);
			var v = htmp.rcSubCountType;
			var idx = v.indexOf('|');
			if (idx > 0){
				v = v.toString().split('|')[0];
			}
			cData.push(v);
		}
//		console.log(cData+"");
//		var functions = {};
//		for(var i=0;i<rsearchArray.length;i++){
//			if(JSON.parse(rsearchArray[i]).subFun !=null && JSON.parse(rsearchArray[i]).subFun.length>0){
//				var s = JSON.parse(rsearchArray[i]).rcSubCountType;
//				var v = s.split("|");
//				functions[v[0]]={"key":JSON.parse(rsearchArray[i]).subFun,"value":JSON.parse(rsearchArray[i]).subFunVal};
//			}
//		}
		var pd = {};
		pd["datasource"] = dataSourceVal;
		pd["condition"] = pData;
		pd["gcondition"] = rpData;
		pd["group"] = hData+"";
		pd["nogroup"] = cData+"";
		pd["pageNo"] = pageNo;
		pd["pageSize"] = pageSize;
		pd["functions"] = functions;
		//console.log(JSON.stringify(pd));
		var index=1;
		$.ajax({
			url : '../elasticsearchstatistics/queryelasticsearch',
			method : 'POST',
			data : {"req":JSON.stringify(pd)},
			error : function() {
				// alert("error");
			},
			success : function(data) {
				var rsearchs = new Array;
				var rsearchsjs=new Array;
				var rcSubCountType = new Array;
//				var functions = {};
				var rFieldVals = {};
				for(var i=0;i<rsearchArray.length;i++){
					if(JSON.parse(rsearchArray[i]).rFieldVal !=null && JSON.parse(rsearchArray[i]).rFieldVal.length>0){
						var s = JSON.parse(rsearchArray[i]).rcSubCountType;
						var v = s.split("|");
						rFieldVals[v[0]]={"name":JSON.parse(rsearchArray[i]).rFieldVal};
					}
					
//					if(JSON.parse(rsearchArray[i]).subFun !=null && JSON.parse(rsearchArray[i]).subFun.length>0){
//						var s = JSON.parse(rsearchArray[i]).rcSubCountType;
//						var v = s.split("|");
//						functions[v[0]]={"key":JSON.parse(rsearchArray[i]).subFun,"value":JSON.parse(rsearchArray[i]).subFunVal};
//					}
					if(JSON.parse(rsearchArray[i]).Identification !="[]"){
                        rsearchsjs.push(JSON.parse(rsearchArray[i]).dataType);
						rsearchs.push(JSON.parse(rsearchArray[i]).Identification);
						var s = JSON.parse(rsearchArray[i]).rcSubCountType;
						var v = s.split("|");
						rcSubCountType.push(v[0]);
					}
				}
				var json = JSON.parse(data.result);
//				console.log(json);
				if(json ==null){
					layer.alert("没有返回数据！");
					endLoading(1);
					return;
				}
				if(typeof(json.total) != "undefined"){
                    count = json.total.total;
                }
//				console.log(json);
				var pageinfo = {};
				pageinfo["page"] = pageNo;
				pageinfo["pageSize"] = pageSize;
				pageinfo["total"] = count;
                $.pagination(pageinfo);
				var jarr = jQuery.makeArray(json.result);
//				console.log(jarr);
				var html="";
				for(var i=0;i<jarr.length;i++){
					var parentVal = checkDate();
					if(typeof(parentVal)!="undefined"){
						parentVal = parentVal.split("_")[0];
					}
					var str = jarr[i];
					var namestr="";
					for(var n=0;n<hData.length;n++){
						var hDatastr = hData[n];
						var nameValue=str[hDatastr];
						if(typeof(nameValue)!="undefined"){
							if(dateBoolean(hDatastr)){
								if(parentVal==8){
									nameValue = nameValue.substring(11,13);
								}else if(parentVal==16){
									nameValue = nameValue.substring(0,4);
								}else if(parentVal==17){
									nameValue = nameValue.substring(5,7);
								}else if(parentVal==18){
									nameValue = nameValue.substring(8,10);
								}else if(parentVal==19){
									var riqi=nameValue.substring(0,11);
									var arys1= new Array();
								    arys1=riqi.split('-');
								    var ssdate=new Date(arys1[0],parseInt(arys1[1]-1),arys1[2]);   
								    nameValue = ssdate.getDay();
								}else{
									nameValue = nameValue.substring(0,10);
								}
							}
						}
						namestr += nameValue+";";
					}
					namestr = namestr.substring(0,namestr.length-1);
					if(nodestr !=null && nodestr.length>0){
						if(namestr.toLowerCase().indexOf(nodestr.toLowerCase()) ==0){
							html+="<tr name='"+namestr+"' class='cgb' style='color: gray;background-color: #E0E0E0'>";
						}else{
							html+="<tr name='"+namestr+"'>";
						}
					}else{
						html+="<tr name='"+namestr+"'>";
					}
					html+="<td>"+(index++)+"</td>";
					for(var j=0;j<cData.length;j++){
						//alert(cData[j]);
						var tdname ="";
						if(typeof(str[cData[j]])!="undefined"){
							tdname = str[cData[j]];
						}
						var style="";
						var img="";
						var cla="";
						

						if(typeof(rFieldVals[cData[j]])!="undefined" && rFieldVals[cData[j]]!="undefined"){
							if(tdname == null || tdname.length<=0){
								tdname = rFieldVals[cData[j]].name;
							}
						}
						for(var k=0;k<rsearchs.length;k++){
                            var datas = rsearchsjs[k].split("|")[0];
							var iden = jQuery.makeArray(JSON.parse(rsearchs[k]));
							for(var z=0;z<iden.length;z++){
								if(cData[j] == rcSubCountType[k]){
//									console.log(str[cData[j]]+"|"+iden[z].Iden);
									var iname = getFunction(functions,cData[j],iden[z].Iden);
//									console.log(iname);
                                    var pdname="";
                                    if(typeof(dataIDXAndDataName[datas+"|"+iname]) !="undefined"){
                                        pdname = dataIDXAndDataName[datas+"|"+iname].name;
                                    }
									if(str[cData[j]] ==iname || str[cData[j]]==pdname){
										style = iden[z].color;
										if(typeof(style)=="undefined" || style=="undefined"){
											style="";
										}
										img=iden[z].img;
										if(img==null || img.length<=0){
											//console.log(str[cData[j]]+"|"+img);
											img="";
											cla="";
										}else{
											cla="icon-picker-md";
										}
										
									}
								}
							}
						}
//						var colorarr = new Array;
//						if(style !=null && style.length>0){
//							colorarr = style.split(":");
//						}else{
//							colorarr[1]="";
//						}
//						alert(colorarr[1]);
						var style2=""
						if(style !=null && style.length>0){
							style2 = "color:"+style.split(":")[1];
						}
						//var wid = (100/parseInt(cData.length))+"%";
						html+="<td><img src='"+img+"' alt='' class='"+cla+"' /><span style='"+style2+"' title='"+tdname+"'>"+tdname+"</span></td>";
						//html+="<td style='"+style+"'>"+str[cData[j]]+"</td>";
						
					}
					html+="</tr>";
				}
				$("#horizontalTab2").find("tbody").empty().html(html);
				endLoading(1);
				
//				if(scroll =="scroll"){
					scrollToLocation();
//				}
			}
		});
	};
	
	//上一页操作
	$("#page").on("click",".prev-page",function(){
		var currentpage = $(".paging-bar li.active").text();
		var page=Number(currentpage)-1;
		var size= $('#pagesize option:selected').text();
		$.select(page,size);
	});
	
	//下一页操作
	$("#page").on("click",".next-page",function(){
		var currentpage = $(".paging-bar li.active").text();
		page = Number(currentpage) + 1;
		var size= $('#pagesize option:selected').text();
		$.select(page,size);
		
	});
    //跳转页数
    $("#jumpCancel").on("click",function(){
        var page = parseInt($("#tiaozhuan").val());
        if(page =="" || page.length <= 0){
            layer.alert("请输入页数");
            return;
        }
        var size= parseInt($('#pagesize option:selected').text());
        var re = /^-?[0-9]+(.[0-9]+)?$/;
        if(!re.test(page)){
            layer.alert("只能输入数字");
            return;
        }
        $.select(page,size);
    });
	$("#page").on("click","li",function(){
		if($(this).hasClass("active"))
			return;
		var size= parseInt($('#pagesize option:selected').text());
		var page = parseInt($(this).html());
		if($(this).html()=="...") return;
		$.select(page,size);
	}); 
	/**
	每页显示的条目数切换
	**/
	$("select#pagesize").on("change",function(){
		var size= $('#pagesize option:selected').text();
		$.select(1,size);
	});

	$(".form-dialog-fix-view").fadeIn(100);
	$(".form-dialog-fix-view").find(".form-dialog").animate({
		"right" : 0
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
	var stenArr = new Array();
	var sthreeArr = new Array();
	var regular={};
	var characterLength={};
	var digitalText={};
	var moreQuery="";
	for(var s=0;s<searchArray.length;s++){
		var tmp = JSON.parse(searchArray[s]);
		var data_source_select = tmp.data_source_select.split("|");
		//console.info(data_source_select);
		
		var query_type=tmp.queryType;
		if(!query_type)query_type=0;
		//console.info(query_type);
		
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
						if(tmp.rstatistics===""){
							tenArr.push(searchArray[s]);
						}else{
							stenArr.push(searchArray[s]);
						}
					}else if(tmp.checked_type==="checked_type_2"){//复选框
						if(tmp.rstatistics===""){
							tenArr.push(searchArray[s]);
						}else{
							stenArr.push(searchArray[s]);
						}
					}else if(tmp.checked_type==="checked_type_3"){//下拉框
						if(tmp.rstatistics===""){
							var selectHtml = "<div class='item'><div class='attr'>"+tmp.cAttrAlias+"</div><div class=' value g-select'><select class='"+sct+"'>";
							var statisticsValStr = tmp.statisticsVal.toString().replace("[","").replace("]","");
							var statisticsValArr = statisticsValStr.split(",");
							for(var ta=0;ta<statisticsValArr.length;ta++){
								var tmpArr = statisticsValArr[ta].toString().replace(/"/g,"").split("|");
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
						}
					}else if(tmp.checked_type==="checked_type_4"){//多选列表
						if(tmp.rstatistics===""){
							var selectHtml = "<div class='item'><div class='attr'>"+tmp.cAttrAlias+"</div><div class='value'><select id='mulSelected_"+s+"' multiple='multiple' style='width:400px;' class='"+sct+"'>";
							var statisticsValStr = tmp.statisticsVal.toString().replace("[","").replace("]","");
							var statisticsValArr = statisticsValStr.split(",");
							for(var ta=0;ta<statisticsValArr.length;ta++){
								var tmpArr = statisticsValArr[ta].toString().replace(/"/g,"").split("|");
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
						}
					}
				}else if(ct=="number"){//数字
					if(tmp.numCount==2){
						doubleArr.push("<div class='item'><div class='attr'>"+tmp.cAttrAlias+"</div><div class='value'><div class='inline-block'>"
								+"<input type='text' placeholder='请输入数字' class='g-input d"+sct+" "+sct+"' '><span class='icon'></span></div>"
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
						if(tmp.rstatistics===""){
							var selectHtml = "<li><div class='left attr'>"+tmp.cAttrAlias+"</div><div class='right value g-select'><select class='"+sct+"'>";
							var statisticsValStr = tmp.statisticsVal.toString().replace("[","").replace("]","");
							var statisticsValArr = statisticsValStr.split(",");
							for(var ta=0;ta<statisticsValArr.length;ta++){
								var tmpArr = statisticsValArr[ta].toString().replace(/"/g,"").split("|");
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
						}
					}else if(tmp.checked_type==="checked_type_4"){//多选列表
						//alert(JSON.stringify(tmp));
						if(tmp.rstatistics===""){
							var sct = tmp.data_source_select;//得到统计子类型
							var sctCode = "";
							if (sct&&sct.indexOf('|') > 0){
								sctCode = sct.toString().split('|')[0];
							}
							var selectHtml = "<li><div class='left attr'>"+tmp.cAttrAlias+"</div><div class='right value'><select id='mulSelected_"+s+"' multiple='multiple' style='width:350px;' class='"+sct+"'>";
							var statisticsValStr = tmp.statisticsVal.toString().replace("[","").replace("]","");
							var statisticsValArr = statisticsValStr.split(",");
							for(var ta=0;ta<statisticsValArr.length;ta++){
								var tmpArr = statisticsValArr[ta].toString().replace(/"/g,"").split("|");
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
    var postArr = {};
  	if(allType[dataSourceVal]){
  		postArr = allType[dataSourceVal].split("#");//将数据源获取的数据转为数组
  	}
  	//var postArr = allType[dataSourceVal].split("#");//将数据源获取的数据转为数组
    var rpData = {};//得到查询组条件
    rgainData(postArr,rpData,mainfieldExtend);
    var groupArray = new Array;
    var pd2 = {};
    pd2["datasource"] = dataSourceVal;
    pd2["group"] = hData2+"";
    pd2["gcondition"] = rpData;
    pd2["functions"] = functions;
    if(hsearchArray.data_range)
    	pd2["isContainSubLib"] = hsearchArray.data_range;
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
	//console.info(gsHtml);
	$(".form-dialog-fix-search>.form-dialog").find("ul").append(gsHtml);
	
	var isLeft=0;
	$("input[id*=zd]").off();//绑定分组对应
	$("input[id*=zd]").on("click",function(){
			
			var sysVal = $(this).val();
			var sysValArr = null;
			if(sysVal!=null){
				sysValArr = sysVal.split("_");
			}
			if(sysValArr!=null&&sysValArr.length>1){/////
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
								zdHtml +=  "<div>";
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
								zdHtml +=  "<div>";
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
											zdHtml +=  "<div>";
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
//					zdHtml += "<font color='red'>该信息是随图书馆变化的，暂不能显示</font></div></div>";
					}
					
					var undef=$(this).parent().parent().next().eq(0).find("[id*='result']").html();
					if(typeof(undef)!="undefined"){
						$(this).parent().parent().next().eq(0).remove();
					}
					$(this).parent().parent().after(zdHtml);
					
					$("#addYears").off();
					var removeYearIndex = 1;
					isLeft=0;
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
						var zdHtml = "<div>";
						$(this).parent().parent().children().first().before(zdHtml);
						
						removeYearIndex = removeYearIndex+1;
						$("a[id^='removeYears']").off();//减去十年
						$("a[id^='removeYears']").on("click",function(){
							$(this).parent().remove();
							htm = $("#addYears").parent().siblings("div").children().first().html();
							year = $(htm).val();
						});
					});
				}else{//sysValArr[1]!="001"&&sysValArr[1]!="002"&&sysValArr[1]!="003"的情况
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
						zdHtml +=  "<div>";
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
								zdHtml +=  "<div>";
							}
							if(length!=0){
								zdHtml += "<div id='result_"+thisId+"' class='senior divBorder' "+of+">"
								for(var ta=0;ta<length;ta++){
									var w = sgroupArr[ta].split("|");
									zdHtml += "<div class='g-checkbox on'><input type='checkbox' name='' class='noReload' value='"+w[0]+"' id=''>"
									+"</div> <label for='checkbox-format-1'>"+w[1]+"</label>";
									if(ta == length - 1){
										zdHtml += "<a href='javascript:void(0)' id='addYears'><img src='../static/images/unfold.png' class='increase_time'/></a>";
									}
								}
								zdHtml += "</div></div></div>";
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
								zdHtml +=  "<div>";
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
											zdHtml +=  "<div>";
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
//					zdHtml += "<font color='red'>该信息是随图书馆变化的，暂不能显示</font></div></div>";
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
					$("#addYears").on("click",function(){
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
								+"</div> <label for='checkbox-format-1'>"+w[0]+"</label>";
								if(ta == sgroupArr.length-1){
									zdHtml += "<a href='javascript:void(0)' id='removeYears"+removeYearIndex+"'><img src='../static/images/unfold1.png' class='increase_time'/></a>";
								}
							}
						}
						zdHtml += "</div>";
						
						var old_html=$(this).parent().parent().html();
						
						zdHtml="<p>"+zdHtml+"</p><p>"+old_html+"</p>";
						$(this).parent().parent().empty().html(zdHtml);
						removeYearIndex = removeYearIndex+1;
						$("a[id^='removeYears']").off();//减去十年
						$("a[id^='removeYears']").on("click",function(){
							$(this).parent().parent().empty().html(old_html);
							htm = $("#addYears").parent().siblings("div").children().first().html();
							year = $(htm).val();
						});
					});
				}

			}//////
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
	
	});
	
	$("input[id*='zd']").each(function (){
		if($(this).is(":checked")||$(this).parent().hasClass("on")){
			$(this).click();//触发一次数据类型
		}
	});
	
	var initTree = function() {
	    return $('#tree').treeview({
	        //nodeIcon: 'glyphicon glyphicon-bookmark',
	        data: JSON.parse(treeStructure),
	        onNodeSelected: function(e, node) {
	    		if(typeof(node)!="undefined"){
	    			$("tr").removeAttr("style");
//		    			console.log(functions);
	    	         //获取选中节点数据
	    			nodestr = node.id;
	    		    $("tr[name^='"+nodestr+"']").attr({style:"color: gray;background-color: #E0E0E0"});
	    		    $("tr[name^='"+node.text+"']").attr({style:"color: gray;background-color: #E0E0E0"});
	    			$("#horizontalTab2").find("tbody").empty();
	    			var postArr = {};
	    	      	if(allType[dataSourceVal]){
	    	      		postArr = allType[dataSourceVal].split("#");//将数据源获取的数据转为数组
	    	      	}
	    	      	//var postArr = allType[dataSourceVal].split("#");//将数据源获取的数据转为数组
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
	    					v = v.toString().split('|')[0];
	    				}
	    				cData.push(v);
	    			}
	    			var bs=";s";
	    			var parentVal = checkDate();
	    			if(typeof(parentVal)!="undefined"){
	    				parentVal = parentVal.split("_")[0];
	    			}
	    			for(var n=0;n<hData.length;n++){
	    				var hDatastr = hData[n];
	    				if(dateBoolean(hDatastr)){
	    					if(parentVal==8){
	    						bs=";t"
	    					}else if(parentVal==16){
	    						bs=";y"
	    					}else if(parentVal==17){
	    						bs=";m"
	    					}else if(parentVal==18){
	    						bs=";d"
	    					}else if(parentVal==19){
	    						bs=";w"
	    					}
	    				}
	    			}
	    			var pd = {};
	    			pd["datasource"] = dataSourceVal;
	    			pd["condition"] = pData;
	    			pd["gcondition"] = rpData;
	    			pd["group"] = hData+"";
	    			pd["nogroup"] = cData+"";
	    			pd["logoid"] = nodestr+bs;
	    			pd["functions"] = functions;
	    			$.ajax({
	    				url : '../elasticsearchstatistics/queryelasticsearch',
	    				method : 'POST',
	    				data : {"req":JSON.stringify(pd)},
	    				error : function() {
	    					// alert("error");
	    				},
	    				success : function(data) {
	    					var num=data.result;
	    					// alert(num);
	    					var size= $('#pagesize option:selected').text();
	    					var page = num%size==0?num/size:parseInt(num/size)+1;
	    					if(page ==0){
	    						page=1;
	    					}
	    					$.select(page,size,"scroll");
	    					
	    				}
	    			});
	    		}
	        }
		});
	};
	
	var functions = {};
	for(var i=0;i<rsearchArray.length;i++){
		if(JSON.parse(rsearchArray[i]).subFun !=null && JSON.parse(rsearchArray[i]).subFun.length>0){
			var s = JSON.parse(rsearchArray[i]).rcSubCountType;
			var v = s.split("|");
			functions[v[0]]={"key":JSON.parse(rsearchArray[i]).subFun,"value":JSON.parse(rsearchArray[i]).subFunVal};
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
	var postArr = {};
	if(allType[dataSourceVal]){
		postArr = allType[dataSourceVal].split("#");//将数据源获取的数据转为数组
	}
	//var postArr = allType[dataSourceVal].split("#");//将数据源获取的数据转为数组
	var rpData = {};//得到查询组条件
	rgainData(postArr,rpData,mainfieldExtend);
	var groupArray = new Array;
	var pd2 = {};
	pd2["datasource"] = dataSourceVal;
	pd2["group"] = hData2+"";
	pd2["gcondition"] = rpData;
	pd2["functions"] = functions;
	if(hsearchArray.data_range)
    	pd2["isContainSubLib"] = hsearchArray.data_range;
//	console.log(rpData);
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
//			console.log(groupStr);
			if(groupStr !=null && groupStr.length>0){
				groupArray = groupStr.split(";");
			}
		}
	});
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
			if(json ==null){
				return;
			}
			for(var i=0;i<json.length;i++){
				var sdata = json[i].data_type+"|"+json[i].data_key;
				dataTypeAndDataDesc[sdata]={"data_desc":json[i].data_desc};
			}
		}
	});
	var horizontalHtml = "<tr><th class='number'><span>序号</span></th>";
	for(var ce=0;ce<ceilArr.length;ce++){
		//var wid = (100/parseInt(ceilArr.length))+"%";
		var cetmp = JSON.parse(ceilArr[ce]);
		horizontalHtml += "<th><span>"+cetmp.rcAttrAlias+"</span></th>";
	}
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
		var dataTypearr= dataTypeStr.split("|");
		dataTypeStr = dataTypearr[0];
		if(dataTypeStr==21||dataTypeStr==22||dataTypeStr==23||dataTypeStr==24){
			dataTypeStr = 21;
		}
		if(len==1){
			var garr = new Array;
			garr = groupArray[0].split(",");
			if(dateBoolean(garr[0])){
				if(typeof(parentVal)!="undefined"){
					dataTypeStr=parentVal.split("_")[0];
				}
			}
			if(garr[1] !=null && garr[1].length >0){
				treeStructure = '[';
				for(var i=1;i<garr.length;i++){
                    if(garr[i].indexOf("_") != 0){
                        var libarr = garr[i].split("_");
                        garr[i] = libarr[libarr.length-1];
                    }
					var nameData=garr[i];
						if(typeof(dataTypeAndDataDesc[dataTypeStr+"|"+garr[i]])=="undefined"){
							if(typeof(dataIDXAndDataName[dataTypeStr+"|"+garr[i]])!="undefined"){
                                nameData = dataIDXAndDataName[dataTypeStr+"|"+garr[i]].name;
                                if(dataTypeStr == 21){
                                    nameData="";
                                    for(var code=2;code<=garr[i].length;code+=2){
                                        nameData+=dataIDXAndDataName[dataTypeStr+"|"+garr[i].substring(0,code)].name;
                                    }
                                }
							}
						}else{
							nameData=dataTypeAndDataDesc[dataTypeStr+"|"+garr[i]].data_desc;
						}
					treeStructure += '{'+
					'"id": "'+garr[i]+'",'+
					'"text": "'+nameData+'"'+
			     '},';
				}
				var treeStructureend = treeStructure.substring(treeStructure.length-1);
				if(treeStructureend ==","){
					treeStructure = treeStructure.substring(0,treeStructure.length-1);
				}
				treeStructure += ']';
			}
		}else if(len>1){
			var garr = new Array;
			if(groupArray[0] !=null){
				garr = groupArray[0].split(",");
			}
			if(dateBoolean(garr[0])){
				if(typeof(parentVal)!="undefined"){
					dataTypeStr=parentVal.split("_")[0];
				}
			}
			if(garr[1] !=null && garr[1].length >0){
				treeStructure = '[';
				for(var i=1;i<garr.length;i++){
					var childrens = "";
                    if(groupArray[1].split(",")[1] !=null && groupArray[1].split(",")[1].length >0){
						childrens = ","+treeChild(1,rtreeArr,groupArray,garr[i],rsearchArray,dataTypeAndDataDesc,dataIDXAndDataName);
					}
                    if(garr[i].indexOf("_") != 0){
                        var libarr = garr[i].split("_");
                        garr[i] = libarr[libarr.length-1];
                    }
					var nameData=garr[i];
						if(typeof(dataTypeAndDataDesc[dataTypeStr+"|"+garr[i]])=="undefined"){
							if(typeof(dataIDXAndDataName[dataTypeStr+"|"+garr[i]])!="undefined"){
                                nameData = dataIDXAndDataName[dataTypeStr+"|"+garr[i]].name;
                                if(dataTypeStr == 21){
                                    nameData="";
                                    for(var code=2;code<=garr[i].length;code+=2){
                                        nameData+=dataIDXAndDataName[dataTypeStr+"|"+garr[i].substring(0,code)].name;
                                    }
                                }
							}
						}else{
							nameData=dataTypeAndDataDesc[dataTypeStr+"|"+garr[i]].data_desc;
						}
					treeStructure += '{'+
					'"id": "'+garr[i]+'",'+
					'"text": "'+nameData+'"'+childrens+
			     '},';
				}
				var treeStructureend = treeStructure.substring(treeStructure.length-1);
				if(treeStructureend ==","){
					treeStructure = treeStructure.substring(0,treeStructure.length-1);
				}
				treeStructure += ']';
			}
//		jsonArr.push(JSON.parse(treeStructure));
			//加载jtree树
		}
	    //$('#tree').data('jstree', false).empty();//清空树
		/*$('#tree').jstree({
			'core' : {
				'data' :JSON.parse(treeStructure)
			}
		});*/
		//$('#tree').data('treeview').destroy();
		/*$('#tree').treeview({
	        //nodeIcon: 'glyphicon glyphicon-bookmark',
	        data: JSON.parse(treeStructure)
		});*/
	}
	initTree();
	//$('#tree').on("changed.jstree", function (e, data) {});
	//填充结果结束
	
	var showHtml = "";
	$("#stmpMethod").html(showHtml);
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
	
//	$("#tmpMethod").html(showHtml);
	showHtml = "";
//	$("#tmpMethod").html(showHtml);
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
	initDatepicker();//初始化时间控件
//	$("select[id^='mulSelected']").select2();//初始化下拉多选列表
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
	$("select[id^='mulSelected']").select2();//初始化下拉多选列表
	initDatepicker();//初始化时间控件

	showHtml = "";
	t = 10;
	var of = "";
	var checkAllorReverse = "";
	for(var t1=0;t1<tenArr.length;t1++){
		checkAllorReverse = "";
		of = "";
		var cv = 0;
		var tenStr = JSON.parse(tenArr[t1]);
		var sct = tenStr.data_source_select;//得到统计子类型
		if(!(typeof(sct)=="undefined")){
			var idx = sct.indexOf('|');
			if (idx > 0)
				sct = sct.toString().split('|')[0];
		}
		var tenStr = JSON.parse(tenArr[t1]);
		var statisticsValStr = tenStr.statisticsVal.toString().replace("[","").replace("]","");
		var statisticsValArr = statisticsValStr.split(",");
		cv = cv+1;
		if(statisticsValArr.length>29){
			of = "style='height:80px; overflow:scroll'";
			if(tenStr.checked_type=="checked_type_2"){
				checkAllorReverse = "<div class='col-4'><div class='item10'>&nbsp;</div><div class='item10'>&nbsp;</div><div class='item10'>&nbsp;</div><div class='item10'>&nbsp;</div><div class='item10'><div class='g-btn' id='chooseAll_"+t1+"'>全选</div></div>"
				+"<div class='item10'><div id='noChooseAll_"+t1+"' class='g-btn'>反选</div></div><div class='item10'>&nbsp;</div><div class='item10'>&nbsp;</div><div class='item10'>&nbsp;</div><div class='item10'>&nbsp;</div></div>";
			}
		}
		showHtml += "<div id='result_"+t1+"' "+of+" class='senior'><div class='col-4'>"; 
		showHtml += "<div class='item10'>"+tenStr.cAttrAlias+"</div>";
		for(var ta=0;ta<statisticsValArr.length;ta++){
			var sct = tenStr.data_source_select;//得到统计子类型
			var sctCode = "";
			if(!(typeof(sct)=="undefined")){
				var idx = sct.indexOf('|');
				if (idx > 0){
					sctCode = sct.toString().split('|')[0];
					sct = sct.toString().split('|')[1];
				}
			}
			var tmpArr = statisticsValArr[ta].toString().replace(/"/g,"").split("|");
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
					showHtml += "<div class='item10'><div class='g-checkbox on'><input checked='checked' class='"+sctCode+"' type='checkbox' name='' value='"+name+"' id=''>"
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
		showHtml += "</div>"+checkAllorReverse+"</div>";
	}
	$("#stmpMethod").append(showHtml);
	
	showHtml = "";
	for(var mt=0;mt<singleArr.length;mt++){
		showHtml += "<div class='col-2 senior'>"+singleArr[mt]+"</div>";
	}
	$("#stmpMethod").append(showHtml);
	
//	$("#moreSearch div[class*='senior']:gt(1)").hide();//查询方式的行数超过两行时，隐藏多余的行
	//$("#stmpMethod div[class*='senior']:gt(1)").hide();//查询方式的行数超过两行时，隐藏多余的行
	
	
	//initChart();//初始化图表
	$(".switch-label-panel").each(function(){
		$(this).hide();
	});
	
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
            var uparr = groupArray[up-1].split(",");
            if(uparr[0] == "device_idx" || uparr[0] == "library_idx" || uparr[0] == "country" || uparr[0] == "province" || uparr[0] == "city" || uparr[0] == "area"){
                break;
            }
            meiosis +=1;
        }
    }
	var data_type="";
	var tArr = JSON.parse(arr[len]);
//	console.log(tArr.rcSubCountType.split("|")[0]);
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
	//alert(garr);
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
//	console.log(mainFieldList);
//	console.log(data_type);
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
    if(idsarr.length == 1){
        upwardId = idsarr[0];
    }

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
                    	nameData = treeName; 
                    }
                }else{
                    nameData=dataTypeAndDataDesc[data_type+"|"+garr[i]].data_desc;
                }
                var s =  "";
                if(groupArray[len+1]&&groupArray[len+1].split(",")[1] !=null && groupArray[len+1].split(",")[1].length >0){
                    s = ","+treeChild(len+1,arr,groupArray,ids+";"+garr[i],rsearchArray,dataTypeAndDataDesc,dataIDXAndDataName);
                }
                //var s = treeChild(len+1,arr,groupArray,ids+";"+garr[i],rsearchArray,dataTypeAndDataDesc,dataIDXAndDataName);
                gstr += '{'+
                    '"id": "'+ids+";"+garr[i]+'",'+
                    '"text": "'+nameData+'"'+s+
                    '},';
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
                gstr += '{'+
                    '"id": "'+ids+";"+garrStr+'",'+
                    '"text": "'+nameData+'"'+
                    '},';
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
	$("#oneDatepicker").datepicker({
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
 
	$("#startDatepicker").datepicker({
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
	
	$("#endDatepicker").datepicker({
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
};

function returnMain(){
	$("#mainframe").load("../templatemanagement/main","",function(){
		$(".form-dialog-fix .form-dialog").each(function(){
			$(this).attr("date-right",$(this).css("right"));
		});
		
	});
}

/**返回顶部**/
$(document).on("click","#topcontrol",function(){
	 $('html,body').animate({scrollTop:0},"fast");  
});

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

function nowtime2(){//将当前时间转换成yyyy-mm-dd HH:MM:SS格式
    var mydate = new Date();
    var year = mydate.getFullYear()+"-";
    var month = mydate.getMonth()+1;
    if(month>9){
     str += month+"-";
    }else{
     str += "0" + month+"-";
    }
    if(mydate.getDate()>9){
     str += mydate.getDate();
    }else{
     str += "0" + mydate.getDate();
    }
    if(mydate.getHours() >9){
    	str +=" "+mydate.getHours()+":";
    }else{
    	str +=" 0"+mydate.getHours()+":";
    }
    if(mydate.getMinutes()>9){
    	str +=mydate.getMinutes()+":";
    }else{
    	str +="0"+mydate.getMinutes()+":";
    }
    if(mydate.getSeconds()>9){
    	str +=mydate.getSeconds();
    }else{
    	str +="0"+mydate.getSeconds();
    }
    return str;
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
function getFunction(functions,cdata,tdname){
//	var tdname="";
	if(typeof(functions[cdata])!="undefined" && functions[cdata]!="undefined"){
	var key = functions[cdata].key;
	var val = functions[cdata].value;
	var vs = val.split(",");
	if(key =="substr"){
		if(vs[0] =="#"){
			tdname = tdname.substring(vs[1],vs[2]);
		}else{
			if(tdname == vs[0].substr(1)){
				tdname = tdname.substring(vs[1],vs[2]);
			}
		}
	}else if(key =="concat"){
		if(vs[0] =="#"){
			tdname = tdname+vs[1];
		}else if(vs[1] =="#"){
			tdname = vs[0]+tdname;
		}else if(vs[0] !="#" && vs[0].indexOf("#")>-1){
			if(tdname == vs[0].substr(1)){
				tdname = tdname+vs[1];
			}
		}else if(vs[1] !="#" && vs[1].indexOf("#")>-1){
			if(tdname == vs[1].substr(1)){
				tdname = vs[0]+tdname;
			}
		}
	}else if(key =="replace"){
		if(vs[0] =="#"){
			if(vs[1] == "#"){
				tdname = tdname.replace(tdname, vs[2]);
			}else{
				tdname = tdname.replace(vs[1], vs[2]);
			}
		}else{
			if(tdname == vs[0].substr(1)){
				if(vs[1] == "#"){
					tdname = tdname.replace(tdname, vs[2]);
				}else{
					tdname = tdname.replace(vs[1], vs[2]);
				}
			}
		}
	}else if(key =="if"){
		var vsarr = val.split(";");
		for(var vr=0;vr<vsarr.length;vr++){
			var vss=vsarr[vr].split(",");
			if(vss[0] == ">"){
				if(parseFloat(tdname) > parseFloat(vss[1])){
					tdname=vss[2];
					break;
				}
			}else if(vss[0] == "="){
				if(tdname == vss[1]){
					tdname=vss[2];
					break;
				}
			}else if(vss[0] == "<"){
				if(parseFloat(tdname) < parseFloat(vss[1])){
					tdname=vss[2];
					break;
				}
			}else if(vss[0] == ">="){
				if(parseFloat(tdname) >= parseFloat(vss[1])){
					tdname=vss[2];
					break;
				}
			}else if(vss[0] == "<="){
				if(parseFloat(tdname) <= parseFloat(vss[1])){
					tdname=vss[2];
					break;
				}
			}
		}
	}else if(key =="adddate"){
		var dateStr = tdname.split(" ");
		var nd = dateStr[0].replace(/-/ig,'/'); 
		var AfterTime= new Date(nd);
		if(AfterTime =="Invalid Date"){
			tdname="该字段不是时间不能使用日期函数！";
		}else{
			var now = AfterTime;  
                // + 1 代表日期加，- 1代表日期减  
                now.setDate(now.getDate()+parseInt(vs[1]));
                var year = now.getFullYear();
                var month = now.getMonth()+1;
                var day = now.getDate();
                if (month < 10) {  
                    month = '0' + month;
                }  
                if (day < 10) {
                    day = '0' + day;
                }
			if(vs[0] =="#"){
				tdname = year + '-' + month + '-' + day+' '+dateStr[1];
			}else{
				if(tdname.indexOf(vs[0].substr(1)) >-1){
					tdname = year + '-' + month + '-' + day+' '+dateStr[1];
				}
			}
		}
	}else if(key =="nowdate"){
		tdname = nowtime();
	}else if(key =="nowtime"){
		tdname = nowtime2();
	}else if(key =="subdate"){
		var dateStr = tdname.split(" ");
		var nd = dateStr[0].replace(/-/ig,'/'); 
		var AfterTime2= new Date();
		var AfterTime= new Date(nd);
		if(vs[0] !="*"){
			var nd2 = vs[0].replace(/-/ig,'/');
			AfterTime2= new Date(nd2);
		}
		if(AfterTime =="Invalid Date"){
			tdname="该字段不是时间不能使用日期函数！";
		}else if(AfterTime2 =="Invalid Date"){
			tdname="日期函数设置错误！";
		}else{
			var da = (AfterTime2.getTime() - AfterTime.getTime())/(24 * 60 * 60 * 1000);
			if(vs[1] =="#"){
				tdname = parseInt(da);
			}else{
				if(tdname == vs[1].substr(1)){
					tdname = parseInt(da);
				}
			}
		}
		
	}else if("add" == key){
		var t = parseFloat(tdname);
		var v= parseFloat(vs[1]);
		if("#" == vs[0]){
			tdname = (t+v)+"";
		}else{
			if(tdname == vs[0].substr(1)){
				tdname = (t+v)+"";
			}
		}
	}else if("subtract" ==key){
		var t = parseFloat(tdname);
		if("#" == vs[0]){
			var v= parseFloat(vs[1]);
			tdname = (t-v)+"";
		}else if("#" ==vs[1]){
			var v= parseFloat(vs[0]);
			tdname = (v-t)+"";
		}else if("#" != vs[0] && vs[0].indexOf("#")>-1){
			if(tdname == vs[0].substr(1)){
				var v= parseFloat(vs[1]);
				tdname = (t-v)+"";
			}
		}else if("#" == vs[1] && vs[1].indexOf("#")>-1){
			if(tdname == vs[1].substr(1)){
				var v= parseFloat(vs[0]);
				tdname = (v-t)+"";
			}
		}
	}else if("multiply".equals(key)){
		var t = parseFloat(tdname);
		var v= parseFloat(vs[1]);
		if("#" == vs[0]){
			tdname = (t*v)+"";
		}else{
			if(tdname == vs[0].substr(1)){
				tdname = (t*v)+"";
			}
		}
	}else if("divide".equals(key)){
		var t = parseFloat(tdname);
		if("#" == vs[0]){
			var v= parseFloat(vs[1]);
			tdname = (t/v)+"";
		}else if("#" ==vs[1]){
			var v= parseFloat(vs[0]);
			tdname = (v/t)+"";
		}else if("#" != vs[0] && vs[0].indexOf("#")>-1){
			if(tdname == vs[0].substr(1)){
				var v= parseFloat(vs[1]);
				tdname = (t/v)+"";
			}
		}else if("#" == vs[1] && vs[1].indexOf("#")>-1){
			if(tdname == vs[1].substr(1)){
				var v= parseFloat(vs[0]);
				tdname = (v/t)+"";
			}
		}
	}
	
}
	return tdname;
}
function scrollToLocation(){
	if(typeof($(".cgb").attr("style"))!="undefined"){
		var mainContainer = $("#horizontalTab2 tbody");
		scrollToContainer = mainContainer.find(".cgb:eq(0)");
//		alert(mainContainer.scrollTop());
		mainContainer.animate({scrollTop:scrollToContainer.offset().top - mainContainer.offset().top + mainContainer.scrollTop()});
	}
}
function checkSelect(allType,mainfieldExtend,rtreeArr,ceilArr,functions) {
    var rdata;
    var postArr = {};
  	if(allType[dataSourceVal]){
  		postArr = allType[dataSourceVal].split("#");//将数据源获取的数据转为数组
  	}
  	//var postArr = allType[dataSourceVal].split("#");//将数据源获取的数据转为数组
    var pData = {};//得到查询条件
    gainData(postArr,pData);
    var rpData = {};//得到查询组条件
    rgainData(postArr,rpData,mainfieldExtend);
    var hData = new Array;//组的数据
    var cData = new Array();//不是组的数据
    var cNameData = new Array();
    for(var h1=0;h1<rtreeArr.length;h1++){
        var htmp = JSON.parse(rtreeArr[h1]);
        var v = htmp.rcSubCountType;
        var idx = v.indexOf('|');
        if (idx > 0){
            v = v.toString().split('|')[0];
        }
        //hData.push(v);
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
            v = v.toString().split('|')[0];
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
    pd["fileName"] = "流通查询";
    pd["functions"] = functions;
    $.ajax({
        url:"../elasticsearchstatistics/checkSelect",
        type:"POST",
        async : false,
        data:{"reqName":JSON.stringify(pd)},
        success:function(data){
            rdata = data;
        }
    });
    return rdata;
}

function checkDate(){
	return $(".ropertime:checked").val()||$(".rTransDate:checked").val()||$(".rItemLoanDate:checked").val()||$(".rItemRenewDate:checked").val();
}

function dateBoolean(gFirst){
	return gFirst =="opertime"||gFirst =="TransDate"||gFirst =="ItemLoanDate"||gFirst =="ItemRenewDate";
}
