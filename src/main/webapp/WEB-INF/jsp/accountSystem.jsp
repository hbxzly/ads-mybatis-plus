<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link href="js/kindeditor-4.1.10/themes/default/default.css" type="text/css" rel="stylesheet">
<script type="text/javascript" charset="utf-8" src="js/kindeditor-4.1.10/kindeditor-all-min.js"></script>
<script type="text/javascript" charset="utf-8" src="js/kindeditor-4.1.10/lang/zh_CN.js"></script>
<div id="toolbar_accountSystem" style=" height: 22px; padding: 3px 11px; background: #fafafa;">

    <a href="#" class="easyui-linkbutton" plain="true" icon="icon-reload" onclick="updateAllAccountSystem()">更新所有后台</a>

    <a href="#" class="easyui-linkbutton" plain="true" icon="icon-reload" onclick="updateOneAccountSystem()">更新所选后台</a>

    <a href="#" class="easyui-linkbutton" plain="true" icon="icon-reload" onclick="updateAccountSystemBasicInfo()">获取基础信息</a>

    <a href="#" class="easyui-linkbutton" plain="true" icon="icon-reload" onclick="openChrome()">启动浏览器</a>

    <a href="#" class="easyui-linkbutton" plain="true" icon="icon-reload" onclick="closeChrome()">关闭浏览器</a>

    <a href="#" class="easyui-linkbutton" plain="true" icon="icon-add" onclick="accountSystemInsert()">添加</a>

    <a href="#" class="easyui-linkbutton" plain="true" icon="icon-edit" onclick="accountSystemEdit()">编辑</a>


    <div id="search_account_system" style="float: right;">
        <form id="searchAccountSystem">
            后台名称：<input class="easyui-textbox" id="search_client_name" name="clientName"
                        style="width:150px; vertical-align: middle;"/>
            账号：<input class="easyui-textbox" id="search_account" name="account"
                      style="width:150px; vertical-align: middle;"/>
            商务：<input class="easyui-textbox" id="search_bdName" name="bdName" style="width:100px; vertical-align: middle;"/>
            余额大于：<input class="easyui-textbox" id="search_balance" name="clientBalance" value="0" style="width:70px; vertical-align: middle;"/>
            消耗金额大于：<input class="easyui-textbox" id="search_spend_amount" name="spendAmount" value="0" style="width:70px; vertical-align: middle;"/>
            <%--商务：<input class="easyui-combobox" id="search_bdName" name="bdName" data-options="valueField:'11',textField:'11'"
                      style="width:150px; vertical-align: middle;"/>--%>
            代理：<input class="easyui-textbox" id="search_agency" name="agency"
                      style="width:100px; vertical-align: middle;"/>
            后台状态：<select class="easyui-combobox" id="search_client_status" name="clientStatus"
                         style="width:100px; vertical-align: middle;">
            <option value="">--</option>
            <option value="1">优秀</option>
            <option value="2">良好</option>
            <option value="3">限开户</option>
            <option value="4">限充值</option>
            <option value="5">清零50%</option></select>
            备注：<input class="easyui-textbox" id="search_note" name="note"
                      style="width:100px; vertical-align: middle;"/>
            </select>
            <a href="#" class="easyui-linkbutton" plain="true" icon="icon-search" onclick="searchAccountSystem()">查询</a>
            <a href="#" class="easyui-linkbutton" plain="true" icon="icon-reload" onclick="accountSystemReload()">清除</a>
        </form>

    </div>


</div>
<table class="easyui-datagrid" id="accountSystemList" style="width: 100% ;height: 700px" title="后台账号信息"
       data-options="singleSelect:false,
                collapsible:true,
				url:'accountSystem/find',
				method:'get',
				rownumbers:true,
				pagination:true,
				pageSize:20">
    <thead>


    <tr>
        <th data-options="field:'ck',checkbox:true"></th>
        <th data-options="field:'clientName',align:'center'">后台名称</th>
        <th data-options="field:'account',align:'center'">账号</th>
        <th data-options="field:'password',align:'center'">密码</th>
        <th data-options="field:'email',align:'center'">邮箱</th>
        <th data-options="field:'clientAlias',align:'center'">简称</th>
        <th data-options="field:'bdName',align:'center'">商务</th>
        <th data-options="field:'clientBalance',align:'center'">钱包余额</th>
        <th data-options="field:'spendAmount',align:'center'">7天消耗</th>
        <th data-options="field:'totalCount',align:'center'">总账户数</th>
        <th data-options="field:'activeCount',align:'center'">活跃账户数</th>
        <th data-options="field:'clientStatus',align:'center', formatter:formatClientStatus ">状态</th>
        <th data-options="field:'agency',align:'center'">代理</th>
        <th data-options="field:'isAble',align:'center', formatter:formatOrAble">是否在用</th>
        <th data-options="field:'note',align:'center'">备注</th>
        <th data-options="field:'lastSyncTime',align:'center'">同步时间</th>
    </tr>
    </thead>
</table>

<div id="addAccountSystemAddWindow" class="easyui-window" title="添加账号"
     data-options="modal:true,closed:true,resizable:true,iconCls:'icon-save',href:'systemView/add'"
     style="width:45%;height:45%;padding:10px;">
</div>

<div id="accountSystemEditWindow" class="easyui-window" title="编辑账户"
     data-options="modal:true,closed:true,resizable:true,iconCls:'icon-save',href:'systemView/edit'"
     style="width:65%;height:65%;padding:10px;">
</div>


<script>
    $('#accountSystemList').datagrid({
        view: detailview,
        detailFormatter:function(index,row){
            return '<div style="width:100px padding:2px"><table class="ddv"></table></div>';
        },
        onExpandRow: function(index,row){
            var ddv = $(this).datagrid('getRowDetail',index).find('table.ddv');
            ddv.datagrid({
                url:'accountSystem/findAdAccountByAccountSystem?accountSystem='+row.clientName,
                fitColumns:true,
                singleSelect:true,
                rownumbers:true,
                loadMsg:'',
                height:'auto',
                columns:[[
                    {field:'adAccountName',title:'账户名称',width:100},
                    {field:'adAccountId',title:'账户ID',width:100},
                    {field:'balance',title:'余额',width:100},
                    {field:'spendAmount',title:'7天消耗',width:100},
                    {field:'accountStatus',title:'账户状态',width:100,formatter:formatAdAccountStatus}
                ]],
                onResize:function(){
                    $('#accountSystemList').datagrid('fixDetailRowHeight',index);
                },
                onLoadSuccess:function(){
                    setTimeout(function(){
                        $('#accountSystemList').datagrid('fixDetailRowHeight',index);
                    },0);
                }
            });
            $('#accountSystemList').datagrid('fixDetailRowHeight',index);
        }
    });

    function formatAdAccountStatus(value) {
        if (value == 1) {
            return '正常';
        } else if (value == 2) {
            return '<span style="color:red">' + '被封' + '</span>';
        }
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
    function formatOrAble(value) {
        if (value == 1) {
            return '在用';
        } else if (value == 2) {
            return '<span style="color:red">' + '不用' + '</span>';
        }
    }

    function searchAccountSystem() {
        /*
        const accountSystemList = $("#accountSystemList");
        const accountSystem = $("#searchAccountSystem").serialize();
        accountSystemList.datagrid("options").url = "accountSystem/find?" + accountSystem;
        accountSystemList.datagrid('reload');
        */
            /*
                    //获取pagination组件对象
                    var pagination = $('#accountList').datagrid('getPager').data("pagination").options;
                    // 获取当前分页索引值
                    var page = pagination.pageNumber;
                    // 获取当前分页条数
                    var rows = pagination.pageSize;
            */
        const accountSystemList = $("#accountSystemList");
        const str = $("#search_client_name").val().trim().replace(/\s+/ig, " ");
        if(str.indexOf(" ") !== -1){
            const strList = str.split(" ");
            //去除重复项
            const list = [...new Set(strList)];
            accountSystemList.datagrid('options').url="accountSystem/findByNameList?names="+list;
            accountSystemList.datagrid('reload');

        }else {
            let accountSystem = $("#searchAccountSystem").serialize();
            accountSystem = accountSystem.replace(/(=\++)/g, '=').replace(/(\++\&)/g, '&').replace(/\++$/, '');
            accountSystemList.datagrid('options').url = "accountSystem/find?" + accountSystem;
            accountSystemList.datagrid('reload');
        }

    }

    function accountSystemReload() {

        const accountSystemList = $("#accountSystemList");
        $("#search_client_name").textbox("setValue", "");
        $("#search_account").textbox("setValue", "");
        $("#search_bdName").textbox("setValue", "");
        $("#search_agency").textbox("setValue", "");
        $("#search_note").textbox("setValue", "");
        $("#search_balance").textbox("setValue", "0");
        $("#search_spend_amount").textbox("setValue", "0");
        $("#search_client_status").textbox("setValue", "");

        accountSystemList.datagrid('options').url = "accountSystem/find";
        accountSystemList.datagrid("reload");
    }

    function accountSystemInsert(){
        $("#addAccountSystemAddWindow").window("open");
    }


    function getAccountSystemSelectionsAccounts() {

        var accountSystemList = $("#accountSystemList");
        var sels = accountSystemList.datagrid("getSelections");
        var accounts = [];
        for (var i in sels) {
            accounts.push(sels[i].account);
        }
        accounts = accounts.join(",");

        return accounts;
    }


    function updateOneAccountSystem(){

        var accounts = getAccountSystemSelectionsAccounts();
        $.ajax({
            type: "GET",
            url: "accountSystem/updateAccountSystem",
            data: 'accounts='+accounts,
            success: function (){
                $.messager.alert('提示', '你选的后台更新成功!', undefined, function(){
                    $("#accountSystemList").datagrid("reload");
                });
            }
        });
    }
    function updateAccountSystemBasicInfo(){

        var accounts = getAccountSystemSelectionsAccounts();
        $.ajax({
            type: "GET",
            url: "accountSystem/updateAccountSystemBasicInfo",
            data: 'accounts='+accounts,
            success: function (){
                $.messager.alert('提示', '更新成功!', undefined, function(){
                    $("#accountSystemList").datagrid("reload");
                });
            }
        });
    }

    function updateAllAccountSystem(){

        $.ajax({
            type: "GET",
            url: "accountSystem/updateAllAccountSystem",
            success: function (){
                $.messager.alert('提示', '所有后台更新成功!', undefined, function(){
                    $("#accountSystemList").datagrid("reload");
                });
            }
        });
    }

    function openChrome(){
        var accountSystemList = $("#accountSystemList");
        var accountSystemTemp = accountSystemList.datagrid("getSelections");
        if (accountSystemTemp.length >= 5){
            $.messager.alert('提示','一次打开浏览器过多',undefined,function () {

            })
        }else {
                var accountSystem = JSON.stringify(accountSystemTemp);
                console.log(accountSystem);
                $.ajax({
                    type: "POST",
                    url: "selenium/openChrome",
                    contentType: 'application/json; charset=UTF-8',
                    data: accountSystem,
                    success: function (){

                    }
                });
            }
        }
    function closeChrome(){
        $.ajax({
            type: "POST",
            url: "selenium/closeChrome",
            success: function (){

            }
        });
    }


    function accountSystemEdit() {

        var accounts = getAccountSystemSelectionsAccounts();

        if (accounts.length == 0) {
            $.messager.alert('提示', '必须选择一个账户才能编辑!');
            return;
        }
        if (accounts.indexOf(',') > 0) {
            $.messager.alert('提示', '只能选择一个账户!');
            return;
        }

        $("#accountSystemEditWindow").window({
            onLoad: function () {
                //回显数据
                var data = $("#accountSystemList").datagrid("getSelections")[0];
                $("#editAccountSystemForm").form("load", data);

            }
        }).window("open");
    }


    $(document).keydown(function (event) {
        if (event.keyCode === 13) {
            const clientName = $("#search_client_name").val();
            const account = $("#search_account").val();
            const bdName = $("#search_bdName").val();
            const agency = $("#search_agency").val();
            const balance = $("#search_balance").val();
            const spendAmount = $("#search_spend_amount").val();
            const note = $("#search_note").val();
            const clientStatus = $("#search_client_status").combobox("getValue");

            if (clientName !== '' ||  account !== '' ||bdName !== '' || agency !== '' ||balance !== '' ||spendAmount !== '' || note !== '' ||
                clientStatus !== '' ) {
                searchAccountSystem();
            }
        }

    });

</script>