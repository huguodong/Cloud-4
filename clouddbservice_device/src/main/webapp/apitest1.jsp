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
						onclick="AddNewDeviceConfig(this)">/devconf/AddNewDeviceConfig</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="UpdDeviceConfig(this)">/devconf/UpdDeviceConfig</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="DelDeviceConfig(this)">/devconf/DelDeviceConfig</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="SelDeviceConfig(this)">/devconf/SelDeviceConfig</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="AddDeviceGroup(this)">/devicegroup/AddDeviceGroup</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="UpdDeviceGroup(this)">/devicegroup/UpdDeviceGroup</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="DeleteDeviceGroup(this)">/devicegroup/DeleteDeviceGroup</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="SelectDeviceGroup(this)">/devicegroup/SelectDCeviceGroup</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="AddMetaDeviceType(this)">/metadevicetype/AddMetaDeviceType</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="UpdMetaDeviceType(this)">/metadevicetype/UpdMetaDeviceType</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="DeleteMetaDeviceType(this)">/metadevicetype/DeleteMetaDeviceType</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="SelectMetaDeviceType(this)">/metadevicetype/SelectMetaDeviceType</a>
					</li>
				<!-- 	<li><a href="javascript: void(0)"
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
					</li> -->
					<li><a href="javascript: void(0)"
						onclick="AddMetaOperCmd(this)">/metaopercmd/AddMetaOperCmd</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="UpdMetaOperCmd(this)">/metaopercmd/UpdMetaOperCmd</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="DeleteMetaOperCmd(this)">/metaopercmd/DeleteMetaOperCmd</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="SelectMetaOperCmd(this)">/metaopercmd/SelectMetaOperCmd</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="AddOperGroup(this)">/operatorgroup/AddOperGroup</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="UpdOperGroup(this)">/operatorgroup/UpdOperGroup</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="DeleteOperGroup(this)">/operatorgroup/DeleteOperGroup</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="SelectOperGroup(this)">/operatorgroup/SelectOperGroup</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="AddRelDeviceGroup(this)">/reldevicegroup/AddRelDeviceGroup</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="UpdRelDeviceGroup(this)">/reldevicegroup/UpdRelDeviceGroup</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="DeleteRelDeviceGroup(this)">/reldevicegroup/DeleteRelDeviceGroup</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="SelectRelDeviceGroup(this)">/reldevicegroup/SelectRelDeviceGroup</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="AddRelOperGroup(this)">/reloperatorgroup/AddRelOperGroup</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="UpdRelOperGroup(this)">/reloperatorgroup/UpdRelOperGroup</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="DeleteRelOperGroup(this)">/reloperatorgroup/DeleteRelOperGroup</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="SelectRelOperGroup(this)">/reloperatorgroup/SelectRelOperGroup</a>
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
  
  function SelDeviceConfig(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
			  	device_idx:2,
				library_idx:1
		  };
		  $("#param").val(JSON.stringify(param));
  }
  function AddNewDeviceConfig(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
				device_idx:2,
				library_idx:2,
				device_ext_tpl_idx:1,
				device_ext_tpl_flg:0,
				device_monitor_tpl_idx:1,
				device_monitor_tpl_flg:0,
				device_run_tpl_idx:2,
				device_run_tpl_flg:0,
				device_dbsync_tpl_idx:1,
				device_dbsync_tpl_flg:0

	  };
	  $("#param").val(JSON.stringify(param));
  }
  function UpdDeviceConfig(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
				 device_idx:2,
				 device_monitor_tpl_idx:2,
				 device_monitor_tpl_flg:0,
				 device_run_tpl_flg:0,
	  };
	  $("#param").val(JSON.stringify(param));
  }
  function DelDeviceConfig	(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
	  device_idx:3,
	  library_idx:2
	  };
	  $("#param").val(JSON.stringify(param));
  }
  function SelectDeviceGroup(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
			  	device_group_idx:2,

		  };
		  $("#param").val(JSON.stringify(param));
  }
  function AddDeviceGroup(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
				library_idx:2,
				device_group_name:"录入机",
				device_group_desc:"自动录入",

	  };
	  $("#param").val(JSON.stringify(param));
  }
  function UpdDeviceGroup(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
				 device_group_idx:2,
				 library_idx:2,
				 device_group_name:"打印机",
				device_group_desc:"打印信息",
	  };
	  $("#param").val(JSON.stringify(param));
  }
  function DeleteDeviceGroup(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
	  device_group_idx:2,
	 
	  };
	  $("#param").val(JSON.stringify(param));
  }
  
  function SelectMetaDeviceType(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
			  	device_group_idx:2,

		  };
		  $("#param").val(JSON.stringify(param));
  }
  function AddMetaDeviceType(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
				device_type:"3",
				device_type_desc:"录入机",
				device_type_mark:"自动录入",

	  };
	  $("#param").val(JSON.stringify(param));
  }
  function UpdMetaDeviceType(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
				meta_devicetype_idx:3,
				device_type:"3",
				device_type_desc:"查询机",
				device_type_mark:"查询书目信息",
	  };
	  $("#param").val(JSON.stringify(param));
  }
  function DeleteMetaDeviceType(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
	  meta_devicetype_idx:3,
	 
	  };
	  $("#param").val(JSON.stringify(param));
  }
  
/*   function SelMetadataExtModel(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
			  	device_group_idx:2,

		  };
		  $("#param").val(JSON.stringify(param));
  }
  function AddNewMetadataExtModel(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
				ext_model:"测试型号",
				ext_model_desc:"EXT0205",
				ext_model_version:"测试第二版",
				ext_type:"测试",

	  };
	  $("#param").val(JSON.stringify(param));
  }
  function UpdMetadataExtModel(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
				meta_ext_idx:3,
				ext_model:"测试型号",
				ext_model_desc:"EXT0205",
				ext_model_version:"测试第二版",
				ext_type:"测试",
	  };
	  $("#param").val(JSON.stringify(param));
  }
  function DelMetadataExtModel(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param=3 ;
	  $("#param").val(param);
  } */
  
  function SelectMetaOperCmd(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
			  	meta_opercmd_idx:2,

		  };
		  $("#param").val(JSON.stringify(param));
  }
  function AddMetaOperCmd(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
				opercmd:"rest",
				opercmd_desc:"休眠",
				
	  };
	  $("#param").val(JSON.stringify(param));
  }
  function UpdMetaOperCmd(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
				meta_opercmd_idx:3,
				opercmd:"rest",
				opercmd_desc:"睡眠",
	  };
	  $("#param").val(JSON.stringify(param));
  }
  function DeleteMetaOperCmd(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
	  meta_opercmd_idx:3,
	 
	  };
	  $("#param").val(JSON.stringify(param));
  }
  
  function SelectOperGroup(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
			  	operator_group_idx:2,

		  };
		  $("#param").val(JSON.stringify(param));
  }
  function AddOperGroup(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
				library_idx:3,
				operator_group_name:"录入员",
				operator_group_desc:"录入图书信息",
				
	  };
	  $("#param").val(JSON.stringify(param));
  }
  function UpdOperGroup(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
				operator_group_idx:4,
				library_idx:3,
				operator_group_name:"录入员",
				operator_group_desc:"录入图书信息",
	  };
	  $("#param").val(JSON.stringify(param));
  }
  function DeleteOperGroup(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
	  meta_opercmd_idx:5,	 
	  };
	  $("#param").val(JSON.stringify(param));
  }
  
  function SelectRelDeviceGroup(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
			  	rel_device_idx:2,

		  };
		  $("#param").val(JSON.stringify(param));
  }
  function AddRelDeviceGroup(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
				library_idx:3,
				device_group_idx:2,
				device_idxL:8
	  };
	  $("#param").val(JSON.stringify(param));
  }
  function UpdRelDeviceGroup(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
				rel_device_idx:4,
				library_idx:3,
				device_group_idx:3,
				device_idx:5,
	  };
	  $("#param").val(JSON.stringify(param));
  }
  function DeleteRelDeviceGroup(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
	  rel_device_idx:4,
	 
	  };
	  $("#param").val(JSON.stringify(param));
  }
  
  function SelectRelOperGroup(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
			  	rel_operator_idx:2,

		  };
		  $("#param").val(JSON.stringify(param));
  }
  function AddRelOperGroup(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
				
				operator_group_idx:4,
				operator_idx:2,
				library_idx:4
	  };
	  $("#param").val(JSON.stringify(param));
  }
  function UpdRelOperGroup(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
				rel_oper_dev_idx:3,
				operator_group_idx:4,
				operator_idx:3,
				library_idx:5
	  };
	  $("#param").val(JSON.stringify(param));
  }
  function DeleteRelOperGroup(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
	  rel_operator_idx:4,
	 
	  };
	  $("#param").val(JSON.stringify(param));
  }
  </script>
</html>