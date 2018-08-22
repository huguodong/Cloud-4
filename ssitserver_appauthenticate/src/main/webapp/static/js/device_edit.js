// JavaScript Document
$(function(){

	$(".yingjian-dialog .edit").on("click",function(){
		
		$("#extTemp").trigger("change");
		//getEXTdata();
		$(".form-dialog-fix.w-900.yingjian").fadeIn(100);
		$(".form-dialog-fix.w-900.yingjian").find(".form-dialog").animate({
			"right":0
		});
		
	});
	$(".yunxing-dialog .edit").on("click",function(){
		$("#runTemp").trigger("change");
		//getRundata();
		$(".form-dialog-fix.w-900.yunxing").fadeIn(100);
		$(".form-dialog-fix.w-900.yunxing").find(".form-dialog").animate({
			"right":0
		});
	});
	$(".monitor-dialog .edit").on("click",function(){
		var id = $("#monitorTemp").val();
		var monitor = monitorTempList;
		monitorinfo = [];
		//monitordata = [];
		//getMonitordata();
		$.each(monitor,function(i,item){
			$.each(item.monitorDetailList,function(j,info){
				if(info.device_mon_tpl_idx == id && info.device_ext_type == 0)
					monitorinfo.push(info);
				/* if(info.device_mon_tpl_idx == deviceinfo.device_idx && info.device_ext_type == 1)
					monitordata.push(info); */
			});
		});
		GetMonitorDataList();
		$(".form-dialog-fix.w-900.monitor").fadeIn(100);
		$(".form-dialog-fix.w-900.monitor").find(".form-dialog").animate({
			"right":0
		});
	});
	
	//数据同步配置 修改按钮点击事件
	$(".dbsync-dialog .edit").on("click",function(){
		var id=$("#dbSyncTemp").val();
		var dbsync = dbsyncTempList; 
		dbsyncinfo = [];
		
		//getDBsyncdata();
		//获取同步下拉数据
		var cycle = new Array();
		$.each(dbsync,function(i,item){
			$.each(item.dbSyncDeatilList,function(j,info){
				cycle.push(info.sync_cycle);
				if(info.device_dbsync_id == id && info.device_tpl_type == 0){
					dbsyncinfo.push(info);
				}
			/* 	if(info.device_dbsync_id == deviceinfo.device_idx && info.device_tpl_type == 1){
					dbsyncdata.push(info);
				} */
			});
		});
		
		var list_cycle = unique(cycle);
		
		addDBsysnc_cycle(list_cycle);
		GetDataFromList();
		$(".form-dialog-fix.dbsync").fadeIn(100);
		$(".form-dialog-fix.dbsync").find(".form-dialog").animate({
			"right":0
		});
	});
	
	$(".form-dialog .form-wrap .btn").on("click",function(){
		$(this).addClass("active").siblings(".btn").removeClass("active");
	});
	
	//文本框聚焦删除error class事件
	$(":text").focus(function(){
		$(this).parent().parent().removeClass("error");
	});

	/*数据同步方法 */
	//数据同步配置保存按钮	
	$(".form-dialog-fix.w-900.dbsync .g-submit2.g-btn-green").on("click",function(){
		$(".zhuce-result").fadeIn();
		dbsyncinfo = [];
		var checkdata = true;
		//获取配置信息
		var data = [];
		$("select.db").each(function(){
			if($(this).parents(".form-wrap").find(".table").val() == 1){
				var dbname = $(this).find("option:selected").html();
				var tablename = $(this).parents(".form-wrap").find(".table option:selected").html();
				var param ={
					"library_idx":0,
					"device_tpl_type":0,
					"db_name":dbname,
					"table_name":tablename,
				};
				if($(this).parents(".form-wrap").find(".tongbu").is(":checked")){
					var cycletime = $(this).parents(".form-wrap").find(".cycle").val();
					if(cycletime== -1){
						alert(dbname+":"+tablename+"未设置同步周期");
						checkdata = false;
						return;
					}
					param.issync = 1;
					param.sync_cycle = cycletime;
				}else{
					param.issync = 0;
				}
				data.push(param);
			}
		});
		if(checkdata == false)
			return;
			
		if(data.length ==0 ){
			$(".zhuce-result").fadeOut(2000);
			return ;
		}
		//获取全局变量
		var dev_info = deviceinfo;
		var dbsync = dbsyncTempList;
		var temp_idx = null;	
		//验证数据是否为一个模版.如果是，将模板id传入temp_idx
		$.each(dbsync,function(n,temp){
			var count = 0;
			$.each(data,function(i,item){
				$.each(temp.dbSyncDeatilList,function(j,info){
					var num = 0;
					if(info.device_tpl_type == 0){
						num +=(item.db_name == info.db_name)?1:0;
						num +=(item.table_name == info.table_name)?1:0;
						num +=(item.issync == info.issync)?1:0;
						num +=(item.sync_cycle == info.sync_cycle)?1:0;
						if(num == 4){
							count +=1;
						}
					}
				});
			});
			if(count == data.length  && count >0 ){
				temp_idx = temp.device_tpl_idx;
				return ;
			}
		});
		
		$.each(dbsync,function(i,item){
			$.each(item.dbSyncDeatilList,function(j,info){
				if(info.device_dbsync_id == temp_idx && info.device_tpl_type == 0){
					dbsyncinfo.push(info);
				}
			});
		});
		if(dbsyncinfo.length != data.length)
			temp_idx = null;

		
		$(".shade").click();
		//alert("temp_idx:"+temp_idx);
		//根据temp_idx匹配模版id，进行处理  
		if(temp_idx == null){
			$.each(data,function(i,item){
					item.device_dbsync_id = dev_info.device_idx;
					item.library_idx = dev_info.library_idx;	
					item.device_tpl_type = 1;
				});
			if(dbsyncdata.length>0){
				$.ajax({
					url:basePath+"device/UpdateDBsyncConfig", 
					type:"POST",
					data:{"json":JSON.stringify(data)},
					success:function(data){
					if(data.state){
						getDBsyncdata();
						$("#dbSyncTemp").val(-1);
						$(".zhuce-result").fadeOut(500);
					}else{
						$(".zhuce-result").addClass("error");
						$(".zhuce-result").fadeOut(1000);
						$(".zhuce-result").removeClass("error");
					}
					}
				});
			}else {
				$.ajax({
					url:basePath+"device/InsertDBsyncConfig",
					type:"POST",
					data:{"json":JSON.stringify(data)},
					success:function(data){
					if(data.state){
						getDBsyncdata();
						$("#dbSyncTemp").val(-1);
						dbsyncdata = data;
						$(".zhuce-result").fadeOut(500);
					}else{
						$(".zhuce-result").addClass("error");
						$(".zhuce-result").fadeOut(1000);
						$(".zhuce-result").removeClass("error");
					}
					}
				});
			}
		}else{
			if(dbsyncdata.length>0){
				$.each(data,function(i,item){
					item.device_dbsync_id = dev_info.device_idx;
					item.library_idx = dev_info.library_idx;	
					item.device_tpl_type = 1;
				});
				$.ajax({
					url:basePath+"device/DeleteDBsyncConfig",
					type:"POST",
					data:{"json":JSON.stringify(data)},
					success:function(data){
					if(data.state){
						
						$("#dbSyncTemp").val(temp_idx);
						$(".zhuce-result").fadeOut(500);
					}else{
						$(".zhuce-result").addClass("error");
						$(".zhuce-result").fadeOut(1000);
						$(".zhuce-result").removeClass("error");
					}
					}
				});
			}
			var conf = {
			"device_dbsync_tpl_idx":temp_idx,
			"device_idx":dev_info.device_idx,
			"device_dbsync_tpl_flg":"1",
			};
			$.ajax({
					url:basePath+"device/UpdateDeviceConfig",
					type:"POST",
					data:{"json":JSON.stringify(conf),},
					success:function(data){
					if(data.state){
						$("#dbSyncTemp").val(temp_idx);
						$(".zhuce-result").fadeOut(500);
						getDBsyncdata();
					}else{
						$(".zhuce-result").addClass("error");
						$(".zhuce-result").fadeOut(3000);
					}
					}
			});
		}
		
	});

	//数据同步配置  获取数据同步当前项数据
	function GetDataFromList(){ 
		//初始化弹出层
		$("#dbsync_right_form .right-content .table").val(-1);
		$("#dbsync_right_form .right-content .cycle").val(-1);
		$("#dbsync_right_form .right-content .tongbu").each(function(){
			if($(this).is(':checked'))
				$(this).click();
		});
		var id = $("#dbSyncTemp").val();
		var datatype = $("#dbsync_right_form span.btn.active").html(); 
		if(id == -1){
			//加载当前项自定义数据
			if(dbsyncdata){
				if(datatype == "模板"){
					$("#dbsync_right_form span.btn.active").siblings(".btn").click();
				}
				$.each(dbsyncdata,function(i,item){
					$("select.db").each(function(){
						if($(this).find("option:selected").html() == item.db_name){
							var tablename = $(this).parents(".form-wrap").find(".table").find("option").html();
							if(tablename == item.table_name){
								$(this).parents(".form-wrap").find(".table").val(1);
								if(item.issync == 1){
									if(!$(this).parents(".form-wrap").find(".tongbu").is(":checked"))
										$(this).parents(".form-wrap").find(".tongbu").click();
									$(this).parents(".form-wrap").find(".cycle").val(item.sync_cycle);
								}
							}
						}
					});
				});
			}else{
				alert("无自定义数据!请选择模板!");
				return;
			}
		}else{
			if(datatype == "数据"){
				$("#dbsync_right_form span.btn.active").siblings(".btn").click();
			}
			//加载当前模版数据
			$.each(dbsyncinfo,function(i,item){
				$("select.db").each(function(){
					if($(this).find("option:selected").html() == item.db_name){
						var tablename = $(this).parents(".form-wrap").find(".table").find("option").html();
						if(tablename == item.table_name){
							$(this).parents(".form-wrap").find(".table").val(1);
							if(item.issync == 1){
								if(!$(this).parents(".form-wrap").find(".tongbu").is(":checked"))
									$(this).parents(".form-wrap").find(".tongbu").click();
								$(this).parents(".form-wrap").find(".cycle").val(item.sync_cycle);
							}
						}
					}
				});
			});
		} 
	} 

	//数据同步配置  添加同步周期下拉选项
	function addDBsysnc_cycle(list_cycle){
		$("#dbsync_right_form .right-content .cycle").html("");
		var html="<option value='-1'>请选择</option>";
		for(var i=0;i<list_cycle.length;i++){
			var cycle = String(list_cycle[i]);
			if(cycle.charAt(cycle.length-1) == "H"){
				var newcycle = cycle.replace("H", "小时");	
				html +="<option value=\""+cycle+"\">"+newcycle+"</option>";
			}
			if(cycle.charAt(cycle.length-1) == "D"){
				var newcycle = cycle.replace("D", "天");	
				html +="<option value=\""+cycle+"\">"+newcycle+"</option>";
			}
			if(cycle.charAt(cycle.length-1) == "M"){	
				var newcycle = cycle.replace("M", "月");
				html +="<option value=\""+cycle+"\">"+newcycle+"</option>";
			}
			if(cycle.charAt(cycle.length-1) == "Y"){
				var newcycle = cycle.replace("Y", "年");
				html +="<option value=\""+cycle+"\">"+newcycle+"</option>";
			}
		}
		$("#dbsync_right_form .right-content .cycle").html(html);		
	}


/*设备监控配置方法 */
	//设备监控配置  获取数据同步当前项数据
	function GetMonitorDataList(){ 
		//初始化弹出层
		$("#monitor_right_form .right-content .colorSelect").val(-1);
		$("#monitor_right_form [name='threshold']").val("");
		var id = $("#monitorTemp").val();
	
		if(id == -1){
			//加载当前项自定义数据
			if(monitordata){
				$.each(monitordata,function(i,item){
					var meta_idx = item.meta_log_obj_idx;
					var btn_name = $("div#"+meta_idx+" .btn.active").html();
					if(item.alert == 1){
						if(btn_name == "失效"){
							$("div#"+meta_idx+" .btn.active").siblings(".btn").click();
						}
					}else{
						if(btn_name == "报警"){
							$("div#"+meta_idx+" .btn.active").siblings(".btn").click();
						}
					}
					if(item.monitor_logic_obj_idx != null && item.plc_logic_obj_idx == null){
			
						$("div#"+meta_idx+" ul#"+item.monitor_logic_obj_idx+" [name='threshold']").val(item.threshold);
						$("div#"+meta_idx+" ul#"+item.monitor_logic_obj_idx+" .colorSelect").val(item.color);
					}else if(item.monitor_logic_obj == null && item.plc_logic_obj_idx !=null){
						$("div#"+meta_idx+" ul#"+item.plc_logic_obj_idx+" [name='threshold']").val(item.threshold);
						$("div#"+meta_idx+" ul#"+item.plc_logic_obj_idx+" .colorSelect").val(item.color);
					}else{
						$("div#"+meta_idx+" .colorSelect").val(item.color);
					}
					
				});
			}else{
				alert("无自定义数据!请选择模板!");
				return;
			}
		}else{
			//加载当前模版数据
			$.each(monitorinfo,function(i,item){
				var meta_idx = item.meta_log_obj_idx;
				var btn_name = $("div#"+meta_idx+" .btn.active").html();
				if(item.alert == 1){
					if(btn_name == "失效"){
						$("div#"+meta_idx+" .btn.active").siblings(".btn").click();
					}
				}else{
					if(btn_name == "报警"){
						$("div#"+meta_idx+" .btn.active").siblings(".btn").click();
					}
				}
				if(item.monitor_logic_obj_idx != null && item.plc_logic_obj_idx == null){
			
					$("div#"+meta_idx+" ul#"+item.monitor_logic_obj_idx+" [name='threshold']").val(item.threshold);
					$("div#"+meta_idx+" ul#"+item.monitor_logic_obj_idx+" .colorSelect").val(item.color);
				}else if(item.monitor_logic_obj == null && item.plc_logic_obj_idx !=null){
					$("div#"+meta_idx+" ul#"+item.plc_logic_obj_idx+" [name='threshold']").val(item.threshold);
					$("div#"+meta_idx+" ul#"+item.plc_logic_obj_idx+" .colorSelect").val(item.color);
				}else{
					$("div#"+meta_idx+" .colorSelect").val(item.color);
				}
					
			});
		} 
	} 

	
	
	//设备监控保存按钮事件
	$(".form-dialog-fix.w-900.monitor .g-submit2.g-btn-green").on("click",function(){
		$(".zhuce-result").fadeIn();
		monitorinfo = [];
		var checkdata = true ;
		//获取监控配置信息
		var data = [];
		for(var i = 1;i<=13;i++){
			$.each($("div#"+i+" .colorSelect"),function(){
				if(i!=3 && i!=4 && i!=9 && i!=10 && i!=11){
					if($(this).val() != -1){
						var color = $(this).val();
						var alert = ($(this).parents("ul").find(".btn.active").html() == "报警")?1:0;
						var param ={
							"visiable":1,
							"alert":alert,
							"color":color,
							"threshold":0,
							"meta_log_obj_idx":i,
							"logic_obj_idx":i,
							"table_name": "metadata_logic_obj",
						};
						data.push(param);
					}
				}else if(i ==3 || i ==4){
					if($(this).val() != -1){
						var color = $(this).val();
						var alert = ($(this).parents("ul").find(".btn.active").html() == "报警")?1:0;
						var threshold = $(this).parents("ul").find("[name = 'threshold']").val();
						if(!threshold){
							alert("未设置报警阈值！");
							checkdata = false;
							return ;
						}
						var logic_idx = $(this).parents("ul").attr("id");
						var param ={
							"visiable":1,
							"alert":alert,
							"color":color,
							"threshold":threshold,
							"meta_log_obj_idx":i,
							"logic_obj_idx":logic_idx,
							"table_name": "monitor_logic_obj",
						};
						data.push(param);
					}
				}else if(i == 9 ||i == 10 ||i == 11){
					if($(this).val() != -1){
						var color = $(this).val();
						var alert = ($(this).parents("ul").find(".btn.active").html() == "报警")?1:0;
						var threshold = $(this).parents("ul").find("[name = 'threshold']").val();
						if(!threshold){
							alert("未设置报警阈值！");
							checkdata = false;
							return ;
						}
						var logic_idx = $(this).parents("ul").attr("id");
						var param ={
							"visiable":1,
							"alert":alert,
							"color":color,
							"threshold":threshold,
							"meta_log_obj_idx":i,
							"logic_obj_idx":logic_idx,
							"table_name": "plc_logic_obj",
						};
						data.push(param);
					}
				}
			});		
		}
		if(checkdata == false)
			return ;
		if(data.length == 0){
			$(".zhuce-result").fadeOut(2000);
			return ;
		}
		//获取全局变量
		var dev_info = deviceinfo;
		var monitor = monitorTempList;
		var temp_idx = null;
		//验证数据是否为一个模板，如果是，将模板id传入temp_idx
		$.each(monitor,function(n,temp){
			var count = 0;
			$.each(data,function(i,item){
				$.each(temp.monitorDetailList,function(j,info){
					var num = 0;
					if(info.device_ext_type == 0){
						num +=(item.visiable == info.visiable)?1:0;
						num +=(item.alert == info.alert)?1:0;
						num +=(item.color == info.color)?1:0;
						num +=(item.threshold == info.threshold)?1:0;
						num +=(item.meta_log_obj_idx == info.meta_log_obj_idx)?1:0;
						num +=(item.logic_obj_idx == info.logic_obj_idx)?1:0;
						num +=(item.table_name == info.table_name)?1:0;
						if(num == 7)
							count +=1;
					}
				});
			});
			if(count == data.length && count >0){
				temp_idx = temp.device_mon_tpl_idx;
				return ;
			}
		});
		
		$.each(monitor,function(i,item){
			$.each(item.monitorDetailList,function(j,info){
				if(info.device_mon_tpl_idx == temp_idx && info.device_ext_type == 0)
					monitorinfo.push(info);
			});
		});
		if(monitorinfo.length != data.length)
			temp_idx = null;
		$(".shade").click();
		
		//根据temp_idx匹配模板id，进行处理
		if(temp_idx == null){
			$.each(data,function(i,item){
				item.library_idx = dev_info.library_idx;
				item.device_ext_type = 1;
				item.device_mon_tpl_idx = dev_info.device_idx;
			});
			if(monitordata.length >0){
				$.ajax({
					url:basePath+"device/UpdateMonitorConfig",
					type:"POST",
					data:{"json":JSON.stringify(data)},
					success:function(data){
					if(data.state){
						getMonitordata();
						$("#monitorTemp").val(-1);
						$(".zhuce-result").fadeOut(500);
					}else{
						$(".zhuce-result").addClass("error");
						$(".zhuce-result").fadeOut(1000);
						$(".zhuce-result").removeClass("error");
					}
					}
				});
			}else{
				$.ajax({
					url:basePath+"device/InsertMonitorConfig",
					type:"POST",
					data:{"json":JSON.stringify(data)},
					success:function(data){
					if(data.state){
						getMonitordata();
						$("#monitorTemp").val(-1);
						$(".zhuce-result").fadeOut(500);
					}else{
						$(".zhuce-result").addClass("error");
						$(".zhuce-result").fadeOut(1000);
						$(".zhuce-result").removeClass("error");
					}
					}
				});
			}
		}else{
			if(monitordata.length>0){
				$.each(data,function(i,item){
					item.library_idx = dev_info.library_idx;
					item.device_ext_type = 1;
					item.device_mon_tpl_idx = dev_info.device_idx;
				});
				var param = {
					"device_mon_tpl_idx":dev_info.device_idx,
					"device_ext_type":1
				};
				$.ajax({
					url:basePath+"device/DeleteMonitorConfig",
					type:"POST",
					data:{"json":JSON.stringify(param)},
					success:function(data){
						if(data.state){
							$(".zhuce-result").fadeOut(500);
						}else{
							$(".zhuce-result").addClass("error");
							$(".zhuce-result").fadeOut(1000);
							$(".zhuce-result").removeClass("error");
						}
					}
				});
			}
			var conf = {
				"device_idx":dev_info.device_idx,
				"device_monitor_tpl_idx":temp_idx,
				"device_monitor_tpl_flg":1,
			};
			$.ajax({
				url:basePath+"device/UpdateDeviceConfig",
				type:"POST",
				data:{"json":JSON.stringify(conf),},
				success:function(data){
				if(data.state){
					
					$(".zhuce-result").fadeOut(500);
					getMonitordata();
				}else{
					$(".zhuce-result").addClass("error");
					$(".zhuce-result").fadeOut(3000);
				}
				}
			});
		}
	});
	

	
	//数组去重
	function unique(arr){
    	var result = [], hash = {};
    	for (var i = 0, elem; (elem = arr[i]) != null; i++) {
    	    if (!hash[elem]) {
    	        result.push(elem);
    	        hash[elem] = true;
       	 	}
    	}
    	return result; 
	}
	
	$(".need-change").on("change",function(){
		/*
			每个需要交换显示的select框，都必须有
			类名need-change，
			唯一的ID名 对应着 将需要显示的那个DIV的date-id属性，
			select框 的value值 对应着 DIV的val属性
			如示例
		 */
		var thisId =  $(this).attr("id");
		var thisVal = $(this).val();
		var $lei = $("[date-id='"+thisId+"']");
		$lei.each(function(){
			var boxVal = $(this).attr("val");

			if(boxVal===thisVal){
				$(this).show();
			}else{
				$(this).hide();
			}
		});
	});

	function selectChange(){
		/*页面加载后，若select已经有值，先将对应的DIV显示*/
		$(".need-change").each(function(){
			var thisId =  $(this).attr("id");
			var thisVal = $(this).val();
			var $lei = $("[date-id='"+thisId+"']");
			$lei.each(function(){
				var boxVal = $(this).attr("val");

				if(boxVal===thisVal){
					$(this).show();
				}else{
					$(this).hide();
				}
			});
		});
	}
	selectChange();

	//运行配置模板编辑侧栏的  左侧菜单点击事件 ，左侧菜单的<li>必须和右侧内容的<div class="right-content">顺序一一对应，否则点击菜单对应的内容不一致
	$("#run_left_tab").on("click","li",function(){
		var thisIndex = $(this).index();
		$(this).addClass("active").siblings("li").removeClass("active");
		
		$("#run_right_form .right-content").hide().eq(thisIndex).show();
	});
	
	
	//硬件与逻辑配置模板编辑侧栏的  左侧菜单点击事件 
	$("#ext_left_tab").on("click","li",function(){
		var thisIndex = $(this).index();
		$(this).addClass("active").siblings("li").removeClass("active");
		
		$("#ext_right_form .right-content").hide().eq(thisIndex).show();
	});
	
	//监控配置模板编辑侧栏的  左侧菜单点击事件 
	$("#monitor_left_tab").on("click","li",function(){
		var thisIndex = $(this).index();
		$(this).addClass("active").siblings("li").removeClass("active");
		
		$("#monitor_right_form .right-content").hide().eq(thisIndex).show();
	});
	
	// 数据同步模板编辑侧栏的  左侧菜单点击事件 
	$("#dbsync_left_tab").on("click","li",function(){
		if($(this).hasClass("active"))
			return;
		var thisIndex = $(this).index();
		$(this).addClass("active").siblings("li").removeClass("active");
		$("#dbsync_right_form .right-content").hide().eq(thisIndex).show();
		
	});
	
	//设备编辑主页面 确定按钮
	$(".g-submit").on("click",function(){
		/*注册成功传入true,否则false*/
		if(!checkData()){
			return;
		}
		var deviceCode = $("#deviceCode").val();
		var libId = $("#libId").val();
		var deviceId = $("#deviceId").val();
		var deviceName = $("#deviceName").val();
		var deviceType = $("#deviceType").val();
		var acsName = $("#acsName").val();
		var acsPwd = $("#acsPwd").val();
		var logisticsNumber = $("#logisticsNumber").val();
		
		var extTempId = $("#extTemp").val();
		var runTempId = $("#runTemp").val();
		var monitorTempId = $("#monitorTemp").val();
		var dbSyncTempId = $("#dbSyncTemp").val();
		var deviceDesc = $("#deviceDesc").val();
		
		var param = {};
		param.deviceCode = deviceCode;
		param.libId = libId;
		param.deviceId = deviceId;
		param.deviceName = deviceName;
		param.deviceType = deviceType;
		param.acsName = acsName;
		param.acsPwd = acsPwd;
		param.logisticsNumber = logisticsNumber;
		param.extTempId = extTempId;
		param.runTempId = runTempId;
		param.monitorTempId = monitorTempId;
		param.dbSyncTempId = dbSyncTempId;
		param.deviceDesc = deviceDesc;
		
		// 数据提交，设备编辑 
		console.time("register");
		$.ajax({
			url:basePath+"device/deviceRegister",
			type:"POST",
			data:param,
			success:function(data){
				console.timeEnd("register");
				console.log(data);
			}
		});
		

	
// 		zhuceResult(true);
	});
	function zhuceResult(status){
		if(!status){
			$(".zhuce-result").addClass("fail");
			$(".zhuce-result").find("status-word").text("抱歉，设备信息编辑失败");
			$(".zhuce-result").find("tips-word").text("你可以请重新尝试编辑，或者联系系统管理<br>员解决问题。");
			$(".zhuce-result").fadeOut(5000);
			$(".zhuce-result").removeClass("fail");
		}

		$(".zhuce-result").fadeIn();
		$(".zhuce-result").fadeOut(5000);
	}



	$(".datepicker" ).datepicker({
		numberOfMonths:1,//显示几个月  
		showButtonPanel:false,//是否显示按钮面板  
		dateFormat: 'yy-mm-dd',//日期格式  
		clearText:"清除",//清除日期的按钮名称  
		closeText:"关闭",//关闭选择框的按钮名称  
		yearSuffix: '', //年的后缀  
		showMonthAfterYear:true,//是否把月放在年的后面  
		defaultDate:new Date(),//默认日期  
		//minDate:'2011-03-05',//最小日期  
		//maxDate:'2011-03-20',//最大日期  
		monthNames: ['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月'],  
		dayNames: ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'],  
		dayNamesShort: ['周日','周一','周二','周三','周四','周五','周六'],  
		dayNamesMin: ['日','一','二','三','四','五','六'],  
		onSelect: function(selectedDate) {//选择日期后执行的操作  
	
		}
	});
	$(".timepicker").timepicker({
		controlType: 'select',
		oneLine: true,
		timeFormat: 'hh:mm tt',
		currentText:"现在",
		closeText:"完成",
		timeOnlyTitle:"选择时间",
		amNames:["AM"],
		pmNames:["PM"],
		timetext:"时间",
		hourText:"时",
		minuteText:"分",
		secondText:"秒"
	});
});




var extTempList;//保存获取到的extTempList
var extdata = [];// 保存当前外设自定义信息 

var logicNameList; // 保存获取到的逻辑名称list 
var extModelList;   // 保存获取到的extModel list 

var runTempList;//保存获取到的runTempList
var rundata = [];// 保存当前运行自定义信息 

var extTempObj={};// 保存当前选中的逻辑外设模板对象 
var runTempObj={};// 保存当前选中的运行模板对象

var extTempSubmit={};// 保存要提交的逻辑外设模板配置   
var runTempSubmit={};// 保存要提交的运行模板配置   

var extTempCustom = {};// 保存逻辑外设模板的自定义数据 
var runTempCustom = {};// 保存运行模板的自定义数据 


var monitorTempList;//保存获取到的monitorTempList
var monitorinfo = new Array();// 保存当前设备监控模板信息 
var monitordata = new Array();// 保存当前设备监控的自定义信息 
	
var dbsyncTempList;//保存获取到的dbsyncTempList
var dbsyncinfo = new Array();//保存当前数据同步的模版信息
var dbsyncdata = new Array();//保存当前数据同步的自定义信息

//监听同步数据div内容变化
/* var title = $('#dbsync_right_form .right-content');//the element I want to monitor
title.bind('DOMNodeInserted', function(e) {
	if($("#dbsync_right_form .right-content").find(" span.btn.active").html() =="模板") 
		$(".form-dialog-fix.w-900.dbsync").find("#dbsync_right_form .right-content span.btn.active").removeClass("active").siblings(".btn").addClass("active");
});   */
function jsonToObj(str){ 
	try{
		return JSON.parse(str); 
	}catch(e){
		return "";
	}
} 


	/* function setTemplate(id){
	
		$("#extTemp").val(Number(id.extidx));
		$("#runTemp").val(Number(id.runidx));
		$("#monitorTemp").val(Number(id.monitoridx));
		$("#dbSyncTemp").val(Number(id.dbsyncidx));
		
	} */
	// 选择设备类型之后触发事件 
	$(document).on("change","#deviceType",function(){
		var id = this.value;
		getExtConfList(id);
		getRunConfList(id);
		getMonitorConfList(id);
		getDbSyncConfList(id);
	});
	
	// 选择硬件与逻辑配置模板之后触发的事件 
	$(document).on("change","#extTemp",function(){
		var index = this.selectedIndex;
		if(extTempList.length>0){
			console.log(extTempList[index-1]);			
		}
	});
	
	// 选择运行配置模板之后触发的事件 
	$(document).on("change","#runTemp",function(){
		var index = this.selectedIndex;
		if(runTempList.length>0){
			console.log(runTempList[index-1]);			
		}
	});

	//获取所有设备类型
	function getDeviceType(){
		$.ajax({
			url:basePath+"metadevicetype/SelectType",
			type:"POST",
			async:false,
			data:"",
			success:function(data){
				if(data!=null && data.state){
					var list = data.result;
					var html = "";
					//deviceType
					for(var i=0;i<list.length;i++){
						html += "<option value=\""+list[i].meta_devicetype_idx+"\">"+list[i].device_type_desc+"</option>";
					}
					$("#deviceType").append(html);
				}else{
					console.log(data.message)
				}
			}
			
		});
	}
	
	//获取数据同步自定义数据
	function getDBsyncdata(){
		var param = {
			"device_dbsync_id":deviceinfo.device_idx,
			"device_tpl_type":1,
		};
		$.ajax({
			url:basePath+"device/SelectDBsyncInfo",
			type:"POST",
			data:{"json":JSON.stringify(param)},
			success:function(data){
				if(data.result !=null && data.state){
					dbsyncdata =  data.result; 
				}
			}
		});
	}
	
	//获取设备监控自定义数据
	function getMonitordata(){
		var param = {
			"device_mon_tpl_idx":deviceinfo.device_idx,
			"device_ext_type":1,
		};
		$.ajax({
			url:basePath+"device/SelMonitordata",
			type:"POST",
			data:{"json":JSON.stringify(param)},
			success:function(data){
				if(data.result !=null && data.state){
					monitordata =  data.result; 
				}
			}
		});
	}
	// 根据数据类型，编辑按钮选用不同的颜色，-1:未选择，0：模板数据，1：自定义数据 
	function extTempType(val){
		if(val=="-1"){
			$(".yingjian-dialog .edit")
			.addClass("g-btn-gray")
			.removeClass("g-btn-blue")
			.removeClass("g-btn-green");
		}
		if(val=="0"){
			$(".yingjian-dialog .edit")
			.removeClass("g-btn-gray")
			.addClass("g-btn-blue")
			.removeClass("g-btn-green");
		}
		if(val=="1"){
			$(".yingjian-dialog .edit")
			.removeClass("g-btn-gray")
			.removeClass("g-btn-blue")
			.addClass("g-btn-green");
		}
	}
	
	// 点击保存按钮的等待效果 
	function startSaveExt(){
		$(".g-loading.save").fadeIn();
		$("#ext_left_tab").hide();
		$("#ext_right_form").hide();
	}
	
	// 取消等待效果 
	function endSaveExt(){
		$(".g-loading.save").fadeOut("",function(){
			$(".shade").eq(0).trigger("click");
			layer.alert("操作成功！",{icon:6});
		});
		$("#ext_left_tab").show();
		$("#ext_right_form").show();
	}
	
	$("#extTempRestore").on("click",function(){
		layer.confirm("恢复至模板数据后，该选项的自定义数据将被清除，确认是否将该模板选项恢复至模板数据？",{icon:1,title:"提示"},function(index){
			startSaveExt();
			// 获取当前选中的模板 
			var idx = $("#extTemp").val();
			if(idx!=null&&idx!=""&&idx!="-1"){
				delete extTempCustom[idx] // 删除该选项的自定义数据 
				$("#extTemp").trigger("change");
			}else{
				layer.alert("请先选择模板");
			}
			layer.close(index);
			endSaveExt();
		});
	})
	
	function clearExtConfData(){
		//先清空数据
		$(".extmodel").val("-1").trigger("change");
		//-先把所有的收款通道选择框取消选择
		$("#money div div").removeClass("on");
		$("#money input").prop("checked",false);
		$("#IdentityReader_comm").val("-1").trigger("change");
		
		
	}
	//获取设备外设自定义数据
	function getEXTdata(){
		var param = {
			"device_ext_id":deviceinfo.device_idx,
		};
		$.ajax({
			url:basePath+"device/SelExtdata",
			type:"POST",
			data:{"json":JSON.stringify(param)},
			success:function(data){
				if(data.result !=null && data.state){
					extdata =  data.result; 
				}
			}
		});
	}
	
	// 加载外设模板logicname的数据  
	function loadAllExtTempData(extObj){
		// 如果选择了模板则填充数据 
		if($("#extTemp").val()!="-1"){
			if(!_.isEmpty(extObj)){
				
				if(!_.isEmpty(extObj.extDetailList)){
					var list = extObj.extDetailList;
					for(var i=0;i<list.length;i++){
						var item = list[i];
						if(item.logic_obj.toLowerCase().indexOf("plc")>-1){
							$("#PLC_logicName").val(item.logic_obj_idx).trigger("change");
							$("#PLC_extModel").val(item.ext_model_idx).trigger("change");	
							// 填充书盒信息  
							if(item.logic_obj.toLowerCase().indexOf("ssl")>-1){
								var ext_extend_conf = jsonToObj(item.ext_extend_conf)
								$("#shelf1").val(ext_extend_conf.SHELF1);
								$("#shelf2").val(ext_extend_conf.SHELF2);
								$("#shelf3").val(ext_extend_conf.SHELF3);
								$("#drawer").val(ext_extend_conf.DRAWER);
								$("#bookbin").val(ext_extend_conf.BOOKBIN);
							}
						}else{
							$("#"+item.logic_obj+"_extModel").val(item.ext_model_idx);
							if (item.logic_obj.toLowerCase().indexOf("cashacceptor")>-1) {
								//收钞机-
								if(item.ext_extend_conf!=null && item.ext_extend_conf!=""){
									var extend = jsonToObj(item.ext_extend_conf);
									if(extend.CHANNEL!=null && extend.CHANNEL!=""){
										var channels = extend.CHANNEL.split(",");
										for(var j=0;j<channels.length;j++){
											$("#money input[value='"+channels[j]+"']").parent().addClass("on");
											$("#money input[value='"+channels[j]+"']").prop("checked",true);
										}
									}
								}
							}else if(item.logic_obj.toLowerCase().indexOf("identityreader")>-1){
								if(item.ext_comm_conf!=null && item.ext_comm_conf!=""){
									var comm = jsonToObj(item.ext_comm_conf);
									$("#IdentityReader_comm").val(comm.TYPE).trigger("change");
									if(comm.TYPE=="com"){
										$("#COM").val(comm.COM);
										$("#BAUD").val(comm.BAUD);
									}
									if(comm.TYPE=="ip"){
										$("#IP").val(comm.IP);
									}
									if(comm.TYPE=="net"){
										$("#NET").val(comm.NET);
									}
								}
							}
						}
					}
				}
			}
		}else{
			// 如果没有选择模板 
		}
		
	}
	
    // 硬件与逻辑配置保存事件  
	$("#extTempSave").on("click",function(){
		startSaveExt();
// 		$(".shade").eq(0).trigger("click");//收起菜单
		var detailList = [];
		if(_.isEmpty(extTempObj)){ 
			// 如果没有选择过ext模板 
// 			return ;
// 			extTempSubmit.device_tpl_type="1";//1:设备数据
// 			extTempSubmit.device_type=$("#deviceType").val();//设备类型
		}
		$("#ext_right_form .right-content").each(function(i,n){
			var obj = {};
			var logicId = $(this).find(".logicname").attr("id");
			var modelId = $(this).find(".extmodel").attr("id");
			if(logicId!=null && logicId!="" ){
				var logic_obj_idx = $("#"+logicId).val();
				obj.logic_obj_idx = logic_obj_idx;
				var ext_model_idx = $("#"+modelId).val();
				if(ext_model_idx=="-1") {
					return true;
				}
				obj.ext_model_idx = ext_model_idx;//模板idx
				var tab = $("#"+logicId).find("option:selected").attr("data-id");
				obj.logic_obj = tab;
				//收钞机,装配数据-
				if(tab.toLowerCase()=="cashacceptor"){
					var ext_extend_conf = {};
					var money = "";
					$("#money input").each(function(i,n){
						if($(this).prop("checked")){
							money += $(this).val() + ",";
						}
					});
					if(money.length>0){
						money = money.substring(0,money.length-1);
					}
					//-{"CURRENCY":"CNY", "CHANNEL":"1,5,10,20,50"}
					ext_extend_conf.CURRENCY="CNY";
					ext_extend_conf.CHANNEL=money;
					obj.ext_extend_conf = ext_extend_conf;
				} 
				
				//身份证阅读器，装配数据-
				if(tab.toLowerCase()=="identityreader"){
					var ext_comm_conf = {};
					var type = $("#IdentityReader_comm").val();
					var com = $("#COM").val();
					var baud = $("#BAUD").val();
					var usb = $("#USB").val();
					var ip = $("#IP").val();
					var port = $("#PORT").val();
					if(type=="com"){
						ext_comm_conf.COM=com;
						ext_comm_conf.TYPE="com";
						ext_comm_conf.BAUD=baud;
					}
					if(type=="usb"){
						ext_comm_conf.TYPE="usb";
						ext_comm_conf.USB=usb;
					}
					if(type=="net"){
						ext_comm_conf.TYPE="net";
						ext_comm_conf.IP=ip;
						ext_comm_conf.PORT=port;
					}
					if(type=="lpt"){
						ext_comm_conf.TYPE="lpt";
					}
					obj.ext_comm_conf = ext_comm_conf;
				} 
				
				
				// PLC 装配数据-
				if(tab.toLowerCase().indexOf("plc")>-1){
					if(tab.toLowerCase().indexOf("ssl")>-1){
						var ext_extend_conf = {};
						// 如果选了自助图书馆装配数据 
						var shelf1 = $("#shelf1").val();
						var shelf2 = $("#shelf2").val();
						var shelf3 = $("#shelf3").val();
						var drawer = $("#drawer").val();
						var bookbin = $("#bookbin").val();
						if(!_.isEmpty(shelf1)){
							ext_extend_conf.SHELF1 = shelf1;
						}else{
							ext_extend_conf.SHELF1 = "";
						}
						if(!_.isEmpty(shelf2)){
							ext_extend_conf.SHELF2 = shelf2;
						}else{
							ext_extend_conf.SHELF2 = "";
						}
						if(!_.isEmpty(shelf3)){
							ext_extend_conf.SHELF3 = shelf3;
						}else{
							ext_extend_conf.SHELF3 = "";
						}
						if(!_.isEmpty(drawer)){
							ext_extend_conf.DRAWER = drawer;
						}else{
							ext_extend_conf.DRAWER = "";
						}
						if(!_.isEmpty(bookbin)){
							ext_extend_conf.BOOKBIN = bookbin;
						}else{
							ext_extend_conf.BOOKBIN = "";
						}
						
					}
					obj.ext_extend_conf = ext_extend_conf;
				}
				detailList.push(obj);
				
			}
		});
		
		extTempSubmit.extDetailList = detailList;
		// 比较ExtTemp模板 
		compareExtTemp();
		
		endSaveExt();
	})
	
	function useCustomExt(idx){
		// 如果不相等相等，则使用自定义数据 
		extTempSubmit.device_tpl_type="1";//1:设备数据使用设备数据，非模板
		extTempCustom[idx] = extTempSubmit;// 保存到该选项的自定义数据中 
		//extTempType("1");	
	}
	
	// 比较ExtTemp模板和保存的数据,device_tpl_type:0-使用模板数据，1-非模板 
	function compareExtTemp(){
		
		if(!_.isEmpty(extTempObj) && !_.isEmpty(extTempSubmit)){
			var idx = extTempObj.device_tpl_idx;
			if(extTempObj.extDetailList.length!=extTempSubmit.extDetailList.length){
				// 如果不相等相等，则使用自定义数据 
				useCustomExt(idx);
				return;
			}
			for(var i=0;i<extTempObj.extDetailList.length;i++){
				var obj = extTempObj.extDetailList[i];
				var submit = extTempSubmit.extDetailList[i];
				if(obj.logic_obj_idx!=submit.logic_obj_idx){
					useCustomExt(idx);
					return;
				}
				if(obj.ext_model_idx!=submit.ext_model_idx){
					useCustomExt(idx);
					return;
				}
				// 身份证阅读器 
				if(obj.logic_obj.toLowerCase().indexOf("identityreader")>-1){
					var ext_comm_conf1 = jsonToObj(obj.ext_comm_conf);
					var ext_comm_conf2 = submit.ext_comm_conf;
					// 如果两个对象不相等 
					if(!_.isEqual(ext_comm_conf1,ext_comm_conf2)){
						useCustomExt(idx);
						return;
					}
				}
				// 收钞机 
				if(obj.logic_obj.toLowerCase().indexOf("cashacceptor")>-1){
					var ext_extend_conf1 = jsonToObj(obj.ext_extend_conf);
					var ext_extend_conf2 = submit.ext_extend_conf;
					if(!_.isEqual(ext_extend_conf1,ext_extend_conf2)){
						useCustomExt(idx);
						return;
					}
				}
				// 自助图书馆 
				if(obj.logic_obj.toLowerCase().indexOf("ssl")>-1){
					var ext_extend_conf1 = jsonToObj(obj.ext_extend_conf);
					var ext_extend_conf2 = submit.ext_extend_conf;
					if(!_.isEqual(ext_extend_conf1,ext_extend_conf2)){
						useCustomExt(idx);
						return;
					}
				}
			}
			extTempSubmit.device_tpl_type="0";//1:设备数据使用设备数据，非模板
			delete extTempCustom[idx];// 删除该选项的自定义数据 
			//extTempType("0");
		}else{
			layer.alert("请选择模板");
			return;
		}
	}
	
	// 填充所有的模板数据 
	function loadAllLogicObj(){
		if(logicNameList!=null && logicNameList.length>0){
			$(".logicname").each(function(i,n){
				var id = n.id.substring(0,n.id.indexOf("_"));
				var html = "";
				for(var i=0;i<logicNameList.length;i++){
					var item = logicNameList[i];
					if(item.logic_obj.toLowerCase().indexOf(id.toLowerCase())>-1){
						html+="<option data-id=\""+item.logic_obj+"\"  value=\""+item.meta_log_obj_idx+"\">"+jsonToObj(item.logic_obj_desc)['zh_CN']+"-"+item.logic_obj+"</option>";
					}
				}
				$(this).html(html);
			});
			$(".logicname").trigger("change");
		}
	}
	// 获取所有的logic_obj和ext_model的元数据
	function getAllLogicObjAndExtModel(){
		$.ajax({
			url:basePath+"device/getAllLogicObjAndExtModel",
			type:"POST",
			success:function(data){
				if(data!=null && data.state){
					var list = data.result; 
					logicNameList = list.logicObjList;
					extModelList = list.extModelList;
					// 获取到数据之后填充逻辑名下拉框.logicname 
					loadAllLogicObj();
				}else{
					console.log(data.message)
				}
			}
		});
	}
	$(document).on("change",".logicname",function(n){
		var logicId = $(this).val();
		var data_id = $(this).find("option:selected").attr("data-id");//自定义参数
		var html = "<option value=\"-1\">请选择</option>";
		if(logicId!="-1"){
			if(!_.isEmpty(data_id) && data_id!="undefinded"){
				for(var i=0;i<extModelList.length;i++){
					var item = extModelList[i];
					var model_version = "";
					if(item.ext_model_version != "-"){
						model_version = "-"+item.ext_model_version;
					}
					if(data_id.toLowerCase().indexOf("reader")>-1){
						if(item.ext_type.toLowerCase().indexOf("rfidreader")>-1){
							html+="<option value=\""+item.meta_ext_idx+"\">"+jsonToObj(item.ext_model_desc)['zh_CN']+model_version+"</option>";
						}
					}else if(data_id.toLowerCase().indexOf("plc")>-1){
						if(item.ext_type.toLowerCase().indexOf("plc")>-1){
							if(data_id.toLowerCase().indexOf("ssl")>-1 
									&& item.ext_model.toLowerCase().indexOf("plc")>-1){
								html+="<option value=\""+item.meta_ext_idx+"\">"+jsonToObj(item.ext_model_desc)['zh_CN']+model_version+"</option>";
								//设置拓展参数的值
								$("#PLC_box").attr("val",item.meta_ext_idx);
							}
							if(data_id.toLowerCase().indexOf("sorter")>-1 
									&& item.ext_model.toLowerCase().indexOf("sorter")>-1){
								html+="<option value=\""+item.meta_ext_idx+"\">"+jsonToObj(item.ext_model_desc)['zh_CN']+model_version+"</option>";
							}
							if(data_id.toLowerCase().indexOf("return")>-1 
									&& item.ext_model.toLowerCase().indexOf("return")>-1){
								html+="<option value=\""+item.meta_ext_idx+"\">"+jsonToObj(item.ext_model_desc)['zh_CN']+model_version+"</option>";
							}
						}
					}else if(data_id.toLowerCase().indexOf("barcode")>-1) {
						if(item.ext_type.toLowerCase().indexOf("barcode")>-1){
							html+="<option value=\""+item.meta_ext_idx+"\">"+jsonToObj(item.ext_model_desc)['zh_CN']+model_version+"</option>";
						}
					}else if(data_id.toLowerCase() == item.ext_type.toLowerCase()){
						html+="<option value=\""+item.meta_ext_idx+"\">"+jsonToObj(item.ext_model_desc)['zh_CN']+model_version+"</option>";
					}
				}
				// PLC设备的ext_type 都是plc 但是ext_model、logic_obj不同，要分开处理 
				if(data_id.toLowerCase().indexOf("plc")>-1){
					$("#PLC_extModel").html(html).trigger("change");
				}else{
					$("#"+data_id+"_extModel").html(html);
				}
			}
		}else{
			//qingxuanze 
			$(".extmodel").html(html);
		}
	});
	
	// 选择硬件与逻辑配置模板之后触发的事件 
	$("#extTemp").on("change",function(){
		// 清除原来的数据 
		clearExtConfData();
		var index = this.selectedIndex;
		if($("#extTemp").val()!="-1"){
			//  选择了模板之后修改编辑按钮样式  
			$(".yingjian-dialog .edit").removeClass("disabled").removeClass("g-btn-gray").addClass("g-btn-green");
			if(extTempList.length>0){
				extTempObj = extTempList[index-1];
				if(!_.isEmpty(extTempObj)){
					var idx = extTempObj.device_tpl_idx;
					if(_.isEmpty(extTempCustom[idx])){
						//深度克隆对象extTempObj
						extTempSubmit = $.extend(true, {},extTempObj);
						//extTempType("0");
					}else{
						// 如果自定义数据不为空则使用自定义数据 
						extTempSubmit = extTempCustom[idx];
						//extTempType("1");
					}
				}
				// 装载配硬件与逻辑配置的数据 
				loadAllExtTempData(extTempSubmit);
			}
		}else{
			//  选择了模板之后修改编辑按钮样式  
			//extTempType("-1")
// 			$(".yingjian-dialog .edit").addClass("disabled").addClass("g-btn-gray").removeClass("g-btn-green");
		}
	});
	
	
	//获取硬件与逻辑配置模板
	function getExtConfList(id){
		if(id=="-1"){
			$("#extTemp").html("<option value=\"-1\" >自定义参数</option>");
		}else{
			$.ajax({
				url:basePath+"device/getAllExtTempList",
				type:"POST",
				async:false,
				data:{json:id},
				success:function(data){
					if(data!=null && data.state){
						
						var list = data.result; 
						extTempList = data.result;
						var html = "<option value=\"-1\" >自定义参数</option>";
						if(list!=null){
							for (var i = 0; i < list.length; i++) {
								var item = list[i];
								html+="<option value=\""+item.device_tpl_idx+"\">"+item.device_tpl_ID+"-"+item.device_tpl_desc+"</option>";
							}
						}
						$("#extTemp").html(html);
	// 						setExtTempListDetail(list);//设置每个模板的详细信息
					}
					else{
						console.log(data.message);
					}
				}
				
			});
		}
	}
	
	//设置每个模板的详细信息
	function setExtTempListDetail(list){
		for (var i = 0; i < list.length; i++) {
			var listId = list[i].device_tpl_idx;
			var childList = list[i].extDetailList;
			var html = "";
			html += "<div class=\"item change-box\" date-id=\"extTemp\" val=\""+listId+"\" style=\"display:none;\">";
			html += "<div class=\"word\">&nbsp;</div>";
			if(childList!=null && childList.length>0){
				html += "<div class=\"choose-data\">" + "<ul>";
				for(var j=0;j<childList.length;j++){
					html += "<li style=\"margin-top:10px\">"+toJson(childList[j].logic_obj_desc).zh_CN+"<div class=\"data-edit edit g-btn-green\">修改</div></li>";
				}
				html += "</ul></div>";
			}
			html+="</div>";
			$("#extdiv").after(html);
		}
	}
	
	//字符串转JSON格式
	function toJson(str){ 
		try{
			return JSON.parse(str); 
		}catch(e){
			return "";
		}
	} 
	
	// 硬件与逻辑模板编辑事件 
	/* $(document).on("click",".data-edit",function(){
		$(".form-dialog-fix.yingjian").fadeIn(100);
		$(".form-dialog-fix.yingjian").find(".form-dialog").animate({
			"right":0
		});
	});*/
	 
	//获取设备运行自定义数据
	function getRundata(){
		var param = {
			"device_run_id":deviceinfo.device_idx,
			"device_tpl_type":1,
			"library_idx":deviceinfo.library_idx,
		};
		$.ajax({
			url:basePath+"device/SelRundata",
			type:"POST",
			data:{"json":JSON.stringify(param)},
			success:function(data){
				if(data.result !=null && data.state){
					rundata =  data.result; 
				}
			}
		});
	}
	
	// 根据数据类型，编辑按钮选用不同的颜色，-1:未选择，0：模板数据，1：自定义数据 
	function runTempType(val){
		if(val=="-1"){
			$(".yunxing-dialog .edit")
			.addClass("disabled")
			.addClass("g-btn-gray")
			.removeClass("g-btn-blue")
			.removeClass("g-btn-green");
		}
		if(val=="0"){
			$(".yunxing-dialog .edit")
			.removeClass("disabled")
			.removeClass("g-btn-gray")
			.addClass("g-btn-blue")
			.removeClass("g-btn-green");
		}
		if(val=="1"){
			$(".yunxing-dialog .edit")
			.removeClass("disabled")
			.removeClass("g-btn-gray")
			.removeClass("g-btn-blue")
			.addClass("g-btn-green");
		}
	}
	
	// 点击保存按钮的等待效果 
	function startSaveRun(){
		$(".g-loading.save").fadeIn();
		$("#run_left_tab").hide();
		$("#run_right_form").hide();
	}
	
	// 取消等待效果 
	function endSaveRun(){
		$(".g-loading.save").fadeOut("",function(){
			$(".shade").eq(1).trigger("click");
			layer.alert("操作成功！",{icon:6});
		});
		$("#run_left_tab").show();
		$("#run_right_form").show();
		
	}
	
	$("#runTempRestore").on("click",function(){
		layer.confirm("恢复至模板数据后，该选项的自定义数据将被清除，确认是否将该模板选项恢复至模板数据？",{icon:1,title:"提示"},function(index){
			startSaveRun();
			// 获取当前选中的模板 
			var idx = $("#runTemp").val();
			if(idx!=null&&idx!=""&&idx!="-1"){
				delete runTempCustom[idx] // 删除该选项的自定义数据 
				$("#runTemp").trigger("change");
			}else{
				layer.alert("请先选择模板");
			}
			layer.close(index);
			endSaveRun();
		});
	})
	
	// 打印设置里面的勾选启用功能 
	$("div[data-type='print'] input[type='checkbox']").on("change",function(){
		if($(this).prop("checked")){
			$(this).closest(".left").siblings().find("input,textarea")
			.removeClass("disabled").removeAttr("disabled");
		}else{
			$(this).closest(".left").siblings().find("input,textarea")
			.addClass("disabled").attr("disabled","disabled");
		}
	})
	
	// 清除模板数据内容 
	function clearRunConfData(){
		// 清空文本框 
		$("div.yunxing input[type='text']").val("");
		$("div.yunxing input[type='password']").val("");
		$("div.yunxing textarea").val("");
		// 选择框默认选中第一个 
		$("div.yunxing select").prop("selectedIndex", "0"); 
		// 复选框默认不勾选 
		$("div.yunxing input[type='checkbox']").prop("checked",false).trigger("change");
		// 记录日志：不记录 
		$("input[name='runlog_level'][value='none']").trigger("change");
		// 分拣类型 
		$("input[name='function_sorttype'][value='0']").trigger("change");
		// PIN类型 
		$("input[name='pin_type'][value='1']").trigger("change");
		// PIN设备输入 
		$("input[name='pin_input'][value='digitsoftpin']").trigger("change");
		// PIN输入超出次数之后 
		$("input[name='pin_retrydo'][value='0']").trigger("change");
		// 打印配置里面的radio默认选中询问读者 
		$("div[data-type='print'] input[type='radio'][name$='_type'][value='2']").trigger("change");
	}
	
	// 选择运行配置模板之后触发的事件 
	$(document).on("change","#runTemp",function(){
		// 先清除原来数据 
		clearRunConfData();
		var index = this.selectedIndex;
		if($("#runTemp").val()!="-1"){
			if(runTempList.length>0){
				runTempObj = runTempList[index-1];
				if(!_.isEmpty(runTempObj)){
					var idx = runTempObj.device_tpl_idx;
					if(_.isEmpty(runTempCustom[idx])){
						// 如果为空则使用模板数据 
						//深度克隆对象runTempObj
						runTempSubmit = $.extend(true,{}, runTempObj);
						runTempType("0");
					}else{
						// 如果自定义数据不为空则使用自定义数据 
						runTempSubmit = runTempCustom[idx];
						runTempType("1");
					}
				}
				// 装载运行配置的数据 
				loadAllRunTempData(runTempSubmit);
			}
		}else{
			//  选择了模板之后修改编辑按钮样式  
			runTempType("-1");
		}
	});
	
	
	function loadAllRunTempData(runObj){
		if($("#runTemp").val()!="-1"){
			if(!_.isEmpty(runObj)){
				// 先初始化输入框 
				if(!_.isEmpty(runObj.runDetailList)){
					var list = runObj.runDetailList;
					for(var i=0;i<list.length;i++){
						var item = list[i];
						var conf_type = item.run_conf_type;
						var conf_value = {};
						if(typeof item.run_conf_value == "object"){
							conf_value = item.run_conf_value
						}else{
							conf_value = jsonToObj(item.run_conf_value)
						}
						
						// 语言配置 
						if(conf_type=="language_config"){
							$("#basic_language").val(conf_value.LANGUAGE);
							continue;
						}
						
						// 运行日志配置 
						if(conf_type=="runlog_config"){
							$("input[name='runlog_level'][value='"+conf_value.RUNLOG_LEVEL+"']").trigger("change");
							if(conf_value.RUNLOG_TYPE.indexOf("file")>-1){
								$("#file").prop("checked",true).trigger("change");
							}else{
								$("#file").prop("checked",false).trigger("change");
							}
							
							if(conf_value.RUNLOG_TYPE.indexOf("db")>-1){
								$("#db").prop("checked",true).trigger("change");
							}else{
								$("#db").prop("checked",false).trigger("change");
							}
							
							$("#runlog_filesize").val(conf_value.RUNLOG_FILESIZE.replace(/[a-zA-Z]*/g,""));
							continue;
						}
						
						// 功能配置 
						if(conf_type=="function_config"){
							$("input[name='function_sorttype'][value='"+conf_value.SORTTYPE+"']").trigger("change");
							$("#function_dailycheckoutlimit").val(conf_value.DAILYCHECKOUTLIMIT);
							
							if(!_.isEmpty(conf_value.FUNCTION)){
								var func = conf_value.FUNCTION;
								var keys = _.keys(func);
								for(var j=0;j<keys.length;j++){
									if(func[keys[j]]=="1"){
										$("#function_"+keys[j].toLowerCase()).prop("checked",true).trigger("change");
									}else{
										$("#function_"+keys[j].toLowerCase()).prop("checked",false).trigger("change");
									}
								}
							}
							
							if(!_.isEmpty(conf_value.PIN)){
								var pin = conf_value.PIN;
								$("input[name='pin_type'][value='"+pin.TYPE+"']").trigger("change");
								$("input[name='pin_input'][value='"+pin.INPUT+"']").trigger("change");
								$("input[name='pin_retrydo'][value='"+pin.RETRYDO+"']").trigger("change");
								
								$("#pin_minlength").val(pin.MINLENGTH);
								$("#pin_maxlength").val(pin.MAXLENGTH);
								$("#pin_retrytimes").val(pin.RETRYTIMES);
							}
							
							if(!_.isEmpty(conf_value.CARDINPUT)){
								var card = conf_value.CARDINPUT;
								var keys = _.keys(card);
								for(var j=0;j<keys.length;j++){
									if(card[keys[j]]=="1"){
										$("#card_"+keys[j].toLowerCase()).prop("checked",true).trigger("change");
									}else{
										$("#card_"+keys[j].toLowerCase()).prop("checked",false).trigger("change");
									}
								}
							}
							
							if(!_.isEmpty(conf_value.BOOKINPUT)){
								var book = conf_value.BOOKINPUT;
								var keys = _.keys(book);
								for(var j=0;j<keys.length;j++){
									if(book[keys[j]]=="1"){
										$("#book_"+keys[j].toLowerCase()).prop("checked",true).trigger("change");
									}else{
										$("#book_"+keys[j].toLowerCase()).prop("checked",false).trigger("change");
									}
								}
							}
							continue;
						}
						
						// 超时配置 
						if(conf_type=="timeout_config"){
							if(conf_value.DISPLAY=="1"){
								$("#display").prop("checked",true).trigger("change");
							}else{
								$("#display").prop("checked",false).trigger("change");
							}
							if(conf_value.PLAYSOUND=="1"){
								$("#playsound").prop("checked",true).trigger("change");
							}else{
								$("#playsound").prop("checked",false).trigger("change");
							}
							
							if(!_.isEmpty(conf_value.INFO)){
								var info = conf_value.INFO;
								var keys = _.keys(info);
								for(var j=0;j<keys.length;j++){
									$("#info_"+keys[j].toLowerCase()).val(info[keys[j]].replace(/s*S*/g,""));
								}
							}
							
							if(!_.isEmpty(conf_value.WAIT)){
								var wait = conf_value.WAIT;
								var keys = _.keys(wait);
								for(var j=0;j<keys.length;j++){
									$("#wait_"+keys[j].toLowerCase()).val(wait[keys[j]].replace(/s*S*/g,""));
								}
							}
							
							if(!_.isEmpty(conf_value.TIMED)){
								var timed = conf_value.TIMED;
								var keys = _.keys(timed);
								for(var j=0;j<keys.length;j++){
									$("#timed_"+keys[j].toLowerCase()).val(timed[keys[j]].replace(/s*S*/g,""));
								}
							}
							
							if(!_.isEmpty(conf_value.SWITCHSCREEN)){
								var scre = conf_value.SWITCHSCREEN;
								if(scre.ENABLE=="1"){
									$("#switchscreen_enable").prop("checked",true).trigger("change");
								}else{
									$("#switchscreen_enable").prop("checked",false).trigger("change");
								}
								$("#switchscreen_cycle").val(scre.CYCLE.replace(/[a-zA-Z]*/g,""));
								$("#switchscreen_waittime").val(scre.WAITTIME.replace(/[a-zA-Z]*/g,""));
							}
							continue;
						}
						
						// 定时任务配置 
						if(conf_type=="scheduletask_config"){
							if(conf_value.ENABLEWEEKSCHEDLER=="1"){
								$("#enableweekschedler").prop("checked",true).trigger("change");
							}else{
								$("#enableweekschedler").prop("checked",false).trigger("change");
							}
							if(conf_value.ENABLEBALANCE=="1"){
								$("#enablebalance").prop("checked",true).trigger("change");
								var time = "";
								if(!_.isEmpty(conf_value.BALANCETIME) && conf_value.BALANCETIME.length >= 6){
									time += conf_value.BALANCETIME.substring(0,2) + ":";
									time += conf_value.BALANCETIME.substring(2,4) + ":";
									time += conf_value.BALANCETIME.substring(4);
								}
								$("#balancetime").val(time);
							}else{
								$("#enablebalance").prop("checked",false).trigger("change");
								$("#balancetime").val("");
							}
							
							if(conf_value.ENABLEREBOOT=="1"){
								$("#enablereboot").prop("checked",true).trigger("change");
								var time = "";
								if(!_.isEmpty(conf_value.REBOOTTIME) && conf_value.REBOOTTIME.length >= 6){
									time += conf_value.REBOOTTIME.substring(0,2) + ":";
									time += conf_value.REBOOTTIME.substring(2,4) + ":";
									time += conf_value.REBOOTTIME.substring(4);
								}
								$("#reboottime").val(time);
							}else{
								$("#enablereboot").prop("checked",false).trigger("change");
								$("#reboottime").val("");
							}
							
							if(conf_value.ENABLESHUTDOWN=="1"){
								$("#enableshutdown").prop("checked",true).trigger("change");
								var time = "";
								if(!_.isEmpty(conf_value.SHUTDOWNTIME) && conf_value.SHUTDOWNTIME.length >= 6){
									time += conf_value.SHUTDOWNTIME.substring(0,2) + ":";
									time += conf_value.SHUTDOWNTIME.substring(2,4) + ":";
									time += conf_value.SHUTDOWNTIME.substring(4);
								}
								$("#shutdowntime").val(time);
							}else{
								$("#enableshutdown").prop("checked",false).trigger("change");
								$("#shutdowntime").val("");
							}
							
							var week = ["MON","TUE","WED","THU","FRI","SAT","SUN"];
							for(var j=0;j<week.length;j++){
								if( !_.isEmpty( conf_value[week[j]] ) ){
									var thisday = conf_value[week[j]];
									if(thisday.ENABLECLOSE == "1"){
										$("#"+week[j].toLowerCase()+"_"+"enableclose").prop("checked",true).trigger("change");
										var stime = "";
										var etime = "";
										if(!_.isEmpty(thisday.STARTUPTIME) && thisday.STARTUPTIME.length >=6){
											stime += thisday.STARTUPTIME.substring(0,2) + ":";
											stime += thisday.STARTUPTIME.substring(2,4) + ":";
											stime += thisday.STARTUPTIME.substring(4);
										}
										if(!_.isEmpty(thisday.SHUTDOWNTIME) && thisday.SHUTDOWNTIME.length >=6){
											etime += thisday.SHUTDOWNTIME.substring(0,2) + ":";
											etime += thisday.SHUTDOWNTIME.substring(2,4) + ":";
											etime += thisday.SHUTDOWNTIME.substring(4);
										}
										$("#"+week[j].toLowerCase()+"_"+"startuptime").val(stime);
										$("#"+week[j].toLowerCase()+"_"+"shutdowntime").val(etime);
										
									}else{
										$("#"+week[j].toLowerCase()+"_"+"enableclose").prop("checked",false).trigger("change");
										$("#"+week[j].toLowerCase()+"_"+"startuptime").val("");
										$("#"+week[j].toLowerCase()+"_"+"shutdowntime").val("");
									}
								}
							}
							continue;
						}
						
						// 打印配置
						if(conf_type=="print_config"){
							var keys = _.keys(conf_value);
							for(var j=0;j<keys.length;j++){
								var thistype = keys[j].toLowerCase();
								var thisobj = conf_value[keys[j]];
								if(_.isEmpty(thisobj)) continue;
								
								if(thistype=="head" || thistype=="tail" || thistype=="content"){
									$("#"+thistype+"_font").val(thisobj.FONT);	
									$("#"+thistype+"_size").val(thisobj.SIZE);	
								}else{
									// 打印类型 
									$("input[name='"+thistype+"_type'][value='"+thisobj.TYPE+"']").trigger("change");
									// 超时打印 
									if(thisobj.TIMEOUT=="1"){
										$("#"+thistype+"_"+"timeout").prop("checked",true).trigger("change");
									}else{
										$("#"+thistype+"_"+"timeout").prop("checked",false).trigger("change");
									}
									
									// 打印日期描述，时间描述，凭证头，凭证尾 
									$("#"+thistype+"_"+"date").val(thisobj.DATE);
									$("#"+thistype+"_"+"time").val(thisobj.TIME);
									$("#"+thistype+"_"+"head").val(thisobj.HEAD);
									$("#"+thistype+"_"+"tail").val(thisobj.TAIL);
									
									// 换汇凭证 
									if(thistype == "checkin"){
										$("#"+thistype+"_"+"barcode").val(thisobj.BARCODE);
										$("#"+thistype+"_"+"title").val(thisobj.TITLE);
										
										if(thisobj.ENABLEAMOUNT == "1"){
											$("#"+thistype+"_"+"enableamount").prop("checked",true).trigger("change");
											$("#"+thistype+"_"+"amount").val(thisobj.AMOUNT);
										}else{
											$("#"+thistype+"_"+"enableamount").prop("checked",false).trigger("change");
											$("#"+thistype+"_"+"amount").val("");
										}
										
										if(thisobj.ENABLECOMMENT == "1"){
											$("#"+thistype+"_"+"enablecomment").prop("checked",true).trigger("change");
											$("#"+thistype+"_"+"comment").val(thisobj.COMMENT);
										}else{
											$("#"+thistype+"_"+"enablecomment").prop("checked",false).trigger("change");
											$("#"+thistype+"_"+"comment").val("");
										}
									}
									// 借出凭证 
									if(thistype == "checkout") {
										$("#"+thistype+"_"+"patron").val(thisobj.PATRON);
										$("#"+thistype+"_"+"barcode").val(thisobj.BARCODE);
										$("#"+thistype+"_"+"title").val(thisobj.TITLE);
										$("#"+thistype+"_"+"duedate").val(thisobj.DUEDATE);
										
										if(thisobj.ENABLENAME == "1"){
											$("#"+thistype+"_"+"enablename").prop("checked",true).trigger("change");
											$("#"+thistype+"_"+"name").val(thisobj.NAME);
										}else{
											$("#"+thistype+"_"+"enablename").prop("checked",false).trigger("change");
											$("#"+thistype+"_"+"name").val("");
										}
										if(thisobj.ENABLEAMOUNT == "1"){
											$("#"+thistype+"_"+"enableamount").prop("checked",true).trigger("change");
											$("#"+thistype+"_"+"amount").val(thisobj.AMOUNT);
										}else{
											$("#"+thistype+"_"+"enableamount").prop("checked",false).trigger("change");
											$("#"+thistype+"_"+"amount").val("");
										}
										if(thisobj.ENABLEFEE == "1"){
											$("#"+thistype+"_"+"enablefee").prop("checked",true).trigger("change");
											$("#"+thistype+"_"+"fee").val(thisobj.FEE);
										}else{
											$("#"+thistype+"_"+"enablefee").prop("checked",false).trigger("change");
											$("#"+thistype+"_"+"fee").val("");
										}
									}
									// 续借凭证 
									if(thistype == "renew") {
										$("#"+thistype+"_"+"patron").val(thisobj.PATRON);
										$("#"+thistype+"_"+"barcode").val(thisobj.BARCODE);
										$("#"+thistype+"_"+"title").val(thisobj.TITLE);
										$("#"+thistype+"_"+"duedate").val(thisobj.DUEDATE);
										
										// 续借书描述  
										if(thisobj.ENABLERENEWBIBLIO == "1"){
											$("#"+thistype+"_"+"enablerenewbiblio").prop("checked",true).trigger("change");
											$("#"+thistype+"_"+"renewbiblio").val(thisobj.RENEWBIBLIO);
										}else{
											$("#"+thistype+"_"+"enablerenewbiblio").prop("checked",false).trigger("change");
											$("#"+thistype+"_"+"renewbiblio").val("");
										}
										// 续借数描述 
										if(thisobj.ENABLERENEWCOUNT == "1"){
											$("#"+thistype+"_"+"enablerenewcount").prop("checked",true).trigger("change");
											$("#"+thistype+"_"+"renewcount").val(thisobj.RENEWCOUNT);
										}else{
											$("#"+thistype+"_"+"enablerenewcount").prop("checked",false).trigger("change");
											$("#"+thistype+"_"+"renewcount").val("");
										}
										// 未续借书描述 
										if(thisobj.ENABLEUNRENEWBIBLIO == "1"){
											$("#"+thistype+"_"+"enableunrenewbiblio").prop("checked",true).trigger("change");
											$("#"+thistype+"_"+"unrenewbiblio").val(thisobj.UNRENEWBIBLIO);
										}else{
											$("#"+thistype+"_"+"enableunrenewbiblio").prop("checked",false).trigger("change");
											$("#"+thistype+"_"+"unrenewbiblio").val("");
										}
										// 未续借数描述 
										if(thisobj.ENABLEUNRENEWCOUNT == "1"){
											$("#"+thistype+"_"+"enableunrenewcount").prop("checked",true).trigger("change");
											$("#"+thistype+"_"+"unrenewcount").val(thisobj.UNRENEWCOUNT);
										}else{
											$("#"+thistype+"_"+"enableunrenewcount").prop("checked",false).trigger("change");
											$("#"+thistype+"_"+"unrenewcount").val("");
										}
									}
									// 收款打印凭证 
									if(thistype == "pay") {
										$("#"+thistype+"_"+"patron").val(thisobj.PATRON);
										$("#"+thistype+"_"+"fee").val(thisobj.FEE);
										
										if(thisobj.ENABLENAME == "1"){
											$("#"+thistype+"_"+"enablename").prop("checked",true).trigger("change");
											$("#"+thistype+"_"+"name").val(thisobj.NAME);
										}else{
											$("#"+thistype+"_"+"enablename").prop("checked",false).trigger("change");
											$("#"+thistype+"_"+"name").val("");
										}
										
										if(thisobj.ENABLEIDCARD == "1"){
											$("#"+thistype+"_"+"enableidcard").prop("checked",true).trigger("change");
											$("#"+thistype+"_"+"idcard").val(thisobj.IDCARD);
										}else{
											$("#"+thistype+"_"+"enableidcard").prop("checked",false).trigger("change");
											$("#"+thistype+"_"+"idcard").val("");
										}
										
										if(thisobj.ENABLECARDTYPE == "1"){
											$("#"+thistype+"_"+"enablecardtype").prop("checked",true).trigger("change");
											$("#"+thistype+"_"+"cardtype").val(thisobj.CARDTYPE);
										}else{
											$("#"+thistype+"_"+"enablecardtype").prop("checked",false).trigger("change");
											$("#"+thistype+"_"+"cardtype").val("");
										}
										
										if(thisobj.ENABLEDEDUCTION == "1"){
											$("#"+thistype+"_"+"enablededuction").prop("checked",true).trigger("change");
											$("#"+thistype+"_"+"deduction").val(thisobj.DEDUCTION);
										}else{
											$("#"+thistype+"_"+"enablededuction").prop("checked",false).trigger("change");
											$("#"+thistype+"_"+"deduction").val("");
										}
										
										if(thisobj.ENABLEDEPOSIT == "1"){
											$("#"+thistype+"_"+"enabledeposit").prop("checked",true).trigger("change");
											$("#"+thistype+"_"+"deposit").val(thisobj.DEPOSIT);
										}else{
											$("#"+thistype+"_"+"enabledeposit").prop("checked",false).trigger("change");
											$("#"+thistype+"_"+"deposit").val("");
										}
										
										if(thisobj.ENABLEIMPREST == "1"){
											$("#"+thistype+"_"+"enableimprest").prop("checked",true).trigger("change");
											$("#"+thistype+"_"+"imprest").val(thisobj.IMPREST);
										}else{
											$("#"+thistype+"_"+"enabledeposit").prop("checked",false).trigger("change");
											$("#"+thistype+"_"+"imprest").val("");
										}
									}
									// 取款箱 
									if(thistype == "cashbin") {
										$("#"+thistype+"_"+"operator").val(thisobj.OPERATOR);
										$("#"+thistype+"_"+"notevalue").val(thisobj.NOTEVALUE);
										$("#"+thistype+"_"+"notecount").val(thisobj.NOTECOUNT);
										$("#"+thistype+"_"+"money").val(thisobj.MONEY);
										$("#"+thistype+"_"+"bincount").val(thisobj.BINCOUNT);
										$("#"+thistype+"_"+"binmoney").val(thisobj.BINMONEY);
									}
									// 取书箱 
									if(thistype == "bookbin") {
										$("#"+thistype+"_"+"operator").val(thisobj.OPERATOR);
										$("#"+thistype+"_"+"binno").val(thisobj.BINNO);
										$("#"+thistype+"_"+"amount").val(thisobj.AMOUNT);
										$("#"+thistype+"_"+"tolamount").val(thisobj.TOLAMOUNT);
									}
									// 补取书凭证 
									if(thistype == "bookrack") {
										$("#"+thistype+"_"+"operator").val(thisobj.OPERATOR);
										$("#"+thistype+"_"+"amount").val(thisobj.AMOUNT);
									}
									// 补取卡凭证 
									if(thistype == "cardbin") {
										$("#"+thistype+"_"+"operator").val(thisobj.OPERATOR);
										$("#"+thistype+"_"+"amount").val(thisobj.AMOUNT);
									}
								}
							}
						}
						
						// 办证配置 
						if(conf_type=="register_config"){
							if(conf_value.ENABLEIDCARD == "1"){
								$("#register_enableidcard").prop("checked",true).trigger("change");
							}else{
								$("#register_enableidcard").prop("checked",false).trigger("change");
							}
							
							if(conf_value.ENABLEOTHERCARD == "1"){
								$("#register_enableothercard").prop("checked",true).trigger("change");
							}else{
								$("#register_enableothercard").prop("checked",false).trigger("change");
							}
							
							if(conf_value.ENABLETEMPCARD == "1"){
								$("#register_enabletempcard").prop("checked",true).trigger("change");
								$("#register_tempcardid").val(conf_value.TEMPCARDID);
							}else{
								$("#register_enabletempcard").prop("checked",false).trigger("change");
								$("#register_tempcardid").val("");
							}
						}
						
						// 本地数据库配置 
						if(conf_type=="localdb_config"){
							$("#localdb_ip").val(conf_value.IP);
							$("#localdb_port").val(conf_value.PORT);
							$("#localdb_user").val(conf_value.USER);
							$("#localdb_pwd").val(conf_value.PWD);
						}
						
						// 云中心配置 
						if(conf_type=="center_config"){
							$("#center_ip").val(conf_value.IP);
							$("#center_port").val(conf_value.PORT);
						}
						
						// ACS服务配置 
						if(conf_type=="ACS_config"){
							$("input[name='ACS_sipversion'][value='"+conf_value.SIPVERSION+"']").trigger("change");
							$("#ACS_charset").val(conf_value.CHARSET);
							$("#ACS_currency").val(conf_value.CURRENCY);
							if(conf_value.NEEDLOGIN == "1"){
								$("#ACS_needlogin").prop("checked",true).trigger("change");
							}else{
								$("#ACS_needlogin").prop("checked",false).trigger("change");
							}
							
							if(conf_value.DEFAULTDECOLLATOR == "1"){
								$("#ACS_defaultdecollator").prop("checked",true).trigger("change");
								$("#ACS_decollator").val(conf_value.DECOLLATOR);
							}else{
								$("#ACS_defaultdecollator").prop("checked",false).trigger("change");
								$("#ACS_decollator").val("");
							}
							
							if(conf_value.ENABLECMD63 == "1"){
								$("#ACS_enablecmd63").prop("checked",true).trigger("change");
							}else{
								$("#ACS_enablecmd63").prop("checked",false).trigger("change");
							}
						}
					}
				}
				
				
			}
		}
	}
	
    // 运行配置保存事件  
	$("#runTempSave").on("click",function(){
		startSaveRun();
// 		$(".shade").eq(1).trigger("click");
		var detailList = [];
		if(_.isEmpty(runTempObj)){ 
			
		}
		var _obj={};
		if(!_.isEmpty(runTempObj) && !_.isEmpty(runTempObj.runDetailList)){
			var list = runTempObj.runDetailList;
			for(var i=0;i<list.length;i++){
				_obj[list[i].run_conf_type] = jsonToObj(list[i].run_conf_value);
			}
		}
		$("#run_right_form .right-content").each(function(){
			var obj = {};
			var thistype = $(this).attr("data-type");
			var conftype = "";
			var confvalue = {};
			
			// 基础配置，language_config、runlog_config 
			if(thistype=="basic"){
				confvalue.LANGUAGE = $("#basic_language").val();
				obj.run_conf_type = "language_config";
				obj.run_conf_value = confvalue;
				detailList.push(obj);
				
				obj = {};
				confvalue = {};
				confvalue.RUNLOG_LEVEL = $("input[name='runlog_level']:checked").val();
				if($("#file").prop("checked")){
					confvalue.RUNLOG_TYPE = "file";
					confvalue.RUNLOG_FILESIZE = $("#runlog_filesize").val().replace(/[a-zA-Z]*/g,"")+"M";
				}else{
					confvalue.RUNLOG_TYPE = "";
					confvalue.RUNLOG_FILESIZE = "";
				}
				obj.run_conf_type = "runlog_config";
				obj.run_conf_value = confvalue;
				detailList.push(obj);
				return true;
			}
			
			// 功能配置function_config 
			if(thistype=="function"){
				conftype = thistype+"_config";
				confvalue.DAILYCHECKOUTLIMIT = $("#function_dailycheckoutlimit").val();
				confvalue.SORTTYPE = $("input[name='function_sorttype']:checked").val();
				
				var pin = {};
				var func = {};
				var card = {};
				var book = {};
				
				pin.TYPE = $("input[name='pin_type']:checked").val();
				pin.INPUT = $("input[name='pin_input']:checked").val();
				pin.RETRYDO = $("input[name='pin_retrydo']:checked").val();
				pin.RETRYTIMES = $("#pin_retrytimes").val();
				pin.MAXLENGTH = $("#pin_maxlength").val();
				pin.MINLENGTH = $("#pin_minlength").val();
				
				$("input[type='checkbox'][id^='function_']").each(function(){
					var thisid = $(this).attr("id");
					var thisname = thisid.substring(thisid.indexOf("_")+1).toUpperCase();
					if($(this).prop("checked")){
						func[thisname] = "1";
					}else{
						func[thisname] = "0";
					}
				});
				
				$("input[type='checkbox'][id^='card_']").each(function(){
					var thisid = $(this).attr("id");
					var thisname = thisid.substring(thisid.indexOf("_")+1).toUpperCase();
					if($(this).prop("checked")){
						card[thisname] = "1";
					}else{
						card[thisname] = "0";
					}
				});
				
				$("input[type='checkbox'][id^='book_']").each(function(){
					var thisid = $(this).attr("id");
					var thisname = thisid.substring(thisid.indexOf("_")+1).toUpperCase();
					if($(this).prop("checked")){
						book[thisname] = "1";
					}else{
						book[thisname] = "0";
					}
				});
				
				confvalue.PIN = pin;				
				confvalue.FUNCTION = func;				
				confvalue.CARDINPUT = card;				
				confvalue.BOOKINPUT = book;		
				
				obj.run_conf_type = conftype;
				obj.run_conf_value = confvalue;
				detailList.push(obj);
				
			}
			
			// 超时配置timeout_config 
			if(thistype == "timeout"){
				conftype = thistype + "_config";
				
				if($("#playsound").prop("checked")){
					confvalue.PLAYSOUND = "1";
				}else{
					confvalue.PLAYSOUND = "0";
				}
				
				if($("#display").prop("checked")){
					confvalue.DISPLAY = "1";
				}else{
					confvalue.DISPLAY = "0";
				}
				
				var info = {};
				var timed = {};
				var wait = {};
				var switchscreen = {};
				
				$("select[id^='info_']").each(function(){
					var thisid = $(this).attr("id");
					var thisname = thisid.substring(thisid.indexOf("_")+1).toUpperCase();
					info[thisname] = $(this).val()+"s";
				});
				
				$("select[id^='timed_']").each(function(){
					var thisid = $(this).attr("id");
					var thisname = thisid.substring(thisid.indexOf("_")+1).toUpperCase();
					timed[thisname] = $(this).val()+"s";
				});
				
				$("select[id^='wait_']").each(function(){
					var thisid = $(this).attr("id");
					var thisname = thisid.substring(thisid.indexOf("_")+1).toUpperCase();
					wait[thisname] = $(this).val()+"s";
				});
				
				if($("#switchscreen_enable").prop("checked")){
					switchscreen.ENABLE = "1"; 
					switchscreen.CYCLE = $("#switchscreen_cycle").val()+"min";
					switchscreen.WAITTIME = $("#switchscreen_waittime").val()+"s";
				}else{
					switchscreen.ENABLE = "0"; 
					switchscreen.CYCLE = "0min";
					switchscreen.WAITTIME = "0s";
				}
				
				confvalue.INFO = info;
				confvalue.TIMED = timed;
				confvalue.WAIT = wait;
				confvalue.SWITCHSCREEN = switchscreen;
				
				obj.run_conf_type = conftype;
				obj.run_conf_value = confvalue;
				detailList.push(obj);
				
			}
			
			// 定时任务配置scheduletask_config 
			if(thistype == "scheduletask"){
				conftype = thistype + "_config";
				if($("#enableweekschedler").prop("checked")){
					confvalue.ENABLEWEEKSCHEDLER = "1";
				}else{
					confvalue.ENABLEWEEKSCHEDLER = "0";
				}
				
				if($("#enablebalance").prop("checked")){
					confvalue.ENABLEBALANCE = "1";
					confvalue.BALANCETIME = $("#balancetime").val().replace(/:/g,"");
				}else{
					confvalue.ENABLEBALANCE = "0";
					confvalue.BALANCETIME = "";
				}
				
				if($("#enablereboot").prop("checked")){
					confvalue.ENABLEREBOOT = "1";
					confvalue.REBOOTTIME = $("#reboottime").val().replace(/:/g,"");
				}else{
					confvalue.ENABLEREBOOT = "0";
					confvalue.REBOOTTIME = "";
				}
				
				if($("#enableshutdown").prop("checked")){
					confvalue.ENABLESHUTDOWN = "1";
					confvalue.SHUTDOWNTIME = $("#shutdowntime").val().replace(/:/g,"");
				}else{
					confvalue.ENABLESHUTDOWN = "0";
					confvalue.SHUTDOWNTIME = "";
				}
				
				var week = ["MON","TUE","WED","THU","FRI","SAT","SUN"];
				for(var i=0;i<week.length;i++){
					var thisday = week[i];
					var thisd = thisday.toLowerCase();
					var day = {}
					if ($("#"+thisd+"_enableclose").prop("checked")){
						day.ENABLECLOSE = "1";
						day.STARTUPTIME = $("#"+thisd+"_startuptime").val().replace(/:/g,"");
						day.SHUTDOWNTIME = $("#"+thisd+"_shutdowntime").val().replace(/:/g,"");
					}else{
						day.ENABLECLOSE = "0";
						day.STARTUPTIME = "";
						day.SHUTDOWNTIME = "";
					}
					confvalue[thisday] = day;
				}
				
				obj.run_conf_type = conftype;
				obj.run_conf_value = confvalue;
				detailList.push(obj);
				
			}
			
			// 打印机配置 print_config 
			if(thistype == "print"){
				conftype = thistype + "_config";
				
				var head = {};
				var tail = {};
				var content = {};
				
				$("div[data-type='print'] div[data-type]").each(function(){
					var ptype = $(this).attr("data-type");
					var thisobj = {};
					if(ptype == "head"){
						head.FONT = $("#head_font").val();
						head.SIZE = $("#head_size").val();
						
						tail.FONT = $("#tail_font").val();
						tail.SIZE = $("#tail_size").val();
						
						content.FONT = $("#content_font").val();
						content.SIZE = $("#content_size").val();
						
						confvalue.HEAD = head;
						confvalue.TAIL = tail;
						confvalue.CONTENT = content;
					}else{
						thisobj.TYPE = $("input[name='"+ptype+"_type']:checked").val();
						if($("#"+ptype+"_"+"timeout").prop("checked")){
							thisobj.TIMEOUT = "1";
						}else{
							thisobj.TIMEOUT = "0";
						}
						thisobj.DATE = $("#"+ptype+"_"+"date").val();
						thisobj.TIME = $("#"+ptype+"_"+"time").val();
						thisobj.HEAD = $("#"+ptype+"_"+"head").val();
						thisobj.TAIL = $("#"+ptype+"_"+"tail").val();
							
						if(ptype == "checkin"){
							thisobj.BARCODE = $("#"+ptype+"_"+"barcode").val();
							thisobj.TITLE = $("#"+ptype+"_"+"title").val();
							if( $("#"+ptype+"_"+"enableamount").prop("checked")){
								thisobj.ENABLEAMOUNT = "1";
								thisobj.AMOUNT = $("#"+ptype+"_"+"amount").val();
							}else{
								thisobj.ENABLEAMOUNT = "0";
								thisobj.AMOUNT = "";
							}
							if( $("#"+ptype+"_"+"enablecomment").prop("checked")){
								thisobj.ENABLECOMMENT = "1";
								thisobj.COMMENT = $("#"+ptype+"_"+"comment").val();
							}else{
								thisobj.ENABLECOMMENT = "0";
								thisobj.COMMENT = "";
							}
						}
						
						if(ptype == "checkout"){
							thisobj.BARCODE = $("#"+ptype+"_"+"barcode").val();
							thisobj.TITLE = $("#"+ptype+"_"+"title").val();
							thisobj.PATRON = $("#"+ptype+"_"+"patron").val();
							thisobj.DUEDATE = $("#"+ptype+"_"+"duedate").val();
							
							if( $("#"+ptype+"_"+"enablename").prop("checked")){
								thisobj.ENABLENAME = "1";
								thisobj.NAME = $("#"+ptype+"_"+"name").val();
							}else{
								thisobj.ENABLENAME = "0";
								thisobj.NAME = "";
							}
							
							if( $("#"+ptype+"_"+"enableamount").prop("checked")){
								thisobj.ENABLEAMOUNT = "1";
								thisobj.AMOUNT = $("#"+ptype+"_"+"amount").val();
							}else{
								thisobj.ENABLEAMOUNT = "0";
								thisobj.AMOUNT = "";
							}
							
							if( $("#"+ptype+"_"+"enablefee").prop("checked")){
								thisobj.ENABLEFEE = "1";
								thisobj.FEE = $("#"+ptype+"_"+"fee").val();
							}else{
								thisobj.ENABLEFEE = "0";
								thisobj.FEE = "";
							}
							
						}
						if(ptype == "renew"){
							thisobj.BARCODE = $("#"+ptype+"_"+"barcode").val();
							thisobj.TITLE = $("#"+ptype+"_"+"title").val();
							thisobj.PATRON = $("#"+ptype+"_"+"patron").val();
							thisobj.DUEDATE = $("#"+ptype+"_"+"duedate").val();

							if( $("#"+ptype+"_"+"enablerenewbiblio").prop("checked")){
								thisobj.ENABLERENEWBIBLIO = "1";
								thisobj.RENEWBIBLIO = $("#"+ptype+"_"+"renewbiblio").val();
							}else{
								thisobj.ENABLERENEWBIBLIO = "0";
								thisobj.RENEWBIBLIO = "";
							}

							if( $("#"+ptype+"_"+"enableunrenewbiblio").prop("checked")){
								thisobj.ENABLEUNRENEWBIBLIO = "1";
								thisobj.UNRENEWBIBLIO = $("#"+ptype+"_"+"unrenewbiblio").val();
							}else{
								thisobj.ENABLEUNRENEWBIBLIO = "0";
								thisobj.UNRENEWBIBLIO = "";
							}

							if( $("#"+ptype+"_"+"enablerenewcount").prop("checked")){
								thisobj.ENABLERENEWCOUNT = "1";
								thisobj.RENEWCOUNT= $("#"+ptype+"_"+"renewcount").val();
							}else{
								thisobj.ENABLERENEWCOUNT = "0";
								thisobj.RENEWCOUNT = "";
							}

							if( $("#"+ptype+"_"+"enableunrenewcount").prop("checked")){
								thisobj.ENABLEUNRENEWCOUNT = "1";
								thisobj.UNRENEWCOUNT = $("#"+ptype+"_"+"unrenewcount").val();
							}else{
								thisobj.ENABLEUNRENEWCOUNT = "0";
								thisobj.UNRENEWCOUNT = "";
							}
						}
						if(ptype == "pay"){
							thisobj.PATRON = $("#"+ptype+"_"+"patron").val();
							thisobj.FEE = $("#"+ptype+"_"+"fee").val();
							
							if( $("#"+ptype+"_"+"enablename").prop("checked")){
								thisobj.ENABLENAME = "1";
								thisobj.NAME = $("#"+ptype+"_"+"name").val();
							}else{
								thisobj.ENABLENAME = "0";
								thisobj.NAME = "";
							}
							
							if( $("#"+ptype+"_"+"enableidcard").prop("checked")){
								thisobj.ENABLEIDCARD = "1";
								thisobj.IDCARD = $("#"+ptype+"_"+"idcard").val();
							}else{
								thisobj.ENABLEIDCARD = "0";
								thisobj.IDCARD = "";
							}
							
							if( $("#"+ptype+"_"+"enablecardtype").prop("checked")){
								thisobj.ENABLECARDTYPE = "1";
								thisobj.CARDTYPE = $("#"+ptype+"_"+"cardtype").val();
							}else{
								thisobj.ENABLECARDTYPE = "0";
								thisobj.CARDTYPE = "";
							}
							
							if( $("#"+ptype+"_"+"enablededuction").prop("checked")){
								thisobj.ENABLEDEDUCTION = "1";
								thisobj.DEDUCTION = $("#"+ptype+"_"+"deduction").val();
							}else{
								thisobj.ENABLEDEDUCTION = "0";
								thisobj.DEDUCTION = "";
							}
							
							if( $("#"+ptype+"_"+"enabledeposit").prop("checked")){
								thisobj.ENABLEDEPOSIT = "1";
								thisobj.DEPOSIT = $("#"+ptype+"_"+"deposit").val();
							}else{
								thisobj.ENABLEDEPOSIT = "0";
								thisobj.DEPOSIT = "";
							}
							
							if( $("#"+ptype+"_"+"enableimprest").prop("checked")){
								thisobj.ENABLEIMPREST = "1";
								thisobj.IMPREST = $("#"+ptype+"_"+"imprest").val();
							}else{
								thisobj.ENABLEIMPREST = "0";
								thisobj.IMPREST = "";
							}
						}
						
						if(ptype == "cashbin"){
							thisobj.OPERATOR = $("#"+ptype+"_"+"operator").val();
							thisobj.NOTEVALUE = $("#"+ptype+"_"+"notevalue").val();
							thisobj.NOTECOUNT = $("#"+ptype+"_"+"notecount").val();
							thisobj.MONEY = $("#"+ptype+"_"+"money").val();
							thisobj.BINCOUNT = $("#"+ptype+"_"+"bincount").val();
							thisobj.BINMONEY = $("#"+ptype+"_"+"binmoney").val();
						}
						
						if(ptype == "bookbin"){
							thisobj.OPERATOR = $("#"+ptype+"_"+"operator").val();
							thisobj.BINNO = $("#"+ptype+"_"+"binno").val();
							thisobj.AMOUNT = $("#"+ptype+"_"+"amount").val();
							thisobj.TOLAMOUNT = $("#"+ptype+"_"+"tolamount").val();
						}
						
						if(ptype == "bookrack"){
							thisobj.OPERATOR = $("#"+ptype+"_"+"operator").val();
							thisobj.AMOUNT = $("#"+ptype+"_"+"amount").val();
						}
						
						if(ptype == "cardbin"){
							thisobj.OPERATOR = $("#"+ptype+"_"+"operator").val();
							thisobj.AMOUNT = $("#"+ptype+"_"+"amount").val();
						}
						
						confvalue[ptype.toUpperCase()] = thisobj;
					}

				});
				
				
				obj.run_conf_type = conftype;
				obj.run_conf_value = confvalue;
				detailList.push(obj);
				
			}
			
			// 办证配置 register_config 
			if(thistype == "register"){
				conftype = thistype + "_config";
				if($("#"+thistype+"_"+"enableidcard").prop("checked")){
					confvalue.ENABLEIDCARD = "1";
				}else{
					confvalue.ENABLEIDCARD = "0";
				}
				
				if($("#"+thistype+"_"+"enableothercard").prop("checked")){
					confvalue.ENABLEOTHERCARD = "1";
				}else{
					confvalue.ENABLEOTHERCARD = "0";
				}
				
				if($("#"+thistype+"_"+"enabletempcard").prop("checked")){
					confvalue.ENABLETEMPCARD = "1";
					confvalue.TEMPCARDID = $("#"+thistype+"_"+"tempcardid").val();
				}else{
					confvalue.ENABLETEMPCARD = "0";
					confvalue.TEMPCARDID = "";
				}
				
				obj.run_conf_type = conftype;
				obj.run_conf_value = confvalue;
				detailList.push(obj);
			}
			// 本地数据库配置 localdb_config 
			if(thistype == "localdb"){
				conftype = thistype + "_config";
				confvalue.IP = $("#"+thistype+"_"+"ip").val();
				confvalue.PORT = $("#"+thistype+"_"+"port").val();
				confvalue.USER = $("#"+thistype+"_"+"user").val();
				confvalue.PWD = $("#"+thistype+"_"+"pwd").val();
				
				obj.run_conf_type = conftype;
				obj.run_conf_value = confvalue;
				detailList.push(obj);
			}
			// 云中心配置 center_config 
			if(thistype == "center"){
				conftype = thistype + "_config";
				confvalue.IP = $("#"+thistype+"_"+"ip").val();
				confvalue.PORT = $("#"+thistype+"_"+"port").val();
				
				obj.run_conf_type = conftype;
				obj.run_conf_value = confvalue;
				detailList.push(obj);
			}
			
			//  acs服务配置 ACS_config 
			if(thistype == "ACS"){
				conftype = thistype + "_config";
				confvalue.CHARSET =  $("#"+thistype+"_"+"charset").val();
				confvalue.CURRENCY =  $("#"+thistype+"_"+"currency").val();
				confvalue.SIPVERSION =  $("input[name='ACS_sipversion']:checked").val();
				if($("#"+thistype+"_"+"defaultdecollator").prop("checked")){
					confvalue.DEFAULTDECOLLATOR =  "1";
					confvalue.DECOLLATOR =  $("#"+thistype+"_"+"decollator").val();
				}else{
					confvalue.DEFAULTDECOLLATOR =  "0";
					confvalue.DECOLLATOR = "";
					
				}
				if($("#"+thistype+"_"+"enablecmd63").prop("checked")){
					confvalue.ENABLECMD63 =  "1";
				}else{
					confvalue.ENABLECMD63 =  "0";
					
				}
				if($("#"+thistype+"_"+"needlogin").prop("checked")){
					confvalue.NEEDLOGIN =  "1";
				}else{
					confvalue.NEEDLOGIN =  "0";
					
				}
				
				obj.run_conf_type = conftype;
				obj.run_conf_value = confvalue;
				detailList.push(obj);
			}
		})
		
		runTempSubmit.runDetailList = detailList;
		
		compareRunTemp();
		
		endSaveRun();
		
	})
	
	// 对比数据，判断是否使用模板 
	function compareRunTemp(){
		
		if(!_.isEmpty(runTempObj) && !_.isEmpty(runTempSubmit)){
			var idx = runTempObj.device_tpl_idx;
			if(runTempObj.runDetailList.length==runTempSubmit.runDetailList.length){
				var list1 = runTempObj.runDetailList;
				var list2 = runTempSubmit.runDetailList;
				
				// 按照类型排序 
				list1 = _.sortBy(list1,"run_conf_type");
				list2 = _.sortBy(list2,"run_conf_type");
				
				for(var i=0;i<list1.length;i++){
					var conf_type1 = list1[i].run_conf_type;
					var conf_type2 = list2[i].run_conf_type;
					
					if(conf_type1!=conf_type2){
						// 如果不相等相等，则使用自定义数据 
						runTempSubmit.device_tpl_type = "1";
						runTempCustom[idx] = runTempSubmit;// 保存到该选项的自定义数据中 
						runTempType("1");
						break;
					}
					
					var conf_value1 = list1[i].run_conf_value;
					var conf_value2 = list2[i].run_conf_value;
					
					if(typeof conf_value1 != 'object'){
						conf_value1 = jsonToObj(conf_value1);
					}
					
					var eql = _.isEqual(conf_value1,conf_value2);
					if(!eql){
						// 如果不相等相等，则使用自定义数据 
						runTempSubmit.device_tpl_type = "1";
						runTempCustom[idx] = runTempSubmit;// 保存到该选项的自定义数据中 
						runTempType("1");
						break;
					}else{
						
					}
					// 如果相等，则使用模板 
					runTempSubmit.device_tpl_type = "0";
					delete runTempCustom[idx];// 删除该选项的自定义数据 
					runTempType("0");
				}
			}else{
				// 如果不相等相等，则使用自定义数据 
				runTempSubmit.device_tpl_type = "1";
				runTempCustom[idx] = runTempSubmit;// 保存到该选项的自定义数据中 
				runTempType("1");
			}
		}else{
			layer.alert("请选择模板");
		}
	}
	
	//获取运行配置模板
	function getRunConfList(id){
		if(id=="-1"){
			$("#runTemp").html("<option value=\"-1\">自定义参数</option>");
		}else{
			$.ajax({
				url:basePath+"device/getAllRunTempList",
				type:"POST",
				async:false,
				data:{json:id},
				success:function(data){
					if(data!=null && data.state){
						
						var list = data.result; 
						runTempList = data.result;
						var html = "<option value=\"-1\">自定义参数</option>";
						if(list!=null){
							for (var i = 0; i < list.length; i++) {
								var item = list[i];
									html+="<option value=\""+item.device_tpl_idx+"\">"+item.device_tpl_ID+"-"+item.device_tpl_desc+"</option>";
							}
							$("#runTemp").html(html);
						}
					}else{
						
						console.log(data.message);
					}
				}
				
			});
		}
	}
	
	// 获取监控配置模板
	function getMonitorConfList(id){
		if(id=="-1"){
			$("#monitorTemp").html("<option value=\"-1\">自定义参数</option>");
		}else{
			$.ajax({
				url:basePath+"device/getAllMonitorTempList",
				type:"POST",
				async:false,
				data:{json:id},
				success:function(data){
					if(data!=null && data.state){
						
						var list = data.result; 
						monitorTempList = data.result;
						var html = "<option value=\"-1\">自定义参数</option>";
						if(list!=null){
							for (var i = 0; i < list.length; i++) {
								var item = list[i];
							
								html+="<option value=\""+item.device_mon_tpl_idx+"\">"+item.device_mon_tpl_id+"-"+item.device_mon_tpl_desc+"</option>";
							}
							$("#monitorTemp").html(html);
						}
					}else{
						console.log(data.message);
					}
				}
				
			});
		}
	}
	
	//获取数据同步配置模板
	function getDbSyncConfList(id){
		if(id=="-1"){
			$("#dbSyncTemp").html("<option value=\"-1\">自定义参数</option>");
		}else{
			$.ajax({
				url:basePath+"device/getAllDbSyncTempList",
				type:"POST",
				async:false,
				data:{json:id},
				success:function(data){
					if(data!=null && data.state){
						var list = data.result; 
						dbsyncTempList = data.result;
						var html = "<option value=\"-1\">自定义参数</option>";
						if(list!=null){
							for (var i = 0; i < list.length; i++) {
								var item = list[i];
									html+="<option value=\""+item.device_tpl_idx+"\">"+item.device_tpl_ID+"-"+item.device_tpl_desc+"</option>";	
							}
							$("#dbSyncTemp").html(html);
						}
					}else{
						console.log(data.message);
					}
				}
				
			});
		}
	}
	// 输入框错误提示信息 
	function errorMsg(str){
		var html = "<span class=\"error-msg\">"+str+"</span>";
		return html;
	}
	
	// 输入数据检查 
	function checkData(){
		var deviceCode = $("#deviceCode").val();
		var libId = $("#libId").val();
		var deviceId = $("#deviceId").val();
		var deviceName = $("#deviceName").val();
		var deviceType = $("#deviceType").val();
		var acsName = $("#acsName").val();
		var acsPwd = $("#acsPwd").val();
		var logisticsNumber = $("#logisticsNumber").val();
		
		var extTempId = $("#extTemp").val();
		var runTempId = $("#runTemp").val();
		var monitorTempId = $("#monitorTemp").val();
		var dbSyncTempId = $("#dbSyncTemp").val();;
		
		var flag = true;
		
		if(deviceCode==""){ 
			$("#deviceCode").closest("li").addClass("error");
			$("#deviceCode").siblings("").remove();
			$("#deviceCode").after(errorMsg("不能为空"));
			flag = false;
		}else{
			$("#deviceCode").closest("li").removeClass("error");
		}
		if(libId==""){ 
			$("#libId").closest("li").addClass("error");
			$("#libId").siblings("").remove();
			$("#libId").after(errorMsg("不能为空"));
			flag = false;
		}else{
			$("#libId").closest("li").removeClass("error");
		}
		if(deviceId==""){
			$("#deviceId").closest("li").addClass("error");
			$("#deviceId").siblings().remove();
			$("#deviceId").after(errorMsg("不能为空"));
			flag = false;
		}else{
			$("#deviceId").closest("li").removeClass("error");
		}
		
		if(deviceName==""){
			$("#deviceName").closest("li").addClass("error");
			$("#deviceName").siblings().remove();
			$("#deviceName").after(errorMsg("不能为空"));
			flag = false;
		}else{
			$("#deviceName").closest("li").removeClass("error");
		}
		
		//下拉框的样式不太一样，错误信息提示跟父节点同级-
		if(deviceType=="-1"){
			$("#deviceType").closest("li").addClass("error");
			$("#deviceType").parent().siblings().remove();
			$("#deviceType").parent().after(errorMsg("请选择"));
			flag = false;
		}else{
			$("#deviceType").closest("li").removeClass("error");
		}
		
		if(acsName==""){
			$("#acsName").closest("li").addClass("error");
			$("#acsName").siblings().remove();
			$("#acsName").after(errorMsg("不能为空"));
			flag = false;
		}else{
			$("#acsName").closest("li").removeClass("error");
		}
		
		if(acsPwd==""){
			$("#acsPwd").closest("li").addClass("error");
			$("#acsPwd").siblings().remove();
			$("#acsPwd").after(errorMsg("不能为空"));
			flag = false;
		}else{
			$("#acsPwd").closest("li").removeClass("error");
		}
		
		if(logisticsNumber==""){
			$("#logisticsNumber").closest("li").addClass("error");
			$("#logisticsNumber").siblings().remove();
			$("#logisticsNumber").after(errorMsg("不能为空"));
			flag = false;
		}else{
			$("#logisticsNumber").closest("li").removeClass("error");
		}

		//下拉框的样式不太一样，错误信息提示跟父节点同级-
		if(extTempId=="-1"){
			flag = false;
		}else{
		}
		if(runTempId=="-1"){
			flag = false;
		}else{
		}
		if(monitorTempId=="-1"){
			flag = false;
		}else{
		}
		if(dbSyncTempId=="-1"){
			flag = false;
		}else{
		}
		return flag;
	}
	
	
	/* $("#dbsync_right_form .right-content #tongbu").on("change",function(){
		$(this).parents(".g-checkbox").toggleClass("on");
		if($(this).is(":checked")){
			//$("#dbsync_right_form .right-content.right.short-select").removeAttr("style");
			$("#dbsync_right_form .right-content #cycle").removeAttr("disabled");	
		}else{
			//$("#dbsync_right_form .right-content.right.short-select").attr("style","display:none");
			$("#dbsync_right_form .right-content #cycle").attr("disabled","disabled");
		}
		$("#dbsync_right_form .right-content:visible #cycle").change();
    }); */

