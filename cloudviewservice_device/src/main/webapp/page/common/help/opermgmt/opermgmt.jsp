<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<style type="text/css">
	img{width:100%;height:100%;}
</style>
				<ul>
					<li>
						一、用户管理模块
						<div class="con-box">
							用户管理模块包括四个部分：用户管理、操作员分组管理、用户鉴权模板配置和权限分组。
						</div>
					</li>
					<li>
						1.1  用户管理
						<div class="con-box">
							点击用户管理，系统管理员会看到以下界面：
							<img alt="" src="${pageContext.request.contextPath}/static/images/help/image001.png">
							<br/>
							在此页面里，系统管理员可以新增、修改、删除和按类型（用户名及姓名）查询相应的用户记录。
						</div>
					</li>
					<li>
						1.1.1  新增用户
						<div class="con-box">
							新增用户界面如下：
							<img alt="" src="${pageContext.request.contextPath}/static/images/help/image003.png">
							<br/>
							带*号的为必填项，系统管理员在填写完用户信息后，可以点击保存按钮，将编辑好的用户数据保存到数据库中。
						</div>
					</li>
					<li>
						1.1.2 修改用户
						<div class="con-box">
							修改用户界面如下：
							<img alt="" src="${pageContext.request.contextPath}/static/images/help/image005.png">
							<br/>
							在此页面，系统管理员可以对用户的信息进行修改，并可以密码重置，重置后的密码为888888(在系统里可以配置).
						</div>
					</li>
					<li>
						1.1.3  删除用户
						<div class="con-box">
							删除用户界面如下：
							<img alt="" src="${pageContext.request.contextPath}/static/images/help/image007.png">
							<br/>
							系统管理员点击删除按钮，则该条用户记录的状态会变为未激活。
						</div>
					</li>
					<li>
						1.1.4  检索用户
						<div class="con-box">
							系统管理员在选择某个类型后，可以输入值，对用户记录进行筛选，点击查询后，系统会将符合的用户记录显示出来，界面如下：<br/>
							<img alt="" src="${pageContext.request.contextPath}/static/images/help/image009.png">
							<br/>
						</div>
					</li>
				</ul>




