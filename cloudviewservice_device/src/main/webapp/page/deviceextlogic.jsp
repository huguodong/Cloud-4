<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<title>硬件与逻辑名模板配置</title>
<%-- <%@include file="common/global.jsf" %> --%>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/underscore/underscore-min.js" ></script>
<style type="text/css">
#money label{
	display: inline-block;
	width: 40px;
}
</style>

<div class="config-yingjian">
		<div class="page-title-bar">
			<span class="title">硬件与逻辑名模板配置<a href="help-page.html" target="_blank" class="g-help"></a></span>
			<div class="form-wrap fr">
				<div class="g-select">
					<select id="deviceType">
						<option value="-1" selected>选择类型</option>
					</select>
					<span class="arr-icon"></span>
				</div>
				<input type="text" name="" id="" class="input g-input" placeholder="输入关键词" />
				<div class="btn search">查询</div>
				<div class="btn increase">新增</div>
				<div class="btn delete">删除</div>
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
						<td>00002</td>
						<td>借还机</td>
						<td>打印机</td>
						<td>打印机：研科打印机</td>
						<td>
							<span class="btn-a edit">编辑</span>
							<span class="btn-a delete">删除</span>
						</td>
					</tr>
					 --%>
 					
				</tbody>
			</table>
		</div>
		
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
	</div>
</div>
<div class="form-dialog-fix w-900">
	<div class="shade"></div>
	<div class="form-dialog">
		<div class="title">
			新增配置
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
				 --%>
					<li class="active" id="basic">基本配置</li>
					<li id="AuthorityBarcode">读者证条码枪</li>
					<li id="AuthorityReader">读者证阅读器</li>
					<li id="CardDispenser">发卡机</li>
					<li id="CashAcceptor">收钞机</li>
					<li id="IdentityReader">身份证阅读器</li>
					<li id="ItemBarcode">图书条码枪</li>
					<li id="ItemLoadReader">补书阅读器</li>
					<li id="ItemRfidReader">图书阅读器</li>
					<li id="DevicePLC">设备PLC</li>
<!-- 					<li id="PlcSorter">分拣机PLC</li> -->
<!-- 					<li id="PlcSSL">自助图书馆PLC</li> -->
					<li id="Printer">打印机</li>
					<li id="RegisterReader">注册阅读器</li>
				</ul>
			</div>
			<div class="right-form" id="ext_right_form">
			
				<%-- 基本配置 --%>
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
							<li>
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
								<div class="left">外设部件</div>
								<div class="right">
									<div class="g-select">
										<select id="AuthorityBarcode_extModel" class="need-change extmodel">
											<option value="-1" selected>请选择</option>
										</select> <span class="arr-icon"></span>
									</div>
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
							<li>
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
								<div class="left">外设部件</div>
								<div class="right">
									<div class="g-select">
										<select id="AuthorityBarcode_extModel" class="need-change extmodel">
											<option value="-1" selected>请选择</option>
										</select> <span class="arr-icon"></span>
									</div>
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
							<li>
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
								<div class="left">外设部件</div>
								<div class="right">
									<div class="g-select">
										<select id="AuthorityReader_extModel" class="need-change extmodel">
											<option value="-1" selected>请选择</option>
										</select> <span class="arr-icon"></span>
									</div>
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
							<li>
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
								<div class="left">外设部件</div>
								<div class="right">
									<div class="g-select">
										<select id="CardDispenser_extModel" class="need-change extmodel">
											<option value="-1" selected>请选择</option>
										</select> <span class="arr-icon"></span>
									</div>
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
							<li>
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
							<li>
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
												<input type="text" name="" id="COM" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
										<li>
											<div class="left">波特率</div>
											<div class="right">
												<input type="text" name="" id="BAUD" class="g-input"
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
												<input type="text" name="" id="USB" class="g-input"
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
												<input type="text" name="" id="IP" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
										<li>
											<div class="left">端口</div>
											<div class="right">
												<input type="text" name="" id="PORT" class="g-input"
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
							<li>
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
								<div class="left">外设部件</div>
								<div class="right">
									<div class="g-select">
										<select id="ItemBarcode_extModel" class="need-change extmodel">
											<option value="-1" selected>请选择</option>
										</select> <span class="arr-icon"></span>
									</div>
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
							<li>
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
								<div class="left">外设部件</div>
								<div class="right">
									<div class="g-select">
										<select id="ItemLoadReader_extModel" class="need-change extmodel">
											<option value="-1" selected>请选择</option>
										</select> <span class="arr-icon"></span>
									</div>
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
							<li>
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
								<div class="left">外设部件</div>
								<div class="right">
									<div class="g-select">
										<select id="ItemRfidReader_extModel" class="need-change extmodel">
											<option value="-1" selected>请选择</option>
										</select> <span class="arr-icon"></span>
									</div>
								</div>
							</li>
						</ul>
					</div>
				</div>
				
				<%-- 设备PLC PlcReturn PlcSorter PlcSSL --%>
				<div class="right-content hide">
					<div class="form-wrap form-wrap2">
						<ul>

							<li style="display:none" >
								<div class="left">模板ID</div>
								<div class="right">
									<input type="text" name="" id="" class="g-input" value="设备PLC" readonly="readonly" style="color: gray;background-color: #E0E0E0"
										placeholder="请输入" /> <span class="error-msg">错误提示</span>
								</div>
							</li>
							<li>
								<div class="left">逻辑名</div>
								<div class="right">
									<div class="g-select">
										<select id="PLC_logicName" class="logicname">
											<option value="" selected>请选择</option>
										</select> <span class="arr-icon"></span>
									</div>
								</div>
							</li>
							<li>
								<div class="left">外设部件</div>
								<div class="right">
									<div class="g-select">
										<select id="PLC_extModel" class="need-change extmodel">
											<option value="-1" selected>请选择</option>
										</select> <span class="arr-icon"></span>
									</div>
								</div>
							</li>
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
							
						</ul>
					</div>
				</div>
				
				<%-- 分拣机PLC --%>
				<%--
				<div class="right-content hide">
					<div class="form-wrap form-wrap2">
						<ul>

							<li >
								<div class="left">模板ID</div>
								<div class="right">
									<input type="text" name="" id="" class="g-input" value="分拣机PLC" readonly="readonly" style="color: gray;background-color: #E0E0E0"
										placeholder="请输入" /> <span class="error-msg">错误提示</span>
								</div>
							</li>
							<li>
								<div class="left">逻辑名</div>
								<div class="right">
									<div class="g-select">
										<select>
											<option value="" selected>请选择</option>
										</select> <span class="arr-icon"></span>
									</div>
								</div>
							</li>
							<li>
								<div class="left">外设部件</div>
								<div class="right">
									<div class="g-select">
										<select id="waishe" class="need-change">
											<option value="-1" selected>请选择</option>
										</select> <span class="arr-icon"></span>
									</div>
								</div>
							</li>

							<li>
								<div class="left">通讯方式</div>
								<div class="right">
									<div class="g-select">
										<select id="tongxun" class="need-change">
											<option value="-1">选择通讯方式</option>
											<option value="1" selected>NET</option>
											<option value="2">NET22</option>
										</select> <span class="arr-icon"></span>
									</div>
								</div>
							</li>
							<li>
								<div class="changebox" date-id="tongxun" val="1">
									<ul>
										<li>
											<div class="left">通讯方式11</div>
											<div class="right">
												<input type="text" name="" id="" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
									</ul>
								</div>
								<div class="changebox" date-id="tongxun" val="2">
									<ul>
										<li>
											<div class="left">通讯方式222</div>
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
				</div>
				--%>
				
				<%-- 自助图书馆PLC --%>
				<%--
				
				<div class="right-content hide">
					<div class="form-wrap form-wrap2">
						<ul>

							<li >
								<div class="left">模板ID</div>
								<div class="right">
									<input type="text" name="" id="" class="g-input" value="自助图书馆PLC" readonly="readonly" style="color: gray;background-color: #E0E0E0"
										placeholder="请输入" /> <span class="error-msg">错误提示</span>
								</div>
							</li>
							<li>
								<div class="left">逻辑名</div>
								<div class="right">
									<div class="g-select">
										<select>
											<option value="" selected>请选择</option>
										</select> <span class="arr-icon"></span>
									</div>
								</div>
							</li>
							<li>
								<div class="left">外设部件</div>
								<div class="right">
									<div class="g-select">
										<select id="waishe" class="need-change">
											<option value="-1" selected>请选择</option>
										</select> <span class="arr-icon"></span>
									</div>
								</div>
							</li>

							<li>
								<div class="left">通讯方式</div>
								<div class="right">
									<div class="g-select">
										<select id="tongxun" class="need-change">
											<option value="-1">选择通讯方式</option>
											<option value="1" selected>NET</option>
											<option value="2">NET22</option>
										</select> <span class="arr-icon"></span>
									</div>
								</div>
							</li>
							<li>
								<div class="changebox" date-id="tongxun" val="1">
									<ul>
										<li>
											<div class="left">通讯方式11</div>
											<div class="right">
												<input type="text" name="" id="" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
									</ul>
								</div>
								<div class="changebox" date-id="tongxun" val="2">
									<ul>
										<li>
											<div class="left">通讯方式222</div>
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
				</div>
				
				--%>
				
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
							<li>
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
								<div class="left">外设部件</div>
								<div class="right">
									<div class="g-select">
										<select id="Printer_extModel" class="need-change extmodel">
											<option value="-1" selected>请选择</option>
										</select> <span class="arr-icon"></span>
									</div>
								</div>
							</li>
						</ul>
					</div>
				</div>
				
				<%-- 注册阅读器 RegisterReader --%>
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
							<li>
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
								<div class="left">外设部件</div>
								<div class="right">
									<div class="g-select">
										<select id="RegisterReader_extModel" class="need-change extmodel">
											<option value="-1" selected>请选择</option>
										</select> <span class="arr-icon"></span>
									</div>
								</div>
							</li>
						</ul>
					</div>
				</div>
				
			</div>
	</div>
</div>

<div class="g-delete-dialog">
	<span class="line"></span>
	<div class="word">
		当前选择了 <span class="font-red">7</span> 个项目<br>
		是否要删除选择的配置？
	</div>
	<div class="form-btn cancel g-btn-gray">取消</div>
	<div class="form-btn remove g-btn-red">删除</div>
</div>
<!-- <script src="${pageContext.request.contextPath}/static/plugins/layer/layer.js"></script> -->

<script type="text/javascript">

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
	var delLayer = null;
	$(document).on("click",".delete",function(){
		console.log($(".g-delete-dialog"))
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
			content: $('.g-delete-dialog')
		});
	});
	$(".form-btn.cancel").on("click",function(){
		if (delLayer) {
			layer.close(delLayer);
		}
	});
	$(".form-btn.remove").on("click",function(){
		/*执行删除操作*/
		
		alert("这里执行删除操作");
	});


	$(document).on("click",".increase,.edit",function(){
		$(".form-dialog-fix").fadeIn(100);
		$(".form-dialog-fix").find(".form-dialog").animate({
			"right":0
		});
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

	$(".g-submit2").on("click",function(){
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
		/*
		提交成功  showMsg("添加配置成功",1000,true);
		提交失败  showMsg("添加配置失败",1000,false);
		 */
		
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
});
</script>

<%---自定义脚本 --%>
<script type="text/javascript">
var extTempList = {};
var param = {};


$(function(){
	getDeviceType();
	getExtConfList(param);
});

<%--获取所有设备类型--%>
function getDeviceType(){
	$.ajax({
		url:"<%=basePath%>deviceext/getDeviceTypes",
		type:"POST",
		data:"",
		success:function(data){
			if(data!=null && data.state){
				var list = data.result;
				var html = "<option value=\"-1\" selected>选择类型</option>";
				//deviceType
				for(var i=0;i<list.length;i++){
					html += "<option value=\""+list[i].meta_devicetype_idx+"\">"+list[i].device_type_desc+"</option>"
				}
				$("#deviceType").html(html);
			}else{
				console.log(data.message)
			}
		}
	});
}

<%--获取硬件与逻辑配置模板--%>
function getExtConfList(param){
		param.page="1";
		param.keyword="";
		$.ajax({
			url:"<%=basePath%>deviceext/getExtTempListByParam",
			type:"POST",
			data:{json:JSON.stringify(param)},
			success:function(data){
				if(data!=null && data.state && data.result!=null){
					if(data.result.rows!=null && data.result.rows.length >0){
						extTempList = data.result.rows;
						var html = "";
						for(var i=0;i<extTempList.length;i++){
							console.log(extTempList[i])
							var item = extTempList[i];
							html += "<tr><td class=\"col1\"><div class=\"g-checkbox\">";
							html += "<input type=\"checkbox\" name=\"\" id=\""+item.device_tpl_idx+"\" /></div></td>";
							html += "<td>"+item.device_tpl_ID+"</td>";
							html += "<td>"+"devicetypess"+"</td>";
							html += "<td>"+item.device_tpl_desc+"</td>";
							html += "<td>"+item.content+"</td>";
							html += "<td><span class=\"btn-a edit\">编辑</span>";
							html += "<span class=\"btn-a delete\">删除</span></td></tr>";
						}
						$(".g-table tbody").html(html);
						
					}
				}
// 				<tr>
// 					<td class="col1"><div class="g-checkbox"><input type="checkbox" name="" id="" /></div></td>
// 					<td>00001</td>
// 					<td>24小时自助借还机</td>
// 					<td>图书阅读器</td>
// 					<td>图书阅读器：博伟UHF阅读器</td>
// 					<td>
// 						<span class="btn-a edit">编辑</span>
// 						<span class="btn-a delete">删除</span>
// 					</td>
// 				</tr>

// 				if(data!=null && data.state){
// 					var list = data.result; 
// 					extTempList = data.result;
// 					var html = "";
// 					if(list!=null){
// 						for (var i = 0; i < list.length; i++) {
// 							var item = list[i];
// 							html+="<option value=\""+item.device_ext_id+"\">"+item.device_tpl_ID+"-"+item.device_tpl_desc+"</option>"
// 						}
// 						$("#extTemp").html(html);
// 						setExtTempListDetail(list);<%--设置每个模板的详细信息--%>
// 					}
// 				}else{
// 					console.log(data.message)
// 				}
			}
			
		});
}




<%-- 硬件与逻辑配置模板编辑侧栏的  左侧菜单点击事件 --%>
$("#ext_left_tab").on("click","li",function(){
	var thisIndex = $(this).index();
	$(this).addClass("active").siblings("li").removeClass("active");
	
	$("#ext_right_form .right-content").hide().eq(thisIndex).show();
});
</script>



