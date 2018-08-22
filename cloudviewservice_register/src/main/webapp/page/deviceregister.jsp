<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<!--[if IE 8]><html class="ie8 oldie"><![endif]-->
<!--[if IE 9]><html class="ie9 oldie"><![endif]-->
<!--[if gt IE 9]><!--><html><!--<![endif]-->
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="renderer" content="webkit">
<title>设备注册</title>
<link rel="stylesheet" type="text/css" href="<%=basePath %>/static/css/style.css" />
<script type="text/javascript" src="<%=basePath %>/static/js/jquery.min.js" ></script>
<script type="text/javascript" src="<%=basePath %>/static/js/common.js" ></script>
<script type="text/javascript" src="<%=basePath %>/static/js/underscore/underscore-min.js" ></script>
<script type="text/javascript" src="<%=basePath %>/static/js/ajaxfileupload.js"></script>

<!--[if IE]>
	<link rel="stylesheet" type="text/css" href="<%=basePath %>/static/css/ie.css" />
<![endif]-->
<!--[if lt IE 9]>
	<script type="text/javascript" src="<%=basePath %>/static/js/html5.js" ></script>
	<script type="text/javascript" src="<%=basePath %>/static/js/ie.js" ></script>
<![endif]-->
<style type="text/css">
#money label{
	display: inline-block;
	width: 40px;
}

.form-wrap li.error div.g-select {
    background-color: #ffeeee;
    border-color: #ff2323;
}

div.muban span.error-msg{
	display: none;
}

div.muban div.item.error div.g-select{
	background-color: #ffeeee;
    border-color: #ff2323;
}

div.muban div.item.error span.error-msg{
	margin-left: 10px;
	display:inline;
	color: #ff2323;
}

.register-back {
    background-color: #2cbe5f;
    border-radius: 2px;
    color: #fff;
    cursor: pointer;
    height: 28px;
    line-height: 28px;
    margin-top: 16px;
    text-align: center;
    width: 80px;
}

.equipment-zhuce .form-wrap.acs {
	padding-top:10px;
}

.equipment-zhuce .form-wrap.acs .item {
	margin-bottom:10px;
}

.form-wrap .delete-li {
	margin-left: 5px;
}

.equipment-zhuce .field-title{
	margin-top:30px;
}
.equipment-zhuce .form-wrap.acs label {
	display: inline-block;
	margin-right:20px;
	margin-left:3px;
	width: 30px;
}

.g-loading.save {
	background-position: 80% center;
}

.readonly{
	color: gray;
	background-color: #E0E0E0;
}

#region {
	width:550px;
}

#region .g-select{
	margin-right:10px;
	width:120px;
}

.form-wrap dd{
	height: 30px;
	line-height: 30px;
}
.form-wrap .select-area .g-checkbox.on{
	background: url(${pageContext.request.contextPath}/static/images/choosen1.png) center center no-repeat;
}
.form-wrap .choose-area{
	padding:10px 10px;
	height: 300px;
	-webkit-box-sizing: border-box;
	-moz-box-sizing: border-box;
	box-sizing: border-box;
	overflow: auto;
}
.form-wrap .choose-jurisdiction{
	padding:5px 0;
	height: 305px;
	-webkit-box-sizing: border-box;
	-moz-box-sizing: border-box;
	box-sizing: border-box;
	overflow: auto;
}
.form-wrap .choose-jurisdiction dd{
	padding-left: 12px;
}
.form-wrap .choose-jurisdiction dd:hover{
	background-color: #00a2e9;
	color:#FFF;
}
.form-wrap .handle-area{
	position: absolute;
	bottom:0;
	left:0;
	width: 100%;
	height: 40px;
	line-height: 40px;
	background-color: #e2e2e2;
}
.form-wrap .handle-area .g-btn{
	margin-left:10px;
}

</style>
</head>
<body>
<div class="g-loading"></div>
<div class="g-loading save" ></div>
	<div class="g-header">
		<a href="#" class="logo"></a>
<!-- 		<a class="exit" href="<%=basePath %>device/connect?deviceCode=${deviceCode }"><span class="icon"></span>退出</a> -->
		<a class="exit" href="<%=basePath %>login/logout?deviceCode=${deviceCode }"><span class="icon"></span>退出</a>
		<div class="status"><span class="icon"></span>系统管理员</div>
<!-- 		<div class="g-select language"> -->
<!-- 			<span class="icon"></span> -->
<!-- 			<select> -->
<!-- 				<option value="zh_CN" selected>中文</option> -->
<!-- 				<option value="en_US" >英文</option> -->
<!-- 			</select> -->
<!-- 			<span class="arr-icon"></span> -->
<!-- 		</div> -->
<!-- 		<a href="main.html" class="index-page"><span class="icon"></span>首页</a> -->
	</div>
	<div class="page-title-bar">
		<a href="#" class="back-btn"></a>
		<span class="title">设备注册<a href="#" class="g-help"></a></span>
	</div>
	<div class="main equipment-zhuce">
		<div class="content-area width-limit86">
			<div class="form-area">
				<div class="tips">
					设备首次登录时需要添写设备基本信息，把信息上传到云平台。<br>
					注册成功后，用户可下载相应的驱动信息。
				</div>
				<div class="form-wrap">
					<div class="item">
						<ul>
							<li >
								<div class="left">设备特征码</div>
								<div class="right" >
								<input type="text" id="deviceCode" value="${deviceCode }"  readonly="readonly" class="g-input" style="color: gray;width: 560px;background-color: #E0E0E0" >
								</div>
							</li>
						</ul>
					</div>
					<div class="item">
						<ul>
							<li >
								<div class="left">PID</div>
								<div class="right" >
								<input type="text" id="pId" class="g-input" style="width: 560px" placeholder="请输入">
								</div>
							</li>
						</ul>
					</div>
					<div class="item">
						<ul>
							<li>
								<div class="left">图书馆ID</div>
								<input type="hidden" id="libIdx" placeholder="请输入" class="g-input">
								<div class="right"><input type="text" id="libId" placeholder="请输入" class="g-input"></div>
							</li>
							<li>
								<div class="left">图书馆名称</div>
								<div class="right"><input type="text"  readonly="readonly"  id="libname" placeholder="" class="g-input" style="color: gray;background-color: #E0E0E0"></div>
							</li>
							
						</ul>
					</div>
					<div class="item">
						<ul>
							<li>
								<div class="left">设备ID</div>
								<div class="right"><input type="text" id="deviceId" placeholder="请输入" class="g-input"></div>
							</li>
							<li >
								<div class="left">设备名称</div>
								<div class="right"><input type="text" id="deviceName" placeholder="请输入" class="g-input">
									<span class="error-msg">错误信息</span>
								</div>
							</li>
						</ul>
					</div>
					<div class="item">
						<ul>
							<li >
								<div class="left">设备类型</div>
								<div class="right">
									<div class="g-select">
										<select id="deviceType">
											<option value="-1">请选择</option>
										</select>
										<span class="arr-icon"></span>
									</div>
									<span class="error-msg">错误信息</span>
								</div>
							</li>
						</ul>
					</div>
					<%--
					<div class="item">
						<ul>
							<li>
								<div class="left">ACS登录帐户</div>
								<div class="right"><input type="text" id="acsName" placeholder="请输入" class="g-input"></div>
							</li>
							<li>
								<div class="left">密码</div>
								<div class="right"><input type="password" id="acsPwd" placeholder="请输入" class="g-input"></div>
							</li>
						</ul>
					</div>
					
					 --%>
					<div class="item">
						<ul>
							<li>
								<div class="left">设备物流编号</div>
								<div class="right"><input type="text" id="logisticsNumber" placeholder="请输入" class="g-input"></div>
							</li>
							<li>
								<div class="left">设备流通地点</div>
								<div class="right"><input type="text" id="circuleLocation" placeholder="请输入" class="g-input"></div>
							</li>
						</ul>
					</div>
					<div class="item ">
						<ul>
							<li style="width:100%;" class="">
								<div class="left">设备地点</div>
								<div class="right" id="region">
									<div class="g-select">
										<select id="nation">
											<option value="-1">国家</option>
										</select>
										<span class="arr-icon"></span>
									</div>
									<div class="g-select">
										<select id="province">
											<option value="-1">省</option>
										</select>
										<span class="arr-icon"></span>
									</div>
									<div class="g-select">
										<select id="city">
											<option value="-1">市</option>
										</select>
										<span class="arr-icon"></span>
									</div>
									<div class="g-select">
										<select id="area">
											<option value="-1">区</option>
										</select>
										<span class="arr-icon"></span>
									</div>
									<span class="error-msg" style="float:left;">请选择完整的地区信息</span>
								</div>
							</li>
						</ul>
					</div>
					<div class="item">
						<ul>
							<li>
								<div class="left">设备描述</div>
								<div class="right">
								<textarea class="g-textarea" style="width:560px" cols="150" rows="" id="deviceDesc"  placeholder="输入内容"></textarea>
								</div>
							</li>
						</ul>
					</div>
				</div>
					<div class="item">
						<ul>
							<li>
								<div class="right">
									<div class="add-li" >
										<div class="li-list">
										</div>
									</div>
									<label class="label-add" style="cursor: pointer;">添加ACS配置</label>
								</div>
							</li>
						</ul>
					</div>
					<%--
				<div class="form-wrap acs" >
					<div class="item">
						<ul>
							<li >
								<div class="left"><div class="delete-li"></div>ACS模板</div>
								<div class="right" >
									<div class="g-select">
										<select id="">
											<option value="-1">请选择</option>
										</select>
										<span class="arr-icon"></span>
									</div>
									<span class="error-msg">错误信息</span>
								</div>
							</li>
							<li >
								<div class="left">后台服务名</div>
								<div class="right"><input type="text" id="" placeholder="英文或数字" class="g-input"></div>
							</li>
						</ul>
					</div>
					<div class="item">
						<ul>
							<li>
								<div class="left">IP</div>
								<div class="right"><input type="text" id="" placeholder="请输入" class="g-input"></div>
							</li>
							<li>
								<div class="left">端口号</div>
								<div class="right"><input type="text" id="" placeholder="请输入" class="g-input"></div>
							</li>
						</ul>
					</div>
					<div class="item">
						<ul>
							<li>
								<div class="left">登录名</div>
								<div class="right"><input type="text" id="acsName" placeholder="请输入" class="g-input"></div>
							</li>
							<li>
								<div class="left">密码</div>
								<div class="right"><input type="password" id="acsPwd" placeholder="请输入" class="g-input"></div>
							</li>
						</ul>
					</div>
				</div>
				 --%>
				
				
				
				<div class="field-title">
					<span class="word">设备配置</span>
				</div>
				<div class="muban">
					<div class="item yingjian-dialog">
						<div class="word">设备硬件模板</div>
						<div class="g-select form">
							<select id="extTemp">
								<option value="-1" >请选择模板</option>
							</select>
							<span class="arr-icon"></span>
						</div>
						<div class="edit g-btn-gray disabled" >修改</div>
						<span class="error-msg" >请选择模板</span>
					</div>
					<div class="item yunxing-dialog">
						<div class="word">设备运行模板</div>
						<div class="g-select form">
							<select id="runTemp">
								<option value="-1">请选择模板</option>
							</select>
							<span class="arr-icon"></span>
						</div>
						<div class="edit g-btn-gray disabled">修改</div>
						<span class="error-msg" >请选择模板</span>
					</div>
					<%--
					<div class="item change-box" date-id="config-yunxing" val="2">
						<div class="word">&nbsp;</div>
						<div class="choose-data">
							<ul>
								<li>数据一 <div class="data-edit">修改</div></li>
								<li>数据一 <div class="data-edit">修改</div></li>
								<li>数据一 <div class="data-edit">修改</div></li>
								<li>数据一 <div class="data-edit">修改</div></li>
								<li>数据一 <div class="data-edit">修改</div></li>
								<li>数据一 <div class="data-edit">修改</div></li>
								<li>数据一 <div class="data-edit">修改</div></li>
								<li>数据一 <div class="data-edit">修改</div></li>
								<li>数据一 <div class="data-edit">修改</div></li>
								<li>数据一 <div class="data-edit">修改</div></li>
								<li>数据一 <div class="data-edit">修改</div></li>
							</ul>
						</div>
					</div>
					 --%>
					<div class="item" >
						<div class="word">设备监控模板</div>
						<div class="g-select form" >
							<select id="monitorTemp">
								<option value="-1" >请选择模板</option>
							</select>
							<span class="arr-icon"></span>
						</div>
						<span class="error-msg" >请选择模板</span>
					</div>
					<div class="item">
						<div class="word">设备数据同步模板</div>
						<div class="g-select form">
							<select id="dbSyncTemp">
								<option value="-1" >请选择模板</option>
							</select>
							<span class="arr-icon"></span>
						</div>
						<span class="error-msg" >请选择模板</span>
					</div>
				</div>
				<div class="field-title">
					<span class="word"><span>NCIP命令模板</span></span>
				</div>
				<div class="muban">
					<ul>
						<li >
							<div class="word">上传zip文件：</div>
							<div class="input">
								<input type="file" name="file" id="ncipTemplate" accept='aplication/zip'></input>
							</div>
						</li>
					</ul>
				</div>
				<input type="submit" value="注册" class="g-submit g-btn-blue">
			</div>
			<div class="register-result">
				<div class="status-word">恭喜你，设备已经注册成功</div>
				<div class="tips-word">
					你的设备在完成注册后，后台系统需下载一<br>些数据更新，请稍等片刻。即将自动跳转...
				</div>
				<div ><input type="button"  class="register-back g-btn-blue" value="返回">
				</div>
			</div>
		</div>
	</div>
	
	<%-- 设备硬件模板配置模板div --%>
	<div class="form-dialog-fix w-900 yingjian">
		<div class="shade"></div>
		<div class="form-dialog">
			<div class="title">
				新增配置
				<input type="reset" value="取消" class="g-cancel2 g-btn-gray">
				<input type="submit" placeholder="" id="extTempSave"   class="g-submit2 g-btn-green" value="保存">
				<input type="button" placeholder="" id="extTempRestore"   class="g-submit2 g-btn-blue" style="margin-right: 10px;" value="恢复至模板">
			</div>
			<div class="left-tab" id="ext_left_tab">
				<ul>
				<%--
					AuthorityBarcode	{\zh_CN\":\"读者证条码枪\"}"
					AuthorityReader	{\zh_CN\":\"读者证阅读器\"}"
					CardDispenser	{\zh_CN\":\"发卡机\"}"
					CashAcceptor	{\zh_CN\":\"收钞机\"}"
					IdentityReader	{\zh_CN\":\"身份证阅读器\"}"
					ItemBarcode	{\zh_CN\":\"图书条码枪\"}"
					ItemLoadReader	{\zh_CN\":\"补书阅读器\"}"
					ItemRfidReader	{\zh_CN\":\"图书阅读器\"}"
					PlcReturn	{\zh_CN\":\"还书机PLC\"}"
					PlcSorter	{\zh_CN\":\"分拣机PLC\"}"
					PlcSSL	{\zh_CN\":\"自助图书馆PLC\"}"
					Printer	{\zh_CN\":\"打印机\"}"
					RegisterReader	{\zh_CN\":\"注册阅读器\"}"
					Other	{\zh_CN\":\"其他\"}"
				 --%>
					<li class="hide" id="AuthorityBarcode">读者证条码枪</li>
					<li class="hide" id="AuthorityReader">读者证阅读器</li>
					<li class="hide" id="CardDispenser">发卡机</li>
					<li class="hide" id="CashAcceptor">收钞机</li>
					<li class="hide" id="IdentityReader">身份证阅读器</li>
					<li class="hide" id="ItemBarcode">图书条码枪</li>
					<li class="hide" id="ItemLoadReader">补书阅读器</li>
					<li class="hide" id="ItemRfidReader">图书阅读器</li>
					<li class="hide" id="PlcReturn">还书机PLC</li>
					<li class="hide" id="PlcSorter">分拣机PLC</li>
					<li class="hide" id="PlcSSL">自助图书馆PLC</li>
					<li class="hide" id="Printer">打印机</li>
					<li class="hide" id="RegisterReader">发卡阅读器</li>
					<li class="hide" id="Switcher">Switch开关</li>
					<li class="hide" id="Other">其他</li>
				</ul>
			</div>
			<div class="right-form" id="ext_right_form">
			
				
				<%-- 读者证条码枪 AuthorityBarcode --%>
				<div class="right-content hide">
					<div class="form-wrap form-wrap2">
						<ul>

							<li style="display:none">
								<div class="left">模板ID</div>
								<div class="right">
									<input type="text" name="" id="AuthorityBarcode_id" class="g-input" value="读者证条码枪" readonly="readonly" style="color: gray;background-color: #E0E0E0"
										placeholder="请输入" /> <span class="error-msg">错误提示</span>
								</div>
							</li>
							<li class="hide">
								<div class="left">逻辑名</div>
								<div class="right">
									<div class="g-select" >
										<select id="AuthorityBarcode_logicName" class="logicname">
											<option value="-1" disabled="disabled">请选择</option>
										</select> <span class="arr-icon"></span>
									</div>
								</div>
							</li>
							<li>
								<div class="left">逻辑名</div>
								<div class="right">
									<input type="text" name="" readonly="readonly" id="AuthorityBarcode_showName"  class="g-input readonly" /> <span class="error-msg">请输入模板id</span>
								</div>
							</li>
						
							<li>
								<div class="left">外设部件</div>
								<div class="right">
									<div class="g-select">
										<select id="AuthorityBarcode_extModel" class="need-change extmodel">
											<option value="-1" selected>请选择</option>
										</select> <span class="arr-icon"></span>
									</div>
								</div>
							</li>
							<li>
								<div class="left">LED灯序号</div>
								<div class="right">
									<input type="text" name="" id="AuthorityBarcode_Light" class="g-input lightnum" 
									    placeholder="请输入"/> 
								</div>
							</li>
							<li>
								<div class="left">外设路径</div>
								<div class="right">
									<input type="text" name="" id="AuthorityBarcode_DriverPath" class="g-input driverPath"
										placeholder="请输入"/> 
								</div>
							</li>
							
							<li>
								<div class="left">天线配置项</div>
								<div class="right">
									<input type="text" name="" id="AuthorityBarcode_AntAssociated"  class="g-input antAssociated"
										placeholder="请输入"/> 
								</div>
							</li>
							<li>
								<div class="left">通讯方式</div>
								<div class="right">
									<div class="g-select">
										<select id="AuthorityBarcode_comm" class="need-change">
											<option value="-1">选择通讯方式</option>
											<option value="com">COM</option>
											<option value="usb">USB</option>
											<option value="lpt">LPT</option>
											<option value="net">NET</option>
										</select> <span class="arr-icon"></span>
									</div>
								</div>
							</li>
							<li>
								<div class="changebox" date-id="AuthorityBarcode_comm" val="com">
									<ul>
										<li>
											<div class="left">COM口</div>
											<div class="right">
												<input type="text" name="" id="AuthorityBarcode_COM" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
										<li>
											<div class="left">波特率</div>
											<div class="right">
												<input type="text" name="" id="AuthorityBarcode_BAUD" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
									</ul>
								</div>
								<div class="changebox" date-id="AuthorityBarcode_comm" val="usb">
									<ul>
										<li>
											<div class="left">USB</div>
											<div class="right">
												<input type="text" name="" id="AuthorityBarcode_USB" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
									</ul>
								</div>
								<div class="changebox" date-id="AuthorityBarcode_comm" val="net">
									<ul>
										<li>
											<div class="left">IP</div>
											<div class="right">
												<input type="text" name="" id="AuthorityBarcode_IP" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
										<li>
											<div class="left">端口</div>
											<div class="right">
												<input type="text" name="" id="AuthorityBarcode_PORT" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
									</ul>
								</div>
							</li>
							
						</ul>
					</div>
				</div>
				
				<%-- 读者证阅读器 AuthorityReader --%>
				<div class="right-content hide">
					<div class="form-wrap form-wrap2">
						<ul>

							<li style="display:none">
								<div class="left">模板ID</div>
								<div class="right">
									<input type="text" name="" id="" class="g-input" value="读者证阅读器" readonly="readonly" style="color: gray;background-color: #E0E0E0"
										placeholder="请输入" /> <span class="error-msg">错误提示</span>
								</div>
							</li>
							<li class="hide">
								<div class="left">逻辑名</div>
								<div class="right">
									<div class="g-select">
										<select id="AuthorityReader_logicName" class="logicname">
											<option value="" selected>请选择</option>
										</select> <span class="arr-icon"></span>
									</div>
								</div>
							</li>
							
							<li>
								<div class="left">逻辑名</div>
								<div class="right">
									<input type="text" name="" readonly="readonly" id="AuthorityReader_showName"  class="g-input readonly" /> <span class="error-msg">请输入模板id</span>
								</div>
							</li>
							
							<li>
								<div class="left">外设部件</div>
								<div class="right">
									<div class="g-select">
										<select id="AuthorityReader_extModel" class="need-change extmodel">
											<option value="-1" selected>请选择</option>
										</select> <span class="arr-icon"></span>
									</div>
								</div>
							</li>
							<li>
								<div class="left">LED灯序号</div>
								<div class="right">
									<input type="text" name="" id="AuthorityReader_Light" class="g-input lightnum" 
									    placeholder="请输入"/> 
								</div>
							</li>
							<li>
								<div class="left">外设路径</div>
								<div class="right">
									<input type="text" name="" id="AuthorityReader_DriverPath" class="g-input driverPath"
										placeholder="请输入"/> 
								</div>
							</li>
							
							<li>
								<div class="left">天线配置项</div>
								<div class="right">
									<input type="text" name="" id="AuthorityReader_AntAssociated"  class="g-input antAssociated"
										placeholder="请输入"/> 
								</div>
							</li>
							<li>
								<div class="left">通讯方式</div>
								<div class="right">
									<div class="g-select">
										<select id="AuthorityReader_comm" class="need-change">
											<option value="-1">选择通讯方式</option>
											<option value="com">COM</option>
											<option value="usb">USB</option>
											<option value="lpt">LPT</option>
											<option value="net">NET</option>
										</select> <span class="arr-icon"></span>
									</div>
								</div>
							</li>
							<li>
								<div class="changebox" date-id="AuthorityReader_comm" val="com">
									<ul>
										<li>
											<div class="left">COM口</div>
											<div class="right">
												<input type="text" name="" id="AuthorityReader_COM" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
										<li>
											<div class="left">波特率</div>
											<div class="right">
												<input type="text" name="" id="AuthorityReader_BAUD" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
									</ul>
								</div>
								<div class="changebox" date-id="AuthorityReader_comm" val="usb">
									<ul>
										<li>
											<div class="left">USB</div>
											<div class="right">
												<input type="text" name="" id="AuthorityReader_USB" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
									</ul>
								</div>
								<div class="changebox" date-id="AuthorityReader_comm" val="net">
									<ul>
										<li>
											<div class="left">IP</div>
											<div class="right">
												<input type="text" name="" id="AuthorityReader_IP" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
										<li>
											<div class="left">端口</div>
											<div class="right">
												<input type="text" name="" id="AuthorityReader_PORT" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
									</ul>
								</div>
							</li>
						</ul>
					</div>
				</div>
				
				<%-- 发卡机CardDispenser --%>
				<div class="right-content hide">
					<div class="form-wrap form-wrap2">
						<ul>

							<li style="display:none">
								<div class="left">模板ID</div>
								<div class="right">
									<input type="text" name="" id="" class="g-input" value="发卡机" readonly="readonly" style="color: gray;background-color: #E0E0E0"
										placeholder="请输入" /> <span class="error-msg">错误提示</span>
								</div>
							</li>
							<li class="hide">
								<div class="left">逻辑名</div>
								<div class="right">
									<div class="g-select">
										<select  id="CardDispenser_logicName" class="logicname">
											<option value="" selected>请选择</option>
										</select> <span class="arr-icon"></span>
									</div>
								</div>
							</li>
							<li>
								<div class="left">逻辑名</div>
								<div class="right">
									<input type="text" name="" readonly="readonly" id="CardDispenser_showName"  class="g-input readonly" /> <span class="error-msg">请输入模板id</span>
								</div>
							</li>
							
							<li>
								<div class="left">外设部件</div>
								<div class="right">
									<div class="g-select">
										<select id="CardDispenser_extModel" class="need-change extmodel">
											<option value="-1" selected>请选择</option>
										</select> <span class="arr-icon"></span>
									</div>
								</div>
							</li>
							<li>
								<div class="left">LED灯序号</div>
								<div class="right">
									<input type="text" name="" id="CardDispenser_Light" class="g-input lightnum" 
									    placeholder="请输入"/> 
								</div>
							</li>
							<li>
								<div class="left">外设路径</div>
								<div class="right">
									<input type="text" name="" id="CardDispenser_DriverPath" class="g-input driverPath"
										placeholder="请输入"/> 
								</div>
							</li>
							
							<li>
								<div class="left">天线配置项</div>
								<div class="right">
									<input type="text" name="" id="CardDispenser_AntAssociated"  class="g-input antAssociated"
										placeholder="请输入"/> 
								</div>
							</li>
							<li>
								<div class="left">通讯方式</div>
								<div class="right">
									<div class="g-select">
										<select id="CardDispenser_comm" class="need-change">
											<option value="-1">选择通讯方式</option>
											<option value="com">COM</option>
											<option value="usb">USB</option>
											<option value="lpt">LPT</option>
											<option value="net">NET</option>
										</select> <span class="arr-icon"></span>
									</div>
								</div>
							</li>
							<li>
								<div class="changebox" date-id="CardDispenser_comm" val="com">
									<ul>
										<li>
											<div class="left">COM口</div>
											<div class="right">
												<input type="text" name="" id="CardDispenser_COM" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
										<li>
											<div class="left">波特率</div>
											<div class="right">
												<input type="text" name="" id="CardDispenser_BAUD" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
									</ul>
								</div>
								<div class="changebox" date-id="CardDispenser_comm" val="usb">
									<ul>
										<li>
											<div class="left">USB</div>
											<div class="right">
												<input type="text" name="" id="CardDispenser_USB" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
									</ul>
								</div>
								<div class="changebox" date-id="CardDispenser_comm" val="net">
									<ul>
										<li>
											<div class="left">IP</div>
											<div class="right">
												<input type="text" name="" id="CardDispenser_IP" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
										<li>
											<div class="left">端口</div>
											<div class="right">
												<input type="text" name="" id="CardDispenser_PORT" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
									</ul>
								</div>
							</li>
						</ul>
					</div>
				</div>
				
				<%-- 收钞机CashAcceptor --%>
				<div class="right-content hide">
					<div class="form-wrap form-wrap2">
					<div class="com-item">
						<ul>
							<li  style="display:none">
								<div class="left">模板ID</div>
								<div class="right">
									<input type="text" name="" id="" class="g-input" value="收钞机" readonly="readonly" style="color: gray;background-color: #E0E0E0"
										placeholder="请输入" /> <span class="error-msg">错误提示</span>
								</div>
							</li>
							<li class="hide">
								<div class="left">逻辑名</div>
								<div class="right">
									<div class="g-select">
										<select id="CashAcceptor_logicName" class="logicname">
											<option value="" selected>请选择</option>
										</select> <span class="arr-icon"></span>
									</div>
								</div>
							</li>
							<li>
								<div class="left">逻辑名</div>
								<div class="right">
									<input type="text" name="" readonly="readonly" id="CashAcceptor_showName"  class="g-input readonly" /> <span class="error-msg">请输入模板id</span>
								</div>
							</li>
							<li>
								<div class="left">外设部件</div>
								<div class="right">
									<div class="g-select">
										<select id="CashAcceptor_extModel" class="need-change extmodel">
											<option value="-1" selected>请选择</option>
										</select> <span class="arr-icon"></span>
									</div>
								</div>
							</li>
							<li>
								<div class="left">LED灯序号</div>
								<div class="right">
									<input type="text" name="" id="CashAcceptor_Light" class="g-input lightnum" 
									    placeholder="请输入"/> 
								</div>
							</li>
							<li>
								<div class="left">外设路径</div>
								<div class="right">
									<input type="text" name="" id="CashAcceptor_DriverPath" class="g-input driverPath"
										placeholder="请输入"/> 
								</div>
							</li>
							
							<li>
								<div class="left">天线配置项</div>
								<div class="right">
									<input type="text" name="" id="CashAcceptor_AntAssociated"  class="g-input antAssociated"
										placeholder="请输入"/> 
								</div>
							</li>
							<li>
								<div class="left">通讯方式</div>
								<div class="right">
									<div class="g-select">
										<select id="CashAcceptor_comm" class="need-change">
											<option value="-1">选择通讯方式</option>
											<option value="com">COM</option>
											<option value="usb">USB</option>
											<option value="lpt">LPT</option>
											<option value="net">NET</option>
										</select> <span class="arr-icon"></span>
									</div>
								</div>
							</li>
							<li>
								<div class="changebox" date-id="CashAcceptor_comm" val="com">
									<ul>
										<li>
											<div class="left">COM口</div>
											<div class="right">
												<input type="text" name="" id="CashAcceptor_COM" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
										<li>
											<div class="left">波特率</div>
											<div class="right">
												<input type="text" name="" id="CashAcceptor_BAUD" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
									</ul>
								</div>
								<div class="changebox" date-id="CashAcceptor_comm" val="usb">
									<ul>
										<li>
											<div class="left">USB</div>
											<div class="right">
												<input type="text" name="" id="CashAcceptor_USB" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
									</ul>
								</div>
								<div class="changebox" date-id="CashAcceptor_comm" val="net">
									<ul>
										<li>
											<div class="left">IP</div>
											<div class="right">
												<input type="text" name="" id="CashAcceptor_IP" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
										<li>
											<div class="left">端口</div>
											<div class="right">
												<input type="text" name="" id="CashAcceptor_PORT" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
									</ul>
								</div>
							</li>
							<li>
								<div class="changebox" id="money" val="" style="display: block">
									<span class="t">收款通道</span>
									<div class="com-item" style="margin-left:31px">
										<div class="g-checkbox"><input type="checkbox" name="oneyuan" id="oneyuan" value="1" ></div><label for="oneyuan">1元</label>
										<div class="g-checkbox"><input type="checkbox" name="fiveyuan" id="fiveyuan" value="5" ></div><label for="fiveyuan" >5元</label>
										<div class="g-checkbox"><input type="checkbox" name="tenyuan" id="tenyuan" value="10" ></div><label for="tenyuan">10元</label>
									</div>
									<div class="com-item" style="margin-left:31px">
										<div class="g-checkbox"><input type="checkbox" name="twentyyuan" id="twentyyuan" value="20" ></div><label for="twentyyuan">20元</label>
										<div class="g-checkbox"><input type="checkbox" name="fiftyyuan" id="fiftyyuan" value="50" ></div><label for="fiftyyuan">50元</label>
										<div class="g-checkbox"><input type="checkbox" name="hundredyuan" id="hundredyuan" value="100" ></div><label for="hundredyuan">100元</label>
									</div>
								</div>
							</li>
							
						</ul>
						</div>
					</div>
				</div>
				
				<%-- 身份证阅读器IdentityReader --%>
				<div class="right-content hide">
					<div class="form-wrap form-wrap2">
						<ul>

							<li style="display:none">
								<div class="left">模板ID</div>
								<div class="right">
									<input type="text" name="" id="" class="g-input" value="身份证阅读器" readonly="readonly" style="color: gray;background-color: #E0E0E0"
										placeholder="请输入" /> <span class="error-msg">错误提示</span>
								</div>
							</li>
							<li class="hide">
								<div class="left">逻辑名</div>
								<div class="right">
									<div class="g-select" >
										<select id="IdentityReader_logicName" class="logicname">
											<option value="-1" selected>请选择</option>
										</select> <span class="arr-icon"></span>
									</div>
								</div>
							</li>
							
							<li>
								<div class="left">逻辑名</div>
								<div class="right">
									<input type="text" name="" readonly="readonly" id="IdentityReader_showName"  class="g-input readonly" /> <span class="error-msg">请输入模板id</span>
								</div>
							</li>
							
							<li>
								<div class="left">外设部件</div>
								<div class="right">
									<div class="g-select">
										<select id="IdentityReader_extModel" class="need-change extmodel">
										</select> <span class="arr-icon"></span>
									</div>
								</div>
							</li>
							<li>
								<div class="left">LED灯序号</div>
								<div class="right">
									<input type="text" name="" id="IdentityReader_Light" class="g-input lightnum" 
									    placeholder="请输入"/> 
								</div>
							</li>
							<li>
								<div class="left">外设路径</div>
								<div class="right">
									<input type="text" name="" id="IdentityReader_DriverPath" class="g-input driverPath"
										placeholder="请输入"/> 
								</div>
							</li>
							
							<li>
								<div class="left">天线配置项</div>
								<div class="right">
									<input type="text" name="" id="IdentityReader_AntAssociated"  class="g-input antAssociated"
										placeholder="请输入"/> 
								</div>
							</li>
							<li>
								<div class="left">通讯方式</div>
								<div class="right">
									<div class="g-select">
										<select id="IdentityReader_comm" class="need-change">
											<option value="-1">选择通讯方式</option>
											<option value="com">COM</option>
											<option value="usb">USB</option>
											<option value="lpt">LPT</option>
											<option value="net">NET</option>
										</select> <span class="arr-icon"></span>
									</div>
								</div>
							</li>
							<li>
								<div class="changebox" date-id="IdentityReader_comm" val="com">
									<ul>
										<li>
											<div class="left">COM口</div>
											<div class="right">
												<input type="text" name="" id="IdentityReader_COM" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
										<li>
											<div class="left">波特率</div>
											<div class="right">
												<input type="text" name="" id="IdentityReader_BAUD" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
									</ul>
								</div>
								<div class="changebox" date-id="IdentityReader_comm" val="usb">
									<ul>
										<li>
											<div class="left">USB</div>
											<div class="right">
												<input type="text" name="" id="IdentityReader_USB" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
									</ul>
								</div>
								<div class="changebox" date-id="IdentityReader_comm" val="net">
									<ul>
										<li>
											<div class="left">IP</div>
											<div class="right">
												<input type="text" name="" id="IdentityReader_IP" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
										<li>
											<div class="left">端口</div>
											<div class="right">
												<input type="text" name="" id="IdentityReader_PORT" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
									</ul>
								</div>
							</li>


						</ul>
					</div>
				</div>
				
				<%-- 图书条码枪ItemBarcode --%>
				<div class="right-content hide">
					<div class="form-wrap form-wrap2">
						<ul>

							<li style="display:none">
								<div class="left">模板ID</div>
								<div class="right">
									<input type="text" name="" id="" class="g-input" value="图书条码枪" readonly="readonly" style="color: gray;background-color: #E0E0E0"
										placeholder="请输入" /> <span class="error-msg">错误提示</span>
								</div>
							</li>
							<li class="hide">
								<div class="left">逻辑名</div>
								<div class="right">
									<div class="g-select">
										<select id="ItemBarcode_logicName" class="logicname">
											<option value="" selected>请选择</option>
										</select> <span class="arr-icon"></span>
									</div>
								</div>
							</li>
							
							<li>
								<div class="left">逻辑名</div>
								<div class="right">
									<input type="text" name="" readonly="readonly" id="ItemBarcode_showName"  class="g-input readonly" /> <span class="error-msg">请输入模板id</span>
								</div>
							</li>
							<li>
								<div class="left">外设部件</div>
								<div class="right">
									<div class="g-select">
										<select id="ItemBarcode_extModel" class="need-change extmodel">
											<option value="-1" selected>请选择</option>
										</select> <span class="arr-icon"></span>
									</div>
								</div>
							</li>
							<li>
								<div class="left">LED灯序号</div>
								<div class="right">
									<input type="text" name="" id="ItemBarcode_Light" class="g-input lightnum" 
									    placeholder="请输入"/> 
								</div>
							</li>
							<li>
								<div class="left">外设路径</div>
								<div class="right">
									<input type="text" name="" id="ItemBarcode_DriverPath" class="g-input driverPath"
										placeholder="请输入"/> 
								</div>
							</li>
							<li>
								<div class="left">天线配置项</div>
								<div class="right">
									<input type="text" name="" id="ItemBarcode_AntAssociated"  class="g-input antAssociated"
										placeholder="请输入"/> 
								</div>
							</li>
							<li>
								<div class="left">通讯方式</div>
								<div class="right">
									<div class="g-select">
										<select id="ItemBarcode_comm" class="need-change">
											<option value="-1">选择通讯方式</option>
											<option value="com">COM</option>
											<option value="usb">USB</option>
											<option value="lpt">LPT</option>
											<option value="net">NET</option>
										</select> <span class="arr-icon"></span>
									</div>
								</div>
							</li>
							<li>
								<div class="changebox" date-id="ItemBarcode_comm" val="com">
									<ul>
										<li>
											<div class="left">COM口</div>
											<div class="right">
												<input type="text" name="" id="ItemBarcode_COM" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
										<li>
											<div class="left">波特率</div>
											<div class="right">
												<input type="text" name="" id="ItemBarcode_BAUD" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
									</ul>
								</div>
								<div class="changebox" date-id="ItemBarcode_comm" val="usb">
									<ul>
										<li>
											<div class="left">USB</div>
											<div class="right">
												<input type="text" name="" id="ItemBarcode_USB" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
									</ul>
								</div>
								<div class="changebox" date-id="ItemBarcode_comm" val="net">
									<ul>
										<li>
											<div class="left">IP</div>
											<div class="right">
												<input type="text" name="" id="ItemBarcode_IP" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
										<li>
											<div class="left">端口</div>
											<div class="right">
												<input type="text" name="" id="ItemBarcode_PORT" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
									</ul>
								</div>
							</li>
						</ul>
					</div>
				</div>
				
				<%-- 补书阅读器 ItemLoadReader--%>
				<div class="right-content hide">
					<div class="form-wrap form-wrap2">
						<ul>

							<li  style="display:none">
								<div class="left">模板ID</div>
								<div class="right">
									<input type="text" name="" id="" class="g-input" value="补书阅读器" readonly="readonly" style="color: gray;background-color: #E0E0E0"
										placeholder="请输入" /> <span class="error-msg">错误提示</span>
								</div>
							</li>
							<li class="hide">
								<div class="left">逻辑名</div>
								<div class="right">
									<div class="g-select">
										<select id="ItemLoadReader_logicName" class="logicname">
											<option value="" selected>请选择</option>
										</select> <span class="arr-icon"></span>
									</div>
								</div>
							</li>
							<li>
								<div class="left">逻辑名</div>
								<div class="right">
									<input type="text" name="" readonly="readonly" id="ItemLoadReader_showName"  class="g-input readonly" /> <span class="error-msg">请输入模板id</span>
								</div>
							</li>
							<li>
								<div class="left">外设部件</div>
								<div class="right">
									<div class="g-select">
										<select id="ItemLoadReader_extModel" class="need-change extmodel">
											<option value="-1" selected>请选择</option>
										</select> <span class="arr-icon"></span>
									</div>
								</div>
							</li>
							<li>
								<div class="left">LED灯序号</div>
								<div class="right">
									<input type="text" name="" id="ItemLoadReader_Light" class="g-input lightnum" 
									    placeholder="请输入"/> 
								</div>
							</li>
							<li>
								<div class="left">外设路径</div>
								<div class="right">
									<input type="text" name="" id="ItemLoadReader_DriverPath" class="g-input driverPath"
										placeholder="请输入"/> 
								</div>
							</li>
							
							<li>
								<div class="left">天线配置项</div>
								<div class="right">
									<input type="text" name="" id="ItemLoadReader_AntAssociated"  class="g-input antAssociated"
										placeholder="请输入"/> 
								</div>
							</li>
							<li>
								<div class="left">通讯方式</div>
								<div class="right">
									<div class="g-select">
										<select id="ItemLoadReader_comm" class="need-change">
											<option value="-1">选择通讯方式</option>
											<option value="com">COM</option>
											<option value="usb">USB</option>
											<option value="lpt">LPT</option>
											<option value="net">NET</option>
										</select> <span class="arr-icon"></span>
									</div>
								</div>
							</li>
							<li>
								<div class="changebox" date-id="ItemLoadReader_comm" val="com">
									<ul>
										<li>
											<div class="left">COM口</div>
											<div class="right">
												<input type="text" name="" id="ItemLoadReader_COM" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
										<li>
											<div class="left">波特率</div>
											<div class="right">
												<input type="text" name="" id="ItemLoadReader_BAUD" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
									</ul>
								</div>
								<div class="changebox" date-id="ItemLoadReader_comm" val="usb">
									<ul>
										<li>
											<div class="left">USB</div>
											<div class="right">
												<input type="text" name="" id="ItemLoadReader_USB" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
									</ul>
								</div>
								<div class="changebox" date-id="ItemLoadReader_comm" val="net">
									<ul>
										<li>
											<div class="left">IP</div>
											<div class="right">
												<input type="text" name="" id="ItemLoadReader_IP" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
										<li>
											<div class="left">端口</div>
											<div class="right">
												<input type="text" name="" id="ItemLoadReader_PORT" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
									</ul>
								</div>
							</li>
						</ul>
					</div>
				</div>
				
				<%-- 图书阅读器ItemRfidReader --%>
				<div class="right-content hide">
					<div class="form-wrap form-wrap2">
						<ul>

							<li  style="display:none">
								<div class="left">模板ID</div>
								<div class="right">
									<input type="text" name="" id="" class="g-input" value="图书阅读器" readonly="readonly" style="color: gray;background-color: #E0E0E0"
										placeholder="请输入" /> <span class="error-msg">错误提示</span>
								</div>
							</li>
							<li class="hide">
								<div class="left">逻辑名</div>
								<div class="right">
									<div class="g-select">
										<select id="ItemRfidReader_logicName" class="logicname">
											<option value="" selected>请选择</option>
										</select> <span class="arr-icon"></span>
									</div>
								</div>
							</li>
							
							<li>
								<div class="left">逻辑名</div>
								<div class="right">
									<input type="text" name="" readonly="readonly" id="ItemRfidReader_showName"  class="g-input readonly" /> <span class="error-msg">请输入模板id</span>
								</div>
							</li>
							<li>
								<div class="left">外设部件</div>
								<div class="right">
									<div class="g-select">
										<select id="ItemRfidReader_extModel" class="need-change extmodel">
											<option value="-1" selected>请选择</option>
										</select> <span class="arr-icon"></span>
									</div>
								</div>
							</li>
							<li>
								<div class="left">LED灯序号</div>
								<div class="right">
									<input type="text" name="" id="ItemRfidReader_Light" class="g-input lightnum" 
									    placeholder="请输入"/> 
								</div>
							</li>
							<li>
								<div class="left">外设部件</div>
								<div class="right">
									<div class="g-select">
										<select id="ItemRfidReader_extModel" class="need-change extmodel">
											<option value="-1" selected>请选择</option>
										</select> <span class="arr-icon"></span>
									</div>
								</div>
							</li>
							<li>
								<div class="left">外设路径</div>
								<div class="right">
									<input type="text" name="" id="ItemRfidReader_DriverPath" class="g-input driverPath"
										placeholder="请输入"/> 
								</div>
							</li>
							<li>
								<div class="left">通讯方式</div>
								<div class="right">
									<div class="g-select">
										<select id="ItemRfidReader_comm" class="need-change">
											<option value="-1">选择通讯方式</option>
											<option value="com">COM</option>
											<option value="usb">USB</option>
											<option value="lpt">LPT</option>
											<option value="net">NET</option>
										</select> <span class="arr-icon"></span>
									</div>
								</div>
							</li>
							<li>
								<div class="changebox" date-id="ItemRfidReader_comm" val="com">
									<ul>
										<li>
											<div class="left">COM口</div>
											<div class="right">
												<input type="text" name="" id="ItemRfidReader_COM" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
										<li>
											<div class="left">波特率</div>
											<div class="right">
												<input type="text" name="" id="ItemRfidReader_BAUD" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
									</ul>
								</div>
								<div class="changebox" date-id="ItemRfidReader_comm" val="usb">
									<ul>
										<li>
											<div class="left">USB</div>
											<div class="right">
												<input type="text" name="" id="ItemRfidReader_USB" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
									</ul>
								</div>
								<div class="changebox" date-id="ItemRfidReader_comm" val="net">
									<ul>
										<li>
											<div class="left">IP</div>
											<div class="right">
												<input type="text" name="" id="ItemRfidReader_IP" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
										<li>
											<div class="left">端口</div>
											<div class="right">
												<input type="text" name="" id="ItemRfidReader_PORT" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
									</ul>
								</div>
							</li>
						</ul>
					</div>
				</div>
				
				<%-- 还书机PLC PlcReturn PlcSorter PlcSSL --%>
				<div class="right-content hide">
					<div class="form-wrap form-wrap2">
						<ul>

							<li style="display:none" >
								<div class="left">模板ID</div>
								<div class="right">
									<input type="text" name="" id="" class="g-input" value="还书机PLC" readonly="readonly" style="color: gray;background-color: #E0E0E0"
										placeholder="请输入" /> <span class="error-msg">错误提示</span>
								</div>
							</li>
							<li class="hide">
								<div class="left">逻辑名</div>
								<div class="right">
									<div class="g-select">
										<select id="PlcReturn_logicName" class="logicname">
											<option value="" selected>请选择</option>
										</select> <span class="arr-icon"></span>
									</div>
								</div>
							</li>
							
							<li>
								<div class="left">逻辑名</div>
								<div class="right">
									<input type="text" name="" readonly="readonly" id="PlcReturn_showName"  class="g-input readonly" /> <span class="error-msg">请输入模板id</span>
								</div>
							</li>
							<li>
								<div class="left">外设部件</div>
								<div class="right">
									<div class="g-select">
										<select id="PlcReturn_extModel" class="need-change extmodel">
											<option value="-1" selected>请选择</option>
										</select> <span class="arr-icon"></span>
									</div>
								</div>
							</li>
							<%-- 
							<li>
								<div class="changebox" id="PLC_box" date-id="PLC_extModel" val="">
									<span class="t">书盒信息</span>
									<div class="w50 fl min-input">
										<span class="word">上层书架</span><input type="text" name="" id="shelf1"
											class="g-input" placeholder="" />个
									</div>
									<div class="w50 fl min-input">
										<span class="word">特型书盒</span><input type="text" name="" id="drawer"
											class="g-input" placeholder="" />个
									</div>
									<div class="w50 fl min-input">
										<span class="word">中层书架</span><input type="text" name="" id="shelf2"
											class="g-input" placeholder="" />个
									</div>
									<div class="w50 fl min-input">
										<span class="word">还书箱个数</span><input type="text" name="" id="bookbin"
											class="g-input" placeholder="" />个
									</div>
									<div class="w50 fl min-input">
										<span class="word">下层书架</span><input type="text" name="" id="shelf3"
											class="g-input" placeholder="" />个
									</div>
								</div>
							</li>
							--%>
							<li>
								<div class="left">通讯方式</div>
								<div class="right">
									<div class="g-select">
										<select id="PlcReturn_comm" class="need-change">
											<option value="-1">选择通讯方式</option>
											<option value="com">COM</option>
											<option value="usb">USB</option>
											<option value="lpt">LPT</option>
											<option value="net">NET</option>
										</select> <span class="arr-icon"></span>
									</div>
								</div>
							</li>
							<li>
								<div class="changebox" date-id="PlcReturn_comm" val="com">
									<ul>
										<li>
											<div class="left">COM口</div>
											<div class="right">
												<input type="text" name="" id="PlcReturn_COM" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
										<li>
											<div class="left">波特率</div>
											<div class="right">
												<input type="text" name="" id="PlcReturn_BAUD" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
									</ul>
								</div>
								<div class="changebox" date-id="PlcReturn_comm" val="usb">
									<ul>
										<li>
											<div class="left">USB</div>
											<div class="right">
												<input type="text" name="" id="PlcReturn_USB" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
									</ul>
								</div>
								<div class="changebox" date-id="PlcReturn_comm" val="net">
									<ul>
										<li>
											<div class="left">IP</div>
											<div class="right">
												<input type="text" name="" id="PlcReturn_IP" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
										<li>
											<div class="left">端口</div>
											<div class="right">
												<input type="text" name="" id="PlcReturn_PORT" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
									</ul>
								</div>
							</li>
							
						</ul>
					</div>
				</div>
				
				<%-- 分拣机PLC PlcSorter--%>
				<div class="right-content hide">
					<div class="form-wrap form-wrap2">
						<ul>

							<li style="display:none;">
								<div class="left">模板ID</div>
								<div class="right">
									<input type="text" name="" id="" class="g-input" value="分拣机PLC" readonly="readonly" style="color: gray;background-color: #E0E0E0"
										placeholder="请输入" /> <span class="error-msg">错误提示</span>
								</div>
							</li>
							<li class="hide">
								<div class="left">逻辑名</div>
								<div class="right">
									<div class="g-select">
										<select id="PlcSorter_logicName" class="logicname">
											<option value="" selected>请选择</option>
										</select> <span class="arr-icon"></span>
									</div>
								</div>
							</li>
							
							<li>
								<div class="left">逻辑名</div>
								<div class="right">
									<input type="text" name="" readonly="readonly" id="PlcSorter_showName"  class="g-input readonly" /> <span class="error-msg">请输入模板id</span>
								</div>
							</li>
							
							<li>
								<div class="left">外设部件</div>
								<div class="right">
									<div class="g-select">
										<select id="PlcSorter_extModel" class="need-change extmodel">
											<option value="-1" selected>请选择</option>
										</select> <span class="arr-icon"></span>
									</div>
								</div>
							</li>
							
							<li>
								<div class="left">通讯方式</div>
								<div class="right">
									<div class="g-select">
										<select id="PlcSorter_comm" class="need-change">
											<option value="-1">选择通讯方式</option>
											<option value="com">COM</option>
											<option value="usb">USB</option>
											<option value="lpt">LPT</option>
											<option value="net">NET</option>
										</select> <span class="arr-icon"></span>
									</div>
								</div>
							</li>
							<li>
								<div class="changebox" date-id="PlcSorter_comm" val="com">
									<ul>
										<li>
											<div class="left">COM口</div>
											<div class="right">
												<input type="text" name="" id="PlcSorter_COM" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
										<li>
											<div class="left">波特率</div>
											<div class="right">
												<input type="text" name="" id="PlcSorter_BAUD" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
									</ul>
								</div>
								<div class="changebox" date-id="PlcSorter_comm" val="usb">
									<ul>
										<li>
											<div class="left">USB</div>
											<div class="right">
												<input type="text" name="" id="PlcSorter_USB" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
									</ul>
								</div>
								<div class="changebox" date-id="PlcSorter_comm" val="net">
									<ul>
										<li>
											<div class="left">IP</div>
											<div class="right">
												<input type="text" name="" id="PlcSorter_IP" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
										<li>
											<div class="left">端口</div>
											<div class="right">
												<input type="text" name="" id="PlcSorter_PORT" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
									</ul>
								</div>
							</li>

						</ul>
					</div>
				</div>
				
				<%-- 自助图书馆PLC PlcSSL--%>
				
				<div class="right-content hide">
					<div class="form-wrap form-wrap2">
						<ul>

							<li style="display:none;">
								<div class="left">模板ID</div>
								<div class="right">
									<input type="text" name="" id="" class="g-input" value="自助图书馆PLC" readonly="readonly" style="color: gray;background-color: #E0E0E0"
										placeholder="请输入" /> <span class="error-msg">错误提示</span>
								</div>
							</li>
							<li class="hide">
								<div class="left">逻辑名</div>
								<div class="right">
									<div class="g-select">
										<select id="PlcSSL_logicName" class="logicname">
											<option value="" selected>请选择</option>
										</select> <span class="arr-icon"></span>
									</div>
								</div>
							</li>
							
							<li>
								<div class="left">逻辑名</div>
								<div class="right">
									<input type="text" name="" readonly="readonly" id="PlcSSL_showName"  class="g-input readonly" /> <span class="error-msg">请输入模板id</span>
								</div>
							</li>
							
							<li>
								<div class="left">外设部件</div>
								<div class="right">
									<div class="g-select">
										<select id="PlcSSL_extModel" class="need-change extmodel">
											<option value="-1" selected>请选择</option>
										</select> <span class="arr-icon"></span>
									</div>
								</div>
							</li>
							
							<li>
								<div class="changebox" id="PlcSSL_box" date-id="PlcSSL_extModel" val="">
									<span class="t">书盒信息</span>
									<div class="w50 fl min-input">
										<span class="word">上层书架</span><input type="text" name="" id="shelf1"
											class="g-input" placeholder="" />个
									</div>
									<div class="w50 fl min-input">
										<span class="word">特型书盒</span><input type="text" name="" id="drawer"
											class="g-input" placeholder="" />个
									</div>
									<div class="w50 fl min-input">
										<span class="word">中层书架</span><input type="text" name="" id="shelf2"
											class="g-input" placeholder="" />个
									</div>
									<div class="w50 fl min-input">
										<span class="word">还书箱个数</span><input type="text" name="" id="bookbin"
											class="g-input" placeholder="" />个
									</div>
									<div class="w50 fl min-input">
										<span class="word">下层书架</span><input type="text" name="" id="shelf3"
											class="g-input" placeholder="" />个
									</div>
								</div>
							</li>
							
							
							<li>
								<div class="left">通讯方式</div>
								<div class="right">
									<div class="g-select">
										<select id="PlcSSL_comm" class="need-change">
											<option value="-1">选择通讯方式</option>
											<option value="com">COM</option>
											<option value="usb">USB</option>
											<option value="lpt">LPT</option>
											<option value="net">NET</option>
										</select> <span class="arr-icon"></span>
									</div>
								</div>
							</li>
							<li>
								<div class="changebox" date-id="PlcSSL_comm" val="com">
									<ul>
										<li>
											<div class="left">COM口</div>
											<div class="right">
												<input type="text" name="" id="PlcSSL_COM" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
										<li>
											<div class="left">波特率</div>
											<div class="right">
												<input type="text" name="" id="PlcSSL_BAUD" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
									</ul>
								</div>
								<div class="changebox" date-id="PlcSSL_comm" val="usb">
									<ul>
										<li>
											<div class="left">USB</div>
											<div class="right">
												<input type="text" name="" id="PlcSSL_USB" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
									</ul>
								</div>
								<div class="changebox" date-id="PlcSSL_comm" val="net">
									<ul>
										<li>
											<div class="left">IP</div>
											<div class="right">
												<input type="text" name="" id="PlcSSL_IP" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
										<li>
											<div class="left">端口</div>
											<div class="right">
												<input type="text" name="" id="PlcSSL_PORT" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
									</ul>
								</div>
							</li>
							
						</ul>
					</div>
				</div>
				
				
				<%-- 打印机 Printer --%>
				<div class="right-content hide">
					<div class="form-wrap form-wrap2">
						<ul>

							<li  style="display:none">
								<div class="left">模板ID</div>
								<div class="right">
									<input type="text" name="" id="" class="g-input" value="打印机" readonly="readonly" style="color: gray;background-color: #E0E0E0"
										placeholder="请输入" /> <span class="error-msg">错误提示</span>
								</div>
							</li>
							<li class="hide">
								<div class="left">逻辑名</div>
								<div class="right">
									<div class="g-select">
										<select id="Printer_logicName" class="logicname">
											<option value="" selected>请选择</option>
										</select> <span class="arr-icon"></span>
									</div>
								</div>
							</li>
							
							<li>
								<div class="left">逻辑名</div>
								<div class="right">
									<input type="text" name="" readonly="readonly" id="Printer_showName"  class="g-input readonly" /> <span class="error-msg">请输入模板id</span>
								</div>
							</li>
							
							<li>
								<div class="left">外设部件</div>
								<div class="right">
									<div class="g-select">
										<select id="Printer_extModel" class="need-change extmodel">
											<option value="-1" selected>请选择</option>
										</select> <span class="arr-icon"></span>
									</div>
								</div>
							</li>
							<li>
								<div class="left">LED灯序号</div>
								<div class="right">
									<input type="text" name="" id="Printer_Light" class="g-input lightnum" 
									    placeholder="请输入"/> 
								</div>
							</li>
							<li>
								<div class="left">外设路径</div>
								<div class="right">
									<input type="text" name="" id="Printer_DriverPath" class="g-input driverPath"
										placeholder="请输入"/> 
								</div>
							</li>
							
							<li>
								<div class="left">天线配置项</div>
								<div class="right">
									<input type="text" name="" id="Printer_AntAssociated"  class="g-input antAssociated"
										placeholder="请输入"/> 
								</div>
							</li>
							<li>
								<div class="left">通讯方式</div>
								<div class="right">
									<div class="g-select">
										<select id="Printer_comm" class="need-change">
											<option value="-1">选择通讯方式</option>
											<option value="com">COM</option>
											<option value="usb">USB</option>
											<option value="lpt">LPT</option>
											<option value="net">NET</option>
										</select> <span class="arr-icon"></span>
									</div>
								</div>
							</li>
							<li>
								<div class="changebox" date-id="Printer_comm" val="com">
									<ul>
										<li>
											<div class="left">COM口</div>
											<div class="right">
												<input type="text" name="" id="Printer_COM" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
										<li>
											<div class="left">波特率</div>
											<div class="right">
												<input type="text" name="" id="Printer_BAUD" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
									</ul>
								</div>
								<div class="changebox" date-id="Printer_comm" val="usb">
									<ul>
										<li>
											<div class="left">USB</div>
											<div class="right">
												<input type="text" name="" id="Printer_USB" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
									</ul>
								</div>
								<div class="changebox" date-id="Printer_comm" val="net">
									<ul>
										<li>
											<div class="left">IP</div>
											<div class="right">
												<input type="text" name="" id="Printer_IP" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
										<li>
											<div class="left">端口</div>
											<div class="right">
												<input type="text" name="" id="Printer_PORT" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
									</ul>
								</div>
							</li>
						</ul>
					</div>
				</div>
				
				<%-- 发卡阅读器 RegisterReader --%>
				<div class="right-content hide">
					<div class="form-wrap form-wrap2">
						<ul>

							<li style="display:none">
								<div class="left">模板ID</div>
								<div class="right">
									<input type="text" name="" id="" class="g-input" value="读者证阅读器" readonly="readonly" style="color: gray;background-color: #E0E0E0"
										placeholder="请输入" /> <span class="error-msg">错误提示</span>
								</div>
							</li>
							<li class="hide">
								<div class="left">逻辑名</div>
								<div class="right">
									<div class="g-select">
										<select id="RegisterReader_logicName" class="logicname">
											<option value="" selected>请选择</option>
										</select> <span class="arr-icon"></span>
									</div>
								</div>
							</li>
							
							<li>
								<div class="left">逻辑名</div>
								<div class="right">
									<input type="text" name="" readonly="readonly" id="RegisterReader_showName"  class="g-input readonly" /> <span class="error-msg">请输入模板id</span>
								</div>
							</li>
							<li>
								<div class="left">外设部件</div>
								<div class="right">
									<div class="g-select">
										<select id="RegisterReader_extModel" class="need-change extmodel">
											<option value="-1" selected>请选择</option>
										</select> <span class="arr-icon"></span>
									</div>
								</div>
							</li>
							<li>
								<div class="left">LED灯序号</div>
								<div class="right">
									<input type="text" name="" id="RegisterReader_Light" class="g-input lightnum" 
									    placeholder="请输入"/> 
								</div>
							</li>
							<li>
								<div class="left">外设路径</div>
								<div class="right">
									<input type="text" name="" id="RegisterReader_DriverPath" class="g-input driverPath"
										placeholder="请输入"/> 
								</div>
							</li>
							
							<li>
								<div class="left">天线配置项</div>
								<div class="right">
									<input type="text" name="" id="RegisterReader_AntAssociated"  class="g-input antAssociated"
										placeholder="请输入"/> 
								</div>
							</li>
							<li>
								<div class="left">通讯方式</div>
								<div class="right">
									<div class="g-select">
										<select id="RegisterReader_comm" class="need-change">
											<option value="-1">选择通讯方式</option>
											<option value="com">COM</option>
											<option value="usb">USB</option>
											<option value="lpt">LPT</option>
											<option value="net">NET</option>
										</select> <span class="arr-icon"></span>
									</div>
								</div>
							</li>
							<li>
								<div class="changebox" date-id="RegisterReader_comm" val="com">
									<ul>
										<li>
											<div class="left">COM口</div>
											<div class="right">
												<input type="text" name="" id="RegisterReader_COM" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
										<li>
											<div class="left">波特率</div>
											<div class="right">
												<input type="text" name="" id="RegisterReader_BAUD" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
									</ul>
								</div>
								<div class="changebox" date-id="RegisterReader_comm" val="usb">
									<ul>
										<li>
											<div class="left">USB</div>
											<div class="right">
												<input type="text" name="" id="RegisterReader_USB" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
									</ul>
								</div>
								<div class="changebox" date-id="RegisterReader_comm" val="net">
									<ul>
										<li>
											<div class="left">IP</div>
											<div class="right">
												<input type="text" name="" id="RegisterReader_IP" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
										<li>
											<div class="left">端口</div>
											<div class="right">
												<input type="text" name="" id="RegisterReader_PORT" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
									</ul>
								</div>
							</li>
						</ul>
					</div>
				</div>
				
				<%-- Switcher开关 --%>
				<div class="right-content hide">
					<div class="form-wrap form-wrap2">
						<ul>

							<li style="display:none">
								<div class="left">模板ID</div>
								<div class="right">
									<input type="text" name="" id="" class="g-input" value="读者证阅读器" readonly="readonly" style="color: gray;background-color: #E0E0E0"
										placeholder="请输入" /> <span class="error-msg">错误提示</span>
								</div>
							</li>
							<li class="hide">
								<div class="left">逻辑名</div>
								<div class="right">
									<div class="g-select">
										<select id="Switcher_logicName" class="logicname">
											<option value="" selected>请选择</option>
										</select> <span class="arr-icon"></span>
									</div>
								</div>
							</li>
							
							<li>
								<div class="left">逻辑名</div>
								<div class="right">
									<input type="text" name="" readonly="readonly" id="Switcher_showName"  class="g-input readonly" /> <span class="error-msg">请输入模板id</span>
								</div>
							</li>
							<li>
								<div class="left">外设部件</div>
								<div class="right">
									<div class="g-select">
										<select id="Switcher_extModel" class="need-change extmodel">
											<option value="-1" selected>请选择</option>
										</select> <span class="arr-icon"></span>
									</div>
								</div>
							</li>
							<li>
								<div class="left">外设路径</div>
								<div class="right">
									<input type="text" name="" id="Switcher_DriverPath" class="g-input driverPath"
										placeholder="请输入"/> 
								</div>
							</li>
							
							<li>
								<div class="left">天线配置项</div>
								<div class="right">
									<input type="text" name="" id="Switcher_AntAssociated"  class="g-input antAssociated"
										placeholder="请输入"/> 
								</div>
							</li>
							<li>
								<div class="left">通讯方式</div>
								<div class="right">
									<div class="g-select">
										<select id="Switcher_comm" class="need-change">
											<option value="-1">选择通讯方式</option>
											<option value="com">COM</option>
											<option value="usb">USB</option>
											<option value="lpt">LPT</option>
											<option value="net">NET</option>
										</select> <span class="arr-icon"></span>
									</div>
								</div>
							</li>
							<li>
								<div class="changebox" date-id="Switcher_comm" val="com">
									<ul>
										<li>
											<div class="left">COM口</div>
											<div class="right">
												<input type="text" name="" id="Switcher_COM" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
										<li>
											<div class="left">波特率</div>
											<div class="right">
												<input type="text" name="" id="Switcher_BAUD" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
									</ul>
								</div>
								<div class="changebox" date-id="Switcher_comm" val="usb">
									<ul>
										<li>
											<div class="left">USB</div>
											<div class="right">
												<input type="text" name="" id="Switcher_USB" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
									</ul>
								</div>
								<div class="changebox" date-id="Switcher_comm" val="net">
									<ul>
										<li>
											<div class="left">IP</div>
											<div class="right">
												<input type="text" name="" id="Switcher_IP" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
										<li>
											<div class="left">端口</div>
											<div class="right">
												<input type="text" name="" id="Switcher_PORT" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
									</ul>
								</div>
							</li>
						</ul>
					</div>
				</div>
				
				<%-- 其他 Other--%>
				<div class="right-content hide" id="OtherDiv">
				</div>
				
			</div>
		</div>
	</div>
	
	
	
	<%---
		ACS_config	{\zh_CN\":\"图书馆ACS服务器配置\"}"
		center_config	{\zh_CN\":\"云中心服务器配置\"}"
		function_config	{\zh_CN\":\"功能选项配置\"}"
		language_config	{\zh_CN\":\"界面语言配置\"}"
		localdb_config	{\zh_CN\":\"本地数据库配置\"}"
		print_config	{\zh_CN\":\"打印凭据配置\"}"
		register_config	{\zh_CN\":\"办证类型配置\"}"
		runlog_config	{\zh_CN\":\"运行日志配置\"}"
		scheduletask_config	{\zh_CN\":\"定时任务配置\"}"
		timeout_config	{\zh_CN\":\"超时时间配置\"}"
		
	 --%>
	<%-- 设备运行模板配置div --%>
	<div class="form-dialog-fix w-900 yunxing">
		<div class="shade"></div>
		<div class="form-dialog">
			<div class="title">
				新增配置
				<input type="reset" value="取消" class="g-cancel2 g-btn-gray">
				<input type="submit" placeholder="" id="runTempSave" class="g-submit2 g-btn-green" value="保存">
				<input type="submit" placeholder="" id="runTempRestore" class="g-submit2 g-btn-blue" style="margin-right: 10px;" value="恢复至模板">
			</div>
			<div class="left-tab" id="run_left_tab">
				<ul>
					<li class="active" id="basic">基本配置</li>
					<li id="function">功能配置</li>
					<li id="timeout">超时配置</li>
					<li id="scheduletask">定时任务配置</li>
					<li id="print">打印机配置</li>
					<li id="register">办证配置</li>
					<li id="localdb">本地数据库配置</li>
					<li id="center">云中心配置</li>
					<li id="ACS">ACS服务配置</li>
				</ul>
			</div>
			
			<div class="right-form" id="run_right_form">
				<div class="right-content" data-type="basic">
<!-- 					<div class="form-wrap form-wrap2"> -->
<!-- 						<ul> -->
<!-- 							<li> -->
<!-- 								<div class="left">类型</div> -->
<!-- 								<div class="right"> -->
<!-- 									<span class="btn active">模板</span> -->
<!-- 									<span class="btn">数据</span> -->
<!-- 								</div> -->
<!-- 							</li> -->
							
<!-- 							<li class="error"> -->
<!-- 								<div class="left">模板ID</div> -->
<!-- 								<div class="right"> -->
<!-- 									<input type="text" name="" id="" class="g-input" placeholder="请输入" /> -->
<!-- 									<span class="error-msg">错误提示</span> -->
<!-- 								</div> -->
<!-- 							</li> -->
<!-- 						</ul> -->
<!-- 					</div> -->
					<div class="segmentation">
						<div class="t">语言版本</div>
					</div>
					<div class="form-wrap form-wrap2">
						<ul>
							<li>
								<div class="left">系统语言</div>
								<div class="right">
									<div class="g-select">
										<select id="basic_language" class="need-change">
											<option value="zh_CN"  selected>简体中文</option>
											<option value="en_US">英文</option>
										</select>
										<span class="arr-icon"></span>
									</div>
								</div>
							</li>
						</ul>
					</div>
					<div class="segmentation"><div class="t">运行日志级别</div></div>

					<div class="com-item">
						<div class="g-radio"><input type="radio" name="runlog_level" id="runlog_none" value="none" checked="checked"></div><label for="runlog_none">不记录</label>
					</div>
					<div class="com-item">
						<div class="g-radio"><input type="radio" name="runlog_level" id="runlog_error" value="error"></div><label for="runlog_error">error</label>
					</div>
					<div class="com-item">
						<div class="g-radio"><input type="radio" name="runlog_level" id="runlog_info" value="info"></div><label for="runlog_info">info</label>
					</div>
					<div class="com-item">
						<div class="g-radio"><input type="radio" name="runlog_level" id="runlog_warning" value="warning"></div><label for="runlog_warning">warning</label>
					</div>
					<div class="com-item">
						<div class="g-radio"><input type="radio" name="runlog_level" id="runlog_debug" value="debug"></div><label for="runlog_debug">debug</label>
					</div>

					<div class="segmentation"><div class="t">运行日志保存方式</div></div>

					<div class="com-item">
						<div class="g-checkbox disabled"><input disabled="disabled" type="checkbox" name="runlog_type" id="db" value="db"></div><label for="db">DB</label>
					</div>
					<div class="com-item">
						<div class="g-checkbox on"><input checked="checked" type="checkbox" name="runlog_type" id="file" value="file"></div><label for="file">FIlE</label>
					</div>
					
					<div class="segmentation"><div class="t">运行日志文件大小</div></div>
					<div class="com-item">
						文件大小
						<input type="text" placeholder="5-100" class="g-input w50" name="runlog_filesize" id="runlog_filesize">
						MB	
					</div>
				</div>

<!-- 					function_config	{\zh_CN\":\"功能选项配置\"}" -->
				<%-- 功能选项配置 --%>
				<div class="right-content hide" data-type="function">
					<div class="com-item">
						本设备单日限借次数
						<input type="text" placeholder="0-500" id="function_dailycheckoutlimit" class="w50 g-input">
					</div>
					<div class="segmentation"><div class="t">分拣方式</div></div>
					<div class="com-item">
						<div class="g-radio on"><input type="radio" name="function_sorttype" id="device_sort" value="0" checked="checked"></div><label for="device_sort">设备分拣（满箱自动切换）</label>
					</div>
					<div class="com-item">
						<div class="g-radio"><input type="radio" name="function_sorttype" id="system_sort" value="1"></div><label for="system_sort">系统分拣控制（按类分拣）</label>
					</div>
					<div class="segmentation"><div class="t">读者服务</div></div>
					<ul class="w13li">
						<li>
							<div class="g-checkbox"><input type="checkbox" id="function_register"></div><label for="function_register">允许办证</label>
						</li>
						<li>
							<div class="g-checkbox"><input type="checkbox" id="function_checkout"></div><label for="function_checkout">允许借书</label>
						</li>
						<li>
							<div class="g-checkbox"><input type="checkbox" id="function_checkin"></div><label for="function_checkin">允许还书</label>
						</li>
						<li>
							<div class="g-checkbox"><input type="checkbox" id="function_renew"></div><label for="function_renew">允许续借</label>
						</li>
						<li>
							<div class="g-checkbox"><input type="checkbox" id="function_imprest"></div><label for="function_imprest">预付款</label>
						</li>
						<li>
							<div class="g-checkbox"><input type="checkbox" id="function_feepaid"></div><label for="function_feepaid">缴费</label>
						</li>
					</ul>
					<div class="segmentation"><div class="t">PIN类型</div></div>
					<ul class="w13li">
						<li>
							<div class="g-radio"><input type="radio" name="pin_type" id="pin0" value="0"></div><label for="pin0">不启用PIN码</label>
						</li>
						<li>
							<div class="g-radio on"><input type="radio" name="pin_type" id="pin1" value="1" checked="checked"></div><label for="pin1">PIN码必备</label>
						</li>
						<li>
							<div class="g-radio"><input type="radio" name="pin_type" id="pin2" value="2"></div><label for="pin2">PIN码可选</label>
						</li>
					</ul>
					<div class="segmentation"><div class="t">PIN输入设备</div></div>
					<div class="com-item">
						<div class="com-item">
							<div class="g-radio on"><input type="radio" name="pin_input" id="input_type1" value="digitsoftpin" checked="checked"></div><label for="input_type1">数字密码软键盘</label>
						</div>
						<div class="com-item">
							<div class="g-radio"><input type="radio" name="pin_input" id="input_type2" value="alphabetsoftpin"></div><label for="input_type2">字母数字密码软键盘</label>
						</div>
						<div class="com-item">
							<div class="g-radio"><input type="radio" name="pin_input" id="input_type3" value="digitpin"></div><label for="input_type3">数字密码键盘</label>
						</div>
						<div class="com-item">
							<div class="g-radio"><input type="radio" name="pin_input" id="input_type4" value="alphabetpin"></div><label for="input_type4">字母数字密码键盘</label>
						</div>
					</div>
					<div class="segmentation"><div class="t">PIN长度</div></div>
					<div class="com-item">
						最小字符数：
<!-- 						<div class="min g-input"><input type="input" placeholder="" name="minlength" id="minlength"></div> -->
						<div class="g-select">
							<select id="pin_minlength">
								<option value="0">0</option>
								<option value="1">1</option>
								<option value="2">2</option>
								<option value="3">3</option>
								<option value="4">4</option>
								<option value="5">5</option>
								<option value="6">6</option>
								<option value="7">7</option>
								<option value="8">8</option>
								<option value="9">9</option>
								<option value="10">10</option>
								<option value="11">11</option>
								<option value="12">12</option>
								<option value="13">13</option>
								<option value="14">14</option>
								<option value="15">15</option>
								<option value="16">16</option>
							</select>
							<span class="arr-icon"></span>
						</div>
						个字符
					</div>
					<div class="com-item">
						最大字符数：
<!-- 						<div class="min g-input"><input type="input" placeholder="" name="maxlength" id="maxlength"></div> -->
						<div class="g-select">
							<select id="pin_maxlength">
								<option value="0">0</option>
								<option value="1">1</option>
								<option value="2">2</option>
								<option value="3">3</option>
								<option value="4">4</option>
								<option value="5">5</option>
								<option value="6">6</option>
								<option value="7">7</option>
								<option value="8">8</option>
								<option value="9">9</option>
								<option value="10">10</option>
								<option value="11">11</option>
								<option value="12">12</option>
								<option value="13">13</option>
								<option value="14">14</option>
								<option value="15">15</option>
								<option value="16">16</option>
							</select>
							<span class="arr-icon"></span>
						</div>
						个字符
					</div>

					<div class="segmentation"><div class="t">PIN输入次数</div></div>
					<div class="com-item">
						允许读者输入
<!-- 						<div class="min g-input"><input type="input" placeholder="" name="input_times" id="input_times"></div> -->
						<div class="g-select">
							<select id="pin_retrytimes">
								<option value="1">1</option>
								<option value="2">2</option>
								<option value="3">3</option>
								<option value="4">4</option>
								<option value="5">5</option>
								<option value="6">6</option>
							</select>
							<span class="arr-icon"></span>
						</div>
						次
					</div>
					<div class="com-item">
						<div class="g-radio"><input type="radio" name="pin_retrydo" id="auto_logout" value="0" checked="checked"></div><label for="auto_logout">自动退出</label>
					</div>
					<div class="com-item">
						<div class="g-radio"><input type="radio" name="pin_retrydo" id="anto_lock" value="1"></div><label for="anto_lock">锁定读者</label>
					</div>
					<div class="segmentation"><div class="t">读者证阅读器</div></div>
					<ul class="w22li" >
						<li>
							<div class="g-checkbox"><input type="checkbox" id="card_keyboard"></div><label for="card_keyboard">允许键盘输入</label>
						</li>
						<li>
							<div class="g-checkbox"><input type="checkbox" id="card_rfid"></div><label for="card_rfid">使用 RFID 读者证</label>
						</li>
						<li>
							<div class="g-checkbox"><input type="checkbox" id="card_barcode"></div><label for="card_barcode">条形码阅读器扫描</label>
						</li>
						<li>
							<div class="g-checkbox"><input type="checkbox" id="card_idrfid"></div><label for="card_idrfid">使用身份证</label>
						</li>
					</ul>
					<div class="segmentation"><div class="t">图书阅读器</div></div>

					<div class="com-item">
						<div class="g-checkbox"><input type="checkbox" name="book_barcode" id="book_barcode"></div><label for="book_barcode">条形码阅读器</label>
					</div>
					<div class="com-item">
						<div class="g-checkbox"><input type="checkbox" name="book_rfid" id="book_rfid"></div><label for="book_rfid">RFID 阅读器</label>
					</div>
					<div class="change-box">
						<div class="com-item">
							<div class="g-checkbox"><input type="checkbox" name="book_multiread" id="book_multiread"></div><label for="book_multiread">允许多本阅读</label>
						</div>
						<div class="com-item">
							<div class="g-checkbox"><input type="checkbox" name="book_confirm" id="book_confirm"></div><label for="book_confirm">需要经过扫描确认</label>
						</div>
					</div>
				</div>
				
				<%-- 超时配置 timeout --%>
				<div class="right-content hide" data-type="timeout">
					<ul class="w13li">
						<li>
							<div class="g-checkbox"><input type="checkbox" id="display"></div><label for="display">显示等待时钟</label>
						</li>
						<li>
							<div class="g-checkbox"><input type="checkbox" id="playsound"></div><label for="playsound">打开时钟声音</label>
						</li>
					</ul>
					<div class="segmentation"><div class="t">监控页面切换频率</div></div>
					<div class="com-item">
						<div class="g-checkbox"><input type="checkbox" id="switchscreen_enable"></div><label for="switchscreen_enable">启用自动换屏功能</label>
					</div>
					<div class="com-item">
						每隔
						<div class="g-select">
							<select id="switchscreen_cycle" class="select60">
								<option value="1" selected>1</option>
							</select>
							<span class="arr-icon"></span>
						</div>
						分钟自动换屏
					</div>
					<div class="com-item">
						等待换屏时间
						<div class="g-select">
							<select id="switchscreen_waittime" class="select100">
								<option value="1" selected>1</option>
							</select>
							<span class="arr-icon"></span>
						</div>
						秒
					</div>
					<div class="segmentation"><div class="t">显示错误时间</div></div>
					<div class="com-item">
						一般错误
						<div class="g-select">
							<select id="info_normal" class="select100">
								<option value="1">1</option>
							</select>
							<span class="arr-icon"></span>
						</div>
						秒
					</div>
					<div class="com-item">
						严重错误
						<div class="g-select">
							<select id="info_error" class="select100">
								<option value="1">1</option>
							</select>
							<span class="arr-icon"></span>
						</div>
						秒
					</div>
					<div class="com-item">
						提问超时
						<div class="g-select">
							<select id="info_ask" class="select100">
								<option value="1">1</option>
							</select>
							<span class="arr-icon"></span>
						</div>
						秒
					</div>
					<div class="segmentation"><div class="t">等待操作</div></div>
					<div class="com-item">
						咨询ACS状态&nbsp;&nbsp;&nbsp;&nbsp;
						每隔
						<div class="g-select">
							<select id="timed_acslogin" class="select500">
								<option value="1">1</option>
							</select>
							<span class="arr-icon"></span>
						</div>
						秒
					</div>
					<div class="com-item">
						每
						<div class="g-select">
							<select id="timed_acsstatus" class="select500">
								<option value="1">1</option>
							</select>
							<span class="arr-icon"></span>
						</div>
						秒尝试登录一次
					</div>
					<div class="com-item">
						显示读者信息
						<div class="g-select">
							<select id="info_patroninfo" class="select200">
								<option value="1">1</option>
							</select>
							<span class="arr-icon"></span>
						</div>
						秒
					</div>
					<div class="com-item">
						显示图书时间
						<div class="g-select">
							<select id="info_bookinfo" class="select200">
								<option value="1">1</option>
							</select>
							<span class="arr-icon"></span>
						</div>
						秒
					</div>
					<div class="com-item">
						显示借还时间/处理结果时间
						<div class="g-select">
							<select id="info_operationinfo" class="select200">
								<option value="1">1</option>
							</select>
							<span class="arr-icon"></span>
						</div>
						秒后自动退出
					</div>
					<div class="com-item">
						等待选择功能/密码输入时间
						<div class="g-select">
							<select id="wait_functionselect" class="select300">
								<option value="1">1</option>
							</select>
							<span class="arr-icon"></span>
						</div>
						秒后自动退出
					</div>
					<div class="com-item">
						等待读卡时间
						<div class="g-select">
							<select id="wait_waitcard" class="select300">
								<option value="1">1</option>
							</select>
							<span class="arr-icon"></span>
						</div>
						秒后自动退出
					</div>
					<div class="com-item">
						等待下一本图书时间
						<div class="g-select">
							<select id="wait_waitbook" class="select300">
								<option value="1">1</option>
							</select>
							<span class="arr-icon"></span>
						</div>
						秒后自动退出
					</div>
					<div class="com-item">
						等待出卡时间
						<div class="g-select">
							<select id="wait_popcard" class="select300">
								<option value="1">1</option>
							</select>
							<span class="arr-icon"></span>
						</div>
						秒后自动退出
					</div>
					<div class="com-item">
						等待出书时间
						<div class="g-select">
							<select id="wait_popbook" class="select300">
								<option value="1">1</option>
							</select>
							<span class="arr-icon"></span>
						</div>
						秒后自动退出
					</div>
					<div class="com-item">
						等待取证时间（超时回收）
						<div class="g-select">
							<select id="wait_fetchcard" class="select300">
								<option value="1">1</option>
							</select>
							<span class="arr-icon"></span>
						</div>
						秒后自动退出
					</div>
					<div class="com-item">
						等待取书时间（超时回收）
						<div class="g-select">
							<select id="wait_fetchbook" class="select300">
								<option value="1">1</option>
							</select>
							<span class="arr-icon"></span>
						</div>
						秒后自动退出
					</div>
					<div class="com-item">
						等待收款时间
						<div class="g-select">
							<select id="wait_waitpay" class="select300">
								<option value="1">1</option>
							</select>
							<span class="arr-icon"></span>
						</div>
						秒后自动退出
					</div>
				</div>
				
				
				<%-- 定时任务配置   scheduletask_config--%>
				<div class="right-content hide" data-type="scheduletask">
					<div class="g-checkbox"><input type="checkbox" id="enableweekschedler"></div><label for="enableweekschedler">启用开关机时间表</label>
					<div class="date-table">
						<div class="item">
							<div class="float-item sec1"></div>
							<div class="float-item sec2">开机时间</div>
							<div class="float-item sec3">关机时间</div>
							<div class="float-item sec4"></div>
						</div>
						<div class="item">
							<div class="float-item sec1">星期一</div>
							<div class="float-item sec2">
								<div class="g-inputtime"><input type="text" readonly="readonly" id="mon_startuptime" placeholder="" class="g-input timepicker"><span class="icon"></span></div>
							</div>
							<div class="float-item sec3">
								<div class="g-inputtime"><input type="text" readonly="readonly" id="mon_shutdowntime" placeholder="" class="g-input timepicker"><span class="icon"></span></div>
							</div>
							<div class="float-item sec4">
								<div class="g-checkbox"><input type="checkbox"  id="mon_enableclose"></div><label for="mon_enableclose">关机</label>
							</div>
						</div>
						<div class="item">
							<div class="float-item sec1">星期二</div>
							<div class="float-item sec2">
								<div class="g-inputtime"><input type="text" readonly="readonly" id="tue_startuptime" placeholder="" class="g-input timepicker"><span class="icon"></span></div>
							</div>
							<div class="float-item sec3">
								<div class="g-inputtime"><input type="text" readonly="readonly" id="tue_shutdowntime" placeholder="" class="g-input timepicker"><span class="icon"></span></div>
							</div>
							<div class="float-item sec4">
								<div class="g-checkbox"><input type="checkbox" id="tue_enableclose"></div><label for="tue_enableclose">关机</label>
							</div>
						</div>
						<div class="item">
							<div class="float-item sec1">星期三</div>
							<div class="float-item sec2">
								<div class="g-inputtime"><input type="text" readonly="readonly" id="wed_startuptime" placeholder="" class="g-input timepicker"><span class="icon"></span></div>
							</div>
							<div class="float-item sec3">
								<div class="g-inputtime"><input type="text" readonly="readonly" id="wed_shutdowntime" placeholder="" class="g-input timepicker"><span class="icon"></span></div>
							</div>
							<div class="float-item sec4">
								<div class="g-checkbox"><input type="checkbox" id="wed_enableclose"></div><label for="wed_enableclose">关机</label>
							</div>
						</div>
						<div class="item">
							<div class="float-item sec1">星期四</div>
							<div class="float-item sec2">
								<div class="g-inputtime"><input type="text" readonly="readonly" id="thu_startuptime" placeholder="" class="g-input timepicker"><span class="icon"></span></div>
							</div>
							<div class="float-item sec3">
								<div class="g-inputtime"><input type="text" readonly="readonly" id="thu_shutdowntime" placeholder="" class="g-input timepicker"><span class="icon"></span></div>
							</div>
							<div class="float-item sec4">
								<div class="g-checkbox"><input type="checkbox" id="thu_enableclose"></div><label for="thu_enableclose">关机</label>
							</div>
						</div>
						<div class="item">
							<div class="float-item sec1">星期五</div>
							<div class="float-item sec2">
								<div class="g-inputtime"><input type="text" readonly="readonly" id="fri_startuptime" placeholder="" class="g-input timepicker"><span class="icon"></span></div>
							</div>
							<div class="float-item sec3">
								<div class="g-inputtime"><input type="text" readonly="readonly" id="fri_shutdowntime" placeholder="" class="g-input timepicker"><span class="icon"></span></div>
							</div>
							<div class="float-item sec4">
								<div class="g-checkbox"><input type="checkbox" id="fri_enableclose"></div><label for="fri_enableclose">关机</label>
							</div>
						</div>
						<div class="item">
							<div class="float-item sec1">星期六</div>
							<div class="float-item sec2">
								<div class="g-inputtime"><input type="text" readonly="readonly" id="sat_startuptime" placeholder="" class="g-input timepicker"><span class="icon"></span></div>
							</div>
							<div class="float-item sec3">
								<div class="g-inputtime"><input type="text" readonly="readonly" id="sat_shutdowntime" placeholder="" class="g-input timepicker"><span class="icon"></span></div>
							</div>
							<div class="float-item sec4">
								<div class="g-checkbox"><input type="checkbox" id="sat_enableclose"></div><label for="sat_enableclose">关机</label>
							</div>
						</div>
						<div class="item">
							<div class="float-item sec1">星期日</div>
							<div class="float-item sec2">
								<div class="g-inputtime"><input type="text" readonly="readonly" id="sun_startuptime" placeholder="" class="g-input timepicker"><span class="icon"></span></div>
							</div>
							<div class="float-item sec3">
								<div class="g-inputtime"><input type="text" readonly="readonly" id="sun_shutdowntime" placeholder="" class="g-input timepicker"><span class="icon"></span></div>
							</div>
							<div class="float-item sec4">
								<div class="g-checkbox"><input type="checkbox" id="sun_enableclose"></div><label for="sun_enableclose">关机</label>
							</div>
						</div>
					</div>
					<div class="com-item">
						<div class="g-checkbox"><input type="checkbox" id="enablebalance"></div><label for="enablebalance">定时财经结算</label>
						<div class="pl26">
							<div class="g-inputtime"><input type="text" readonly="readonly" id="balancetime" placeholder="" class="g-input timepicker"><span class="icon"></span></div>
						</div>
					</div>
					<div class="com-item">
						<div class="g-checkbox"><input type="checkbox" id="enablereboot"></div><label for="enablereboot">定时重启机器</label>
						<div class="pl26">
							<div class="g-inputtime"><input type="text" readonly="readonly" id="reboottime" placeholder="" class="g-input timepicker"><span class="icon"></span></div>
						</div>
					</div>
					<div class="com-item">
						<div class="g-checkbox"><input type="checkbox" id="enableshutdown"></div><label for="enableshutdown">自动关机</label>
						<div class="pl26">
							<div class="g-inputtime"><input type="text" readonly="readonly" id="shutdowntime" placeholder="" class="g-input timepicker"><span class="icon"></span></div>
						</div>
					</div>
				</div>
				
			<%-- 打印配置 print_config --%>
			<div class="right-content hide" data-type="print">
				
				<div class="com-form" data-type="head">
						<div class="item">
							<div class="left">
								凭据头字体
							</div>
							<div class="right">
								<div class="g-select w22">
									<select id="head_font" class="font">
										<option value="-1">选择字体</option>
									</select>
									<span class="arr-icon"></span>
								</div>
								<div class="g-select w10">
									<select id="head_size" class="fontsize">
										<option value="-1">选择字号</option>
									</select>
									<span class="arr-icon"></span>
								</div>
							</div>
						</div>
						<div class="item">
							<div class="left">
								凭据尾字体
							</div>
							<div class="right">
								<div class="g-select w22">
									<select id="tail_font" class="font">
										<option value="-1">选择字体</option>
									</select>
									<span class="arr-icon"></span>
								</div>
								<div class="g-select w10">
									<select id="tail_size" class="fontsize">
										<option value="-1">选择字号</option>
									</select>
									<span class="arr-icon"></span>
								</div>
							</div>
						</div>
						<div class="item">
							<div class="left">
								数据内容字体
							</div>
							<div class="right">
								<div class="g-select w22">
									<select id="content_font" class="font">
										<option value="-1">选择字体</option>
									</select>
									<span class="arr-icon"></span>
								</div>
								<div class="g-select w10">
									<select id="content_size" class="fontsize">
										<option value="-1">选择字号</option>
									</select>
									<span class="arr-icon"></span>
								</div>
							</div>
						</div>
					</div>
					
				<div class="segmentation"><div class="t">还书凭据</div></div>
				<div class="com-form" data-type="checkin">
					<div class="item">
						<div class="left">
							&nbsp;
						</div>
						<div class="right">
							<div class="g-radio"><input type="radio" name="checkin_type" id="checkin_yes" value="0"></div><label for="checkin_yes">打印</label>
							<div class="g-radio"><input type="radio" name="checkin_type" id="checkin_no" value="1"></div><label for="checkin_no">不打印</label>
							<div class="g-radio"><input type="radio" name="checkin_type" id="checkin_ask" value="2" checked="checked"></div><label for="checkin_ask">询问读者</label>
							<div class="fr">
								<div class="g-checkbox"><input type="checkbox" name="checkin_timeout" id="checkin_timeout"></div><label for="checkin_timeout">超时打印</label>
							</div>
							
						</div>
					</div>
					<div class="small-item">
						<div class="left">
							日期描述
						</div>
						<div class="right">
							<input type="text" id="checkin_date" placeholder="" class="g-input w18">
						</div>
					</div>
					<div class="small-item">
						<div class="left">
							时间描述
						</div>
						<div class="right">
							<input type="text" id="checkin_time" placeholder="" class="g-input w18">
						</div>
					</div>
					<div class="small-item">
						<div class="left">
							条码号描述
						</div>
						<div class="right">
							<input type="text" id="checkin_barcode" placeholder="" class="g-input w18">
							
						</div>
					</div>
					<div class="small-item">
						<div class="left">
							书名描述
						</div>
						<div class="right">
							<input type="text" id="checkin_title" placeholder="" class="g-input w18">
							
						</div>
					</div>
					<div class="small-item">
						<div class="left">
							<div class="g-checkbox"><input type="checkbox" id="checkin_enableamount"></div><label for="checkin_enableamount">打印数量描述</label>
						</div>
						<div class="right">
							<input type="text" placeholder="" id="checkin_amount" class="g-input w18 ">
						</div>
					</div>
					<div class="item">
						<div class="left">
							<div class="g-checkbox"><input type="checkbox" id="checkin_enablecomment"></div><label for="checkin_enablecomment">备注</label>
						</div>
						<div class="right">
							<textarea class="g-textarea disabled" id="checkin_comment" disabled="disabled" placeholder="输入内容"></textarea>
							
						</div>
					</div>
					<div class="item">
						<div class="left">
							票据头
						</div>
						<div class="right">
							<textarea class="g-textarea " id="checkin_head" placeholder="输入内容"></textarea>
							
						</div>
					</div>
					<div class="item">
						<div class="left">
							票据尾
						</div>
						<div class="right">
							<textarea class="g-textarea" id="checkin_tail" placeholder="输入内容"></textarea>
							
						</div>
					</div>
				</div>
				
				<div class="segmentation"><div class="t">借书凭据</div></div>
				<div class="com-form" data-type="checkout">
					<div class="item">
						<div class="left">
							&nbsp;
						</div>
						<div class="right">
							<div class="g-radio"><input type="radio" name="checkout_type" id="checkout_yes" value="0"></div><label for="checkout_yes">打印</label>
							<div class="g-radio"><input type="radio" name="checkout_type" id="checkout_no" value="1"></div><label for="checkout_no">不打印</label>
							<div class="g-radio"><input type="radio" name="checkout_type" id="checkout_ask" value="2" checked="checked"></div><label for="checkout_ask">询问读者</label>
							<div class="fr">
								<div class="g-checkbox"><input type="checkbox" name="checkout_timeout" id="checkout_timeout"></div><label for="checkout_timeout">超时打印</label>
							</div>
							
						</div>
					</div>
					<div class="small-item">
						<div class="left">
							日期描述
						</div>
						<div class="right">
							<input type="text" id="checkout_date" placeholder="" class="g-input w18">
						</div>
					</div>
					<div class="small-item">
						<div class="left">
							时间描述
						</div>
						<div class="right">
							<input type="text" id="checkout_time" placeholder="" class="g-input w18">
						</div>
					</div>
					<div class="small-item">
						<div class="left">
							<div class="g-checkbox"><input type="checkbox" id="checkout_enablename"></div><label for="checkout_enablename">读者姓名描述</label>
						</div>
						<div class="right">
							<input type="text" placeholder="" id="checkout_name" class="g-input w18 ">
						</div>
					</div>
					<div class="small-item">
						<div class="left">
							读者证号描述
						</div>
						<div class="right">
							<input type="text" id="checkout_patron" placeholder="" class="g-input w18">
							
						</div>
					</div>
					<div class="small-item">
						<div class="left">
							条码号描述
						</div>
						<div class="right">
							<input type="text" id="checkout_barcode" placeholder="" class="g-input w18">
							
						</div>
					</div>
					<div class="small-item">
						<div class="left">
							书名描述
						</div>
						<div class="right">
							<input type="text" id="checkout_title" placeholder="" class="g-input w18">
							
						</div>
					</div>
					<div class="small-item">
						<div class="left">
							<div class="g-checkbox"><input type="checkbox" id="checkout_enableamount"></div><label for="checkout_enableamount">打印数量描述</label>
						</div>
						<div class="right">
							<input type="text" placeholder="" id="checkout_amount" class="g-input w18 ">
						</div>
					</div>
					<div class="small-item">
						<div class="left">
							还书日期描述
						</div>
						<div class="right">
							<input type="text" id="checkout_duedate" placeholder="" class="g-input w18">
							
						</div>
					</div>
					<div class="small-item">
						<div class="left">
							<div class="g-checkbox"><input type="checkbox" id="checkout_enablefee"></div><label for="checkout_enablefee">费用描述</label>
						</div>
						<div class="right">
							<input type="text" placeholder="" id="checkout_fee" class="g-input w18 ">
						</div>
					</div>
					<div class="item">
						<div class="left">
							票据头
						</div>
						<div class="right">
							<textarea class="g-textarea " id="checkout_head"  placeholder="输入内容"></textarea>
							
						</div>
					</div>
					<div class="item">
						<div class="left">
							票据尾
						</div>
						<div class="right">
							<textarea class="g-textarea" id="checkout_tail" placeholder="输入内容"></textarea>
							
						</div>
					</div>
				</div>
				
				<div class="segmentation"><div class="t">续借凭据</div></div>
				<div class="com-form" data-type="renew">
					<div class="item">
						<div class="left">
							&nbsp;
						</div>
						<div class="right">
							<div class="g-radio"><input type="radio" name="renew_type" id="renew_yes" value="0"></div><label for="renew_yes">打印</label>
							<div class="g-radio"><input type="radio" name="renew_type" id="renew_no" value="1"></div><label for="renew_no">不打印</label>
							<div class="g-radio"><input type="radio" name="renew_type" id="renew_ask" value="2" checked="checked"></div><label for="renew_ask">询问读者</label>
							<div class="fr">
								<div class="g-checkbox"><input type="checkbox" name="renew_timeout" id="renew_timeout"></div><label for="renew_timeout">超时打印</label>
							</div>
							
						</div>
					</div>
					<div class="small-item">
						<div class="left">
							日期描述
						</div>
						<div class="right">
							<input type="text" id="renew_date" placeholder="" class="g-input w18">
						</div>
					</div>
					<div class="small-item">
						<div class="left">
							时间描述
						</div>
						<div class="right">
							<input type="text" id="renew_time" placeholder="" class="g-input w18">
						</div>
					</div>
					<div class="small-item">
						<div class="left">
							书名描述
						</div>
						<div class="right">
							<input type="text" id="renew_title" placeholder="" class="g-input w18">
							
						</div>
					</div>
					<div class="small-item">
						<div class="left">
							读者证号描述
						</div>
						<div class="right">
							<input type="text" id="renew_patron" placeholder="" class="g-input w18">
							
						</div>
					</div>
					<div class="small-item">
						<div class="left">
							条码号描述
						</div>
						<div class="right">
							<input type="text" id="renew_barcode" placeholder="" class="g-input w18">
							
						</div>
					</div>
					<div class="small-item">
						<div class="left">
							还书日期描述
						</div>
						<div class="right">
							<input type="text" id="renew_duedate" placeholder="" class="g-input w18">
							
						</div>
					</div>
					<div class="small-item">
						<div class="left">
							<div class="g-checkbox"><input type="checkbox" id="renew_enablerenewbiblio"></div><label for="renew_enablerenewbiblio">续借书描述</label>
						</div>
						<div class="right">
							<input type="text" placeholder="" id="renew_renewbiblio" class="g-input w18 ">
						</div>
					</div>
					<div class="small-item">
						<div class="left">
							<div class="g-checkbox"><input type="checkbox" id="renew_enablerenewcount"></div><label for="renew_enablerenewcount">续借数描述</label>
						</div>
						<div class="right">
							<input type="text" placeholder="" id="renew_renewcount" class="g-input w18 ">
						</div>
					</div>
					<div class="small-item">
						<div class="left">
							<div class="g-checkbox"><input type="checkbox" id="renew_enableunrenewbiblio"></div><label for="renew_enableunrenewbiblio">未续借书描述</label>
						</div>
						<div class="right">
							<input type="text" placeholder="" id="renew_unrenewbiblio" class="g-input w18 ">
						</div>
					</div>
					<div class="small-item">
						<div class="left">
							<div class="g-checkbox"><input type="checkbox" id="renew_enableunrenewcount"></div><label style="padding-left:3px;" for="renew_enableunrenewcount">未续借数描述</label>
						</div>
						<div class="right">
							<input type="text" placeholder="" id="renew_unrenewcount" class="g-input w18 ">
						</div>
					</div>
					<div class="item">
						<div class="left">
							票据头
						</div>
						<div class="right">
							<textarea class="g-textarea " id="renew_head"  placeholder="输入内容"></textarea>
						</div>
					</div>
					<div class="item">
						<div class="left">
							票据尾
						</div>
						<div class="right">
							<textarea class="g-textarea" id="renew_tail" placeholder="输入内容"></textarea>
						</div>
					</div>
				</div>
				
				<div class="segmentation"><div class="t">收款凭据</div></div>
				<div class="com-form" data-type="pay">
					<div class="item">
						<div class="left">
							&nbsp;
						</div>
						<div class="right">
							<div class="g-radio"><input type="radio" name="pay_type" id="pay_yes" value="0"></div><label for="pay_yes">打印</label>
							<div class="g-radio"><input type="radio" name="pay_type" id="pay_no" value="1"></div><label for="pay_no">不打印</label>
							<div class="g-radio"><input type="radio" name="pay_type" id="pay_ask" value="2" checked="checked"></div><label for="pay_ask">询问读者</label>
							<div class="fr">
								<div class="g-checkbox"><input type="checkbox" name="pay_timeout" id="pay_timeout"></div><label for="pay_timeout">超时打印</label>
							</div>
							
						</div>
					</div>
					<div class="small-item">
						<div class="left">
							日期描述
						</div>
						<div class="right">
							<input type="text" id="pay_date" placeholder="" class="g-input w18">
						</div>
					</div>
					<div class="small-item">
						<div class="left">
							时间描述
						</div>
						<div class="right">
							<input type="text" id="pay_time" placeholder="" class="g-input w18">
						</div>
					</div>
					<div class="small-item">
						<div class="left">
							读者证号描述
						</div>
						<div class="right">
							<input type="text" id="pay_patron" placeholder="" class="g-input w18">
							
						</div>
					</div>
					<div class="small-item">
						<div class="left">
							收款金额描述
						</div>
						<div class="right">
							<input type="text" id="pay_fee" placeholder="" class="g-input w18">
							
						</div>
					</div>
					<div class="small-item">
						<div class="left">
							<div class="g-checkbox"><input type="checkbox" id="pay_enablename"></div><label for="pay_enablename">读者姓名描述</label>
						</div>
						<div class="right">
							<input type="text" placeholder="" id="pay_name" class="g-input w18 ">
						</div>
					</div>
					<div class="small-item">
						<div class="left">
							<div class="g-checkbox"><input type="checkbox" id="pay_enableidcard"></div><label style="padding-left:3px;" for="pay_enableidcard">身份证号描述</label>
						</div>
						<div class="right">
							<input type="text" placeholder="" id="pay_idcard" class="g-input w18 ">
						</div>
					</div>
					<div class="small-item">
						<div class="left">
							<div class="g-checkbox"><input type="checkbox" id="pay_enablecardtype"></div><label for="pay_enablecardtype">读者类型描述</label>
						</div>
						<div class="right">
							<input type="text" placeholder="" id="pay_cardtype" class="g-input w18 ">
						</div>
					</div>
					<div class="small-item">
						<div class="left">
							<div class="g-checkbox"><input type="checkbox" id="pay_enablededuction"></div><label style="padding-left:3px;" for="pay_enablededuction">扣款金额描述</label>
						</div>
						<div class="right">
							<input type="text" placeholder="" id="pay_deduction" class="g-input w18 ">
						</div>
					</div>
					<div class="small-item">
						<div class="left">
							<div class="g-checkbox"><input type="checkbox" id="pay_enabledeposit"></div><label for="pay_enabledeposit">押金余额描述</label>
						</div>
						<div class="right">
							<input type="text" placeholder="" id="pay_deposit" class="g-input w18 ">
						</div>
					</div>
					<div class="small-item">
						<div class="left">
							<div class="g-checkbox"><input type="checkbox" id="pay_enableimprest"></div><label style="padding-left:3px;" for="pay_enableimprest">预付款描述</label>
						</div>
						<div class="right">
							<input type="text" placeholder="" id="pay_imprest" class="g-input w18 ">
						</div>
					</div>
					<div class="item">
						<div class="left">
							票据头
						</div>
						<div class="right">
							<textarea class="g-textarea " id="pay_head"  placeholder="输入内容"></textarea>
						</div>
					</div>
					<div class="item">
						<div class="left">
							票据尾
						</div>
						<div class="right">
							<textarea class="g-textarea" id="pay_tail" placeholder="输入内容"></textarea>
						</div>
					</div>
				</div>
				 
				<div class="segmentation"><div class="t">取钱箱凭据</div></div>
				<div class="com-form" data-type="cashbin">
					<div class="item">
						<div class="left">
							&nbsp;
						</div>
						<div class="right">
							<div class="g-radio"><input type="radio" name="cashbin_type" id="cashbin_yes" value="0"></div><label for="cashbin_yes">打印</label>
							<div class="g-radio"><input type="radio" name="cashbin_type" id="cashbin_no" value="1"></div><label for="cashbin_no">不打印</label>
							<div class="g-radio"><input type="radio" name="cashbin_type" id="cashbin_ask" value="2" checked="checked"></div><label for="cashbin_ask">询问读者</label>
							<div class="fr">
								<div class="g-checkbox"><input type="checkbox" name="cashbin_timeout" id="cashbin_timeout"></div><label for="cashbin_timeout">超时打印</label>
							</div>
							
						</div>
					</div>
					<div class="small-item">
						<div class="left">
							日期描述
						</div>
						<div class="right">
							<input type="text" id="cashbin_date" placeholder="" class="g-input w18">
						</div>
					</div>
					<div class="small-item">
						<div class="left">
							时间描述
						</div>
						<div class="right">
							<input type="text" id="cashbin_time" placeholder="" class="g-input w18">
						</div>
					</div>
					<div class="small-item">
						<div class="left">
							操作员卡号描述
						</div>
						<div class="right">
							<input type="text" id="cashbin_operator" placeholder="" class="g-input w18">
						</div>
					</div>
					<div class="small-item">
						<div class="left">
							面值描述
						</div>
						<div class="right">
							<input type="text" id="cashbin_notevalue" placeholder="" class="g-input w18">
						</div>
					</div>
					<div class="small-item">
						<div class="left">
							张数描述
						</div>
						<div class="right">
							<input type="text" id="cashbin_notecount" placeholder="" class="g-input w18">
						</div>
					</div>
					<div class="small-item">
						<div class="left">
							金额描述
						</div>
						<div class="right">
							<input type="text" id="cashbin_money" placeholder="" class="g-input w18">
						</div>
					</div>
					<div class="small-item">
						<div class="left">
							总张数描述
						</div>
						<div class="right">
							<input type="text" id="cashbin_bincount" placeholder="" class="g-input w18">
						</div>
					</div>
					<div class="small-item">
						<div class="left">
							总金额描述
						</div>
						<div class="right">
							<input type="text" id="cashbin_binmoney" placeholder="" class="g-input w18">
						</div>
					</div>
					
					<div class="item">
						<div class="left">
							票据头
						</div>
						<div class="right">
							<textarea class="g-textarea " id="cashbin_head"  placeholder="输入内容"></textarea>
						</div>
					</div>
					<div class="item">
						<div class="left">
							票据尾
						</div>
						<div class="right">
							<textarea class="g-textarea" id="cashbin_tail" placeholder="输入内容"></textarea>
						</div>
					</div>
				</div>
				
				<div class="segmentation"><div class="t">取书箱凭据</div></div>
				<div class="com-form" data-type="bookbin">
					<div class="item">
						<div class="left">
							&nbsp;
						</div>
						<div class="right">
							<div class="g-radio"><input type="radio" name="bookbin_type" id="bookbin_yes" value="0"></div><label for="bookbin_yes">打印</label>
							<div class="g-radio"><input type="radio" name="bookbin_type" id="bookbin_no" value="1"></div><label for="bookbin_no">不打印</label>
							<div class="g-radio"><input type="radio" name="bookbin_type" id="bookbin_ask" value="2" checked="checked"></div><label for="bookbin_ask">询问读者</label>
							<div class="fr">
								<div class="g-checkbox"><input type="checkbox" name="bookbin_timeout" id="bookbin_timeout"></div><label for="bookbin_timeout">超时打印</label>
							</div>
							
						</div>
					</div>
					<div class="small-item">
						<div class="left">
							日期描述
						</div>
						<div class="right">
							<input type="text" id="bookbin_date" placeholder="" class="g-input w18">
						</div>
					</div>
					<div class="small-item">
						<div class="left">
							时间描述
						</div>
						<div class="right">
							<input type="text" id="bookbin_time" placeholder="" class="g-input w18">
						</div>
					</div>
					<div class="small-item">
						<div class="left">
							操作员卡号描述
						</div>
						<div class="right">
							<input type="text" id="bookbin_operator" placeholder="" class="g-input w18">
						</div>
					</div>
					<div class="small-item">
						<div class="left">
							书箱号描述
						</div>
						<div class="right">
							<input type="text" id="bookbin_binno" placeholder="" class="g-input w18">
						</div>
					</div>
					<div class="small-item">
						<div class="left">
							数量描述
						</div>
						<div class="right">
							<input type="text" id="bookbin_amount" placeholder="" class="g-input w18">
						</div>
					</div>
					<div class="small-item">
						<div class="left">
							总数量描述
						</div>
						<div class="right">
							<input type="text" id="bookbin_tolamount" placeholder="" class="g-input w18">
						</div>
					</div>
					<div class="item">
						<div class="left">
							票据头
						</div>
						<div class="right">
							<textarea class="g-textarea " id="bookbin_head"  placeholder="输入内容"></textarea>
						</div>
					</div>
					<div class="item">
						<div class="left">
							票据尾
						</div>
						<div class="right">
							<textarea class="g-textarea" id="bookbin_tail" placeholder="输入内容"></textarea>
						</div>
					</div>
				</div>
				
				<div class="segmentation"><div class="t">补取书凭据</div></div>
				<div class="com-form" data-type="bookrack">
					<div class="item">
						<div class="left">
							&nbsp;
						</div>
						<div class="right">
							<div class="g-radio"><input type="radio" name="bookrack_type" id="bookrack_yes" value="0"></div><label for="bookrack_yes">打印</label>
							<div class="g-radio"><input type="radio" name="bookrack_type" id="bookrack_no" value="1"></div><label for="bookrack_no">不打印</label>
							<div class="g-radio"><input type="radio" name="bookrack_type" id="bookrack_ask" value="2" checked="checked"></div><label for="bookrack_ask">询问读者</label>
							<div class="fr">
								<div class="g-checkbox"><input type="checkbox" name="bookrack_timeout" id="bookrack_timeout"></div><label for="bookrack_timeout">超时打印</label>
							</div>
						</div>
					</div>
					<div class="small-item">
						<div class="left">
							日期描述
						</div>
						<div class="right">
							<input type="text" id="bookrack_date" placeholder="" class="g-input w18">
						</div>
					</div>
					<div class="small-item">
						<div class="left">
							时间描述
						</div>
						<div class="right">
							<input type="text" id="bookrack_time" placeholder="" class="g-input w18">
						</div>
					</div>
					<div class="small-item">
						<div class="left">
							操作员卡号描述
						</div>
						<div class="right">
							<input type="text" id="bookrack_operator" placeholder="" class="g-input w18">
						</div>
					</div>
					<div class="small-item">
						<div class="left">
							数量描述
						</div>
						<div class="right">
							<input type="text" id="bookrack_amount" placeholder="" class="g-input w18">
						</div>
					</div>
					<div class="item">
						<div class="left">
							票据头
						</div>
						<div class="right">
							<textarea class="g-textarea " id="bookrack_head"  placeholder="输入内容"></textarea>
						</div>
					</div>
					<div class="item">
						<div class="left">
							票据尾
						</div>
						<div class="right">
							<textarea class="g-textarea" id="bookrack_tail" placeholder="输入内容"></textarea>
						</div>
					</div>
				</div>
				
				<div class="segmentation"><div class="t">补取卡凭据</div></div>
				<div class="com-form" data-type="cardbin">
					<div class="item">
						<div class="left">
							&nbsp;
						</div>
						<div class="right">
							<div class="g-radio"><input type="radio" name="cardbin_type" id="cardbin_yes" value="0"></div><label for="cardbin_yes">打印</label>
							<div class="g-radio"><input type="radio" name="cardbin_type" id="cardbin_no" value="1"></div><label for="cardbin_no">不打印</label>
							<div class="g-radio"><input type="radio" name="cardbin_type" id="cardbin_ask" value="2" checked="checked"></div><label for="cardbin_ask">询问读者</label>
							<div class="fr">
								<div class="g-checkbox"><input type="checkbox" name="cardbin_timeout" id="cardbin_timeout"></div><label for="cardbin_timeout">超时打印</label>
							</div>
						</div>
					</div>
					<div class="small-item">
						<div class="left">
							日期描述
						</div>
						<div class="right">
							<input type="text" id="cardbin_date" placeholder="" class="g-input w18">
						</div>
					</div>
					<div class="small-item">
						<div class="left">
							时间描述
						</div>
						<div class="right">
							<input type="text" id="cardbin_time" placeholder="" class="g-input w18">
						</div>
					</div>
					<div class="small-item">
						<div class="left">
							操作员卡号描述
						</div>
						<div class="right">
							<input type="text" id="cardbin_operator" placeholder="" class="g-input w18">
						</div>
					</div>
					<div class="small-item">
						<div class="left">
							数量描述
						</div>
						<div class="right">
							<input type="text" id="cardbin_amount" placeholder="" class="g-input w18">
						</div>
					</div>
					<div class="item">
						<div class="left">
							票据头
						</div>
						<div class="right">
							<textarea class="g-textarea " id="cardbin_head"  placeholder="输入内容"></textarea>
						</div>
					</div>
					<div class="item">
						<div class="left">
							票据尾
						</div>
						<div class="right">
							<textarea class="g-textarea" id="cardbin_tail" placeholder="输入内容"></textarea>
						</div>
					</div>
				</div>
 
				</div>
				
				<%-- 办证配置 register_config --%>
				<div class="right-content hide" data-type="register">
					<div class="segmentation"><div class="t">办证</div></div>
					<div class="com-item">
						<div class="g-checkbox"><input type="checkbox" id="register_enableidcard"></div><label for="register_enableidcard">支持用身份证做读者证</label>
					</div>
					<div class="change-box">
						<div class="com-item">
							<div class="g-checkbox"><input type="checkbox" id="register_enableothercard"></div><label for=""register_enableothercard"">支持其他类型卡做读者证</label>
						</div>
					</div>
					<br>
					<div class="com-item">
						<div class="g-checkbox"><input type="checkbox" id="register_enabletempcard"></div><label for="register_enabletempcard">服务器支持临时读者证</label>
					</div>
					<div class="change-box">
						<div class="com-item">
							临时证类型标识
							<input type="text" placeholder="" class="w10 g-input" id="register_tempcardid">
						</div>
					</div>
				</div>

				<%-- 本地服务器配置 localdb_config --%>
				<div class="right-content hide" data-type="localdb">
					<div class="com-item">
						<div class="fl">
							服务器IP <input type="text" id="localdb_ip" placeholder="" class="g-input w18 ">
						</div>
						<div class="fl ml30">
							端口 <input type="text" id="localdb_port" placeholder="" class="g-input w10">
						</div>
					</div>

					<div class="com-item">
						<span class="w50">用户ID</span><input id="localdb_user" type="text" class="g-input w18">
					</div>
					<div class="com-item">
						<span class="w50">密码</span><input id="localdb_pwd" type="password" class="g-input w18">
					</div>
				</div>
				
				<%-- 云中心配置 center_config--%>
				<div class="right-content hide" data-type="center">
					<div class="com-item">
						<div class="fl">
							服务器IP <input type="text" id="center_ip" placeholder="" class="g-input w18 ">
						</div>
						<div class="fl ml30">
							端口 <input type="text" id="center_port" placeholder="" class="g-input w10 ">
						</div>
					</div>
				</div>
				
				<%-- acs服务配置 ACS_config --%>
				<div class="right-content hide" data-type="ACS">
					<div class="form-wrap form-wrap2">
						<ul>
							<li class="error">
								<div class="left w-auto">协议版本</div>
								<div class="right">
									<div class="g-radio disabled"><input type="radio" disabled="disabled" name="ACS_sipversion" id="ver1" value="1"></div><label for="ver1">ver.1</label>
									<div class="g-radio"><input type="radio" name="ACS_sipversion" id="ver2" value="2" checked="checked"></div><label for="ver2">ver.2</label>
								</div>
							</li>
						</ul>
					</div>
					<div class="com-item">
						<div class="g-checkbox"><input type="checkbox" id="ACS_needlogin"></div><label for="ACS_needlogin">要求登录</label>
					</div>
					<div class="com-item">
						ACS系统所使用的字符集
						<div class="g-select w18">
							<select id="ACS_charset">
								<option value="UTF-8" selected>UTF-8</option>
								<option value="GBK" >GBK</option>
							</select>
							<span class="arr-icon"></span>
						</div>
					</div>
					<div class="segmentation">
						<div class="t">消息</div>
					</div>
					<div>
						<div class="g-checkbox"><input type="checkbox" id="ACS_defaultdecollator"></div>
						<label for="ACS_defaultdecollator">使用缺省消息字段分隔符 （HEX 7C）</label>
						<input type="text" id="ACS_decollator" placeholder="输入" class="min g-input">
						（HEX : 00--FF ,如7C）
					</div>
					<div>
						<div class="g-checkbox"><input type="checkbox" id="ACS_enablecmd63"></div>
						<label for="ACS_enablecmd63">支持63指令消息请求</label>
					</div>
					<div class="segmentation"></div>
					
					<div class="com-item">
						货币代码
						<input type="text" id="ACS_currency" placeholder="" class="w50 g-input">
						RMB,USD,ETC
					</div>
				</div>
			</div>
			
		</div>
	</div>
</div>
</div>

<link rel="stylesheet" href="<%=basePath %>/static/css/jqueryui.css">
<link rel="stylesheet" href="<%=basePath %>/static/css/theme.css">
<script src="<%=basePath %>/static/js/jqueryui.js"></script>
<script src="<%=basePath %>/static/js/jqueryuitimepicker.js"></script>
<script src="<%=basePath %>/static/plugins/layer/layer.js"></script>
<script type="text/javascript" src="<%=basePath %>/static/js/template-web.js"></script>

<script type="text/javascript">
$(function(){
	<%-- 设备硬件模板配置的编辑按钮 --%>
	$(".yingjian-dialog .edit").on("click",function(){
		if($(this).hasClass("disabled")){
			return;
		}
		$(".form-dialog-fix.w-900.yingjian").fadeIn(100);
		$(".form-dialog-fix.w-900.yingjian").find(".form-dialog").animate({
			"right":0
		});
	});
	
	<%-- 设备运行模板配置模板的编辑按钮 --%>
	$(".yunxing-dialog .edit").on("click",function(){
		if($(this).hasClass("disabled")){
			return;
		}
		$(".form-dialog-fix.w-900.yunxing").fadeIn(100);
		$(".form-dialog-fix.w-900.yunxing").find(".form-dialog").animate({
			"right":0
		});
	});

	$(".form-dialog .form-wrap .btn").on("click",function(){
		$(this).addClass("active").siblings(".btn").removeClass("active");
	});

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

	<%-- 设备运行模板配置模板编辑侧栏的  左侧菜单点击事件 ，左侧菜单的<li>必须和右侧内容的<div class="right-content">顺序一一对应，否则点击菜单对应的内容不一致--%>
	$("#run_left_tab").on("click","li",function(){
		var thisIndex = $(this).index();
		$(this).addClass("active").siblings("li").removeClass("active");
		
		$("#run_right_form .right-content").hide().eq(thisIndex).show();
	});
	
	
	<%-- 设备硬件模板配置模板编辑侧栏的  左侧菜单点击事件 --%>
	$("#ext_left_tab").on("click","li",function(){
		var thisIndex = $(this).index();
		$(this).addClass("active").siblings("li").removeClass("active");
		
		$("#ext_right_form .right-content").hide().eq(thisIndex).show();
	});

	
	
	$(".datepicker" ).datepicker({
		numberOfMonths:1,//显示几个月  
		showButtonPanel:false,//是否显示按钮面板  
		dateFormat: 'yy-mm-dd',//日期格式  
		clearText:"清除",//清除日期的按钮名称  
		closeText:"关闭",//关闭选择框的按钮名称  
		yearSuffix: '', //年的后缀  
		showMonthAfterYear:true,//是否把月放在年的后面  
		defaultDate:'2016-04-19',//默认日期  
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
		timeFormat: 'HH:mm:ss',
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
	
	<%-- 查询所有地点 --%>
	queryAllRegion();
		
});

var regionNode = {};

<%-- 查询所有地点 --%>
function queryAllRegion(){
	$.ajax({
		url:"${pageContext.request.contextPath}/device/queryAllRegion",
		type:"POST",
		data:{}
	}).done(function(data){
		if(data && data.state){
			regionNode = data.result;
			if(regionNode['root']){
				var item = regionNode['root'];
				var html = "";
				for(var i in item){
					html += "<option value=\""+item[i].code+"\">"+item[i].name+"</option>";
				}
				$("#nation").append(html)
			}
		}
	});
}

$("#nation").on("change", function(){
	var nCode = $("#nation").val();
	var html = "<option value=\"-1\">省</option>";
	if(regionNode[nCode]){
		var item = regionNode[nCode];
		for(var i in item){
			html += "<option value=\""+item[i].code+"\">"+item[i].name+"</option>";
		}
	}
	$("#province").html(html).trigger("change");
});

$("#province").on("change", function(){
	var nCode = $("#province").val();
	var html = "<option value=\"-1\">市</option>";
	if(regionNode[nCode]){
		var item = regionNode[nCode];
		for(var i in item){
			html += "<option value=\""+item[i].code+"\">"+item[i].name+"</option>";
		}
	}
	$("#city").html(html).trigger("change");
});


$("#city").on("change", function(){
	var nCode = $("#city").val();
	var html = "<option value=\"-1\">区</option>";
	if(regionNode[nCode]){
		var item = regionNode[nCode];
		for(var i in item){
			html += "<option value=\""+item[i].code+"\">"+item[i].name+"</option>";
		}
	}
	$("#area").html(html);
});


<%-- 添加acs配置 --%>
$(".add-li,.label-add").on("click",function(){
	var html = "";
	html += " <div class=\"form-wrap acs\" >";
	html += " <div class=\"item\">";
	html += " <ul>";
	html += " <li >";
	html += " 				<div class=\"left\"><div class=\"delete-li\"></div>ACS模板</div>";
	html += " 				<div class=\"right\" >";
	html += " <div class=\"g-select\">";
	html += " <select id=\"\">";
// 	html += " <option value=\"-1\">请选择</option>";
	if(acsTempHtml!=""){
		html += acsTempHtml;
	}else{
		html += " <option value=\"-1\">请选择</option>";
	}
	html += " </select>";
	html += " <span class=\"arr-icon\"></span>";
	html += " </div>";
	html += "<span class=\"error-msg\">请选择acs模板</span>";
	html += " 				</div>";
	html += " </li>";
	html += " <li >";
	html += " <div class=\"left\">后台服务名</div>";
	html += " <div class=\"right\"><input type=\"text\" id=\"\" placeholder=\"英文或数字\" class=\"g-input servername\">";
	html += " <span class=\"error-msg\"></div>";
	html += " </li>";
	html += " </ul>";
	html += " </div>";
	html += " <div class=\"item\">";
	html += " <ul>";
	html += " <li>";
	html += " 				<div class=\"left\">IP</div>";
	html += " 				<div class=\"right\"><input type=\"text\" id=\"\" placeholder=\"请输入\" class=\"g-input acsip\">";
	html += " <span class=\"error-msg\">请输入IP</div>";
	html += " </li>";
	html += " <li>";
	html += " 				<div class=\"left\">端口号</div>";
	html += " 				<div class=\"right\"><input type=\"text\" id=\"\" placeholder=\"请输入\" class=\"g-input acsport\">";
	html += " <span class=\"error-msg\">请输入端口号</div>";
	html += " </li>";
	html += " </ul>";
	html += " </div>";
	html += " <div class=\"item\">";
	html += " <ul>";
	html += " <li>";
	html += " 				<div class=\"left\">登录名</div>";
	html += " 				<div class=\"right\"><input type=\"text\" id=\"\" placeholder=\"请输入\" class=\"g-input loginname\">";
	html += " <span class=\"error-msg\">请输入登录名</div>";
	html += " </li>";
	html += " <li>";
	html += " 				<div class=\"left\">密码</div>";
	html += " 				<div class=\"right\"><input type=\"password\" id=\"\" placeholder=\"请输入\" class=\"g-input loginpwd\">";
	html += " <span class=\"error-msg\">请输入密码</div>";
	html += " </li>";
	html += " </ul>";
	html += " </div>";
	
	html += " <div class=\"item\">";
	html += " <ul>";
	html += " <li>";
	html += " 				<div class=\"left\">登录类型</div>";
	html += " 				<div class=\"right\">";
	html += " <div class=\"g-select\">";
	html += " <select id=\"\" class=\"g-input logintype\" onchange=\"clickChange(this)\">";
	html += " <option>--请选择--</option>";
	html += " <option value=\"1\">SIP2</option>";
	html += " <option value=\"2\">NCIP</option>";
	html += " </select>";
	html += " <span class=\"arr-icon\"></span>";
	html += " </div>";
	html += " <span class=\"error-msg\">请选择登录类型</div>";
	html += " </li>";
	html += " <li>";
	html += " 				<div class=\"left\">允许登录次数</div>";
	html += " 				<div class=\"right\"><input type=\"text\" id=\"\" placeholder=\"请输入允许登录次数\" class=\"g-input logincount\">";
	html += " <span class=\"error-msg\">请设置允许登录次数</div>";
	html += " </li>";
	html += " </ul>";
	html += " </div>";
	
	html += " <div class=\"item\">";
	html += " <ul>";
	html += " <li>";
	html += " 				<div class=\"left\">是否需要登录</div>";
	html += " 				<div class=\"right\">";
	html += " <div class=\"g-select\">";
	html += " <select id=\"\" class=\"g-input logincheck\">";
	html += " <option value=\"1\">是</option>";
	html += " <option value=\"0\">否</option>";
	html += " </select>";
	html += " <span class=\"error-msg\">请选择是否需要登录</div>";
	html += " </li>";
	html += " <li>";
	html += " 				<div class=\"left\">字符编码格式</div>";
	html += " 				<div class=\"right\"><input type=\"text\" id=\"\" placeholder=\"请输入字符编码格式\" class=\"g-input logincode\">";
	html += " <span class=\"error-msg\">请选择字符编码格式</div>";
	html += " </li>";
	html += " </ul>";
	html += " </div>";
	html += " </div>";
	$(this).closest("div.item").after(html);
	<%-- 设置acs模板的值 --%>
// 	setAcsTemp();
});

<%-- 删除ACS按钮，删除当前选项 --%>
$(document).on("click",".delete-li",function(){
	$(this).closest("div.form-wrap.acs").remove();
});


// $(document).on("click",".purpose",function(){
// 	if($(this).prop("checked")){
// 		$(this).prop("checked",false).parent().removeClass("on");		
// 	}else{
// 		var name = $(this).prop("name");
// 		alert(name);
// 		$(":checkbox[name='"+name+"']").prop("checked",false).parent().removeClass("on");
// 		$(this).prop("checked",true).parent().addClass("on");
// 	}
// });


</script>


<%--###########################################################################################################----%>
<%--###########################################################################################################----%>
<%--###########################################################################################################----%>
<%--###########################################################################################################----%>
<%-- 自定义js脚本 --%>
<script type="text/javascript">
<%--字符串转JSON格式--%>
function jsonToObj(str){ 
	try{
		return JSON.parse(str); 
	}catch(e){
		return "";
	}
} 

<%-- 输入框错误提示信息 --%>
function errorMsg(str){
	var html = "<span class=\"error-msg\">"+str+"</span>";
	return html;
}

	var extTempList;<%--保存获取到的extTempList--%>
	var runTempList;<%--保存获取到的runTempList--%>
	
	var extTempObj={};<%-- 保存当前选中的逻辑外设模板对象 --%>
	var runTempObj={};<%-- 保存当前选中的运行模板对象--%>
	
	var extTempSubmit={};<%-- 保存要提交的逻辑外设模板配置   --%>
	var runTempSubmit={};<%-- 保存要提交的运行模板配置   --%>
	
	var extTempCustom = {};<%-- 保存逻辑外设模板的自定义数据 --%>
	var runTempCustom = {};<%-- 保存运行模板的自定义数据 --%>
	
	var logicNameList; <%-- 保存获取到的逻辑名称list --%>
	var extModelList;   <%-- 保存获取到的extModel list --%>
	var extModelMap = {};   <%-- 保存获取到的extModel Map key为ext_model --%>
	
	var deviceTypeList = {}; <%-- 保存设备类型list --%>
	
	var acsTempList = {};<%-- 保存acs模板对象 --%>
	var acsTempHtml = "";<%-- acs下拉框选项 --%>

	$(function(){
		//获取设备类型
		getDeviceType();
// 		getExtConfList();
// 		getRunConfList();
// 		getMonitorConfList();
// 		getDbSyncConfList();

		<%---查询所有的逻辑部件--%>
// 		getAllLogicObj();
		<%-- 获取所有的extModel --%>
// 		getAllExtModel();
		<%-- 获取所有的logic_obj和ext_model的元数据 --%>
		getAllLogicObjAndExtModel();
		
		initRunConfSetting();
		
	});
	
	<%--获取所有设备类型--%>
	function getDeviceType(){
		$.ajax({
			url:"<%=basePath%>device/getDeviceTypes",
			type:"POST",
			data:"",
			success:function(data){
				if(data!=null && data.state){
					var list = data.result;
					var html = "";
					//deviceType
					if(list!=null && list.length>0){
						for(var i=0;i<list.length;i++){
							html += "<option value=\""+list[i].meta_devicetype_idx+"\" data-type=\""+list[i].device_type+"\">"+list[i].device_type_desc+"</option>"
							deviceTypeList[list[i].meta_devicetype_idx] = list[i];
						}
					}
					$("#deviceType").append(html);
// 					$("#deviceType").val("1").trigger("change");
				}else{
					layer.alert(data.message);
// 					console.log(data.message);
				}
			}
		});
	}
	
	<%-- 获取ACS模板数据 --%>
	function getAscTempList(libIdx){
		var param = {};
		if(libId!=null){
			param.library_idx=libIdx;
			$.ajax({
				url:"<%=basePath%>device/getAscTempList",
				type:"GET",
				data:{json:JSON.stringify(param)}
			}).done(function(data){
				if(!_.isEmpty(data) && data.state && !_.isEmpty(data.result)){
					acsTempList = data.result;
					acsTempHtml = "<option value=\"-1\">请选择</option>";
					for(var i=0;i<acsTempList.length;i++){
						acsTempHtml += "<option value=\""+acsTempList[i].protocol_tpl_idx+"\">"+acsTempList[i].protocol_tpl_desc+"</option>"
					}
					$(".form-wrap.acs select").html(acsTempHtml);
				}
			});
		}
	}
	
	<%-- 设置acs模板的值 --%>
// 	function setAcsTemp(){
// 		if(!_.isEmpty(acsTempHtml)){
// 			$(".form-wrap.acs select").html(acsTempHtml);
// 		}
// 	}
	
	<%-- 选择设备类型之后触发事件 --%>
	$(document).on("change","#deviceType",function(){
		var id = this.value;
		getExtConfList(id);
		getRunConfList(id);
		getMonitorConfList(id);
		getDbSyncConfList(id);
		showMenu(id);
	});
	
	<%--获取设备硬件模板配置模板--%>
	function getExtConfList(id){
		if(id=="-1"){
			$("#extTemp").html("<option value=\"-1\" >请选择模板</option>").trigger("change");
		}else{
			$.ajax({
				url:"<%=basePath%>device/getAllExtTempList",
				type:"POST",
				data:{json:id},
				success:function(data){
					if(data!=null && data.state){
						var list = data.result; 
						extTempList = data.result;
						var html = "<option value=\"-1\" >请选择模板</option>";
						if(list!=null){
							for (var i = 0; i < list.length; i++) {
								var item = list[i];
								html+="<option value=\""+item.device_tpl_idx+"\">"+item.device_tpl_ID+"-"+item.device_tpl_desc+"</option>"
							}
							$("#extTemp").html(html);
	// 						setExtTempListDetail(list);<%--设置每个模板的详细信息--%>
							extTempType("-1");<%--  --%>
						}
					}else{
						console.log(data.message);
// 						layer.alert(data.message);
					}
				}
				
			});
		}
	}
	
	<%--获取设备运行模板配置模板--%>
	function getRunConfList(id){
		if(id=="-1"){
			$("#runTemp").html("<option value=\"-1\">请选择模板</option>").trigger("change");
		}else{
			$.ajax({
				url:"<%=basePath%>device/getAllRunTempList",
				type:"POST",
				data:{json:id},
				success:function(data){
					if(data!=null && data.state){
						var list = data.result; 
						runTempList = data.result;
						var html = "<option value=\"-1\">请选择模板</option>";
						if(list!=null){
							for (var i = 0; i < list.length; i++) {
								var item = list[i];
								html+="<option value=\""+item.device_tpl_idx+"\">"+item.device_tpl_ID+"-"+item.device_tpl_desc+"</option>"
							}
							$("#runTemp").html(html);
							runTempType("-1");<%--  --%>
						}
					}else{
						console.log(data.message);
// 						layer.alert(data.message);
					}
				}
				
			});
		}
	}
	

	
	<%-- 获取监控配置模板--%>
	function getMonitorConfList(id){
		if(id=="-1"){
			$("#monitorTemp").html("<option value=\"-1\">请选择模板</option>");
		}else{
			$.ajax({
				url:"<%=basePath%>device/getAllMonitorTempList",
				type:"POST",
				data:{json:id},
				success:function(data){
					if(data!=null && data.state){
						var list = data.result; 
						var html = "<option value=\"-1\">请选择模板</option>";
						if(list!=null){
							for (var i = 0; i < list.length; i++) {
								var item = list[i];
								html+="<option value=\""+item.device_mon_tpl_idx+"\">"+item.device_mon_tpl_id+"-"+item.device_mon_tpl_desc+"</option>"
							}
							$("#monitorTemp").html(html);
						}
					}else{
						console.log(data.message);
// 						layer.alert(data.message);
					}
				}
				
			});
		}
	}
	

	<%--获取设备运行模板配置模板--%>
	function getDbSyncConfList(id){
		if(id=="-1"){
			$("#dbSyncTemp").html("<option value=\"-1\">请选择模板</option>");
		}else{
			$.ajax({
				url:"<%=basePath%>device/getAllDbSyncTempList",
				type:"POST",
				data:{json:id},
				success:function(data){
					if(data!=null && data.state){
						var list = data.result; 
						var html = "<option value=\"-1\">请选择模板</option>";
						if(list!=null){
							for (var i = 0; i < list.length; i++) {
								var item = list[i];
								html+="<option value=\""+item.device_tpl_idx+"\">"+item.device_tpl_ID+"-"+item.device_tpl_desc+"</option>"
							}
							$("#dbSyncTemp").html(html);
						}
					}else{
						console.log(data.message);
// 						layer.alert(data.message);
					}
				}
				
			});
		}
	}
	
	
	
	<%-- 设备硬件模板配置模板相关
		##########################################################################################################################################################
		##########################################################################################################################################################
		##########################################################################################################################################################
		##########################################################################################################################################################
	 --%>
	
	<%-- 根据数据类型，编辑按钮选用不同的颜色，-1:未选择，0：模板数据，1：自定义数据 --%>
	function extTempType(val){
		if(val=="-1"){
			$(".yingjian-dialog .edit")
			.addClass("disabled")
			.addClass("g-btn-gray")
			.removeClass("g-btn-blue")
			.removeClass("g-btn-green");
		}
		if(val=="0"){
			$(".yingjian-dialog .edit")
			.removeClass("disabled")
			.removeClass("g-btn-gray")
			.addClass("g-btn-blue")
			.removeClass("g-btn-green");
		}
		if(val=="1"){
			$(".yingjian-dialog .edit")
			.removeClass("disabled")
			.removeClass("g-btn-gray")
			.removeClass("g-btn-blue")
			.addClass("g-btn-green");
		}
		
		if($(".yingjian-dialog .edit").hasClass("g-btn-green")){
			$(".yingjian-dialog .edit").html("自定义");
		}else{
			$(".yingjian-dialog .edit").html("修改");
		}
	}
	
	<%-- 点击保存按钮的等待效果 --%>
	function startSaveExt(){
		$(".g-loading.save").fadeIn();
		$("#ext_left_tab").hide();
		$("#ext_right_form").hide();
	}
	
	<%-- 取消等待效果 --%>
	function endSaveExt(){
		$(".g-loading.save").fadeOut("",function(){
			$(".shade").eq(0).trigger("click");
			layer.alert("操作成功！",{icon:6});
		});
		$("#ext_left_tab").show();
		$("#ext_right_form").show();
	}
	
	$(document).on("click","#extTempRestore",function(){
		layer.confirm("恢复至模板数据后，该选项的自定义数据将被清除，确认是否将该模板选项恢复至模板数据？",{icon:1,title:"提示"},function(index){
			startSaveExt();
			<%-- 获取当前选中的模板 --%>
			var idx = $("#extTemp").val();
			if(idx!=null&&idx!=""&&idx!="-1"){
				delete extTempCustom[idx] <%-- 删除该选项的自定义数据 --%>
				$("#extTemp").trigger("change");
			}else{
				layer.alert("请先选择模板");
			}
			layer.close(index);
			endSaveExt();
		});
	})
	
	function clearExtConfData(){
		<%--先清空数据--%>
		$(".extmodel").val("-1").trigger("change");
		<%---先把所有的收款通道选择框取消选择--%>
		$("#money div div").removeClass("on");
		$("#money input").prop("checked",false);
		$("#IdentityReader_comm").val("-1").trigger("change");
		<%-- 清除其他选项里面的关于收钞机的选项 --%>
		$("#OtherDiv").find(".money :checkbox").each(function(i,n){
			$(n).prop("checked",false);
			$(n).parent().removeClass("on");
		});
		
	}
	
	<%-- 选择设备硬件模板配置模板之后触发的事件 --%>
	$(document).on("change","#extTemp",function(){
		<%-- 清除原来的数据 --%>
		clearExtConfData();
		var index = this.selectedIndex;
		if($("#extTemp").val()!="-1"){
			<%--  选择了模板之后修改编辑按钮样式  --%>
			$(".yingjian-dialog .edit").removeClass("disabled").removeClass("g-btn-gray").addClass("g-btn-green");
		
			if(extTempList.length>0){
				extTempObj = extTempList[index-1];
				if(!_.isEmpty(extTempObj)){
					var idx = extTempObj.device_tpl_idx;
					<%-- 判断是不是有自定义数据，如果有的话就填充自定义数据 --%>
					if(_.isEmpty(extTempCustom[idx])){
						<%--深度克隆对象extTempObj--%>
						extTempSubmit = $.extend(true, {},extTempObj);
						extTempType("0");
					}else{
						<%-- 如果自定义数据不为空则使用自定义数据 --%>
						extTempSubmit = extTempCustom[idx];
						extTempType("1");
					}
				}
				<%-- 装载配设备硬件模板配置的数据 --%>
				loadAllExtTempData(extTempSubmit);
			}
		}else{
			<%--  选择了模板之后修改编辑按钮样式  --%>
			extTempType("-1");
			<%-- 隐藏侧栏菜单里面的内容 --%>
// 			$(".yingjian-dialog .edit").addClass("disabled").addClass("g-btn-gray").removeClass("g-btn-green");
		}
	});
	
	
	<%-- 动态显示硬件逻辑模板的选项  --%>
	function showMenu(id){
		$("#ext_left_tab li").addClass("hide");
		$("#OtherDiv").html(""); <%-- 清空其他选项卡的内容 --%>
		if(id!="-1"){
			var item = deviceTypeList[id];
			var logic_list = item.device_logic_list;
			if(!_.isEmpty(logic_list)){
				var list = logic_list.split(",");
				for(i in list){
					$("#"+list[i]).removeClass("hide");
					<%-- 如果有其它选项卡，则显示它的硬件列表 --%>
					if(list[i]=="Other"){
						showOtherExt(item.device_logic_other);
					}
				}
			}
		}
		$("#ext_left_tab li:not('.hide')").eq(0).trigger("click");
	}
	
	<%--  extModelMap 硬件列表，每条数据如下
	ext_model:"CRV100D"
	ext_model_desc:"{"zh_CN":"华视身份证阅读器"}"
	ext_model_driver_path:""
	ext_model_version:"V3"
	ext_type:"idreader"
	meta_ext_idx:1
	--%>
	<%-- 显示硬件列表 --%>
	function showOtherExt(logic_other){
		var data = {};
		if(logic_other){
			var arr = logic_other.split(",");
			var html = "";
			for(var i=0;i<arr.length;i++){
	// 			console.log(arr[i])
				var key = arr[i];
				var item = extModelMap[key];
			}
			data.keys = arr;
		}
		data.map = {};
		data.map = extModelMap;
		
		<%-- 自定义模板中使用的方法 jsonToObj --%>
		template.defaults.imports.jsonToObj = function(str){
			try{
				return JSON.parse(str); 
			}catch(e){
				return "";
			}
		}
		<%-- 获取other的logicid --%>
		var meta_log_obj_idx = null;
		for(i in logicNameList){
			if(logicNameList[i].logic_obj == "Other"){
				meta_log_obj_idx = logicNameList[i].meta_log_obj_idx;
			}
		}
		data.meta_log_obj_idx = meta_log_obj_idx;
	
		var html = template('test', data); <%-- 将数据放入模板中获取html代码 --%>
		
		$("#OtherDiv").html(html)
		
		
		<%-- 重新绑定动态加载的下拉框 --%>
		$("#OtherDiv .need-change").on("change",function(){
			/*
				每个需要交换显示的select框，都必须有
				类名need-change，
				唯一的ID名 对应着 将需要显示的那个DIV的date-id属性，
				select框 的value值 对应着 DIV的val属性
				如示例
			 */
			var thisId =  $(this).attr("id");
			var thisVal = $(this).val();
	
			var $lei = $("#OtherDiv [date-id='"+thisId+"']");
	
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
	
	<%---
	AuthorityBarcode
	AuthorityReader
	CardDispenser
	CashAcceptor
	IdentityReader
	ItemBarcode
	ItemLoadReader
	ItemRfidReader
	PlcReturn
	PlcSorter
	PlcSSL
	Printer
	RegisterReader
		
	--%>
	
	<%-- 选择逻辑名之后触发的事件，装载extModel的数据 --%>
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
					<%--  身份证阅读器 --%>
					if(data_id.toLowerCase().indexOf("identityreader")>-1){
						if(item.ext_type.toLowerCase().indexOf("idreader")>-1){
							html+="<option value=\""+item.meta_ext_idx+"\">"+jsonToObj(item.ext_model_desc)['zh_CN']+model_version+"</option>";
						}
					<%-- 其他阅读器 --%>
					}else if(data_id.toLowerCase().indexOf("reader")>-1){
						if(item.ext_type.toLowerCase().indexOf("hfreader")>-1 || item.ext_type.toLowerCase().indexOf("uhfreader")>-1){
							html+="<option value=\""+item.meta_ext_idx+"\">"+jsonToObj(item.ext_model_desc)['zh_CN']+model_version+"</option>";
						}
					<%-- plc设备，包括分拣机，还书机，自助图书馆  --%>
					}else if(data_id.toLowerCase().indexOf("plcssl")>-1){
						if(item.ext_type.toLowerCase().indexOf("plcssl")>-1){
							html+="<option value=\""+item.meta_ext_idx+"\">"+jsonToObj(item.ext_model_desc)['zh_CN']+model_version+"</option>";
							<%--设置拓展参数的值--%>
							$("#PlcSSL_box").attr("val",item.meta_ext_idx);
// 							if(data_id.toLowerCase().indexOf("ssl")>-1 
// 									&& item.ext_model.toLowerCase().indexOf("plc")>-1){
// 								html+="<option value=\""+item.meta_ext_idx+"\">"+jsonToObj(item.ext_model_desc)['zh_CN']+model_version+"</option>";
// 								$("#PlcSSL_box").attr("val",item.meta_ext_idx);
// 							}
// 							if(data_id.toLowerCase().indexOf("sorter")>-1 
// 									&& item.ext_model.toLowerCase().indexOf("sorter")>-1){
// 								html+="<option value=\""+item.meta_ext_idx+"\">"+jsonToObj(item.ext_model_desc)['zh_CN']+model_version+"</option>";
// 							}
// 							if(data_id.toLowerCase().indexOf("return")>-1 
// 									&& item.ext_model.toLowerCase().indexOf("return")>-1){
// 								html+="<option value=\""+item.meta_ext_idx+"\">"+jsonToObj(item.ext_model_desc)['zh_CN']+model_version+"</option>";
// 							}
						}
					<%-- 条码枪 --%>
					}else if(data_id.toLowerCase().indexOf("barcode")>-1) {
						if(item.ext_type.toLowerCase().indexOf("barcode")>-1){
							html+="<option value=\""+item.meta_ext_idx+"\">"+jsonToObj(item.ext_model_desc)['zh_CN']+model_version+"</option>";
						}
					}else if(data_id.toLowerCase() == item.ext_type.toLowerCase()){
						html+="<option value=\""+item.meta_ext_idx+"\">"+jsonToObj(item.ext_model_desc)['zh_CN']+model_version+"</option>";
					}
				}
				<%-- PLC设备的ext_type 都是plc 但是ext_model、logic_obj不同，要分开处理 --%>
				if(data_id.toLowerCase().indexOf("identityreader")>-1){
					$("#IdentityReader_extModel").html(html);				
				}else{
					$("#"+data_id+"_extModel").html(html);
				}
			}
		}else{
			//qingxuanze 
			$(".extmodel").html(html);
		}
	});

	<%--设置每个模板的详细信息--%>
	function setExtTempListDetail(list){
		for (var i = 0; i < list.length; i++) {
			var listId = list[i].device_ext_id;
			var childList = list[i].extDetailList;
			var html = "";
			html += "<div class=\"item change-box\" date-id=\"extTemp\" val=\""+listId+"\" style=\"display:none;\">";
			html += "<div class=\"word\">&nbsp;</div>";
			if(childList!=null && childList.length>0){
				html += "<div class=\"choose-data\">" + "<ul>";
				for(var j=0;j<childList.length;j++){
					html += "<li style=\"margin-top:10px\">"+jsonToObj(childList[j].logic_obj_desc).zh_CN+"<div class=\"data-edit edit g-btn-green\">修改</div></li>";
				}
				html += "</ul></div>";
			}
			html+="</div>";
			$("#extdiv").after(html);
		}
	}
	

	
	<%-- 获取所有的logic_obj和ext_model的元数据--%>
	function getAllLogicObjAndExtModel(){
		$.ajax({
			url:"<%=basePath%>device/getAllLogicObjAndExtModel",
			type:"POST",
			success:function(data){
				if(data!=null && data.state){
					var list = data.result; 
					logicNameList = list.logicObjList;
					extModelList = list.extModelList;
					<%-- 获取到数据之后填充逻辑名下拉框.logicname --%>
					<%-- 获取到数据之后填充逻辑名下拉框.logicname --%>
					if(extModelList){
						for(var i=0;i<extModelList.length;i++){
							extModelMap[extModelList[i].ext_model] = extModelList[i];
						}
					}
					loadAllLogicObj();
				}else{
					console.log(data.message)
				}
			}
		});
	}
	
	
	<%--获取所有逻辑外设部件--%>
	function getAllLogicObj(){
		$.ajax({
			url:"<%=basePath%>device/getAllLogicObj",
			type:"POST",
			data:"",
			success:function(data){
				if(data!=null && data.state){
					var list = data.result; 
					logicNameList = list;
					<%-- 获取到数据之后填充逻辑名下拉框.logicname --%>
					loadAllLogicObj();
				}else{
					console.log(data.message)
				}
			}
			
		});
	}
	
	<%-- 获取所有的外设模型 --%>
	function getAllExtModel(){
		$.ajax({
			url:"<%=basePath%>device/getAllExtModel",
			type:"POST",
			data:"",
			success:function(data){
				if(data!=null && data.state){
					var list = data.result; 
					extModelList = list;
					var html = "<option value=\"-1\">请选择</option>";
					if(list!=null){
						for (var i = 0; i < list.length; i++) {
							var item = list[i];
// 							console.log("model",item);
							var model_version = "";
							if(item.ext_model_version != "-"){
								model_version = "-"+item.ext_model_version;
							}
// 							html+="<option value=\""+item.meta_ext_idx+","+item.ext_model+"\">"+jsonToObj(item.ext_model_desc)['zh_CN']+model_version+"</option>"
							html+="<option value=\""+item.meta_ext_idx+"\">"+jsonToObj(item.ext_model_desc)['zh_CN']+model_version+"</option>"
						}
						<%-- 所有外设下拉框 --%>
// 						$(".extmodel").html(html);
					}
					
				}else{
					console.log(data.message);
				}
			}
		});
	}
	
	
	<%-- 填充所有的模板数据 --%>
	function loadAllLogicObj(){
		if(logicNameList!=null && logicNameList.length>0){
			$(".logicname").each(function(i,n){
				var id = n.id.substring(0,n.id.indexOf("_"));
				var html = "";
				for(var i=0;i<logicNameList.length;i++){
					var item = logicNameList[i];
					if(item.logic_obj.toLowerCase().indexOf(id.toLowerCase())>-1){
						html+="<option data-id=\""+item.logic_obj+"\"  value=\""+item.meta_log_obj_idx+"\">"+jsonToObj(item.logic_obj_desc)['zh_CN']+"-"+item.logic_obj+"</option>";
						$("#"+id+"_showName").val(jsonToObj(item.logic_obj_desc)['zh_CN']+"-"+item.logic_obj);
					}
				}
				$(this).html(html);
			});
			$(".logicname").trigger("change");
		}
	}
	
	<%-- 加载外设模板logicname的数据  --%>
	function loadAllExtTempData(extObj){
		<%-- 如果选择了模板则填充数据 --%>
		if($("#extTemp").val()!="-1"){
			if(!_.isEmpty(extObj)){
				
				if(!_.isEmpty(extObj.extDetailList)){
					var list = extObj.extDetailList;
					for(var i=0;i<list.length;i++){
						var item = list[i];
						<%-- 其他选项卡数据填充 --%>
						if(item.logic_obj=="Other"){
							//DOMid:other_CZC100RMB_showName
							var tempid = item.ext_model;
							var ext_extend_conf = null;
							if(typeof(item.ext_extend_conf)=="object") {
								ext_extend_conf = item.ext_extend_conf;
							}else{
								ext_extend_conf = jsonToObj(item.ext_extend_conf);
							}
							$("#other_"+tempid+"_showName").val(ext_extend_conf.logicname);
							
							<%-- 通讯方式配置 --%>
							if(item.ext_comm_conf!=null && item.ext_comm_conf!=""){
								var comm = null;
								if(typeof(item.ext_comm_conf)=="object") {
									comm = item.ext_comm_conf;
								}else{
									comm = jsonToObj(item.ext_comm_conf);
								}
								
								$("#other_"+tempid+"_comm").val(comm.TYPE).trigger("change");
								if(comm.TYPE=="com"){
									$("#other_"+tempid+"_COM").val(comm.COM);
									$("#other_"+tempid+"_BAUD").val(comm.BAUD);
								}
								if(comm.TYPE=="usb"){
									$("#other_"+tempid+"_USB").val(comm.USB);
								}
								if(comm.TYPE=="net"){
									$("#other_"+tempid+"_IP").val(comm.IP);
									$("#other_"+tempid+"_PORT").val(comm.PORT);
								}
							}
							if(item.ext_type.toLowerCase().indexOf("plcssl")>-1){
//		 						$("#"+item.logic_obj+"_extModel").val(item.ext_model_idx).trigger("change");	
								<%-- 填充书盒信息  --%>
								$("#other_"+tempid+"_shelf1").val(ext_extend_conf.SHELF1);
								$("#other_"+tempid+"_shelf2").val(ext_extend_conf.SHELF2);
								$("#other_"+tempid+"_shelf3").val(ext_extend_conf.SHELF3);
								$("#other_"+tempid+"_drawer").val(ext_extend_conf.DRAWER);
								$("#other_"+tempid+"_bookbin").val(ext_extend_conf.BOOKBIN);
							}
							
							<%--收钞机---%>
							if (item.ext_type.toLowerCase().indexOf("cashacceptor")>-1) {
								if(ext_extend_conf.CHANNEL!=null && ext_extend_conf.CHANNEL!=""){
									var channels = ext_extend_conf.CHANNEL.split(",");
									for(var j=0;j<channels.length;j++){
										$("#other_"+tempid+"_money input[value='"+channels[j]+"']").parent().addClass("on");
										$("#other_"+tempid+"_money input[value='"+channels[j]+"']").prop("checked",true);
									}
								}
							}
							
							continue;
						}
						
						<%-- 通讯方式配置 --%>
						if(item.ext_comm_conf!=null && item.ext_comm_conf!=""){
							var comm = null;
							if(typeof(item.ext_comm_conf)=="object") {
								comm = item.ext_comm_conf;
							}else{
								comm = jsonToObj(item.ext_comm_conf);
							}
							var logic_obj = item.logic_obj;
							
							$("#"+logic_obj+"_comm").val(comm.TYPE).trigger("change");
							if(comm.TYPE=="com"){
								$("#"+logic_obj+"_COM").val(comm.COM);
								$("#"+logic_obj+"_BAUD").val(comm.BAUD);
							}
							if(comm.TYPE=="usb"){
								$("#"+logic_obj+"_USB").val(comm.USB);
							}
							if(comm.TYPE=="net"){
								$("#"+logic_obj+"_IP").val(comm.IP);
								$("#"+logic_obj+"_PORT").val(comm.PORT);
							}
							if(comm.SWITCHERASSOCIATED){
								$("#"+logic_obj+"_Light").val(comm.SWITCHERASSOCIATED);
							}
							if(comm.DRIVERPATH){
								$("#"+logic_obj+"_DriverPath").val(comm.DRIVERPATH);
							}
							if(comm.ANTASSOCIATED){
								$("#"+logic_obj+"_AntAssociated").val(comm.ANTASSOCIATED);
							}
							<%--
							
							if(logic_obj.toLowerCase().indexOf("plc") > -1){
								$("#PLC_comm").val(comm.TYPE).trigger("change");
								if(comm.TYPE=="com"){
									$("#PLC_COM").val(comm.COM);
									$("#PLC_BAUD").val(comm.BAUD);
								}
								if(comm.TYPE=="usb"){
									$("#PLC_USB").val(comm.USB);
								}
								if(comm.TYPE=="net"){
									$("#PLC_IP").val(comm.IP);
									$("#PLC_PORT").val(comm.PORT);
								}
							}else{
								$("#"+logic_obj+"_comm").val(comm.TYPE).trigger("change");
								if(comm.TYPE=="com"){
									$("#"+logic_obj+"_COM").val(comm.COM);
									$("#"+logic_obj+"_BAUD").val(comm.BAUD);
								}
								if(comm.TYPE=="usb"){
									$("#"+logic_obj+"_USB").val(comm.USB);
								}
								if(comm.TYPE=="net"){
									$("#"+logic_obj+"_IP").val(comm.IP);
									$("#"+logic_obj+"_PORT").val(comm.PORT);
								}
							}
							--%>
						}
						
						<%-- plc设备的设置 以及书盒信息配置 --%>
						if(item.logic_obj.toLowerCase().indexOf("plcssl")>-1){
							$("#"+item.logic_obj+"_extModel").val(item.ext_model_idx).trigger("change");	
							<%-- 填充书盒信息  --%>
							if(item.logic_obj.toLowerCase().indexOf("plcssl")>-1){
								var ext_extend_conf = null;
								if(typeof(item.ext_extend_conf)=="object") {
									ext_extend_conf = item.ext_extend_conf;
								}else{
									ext_extend_conf = jsonToObj(item.ext_extend_conf);
								}
								$("#shelf1").val(ext_extend_conf.SHELF1);
								$("#shelf2").val(ext_extend_conf.SHELF2);
								$("#shelf3").val(ext_extend_conf.SHELF3);
								$("#drawer").val(ext_extend_conf.DRAWER);
								$("#bookbin").val(ext_extend_conf.BOOKBIN);
							}
						}else{
							<%-- 外设部件 --%>
							$("#"+item.logic_obj+"_extModel").val(item.ext_model_idx);
							if (item.logic_obj.toLowerCase().indexOf("cashacceptor")>-1) {
								<%--收钞机---%>
								if(item.ext_extend_conf!=null && item.ext_extend_conf!=""){
									var extend = null;
									if(typeof(item.ext_extend_conf)=="object") {
										extend = item.ext_extend_conf;
									}else{
										extend = jsonToObj(item.ext_extend_conf);
									}
									if(extend.CHANNEL!=null && extend.CHANNEL!=""){
										var channels = extend.CHANNEL.split(",");
										for(var j=0;j<channels.length;j++){
											$("#money input[value='"+channels[j]+"']").parent().addClass("on");
											$("#money input[value='"+channels[j]+"']").prop("checked",true);
										}
									}
								}
							}
						}
					}
				}
			}
		}else{
			<%-- 如果没有选择模板 --%>
		}
		
	}
	
    <%-- 设备硬件模板配置保存事件  --%>
	$(document).on("click","#extTempSave",function(){
		startSaveExt();
// 		$(".shade").eq(0).trigger("click");//收起菜单
		var detailList = [];
		if(_.isEmpty(extTempObj)){ 
			<%-- 如果没有选择过ext模板 --%>
// 			return ;
// 			extTempSubmit.device_tpl_type="1";//1:设备数据
// 			extTempSubmit.device_type=$("#deviceType").val();//设备类型
		}
		$("#ext_right_form .right-content").each(function(i,n){
			if($(n).attr("id")=="OtherDiv"){
				<%-- 如果没有其它选项 --%>
				if($("#Other").hasClass("hide")){
					return true;
				}
				<%-- 逻辑名的idx，都为【其他】 --%>
				var logic_obj_idx = $("#meta_log_obj_idx").val();
				<%-- 拼装other选项的数据 --%>
				$("#OtherDiv .form-wrap2").each(function(j,m){
					<%-- 保存other所有的数据 --%>
					var other = {};
					<%-- 获取id前缀 --%>
					var logicDomId = $(m).find(".showname").attr("id");
					//$("id"+"_extModel").val()
					var tempId = logicDomId.substring(0,logicDomId.lastIndexOf("_"));
					var ext_model_idx = $("#"+tempId+"_extModel").val();
					var ext_comm_conf = {};
					var ext_extend_conf = {};
					
					other.logic_obj = "Other";
					other.logic_obj_idx = logic_obj_idx;
					other.ext_model_idx = ext_model_idx;
					other.ext_model = tempId.replace("other_","");
					
					var type = $("#"+tempId+"_comm").val();
					var com = $("#"+tempId+"_COM").val();
					var baud = $("#"+tempId+"_BAUD").val();
					var usb = $("#"+tempId+"_USB").val();
					var ip = $("#"+tempId+"_IP").val();
					var port = $("#"+tempId+"_PORT").val();
					var tempLogicName = $("#"+tempId+"_showName").val();<%-- 自定义的逻辑名 --%>
					ext_extend_conf.logicname = tempLogicName;
					console.info("-----ext_comm_conf------ :",ext_comm_conf);
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
					other.ext_comm_conf = ext_comm_conf;
					
					<%-- 外设类型，根据外设类型判断是否获取额外的信息 --%>
					var other_ext_type = $("#"+tempId+"_extModel").find("option:selected").attr("data-id");
					
					other.ext_type = other_ext_type;
					
					<%--收钞机,装配数据---%>
					if(other_ext_type.toLowerCase()=="cashacceptor"){
						var money = "";
						var $moneyDom = $(m).find(".changebox.money input");
						$moneyDom.each(function(k,o){
							if($(o).prop("checked")){
								money += $(o).val() + ",";
							}
						});
						if(money.length>0){
							money = money.substring(0,money.length-1);
						}
						<%---{"CURRENCY":"CNY", "CHANNEL":"1,5,10,20,50"}--%>
						ext_extend_conf.CURRENCY="CNY";
						ext_extend_conf.CHANNEL=money;
					} 
					
					<%-- PLC 装配数据---%>
					if(other_ext_type.toLowerCase().indexOf("plcssl")>-1){
						<%-- 如果选了自助图书馆装配数据 --%>
						var shelf1 = $(m).find(".shelf1").val();
						var shelf2 = $(m).find(".shelf2").val();
						var shelf3 = $(m).find(".shelf3").val();
						var drawer = $(m).find(".drawer").val();
						var bookbin = $(m).find(".bookbin").val();
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
					other.ext_extend_conf = ext_extend_conf;
					detailList.push(other);
				});
			}else{
				
				var obj = {};
				var logicId = $(this).find(".logicname").attr("id");
				var modelId = $(this).find(".extmodel").attr("id");
				if(logicId!=null && logicId!="" ){
					<%-- 先判断是否有这个菜单，如果没有的话，则保存下一条 --%>
					var liId = logicId.substring(0,logicId.indexOf("_"));
					if($("#"+liId).hasClass("hide")){
						return true;
					}
					
					var logic_obj_idx = $("#"+logicId).val();
					obj.logic_obj_idx = logic_obj_idx;
					var ext_model_idx = $("#"+modelId).val();
					if(ext_model_idx=="-1") {
						return true;
					}
					obj.ext_model_idx = ext_model_idx;//模板idx
					var tab = $("#"+logicId).find("option:selected").attr("data-id");
					obj.logic_obj = tab;
					
					<%--收钞机,装配数据---%>
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
						<%---{"CURRENCY":"CNY", "CHANNEL":"1,5,10,20,50"}--%>
						ext_extend_conf.CURRENCY="CNY";
						ext_extend_conf.CHANNEL=money;
						obj.ext_extend_conf = ext_extend_conf;
					} 
					
					<%-- 通讯方式数据装配 --%>
					var ext_comm_conf = {};
					var type = $("#"+tab+"_comm").val();
					var com = $("#"+tab+"_COM").val();
					var baud = $("#"+tab+"_BAUD").val();
					var usb = $("#"+tab+"_USB").val();
					var ip = $("#"+tab+"_IP").val();
					var port = $("#"+tab+"_PORT").val();
					<%-- Led序号 --%>
					var light = $(this).find(".lightnum").val();
					var driverPath = $(this).find(".driverPath").val();
					var antAssociated = $(this).find(".antAssociated").val();
					ext_comm_conf.SWITCHERASSOCIATED=light; 
					ext_comm_conf.DRIVERPATH=driverPath; 
					ext_comm_conf.ANTASSOCIATED=antAssociated;
					console.info("----- ext_comm_conf  -----",ext_comm_conf);
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
					
					
					<%-- PLC 装配数据---%>
					if(tab.toLowerCase().indexOf("plcssl")>-1){
						if(tab.toLowerCase().indexOf("plcssl")>-1){
							var ext_extend_conf = {};
							<%-- 如果选了自助图书馆装配数据 --%>
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
			}
		});
		
		extTempSubmit.extDetailList = detailList;
		<%-- 比较ExtTemp模板 --%>
		compareExtTemp();
		
		endSaveExt();
	})
	
	function useCustomExt(idx){
		<%-- 如果不相等相等，则使用自定义数据 --%>
		extTempSubmit.device_tpl_type="1";//1:设备数据使用设备数据，非模板
		extTempCustom[idx] = extTempSubmit;<%-- 保存到该选项的自定义数据中 --%>
		extTempType("1");	
	}
	
	<%-- 比较ExtTemp模板和保存的数据,device_tpl_type:0-使用模板数据，1-非模板 --%>
	function compareExtTemp(){
		
		if(!_.isEmpty(extTempObj) && !_.isEmpty(extTempSubmit)){
			var idx = extTempObj.device_tpl_idx;
			if(extTempObj.extDetailList.length!=extTempSubmit.extDetailList.length){
				<%-- 如果不相等相等，则使用自定义数据 --%>
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
				
				<%-- 通讯方式对比 --%>
				var ext_comm_conf1 = jsonToObj(obj.ext_comm_conf);
				var ext_comm_conf2 = submit.ext_comm_conf;
				<%-- 如果两个对象不相等 --%>
				if(!_.isEqual(ext_comm_conf1,ext_comm_conf2)){
					useCustomExt(idx);
					return;
				}
				
				<%-- 身份证阅读器 --%>
				<%--
				if(obj.logic_obj.toLowerCase().indexOf("identityreader")>-1){
					var ext_comm_conf1 = jsonToObj(obj.ext_comm_conf);
					var ext_comm_conf2 = submit.ext_comm_conf;
					if(!_.isEqual(ext_comm_conf1,ext_comm_conf2)){
						useCustomExt(idx);
						return;
					}
				}
				--%>
				
				<%-- 收钞机 --%>
				if(obj.logic_obj.toLowerCase().indexOf("cashacceptor")>-1){
					var ext_extend_conf1 = jsonToObj(obj.ext_extend_conf);
					var ext_extend_conf2 = submit.ext_extend_conf;
					if(!_.isEqual(ext_extend_conf1,ext_extend_conf2)){
						useCustomExt(idx);
						return;
					}
				}
				<%-- 自助图书馆 --%>
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
			delete extTempCustom[idx];<%-- 删除该选项的自定义数据 --%>
			extTempType("0");
		}else{
			layer.alert("请选择模板");
			return;
		}
	}
	
	
	
	<%-- 设备运行模板配置相关事件 
	################################################################################################################################################
	################################################################################################################################################
	################################################################################################################################################
	################################################################################################################################################
	################################################################################################################################################
	--%>
	
	<%-- 初始化设备运行模板配置设置 --%>
	function initRunConfSetting(){
		var html60;
		var html100;
		var html200;
		var html300;
		var html500;
		for(var i=1;i<=60;i++){
			html60 += "<option value=\""+i+"\">"+i+"</option>";
		}
		
		html100 += "<option value=\""+1+"\">"+1+"</option>";
		for (var i=10;i<=100;i+=10){
			html100 += "<option value=\""+i+"\">"+i+"</option>";
		}
		for (var i=110;i<=200;i+=10){
			html200 += "<option value=\""+i+"\">"+i+"</option>";
		}
		for (var i=210;i<=300;i+=10){
			html300 += "<option value=\""+i+"\">"+i+"</option>";
		}
		for (var i=310;i<=500;i+=10){
			html500 += "<option value=\""+i+"\">"+i+"</option>";
		}
		
		$(".select60").html(html60);
		$(".select100").html(html100);
		$(".select200").html(html100+html200);
		$(".select300").html(html100+html200+html300);
		$(".select500").html(html100+html200+html300+html500);
		
		
		var font = "";
		var fontsize = "";
		
		<%-- 宋体/宋体;黑体/黑体;仿宋/仿宋_GB2312;楷体/楷体_GB2312;隶书/隶书;幼圆/幼圆;微软雅黑/微软雅黑 --%>
		font += "<option value=\"宋体\" style=\"font-family:宋体;\">宋体</option>";
		font += "<option value=\"仿宋\" style=\"font-family:仿宋;\">仿宋</option>";
		font += "<option value=\"楷体\" style=\"font-family:楷体;\">楷体</option>";
		font += "<option value=\"幼圆\" style=\"font-family:幼圆;\">幼圆</option>";
		font += "<option value=\"隶书\" style=\"font-family:隶书;\">隶书</option>";
		font += "<option value=\"黑体\" style=\"font-family:黑体;\">黑体</option>";
		font += "<option value=\"微软雅黑\" style=\"font-family:微软雅黑;\">微软雅黑</option>";
		
		fontsize += "<option value=\"-1\">选择字体</option>";
		for(var i=8;i<=18;i+=2){
			fontsize += "<option value=\""+i+"\">"+i+"</option>";
		}
		
		
		$(".font").html(font);
		$(".fontsize").html(fontsize);
		
		<%-- 初始化的时候触发一下，确保每一个radio以及checkbox是否勾选与实际显示是否勾选保持一致 --%>
		$("div.yunxing input[type='radio']:checked").trigger("change");
		$("div.yunxing input[type='checkbox']").trigger("change");
		
	}
	
	<%-- 根据数据类型，编辑按钮选用不同的颜色，-1:未选择，0：模板数据，1：自定义数据 --%>
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
		
		if($(".yunxing-dialog .edit").hasClass("g-btn-green")){
			$(".yunxing-dialog .edit").html("自定义");
		}else{
			$(".yunxing-dialog .edit").html("修改");
		}
	}
	
	<%-- 点击保存按钮的等待效果 --%>
	function startSaveRun(){
		$(".g-loading.save").fadeIn();
		$("#run_left_tab").hide();
		$("#run_right_form").hide();
	}
	
	<%-- 取消等待效果 --%>
	function endSaveRun(){
		$(".g-loading.save").fadeOut("",function(){
			$(".shade").eq(1).trigger("click");
			layer.alert("操作成功！",{icon:6});
		});
		$("#run_left_tab").show();
		$("#run_right_form").show();
		
	}
	
	$(document).on("click","#runTempRestore",function(){
		layer.confirm("恢复至模板数据后，该选项的自定义数据将被清除，确认是否将该模板选项恢复至模板数据？",{icon:1,title:"提示"},function(index){
			startSaveRun();
			<%-- 获取当前选中的模板 --%>
			var idx = $("#runTemp").val();
			if(idx!=null&&idx!=""&&idx!="-1"){
				delete runTempCustom[idx] <%-- 删除该选项的自定义数据 --%>
				$("#runTemp").trigger("change");
			}else{
				layer.alert("请先选择模板");
			}
			layer.close(index);
			endSaveRun();
		});
	})
	
	<%-- 打印设置里面的勾选启用功能 --%>
	$(document).on("change","div[data-type='print'] input[type='checkbox']",function(){
		if($(this).prop("checked")){
			$(this).closest(".left").siblings().find("input,textarea")
			.removeClass("disabled").removeAttr("disabled");
		}else{
			$(this).closest(".left").siblings().find("input,textarea")
			.addClass("disabled").attr("disabled","disabled");
		}
	})
	
	<%-- 清除模板数据内容 --%>
	function clearRunConfData(){
		<%-- 清空文本框 --%>
		$("div.yunxing input[type='text']").val("");
		$("div.yunxing input[type='password']").val("");
		$("div.yunxing textarea").val("");
		<%-- 选择框默认选中第一个 --%>
		$("div.yunxing select").prop("selectedIndex", "0"); 
		<%-- 复选框默认不勾选 --%>
		$("div.yunxing input[type='checkbox']").prop("checked",false).trigger("change");
		<%-- 记录日志：不记录 --%>
		$("input[name='runlog_level'][value='none']").trigger("change");
		<%-- 分拣类型 --%>
		$("input[name='function_sorttype'][value='0']").trigger("change");
		<%-- PIN类型 --%>
		$("input[name='pin_type'][value='1']").trigger("change");
		<%-- PIN设备输入 --%>
		$("input[name='pin_input'][value='digitsoftpin']").trigger("change");
		<%-- PIN输入超出次数之后 --%>
		$("input[name='pin_retrydo'][value='0']").trigger("change");
		<%-- 打印配置里面的radio默认选中询问读者 --%>
		$("div[data-type='print'] input[type='radio'][name$='_type'][value='2']").trigger("change");
	}
	
	<%-- 选择设备运行模板配置模板之后触发的事件 --%>
	$(document).on("change","#runTemp",function(){
		<%-- 先清除原来数据 --%>
		clearRunConfData();
		var index = this.selectedIndex;
		if($("#runTemp").val()!="-1"){
			if(runTempList.length>0){
				runTempObj = runTempList[index-1];
				if(!_.isEmpty(runTempObj)){
					var idx = runTempObj.device_tpl_idx;
					if(_.isEmpty(runTempCustom[idx])){
						<%-- 如果为空则使用模板数据 --%>
						<%--深度克隆对象runTempObj--%>
						runTempSubmit = $.extend(true,{}, runTempObj);
						runTempType("0");
					}else{
						<%-- 如果自定义数据不为空则使用自定义数据 --%>
						runTempSubmit = runTempCustom[idx];
						runTempType("1");
					}
				}
				<%-- 装载设备运行模板配置的数据 --%>
				loadAllRunTempData(runTempSubmit);
			}
		}else{
			<%--  选择了模板之后修改编辑按钮样式  --%>
			runTempType("-1");
		}
	});
	
	
	function loadAllRunTempData(runObj){
		if($("#runTemp").val()!="-1"){
			if(!_.isEmpty(runObj)){
				<%-- 先初始化输入框 --%>
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
						
						<%-- 语言配置 --%>
						if(conf_type=="language_config"){
							$("#basic_language").val(conf_value.LANGUAGE);
							continue;
						}
						
						<%-- 运行日志配置 --%>
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
						
						<%-- 功能配置 --%>
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
						
						<%-- 超时配置 --%>
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
						
						<%-- 定时任务配置 --%>
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
						
						<%-- 打印配置--%>
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
									<%-- 打印类型 --%>
									$("input[name='"+thistype+"_type'][value='"+thisobj.TYPE+"']").trigger("change");
									<%-- 超时打印 --%>
									if(thisobj.TIMEOUT=="1"){
										$("#"+thistype+"_"+"timeout").prop("checked",true).trigger("change");
									}else{
										$("#"+thistype+"_"+"timeout").prop("checked",false).trigger("change");
									}
									
									<%-- 打印日期描述，时间描述，凭证头，凭证尾 --%>
									$("#"+thistype+"_"+"date").val(thisobj.DATE);
									$("#"+thistype+"_"+"time").val(thisobj.TIME);
									$("#"+thistype+"_"+"head").val(thisobj.HEAD);
									$("#"+thistype+"_"+"tail").val(thisobj.TAIL);
									
									<%-- 还回凭证 --%>
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
									<%-- 借出凭证 --%>
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
									<%-- 续借凭证 --%>
									if(thistype == "renew") {
										$("#"+thistype+"_"+"patron").val(thisobj.PATRON);
										$("#"+thistype+"_"+"barcode").val(thisobj.BARCODE);
										$("#"+thistype+"_"+"title").val(thisobj.TITLE);
										$("#"+thistype+"_"+"duedate").val(thisobj.DUEDATE);
										
										<%-- 续借书描述  --%>
										if(thisobj.ENABLERENEWBIBLIO == "1"){
											$("#"+thistype+"_"+"enablerenewbiblio").prop("checked",true).trigger("change");
											$("#"+thistype+"_"+"renewbiblio").val(thisobj.RENEWBIBLIO);
										}else{
											$("#"+thistype+"_"+"enablerenewbiblio").prop("checked",false).trigger("change");
											$("#"+thistype+"_"+"renewbiblio").val("");
										}
										<%-- 续借数描述 --%>
										if(thisobj.ENABLERENEWCOUNT == "1"){
											$("#"+thistype+"_"+"enablerenewcount").prop("checked",true).trigger("change");
											$("#"+thistype+"_"+"renewcount").val(thisobj.RENEWCOUNT);
										}else{
											$("#"+thistype+"_"+"enablerenewcount").prop("checked",false).trigger("change");
											$("#"+thistype+"_"+"renewcount").val("");
										}
										<%-- 未续借书描述 --%>
										if(thisobj.ENABLEUNRENEWBIBLIO == "1"){
											$("#"+thistype+"_"+"enableunrenewbiblio").prop("checked",true).trigger("change");
											$("#"+thistype+"_"+"unrenewbiblio").val(thisobj.UNRENEWBIBLIO);
										}else{
											$("#"+thistype+"_"+"enableunrenewbiblio").prop("checked",false).trigger("change");
											$("#"+thistype+"_"+"unrenewbiblio").val("");
										}
										<%-- 未续借数描述 --%>
										if(thisobj.ENABLEUNRENEWCOUNT == "1"){
											$("#"+thistype+"_"+"enableunrenewcount").prop("checked",true).trigger("change");
											$("#"+thistype+"_"+"unrenewcount").val(thisobj.UNRENEWCOUNT);
										}else{
											$("#"+thistype+"_"+"enableunrenewcount").prop("checked",false).trigger("change");
											$("#"+thistype+"_"+"unrenewcount").val("");
										}
									}
									<%-- 收款打印凭证 --%>
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
									<%-- 取款箱 --%>
									if(thistype == "cashbin") {
										$("#"+thistype+"_"+"operator").val(thisobj.OPERATOR);
										$("#"+thistype+"_"+"notevalue").val(thisobj.NOTEVALUE);
										$("#"+thistype+"_"+"notecount").val(thisobj.NOTECOUNT);
										$("#"+thistype+"_"+"money").val(thisobj.MONEY);
										$("#"+thistype+"_"+"bincount").val(thisobj.BINCOUNT);
										$("#"+thistype+"_"+"binmoney").val(thisobj.BINMONEY);
									}
									<%-- 取书箱 --%>
									if(thistype == "bookbin") {
										$("#"+thistype+"_"+"operator").val(thisobj.OPERATOR);
										$("#"+thistype+"_"+"binno").val(thisobj.BINNO);
										$("#"+thistype+"_"+"amount").val(thisobj.AMOUNT);
										$("#"+thistype+"_"+"tolamount").val(thisobj.TOLAMOUNT);
									}
									<%-- 补取书凭证 --%>
									if(thistype == "bookrack") {
										$("#"+thistype+"_"+"operator").val(thisobj.OPERATOR);
										$("#"+thistype+"_"+"amount").val(thisobj.AMOUNT);
									}
									<%-- 补取卡凭证 --%>
									if(thistype == "cardbin") {
										$("#"+thistype+"_"+"operator").val(thisobj.OPERATOR);
										$("#"+thistype+"_"+"amount").val(thisobj.AMOUNT);
									}
								}
							}
						}
						
						<%-- 办证配置 --%>
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
						
						<%-- 本地数据库配置 --%>
						if(conf_type=="localdb_config"){
							$("#localdb_ip").val(conf_value.IP);
							$("#localdb_port").val(conf_value.PORT);
							$("#localdb_user").val(conf_value.USER);
							$("#localdb_pwd").val(conf_value.PWD);
						}
						
						<%-- 云中心配置 --%>
						if(conf_type=="center_config"){
							$("#center_ip").val(conf_value.IP);
							$("#center_port").val(conf_value.PORT);
						}
						
						<%-- ACS服务配置 --%>
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
	
    <%-- 设备运行模板配置保存事件  --%>
	$(document).on("click","#runTempSave",function(){
		startSaveRun();
// 		$(".shade").eq(1).trigger("click");
		var detailList = [];
		if(_.isEmpty(runTempObj)){ 
			
		}
		$("#run_right_form .right-content").each(function(){
			var obj = {};
			var thistype = $(this).attr("data-type");
			var conftype = "";
			var confvalue = {};
			
			<%-- 基础配置，language_config、runlog_config --%>
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
			
			<%-- 功能配置function_config --%>
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
			
			<%-- 超时配置timeout_config --%>
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
			
			<%-- 定时任务配置scheduletask_config --%>
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
			
			<%-- 打印机配置 print_config --%>
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
			
			<%-- 办证配置 register_config --%>
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
			<%-- 本地数据库配置 localdb_config --%>
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
			<%-- 云中心配置 center_config --%>
			if(thistype == "center"){
				conftype = thistype + "_config";
				confvalue.IP = $("#"+thistype+"_"+"ip").val();
				confvalue.PORT = $("#"+thistype+"_"+"port").val();
				
				obj.run_conf_type = conftype;
				obj.run_conf_value = confvalue;
				detailList.push(obj);
			}
			
			<%--  acs服务配置 ACS_config --%>
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
	
	<%-- 对比数据，判断是否使用模板 --%>
	function compareRunTemp(){
		
		if(!_.isEmpty(runTempObj) && !_.isEmpty(runTempSubmit)){
			var idx = runTempObj.device_tpl_idx;
			if(runTempObj.runDetailList.length==runTempSubmit.runDetailList.length){
				var list1 = runTempObj.runDetailList;
				var list2 = runTempSubmit.runDetailList;
				
				<%-- 按照类型排序 --%>
				list1 = _.sortBy(list1,"run_conf_type");
				list2 = _.sortBy(list2,"run_conf_type");
				
				for(var i=0;i<list1.length;i++){
					var conf_type1 = list1[i].run_conf_type;
					var conf_type2 = list2[i].run_conf_type;
					
					if(conf_type1!=conf_type2){
						<%-- 如果不相等相等，则使用自定义数据 --%>
						runTempSubmit.device_tpl_type = "1";
						runTempCustom[idx] = runTempSubmit;<%-- 保存到该选项的自定义数据中 --%>
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
						<%-- 如果不相等相等，则使用自定义数据 --%>
						runTempSubmit.device_tpl_type = "1";
						runTempCustom[idx] = runTempSubmit;<%-- 保存到该选项的自定义数据中 --%>
						runTempType("1");
						break;
					}else{
						
					}
					<%-- 如果相等，则使用模板 --%>
					runTempSubmit.device_tpl_type = "0";
					delete runTempCustom[idx];<%-- 删除该选项的自定义数据 --%>
					runTempType("0");
				}
			}else{
				<%-- 如果不相等相等，则使用自定义数据 --%>
				runTempSubmit.device_tpl_type = "1";
				runTempCustom[idx] = runTempSubmit;<%-- 保存到该选项的自定义数据中 --%>
				runTempType("1");
			}
		}else{
			layer.alert("请选择模板");
		}
	}
	
	
	
	
	<%--注册相关
	####################################################################################################################################################################################
	####################################################################################################################################################################################
	####################################################################################################################################################################################
	####################################################################################################################################################################################
	####################################################################################################################################################################################
	####################################################################################################################################################################################
	--%>
	var loaddingLibName = false;
	
	$(document).on("change","#libId",function(){
		var libid = $("#libId").val();
		acsTempList = {};
		acsTempHtml = "";
		if(!_.isEmpty(libid) && !loaddingLibName){
			loaddingLibName=true;
			$.ajax({
				url:"<%=basePath%>device/getLibName",
				type:"POST",
				data:{json:libid},
				success:function(data){
					loaddingLibName=false;
					if(data!=null && data!=""){
						if(data.state){
							$("#libname").val(data.result);
							if(!_.isEmpty(data.message)){
								$("#libId").val(data.message);
								
							}
							if(!_.isEmpty(data.retMessage)){
								$("#libIdx").val(data.retMessage);
								<%-- 获取acs模板数据 --%>
								getAscTempList(data.retMessage);
							}
						}
					}
				}
			});
		}
	})
	
	<%-- 输入数据检查 --%>
	function checkData(){
		var deviceCode = $("#deviceCode").val();
		var libId = $("#libId").val();
		var deviceId = $("#deviceId").val();
		var deviceName = $("#deviceName").val();
		var deviceType = $("#deviceType").val();
		var acsName = $("#acsName").val();
		var acsPwd = $("#acsPwd").val();
		var logisticsNumber = $("#logisticsNumber").val();
		var region = $("#area").val();
		

		var extTempId = $("#extTemp").val();
		var runTempId = $("#runTemp").val();
		var monitorTempId = $("#monitorTemp").val();
		var dbSyncTempId = $("#dbSyncTemp").val();
		
		var pId = $("#pId").val();
		var flag = true;
		
		if(pId == ""){
			$("#pId").closest("li").addClass("error");
			$("#pId").siblings("").remove();
			$("#pId").after(errorMsg("不能为空"));
			flag = false;
		}else{
			$("#pId").closest("li").removeClass("error");
		}
		
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
		}else if(deviceId.indexOf("#")>=0 || deviceId.indexOf("/")>=0 || deviceId.indexOf("\\")>=0 || deviceId.indexOf(".")>=0 || deviceId.indexOf("@")>=0){
			$("#deviceId").closest("li").addClass("error");
			$("#deviceId").siblings().remove();
			$("#deviceId").after(errorMsg("不能使用 # / . \\ @ 特殊字符"));
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
		
		<%--下拉框的样式不太一样，错误信息提示跟父节点同级---%>
		if(deviceType=="-1"){
			$("#deviceType").closest("li").addClass("error");
			$("#deviceType").parent().siblings().remove();
			$("#deviceType").parent().after(errorMsg("请选择设备类型"));
			flag = false;
		}else{
			$("#deviceType").closest("li").removeClass("error");
		}
		
		if(region=="-1"){
			$("#area").closest("li").addClass("error");
			flag = false;
		}else{
			$("#area").closest("li").removeClass("error");
		}
		
		<%-- 检查acs的值 --%>
		$(".form-wrap.acs").each(function(i,n){
			$select = $(this).find("select");
			$serverName = $(this).find("input.servername");
			$acsip = $(this).find("input.acsip");
			$acsport = $(this).find("input.acsport");
			$acsloginname = $(this).find("input.loginname");
			$acsloginpwd = $(this).find("input.loginpwd");
			
			if($select.val()=="" || $select.val()=="-1"){
				$select.closest("li").addClass("error");
				flag = false;
			}else{
				$select.closest("li").removeClass("error");
			}
			
			if(_.isEmpty($serverName.val())){
				$serverName.closest("li").addClass("error");
				$serverName.siblings(".error-msg").html("请填写后台服务名")
				flag = false;
			}else{
				if($serverName.val().match(/^\w+$/)==null){
					$serverName.siblings(".error-msg").html("请填写英文或者数字")
					$serverName.closest("li").addClass("error");
				}else{
					$serverName.closest("li").removeClass("error");
				}
			}
			
			if(_.isEmpty($acsip.val())){
				$acsip.closest("li").addClass("error");
				flag = false;
			}else{
				$acsip.closest("li").removeClass("error");
			}
			
			if(_.isEmpty($acsport.val())){
				$acsport.closest("li").addClass("error");
				flag = false;
			}else{
				$acsport.closest("li").removeClass("error");
			}
			
			if(_.isEmpty($acsloginname.val())){
				$acsloginname.closest("li").addClass("error");
				flag = false;
			}else{
				$acsloginname.closest("li").removeClass("error");
			}
			
			if(_.isEmpty($acsloginpwd.val())){
				$acsloginpwd.closest("li").addClass("error");
				flag = false;
			}else{
				$acsloginpwd.closest("li").removeClass("error");
			}
			
		});
		
		

		<%--下拉框的样式不太一样，错误信息提示跟父节点同级---%>
		if(extTempId=="-1"){
			$("#extTemp").closest("div.item").addClass("error");
			flag = false;
		}else{
			$("#extTemp").closest("div.item").removeClass("error");
		}
		if(runTempId=="-1"){
			$("#runTemp").closest("div.item").addClass("error");
			flag = false;
		}else{
			$("#runTemp").closest("div.item").removeClass("error");
		}
		if(monitorTempId=="-1"){
			$("#monitorTemp").closest("div.item").addClass("error");
			flag = false;
		}else{
			$("#monitorTemp").closest("div.item").removeClass("error");
		}
		if(dbSyncTempId=="-1"){
			$("#dbSyncTemp").closest("div.item").addClass("error");
			flag = false;
		}else{
			$("#dbSyncTemp").closest("div.item").removeClass("error");
		}
		
		return flag;
	}
	
	function gotoUrl(url){
		window.location.href = url;
	}
	
	function beginRegister(){
		$(".g-loading:not(.save)").stop();
		$(".g-loading:not(.save)").fadeIn();
	}
	
	function endRegister(time){
		$(".g-loading:not(.save)").fadeOut();
	}
	
	<%--注册按钮--%>
	$(".g-submit").on("click",function(){
		beginRegister();
		/*注册成功传入true,否则false*/
		if(!checkData()){
			endRegister();
			return;
		}
		var deviceCode = $("#deviceCode").val();
		var pId = $("#pId").val();
		var library_idx = $("#libIdx").val();
		var libId = $("#libId").val();
		var deviceId = $("#deviceId").val();
		var deviceName = $("#deviceName").val();
		var deviceType = $("#deviceType").val();
		var deviceTypeDesc = $("#deviceType").find("option:selected").attr("data-type");
		var acsName = $("#acsName").val();
		var acsPwd = $("#acsPwd").val();
		var logisticsNumber = $("#logisticsNumber").val();
		var region = $("#area").val();
		
		var extTempId = $("#extTemp").val();
		var runTempId = $("#runTemp").val();
		var monitorTempId = $("#monitorTemp").val();
		var dbSyncTempId = $("#dbSyncTemp").val();
		var deviceDesc = $("#deviceDesc").val();
		var circuleLocation = $("#circuleLocation").val();
		
		var acsList = [];
		<%-- 检查acs的值 --%>
		$(".form-wrap.acs").each(function(i,n){
			$select = $(this).find("select");
			$serverName = $(this).find("input.servername");
			$acsip = $(this).find("input.acsip");
			$acsport = $(this).find("input.acsport");
			$acsloginname = $(this).find("input.loginname");
			$acsloginpwd = $(this).find("input.loginpwd");
			$acslogintype = $(this).find("select.logintype");
			$acslogincheck = $(this).find("select.logincheck");
			$acslogincount = $(this).find("input.logincount");
			$acslogincode = $(this).find("input.logincode");
			
			var acsObj = {};
			acsObj.protocol_tpl_idx = $select.val();
			acsObj.library_idx = $("#libIdx").val();
			acsObj.login_ip = $acsip.val();
			acsObj.login_port = $acsport.val();
			acsObj.login_username = $acsloginname.val();
			acsObj.login_pwd = $acsloginpwd.val();
			
			acsObj.login_type = $acslogintype.val();
			acsObj.login_check = $acslogincheck.val();
			acsObj.login_count= $acslogincount.val();
			acsObj.login_code = $acslogincode.val();
			
			acsObj.acs_service_name = $serverName.val();
			
			acsList.push(acsObj);
		});
		
		var param = {};
		param.deviceCode = deviceCode;
		param.pId = pId;
		param.libId = libId;
		param.library_idx=library_idx;
		param.deviceId = deviceId;
		param.deviceName = deviceName;
		param.deviceType = deviceType;
		param.deviceTypeDesc = deviceTypeDesc;
		param.acsName = acsName;
		param.acsPwd = acsPwd;
		param.logisticsNumber = logisticsNumber;
		param.extTempId = extTempId;
		param.runTempId = runTempId;
		param.monitorTempId = monitorTempId;
		param.dbSyncTempId = dbSyncTempId;
		param.deviceDesc = deviceDesc;
		param.acsList = acsList;
		param.circuleLocation = circuleLocation;
		param.region = region; <%-- 地区信息 --%>
		if(extTempSubmit.device_tpl_type == "1"){
			param.ext_tpl_type = "1";
			param.extTempSubmit = JSON.stringify(extTempSubmit);
		}
		if(runTempSubmit.device_tpl_type == "1"){
			param.run_tpl_type = "1";
			param.runTempSubmit = JSON.stringify(runTempSubmit);
		}
		console.info("ACS所有配置参数:",param);
		<%-- 数据提交，设备注册 --%>
		$.ajax({
			url:"<%=basePath%>device/deviceRegister",
			type:"POST",
			data:param,
			success:function(data){
				if(data!=null ){
					if(data.state){
						if(data.message=="success"){
							registerResult(true);
							//保存上传文件
							console.info("param2---------:",param.library_idx+ param.libId+ param.deviceId);
							$.ajaxFileUpload({
							   	url:"<%=basePath%>common/uploadFileToFileServer",
							   	secureuri :false,
								 fileElementId :"ncipTemplate",//file控件id
								 type:'post',
								 data: { libIdx:param.library_idx,libId: param.libId, deviceId: param.deviceId},
								 dataType:'json',
								 success : function (data,status){
									 console.info("param3---------:",param.library_idx+ param.libId+ param.deviceId);
									
								},
								 error: function(data, status, e){
								      console.log(data,status,e);
								 } 
							}); 
							if(!_.isEmpty(data.result) 
									&& !_.isEmpty(data.result.libraryId) 
									&& !_.isEmpty(data.result.deviceId)){
								<%-- 访问中间件 --%>
								systemInit(data.retMessage,data.result.libraryId,data.result.deviceId);
							}else{
								registerResult(false,"参数不足，请联系维护人员！","注册成功，系统初始化时出现问题！");
							}
// 							setTimeout(function(){gotoUrl(data.retMessage)},3000);
						}
					}else{
						if(data.message=="code"){
							registerResult(false,"该特征码设备已经注册，自动跳转...");
							setTimeout(function(){gotoUrl(data.retMessage)},3000);
							return;
						}else if(data.message=="max"){
							registerResult(false,"注册失败，已经超出该馆的最大注册设备数！");
						}else{
							layer.alert(data.message);
						}
					}
				}else{
					layer.alert("网络连接失败，请重试！");
				}
			}
		}).always(function(){
			endRegister();
		})
	});
	
	<%-- 访问中间件，进行设备初始化 
	地址：http://localhost:38090/webservice/sys.asmx/ServiceHandle  
	参数： {
	    "servicetype":"sys",
	    "target":"sys_config",
	    "operation":"sys_init_set_id",
	    "data":"{"LIBRARYID":"TEST001","DEVICEID":"STA010"}"
	}
	返回：{
	    "servicetype":"sys",
	    "target":"sys_config",
	    "operation":"sys_init_set_id",
	    "data":"",
	    "result":"0"
	} 
	--%>
	function systemInit(dataUrl,libraryId,deviceId){
		var param = {
				req:{
					servicetype:"sys",
					target:"sys_init",
					operation:"sys_init_set_id",
					data:"{\"DEVICEID\":\""+deviceId+"\",\"LIBRARYID\":\""+libraryId+"\"}"
				}
		};
		var errMsg = "注册成功，系统初始化时出现问题！";
		$.ajax({
			url:"http://localhost:38090/webservice/sys.asmx/ServicesHandle",
			type:"POST",
			timeout:120000,//120秒超时时间
			contentType: "application/json; charset=utf-8",
			data:JSON.stringify(param)
		}).done(function(data,status){
			console.log("done:",JSON.stringify(data));
			if(!_.isEmpty(data) && !_.isEmpty(data.d) && data.d.result!=null){
				if(data.d.result=="0" || data.d.result==0){
					registerResult(true,"正在跳转，请稍后...");	
					setTimeout(function(){gotoUrl(dataUrl)},2000);
				}else{
// 					var err = jsonToObj(data.d.data);
// 					var message = "";
// 					var description = "";
// 					if(err!=""){
// 						message = err.Message;
// 						description = err.Description;
// 					}
					registerResult(false,"请联系维护人员！",errMsg);	
				}
			}else{
				registerResult(false,"请联系维护人员！",errMsg);			
			}
		}).fail(function(data,status,code){
			console.log("fail:",JSON.stringify(data));
			if(data!=null && data.statusText!="timeout"){
				registerResult(false,"设备连接初始化服务器失败，错误码："+data.status+"，请联系维护人员！",errMsg);	
			}
		}).always(function(data,status){
			console.log("always:",JSON.stringify(data));
			if(status=="timeout"){
				console.log("超时");
				registerResult(false,"设备初始化超时，请联系维护人员！",errMsg);					
			}
		});
	}
	
	
	<%-- 注册之后提示的返回按钮 --%>
	$(document).on("click",".register-back",function(){
		$(".register-result").fadeOut();
	})
	
	<%-- 注册结果页面效果 --%>
	function registerResult(status,msg,errMsg){
		$("html, body").animate({scrollTop:0},"fast");
		if(!status){
			$(".register-result").addClass("fail")
			if(errMsg!=null){
				$(".register-result").find(".status-word").html(errMsg);
			}else{
				$(".register-result").find(".status-word").html("抱歉，你的设备注册失败");
			}
			if(msg!=null){
				$(".register-result").find(".tips-word").html(msg);
			}else{
				$(".register-result").find(".tips-word").html("你可以请重新尝试注册，或者联系系统管理员解决问题。");
			}
			$(".register-back").show();
		}else{
			$(".register-back").hide();
			$(".register-result").removeClass("fail");
			if(msg!=null){
				$(".register-result").find(".status-word").html("恭喜你，初始化成功");
				$(".register-result").find(".tips-word").html(msg);				
			}else{
				$(".register-result").find(".status-word").html("恭喜你，设备已经注册成功");
				$(".register-result").find(".tips-word").html("系统正在进行初始化，请稍后...");
			}
		}
		$(".register-result").fadeIn();
	}

	
	
	
	
	<%---输入框初始值，开发 测试用--%>
	$(function(){
// 		$("#libId").val("m31").trigger("change");
// 		$("#deviceId").val("4041");
// 		$("#deviceName").val("40141");
// 		$("#acsName").val("acsName");
// 		$("#acsPwd").val("acsPwd");
// 		$("#logisticsNumber").val("WLBH");
// 		$("#circuleLocation").val("LOCATION");
		
	});
	
</script>

</body>
</html>

<%-- 使用template-web.js模板来组装其他选项卡里面的数据  https://aui.github.io/art-template/docs/syntax.html--%>
<%--  item数据如下
	ext_model:"CRV100D"
	ext_model_desc:"{"zh_CN":"华视身份证阅读器"}"
	ext_model_driver_path:""
	ext_model_version:"V3"
	ext_type:"idreader"
	meta_ext_idx:1
--%>
<script type="text/html" id="test">
{{if keys}}
<input type="hidden" value="{{meta_log_obj_idx}}" id="meta_log_obj_idx">
	{{each keys}}
		<%-- 设置item变量的值，内容如上 --%>
		{{set item = map[$value]}}
<%--{{item}}--%>
<%--{{$value}}--%>
				<div class="form-wrap form-wrap2">
						<ul>
							<li>
								<div class="left">逻辑名</div>
								<div class="right">
									<input type="text" name="" id="other_{{item.ext_model}}_showName"  class="g-input showname" /> <span class="error-msg">请输入模板id</span>
								</div>
							</li>
							<li style="">
								<div class="left">外设型号</div>
								<div class="right">
									<input type="text" name="" id="" class="g-input" value="{{item.ext_model}}" readonly="readonly" style="color: gray;background-color: #E0E0E0"
										placeholder="请输入" /> <span class="error-msg">错误提示</span>
								</div>
							</li>
							<li class="hide">
								<div class="left">逻辑名</div>
								<div class="right">
									<div class="g-select">
										<select id="other_{{item.ext_model}}_logicName" class="logicname">
											<option value="Other" selected>其它</option>
										</select> <span class="arr-icon"></span>
									</div>
								</div>
							</li>
							
							<li>
								<div class="left">外设部件</div>
								<div class="right">
									<div class="g-select">
										<select id="other_{{item.ext_model}}_extModel" class="need-change extmodel">
											<option data-id={{item.ext_type}} value="{{item.meta_ext_idx}}" selected>{{jsonToObj(item.ext_model_desc)['zh_CN']}}</option>
										</select> <span class="arr-icon"></span>
									</div>
								</div>
							</li>
							<li>
								<div class="left">通讯方式</div>
								<div class="right">
									<div class="g-select">
										<select id="other_{{item.ext_model}}_comm" class="need-change notice-type">
											<option value="-1">选择通讯方式</option>
											<option value="com">COM</option>
											<option value="usb">USB</option>
											<option value="lpt">LPT</option>
											<option value="net">NET</option>
										</select> <span class="arr-icon"></span>
									</div>
								</div>
							</li>
							<li>
								<div class="changebox" date-id="other_{{item.ext_model}}_comm" val="com">
									<ul>
										<li>
											<div class="left">COM口</div>
											<div class="right">
												<input type="text" name="" id="other_{{item.ext_model}}_COM" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
										<li>
											<div class="left">波特率</div>
											<div class="right">
												<input type="text" name="" id="other_{{item.ext_model}}_BAUD" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
									</ul>
								</div>
								<div class="changebox" date-id="other_{{item.ext_model}}_comm" val="usb">
									<ul>
										<li>
											<div class="left">USB</div>
											<div class="right">
												<input type="text" name="" id="other_{{item.ext_model}}_USB" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
									</ul>
								</div>
								<div class="changebox" date-id="other_{{item.ext_model}}_comm" val="net">
									<ul>
										<li>
											<div class="left">IP</div>
											<div class="right">
												<input type="text" name="" id="other_{{item.ext_model}}_IP" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
										<li>
											<div class="left">端口</div>
											<div class="right">
												<input type="text" name="" id="other_{{item.ext_model}}_PORT" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
									</ul>
								</div>
							</li>
							{{if item.ext_type.toLowerCase()=='cashacceptor'}}
								<li>
									<div class="changebox money" id="other_{{item.ext_model}}_money" val="" style="display: block;">
										<span class="t">收款通道</span>
										<div class="com-item" style="margin-left:31px">
											<div class="g-checkbox"><input type="checkbox" name="oneyuan" id="{{item.ext_model}}_one" value="1" ></div><label for="{{item.ext_model}}_one">1元</label>
											<div class="g-checkbox"><input type="checkbox" name="fiveyuan" id="{{item.ext_model}}_five" value="5" ></div><label for="{{item.ext_model}}_five" >5元</label>
											<div class="g-checkbox"><input type="checkbox" name="tenyuan" id="{{item.ext_model}}_ten" value="10" ></div><label for="{{item.ext_model}}_ten">10元</label>
										</div>
										<div class="com-item" style="margin-left:31px">
											<div class="g-checkbox"><input type="checkbox" name="twentyyuan" id="{{item.ext_model}}_twenty" value="20" ></div><label for="{{item.ext_model}}_twenty">20元</label>
											<div class="g-checkbox"><input type="checkbox" name="fiftyyuan" id="{{item.ext_model}}_fifty" value="50" ></div><label for="{{item.ext_model}}_fifty">50元</label>
											<div class="g-checkbox"><input type="checkbox" name="hundredyuan" id="{{item.ext_model}}_hundred" value="100" ></div><label for="{{item.ext_model}}_hundred">100元</label>
										</div>
									</div>
								</li>
							{{/if}}
							{{if item.ext_type.toLowerCase().indexOf('plcssl')>-1}}
								<li>
									<div class="changebox" id="" date-id="" val="" style="display: block;">
										<span class="t">书盒信息</span>
										<div class="w50 fl min-input">
											<span class="word">上层书架</span><input type="text" name="" id="other_{{item.ext_model}}_shelf1"
												class="g-input shelf1" placeholder="" />个
										</div>
										<div class="w50 fl min-input">
											<span class="word">特型书盒</span><input type="text" name="" id="other_{{item.ext_model}}_drawer"
												class="g-input drawer" placeholder="" />个
										</div>
										<div class="w50 fl min-input">
											<span class="word">中层书架</span><input type="text" name="" id="other_{{item.ext_model}}_shelf2"
												class="g-input shelf2" placeholder="" />个
										</div>
										<div class="w50 fl min-input">
											<span class="word">还书箱个数</span><input type="text" name="" id="other_{{item.ext_model}}_bookbin"
												class="g-input bookbin" placeholder="" />个
										</div>
										<div class="w50 fl min-input">
											<span class="word">下层书架</span><input type="text" name="" id="other_{{item.ext_model}}_shelf3"
												class="g-input shelf3" placeholder="" />个
										</div>
									</div>
								</li>
							{{/if}}
								
						</ul>
					</div>
		<%-- 分割线 --%>
		<div class="segmentation">
			<div class="t"></div>
		</div>
	{{/each }}
{{/if}}

</script>





