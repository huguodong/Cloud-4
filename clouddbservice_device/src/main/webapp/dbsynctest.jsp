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
						<a href="javascript: void(0)"  onclick="AddNewDeviceDbsyncTemp(this)">/dbsynconf/AddNewDeviceDbsyncTemp</a>
					</li>
					<li>
						<a href="javascript: void(0)"  onclick="UpdDeviceDbsyncTemp(this)">/dbsynconf/UpdDeviceDbsyncTemp</a>
					</li>
					<li>
						<a href="javascript: void(0)"  onclick="DelDeviceDbsyncTemp(this)">/dbsynconf/DelDeviceDbsyncTemp</a>
					</li>
					<li>
						<a href="javascript: void(0)"  onclick="SelDeviceDbsyncTemp(this)">/dbsynconf/SelDeviceDbsyncTemp</a>
					</li>
					<li>
						<a href="javascript: void(0)"  onclick="AddNewDeviceDbsync(this)">/dbsynconf/AddNewDeviceDbsync</a>
					</li>
					<li>
						<a href="javascript: void(0)"  onclick="UpdDeviceDbsync(this)">/dbsynconf/UpdDeviceDbsync</a>
					</li>
					<li>
						<a href="javascript: void(0)"  onclick="DelDeviceDbsync(this)">/dbsynconf/DelDeviceDbsync</a>
					</li>
					<li>
						<a href="javascript: void(0)"  onclick="SelDeviceDbsync(this)">/dbsynconf/SelDeviceDbsync</a>
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
  /*
  	device_tpl_idx	int(11) NOT NULL
	device_tpl_ID	varchar(10) NULL模板ID
	device_tpl_desc	varchar(100) NULL模板描述
	device_type	varchar(100) NULL设备类型
  */
  
  function AddNewDeviceDbsyncTemp(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
			  device_tpl_ID:"MUBANID008",
			  device_tpl_desc:"数据同步",
			  device_type:"读卡机"
	  }
	  $("#param").val(JSON.stringify(param));
  }
  function UpdDeviceDbsyncTemp(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
			  device_tpl_idx:2,
			  device_tpl_ID:"MUBANID",
			  device_tpl_desc:"数据同步D",
			  device_type:"读卡机"
	  }
	  $("#param").val(JSON.stringify(param));
  }
  function SelDeviceDbsyncTemp(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
			  device_tpl_ID:"MUBANID",
			  device_tpl_desc:"数据同步",
			  device_type:"读卡机"
	  }
	  $("#param").val(JSON.stringify(param));
  }
  function DelDeviceDbsyncTemp(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
			  device_tpl_idx:2
	  }
	  $("#param").val(JSON.stringify(param));
  }
  
  /**
  		device_dbsync_id	int(11) NOT NULL模板ID或设备ID
		library_idx	int(11) NOT NULL馆ID
		device_tpl_type	int(11) NOT NULL模板区分 0模板 1数据
		db_name	varchar(50) NOT NULL数据库名
		table_name	varchar(50) NOT NULL数据库表名
		issync	int(11) NULL是否同步
		sync_cycle	int(11) NULL同步周期
		last_sync_time	timestamp NULL最后同步时间
		last_modify_time	timestamp NULL最后修改时间
  **/
  function AddNewDeviceDbsync(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param = {
			  device_dbsync_id:5001,
			  library_idx:3001,
			  device_tpl_type:0,
			  db_name:"device_config",
			  table_name:"metadata_ext_model",
			  issync:1,
			  sync_cycle:5
		}
	  $("#param").val(JSON.stringify(param));
  }
  /**
  		AND device_dbsync_id={device_dbsync_id}
		AND library_idx={library_idx}
		AND device_tpl_type={device_tpl_type}
		AND db_name={db_name}
		AND table_name={table_name}
  **/
  function UpdDeviceDbsync(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param = {
			  device_dbsync_id:5001,
			  library_idx:3001,
			  device_tpl_type:1,
			  db_name:"device_config",
			  table_name:"metadata_ext_model",
			  issync:1,
			  sync_cycle:50
		}
	  $("#param").val(JSON.stringify(param));
  }
  function SelDeviceDbsync(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param = 
	  {
			  db_name:"device_config",
			  table_name:"metadata_ext_model"
	  }
	  $("#param").val(JSON.stringify(param));
  }
  /**
	AND device_dbsync_id={device_dbsync_id}
	AND library_idx={library_idx}
	AND device_tpl_type={device_tpl_type}
	AND db_name={db_name}
	AND table_name={table_name}
  **/
  function DelDeviceDbsync(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param = {
			  device_dbsync_id:1001,
		      library_idx:3001,
		      device_tpl_type:1,
		      db_name:"device_config",
		      table_name:"metadata_ext_model"
		}
	  $("#param").val(JSON.stringify(param));
  }
  </script>
</html>