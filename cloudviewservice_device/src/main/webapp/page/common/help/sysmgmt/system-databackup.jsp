<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<style type="text/css">
	img{width:100%;height:100%;margin-top: 15px;margin-bottom: 15px}
</style>
				<ul>
					<li>
						四、系统管理模块
						<div class="con-box">
							系统管理模块包括两个部分：数据库备份和系统日志。
						</div>
					</li>
					<li>
						4.1. 数据库备份
						<div class="con-box">
							点击数据库备份，系统管理员会看到以下界面，在此页面里，系统管理员可以备份、还原、上传、下载、删除和按日期查询相应的数据库备份记录。
							<br/>
							<img alt="" src="${pageContext.request.contextPath}/static/images/help/image189.png">
						</div>
					</li>
					<li>
						4.1.1备份数据库
						<div class="con-box">
							备份数据库界面如下，系统管理员点击备份后，数据库里的数据将会被备份zip文件，这时管理员可以下载或者还原（就是还原当前文件的数据到数据库）及删除该文件。
							<br/>
							<img alt="" src="${pageContext.request.contextPath}/static/images/help/image191.png">
							
						</div>
					</li>
					<li>
						4.1.2上传数据文件
						<div class="con-box">
							上传数据文件界面如下：<br/>
							<img alt="" src="${pageContext.request.contextPath}/static/images/help/image193.png">
							<br/>用户在选择要上传的zip格式的文件后，点击上传，即可上传数据文件。
							文件格式为：<br/>
							<img alt="" src="${pageContext.request.contextPath}/static/images/help/image195.png">
						</div>
					</li>
					<li>
						4.1.3删除数据文件
						<div class="con-box">
							删除数据文件如下，系统管理员点击删除按钮，则该条数据库文件将会被删除。<br/>
							<img alt="" src="${pageContext.request.contextPath}/static/images/help/image197.png">
						</div>
					</li>
					<li>
						4.1.4检索数据文件
						<div class="con-box">
							系统管理员选定某个时间段，点击查询后，系统会将符合条件的数据库文件显示出来，界面如下：<br/>
							<img alt="" src="${pageContext.request.contextPath}/static/images/help/image199.png">
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



