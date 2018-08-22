$(function(){	
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
			//console.log(mainFieldList);
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
  	$("#data_source_select").on("change",function (){
  		$("#cOptionType").val("text").change();
//  		$("#data-source-dom").change;
  	});
	var textHtml = '<table width="100%">'+
		'<tr class="border">'+
		'<th class="text-center" width="110">相关参数</th>'+
		'<td colspan="3" class="td-table">'+
			'<table width="100%">'+
				'<tbody>'+
//					'<tr>'+
//						'<th>文本框长度</th>'+
//						'<td>'+
//							'<input type="text" name="" id="textLen" class="g-input" placeholder="文本框长度">'+
//						'</td>'+
//					'</tr>'+
					'<tr>'+
						'<th>默认值</th>'+
						'<td>'+
							'<input type="text" name="" id="textDefalutVal" class="g-input" placeholder="默认值">'+
						'</td>'+
					'</tr>'+
					'<tr>'+
						'<th>个数</th>'+
						'<td>'+
							'<div class="g-radio">'+
								'<input type="radio" name="checked-type" id="textCount" value="1">'+
							'</div>'+
							'<label for="checked-type-1" class="ml5 label-type mr10">1个</label>'+
	
							'<div class="g-radio">'+
								'<input type="radio" name="checked-type" id="textCount_2" value="2">'+
							'</div>'+
							'<label for="checked-type-2" class="ml5 label-type mr10">2个</label>'+
						'</td>'+
					'</tr>'+
				'</tbody>'+
			'</table>'+
		'</td>'+
	'</tr>'+
	'</table>';
	
	$("#data-source-dom .g-select-field-multiple").each(function(){ //字段类型第一次执行
//		debugger;
		$(this).parents(".table-form").next().html( //获取第一次
				textHtml
		);
	})

	$("#data-source-dom").on("change",'.g-select-field-multiple',function(){
		var val = $(this).val();
		var html = "";
		var va="";
		$(this).parents(".table-form").next().empty();
		if( val == "text" ){ //单行文本
			html = textHtml;
			$(".tmpMainType").hide();//隐藏统计类型
			$("#strLenId").show();//显示字符长度取值范围
		}else if(val == "multiple-text"){//多行文本
			html = 
			'<table width="100%">' +
				'<tr class="border">' +
					'<th class="text-center" width="110">相关参数</th>' +
					'<td colspan="3" class="td-table">' +
						'<table width="100%">' +
							'<tbody>' +
//								'<tr>' +
//									'<th>文本域宽度</th>' +
//									'<td>' +
//										'<input type="text" name="" id="mulTextW" class="g-input inline-block" placeholder="文本域宽度" /> %' +
//									'</td>' +
//								'</tr>' +
								'<tr>' +
									'<th>默认值</th>' +
									'<td>' +
										'<textarea class="g-textarea" id="MulTextDefVal"></textarea>' +
									'</td>' +
								'</tr>' +
							'</tbody>' +
						'</table>' +
					'</td>' +
				'</tr>' +
			'</table>';
			$(".tmpMainType").hide();//隐藏统计类型
			$("#strLenId").show();//显示字符长度取值范围
		}else if(val == "select"){//选项
	    	html = 
	    	'<table width="100%">'+
				'<tr class="border">'+
					'<th class="text-center" width="110">相关参数</th>'+
					'<td colspan="3" class="td-table">'+
						'<table width="100%">'+
							'<tbody>'+
								'<tr>'+
									'<th width="85">选项列表</th>'+
									'<td><textarea class="g-textarea" id="selectList"></textarea></td>'+
								'</tr>'+
								'<tr>'+
									'<th>选项类型</th>'+
									'<td>'+
										'<div class="g-radio">'+
											'<input type="radio" name="checked-type" id="checked_type" value="checked_type">'+
										'</div>'+
										'<label for="checked-type-1" class="ml5 label-type mr10">单选按钮</label>'+

										'<div class="g-radio">'+
											'<input type="radio" name="checked-type" id="checked_type_2" value="checked_type_2">'+
										'</div>'+
										'<label for="checked-type-2" class="ml5 label-type mr10">复选框</label>'+

										'<div class="g-radio">'+
											'<input type="radio" name="checked-type" id="checked_type_3" value="checked_type_3">'+
										'</div>'+
										'<label for="checked-type-3" class="ml5 label-type mr10">下拉框</label>'+

										'<div class="g-radio">'+
											'<input type="radio" name="checked-type" id="checked_type_4" value="checked_type_4">'+
										'</div>'+
										'<label for="checked-type-4" class="ml5 label-type mr10">多选列表</label>'+
									'</td>'+
								'</tr>'+
								'<tr>'+
									'<th>默认值</th>'+
									'<td><input type="text" name="" id="selectDefVal" class="g-input" placeholder="请设置默认值"></td>'+
								'</tr>'+
								'<tr>'+
									'<th>输出格式</th>'+
									'<td>'+
										'<div class="g-radio">'+
											'<input type="radio" name="format-output" id="format_output" value="format_output">'+
										'</div>'+
										'<label for="format-output1" class="label-type ml5 mr20">选项值</label>'+
										'<div class="g-radio">'+
											'<input type="radio" name="format-output" id="format_output_2" value="format_output_2">'+
										'</div>'+
										'<label for="format-output2" class="label-type">选项名称</label>'+
									'</td>'+
								'</tr>'+
							'</tbody>'+
						'</table>'+
					'</td>'+
				'</tr>'+
			'</table>';
	    	$(".tmpMainType").show();//显示统计类型
	    	$("#minVal").val("");//最小值置为空
	    	$("#maxVal").val("");//最大值置为空
	    	$("#strLenId").hide();//隐藏字符长度取值范围
	    	var dss = $("#data_source_select").val();
	    	if(dss!=null && dss.length>0){
	    		var midx = mainFieldList[dss].ref_statistics;
		    	var res = getValue(midx);
		    	if(res !=null){
		    		if(res.length <=0){
		    			va="|请选择";
		    		}else{
		    			va="|请选择"+"\r\n";
		    		}
		    		for(var i=0;i<res.length;i++){
		    			if(i==res.length-1){
		    				va += res[i].key+"|"+res[i].code;
		    			}else{
		    				va += res[i].key+"|"+res[i].code+"\r\n";
		    			}
		    		}
		    	}
	    	}
	    }else if(val == "number"){//数字
	    	html = 
	    	'<table width="100%">'+
				'<tr class="border">'+
					'<th class="text-center" width="110">相关参数</th>'+
					'<td colspan="3" class="td-table">'+
						'<table width="100%">'+
							'<tbody>'+
								'<tr>'+
									'<th>取值范围</th>'+
									'<td>'+
										'<input type="text" name="" id="numMinVal" class="g-input inline-block w100" placeholder="最小值" value="1" onkeyup="(this.v=function(){this.value=this.value.replace(/[^-.0-9]+/,\'\');}).call(this)" onfocus="(this.v1=function(){this.value=this.value;}).call(this)" onblur="this.v1();"/>'+
										'<div class="mr10 ml10 td-label">'+
											'-' + 
										'</div>'+
										'<input type="text" name="" id="numMaxVal" class="g-input inline-block w100" placeholder="最大值" value="" onkeyup="(this.v=function(){this.value=this.value.replace(/[^-.0-9]+/,\'\');}).call(this)" onfocus="(this.v1=function(){this.value=this.value;}).call(this)" onblur="this.v1();"/>'+
									'</td>'+
								'</tr>'+
								'<tr>'+
									'<th>小数位数</th>'+
									'<td>'+
										'<input type="text" name="" id="numUnit" class="g-input" placeholder="小数位数" onkeyup="(this.v2=function(){this.value=this.value.replace(/[^0-9]+/,\'\');}).call(this)" onfocus="(this.v2=function(){this.value=this.value;}).call(this)" onblur="this.v2();">'+
									'</td>'+
								'</tr>'+
								'<tr>'+
									'<th>个数</th>'+
									'<td>'+
										'<div class="g-radio">'+
											'<input type="radio" name="checked-type" id="numCount" value="1">'+
										'</div>'+
										'<label for="checked-type-1" class="ml5 label-type mr10">1个</label>'+
				
										'<div class="g-radio">'+
											'<input type="radio" name="checked-type" id="numCount_2" value="2">'+
										'</div>'+
										'<label for="checked-type-2" class="ml5 label-type mr10">2个</label>'+
									'</td>'+
								'</tr>'+
								/*'<tr>'+
									'<th>默认值</th>'+
									'<td>'+
										'<div class="g-radio">'+
											'<input type="radio" name="is_num_text" id="numDefVal">'+
										'</div>'+
										'<label for="is_password_text_1" class="label-type ml5 mr20">是</label>'+

										'<div class="g-radio">'+
											'<input type="radio" name="is_num_text" id="numDefVal_2">'+
										'</div>'+
										'<label for="is_filter_2" class="label-type">否</label>'+
										'<div class="tips mt10">'+
											'注：区间字段可以通过<br />filters("字段名称","模型id","自定义数组")调用'+
										'</div>'+
									'</td>'+
								'</tr>'+*/
							'</tbody>'+
						'</table>'+
					'</td>'+
				'</tr>'+
			'</table>';
	    	$(".tmpMainType").hide();//隐藏统计类型
	    	$("#minVal").val("");//最小值置为空
	    	$("#maxVal").val("");//最大值置为空
	    	$("#strLenId").hide();//隐藏字符长度取值范围
//	    	onblur="(this.v=function(){var v = this.value; if(!$.isNumeric(this.value)){v=\'\'}this.value=v;}).call(this)"
	    }else if(val == "date-time"){//日期和时间
	    	html = 
	    	'<table width="100%">'+
				'<tr class="border">'+
					'<th class="text-center" width="110">相关参数</th>'+
					'<td colspan="3" class="td-table">'+
						'<table width="100%">'+
							'<tbody>'+
								/*'<tr>'+
									'<th>时间格式</th>'+
									'<td>'+
										'<div class="mb10">'+
											'<div class="g-radio">'+
												'<input type="radio" name="checked-date-time" id="checked_date_time" value="checked_date_time">'+
											'</div>'+
											'<label for="checked-date-time-1" class="label-type">'+
												'日期（yyyy-MM-dd）'+
											'</label>'+
										'</div>'+
//										'<div class="mb10">'+
//											'<div class="g-radio">'+
//												'<input type="radio" name="checked-date-time" id="checked_date_time_2" value="checked_date_time_2">'+
//											'</div>'+
//											'<label for="checked-date-time-2" class="label-type">'+
//												'日期+12小时制时间（2017-03-20 07:59:01）'+
//											'</label>'+
//										'</div>'+
//										'<div class="mb10">'+
//											'<div class="g-radio">'+
//												'<input type="radio" name="checked-date-time" id="checked_date_time_3" value="checked_date_time_3">'+
//											'</div>'+
//											'<label for="checked-date-time-3" class="label-type">'+
//												'日期+24小时制时间（2017-03-20 19:59:01）'+
//											'</label>'+
//										'</div>'+
										'<div class="mb10">'+
											'<div class="g-radio">'+
												'<input type="radio" name="checked-date-time" id="checked_date_time_4" value="checked_date_time_4">'+
											'</div>'+
											'<label for="checked-date-time-4" class="label-type"> '+
												'整数  显示格式： '+
											'</label>'+
											'<div class="g-select inline-block g-select-210">'+
												'<select name="setting[format]" id="dateFormat">'+
													'<option value="Y-m-d Ah:i:s">'+
													 	'12小时制:2017-03-20 07:59:01'+
													 '</option>'+
													'<option value="Y-m-d H:i:s">'+
													 	'24小时制:2017-03-20 19:59:01'+
													'</option>'+
													'<option value="Y-m-d H:i">'+
													 	'2017-03-20 19:59'+
													'</option>'+
													'<option value="Y-m-d">'+
													 	'2017-03-20'+
													'</option>'+
													'<option value="m-d">'+
													 	'03-20'+
													'</option>'+
												'</select>'+
												'<span class="arr-icon"></span>'+
											'</div>'+
										'</div>'+
									'</td>'+
								'</tr>'+*/
								'<tr>'+
									'<th>个数</th>'+
									'<td>'+
										'<div class="g-radio">'+
											'<input type="radio" name="checked-type" id="dateCount" value="1">'+
										'</div>'+
										'<label for="checked-type-1" class="ml5 label-type mr10">1个</label>'+
				
										'<div class="g-radio">'+
											'<input type="radio" name="checked-type" id="dateCount_2" value="2">'+
										'</div>'+
										'<label for="checked-type-2" class="ml5 label-type mr10">2个</label>'+
									'</td>'+
								'</tr>'+
							'</tbody>'+
						'</table>'+
					'</td>'+
				'</tr>'+
			'</table>';
	    	$(".tmpMainType").hide();//隐藏统计类型
	    	$("#minVal").val("");//最小值置为空
	    	$("#maxVal").val("");//最大值置为空
	    	$("#strLenId").hide();//隐藏字符长度取值范围
	    }
	    $(this).parents(".table-form").next().html(html);
//	    if(va!=null && va.length>0){
	    	$("#selectList").val(va);
//	    	$("#selectList").attr("readonly","readonly");
//	    	$("#selectList").attr({style:"color: gray;background-color: #E0E0E0"});
//	    }
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