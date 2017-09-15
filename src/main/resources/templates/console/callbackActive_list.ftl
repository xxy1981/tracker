<#import "/macro/header.ftl" as h>
<#import "/macro/menu.ftl" as m>
<#import "/macro/page.ftl" as p>
<#import "/macro/staticImport.ftl" as s>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<@s.staticImport />
</head>

<body>
<@h.header current="partner" />
<div class="main-panel">
	<@m.menu current="1-3" />
    <div class="right-panel">
    	<div class="nav">
	    	<ul class="clr">
	    		<li class="nav_current">回调查询</li>
			</ul>
		</div>
		<div class="detail">
	    	<fieldset class="filter">
	    		<form id="form-search" action="${rc.contextPath}/callbackActive" method="get">
		            <input type="text" name="sid" value="${sid!}" placeholder="请输入流水号" style="width:300px">
		            <select name="partnerId" style="width:300px">
		            	<option value="">---请选择客户---</option>
		            	<#list parters as partner>
		            		<#if partnerId==partner.id>
			            		<option value="${partner.id}" selected="selected">${partner.getName()}</option>
			            	<#else>
			            		<option value="${partner.id}">${partner.getName()}</option>
			            	</#if>
		            	</#list>
		            </select>
		            <br/>
		            <input type="hidden" id="pageNo" name="pageNo" value="${pageNo!}">
		            <div id="btn-search" class="button button-primary">查询</div>
		            <a href="${rc.contextPath}/callbackActive" class="button">清空</a>
	            </form>
	    	</fieldset>
	    	<@p.page display="${pageHtmlDisplay!}" />
			<#if (list??) && (list?size > 0)>
	    	<table class="table-list">
	    		<tr>
	    			<th style="width:20%">流水号</th>
	    			<th style="width:20%">ISO设备ID</th>
	    			<th style="width:20%">Android设备ID（SHA-1）</th>
	    			<th style="width:15%">客户ID</th>
	    			<th>APP应用ID</th>
	    			<th>创建时间</th>
	    		</tr>
	    		<#if list??>
	    			<#list list as callbackActive>
			    		<tr>
			    			<td>${callbackActive.sid!}</td>
			    			<td>${callbackActive.idfa!}</td>
			    			<td>${callbackActive.androidIdSha1!}</td>
			    			<td>${callbackActive.partnerId!}</td>
			    			<td>${callbackActive.appId!}</td>
			    			<td>${callbackActive.createTime!}</td>				
			    		</tr>
		    		</#list>
	    		</#if>
	    	</table>
	    	</#if>
    	</div>
    </div>
</div>
<script>
$(function () {
	// 全选
	$("#cb_all").click(function() {
		$(".cb_single").each(function() {
			$(this).prop("checked",!!$("#cb_all").prop("checked"));
		});
		showTools('action-del', $('#btn-del'));
	});
	$(".cb_single").click(function() {
		if(!$(this).prop("checked")) {
			$("#cb_all").prop("checked", false);
		}
		showTools('action-del', $('#btn-del'));
	});
	
	// 批量删除
	$("#btn-del").click(function() {
		if($(this).attr("can-do")=='true') {
			if(confirm("确定要删除吗？")) {
				$.ajax({
					url: '${rc.contextPath}/partner/delete?ids=' + getCheckedValue(),
					type: 'DELETE',
					async: false,
					success: function(result) {
						if(result == 'ok') {
							$("#form-search").submit();
						} else {
							alert("失败："+result);
						}
					}
				});
			}
		}
	});
	
	// 是否显示操作按钮
	function showTools(action, btn) {
		var countChecked = 0;
		var countAction = 0;
		$(".cb_single").each(function() {
			if($(this).prop("checked")) {
				countChecked++;
				if($(this).attr(action) == 'true') {
					countAction++;
				}
			}
		})
		if(countChecked > 0 && (countChecked == countAction)) {
			btn.attr('can-do', 'true');
			btn.find('span').removeClass("tool-disable");
		} else {
			btn.attr('can-do', 'false');
			btn.find('span').addClass("tool-disable");
		}
	}
	
	// 获取所有选中的元素
	function getCheckedValue() {
		var str="";
		$(".cb_single").each(function(){
			if($(this).prop("checked")){
				str+=$(this).val()+",";  
			} 
		})
		if(str.endsWith(',')) {
			str = str.substring(0, str.length-1);
		}
		return str;
	}
});
</script>
</body>
</html>