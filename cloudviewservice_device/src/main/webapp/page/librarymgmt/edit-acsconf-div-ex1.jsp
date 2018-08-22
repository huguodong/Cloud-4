<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
	ASC新增配置模板 显示页面 lbh
--%>
<style>
 .form-wrap li{
    float: left;
    width: 45%;
}
.form-dialog .form-wrap li .left {
    float: left;
    padding-right: 10px;
    text-align: right;
    width: 90px;
    line-height: 28px;
}
.form-wrap .item {
    margin-bottom: 5px;
    overflow: hidden;
    zoom: 1;
    width: 700px;
} 
.form-dialog .form-wrap li .right {
    float: left;
    width: 200px;
}
.form-dialog .form-wrap li .right .g-input {
    width: 200px;
    height: 28px;
}
.form-dialog-fix .form-dialog .form-wrap .right {
    width: 210px;
}
.form-dialog .form-wrap ul li {
    margin-bottom: 10px;
}
.w-900 .segmentation {
    margin: 10px 0;
    position: relative;
    height: 1px;
    background-color: #DDD;
    width: 500px;
}
</style>
<div id="edit-acsconf-div" class="form-dialog-fix w-900">
	<div class="shade"></div>
	<div class="form-dialog">
		<div class="title">
			修改配置
			<input type="reset" value="取消" class="g-cancel2 g-btn-gray">
			<input type="submit" name="saveOrUpd" placeholder="" class="g-submit2 g-btn-green" value="保存">
			<input hidden="hidden" type="submit" value="TEST" id="test">
		</div>
		<div class="left-tab" id="edit-protocol-left-tab">
			<ul>
				
			</ul>
		</div>
		<div class="right-form">
			<div class="right-content hide" protocol_idx="">
				 <div class="form-wrap">
				 <div class="item">
					 	<ul>
							<li>
								<div class="left">指令代码</div>
								<div class="right">
									<input type="text" name="protocol_show" id="" readonly="readonly" class="g-input" placeholder="请输入" />
									<span class="error-msg">错误提示</span>
								</div>
							</li>
						</ul>
				</div>
				<div class="copy-temp hide">
					<div class="item">
						<ul>
				 			<li>
						 		<div class="segmentation">
						 			<div class="t">desc</div><%--protocol_field_desc--%>
						 		</div>
						 	</li>
				 		</ul>
					</div>
				 		<div class="item">
						 <ul>
							 <li>
								<div class="left">请求规则</div>
								<div class="right">
									<!-- <input type="text" name="protocol_start" id="" class="g-input" placeholder="请输入" /> -->
									<textarea rows="8" cols="50" name="protocol_reqrule" placeholder="请求规则">
										
									</textarea>
									<span class="error-msg">错误提示</span>
								</div>
							
							</li>
						 </ul>	
					</div>
					<div class="item">
						<ul>
							<li>
								<div class="left">响应规则</div>
								<div class="right">
									<!-- <input type="text" name="protocol_end" id="" class="g-input" placeholder="请输入" /> -->
									<textarea rows="8" cols="50" name="protocol_resprule" placeholder="响应规则"></textarea>
									<span class="error-msg">错误提示</span>
								</div>
							</li>
						</ul>
					</div>
					<div class="item">
						<ul>
							<li>
								<div class="left">指令描述</div>
								<div class="right">
									<textarea rows="8" cols="50" name="protocol_description" placeholder="指令描述"></textarea>
									<span class="error-msg">错误提示</span>
								</div>
							</li>
						</ul>
					</div>
				 </div>	
				 
				</div>
			</div>
			
		</div>
	</div>
</div>