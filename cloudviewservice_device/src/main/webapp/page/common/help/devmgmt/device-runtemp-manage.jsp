<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<style type="text/css">
	img{width:100%;height:100%;margin-top: 15px;margin-bottom: 15px}
</style>
				<ul>
					<li>
						3.6设备运行模板配置
						<div class="con-box">
							运行模板配置对系统中的运行模板进行管理（设备注册以及设备信息修改需要选择）
							<img alt="" src="${pageContext.request.contextPath}/static/images/help/image155.png">
						</div>
					</li>
					<li>
						3.6.1查询
						<div class="con-box">
							可以选择对应的设备类型，也可以输入模板名称进行模糊查询。
						</div>
					</li>
					<li>
						3.6.2新增
						<div class="con-box">
							点击新增，弹出运行模板的新增界面。如图所示
							<br/>
							<img style="height: 50%;width: 50%" alt="" src="${pageContext.request.contextPath}/static/images/help/image157.png">
							<br/>基本配置
							如图，基本配置主要填写模板ID、名称、设备类型、系统语言，是否记录日志、日志类型、大小等基本信息。
							<img style="height: 50%;width: 50%" alt="" src="${pageContext.request.contextPath}/static/images/help/image159.png">
							<br/>功能配置
							功能设置是对设备具体功能的详细配置，操作员根据实际需要来配置模板即可。
							<br/>
							<img style="height: 50%;width: 50%"  alt="" src="${pageContext.request.contextPath}/static/images/help/image161.png">
							<img style="height: 50%;width: 50%"  alt="" src="${pageContext.request.contextPath}/static/images/help/image163.png">
							<br/>超时配置
							超市配置是对设备各个超时时间进行配置以及启用的模块。
							<br/>
							<img style="height: 50%;width: 50%" alt="" src="${pageContext.request.contextPath}/static/images/help/image165.png">
							<img style="height: 50%;width: 50%" alt="" src="${pageContext.request.contextPath}/static/images/help/image167.png">
							<br/>定时任务配置
							可以对设备定时关机，财经结算等定时任务进行配置。
							<br/>
							<img style="height: 50%;width: 50%" alt="" src="${pageContext.request.contextPath}/static/images/help/image169.png">
							<br/>打印机配置
							打印机配置中可以对设备打印凭证相关进行设置。
							<br/>
							<img style="height: 50%;width: 50%" alt="" src="${pageContext.request.contextPath}/static/images/help/image171.png">
							<br/>办证配置
							<img style="height: 50%;width: 50%" alt="" src="${pageContext.request.contextPath}/static/images/help/image173.png">
							<br/>本地数据库配置 对设备上的数据库进行配置
							<br/>
							<img style="height: 50%;width: 50%" alt="" src="${pageContext.request.contextPath}/static/images/help/image175.png">
							<br/>云中心配置 设备连接云中心的配置
							<br/>
							<img style="height: 50%;width: 50%" alt="" src="${pageContext.request.contextPath}/static/images/help/image177.png">
							<br/>ACS服务配置 设备连接图书馆ACS服务的配置
							<br/>
							<img style="height: 50%;width: 50%" alt="" src="${pageContext.request.contextPath}/static/images/help/image179.png">
							
						</div>
					</li>
					<li>
						3.6.3编辑
						<div class="con-box">
							对已有的运行模板进行编辑，具体配置参见运行模板新增。
						</div>
					</li>
					<li>
						3.6.4删除
						<div class="con-box">
							点击对应运行模板的删除按钮就可以删除对应的运行模板信息。
						</div>
					</li>
					<li>
						3.6.5批量删除
						<div class="con-box">
							勾选要删除的运行模板，然后点击右上角的删除，根据提示即可批量删除运行模板信息。
						</div>
					</li>
				</ul>

<script type="text/javascript">
$(function(){
     $("img").click(function(){
     	if($(this).attr("style") && $(this).attr("style").indexOf("50%")>=0){
     		$(this).width("100%");
             $(this).height("100%");
     	}else{
     		$(this).width("50%");
             $(this).height("50%");
     	}
     	//console.log(height);
     });
 });
</script>


