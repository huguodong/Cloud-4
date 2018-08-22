<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<style type="text/css">
	img{width:100%;height:100%;margin-top: 15px;margin-bottom: 15px}
</style>
				<ul>
					<li>
						3.7设备数据同步模板配置
						<div class="con-box">
						</div>
					</li>
					<li>
						3.7.1查询
						<div class="con-box">
							可以选择对应的模板ID，也可以输入模板名称进行模糊查询。<br/>
							<img style="height: 50%;width: 50%" alt="" src="${pageContext.request.contextPath}/static/images/help/image181.png">
						</div>
					</li>
					<li>
						3.7.2新增
						<div class="con-box">
							基本信息：填写模板ID、模板名称、设备类型等主要信息
							其他：对应设备表的具体设置
							<br/>
							<img style="height: 50%;width: 50%" alt="" src="${pageContext.request.contextPath}/static/images/help/image183.png">
							<br/>基本配置
							如图，基本配置主要填写模板ID、名称、设备类型、系统语言，是否记录日志、日志类型、大小等基本信息。
							<img style="height: 50%;width: 50%" alt="" src="${pageContext.request.contextPath}/static/images/help/image185.png">
							<img style="height: 50%;width: 50%"  alt="" src="${pageContext.request.contextPath}/static/images/help/image187.png">
						
						</div>
					</li>
					<li>
						3.7.3编辑
						<div class="con-box">
							对已有的数据同步模板进行编辑，具体配置参见运行模板新增。
						</div>
					</li>
					<li>
						3.7.4删除
						<div class="con-box">
							点击对应数据同步模板的删除按钮就可以删除对应的数据同步模板信息。
						</div>
					</li>
					<li>
						3.7.5批量删除
						<div class="con-box">
							勾选要删除的数据同步模板，然后点击右上角的删除，根据提示即可批量删除数据同步模板信息。
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


