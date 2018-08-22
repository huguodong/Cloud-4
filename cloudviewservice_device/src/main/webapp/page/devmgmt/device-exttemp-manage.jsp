<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<style type="text/css">
#money label{
	display: inline-block;
	width: 40px;
}

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

.readonly{
	color: gray;
	background-color: #E0E0E0;
}
.page-title-bar .g-select {
    float: left;
    margin-left: 10px;
    width: 130px;/*modify*/
    height: 28px;
    line-height: 28px;
    border: 1px solid #dddddd;
    border-radius: 2px;
    background-color: #f6f6f6;
}
</style>


<div class="g-loading"></div>
<div class="g-loading save" ></div>
<div class="config-yingjian">
	<div class="">
		<div class="page-title-bar">
			<span class="title">设备硬件模板配置<a href="${pageContext.request.contextPath}/help/main?url=/page/common/help/devmgmt/device-exttemp-manage.jsp" target="_blank" class="g-help"></a></span>
			<div class="form-wrap fr">
				<div class="g-select">
					<select id="deviceType">
						<option value="-1" selected>选择类型</option>
					</select>
					<span class="arr-icon"></span>
				</div>
				<input type="text" name="keyword" id="keyword" class="input g-input" placeholder="输入模板ID或模板名称" />
				<div class="btn search">查询</div>
				<!-- 修改权限 by huanghuang 20170215 -->
				<shiro:hasPermission name="0102020601">
					<div class="btn increase">新增</div>
				</shiro:hasPermission>
				<shiro:hasPermission name="0102020602">
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
						<th class="col3">模板名称</th>
						<th class="col4">类型</th>
						<th class="col5">模板内容</th>
						<th class="col6">操作</th>
					</tr>
				</thead>
				<tbody>
 					
				</tbody>
			</table>
		</div>
		<%@include file="../include/page_bar.jsf" %>
	</div>
</div>
<div class="form-dialog-fix w-900">
	<div class="shade"></div>
	<div class="form-dialog" id="form-dialog" >
		<div class="title">
			<span >新增配置</span>
			<input type="reset" value="取消" class="g-cancel2 g-btn-gray">
			<input type="submit" placeholder="" class="g-submit2 g-btn-green" value="保存">
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
					<li class="active" id="basic">基本配置</li>
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
			
				<%-- 基本配置 --%>
				<div class="right-content">
					<div class="form-wrap form-wrap2">
						<ul>
							<li class="">
								<div class="left">设备类型</div>
								<div class="right">
									<div class="g-select" >
										<select id="deviceType_edit" class="deviceType">
											<option value="-1" >请选择</option>
										</select> <span class="arr-icon"></span>
									</div>
									<span class="error-msg" >请选择设备类型</span>
								</div>
							</li>
							<li  class="">
								<div class="left">模板ID</div>
								<div class="right">
									<input type="hidden" name="" id="tpl_idx" />
									<input type="text" name="" id="ext_id" maxlength="10" class="g-input" 
										placeholder="请输入" /> <span class="error-msg">请输入模板id</span>
								</div>
							</li>
							<li  class="">
								<div class="left">模板名称</div>
								<div class="right">
									<input type="text" name="" id="ext_desc" class="g-input" 
										placeholder="请输入" /> <span class="error-msg">请输入模板名称</span>
								</div>
							</li>
						
						</ul>
					</div>
				</div>
				
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
								<div class="left">外设部件</div>
								<div class="right">
									<div class="g-select">
										<select id="IdentityReader_extModel" class="need-change extmodel">
										</select> <span class="arr-icon"></span>
									</div>
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
								<div class="left">LED灯序号</div>
								<div class="right">
									<input type="text" name="" id="ItemRfidReader_Light" class="g-input lightnum" 
									    placeholder="请输入"/> 
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
								<div class="left">天线配置项</div>
								<div class="right">
									<input type="text" name="" id="ItemRfidReader_AntAssociated"  class="g-input antAssociated"
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
								<div class="left">外设路径</div>
								<div class="right">
									<input type="text" name="" id="PlcReturn_DriverPath" class="g-input driverPath"
										placeholder="请输入"/> 
								</div>
							</li>
							
							<li>
								<div class="left">天线配置项</div>
								<div class="right">
									<input type="text" name="" id="PlcReturn_AntAssociated"  class="g-input antAssociated"
										placeholder="请输入"/> 
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
								<div class="left">外设路径</div>
								<div class="right">
									<input type="text" name="" id="PlcSorter_DriverPath" class="g-input driverPath"
										placeholder="请输入"/> 
								</div>
							</li>
							
							<li>
								<div class="left">天线配置项</div>
								<div class="right">
									<input type="text" name="" id="PlcSorter_AntAssociated"  class="g-input antAssociated"
										placeholder="请输入"/> 
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
								<div class="left">外设路径</div>
								<div class="right">
									<input type="text" name="" id="PlcSSL_DriverPath" class="g-input driverPath"
										placeholder="请输入"/> 
								</div>
							</li>
							
							<li>
								<div class="left">天线配置项</div>
								<div class="right">
									<input type="text" name="" id="PlcSSL_AntAssociated"  class="g-input antAssociated"
										placeholder="请输入"/> 
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
				
				<%-- Switch开关 --%>
				<div class="right-content hide">
					<div class="form-wrap form-wrap2">
						<ul>

							<li style="display:none">
								<div class="left">模板ID</div>
								<div class="right">
									<input type="text" name="" id="" class="g-input" value="Switch开关" readonly="readonly" style="color: gray;background-color: #E0E0E0"
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
				
				<%-- 其他Other --%>
				<div class="right-content hide" id="OtherDiv">
				    <%-- 
					<div class="form-wrap form-wrap2">
						<ul>
							<li style="display:none;">
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
												<input type="text" name="" id="" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
										<li>
											<div class="left">波特率</div>
											<div class="right">
												<input type="text" name="" id="" class="g-input"
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
												<input type="text" name="" id="" class="g-input"
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
												<input type="text" name="" id="" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
										<li>
											<div class="left">端口</div>
											<div class="right">
												<input type="text" name="" id="" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
									</ul>
								</div>
							</li>
						</ul>
					</div>
					<div class="segmentation">
							<div class="t"></div>
						</div>
						--%>
				</div>
				
			</div>
	</div>
</div>

<div class="g-delete-dialog multi">
	<span class="line"></span>
	<div class="word">
		当前选择了 <span class="font-red"></span> 个项目<br>
		是否删除选择的设备硬件模板配置？
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


<script type="text/javascript" src="${pageContext.request.contextPath }/page/js/template-web.js"></script>
<script type="text/javascript">



</script>


<script type="text/javascript">

function startLoading(){
	$(".g-loading:not(.save)").stop();
	$(".g-loading:not(.save)").fadeIn();
}

function endLoading(val){
	if(val=="1"){
		$(".g-loading:not(.save)").hide();
	}else{
		$(".g-loading:not(.save)").fadeOut();
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
	
	<%-- 删除按钮事件 --%>
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
			title: false, 
			//不显示标题
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
			var extId = $(this).attr("data-name");
			obj.idx=idx;
			obj.extId=extId;
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
			url:"<%=basePath%>deviceext/delExtTemp",
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
					layer.alert("删除失败,"+msg);
// 					showMsg({
// 						timeout : 3000,
// 						showText : "删除失败,"+msg,
// 						status : false
// 					});
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
			url:"<%=basePath%>deviceext/delMultiExtTemp",
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
					layer.alert("删除失败,"+data.message);
// 					showMsg({
// 						timeout : 3000,
// 						showText : "删除失败,"+data.message,
// 						status : false
// 					});
				}
			}
		}).always(function(){
			$delete.removeClass("disabled");
			refreshCurrent();
			closeLayer(delLayer);
		});
	});
	
	<%-- 新增按钮操作 --%>
	$("div.page-title-bar").on("click",".increase",function(){
		clearExtConfData();
		$("div.title span").html("新增配置");
		$(".form-dialog-fix.w-900").fadeIn(100);
		$(".form-dialog-fix.w-900").find(".form-dialog").animate({
			"right":0
		});
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
		clearExtConfData();
		
		if(!_.isEmpty(allExtTempList[idx])){
			loadAllExtTempData(allExtTempList[idx]);
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

	<%-- 点击保存按钮触发事件 --%>
	$(".g-submit2").on("click",function(){
		startSaving();
		if(!checkData()){
			$("#ext_left_tab li").eq(0).trigger("click");
			endSaving(1);
			return;
		}
		var tpl_idx = $("#tpl_idx").val();
		
		extTempSubmit = {};
		extTempSubmit.device_tpl_ID = $("#ext_id").val();
		extTempSubmit.device_tpl_desc = $("#ext_desc").val();
		extTempSubmit.device_type = $("#deviceType_edit").val();
		extTempSubmit = saveExtTemp();
		if(extTempSubmit.extDetailList.length<=0){
			endSaving(1);
			layer.alert("至少设置一个设备逻辑部件的配置信息");
			return;
		}
		if(!_.isEmpty(tpl_idx)){
			<%-- 不为空，编辑，判断是否跟修改前相等，如果相等则不保存--%>
			extTempSubmit.device_tpl_idx = $("#tpl_idx").val();
			<%-- 比较ExtTemp模板 --%>
			var flag = compareExtTemp(allExtTempList[tpl_idx], extTempSubmit);
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
				uploadExtTempData(extTempSubmit,"update");
			}
		}else{
			<%-- 不为空，新增 --%>
			//提交数据保存到数据库
			uploadExtTempData(extTempSubmit,"add");
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
					var thisRight =  thisDom.parents(".form-dialog-fix.w-900").find(".form-dialog").attr("date-right");

					thisDom.parents(".form-dialog-fix.w-900").find(".form-dialog").animate({
						"right":thisRight
					},function(){
						thisDom.parents(".form-dialog-fix.w-900").fadeOut(100);
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
	
	<%-- 校验数据 --%>
	function checkData(){
		var device_tpl_ID = $("#ext_id").val();
		var device_tpl_desc = $("#ext_desc").val();
		var device_tpl_type = $("#deviceType_edit").val();
		
		
		var flag = true;
		
		if(_.isEmpty(device_tpl_ID)){
			$("#ext_id").closest("li").addClass("error");
			flag = false;
		}else{
			$("#ext_id").closest("li").removeClass("error");
		}
		
		if(_.isEmpty(device_tpl_desc)){
			$("#ext_desc").closest("li").addClass("error");
			flag = false;
		}else{
			$("#ext_desc").closest("li").removeClass("error");
		}
		
		if(_.isEmpty(device_tpl_type) || device_tpl_type == "-1"){
			$("#deviceType_edit").closest("li").addClass("error");
			flag = false;
		}else{
			$("#deviceType_edit").closest("li").removeClass("error");
		}
		
		$PlcSSL_box=$("#PlcSSL_box");
		var alert_str="";
		if($PlcSSL_box.attr("style").indexOf("block")>=0){
			var shelf1=$PlcSSL_box.find("#shelf1").val();
			var drawer=$PlcSSL_box.find("#drawer").val();
			var shelf2=$PlcSSL_box.find("#shelf2").val();
			var bookbin=$PlcSSL_box.find("#bookbin").val();
			var shelf3=$PlcSSL_box.find("#shelf3").val();
			if(!$.isNumeric(shelf1)){
				alert_str+="上层书架,";
			}
			if(!$.isNumeric(shelf2)){
				alert_str+="中层书架,";
			}
			if(!$.isNumeric(shelf3)){
				alert_str+="下层书架,";
			}
			if(!$.isNumeric(drawer)){
				alert_str+="特型书盒,";
			}
			if(!$.isNumeric(bookbin)){
				alert_str+="还书箱个数,";
			}
			
		}
		$PlcSSL_comm=$("select#PlcSSL_comm");
		if($PlcSSL_comm.val()=="com"){
			var PlcSSL_COM=$("#PlcSSL_COM").val();
			var PlcSSL_BAUD=$("#PlcSSL_BAUD").val();
			if(!$.isNumeric(PlcSSL_COM)){
				alert_str+="COM口，";
			}
			if(!$.isNumeric(PlcSSL_BAUD)){
				alert_str+="波特率，";
			}
		}
		if(alert_str!=""){
			layer.alert("自助图书馆PLC："+alert_str+" 请填写整数");
			return false;
		}
		return flag;
	}
	
	function saveExtTemp(){
		var detailList = [];
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
					
					other.logic_obj_idx = logic_obj_idx;
					other.ext_model_idx = ext_model_idx;
					
					var type = $("#"+tempId+"_comm").val();
					var com = $("#"+tempId+"_COM").val();
					var baud = $("#"+tempId+"_BAUD").val();
					var usb = $("#"+tempId+"_USB").val();
					var ip = $("#"+tempId+"_IP").val();
					var port = $("#"+tempId+"_PORT").val();
					
					var tempLogicName = $("#"+tempId+"_showName").val();<%-- 自定义的逻辑名 --%>
					ext_extend_conf.logicname = tempLogicName;
					
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
					<%-- 保存通信方式 --%>
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
		return extTempSubmit;
		
	}
	
	function compareExtTemp(tempObj,tempSubmit){
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
			if(tempObj.extDetailList.length!=tempSubmit.extDetailList.length){
				<%-- 如果不相等相等，则使用自定义数据 --%>
				return false;
			}
			for(var i=0;i<tempObj.extDetailList.length;i++){
				var obj = tempObj.extDetailList[i];
				var submit = tempSubmit.extDetailList[i];
				if(obj.logic_obj_idx!=submit.logic_obj_idx){
					return false;
				}
				if(obj.ext_model_idx!=submit.ext_model_idx){
					return false;
				}
				
				<%-- 通讯方式对比 --%>
				var ext_comm_conf1 = jsonToObj(obj.ext_comm_conf);
				var ext_comm_conf2 = submit.ext_comm_conf;
				<%-- 如果两个对象不相等 --%>
				if(!_.isEqual(ext_comm_conf1,ext_comm_conf2)){
					return false;
				}
				
				<%-- 收钞机 --%>
				if(obj.logic_obj.toLowerCase().indexOf("cashacceptor")>-1){
					var ext_extend_conf1 = jsonToObj(obj.ext_extend_conf);
					var ext_extend_conf2 = submit.ext_extend_conf;
					if(!_.isEqual(ext_extend_conf1,ext_extend_conf2)){
						return false;
					}
				}
				<%-- 自助图书馆 --%>
				if(obj.logic_obj.toLowerCase().indexOf("ssl")>-1){
					var ext_extend_conf1 = jsonToObj(obj.ext_extend_conf);
					var ext_extend_conf2 = submit.ext_extend_conf;
					if(!_.isEqual(ext_extend_conf1,ext_extend_conf2)){
						return false;
					}
				}
				
				<%-- 属于其它项的数据,判断收钞机或者自助图书馆，或者逻辑名称  --%>
				if(obj.logic_obj.toLowerCase().indexOf("other")>-1){
					var ext_extend_conf1 = jsonToObj(obj.ext_extend_conf);
					var ext_extend_conf2 = submit.ext_extend_conf;
					if(!_.isEqual(ext_extend_conf1,ext_extend_conf2)){
						return false;
					}
				}
			}
			return true;
		}
	}
	
	function uploadExtTempData(obj,type){
		<%-- 更新模板信息 --%>
		if(type=="update"){
			$.ajax({
				url:"<%=basePath%>deviceext/updateExtTemp",
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
// 						showMsg({
// 							timeout : 3000,
// 							showText : "修改失败！"+data.message,
// 							status : false
// 						});
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
				url:"<%=basePath%>deviceext/addExtTemp",
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
// 						showMsg({
// 							timeout : 3000,
// 							showText : "新增失败！"+data.message,
// 							status : false
// 						});
						layer.alert("新增失败！"+data.message);
					}
				}
			}).always(function(){
				endSaving(1);
			});
		}
	}

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
});


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
	getExtConfList(Page);
});

//上一页操作
$("div.paging-bar").on("click",".prev-page",function(){
	var currentpage = $("#page").find("li.active").html();
	var page=Number(currentpage)-1;
	var Page=makeQueryParam(page, pageSize);
	//带参数
	getExtConfList(Page);
});

//点击某一页
$("div.paging-bar").on("click","li",function(){
	if($(this).hasClass("active")) return;
	var page = $(this).html();
	if(page=="...") return;	
	var Page=makeQueryParam(page, pageSize);
	getExtConfList(Page);
});


<%-- 选择显示页码 --%>
$("div.paging-bar").on("change",".refresh select",function(){
	pageSize = $(this).val();
	var Page=makeQueryParam(1, pageSize);
	getExtConfList(Page);
});


//刷新当前页
function refreshCurrent(){
	var currentpage = $("#page").find("li.active").html();
	var Page=makeQueryParam(currentpage, pageSize);
	//带参数
	getExtConfList(Page);
}


<%-- 查询按钮 --%>
$("div.btn.search").off("click").on("click",function(){
	page = makeQueryParam(1,pageSize)
	getExtConfList(page);
});
</script>

<%--##############################################################################################################--%>
<%--##############################################################################################################--%>
<%--##############################################################################################################--%>
<%--##############################################################################################################--%>
<%---自定义脚本 --%>
<script type="text/javascript">
<%--字符串转JSON格式--%>
function jsonToObj(str){ 
	try{
		return JSON.parse(str); 
	}catch(e){
		return "";
	}
} 

var pageSize=10;
var Page={"page":1,"pageSize":pageSize,};

var extTempList = {}; <%-- 保存当前页面获取到的外设list --%>
var param = {}; <%-- 保存查询条件参数 --%>
var allExtTempList = {}; <%-- 保存页面所有的每一条模板信息  格式为（temp_idx,extTempObj）--%>
var extTempSubmit = {};<%-- 保存要提交到服务器的数据对象 --%>

var logicNameList; <%-- 保存获取到的逻辑名称list --%>
var extModelList;   <%-- 保存获取到的extModel list --%>
var extModelMap = {};   <%-- 保存获取到的extModel Map key为ext_model --%>

var deviceTypeList = {};<%-- 保存设备类型list --%>


$(function(){
	getDeviceType();
	param = makeQueryParam(1,pageSize);
	getExtConfList(param);
	getAllLogicObjAndExtModel();
});


function clearExtConfData(){
	<%-- 清除其他选项卡里面的数据 --%>
	$("#OtherDiv").html("");
	<%-- 清除错误消息的提示 --%>
	$("li.error").removeClass("error");
	<%-- 打开第一项 --%>
	$("#ext_left_tab li").eq(0).trigger("click");
	<%--先清空数据--%>
	$(".extmodel").val("-1").trigger("change");
	$("#tpl_idx").val("");
	<%-- 所有的文本框清空 --%>
	$("#ext_right_form input[type='text']:not('.readonly')").val("");
	<%-- 通讯方式清空 --%>
	$("#ext_right_form select[id$='_comm']").val("-1");
	<%---先把所有的收款通道选择框取消选择--%>
	$("#deviceType_edit").val("-1");
	$("#money div div").removeClass("on");
	$("#money input").prop("checked",false);
	$("#IdentityReader_comm").val("-1").trigger("change");
	$("#deviceType_edit").trigger("change");
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
				deviceTypeList[list[i].meta_devicetype_idx] = list[i];
			}
			$("#deviceType").html(html);
			<%-- 编译以及新增时的设备类型下拉框 --%>
			$("#deviceType_edit").html(html);
			
		}else{
			console.log(data.message)
		}
	});
}

<%--获取硬件与逻辑配置模板--%>
function getExtConfList(param){
		startLoading();
		$(".g-table tbody").html("");
		$(".col1 :checkbox").prop("checked",false).trigger("change");
		$.ajax({
			url:"<%=basePath%>deviceext/getExtTempListByParam",
			type:"POST",
			data:{json:JSON.stringify(param)}
		}).done(function(data){
			if(data!=null && data.state && data.result!=null){
				if(data.result.rows!=null && data.result.rows.length >0){
					extTempList = data.result.rows;
					<%-- 分页 --%>
					$.pagination(data.result);
					var html = "";
					var perNum=0;
					for(var i=0;i<extTempList.length;i++){
						var item = extTempList[i];
						allExtTempList[item.device_tpl_idx] = item;
						html += "<tr><td class=\"col1\"><div class=\"g-checkbox\">";
						html += "<input type=\"checkbox\" name=\"\" id=\""+item.device_tpl_idx+"\" data-name=\""+item.device_tpl_ID+"\"/></div></td>";
						html += "<td>"+item.device_tpl_ID+"</td>";
						html += "<td>"+item.device_tpl_desc+"</td>";
						html += "<td>"+item.device_type_desc+"</td>";
						html += "<td>"+item.content+"</td>";
						html += "<td class='col6'>";
						<shiro:hasPermission name="0102020603">
							html += "<span class=\"btn-a edit\" data-id=\""+item.device_tpl_idx+"\">编辑</span>";
							perNum++;
						</shiro:hasPermission>
						<shiro:hasPermission name="0102020602">
							html += "<span class=\"btn-a delete\" data-id=\""+item.device_tpl_idx+"\" data-name=\""+item.device_tpl_ID+"\">删除</span>";
							perNum++;
						</shiro:hasPermission>
						html +="</td></tr>";
					}
					$(".g-table tbody").html(html);
					if(perNum==0){
						$(".col6").attr("style","display:none;");
					}
				}
			}
		}).always(function(){
			<%-- 任何情况下都执行 --%>
			endLoading(1);
		});
}



<%-- 硬件与逻辑配置模板编辑侧栏的  左侧菜单点击事件 --%>
$("#ext_left_tab").on("click","li",function(){
	var thisIndex = $(this).index();
	$(this).addClass("active").siblings("li").removeClass("active");
	
	$("#ext_right_form .right-content").hide().eq(thisIndex).show();
});


<%-- 获取所有的logic_obj和ext_model的元数据--%>
function getAllLogicObjAndExtModel(){
	$.ajax({
		url:"<%=basePath%>deviceext/getAllLogicObjAndExtModel",
		type:"POST",
		success:function(data){
			if(data!=null && data.state){
				var list = data.result; 
				logicNameList = list.logicObjList;
				extModelList = list.extModelList;
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


<%-- 选择逻辑名之后触发的事件，装载extModel的数据 --%>
$("#ext_right_form").on("change",".logicname",function(n){
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
				<%-- plcssl自助图书馆 --%>
				}else if(data_id.toLowerCase().indexOf("plcssl")>-1){
					if(item.ext_type.toLowerCase().indexOf("plcssl")>-1){
						html+="<option value=\""+item.meta_ext_idx+"\">"+jsonToObj(item.ext_model_desc)['zh_CN']+model_version+"</option>";
						<%--设置拓展参数的值--%>
						$("#PlcSSL_box").attr("val",item.meta_ext_idx);
// 						if(data_id.toLowerCase().indexOf("ssl")>-1 
// 								&& item.ext_model.toLowerCase().indexOf("plc")>-1){
// 						}
// 						if(data_id.toLowerCase().indexOf("sorter")>-1 
// 								&& item.ext_model.toLowerCase().indexOf("sorter")>-1){
// 							html+="<option value=\""+item.meta_ext_idx+"\">"+jsonToObj(item.ext_model_desc)['zh_CN']+model_version+"</option>";
// 						}
// 						if(data_id.toLowerCase().indexOf("return")>-1 
// 								&& item.ext_model.toLowerCase().indexOf("return")>-1){
// 							html+="<option value=\""+item.meta_ext_idx+"\">"+jsonToObj(item.ext_model_desc)['zh_CN']+model_version+"</option>";
// 						}
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

<%-- 加载外设模板logicname的数据  --%>
function loadAllExtTempData(extObj){
	if(!_.isEmpty(extObj)){
		<%-- 设备类型 --%>
		$("#deviceType_edit").val(extObj.device_type).trigger("change");
		$("#ext_id").val(extObj.device_tpl_ID);
		$("#ext_desc").val(extObj.device_tpl_desc);
		$("#tpl_idx").val(extObj.device_tpl_idx);
		
		if(!_.isEmpty(extObj.extDetailList)){
			var list = extObj.extDetailList;
			for(var i=0;i<list.length;i++){
				var item = list[i];
				<%-- 其他选项卡数据填充 --%>
				if(item.logic_obj=="Other"){
					//DOMid:other_CZC100RMB_showName
					var tempid = item.ext_model;
					var ext_extend_conf = jsonToObj(item.ext_extend_conf);
					$("#other_"+tempid+"_showName").val(ext_extend_conf.logicname);
					
					<%-- 通讯方式配置 --%>
					if(item.ext_comm_conf!=null && item.ext_comm_conf!=""){
						var comm = jsonToObj(item.ext_comm_conf);
						
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
// 						$("#"+item.logic_obj+"_extModel").val(item.ext_model_idx).trigger("change");	
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
					var comm = jsonToObj(item.ext_comm_conf);
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
					if(logic_obj.toLowerCase().indexOf("plc1111111") > -1){
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
				
				if(item.logic_obj.toLowerCase().indexOf("plcssl")>-1){
					$("#"+item.logic_obj+"_extModel").val(item.ext_model_idx).trigger("change");	
					<%-- 填充书盒信息  --%>
					if(item.logic_obj.toLowerCase().indexOf("ssl")>-1){
						var ext_extend_conf = jsonToObj(item.ext_extend_conf)
						$("#shelf1").val(ext_extend_conf.SHELF1);
						$("#shelf2").val(ext_extend_conf.SHELF2);
						$("#shelf3").val(ext_extend_conf.SHELF3);
						$("#drawer").val(ext_extend_conf.DRAWER);
						$("#bookbin").val(ext_extend_conf.BOOKBIN);
					}
				}else{
					<%-- 外设部件下拉框 --%>
					$("#"+item.logic_obj+"_extModel").val(item.ext_model_idx);
					if (item.logic_obj.toLowerCase().indexOf("cashacceptor")>-1) {
						<%--收钞机---%>
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
					<%--
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
						--%>
					}
				}
			}
		}
	}
}

<%-- 编辑或者新增的时候选择设备类型之后的事件 --%>
$("#deviceType_edit").change(function(){
	var id = $(this).val();
	$("#OtherDiv").html(""); <%-- 清空其他选项卡的内容 --%>
	$("#ext_left_tab li:not('#basic')").addClass("hide");
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
});
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

	<%-- 回车事件 --%>
	$('#keyword').keydown(function(e){
		if(e.keyCode==13){
			param = makeQueryParam(1,pageSize)
			getExtConfList(Page);
		}
	});


</script>
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

