<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<style type="text/css">
	img{width:100%;height:100%;margin-top: 15px;margin-bottom: 15px}
</style>
				<ul>
					<li>
						三、设备管理
						<div class="con-box">
							设备管理模块包括五个部分：设备管理、设备类型、设备分组、设备监控模板配置、设备监控模板配置、设备运行模板配置、设备数据同步模板配置。
						</div>
					</li>
					<li>
						3.1设备管理
						<div class="con-box">
							设备管理显示的是图书馆所有的设备列表，所有的设备都必须通过设备注册并且成功之后才能显示到此页面中，在设备管理中可以对设备进行修改删除等操作。如图
							<img alt="" src="${pageContext.request.contextPath}/static/images/help/image093.png">
						</div>
					</li>
					<li>
						3.1.1设备查询
						<div class="con-box">
							在设备管理页面中的搜索栏中可以选择设备类型，设备分组，或者输入ID、设备名称进行模糊查询。
						</div>
					</li>
					<li>
						3.1.2编辑设备
						<div class="con-box">
							点击对应设备的编辑按钮，跳转到编辑页面，如图
							<img alt="" src="${pageContext.request.contextPath}/static/images/help/image095.png">
							在设备编辑页面，可以查看设备的基本信息并且修改，其中设备特征码、所属馆ID图书馆名称、设备ID不允许修改。设备名称，设备类型，设备分组等其他信息均可以修改。
							设备的模板配置可以选择系统中已经录入的模板，也可以根据需要，在已有的模板基础上进行自定义修改。具体说明参见设备的模板配置。
							点击保存之后显示修改成功，点击返回回到设备列表页面。如图
							<img alt="" src="${pageContext.request.contextPath}/static/images/help/image097.png">
						</div>
					</li>
					<li>
						3.1.3删除设备
						<div class="con-box">
							点击对应设备的删除按钮，根据提示即可删除设备。
							<img alt="" src="${pageContext.request.contextPath}/static/images/help/image099.png">
						</div>
					</li>
					<li>
						3.1.4批量删除设备
						<div class="con-box">
							勾选需要删除的设备，然后点击搜索栏上的删除，根据提示即可批量删除设备。
							<img alt="" src="${pageContext.request.contextPath}/static/images/help/image101.png">
						</div>
					</li>
					<li>
						3.1.4批处理
						<div class="con-box">
							批处理功能可以将设备批量转移到其他图书馆名下。此功能只有云平台管理员，以及云平台维护人员才能使用，批处理页面如图。
							<img alt="" src="${pageContext.request.contextPath}/static/images/help/image103.png">
							操作员需要根据要求填入需要转出设备的原始馆ID，以及需要转入设备的馆ID，如果设备太多，还可以填写设备类型，设备分组，设备ID或者名称进行进一步筛选，如图
							<img alt="" src="${pageContext.request.contextPath}/static/images/help/image105.png">
							系统会将符合条件的设备列出来，操作员将需要转移的设备进行勾选，然后点击右上角的转移到新馆即可。
							注意：一般设备转移之后，设备IP会改变，因此，在转移的时候可以将对应设备的IP进行修改，转移到新馆之后，系统会将设备新的IP保存。
						</div>
					</li>
				</ul>




