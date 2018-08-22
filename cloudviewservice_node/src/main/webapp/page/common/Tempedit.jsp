<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

	<div class="form-dialog-fix w-900 yingjian">
		<div class="shade"></div>
		<div class="form-dialog">
			<div class="title">
				修改配置
				<input type="reset" value="取消" class="g-cancel2 g-btn-gray">
				<input type="submit" placeholder="" id="extTempSave"   class="g-submit2 g-btn-green" value="保存">
				<%-- <input type="button" placeholder="" id="extTempRestore"   class="g-submit2 g-btn-blue" style="margin-right: 10px;" value="恢复至模板"> --%>
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
					<li class="active" id="AuthorityBarcode">读者证条码枪</li>
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
				<%-- 读者证条码枪 AuthorityBarcode --%>
				<div class="right-content">
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
							<li>
								<div class="left">通讯方式</div>
								<div class="right">
									<div class="g-select">
										<select id="PLC_comm" class="need-change">
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
								<div class="changebox" date-id="PLC_comm" val="com">
									<ul>
										<li>
											<div class="left">COM口</div>
											<div class="right">
												<input type="text" name="" id="PLC_COM" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
										<li>
											<div class="left">波特率</div>
											<div class="right">
												<input type="text" name="" id="PLC_BAUD" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
									</ul>
								</div>
								<div class="changebox" date-id="PLC_comm" val="usb">
									<ul>
										<li>
											<div class="left">USB</div>
											<div class="right">
												<input type="text" name="" id="PLC_USB" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
									</ul>
								</div>
								<div class="changebox" date-id="PLC_comm" val="net">
									<ul>
										<li>
											<div class="left">IP</div>
											<div class="right">
												<input type="text" name="" id="PLC_IP" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
										<li>
											<div class="left">端口</div>
											<div class="right">
												<input type="text" name="" id="PLC_PORT" class="g-input"
													placeholder="请输入" />
											</div>
										</li>
									</ul>
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
				
			</div>
		</div>
	</div>
	
	<%-- 运行配置div --%>
	<div class="form-dialog-fix w-900 yunxing">
		<div class="shade"></div>
		<div class="form-dialog">
			<div class="title">
				修改配置
				<input type="reset" value="取消" class="g-cancel2 g-btn-gray">
				<input type="submit" placeholder="" id="runTempSave" class="g-submit2 g-btn-green" value="保存">
				<%-- 				
					<input type="submit" placeholder="" id="runTempRestore" class="g-submit2 g-btn-blue" style="margin-right: 10px;" value="恢复至模板">
				 --%>			
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
						显示书本时间
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
								<div class="g-inputtime"><input type="text" readonly="readonly" id="mon_startuptime" placeholder="输入时间" class="g-input timepicker"><span class="icon"></span></div>
							</div>
							<div class="float-item sec3">
								<div class="g-inputtime"><input type="text" readonly="readonly" id="mon_shutdowntime" placeholder="输入时间" class="g-input timepicker"><span class="icon"></span></div>
							</div>
							<div class="float-item sec4">
								<div class="g-checkbox"><input type="checkbox"  id="mon_enableclose"></div><label for="mon_enableclose">关机</label>
							</div>
						</div>
						<div class="item">
							<div class="float-item sec1">星期二</div>
							<div class="float-item sec2">
								<div class="g-inputtime"><input type="text" readonly="readonly" id="tue_startuptime" placeholder="输入时间" class="g-input timepicker"><span class="icon"></span></div>
							</div>
							<div class="float-item sec3">
								<div class="g-inputtime"><input type="text" readonly="readonly" id="tue_shutdowntime" placeholder="输入时间" class="g-input timepicker"><span class="icon"></span></div>
							</div>
							<div class="float-item sec4">
								<div class="g-checkbox"><input type="checkbox" id="tue_enableclose"></div><label for="tue_enableclose">关机</label>
							</div>
						</div>
						<div class="item">
							<div class="float-item sec1">星期三</div>
							<div class="float-item sec2">
								<div class="g-inputtime"><input type="text" readonly="readonly" id="wed_startuptime" placeholder="输入时间" class="g-input timepicker"><span class="icon"></span></div>
							</div>
							<div class="float-item sec3">
								<div class="g-inputtime"><input type="text" readonly="readonly" id="wed_shutdowntime" placeholder="输入时间" class="g-input timepicker"><span class="icon"></span></div>
							</div>
							<div class="float-item sec4">
								<div class="g-checkbox"><input type="checkbox" id="wed_enableclose"></div><label for="wed_enableclose">关机</label>
							</div>
						</div>
						<div class="item">
							<div class="float-item sec1">星期四</div>
							<div class="float-item sec2">
								<div class="g-inputtime"><input type="text" readonly="readonly" id="thu_startuptime" placeholder="输入时间" class="g-input timepicker"><span class="icon"></span></div>
							</div>
							<div class="float-item sec3">
								<div class="g-inputtime"><input type="text" readonly="readonly" id="thu_shutdowntime" placeholder="输入时间" class="g-input timepicker"><span class="icon"></span></div>
							</div>
							<div class="float-item sec4">
								<div class="g-checkbox"><input type="checkbox" id="thu_enableclose"></div><label for="thu_enableclose">关机</label>
							</div>
						</div>
						<div class="item">
							<div class="float-item sec1">星期五</div>
							<div class="float-item sec2">
								<div class="g-inputtime"><input type="text" readonly="readonly" id="fri_startuptime" placeholder="输入时间" class="g-input timepicker"><span class="icon"></span></div>
							</div>
							<div class="float-item sec3">
								<div class="g-inputtime"><input type="text" readonly="readonly" id="fri_shutdowntime" placeholder="输入时间" class="g-input timepicker"><span class="icon"></span></div>
							</div>
							<div class="float-item sec4">
								<div class="g-checkbox"><input type="checkbox" id="fri_enableclose"></div><label for="fri_enableclose">关机</label>
							</div>
						</div>
						<div class="item">
							<div class="float-item sec1">星期六</div>
							<div class="float-item sec2">
								<div class="g-inputtime"><input type="text" readonly="readonly" id="sat_startuptime" placeholder="输入时间" class="g-input timepicker"><span class="icon"></span></div>
							</div>
							<div class="float-item sec3">
								<div class="g-inputtime"><input type="text" readonly="readonly" id="sat_shutdowntime" placeholder="输入时间" class="g-input timepicker"><span class="icon"></span></div>
							</div>
							<div class="float-item sec4">
								<div class="g-checkbox"><input type="checkbox" id="sat_enableclose"></div><label for="sat_enableclose">关机</label>
							</div>
						</div>
						<div class="item">
							<div class="float-item sec1">星期日</div>
							<div class="float-item sec2">
								<div class="g-inputtime"><input type="text" readonly="readonly" id="sun_startuptime" placeholder="输入时间" class="g-input timepicker"><span class="icon"></span></div>
							</div>
							<div class="float-item sec3">
								<div class="g-inputtime"><input type="text" readonly="readonly" id="sun_shutdowntime" placeholder="输入时间" class="g-input timepicker"><span class="icon"></span></div>
							</div>
							<div class="float-item sec4">
								<div class="g-checkbox"><input type="checkbox" id="sun_enableclose"></div><label for="sun_enableclose">关机</label>
							</div>
						</div>
					</div>
					<div class="com-item">
						<div class="g-checkbox"><input type="checkbox" id="enablebalance"></div><label for="enablebalance">定时财经结算</label>
						<div class="pl26">
							<div class="g-inputtime"><input type="text" readonly="readonly" id="balancetime" placeholder="输入时间" class="g-input timepicker"><span class="icon"></span></div>
						</div>
					</div>
					<div class="com-item">
						<div class="g-checkbox"><input type="checkbox" id="enablereboot"></div><label for="enablereboot">定时重启机器</label>
						<div class="pl26">
							<div class="g-inputtime"><input type="text" readonly="readonly" id="reboottime" placeholder="输入时间" class="g-input timepicker"><span class="icon"></span></div>
						</div>
					</div>
					<div class="com-item">
						<div class="g-checkbox"><input type="checkbox" id="enableshutdown"></div><label for="enableshutdown">自动关机</label>
						<div class="pl26">
							<div class="g-inputtime"><input type="text" readonly="readonly" id="shutdowntime" placeholder="输入时间" class="g-input timepicker"><span class="icon"></span></div>
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
							<input type="text" id="checkin_date" placeholder="输入时间" class="g-input w18">
						</div>
					</div>
					<div class="small-item">
						<div class="left">
							时间描述
						</div>
						<div class="right">
							<input type="text" id="checkin_time" placeholder="输入时间" class="g-input w18">
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
							<input type="text" id="checkout_date" placeholder="输入时间" class="g-input w18">
						</div>
					</div>
					<div class="small-item">
						<div class="left">
							时间描述
						</div>
						<div class="right">
							<input type="text" id="checkout_time" placeholder="输入时间" class="g-input w18">
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
							<input type="text" id="renew_date" placeholder="输入时间" class="g-input w18">
						</div>
					</div>
					<div class="small-item">
						<div class="left">
							时间描述
						</div>
						<div class="right">
							<input type="text" id="renew_time" placeholder="输入时间" class="g-input w18">
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
							<input type="text" id="pay_date" placeholder="输入时间" class="g-input w18">
						</div>
					</div>
					<div class="small-item">
						<div class="left">
							时间描述
						</div>
						<div class="right">
							<input type="text" id="pay_time" placeholder="输入时间" class="g-input w18">
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
							<input type="text" id="cashbin_date" placeholder="输入时间" class="g-input w18">
						</div>
					</div>
					<div class="small-item">
						<div class="left">
							时间描述
						</div>
						<div class="right">
							<input type="text" id="cashbin_time" placeholder="输入时间" class="g-input w18">
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
							<input type="text" id="bookbin_date" placeholder="输入时间" class="g-input w18">
						</div>
					</div>
					<div class="small-item">
						<div class="left">
							时间描述
						</div>
						<div class="right">
							<input type="text" id="bookbin_time" placeholder="输入时间" class="g-input w18">
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
							<input type="text" id="bookrack_date" placeholder="输入时间" class="g-input w18">
						</div>
					</div>
					<div class="small-item">
						<div class="left">
							时间描述
						</div>
						<div class="right">
							<input type="text" id="bookrack_time" placeholder="输入时间" class="g-input w18">
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
							<input type="text" id="cardbin_date" placeholder="输入时间" class="g-input w18">
						</div>
					</div>
					<div class="small-item">
						<div class="left">
							时间描述
						</div>
						<div class="right">
							<input type="text" id="cardbin_time" placeholder="输入时间" class="g-input w18">
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
						<label for="xiaoxi2">支持63指令消息请求</label>
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

	<%-- 监控配置 --%>
	<div class="form-dialog-fix w-900 monitor">
		<div class="shade"></div>
		<div style="right: -900px;" date-right="-900px" class="form-dialog">
			<div class="title">
				修改配置 <input value="取消" class="g-cancel2 g-btn-gray" type="reset">
				<input name="saveOrUpd" placeholder="" class="g-submit2 g-btn-green"
					value="保存" type="submit">
			</div>
			<div class="left-tab" id="monitor_left_tab">
				<ul>
				</ul>
			</div>
			<div class="right-form" id="monitor_right_form">
			<%--	
				<div class="right-content">...</div>
				<div class="right-content">...</div>
			 --%>
			 <c:forEach items="${metadataLogicObjects}" var="metadataLogicObject">
					<div class="right-content" logicObjName="${metadataLogicObject.logic_obj}" id="${metadataLogicObject.meta_log_obj_idx}">
						 <div class="form-wrap">
						 	<c:choose>
						 	<%--24小时自助借还--%>
						 	<c:when test="${metadataLogicObject.logic_obj eq 'PlcSSL'}">
						 		<c:forEach items="${plcLogicObjs}" var="plcLogicObj">
						 			<ul plcLogicObjIdx="${plcLogicObj.plc_logic_obj_idx}">
						 				<li>
											<div class="segmentation">
												<div class="t">${plcLogicObj.plc_logic_obj_desc}</div>
											</div>
										</li>
										<c:if test="${fn:startsWith(plcLogicObj.plc_logic_obj,'Drawer')}">
											<li>
												<div class="left">报警限额</div>
												<div class="right">
													<input type="text" name="threshold" id="" class="g-input" placeholder="请输入"  />
													<div class="tips">注：即超过多少本时报警</div>
													<span class="error-msg">不能为空</span>
												</div>
											</li>
										</c:if>
										<li>
											<div class="left">启动</div>
											<div class="right level">
												<span class="alert btn active">报警</span>
												<span class="error btn">失效</span>
											</div>
										</li>
										<li>
											<div class="left">颜色</div>
												<div class="right">
													<div class="g-select">
													<select class="colorSelect"></select>
													<span class="arr-icon"></span>
												</div>
											</div>
										</li>
						 			</ul>
						 		</c:forEach>
						 	</c:when>
						 <%-- 	分拣机 --%>
						 	<c:when test="${metadataLogicObject.logic_obj eq 'PlcSorter'}">
							 		<c:forEach items="${plcLogicObjs}" var="plcLogicObj">
							 			<c:if test="${fn:contains(plcSorter,plcLogicObj.plc_logic_obj)}">
							 			 <ul plcLogicObjIdx="${plcLogicObj.plc_logic_obj_idx}">
							 				<li>
												<div class="segmentation">
													<div class="t">${plcLogicObj.plc_logic_obj_desc}</div>
												</div>
											</li>
											<li>
											<div class="left">启动</div>
											<div class="right level">
												<span class="alert btn active">报警</span>
												<span class="error btn">失效</span>
											</div>
										</li>
										<li>
											<div class="left">颜色</div>
												<div class="right">
													<div class="g-select">
													<select class="colorSelect"></select>
													<span class="arr-icon"></span>
												</div>
											</div>
										</li>
										</ul>
							 		</c:if>
							 	</c:forEach>
						 	</c:when>
						 <%-- 	还书机 --%>
						 	<c:when test="${metadataLogicObject.logic_obj eq 'PlcReturn'}">
							 		<c:forEach items="${plcLogicObjs}" var="plcLogicObj">
							 			<c:if test="${fn:contains(plcReturn,plcLogicObj.plc_logic_obj)}">
							 			 <ul plcLogicObjIdx="${plcLogicObj.plc_logic_obj_idx}">	
							 				<li>
												<div class="segmentation">
													<div class="t">${plcLogicObj.plc_logic_obj_desc}</div>
												</div>
											</li>
											<li>
											<div class="left">启动</div>
											<div class="right level">
												<span class="alert btn active">报警</span>
												<span class="error btn">失效</span>
											</div>
										</li>
										<li>
											<div class="left">颜色</div>
												<div class="right">
													<div class="g-select">
													<select class="colorSelect"></select>
													<span class="arr-icon"></span>
												</div>
											</div>
										 </li>
										</ul>
							 			</c:if>
							 		</c:forEach>
						 	</c:when>
						<%-- 	其他 --%>
						 	<c:otherwise>
						 	 <ul>
								 <li>
									<div class="segmentation">
										<div class="t">${metadataLogicObject.logic_obj_desc}</div>
									</div>
								</li>
								 <c:if test="${metadataLogicObject.logic_obj eq 'CardDispenser'}">
									<li>
										<div class="left">报警限额</div>
										<div class="right">
											<input type="text" name="threshold" id="" class="g-input" placeholder="请输入"  />
											<div class="tips">注：即超过多少张卡片时报警</div>
											<span class="error-msg">不能为空</span>
										</div>
									</li>
								</c:if>
								<c:if test="${metadataLogicObject.logic_obj eq 'CashAcceptor'}">
									<li>
										<div class="left">纸币数量</div>
										<div class="right">
											<input type="text"  name="threshold" id="threshold-rmb-num" class="g-input" placeholder="请输入"  />
											<div class="tips">注：即超过多少张钱的时候报警</div>
											<span class="error-msg">不能为空</span>
										</div>
									</li>
								</c:if>
								<li>
									<div class="left">启动</div>
									<div class="right level">
										<span class="alert btn active">报警</span>
										<span class="error btn">失效</span>
									</div>
								</li>
								<li>
									<div class="left">颜色</div>
										<div class="right">
											<div class="g-select">
											<select class="colorSelect"></select>
											<span class="arr-icon"></span>
										</div>
									</div>
								</li>
						 	</ul>
						 	</c:otherwise>
						 	</c:choose>
						</div>
					</div>
			</c:forEach>
			<%--超时设置--%>
			<div class="right-content" logicObjName="time_out" id="time_out">
				 <div class="form-wrap">
				 	<div class="item">
				 		<ul>
				 			<li>
								<div class="segmentation">
									<div class="t">设备与云平台通信超时时间设置</div>
								</div>
							</li>
							<li>
								<div class="left">超时限额</div>
								<div class="right">
									<input type="text" name="threshold" id="" class="g-input" placeholder="请输入"  />
									<div class="tips">注：单位秒</div>
									<span class="error-msg">不能为空</span>
								</div>
							</li>
						</ul>
				 	</div>
				 </div>
			</div>
			</div>
		</div>
	</div>
	<!-- 同步配置 -->
	<div class="form-dialog-fix w-900 dbsync">
	<div class="shade"></div>
	<div class="form-dialog">
		<div class="title">
			新增配置
			<input type="reset" value="取消" class="g-cancel2 g-btn-gray">
			<input type="submit" placeholder="" class="g-submit2 g-btn-green" value="保存">
		</div>
		<div class="left-tab" id="dbsync_left_tab">
			<ul>
				<%-- <li class="basicInfo">基本信息</li> --%>
				<li class="device_config active">device_config</li>
				<li>device_library</li>
				<li>device_operlog</li>
				<li>device_state</li>
			</ul>
		</div>
		<div class="right-form" id="dbsync_right_form">
		 <%--  <div class="right-content hide">
			<div class="submenu-segmentation-line">
				<div class="t">基本信息</div>
			</div>
			<div class="form-wrap">
				<ul>
					<li>
						<div class="left">模板ID</div>
						<div class="right">
							<input type="text" name="" id="temp_ID" class="g-input" placeholder="请输入" />
							<span class="error-msg">ID不能为空</span>
						</div>
					</li>
					<li>
						<div class="left">模板名</div>
						<div class="right">
							<input type="text" name="" id="temp_name" class="g-input" placeholder="请输入" />
							<span class="error-msg">模板名不能为空</span>
						</div>
					</li>
					<li>
						<div class="left">设备类型</div>
						<div class="right">
							<div class="g-select">
								<select id="type">
									<option value="-1" selected>请选择</option>
								</select>
								<span class="arr-icon"></span>
							</div>
							<span class="error-msg">请选择模板的设备类型</span>
						</div>
					</li> 
				</ul>
			</div>
		  </div> --%>
		  <div class="right-content">
			<div class="submenu-segmentation-line">
				<div class="t">device_dbsync_config</div>
			</div>
			<div class="form-wrap">
				<ul>
					<li>
						<div class="left">数据库名</div>
						<div class="right">
							<div class="g-select">
								<select class="db">
									<option value="" selected>device_config</option>
								</select>
								<span class="arr-icon"></span>
							</div>
						</div>
					</li>
					<li>
						<div class="left">表名</div>
						<div class="right">
							<div class="g-select">
								<select class="table">
									<option value="1" >device_dbsync_config</option>
									<option value="-1">请选择</option>
								</select>
								<span class="arr-icon"></span>
							</div>
						</div>
					</li>
					<li>
						<div class="left">&nbsp;</div>
						<div class="right">
							<div class="g-checkbox"><input type="checkbox"  name="" value="" class="tongbu" /></div><label for="tongbu">是否同步</label>
						</div>
					</li>
					<li>
						<form action="">
						 <!-- <div class="left">&nbsp;</div> -->
						<div class="right short-select">
							<span class="fl">
							同步周期
							<input type="text" class="cycle-input g-input" name="cycle-num" value=""/>
							</span>
							<div class="g-select">
								<select class="cycle">
									<option value="">一天</option>
								</select>	
								<div class="arr-icon"></div>
							</div>
						</div>
						
						</form>
					</li>
				</ul>
			</div>
			<div class="submenu-segmentation-line">
				<div class="t">device_ext_config</div>
			</div>
			<div class="form-wrap">
				<ul>
					<li>
						<div class="left">数据库名</div>
						<div class="right">
							<div class="g-select">
								<select class="db">
									<option value="" selected>device_config</option>
								</select>
								<span class="arr-icon"></span>
							</div>
						</div>
					</li>
					<li>
						<div class="left">表名</div>
						<div class="right">
							<div class="g-select">
								<select class = "table">
									<option value="1">device_ext_config</option>
									<option value="-1">请选择</option>
								</select>
								<span class="arr-icon"></span>
							</div>
						</div>
					</li>
					<li>
						<div class="left">&nbsp;</div>
						<div class="right">
							<div class="g-checkbox"><input type="checkbox"  name="" value="" class="tongbu" /></div><label for="tongbu">是否同步</label>
						</div>
					</li>
					<li>
						<form action="">
						<!-- <div class="left">&nbsp;</div> -->
						<div class="right short-select">
							<span class="fl">
							同步周期
							<input type="text" class="cycle-input g-input" name="cycle-num" value=""/>
							</span>
							<div class="g-select">
								<select class = "cycle">
									<option value="">一天</option>
								</select>	
								<div class="arr-icon"></div>
							</div>
						</div>
						</form>
					</li>
				</ul>
			</div>
			<div class="submenu-segmentation-line">
				<div class="t">device_run_config</div>
			</div>
			<div class="form-wrap">
				<ul>
					<li>
						<div class="left">数据库名</div>
						<div class="right">
							<div class="g-select">
								<select class = "db">
									<option value="" selected>device_config</option>
									
								</select>
								<span class="arr-icon"></span>
							</div>
						</div>
					</li>
					<li>
						<div class="left">表名</div>
						<div class="right">
							<div class="g-select">
								<select class = "table">
									<option value="1" >device_run_config</option>
									<option value="-1">请选择</option>
								</select>
								<span class="arr-icon"></span>
							</div>
						</div>
					</li>
					<li>
						<div class="left">&nbsp;</div>
						<div class="right">
							<div class="g-checkbox"><input type="checkbox"  name="" value="" class="tongbu" /></div><label for="tongbu">是否同步</label>
						</div>
					</li>
					<li>
						<form action="">
						<!-- <div class="left">&nbsp;</div> -->
						<div class="right short-select">
							<span class="fl">
							同步周期
							<input type="text" class="cycle-input g-input" name="cycle-num" value=""/>
							</span>
							<div class="g-select">
								<select class = "cycle">
									<option value="">一天</option>
								</select>	
								<div class="arr-icon"></div>
							</div>
						</div>
						</form>
					</li>
				</ul>
			</div>
		  </div>
		  <div class="right-content hide">
			<div class="submenu-segmentation-line">
				<div class="t">book</div>
			</div>
			<div class="form-wrap">
				<ul>
					<li>
						<div class="left">数据库名</div>
						<div class="right">
							<div class="g-select">
								<select class = "db">
									<option value="" selected>device_library</option>
								</select>
								<span class="arr-icon"></span>
							</div>
						</div>
					</li>
					<li>
						<div class="left">表名</div>
						<div class="right">
							<div class="g-select">
								<select class = "table">
									<option value="1" >book</option>
									<option value="-1">请选择</option>
								</select>
								<span class="arr-icon"></span>
							</div>
						</div>
					</li>
					<li>
						<div class="left">&nbsp;</div>
						<div class="right">
							<div class="g-checkbox"><input type="checkbox"  name="" value="" class="tongbu" /></div><label for="tongbu">是否同步</label>
						</div>
					</li>
					<li>
						<form action="">
						<!-- <div class="left">&nbsp;</div> -->
						<div class="right short-select">
							<span class="fl">
							同步周期
							<input type="text" class="cycle-input g-input" name="cycle-num" value=""/>
							</span>
							<div class="g-select">
								<select class = "cycle">
									<option value="-1">请选择</option>
								</select>	
								<div class="arr-icon"></div>
							</div>
						</div>
						</form>
					</li>
				</ul>
			</div>
			<div class="submenu-segmentation-line">
				<div class="t">patron</div>
			</div>
			<div class="form-wrap">
				<ul>
					<li>
						<div class="left">数据库名</div>
						<div class="right">
							<div class="g-select">
								<select class = "db">
									<option value="" selected>device_library</option>
								</select>
								<span class="arr-icon"></span>
							</div>
						</div>
					</li>
					<li>
						<div class="left">表名</div>
						<div class="right">
							<div class="g-select">
								<select class = "table">
									<option value="1" >patron</option>
									<option value="-1">请选择</option>
								</select>
								<span class="arr-icon"></span>
							</div>
						</div>
					</li>
					<li>
						<div class="left">&nbsp;</div>
						<div class="right">
							<div class="g-checkbox"><input type="checkbox"  name="" value="" class="tongbu" /></div><label for="tongbu">是否同步</label>
						</div>
					</li>
					<li>
						<form action="">
						<!-- <div class="left">&nbsp;</div> -->
						<div class="right short-select">
							<span class="fl">
							同步周期
							<input type="text" class="cycle-input g-input" name="cycle-num" value=""/>
							</span>
							<div class="g-select">
								<select class = "cycle">
									<option value="-1">请选择</option>
								</select>	
								<div class="arr-icon"></div>
							</div>
						</div>
						</form>
					</li>
				</ul>
			</div>
			<div class="submenu-segmentation-line">
				<div class="t">reader_type</div>
			</div>
			<div class="form-wrap">
				<ul>
					<li>
						<div class="left">数据库名</div>
						<div class="right">
							<div class="g-select">
								<select class = "db">
									<option value="" selected>device_library</option>
									
								</select>
								<span class="arr-icon"></span>
							</div>
						</div>
					</li>
					<li>
						<div class="left">表名</div>
						<div class="right">
							<div class="g-select">
								<select class = "table">
									<option value="1" >reader_type</option>
									<option value="-1">请选择</option>
								</select>
								<span class="arr-icon"></span>
							</div>
						</div>
					</li>
					<li>
						<div class="left">&nbsp;</div>
						<div class="right">
							<div class="g-checkbox"><input type="checkbox"  name="" value="" class="tongbu" /></div><label for="tongbu">是否同步</label>
						</div>
					</li>
					<li>
						<form action="">
						<!-- <div class="left">&nbsp;</div> -->
						<div class="right short-select">
							<span class="fl">
							同步周期
							<input type="text" class="cycle-input g-input" name="cycle-num" value=""/>
							</span>
							<div class="g-select">
								<select class = "cycle">
									<option value="-1">请选择</option>
								</select>	
								<div class="arr-icon"></div>
							</div>
						</div>
						</form>
					</li>
				</ul>
			</div>
			<div class="submenu-segmentation-line">
				<div class="t">sip2_config</div>
			</div>
			<div class="form-wrap">
				<ul>
					<li>
						<div class="left">数据库名</div>
						<div class="right">
							<div class="g-select">
								<select class = "db">
									<option value="" selected>device_library</option>
								</select>
								<span class="arr-icon"></span>
							</div>
						</div>
					</li>
					<li>
						<div class="left">表名</div>
						<div class="right">
							<div class="g-select">
								<select class = "table">
									<option value="1" >sip2_config</option>
									<option value="-1">请选择</option>
								</select>
								<span class="arr-icon"></span>
							</div>
						</div>
					</li>
					<li>
						<div class="left">&nbsp;</div>
						<div class="right">
							<div class="g-checkbox"><input type="checkbox"  name="" value="" class="tongbu" /></div><label for="tongbu">是否同步</label>
						</div>
					</li>
					<li>
						<form action="">
						<!-- <div class="left">&nbsp;</div> -->
						<div class="right short-select">
							<span class="fl">
							同步周期
							<input type="text" class="cycle-input g-input" name="cycle-num" value=""/>
							</span>
							<div class="g-select">
								<select class = "cycle">
									<option value="-1">请选择</option>
								</select>	
								<div class="arr-icon"></div>
							</div>
						</div>
						</form>
					</li>
				</ul>
			</div>		
		  </div>
		  <div class="right-content hide">
			<div class="submenu-segmentation-line">
				<div class="t">operation_log_1</div>
			</div>
			<div class="form-wrap">
				<ul>
					<li>
						<div class="left">数据库名</div>
						<div class="right">
							<div class="g-select">
								<select class = "db">
									<option value="" selected>device_operlog</option>
								</select>
								<span class="arr-icon"></span>
							</div>
						</div>
					</li>
					<li>
						<div class="left">表名</div>
						<div class="right">
							<div class="g-select">
								<select class = "table">
									<option value="1" >operation_log_1</option>
									<option value="-1">请选择</option>
								</select>
								<span class="arr-icon"></span>
							</div>
						</div>
					</li>
					<li>
						<div class="left">&nbsp;</div>
						<div class="right">
							<div class="g-checkbox"><input type="checkbox"  name="" value="" class="tongbu" /></div><label for="tongbu">是否同步</label>
						</div>
					</li>
					<li>
						<form action="">
						<!-- <div class="left">&nbsp;</div> -->
						<div class="right short-select">
							<span class="fl">
							同步周期
							<input type="text" class="cycle-input g-input" name="cycle-num" value=""/>
							</span>
							<div class="g-select">
								<select class = "cycle">
									<option value="-1">请选择</option>
								</select>	
								<div class="arr-icon"></div>
							</div>
						</div>
						</form>
					</li>
				</ul>
			</div>
			<div class="submenu-segmentation-line">
				<div class="t">operation_log_2</div>
			</div>
			<div class="form-wrap">
				<ul>
					<li>
						<div class="left">数据库名</div>
						<div class="right">
							<div class="g-select">
								<select class = "db">
									<option value="" selected>device_operlog</option>
								</select>
								<span class="arr-icon"></span>
							</div>
						</div>
					</li>
					<li>
						<div class="left">表名</div>
						<div class="right">
							<div class="g-select">
								<select class = "table">
									<option value="1" >operation_log_2</option>
									<option value="-1">请选择</option>
								</select>
								<span class="arr-icon"></span>
							</div>
						</div>
					</li>
					<li>
						<div class="left">&nbsp;</div>
						<div class="right">
							<div class="g-checkbox"><input type="checkbox"  name="" value="" class="tongbu" /></div><label for="tongbu">是否同步</label>
						</div>
					</li>
					<li>
						<form action="">
						<!-- <div class="left">&nbsp;</div> -->
						<div class="right short-select">
							<span class="fl">
							同步周期
							<input type="text" class="cycle-input g-input" name="cycle-num" value=""/>
							</span>
							<div class="g-select">
								<select class = "cycle">
									<option value="-1">请选择</option>
								</select>	
								<div class="arr-icon"></div>
							</div>
						</div>
						</form>
					</li>
				</ul>
			</div>		
			<div class="submenu-segmentation-line">
				<div class="t">operation_log_3</div>
			</div>
			<div class="form-wrap">
				<ul>
					<li>
						<div class="left">数据库名</div>
						<div class="right">
							<div class="g-select">
								<select class = "db">
									<option value="" selected>device_operlog</option>
								</select>
								<span class="arr-icon"></span>
							</div>
						</div>
					</li>
					<li>
						<div class="left">表名</div>
						<div class="right">
							<div class="g-select">
								<select class = "table">
									<option value="1" >operation_log_3</option>
									<option value="-1">请选择</option>
								</select>
								<span class="arr-icon"></span>
							</div>
						</div>
					</li>
					<li>
						<div class="left">&nbsp;</div>
						<div class="right">
							<div class="g-checkbox"><input type="checkbox"  name="" value="" class="tongbu" /></div><label for="tongbu">是否同步</label>
						</div>
					</li>
					<li>
						<form action="">
						<!-- <div class="left">&nbsp;</div> -->
						<div class="right short-select">
							<span class="fl">
							同步周期
							<input type="text" class="cycle-input g-input" name="cycle-num" value=""/>
							</span>
							<div class="g-select">
								<select class = "cycle">
									<option value="-1">请选择</option>
								</select>	
								<div class="arr-icon"></div>
							</div>
						</div>
						</form>
					</li>
				</ul>
			</div>
			<div class="submenu-segmentation-line">
				<div class="t">operation_log_4</div>
			</div>
			<div class="form-wrap">
				<ul>
					<li>
						<div class="left">数据库名</div>
						<div class="right">
							<div class="g-select">
								<select class = "db">
									<option value="" selected>device_operlog</option>
								
								</select>
								<span class="arr-icon"></span>
							</div>
						</div>
					</li>
					<li>
						<div class="left">表名</div>
						<div class="right">
							<div class="g-select">
								<select class = "table">
									<option value="1" >operation_log_4</option>
									<option value="-1">请选择</option>
								</select>
								<span class="arr-icon"></span>
							</div>
						</div>
					</li>
					<li>
						<div class="left">&nbsp;</div>
						<div class="right">
							<div class="g-checkbox"><input type="checkbox"  name="" value="" class="tongbu" /></div><label for="tongbu">是否同步</label>
						</div>
					</li>
					<li>
						<form action="">
						<!-- <div class="left">&nbsp;</div> -->
						<div class="right short-select">
							<span class="fl">
							同步周期
							<input type="text" class="cycle-input g-input" name="cycle-num" value=""/>
							</span>
							<div class="g-select">
								<select class = "cycle">
									<option value="-1">请选择</option>
								</select>	
								<div class="arr-icon"></div>
							</div>
						</div>
						</form>
					</li>
				</ul>
			</div>
			<div class="submenu-segmentation-line">
				<div class="t">operation_log_5</div>
			</div>
			<div class="form-wrap">
				<ul>
					<li>
						<div class="left">数据库名</div>
						<div class="right">
							<div class="g-select">
								<select class = "db">
									<option value="" selected>device_operlog</option>
									
								</select>
								<span class="arr-icon"></span>
							</div>
						</div>
					</li>
					<li>
						<div class="left">表名</div>
						<div class="right">
							<div class="g-select">
								<select class = "table">
									<option value="1" >operation_log_5</option>
									<option value="-1"></option>
								</select>
								<span class="arr-icon"></span>
							</div>
						</div>
					</li>
					<li>
						<div class="left">&nbsp;</div>
						<div class="right">
							<div class="g-checkbox"><input type="checkbox"  name="" value="" class="tongbu" /></div><label for="tongbu">是否同步</label>
						</div>
					</li>
					<li>
						<form action="">
						<!-- <div class="left">&nbsp;</div> -->
						<div class="right short-select">
							<span class="fl">
							同步周期
							<input type="text" class="cycle-input g-input" name="cycle-num" value=""/>
							</span>
							<div class="g-select">
								<select class = "cycle">
									<option value="-1">请选择</option>
								</select>	
								<div class="arr-icon"></div>
							</div>
						</div>
						</form>
					</li>
				</ul>
			</div>
			<div class="submenu-segmentation-line">
				<div class="t">operation_log_6</div>
			</div>
			<div class="form-wrap">
				<ul>
					<li>
						<div class="left">数据库名</div>
						<div class="right">
							<div class="g-select">
								<select class = "db">
									<option value="" selected>device_operlog</option>
									
								</select>
								<span class="arr-icon"></span>
							</div>
						</div>
					</li>
					<li>
						<div class="left">表名</div>
						<div class="right">
							<div class="g-select">
								<select class = "table">
									<option value="1" >operation_log_6</option>
									<option value="-1"></option>
								</select>
								<span class="arr-icon"></span>
							</div>
						</div>
					</li>
					<li>
						<div class="left">&nbsp;</div>
						<div class="right">
							<div class="g-checkbox"><input type="checkbox"  name="" value="" class="tongbu" /></div><label for="tongbu">是否同步</label>
						</div>
					</li>
					<li>
						<form action="">
						<!-- <div class="left">&nbsp;</div> -->
						<div class="right short-select">
							<span class="fl">
							同步周期
							<input type="text" class="cycle-input g-input" name="cycle-num" value=""/>
							</span>
							<div class="g-select">
								<select class = "cycle">
									<option value="-1">请选择</option>
								</select>	
								<div class="arr-icon"></div>
							</div>
						</div>
						</form>
					</li>
				</ul>
			</div>
			<div class="submenu-segmentation-line">
				<div class="t">operation_log_7</div>
			</div>
			<div class="form-wrap">
				<ul>
					<li>
						<div class="left">数据库名</div>
						<div class="right">
							<div class="g-select">
								<select class = "db">
									<option value="" selected>device_operlog</option>
									
								</select>
								<span class="arr-icon"></span>
							</div>
						</div>
					</li>
					<li>
						<div class="left">表名</div>
						<div class="right">
							<div class="g-select">
								<select class = "table">
									<option value="1" >operation_log_7</option>
									<option value="-1"></option>
								</select>
								<span class="arr-icon"></span>
							</div>
						</div>
					</li>
					<li>
						<div class="left">&nbsp;</div>
						<div class="right">
							<div class="g-checkbox"><input type="checkbox"  name="" value="" class="tongbu" /></div><label for="tongbu">是否同步</label>
						</div>
					</li>
					<li>
						<form action="">
						<!-- <div class="left">&nbsp;</div> -->
						<div class="right short-select">
							<span class="fl">
							同步周期
							<input type="text" class="cycle-input g-input" name="cycle-num" value=""/>
							</span>
							<div class="g-select">
								<select class = "cycle">
									<option value="-1">请选择</option>
								</select>	
								<div class="arr-icon"></div>
							</div>
						</div>
						</form>
					</li>
				</ul>
			</div>
</div>
		  <div class="right-content hide">
			<div class="submenu-segmentation-line">
				<div class="t">bin_state</div>
			</div>
			<div class="form-wrap">
				<ul>
					<li>
						<div class="left">数据库名</div>
						<div class="right">
							<div class="g-select">
								<select class = "db">
									<option value="" selected>device_state</option>
								</select>
								<span class="arr-icon"></span>
							</div>
						</div>
					</li>
					<li>
						<div class="left">表名</div>
						<div class="right">
							<div class="g-select">
								<select class = "table">
									<option value="1" >bin_state</option>
									<option value="-1">请选择</option>
								</select>
								<span class="arr-icon"></span>
							</div>
						</div>
					</li>
					<li>
						<div class="left">&nbsp;</div>
						<div class="right">
							<div class="g-checkbox"><input type="checkbox"  name="" value="" class="tongbu" /></div><label for="tongbu">是否同步</label>
						</div>
					</li>
					<li>
						<form action="">
						<!-- <div class="left">&nbsp;</div> -->
						<div class="right short-select">
							<span class="fl">
							同步周期
							<input type="text" class="cycle-input g-input" name="cycle-num" value=""/>
							</span>
							<div class="g-select">
								<select class = "cycle">
									<option value="-1">请选择</option>
								</select>	
								<div class="arr-icon"></div>
							</div>
						</div>
						</form>
					</li>
				</ul>
			</div>
			<div class="submenu-segmentation-line">
				<div class="t">bookrack_state</div>
			</div>
			<div class="form-wrap">
				<ul>
					<li>
						<div class="left">数据库名</div>
						<div class="right">
							<div class="g-select">
								<select class = "db">
									<option value="" selected>device_state</option>
								</select>
								<span class="arr-icon"></span>
							</div>
						</div>
					</li>
					<li>
						<div class="left">表名</div>
						<div class="right">
							<div class="g-select">
								<select class = "table">
									<option value="1" >bookrack_state</option>
									<option value="-1">请选择</option>
								</select>
								<span class="arr-icon"></span>
							</div>
						</div>
					</li>
					<li>
						<div class="left">&nbsp;</div>
						<div class="right">
							<div class="g-checkbox"><input type="checkbox"  name="" value="" class="tongbu" /></div><label for="tongbu">是否同步</label>
						</div>
					</li>
					<li>
						<form action="">
						<!-- <div class="left">&nbsp;</div> -->
						<div class="right short-select">
							<span class="fl">
							同步周期
							<input type="text" class="cycle-input g-input" name="cycle-num" value=""/>
							</span>
							<div class="g-select">
								<select class = "cycle">
									<option value="-1">请选择</option>
								</select>	
								<div class="arr-icon"></div>
							</div>
						</div>
						</form>
					</li>
				</ul>
			</div>
			<div class="submenu-segmentation-line">
				<div class="t">ext_state</div>
			</div>
			<div class="form-wrap">
				<ul>
					<li>
						<div class="left">数据库名</div>
						<div class="right">
							<div class="g-select">
								<select class = "db">
									<option value="" selected>device_state</option>
								</select>
								<span class="arr-icon"></span>
							</div>
						</div>
					</li>
					<li>
						<div class="left">表名</div>
						<div class="right">
							<div class="g-select">
								<select class = "table">
									<option value="1" >ext_state</option>
									<option value="-1"></option>
								</select>
								<span class="arr-icon"></span>
							</div>
						</div>
					</li>
					<li>
						<div class="left">&nbsp;</div>
						<div class="right">
							<div class="g-checkbox"><input type="checkbox"  name="" value="" class="tongbu" /></div><label for="tongbu">是否同步</label>
						</div>
					</li>
					<li>
						<form action="">
						<!-- <div class="left">&nbsp;</div> -->
						<div class="right short-select">
							<span class="fl">
							同步周期
							<input type="text" class="cycle-input g-input" name="cycle-num" value=""/>
							</span>
							<div class="g-select">
								<select class = "cycle">
									<option value="-1">请选择</option>
								</select>	
								<div class="arr-icon"></div>
							</div>
						</div>
						</form>
					</li>
				</ul>
			</div>
			<div class="submenu-segmentation-line">
				<div class="t">localoperator</div>
			</div>
			<div class="form-wrap">
				<ul>
					<li>
						<div class="left">数据库名</div>
						<div class="right">
							<div class="g-select">
								<select class = "db">
									<option value="" selected>device_state</option>
									
								</select>
								<span class="arr-icon"></span>
							</div>
						</div>
					</li>
					<li>
						<div class="left">表名</div>
						<div class="right">
							<div class="g-select">
								<select class = "table">
									<option value="1" >localoperator</option>
									<option value="-1"></option>
								</select>
								<span class="arr-icon"></span>
							</div>
						</div>
					</li>
					<li>
						<div class="left">&nbsp;</div>
						<div class="right">
							<div class="g-checkbox"><input type="checkbox"  name="" value="" class="tongbu" /></div><label for="tongbu">是否同步</label>
						</div>
					</li>
					<li>
						<form action="">
						<!-- <div class="left">&nbsp;</div> -->
						<div class="right short-select">
							<span class="fl">
							同步周期
							<input type="text" class="cycle-input g-input" name="cycle-num" value=""/>
							</span>
							<div class="g-select">
								<select class = "cycle">
									<option value="-1">请选择</option>
								</select>	
								<div class="arr-icon"></div>
							</div>
						</div>
						</form>
					</li>
				</ul>
			</div>		
			<div class="submenu-segmentation-line">
				<div class="t">soft_state</div>
			</div>
			<div class="form-wrap">
				<ul>
					<li>
						<div class="left">数据库名</div>
						<div class="right">
							<div class="g-select">
								<select class = "db">
									<option value="" selected>device_state</option>
								</select>
								<span class="arr-icon"></span>
							</div>
						</div>
					</li>
					<li>
						<div class="left">表名</div>
						<div class="right">
							<div class="g-select">
								<select class = "table">
									<option value="1" >soft_state</option>
									<option value="-1"></option>
								</select>
								<span class="arr-icon"></span>
							</div>
						</div>
					</li>
					<li>
						<div class="left">&nbsp;</div>
						<div class="right">
							<div class="g-checkbox"><input type="checkbox"  name="" value="" class="tongbu" /></div><label for="tongbu">是否同步</label>
						</div>
					</li>
					<li>
						<form action="">
						<!-- <div class="left">&nbsp;</div> -->
						<div class="right short-select">
							<span class="fl">
							同步周期
							<input type="text" class="cycle-input g-input" name="cycle-num" value=""/>
							</span>
							<div class="g-select">
								<select class = "cycle">
									<option value="-1">请选择</option>
								</select>	
								<div class="arr-icon"></div>
							</div>
						</div>
						</form>
					</li>
				</ul>
			</div>
		</div>
		</div>
	</div>
</div>