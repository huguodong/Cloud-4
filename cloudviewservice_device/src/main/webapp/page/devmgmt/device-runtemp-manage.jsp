<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>

<style>

.form-wrap li.error div.g-select {
    background-color: #ffeeee;
    border-color: #ff2323;
}

div.form-wrap span.error-msg{
	display: none;
}

div.form-wrap div.li.error div.g-select{
	background-color: #ffeeee;
    border-color: #ff2323;
}

div.form-wrap div.li.error span.error-msg{
	margin-left: 10px;
	display:inline;
	color: #ff2323;
}
.g-loading.save {
	background-position: 80% center;
}
.print_config label {
    padding-left: 10px;
    vertical-align: middle;
    display: -webkit-inline-box;
    min-width: 60px;
}
.page-title-bar .g-select {
    float: left;
    margin-left: 10px;
    width: 130px;
    height: 28px;
    line-height: 28px;
    border: 1px solid #dddddd;
    border-radius: 2px;
    background-color: #f6f6f6;
}
</style>

<div class="config-yingjian">
<div class="g-loading"></div>
<div class="g-loading save" ></div>
	<div class="">
		<div class="page-title-bar">
			<span class="title">运行模板配置<a href="${pageContext.request.contextPath}/help/main?url=/page/common/help/devmgmt/device-runtemp-manage.jsp" target="_blank" class="g-help"></a></span>
			<div class="form-wrap fr">
				<div class="g-select">
					<select id="deviceType">
						<option value="-1" selected>选择设备类型</option>
					</select>
					<span class="arr-icon"></span>
				</div>
				<input type="text" name="" id="keyword" class="input g-input" placeholder="输入模板ID或模板名称" />
				<div class="btn search">查询</div>
				<!-- 修改权限 by huanghuang 20170215 -->
				<shiro:hasPermission name="0102020701">
					<div class="btn increase">新增</div>
				</shiro:hasPermission>
				<shiro:hasPermission name="0102020702">
					<div class="btn delete">删除</div>
				</shiro:hasPermission>
			</div>
		</div>
		<div class="main">
			<table class="g-table">
				<thead>
					<tr>
						<th class="col1"><div class="g-checkbox"><input type="checkbox" name="" id="" /></div></th>
						<th class="col2">模板ID</th>
						<th class="col3">名称</th>
						<th class="col4">模板模块</th>
						<th class="col5">模板内容</th>
						<th class="col6">操作</th>
					</tr>
				</thead>
				<tbody>
				<%--
					<tr>
						<td class="col1"><div class="g-checkbox"><input type="checkbox" name="" id="" /></div></td>
						<td>00001</td>
						<td>24小时自助借还运行配置</td>
						<td>基本配置</td>
						<td>系统语言：简体中文；运行日志级别：error；运行日志保存方式：数据库；运行日志文件大小：100MB</td>
						<td>
							<span class="btn-a edit">编辑</span>
							<span class="btn-a delete">删除</span>
						</td>
					</tr>
					<tr>
						<td class="col1"><div class="g-checkbox"><input type="checkbox" name="" id="" /></div></td>
						<td>00002</td>
						<td>借书机运行配置</td>
						<td>ACS配置</td>
						<td>系统语言：简体中文；运行日志级别：error；运行日志保存方式：数据库；运行日志文件大小：100MB</td>
						<td>
							<span class="btn-a edit">编辑</span>
							<span class="btn-a delete">删除</span>
						</td>
					</tr>
					 --%>
				</tbody>
			</table>
		</div>
		
		<%@include file="../include/page_bar.jsf" %>
		<%--
		<div class="paging-bar">
			<div class="left">
				<span class="t fl">选择</span>
				<div class="btn g-chooseAll">全选</div>
				<div class="btn g-noChooseAll">反选</div>
				<span class="t2 fl">显示</span>
				<div class="g-select refresh">
					<select>
						<option value="" selected>30</option>
						<option value="">60</option>
					</select>
					<span class="arr-icon"></span>
				</div>
				<span class="t2 fl">已选中7个</span>
			</div>
			<div class="right">
				<div class="prev-page">上一页</div>
				<ul>
					<li class="active">1</li>
					<li>2</li>
					<li>3</li>
					<li>4</li>
					<li>5</li>
					<li>...</li>
					<li>50</li>
					<li>120</li>
				</ul>
				<div class="next-page">下一页</div>
			</div>
			<span class="total-page fr">共500</span>
		</div>
		 --%>
	</div>
</div>




<div class="form-dialog-fix w-900">
	<div class="shade"></div>
	<div class="form-dialog">
			<div class="title">
				<span>修改配置</span>
				<input type="reset" value="取消" class="g-cancel2 g-btn-gray">
				<input type="submit" placeholder="" id="runTempSave" class="g-submit2 g-btn-green" value="保存">
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
						<div class="t">基本信息</div>
					</div>
					<div class="form-wrap form-wrap2">
						<ul>
							<li style="">
								<div class="left">模板ID</div>
								<div class="right">
									<input type="hidden" name="" id="tpl_idx" />
									<input type="text" name="" id="run_id" maxlength="10" class="g-input" 
										placeholder="请输入" /> <span class="error-msg">请输入模板ID</span>
								</div>
							</li>
							<li style="">
								<div class="left">模板名称</div>
								<div class="right">
									<input type="text" name="" id="run_desc" class="g-input" 
										placeholder="请输入" /> <span class="error-msg">请输入模板名称</span>
								</div>
							</li>
							<li>
								<div class="left">设备类型</div>
								<div class="right">
									<div class="g-select" >
										<select id="deviceType_edit" class="deviceType">
											<option value="-1" >请选择</option>
										</select> <span class="arr-icon"></span>
									</div>
									<span class="error-msg">请选择设备类型</span>
								</div>
							</li>
						</ul>
					</div>
					<div class="segmentation">
						<div class="t">语言版本</div>
					</div>
					<div class="form-wrap form-wrap2">
						<!-- <ul>
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
						</ul> -->
						<ul class="w13li basic_language">
							<li>
								<div class="g-checkbox"><input  json='{"index":"1","language":"chinese","remarks":"简体中文"}' type="checkbox" id="chinese"></div><label for="chinese">简体中文</label>
							</li>
							<li>
								<div class="g-checkbox"><input  json='{"index":"2","language":"traditional","remarks":"繁體中文"}' type="checkbox" id="traditional"></div><label for="traditional">繁体中文</label>
							</li>
							<li>
								<div class="g-checkbox"><input  json='{"index":"3","language":"english","remarks":"English"}' type="checkbox" id="english"></div><label for="english">英文</label>
							</li>
							<li>
								<div class="g-checkbox"><input  json='{"index":"4","language":"korean","remarks":"한국어"}' type="checkbox" id="korean"></div><label for="korean">韩文</label>
							</li>
							<li>
								<div class="g-checkbox"><input  json='{"index":"5","language":"japanese","remarks":"日本語の"}' type="checkbox" id="japanese"></div><label for="japanese">日文</label>
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
					<div class="segmentation"><div class="t">隐私保护</div></div>
					<div class="com-item">
						<div class="g-checkbox on"><input type="checkbox" name="private_hide" id="private_hide"/></div><label for="">隐藏隐私</label>
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
						<li>
							<div class="g-checkbox"><input type="checkbox" id="function_supplement"></div><label for="function_supplement">补书</label>
						</li>
						<li>
							<div class="g-checkbox"><input type="checkbox" id="function_onshelve"></div><label for="function_onshelve">上架</label>
						</li>
						<li>
							<div class="g-checkbox"><input type="checkbox" id="function_offshelve"></div><label for="function_offshelve">下架</label>
						</li>
						<li>
							<div class="g-checkbox"><input type="checkbox" id="function_stocktaking"></div><label for="function_stocktaking">盘点</label>
						</li>
						<li>
							<div class="g-checkbox"><input type="checkbox" id="function_alert"></div><label for="function_alert">告警（安全门）</label>
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
			<div class="right-content hide print_config" data-type="print">
				
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
							<div class="g-radio"><input type="radio" name="checkin_type" id="checkin_ask" value="2" checked="checked"></div><label for="checkin_ask">询问读者</label>
							<div class="g-radio"><input type="radio" name="checkin_type" id="checkin_no" value="1"></div><label for="checkin_no">不打印</label>
							<div class="fr"><!-- fr -->
								<div class="g-checkbox"><input type="checkbox" name="checkin_timeout" id="checkin_timeout"></div><label for="checkin_timeout">超时打印</label>
							</div>
							<div class="right">
								<div class="g-checkbox"><input type="checkbox" id="checkin_date_checkbox"></div><label for="checkin_date_checkbox">日期</label>
								<div class="g-checkbox"><input type="checkbox" id="checkin_time_checkbox"></div><label for="checkin_time_checkbox">时间</label>
								<div class="g-checkbox"><input type="checkbox" id="checkin_enableamount_checkbox"></div><label for="checkin_enableamount_checkbox">数量</label>
							</div>
						</div>
					</div>
				<%-- <div class="small-item">
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
					</div> --%>
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
							<div class="g-radio"><input type="radio" name="checkout_type" id="checkout_ask" value="2" checked="checked"></div><label for="checkout_ask">询问读者</label>
							<div class="g-radio"><input type="radio" name="checkout_type" id="checkout_no" value="1"></div><label for="checkout_no">不打印</label>
							<div class="fr">
								<div class="g-checkbox"><input type="checkbox" name="checkout_timeout" id="checkout_timeout"></div><label for="checkout_timeout">超时打印</label>
							</div>
							<div class="right">
								<div class="g-checkbox"><input type="checkbox" id="checkout_date_checkbox"></div><label for="checkout_date_checkbox">日期</label>
								<div class="g-checkbox"><input type="checkbox" id="checkout_time_checkbox"></div><label for="checkout_time_checkbox">时间</label>
								<div class="g-checkbox"><input type="checkbox" id="checkout_enableamount_checkbox"></div><label for="checkout_enableamount_checkbox">数量</label>
							</div>
							<div class="right">
								<div class="g-checkbox"><input type="checkbox" id="checkout_enablename_checkbox"></div><label for="checkout_enablename_checkbox">读者姓名</label>
								<div class="g-checkbox"><input type="checkbox" id="checkout_patron_checkbox"></div><label for="checkout_patron_checkbox">读者证号</label>
								<div class="g-checkbox"><input type="checkbox" id="checkout_enablefee_checkbox"></div><label for="checkout_enablefee_checkbox">费用</label>
							</div>
							
						</div>
					</div>
					<%-- <div class="small-item">
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
					</div> --%>
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
							<div class="g-radio"><input type="radio" name="renew_type" id="renew_ask" value="2" checked="checked"></div><label for="renew_ask">询问读者</label>
							<div class="g-radio"><input type="radio" name="renew_type" id="renew_no" value="1"></div><label for="renew_no">不打印</label>
							<div class="fr">
								<div class="g-checkbox"><input type="checkbox" name="renew_timeout" id="renew_timeout"></div><label for="renew_timeout">超时打印</label>
							</div>
							<div class="right">
								<div class="g-checkbox"><input type="checkbox" id="renew_date_checkbox"></div><label for="renew_date_checkbox">日期</label>
								<div class="g-checkbox"><input type="checkbox" id="renew_time_checkbox"></div><label for="renew_time_checkbox">时间</label>
								<div class="g-checkbox"><input type="checkbox" id="renew_patron_checkbox"></div><label for="renew_patron_checkbox">读者证号</label>
							</div>
							<div class="right">
								<div class="g-checkbox"><input type="checkbox" id="renew_enablerenewcount_checkbox"></div><label for="renew_enablerenewcount_checkbox">续借数</label>
								<div class="g-checkbox"><input type="checkbox" id="renew_enableunrenewcount_checkbox"></div><label for="renew_enableunrenewcount_checkbox">未续借数</label>
							</div>
						</div>
					</div>
					<%-- <div class="small-item">
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
					</div> --%>
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
							<div class="g-radio"><input type="radio" name="pay_type" id="pay_ask" value="2" checked="checked"></div><label for="pay_ask">询问读者</label>
							<div class="g-radio"><input type="radio" name="pay_type" id="pay_no" value="1"></div><label for="pay_no">不打印</label>
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
							<div class="g-radio"><input type="radio" name="cashbin_type" id="cashbin_ask" value="2" checked="checked"></div><label for="cashbin_ask">询问读者</label>
							<div class="g-radio"><input type="radio" name="cashbin_type" id="cashbin_no" value="1"></div><label for="cashbin_no">不打印</label>
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
							<div class="g-radio"><input type="radio" name="bookbin_type" id="bookbin_ask" value="2" checked="checked"></div><label for="bookbin_ask">询问读者</label>
							<div class="g-radio"><input type="radio" name="bookbin_type" id="bookbin_no" value="1"></div><label for="bookbin_no">不打印</label>
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
							<div class="g-radio"><input type="radio" name="bookrack_type" id="bookrack_ask" value="2" checked="checked"></div><label for="bookrack_ask">询问读者</label>
							<div class="g-radio"><input type="radio" name="bookrack_type" id="bookrack_no" value="1"></div><label for="bookrack_no">不打印</label>
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
							<div class="g-radio"><input type="radio" name="cardbin_type" id="cardbin_ask" value="2" checked="checked"></div><label for="cardbin_ask">询问读者</label>
							<div class="g-radio"><input type="radio" name="cardbin_type" id="cardbin_no" value="1"></div><label for="cardbin_no">不打印</label>
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
						<div class="g-checkbox"><input type="checkbox" id="ACS_Search_finance"></div><label for="ACS_Search_finance">查询财经</label>
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

<div class="g-delete-dialog multi">
	<span class="line"></span>
	<div class="word">
		当前选择了 <span class="font-red"></span> 个项目<br>
		是否删除选择的设备运行模板配置？
	</div>
	<div class="form-btn cancel g-btn-gray">取消</div>
	<div class="form-btn remove g-btn-red">删除</div>
</div>

<div class="g-delete-dialog single">
	<span class="line"></span>
	<div class="word">
		是否要删除 <span class="font-red"></span>？
	</div>
	<div class="form-btn cancel g-btn-gray">取消</div>
	<div class="form-btn remove g-btn-red">删除</div>
</div>

<div class="g-delete-dialog alert">
	<span class="line"></span>
	<div class="word">
	</div>
	<div class="form-btn cancel g-btn-green">确定</div>
</div>


<script type="text/javascript">


function startLoading(){
	$(".g-loading").stop();
	$(".g-loading").fadeIn();
}

function endLoading(val){
	if(val=="1"){
		$(".g-loading").hide();
	}else{
		$(".g-loading").fadeOut();
	}
}

function startSaving(){
	$(".g-loading.save").stop();
	$(".g-loading.save").fadeIn();
}

function endSaving(val){
	if(val=="1"){
		$(".g-loading.save").hide();
	}else{
		$(".g-loading.save").fadeOut();
	}
}

<%-- 运行配置模板中的复选框选中事件 --%>
$("#run_right_form").on("change",".g-checkbox input[type='checkbox']",function(){
	if($(this).prop("checked")){
		$(this).parents(".g-checkbox").addClass("on");
	}else{
		$(this).parents(".g-checkbox").removeClass("on");
	}
});


$(function(){
	(function mainHeightController(){
		var winH = $(window).height();
		var headerH = $(".g-header").outerHeight();
		var pageTitleBar = $(".page-title-bar").outerHeight();
		var pagingBarH = $(".paging-bar").outerHeight();

		$(".main").css({
			"min-height":winH - headerH - pagingBarH -pageTitleBar
		});
	})();
	
	<%-- 删除事件 --%>
	var delLayer = null;
	
	<%-- 单条删除事件 --%>
	var deleteIdx = "";
	var deleteName = "";
	$("tbody").on("click",".delete",function(){
		deleteIdx = $(this).attr("data-id");
		deleteName = $(this).attr("data-name");
		$(".g-delete-dialog.single .font-red").html(deleteName);
		delLayer = layer.open({
			type: 1,
			shade: false,
			title: false, //不显示标题
			scrollbar :false,
			closeBtn :0,
			shade:0.5,
			shadeClose :false,
			area:["400px"],
			offset :["195px"],
			content: $('.g-delete-dialog.single')
		});
	});
	
	<%-- 点击 批量删除 按钮 --%>
	var deleteList = [];
	$("div.page-title-bar").on("click",".delete",function(){
		deleteList = [];
		var len = 0;
		$("tbody input[type='checkbox']:checked").each(function(){
			var obj = {};
			var idx = $(this).prop("id");
			var runId = $(this).attr("data-name");
			obj.idx=idx;
			obj.runId=runId;
			deleteList.push(obj);
		});
		len = deleteList.length;
		if(len<=0){
			layer.alert("请先选择要删除的模板！");
			return;
		}
		$(".g-delete-dialog.multi .font-red").html(len);
		
		delLayer = layer.open({
			type: 1,
			shade: false,
			title: false, //不显示标题
			scrollbar :false,
			closeBtn :0,
			shade:0.5,
			shadeClose :false,
			area:["400px"],
			offset :["195px"],
			content: $('.g-delete-dialog.multi')
		});
	});
	
	<%-- 取消按钮 --%>
	$(".form-btn.cancel").on("click",function(){
		closeLayer(delLayer);
	});
	
	<%-- 取消按钮 --%>
	$(".form-btn.cancel.g-btn-green").on("click",function(){
		closeLayer(multiLayer);
	});
	
	
	function closeLayer(index){
		if (index) {
			layer.close(index);
		}
	}
	
	<%-- 执行单条删除删除操作 --%>
	$(".g-delete-dialog.single .form-btn.remove").on("click",function(){
		if(deleteIdx==""){
			closeLayer(delLayer);
			return;
		}
		$delete = $(this);
		if($delete.hasClass("disabled")){
			return;
		}else{
			$delete.addClass("disabled");
		}
		$.ajax({
			url:"<%=basePath%>devicerun/delRunTemp",
			type:"POST",
			data:{"json":deleteIdx}
		}).done(function(data){
			if(data!=null && data!=""){
				if(data.state && data.message=="success"){
					showMsg({
						timeout : 1000,
						showText : '删除成功',
						status : true
					});
				}else{
					var msg = "";
					if(data.message=="1"){
						msg = deleteName+"正在被使用！";
					}
// 					showMsg({
// 						timeout : 3000,
// 						showText : "删除失败,"+msg,
// 						status : false
// 					});
					layer.alert(msg);
				}
			}
		}).always(function(){
			$delete.removeClass("disabled");
			refreshCurrent();
			closeLayer(delLayer);
		});
		
	});
	
	var multiLayer = null;
	<%-- 执行批量删除操作 --%>
	$(".g-delete-dialog.multi .form-btn.remove").on("click",function(){
		if(deleteList.length<=0){
			closeLayer(multiLayer);
			return;
		}
		$delete = $(this);
		if($delete.hasClass("disabled")){
			return;
		}else{
			$delete.addClass("disabled");
		}
		$.ajax({
			url:"<%=basePath%>devicerun/delMultiRunTemp",
			type:"POST",
			data:{"json":JSON.stringify(deleteList)}
		}).done(function(data){
			if(data!=null && data!=""){
				if(data.state && data.message=="success"){
					if(!_.isEmpty(data.result.cannotDel)){
						closeLayer(delLayer);
						$(".g-delete-dialog.alert .word").html(""+data.result.cannotDel+"已经被占用，无法删除！");
						multiLayer = layer.open({
							type: 1,
							shade: false,
							title: false, //不显示标题
							scrollbar :false,
							closeBtn :0,
							shade:0.5,
							shadeClose :false,
							area:["400px"],
							offset :["195px"],
							content: $('.g-delete-dialog.alert')
						});				
					}else{
						showMsg({
							timeout : 1000,
							showText : '删除成功',
							status : true
						});
					}
				}else{
// 					showMsg({
// 						timeout : 3000,
// 						showText : "删除失败,"+data.message,
// 						status : false
// 					});
					layer.alert(msg);
				}
			}
		}).always(function(){
			$delete.removeClass("disabled");
			refreshCurrent();
			closeLayer(delLayer);
		});
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


// 	$(".increase,.edit").on("click",function(){
// 		$(".form-dialog-fix").fadeIn(100);
// 		$(".form-dialog-fix").find(".form-dialog").animate({
// 			"right":0
// 		});
// 		$("body").css("overflow","hidden");
// 	});
	
	<%-- 新增按钮操作 --%>
	$("div.page-title-bar").on("click",".increase",function(){
		$("div.title span").html("新增配置");
		
		clearRunTempData();
		$(".form-dialog-fix").fadeIn(100);
		$(".form-dialog-fix").find(".form-dialog").animate({
			"right":0
		});
		$("body").css("overflow","hidden");
	});
	
	<%-- 编辑按钮 --%>
	$("tbody").on("click",".edit",function(){
		$("div.title span").html("修改配置");
		
		$(".form-dialog-fix.w-900").fadeIn(100);
		$(".form-dialog-fix.w-900").find(".form-dialog").animate({
			"right":0
		});
		var idx = $(this).attr("data-id");
		
		<%-- 清空原来模板的数据 --%>
		clearRunTempData();
		<%-- 打开第一项 --%>
		$("#run_left_tab li").eq(0).trigger("click");
		if(!_.isEmpty(allRunTempList[idx])){
			loadAllRunTempData(allRunTempList[idx]);
		}
	});

	$(".form-dialog .form-wrap  .btn").on("click",function(){
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

	$(".left-tab").on("click","li",function(){
		var thisIndex = $(this).index();
		$(this).addClass("active").siblings("li").removeClass("active");
		
		$(".right-content").hide().eq(thisIndex).show();
	});
	
	
	
	
	<%-- 保存按钮 --%>
	$(".g-submit2").on("click",function(){
		startSaving();
		if(!checkData()){
			$("#run_left_tab li").eq(0).trigger("click");
			endSaving(1);
			return;
		}
		
		var tpl_idx = $("#tpl_idx").val();
		
		runTempSubmit = {};
		runTempSubmit.device_tpl_ID = $("#run_id").val();
		runTempSubmit.device_tpl_desc = $("#run_desc").val();
		runTempSubmit.device_type = $("#deviceType_edit").val();
		runTempSubmit = saveRunTemp();
		if(runTempSubmit==false){
			endSaving(1);
			return;
		}
		if(!_.isEmpty(tpl_idx)){
			<%-- 不为空，编辑，判断是否跟修改前相等，如果相等则不保存--%>
			runTempSubmit.device_tpl_idx = $("#tpl_idx").val();
			<%-- 比较ExtTemp模板 --%>
			var flag = compareRunTemp(allRunTempList[tpl_idx], runTempSubmit);
			if(flag){
				//直接提示保存成功
				showMsg({
					timeout : 1000,
					showText : '保存成功',
					status : true,
					callback:function(){
						endSaving(1);
						var thisDom = $(".g-submit2");
						/*应该是成功的时候收回吧，酌情处理*/
						var thisRight =  thisDom.parents(".form-dialog-fix.w-900").find(".form-dialog").attr("date-right");

						thisDom.parents(".form-dialog-fix.w-900").find(".form-dialog").animate({
							"right":thisRight
						},function(){
							thisDom.parents(".form-dialog-fix.w-900").fadeOut(100);
						});
					}
				});
			}else{
				//更新到数据库
				uploadRunTempData(runTempSubmit,"update");
			}
		}else{
			<%-- 不为空，新增 --%>
			//提交数据保存到数据库
			uploadRunTempData(runTempSubmit,"add");
		}
		
		
		
		
		<%--
		var thisDom = $(this);
		if(true){
			showMsg({
				timeout : 1000,
				showText : '添加配置成功',
				status : true,
				callback:function(){
					/*应该是成功的时候收回吧，酌情处理*/
					var thisRight =  thisDom.parents(".form-dialog-fix").find(".form-dialog").attr("date-right");

					thisDom.parents(".form-dialog-fix").find(".form-dialog").animate({
						"right":thisRight
					},function(){
						thisDom.parents(".form-dialog-fix").fadeOut(100);
					});
				}

			});
		}
		--%>
		/*
		提交成功  showMsg("添加配置成功",1000,true);
		提交失败  showMsg("添加配置失败",1000,false);
		 */
		
	});


});
function showMsg(option){
	var defaults = {
		timeout : option.timeout || 1000,
		showText : option.showText || '添加配置成功',
		backgroundColor : option.status === true ? "#2ab65b" :"#ff3d3d",
		callback:function(){

		}
	};
	defaults = $.extend(defaults,option);
	layer.msg(defaults.showText, {
		area:["240px"],
		offset:["110px"],
	  	time: defaults.timeout,
	  	success: function(layero, index){
	  	    $(".layui-layer-hui").css("background-color",defaults.backgroundColor);
	  	}
	}, function(){
		defaults.callback();
	});  
}



//组装 翻页和查询 参数
function makeQueryParam(page,pageSize){
	var keyword=$("#keyword").val();
	var type=$("#deviceType").val();
	pageSize = $("#showSize").val();
	var Page ={
		"page":page,
		"pageSize":pageSize,
		"deviceType":type,
		"keyword":keyword
	};
	return Page;
};

//下一页操作
$("div.paging-bar").on("click",".next-page",function(){
	var currentpage = $("#page").find("li.active").html();//当前页
	var cpage = Number(currentpage) + 1;//下一页
	var Page = makeQueryParam(cpage, pageSize);
	getRunTempList(Page);
});

//上一页操作
$("div.paging-bar").on("click",".prev-page",function(){
	var currentpage = $("#page").find("li.active").html();
	var page=Number(currentpage)-1;
	var Page=makeQueryParam(page, pageSize);
	//带参数
	getRunTempList(Page);
});

//点击某一页
$("div.paging-bar").on("click","li",function(){
	if($(this).hasClass("active")) return;
	var page = $(this).html();
	if(page=="...") return;	
	var Page=makeQueryParam(page, pageSize);
	getRunTempList(Page);
});

<%-- 选择显示页码 --%>
$("div.paging-bar").on("change",".refresh select",function(){
	pageSize = $(this).val();
	var Page=makeQueryParam(1, pageSize);
	getRunTempList(Page);
});

//刷新当前页
function refreshCurrent(){
	var currentpage = $("#page").find("li.active").html();
	var Page=makeQueryParam(currentpage, pageSize);
	//带参数
	getRunTempList(Page);
}


<%-- 查询按钮 --%>
$("div.btn.search").off("click").on("click",function(){
	page = makeQueryParam(1,pageSize)
	getRunTempList(page);
});

</script>

<%-- 自定义脚本 --%>
<script type="text/javascript">
<%--字符串转JSON格式--%>
function jsonToObj(str){ 
	try{
		return JSON.parse(str); 
	}catch(e){
		console.error("jsonToObj error",e);
		console.error("json str",str);
		return "";
	}
} 

<%-- 数据校验 --%>
function checkData(){
	var device_tpl_ID = $("#run_id").val();
	var device_tpl_desc = $("#run_desc").val();
	var device_tpl_type = $("#deviceType_edit").val();
	
	var flag = true;
	
	if(_.isEmpty(device_tpl_ID)){
		$("#run_id").closest("li").addClass("error");
		flag = false;
	}else{
		$("#run_id").closest("li").removeClass("error");
	}
	
	if(_.isEmpty(device_tpl_desc)){
		$("#run_desc").closest("li").addClass("error");
		flag = false;
	}else{
		$("#run_desc").closest("li").removeClass("error");
	}
	
	if(_.isEmpty(device_tpl_type) || device_tpl_type == "-1"){
		$("#deviceType_edit").closest("li").addClass("error");
		flag = false;
	}else{
		$("#deviceType_edit").closest("li").removeClass("error");
	}
	return flag;
}

var pageSize=10;
var Page={"page":1,"pageSize":pageSize,};

var runTempList = {}; <%-- 保存当前页面获取到的外设list --%>
var param = {}; <%-- 保存查询条件参数 --%>
var allRunTempList = {}; <%-- 保存页面所有的每一条模板信息  格式为（temp_idx,runTempObj）--%>
var runTempSubmit = {};<%-- 保存要提交到服务器的数据对象 --%>



$(function(){
	getDeviceType();
	initRunConfSetting();
	param = makeQueryParam(1,pageSize);
	getRunTempList(param);
})


<%-- 弃用：打印设置里面的勾选启用功能 --%>
$("#run_right_form").on("change","div[data-type='print'] input[type='checkbox']",function(){
	if($(this).prop("checked")){
		$(this).closest(".left").siblings().find("input,textarea")
		.removeClass("disabled").removeAttr("disabled");
	}else{
		$(this).closest(".left").siblings().find("input,textarea")
		.addClass("disabled").attr("disabled","disabled");
	}
})

<%-- 打印配置的启用禁用事件样式 --%>
function enabledCheckbox(container,flag){
	$container = container;
	
	if(flag){
		$container.prop("checked",true).parents(".g-checkbox").addClass("on")
		
		$container.closest(".left").siblings().find("input,textarea")
		.removeClass("disabled").removeAttr("disabled");
	}else{
		$container.prop("checked",false).parents(".g-checkbox").removeClass("on")
		
		$container.closest(".left").siblings().find("input,textarea")
		.addClass("disabled").attr("disabled","disabled");
	}
}

function clearRunTempData(){
	<%-- 清除错误消息的提示 --%>
	$("li.error").removeClass("error");
	<%-- 打开第一项 --%>
	$("#run_left_tab li").eq(0).trigger("click");
	$("#tpl_idx").val("");
	<%-- 所有的文本框清空 --%>
	$("#run_right_form input[type='text']").val("");
	$("#run_right_form input[type='password']").val("");
	$("#run_right_form textarea").val("");
	
	<%-- 选择框默认选中第一个 --%>
	$("#run_right_form select").prop("selectedIndex", "0"); 
	<%-- 复选框默认不勾选 --%>
// 	$("#run_right_form input[type='checkbox']").prop("checked",false).trigger("change");
	$("#run_right_form input[type='checkbox']").prop("checked",false).parents(".g-checkbox").removeClass("on");
	
	<%-- 记录日志：不记录 --%>
	$("input[name='runlog_level'][value='none']").trigger("change");
	<%-- 记录方式：文件 --%>
	$("#file").prop("checked",true).parents(".g-checkbox").addClass("on");
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
	<%-- 打印配置的复选框触发事件 --%>
// 	$("div[data-type='print'] input[type='checkbox']").trigger("change");
	$("div[data-type='print'] input[type='checkbox']").each(function(){
		enabledCheckbox($(this), false);
	});
}

<%--获取所有设备类型--%>
function getDeviceType(){
	$.ajax({
		url:"<%=basePath%>deviceext/getDeviceTypes",
		type:"POST",
		data:""
	}).done(function(data){
		if(data!=null && data.state){
			var list = data.result;
			var html = "<option value=\"-1\" selected>选择设备类型</option>";
			//deviceType
			for(var i=0;i<list.length;i++){
				html += "<option value=\""+list[i].meta_devicetype_idx+"\">"+list[i].device_type_desc+"</option>"
			}
			$("#deviceType").html(html);
			<%-- 编译以及新增时的设备类型下拉框 --%>
			$("#deviceType_edit").html(html);
			
		}else{
			console.log(data.message)
		}
	});
}

<%--获运行配置模板 --%>
function getRunTempList(param){
		startLoading();
		$(".g-table tbody").html("");
		$(".col1 :checkbox").prop("checked",false).trigger("change");
		$.ajax({
			url:"<%=basePath%>devicerun/getRunTempListByParam",
			type:"POST",
			timeout:8000,
			data:{json:JSON.stringify(param)}
		}).done(function(data){
			if(data!=null && data.state && data.result!=null){
				if(data.result.rows!=null && data.result.rows.length >0){
					runTempList = data.result.rows;
					console.log("runTempList",runTempList);
					<%-- 分页 --%>
					$.pagination(data.result);
					var html = "";
					var perNum=0;
					for(var i=0;i<runTempList.length;i++){
						var item = runTempList[i];
						allRunTempList[item.device_tpl_idx] = item;
						html += "<tr><td class=\"col1\"><div class=\"g-checkbox\">";
						html += "<input type=\"checkbox\" name=\"\" id=\""+item.device_tpl_idx+"\" data-name=\""+item.device_tpl_ID+"\"/></div></td>";
						html += "<td>"+item.device_tpl_ID+"</td>";
						html += "<td>"+item.device_tpl_desc+"</td>";
						html += "<td>"+item.device_type_desc+"</td>";
						var doc = "";
						if(!_.isEmpty(item.content)){
							//doc = "......";
							var contents=item.content;
							console.log("contents",contents);
							for(var j=0;j<contents.length;j++){
								var jsonContent=contents[j];
								var obj=jsonToObj(jsonContent);
								if(obj){
									//var type=obj.TYPE;
									var privacy=obj.PRIVACY;
									var runlog_level=obj.RUNLOG_LEVEL;
									var language=obj.LANGUAGE;
									
									//if(type){
									if(language){
										doc+="语言版本：";
										//for(var k=0;k<type.length;k++){
										//	var lang=type[k];
										//	doc+=lang.remarks+"、";
										//}
										for(var k=0;k<language.length;k++){
											var langIndex=language[k];
											if(langIndex==1){
												doc+="简体中文、";
											}else if(langIndex==2){
												doc+="繁体中文、";
											}else if(langIndex==3){
												doc+="英语、";
											}else if(langIndex==4){
												doc+="韩语、";
											}else if(langIndex==5){
												doc+="日语、";
											}
										}
										doc=doc.substring(0,doc.length-1)+"<br/>";
										doc+=privacy=="1"?"隐私保护：有<br/>":"隐私保护：无<br/>";
									}else if(runlog_level){
										doc+="运行日志级别："+obj.RUNLOG_LEVEL+"，";
										doc+="保存方式："+obj.RUNLOG_TYPE+"，";
										doc+="文件大小："+obj.RUNLOG_FILESIZE+"<br/>";
									}
								}
							}
							
						}
						//html += "<td>"+item.content+doc+"</td>";
						html += "<td>"+doc+"</td>";
						<shiro:hasPermission name="0102020703">
							html += "<td><span class=\"btn-a edit\" data-id=\""+item.device_tpl_idx+"\">编辑</span>";
							perNum++;
						</shiro:hasPermission>
						<shiro:hasPermission name="0102020702">
							html += "<span class=\"btn-a delete\" data-id=\""+item.device_tpl_idx+"\" data-name=\""+item.device_tpl_ID+"\">删除</span></td></tr>";
							perNum++;
						</shiro:hasPermission>
					}
					$(".g-table tbody").html(html);
					if(perNum==0){
						$(".col6").attr("style","display:none;");
					}else{
						$(".col6").attr("style","");
					}
				}
			}
		}).always(function(data,status){
			if(status=="timeout"){
				layer.alert("获取信息超时，请重试！");
			}
			<%-- 任何情况下都执行 --%>
			endLoading(1);
		});
}

function loadAllRunTempData(runObj){
	if($("#runTemp").val()!="-1"){
		if(!_.isEmpty(runObj)){
			<%-- 设备类型 --%>
			$("#deviceType_edit").val(runObj.device_type);
			$("#run_id").val(runObj.device_tpl_ID);
			$("#run_desc").val(runObj.device_tpl_desc);
			$("#tpl_idx").val(runObj.device_tpl_idx);
			
			if(!_.isEmpty(runObj.runDetailList)){
				var list = runObj.runDetailList;
				for(var i=0;i<list.length;i++){
					var item = list[i];
					var conf_type = item.run_conf_type;
					var conf_value = {};
					if(typeof item.run_conf_value == "object"){
						conf_value = item.run_conf_value;
					}else{
						conf_value = jsonToObj(item.run_conf_value);
					}
					console.log("conf_type",conf_type,"conf_value",conf_value);
					<%-- 语言配置 --%>
					if(conf_type=="language_config"){
						//$("#basic_language").val(conf_value.LANGUAGE);
						//continue;
						var types=conf_value.TYPE;
						var privacy=conf_value.PRIVACY;
						if(types){//
							for(var k=0;k<types.length;k++){
								var type=types[k];
								//var remarks=type.remarks;
								var language=type.language;
								if(language){
									var id="input#"+language;
									if(!$(id).is(":checked")){
										$(id).trigger("click");
									}
								}
							}						
						}
						if(privacy=="1"){
							if(!$("#private_hide").is(":checked")){
								$("#private_hide").trigger("click");
							}
						}else if($("#private_hide").is(":checked")){
							$("#private_hide").trigger("click");
						}
						continue;
					}
					
					
					<%-- 运行日志配置 --%>
					if(conf_type=="runlog_config"){
						$("input[name='runlog_level'][value='"+conf_value.RUNLOG_LEVEL+"']").trigger("change");
						if(conf_value.RUNLOG_TYPE.indexOf("file")>-1){
// 							$("#file").prop("checked",true).trigger("change");
							$("#file").prop("checked",true).parents(".g-checkbox").addClass("on");
						}else{
// 							$("#file").prop("checked",false).trigger("change");
							$("#file").prop("checked",false).parents(".g-checkbox").removeClass("on");
						}
						
						if(conf_value.RUNLOG_TYPE.indexOf("db")>-1){
// 							$("#db").prop("checked",true).trigger("change");
							$("#db").prop("checked",true).parents(".g-checkbox").addClass("on");
						}else{
// 							$("#db").prop("checked",false).trigger("change");
							$("#db").prop("checked",false).parents(".g-checkbox").removeClass("on");
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
// 									$("#function_"+keys[j].toLowerCase()).prop("checked",true).trigger("change");
									$("#function_"+keys[j].toLowerCase()).prop("checked",true).parents(".g-checkbox").addClass("on");
								}else{
// 									$("#function_"+keys[j].toLowerCase()).prop("checked",false).trigger("change");
									$("#function_"+keys[j].toLowerCase()).prop("checked",false).parents(".g-checkbox").removeClass("on");
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
// 									$("#card_"+keys[j].toLowerCase()).prop("checked",true).trigger("change");
									$("#card_"+keys[j].toLowerCase()).prop("checked",true).parents(".g-checkbox").addClass("on");
								}else{
// 									$("#card_"+keys[j].toLowerCase()).prop("checked",false).trigger("change");
									$("#card_"+keys[j].toLowerCase()).prop("checked",false).parents(".g-checkbox").removeClass("on");
								}
							}
						}
						
						if(!_.isEmpty(conf_value.BOOKINPUT)){
							var book = conf_value.BOOKINPUT;
							var keys = _.keys(book);
							for(var j=0;j<keys.length;j++){
								if(book[keys[j]]=="1"){
// 									$("#book_"+keys[j].toLowerCase()).prop("checked",true).trigger("change");
									$("#book_"+keys[j].toLowerCase()).prop("checked",true).parents(".g-checkbox").addClass("on");
								}else{
// 									$("#book_"+keys[j].toLowerCase()).prop("checked",false).trigger("change");
									$("#book_"+keys[j].toLowerCase()).prop("checked",false).parents(".g-checkbox").removeClass("on");
								}
							}
						}
						continue;
					}
					
					<%-- 超时配置 --%>
					if(conf_type=="timeout_config"){
						if(conf_value.DISPLAY=="1"){
// 							$("#display").prop("checked",true).trigger("change");
							$("#display").prop("checked",true).parents(".g-checkbox").addClass("on");
						}else{
// 							$("#display").prop("checked",false).trigger("change");
							$("#display").prop("checked",false).parents(".g-checkbox").removeClass("on");
						}
						if(conf_value.PLAYSOUND=="1"){
// 							$("#playsound").prop("checked",true).trigger("change");
							$("#playsound").prop("checked",true).parents(".g-checkbox").addClass("on");
						}else{
// 							$("#playsound").prop("checked",false).trigger("change");
							$("#playsound").prop("checked",false).parents(".g-checkbox").removeClass("on");
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
// 								$("#switchscreen_enable").prop("checked",true).trigger("change");
								$("#switchscreen_enable").prop("checked",true).parents(".g-checkbox").addClass("on");
							}else{
// 								$("#switchscreen_enable").prop("checked",false).trigger("change");
								$("#switchscreen_enable").prop("checked",false).parents(".g-checkbox").removeClass("on");
							}
							$("#switchscreen_cycle").val(scre.CYCLE.replace(/[a-zA-Z]*/g,""));
							$("#switchscreen_waittime").val(scre.WAITTIME.replace(/[a-zA-Z]*/g,""));
						}
						continue;
					}
					
					<%-- 定时任务配置 --%>
					if(conf_type=="scheduletask_config"){
						if(conf_value.ENABLEWEEKSCHEDLER=="1"){
// 							$("#enableweekschedler").prop("checked",true).trigger("change");
							$("#enableweekschedler").prop("checked",true).parents(".g-checkbox").addClass("on");
						}else{
// 							$("#enableweekschedler").prop("checked",false).trigger("change");
							$("#enableweekschedler").prop("checked",false).parents(".g-checkbox").removeClass("on");
						}
						if(conf_value.ENABLEBALANCE=="1"){
// 							$("#enablebalance").prop("checked",true).trigger("change");
							$("#enablebalance").prop("checked",true).parents(".g-checkbox").addClass("on");
							var time = "";
							if(!_.isEmpty(conf_value.BALANCETIME) && conf_value.BALANCETIME.length >= 6){
								time += conf_value.BALANCETIME.substring(0,2) + ":";
								time += conf_value.BALANCETIME.substring(2,4) + ":";
								time += conf_value.BALANCETIME.substring(4);
							}
							$("#balancetime").val(time);
						}else{
// 							$("#enablebalance").prop("checked",false).trigger("change");
							$("#enablebalance").prop("checked",false).parents(".g-checkbox").removeClass("on");
							$("#balancetime").val("");
						}
						
						if(conf_value.ENABLEREBOOT=="1"){
// 							$("#enablereboot").prop("checked",true).trigger("change");
							$("#enablereboot").prop("checked",true).parents(".g-checkbox").addClass("on");
							var time = "";
							if(!_.isEmpty(conf_value.REBOOTTIME) && conf_value.REBOOTTIME.length >= 6){
								time += conf_value.REBOOTTIME.substring(0,2) + ":";
								time += conf_value.REBOOTTIME.substring(2,4) + ":";
								time += conf_value.REBOOTTIME.substring(4);
							}
							$("#reboottime").val(time);
						}else{
// 							$("#enablereboot").prop("checked",false).trigger("change");
							$("#enablereboot").prop("checked",false).parents(".g-checkbox").removeClass("on");
							$("#reboottime").val("");
						}
						
						if(conf_value.ENABLESHUTDOWN=="1"){
// 							$("#enableshutdown").prop("checked",true).trigger("change");
							$("#enableshutdown").prop("checked",true).parents(".g-checkbox").addClass("on");
							var time = "";
							if(!_.isEmpty(conf_value.SHUTDOWNTIME) && conf_value.SHUTDOWNTIME.length >= 6){
								time += conf_value.SHUTDOWNTIME.substring(0,2) + ":";
								time += conf_value.SHUTDOWNTIME.substring(2,4) + ":";
								time += conf_value.SHUTDOWNTIME.substring(4);
							}
							$("#shutdowntime").val(time);
						}else{
// 							$("#enableshutdown").prop("checked",false).trigger("change");
							$("#enableshutdown").prop("checked",false).parents(".g-checkbox").removeClass("on");
							$("#shutdowntime").val("");
						}
						
						var week = ["MON","TUE","WED","THU","FRI","SAT","SUN"];
						for(var j=0;j<week.length;j++){
							if( !_.isEmpty( conf_value[week[j]] ) ){
								var thisday = conf_value[week[j]];
								if(thisday.ENABLECLOSE == "1"){
// 									$("#"+week[j].toLowerCase()+"_"+"enableclose").prop("checked",true).trigger("change");
									$("#"+week[j].toLowerCase()+"_"+"enableclose").prop("checked",true).parents(".g-checkbox").addClass("on");
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
// 									$("#"+week[j].toLowerCase()+"_"+"enableclose").prop("checked",false).trigger("change");
									$("#"+week[j].toLowerCase()+"_"+"enableclose").prop("checked",false).parents(".g-checkbox").removeClass("on");
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
// 									$("#"+thistype+"_"+"timeout").prop("checked",true).trigger("change");
									$("#"+thistype+"_"+"timeout").prop("checked",true).parents(".g-checkbox").addClass("on");
								}else{
// 									$("#"+thistype+"_"+"timeout").prop("checked",false).trigger("change");
									$("#"+thistype+"_"+"timeout").prop("checked",false).parents(".g-checkbox").removeClass("on");
								}
								
								<%-- 打印日期描述，时间描述，凭证头，凭证尾 --%>
								$("#"+thistype+"_"+"date").val(thisobj.DATE);
								$("#"+thistype+"_"+"time").val(thisobj.TIME);
								$("#"+thistype+"_"+"head").val(thisobj.HEAD);
								$("#"+thistype+"_"+"tail").val(thisobj.TAIL);
								
								<%-- 还回凭证 --%>
								if(thistype == "checkin"){
									//$("#"+thistype+"_"+"barcode").val(thisobj.BARCODE);
									//$("#"+thistype+"_"+"title").val(thisobj.TITLE);
									if(thisobj.DATE=="1"){
										enabledCheckbox($("#"+thistype+"_"+"date_checkbox"),true);
									}else{
										enabledCheckbox($("#"+thistype+"_"+"date_checkbox"),false);
									}
									if(thisobj.TIME=="1"){
										enabledCheckbox($("#"+thistype+"_"+"time_checkbox"),true);
									}else{
										enabledCheckbox($("#"+thistype+"_"+"time_checkbox"),false);
									}
									if(thisobj.PATRON=="1"){
										enabledCheckbox($("#"+thistype+"_"+"patron_checkbox"),true);
									}else{
										enabledCheckbox($("#"+thistype+"_"+"patron_checkbox"),false);
									}
									if(thisobj.ENABLEAMOUNT == "1"){
										enabledCheckbox($("#"+thistype+"_"+"enableamount_checkbox"), true);
// 										$("#"+thistype+"_"+"enableamount").prop("checked",true).trigger("change");
// 										$("#"+thistype+"_"+"enableamount").prop("checked",true).parents(".g-checkbox").addClass("on");
										//$("#"+thistype+"_"+"amount").val(thisobj.AMOUNT);
									}else{
										enabledCheckbox($("#"+thistype+"_"+"enableamount_checkbox"), false);
// 										$("#"+thistype+"_"+"enableamount").prop("checked",false).trigger("change");
// 										$("#"+thistype+"_"+"enableamount").prop("checked",false).parents(".g-checkbox").removeClass("on");
										//$("#"+thistype+"_"+"amount").val("");
									}
									
									if(thisobj.ENABLECOMMENT == "1"){
										enabledCheckbox($("#"+thistype+"_"+"enablecomment"),true);
// 										$("#"+thistype+"_"+"enablecomment").prop("checked",true).trigger("change");
// 										$("#"+thistype+"_"+"enablecomment").prop("checked",true).parents(".g-checkbox").addClass("on");
										$("#"+thistype+"_"+"comment").val(thisobj.COMMENT);
									}else{
										enabledCheckbox($("#"+thistype+"_"+"enablecomment"),false);
// 										$("#"+thistype+"_"+"enablecomment").prop("checked",false).trigger("change");
// 										$("#"+thistype+"_"+"enablecomment").prop("checked",false).parents(".g-checkbox").removeClass("on");
										$("#"+thistype+"_"+"comment").val("");
									}
								}
								<%-- 借出凭证 --%>
								if(thistype == "checkout") {
									//$("#"+thistype+"_"+"patron").val(thisobj.PATRON);
									//$("#"+thistype+"_"+"barcode").val(thisobj.BARCODE);
									//$("#"+thistype+"_"+"title").val(thisobj.TITLE);
									//$("#"+thistype+"_"+"duedate").val(thisobj.DUEDATE);
									if(thisobj.DATE=="1"){
										enabledCheckbox($("#"+thistype+"_"+"date_checkbox"),true);
									}else{
										enabledCheckbox($("#"+thistype+"_"+"date_checkbox"),false);
									}
									if(thisobj.TIME=="1"){
										enabledCheckbox($("#"+thistype+"_"+"time_checkbox"),true);
									}else{
										enabledCheckbox($("#"+thistype+"_"+"time_checkbox"),false);
									}
									if(thisobj.PATRON=="1"){
										enabledCheckbox($("#"+thistype+"_"+"patron_checkbox"),true);
									}else{
										enabledCheckbox($("#"+thistype+"_"+"patron_checkbox"),false);
									}
									if(thisobj.ENABLENAME == "1"){
										enabledCheckbox($("#"+thistype+"_"+"enablename_checkbox"),true);
// 										$("#"+thistype+"_"+"enablename").prop("checked",true).trigger("change");
// 										$("#"+thistype+"_"+"enablename").prop("checked",true).parents(".g-checkbox").addClass("on");
										//$("#"+thistype+"_"+"name").val(thisobj.NAME);
									}else{
										enabledCheckbox($("#"+thistype+"_"+"enablename_checkbox"),false);
// 										$("#"+thistype+"_"+"enablename").prop("checked",false).trigger("change");
// 										$("#"+thistype+"_"+"enablename").prop("checked",false).parents(".g-checkbox").removeClass("on");
										//$("#"+thistype+"_"+"name").val("");
									}
									if(thisobj.ENABLEAMOUNT == "1"){
										enabledCheckbox($("#"+thistype+"_"+"enableamount_checkbox"),true);
// 										$("#"+thistype+"_"+"enableamount").prop("checked",true).trigger("change");
// 										$("#"+thistype+"_"+"enableamount").prop("checked",true).parents(".g-checkbox").addClass("on");
										//$("#"+thistype+"_"+"amount").val(thisobj.AMOUNT);
									}else{
										enabledCheckbox($("#"+thistype+"_"+"enableamount_checkbox"),false);
// 										$("#"+thistype+"_"+"enableamount").prop("checked",false).trigger("change");
// 										$("#"+thistype+"_"+"enableamount").prop("checked",false).parents(".g-checkbox").removeClass("on");
										//$("#"+thistype+"_"+"amount").val("");
									}
									if(thisobj.ENABLEFEE == "1"){
										enabledCheckbox($("#"+thistype+"_"+"enablefee_checkbox"),true);
// 										$("#"+thistype+"_"+"enablefee").prop("checked",true).trigger("change");
// 										$("#"+thistype+"_"+"enablefee").prop("checked",true).parents(".g-checkbox").addClass("on");
										//$("#"+thistype+"_"+"fee").val(thisobj.FEE);
									}else{
										enabledCheckbox($("#"+thistype+"_"+"enablefee_checkbox"),false);
// 										$("#"+thistype+"_"+"enablefee").prop("checked",false).trigger("change");
// 										$("#"+thistype+"_"+"enablefee").prop("checked",false).parents(".g-checkbox").removeClass("on");
										//$("#"+thistype+"_"+"fee").val("");
									}
								}
								<%-- 续借凭证 --%>
								if(thistype == "renew") {
									<%-- 续借书描述  --%>
									//$("#"+thistype+"_"+"patron").val(thisobj.PATRON);
									//$("#"+thistype+"_"+"barcode").val(thisobj.BARCODE);
									//$("#"+thistype+"_"+"title").val(thisobj.TITLE);
									//$("#"+thistype+"_"+"duedate").val(thisobj.DUEDATE);
									if(thisobj.DATE=="1"){
										enabledCheckbox($("#"+thistype+"_"+"date_checkbox"),true);
									}else{
										enabledCheckbox($("#"+thistype+"_"+"date_checkbox"),false);
									}
									if(thisobj.TIME=="1"){
										enabledCheckbox($("#"+thistype+"_"+"time_checkbox"),true);
									}else{
										enabledCheckbox($("#"+thistype+"_"+"time_checkbox"),false);
									}
									if(thisobj.PATRON=="1"){
										enabledCheckbox($("#"+thistype+"_"+"patron_checkbox"),true);
									}else{
										enabledCheckbox($("#"+thistype+"_"+"patron_checkbox"),false);
									}
									if(thisobj.ENABLERENEWBIBLIO == "1"){
										enabledCheckbox($("#"+thistype+"_"+"enablerenewbiblio"),true);
// 										$("#"+thistype+"_"+"enablerenewbiblio").prop("checked",true).trigger("change");
// 										$("#"+thistype+"_"+"enablerenewbiblio").prop("checked",true).parents(".g-checkbox").addClass("on");
										$("#"+thistype+"_"+"renewbiblio").val(thisobj.RENEWBIBLIO);
									}else{
										enabledCheckbox($("#"+thistype+"_"+"enablerenewbiblio"),false);
// 										$("#"+thistype+"_"+"enablerenewbiblio").prop("checked",false).trigger("change");
// 										$("#"+thistype+"_"+"enablerenewbiblio").prop("checked",false).parents(".g-checkbox").removeClass("on");
										$("#"+thistype+"_"+"renewbiblio").val("");
									}
									<%-- 续借数描述 --%>
									if(thisobj.ENABLERENEWCOUNT == "1"){
										enabledCheckbox($("#"+thistype+"_"+"enablerenewcount_checkbox"),true);
// 										$("#"+thistype+"_"+"enablerenewcount").prop("checked",true).trigger("change");
// 										$("#"+thistype+"_"+"enablerenewcount").prop("checked",true).parents(".g-checkbox").addClass("on");
										//$("#"+thistype+"_"+"renewcount").val(thisobj.RENEWCOUNT);
									}else{
										enabledCheckbox($("#"+thistype+"_"+"enablerenewcount_checkbox"),false);
// 										$("#"+thistype+"_"+"enablerenewcount").prop("checked",false).trigger("change");
// 										$("#"+thistype+"_"+"enablerenewcount").prop("checked",false).parents(".g-checkbox").removeClass("on");
										//$("#"+thistype+"_"+"renewcount").val("");
									}
									<%-- 未续借书描述 --%>
									if(thisobj.ENABLEUNRENEWBIBLIO == "1"){
										enabledCheckbox($("#"+thistype+"_"+"enableunrenewbiblio"),true);
// 										$("#"+thistype+"_"+"enableunrenewbiblio").prop("checked",true).trigger("change");
// 										$("#"+thistype+"_"+"enableunrenewbiblio").prop("checked",true).parents(".g-checkbox").addClass("on");
										$("#"+thistype+"_"+"unrenewbiblio").val(thisobj.UNRENEWBIBLIO);
									}else{
										enabledCheckbox($("#"+thistype+"_"+"enableunrenewbiblio"),false);
// 										$("#"+thistype+"_"+"enableunrenewbiblio").prop("checked",false).trigger("change");
// 										$("#"+thistype+"_"+"enableunrenewbiblio").prop("checked",false).parents(".g-checkbox").removeClass("on");
										$("#"+thistype+"_"+"unrenewbiblio").val("");
									}
									<%-- 未续借数描述 --%>
									if(thisobj.ENABLEUNRENEWCOUNT == "1"){
										enabledCheckbox($("#"+thistype+"_"+"enableunrenewcount_checkbox"),true);
// 										$("#"+thistype+"_"+"enableunrenewcount").prop("checked",true).trigger("change");
// 										$("#"+thistype+"_"+"enableunrenewcount").prop("checked",true).parents(".g-checkbox").addClass("on");
										//$("#"+thistype+"_"+"unrenewcount").val(thisobj.UNRENEWCOUNT);
									}else{
										enabledCheckbox($("#"+thistype+"_"+"enableunrenewcount_checkbox"),false);
// 										$("#"+thistype+"_"+"enableunrenewcount").prop("checked",false).trigger("change");
// 										$("#"+thistype+"_"+"enableunrenewcount").prop("checked",false).parents(".g-checkbox").removeClass("on");
										//$("#"+thistype+"_"+"unrenewcount").val("");
									}
								}
								<%-- 收款打印凭证 --%>
								if(thistype == "pay") {
									$("#"+thistype+"_"+"patron").val(thisobj.PATRON);
									$("#"+thistype+"_"+"fee").val(thisobj.FEE);
									
									if(thisobj.ENABLENAME == "1"){
										enabledCheckbox($("#"+thistype+"_"+"enablename"),true);
// 										$("#"+thistype+"_"+"enablename").prop("checked",true).trigger("change");
// 										$("#"+thistype+"_"+"enablename").prop("checked",true).parents(".g-checkbox").addClass("on");
										$("#"+thistype+"_"+"name").val(thisobj.NAME);
									}else{
										enabledCheckbox($("#"+thistype+"_"+"enablename"),false);
// 										$("#"+thistype+"_"+"enablename").prop("checked",false).trigger("change");
// 										$("#"+thistype+"_"+"enablename").prop("checked",false).parents(".g-checkbox").removeClass("on");
										$("#"+thistype+"_"+"name").val("");
									}
									
									if(thisobj.ENABLEIDCARD == "1"){
										enabledCheckbox($("#"+thistype+"_"+"enableidcard"),true);
// 										$("#"+thistype+"_"+"enableidcard").prop("checked",true).trigger("change");
// 										$("#"+thistype+"_"+"enableidcard").prop("checked",true).parents(".g-checkbox").addClass("on");
										$("#"+thistype+"_"+"idcard").val(thisobj.IDCARD);
									}else{
										enabledCheckbox($("#"+thistype+"_"+"enableidcard"),false);
// 										$("#"+thistype+"_"+"enableidcard").prop("checked",false).trigger("change");
// 										$("#"+thistype+"_"+"enableidcard").prop("checked",false).parents(".g-checkbox").removeClass("on");
										$("#"+thistype+"_"+"idcard").val("");
									}
									
									if(thisobj.ENABLECARDTYPE == "1"){
										enabledCheckbox($("#"+thistype+"_"+"enablecardtype"),true);
// 										$("#"+thistype+"_"+"enablecardtype").prop("checked",true).trigger("change");
// 										$("#"+thistype+"_"+"enablecardtype").prop("checked",true).parents(".g-checkbox").addClass("on");
										$("#"+thistype+"_"+"cardtype").val(thisobj.CARDTYPE);
									}else{
										enabledCheckbox($("#"+thistype+"_"+"enablecardtype"),false);
// 										$("#"+thistype+"_"+"enablecardtype").prop("checked",false).trigger("change");
// 										$("#"+thistype+"_"+"enablecardtype").prop("checked",false).parents(".g-checkbox").removeClass("on");
										$("#"+thistype+"_"+"cardtype").val("");
									}
									
									if(thisobj.ENABLEDEDUCTION == "1"){
										enabledCheckbox($("#"+thistype+"_"+"enablededuction"),true);
// 										$("#"+thistype+"_"+"enablededuction").prop("checked",true).trigger("change");
// 										$("#"+thistype+"_"+"enablededuction").prop("checked",true).parents(".g-checkbox").addClass("on");
										$("#"+thistype+"_"+"deduction").val(thisobj.DEDUCTION);
									}else{
										enabledCheckbox($("#"+thistype+"_"+"enablededuction"),false);
// 										$("#"+thistype+"_"+"enablededuction").prop("checked",false).trigger("change");
// 										$("#"+thistype+"_"+"enablededuction").prop("checked",false).parents(".g-checkbox").removeClass("on");
										$("#"+thistype+"_"+"deduction").val("");
									}
									
									if(thisobj.ENABLEDEPOSIT == "1"){
										enabledCheckbox($("#"+thistype+"_"+"enabledeposit"),true);
// 										$("#"+thistype+"_"+"enabledeposit").prop("checked",true).trigger("change");
// 										$("#"+thistype+"_"+"enabledeposit").prop("checked",true).parents(".g-checkbox").addClass("on");
										$("#"+thistype+"_"+"deposit").val(thisobj.DEPOSIT);
									}else{
										enabledCheckbox($("#"+thistype+"_"+"enabledeposit"),false);
// 										$("#"+thistype+"_"+"enabledeposit").prop("checked",false).trigger("change");
// 										$("#"+thistype+"_"+"enabledeposit").prop("checked",false).parents(".g-checkbox").removeClass("on");
										$("#"+thistype+"_"+"deposit").val("");
									}
									
									if(thisobj.ENABLEIMPREST == "1"){
										enabledCheckbox($("#"+thistype+"_"+"enableimprest"),true);
// 										$("#"+thistype+"_"+"enableimprest").prop("checked",true).trigger("change");
// 										$("#"+thistype+"_"+"enableimprest").prop("checked",true).parents(".g-checkbox").addClass("on");
										$("#"+thistype+"_"+"imprest").val(thisobj.IMPREST);
									}else{
										enabledCheckbox($("#"+thistype+"_"+"enabledeposit"),false);
// 										$("#"+thistype+"_"+"enabledeposit").prop("checked",false).trigger("change");
// 										$("#"+thistype+"_"+"enabledeposit").prop("checked",false).parents(".g-checkbox").removeClass("on");
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
// 							$("#register_enableidcard").prop("checked",true).trigger("change");
							$("#register_enableidcard").prop("checked",true).parents(".g-checkbox").addClass("on");
						}else{
// 							$("#register_enableidcard").prop("checked",false).trigger("change");
							$("#register_enableidcard").prop("checked",false).parents(".g-checkbox").removeClass("on");
						}
						
						if(conf_value.ENABLEOTHERCARD == "1"){
// 							$("#register_enableothercard").prop("checked",true).trigger("change");
							$("#register_enableothercard").prop("checked",true).parents(".g-checkbox").addClass("on");
						}else{
// 							$("#register_enableothercard").prop("checked",false).trigger("change");
							$("#register_enableothercard").prop("checked",false).parents(".g-checkbox").removeClass("on");
						}
						
						if(conf_value.ENABLETEMPCARD == "1"){
// 							$("#register_enabletempcard").prop("checked",true).trigger("change");
							$("#register_enabletempcard").prop("checked",true).parents(".g-checkbox").addClass("on");
							$("#register_tempcardid").val(conf_value.TEMPCARDID);
						}else{
// 							$("#register_enabletempcard").prop("checked",false).trigger("change");
							$("#register_enabletempcard").prop("checked",false).parents(".g-checkbox").removeClass("on");
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
// 							$("#ACS_needlogin").prop("checked",true).trigger("change");
							$("#ACS_needlogin").prop("checked",true).parents(".g-checkbox").addClass("on");
						}else{
// 							$("#ACS_needlogin").prop("checked",false).trigger("change");
							$("#ACS_needlogin").prop("checked",false).parents(".g-checkbox").removeClass("on");
						}
						
						if(conf_value.FINANCE=="1"){
							$("#ACS_Search_finance").prop("checked",true).parents(".g-checkbox").addClass("on");
						}else{
							$("#ACS_Search_finance").prop("checked",false).parents(".g-checkbox").removeClass("on");
						}
						
						if(conf_value.DEFAULTDECOLLATOR == "1"){
// 							$("#ACS_defaultdecollator").prop("checked",true).trigger("change");
							$("#ACS_defaultdecollator").prop("checked",true).parents(".g-checkbox").addClass("on");
							$("#ACS_decollator").val(conf_value.DECOLLATOR);
						}else{
// 							$("#ACS_defaultdecollator").prop("checked",false).trigger("change");
							$("#ACS_defaultdecollator").prop("checked",false).parents(".g-checkbox").removeClass("on");
							$("#ACS_decollator").val("");
						}
						
						if(conf_value.ENABLECMD63 == "1"){
// 							$("#ACS_enablecmd63").prop("checked",true).trigger("change");
							$("#ACS_enablecmd63").prop("checked",true).parents(".g-checkbox").addClass("on");
						}else{
// 							$("#ACS_enablecmd63").prop("checked",false).trigger("change");
							$("#ACS_enablecmd63").prop("checked",false).parents(".g-checkbox").removeClass("on");
						}
					}
				}
			}
			
			
		}
	}
}

<%-- 初始化运行配置设置 --%>
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
function saveRunTemp(){
	
	var detailList = [];
	var checkPassed=true;
	$("#run_right_form .right-content").each(function(){
		var obj = {};
		var thistype = $(this).attr("data-type");
		var conftype = "";
		var confvalue = {};
		<%-- 基础配置，language_config、runlog_config --%>
		if(thistype=="basic"){
			//confvalue.LANGUAGE = $("#basic_language").val();
			//{"index":"1","language":"chinese","remarks":"简体中文"},
			//{"index":"2","language":"traditional","remarks":"繁體中文"},
			//{"index":"3","language":"english","remarks":"English"},
			//{"index":"4","language":"korean","remarks":"한국어"},
			//{"index":"5","language":"japanese","remarks":"日本語の"}
			var langArray=[];
			var langDescArray=[];
			$(".basic_language").find("input[type=checkbox]:checked").each(function(index,dom){
				var json=$(dom).attr("json");
				console.log("langjson",json);
				var jsonObj=jsonToObj(json);
				if(jsonObj.index){
					langArray.push(jsonObj.index);
					langDescArray.push(jsonObj);
				}
			});	
			confvalue.LANGUAGE=langArray;
			confvalue.TYPE=langDescArray;
			if($("input#private_hide").is(":checked")){
				confvalue.PRIVACY="1";//隐藏隐私
			}else{
				confvalue.PRIVACY="0";
			}
			obj.run_conf_type = "language_config";
			obj.run_conf_value = confvalue;
			detailList.push(obj);
			
			obj = {};
			confvalue = {};
			confvalue.RUNLOG_LEVEL = $("input[name='runlog_level']:checked").val();
			if($("#file").prop("checked")){
				confvalue.RUNLOG_TYPE = "file";
				var filesize=$("#runlog_filesize").val();
				//check data
				if(!$.isNumeric(filesize)){
					layer.alert("日志文件大小请输入整数");
					checkPassed=false;
					return false;
				}
				confvalue.RUNLOG_FILESIZE = filesize.replace(/[a-zA-Z]*/g,"")+"M";
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
		
		<%-- 
			打印机配置 print_config
			调整 还书凭证 、借书凭证、续借凭证 
		--%>
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
					if(ptype == "checkin"){//还书
						thisobj.DATE=$("#checkin_date_checkbox").is(":checked")?"1":"0";
						thisobj.TIME=$("#checkin_time_checkbox").is(":checked")?"1":"0";
						//thisobj.BARCODE = $("#"+ptype+"_"+"barcode").val();
						//thisobj.TITLE = $("#"+ptype+"_"+"title").val();
						
						if( $("#"+ptype+"_"+"enableamount_checkbox").prop("checked")){
							thisobj.ENABLEAMOUNT = "1";
							//thisobj.AMOUNT = $("#"+ptype+"_"+"amount").val();
							thisobj.AMOUNT="1"
						}else{
							thisobj.ENABLEAMOUNT = "0";
							thisobj.AMOUNT="0"
						}
						/* if( $("#"+ptype+"_"+"enablecomment").prop("checked")){
							thisobj.ENABLECOMMENT = "1";
							thisobj.COMMENT = $("#"+ptype+"_"+"comment").val();
						}else{
							thisobj.ENABLECOMMENT = "0";
							thisobj.COMMENT = "";
						} */
					}
					
					if(ptype == "checkout"){
						//thisobj.BARCODE = $("#"+ptype+"_"+"barcode").val();
						//thisobj.TITLE = $("#"+ptype+"_"+"title").val();
						//thisobj.PATRON = $("#"+ptype+"_"+"patron").val();
						//thisobj.DUEDATE = $("#"+ptype+"_"+"duedate").val();
						
						thisobj.PATRON=$("#"+ptype+"_"+"patron_checkbox").is(":checked")?"1":"0";
						thisobj.DATE=$("#checkout_date_checkbox").is(":checked")?"1":"0";
						thisobj.TIME=$("#checkout_time_checkbox").is(":checked")?"1":"0";
						
						//$("#"+ptype+"_"+"enablename").prop("checked")
						if( $("#"+ptype+"_"+"enablename_checkbox").prop("checked")){
							thisobj.ENABLENAME = "1";
							//thisobj.NAME = $("#"+ptype+"_"+"name").val();
							thisobj.NAME="1";
						}else{
							thisobj.ENABLENAME = "0";
							thisobj.NAME = "0";
						}
						
						if( $("#"+ptype+"_"+"enableamount_checkbox").prop("checked")){
							thisobj.ENABLEAMOUNT = "1";
							//thisobj.AMOUNT = $("#"+ptype+"_"+"amount").val();
							thisobj.AMOUNT="1";
						}else{
							thisobj.ENABLEAMOUNT = "0";
							thisobj.AMOUNT = "0";
						}
						//checkout_enablefee
						if( $("#"+ptype+"_"+"enablefee_checkbox").prop("checked")){
							thisobj.ENABLEFEE = "1";
							//thisobj.FEE = $("#"+ptype+"_"+"fee").val();
							thisobj.FEE="1"
						}else{
							thisobj.ENABLEFEE = "0";
							thisobj.FEE = "0";
						}
						
					}
					if(ptype == "renew"){
						//thisobj.BARCODE = $("#"+ptype+"_"+"barcode").val();
						//thisobj.TITLE = $("#"+ptype+"_"+"title").val();
						//thisobj.PATRON = $("#"+ptype+"_"+"patron").val();
						//thisobj.DUEDATE = $("#"+ptype+"_"+"duedate").val();
						thisobj.PATRON=$("#renew_patron_checkbox").is(":checked")?"1":"0";
						thisobj.DATE=$("#renew_date_checkbox").is(":checked")?"1":"0";
						thisobj.TIME=$("#renew_time_checkbox").is(":checked")?"1":"0";
						/*
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
						*/
						if( $("#"+ptype+"_"+"enablerenewcount_checkbox").prop("checked")){
							thisobj.ENABLERENEWCOUNT = "1";
							//thisobj.RENEWCOUNT= $("#"+ptype+"_"+"renewcount").val();
							thisobj.RENEWCOUNT="1";
						}else{
							thisobj.ENABLERENEWCOUNT = "0";
							thisobj.RENEWCOUNT = "";
						}

						if( $("#"+ptype+"_"+"enableunrenewcount_checkbox").prop("checked")){
							thisobj.ENABLEUNRENEWCOUNT = "1";
							//thisobj.UNRENEWCOUNT = $("#"+ptype+"_"+"unrenewcount").val();
							thisobj.UNRENEWCOUNT="1";
						}else{
							thisobj.ENABLEUNRENEWCOUNT = "0";
							thisobj.UNRENEWCOUNT = "0";
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
			
			if($("#"+thistype+"_"+"Search_finance").prop("checked")){
				confvalue.FINANCE =  "1";
			}else{
				confvalue.FINANCE =  "0";
			}
			
			obj.run_conf_type = conftype;
			obj.run_conf_value = confvalue;
			detailList.push(obj);
		}
	})
	
	runTempSubmit.runDetailList = detailList;
	if(!checkPassed){
		return checkPassed;
	}
	return runTempSubmit;
	
}

<%-- 对比数据，判断是否使用模板 --%>
function compareRunTemp(tempObj,tempSubmit){
	
	if(!_.isEmpty(tempObj) && !_.isEmpty(tempSubmit)){
		
		if(tempObj.device_tpl_ID != tempSubmit.device_tpl_ID ){
			return false;
		}
		if(tempObj.device_tpl_desc != tempSubmit.device_tpl_desc ){
			return false;
		}
		if(tempObj.device_type != tempSubmit.device_type ){
			return false;
		}
		
		var idx = tempObj.device_tpl_idx;
		if(tempObj.runDetailList.length==tempSubmit.runDetailList.length){
			var list1 = tempObj.runDetailList;
			var list2 = tempSubmit.runDetailList;
			
			<%-- 按照类型排序 --%>
			list1 = _.sortBy(list1,"run_conf_type");
			list2 = _.sortBy(list2,"run_conf_type");
			
			for(var i=0;i<list1.length;i++){
				var conf_type1 = list1[i].run_conf_type;
				var conf_type2 = list2[i].run_conf_type;
				
				if(conf_type1!=conf_type2){
					return false;
				}
				
				var conf_value1 = list1[i].run_conf_value;
				var conf_value2 = list2[i].run_conf_value;
				
				if(typeof conf_value1 != 'object'){
					conf_value1 = jsonToObj(conf_value1);
				}
				
				var eql = _.isEqual(conf_value1,conf_value2);
				if(!eql){
					return false;
				}
			}
			return true;
		}else{
			return false;
		}
	}
}

<%-- 上传运行模板数据 --%>
function uploadRunTempData(obj,type){
	<%-- 更新模板信息 --%>
	if(type=="update"){
		$.ajax({
			url:"<%=basePath%>devicerun/updateRunTemp",
			type:"POST",
			data:{"json":JSON.stringify(obj)}
		}).done(function(data){
			if(data!=null && data!=""){
				if(data.state && data.message=="success"){
					showMsg({
						timeout : 1000,
						showText : '修改成功',
						status : true,
						callback:function(){
							refreshCurrent();
							
							var thisDom = $(".g-submit2");
							/*应该是成功的时候收回吧，酌情处理*/
							var thisRight =  thisDom.parents(".form-dialog-fix.w-900").find(".form-dialog").attr("date-right");

							thisDom.parents(".form-dialog-fix.w-900").find(".form-dialog").animate({
								"right":thisRight
							},function(){
								thisDom.parents(".form-dialog-fix.w-900").fadeOut(100);
							});
						}

					});
				}else{
// 					showMsg({
// 						timeout : 3000,
// 						showText : "修改失败！"+data.message,
// 						status : false
// 					});
					layer.alert("修改失败！"+data.message);
				}
			}
			
		}).always(function(){
			endSaving(1);
		});
	}
	<%-- 新增模板信息 --%>
	if(type=="add"){
		$.ajax({
			url:"<%=basePath%>devicerun/addRunTemp",
			type:"POST",
			data:{"json":JSON.stringify(obj)}
		}).done(function(data){
			if(data!=null && data!=""){
				if(data.state && data.message=="success"){
					showMsg({
						timeout : 1000,
						showText : '新增成功！',
						status : true,
						callback:function(){
							refreshCurrent();								
							var thisDom = $(".g-submit2");
							/*应该是成功的时候收回吧，酌情处理*/
							var thisRight =  thisDom.parents(".form-dialog-fix.w-900").find(".form-dialog").attr("date-right");

							thisDom.parents(".form-dialog-fix.w-900").find(".form-dialog").animate({
								"right":thisRight
							},function(){
								thisDom.parents(".form-dialog-fix.w-900").fadeOut(100);
							});
						}

					});
				}else{
// 					showMsg({
// 						timeout : 3000,
// 						showText : "新增失败！"+data.message,
// 						status : false
// 					});
					layer.alert("新增失败！"+data.message);
				}
			}
		}).always(function(){
			endSaving(1);
		});
	}
}

<%-- 回车事件 --%>
$('#keyword').keydown(function(e){
	if(e.keyCode==13){
		param = makeQueryParam(1,pageSize)
		getRunTempList(Page);
	}
});


</script>





