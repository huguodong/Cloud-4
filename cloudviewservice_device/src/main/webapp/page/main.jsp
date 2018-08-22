<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<!--[if IE 8]><html class="ie8 oldie"><![endif]-->
<!--[if IE 9]><html class="ie9 oldie"><![endif]-->
<!--[if gt IE 9]><!-->
<html>
<!--<![endif]-->
<head>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=HLY8UYq0rnxe47hg9oNzmV2F7yk1wzai"></script>
<%@include file="common/global.jsf"%>
<style>
* a {
	color: #000;
}
.anchorBL{display:none;}
</style>
</head>
<body>
	<%@include file="common/header.jsf"%>

	<div class="main-content">
		<div class="left-side">
			<div class="control-btn"></div>
			<div class="absolute-wrap">
				<div class="title">
					<spring:message code="main.jsp.menu"></spring:message>
				</div>
				<div class="item" id="mainIndex">
					<div class="t icon1">
						<a href="javascript:void(0)" onclick="gotoMain()"><spring:message
								code="main.jsp.index" /></a>
					</div>
				</div>
				<!-- 			<shiro:hasPermission name="010101"><%-- 用户管理 --%> -->
				<!-- 				<div class="item"> -->
				<!-- 					<div class="t icon2"><spring:message code="main.jsp.usermgmt"/><span class="arr-icon"></span></div> -->
				<!-- 					<ul> -->
				<!-- 						<shiro:hasPermission name="01010101"><%--用户管理 --%> -->
				<!-- 							<li><a href="javascript:void(0)" data-url="${pageContext.request.contextPath}/operator/main"><spring:message code="main.jsp.usermgmt"/></a></li> -->
				<!-- 						</shiro:hasPermission> -->
				<!-- 						<shiro:hasPermission name="01020102"><%--用户组管理 --%> -->
				<!-- 							<li><a href="javascript:void(0)" data-url="${pageContext.request.contextPath}/opergroup/main"><spring:message code="main.jsp.opergroupmgmt"/></a></li> -->
				<!-- 						</shiro:hasPermission> -->
				<!-- 						<shiro:hasPermission name="01020203"><%--密码模板管理 --%> -->
				<!-- 							<li><a href="javascript:void(0)" data-url="${pageContext.request.contextPath}/soxtemp/main"><spring:message code="main.jsp.soxtempmgmt"/></a></li> -->
				<!-- 						</shiro:hasPermission> -->
				<!-- 						<shiro:hasPermission name="01020204"> -->
				<!-- 							<li><a href="javascript:void(0)" data-url="${pageContext.request.contextPath}/servgroup/main"><spring:message code="main.jsp.authgroup"/></a></li> -->
				<!-- 						</shiro:hasPermission> -->
				<!-- 					</ul> -->
				<!-- 				</div> -->
				<!-- 			</shiro:hasPermission> -->
				<!-- 			<shiro:hasPermission name="010201"><%-- 图书馆管理 --%> -->
				<!-- 				<div class="item"> -->
				<!-- 					<div class="t icon3"><spring:message code="main.jsp.libmgmt"/><span class="arr-icon"></span></div> -->
				<!-- 					<ul> -->
				<!-- 						<shiro:hasPermission name="01020201"> -->
				<!-- 							<li><a href="javascript:void(0)" data-url="${pageContext.request.contextPath}/library/main"><spring:message code="main.jsp.libmgmt"/></a></li> -->
				<!-- 						</shiro:hasPermission> -->
				<!-- 						<shiro:hasPermission name="01020202"> -->
				<!-- 							<shiro:hasRole name="1"> -->
				<!-- 								<li><a href="javascript:void(0)" data-url="${pageContext.request.contextPath}/libtempconf/main"><spring:message code="main.jsp.libtempmgmt"/></a></li> -->
				<!-- 							</shiro:hasRole> -->
				<!-- 						</shiro:hasPermission> -->
				<!-- 						<shiro:hasPermission name="01020301"> -->
				<!-- 							<li><a href="javascript:void(0)" data-url="${pageContext.request.contextPath}/ascconfig/main"><spring:message code="main.jsp.acstempmgmt"/></a></li> -->
				<!-- 						</shiro:hasPermission> -->
				<!-- 						<shiro:hasPermission name="01020302"> -->
				<!-- 							<li><a href="javascript:void(0)" data-url="${pageContext.request.contextPath}/ReaderType/ReaderCard"><spring:message code="main.jsp.readertypemgmt"/></a></li> -->
				<!-- 						</shiro:hasPermission> -->
				<!-- 						<shiro:hasPermission name="01020313"> -->
				<!-- 							<li><a href="javascript:void(0)" data-url="${pageContext.request.contextPath}/ReaderType/SystemCard"><spring:message code="main.jsp.syscardmgmt"/></a></li> -->
				<!-- 						</shiro:hasPermission> -->
				<!-- 					</ul> -->
				<!-- 				</div> -->
				<!-- 			</shiro:hasPermission> -->
				<!-- 			<shiro:hasPermission name="010202"><%-- 设备管理--%> -->
				<!-- 				<div class="item"> -->
				<!-- 					<div class="t icon4"><spring:message code="main.jsp.devicemgmt"/><span class="arr-icon"></span></div> -->
				<!-- 						<ul> -->
				<!-- 						<shiro:hasPermission name="01020305"> -->
				<!-- 							<li><a href="javascript:void(0)" data-url="${pageContext.request.contextPath}/device/main"><spring:message code="main.jsp.devicemgmt"/></a></li> -->
				<!-- 						</shiro:hasPermission> -->
				<!-- 						<shiro:hasPermission name="01020312"> -->
				<!-- 							<li><a href="javascript:void(0)" data-url="${pageContext.request.contextPath}/metadevicetype/main"><spring:message code="main.jsp.devicetypemgmt"/></a></li> -->
				<!-- 						</shiro:hasPermission> -->
				<!-- 						<shiro:hasPermission name="01020306"> -->
				<!-- 							<li><a href="javascript:void(0)" data-url="${pageContext.request.contextPath}/devicegroup/main"><spring:message code="main.jsp.devicegroupmgmt"/></a></li> -->
				<!-- 						</shiro:hasPermission> -->
				<!-- 						<shiro:hasPermission name="01020320"> -->
				<!-- 							<li><a href="javascript:void(0)" data-url="${pageContext.request.contextPath}/devicedisplay/main"><spring:message code="main.jsp.devicedisplaymgmt"/></a></li> -->
				<!-- 						</shiro:hasPermission> -->
				<!-- 						<shiro:hasPermission name="01020307"> -->
				<!-- 							<li><a href="javascript:void(0)" data-url="${pageContext.request.contextPath}/devicemonconf/main"><spring:message code="main.jsp.devmonitortempmgmt"/></a></li> -->
				<!-- 						</shiro:hasPermission> -->
				<!-- 						<shiro:hasPermission name="01020308"> -->
				<!-- 							<li><a href="javascript:void(0)" data-url="${pageContext.request.contextPath}/deviceext/main"><spring:message code="main.jsp.devconftempmgmt"/></a></li> -->
				<!-- 						</shiro:hasPermission> -->
				<!-- 						<shiro:hasPermission name="01020309"> -->
				<!-- 							<li><a href="javascript:void(0)" data-url="${pageContext.request.contextPath}/devicerun/main"><spring:message code="main.jsp.devruntempmgmt"/></a></li> -->
				<!-- 						</shiro:hasPermission> -->
				<!-- 						<shiro:hasPermission name="01020310"> -->
				<!-- 							<li><a href="javascript:void(0)" data-url="${pageContext.request.contextPath}/dbsync/main"><spring:message code="main.jsp.devdatasyncmgmt"/></a></li> -->
				<!-- 						</shiro:hasPermission> -->
				<!--<li><a href="javascript:void(0)" data-url="${pageContext.request.contextPath}/page/devmgmt/mongoInstance.jsp">状态数据库管理</a></li>-->
				<!-- 						</ul> -->
				<!-- 				</div> -->
				<!-- 			</shiro:hasPermission> -->
				<!-- 			<shiro:hasPermission name="010801"><%-- 书架管理--%> -->
				<!-- 				<div class="item"> -->
				<!-- 					<div class="t icon7"><spring:message code="main.jsp.shelfmgmt"/><span class="arr-icon"></span></div> -->
				<!-- 						<ul> -->
				<!-- 							<li><a href="javascript:void(0)" data-url="${pageContext.request.contextPath}/bookshelflayer/main">层架标管理</a></li> -->
				<!-- 							<li><a href="javascript:void(0)" data-url="${pageContext.request.contextPath}/bookshelf/main">书架管理</a></li> -->
				<!-- 							<li><a href="javascript:void(0)" data-url="${pageContext.request.contextPath}/shelfGroup/main">书架分组</a></li> -->
				<!-- 						</ul> -->
				<!-- 				</div> -->
				<!-- 			</shiro:hasPermission> -->
				<!-- 			<shiro:hasPermission name="010901"> -->
				<!-- 				<div class="item"> -->
				<!-- 				<div class="t icon5"><spring:message code="main.jsp.systemmgmt"/><span class="arr-icon"></span></div> -->
				<!-- 					<ul> -->
				<!-- 						<shiro:hasPermission name="01020402"> -->
				<!-- 							<li><a href="javascript:void(0)" data-url="${pageContext.request.contextPath}/database/main"><spring:message code="main.jsp.dbbakup"/></a></li> -->
				<!-- 						</shiro:hasPermission> -->
				<!-- 						<shiro:hasPermission name="01020401"> -->
				<!-- 							<li><a href="javascript:void(0)" data-url="${pageContext.request.contextPath}/systemlog/main"><spring:message code="main.jsp.systemlog"/></a></li> -->
				<!-- 						</shiro:hasPermission> -->
				<!-- 						<shiro:hasPermission name="01020408"> -->
				<!-- 							<li><a href="javascript:void(0)" data-url="${pageContext.request.contextPath}/database/mainext1">数据库备份（馆）</a></li> -->
				<!-- 						</shiro:hasPermission> -->
				<!-- 					</ul> -->
				<!-- 				</div> -->
				<!-- 			</shiro:hasPermission> -->
				<!-- 			<c:if test="${sessionScope.oper.operator_type=='1'}"> -->
				<!-- 				<div class="item"> -->
				<!-- 					<div class="t icon6"><spring:message code="main.jsp.updgrademgmt"/><span class="arr-icon"></span></div> -->
				<!-- 					<ul> -->
				<!-- 						<li><a href="javascript:void(0)" data-url="${pageContext.request.contextPath}/upgrade/main"><spring:message code="main.jsp.updgradetempmgmt"/></a></li> -->
				<!-- 					</ul> -->
				<!-- 				</div> -->
				<!-- 			</c:if> -->
			</div>
		</div>
		<div id="mainframe">
			<div class="page-title-bar">
				<span class="title"><spring:message code="main.jsp.index" /></span>
				<div class="today">今天是 2016年5月12日 星期四</div>
			</div>
			<div class="main main-v2">
				<div class="main-page main-page-v2">
					<div class="title">欢迎使用海恒智能云平台系统</div>
					<div class="version">版本 V 1.0</div>
					<div class="company">深圳市海恒智能技术有限公司</div>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		$(function() {
			(function mainHeightController() {
				var winH = $(window).height();
				var headerH = $(".g-header").outerHeight();
				var pageTitleBar = $(".page-title-bar").outerHeight();
				var pagingBarH = $(".paging-bar").outerHeight();

				$(".main-page").css(
						{
							"min-height" : winH - headerH - pagingBarH
									- pageTitleBar - 40
						});
			})();

			// 	var url = sessionStorage.getItem("currentMenu");
			// 	if(!_.isEmpty(url)){
			// 		$("ul li a[data-url='"+url+"']").closest(".item").find("div.t").trigger("click");
			// 		$("ul li a[data-url='"+url+"']").trigger("click");
			// 	}

			//设置服务器时间
			$.ajax({
				url : "${pageContext.request.contextPath}/login/getTime",
				type : "GET",
			}).done(function(data) {
				if (data) {
					$(".today").html(data.result);
				}
			});
		});
	<%--跳转到之前访问的菜单--%>
		function toUrl() {

			var url = sessionStorage.getItem("currentMenu");
			if (!_.isEmpty(url)) {
				$("ul li a[data-url='" + url + "']").closest(".item").find(
						"div.t").trigger("click");
				$("ul li a[data-url='" + url + "']").trigger("click");
			}
		}

		$(document).on("click", "ul li a", function(data) {
			var url = $(this).attr("data-url");
	<%--保存当前访问菜单--%>
		sessionStorage.setItem("currentMenu", url);
			$("#mainframe").load(url, "", function() {
				$(".form-dialog-fix .form-dialog").each(function() {
					$(this).attr("date-right", $(this).css("right"));
				});

			});
		})

		function gotoMain() {
			sessionStorage.removeItem("currentMenu");
			window.location.href = "${pageContext.request.contextPath}/login/main";
		}

		$(function() {
			getUserMenu();
		})
	<%-- 获取用户的菜单 --%>
		function getUserMenu() {
			$
					.ajax(
							{
								url : "${pageContext.request.contextPath}/login/getUserMenus",
								type : "POST",
								data : ""
							})
					.done(
							function(data) {
								if (data != null && data.state) {
									var list = data.result;
									var html = "";
									for (var i = 0; i < list.length; i++) {
										var item = list[i];
										var subMenu = item.subMenu;

										if (subMenu != null) {
											var iconClass = item.menu_mark == null ? "icon1"
													: item.menu_mark;
											html += "<div class=\"item\">";
											html += "<div class=\"t "+iconClass+"\">"
													+ item.name
													+ "<span class=\"arr-icon\"></span></div>";
											html += "<ul>";
											for (var j = 0; j < subMenu.length; j++) {
												var sitem = subMenu[j];
												html += "	<li><a href=\"javascript:void(0)\" data-url=\"${pageContext.request.contextPath}/"
														+ sitem.url
														+ "\">"
														+ sitem.name
														+ "</a></li>";
											}
											html += "</ul>";
											html += "</div>";
										}
									}
									$("#mainIndex").after(html);
									toUrl();
								}
							});
		}
	</script>
	<%--返回顶部图标 --%>
	<div id="topcontrol" title="回到顶部"
		style="position: fixed; bottom: 105px; right: 25px; cursor: pointer; display: block;">
		<img
			src="${pageContext.request.contextPath}/static/css/images/backTop.png"
			style="width:31px; height:31px;">
	</div>
</body>
</html>