<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<style type="text/css">
	img{width:100%;height:100%;margin-top: 15px;margin-bottom: 15px}
</style>
				<ul>
					<li>
						3.5设备硬件模板配置
						<div class="con-box">
							硬件模板配置对系统中的硬件模板进行管理（设备注册以及设备信息修改需要选择）
							<img alt="" src="${pageContext.request.contextPath}/static/images/help/image137.png">
						</div>
					</li>
					<li>
						3.5.1查询
						<div class="con-box">
							可以选择对应的设备类型，也可以输入模板名称进行模糊查询。
						</div>
					</li>
					<li>
						3.5.2新增
						<div class="con-box">
							点击新增，弹出硬件模板的新增界面。如图所示
							<img alt="" src="${pageContext.request.contextPath}/static/images/help/image139.png">
							基本能配置，需要填写模板的ID，模板名称，以及设备类型，需要先选择设备类型，不同的设备类型对应的拥有不同的逻辑设备（设备类型对应的逻辑设备可以在设备类型菜单中配置）。
							不同的逻辑设备基本选项如下图，外设部件，通讯方式两个选项，外设部件是事先已经录入系统中的，通讯方式主要有COM、USB、LPT、NET这四个选项，不同的选项需要填写的参数不同，其中LPT为打印机所用，不需要填写其他参数。如图所示
							<img alt="" src="${pageContext.request.contextPath}/static/images/help/image141.png">
							<img style="height: 50%;width: 50%" alt="" src="${pageContext.request.contextPath}/static/images/help/image145.png">
							<img style="height: 50%;width: 50%" alt="" src="${pageContext.request.contextPath}/static/images/help/image143.png">
							<img style="height: 50%;width: 50%" alt="" src="${pageContext.request.contextPath}/static/images/help/image147.png">
							<img style="height: 50%;width: 50%" alt="" src="${pageContext.request.contextPath}/static/images/help/image149.png">
							<br/>收钞机除了基本的选项以外，还需要选择收款通道，如图所示
							<img alt="" src="${pageContext.request.contextPath}/static/images/help/image151.png">
							自助图书馆PLC，选择了外设部件之后，还需要填写书盒相关的信息，如图所示
							<img alt="" src="${pageContext.request.contextPath}/static/images/help/image153.png">
						</div>
					</li>
					<li>
						3.5.3编辑
						<div class="con-box">
							对已经存在的硬件模板进行编辑，参见硬件模板配置的新增说明
						</div>
					</li>
					<li>
						3.5.4删除
						<div class="con-box">
							点击对应硬件模板的删除按钮就可以删除对应的硬件模板信息。
						</div>
					</li>
					<li>
						3.5.5批量删除
						<div class="con-box">
							勾选要删除的硬件模板，然后点击右上角的删除，根据提示即可批量删除硬件模板信息。
						</div>
					</li>
				</ul>




