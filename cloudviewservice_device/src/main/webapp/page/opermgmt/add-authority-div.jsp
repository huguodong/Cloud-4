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
</style>
<div id="add-authority-div" class="form-dialog-2">
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
				<li class="">
					<div class="left"><span class="g-mustIn">图书馆ID</span></div>
					<div class="right">
						<input type="text" name="lib_id" id="" class="g-input" placeholder="请输入"  />
						<span class="error-msg">请输入正确的图书馆ID</span>
					</div>
				</li>
				<li class="">
					<div class="left"><span class="g-mustIn">图书馆名称</span></div>
					<div class="right">
						<input type="text" name="lib_name" style="color:gray;background-color:#E0E0E0" readonly="readonly" id="" class="g-input" placeholder="请输入"  />
						<span class="error-msg"></span>
					</div>
				</li>
			</ul>
		</div>
		<div class="item">
			<ul>
				<li>
					<div class="left">设置权限</div>
					<div class="right">
						<div class="select-area">
							<dl class="choose-area indexPermessions">
								<!--
								 <dd><div class="g-checkbox"><input type="checkbox"></div>系统管理</dd>
							 	-->
							</dl>
							<div class="handle-area">
								<div class="g-btn checkAll">全选</div>
								<div class="g-btn noCheckAll">反选</div>
							</div>
						</div>
						<div class="select-area">
							<dl class="choose-area permessions">
								<!-- 
								<dd><div class="g-checkbox"><input type="checkbox"></div>系统用户管理</dd>
							  	-->
							</dl>
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
