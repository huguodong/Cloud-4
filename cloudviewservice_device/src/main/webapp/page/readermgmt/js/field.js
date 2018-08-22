$(function(){	
	var mainFieldList = {};// 获取统计类型
  	// 填充统计数据源
  	$.ajax({
		url : '../configField/selectConfigFieldList',
		method : 'POST',
		data : {"req":"{}"},
		async:false,
		error : function() {
		},
		success : function(data) {
			var JsonResult = {};//获取统计类型
			JsonResult['cardissue_log'] = "device_id"+"||"+"设备ID号";//JsonResult[i].field+"||"+JsonResult[i].field_desc;
			JsonResult['bookitem'] = "device_type"+"||"+"设备类型IDX";
			var JsonResult = data.result;
			console.info("JsonResult:",JsonResult);
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
  	
  	function getValue(midx){
  		var pData = {};
  		var res;
  		pData["maintype_idx"] = midx;
  		 $.ajax({
  				url: "../statisticsTemplate/selectBySql",
  				method: "POST",
  				async : false,
  				data: {"req":JSON.stringify(pData)},
  				error: function () 
  				{
  					
  				},
  				success: function (data){
  					res = data.result;
  				}
  			});
  		 
  		 return res;
  	}
  	//点击选择分隔符类型
  	$("#dataFilter").on("change",function (){
  		alert("bbbb");
  		countFlg=1;
  		$("#viewTr").html("");
  		$("#data_source_select option:first").prop("selected", 'selected');
  	});
  	
  	//点击选择字段类型：单列或者多列
  	$("#cOptionType").on("change",function (){
  		//$("#viewTr").html("");
  		$("#data_source_select option:first").prop("selected", 'selected');
		$("#dataFilter option:first").prop("selected", 'selected');
  	});
  	
  	//点击选择字段
  	$("#data_source_select").on("change",function (){
  		if($(this).find("option:selected").attr("title")){
  			//alert($(this).find("option:selected").text());
  			var txtVal = $(this).find("option:selected").text();  //职位
  			var dataVal = $(this).find("option:selected").val();   //occupation|职位
  			
  			var bodyEle = $("#dataFilter").parent().parent().parent().parent();
  			//如果分割符属于隐藏状态则不需要添加分割符到预览，并且先清除后再添加
			if(!bodyEle.is(":hidden")){
				var filter = $("#dataFilter").find("option:selected").val();  //pound
				var filterTxt = $("#dataFilter").find("option:selected").text(); //#
	  			//判断分隔符是否选择，如果选择了添加到字段预览，如果没有则提示
	  			if(filter){
	  					console.info("count:",$("#viewTr td").size());
	  					if($("#viewTr td").size()==0){
	  						//$("#viewTr").html(""); //第一次点击清空
	  						var $tdName = $("<td></td>"); 
		  					$tdName.html("<input name='"+dataVal+"' value='"+txtVal+"'  disabled='disabled' />");
		  					$("#viewTr").append($tdName);
	  					}else{
	  						var $tdName = $("<td>"+filterTxt+"</td>");
	  						$("#viewTr").append($tdName);
	  						$tdName = $("<td></td>"); 
		  					$tdName.html("<input name='"+dataVal+"' value='"+txtVal+"' disabled='disabled'/>");
		  					$("#viewTr").append($tdName);
	  					}
	  			}else{
	  				countFlg=1;
	  				layer.alert("请选择分隔符");
	  				return;
	  			}
			}else{
					//如果分割符未隐藏则需要添加分割符到预览字段，并且不要清除直接添加
					//alert("隐藏");
					$("#viewTr").html("");
					var $tdName = $("<td></td>"); 
  					$tdName.html("<input name='"+dataVal+"' value='"+txtVal+"' disabled='disabled'/>");
  					$("#viewTr").append($tdName);
			}
  		}
  	});
	var textHtml = '';
	//点击多文本时，第一次
	$("#data-source-dom .g-select-field-multiple").each(function(){ //字段类型第一次执行
		/*$(this).parents(".table-form").next().html( //获取第一次
				textHtml
		);*/
	})

	//点击单行字段或者多列字段
	$("#data-source-dom").on("change",'.g-select-field-multiple',function(){
		var val = $(this).val();
		var html = "";
		var va="";
		$(this).parents(".table-form").next().empty();
		$("#viewTr").html("");
		if( val == "text" ){ //单行文本
			html = textHtml;
			
			//选择单行文本则隐藏分割符，并将分隔符选择未选择状态
			$("#dataFilter").parent().parent().parent().parent().hide();
			$("#dataFilter option:first").prop("selected", 'selected');
		}else if(val == "multiple-text"){//多行文本
			//显示分割符
			$("#dataFilter").parent().parent().parent().parent().show();
			
		}
	    
	    $("#selectList").val(va);
	});

	
	$("#data-source-dom").on("change",'.g-select-field-type',function(){  //整数匹配
		var val = $(this).val();
		if(val != "varchar"){
			$(this).parent().next().show();
		}else{
			$(this).parent().next().hide();
		}
	});
	$("#data-source-dom").on("change",'.g-reg-filter',function(){ //正则匹配
		var val = $(this).val();
		$(this).parents("td").find("input").val(val);
	})
})