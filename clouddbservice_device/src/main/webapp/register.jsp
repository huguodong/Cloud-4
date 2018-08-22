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
						<a href="javascript: void(0)"  onclick="AddRegisterInfo(this)">/device/AddRegisterInfo</a>
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
 * 
 device
 device_idx	int(11) NOT NULL
 device_id	varchar(20) NOT NULL设备ID
 library_idx	int(11) NOT NULL馆ID
 device_model_idx	int(11) NOT NULL设备类型id
 device_name	varchar(100) NOT NULL设备名
 device_code	varchar(100) NOT NULL设备特征码。设备自动计算获得
 device_desc	varchar(100) NULL设备描述
 
 template
 
 device_tpl_idx	int(11) NOT NULL
 device_tpl_ID	varchar(10) NULL模板ID
 device_tpl_desc	varchar(100) NULL模板描述
 device_type	varchar(100) NULL设备类型
 
 /**
	 * device_monitor_template
	 * device_mon_tpl_idx	int(11) NOT NULL
		device_mon_tpl_id	varchar(10) NOT NULL显示模板ID
		device_mon_tpl_desc	varchar(500) NOT NULL显示模板描述
		device_mon_type_idx	int(11) NOT NULL设备类型
	 */
  
  
  function AddRegisterInfo(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
			device:{
				device_id:"regID",
				library_idx:4566,
				device_model_idx:1,
				device_name:"注册设备",
				device_code:"XXFFASDDD",
				device_desc:"注册设备描述"
			},
			device_dbsync_template:{
				device_tpl_ID:"tempIDxx",
				device_tpl_desc:"descdesc",
				device_type:"typess"
			},
			device_monitor_template:{
				device_mon_tpl_id:"tempIDxx",
				device_mon_tpl_desc:"descdesc",
				device_mon_type_idx:55555
			},
			device_run_template:{
				device_tpl_ID:"tempIDxx",
				device_tpl_desc:"descdesc",
				device_type:"typess"
			},
			device_ext_template:{
				device_tpl_ID:"tempIDxx",
				device_tpl_desc:"descdesc",
				device_type:"typess"
			}
	  }
	  $("#param").val(JSON.stringify(param));
  }
  </script>
</html>