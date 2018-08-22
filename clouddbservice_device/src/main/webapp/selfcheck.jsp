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
    
    
    <fieldset >
      <div id="legend" class="">
        <legend class=""></legend>
      </div>
      <div class="span6">
			<div class="btn-group">
				 <button id="selectText" class="btn btn-default " >Action</button> <button data-toggle="dropdown" class="btn dropdown-toggle btn-default"><span class="caret"></span></button>
				<ul class="dropdown-menu">
					<li>
						<a href="javascript: void(0)"  onclick="AddSelfCheckConf(this)">/selfcheckconf/AddSelfCheckConf</a>
					</li>
					<li>
						<a href="javascript: void(0)"  onclick="UpdSelfCheckConf(this)">/selfcheckconf/UpdSelfCheckConf</a>
					</li>
					<li>
						<a href="javascript: void(0)"  onclick="DelSelfCheckConf(this)">/selfcheckconf/DelSelfCheckConf</a>
					</li>
					<li>
						<a href="javascript: void(0)"  onclick="SelSelfCheckConf(this)">/selfcheckconf/SelSelfCheckConf</a>
					</li>
					<li>
						<a href="javascript: void(0)"  onclick="SelSelfCheckConfExactly(this)">/selfcheckconf/SelSelfCheckConfExactly</a>
					</li>
					
					<li><a href="javascript: void(0)"
						onclick="AddRelOperDevGroup(this)">/reloperatordevicegroup/AddRelOperDevGroup</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="UpdRelOperDevGroup(this)">/reloperatordevicegroup/UpdRelOperDevGroup</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="DeleteRelOperDevGroup(this)">/reloperatordevicegroup/DeleteRelOperDevGroup</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="SelectRelOperDevGroup(this)">/reloperatordevicegroup/SelectRelOperDevGroup</a>
					</li>
					
					<li><a href="javascript: void(0)"
						onclick="AddRelOperServGroup(this)">/reloperatorservicegroup/AddRelOperServGroup</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="UpdRelOperServGroup(this)">/reloperatorservicegroup/UpdRelOperServGroup</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="DeleteRelOperServGroup(this)">/reloperatorservicegroup/DeleteRelOperServGroup</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="SelectRelOperServGroup(this)">/reloperatorservicegroup/SelectRelOperServGroup</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="AddRelServGroup(this)">/relservicegroup/AddRelServGroup</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="UpdRelServGroup(this)">/relservicegroup/UpdRelServGroup</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="DeleteRelServGroup(this)">/relservicegroup/DeleteRelServGroup</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="SelectRelServGroup(this)">/relservicegroup/SelectRelServGroup</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="AddServiceGroup(this)">/servicegroup/AddServiceGroup</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="UpdServiceGroup(this)">/servicegroup/UpdServiceGroup</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="DeleteServiceGroup(this)">/servicegroup/DeleteServiceGroup</a>
					</li>
					<li><a href="javascript: void(0)"
						onclick="SelectServiceGroup(this)">/servicegroup/SelectServiceGroup</a>
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
          <label class="control-label">JSON args:  eg. operInfo={"operator_id":"1"}&deviceInfo={"device_id":"1"}</label>
          <div class="controls">
            <div class="textarea">
                  <textarea id="param" type="" class="" cols="100" rows="5"> </textarea>
            </div>
          </div>
          
        </div>
        
        
        <div class="control-group" style="padding-top: 20px">

          <!-- Textarea -->
          <label class="control-label" >back</label>
          <div class="controls">
            <div class="textarea">
                  <textarea id="result" type="" class="" cols="100" rows="10" style="background-color: #fff"> </textarea>
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
  
  /**
  
	protocol_idx	int(11) NOT NULL模板ID号或设备ID号
	device_tpl_type	int(11) NOT NULL模板区分 0模板 1数据
	library_idx	int(11) NOT NULL馆ID
	protocol_type	int(11) NOT NULL指令类型 1SIP2 2NCIP
	protocol_show	varchar(50) NOT NULL指令代码 63、64等
	protocol_library_idx	int(11) NOT NULL指令字段描述，外关连library_selfcheck_protocol_config
	protocol_start	int(11) NULL指令起始位置
	protocol_end	int(11) NULL指令结束位置
	protocol_code	varchar(50) NULL指令标识符
	protocol_defalut	varchar(50) NULL默认值
	operator_idx	int(11) NOT NULL操作员ID
	createtime	timestamp NOT NULL创建时间
	updatetime	timestamp NOT NULL更新时间
  *
  */
  function AddSelfCheckConf(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
			  protocol_idx:1,
			  device_tpl_type:0,
			  library_idx:1,
			  protocol_type:1,
			  protocol_show:64,
			  protocol_library_idx:1,
			  protocol_start:"",
			  protocol_end:"",
			  protocol_code:"",
			  protocol_defalut:"",
			  operator_idx:5
	  };
	  $("#param").val(JSON.stringify(param));
  }
  function UpdSelfCheckConf(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
			  protocol_idx:1,
			  device_tpl_type:0,
			  protocol_library_idx:1,
			  protocol_type:1,
			  
			  protocol_show:64,
			  protocol_start:"",
			  protocol_end:"",
			  protocol_code:"",
			  protocol_defalut:"",
			  operator_idx:5
	  };
	  $("#param").val(JSON.stringify(param));
  }
  function DelSelfCheckConf(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
			  protocol_idx:1,
			  device_tpl_type:0,
			  protocol_library_idx:1,
			  protocol_type:1,
	  };
	  $("#param").val(JSON.stringify(param));
  }
  function SelSelfCheckConf(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
			  protocol_idx:1,
			  device_tpl_type:0,
			  library_idx:1,
			  protocol_type:1,
			  protocol_show:64,
			  protocol_library_idx:1,
			  protocol_start:"",
			  protocol_end:"",
			  protocol_code:"",
			  protocol_defalut:"",
			  operator_idx:5
	  };
	  $("#param").val(JSON.stringify(param));
  }
  function SelSelfCheckConfExactly(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
			  protocol_idx:1,
			  device_tpl_type:0,
			  library_idx:1,
			  protocol_type:1,
			  protocol_show:64,
			  protocol_library_idx:1
			  
	  }
	  $("#param").val(JSON.stringify(param));
  }
  
  function SelectRelOperDevGroup(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
			  	rel_oper_dev_idx:2,

		  };
		  $("#param").val(JSON.stringify(param));
  }
  function AddRelOperDevGroup(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
				operator_group_idx:2,
				device_group_idx:2,
				library_idx:8
	  };
	  $("#param").val(JSON.stringify(param));
  }
  function UpdRelOperDevGroup(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
				rel_oper_dev_idx:4,
				operator_group_idx:2,
				device_group_idx:2,
				library_idx:8,
	  };
	  $("#param").val(JSON.stringify(param));
  }
  function DeleteRelOperDevGroup(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
	  rel_oper_dev_idx:4,
	 
	  };
	  $("#param").val(JSON.stringify(param));
  }
  function SelectRelOperServGroup(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
			  	rel_oper_serv_idx:2,

		  };
		  $("#param").val(JSON.stringify(param));
  }
  function AddRelOperServGroup(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
				
				operator_group_idx:4,
				service_group_idx:2,
				library_idx:4
	  };
	  $("#param").val(JSON.stringify(param));
  }
  function UpdRelOperServGroup(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
				rel_oper_serv_idx:3,
				operator_group_idx:2,
				service_group_idx:1,
				library_idx:5
	  };
	  $("#param").val(JSON.stringify(param));
  }
  function DeleteRelOperServGroup(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
	  rel_oper_serv_idx:4,
	 
	  };
	  $("#param").val(JSON.stringify(param));
  }
  
  function SelectRelServGroup(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
			  	rel_service_idx:2,

		  };
		  $("#param").val(JSON.stringify(param));
  }
  function AddRelServGroup(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
				
				service_group_idx:4,
				meta_opercmd_idx:2,
				library_idx:4
	  };
	  $("#param").val(JSON.stringify(param));
  }
  function UpdRelServGroup(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
				rel_service_idx:3,
				service_group_idx:2,
				meta_opercmd_idx:1,
				library_idx:5
	  };
	  $("#param").val(JSON.stringify(param));
  }
  function DeleteRelServGroup(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
	  rel_service_idx:4,
	 
	  };
	  $("#param").val(JSON.stringify(param));
  }
  function SelectServiceGroup(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
			  	service_group_idx:2,

		  };
		  $("#param").val(JSON.stringify(param));
  }
  function AddServiceGroup(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
				
				service_group_name:"查询业务",
				service_group_desc:"查询用户信息",
				library_idx:1,
	  };
	  $("#param").val(JSON.stringify(param));
  }
  function UpdServiceGroup(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
				
				service_group_idx:3,
				library_idx:5,
				service_group_name:"查询业务",
				service_group_desc:"查询图书信息"
	  };
	  $("#param").val(JSON.stringify(param));
  }
  function DeleteServiceGroup(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
	  service_group_idx:4,
	 
	  };
	  $("#param").val(JSON.stringify(param));
  }
  </script>
</html>