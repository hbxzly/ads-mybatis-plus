<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link href="js/kindeditor-4.1.10/themes/default/default.css" type="text/css" rel="stylesheet">
<script type="text/javascript" charset="utf-8" src="js/kindeditor-4.1.10/kindeditor-all-min.js"></script>
<script type="text/javascript" charset="utf-8" src="js/kindeditor-4.1.10/lang/zh_CN.js"></script>
<table class="easyui-datagrid" id="accountCookieList" title="Cookie信息"
       data-options="singleSelect:false,
                collapsible:true,
				url:'accountCookie/find',
				method:'get',
				rownumbers:true,
				pagination:true,
				pageSize:20,
		toolbar:toolbar_accountCookie">
    <thead>
    <tr>
        <th data-options="field:'ck',checkbox:true"></th>
        <th data-options="field:'account',align:'center'">账号</th>
        <th data-options="field:'cookie',align:'center',width:'400px'">cookie</th>
        <th data-options="field:'updateTime',align:'center'">同步时间</th>
    </thead>
</table>

<div id="toolbar_accountCookie" style=" height: 22px; padding: 3px 11px; background: #fafafa;">


    <div style="float: left;">
        <a href="#" class="easyui-linkbutton" plain="true" icon="icon-reload" onclick="updateOneAccountSystemCookie()">更新单个后台</a>
    </div>
    <div style="float: left;">
        <a href="#" class="easyui-linkbutton" plain="true" icon="icon-reload" onclick="updateAllAccountSystemCookie()">更新所有后台</a>
    </div>


    <div id="search_account_cookies" style="float: right;">
        <form id="searchAccountSystemCookie">

            账号：<input class="easyui-textbox" id="search_account_cookie" name="account"
                      style="width:150px; vertical-align: middle;"/>

            <a href="#" class="easyui-linkbutton" plain="true" icon="icon-search" onclick="searchAccountCookie()">查询</a>
            <a href="#" class="easyui-linkbutton" plain="true" icon="icon-reload" onclick="accountCookieReload()">清除</a>
        </form>

    </div>


</div>


<div id="addAccountSystemAddWindow" class="easyui-window" title="添加账号"
     data-options="modal:true,closed:true,resizable:true,iconCls:'icon-save',href:'systemView/add'"
     style="width:45%;height:45%;padding:10px;">
</div>

<div id="accountEditWindow" class="easyui-window" title="编辑账户"
     data-options="modal:true,closed:true,resizable:true,iconCls:'icon-save',href:'account/edit'"
     style="width:65%;height:65%;padding:10px;">
</div>


<script>

    function getSelections() {

        var accountCookieList = $("#accountCookieList");
        var sels = accountCookieList.datagrid("getSelections");
        var accounts = [];
        for (var i in sels) {
            accounts.push(sels[i].account);
        }
        accounts = accounts.join(",");

        return accounts;
    }

    function formatClientStatus(value) {
        if (value == 1) {
            return '优秀';
        } else if (value == 2) {
            return '良好';
        } else if (value == 3) {
            return '限开户';
        } else if (value == 4) {
            return '<span style="color:red">' + '限充值' + '</span>';
        } else if (value == 5) {
            // return '清零50%';
            return '<span style="color:red">' + '清零50%' + '</span>';
        }
    }

    function searchAccountCookie() {

        var accountCookieList = $("#accountCookieList");
        var account = $("#search_account_cookie").val().trim();

        accountCookieList.datagrid('options').url = "accountCookie/find?account=" + account;
        accountCookieList.datagrid('reload');

    }

    function accountCookieReload() {

        const accountCookieList = $("#accountCookieList");
        $("#search_account_cookie").textbox("setValue", "");

        accountCookieList.datagrid('options').url = "accountCookie/find";
        accountCookieList.datagrid("reload");
    }


    function updateOneAccountSystemCookie(){

        var account = getSelections();
        if (account.length ==0){
            $.messager.alert('提示', '必须选择一个账户后台!');
            return;
        }
        if (account.indexOf(",")>0){
            $.messager.alert('提示', '只能选择一个账户后台!');
            return;
        }
        $.ajax({
            type: "GET",
            url: "accountCookie/updateOneAccountSystemCookie",
            data: 'account='+account,
            success: function (){
                $("#accountCookieList").datagrid("reload");
            }
        });
    }
    function updateAllAccountSystemCookie(){

        $.ajax({
            method: "GET",
            url: "accountCookie/updateAllAccountSystemCookie",
            success: function (){
                $.messager.alert('提示', '更新成功!', undefined, function(){
                    $("#accountCookieList").datagrid("reload");
                });
            }
        });
    }

   /* $(function(){
        $.ajax({
            type:'POST',
            url:"/ads_mybatis_plus_war_exploded/accountSystem/getBdName",//请求后台数据
            contentType:'application/json;charset=UTF-8',
            success:function(data){
                console.log(data);
                $("#search_bdName").combobox({//往下拉框塞值
                    data:data,
                    valueField:11,//value值
                    textField:11,//文本值
                    panelHeight:"auto"
                })
            }
        });
    });*/

    $(document).keydown(function (event) {
        if (event.keyCode === 13) {
            const account = $("#search_account").val();

            if ( account !== '' ) {
                searchAccountCookie();
            }
        }

    });

</script>