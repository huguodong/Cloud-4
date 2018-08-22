<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<style>
/*  .form-wrap ul li .error-msg:before {
    content: "";
    position: absolute;
    left: -4px;
    top: 14px;
    width: 0;
    height: 0;
    border-right: 4px solid #ffdddd;
    border-top: 4px solid transparent;
    border-bottom: 4px solid transparent;
}
.form-wrap ul li .error-msg {
    position: relative;
    left: 280px;
    top: -32px;
    width: 150px;
    height: 35px;
    text-align: center;
    line-height: 35px;
    border-radius: 2px;
    background-color: #ffdddd;
} */
.form-dialog-2 .select-area1{
	position: relative;
	float: left;
	margin-right: 20px;
	width: 540px;
	height: 160px;
	background-color: #f6f6f6;
    border: 1px solid #DDD;
	-webkit-box-sizing: border-box;
	-moz-box-sizing: border-box;
	box-sizing: border-box;
}
.form-dialog-2 .select-area2{
	position: relative;
	float: left;
	margin-right: 20px;
	width: 540px;
	height: 160px;
	background-color: #f6f6f6;
    border: 1px solid #DDD;
	-webkit-box-sizing: border-box;
	-moz-box-sizing: border-box;
	box-sizing: border-box;
}
.indexPermessions1{
	height: 130px;
	overflow:scroll;
}
.permessions1{
	height: 130px;
	overflow:scroll;
}

.form-dialog-2 dd{
	width: 50%;
	float: left;
}

.form-dialog-2 .choose-area1{
	padding:1px 10px;
	height: 153px;
	-webkit-box-sizing: border-box;
	-moz-box-sizing: border-box;
	box-sizing: border-box;
	overflow: auto;
}
.form-dialog-2 .choose-area2{
	padding:1px 10px;
	height: 153px;
	-webkit-box-sizing: border-box;
	-moz-box-sizing: border-box;
	box-sizing: border-box;
	overflow: auto;
}

</style>
<div id="add-templategroup-div" class="form-dialog-2">
		<div class="title">
		新增分组
		</div>
		<div class="form-wrap">
		<div class="item">
			<ul>
				<li>
					<div class="left"><span class="g-mustIn">组ID</span></div>
					<div class="right">
						<input type="text" name="auth-group-id" id="" class="g-input" placeholder="请输入"  />
						<span class="error-msg">组ID不能为空</span>
					</div>
				</li>
				<li class="">
					<div class="left"><span class="g-mustIn">组名称</span></div>
					<div class="right">
						<input type="text" name="auth-group-name" id="" class="g-input" placeholder="请输入"  />
						<span class="error-msg">组名不能为空</span>
					</div>
				</li>
			</ul>
		</div>
		<div class="item">
			<ul>
				<li>
					<div class="left">选择查询模板</div>
					<div class="right">
						<div class="select-area1">
							<div class="indexPermessions1">
								<dl class="choose-area1 indexPermessions addCX">
									<!--
									 <dd><div class="g-checkbox"><input type="checkbox"></div></dd>
								 	-->
								</dl>
							</div>
							<div class="handle-area">
								<div class="g-btn checkAll">全选</div>
								<div class="g-btn noCheckAll">反选</div>
							</div>
						</div>
					</div>
				</li>
				<li>
					<div class="left">选择统计模板</div>
					<div class="right">
						<div class="select-area2">
							<div class="permessions1">
								<dl class="choose-area2 permessions addTJ">
									<!-- 
									<dd><div class="g-checkbox"><input type="checkbox"></div></dd>
								  	-->
								</dl>
							</div>
							<div class="handle-area">
								<div class="g-btn checkAll">全选</div>
								<div class="g-btn noCheckAll">反选</div>
							</div>
						</div>
					</div>
				</li>
				<li>
					<div class="left">备注</div>
					<div class="right">
						<textarea name="auth-group-desc" class="g-textarea" placeholder="请输入"></textarea>
					</div>
				</li>
			</ul>
		</div>
		<input type="submit" value="新增" class="g-submit" />
		</div>
	</div>
