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
<title>新建统计模板</title>
<style type="text/css">
.search-template-md .col-4 .item10{
		width: 10%;
		float: left;
		vertical-align: middle;
		line-height: 36px;
}
.search-template-md .col-4 .item1{
		width: 100%;
		float: left;
		vertical-align: middle;
		line-height: 36px;
}
.search-template-md .col-6 .item10{
		width: 10%;
		float: left;
		vertical-align: middle;
		line-height: 36px;
}
.search-template-md .col-6 .item1{
		width: 100%;
		float: left;
		vertical-align: middle;
		line-height: 36px;
}
.table-form .w200{
	width: 200px !important;
}
.divBorder{
	border:1px solid #f6f6f6
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
<input type="hidden" id="statistics_tpl_idx" value="${statistics_tpl_idx }">
<input type="hidden" id="copyRecord" value="${copyRecord }">
<input type="hidden" id="currentpage" value="${currentpage}">
<input type="hidden" id="endpage" value="${endpage}">
<div class="equipment-leixing">
	<div class="page-title-bar">
		<span class="title" id="title">新建统计模板</span>
		<div class="form-wrap fr">
			<div class="btn" id="saveTemplate">保存模板</div>
			<div class="btn cancel" id="templateCancel">取消</div>
			<div class="btn review">
				<a href="javascript:;">预览</a>
			</div>
		</div>
	</div>
	<div class="main">
		<div class="content-md">
				<table class="table-search" width="100%">
					<tr>
						<td class="sub-color"><span class="g-mustIn">模板名称</span></td>
						<td width="250"><input type="text" placeholder="请输入" class="g-input" id="templateName"/></td>
						<td  width="100" class="sub-color">模板描述</td>
						<td rowspan="2" colspan="3"><textarea class="g-textarea" id="templateDesc" style="width: 600px;"></textarea>
						</td>
					</tr>
					<tr>
						<td class="sub-color"><span class="g-mustIn">模板ID</span></td>
						<td width="210"><input type="text" placeholder="请输入" class="g-input" id="templateID"/></td>
						<td class="sub-color">&nbsp;</td>
					</tr>
					<tr>
						<td class="sub-color"><span class="g-mustIn">数据源</span></td>
						<td>
							<div class="g-select inline-block">
								<select id="dataSource"></select>
								<span class="arr-icon"></span>
							</div>
						</td>
						<td class="sub-color">数据导出</td>
						<td width="210">
							<div class="g-radio">
								<input type="radio" name="data-output" id="data_output" value="0">
							</div> 
							<label for="data-output1" class="label-type">允许</label>
							<div class="g-radio">
								<input type="radio" name="data-output" id="data_output_2" value="1">
							</div> 
							<label for="data-output2" class="label-type">禁用</label>
						</td>
						<td width="100" class="sub-color">导出格式</td>
						<td>
							<div class="g-checkbox">
								<input type="checkbox" name="" value="Txt" id="checkbox_format">
							</div> 
							<label class="label-type" for="checkbox-format-1">Txt</label>
							<div class="g-checkbox">
								<input type="checkbox" name="" value="Excel" id="checkbox_format_2">
							</div> 
							<label class="label-type" for="checkbox-format-2">Excel</label>
						</td>
					</tr>
					<tr>
						<td class="sub-color" width="100">是否包含子馆</td>
						<td>
							<div class="g-radio">
								<input type="radio" name="data_range" id="data_range" value="0">
							</div> 
							<label for="data_range1" class="label-type">包含</label>
							<div class="g-radio">
								<input type="radio" name="data_range" id="data_range_2" value="1">
							</div> 
							<label for="data_range2" class="label-type">不包含</label>
						</td>
						<td class="sub-color">查询结果数</td>
						<td colspan="3">
							<input type="text" placeholder="请输入正整数" class="g-input" id="topHits" style="display: inline;"/>
							<span class="sub-color">&nbsp;&nbsp;(TOP排名时有效，比如查询top 10)</span>
						</td>
					</tr>
				</table>
				<div class="panel-md mt20">
					<div class="panel-title">
						<div class="fr">
							<div class="add-field" id="add-field">
								<a href="javascript:;"><strong>+</strong> 添加字段</a>
							</div>
						</div>
						<ul>
							<li class="active" data-field="add-field-search">查询条件模块</li>
							<li data-field="add-field-result">查询结果模块</li>
						</ul>
					</div>
					<div class="panel-content">
						<div class="panel">
							<div class="normal-table normal-table-template">
								<table width="100%" cellpadding="0" cellspacing="0" id="searchTable">
									<thead>
										<tr>
											<th width="50">ID</th>
											<th width="100">字段别名</th>
											<th>配置描述</th>
											<th width="150">操作</th>
										</tr>
									</thead>
									<!-- 查询模块的行数插入 -->
									<tbody>

									</tbody>
								</table>
							</div>
						</div>
						<div class="panel hidden">
							<div class="normal-table normal-table-template">
								<table width="100%" cellpadding="0" cellspacing="0"
									id="result-module">
									<thead>
										<tr>
											<th width="50">显示顺序</th>
											<th width="100">字段别名</th>
											<th>配置描述</th>
											<th width="240">操作</th>
										</tr>
									</thead>
									<tbody>

									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
	</div>
</div>
<div class="form-dialog-fix w-600 form-dialog-fix-search">
	<div class="shade"></div>
	<div class="form-dialog">
		<div class="title">
			统计查询字段编辑
			<input type="reset" value="取消" class="g-cancel2 g-btn-gray">
			<input type="submit" placeholder="" class="g-submit2 g-btn-green save-dialog-search save-field" value="保存">
		</div>
		<div class="form-container">
			<!-- 字段在js/field.js -->
			<div class="table-form">
				<table width="100%">
					<tbody>
						<tr>
							<th width="110"><span class="g-mustIn">数据源</span></th>
							<td>
								<div class="g-select">
									<select id="data_source_select">
									</select> <span class="arr-icon"></span>
								</div>
							</td>
							<th><span class="g-mustIn">字段别名</span></th>
							<td width="180"><input type="text" name="" id="cAttrAlias" class="g-input" placeholder="请输入字段别名"></td>
						</tr>
						<!-- <tr>
							<td>设置排序</td>
							<td colspan="3"><input type="text" name="" id="cAttrSort" class="g-input" placeholder="请设置排序" /></td>
						</tr> -->
					</tbody>
				</table>
			</div>
			<div id="data-source-dom">
			<div class="table-form">
		<table width="100%">
			<tr>
				<th width="110">字段类型</th>
				<td colspan="3">
					<div class="g-select">
						<select class="g-select-field-multiple" id="cOptionType">
							<option value="text" selected="">单行文本</option>
			                <option value="multiple-text">多行文本</option>
			                <option value="select">选项</option>
			                <option value="number">数字</option>
			                <option value="date-time">日期和时间</option> 
						</select>
						<span class="arr-icon"></span>
					</div>
				</td>
			</tr>
		</table>
	</div>
	<div class="table-form mt10 mb10"></div> 
	<div class="table-form">
		<!-- <div class="tmpMainType"><td>统计类型是否默认选中:&nbsp;&nbsp;&nbsp;</td>
							<td>
								<div class="g-radio">
									<input type="radio" name="isteam" id="sDefault" value="1">
								</div> <label for="isteam_1" class="label-type mr20">是</label>
								<div class="g-radio">
									<input type="radio" name="isteam" id="sDefault_2" value="0">
								</div> <label for="isteam_2" class="label-type">否</label>
							</td>
		</div> -->
		<!-- <div id="mainType" class="tmpMainType">
		</div> -->
		<!-- <div class="tmpMainType">
			<tr>
				<td colspan="4">
					<div
						class="normal-table normal-table-template normal-table-template-dialog" id="initAttr">
						<table width="100%" cellpadding="0" cellspacing="0">
							<thead>
								<tr>
									<td width="50">ID</td>
									<td>原始字段</td>
									<td width="100">操作&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:expand()"><font size="5px">+/-</font></a></td>
								</tr>
							</thead>
							填充原始字段
						</table>
					</div>
				</td>
			</tr>
		</div> -->
		<table width="100%">
			<tr id="strLenId" style="display: none;">
				<th>字符长度取值范围</th>
				<td colspan="3">
					<div class="ml10 td-label">
						最小值
					</div>
					<input type="text" name="" id="minVal" class="g-input inline-block w100" placeholder="最小值" value="" onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9-]+/,'');}).call(this)" onblur="this.v();"/>
					<div class="ml10 td-label">
						最大值
					</div>
					<input type="text" name="" id="maxVal" class="g-input inline-block w100" placeholder="最大值" value="" onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9-]+/,'');}).call(this)" onblur="this.v();"/>
					<div class="tips ml10 mt5">
						系统将在表单提交时检测数据长度范围是否符合要求，如果不想限制长度请留空
					</div>
				</td>
			</tr>
			<tr>
				<th>数据校验</th>
				<td colspan="3">
					<div class="fr">
						<div class="g-select">
							<select class="g-reg-filter" id="dataFilter">
								<option value="">常用正则</option>
								<option value="/^[0-9.-]+$/">数字</option>
								<option value="/^[0-9-]+$/">整数</option>
								<option value="/^[a-z]+$/i">字母</option>
								<option value="/^[0-9a-z]+$/i">数字+字母</option>
								<option value="/^[\w\-\.]+@[\w\-\.]+(\.\w+)+$/">E-mail</option>
								<option value="/^[0-9]{5,20}$/">QQ</option>
								<option value="/^http:\/\//">超级链接</option>
								<option value="/^(1)[0-9]{10}$/">手机号码</option>
								<option value="/^[0-9-]{6,13}$/">电话号码</option>
							</select>
							<span class="arr-icon"></span>
						</div>
					</div>
					<input type="text" name="" id="dataRegular" class="g-input w200" placeholder="数据校验" />
				</td>
			</tr>
			
			<tr>
				<th>校验未通过提示</th>
				<td colspan="3">
					<input type="text" name="" id="regularResult" class="g-input g-input-p100" placeholder="校验未通过提示">
				</td>
			</tr>
			<tr>
				<th>查询条件属性</th>
				<td>
					<div class="g-radio on"><input id="queryType" name="queryType" value="0" type="radio"></div>
					<label for="queryType-1" class="label-type">默认查询</label>
				</td>
				<td>
					<div class="g-radio"><input id="queryType_2" name="queryType" value="1" type="radio"></div>
					<label for="queryType-2" class="label-type">高级查询</label>
				</td>
				<td></td>
			</tr>
		</table>
	</div>
			
			</div>
		</div>
	</div>
</div>
<div class="form-dialog-fix w-600 form-dialog-fix-result">
		<div class="shade"></div>
		<div class="form-dialog">
			<div class="title">
				统计结果字段编辑 <input type="reset" value="取消" class="g-cancel2 g-btn-gray">
				<input type="submit" placeholder=""
					class="g-submit2 g-btn-green save-dialog-result save-field"
					value="保存">
			</div>
			<div class="form-container">
				<div class="table-form">
					<table width="100%">
						<tr>
							<td width="80"><span class="g-mustIn">结果数据源</span></td>
							<td>
								<div class="g-select">
									<select id="rcSubCountType">
										
									</select> <span class="arr-icon"></span>
								</div>
							</td>
							<td width="80"><span class="g-mustIn">字段别名</span></td>
							<td width="180"><input type="text" name="" id="rcAttrAlias"
								class="g-input" placeholder="请输入字段别名" /></td>
						</tr>
						<tr>
							<td><span class="g-mustIn">显示顺序</span></td>
							<td><input type="text" name="" id="rcAttrSort" class="g-input"
								placeholder="请设置显示顺序" onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9-]+/,'');}).call(this)" onblur="this.v();"/></td>
							<td>是否分组</td>
							<td>
								<div class="g-radio on">
									<input type="radio" name="isteam" id="rGroup" value="1">
								</div> <label for="isteam_1" class="label-type mr20">是</label>
								<div class="g-radio">
									<input type="radio" name="isteam" id="rGroup_2" value="0">
								</div> <label for="isteam_2" class="label-type">否</label>
							</td>
						</tr>
						<tr>
							<td>字段值</td>
							<td colspan="3"><input type="text" name="" id="rFieldVal"
								class="g-input" placeholder="请输入字段值" /></td>
						</tr>
						<tr id="dataTypeTr">
							<td>数据类型</td>
							<td>
								<div class="g-select">
									<select id="dataType">
									</select> <span class="arr-icon"></span>
								</div>
							</td>
							<td colspan="2">
								<div class="g-radio">
									<input type="radio" name="rdt" id="dtRadio" value="001">
								</div> <label for="is_dt_1" class="label-type mr20">自定义</label>
								<div class="g-radio">
									<input type="radio" name="rdt" id="dtRadio_2" value="002">
								</div> <label for="is_dt_2" class="label-type">默认值</label>
								<div class="g-radio">
									<input type="radio" name="rdt" id="dtRadio_3" value="003">
								</div> <label for="is_dt_3" class="label-type">取系统设置</label>
							</td>
						</tr>
						<tr id="gFun">
							<td>函数处理</td>
							<td colspan="3">
								<div class="fr">
									<input type="text" name="" id="subFunDesc" class="g-input display-inline g-input-long" placeholder="" disabled="disabled"/>
								</div>
								<div class="g-select w125 display-inline">
									<select id="mainFun">
										
									</select>
									<span class="arr-icon"></span>
								</div>
							</td>
						</tr>
						<tr id="gFun_2">
							<td>选择类型</td>
							<td colspan="3">
								<div class="fr">
									<input type="text" name="" id="subFunVal" class="g-input display-inline g-input-long" placeholder="" />
								</div>
								<div class="g-select w125 display-inline">
									<select id="subFun">
										<option value="" selected="">请选择函数类型</option>
									</select>
									<span class="arr-icon"></span>
								</div>
								
							</td>
						</tr>
						<!-- <tr id="mainType">
							<td>统计类型:</td>
							<td colspan="3"></td>
						</tr> -->
					</table>
				</div>
				<div name="report_style" class="form-title" style="display: none;">
					<div class="line"></div>
					<span>统计报表样式</span>
				</div>
				<div name="report_style" class="chart-select-list" style="display: none;">
					<ul>
						<li><img src="${pageContext.request.contextPath }/static/images/img-chart-2.png" alt="" />
							<div class="g-checkbox">
								<input type="checkbox" name="" value="chart_1" id="chart_1">
							</div> <label for="chart_1">柱状图</label></li>
						<li><img src="${pageContext.request.contextPath }/static/images/img-chart-3.png" alt="" />
							<div class="g-checkbox">
								<input type="checkbox" name="" value="chart_2" id="chart_2">
							</div> <label for="chart_2">圆饼图</label></li>
						<li><img src="${pageContext.request.contextPath }/static/images/img-chart-4.png" alt="" />
							<div class="g-checkbox">
								<input type="checkbox" name="" value="chart_3" id="chart_3">
							</div> <label for="chart_3">曲线图</label></li>
						<li><img src="${pageContext.request.contextPath }/static/images/img-chart-1.png" alt="" />
							<div class="g-checkbox">
								<input type="checkbox" name="" value="chart_4" id="chart_4">
							</div> <label for="chart_4">Top排名</label></li>
					</ul>
				</div>
			</div>
		</div>
	</div>
<!-- 预览 dom 开始 -->
	<div class="form-dialog-fix w-1200 form-dialog-fix-view">
		<div class="shade"></div>
		<div class="form-dialog">
			<div class="title">
				模板预览 <input type="reset" value="关闭" class="g-cancel2 g-btn-gray">
				<input type="submit" placeholder=""
					class="g-submit2 g-btn-green save-template" value="保存">
			</div>
			<div class="template-view-content">
				<div class="head mb20"><span  id="dialogTitle">待写入</span>
					<div style="float: right;">
					 	<div class="button button-search">查询</div>
						<div class="button button-reset">重置</div>
						<div class="button button-more-search">高级查询 <i class="ico icon-arrow-right-middle-1x"></i></div>
						<div class="btn" id="exportTxt2" style="display: none;">导出查询Txt</div>
						<div class="btn" id="exportExcel2" style="display: none;">导出查询Excel</div>
					</div>
				</div>
				<!-- <div class="head mb20" id="dialogTitle">待写入</div> -->
				<div class="search-template-md">
					<!-- col-2 可以放1个item  item 100% -->
					<!-- col-4 可以放2个item  item 50% -->
					<!-- col-6 可以放3个item  item 33.33333% -->
					<div class="col-2" id="moreSearch">
					<!-- <div class="col-2">
						<div class="item">
							<div class="attr">日期范围</div>
							<div class="value">
								<div class="g-inputdate inline-block">
									<input type="text" placeholder="起始时间"
										class="g-input datepicker"><span class="icon"></span>
								</div>
								<span class="inline-block"> - </span>
								<div class="g-inputdate inline-block">
									<input type="text" placeholder="结束时间"
										class="g-input datepicker"><span class="icon"></span>
								</div>
							</div>
						</div>
					</div> -->
					<!-- <div class="item">
						<div class="attr">查询条件：</div>
					</div> -->
					<div class="item" id="stmpMethod">
						
					</div>
					<!-- <div class="item" id="colTmp" style="display: none;">
						<div class="attr" id="gcolTmp" style="display: none;">分组对应：</div>
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
						<div class="panel">
							<div class="statistics-show-box clearfix">
								<div class="fl left">
									<div class="box-aside-hd">查询分组</div>
									<!-- <div class="box-asdie" id="js-box-aside"> -->
										<div class="tree-dom">
											<div id="tree"></div>
										</div>
										<!-- <dl>
											<dt>
												<i class="ico icon-arrow-right-1x fr"></i> 24小时自助设备
											</dt>
											<dd>
												<ul>
													<li>深图门口A</li>
													<li>深图后门</li>
													<li>深图后门</li>
												</ul>
											</dd>
										</dl>
										<dl>
											<dt>
												<i class="ico icon-arrow-right-1x fr"></i> 借还书机
											</dt>
											<dd>
												<ul class="hidden">
													<li>借还书机1</li>
													<li>借还书机2</li>
													<li>借还书机3</li>
												</ul>
											</dd>
										</dl>
										<dl>
											<dt>
												<i class="ico icon-arrow-right-1x fr"></i> 借还书机
											</dt>
											<dd>
												<ul class="hidden">
													<li>借还书机1</li>
													<li>借还书机2</li>
													<li>借还书机3</li>
												</ul>
											</dd>
										</dl> -->
									<!-- </div> -->
								</div>
								<div class="right">
									<div class="normal-table">
										<table width="100%" cellpadding="0" cellspacing="0" id="horizontalTab">
											<thead>
												<tr>
													<!-- <td>位置</td>
													<td>设备</td>
													<td>借书</td>
													<td>还书</td>
													<td>续借</td>
													<td>统计</td> -->
												</tr>
											</thead>
										</table>
									</div>
								</div>

							</div>
						</div>
						<div class="panel hidden">
							<div class="switch-label-md">
								<div class="switch-label" id="gchart">
									<!-- class="switch-label-hd active" -->
									<!-- <a href="javascript:;" class="switch-label-hd" style="display: none;" id="chart_1">曲线图</a>
									<a href="javascript:;" class="switch-label-hd" style="display: none;" id="chart_2">柱状图</a>
									<a href="javascript:;" class="switch-label-hd" style="display: none;" id="chart_3">圆饼图</a>
								    <a href="javascript:;" class="switch-label-hd" style="display: none;" id="chart_4">Top排名</a> -->
								</div>
								<!-- 图表控件 地址 http://echarts.baidu.com/index.html -->
								<div class="chart-show">
									<div class="switch-label-panel hidden">
										<div id="chart-1" class="chart-md"></div>
									</div>
									<div class="switch-label-panel hidden">
										<div id="chart-2" class="chart-md"></div>
									</div>
									<div class="switch-label-panel hidden">
										<div id="chart-3" class="chart-md"></div>
									</div>
									<div class="switch-label-panel hidden">
										<div id="chart-4" class="chart-md"></div>
									</div>
								</div>
							</div>

						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 预览 dom 结束 -->
<div class="g-delete-dialog" id="delDialog">
	<span class="line"></span>
	<div class="word">
		是否要删除当前字段？
	</div>
	<div class="form-btn cancel g-btn-gray" id="delCancel">取消</div>
	<div class="form-btn remove g-btn-red" id="delSure">删除</div>
</div>
<div class="g-delete-dialog" id="rdelDialog">
	<span class="line"></span>
	<div class="word">
		是否要删除当前字段？
	</div>
	<div class="form-btn cancel g-btn-gray" id="rdelCancel">取消</div>
	<div class="form-btn remove g-btn-red" id="rdelSure">删除</div>
</div>

<div class="g-delete-dialog" id="confirmDialog">
	<span class="line"></span>
	<div class="word" id="confirmMsg">
		
	</div>
	<div class="form-btn cancel g-btn-gray" id="confirmCancel">取消</div>
	<div class="form-btn remove g-btn-red" id="confirmSure">确认</div>
</div>

<!-- 对应的本地文件,必须放在最后，要不会出现页面拉长的情况 -->
<script src="${pageContext.request.contextPath }/page/statisticstemplate/js/statistics_template_tongji.js"></script>
<!-- 添加字段js -->
<script src="${pageContext.request.contextPath }/static/js/field.js"></script>
</body>
</html>