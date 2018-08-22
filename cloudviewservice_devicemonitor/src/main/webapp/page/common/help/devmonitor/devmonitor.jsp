<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<style type="text/css">
	img{width:100%;height:100%;margin-top: 15px;margin-bottom: 15px}
</style>
				<ul>
					<li>
						七、设备监控管理
						<div class="con-box">
							远程监控自助借还设备状态，及获取远程设备的信息进行统计分析等。
						</div>
					</li>
					<li>
						7.1访问地址
						<div class="con-box">
							设备监控管理的连接地址为：
							${url}/ cloudviewservice_devicemonitor 为访问云中心的地址
							<br/>如：http://127.0.0.1:8080/ cloudviewservice_devicemonitor

							<br/>
						</div>
					</li>
					<li>
						7.2 监控管理
						<div class="con-box">
						主界面如下，头部左上角为根据不同角色权限可以显示对应图书馆的下拉列表右上角可根据设备名查询，如果设备部件出现问题，则主界面会出现告警提示；如果没有同步数据则显示灰色的无状态。
						<br/>
						<img alt="" src="${pageContext.request.contextPath}/static/images/help/image225.png">
						<br/>
						页面的左下角：可以选择切换页数的频率。30秒、60秒或者不切换。根据选定的时间间隔，每隔一定时间之后，右下角页数会自动切换，在界面上显示不同的设备。
						<br/>
						<img alt="" src="${pageContext.request.contextPath}/static/images/help/image227.png">
						</div>
					</li>
					<li>
						7.3 设备详情页面
						<div class="con-box">
							如下图所示为办证机的详细情况，显示的内容根据不同的设备不同。如下办证显示卡证箱、
							纸币箱、外设备状态、功能状态信息。而借还书机没有卡证箱和纸币箱的信息。
							如下图外设状态，出现异常的部件会显示红叉，正常的部件显示绿色打勾，灰色打叉为在配置中有该部件，但是远程设备没有同步该部件的状态信息。
							<br/>
							<img alt="" src="${pageContext.request.contextPath}/static/images/help/image229.png">
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
 });
</script>



