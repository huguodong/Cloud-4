<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
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
				<button id="selectText1" class="btn btn-default" data-toggle="dropdown">Table</button>
				<button data-toggle="dropdown"
					class="btn dropdown-toggle btn-default">
					<span class="caret"></span>
				</button>
				<ul class="dropdown-menu">
					<li><a href="javascript: void(0)" onclick="change(this)">ip_white</a></li>
					<li><a href="javascript: void(0)" onclick="change(this)">library</a></li>
					<li><a href="javascript: void(0)" onclick="change(this)">library_info</a></li>
					<li><a href="javascript: void(0)" onclick="change(this)">library_service_template</a></li>
					<li><a href="javascript: void(0)" onclick="change(this)">metadata_infotype</a></li>
					<li><a href="javascript: void(0)" onclick="change(this)">operator</a></li>
					<li><a href="javascript: void(0)" onclick="change(this)">operator_info</a></li>
					<li><a href="javascript: void(0)" onclick="change(this)">password_history</a></li>
					<li><a href="javascript: void(0)" onclick="change(this)">rel_libs</a></li>
					<li><a href="javascript: void(0)" onclick="change(this)">sox_template</a></li>
				</ul>
			</div>
			
			<div class="btn-group">
				<button id="selectText" class="btn btn-default" data-toggle="dropdown">Action</button>
				<button data-toggle="dropdown"
					class="btn dropdown-toggle btn-default">
					<span class="caret"></span>
				</button>
				<ul class="dropdown-menu"  id="dd">
				</ul>

			</div>


			<div class="control-group" style="padding-top:20px;">

				<label class="control-label">JSON args: eg.
					operInfo={"operator_id":"1"}&deviceInfo={"device_id":"1"}</label>
				<div class="controls">
					<div class="textarea">
						<textarea id="param" type="" class="" cols="100" rows="5"> </textarea>
					</div>
				</div>

			</div>
			

			<div class="control-group" style="padding-top: 20px;">

				<label class="control-label">back</label>
				<div class="controls">
					<div class="textarea">
						<textarea id="result" type="" class="" cols="100" rows="10"
							readonly="readonly" style="background-color: #fff"> </textarea>
					</div>
				</div>
			</div>
			
			
			<div class="control-group" style="float: left;padding-left: 650px; float: inherit;">
				<label class="control-label"></label>
				<div class="controls">
					<button id="submit" onclick="save()" class="btn btn-info">submit</button>
					<button id="submit" onclick="jsonFormat()" class="btn btn-info">formatJson</button>
				</div>
			</div>

			
		</div>

	</fieldset>
	
	<%----ip_white --%>
	<ul id="ip_white" style="display: none">
		<li><a href="javascript: void(0)" onclick="addIpWhite(this)">/operator/addIpWhite</a></li>
		<li><a href="javascript: void(0)" onclick="delIpWhiteByOperIdx(this)">/operator/delIpWhiteByOperIdx</a></li>
		<li><a href="javascript: void(0)" onclick="updIpWhite(this)">/operator/updIpWhite</a></li>
		<li><a href="javascript: void(0)" onclick="selIpWhiteByIdx(this)">/operator/selIpWhiteByIdx</a></li>
	</ul>
	
	<%----library --%>
	<ul id="library" style="display: none">
		<li><a href="javascript: void(0)" onclick="addLibrary(this)">/library/addLibrary</a></li>
		<li><a href="javascript: void(0)" onclick="delLibraryByIdx(this)">/library/delLibraryByIdx</a></li>
		<li><a href="javascript: void(0)" onclick="selLibraryByIdxOrId(this)">/library/selLibraryByIdxOrId</a></li>
		<li><a href="javascript: void(0)" onclick="selLibraryByIdxsOrIds(this)">/library/selLibraryByIdxsOrIds</a></li>
	</ul>
	
	<%----library_info --%>
	<ul id="library_info" style="display: none">
		<li><a href="javascript: void(0)" onclick="addLibraryInfo(this)">/library/addLibraryInfo</a></li>
		<li><a href="javascript: void(0)" onclick="addLibraryInfoList(this)">/library/addLibraryInfoList</a></li>
	</ul>
	
	<%----library_service_template --%>
	<ul id="library_service_template" style="display: none">
		<li><a href="javascript: void(0)" onclick="addLibraryTemplate(this)">/library/addLibraryTemplate</a></li>
		<li><a href="javascript: void(0)" onclick="delLibraryTemplateById(this)">/library/delLibraryTemplateById</a></li>
		<li><a href="javascript: void(0)" onclick="selLibraryTempList(this)">/library/selLibraryTempList</a></li>
		<li><a href="javascript: void(0)" onclick="updLibraryTemplate(this)">/library/updLibraryTemplate</a></li>
	</ul> 
	
	<%----metadata_infotype --%>
	<ul id="metadata_infotype" style="display: none">
		<li><a href="javascript: void(0)" onclick="delMetadataInfotypeByIdx(this)">/metadata/delMetadataInfotypeByIdx</a></li>
	</ul>
	
	
	<%----metadata_infotype --%>
	<ul id="operator" style="display: none">
		<li><a href="javascript: void(0)" onclick="getVaildTime(this)">/operator/getVaildTime</a></li>
		<li><a href="javascript: void(0)" onclick="delOperatorByIdx(this)">/operator/delOperatorByIdx</a></li>
		<li><a href="javascript: void(0)" onclick="updOperatorByIdx(this)">/operator/updOperatorByIdx</a></li>
		<li><a href="javascript: void(0)" onclick="updOperatorByParam(this)">/operator/updOperatorByParam</a></li>
		<li><a href="javascript: void(0)" onclick="changePassword(this)">/operator/changePassword</a></li>
		<li><a href="javascript: void(0)" onclick="loginCheck(this)">/operator/loginCheck</a></li>
		<li><a href="javascript: void(0)" onclick="selOperatorByOperIdOrIdx(this)">/operator/selOperatorByOperIdOrIdx</a></li>
		<li><a href="javascript: void(0)" onclick="deviceLoginCheck(this)">/operator/deviceLoginCheck</a></li>
	</ul>
	
	<%----operator_info --%>
	<ul id="operator_info" style="display: none">
		<li><a href="javascript: void(0)" onclick="addOperatorInfo(this)">/operator/addOperatorInfo</a></li>
	</ul>
	
	<%----password_history --%>
	<ul id="password_history" style="display: none">
		<li><a href="javascript: void(0)" onclick="addPasswordHistory(this)">/operator/addPasswordHistory</a></li>
	</ul>
	
	<%----rel_libs --%>
	<ul id="rel_libs" style="display: none">
	
	</ul>
	
	<%----sox_template --%>
	<ul id="sox_template" style="display: none">
		<li><a href="javascript: void(0)" onclick="addSoxTemplate(this)">/operator/addSoxTemplate</a></li>
		
	</ul>
	


</body>
<script type="text/javascript">
function change(obj){
	$("#selectText1").html($(obj).text());
	$("#dd").html($("#"+$(obj).text()).html());
}


  	function save(){
	  var url1 = $("#selectText").text();
	  var param = $("#param").val();
	  if(url1=="URL" || url1==""){return;}
	  if(param==""){return;}
	  url1 = '<%=path%>' + url1;
		$.ajax({
			url : url1,
			type : "POST",
			data : param,
			success : function(data) {
				var res = JSON.stringify(data);
				$("#result").val(res);
			}
		});
	}
  	
  	function jsonFormat(){
  		$("#result").val(formatJson($("#result").val()));
  	}

	function addOperator(obj) {
		$("#submit").removeAttr("disabled");
		$("#selectText").html($(obj).text());
		var param = 'req={'
			  +'"servicetype":"cloudbusinessservice_authentication",  '
			  +'"target":"ext",	'
			  +'"operation":"addOperator",'
			  +'"data":'
			  +'     {'
			  +'"operator_id":"ss",' 
			  + '"library_idx":"1",'
				+ '"sox_tpl_id":"1",' 
				+ '"operator_name":"1",'
				+ '"operator_pwd":"1",' 
				+ '"operator_type":"1",'
				+ '"isActive":"1",' 
				+ '"isLock":"1",' 
				+ '"isLogged":"1"'  
			  +'       }   ' 
			  +'}';
		$("#param").val(param);
	}

	function addOperatorInfo(obj) {
		$("#submit").removeAttr("disabled");
		$("#selectText").html($(obj).text());
		var param = 'operInfo={' + '"operator_idx":"1",'
				+ '"infotype_idx":"50",' + '"info_value":"phone"' + '}';
		$("#param").val(param);
	}

	function getVaildTime(obj) {
		$("#submit").removeAttr("disabled");
		$("#selectText").html($(obj).text());
		
		var param = 'req={'
			  +'	"servicetype":"cloudbusinessservice_authentication",  '
			  +'	"target":"ext",	'
			  +'	"operation":"getVaildTime",'
			  +'	"data":'
			  +'       {'
			  +'			  "operator_id":"67" '
			  +'       }   ' 
			  +'}';
		$("#param").val(param);
	}

	function addSoxTemplate(obj) {
		$("#submit").removeAttr("disabled");
		$("#selectText").html($(obj).text());
		var param = 'soxInfo={"sox_tpl_name":"ss",' + '"password_length":"1",'
				+ '"password_charset":"1",' + '"login_fail_times":"1",'
				+ '"lock_time":"1",' + '"first_login_chgpwd":"1",'
				+ '"count_history_password":"1",' + '"password_validdays":"1",'
				+ '"vaild_time":"1"}';
		$("#param").val(param);
	}

	function addLibraryTemplate(obj) {
		$("#submit").removeAttr("disabled");
		$("#selectText").html($(obj).text());
		var param = 'libTempInfo={' + '"lib_service_tpl_desc":"ss",'
				+ '"max_device_count":"1",'
				+ '"max_operator_count":"1",' 
				+ '"max_sublib_count":"1"' + '}';
		$("#param").val(param);
	}

	function addLibrary(obj) {
		$("#submit").removeAttr("disabled");
		$("#selectText").html($(obj).text());
		var param = 'req={'
			  +'"servicetype":"cloudbusinessservice_authentication",  '
			  +'"target":"ext",	'
			  +'"operation":"addLibrary",'
			  +'"data":'
			  +'     {'
			  + '"lib_id":"ss",' 
			  + '"lib_name":"1",'
			  + '"lib_service_tpl_id":"1"' 
			  +'       }   ' 
			  +'}';
		$("#param").val(param);
	}
	
	function addLibraryInfo(obj) {
		$("#submit").removeAttr("disabled");
		$("#selectText").html($(obj).text());
		var param = 'libInfo={' + '"library_idx":"1",' + '"infotype_idx":"50",'
				+ '"info_value":"phone"' + '}';
		$("#param").val(param);
	}
	
	function addLibraryInfoList(obj) {
		$("#submit").removeAttr("disabled");
		$("#selectText").html($(obj).text());
		var param = 'libInfo='
		+'[' 
		+'{' 
			+ '"library_idx":"1",' 
			+ '"infotype_idx":"15",'
			+ '"info_value":"info15"' 
			+ '},' 
		+'{' 
			+ '"library_idx":"1",' 
			+ '"infotype_idx":"16",'
			+ '"info_value":"info16"' 
			+ '},' 
		+'{' 
			+ '"library_idx":"1",' 
			+ '"infotype_idx":"17",'
			+ '"info_value":"info17"' 
			+ '},' 
		+'{' 
			+ '"library_idx":"1",' 
			+ '"infotype_idx":"18",'
			+ '"info_value":"info18"' 
			+ '},' 
		+'{' 
			+ '"library_idx":"1",' 
			+ '"infotype_idx":"19",'
			+ '"info_value":"info19"' 
			+ '},' 
		+'{' 
			+ '"library_idx":"1",' 
			+ '"infotype_idx":"20",'
			+ '"info_value":"info20"' 
			+ '},' 
		+'{' 
			+ '"library_idx":"1",' 
			+ '"infotype_idx":"21",'
			+ '"info_value":"info21"' 
			+ '},' 
		+'{' 
			+ '"library_idx":"1",' 
			+ '"infotype_idx":"22",'
			+ '"info_value":"info22"' 
			+ '}' 
			+']';
		$("#param").val(param);
	}

	function addPasswordHistory(obj) {
		$("#submit").removeAttr("disabled");
		$("#selectText").html($(obj).text());
		var param = 'passwordInfo={' + '"operator_idx":"1",'
				+ '"password":"ASLDJsdaskdjqwi123asldjaLJ",'
				+ '"modifyTime":1459855106000' + '}';
		$("#param").val(param);
	}

	function delOperatorByIdx(obj) {
		$("#submit").removeAttr("disabled");
		$("#selectText").html($(obj).text());
		var param = 'req={'
			  +'"servicetype":"cloudbusinessservice_authentication",  '
			  +'"target":"ext",	'
			  +'"operation":"delOperatorByIdx",'
			  +'"data":'
			  +'     {'
			  + '"operator_idx":"9"'  
			  +'       }   ' 
			  +'}';
		$("#param").val(param);
	}

	function updOperatorByIdx(obj) {
		$("#submit").removeAttr("disabled");
		$("#selectText").html($(obj).text());
		var param = 'req={'
			  +'"servicetype":"cloudbusinessservice_authentication",  '
			  +'"target":"ext",	'
			  +'"operation":"updOperatorByIdx",'
			  +'"data":'
			  +'     {'
			  + '"operator_idx":"7",'
			  + '"operator_id":"ss",' + '"library_idx":"1",'
			  + '"sox_tpl_id":"1",' + '"operator_name":"1",'
			  + '"operator_pwd":"1",' + '"operator_type":"1",'
			  + '"isActive":"1",' + '"isLock":"1",' + '"isLogged":"1"'
			  +'       }   ' 
			  +'}';
		$("#param").val(param);
	}

	function delIpWhiteByOperIdx(obj) {
		$("#submit").removeAttr("disabled");
		$("#selectText").html($(obj).text());
		var param = 'json={' + '"operator_idx":"3"' + '}';
		$("#param").val(param);
	}

	function delLibraryByIdx(obj) {
		$("#submit").removeAttr("disabled");
		$("#selectText").html($(obj).text());
		var param = 'json={' + '"library_idx":"2"' + '}';
		$("#param").val(param);
	}
	
	function selLibraryByIdxOrId(obj) {
		$("#submit").removeAttr("disabled");
		$("#selectText").html($(obj).text());
		var param = 'req={'
			  +'"servicetype":"cloudbusinessservice_authentication",  '
			  +'"target":"ext",	'
			  +'"operation":"selLibraryByIdxOrId",'
			  +'"data":'
			  +'     {'
			  + '"library_idx":"2"'
			  +'       }   ' 
			  +'}';
		$("#param").val(param);
	}
	
	function selLibraryByIdxsOrIds(obj) {
		$("#submit").removeAttr("disabled");
		$("#selectText").html($(obj).text());
		var param = 'json={'
			  + '"library_idx":"1,2,3,4"'
			  +'}';
		$("#param").val(param);
	}

	function delLibraryTemplateById(obj) {
		$("#submit").removeAttr("disabled");
		$("#selectText").html($(obj).text());
		var param = 'libTempInfo={' + '"lib_service_tpl_id":"5"' + '}';
		$("#param").val(param);
	}
	
	
	function selLibraryTempList(obj) {
		$("#submit").removeAttr("disabled");
		$("#selectText").html($(obj).text());
		var param = 'libTempInfo={' + '"lib_service_tpl_desc":"ss",'
					+ '"max_device_count":"1",'
					+ '"max_operator_count":"1",' 
					+ '"max_sublib_count":"1"' + '}';
		$("#param").val(param);
	}
	
	function updLibraryTemplate(obj) {
		$("#submit").removeAttr("disabled");
		$("#selectText").html($(obj).text());
		var param = 'libTempInfo={' 
					+ '"lib_service_tpl_id":"2",'
					+ '"lib_service_tpl_desc":"sss",'
					+ '"max_device_count":"12",'
					+ '"max_operator_count":"12",' 
					+ '"max_sublib_count":"12"' + '}';
		$("#param").val(param);
	}

	function delMetadataInfotypeByIdx(obj) {
		$("#submit").removeAttr("disabled");
		$("#selectText").html($(obj).text());
		var param = 'infotype={' + '"infotype_idx":"67"' + '}';
		$("#param").val(param);
	}

	function updOperatorByParam(obj) {
		$("#submit").removeAttr("disabled");
		$("#selectText").html($(obj).text());
		var param = 'req={'
			  +'"servicetype":"cloudbusinessservice_authentication",  '
			  +'"target":"ext",	'
			  +'"operation":"updOperatorByParam",'
			  +'"data":'
			  +'     {'
			  +    '{"updParam"{' + '"operator_id":"67",' + '"library_idx":"1",'
				+ '"sox_tpl_id":"2",' + '"operator_name":"67",'
				+ '"operator_pwd":"SAJDASLKDJSwqwlkj12312jasdji12Ljkj",'
				+ '"operator_type":"2",' + '"isActive":"2",' + '"isLock":"2",'
				+ '"isLogged":"2",' + '"last_login_ip":"127.0.0.1",'
				+ '"last_login_time":1460008728,'
				+ '"last_lock_time":1460008728,'
				+ '"last_chgpwd_time":1460008728,' + '"login_fail_times":2'
				+ '}' 
				+ ','
				+ '{"whereParam":"{' + '"operator_idx":"1"'
			  +'       }"  } ' 
			  +'}';
		$("#param").val(param);
	}

	function selOperatorByOperIdOrIdx(obj) {
		$("#submit").removeAttr("disabled");
		$("#selectText").html($(obj).text());
		var param = 'operInfo={' + '"operator_idx":"67"' + '}';
		$("#param").val(param);
	}

	function loginCheck(obj) {
		$("#submit").removeAttr("disabled");
		$("#selectText").html($(obj).text());
		var param = 'req={'
			  +'"servicetype":"cloudbusinessservice_authentication",  '
			  +'"target":"ext",	'
			  +'"operation":"loginCheck",'
			  +'"data":'
			  +'     {'
			  + '"operator_id":"67",'
			  + '"operator_pwd":"123",' 
			  + '"ip":"127.0.0.1",'
			  + '"port":"10086",' 
			  + '"faild_times":"0"'
			  +'       }   ' 
			  +'}';
		$("#param").val(param);
	}
	
	function deviceLoginCheck(obj) {
		$("#submit").removeAttr("disabled");
		$("#selectText").html($(obj).text());
		var param = 'req={'
			  +'"servicetype":"cloudbusinessservice_authentication",  '
			  +'"target":"ext",	'
			  +'"operation":"deviceLoginCheck",'
			  +'"data":'
			  +'     {'
			  + '"operator_id":"d001",'
			  + '"ip":"127.0.0.1",'
			  + '"port":"10086"' 
			  +'       }   ' 
			  +'}';
		$("#param").val(param);
	}

	function addIpWhite(obj) {
		$("#submit").removeAttr("disabled");
		$("#selectText").html($(obj).text());
		var param = 'json={' + '"operator_idx":"1",' + '"ipaddr":"127.0.0.1"'
				+ '}';
		$("#param").val(param);
	}
	
	function updIpWhite(obj) {
		$("#submit").removeAttr("disabled");
		$("#selectText").html($(obj).text());
		var param = 'json={' + '"operator_idx":"1",' + '"ipaddr":"127.0.0.1"'
				+ '}';
		$("#param").val(param);
	}
	
	function selIpWhiteByIdx(obj) {
		$("#submit").removeAttr("disabled");
		$("#selectText").html($(obj).text());
		var param = 'json={' 
				+ '"operator_idx":"1"' 
				+ '}';
		$("#param").val(param);
	}
	
	function changePassword(obj) {
		$("#submit").removeAttr("disabled");
		$("#selectText").html($(obj).text());
		var param = 'req={'
			  +'"servicetype":"cloudbusinessservice_authentication",  '
			  +'"target":"ext",	'
			  +'"operation":"changePassword",'
			  +'"data":'
			  +'     {'
			  + '"operator_id":"67",' 
			  + '"old_password":"123456",'
			  + '"new_password":"123"' 
			  +'       }   ' 
			  +'}';
		$("#param").val(param);
	}
	
	
	
	<%--json数据格式化--%>
	var formatJson = function(json, options) {
		var reg = null,
			formatted = '',
			pad = 0,
			PADDING = '    '; // one can also use '\t' or a different number of spaces
	 
		// optional settings
		options = options || {};
		// remove newline where '{' or '[' follows ':'
		options.newlineAfterColonIfBeforeBraceOrBracket = (options.newlineAfterColonIfBeforeBraceOrBracket === true) ? true : false;
		// use a space after a colon
		options.spaceAfterColon = (options.spaceAfterColon === false) ? false : true;
	 
		// begin formatting...
		if (typeof json !== 'string') {
			// make sure we start with the JSON as a string
			json = JSON.stringify(json);
		} else {
			// is already a string, so parse and re-stringify in order to remove extra whitespace
			json = JSON.parse(json);
			json = JSON.stringify(json);
		}
	 
		// add newline before and after curly braces
		reg = /([\{\}])/g;
		json = json.replace(reg, '\r\n$1\r\n');
	 
		// add newline before and after square brackets
		reg = /([\[\]])/g;
		json = json.replace(reg, '\r\n$1\r\n');
	 
		// add newline after comma
		reg = /(\,)/g;
		json = json.replace(reg, '$1\r\n');
	 
		// remove multiple newlines
		reg = /(\r\n\r\n)/g;
		json = json.replace(reg, '\r\n');
	 
		// remove newlines before commas
		reg = /\r\n\,/g;
		json = json.replace(reg, ',');
	 
		// optional formatting...
		if (!options.newlineAfterColonIfBeforeBraceOrBracket) {			
			reg = /\:\r\n\{/g;
			json = json.replace(reg, ':{');
			reg = /\:\r\n\[/g;
			json = json.replace(reg, ':[');
		}
		if (options.spaceAfterColon) {			
			reg = /\:/g;
			json = json.replace(reg, ': ');
		}
	 
		$.each(json.split('\r\n'), function(index, node) {
			var i = 0,
				indent = 0,
				padding = '';
	 
			if (node.match(/\{$/) || node.match(/\[$/)) {
				indent = 1;
			} else if (node.match(/\}/) || node.match(/\]/)) {
				if (pad !== 0) {
					pad -= 1;
				}
			} else {
				indent = 0;
			}
	 
			for (i = 0; i < pad; i++) {
				padding += PADDING;
			}
	 
			formatted += padding + node + '\r\n';
			pad += indent;
		});
	 
		return formatted;
	};
	
</script>
</html>