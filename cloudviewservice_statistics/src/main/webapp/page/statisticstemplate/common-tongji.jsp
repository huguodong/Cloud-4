<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="renderer" content="webkit">
<title>预览统计模板</title>
<style type="text/css">
#horizontalTab2{
	table-layout:fixed;
}
.form-dialog-fix .form-wrap{
	width: 600px;
}
.form-dialog-fix .form-dialog .form-wrap .right {
    width: 450px;
}
.form-dialog-fix .form-dialog .form-wrap .right .g-inputdate{
    line-height:36px;
    vertical-align: middle;
}
.form-dialog-fix .form-dialog .form-wrap .g-select {
    width: 200px;
}
.g-textarea {
    width: 350px;
}
.search-template-md .col-4 .item10{
	width: 10%;
	float: left;
	padding-bottom: 5px;
}
.search-template-md .col-6 .item10{
	width: 10%;
	float: left;
	padding-bottom: 5px;
}

.table-form .w200{
	width: 200px !important;
}

.grayTr{
	color: red;
}

.divBorder{
	border:1px solid #f6f6f6
}

.search-template-md .col-4 .item1{
		width: 100%;
		float: left;
}

.search-template-md .col-6 .item1{
		width: 100%;
		float: left;
}
.search-template-md .col-4 .item8{
		width: 80%;
		float: left;
}
.search-template-md .col-6 .item8{
	width: 80%;
	float: left;
}
.page-title-bar .btn {
    float: right;
    padding: 0 10px;
}
.search-template-md .col-6 .item8{
		width: 30%;
		float: left;
}

.increase_time{
   /*  margin-left: 16px; */
    width: 13px;
    margin-top: -3px;
}
.statistics-show-box {
    border: 1px solid #ddd;
}
.tree-dom {
	margin:0;
    padding: 5px;
    width: 195px;
    overflow-x: auto;
    height: 600px;    
    -webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
    box-sizing: border-box;
}
.gundong{
   overflow: hidden;
   border-bottom:1px;
   height: 35px;
}
.normal-table2{
   height: 585px;
   overflow: auto;
}

/* .tbody{
   height: 365px;
   overflow-y: auto;
   overflow-x: scroll;
} */
.paging-bar {
    padding: 0 20px;
    height: 46px;
    line-height: 50px;
    border-top: 1px solid #ddd;
    background-color: #fff;
    overflow: hidden;
}
.pager-min .paging-bar {
    padding: 1px 20px;
}
.error-msg{
	color: red;
}
.form-wrap.fr {
    width: 100%;
}
div#dialogTitle {
    float: left;
    font-size: 16px;
}
.form-dialog-fix .form-dialog .form-wrap .left {
    width: 140px;
}
.form-dialog .form-wrap .left,.form-dialog .form-wrap .attr {
    /* padding-top: 3px; */
    /* float: left; */
    /* width: 100px; */
    /* color: #888; */
    white-space:nowrap;
    line-height:38px;
    vertical-align: middle;
}
.form-dialog .form-wrap {
    padding: 10px 0 40px;
}
.form-dialog label {
    padding-left: 5px;
    margin-right: 15px;
}
</style>
<!--[if IE]>
	<link rel="stylesheet" type="text/css" href="css/ie.css" />
<![endif]-->
<!--[if lt IE 9]>
	<script type="text/javascript" src="js/html5.js" ></script>
	<script type="text/javascript" src="js/ie.js" ></script>
<![endif]-->
</head>
<body>
<input type="hidden" value="${statistics_tpl_idx }" id="statistics_tpl_idx">
<div class="g-loading"></div>
<div class="equipment-leixing">
	<!-- 预览 dom 开始 -->
	<div class="page-title-bar">
		<div class="form-wrap fr">
		    <div class="head mb20" id="dialogTitle"></div>
		    <div style="float: right;">
			 	<div class="button button-search">查询</div>
				<div class="button button-reset">重置</div>
				<div class="button button-more-search">高级查询 <i class="ico icon-arrow-right-middle-1x"></i></div>
				<div class="btn" id="exportTxt" style="display: none;">导出查询Txt</div>
				<div class="btn" id="exportExcel" style="display: none;">导出查询Excel</div>
				<!-- <div class="btn cancel" id="templateCancel">取消</div> -->
			</div>
		</div>
	</div>
	<div class="template-view-content">
		<div class="search-template-md">
			<!-- col-2 可以放1个item  item 100% -->
			<!-- col-4 可以放2个item  item 50% -->
			<!-- col-6 可以放3个item  item 33.33333% -->
			<div class="col-2" id="moreSearch">
				<div class="item" id="stmpMethod">
				</div>
				<!-- <div class="item" id="colTmp" style="display: none;">
					<div class="attr" id="gcolTmp" style="display: none;">对应分组：</div>
					<div class="value" id="tmpMethod">
					</div>
				</div> -->
				<!-- <div class="text-center">
					<div class="button button-search">查询</div>
					<div class="button button-reset">重置</div>
					<div class="button button-more-search">高级查询 <i class="ico ico-arrow-top-1x"></i></div>
				</div> -->
			</div>
		</div>
		<div class="panel-md mt20">
			<div class="panel-title">
				<ul>
					<li class="active">数据统计</li>
					<li>图表统计</li>
				</ul>
			</div>
			<div class="panel-content">
				<!-- >数据统计 -->
				<div class="panel">
					<div class="statistics-show-box clearfix">
						<div class="fl left">
							<div class="box-aside-hd">查询分组</div>
							<div class="tree-dom">
								<div id="tgtree"></div>
							</div>
						</div>
						<div class="right">
							<div class="normal-table">
								<div class="gundong">
								 	<div class="normal-table2">
								   		<table width="100%" cellpadding="0" cellspacing="1" id="horizontalTab2">
											<thead>
											</thead>
											<tbody>
											</tbody>
										</table>
									</div>
									<div class="pager-min">
										<div class="paging-bar">
											<div class="left">
												<span class="fl">每页显示</span>
												<div class="g-select refresh">
													<select id="pagesize">
														<option value="" selected>25</option>
														<option value="">50</option>
														<option value="">100</option>
														<option value="">200</option>
													</select>
													<span class="arr-icon"></span>
												</div>
												<span class="t2 fl">条</span>
											</div>
											<div id="page" class="right"></div>
											<span class="total-page fr"></span>
											<span class="total-num fr"></span>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- 图形展示 -->
				<div class="panel hidden">
					<div class="switch-label-md">
						<div class="switch-label" id="gchart">
						</div>
						<!-- 图表控件 地址 http://echarts.baidu.com/index.html -->
						<div class="chart-show" style="height: 600px;overflow:auto;">
							<div class="switch-label-panel hidden">
								<div id="chart-1" class="chart-md special"></div>
							</div>
							<div class="switch-label-panel hidden">
								<div id="chart-2" class="chart-md special"></div>
							</div>
							<div class="switch-label-panel hidden">
								<div id="chart-3" class="chart-md special"></div>
							</div>
							<div class="switch-label-panel hidden">
								<div id="chart-4" class="chart-md top"></div>
							</div>
							<div class="switch-label-panel hidden">
								<div id="chart-5" class="chart-md special"></div>
							</div>
							<div class="switch-label-panel hidden">
								<div id="chart-6" class="chart-md special"></div>
							</div>
							<div class="switch-label-panel hidden">
								<div id="chart-7" class="chart-md special"></div>
							</div>
							<div class="switch-label-panel hidden">
								<div id="chart-8" class="chart-md top"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- 弹出层 -->
<div class="form-dialog-fix w-600 form-dialog-fix-search">
	<div class="shade"></div>
	<div class="form-dialog">
		<div class="title">
			<span>高级查询</span>
			<input type="reset" value="取消" class="g-cancel2 g-btn-gray">
			<input type="submit" placeholder="" class="g-submit2 g-btn-green button-search2" value="确定">
		</div>
		<div style="overflow-y:auto">
			<div class="form-wrap">
				<div class="item">
					<ul></ul>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- 对应的本地文件,必须放在最后，要不会出现页面拉长的情况 -->
<script src="${pageContext.request.contextPath }/page/statisticstemplate/js/common-tongji.js"></script>
<!-- 添加字段js -->
<script src="${pageContext.request.contextPath }/static/js/field.js"></script>
<script src="${pageContext.request.contextPath }/static/plugins/pagination/pagination.js"></script>
<script type="text/javascript">
$(function() {
	$("#horizontalTab2").tablesort().data('tablesort');
	$("#horizontalTab2 thead th.number").data('sortBy', function(th, td, sorter) {
		return parseInt(td.text(), 10);
	});
	var len = $("#horizontalTab2").find("thead tr").eq(0).children().length-1;
	$("#horizontalTab2 thead th.number").eq(len).click();
	$("#horizontalTab2 thead th.number").eq(len).click();
});
</script>
</body>
</html>