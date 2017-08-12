<#import "/macro/staticImport.ftl" as s>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<@s.staticImport />
</head>

<body>
<div class="main-panel">
	<div class="page-detail">
		<h3>客户信息</h3></br>
		<table class="table-list">
			<form id="form-add" action="${rc.contextPath}/partner/add" method="post">
				  <label class="form-label">
					  <span class="form-name"><span class="required">*</span>客户ID：</span>
					  <input type="text" name="id" placeholder="请输入客户ID" style="width:200px"></br>
				  </label>
				  <label class="form-label">
		              <span class="form-name"><span class="required">*</span>客户名称：</span>
		              <input type="text" name="name"  placeholder="请输入客户名称" style="width:200px"></br>
		          </label>
				  <label class="form-label">
					  	<span class="form-name"><span class="required">*</span>图书类型：</span>
			            <select name="type">
			            	<#list enums["com.xxytech.tracker.enums.DeviceType"]?values as e> 
				            	<option value="${e.code}">${e.getName()}</option>
			            	</#list>
			            </select>
		            	<br/>
		          </label>
	              <label class="form-label">
				  	  	<span class="form-name"><span class="required">*</span>APP应用ID：</span>
	            	  	<input type="text" name="propertyId" placeholder="请输入应用ID" style="width:200px"></br>
		          </label>
				  <label class="form-label">
					  	<span class="form-name"><span class="required">*</span>CHN：</span>
		            	<input type="text" name="chn" placeholder="请输入CHN值" style="width:200px"></br>
		          </label>
		          </br>
		          <div class="form-footer">
			          <input type="submit" value="确认" class="form-btn">
			      </div>
            </form>	
		</table>
	</div>
</div>
</body>
</html>