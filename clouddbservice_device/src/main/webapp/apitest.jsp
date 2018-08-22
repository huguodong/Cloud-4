<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>Welcome</title>

<link href="bootstrap-3.3.5-dist/css/bootstrap.min.css" rel="stylesheet">
<script src="js/jquery-1.12.2.min.js"></script>
<script src="bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>


</head>
<body style="width: 80%;padding-left: 15%">
	<h1>CloudServer API TEST v1.0</h1>


	<fieldset>
		<div id="legend" class="">
			<legend class=""></legend>
		</div>

		<div class="span6">
			<div class="btn-group">
				<button id="selectText" class="btn btn-default ">Action</button>
				<button data-toggle="dropdown"
					class="btn dropdown-toggle btn-default">
					<span class="caret"></span>
				</button>

				<ul class="dropdown-menu">
				    <li><a href="javascript: void(0)"
						onclick="AddNewDeviceRunTemp(this)">/runconf/AddNewDeviceRunTemp</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="SelDeviceRunTemp(this)">/runconf/SelDeviceRunTemp</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="UpdDeviceRunTemp(this)">/runconf/UpdDeviceRunTemp</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="DelDeviceRunTemp(this)">/runconf/DelDeviceRunTemp</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="AddNewDeviceRunConf(this)">/runconf/AddNewDeviceRunConf</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="UpdDeviceRunConf(this)">/runconf/UpdDeviceRunConf</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="SelDeviceRunConf(this)">/runconf/SelDeviceRunConf</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="DelDeviceRunConf(this)">/runconf/DelDeviceRunConf</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="SelDeviceExtTemp(this)">/extconf/SelDeviceExtTemp</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="AddNewDeviceExtTemp(this)">/extconf/AddNewDeviceExtTemp</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="UpdDeviceExtTemp(this)">/extconf/UpdDeviceExtTemp</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="DelDeviceExtTemp(this)">/extconf/DelDeviceExtTemp</a>
					</li>
					
					<li><a href="javascript: void(0)"
						onclick="AddNewMetadataRunConf(this)">/runconf/AddNewMetadataRunConf</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="DelMetadataRunConf(this)">/runconf/DelMetadataRunConf</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="UpdMetadataRunConf(this)">/runconf/UpdMetadataRunConf</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="SelMetadataRunConf(this)">/runconf/SelMetadataRunConf</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="AddNewMetadataExtModel(this)">/extconf/AddNewMetadataExtModel</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="UpdMetadataExtModel(this)">/extconf/UpdMetadataExtModel</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="DelMetadataExtModel(this)">/extconf/DelMetadataExtModel</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="SelMetadataExtModel(this)">/extconf/SelMetadataExtModel</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="AddNewDeviceExtConfig(this)">/extconf/AddNewDeviceExtConfig</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="UpdDeviceExtConfig(this)">/extconf/UpdDeviceExtConfig</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="DelDeviceExtConfig(this)">/extconf/DelDeviceExtConfig</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="SelDeviceExtConfig(this)">/extconf/SelDeviceExtConfig</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="AddMetadataOrder(this)">/metaorder/AddMetadataOrder</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="UpdMetadataOrder(this)">/metaorder/UpdMetadataOrder</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="DelMetadataOrder(this)">/metaorder/DelMetadataOrder</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="SelMetadataOrder(this)">/metaorder/SelMetadataOrder</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="AddDeviceLogConfig(this)">/devicelogconf/AddDeviceLogConfig</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="UpdDeviceLogConfig(this)">/devicelogconf/UpdDeviceLogConfig</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="DelDeviceLogConfig(this)">/devicelogconf/DelDeviceLogConfig</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="SelectDeviceLogConfig(this)">/devicelogconf/SelectDeviceLogConfig</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="AddMetadataLogicObj(this)">/metalogicobj/AddMetadataLogicObj</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="UpdateMetadataLogicObj(this)">/metalogicobj/UpdateMetadataLogicObj</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="DeleteMetadataLogicObj(this)">/metalogicobj/DeleteMetadataLogicObj</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="SelectMetadataLogicObj(this)">/metalogicobj/SelectMetadataLogicObj</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="AddPatchInfo(this)">/patchinfo/AddPatchInfo</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="UpdPatchInfo(this)">/patchinfo/UpdPatchInfo</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="DeletePatchInfo(this)">/patchinfo/DeletePatchInfo</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="SelectPatchInfo(this)">/patchinfo/SelectPatchInfo</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="AddNewDevice(this)">/device/AddNewDevice</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="UpdDevice(this)">/device/UpdDevice</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="DeleteDevice(this)">/device/DeleteDevice</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="SelectDevice(this)">/device/SelectDevice</a>
					</li>
				</ul>

			</div>


			<!--     <div class="control-group"> -->

			<!-- Text input-->
			<!--           <label class="control-label" for="input01">URL:</label> -->
			<!--           <div class="controls "> -->
			<!--             <input id="url" type="text" placeholder="/operator/addOperator"  class="input-xlarge" style="width: 400px"> -->
			<!--             <p class="help-block"></p> -->
			<!--           </div> -->

			<!--         </div> -->

			<div class="control-group" style="padding-top:20px">

				<!-- Textarea -->
				<label class="control-label">JSON args: eg.
					operInfo={"operator_id":"1"}&deviceInfo={"device_id":"1"}</label>
				<div class="controls">
					<div class="textarea">
						<textarea id="param" type="" class="" cols="100" rows="5"> </textarea>
					</div>
				</div>

			</div>


			<div class="control-group" style="padding-top: 20px">

				<!-- Textarea -->
				<label class="control-label">back</label>
				<div class="controls">
					<div class="textarea">
						<textarea id="result" type="" class="" cols="100" rows="10"
							style="background-color: #fff"> </textarea>
					</div>
				</div>
			</div>


			<div class="control-group" style="float: left;padding-left: 650px">
				<label class="control-label"></label>

				<!-- Button -->
				<div class="controls">
					<button id="submit" onclick="save()" class="btn btn-info">submit</button>
				</div>
			</div>
	</fieldset>



</body>
<script type="text/javascript">
  function save(){
	  var url1 = $("#selectText").text();
	  var param = $("#param").val();
	  if(url1=="URL" || url1==""){return;}
	  if(param==""){return;}
	  url1 = '<%=path%>'+url1;
		$.ajax({
		 url:url1,
		 type:"POST",
		 data:{"json":param},
		 success:function(data){
			 var res = JSON.stringify(data);
			 $("#result").val(res);
		 }
	  }); 

  }
  
  
  function AddNewDeviceRunTemp(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param = {
			  device_tpl_ID:"IDTEST",
			  device_tpl_desc:"测试描述",
			  device_type:"设备类型1"
	  }
	  $("#param").val(JSON.stringify(param));
  }
  
  function AddNewDeviceRunConf(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var timestamp=Date.parse(new Date());
	  var param =[
				 	{
				 		device_tpl_type:0,
				 		device_run_id:5001,
				 		library_idx:1002,
				 		run_config_idx:1,
				 		run_conf_value:'{"SERVER":[{"IP":"10.10.10.12","PORT":"10001","SERVERTYPE":"register","NEEDLOGIN":"1","USER":"root","PWD":"123456","PROTOCOL":"sip2","CHARSET":"UTF-8"}]}'
				 	},
				 	{
				 		device_tpl_type:0,
				 		device_run_id:5001,
				 		library_idx:1002,
				 		run_config_idx:2,
				 		run_conf_value:'{"IP":"127.0.0.1","PORT":"8080"}'
				 	}
			 ];
	  $("#param").val(JSON.stringify(param));
  }
  function SelDeviceRunTemp(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
			  device_tpl_ID:"IDTEST",
			  device_tpl_desc:"测试描述",
			  device_type:"设备类型"	  
	  }
	  $("#param").val(JSON.stringify(param));
  }
  
  function DelDeviceRunTemp(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={device_tpl_idx:3};
	  $("#param").val(JSON.stringify(param));
  }
  function UpdDeviceRunConf(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
			    device_tpl_type:0,
		 		device_run_id:5001,
		 		library_idx:1002,
		 		run_config_idx:1,
		 		run_conf_value:'{"SERVER":[{"IP":"127.0.0.1","PORT":"10001","SERVERTYPE":"register","NEEDLOGIN":"1","USER":"root","PWD":"123456","PROTOCOL":"sip2","CHARSET":"UTF-8"}]}'
	  };
	  $("#param").val(JSON.stringify(param));
  }
  
 function UpdDeviceRunTemp(obj){
	 $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
			 device_tpl_idx:6,
			 device_tpl_ID:"IDTEST55",
			 device_tpl_desc:"测试描述000",
			 device_type:"设备类型454545"	  
	  };
	  $("#param").val(JSON.stringify(param));
 }
  function SelDeviceRunConf(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
			    device_tpl_type:0,
		 		device_run_id:5001,
		 		library_idx:1002,
		 		run_config_idx:1,  
		  };
		  $("#param").val(JSON.stringify(param));
  }
  function DelDeviceRunConf(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
			  device_tpl_type:1,
			  device_run_id:1001,
			  library_idx:2001,
			  run_config_idx:3,  
	  };
	  $("#param").val(JSON.stringify(param));
  }
  function SelDeviceExtTemp(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
			  	device_tpl_idx:1,
				 device_tpl_ID:"EXT0"
		  };
		  $("#param").val(JSON.stringify(param));
  }
  function AddNewDeviceExtTemp(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
			     device_tpl_ID:"EXTIDT55",
				 device_tpl_desc:"EXT测试描述x000",
				 device_type:"EXT设备类型454545"	  
	  };
	  $("#param").val(JSON.stringify(param));
  }
  function UpdDeviceExtTemp(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
				 device_tpl_idx:1,	
			     device_tpl_ID:"EXT0205",
				 device_tpl_desc:"EXT测x000",
				 device_type:"EXT设备类型454545"	  
	  };
	  $("#param").val(JSON.stringify(param));
  }
  function DelDeviceExtTemp(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
			  device_tpl_idx:2
	  };
	  $("#param").val(JSON.stringify(param));
  }
  
  function SelMetadataRunConf(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
			  	meta_run_cfg_idx:2,
				 run_conf_type:"center_config"
		  };
		  $("#param").val(JSON.stringify(param));
  }
  function AddNewMetadataRunConf(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
			     run_conf_type:"测试类型",
				 run_conf_type_desc:"测试描述"	  
	  };
	  $("#param").val(JSON.stringify(param));
  }
  function UpdMetadataRunConf(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
				 meta_run_cfg_idx:1,	
			     run_conf_type:"EXT0205",
				 run_conf_type_desc:"EXT测x000",  
	  };
	  $("#param").val(JSON.stringify(param));
  }
  function DelMetadataRunConf(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
			  meta_run_cfg_idx:2,
	  };
	  $("#param").val(param);
  }
  function SelMetadataExtModel(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
			  	meta_ext_idx:2,
				 
		  };
		  $("#param").val(JSON.stringify(param));
  }
  function AddNewMetadataExtModel(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
			     ext_model:"测试型号",
				 ext_model_desc:"测试描述",
				 ext_model_version:"测试版本",
				 ext_type:"测试类型"					 
	  };
	  $("#param").val(JSON.stringify(param));
  }
  function UpdMetadataExtModel(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
				 meta_ext_idx:1,	
			     ext_model_desc:"EXT0205",
				 ext_type:"EXT测x000",  
	  };
	  $("#param").val(JSON.stringify(param));
  }
  function DelMetadataExtModel(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
			  meta_ext_idx:2
	  };
	  $("#param").val(JSON.stringify(param));
  }
  
  function SelDeviceExtConfig(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
			  	device_ext_id:1,
				 
		  };
		  $("#param").val(JSON.stringify(param));
  }
  function AddNewDeviceExtConfig(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
				library_idx:1,
				device_tpl_type:1,
				device_ext_id:4,
				ext_comm_conf:"测试型号",
				ext_extend_conf:"",
				logic_obj_idx:1,
				ext_model_idx:1,
			    					 
	  };
	  $("#param").val(JSON.stringify(param));
  }
  function UpdDeviceExtConfig(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
				 device_ext_id:1,	
			     logic_obj_idx:2,  
	  };
	  $("#param").val(JSON.stringify(param));
  }
  function DelDeviceExtConfig(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
			  device_ext_id:1
	  };
	  $("#param").val(JSON.stringify(param));
  }
  function SelMetadataOrder(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
			  	order_idx:2,
				order_os:3
		  };
		  $("#param").val(JSON.stringify(param));
  }
  function AddMetadataOrder(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
				
				order_desc:"重置",
				order_cmd:1,
				order_os:3,
			    					 
	  };
	  $("#param").val(JSON.stringify(param));
  }
  function UpdMetadataOrder(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
				 order_idx:2,
				 order_desc:"测试描述",
				 order_cmd:"测试命令",	
			     order_os:2,  
	  };
	  $("#param").val(JSON.stringify(param));
  }
  function DelMetadataOrder(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
			  order_idx:2
	  };
	  $("#param").val(JSON.stringify(param));
  }
  
  function SelectDeviceLogConfig(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
			  	device_idx:2,
				runlog_type:3
		  };
		  $("#param").val(JSON.stringify(param));
  }
  function AddDeviceLogConfig(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
				
				device_idx:2,
				runlog_level:1,
				runlog_type:3,
			    runlog_filesize:2,
				library_idx:1
	  };
	  $("#param").val(JSON.stringify(param));
  }
  function UpdDeviceLogConfig(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
				 device_log_idx:2,
				 runlog_level:3,
			     runlog_type:1 
	  };
	  $("#param").val(JSON.stringify(param));
  }
  function DelDeviceLogConfig(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
			  device_log_idx:3
	  };
	  $("#param").val(JSON.stringify(param));
  }
  
  function SelectMetadataLogicObj(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
			  	meta_log_obj_idx:2,
				logic_obj:"aabb"
		  };
		  $("#param").val(JSON.stringify(param));
  }
  function AddMetadataLogicObj(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
				
				logic_obj:"aabb",
				logic_obj_desc:"测试描述",

	  };
	  $("#param").val(JSON.stringify(param));
  }
  function UpdateMetadataLogicObj(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
				 meta_log_obj_idx:2,
				 logic_obj:"彻彻底底",
				logic_obj_desc:"描述逻辑不见",
	  };
	  $("#param").val(JSON.stringify(param));
  }
  function DeleteMetadataLogicObj	(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
			  meta_log_obj_idx:3
	  };
	  $("#param").val(JSON.stringify(param));
  }
  function SelectPatchInfo(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
			  	patch_idx:2,
				patch_version:"1.0"
		  };
		  $("#param").val(JSON.stringify(param));
  }
  function AddPatchInfo(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
				patch_version:"aabb",
				patch_desc:"测试描述",
				patch_type:"aabb",
				restrict_info:"测试描述",
				patch_directory:"aabb",

	  };
	  $("#param").val(JSON.stringify(param));
  }
  function UpdPatchInfo(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
				 patch_idx:2,
				 patch_version:"2.0",
				 patch_desc:"测试版本",
				 restrict_info:"仅供测试"
	  };
	  $("#param").val(JSON.stringify(param));
  }
  function DeletePatchInfo	(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
			  patch_idx:3,
	  };
	  $("#param").val(JSON.stringify(param));
  }
  
  function SelectDevice(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
			  	device_idx:2,
				library_idx:1
		  };
		  $("#param").val(JSON.stringify(param));
  }
  function AddNewDevice(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
				device_id:"aabb",
				library_idx:1,
				device_model_idx:1,
				device_name:"测试描述",
				device_code:"aabb",
				device_desc:"aabb"

	  };
	  $("#param").val(JSON.stringify(param));
  }
  function UpdDevice(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
				 device_idx:2,
				 device_model_idx:2,
				 device_name:"测试版本",
				 device_desc:"仅供测试"
	  };
	  $("#param").val(JSON.stringify(param));
  }
  function DeleteDevice	(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
			  device_idx:3,
	  };
	  $("#param").val(JSON.stringify(param));
  }
  </script>
</html>