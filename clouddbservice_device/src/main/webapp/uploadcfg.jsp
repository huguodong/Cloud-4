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
						<a href="javascript: void(0)"  onclick="uploadcfgSyn(this)">/devconf/uploadcfgSyn</a>
					</li>
					<li>
						<a href="javascript: void(0)"  onclick="uploadcfgSynExt(this)">/devconf/uploadcfgSyn</a>
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
		 data:{"req":param},
		 success:function(data){
			 var res = JSON.stringify(data);
			 $("#result").val(res);
		 }
	  }); 

  }
function uploadcfgSyn(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
			     "device_id":"www",
			 	 "library_id":"2001",
			      "table":"device_run_config",
				  "fields":"run_conf_type,run_conf_value",
				  "records":[
				             {"run_conf_type":"center_config","run_conf_value":"{\"IP\":\"127.0.0.1\",\"PORT\":\"8080\"}"}
				            ]
			  }

	  $("#param").val(JSON.stringify(param));
}
function uploadcfgSynExt(obj){
	  $("#submit").removeAttr("disabled");
	  $("#selectText").html($(obj).text());
	  var param={
			      "device_id":"www",
			 	  "library_id":"1",
			      "table":"device_ext_config",
				  "fields":"logic_obj,ext_model,ext_comm_conf,ext_extend_conf",
				  "records":[
				             {
				             "ext_comm_conf":"testtestest",
				             "logic_obj":"01",
				             "ext_extend_conf":"xxx",
				             "ext_model":"df"
				             }
				            ]
			  }

	  $("#param").val(JSON.stringify(param));
}
  </script>
</html>