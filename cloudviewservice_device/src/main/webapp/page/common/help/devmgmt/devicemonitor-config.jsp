<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<style type="text/css">
	img{width:100%;height:100%;margin-top: 15px}
</style>
				<ul>
					<li>
						3.4设备监控模板配置
						<div class="con-box">
							监控模板配置对系统中的监控模板进行管理（设备注册以及设备信息修改需要选择）
							<img alt="" src="${pageContext.request.contextPath}/static/images/help/image123.png">
						</div>
					</li>
					<li>
						3.4.1查询
						<div class="con-box">
							可以选择对应的设备类型，也可以输入模板名称进行模糊查询。
						</div>
					</li>
					<li>
						3.4.2新增
						<div class="con-box">
							如图所示，点击新增弹出新增界面
							<img alt="" src="${pageContext.request.contextPath}/static/images/help/image125.png">
								新增模板的时候需要先选择设备类型，不同的设备类型对应的拥有不同的逻辑设备（设备类型对应的逻辑设备可以在设备类型菜单中配置）。
								类型一
                                                                                                监控配置根据不同的逻辑设备有不同的配置选项，如图，仅需要选择是否启用对该逻辑设备的监控以及对应的颜色即可。
							<img alt="" src="${pageContext.request.contextPath}/static/images/help/image127.png">
							类型二
							除了类型一要填写的内容以外，还需要填写报警限额。
							<img alt="" src="${pageContext.request.contextPath}/static/images/help/image129.png">
							类型三
						            超时设置，设备连接系统超时的时间长度。
							<img alt="" src="${pageContext.request.contextPath}/static/images/help/image131.png">
							类型四
							PLC设备中有多个设备组成， 配置的报警项也是由多个组成
							<img alt="" src="${pageContext.request.contextPath}/static/images/help/image133.png">
						</div>
					</li>
					<li>
						3.4.3编辑
						<div class="con-box">
							对已经存在的监控模板进行编辑，参见监控模板配置的新增说明
						</div>
					</li>
					<li>
						3.4.4删除
						<div class="con-box">
							点击对应监控模板的删除按钮就可以删除对应的监控模板信息。
						</div>
					</li>
					<li>
						3.4.5批量删除
						<div class="con-box">
							勾选要删除的监控模板，然后点击右上角的删除，根据提示即可批量删除监控模板信息
							<img alt="" src="${pageContext.request.contextPath}/static/images/help/image135.png">
						</div>
					</li>
				</ul>




