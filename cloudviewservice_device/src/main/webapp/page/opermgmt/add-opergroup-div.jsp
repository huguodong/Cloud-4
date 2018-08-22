<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="add-opergroup-div" class="form-dialog-2">
		<div class="title">
		新增分组
		</div>
		<div class="form-wrap">
		<div class="item">
			<ul>
				<li>
					<div class="left"><span class="g-mustIn">组ID</span></div>
					<div class="right">
						<input type="text" name="oper-group-id" id="" class="g-input" placeholder="请输入"  />
						<span class="error-msg">组ID不能为空</span>
					</div>
				</li>
				<li class="">
					<div class="left"><span class="g-mustIn">组名</span></div>
					<div class="right">
						<input type="text" name="oper-group-name" id="" class="g-input" placeholder="请输入"  />
						<span class="error-msg">组名不能为空</span>
					</div>
				</li>
				<c:if test="${operator.operator_type==1 or operator.operator_type==2}">
					<li>
						<div class="left"><span class="g-mustIn">图书馆ID</span></div>
						<div class="right">
							<input type="text" name="library_id" id="" class="g-input" placeholder="请输入"  />
							<input type="hidden" name="library_idx"/>
							<span class="error-msg">请输入正确的图书馆ID</span>
						</div>
						<div class="reset">
							<input type="text" placeholder="所属馆名称" readonly="readonly" name="library_name" class="g-input" style="color: gray;background-color: #E0E0E0">
						</div>
					</li>
				</c:if>
			</ul>
		</div>
		<div class="item">
			<ul>
				<li>
					<div class="left">选择权限组</div>
					<div class="right">
						<div class="g-select">
							<select class="serv-group">
						<!-- 		<option value="" selected>组1</option>
								<option value="">组2</option> -->
							</select>
							<span class="arr-icon"></span>
						</div>
						<span class="error-msg">请选择权限组</span>
					</div>
				</li>
				<li>
					<div class="left">查看权限</div>
					<div class="right">
						<div class="select-area">
							<dl class="choose-jurisdiction indexPermessions">
								<!-- <dd>系统管理</dd> -->
							
							</dl>
						</div>
						<div class="select-area">
							<dl class="choose-jurisdiction permessions">
								<!-- <dd>系统用户管理</dd> -->
							</dl>
						</div>
					</div>
				</li>
				
			</ul>
		</div>
		<div class="item">
			<ul>
			    <li>
			        <div class="left">选择模板组</div>
					<div class="right">
						<div class="g-select">
							<select class="template_group">
						<!-- 		<option value="" selected>组1</option>
								<option value="">组2</option> -->
							</select>
							<span class="arr-icon"></span>
						</div>
						<span class="error-msg">请选择模板组</span>
					</div>
					<div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;选择设备组</div>
			    </li>
				<li>
				<div class="left">查看模板组</div>
				   <div class="right">
					      <div class="select-area">
							<dl class="choose-jurisdiction template">
								<!-- <dd>模板管理</dd> -->
							
							</dl>
						 </div>
					 </div>
					<div class="select-area">
						<dl class="choose-area dev-group">
							<!-- 								
							<dd><div class="g-checkbox"><input type="checkbox"></div>系统管理</dd>
					 		-->							
					 	</dl>
						<div class="handle-area">
							<div class="g-btn checkAll">全选</div>
							<div class="g-btn noCheckAll">反选</div>
						</div>
					</div>
				</li>
				<li>
					<div class="left">备注</div>
					<div class="right">
						<textarea name="operator-group-desc" class="g-textarea" placeholder="请输入"></textarea>
					</div>
				</li>
			</ul>
		</div>
		
		<input type="submit" value="保存" class="g-btn-blue g-submit" />
		</div>
	</div>