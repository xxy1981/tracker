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
	<@m.menu current="1-1" />
    <div class="right-panel">
    	<div class="nav">
	    	<ul class="clr">
	    		<li class="nav_current">客户查询</li>
			</ul>
		</div>
		<div class="detail">
	    	<fieldset class="filter">
	    		<form id="form-search" action="${rc.contextPath}/partner" method="get">
		            <input type="text" name="id" value="${id!}" placeholder="请输入客户ID" style="width:300px">
		            <input type="text" name="name" value="${name!}" placeholder="请输入客户名称" style="width:300px">
		            <#--select name="type" style="width:300px">

		            	<#list enums["com.xxytech.tracker.enums.DeviceType"]?values as e> 
		            		<#if type==e.code>
			            		<option value="${e.code}" selected="selected">${e.getName()}</option>
			            	<#else>
			            		<option value="${e.code}">${e.getName()}</option>
			            	</#if>
		            	</#list>
		            </select-->
		            <br/>
		            <input type="hidden" id="pageNo" name="pageNo" value="${pageNo!}">
		            <div id="btn-search" class="button button-primary">查询</div>
		            <a href="${rc.contextPath}/partner" class="button">清空</a>
	            </form>
	    	</fieldset>
	    	<@p.page display="${pageHtmlDisplay!}" />
			<#if (list??) && (list?size > 0)>
	    	<table class="table-list">
	    		<tr>
	    			<th><input id="cb_all" type="checkbox" value="all" /></th>
	    			<th style="width:25%">客户ID(点击更新)</th>
	    			<th style="width:55%">客户名称</th>
	    			<th>创建时间</th>
	    		</tr>
	    		<#if list??>
	    			<#list list as partner>
			    		<tr>
			    			<td>
			    				<input type="checkbox" class="cb_single" id="cb_id_${partner.id}" value="${partner.id}"  action-add='true' action-del='true' />
			    			</td>
			    			<td><a href="${rc.contextPath}/partner/${partner.id}" >${partner.id}</a></td>
			    			<td>${partner.name!}</td>
			    			<td>${partner.createTime!}</td>				
			    		</tr>
		    		</#list>
	    		</#if>
	    	</table>
	    	</#if>
	    	
	    	<div class="tools-bar">
	    		<a href="${rc.contextPath}/partner/insert" id="btn-add" can-do="true"><span>增加</span></a>
	    		<a id="btn-del" can-do="false"><span class="tool-disable">删除</span></a>
	    		<span class="error-msg"></span>
	    	</div>
	    	<div class="remodal" data-remodal-id="tool-confirm">
				<button data-remodal-action="close" class="remodal-close"></button>
				<div>是否确定？</div>
				<button data-remodal-action="cancel" class="remodal-cancel">取消</button>
				<button data-remodal-action="confirm" class="remodal-confirm">确定</button>
			</div>
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