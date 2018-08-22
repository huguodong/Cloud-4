<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<style type="text/css">
	img{width:100%;height:100%;margin-top: 15px;margin-bottom: 15px}
</style>
				<ul>
					<li>
						4.2. 系统日志模块
						<div class="con-box">
							点击系统日志模块，系统管理员会看到以下界面，在此页面里，系统管理员可以按日志类型、操作者及时间段查询相应的系统日志记录。
							<br/>
							<img alt="" src="${pageContext.request.contextPath}/static/images/help/image201.png">
						</div>
					</li>
					<li>
						4.2.1检索系统日志
						<div class="con-box">
							系统管理员在选择日志类型或操作者及某个时间段后，点击查询，系统会将符合的系统日志记录显示出来，界面如下：
							<br/>
							<img alt="" src="${pageContext.request.contextPath}/static/images/help/image203.png">
							
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



