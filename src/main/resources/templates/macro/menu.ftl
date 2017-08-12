<#macro menu current>
<div class="left-panel">
    <div class="menu-bar">跟踪管理</div>
    <ul class="menu-list">
        <li class='<#if current="1-1">menu-list-current</#if>'>- <a href="${rc.contextPath}/partner">客户管理</a></li>
        <li class='<#if current="1-2">menu-list-current</#if>'>- <a href="${rc.contextPath}/tracker">点击管理</a></li>
        <li class='<#if current="1-3">menu-list-current</#if>'>- <a href="${rc.contextPath}/callbackActive">回调管理</a></li>
        <li class='<#if current="1-4">menu-list-current</#if>'>&nbsp;&nbsp;&nbsp;- <a href="${rc.contextPath}/test" style="color:red;">模拟测试</a></li>
    </ul>
</div>
</#macro>