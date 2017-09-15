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
<@h.header current="Test" />
<div class="main-panel">
	<@m.menu current="1-4" />
    <div class="right-panel">
    	<div class="nav">
	    	<ul class="clr">
	    		<li class="nav_current">模拟测试</li>
			</ul>
		</div>
		<div class="detail">
	    	<fieldset class="filter">
	    		<form id="form-search" action="${rc.contextPath}/serveTest" method="get">
		            <select name="campaignId" style="width:412px">
		            	<#list campaigns as campaign>
		            		<option value="${campaign.id}">${campaign.getName()}</option>
		            	</#list>
		            </select>（请选择活动）<br/>
		            <input type="text" name="sid" value="${sid!}" placeholder="请输入流水号" style="width:400px"><br/>
		            <input type="text" name="idfa" value="${idfa!}" placeholder="请输入ISO设备ID" style="width:400px">（苹果设备填写）<br/>
		            <input type="text" name="androidIdSha1" value="${androidIdSha1!}" placeholder="请输入安卓设备ID（SHA-1）" style="width:400px">（安卓设备填写）<br/>
		            <input type="text" name="subChn" value="${subChn!}" placeholder="请输入二级渠道" style="width:400px"><br/>
		            <select name="action" style="width:412px">
		            	<option value="click">点击测试</option>
		            	<option value="impression">曝光测试</option>
		            </select>
		            <br/><br/>
		            <div id="btn-search" class="button button-primary">测试</div>
		            <a href="${rc.contextPath}/test" class="button">清空</a>
	            </form>
	    	</fieldset>
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