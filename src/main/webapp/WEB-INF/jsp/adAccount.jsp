<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link href="js/kindeditor-4.1.10/themes/default/default.css" type="text/css" rel="stylesheet">
<script type="text/javascript" charset="utf-8" src="js/kindeditor-4.1.10/kindeditor-all-min.js"></script>
<script type="text/javascript" charset="utf-8" src="js/kindeditor-4.1.10/lang/zh_CN.js"></script>
<table class="easyui-datagrid" id="adAccountList" title="广告账户信息"
       data-options="singleSelect:false,
                collapsible:true,
				url:'adAccount/find',
				method:'get',
				rownumbers:true,
				pagination:true,
				pageSize:20,
		toolbar:toolbar_adAccount">
    <thead>
    <tr>
        <th data-options="field:'ck',checkbox:true"></th>
        <th data-options="field:'adAccountName',align:'center'">账户名称</th>
        <th data-options="field:'adAccountId',align:'center'">账户ID</th>
        <th data-options="field:'balance',align:'center'">余额</th>
        <th data-options="field:'spendAmount',align:'center' ">7天消耗</th>
        <th data-options="field:'accountStatus',align:'center',formatter:formatAdAccountStatus">账户状态</th>
        <th data-options="field:'rechargeStatus',align:'center',formatter:formatRechargeStatus">账户是否可充值</th>
        <th data-options="field:'adAccountSystem',align:'center'">所属后台</th>
        <th data-options="field:'adAccountSystemAlias',align:'center'">后台简称</th>
        <th data-options="field:'isSell',align:'center', formatter:formatOrSell">是否卖出</th>
        <th data-options="field:'adAccountSystemStatus',align:'center',formatter:formatClientStatus">后台状态</th>
        <th data-options="field:'adAccountSystemBdName',align:'center'">商务</th>
        <th data-options="field:'updateTime',align:'center'">同步时间</th>
    </tr>
    </thead>
</table>

<div id="toolbar_adAccount" style=" height: 22px; padding: 3px 11px; background: #fafafa;">

    <div style="float: left;">
        <a href="#" class="easyui-linkbutton" plain="true" icon="icon-reload" onclick="updateAdAccountList()">更新所有账户</a>
    </div>
    <div style="float: left;">
        <a href="#" class="easyui-linkbutton" plain="true" icon="icon-reload" onclick="getAdAccountSpend()">更新7天消耗</a>
    </div>
    <div style="float: left;">
        <a href="#" class="easyui-linkbutton" plain="true" icon="icon-reload" onclick="updateAdAccount()">更新账户</a>
    </div>
    <div style="float: left;">
        <a href="#" class="easyui-linkbutton" plain="true" icon="icon-recharge" onclick="adAccountRecharge()">充值</a>
    </div>
    <div style="float: left;">
        <a href="#" class="easyui-linkbutton" plain="true" icon="icon-edit" onclick="openAccountSystem()">打开后台</a>
    </div>
    <%-- <div style="float: left;">
         <a href="#" class="easyui-linkbutton" plain="true" icon="icon-reload"
            onclick="update_all_account_system_balance()">更新所有后台</a>
     </div>
     <div style="float: left;">
         <a href="#" class="easyui-linkbutton" plain="true" icon="icon-reload" onclick="update_one_account_balance()">更新单个后台</a>
     </div>
     <div style="float: left;">
         <a href="#" class="easyui-linkbutton" plain="true" icon="icon-reload" onclick="update_one_account_balance()">测试启动浏览器</a>
     </div>--%>


    <div id="search_ad_account" style="float: right;">
        <form id="searchAdAccount">
            账户ID：<input class="easyui-textbox" id="search_ad_account_id" name="adAccountId"
                        style="width:150px; vertical-align: middle;"/>
            商务：<input class="easyui-textbox" id="search_ad_account_bd_name" name="adAccountSystemBdName"
                      style="width:150px; vertical-align: middle;"/>
            后台：<input class="easyui-textbox" id="search_ad_account_system" name="adAccountSystem"
                      style="width:150px; vertical-align: middle;"/>
            余额大于：<input class="easyui-textbox" id="search_ad_account_balance" name="balance" value="0"
                        style="width:70px; vertical-align: middle;"/>
            消耗金额大于：<input class="easyui-textbox" id="search_spend_amount" name="spendAmount" value="0"
                          style="width:70px; vertical-align: middle;"/>
            <%--商务：<input class="easyui-combobox" id="search_bdName" name="bdName" data-options="valueField:'11',textField:'11'"
                      style="width:150px; vertical-align: middle;"/>--%>
            账户状态：<select class="easyui-combobox" id="search_account_status" name="accountStatus"
                         style="width:100px; vertical-align: middle;">
            <option value="">--</option>
            <option value="1">正常</option>
            <option value="2">被封</option>
        </select>
            后台状态：<select class="easyui-combobox" id="search_ad_account_system_status" name="adAccountSystemStatus"
                         style="width:100px; vertical-align: middle;">
            <option value="">--</option>
            <option value="1">优秀</option>
            <option value="2">良好</option>
            <option value="3">限开户</option>
            <option value="4">限充值</option>
            <option value="5">清零50%</option>
        </select>
            <a href="#" class="easyui-linkbutton" plain="true" icon="icon-search" onclick="searchAdAccount()">查询</a>
            <a href="#" class="easyui-linkbutton" plain="true" icon="icon-reload" onclick="adAccountReload()">清除</a>
            <a href="#" class="easyui-linkbutton" plain="true" icon="icon-edit" onclick="exportExcel()">导出</a>
        </form>

    </div>


</div>


<div id="addAccountSystemAddWindow" class="easyui-window" title="添加账号"
     data-options="modal:true,closed:true,resizable:true,iconCls:'icon-save',href:'accountSystem/add'"
     style="width:45%;height:45%;padding:10px;">
</div>

<div id="accountEditWindow" class="easyui-window" title="编辑账户"
     data-options="modal:true,closed:true,resizable:true,iconCls:'icon-save',href:'account/edit'"
     style="width:65%;height:65%;padding:10px;">
</div>

<div id="accountRechargeWindow" class="easyui-window" title="账户充值"
     data-options="modal:true,closed:true,resizable:true,iconCls:'icon-save',href:'systemView/adAccountRecharge'"
     style="width:65%;height:65%;padding:10px;">
</div>


<script>
    $('#adAccountList').datagrid({
        view: detailview,
        detailFormatter:function(index,row){
            return '<div class="ddv" style="padding:5px 0 ; text-align:center"></div>';
        },
        onExpandRow: function(index,row){
            var ddv = $(this).datagrid('getRowDetail',index).find('div.ddv');
            ddv.panel({
                border:false,
                cache:false,
                href:'adAccount/findAccountSystemByClientName?clientName='+row.adAccountSystem,
                onLoad:function(){
                    $('#adAccountList').datagrid('fixDetailRowHeight',index);
                }
            });
            $('#adAccountList').datagrid('fixDetailRowHeight',index);
        }
    });

    function onClickRow(index){
        console.log("点击行"+index);
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

    function getSelections() {

        var adAccountList = $("#adAccountList");
        var sels = adAccountList.datagrid("getSelections");
        var accountSystems = [];
        for (var i in sels) {
            accountSystems.push(sels[i].adAccountSystem);
        }
        accountSystems = accountSystems.join(",");

        return accountSystems;
    }



    function formatOrSell(value){
        if (value == "1") {
            return '<span style="color:red">' + '是' + '</span>';
        }
    }

    function formatRechargeStatus(value) {
        if (value == "false") {
            return '是';
        }
        if (value == "true") {
            return '<span style="color:red">' + '否' + '</span>';
        }
    }

    function formatAdAccountStatus(value) {
        if (value == 1) {
            return '正常';
        } else if (value == 2) {
            return '<span style="color:red">' + '被封' + '</span>';
        }
    }


    function searchAdAccount() {

        const adAccountList = $("#adAccountList");
        const str = $("#search_ad_account_id").val().trim().replace(/\s+/ig, " ");
        if (str.indexOf(" ") !== -1) {
            const strList = str.split(" ");
            //去除重复项
            const list = [...new Set(strList)];
            const adAccountSystemBdName = $("#search_ad_account_bd_name").val();
            const balance = $("#search_ad_account_balance").val();
            adAccountList.datagrid('options').url = "adAccount/findByAdAccountIdList?adAccounts=" + list + "&adAccountSystemBdName=" + adAccountSystemBdName + "&balance=" + balance;
            adAccountList.datagrid('reload');

        } else {
            let adAccount = $("#searchAdAccount").serialize();
            adAccount = adAccount.replace(/(=\++)/g, '=').replace(/(\++\&)/g, '&').replace(/\++$/, '');
            adAccountList.datagrid('options').url = "adAccount/find?" + adAccount;
            adAccountList.datagrid('reload');
        }


    }
    function openAccountSystem(){
        var accountSystems = getSelections();
        $.ajax({
            type: "GET",
            url: "adAccount/openAdAccountSystem",
            data: "accountSystems="+accountSystems,
            success: function (){

            }
        });
    }

    function adAccountReload() {

        const adAccountList = $("#adAccountList");
        $("#search_ad_account_id").textbox("setValue", "");
        $("#search_ad_account_bd_name").textbox("setValue", "");
        $("#search_ad_account_system").textbox("setValue", "");
        $("#search_ad_account_balance").textbox("setValue", "0");
        $("#search_spend_amount").textbox("setValue", "0");
        $("#search_account_status").textbox("setValue", "");
        $("#search_ad_account_system_status").textbox("setValue", "");

        adAccountList.datagrid('options').url = "adAccount/find";
        adAccountList.datagrid("reload");
    }

    function updateAdAccountList() {
        $.ajax({
            type: "POST",
            url: "adAccount/updateAdAccountList",
            success: function () {
                $.messager.alert('提示', '账户更新成功!');
            }
        });
    }
    function updateAdAccount() {

        var clientName = getSelections();

        if (clientName.length == 0){
            $.messager.alert('提示', '请选择一个账户!');
            return;
        }
        if (clientName.indexOf(',') > 0){
            $.messager.alert('提示', '请只选择一个账户!');
            return;
        }
        $.ajax({
            type: "POST",
            url: "adAccount/updateAdAccount",
            data: "clientName="+clientName,
            success: function () {
                $.messager.alert('提示', '账户更新成功!');
                $("#adAccountList").datagrid("reload")
            }
        });
    }

    function exportExcel() {

        var adAccount = $("#searchAdAccount").serialize();
        window.open("http://localhost:8080/ads_mybatis_plus_war_exploded/adAccount/exportExcel?adAccount="+adAccount);
    }

    function adAccountRecharge2() {

        var adAccountList = $("#adAccountList");
        var adAccountTemp = adAccountList.datagrid("getSelections");
        var adAccounts = JSON.stringify(adAccountTemp);
        $.ajax({
            type: "POST",
            url: "selenium/adAccountRecharge",
            contentType: 'application/json; charset=UTF-8',
            data: adAccounts,
            success: function (){

            }
        });
    }
    function adAccountRecharge() {


        var adAccountList = $("#adAccountList");
        var sels = adAccountList.datagrid("getSelections");
        var accounts = [];
        for (var i in sels) {
            accounts.push(sels[i].adAccountId);
        }
        accounts = accounts.join(",");

        $("#accountRechargeWindow").window({
            onLoad: function (){
                $("#adAccountRechargeList").datagrid("options").url = "adAccount/getRechargeList?adAccounts=" + accounts;
                $("#adAccountRechargeList").datagrid("reload");
            }
        }).window("open");
    }
    function adAccountRecharge1() {

        $("#accountRechargeWindow").window({
            onLoad: function (){
                    //回显数据
                    var data = $("#adAccountList").datagrid("getSelections")[0];
                    $("#AdAccountRechargeForm").form("load", data);
            }
        }).window("open");
    }

    function getAdAccountSpend() {
        $.ajax({
            type: "POST",
            url: "adAccount/getAdAccountSpend",
            success: function () {
                $.messager.alert('提示', '账户消耗更新成功!');
            }
        });
    }


    $(document).keydown(function (event) {
        if (event.keyCode === 13) {
            const adAccountId = $("#search_ad_account_id").val();
            const adAccountBdName = $("#search_ad_account_bd_name").val();
            const adAccountSystem = $("#search_ad_account_system").val();
            const balance = $("#search_ad_account_balance").val();
            const spendAmount = $("#search_spend_amount").val();
            const accountStatus = $("#search_account_status").combobox("getValue");
            const adAccountSystemStatus = $("#search_ad_account_system_status").combobox("getValue");

            if (adAccountId !== '' || adAccountBdName !== '' || balance !== '' || adAccountSystem !== '' || spendAmount !== '' || accountStatus !== '' ||
                adAccountSystemStatus !== '') {
                searchAdAccount();
            }
        }

    });

</script>