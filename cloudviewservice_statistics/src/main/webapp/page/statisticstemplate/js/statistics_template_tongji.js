//statisticsVal 统计类型
var searchFld = new Array(
		    "data_source_select", //搜索数据源
		    "cAttrAlias", //字段别名
//		    "cAttrSort", //字段排序
		    "cOptionType",//字段类型
		    "minVal",//最小值
		    "maxVal",//最大值
		    "dataFilter",//校验选项
		    "dataRegular",//校验格式
		    "regularResult",//校验结果显示
//		    "rstatistics", //统计类型
//		    "sDefault", //是否默认
//		    单行文本
//		    "textLen",//文本框长度
		    "textDefalutVal",//默认值
		    "textCount",//个数
//		    多行文本
//		    "mulTextW",//文本域宽度
		    "MulTextDefVal",//默认值
//		    选项
		    "selectList",//选项列表
		    "checked_type",//选项类型
		    "selectDefVal",//选项默认值
		    "format_output",//输出格式
//		    数字
		    "numMinVal",//最小值
		    "numMaxVal",//最大值
		    "numUnit",//小数位数
//		    "numDefVal",//默认值
		    "numCount",//个数
//		    时间和日期
		    "checked_date_time",//时间格式
		    "dateFormat",//显示格式
		    "dateCount",//个数
		    //查询类型
		    "queryType"//查询类型
		);
//chartVal 统计类型图形表
var resultFld = new Array(
	    "rcSubCountType",//结果数据源
		"rcAttrAlias", //字段别名
	    "rcAttrSort", //字段排序
		"rGroup", //是否分组
	    "rFieldVal", //字段值
	    "mainFun", //函数处理
	    "subFun", //选择类型
	    "dataType", //数据类型
	    "dtRadio", //数据类型默认值
	    "subFunVal" //选择类型值
	);

var headerFld = new Array(
		"templateName",//模板名称
		"templateDesc", //模板描述
		"templateID", //模板ID
		"dataSource", //数据源
		"data_output",//数据导出
		"data_range", //是否包含子馆
//		"checkbox_format", //导出格式
		"topHits"//top排行
);

//var groupArr = new Array();//统计结果的分组

$(function() {
	(function mainHeightController(){
		var winH = $(window).height();
		var headerH = $(".g-header").outerHeight();
		var pageTitleBar = $(".page-title-bar").outerHeight();
		var pagingBarH = $(".paging-bar").outerHeight();

		$(".content-md").css({
			"min-height":winH - headerH - pagingBarH -pageTitleBar -85
		});
		$(".form-container").css({
			"max-height":winH-100,
			"overflow" : "auto"
		});
	})();
	
	$("#dataFilter").off();//解绑数据校验
	$("#dataFilter").on("change",function (){//绑定数据校验
		var v = $.trim($(this).val());
		if(v.length==0){
			$("#dataRegular").removeAttr("disabled");
		}else{
			$("#dataRegular").attr("disabled","disabled");
		}
	});
	
	$("#data_range").parent().addClass("on");
	$("#data_output").parent().addClass("on");
	
	//绑定分组的单选框事件
	$("input[name='isteam']").off();
	$("input[name='isteam']").on("click",function (){
		/*if($.isEmptyObject($("#rcSubCountType").val())){
			layer.alert("结果数据源不能为空");
			return;
		}*/
		$("div[name='report_style']").hide();//隐藏统计样式
		$("#dataTypeTr").hide();//隐藏数据类型
		if($(this).val()==1){
			$("tr[id^='gFun']").hide();
			$("div[name='report_style']").show();
			$("#dataTypeTr").show();//显示数据类型
			$("#dtRadio").parent().addClass("on");//add by huanghuang 20170825
			$("#dtRadio").click();
		}else if($(this).val()==0){//未选中分组时，统计样式置为空
			$("tr[id^='gFun']").show();
			$("input[id^=\"chart_\"]").each(function (){
				$(this).parent().removeClass("on");
				$(this).attr("checked", false);
			});
			$("input[id^=\"dtRadio\"]").each(function (){
				$(this).parent().removeClass("on");
				$(this).attr("checked", false);
			});
			$("#dataType").val("");
		}
	});
	$("div[name='report_style']").hide();//隐藏统计样式
	$("#dataTypeTr").hide();//显示数据类型
	
	$("input[name*='rdt']").off();//绑定数据类型
	$("input[name*='rdt']").on("click",function(){
		var code = $(this).val();
		if(code!="001"){
			var rcSubCountType = $("#rcSubCountType").val();
			var JsonResult=mainFieldList[rcSubCountType];
			if(typeof(JsonResult)!="undefined"){
				var val = JsonResult.ref_statistics.split("#");
				$("#dataType").html("");
				var dataHtml = "<option value='-1'>请选择数据类型</option>";
				for(var i=0;i<mainType.length;i++){
					if($.inArray(mainType[i].maintype_idx+"",val)>-1){
						dataHtml+="<option value='"+mainType[i].maintype_idx+"|"+mainType[i].type_value+"'>"+mainType[i].type_value+"</option>";
					}
				}
				$("#dataType").html(dataHtml);
			}else{
				$("#dataType").html("");
				var dataHtml = "<option value='-1'>请选择数据类型</option>";
				$("#dataType").html(dataHtml);
			}
            
		}else{
			$("#dataType").html("");
         	var dataHtml = "<option value='-1'>请选择数据类型</option>";
        	for(var i=0;i<mainType.length;i++){
        		dataHtml+="<option value='"+mainType[i].maintype_idx+"|"+mainType[i].type_value+"'>"+mainType[i].type_value+"</option>";
        	}
        	$("#dataType").html(dataHtml);
		}
	});
	$("#rcSubCountType").off();
	$("#rcSubCountType").on("change",function(){
		$("input[id*=\"dtRadio\"]").each(function (){
			if($(this).is(":checked")||$(this).parent().hasClass("on")){
				$(this).click();//触发一次数据类型
			}
		});
	});
	
	var allType = {};//获取统计类型
	var oldval,newval;//数据源的新值和旧值
	//填充统计数据源
     $.ajax({
        url: '../statisticsTemplate/takeDataSource',
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
        	   var dataSourceHtml = "<option value=\"\">请选择数据源</option>";
        	   var aliasVal = {"loan_log":"流通统计","finance_log":"财经统计","cardissue_log":"办证统计","bookitem":"馆藏统计","reader_circulation_log":"读者统计"};//map格式存储数据源
        	   if(JsonResult!=null){
        		   for(var i=0;i<JsonResult.length;i++){
        			   allType[JsonResult[i].mainfield_table] = JsonResult[i].field+"||"+JsonResult[i].field_desc;
        			   dataSourceHtml += "<option value=\""+JsonResult[i].mainfield_table+"\">"+aliasVal[JsonResult[i].mainfield_table]+"</option>";
        		   }
        		   $("#dataSource").append(dataSourceHtml);
        		   $("#dataSource").off();//绑定子函数处理
        		   oldval=$("#dataSource").val();
        		   $("#dataSource").click(function(){
        		       oldval=$(this).val();
        		   }).change(function(){
        		       newval=$(this).val();
        		       if(searchArray.length>0||rsearchArray.length>0){
        		    	   $("#dataSource").val(oldval);
	       					layer.alert("该数据源下有数据，不能选择其他数据源，请清空数据后，再选择");
	       					return;
	       				}
        		   });
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
            	var dataHtml = "<option value='-1'>请选择数据类型</option>";
            	for(var i=0;i<JsonResult.length;i++){
            		dataHtml+="<option value='"+JsonResult[i].maintype_idx+"|"+JsonResult[i].type_value+"'>"+JsonResult[i].type_value+"</option>";
            		mainType[i] = JsonResult[i];
            	}
            	$("#dataType").html(dataHtml);
            	$("#dataTypeTr").hide();
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
  					// alert("error");
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
     
     
     var mainFun = new Array();//获取统计主函数实体
 	//填充主函数
      $.ajax({
         url: '../statisticsTemplate/selectFunMaindatas',
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
            		var main_idx = JsonResult[i].main_idx;
            		mainFun[main_idx] = JsonResult[i];
            	}
            }
         }
      });
      
      var subFun = new Array();//获取统计子函数实体
      $.ajax({
 	         url: '../statisticsTemplate/selectFunSubdatas',
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
 	        		   subFun[i] = JsonResult[i];
 	        	   }
 	           }
 	         }
 	      });
      
	//添加字段
	var sSaveOrEdit = -1;//统计搜索的新增或者编辑标识
	var rsSaveOrEdit = -1;//统计结果的新增或者编辑标识
	$("#add-field").on("click", function() {
		$("tr[id^='gFun']").show();//显示函数处理
		$("#dataTypeTr").hide();//数据类型隐藏
		$("div[name='report_style']").hide();//数据类型隐藏
		if($("#dataSource option:selected").val()===""){//未选中数据源时，无法添加字段
			layer.alert("请选择数据源");
			return;
		}
		dataToScreen(searchFld, JSON.stringify(searchFld));//新增统计查询设置时，将页面初始化
		var field = $(".panel-title li.active").data("field");
		if (field == "add-field-search") {
//			groupArr.splice(0,groupArr.length);//统计类型改变时，清空原始字段数组
//		    $("#initAttr").find("thead").nextAll().remove();
//			var mainTypeHtml = "";
//			var t = 3;
//			var cLen = Math.ceil(mainType.length/t);
//			for(var mt=1;mt<=cLen;mt++){
//				mainTypeHtml += "<tr><td></td><td colspan=\"3\">";
//				for(var m=t*(mt-1);m<t*mt;m++){
//					if(mt<cLen){
//						if(m==0){
//							mainTypeHtml += "<div class=\"g-radio\"><input type=\"radio\" id=\"rstatistics\" name=\"checked-maintype\" value=\""+mainType[m].maintype_idx+"\">"
//							+"</div> <label class=\"label-type\">"+mainType[m].type_value+"</label>";
//						}else{
//							mainTypeHtml += "<div class=\"g-radio\"><input type=\"radio\" id=\"rstatistics_"+m+"\" name=\"checked-maintype\" value=\""+mainType[m].maintype_idx+"\">"
//							+"</div> <label class=\"label-type\">"+mainType[m].type_value+"</label>";
//						}
//					}else{
//						if(mt==cLen&&m<mainType.length-1){
//							mainTypeHtml += "<div class=\"g-radio\"><input type=\"radio\" id=\"rstatistics_"+m+"\" name=\"checked-maintype\" value=\""+mainType[m].maintype_idx+"\">"
//							+"</div> <label class=\"label-type\">"+mainType[m].type_value+"</label>";
//						}
//					}
//				}
//				mainTypeHtml += "</td></tr>";
//			}
//			$("#mainType").html(mainTypeHtml);
			
//			$("input[name='checked-maintype']").off();
//   			$("input[name='checked-maintype']").on("change", function() {
////   				groupArr.splice(0,groupArr.length);//统计类型改变时，清空原始字段数组
//   				$("#initAttr").find("thead").nextAll().remove();
//   				var k = $(this).val();//点击统计类型的单选框时，获取选择的值
//   				var initAttrHtml = "";
//   				var ini = 0;
//   				if(mainType[k-1].type_code==2){
//   					initAttrHtml += "<td colspan=\"3\"><font color=\"red\">该信息是随图书馆变化的，暂不能显示</font></td>";
//   				}else{
//   					for(var h=0;h<staticsType.length;h++){
//   						if(k==staticsType[h].data_type){
//   							groupArr[ini] = staticsType[h].data_key+"|"+staticsType[h].data_desc;
//   							ini++;
//   							initAttrHtml += "<tr><td>"+ini+"</td><td>"+staticsType[h].data_desc+"</td><td><a id=\"del_"+ini+"\" href=\"javascript:delInitAttr("+ini+")\" class=\"ml10\">删除</a></td></tr>";
//   						}
//   					}
//   				}
//   				$("#initAttr").find("thead").after(initAttrHtml);
//   				
//   			});
//   			$(".tmpMainType").hide();//隐藏统计类型
			sSaveOrEdit = -1;
			//add-field-result 添加查询字段
			$(".form-dialog-fix-search").fadeIn(100);
			$(".form-dialog-fix-search").find(".form-dialog").animate({
				"right" : 0
			});
			//添加查询字段时，填充子类型统计
			$("#data_source_select").html("");
			var dsVal = $("#dataSource option:selected").val();
			var subCountHtml = "<option value=\"\" selected=\"\">请选择搜索数据源</option>";
			if(dsVal!=""&&dsVal!=null){
				var subArr = allType[dsVal].split("||");
				var subCountCode = subArr[0].split("#");
				var subCountVal = subArr[1].split("#");
				for(var c=0,v=0;c<subCountCode.length,v<subCountVal.length;c++,v++){
                    if(subCountVal[v].length > 15){
                        subCountHtml += "<option title='"+subCountVal[v]+"' value=\""+subCountCode[c]+'|'+subCountVal[v]+"\">"+subCountVal[v].substring(0,15)+"</option>";
                    }else{
                        subCountHtml += "<option title='"+subCountVal[v]+"' value=\""+subCountCode[c]+'|'+subCountVal[v]+"\">"+subCountVal[v]+"</option>";
                    }
					//subCountHtml += "<option value=\""+subCountCode[c]+'|'+subCountVal[v]+"\">"+subCountVal[v]+"</option>";
				}
			}
			$("#data_source_select").append(subCountHtml);
		} else {
//			$("input[name='isteam']").off();
//			$("input[name='isteam']").on("click",function (){
//				$("div[name='report_style']").hide();//隐藏统计样式
//				if($(this).val()==1){
//					$("div[name='report_style']").show();
//				}else if($(this).val()==0){//未选中分组时，统计样式置为空
//					$("input[id^=\"chart_\"]").each(function (){
//						$(this).parent().removeClass("on");
//						$(this).attr("checked", false);
//					});
//				}
//			});
//			$("div[name='report_style']").hide();//隐藏统计样式
			dataToScreen(resultFld, JSON.stringify(resultFld));//新增统计结果设置时，将页面初始化
			$("#rGroup_2").parent().addClass("on");//默认选中不分组
			$("#initAttr").find("thead").nextAll().remove();
			$("input[id^='chart']").each(function (){
				$(this).parent().removeClass("on");
				$(this).attr("checked",false);
			});
			rsSaveOrEdit = -1;
			//add-field-result 添加结果字段
			$(".form-dialog-fix-result").fadeIn(100);
			$(".form-dialog-fix-result").find(".form-dialog").animate({
				"right" : 0
			});
			//添加结果字段时，填充子类型统计
			$("#rcSubCountType").html("");
			var dsVal = $("#dataSource option:selected").val();
			var subCountHtml = "<option value=\"\" selected=\"\">请选择结果数据源</option>";
			if(dsVal!=""&&dsVal!=null){
				var subArr = allType[dsVal].split("||");
				var subCountCode = subArr[0].split("#");
				var subCountVal = subArr[1].split("#");
				for(var c=0,v=0;c<subCountCode.length,v<subCountVal.length;c++,v++){
                    if(subCountVal[v].length > 15){
                        subCountHtml += "<option title='"+subCountVal[v]+"' value=\""+subCountCode[c]+'|'+subCountVal[v]+"\">"+subCountVal[v].substring(0,15)+"</option>";
                    }else{
                        subCountHtml += "<option title='"+subCountVal[v]+"' value=\""+subCountCode[c]+'|'+subCountVal[v]+"\">"+subCountVal[v]+"</option>";
                    }
					//subCountHtml += "<option value=\""+subCountCode[c]+'|'+subCountVal[v]+"\">"+subCountVal[v]+"</option>";
				}
			}
			$("#rcSubCountType").append(subCountHtml);
			
			//添加结果字段时，填充函数处理
			$("#mainFun").html("");
			var mainFunHtml = "<option value=\"\" selected=\"\">请选择函数处理</option>";
			for(var mfh=1;mfh<mainFun.length;mfh++){
                if(typeof(mainFun[mfh]) != "undefined"){
                    mainFunHtml += "<option value=\""+mainFun[mfh].main_idx+"\">"+mainFun[mfh].main_desc+"</option>";
                }
			}
			$("#mainFun").append(mainFunHtml);
			
			var subTmpMap = null;//子函数说明数组
			$("#mainFun").off();//绑定函数处理
			$("#mainFun").on("change",function(){
				subTmpMap = {};
				$("#subFun").html("");
				var subFunHtml = "<option value=\"\" selected=\"\">请选择函数类型</option>";
				for(var h=0;h<subFun.length;h++){
					var mfk = $(this).val();
					if(mfk==subFun[h].main_idx){
						subFunHtml += "<option value=\""+subFun[h].sub_value+"\">"+subFun[h].sub_value+"</option>";
						subTmpMap[subFun[h].sub_value]=subFun[h].sub_desc;
					}
				}
				$("#subFun").append(subFunHtml);
				$("#subFunDesc").val("");
				$("#subFunVal").val("");
			});
			
			$("#subFun").off();//绑定子函数处理
			$("#subFun").on("change",function(){
				var mapK = $(this).val();
				$("#subFunDesc").val(subTmpMap[mapK]);
				$("#subFunVal").val("");
			});
			

		}
	});
	
	var searchArray = new Array();//存储统计搜索字段的数组
	var rsearchArray = new Array();//存储统计结果字段的数组
	var statistics_tpl_idx = $("#statistics_tpl_idx").val();
	var copyRecord = $("#copyRecord").val();//复制的标识
	   if(statistics_tpl_idx!=""){//新增时，不触发
		   $("#dataSource").attr("style","color: gray;background-color: #E0E0E0");
		   $("#dataSource").attr("disabled","disabled");
		   var editData = {};
		   editData["statistics_tpl_idx"] = statistics_tpl_idx;
		   $.ajax({
			   url:"../statisticsTemplate/findStatisticsTemplateById",
			   type:"POST",
			   data:{"req":JSON.stringify(editData)},
			   success:function(data){
				   if(copyRecord==1){
					   $("#title").html("复制统计模板");
				   }else{
					   $("#title").html("编辑统计模板");
				   }
				   var editData = JSON.parse(data.result.statistics_tpl_value);
				   dataToScreen(headerFld,JSON.stringify(editData.headerData));
				   var isShowStr = $.trim(editData.headerData.checkbox_format.toString());
				   if(isShowStr!=null&&isShowStr.length>0){
					   var isShowArr = isShowStr.split(",");
					   for(var i=0;i<isShowArr.length;i++){
							$("input[id^=\"checkbox_format\"]").each(function (){
								var val = $(this).val();
								if(val==isShowArr[i]){
									$(this).parent().addClass("on");
								}
							});
					   }
				   }
				   var tsearchArray = jQuery.makeArray(editData.searchinfo);
				   for(var t1=0;t1<tsearchArray.length;t1++){
					   searchArray.push(tsearchArray[t1].toString().replace(/^"|"$/g, ""));
				   }
				   drawSearch();
				   var trsearchArray = jQuery.makeArray(editData.resultinfo);
				   for(var t2=0;t2<trsearchArray.length;t2++){
					   rsearchArray.push(trsearchArray[t2].toString().replace(/^"|"$/g, ""));
				   }
				   rdrawSearch();
			   }
		   });
	   }
	function drawSearch(){//填充统计查询属性
		//往字段显示列表里加入数据
		//$("#searchTable").find("thead").nextAll().remove();
		var html = "";
		var sIndex = 1;
		for(var s=0;s<searchArray.length;s++){
			var sa = JSON.parse(searchArray[s]);
			var content = searchArray[s].toString();
			var tem = searchArray[s].toString().replace(/"/g, "'");
			if(content.length>40){
				content = content.substr(0,40)+"......}";
			}
			html += "<tr><td>"+sIndex+"</td><td>"+sa["cAttrAlias"]+"</td><td><p title=\""+tem+"\">"+content+"</p></td><td><input id=\"search_"+s+"\" type=\"hidden\" value=\""+s+"\"><span class=\"button button-edit-search-md\">编辑</span><span class=\"button button-delete sdelete\">删除</span></td></tr>";
			sIndex = sIndex+1;
		}

		$("#searchTable").find("tbody").empty().html(html);
		//查询模块编辑弹出窗
		$(".button-edit-search-md").off();//移除搜索结果的编辑事件
		$(".button-edit-search-md").on("click", function() {//绑定搜索结果的编辑事件
			$(".form-dialog-fix-search").fadeIn(100);
			$(".form-dialog-fix-search").find(".form-dialog").animate({
				"right" : 0
			});
			$(".main-content").css(
				{
					"height":$(window).height() - 60,
					overflow : "hidden"
				}
			);
			sSaveOrEdit = $(this).prev().val();
			//添加查询字段时，填充子类型统计
			$("#data_source_select").html("");
			var dsVal = $("#dataSource option:selected").val();
			var subCountHtml = "<option value=\"\" selected=\"\">请选择搜索数据源</option>";
			if(dsVal!=""&&dsVal!=null){
				var subArr = allType[dsVal].split("||");
				var subCountCode = subArr[0].split("#");
				var subCountVal = subArr[1].split("#");
				for(var c=0,v=0;c<subCountCode.length,v<subCountVal.length;c++,v++){
                    if(subCountVal[v].length > 15){
                        subCountHtml += "<option title='"+subCountVal[v]+"' value=\""+subCountCode[c]+'|'+subCountVal[v]+"\">"+subCountVal[v].substring(0,15)+"</option>";
                    }else{
                        subCountHtml += "<option title='"+subCountVal[v]+"' value=\""+subCountCode[c]+'|'+subCountVal[v]+"\">"+subCountVal[v]+"</option>";
                    }
					//subCountHtml += "<option value=\""+subCountCode[c]+'|'+subCountVal[v]+"\">"+subCountVal[v]+"</option>";
				}
			}
			$("#data_source_select").append(subCountHtml);
			$("#initAttr").find("thead").nextAll().remove();
			var mainTypeHtml = "";
			var t = 3;
			var cLen = Math.ceil(mainType.length/t);
			for(var mt=1;mt<=cLen;mt++){
				mainTypeHtml += "<tr><td></td><td colspan=\"3\">";
				for(var m=t*(mt-1);m<t*mt;m++){
					if(mt<cLen){
						if(m==0){
							mainTypeHtml += "<div class=\"g-radio\"><input type=\"radio\" id=\"rstatistics\" name=\"checked-maintype\" value=\""+mainType[m].maintype_idx+"\">"
							+"</div> <label class=\"label-type\">"+mainType[m].type_value+"</label>";
						}else{
							mainTypeHtml += "<div class=\"g-radio\"><input type=\"radio\" id=\"rstatistics_"+m+"\" name=\"checked-maintype\" value=\""+mainType[m].maintype_idx+"\">"
							+"</div> <label class=\"label-type\">"+mainType[m].type_value+"</label>";
						}
					}else{
						if(mt==cLen&&m<mainType.length-1){
							mainTypeHtml += "<div class=\"g-radio\"><input type=\"radio\" id=\"rstatistics_"+m+"\" name=\"checked-maintype\" value=\""+mainType[m].maintype_idx+"\">"
							+"</div> <label class=\"label-type\">"+mainType[m].type_value+"</label>";
						}
					}
				}
				mainTypeHtml += "</td></tr>";
			}
			$("#mainType").html(mainTypeHtml);
			
//			$("input[name='checked-maintype']").off();
//   			$("input[name='checked-maintype']").on("change", function() {
//   				groupArr.splice(0,groupArr.length);//统计类型改变时，清空原始字段数组
//   				$("#initAttr").find("thead").nextAll().remove();
//   				var k = $(this).val();//点击统计类型的单选框时，获取选择的值
//   				var initAttrHtml = "";
//   				var ini = 0;
//   				if(mainType[k-1].type_code==2){
//   					initAttrHtml += "<td colspan=\"3\"><font color=\"red\">该信息是随图书馆变化的，暂不能显示</font></td>";
//   				}else{
//   					for(var h=0;h<staticsType.length;h++){
//   						if(k==staticsType[h].data_type){
//   							groupArr[ini] = staticsType[h].data_key+"|"+staticsType[h].data_desc;
//   							ini++;
//   							initAttrHtml += "<tr><td>"+ini+"</td><td>"+staticsType[h].data_desc+"</td><td><a id=\"del_"+ini+"\" href=\"javascript:delInitAttr("+ini+")\" class=\"ml10\">删除</a></td></tr>";
//   						}
//   					}
//   				}
//   				$("#initAttr").find("thead").after(initAttrHtml);
//   				
//   			});
//   			$(".tmpMainType").hide();//隐藏统计类型
			dataToScreen(searchFld, searchArray[sSaveOrEdit]);
//			var rJson = JSON.parse(searchArray[sSaveOrEdit]);
//			var statisticsValStr = rJson.statisticsVal.toString().replace("[","").replace("]","");
//			var statisticsValArr = statisticsValStr.split(",");
//			$("#initAttr").find("thead").nextAll().remove();
//			if(rJson.rstatistics!=""){//未选中统计类型，则不往表格写入数据
//				var insertHtml = "";
//				if(statisticsValArr[0]!=""){
//					for(var sv in statisticsValArr){
//						var tmpArr = statisticsValArr[sv].toString().replace("\"","").replace("\"","").split("|");
//						var svindex = ++sv;
//						insertHtml += "<tr><td>"+svindex+"</td><td>"+tmpArr[1]+"</td><td><a id=\"del_"+svindex+"\" href=\"javascript:delInitAttr("+svindex+")\" class=\"ml10\">删除</a></td></tr>";
//					}
//				}else{
//					insertHtml += "<td colspan=\"3\"><font color=\"red\">该信息是随图书馆变化的，暂不能显示</font></td>";
//				}
//				$("#initAttr").find("thead").after(insertHtml);
//			}
		});
		//查询模块编辑弹出窗
		//删除字段
		var delLayer = null;
		var delIndex = 0;//要删除行的角标
		$(".sdelete").off();//移除搜索结果的删除事件
		$(".sdelete").on("click", function() {//绑定搜索结果的删除事件
			delLayer = layer.open({
				type : 1,
				shade : false,
				title : false, //不显示标题
				scrollbar : false,
				closeBtn : 0,
				shade : 0.5,
				shadeClose : true,
				area : [ "400px" ],
				offset : [ "195px" ],
				content : $('#delDialog')
			});
			delIndex = $(this).prev().prev().val();
		});
		$("#delCancel").off();//移除事件
		$("#delCancel").on("click", function() {
			if (delLayer) {
				layer.close(delLayer);
			}
		});
		$("#delSure").off();//移除事件
		$("#delSure").on("click", function() {//统计查询确认删除
			if (delLayer) {
				var ind = "#search_"+delIndex;//删除选中行
				$(ind).parent().parent().remove();
				searchArray.splice(delIndex,1);//删除数组里相应的元素
				layer.close(delLayer);
				drawSearch();//调用一次本身
			}
		});
	}
	
	function rdrawSearch(){//填充统计查询属性
		//往字段显示列表里加入数据
		$("#result-module").find("tbody").children().remove();
		var html = "";
		var gsortArr = new Array();
		var sortArr = new Array();
		for(var s=0;s<rsearchArray.length;s++){
			var sa = JSON.parse(rsearchArray[s]);
			var sortIndex = pad(sa["rcAttrSort"]);
			if(sa["rGroup"]==1){
				gsortArr.push(sortIndex);
			}else{
				sortArr.push(sortIndex);
			}
		}
		gsortArr.sort();
		sortArr.sort();
		var temArr = new Array();
		for(var s3=0;s3<gsortArr.length;s3++){
			for(var s=0;s<rsearchArray.length;s++){
				var sa = JSON.parse(rsearchArray[s]);
				if(parseInt(sa["rcAttrSort"])==parseInt(gsortArr[s3])){
					temArr[s3] = rsearchArray[s];
					break;
				}
			}
		}
		for(var s4=0;s4<sortArr.length;s4++){
			var len = temArr.length;
			for(var s=0;s<rsearchArray.length;s++){
				var sa = JSON.parse(rsearchArray[s]);
				if(parseInt(sa["rcAttrSort"])==parseInt(sortArr[s4])){
					temArr[len] = rsearchArray[s];
					break;
				}
			}
		}
		rsearchArray.slice(0, rsearchArray.length);
		rsearchArray = temArr;
		for(var s=0;s<rsearchArray.length;s++){
			var sa = JSON.parse(rsearchArray[s]);
			var content = rsearchArray[s].toString();
			if(content.length>40){
				content = content.substr(0,40)+"......}";
			}
			var tem = rsearchArray[s].toString().replace(/"/g, "'");
			if(sa["rGroup"]==1){
				html += "<tr data-id=\""+sa["rcAttrSort"]+"\"><td>"+sa["rcAttrSort"]+"</td><td><span class=\"icon\"><img src=\"../static/images/icon-group.png\" alt=\"\" />"+sa["rcAttrAlias"]+"</span></td><td title=\""+tem+"\">"+content+"</td><td><input id=\"result_"+s+"\" type=\"hidden\" value=\""+s+"\"><span class=\"button button-edit-result-md\">编辑</span>"
				+"<span class=\"button button-delete rdelete\">删除</span> <span class=\"button button-arrow prev\" title=\"往前移\">↑</span> <span class=\"button button-arrow next\" title=\"往后移\">↓</span></td></tr>";
			}else{
				html += "<tr data-id=\""+sa["rcAttrSort"]+"\"><td>"+sa["rcAttrSort"]+"</td><td>"+sa["rcAttrAlias"]+"</td><td title=\""+tem+"\">"+content+"</td><td><input id=\"result_"+s+"\" type=\"hidden\" value=\""+s+"\"><span class=\"button button-edit-result-md\">编辑</span>"
				+"<span class=\"button button-delete rdelete\">删除</span> <span class=\"button button-arrow prev\" title=\"往前移\">↑</span> <span class=\"button button-arrow next\" title=\"往后移\">↓</span></td></tr>";
			}
		}
		$("#result-module").find("tbody").append(html);
		move_result_module_init();//初始化结果模块的箭头
		//查询模块编辑弹出窗
		$(".button-edit-result-md").off();//移除搜索结果的编辑事件
		$(".button-edit-result-md").on("click", function() {//绑定搜索结果的编辑事件
			$(".form-dialog-fix-result").fadeIn(100);
			$(".form-dialog-fix-result").find(".form-dialog").animate({
				"right" : 0
			});
			$(".main-content").css(
				{
					"height":$(window).height() -60,
					overflow : "hidden"
				}
			);
			$("div[name='report_style']").hide();//隐藏统计样式
			$("#dataTypeTr").hide();//显示数据类型
			rsSaveOrEdit = $(this).prev().val();
			//初始化下拉框的数据 start ////////
			initAllSelect(allType,mainFun,subFun);
			//初始化下拉框的数据 end
			dataToScreen(resultFld, rsearchArray[rsSaveOrEdit]);
			if(rsearchArray[rsSaveOrEdit]!=null){
				if(JSON.parse(rsearchArray[rsSaveOrEdit]).rGroup==1){//是组的时候
					$("tr[id^='gFun']").hide();//隐藏函数处理
				}else{
					$("tr[id^='gFun']").show();//显示函数处理
				}
			}
			//对于特殊的statisticsVal、chartVal进行特殊处理
			initChartAndType(rsSaveOrEdit,rsearchArray);
			
		});
		//查询模块编辑弹出窗
		//删除字段
		var delLayer = null;
		var rdelIndex = 0;//要删除行的角标
		$(".rdelete").off();//移除搜索结果的删除事件
		$(".rdelete").on("click", function() {//绑定搜索结果的删除事件
			delLayer = layer.open({
				type : 1,
				shade : false,
				title : false, //不显示标题
				scrollbar : false,
				closeBtn : 0,
				shade : 0.5,
				shadeClose : true,
				area : [ "400px" ],
				offset : [ "195px" ],
				content : $('#rdelDialog')
			});
			rdelIndex = $(this).prev().prev().val();
		});
		$("#rdelCancel").off();//移除事件
		$("#rdelCancel").on("click", function() {
			if (delLayer) {
				layer.close(delLayer);
			}
		});
		$("#rdelSure").off();//移除事件
		$("#rdelSure").on("click", function() {//统计查询确认删除
			if (delLayer) {
				var ind = "#result_"+rdelIndex;//删除选中行
				$(ind).parent().parent().remove();
				rsearchArray.splice(rdelIndex,1);//删除数组里相应的元素
				layer.close(delLayer);
				rdrawSearch();//调用一次本身
			}
		});
	}
	
	//上下移动获取id
	function get_result_module_id() {
		var tem = [];
		$("#result-module tr").each(function() {
//			debugger;
			tem.push($(this).data("id"));
		});

		return tem;
	}

	//上下移动初始箭头 第一无上 最后无下
	function move_result_module_init() {
		$("#result-module tbody tr").each(function(i) {
			$(this).find(".prev").show();
			$(this).find(".next").show();
			if (i == 0) {
				$(this).find(".prev").hide();
			}
			if (i == $("#result-module tbody tr").size() - 1) {
				$(this).find(".next").hide();
			}
		});
	}
//	move_result_module_init();//初始化结果模块的箭头
	// 向上箭头
	$("#result-module").on("click", ".prev", function() {
		var pFlag = true;
		var cur_item = $(this).parents("tr");
		var prev_item = cur_item.prev();
		if (prev_item.length != 0) {
			//数组交换开始
			var preIn = prev_item.children().eq(0).text();
			var nextIn = cur_item.children().eq(0).text();
			var prevIndex = $(this).prevAll().eq(2).val();//向上箭头获取的当前数组角标
			var cur = JSON.parse(rsearchArray[prevIndex]);
			var pre = JSON.parse(rsearchArray[prevIndex-1]);
			if(pre["rGroup"]!=cur["rGroup"]){
				layer.alert("不是相同类型的数据，不能互相交换");
				return;
			}
			pre["rcAttrSort"] = nextIn;
			cur["rcAttrSort"] = preIn;
			if(cur["chartVal"].length==2&&pre["chartVal"].length>2){
				pFlag = false;
				$("#confirmMsg").html("请为当前移动行设置统计报表样式，上一行的统计报表样式将置为空");
				//删除字段
				var delLayer = layer.open({
					type : 1,
					shade : false,
					title : false, //不显示标题
					scrollbar : false,
					closeBtn : 0,
					shade : 0.5,
					shadeClose : true,
					area : [ "400px" ],
					offset : [ "195px" ],
					content : $('#confirmDialog')
				});
				$("#confirmCancel").off();//移除事件
				$("#confirmCancel").on("click", function() {
					if (delLayer) {
						layer.close(delLayer);
					}
				});
				$("#confirmSure").off();//移除事件
				$("#confirmSure").on("click", function() {//统计查询确认删除
					if (delLayer) {
						layer.close(delLayer);
					}
					prev_item.find("input[type='hidden']").val(prevIndex);
					cur_item.find("input[type='hidden']").val(prevIndex-1);
					pre["chartVal"] = "[]";
					rsearchArray[prevIndex-1] = JSON.stringify(cur);
					rsearchArray[prevIndex] = JSON.stringify(pre);
					prev_item.children().eq(0).text(nextIn);
					cur_item.children().eq(0).text(preIn);
					//数组交换结束
					prev_item.before(cur_item);
					move_result_module_init();
					
					$(".form-dialog-fix-result").fadeIn(100);
					$(".form-dialog-fix-result").find(".form-dialog").animate({
						"right" : 0
					});
					$(".main-content").css(
						{
							"height":$(window).height() -60,
							overflow : "hidden"
						}
					);
					rsSaveOrEdit = prevIndex-1;
					//初始化下拉框的数据 start ////////
					initAllSelect(allType,mainFun,subFun);
					//初始化下拉框的数据 end
					dataToScreen(resultFld, rsearchArray[rsSaveOrEdit]);
					//对于特殊的statisticsVal、chartVal进行特殊处理
					initChartAndType(rsSaveOrEdit,rsearchArray);
					
				});
			}
			if(pFlag){
				prev_item.find("input[type='hidden']").val(prevIndex);
				cur_item.find("input[type='hidden']").val(prevIndex-1);
				
				rsearchArray[prevIndex-1] = JSON.stringify(cur);
				rsearchArray[prevIndex] = JSON.stringify(pre);
				prev_item.children().eq(0).text(nextIn);
				cur_item.children().eq(0).text(preIn);
				//数组交换结束
				prev_item.before(cur_item);
				move_result_module_init();
			}
//			alert("排序后id数组：" + get_result_module_id());
//			alert("prev");
		}
	});
//	向下箭头
	$("#result-module").on("click", ".next", function() {
		var nFlag = true;
		var cur_item = $(this).parents("tr");
		var next_item = cur_item.next();
		if (next_item.length != 0) {
			//数组交换开始
			var preIn = next_item.children().eq(0).text();
			var nextIn = cur_item.children().eq(0).text();
			var prevIndex = $(this).prevAll().eq(3).val();//向下箭头获取的当前数组角标
			var cur = JSON.parse(rsearchArray[prevIndex]);
			var i = parseInt(prevIndex)+1;
			var pre = JSON.parse(rsearchArray[i]);
			if(pre["rGroup"]!=cur["rGroup"]){
				layer.alert("不是相同类型的数据，不能互相交换");
				return;
			}
			pre["rcAttrSort"] = nextIn;
			cur["rcAttrSort"] = preIn;
			if(cur["chartVal"].length>2&&pre["chartVal"].length==2){
				nFlag = false;
				$("#confirmMsg").html("请为当前移动行设置统计报表样式，同时下一行的统计报表样式将置为空");
				//删除字段
				var delLayer = layer.open({
					type : 1,
					shade : false,
					title : false, //不显示标题
					scrollbar : false,
					closeBtn : 0,
					shade : 0.5,
					shadeClose : true,
					area : [ "400px" ],
					offset : [ "195px" ],
					content : $('#confirmDialog')
				});
				$("#confirmCancel").off();//移除事件
				$("#confirmCancel").on("click", function() {
					if (delLayer) {
						layer.close(delLayer);
					}
				});
				$("#confirmSure").off();//移除事件
				$("#confirmSure").on("click", function() {//统计查询确认删除
					if (delLayer) {
						layer.close(delLayer);
					}
					next_item.find("input[type='hidden']").val(prevIndex);
					cur_item.find("input[type='hidden']").val(i);
					
					cur["chartVal"] = "[]";
					rsearchArray[i] = JSON.stringify(cur);
					rsearchArray[prevIndex] = JSON.stringify(pre);
					next_item.children().eq(0).text(nextIn);
					cur_item.children().eq(0).text(preIn);
					//数组交换结束
					next_item.after(cur_item);
					move_result_module_init();
					
					$(".form-dialog-fix-result").fadeIn(100);
					$(".form-dialog-fix-result").find(".form-dialog").animate({
						"right" : 0
					});
					$(".main-content").css(
						{
							"height":$(window).height() -60,
							overflow : "hidden"
						}
					);
					rsSaveOrEdit = prevIndex;
					//初始化下拉框的数据 start ////////
					initAllSelect(allType,mainFun,subFun);
					//初始化下拉框的数据 end
					dataToScreen(resultFld, rsearchArray[rsSaveOrEdit]);
					//对于特殊的statisticsVal、chartVal进行特殊处理
					initChartAndType(rsSaveOrEdit,rsearchArray);
					
				});
			}
			if(nFlag){
				next_item.find("input[type='hidden']").val(prevIndex);
				cur_item.find("input[type='hidden']").val(i);
				
				rsearchArray[i] = JSON.stringify(cur);
				rsearchArray[prevIndex] = JSON.stringify(pre);
				next_item.children().eq(0).text(nextIn);
				cur_item.children().eq(0).text(preIn);
				//数组交换结束
				next_item.after(cur_item);
				move_result_module_init();
			}
//			alert("排序后id数组：" + get_result_module_id());
//			alert("next");
		}
	});
	
	function pad(num) {//不足的补0
		if ((num + "").length >= 5) 
			return num;   
		return pad("0" + num, 5);   
	} 
	//弹出窗保存
	var sscv = -1;//默认值的判断标识
	$(".save-field").on(
			"click",
			function() {
				var searchData = {};//统计搜索转json
				var sflag = true;//查询时判断统计类型是否存在默认值
				if ($(this).hasClass("save-dialog-search")) { //保存查询post
//					var selectIndex = $("#rcSubCountType").val();
//					if(typeof(mainFieldList[selectIndex])=="undefined"){
//						layer.alert("数据源不能为空");
//						return;
//					}
					screenToData(searchFld, searchData);
					//console.info(searchData);
					if(searchData!=null){
						if($.trim(searchData["data_source_select"])==null||$.trim(searchData["data_source_select"])==""){
							layer.alert("数据源不能为空");
							return;
						}
						if($.trim(searchData["cAttrAlias"])==null||$.trim(searchData["cAttrAlias"])==""){
							layer.alert("字段别名不能为空");
							return;
						}
						if($.trim(searchData["queryType"])==null||$.trim(searchData["queryType"])==""){
							layer.alert("请选择查询条件属性！");
							return;
						}
					}
					for(var scv=0;scv<searchArray.length;scv++){
						var scvStr = JSON.parse(searchArray[scv]);
						if(scvStr["sDefault"]==1&&searchData["sDefault"]==1&&scv!=sSaveOrEdit){
							sscv = scv;
							sflag = false;
							$("#confirmMsg").html("已存在默认值，保存会将之前的默认值置空，并保存当前设置的默认值");
							//删除字段
							var delLayer = layer.open({
								type : 1,
								shade : false,
								title : false, //不显示标题
								scrollbar : false,
								closeBtn : 0,
								shade : 0.5,
								shadeClose : true,
								area : [ "400px" ],
								offset : [ "195px" ],
								content : $('#confirmDialog')
							});
							$("#confirmCancel").off();//移除事件
							$("#confirmCancel").on("click", function() {
								if (delLayer) {
									layer.close(delLayer);
								}
							});
							$("#confirmSure").off();//移除事件
							$("#confirmSure").on("click", function() {//统计查询确认删除
								scvStr["sDefault"] = 0;
								searchArray[sscv] = JSON.stringify(scvStr);
//								if(searchData.rstatistics===""){
									var sgroupArr = new Array();
									var newS = searchData.selectList.replace(/(\s+)/g,";");
									var sgroupArr = newS.split(";");
//									searchData["statisticsVal"]=JSON.stringify(sgroupArr);//统计类型的分组
//								}else{
//									searchData["statisticsVal"]=JSON.stringify(groupArr);//统计类型的分组
//								}
								if (delLayer) {
									layer.close(delLayer);
								}
								if(sSaveOrEdit!=-1){//编辑
									searchArray[sSaveOrEdit] = JSON.stringify(searchData);
								}else{//新增
									searchArray.push(JSON.stringify(searchData));
								}
								drawSearch();
								success();//保存成功
							});
						}
					}
					if(sflag){
						if(searchData.rstatistics===""){
							var sgroupArr = new Array();
							var newS = searchData.selectList.replace(/(\s+)/g,";");
							var sgroupArr = newS.split(";");
//							searchData["statisticsVal"]=JSON.stringify(sgroupArr);//统计类型的分组
						}else{
//							searchData["statisticsVal"]=JSON.stringify(groupArr);//统计类型的分组
						}
						if(sSaveOrEdit!=-1){//编辑
							searchArray[sSaveOrEdit] = JSON.stringify(searchData);
						}else{//新增
							searchArray.push(JSON.stringify(searchData));
						}
						drawSearch();
						success();
					}
				}
				//===================================================================================
				//上面是条件模板，下面是结果模板
				//===================================================================================
				var rsearchData = {};//统计搜索转json
				var rflag = true;//结果时判断组
				if ($(this).hasClass("save-dialog-result")) { //保存结果post
					var sss = $("#rcSubCountType").val();
					var dataType = -1;
					if($("#dataType").val()!=null){
						dataType = $("#dataType").val().split("|")[0];
					}
					if(typeof(mainFieldList[sss])=="undefined"){
						layer.alert("结果数据源不能为空");
						return;
					}
					if(mainFieldList[sss].ref_statistics =="null"){
						if(dataType != -1){
							layer.alert("没有数据类型匹配，请重新选择！");
							return;
						}
					}
					var ref = mainFieldList[sss].ref_statistics.split("#");
					var t=0;
					if(dataType ==-1){
						t=1;
					}
					for(var r=0;r<ref.length;r++){
						if(ref[r] ==dataType){
							t=1;
						}
					}
					if(t==0){
						layer.alert("数据源与数据类型不匹配，请重新选择！");
						return;
					}
					screenToData(resultFld, rsearchData);
					if(rsearchData!=null){//20170516
						if($.trim(rsearchData["rcAttrAlias"])==null||$.trim(rsearchData["rcAttrAlias"])==""){
							layer.alert("字段别名不能为空");
							return;
						}
						if($.trim(rsearchData["rcAttrSort"])==null||$.trim(rsearchData["rcAttrSort"])==""){
							layer.alert("设置排序不能为空");
							return;
						}
						if($.trim(rsearchData["rGroup"])!=""){
							if($.trim(rsearchData["dtRadio"])=="001"||$.trim(rsearchData["dtRadio"])=="002"){
								if($.trim(rsearchData["dataType"])=="-1"){//自定义和默认值必须选中数据类型
									layer.alert("数据类型不能为空");
									return;
								}
								
							}
						}
					}
                    if(rsearchData.mainFun !=null && rsearchData.mainFun >0){
                        if(rsearchData.subFun != null && rsearchData.subFun.length >0){
                            if(rsearchData.subFunVal == null || rsearchData.subFunVal.length <=0){
                                layer.alert("请填写完整函数！");
                                return;
                            }
                        }else{
                            layer.alert("请填写完整函数！");
                            return;
                        }

                    }
					var chartArr = new Array();
					$("input[id^='chart']").each(function(){
						if($(this).parent().hasClass("on")){
							chartArr.push($(this).val());
						}
					});
					rsearchData["chartVal"]=JSON.stringify(chartArr);//统计类型的图形
					var tmpv = 0;//记录该排序之前的组个数
					var tmpc = 0;//记录总共的组个数
					if(rsearchArray.length>6){
						var groupLen = 0;
						for(var g=0;g<rsearchArray.length;g++){
							var jsonG = JSON.parse(rsearchArray[g]);
							if(jsonG.rGroup=="1"){
								groupLen++;
							}
						}
						if(groupLen>6){
							layer.alert("分组个数已超过7个，不能再设置分组");
							return;
						}
					}
					var gtf = 0;
					for(var v=0;v<rsearchArray.length;v++){
						var tv = JSON.parse(rsearchArray[v]);
						if(tv["chartVal"].length>3){
							gtf = gtf+1;
						}
						var tv2 = null;
						if(rsSaveOrEdit==-1){//新增
							if(tv["rcAttrSort"]==rsearchData["rcAttrSort"]){
								layer.alert("设置的排序存在重复，请检查后再保存");
								return;
							}
							
						}else{//编辑
							tv2 = JSON.parse(rsearchArray[rsSaveOrEdit]);
							if(tv["rcAttrSort"]==rsearchData["rcAttrSort"]&&tv2["rcAttrSort"]!=rsearchData["rcAttrSort"]/*&&rsSaveOrEdit==v*/){
								layer.alert("设置的排序存在重复，请检查后再保存");
								return;
							}
						}
						if(tv["rGroup"]==1){//是分组的
							tmpc = tmpc+1;
							if(tv["rcAttrSort"]<rsearchData["rcAttrSort"]){
								tmpv = tmpv+1;
							}
						}
					}
					//add by 20170914 start
					if(rsSaveOrEdit!=-1){//编辑
						var existed = rsearchData.rGroup=="1"&&rsSaveOrEdit>1;
						if(existed){
							var statisticsValStr = rsearchData["chartVal"].replace("[","").replace("]","");
							if(statisticsValStr.indexOf("chart")>-1){
								layer.alert("当存在两个以上的统计组时，当前分组不能选择统计报表样式");
								return;
							}
						}
						
					}else{//新增
						if(tmpc>1){//分组是两组之后的情况
							var statisticsValStr = rsearchData["chartVal"].replace("[","").replace("]","");
							if(statisticsValStr.indexOf("chart")>-1){
								layer.alert("当存在两个以上的统计组时，当前分组不能选择统计报表样式");
								return;
							}
						}
					}
					//add by 20170914 end
					if(tmpv!=0&&tmpv<2&&tmpc>1&&rsearchData["rGroup"]==1){
						rflag = false;
						if(rsSaveOrEdit==-1){//新增
							$("#confirmMsg").html("分组设置前已存在两个组的统计报表样式,系统将会自动存储前两个");
							//删除字段
							var delLayer = layer.open({
								type : 1,
								shade : false,
								title : false, //不显示标题
								scrollbar : false,
								closeBtn : 0,
								shade : 0.5,
								shadeClose : true,
								area : [ "400px" ],
								offset : [ "195px" ],
								content : $('#confirmDialog')
							});
							$("#confirmCancel").off();//移除事件
							$("#confirmCancel").on("click", function() {
								if (delLayer) {
									layer.close(delLayer);
								}
							});
							$("#confirmSure").off();//移除事件
							$("#confirmSure").on("click", function() {//统计查询确认删除
								if (delLayer) {
									layer.close(delLayer);
								}
								for(var i=0;i<rsearchArray.length;i++){
									var p = JSON.parse(rsearchArray[i]);
									var f = p["rcAttrSort"]<rsearchData["rcAttrSort"]&&p["rGroup"]==1;//判断组删除排序前两个后的图形参数
									/*var statisticsValStr = rsearchData["chartVal"].replace("[","").replace("]","");
									if(statisticsValStr.indexOf(",")>0){
										layer.alert("当存在两个以上的统计组时，每个统计组只能选择一个统计报表样式");
										return;
									}*/
									if(!f){
										p["chartVal"] = "[]";
										rsearchArray[i] = JSON.stringify(p);
									}
								}
								if(rsSaveOrEdit!=-1){//编辑
									rsearchArray[rsSaveOrEdit] = JSON.stringify(rsearchData);
								}else{//新增
									rsearchArray.push(JSON.stringify(rsearchData));
								}
								rdrawSearch();
								success();
							});
						}else{//编辑
							tv2 = JSON.parse(rsearchArray[rsSaveOrEdit]);
							if(tv2["rcAttrSort"]!=rsearchData["rcAttrSort"]){
								$("#confirmMsg").html("分组设置前已存在两个组的统计报表样式,系统将会自动存储前两个");
								//删除字段
								var delLayer = layer.open({
									type : 1,
									shade : false,
									title : false, //不显示标题
									scrollbar : false,
									closeBtn : 0,
									shade : 0.5,
									shadeClose : true,
									area : [ "400px" ],
									offset : [ "195px" ],
									content : $('#confirmDialog')
								});
								$("#confirmCancel").off();//移除事件
								$("#confirmCancel").on("click", function() {
									if (delLayer) {
										layer.close(delLayer);
									}
								});
								$("#confirmSure").off();//移除事件
								$("#confirmSure").on("click", function() {//统计查询确认删除
									if (delLayer) {
										layer.close(delLayer);
									}
									for(var i=0;i<rsearchArray.length;i++){
										var p = JSON.parse(rsearchArray[i]);
										var f = p["rcAttrSort"]<rsearchData["rcAttrSort"]&&p["rGroup"]==1;//判断组删除排序前两个后的图形参数
										/*var statisticsValStr = rsearchData["chartVal"].replace("[","").replace("]","");
										if(statisticsValStr.indexOf(",")>0){
											layer.alert("当存在两个以上的统计组时，每个统计组只能选择一个统计报表样式");
											return;
										}*/
										if(!f){
											p["chartVal"] = "[]";
											rsearchArray[i] = JSON.stringify(p);
										}
									}
									if(rsSaveOrEdit!=-1){//编辑
										rsearchArray[rsSaveOrEdit] = JSON.stringify(rsearchData);
									}else{//新增
										rsearchArray.push(JSON.stringify(rsearchData));
									}
									rdrawSearch();
									success();
								});
							}else{
								/*var statisticsValStr = rsearchData["chartVal"].replace("[","").replace("]","");
								if(statisticsValStr.indexOf(",")>0){
									layer.alert("当存在两个以上的统计组时，每个统计组只能选择一个统计报表样式");
									return;
								}*/
								if(rsSaveOrEdit!=-1){//编辑
									rsearchArray[rsSaveOrEdit] = JSON.stringify(rsearchData);
								}else{//新增
									rsearchArray.push(JSON.stringify(rsearchData));
								}
								rdrawSearch();
								success();
							}
						}
					}else if(gtf>1&&rsearchData["rGroup"]==1&&tmpv!=0&&rsearchData["chartVal"].length>2){//判断是否有添加的样式，如果有则弹出框显示
						rflag = false;
						$("#confirmMsg").html("分组设置前已存在两个组的统计报表样式,当前设置的统计报表样式不会被保存");
						//删除字段
						var delLayer = layer.open({
							type : 1,
							shade : false,
							title : false, //不显示标题
							scrollbar : false,
							closeBtn : 0,
							shade : 0.5,
							shadeClose : true,
							area : [ "400px" ],
							offset : [ "195px" ],
							content : $('#confirmDialog')
						});
						$("#confirmCancel").off();//移除事件
						$("#confirmCancel").on("click", function() {
							if (delLayer) {
								layer.close(delLayer);
							}
						});
						$("#confirmSure").off();//移除事件
						$("#confirmSure").on("click", function() {//统计查询确认删除
							if (delLayer) {
								layer.close(delLayer);
							}
							rsearchData["chartVal"] = "[]";
							if(rsSaveOrEdit!=-1){//编辑
								rsearchArray[rsSaveOrEdit] = JSON.stringify(rsearchData);
							}else{//新增
								rsearchArray.push(JSON.stringify(rsearchData));
							}
							rdrawSearch();
							success();
						});
					}else if(tmpv==0&&rsearchArray.length>1){
						/*var statisticsValStr = rsearchData["chartVal"].replace("[","").replace("]","");
						if(statisticsValStr.indexOf(",")>0&&tmpc>1){
							layer.alert("当存在两个以上的统计组时，每个统计组只能选择一个统计报表样式");
							return;
						}*/
					}else if(gtf==1&&rsearchData["rGroup"]==1&&rsSaveOrEdit==-1){
						/*var statisticsValStr = rsearchData["chartVal"].replace("[","").replace("]","");
						if(statisticsValStr.indexOf(",")>0){
							layer.alert("当存在两个以上的统计组时，每个统计组只能选择一个统计报表样式");
							return;
						}*/
						
					}
					if(rflag){
						if(rsSaveOrEdit!=-1){//编辑
							rsearchArray[rsSaveOrEdit] = JSON.stringify(rsearchData);
						}else{//新增
							rsearchArray.push(JSON.stringify(rsearchData));
						}
						rdrawSearch();
						success();
					}
				}

			});

	//预览 js
	//预览页面js
	$(".review").on("click", function() {
		$(".form-dialog-fix-view").fadeIn(100);
		$(".form-dialog-fix-view").find(".form-dialog").animate({
			"right" : 0
		});
		$("#dialogTitle").text($("#templateName").val());//模板名称
		/*$("#dialogTitle").html("");//填充
		$("#dialogTitle").append("待写入");//填充
*/		//填充搜索开始
		$("#colTmp").hide();//隐藏分组对应
		$("#tmpMethod").html("");
		var singleArr = new Array();
		var doubleArr = new Array();
		var timeDoubleArr = new Array();
		var threeArr = new Array();
		var tenArr = new Array();
//		var stenArr = new Array();
//		var sdoubleArr = new Array();
//		var sthreeArr = new Array();
//		console.log(searchArray);
		for(var s=0;s<searchArray.length;s++){
			var tmp = JSON.parse(searchArray[s]);
			var ct = tmp.cOptionType;
			var sct = tmp.data_source_select;//得到统计子类型
			var query_type=tmp.queryType;
			if(!query_type)query_type=0;
			if(query_type==0){
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
							
							doubleArr.push("<div class=\"item\"><div class=\"attr\">"+tmp.cAttrAlias+"</div><div class=\"value\"><div class=\"inline-block\">"
									+"<input type=\"text\" value=\""+strs[0]+"\" placeholder=\"请输入值\" class=\"g-input\"><span class=\"icon\"></span></div>"
									+"<span class=\"inline-block\">&nbsp;-&nbsp;</span><div class=\"inline-block\"><input type=\"text\" value=\""+str+"\" placeholder=\"请输入值\" class=\"g-input\">"
									+"<span class=\"icon\"></span></div></div></div>");
						}else{
							threeArr.push("<div class=\"item\"><div class=\"attr\">"+tmp.cAttrAlias+"</div><div class=\"value\"><input type=\"text\" name=\"\" id=\"\" class=\"g-input\" value=\""+tmp.textDefalutVal+"\" placeholder=\"请输入值\"></div></div>");
						}
					}else if(ct=="multiple-text"){//多行文本
						singleArr.push("<div class=\"item\"><div class=\"attr\">"+tmp.cAttrAlias+"</div><div class=\"value\"><textarea class=\"g-textarea\">"+tmp.MulTextDefVal+"</textarea></div></div>");
					}else if(ct=="select"){//选项
						if(tmp.checked_type==="checked_type"){//单选按钮
	//						if(tmp.rstatistics===""){
								tenArr.push(searchArray[s]);
	//						}
	//						else{
	//							stenArr.push(searchArray[s]);
	//						}
						}else if(tmp.checked_type==="checked_type_2"){//复选框
	//						if(tmp.rstatistics===""){
								tenArr.push(searchArray[s]);
	//						}
	//						else{
	//							stenArr.push(searchArray[s]);
	//						}
						}else if(tmp.checked_type==="checked_type_3"){//下拉框
	//						if(tmp.rstatistics===""){
								var selectHtml = "<div class=\"item\"><div class=\"attr\">"+tmp.cAttrAlias+"</div><div class=\" value g-select\"><select>";
								var statisticsValStr = tmp.selectList.replace(/(\s+)/g,";");
								var statisticsValArr = statisticsValStr.split(";");
								for(var ta=0;ta<statisticsValArr.length;ta++){
									var tmpArr = statisticsValArr[ta].toString().replace("\"","").replace("\"","").split("|");
									var name=tmpArr[1];
									if(tmp.format_output == "format_output"){
										name=tmpArr[0];
									}
									if(tmpArr[0] == tmp.selectDefVal){
										selectHtml += "<option value=\""+tmpArr[0]+"\" selected>"+name+"</option>";
									}else{
										selectHtml += "<option value=\""+tmpArr[0]+"\">"+name+"</option>";
									}
								}
								selectHtml += "</select><span class=\"arr-icon\"></span></div></div>";
								threeArr.push(selectHtml);
	//						}
	//						else{
	//							var selectStr = "";
	//							if(tmp["sDefault"]==1){
	//								selectStr = "class=\"on g-radio\"";
	//							}else{
	//								selectStr = "class=\"g-radio\"";
	//							}
	//							var selectHtml = "<div class=\"item\"><div class=\"attr\"><div "+selectStr+"><input type=\"radio\" name=\"type_tongji\" id=\"type_tongji1\" value=\""+sct+"\"></div>"+tmp.cAttrAlias+"</div><div class=\" value g-select\"><select>";
	//							var statisticsValStr = tmp.statisticsVal.toString().replace("[","").replace("]","");
	//							var statisticsValArr = statisticsValStr.split(",");
	//							for(var ta=0;ta<statisticsValArr.length;ta++){
	//								var tmpArr = statisticsValArr[ta].toString().replace("\"","").replace("\"","").split("|");
	//								var name=tmpArr[1];
	//								if(tmp.format_output == "format_output"){
	//									name=tmpArr[0];
	//								}
	//								if(tmpArr[0] == tmp.selectDefVal){
	//									selectHtml += "<option value=\""+tmpArr[0]+"\" selected>"+name+"</option>";
	//								}else{
	//									selectHtml += "<option value=\""+tmpArr[0]+"\">"+name+"</option>";
	//								}
	//							}
	//							selectHtml += "</select><span class=\"arr-icon\"></span></div></div>";
	//							sthreeArr.push(selectHtml);
	//						}
						}else if(tmp.checked_type==="checked_type_4"){//多选列表
	//						if(tmp.rstatistics===""){
								var selectHtml = "<div class=\"item\"><div class=\"attr\">"+tmp.cAttrAlias+"</div><div class=\"value\"><select name=\"\" id=\"mulSelected_"+s+"\" multiple=\"multiple\" style=\"width:400px;\">";
	//							var statisticsValStr = tmp.statisticsVal.toString().replace("[","").replace("]","");
	//							var statisticsValArr = statisticsValStr.split(",");
								var statisticsValStr = tmp.selectList.replace(/(\s+)/g,";");
								var statisticsValArr = statisticsValStr.split(";");
								for(var ta=0;ta<statisticsValArr.length;ta++){
									var tmpArr = statisticsValArr[ta].toString().replace("\"","").replace("\"","").split("|");
									var name=tmpArr[1];
									if(tmp.format_output == "format_output"){
										name=tmpArr[0];
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
										selectHtml += "<option value=\""+tmpArr[0]+"\" selected>"+name+"</option>";
									}else{
										selectHtml += "<option value=\""+tmpArr[0]+"\">"+name+"</option>";
									}
								}
								selectHtml += "</select><span class=\"arr-icon\"></span></div></div>";
								doubleArr.push(selectHtml);
	//						}
	//						else{
	//							var selectStr = "";
	//							if(tmp["sDefault"]==1){
	//								selectStr = "class=\"on g-radio\"";
	//							}else{
	//								selectStr = "class=\"g-radio\"";
	//							}
	//							var selectHtml = "<div class=\"item\"><div class=\"attr\"><div "+selectStr+"><input type=\"radio\" name=\"type_tongji\" id=\"type_tongji1\" value=\""+sct+"\"></div>"+tmp.cAttrAlias+"</div><div class=\"value\"><select name=\"\" id=\"mulSelected_"+s+"\" multiple=\"multiple\" style=\"width:400px;\">";
	//							var statisticsValStr = tmp.statisticsVal.toString().replace("[","").replace("]","");
	//							var statisticsValArr = statisticsValStr.split(",");
	//							for(var ta=0;ta<statisticsValArr.length;ta++){
	//								var tmpArr = statisticsValArr[ta].toString().replace("\"","").replace("\"","").split("|");
	//								var name=tmpArr[1];
	//								if(tmp.format_output == "format_output"){
	//									name=tmpArr[0];
	//								}
	//								var strs= new Array();
	//								strs = tmp.selectDefVal.split(",");
	//								var t=0;
	//								for(var i=0;i<strs.length;i++){
	//									if(strs[i] == tmpArr[0]){
	//										t=1;
	//									}
	//								}
	//								if(t == 1){
	//									selectHtml += "<option value=\""+tmpArr[0]+"\" selected>"+name+"</option>";
	//								}else{
	//									selectHtml += "<option value=\""+tmpArr[0]+"\">"+name+"</option>";
	//								}
	//							}
	//							selectHtml += "</select><span class=\"arr-icon\"></span></div></div>";
	//							sdoubleArr.push(selectHtml);
	//						}
						}
					}else if(ct=="number"){//数字
						if(tmp.numCount==2){
							doubleArr.push("<div class=\"item\"><div class=\"attr\">"+tmp.cAttrAlias+"</div><div class=\"value\"><div class=\"inline-block\">"
									+"<input type=\"text\" placeholder=\"请输入数字\" class=\"g-input\" onkeyup=\"(this.v=function(){this.value=this.value.replace(/[^0-9-]+/,'');}).call(this)\" onblur=\"this.v();\"><span class=\"icon\"></span></div>"
									+"<span class=\"inline-block\">&nbsp;-&nbsp;</span><div class=\"inline-block\"><input type=\"text\" placeholder=\"请输入数字\" class=\"g-input\" onkeyup=\"(this.v=function(){this.value=this.value.replace(/[^0-9-]+/,'');}).call(this)\" onblur=\"this.v();\">"
									+"<span class=\"icon\"></span></div></div></div>");
						}else{
							threeArr.push("<div class=\"item\"><div class=\"attr\">"+tmp.cAttrAlias+"</div><div class=\"value\"><input type=\"text\" name=\"\" id=\"\" class=\"g-input\" placeholder=\"请输入数字\" onkeyup=\"(this.v=function(){this.value=this.value.replace(/[^0-9-]+/,'');}).call(this)\" onblur=\"this.v();\"></div></div>");
						}
					}else if(ct=="date-time"){//日期和时间
						if(tmp.dateCount==="1"){
							threeArr.push("<div class=\"item\"><div class=\"attr\">"+tmp.cAttrAlias+"</div><div class=\"value\"><div class=\"g-inputdate inline-block\">"
									+"<input type=\"text\" placeholder=\"时间\" class=\"g-input datepicker\"><span class=\"icon\"></span></div>"
									+"</div></div>");
						}else if(tmp.dateCount==="2"){
							timeDoubleArr.push("<div class=\"item\"><div class=\"attr\">"+tmp.cAttrAlias+"</div><div class=\"value\"><div class=\"g-inputdate inline-block\">"
									+"<input type=\"text\" placeholder=\"起始时间\" class=\"g-input datepicker\"><span class=\"icon\"></span></div>"
									+"<span class=\"inline-block\">&nbsp;-&nbsp;</span><div class=\"g-inputdate inline-block\"><input type=\"text\" placeholder=\"结束时间\" class=\"g-input datepicker\">"
									+"<span class=\"icon\"></span></div></div></div>");
						}
	
					}
			}
		}else{
			
		}
		
	}
		//填充结果开始
		var aTmpArr = new Array();
		var rtreeArr = new Array();//组
		var ceilArr = new Array();//不是组
		var sortTmp = new Array();
		for(var s=0;s<rsearchArray.length;s++){
			var sa = JSON.parse(rsearchArray[s]);
			var sortIndex = pad(sa["rcAttrSort"]);
			sortTmp.push(sortIndex);
		}
		sortTmp.sort();//数组排序
		for(var i=0;i<sortTmp.length;i++){
			for(var r=0;r<rsearchArray.length;r++){
				var sa = JSON.parse(rsearchArray[r]);
				if(sortTmp[i]===pad(sa["rcAttrSort"])){
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
		
		var horizontalHtml = "";
		$("#horizontalTab").find("thead").find("tr").html(horizontalHtml);
		for(var ce=0;ce<ceilArr.length;ce++){
			var cetmp = JSON.parse(ceilArr[ce]);
			horizontalHtml += "<th>"+cetmp.rcAttrAlias+"</th>";
		}
		horizontalHtml += "<th>合计</th>";
		$("#horizontalTab").find("thead").find("tr").html(horizontalHtml);
		
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
			if(len==1){
				treeStructure = '[{'+
				'"id": "'+ras+'",'+
				'"text": "'+rsct+'"'+
				'}]';
			}else if(len>1){
				var childrens = treeChild(1,rtreeArr);
				treeStructure = '[{'+
					'"id": "'+ras+'",'+
					'"text": "'+rsct+'",'+childrens+
				'}]';
			}
			//加载jtree树
		}
	    /*$('#tree').data('jstree', false).empty();//清空树
		$('#tree').jstree({
			'core' : {
				'data' : JSON.parse(treeStructure)
			}
		});

		$('#tree').on("changed.jstree", function (e, data) {
			//获取选中节点数据
		    console.log(data.node); 
		});*/
		
		$('#tree').treeview({
	        //nodeIcon: 'glyphicon glyphicon-bookmark',
	        data: JSON.parse(treeStructure)
		});
		
		//填充结果结束
		var showHtml = "";
		$("#stmpMethod").html(showHtml);
		
		//填充timeDoubleArr
		var t = 2;
		var sdLen = Math.ceil(timeDoubleArr.length/t);
		for(var mt=1;mt<=sdLen;mt++){
			showHtml += "<div class=\"col-4 senior\">";
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
//		showHtml += "</div>";
//		$("#stmpMethod").append(showHtml);
//		showHtml = "";
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
		
//		$("#tmpMethod").html(showHtml);
		showHtml = "";
		t = 3;
		sdLen = Math.ceil(threeArr.length/t);
		for(var mt=1;mt<=sdLen;mt++){
			if(mt<sdLen){
				showHtml += "<div class=\"col-6 senior\">";
			}else{
				if(threeArr.length%t==0||threeArr.length%t==2){
					showHtml += "<div class=\"col-6 senior\">";
				}else if(threeArr.length%t==1&&doubleArr.length!=0){
					showHtml += "<div class=\"col-4 senior\">";
				}else{
					showHtml += "<div class=\"col-6 senior\">";
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
			showHtml += "<div class=\"col-4 senior\">";
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
//			var statisticsValStr = tenStr.statisticsVal.toString().replace("[","").replace("]","");
			var newS = tenStr.selectList.replace(/(\s+)/g,";");//////
			var statisticsValArr = newS.split(";");
			cv = cv+1;
			if(statisticsValArr.length>29){
				sof = "style='height:80px; overflow:scroll'";
				if(tenStr.checked_type==="checked_type_2"){
					scheckAllorReverse = "<div class=\"col-4\"><div class=\"item10\">&nbsp;</div><div class=\"item10\">&nbsp;</div><div class=\"item10\">&nbsp;</div><div class=\"item10\">&nbsp;</div><div class=\"item10\"><div class=\"g-btn\" id=\"chooseAll_s"+t1+"\">全选</div></div>"
					+"<div class=\"item10\"><div id=\"noChooseAll_s"+t1+"\" class=\"g-btn\">反选</div></div><div class=\"item10\">&nbsp;</div><div class=\"item10\">&nbsp;</div><div class=\"item10\">&nbsp;</div><div class=\"item10\">&nbsp;</div></div>";
				}
			}
			showHtml += "<div id=\"result_s"+t1+"\" "+sof+" class=\"senior\"><div class=\"col-4\">"; 
			showHtml += "<div class=\"item10\">"+tenStr.cAttrAlias+"</div>";
			for(var ta=0;ta<statisticsValArr.length;ta++){
				var sct = tenStr.data_source_select;//得到统计子类型
				if(!(typeof(sct)=="undefined")){
					var idx = sct.indexOf('|');
					if (idx > 0)
						sct = sct.toString().split('|')[1];
				}
				var tmpArr = statisticsValArr[ta].toString().replace("\"","").replace("\"","").split("|");
				if(tenStr.checked_type==="checked_type"){//单选
					var name=tmpArr[1];
					if(tenStr.format_output == "format_output"){
						name=tmpArr[0];
					}
					if(tmpArr[0] == tenStr.selectDefVal){
						showHtml += "<div class=\"item10\"><div class=\"g-radio on\"><input type=\"radio\" name=\"data-output\" value=\""+tmpArr[0]+"\" id=\"\">" +
						"</div> <label for=\"data-output2\">"+name+"</label></div>";
					}else{
						showHtml += "<div class=\"item10\"><div class=\"g-radio\"><input type=\"radio\" name=\"data-output\" value=\""+tmpArr[0]+"\" id=\"\">" +
						"</div> <label for=\"data-output2\">"+name+"</label></div>";
					}
				}else if(tenStr.checked_type==="checked_type_2"){//复选
					var name=tmpArr[1];
					if(tenStr.format_output == "format_output"){
						name=tmpArr[0];
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
						showHtml += "<div class=\"item10\"><div class=\"g-checkbox on\"><input checked=\"checked\" type=\"checkbox\" name=\"\" value=\""+tmpArr[0]+"\" id=\"\">"
						+"</div> <label for=\"checkbox-format-1\">"+name+"</label></div>";
					}else{
						showHtml += "<div class=\"item10\"><div class=\"g-checkbox\"><input type=\"checkbox\" name=\"\" value=\""+tmpArr[0]+"\" id=\"\">"
						+"</div> <label for=\"checkbox-format-1\">"+name+"</label></div>";
					}
				}
				cv = cv+1;
				if(cv%10==0){
					showHtml += "</div><div class=\"col-4\">";
				}
			}
			showHtml += "</div>"+scheckAllorReverse+"</div>";
		}
		$("#stmpMethod").append(showHtml);
		
		showHtml = "";
		for(var mt=0;mt<singleArr.length;mt++){
			showHtml += "<div class=\"col-2 senior\">"+singleArr[mt]+"</div>";
		}
		$("#stmpMethod").append(showHtml);
		//001自定义002默认值003系统默认值
		var gsHtml = "";
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
//			console.log(JsonResult);
			if(group.dtRadio=="001"){
				if(group.dataType!=-1){
					gsHtml += "<div class=\"col-6 senior\">";
					gsHtml += "<div class=\"item10\"><div class=\"attr\">"+group.rcSubCountType.split("|")[1]+"</div><div class=\"value\"></div></div>";
					gsHtml += "<div class=\"item10\"><div class=\"g-radio on\"><input type=\"radio\" name=\"data-output"+rt+"\" value=\""+code+"\" id=\"zd1_"+rt+"\">" +
					"</div><label for=\"data-output2\">"+alias+"</label></div>";
					gsHtml += "</div>";
				}
			}else if(group.dtRadio=="002"){//默认值不能为空，所以不用判断-1
				if(typeof(JsonResult)!="undefined"){
					var val = JsonResult.ref_statistics.split("#");
					if(JsonResult.ref_statistics!="null"){
						gsHtml += "<div class=\"col-6 senior\">";
						gsHtml += "<div class=\"item10\"><div class=\"attr\">"+group.rcSubCountType.split("|")[1]+"</div><div class=\"value\"></div></div>";
						for(var i=0;i<mainType.length;i++){
							if($.inArray(mainType[i].maintype_idx+"",val)>-1){
								if(mainType[i].maintype_idx==code){
									gsHtml += "<div class=\"item10\"><div class=\"g-radio on\"><input type=\"radio\" name=\"data-output"+rt+"\" value=\""+mainType[i].maintype_idx+"\" id=\"zd2_"+i+rt+"\">" +
									"</div><label for=\"data-output2\">"+mainType[i].type_value+"</label></div>";
								}else{
									gsHtml += "<div class=\"item10\"><div class=\"g-radio\"><input type=\"radio\" name=\"data-output"+rt+"\" value=\""+mainType[i].maintype_idx+"\" id=\"zd2_"+i+rt+"\">" +
									"</div><label for=\"data-output2\">"+mainType[i].type_value+"</label></div>";
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
						gsHtml += "<div class=\"col-6 senior\">";
						gsHtml += "<div class=\"item10\"><div class=\"attr\">"+group.rcSubCountType.split("|")[1]+"</div><div class=\"value\"></div></div>";
						for(var i=0;i<mainType.length;i++){
							if($.inArray(mainType[i].maintype_idx+"",val)>-1){
								if(mainType[i].maintype_idx==code){
									gsHtml += "<div class=\"item10\"><div class=\"g-radio on\"><input type=\"radio\" name=\"data-output"+rt+"\" value=\""+mainType[i].maintype_idx+"\" id=\"zd3_"+i+rt+"\">" +
									"</div><label for=\"data-output2\">"+mainType[i].type_value+"</label></div>";
								}else{
									gsHtml += "<div class=\"item10\"><div class=\"g-radio\"><input type=\"radio\" name=\"data-output"+rt+"\" value=\""+mainType[i].maintype_idx+"\" id=\"zd3_"+i+rt+"\">" +
									"</div><label for=\"data-output2\">"+mainType[i].type_value+"</label></div>";
								}
							}
						}
						gsHtml += "</div>";
					}
				}
			}
		}
		$("#tmpMethod").append(gsHtml);
		$("input[id*=zd]").off();//绑定分组对应
		$("input[id*=zd]").on("click",function(){
			var index = $(this).val();
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
			if(writegArr.length>20){
				of = "style='height:80px; overflow:scroll'";
				checkAllorReverse = "<div class=\"col-4\"><div class=\"item10\">&nbsp;</div><div class=\"item10\">&nbsp;</div><div class=\"item10\">&nbsp;</div><div class=\"item10\">&nbsp;</div><div class=\"item10\"><div class=\"g-btn\" id=\"chooseAll_"+thisId+"\">全选</div></div>"
				+"<div class=\"item10\"><div id=\"noChooseAll_"+thisId+"\" class=\"g-btn\">反选</div></div><div class=\"item10\">&nbsp;</div><div class=\"item10\">&nbsp;</div><div class=\"item10\">&nbsp;</div><div class=\"item10\">&nbsp;</div></div>";
			}
			zdHtml += "<div id=\"result_"+thisId+"\" "+of+" class=\"senior divBorder\"><div class=\"col-4\">";
			if(writegArr.length!=0){
				for(var ta=0;ta<writegArr.length;ta++){
					var w = writegArr[ta].split("|");
					zdHtml += "<div class=\"item10\"><div class=\"g-checkbox\"><input type=\"checkbox\" name=\"\" value=\""+w[0]+"\" id=\"\">"
					+"</div> <label for=\"checkbox-format-1\">"+w[1]+"</label></div>";
					if((ta+1)%10==0&&ta!=0){
						zdHtml += "</div><div class=\"col-4\">";
					}
				}
				zdHtml += "</div>"+checkAllorReverse+"</div>";
			}else{
				
				zdHtml += "<class=\"item10\"><font color=\"red\">该信息是随图书馆变化的，暂不能显示</font></div></div></div>";
			}
			if($(this).parent().parent().parent().next().eq(0).is("[id*='result']")){
				$(this).parent().parent().parent().next().eq(0).remove();
			}
			$(this).parent().parent().parent().after(zdHtml);
			$("div[id^='chooseAll']").off();//全选
			$("div[id^='chooseAll']").on("click",function(){
				$(this).parent().parent().siblings().find("input[type='checkbox']").each(function(){
					if(!$(this).is(":checked")){
						$(this).prop("checked",true).parents(".g-checkbox").addClass("on").parents(".item").addClass("active");
					}
				});
			});
			$("div[id^='noChooseAll']").off();//反选
			$("div[id^='noChooseAll']").on("click",function(){
				$(this).parent().parent().siblings().find("input[type='checkbox']").each(function(){
					if(!$(this).parent().hasClass("on")){
						$(this).prop("checked",true).parents(".g-checkbox").addClass("on").parents(".item").addClass("active");
					}else{
						$(this).removeAttr("checked").parents(".g-checkbox").removeClass("on").parents(".item").removeClass("active");
					}
				});
			});
		});
		$("input[id*=\"zd\"]").each(function (){
			if($(this).is(":checked")||$(this).parent().hasClass("on")){
				$(this).click();//触发一次数据类型
			}
		});
		
		
		
//		showHtml = "";
//		t = 3;
//		sdLen = Math.ceil(sthreeArr.length/t);
//		for(var mt=1;mt<=sdLen;mt++){
//			if(mt<sdLen){
//				showHtml += "<div class=\"col-6 senior\">";
//			}else{
//				if(sthreeArr.length%t==0||sthreeArr.length%t==2){
//					showHtml += "<div class=\"col-6 senior\">";
//				}else if(sthreeArr.length%t==1&&doubleArr.length!=0){
//					showHtml += "<div class=\"col-4 senior\">";
//				}else{
//					showHtml += "<div class=\"col-6 senior\">";
//				}
//			}
//			for(var m=t*(mt-1);m<t*mt;m++){
//				if(mt<sdLen){
//					showHtml += sthreeArr[m];
//					if((m+1)%t==0){
//						showHtml += "</div>";
//					}
//					
//				}else{
//					if(mt==sdLen&&m<sthreeArr.length){
//						showHtml += sthreeArr[m];
//						if((m+1)%t==0){
//							showHtml += "</div>";
//						}
//					}
//				}
//			}
//		}
//		if(sthreeArr.length%t==1&&sdoubleArr.length!=0){
//			showHtml += sdoubleArr[0];
//			sdoubleArr.splice(0, 1);
//			showHtml += "</div>";
//		}
//		$("#tmpMethod").append(showHtml);
		
//		showHtml = "";
//		t = 2;
//		sdLen = Math.ceil(sdoubleArr.length/t);
//		for(var mt=1;mt<=sdLen;mt++){
//			showHtml += "<div class=\"col-4 senior\">";
//			for(var m=t*(mt-1);m<t*mt;m++){
//				if(mt<sdLen){
//					showHtml += sdoubleArr[m];
//					if((m+1)%t==0){
//						showHtml += "</div>";
//					}
//				}else{
//					if(mt==sdLen&&m<sdoubleArr.length){
//						showHtml += sdoubleArr[m];
//						if((m+1)%t==0){
//							showHtml += "</div>";
//						}
//					}
//				}
//			}
//		}
//		showHtml += "</div>";
//		$("#tmpMethod").append(showHtml);
		
//		showHtml = "";
//		t = 10;
//		var of = "";
//		var checkAllorReverse = "";
//		for(var t1=0;t1<stenArr.length;t1++){
//			checkAllorReverse = "";
//			of = "";
//			var cv = 0;
//			var tenStr = JSON.parse(stenArr[t1]);
//			var selectStr = "";
//			if(tenStr["sDefault"]==1){//默认选中时
//				selectStr = "class=\"on g-radio\"";
//			}else{
//				selectStr = "class=\"g-radio\"";
//			}
//			var sct = tenStr.data_source_select;//得到统计子类型
//			if(!(typeof(sct)=="undefined")){
//				var idx = sct.indexOf('|');
//				if (idx > 0)
//					sct = sct.toString().split('|')[0];
//			}
//			var tenStr = JSON.parse(stenArr[t1]);
//			var statisticsValStr = tenStr.statisticsVal.toString().replace("[","").replace("]","");
//			var statisticsValArr = statisticsValStr.split(",");
//			cv = cv+1;
//			if(statisticsValArr.length>29){
//				of = "style='height:80px; overflow:scroll'";
//				if(tenStr.checked_type==="checked_type_2"){
//					checkAllorReverse = "<div class=\"col-4\"><div class=\"item10\">&nbsp;</div><div class=\"item10\">&nbsp;</div><div class=\"item10\">&nbsp;</div><div class=\"item10\">&nbsp;</div><div class=\"item10\"><div class=\"g-btn\" id=\"chooseAll_"+t1+"\">全选</div></div>"
//					+"<div class=\"item10\"><div id=\"noChooseAll_"+t1+"\" class=\"g-btn\">反选</div></div><div class=\"item10\">&nbsp;</div><div class=\"item10\">&nbsp;</div><div class=\"item10\">&nbsp;</div><div class=\"item10\">&nbsp;</div></div>";
//				}
//			}
//			showHtml += "<div id=\"result_"+t1+"\" "+of+" class=\"senior\"><div class=\"col-4\">";
//			showHtml += "<div class=\"item10\"><div "+selectStr+"><input type=\"radio\" name=\"type_tongji\" id=\"type_tongji1\" value=\""+sct+"\"></div>"+tenStr.cAttrAlias+"</div>";
//			for(var ta=0;ta<statisticsValArr.length;ta++){
//				var sct = tenStr.data_source_select;//得到统计子类型
//				if(!(typeof(sct)=="undefined")){
//					var idx = sct.indexOf('|');
//					if (idx > 0)
//						sct = sct.toString().split('|')[1];
//				}
//				var tmpArr = statisticsValArr[ta].toString().replace("\"","").replace("\"","").split("|");
//				if(tenStr.checked_type==="checked_type"){//单选
//					var name=tmpArr[1];
//					if(tenStr.format_output == "format_output"){
//						name=tmpArr[0];
//					}
//					if(tmpArr[0] == tenStr.selectDefVal){
//						showHtml += "<div class=\"item10\"><div class=\"g-radio on\"><input type=\"radio\" name=\"data-output\" value=\""+tmpArr[0]+"\" id=\"\">" +
//						"</div> <label for=\"data-output2\">"+name+"</label></div>";
//					}else{
//						showHtml += "<div class=\"item10\"><div class=\"g-radio\"><input type=\"radio\" name=\"data-output\" value=\""+tmpArr[0]+"\" id=\"\">" +
//						"</div> <label for=\"data-output2\">"+name+"</label></div>";
//					}
//
//				}else if(tenStr.checked_type==="checked_type_2"){//复选
//					var name=tmpArr[1];
//					if(tenStr.format_output == "format_output"){
//						name=tmpArr[0];
//					}
//					var strs= new Array();
//					strs = tenStr.selectDefVal.split(",");
//					var t=0;
//					for(var i=0;i<strs.length;i++){
//						if(strs[i] == tmpArr[0]){
//							t=1;
//						}
//					}
//					if(t == 1){
//						showHtml += "<div class=\"item10\"><div class=\"g-checkbox on\"><input checked=\"checked\" type=\"checkbox\" name=\"\" value=\""+tmpArr[0]+"\" id=\"\">"
//						+"</div> <label for=\"checkbox-format-1\">"+name+"</label></div>";
//					}else{
//						showHtml += "<div class=\"item10\"><div class=\"g-checkbox\"><input type=\"checkbox\" name=\"\" value=\""+tmpArr[0]+"\" id=\"\">"
//						+"</div> <label for=\"checkbox-format-1\">"+name+"</label></div>";
//					}
//				}
//				cv = cv+1;
//				if(cv%10==0){
//					showHtml += "</div><div class=\"col-4\">";
//				}
//			}
//			showHtml += "</div>"+checkAllorReverse+"</div>";
//		}
//		$("#tmpMethod").append(showHtml);
		initDatepicker();//初始化时间控件
		$("select[id^='mulSelected']").select2();//初始化下拉多选列表
		$("#stmpMethod div[class*='senior']:gt(1)").hide();//查询方式的行数超过两行时，隐藏多余的行
//		var childrens = $("#tmpMethod").find("div[class*='senior']").length;
//		if(childrens>0){//统计方式有子元素时，显示统计方式
//			$("#gcolTmp").show();
//		}
//		$("#moreSearch div[class*='senior']:gt(2)").hide();//查询方式的行数超过两行时，隐藏多余的行
//		var schildrens = $("#stmpMethod").find("div[class*='senior']").length;
//		if(schildrens>=2){//当查询方式大于两行时，隐藏
//			$("#colTmp").hide();
//		}
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
						chartHtml += "<a href=\"javascript:;\" class=\"switch-label-hd\" id=\"0\">柱状图</a>";
						
					}else if(ct=="chart_2"){
						chartHtml += "<a href=\"javascript:;\" class=\"switch-label-hd\" id=\"1\">圆饼图</a>";
						
					}else if(ct=="chart_3"){
						chartHtml += "<a href=\"javascript:;\" class=\"switch-label-hd\" id=\"2\">曲线图</a>";
						
					}else if(ct=="chart_4"){
						chartHtml += "<a href=\"javascript:;\" class=\"switch-label-hd\" id=\"3\">Top排名</a>";
						
					}
				}
			}
		}else if(gLen>1){
			for(var h=0;h<gLen;h++){
				var charStr = JSON.parse(rtreeArr[h]);
				if(charStr["chartVal"].length>2){
					var cStr = charStr.chartVal.toString().replace("[","").replace("]","");
					if(cStr!=""){
						var cArr = cStr.split(",");
						for(var c=0;c<cArr.length;c++){
							var ct = cArr[c].replace(/"/ig,"");
							if((ct=="chart_1")&&(chartHtml.indexOf("<a href=\"javascript:;\" class=\"switch-label-hd\" id=\"0\">柱状图</a>")<0)){
								chartHtml += "<a href=\"javascript:;\" class=\"switch-label-hd\" id=\"0\">柱状图</a>";
								
							}else if((ct=="chart_2")&&(chartHtml.indexOf("<a href=\"javascript:;\" class=\"switch-label-hd\" id=\"1\">圆饼图</a>")<0)){
								chartHtml += "<a href=\"javascript:;\" class=\"switch-label-hd\" id=\"1\">圆饼图</a>";
								
							}else if((ct=="chart_3")&&(chartHtml.indexOf("<a href=\"javascript:;\" class=\"switch-label-hd\" id=\"2\">曲线图</a>")<0)){
								chartHtml += "<a href=\"javascript:;\" class=\"switch-label-hd\" id=\"2\">曲线图</a>";
								
							}else if((ct=="chart_4")&&(chartHtml.indexOf("<a href=\"javascript:;\" class=\"switch-label-hd\" id=\"3\">Top排名</a>")<0)){
								chartHtml += "<a href=\"javascript:;\" class=\"switch-label-hd\" id=\"3\">Top排名</a>";
								
							}
						}
					}
				}
			}
		}
		$("#gchart").append(chartHtml);
		initChart();//初始化图表
		$(".switch-label-panel").each(function(){
			$(this).hide();
		});
		var childrens = $("#gchart").children();
		if(childrens.length>0){
			childrens.eq(0).addClass("active");
			var cid = childrens.eq(0).attr("id");
			$(".switch-label-panel").eq(cid).show();
		}
		
	});
	
	function treeChild(len,arr){
		var tArr = JSON.parse(arr[len]);
		var t = tArr.rcSubCountType;//得到统计子类型
		var tc = "";//得到统计子类型的code
		if(!(typeof(t)=="undefined")){
			var idx = t.indexOf('|');
			if (idx > 0)
				tc = t.toString().split('|')[0];
				t = t.toString().split('|')[1];
		}
		if(len+1<arr.length){
			return '"children": ['+
			    '{'+
				    '"id": "'+tc+'",'+
				    '"text": "'+t+'",'+treeChild(len+1,arr)+
			    '}'+
			']';
		}
		else if(len+1==arr.length){
			return '"children": ['+
			    '{'+
			    '"id": "'+tc+'",'+
			    '"text": "'+t+'"'+
		    '}'+
		   ']';
		}
	}
	
	initDatepicker();//初始化时间控件
	function initDatepicker(){
		$(".datepicker").datepicker(
				{
					numberOfMonths : 1,//显示几个月  
					showButtonPanel : false,//是否显示按钮面板  
					dateFormat : 'yy-mm-dd',//日期格式  
					clearText : "清除",//清除日期的按钮名称  
					closeText : "关闭",//关闭选择框的按钮名称  
					yearSuffix : '', //年的后缀  
					showMonthAfterYear : true,//是否把月放在年的后面  
					defaultDate : '2016-04-19',//默认日期  
					//minDate:'2011-03-05',//最小日期  
					//maxDate:'2011-03-20',//最大日期  
					monthNames : [ '一月', '二月', '三月', '四月', '五月', '六月',
					               '七月', '八月', '九月', '十月', '十一月', '十二月' ],
					               dayNames : [ '星期日', '星期一', '星期二', '星期三', '星期四', '星期五',
					                            '星期六' ],
					                            dayNamesShort : [ '周日', '周一', '周二', '周三', '周四', '周五',
					                                              '周六' ],
					                                              dayNamesMin : [ '日', '一', '二', '三', '四', '五', '六' ],
					                                              onSelect : function(selectedDate) {//选择日期后执行的操作  
					                                            	  
					                                              }
				});
	}


	//js-box-aside 查询分组 二级切换
	$("#js-box-aside dt").on("click", function() {
		var dl = $(this).parent();
		dl.siblings().removeClass("active");
		dl.siblings().find("ul").hide();
		if (dl.hasClass("active")) {
			dl.removeClass("active");
			dl.find("ul").slideUp();
		} else {
			dl.addClass("active");
			dl.find("ul").slideDown();
		}
	});
	
	//高级查询
	$(".button-more-search").on("click",function(){
		var childrens = $("#tmpMethod").find("div[class*='senior']").length;
		if(childrens>0){//统计方式有子元素时，显示统计方式
			$("#colTmp").toggle();
			$("#gcolTmp").toggle();
		}
		if($(this).hasClass("button-more-search-active")){
//			$("#more-search input").val("");
		}
		$(this).toggleClass("button-more-search-active");
//		$("#more-search").toggle();
		
		$("#stmpMethod div[class*='senior']:gt(1)").toggle();
	});

	//重置
	$(".button-reset").on("click", function() {
		$(".search-template-md input").val("");
		$(".search-template-md select").each(function() {
			$(this).find("option").eq(0).prop("selected", true);
		})
		$(".search-template-md textarea").val("");
	});

	//查询
	$(".button-search").on("click", function() {
		/*示例用，及时删除*/
		$(".g-loading").fadeIn();
		setTimeout(function() {
			$(".g-loading").fadeOut();
		}, 1000);
	});

	var headerData = {};
	var postData = {};
	var postParmeter = {};
	//预览保存
	$(".save-template").on("click",function(){
		screenToData(headerFld, headerData);
		if(headerData!=null){
			if($.trim(headerData["templateName"])==null||$.trim(headerData["templateName"])==""){
				layer.alert("模板名称不能为空");
				return;
			}else if($.trim(headerData["templateID"])==null||$.trim(headerData["templateID"])==""){
				layer.alert("模板ID不能为空");
				return;
			}else if($.trim(headerData["dataSource"])==null||$.trim(headerData["dataSource"])==""){
				layer.alert("数据源不能为空");
				return;
			}
		}
		var checkbox_formatStr = "";
		$("input[id^=\"checkbox_format\"]").each(function (){//////
			if($(this).is(":checked")||$(this).parent().hasClass("on")){
				if($.trim(checkbox_formatStr).length>0){
					checkbox_formatStr = checkbox_formatStr + "," + $(this).val();
				}else{
					checkbox_formatStr = $(this).val();
				}
			}
		});
		headerData["checkbox_format"] = checkbox_formatStr;
		postParmeter["headerData"] = headerData;
		postParmeter["searchinfo"] = searchArray;
		console.info("=============================");
		console.info(searchArray);
		
		postParmeter["resultinfo"] = rsearchArray;
		postData["statistics_tpl_type"] = 2;
		postData["statistics_tpl_desc"] = headerData["templateName"];
		postData["statistics_tpl_id"] = headerData["templateID"];
		postData["statistics_tpl_value"] = postParmeter;
		var statistictplidx = $("#statistics_tpl_idx").val();
		var cr = $("#copyRecord").val();//复制的标识
		if(statistictplidx!=""&&cr!=1){//新增时，不触发
			postData["statistics_tpl_idx"] = parseInt(statistictplidx);
			$.ajax({
				url: "../statisticsTemplate/updateStatisticsTemplate",
				method: "POST",
				data: {"req":JSON.stringify(postData)},
				timeout:8000,
				error: function () 
				{
					
				},
				success: function (data, textStatus, jqXHR) 
				{
					if(data!=null&&data.retMessage!=null){
						if(data.retMessage.indexOf("MySQLIntegrityConstraintViolationException")>-1){
							if(data.retMessage.indexOf("for key 'statistics_tpl_desc'")>-1){
								layer.alert("模板名称重复");
								return;
							}
							if(data.retMessage.indexOf("for key 'statistics_tpl_id'")>-1){
								layer.alert("模板ID重复");
								return;
							}
						}
						success();
						returnMain();
					}
				}
			});
		}else{
			$.ajax({
				url: "../statisticsTemplate/addStatisticsTemplate",
				method: "POST",
				data: {"req":JSON.stringify(postData)},
				timeout:8000,
				error: function () 
				{
					
				},
				success: function (data, textStatus, jqXHR) 
				{
					if(data!=null&&data.retMessage!=null){
						if(data.retMessage.indexOf("MySQLIntegrityConstraintViolationException")>-1){
							if(data.retMessage.indexOf("for key 'statistics_tpl_desc'")>-1){
								layer.alert("模板名称重复");
								return;
							}
							if(data.retMessage.indexOf("for key 'statistics_tpl_id'")>-1){
								layer.alert("模板ID重复");
								return;
							}
						}
						success();
						returnMain();
					}
				}
			});
		}
	});
	$("#saveTemplate").off();
	$("#saveTemplate").on("click",function(){
		screenToData(headerFld, headerData);
		if(headerData!=null){
			if($.trim(headerData["templateName"])==null||$.trim(headerData["templateName"])==""){
				layer.alert("模板名称不能为空");
				return;
			}else if($.trim(headerData["templateID"])==null||$.trim(headerData["templateID"])==""){
				layer.alert("模板ID不能为空");
				return;
			}else if($.trim(headerData["dataSource"])==null||$.trim(headerData["dataSource"])==""){
				layer.alert("数据源不能为空");
				return;
			}else if($.trim(headerData["topHits"])!=null&&$.trim(headerData["topHits"])!=""){
				var topHits=headerData["topHits"];
				var reg = /^[0-9]*$/;
				if (!reg.test(topHits) || parseInt(topHits) <= 0) {
					layer.alert("统计结果数只能为整数");
					return;
				}
			}
		}
		var checkbox_formatStr = "";
		$("input[id^=\"checkbox_format\"]").each(function (){////////
			if($(this).is(":checked")||$(this).parent().hasClass("on")){
				if($.trim(checkbox_formatStr).length>0){
					checkbox_formatStr = checkbox_formatStr + "," + $(this).val();
				}else{
					checkbox_formatStr = $(this).val();
				}
			}
		});
		headerData["checkbox_format"] = checkbox_formatStr;
		postParmeter["headerData"] = headerData;
		postParmeter["searchinfo"] = searchArray;
		//console.info("=============================");
		//console.info(searchArray);
		
		postParmeter["resultinfo"] = rsearchArray;
		postData["statistics_tpl_type"] = 2;
		postData["statistics_tpl_desc"] = headerData["templateName"];
		postData["statistics_tpl_id"] = headerData["templateID"];
		postData["statistics_tpl_value"] = postParmeter;
		var statistictplidx = $("#statistics_tpl_idx").val();
		var cr = $("#copyRecord").val();//复制的标识
		if(statistictplidx!=""&&cr!=1){//新增时，不触发
			postData["statistics_tpl_idx"] = parseInt(statistictplidx);
			$.ajax({
				url: "../statisticsTemplate/updateStatisticsTemplate",
				method: "POST",
				data: {"req":JSON.stringify(postData)},
				timeout:8000,
				error: function () 
				{
					
				},
				success: function (data, textStatus, jqXHR) 
				{
					if(data!=null&&data.retMessage!=null){
						if(data.retMessage.indexOf("MySQLIntegrityConstraintViolationException")>-1){
							if(data.retMessage.indexOf("for key 'statistics_tpl_desc'")>-1){
								layer.alert("模板名称重复");
								return;
							}
							if(data.retMessage.indexOf("for key 'statistics_tpl_id'")>-1){
								layer.alert("模板ID重复");
								return;
							}
						}
						success();
						returnMain();
					}
				}
			});
		}else{
			$.ajax({
				url: "../statisticsTemplate/addStatisticsTemplate",
				method: "POST",
				data: {"req":JSON.stringify(postData)},
				timeout:8000,
				error: function () 
				{
					
				},
				success: function (data, textStatus, jqXHR) 
				{
					if(data!=null&&data.retMessage!=null){
						if(data.retMessage.indexOf("MySQLIntegrityConstraintViolationException")>-1){
							if(data.retMessage.indexOf("for key 'statistics_tpl_desc'")>-1){
								layer.alert("模板名称重复");
								return;
							}
							if(data.retMessage.indexOf("for key 'statistics_tpl_id'")>-1){
								layer.alert("模板ID重复");
								return;
							}
						}
						success();
						returnMain();
					}
				}
			});
		}
	});

});

function initChart(){
	//配置图表容器长宽
	$(".chart-md").css({
		width : 1160,
		height : 300
	});

	// 基于准备好的dom，初始化echarts实例
	var chart_1 = echarts.init(document.getElementById('chart-1'));
	var chart_2 = echarts.init(document.getElementById('chart-2'));
	var chart_3 = echarts.init(document.getElementById('chart-3'));
    var chart_4 = echarts.init(document.getElementById('chart-4'));

//	// 指定图表的配置项和数据
	var option_1 = {
		legend : {
			data : [ '24小时自助设备', '借书机:大厅A', '还书机:深图门口B' ],
			left : 20,
			itemGap : 30
		},
		tooltip : {
			trigger : 'axis'
		},
		xAxis : {
			data : [ "01", "02", "03", "04", "05", "06", "07", "08",
					"09", "10", "11", "12", "13", "14", "15" ]
		},
		yAxis : {
			splitLine : {
				show : false
			}
		},
		series : [
				{
					name : '24小时自助设备',
					type : 'bar',
					data : [ 5, 20, 36, 10, 10, 20, 5, 20, 36, 10, 10,
							20, 5, 40, 60 ],
					itemStyle : {
						normal : {
							color : "#008BD0"
						}
					}
				},
				{
					name : '借书机:大厅A',
					type : 'bar',
					data : [ 20, 36, 10, 10, 20, 5, 20, 36, 10, 10, 20,
							5, 40, 50, 60 ],
					itemStyle : {
						normal : {
							color : "#50e3c2"
						}
					}
				},
				{
					name : '还书机:深图门口B',
					type : 'bar',
					data : [ 5, 36, 10, 10, 20, 5, 20, 36, 10, 10, 20,
							5, 40, 55, 60 ],
					itemStyle : {
						normal : {
							color : "#fd5f72"
						}
					}
				} ]
	};
	var option_2 = {
		    tooltip : {
		        trigger: 'item',
		        formatter: "{b} : {c} ({d}%)"
		    },
		    legend: {
		        data:['24小时自助设备','借书机:大厅A','还书机:深图门口B'],
		        left: 20,
		        itemGap : 30
		    },
		    color:['#008BD0','#50e3c2','#fd5f72'],
		    series : [
		        {
		            type: 'pie',
		            radius : '55%',
		            center: ['50%', '60%'],
		            data:[
			 			{value:335, name:'24小时自助设备'},
						{value:310, name:'借书机:大厅A'},
		        		{value:1548, name:'还书机:深图门口B'}
		            ]
		        }
		    ]
	    };
	var option_3 = {
			legend : {
				data : [ '24小时自助设备', '借书机:大厅A', '还书机:深图门口B' ],
				left : 20,
				itemGap : 30
			},
			tooltip : {
				trigger : 'axis'
			},
			xAxis : {
				data : [ "01", "02", "03", "04", "05", "06", "07", "08",
						"09", "10", "11", "12", "13", "14", "15" ]
			},
			yAxis : {
				splitLine : {
					show : false
				}
			},
			series : [
					{
						name : '24小时自助设备',
						type : 'line',
						data : [ 5, 20, 36, 10, 10, 20, 5, 20, 36, 10, 10,
								20, 5, 40, 60 ],
						itemStyle : {
							normal : {
								color : "#008BD0"
							}
						}
					},
					{
						name : '借书机:大厅A',
						type : 'line',
						data : [ 20, 36, 10, 10, 20, 5, 20, 36, 10, 10, 20,
								5, 40, 50, 60 ],
						itemStyle : {
							normal : {
								color : "#50e3c2"
							}
						}
					},
					{
						name : '还书机:深图门口B',
						type : 'line',
						data : [ 5, 36, 10, 10, 20, 5, 20, 36, 10, 10, 20,
								5, 40, 55, 60 ],
						itemStyle : {
							normal : {
								color : "#fd5f72"
							}
						}
					} ]
		};

	    var option_4 = {
	        legend: {
	            data:['24小时自助设备','借书机:大厅A','还书机:深图门口B'],
	            left: 20,
	            itemGap : 30
	        },
	        tooltip: {
		        trigger: 'axis'
		    },
	        xAxis: {
	            data: ["01","02","03","04","05"]
	        },
	        yAxis: {
	        	splitLine: {
	                show: false
	            }
	        },
	        series: [{
	            name: '24小时自助设备',
	            type: 'bar',
	            data: [55, 45, 36, 20, 10],
	            itemStyle : {
	            	normal : {
	            		color : "#008BD0"
	            	}
	            }
	        },
	        {
	            name: '借书机:大厅A',
	            type: 'bar',
	            data: [60, 50, 40, 20, 10],
	            itemStyle : {
	            	normal : {
	            		color : "#50e3c2"
	            	}
	            }
	        },
	        {
	            name: '还书机:深图门口B',
	            type: 'bar',
	            data: [60, 50, 40, 20, 10],
	            itemStyle : {
	            	normal : {
	            		color : "#fd5f72"
	            	}
	            }
	        }
	        ]
	    };

	// 使用刚指定的配置项和数据显示图表。
	chart_1.setOption(option_1);
	chart_2.setOption(option_2);
	chart_3.setOption(option_3);
    chart_4.setOption(option_4);
    $(window).resize(function() {
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
	});
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

//function delInitAttr(index){
//	groupArr.splice(index-1,1);//删除结果模块行时，对应删除分组数组
//	var delval = "#del_"+index;
//	$(delval).parent().parent().remove();
//}

function success(){
	var thisDom = $(".save-field");
	showMsg({
		timeout : 1000,
		showText : '添加字段成功',
		status : true,
		callback : function() {
			/*应该是成功的时候收回吧，酌情处理*/
			var thisRight = thisDom.parents(
					".form-dialog-fix").find(
					".form-dialog").attr("date-right");

			thisDom.parents(".form-dialog-fix").find(
					".form-dialog").animate(
					{
						"right" : thisRight
					},
					function() {
						thisDom.parents(
								".form-dialog-fix")
								.fadeOut(100);
					});
		}

	});
}


function showMsg(option) {
	var defaults = {
		timeout : option.timeout || 1000,
		showText : option.showText || '添加字段成功',
		backgroundColor : option.status === true ? "#2ab65b"
				: "#ff3d3d",
		callback : function() {

		}
	};
	defaults = $.extend(defaults, option);
	layer.msg(defaults.showText, {
		area : [ "240px" ],
		offset : [ "110px" ],
		time : defaults.timeout,
		success : function(layero, index) {
			$(".layui-layer-hui").css("background-color",
					defaults.backgroundColor);
		}
	}, function() {
		defaults.callback();
	});
}

function expand(){
	$("#initAttr").find("thead").nextAll().slideToggle("fast");
}
//模板取消
$("#templateCancel").click(function (){
	returnMain(-2);
});

function returnMain(type){//返回主界面
    var currentpage = $("#currentpage").val();
    var endpage = $("#endpage").val();
    if(type != -2){
        if(endpage != null && endpage.length>0){
            currentpage=endpage;
        }
    }
	$("#mainframe").load("../templatemanagement/main?currentpage="+currentpage,"",function(){
		$(".form-dialog-fix .form-dialog").each(function(){
			$(this).attr("date-right",$(this).css("right"));
		});
		
	});
}

function initAllSelect(allType,mainFun,subFun){
	//添加结果字段时，填充子类型统计
	$("#rcSubCountType").html("");
	var dsVal = $("#dataSource option:selected").val();
	var subCountHtml = "<option value=\"\" selected=\"\">请选择结果数据源</option>";
	if(dsVal!=""&&dsVal!=null){
		var subArr = allType[dsVal].split("||");
		var subCountCode = subArr[0].split("#");
		var subCountVal = subArr[1].split("#");
		for(var c=0,v=0;c<subCountCode.length,v<subCountVal.length;c++,v++){
            if(subCountVal[v].length > 15){
                subCountHtml += "<option title='"+subCountVal[v]+"' value=\""+subCountCode[c]+'|'+subCountVal[v]+"\">"+subCountVal[v].substring(0,15)+"</option>";
            }else{
                subCountHtml += "<option title='"+subCountVal[v]+"' value=\""+subCountCode[c]+'|'+subCountVal[v]+"\">"+subCountVal[v]+"</option>";
            }
			//subCountHtml += "<option value=\""+subCountCode[c]+'|'+subCountVal[v]+"\">"+subCountVal[v]+"</option>";
		}
	}
	$("#rcSubCountType").append(subCountHtml);
	//添加结果字段时，填充子类型统计
	$("#mainFun").html("");
	var mainFunHtml = "<option value=\"\" selected=\"\">请选择函数处理</option>";
	for(var mfh=1;mfh<mainFun.length;mfh++){
        if(typeof(mainFun[mfh]) != "undefined"){
            mainFunHtml += "<option value=\""+mainFun[mfh].main_idx+"\">"+mainFun[mfh].main_desc+"</option>";
        }
	}
	$("#mainFun").append(mainFunHtml);
	
	var subTmpMap = null;//子函数说明数组
	$("#mainFun").off();//绑定函数处理
	$("#mainFun").on("change",function(){
		subTmpMap = {};
		$("#subFun").html("");
		var subFunHtml = "<option value=\"\" selected=\"\">请选择函数类型</option>";
		for(var h=0;h<subFun.length;h++){
			var mfk = $(this).val();
			if(mfk==subFun[h].main_idx){
				subFunHtml += "<option value=\""+subFun[h].sub_value+"\">"+subFun[h].sub_value+"</option>";
				subTmpMap[subFun[h].sub_value]=subFun[h].sub_desc;
			}
		}
		$("#subFun").append(subFunHtml);
		$("#subFunDesc").val("");
		$("#subFunVal").val("");
	});
	
	$("#subFun").off();//绑定子函数处理
	$("#subFun").on("change",function(){
		var mapK = $(this).val();
		$("#subFunDesc").val(subTmpMap[mapK]);
		$("#subFunVal").val("");
	});
}

function initChartAndType(rsSaveOrEdit,rsearchArray){
	$("input[id*=\"dtRadio\"]").each(function (){//触发一次数据类型,并把数据类型的下拉框赋值
		if($(this).is(":checked")||$(this).parent().hasClass("on")){
			$(this).click();
			$("#dataType").val(JSON.parse(rsearchArray[rsSaveOrEdit]).dataType);
		}
	});
	//对于特殊的statisticsVal、chartVal进行特殊处理
	var rsJson = JSON.parse(rsearchArray[rsSaveOrEdit]);
	var chartValStr = rsJson.chartVal.toString().replace("[","").replace("]","");
	var chartValArr = chartValStr.split(",");
	if(rsJson["rGroup"]==1){
		$("div[name='report_style']").show();//显示统计样式
		$("#dataTypeTr").show();//显示数据类型
	}
	$("input[id^='chart']").each(function (){
		$(this).parent().removeClass("on");
		$(this).attr("checked",false);
		for(var csv in chartValArr){
			if($(this).val()==chartValArr[csv].toString().replace("\"","").replace("\"","")){
				$(this).parent().addClass("on");
			}
		}
	});
}