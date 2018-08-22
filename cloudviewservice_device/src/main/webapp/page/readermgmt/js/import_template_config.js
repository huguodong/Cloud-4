//statisticsVal 统计类型
var searchFld = new Array(
//		    "cAttrSort", //字段排序
		    "cOptionType",//字段类型
		    "dataFilter",//校验选项
		    "columnRank",
		    "data_source_select" //搜索数据源
/*//		    单行文本
		    "textDefalutVal",//默认值
		    "textCount",//个数
//		    多行文本
//		    "mulTextW",//文本域宽度
		    "MulTextDefVal",//默认值
*/		);
//chartVal 统计类型图形表
var resultFld = new Array(
	    "rcSubCountType",//结果数据源
	    "rcAttrSort", //字段排序
	    "rFieldVal", //字段值
	    "dataType", //数据类型
	    "dtRadio", //数据类型默认值
	    "subFunVal" //选择类型值
);

var headerFld = new Array(
		"templateName",//模板名称
		"lib_id", //图书馆id
		"templateID", //模板ID
		"dataSource", //数据源
		/*"data_output"*/ //数据导出
//		"checkbox_format" //导出格式
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
	
	var debugFlag = 1;
	
	$.ajax({
		url:"../ascconfig/querylibInfoByCurUser",
		type:"GET",
		data:{}
	 }).then(function(data){
		if(data){
			//console.info("获取到图书馆：",data);		
			if(data.result){
				for(var i=0;i<data.result.length;i++){
					var option = "<option libIdx="+data.result[i].library_idx+" value="+data.result[i].lib_id+">"+data.result[i].lib_name+"</option>";
					$("#lib_id").append(option);
				}
				var operator_type = $("#operator_type").val();
				
				//如果不是云平台用户，lib_option都选定，且不可编辑
				if(operator_type!="1"){
					var library_id = $("#library_id").val();
					$("#lib_id option").each(function() {
					    if($(this).val() == library_id) {
					    	$(this).attr('selected', true); 
					    	$("#lib_id").attr("disabled","disabled");
					    }
					})
				}
				var import_tpl_idx = $("#import_tpl_idx").val();
				//云平台用户非新增时，lib_option选定且不可编辑
				if(import_tpl_idx!=""){//云平台用户非新增时，触发
					//如果不是云平台用户
					if(operator_type=="1"){
						var library_id = $("#library_id").val();
						$("#lib_id option").each(function() {
						    if($(this).val() == library_id) {
						    	$(this).attr('selected', true); 
						    	$("#lib_id").attr("disabled","disabled");
						    }
						})
					}
				}
			}
		}
	 });
	
	$("#dataFilter").off();//解绑数据校验
	$("#dataFilter").on("change",function (){//绑定数据校验
		$("#viewTr").html("");
	});
	
	//绑定分组的单选框事件
	$("input[name='isteam']").off();
	$("input[name='isteam']").on("click",function (){
		if($.isEmptyObject($("#rcSubCountType").val())){
			layer.alert("结果数据源不能为空");
			return;
		}
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
		$("input[id*=\"dtRadio\"]").each(function(){
			if($(this).is(":checked")||$(this).parent().hasClass("on")){
				$(this).click();//触发一次数据类型
			}
		});
	});
	
	var allType = {};//获取统计类型
	var oldval,newval;//数据源的新值和旧值
	//填充统计数据源
   $.ajax({
       url: '../configField/takeDataSource',
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
       	   var aliasVal = {"student_config":"读者导入配置","faculty_config":"教工导入配置","location_config":"馆藏导入配置","bookitem_config":"书目导入配置"};//map格式存储数据源
       	   //JsonResult = JSON.stringify(JsonResult);
       	   if(JsonResult!=null){
       		  for(var i=0;i<JsonResult.length;i++){
       			  //JsonResult[i] = JSON.stringify(JsonResult[i]);
       			   allType[JsonResult[i].config_table] = JsonResult[i].field+"||"+JsonResult[i].field_desc;
       			   dataSourceHtml += "<option value=\""+JsonResult[i].config_table+"\">"+aliasVal[JsonResult[i].config_table]+"</option>";
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
	       	var import_tpl_type = $("#import_tpl_type").val();
	        //console.info("import_tpl_type:--------------",import_tpl_type);
	        if(import_tpl_type==1){
	        	$("#dataSource").find("option[value='student_config']").prop('selected', true); 
	        }else if(import_tpl_type==2){
	        	$("#dataSource option[value='faculty_config']").attr('selected', true); 
	        }else if(import_tpl_type==3){
	        	$("#dataSource option[value='bookitem_config']").attr('selected', true);
	        }else if(import_tpl_type==4){
	        	$("#dataSource option[value='bookitem_config']").attr('selected', true);
	        	//$("#dataSource option[value='location_config']").attr('selected', true); 
	        }
	        $("#dataSource").prop("disabled",true);
          }
      }
    });
    
    var mainType = new Array();//获取统计主类型实体
 	
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
	
	 var staticsType = new Array();//获取统计主类型实体
     
     var mainFun = new Array();//获取统计主函数实体
      
     var subFun = new Array();//获取统计子函数实体
      
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
		//console.info("searchFld:",searchFld);
		dataToScreen(searchFld, JSON.stringify(searchFld));//新增统计查询设置时，将页面初始化
		//console.info("列数：",$("#columnRank").val());
		//如果列名不为空说明是新增则加1
		var count = $("#columnRank").val();
		if(!count){
			var trCount = $("#searchTable tbody").children().length;
			$("#columnRank").val(++trCount);
			$("#columnRank").attr("disabled","disabled");
			$("#columnRank").css("background-color","#C0C0C0");
		}
		var field = $(".panel-title li.active").data("field");
		if (field == "add-field-search") {
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
				}
			}
			$("#data_source_select").append(subCountHtml);
			$("#data_source_select").css("display","block");
		} 
	});
	
	var searchArray = new Array();//存储统计搜索字段的数组
	var rsearchArray = new Array();//存储统计结果字段的数组
	var import_tpl_idx = $("#import_tpl_idx").val();
	var copyRecord = $("#copyRecord").val();//复制的标识
	if(import_tpl_idx!=""){//新增时，不触发
		   $("#dataSource").attr("style","color: gray;background-color: #E0E0E0");
		   $("#dataSource").attr("disabled","disabled");
		   var editData = {};
		   editData["import_tpl_idx"] = import_tpl_idx;
		   $.ajax({
			   url:"../reader/findImportTemplateById",
			   type:"POST",
			   data:{"req":JSON.stringify(editData)},
			   success:function(data){
				   if(copyRecord==1){
					   $("#title").html("复制统计模板");
				   }else{
					   $("#title").html("编辑统计模板");
				   }
				   var editData = JSON.parse(data.result.import_tpl_value);
				   //传入的参数为json对象
				   dataToScreen(headerFld,editData.headerData);
				   //如果不是云平台用户
				   if(operator_type!="1"){
					    //选好的图书馆设置不可编辑
						$("#lib_id").attr("disabled","disabled");
					}
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
				   //获取的数据为json格式
				   for(var t1=0;t1<tsearchArray.length;t1++){
					   //searchArray.push(tsearchArray[t1].toString().replace(/^"|"$/g, ""));
					   searchArray.push(tsearchArray[t1]);
				   }
				   drawSearch();
				   /*var trsearchArray = jQuery.makeArray(editData.resultinfo);
				   for(var t2=0;t2<trsearchArray.length;t2++){
					   rsearchArray.push(trsearchArray[t2].toString().replace(/^"|"$/g, ""));
				   }
				   rdrawSearch();*/
			   }
		   });
	   }
	   
	function drawSearch(){//填充统计查询属性
		//往字段显示列表里加入数据
		//$("#searchTable").find("thead").nextAll().remove();
		var html = "";
		var sIndex = 1;
		for(var s=0;s<searchArray.length;s++){
			var sa = searchArray[s];
			var content = JSON.stringify(searchArray[s]);  //将json对象转为字符串
			//console.info("content",content);
			var tem = JSON.stringify(searchArray[s]).replace(/"/g, "'");   //将字符串中的符号替换
			if(content.length>40){
				content = content.substr(0,40)+"......}";
			}
			//debugger;
			var cloumnName = sa["data_source_select"].trim("\"").split(" ")[1];
			html += "<tr><td>"+sIndex+"</td><td>"+sIndex+"</td><td>"+cloumnName+"</td><td><p title=\""+tem+"\">"+content+"</p></td><td><input id=\"search_"+s+"\" type=\"hidden\" value=\""+s+"\"><span class=\"button button-edit-search-md\">编辑</span><span class=\"button button-delete sdelete\">删除</span></td></tr>";
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
			//console.info("mainTypeHtml:",mainTypeHtml);
			$("#mainType").html(mainTypeHtml);
			//console.info("searchFld",searchFld);
			//console.info("searchArray[sSaveOrEdit]",searchArray[sSaveOrEdit]);
			var indexColumn = $(this).prev().val();
			dataToScreen(searchFld, searchArray[sSaveOrEdit],indexColumn);
			$("#columnRank").attr("disabled","disabled");
			$("#columnRank").css("background-color","#C0C0C0");
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
				//得到满足列数大于选定index行的所有列数，将所有的行数值减1
				var timo = searchArray.filter((p) => {
					//console.info("p.columnRank:",p.columnRank);
					//console.info("delIndex:",delIndex);
				    return parseInt(p.columnRank) > parseInt(delIndex);
				});
				if(debugFlag==1)
					console.info("满足column数大于index条件的所有值:",timo);
				//修改column数的值
				for(var j=0;j<timo.length;j++){
					var columnRankInt= parseInt(timo[j].columnRank)-1;
					timo[j].columnRank = columnRankInt + "" ;
				}
				
				layer.close(delLayer);
				drawSearch();//调用一次本身
			}
		});
	}
	
	
	function pad(num) {//不足的补0
		if ((num + "").length >= 5) 
			return num;   
		return pad("0" + num, 5);   
	} 
	//弹出窗保存
	var sscv = -1;//默认值的判断标识
	$(".save-field").on("click",function() {
		var searchData = {};//统计搜索转json
		var sflag = true;//查询时判断统计类型是否存在默认值
		if ($(this).hasClass("save-dialog-search")) { //保存查询post
			screenToData(searchFld, searchData);
			if(searchData!=null){
				if($.trim(searchData["data_source_select"])==null||$.trim(searchData["data_source_select"])==""){
					layer.alert("数据源不能为空");
					return;
				}
			}
			if(searchData.rstatistics===""){
				var sgroupArr = new Array();
				var newS = searchData.selectList.replace(/(\s+)/g,";");
				var sgroupArr = newS.split(";");
			}else{
			}
			if(sSaveOrEdit!=-1){//编辑
				searchArray[sSaveOrEdit] = searchData;
			}else{//新增
				searchArray.push(searchData);
			}
			//console.info("-----searchArray:",searchArray);
			drawSearch();
			success();
		}
	});

	//预览 js
	//预览页面js
	
	
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
			}else if($.trim(headerData["lib_id"])==null||$.trim(headerData["lib_id"])==""){
				layer.alert("图书馆不能为空");
				return;
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
		postParmeter["resultinfo"] = rsearchArray;
		postData["import_tpl_type"] = $("#import_tpl_type").val();
		var IdAndNameArr = [];
		var libIdAndName =headerData["lib_id"];
		libIdAndName= libIdAndName.split(" ");
		postData["lib_id"]=libIdAndName[0];
		postData["lib_name"]=libIdAndName[1];
		postData["library_idx"]=libIdAndName[2];
		postData["import_tpl_desc"] = headerData["templateName"];
		postData["import_tpl_id"] = headerData["templateID"];
		postData["import_tpl_value"] = postParmeter;
		var importtplidx = $("#import_tpl_idx").val();
		var cr = $("#copyRecord").val();//复制的标识
		if(importtplidx!=""&&cr!=1){//新增时，不触发
			//alert("编辑....");
			postData["import_tpl_idx"] = parseInt(importtplidx);
			$.ajax({
				url: "../configField/updateImportTemplate",
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
							if(data.retMessage.indexOf("for key 'import_tpl_desc'")>-1){
								layer.alert("模板名称重复");
								return;
							}
							if(data.retMessage.indexOf("for key 'import_tpl_desc'")>-1){
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
			//alert("新增....");
			$.ajax({
				url: "../configField/addConfigTemplate",
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
							if(data.retMessage.indexOf("for key 'import_tpl_desc'")>-1){
								layer.alert("模板名称重复");
								return;
							}
							if(data.retMessage.indexOf("for key 'import_tpl_id'")>-1){
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
    var import_tpl_type = $("#import_tpl_type").val();
    //alert(currentpage + " " +endpage + " " + type);
    if(type != -2){
        if(endpage != null && endpage.length>0){
            currentpage=endpage;
        }
    }
    if(import_tpl_type==1||import_tpl_type==2){
    	$("#mainframe").load("../reader/main?currentpage="+currentpage,"",function(){
    		$(".form-dialog-fix .form-dialog").each(function(){
    			$(this).attr("date-right",$(this).css("right"));
    		});
    		
    	});
    }else{
    	$("#mainframe").load("../bookbiblios/main?currentpage="+currentpage,"",function(){
    		$(".form-dialog-fix .form-dialog").each(function(){
    			$(this).attr("date-right",$(this).css("right"));
    		});
    		
    	});
    }
	
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
}