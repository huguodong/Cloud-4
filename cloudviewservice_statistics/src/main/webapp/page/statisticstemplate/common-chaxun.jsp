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
#horizontalTab2{table-layout:fixed;}
.form-dialog-fix .form-wrap{width: 600px;}
.form-dialog-fix .form-dialog .form-wrap .right {width: 450px;}
.form-dialog-fix .form-dialog .form-wrap .right .g-inputdate{
    line-height:36px;
    vertical-align: middle;
}
.form-dialog-fix .form-dialog .form-wrap .g-select {width: 200px;}
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
.page-title-bar .btn{
	float: right;
	padding:0 10px;
}
img.icon-picker-md{
    width: 20px;
    height: 20px;
}
.increase_time{
    /* margin-left: 16px; */
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
    overflow: auto;
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
   height: 580px;
   overflow-x: auto;
   overflow-y: auto;
}
.normal-table td :not(:first-child) {
    padding: 8px 0;
    width: 150px;
    white-space: nowrap;
    text-overflow: ellipsis;
    -o-text-overflow: ellipsis;
    -moz-binding: url('ellipsis.xml#ellipsis');
    overflow: hidden;
}
.pager-min .paging-bar {
    padding: 1px 20px;
    height: 50px;
    line-height: 50px;
    border-top: 1px solid #ddd;
    overflow: hidden;
}
.pager-min .g-input {
    border: 0;
}
.error-msg{
	color: red;
}
.g-loading {
    top: 29px;
    left: 176px;
}
.form-wrap.fr {
    width: 100%;
}
div#dialogTitle {
    float: left;
    font-size: 16px;
}
.template-view-content {
    background: #fff;
    padding: 20px;
    overflow: auto;
}
input#tiaozhuan {
    height: 24px;
}
.statistics-show-box .left.jum {
    width: 162px;
}
div#jumCan {
    height: 26px;
    margin-top: -8px;
}
input#exportpage {
    margin-top: -24px;
    margin-bottom: 25px;
    margin-left: 170px;
    width: 50px;
    height: 29px;
}
input#exportpageTxt {
     margin-top: -24px;
     margin-bottom: 25px;
     margin-left: 170px;
     width: 50px;
     height: 29px;
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
.form-dialog label {
   	padding-left: 5px;
    margin-right: 15px;
}
.normal-table th:first-child,.normal-table td:first-child{
	width: 50px;
}
</style>
<!--[if IE]>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/ie.css" />
<![endif]-->
<!--[if lt IE 9]>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/html5.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/ie.js" ></script>
<![endif]-->
</head>
<body>
<input type="hidden" value="${statistics_tpl_idx }" id="statistics_tpl_idx">
<div class="equipment-leixing">
<div class="g-loading"></div>
	<!-- 预览 dom 开始 -->
	<div class="page-title-bar">
		<div class="form-wrap fr">
		 <div class="head mb20" id="dialogTitle"></div>
		 <div style="float: right;">
		 	<div class="button button-search">查询</div>
			<div class="button button-reset">重置</div>
			<div class="button button-more-search">高级查询 <i class="ico icon-arrow-right-middle-1x"></i></div>
			<div class="btn" id="exportTxt2" style="display: none;">导出查询Txt</div>
			<div class="btn" id="exportExcel2" style="display: none;">导出查询Excel</div>
			<!-- <div class="btn cancel" id="templateCancel">取消</div> -->
		</div>
		</div>
	</div>
	<!-- <div class="form-dialog"> -->

			<div class="template-view-content">
				<div class="search-template-md">
					<div class="col-2" id="moreSearch">				
						<div class="item" id="stmpMethod"></div>
					</div>
				</div>
			
				<!-- <div class="text-center">
					<div class="button button-search">查询</div>
					<div class="button button-reset">重置</div>
					<div class="button button-more-search">高级查询 <i class="ico ico-arrow-top-1x"></i></div>
			    </div> -->
				<div class="panel-md mt20">
					<div class="panel-content">
						<div class="panel">
							<div class="statistics-show-box clearfix">
								<div class="fl left">
									<div class="box-aside-hd">查询分组</div>
									<!-- <div class="box-asdie" id="js-box-aside"> -->
									<div class="tree-dom">
										<div id="tree"></div>
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
											<div class="left jum">
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
                                            <div class="right" id="jumCan">
                                                <span class="fl">&nbsp;&nbsp;到第</span>
                                                <div class="g-select refresh">
                                                    <input type="text" name="" id="tiaozhuan" class="g-input" placeholder=""  />
                                                </div>
                                                <span class="t2 fl">页</span>
                                                <div class="btn" id="jumpCancel">确定</div>
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
				</div>
			</div>
		<!-- </div> -->
	<!-- 预览 dom 结束 -->
	
</div>
<div class="g-delete-dialog exc">
    <span class="line"></span>
    <div class="word">
        每次导出数据最大1000条，需分页导出。<br>当前数据总页数：<span class="exc1"></span>，请选择页数：
    </div>
    <div class="form-wrap">
        <ul>
            <li>
                <div class="">
                    <input type="text" name="" id="exportpage" class="g-input" placeholder=""  />
                </div>
            </li>
        </ul>
    </div>
    <div class="form-btn cancel g-btn-gray">取消</div>
    <div class="form-btn export g-btn-blue">确定</div>
</div>
<div class="g-delete-dialog txt">
    <span class="line"></span>
    <div class="word">
        每次导出数据最大1000条，需分页导出。<br>当前数据总页数：<span class="exc2"></span>，请选择页数：
    </div>
    <div class="form-wrap">
        <ul>
            <li>
                <div class="">
                    <input type="text" name="" id="exportpageTxt" class="g-input" placeholder=""  />
                </div>
            </li>
        </ul>
    </div>
    <div class="form-btn cancel g-btn-gray">取消</div>
    <div class="form-btn exportTxt g-btn-blue">确定</div>
</div>

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
<script src="${pageContext.request.contextPath }/page/statisticstemplate/js/common-chaxun.js"></script>
<!-- 添加字段js -->
<script src="${pageContext.request.contextPath }/static/js/field.js"></script>
<script src="${pageContext.request.contextPath }/static/plugins/pagination/pagination.js"></script>
<script type="text/javascript">
$(".tbody").scroll(function() {
    $(".normal-table2").scrollLeft($(".tbody").scrollLeft());
});
$(".normal-table2").scroll(function() {
    $(".tbody").scrollLeft($(".normal-table2").scrollLeft());
});
$(function() {
	$("#horizontalTab2").tablesort().data('tablesort');
	$("#horizontalTab2 thead th.number").data('sortBy', function(th, td, sorter) {
		return parseInt(td.text(), 10);
	});
});
</script>
</body>
</html>