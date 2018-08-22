<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<style type="text/css">
	img{width:100%;height:100%;margin-top: 15px;margin-bottom: 15px}
</style>
				<ul>
					<li>
						六、设备注册
						<div class="con-box">
							设备在云平台注册录入信息。
						</div>
					</li>
					<li>
						6.1访问地址
						<div class="con-box">
							设备注册的连接地址为：
							${url}/ cloudviewservice_register /device/connect?deviceCode=${deviceCode}
							${url} 为访问云中心的地址
							${deviceCode}为设备的设备码<br/>
							如：
							http://127.0.0.1:8080/cloudviewservice_register/device/connect?deviceCode=GFBFM-BX4DR

							<br/>
						</div>
					</li>
					<li>
						6.2设备检测
						<div class="con-box">
						如果设备已经注册过，访问上面的地址之后会跳转到对应设备的界面，否则跳转到云中心的登陆界面，登陆之后填写一些相关信息，然后注册，注册成功之后设备会根据设备类型自动跳转到对应的界面。
						</div>
					</li>
					<li>
						6.3设备注册
						<div class="con-box">
							操作员登陆成功之后，页面如图所示<br/>
							<img alt="" src="${pageContext.request.contextPath}/static/images/help/image215.png">
							<br/>
							操作员根据页面提示，填写相关信息，点击注册即可。
							设备特征码：为自动填写，不可修改。
							图书馆ID：输入图书馆ID之后，系统会根据ID查询图书馆的名称，然后显示。
							每个设备必须要选择对应的模板配置，模板配置参见设备管理中的各个模板配置。
							操作员还可以在注册的时候根据实际情况，对硬件模板，或者运行模板进行自定义的修改。如果修改过之后，数据跟模板不一致，则显示的按钮为绿色，如图所示
							<br/>
							<img alt="" src="${pageContext.request.contextPath}/static/images/help/image217.png">						
							<br/>
								ACS配置，点击添加ACS配置，可以添加设备连接图书馆ACS服务配置详情，根据实际情况进行填写，如图
							<img alt="" src="${pageContext.request.contextPath}/static/images/help/image219.png">						
						</div>
					</li>
					<li>
						6.4注册结果
						<div class="con-box">
								注册成功之后，会自动进行本地服务配置，成功之后进行自动转到对应机器的界面。<br/>
							<img alt="" src="${pageContext.request.contextPath}/static/images/help/image221.png">
							<br/>
								注册成功，但是本地初始化时出现问题
							<br/>
							<img alt="" src="${pageContext.request.contextPath}/static/images/help/image223.png">
							<br/>其他结果，见具体提示<br/>
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
     });
     $("img").trigger("click");
 });
</script>



