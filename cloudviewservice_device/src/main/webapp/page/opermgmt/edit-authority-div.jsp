<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div id="edit-authority-div" class="form-dialog-2">
		<div class="title">
		编辑分组
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
						<input type="hidden" name="version_stamp" id="" class="g-input" placeholder=""  />
						<span class="error-msg">组名称不能为空</span>
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
						<input type="text" style="color:gray;background-color:#E0E0E0" readonly="readonly" name="lib_name" id="" class="g-input" placeholder="请输入"  />
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
		<input type="submit" value="保存" class="g-submit" />
		</div>
	</div>
