<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<style type="text/css">
	img{width:100%;height:100%;margin-top: 15px;margin-bottom: 15px}
</style>
				<ul>
					<li>
						五、升级管理模块
						<div class="con-box">
							升级管理模块包括一个部分：升级模板配置。
						</div>
					</li>
					<li>
						5.1. 升级模板配置
						<div class="con-box">
							点击升级模板配置，系统管理员会看到以下界面，在此页面里，系统管理员可以新增、修改、删除和按版本类型及按检索类型（版本号及版本说明）查询相应的升级模板配置记录。
							<br/>
							<img alt="" src="${pageContext.request.contextPath}/static/images/help/image205.png">
						</div>
					</li>
					<li>
						5.1.1新增升级模板配置
						<div class="con-box">
							新增升级模板配置界面如下，系统管理员在填写完升级模板配置信息后，可以点击保存按钮，将编辑好的升级模板配置数据保存到数据库中（*1.远程下载路径系统会自动取得;2.版本类型需要根据具体内容填写）。
							<br/>
							<img alt="" src="${pageContext.request.contextPath}/static/images/help/image207.png">
							
						</div>
					</li>
					<li>
						5.1.2修改升级模板配置
						<div class="con-box">
							修改升级模板配置界面如下，在此页面，系统管理员可以对升级模板配置的信息进行修改。<br/>
							<img alt="" src="${pageContext.request.contextPath}/static/images/help/image209.png">
						</div>
					</li>
					<li>
						5.1.3删除升级模板配置
						<div class="con-box">
							删除升级模板配置界面如下，系统管理员点击删除按钮，则该条升级模板配置记录将会被删除。<br/>
							<img alt="" src="${pageContext.request.contextPath}/static/images/help/image211.png">
						</div>
					</li>
					<li>
						5.1.4检索升级模板配置
						<div class="con-box">
							系统管理员在选择版本类型及某个检索类型后，输入值相应值，即可对升级模板配置记录进行筛选，点击查询后，系统会将符合的升级模板配置记录显示出来，界面如下：<br/>
							<img alt="" src="${pageContext.request.contextPath}/static/images/help/image213.png">
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



