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
	<@m.menu current="1-2" />
    <div class="right-panel">
    	<div class="nav">
	    	<ul class="clr">
	    		<li class="nav_current">点击查询</li>
			</ul>
		</div>
		<div class="detail">
	    	<fieldset class="filter">
	    		<form id="form-search" action="${rc.contextPath}/tracker" method="get">
		            <input type="text" name="impId" value="${impId!}" placeholder="请输入ImpID" style="width:300px">
		            <input type="text" name="siteId" value="${siteId!}" placeholder="请输入SiteID" style="width:300px">
		            <select name="trackingPartner" style="width:300px">
		            	<option value="">---请选择客户---</option>
		            	<#list parters as partner>
		            		<#if trackingPartner==partner.id>
			            		<option value="${partner.id}" selected="selected">${partner.getName()}</option>
			            	<#else>
			            		<option value="${partner.id}">${partner.getName()}</option>
			            	</#if>
		            	</#list>
		            </select>
		            <br/>
		            <input type="hidden" id="pageNo" name="pageNo" value="${pageNo!}">
		            <div id="btn-search" class="button button-primary">查询</div>
		            <a href="${rc.contextPath}/tracker" class="button">清空</a>
	            </form>
	    	</fieldset>
			<@p.page display="${pageHtmlDisplay!}" />
			<#if (list??) && (list?size > 0)>
	    	<table class="table-list">
	    		<tr>
	    			<th style="width:15%">ImpID</th>
	    			<th style="width:20%">ISO设备ID</th>
	    			<th style="width:20%">Android设备ID</th>
	    			<th style="width:10%">SiteID</th>
	    			<th style="width:10%">客户ID</th>
	    			<th style="width:10%">用户IP</th>
	    			<th>创建时间</th>
	    		</tr>
	    		<#if list??>
	    			<#list list as tracker>
			    		<tr>
			    			<td>${tracker.impId!}</td>
			    			<td>${tracker.idfa!}</td>
			    			<td>${tracker.o1!}</td>
			    			<td>${tracker.siteId!}</td>
			    			<td>${tracker.trackingPartner!}</td>
			    			<td>${tracker.ip!}</td>
			    			<td>${tracker.createTime!}</td>				
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