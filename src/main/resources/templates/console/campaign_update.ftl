<#import "/macro/staticImport.ftl" as s>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<@s.staticImport />
</head>

<body>
<div class="main-panel">
	<#if campaignDetail??>
	<div class="page-detail">
		<h3>活动信息</h3></br>
		<table class="table-list">
			<form id="form-update" action="${rc.contextPath}/campaign/update" method="post">
		          <label class="form-label">
					  <span class="form-name"><span class="required">*</span>活动ID：</span>
					  <input type="text" name="id" value="${campaignDetail.id!}" placeholder="请输入活动ID" style="width:300px"></br>
				  </label>
				  <label class="form-label">
		              <span class="form-name"><span class="required">*</span>活动名称：</span>
		              <input type="text" name="name" value="${campaignDetail.name!}"  placeholder="请输入活动名称" style="width:300px"></br>
		          </label>
		          <label class="form-label">
					  <span class="form-name"><span class="required">*</span>三方平台ID：</span>
					  <input type="text" name="thirdCode" value="${campaignDetail.thirdCode!}" placeholder="请输入三方平台ID" style="width:300px"></br>
				  </label>
				  <label class="form-label">
					  <span class="form-name"><span class="required">*</span>三方平台名称：</span>
					  <input type="text" name="thirdName" value="${campaignDetail.thirdName!}" placeholder="请输入三方平台名称" style="width:300px"></br>
				  </label>
				  <label class="form-label">
					  <span class="form-name"><span class="required">*</span>三方平台URL：</span>
					  <input type="text" name="thirdUrl" value="${campaignDetail.thirdUrl!}" placeholder="请输入三方平台URL" style="width:300px"></br>
				  </label>
				  <label class="form-label">
					  <span class="form-name"><span class="required">*</span>渠道：</span>
					  <input type="text" name="channel" value="${campaignDetail.channel!}" placeholder="请输入渠道" style="width:300px"></br>
				  </label>
				  <label class="form-label">
					  	<span class="form-name"><span class="required">*</span>客户：</span>
			            <select name="partnerId" style="width:311px">
		            	<#list parters as partner>
		            		<#if campaignDetail.partnerId==partner.id>
			            		<option value="${partner.id}" selected="selected">${partner.getName()}</option>
			            	<#else>
			            		<option value="${partner.id}">${partner.getName()}</option>
			            	</#if>
		            	</#list>
		            </select>
		          <br/>
		          </label>
	              <label class="form-label">
				  	  	<span class="form-name"><span class="required">*</span>APP应用ID：</span>
	            	  	<input type="text" name="appId" value="${campaignDetail.appId!}" placeholder="请输入APP应用ID" style="width:300px"></br>
		          </label>
	              <label class="form-label">
				  	  	<span class="form-name"><span class="required">*</span>APP应用名称：</span>
	            	  	<input type="text" name="appName" value="${campaignDetail.appName!}" placeholder="请输入APP应用名称" style="width:300px"></br>
		          </label>
				  <label class="form-label">
					  	<span class="form-name"><span class="required">*</span>外链URL：</span>
		            	<input type="text" name="url" value="${campaignDetail.url!}" placeholder="请输入外链URL" style="width:300px" disabled="disabled" ></br>
		          </label>
		          </br>

		          <div class="form-footer">
			          <input type="submit" value="确认" class="form-btn">
			          &nbsp;&nbsp;&nbsp;<a href="${rc.contextPath}/campaign" class="form-btn">返回</a>
			      </div>
            </form>	
		</table>
	</div>
	</#if>
</div>
</body>
</html>