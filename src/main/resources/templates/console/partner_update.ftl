<#import "/macro/staticImport.ftl" as s>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<@s.staticImport />
</head>

<body>
<div class="main-panel">
	<#if partnerDetail??>
	<div class="page-detail">
		<h3>客户信息</h3></br>
		<table class="table-list">
			<form id="form-update" action="${rc.contextPath}/partner/update" method="post">
				  <label class="form-label">
					  <span class="form-name"><span class="required">*</span>客户ID：</span>
					  <input type="text" name="id" value="${partnerDetail.id!}" placeholder="请输入客户ID" style="width:200px"></br>
				  </label>
				  <label class="form-label">
		              <span class="form-name"><span class="required">*</span>客户名称：</span>
		              <input type="text" name="name" value="${partnerDetail.name!}" placeholder="请输入客户名称" style="width:200px"></br>
		          </label>
				  <#--label class="form-label">
					  	<span class="form-name"><span class="required">*</span>客户类型：</span>
			            <select name="type">
			            	<#list enums["com.xxytech.tracker.enums.DeviceType"]?values as e> 
			            		<#if partnerDetail.deviceType==e.code>
				            		<option value="${e.code}" selected="selected">${e.getName()}</option>
				            	<#else>
				            		<option value="${e.code}">${e.getName()}</option>
				            	</#if>
			            	</#list>
			            </select>
		            <br/>
		          </label-->
	              <label class="form-label">
				  	  	<span class="form-name"><span class="required">*</span>APP应用ID：</span>
	            	  	<input type="text" name="propertyId" value="${partnerDetail.propertyId!}" placeholder="请输入应用ID" style="width:200px"></br>
		          </label>
				  <label class="form-label">
					  	<span class="form-name"><span class="required">*</span>CHN：</span>
		            	<input type="text" name="chn" value="${partnerDetail.chn!}" placeholder="请输入CHN" style="width:200px"></br>
		          </label>
		          </br>
		          <div class="form-footer">
			          <input type="submit" value="确认" class="form-btn">
			      </div>
            </form>	
		</table>
	</div>
	</#if>
</div>
</body>
</html>