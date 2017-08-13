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
					  <input type="text" name="id" value="${partnerDetail.id!}" placeholder="请输入客户ID" style="width:300px"></br>
				  </label>
				  <label class="form-label">
		              <span class="form-name"><span class="required">*</span>客户名称：</span>
		              <input type="text" name="name" value="${partnerDetail.name!}" placeholder="请输入客户名称" style="width:300px"></br>
		          </label>
		          </br>
		          <div class="form-footer">
			          <input type="submit" value="确认" class="form-btn">
			          &nbsp;&nbsp;&nbsp;<a href="${rc.contextPath}/partner" class="form-btn">返回</a>
			      </div>
            </form>	
		</table>
	</div>
	</#if>
</div>
</body>
</html>