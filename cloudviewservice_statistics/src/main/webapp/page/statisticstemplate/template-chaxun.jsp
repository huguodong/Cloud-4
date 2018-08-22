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
<title>新建查询模板</title>
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
.fun-color {
    color: #00a2e9 !important;
    margin-left: 127px;
}
.deletefunct {
     float: left;
    padding-left: 107px;
}
.table-form .g-input-long {
    width: 320px !important;
}
.panel-content {
    margin-top: 15px;
    overflow: auto;
    height: 433px;
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
		<span class="title" id="title">新建查询模板</span>
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
						<td width="70" class="sub-color"><span class="g-mustIn">模板名称</span></td>
						<td width="250"><input type="text" placeholder="请输入"
							class="g-input" id="templateName"/></td>
						<td width="70" class="sub-color">模板描述</td>
						<td rowspan="2" colspan="3"><textarea class="g-textarea" id="templateDesc"></textarea>
						</td>
					</tr>
					<tr>
						<td class="sub-color"><span class="g-mustIn">模板ID</span></td>
						<td width="210"><input type="text" placeholder="请输入"
							class="g-input" id="templateID"/></td>
						<td width="70" class="sub-color"></td>
					</tr>
					<tr>
						<td class="sub-color"><span class="g-mustIn">数据源</span></td>
						<td>
							<div class="g-select inline-block">
								<select id="dataSource">
									
								</select> <span class="arr-icon"></span>
							</div>
						</td>
						<td class="sub-color">数据导出</td>
						<td>
							<div class="g-radio">
								<input type="radio" name="data-output" id="data_output" value="0">
							</div> <label for="data-output1" class="label-type">允许</label>
							<div class="g-radio">
								<input type="radio" name="data-output" id="data_output_2" value="1">
							</div> <label for="data-output2" class="label-type">禁用</label>
						</td>
						<td class="sub-color" width="70">导出格式</td>
						<td>
							<div class="g-checkbox">
								<input type="checkbox" name="" value="Txt" id="checkbox_format">
							</div> <label class="label-type" for="checkbox-format-1">Txt</label>

							<div class="g-checkbox">
								<input type="checkbox" name="" value="Excel" id="checkbox_format_2">
							</div> <label class="label-type" for="checkbox-format-2">Excel</label>
						</td>
						<td class="sub-color">是否包含子馆</td>
						<td>
							<div class="g-radio">
								<input type="radio" name="data_range" id="data_range" value="0">
							</div> <label for="data_range1" class="label-type">包含</label>
							<div class="g-radio">
								<input type="radio" name="data_range" id="data_range_2" value="1">
							</div> <label for="data_range2" class="label-type">不包含</label>
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
											<th width="50">显示顺序</th>
											<th width="100">字段</th>
											<th>配置描述</td>
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
											<th width="100">字段</th>
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
			查询条件模块字段编辑
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
									<select id="data_source_select"></select>
									<span class="arr-icon"></span>
								</div>
							</td>
							<th>字段别名</th>
							<td width="180"><input type="text" name="" id="cAttrAlias" class="g-input" placeholder="请输入字段别名"></td>
						</tr>
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
					
					<table width="100%">
						<tr id="strLenId" style="display: none;">
							<th>字符长度取值范围</th>
							<td colspan="3">
								<div class="ml10 td-label">
									最小值
								</div>
								<input type="text" name="" id="minVal" class="g-input inline-block w100" placeholder="最小值" value="" />
								<div class="ml10 td-label">
									最大值
								</div>
								<input type="text" name="" id="maxVal" class="g-input inline-block w100" placeholder="最大值" value=""/>
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
								<input type="text" name="" id="dataRegular" class="g-input w250" placeholder="数据校验" />
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
				查询结果模块字段编辑 <input type="reset" value="取消" class="g-cancel2 g-btn-gray">
				<input type="submit" placeholder=""
					class="g-submit2 g-btn-green save-dialog-result save-field"
					value="保存">
			</div>
			<div class="form-container">
				<div class="table-form">
					<table width="100%">
						<tr>
							<td width="90"><span class="g-mustIn">结果数据源</span></td>
							<td>
								<div class="g-select">
									<select id="rcSubCountType">
										
									</select> <span class="arr-icon"></span>
								</div>
							</td>
							<td width="80">字段别名</td>
							<td width="180"><input type="text" name="" id="rcAttrAlias"
								class="g-input" placeholder="请输入字段别名" /></td>
						</tr>
						<tr>
							<td><span class="g-mustIn">显示顺序</span></td>
							<td><input type="text" name="" id="rcAttrSort" class="g-input"
								placeholder="请设置显示顺序" /></td>
							<td>是否分组</td>
							<td>
								<div class="g-radio">
									<input type="radio" name="isteam" id="rGroup" value="1">
								</div> <label for="is_team_1" class="label-type mr20">是</label>
								<div class="g-radio on">
									<input type="radio" name="isteam" id="rGroup_2" value="0">
								</div> <label for="is_team_2" class="label-type">否</label>
							</td>
						</tr>
						<tr>
							<td>字段值</td>
							<td colspan="3"><input type="text" name="" id="rFieldVal"
								class="g-input" placeholder="请输入字段值" /></td>
						</tr>
						<tr>
							<td>数据类型</td>
							<td>
								<div class="g-select">
									<select id="dataType">
									</select> <span class="arr-icon"></span>
								</div>
							</td>
							<td colspan="2" id="dataTypeTr">
								<div class="g-radio on">
									<input type="radio" name="rdt" id="dtRadio" checked="checked" value="001">
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
								    <input type="hidden" name="" id="subFunVal" />
									<input type="text" name="subFunVal" id="" class="g-input display-inline g-input-long" placeholder="" />
								</div>
								<div class="g-select w125 display-inline">
									<select id="subFun">
										<option value="" selected="">请选择函数类型</option>
									</select>
									<span class="arr-icon"></span>
								</div>
							</td>
						</tr>
						
						<tr id="addfun">
						  <td>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</td>
						  <td><a href="javascript:;" class="fun-color" id="function-add" style="display: none;"><strong>+</strong> 添加一行</a></td>
						</tr>
					</table>
				</div>
				<div class="form-title">
				<div class="line"></div>
				<span>字段标识</span>
			    </div>
			    <div class="picker-field">
				<div class="clearfix">
					<div class="fl table-form field">
						字段
					</div>
					<div class="fl color">
						文本颜色
					</div>
					<div class="fl icon">
						引用图标
					</div>
				</div>
				<div id="picker-field-list">
					
				</div>
				<a href="javascript:;" class="imt-color" id="picker-field-add"><strong>+</strong> 添加字段标识</a>
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
						<div class="btn" id="exportTxt2" style="display: none;">导出查询Txt</div>
						<div class="btn" id="exportExcel2" style="display: none;">导出查询Excel</div>
					</div>
				</div>
				
				<div class="search-template-md">
					<div class="col-2" id="moreSearch">
					
					<div class="item" id="stmpMethod">
						
					</div>
					<!-- <div class="item" id="colTmp" style="display: none;">
						<div class="attr" id="gcolTmp" style="display: none;">分组对应：</div>
						<div class="value" id="tmpMethod">
							
						</div>
						
					</div> -->
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
										<table width="100%" cellpadding="0" cellspacing="0" id="horizontalTab">
											<thead>
												<tr>
													
												</tr>
											</thead>
										</table>
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
<div class="g-delete-dialog" id="sdelDialog">
	<span class="line"></span>
	<div class="word">
		是否要删除当前字段？
	</div>
	<div class="form-btn cancel g-btn-gray" id="sdelCancel">取消</div>
	<div class="form-btn remove g-btn-red" id="sdelSure">删除</div>
</div>
<div class="g-delete-dialog" id="rdelDialog">
	<span class="line"></span>
	<div class="word">
		是否要删除当前字段？
	</div>
	<div class="form-btn cancel g-btn-gray" id="rdelCancel">取消</div>
	<div class="form-btn remove g-btn-red" id="rdelSure">删除</div>
</div>
<!-- 对应的本地文件,必须放在最后，要不会出现页面拉长的情况 -->
<script src="${pageContext.request.contextPath }/page/statisticstemplate/js/statistics_template_chaxun.js"></script>
<!-- 添加字段js -->
<script src="${pageContext.request.contextPath }/static/js/field.js"></script>
</body>
</html>