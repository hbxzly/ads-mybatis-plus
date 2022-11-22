<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link href="js/kindeditor-4.1.10/themes/default/default.css" type="text/css" rel="stylesheet">
<script type="text/javascript" charset="utf-8" src="js/kindeditor-4.1.10/kindeditor-all-min.js"></script>
<script type="text/javascript" charset="utf-8" src="js/kindeditor-4.1.10/lang/zh_CN.js"></script>
<table class="easyui-datagrid" id="rechargeRecordList" title="账户清零记录"
       data-options="singleSelect:false,
                collapsible:true,
				url:'walletRechargeRecord/find',
				method:'get',
				rownumbers:true,
				pagination:true,
				pageSize:20,
		toolbar:toolbar_rechargeRecord">
    <thead>
    <tr>
        <th data-options="field:'ck',checkbox:true"></th>
        <th data-options="field:'mobile',align:'center',hidden:'true'">账号</th>
        <th data-options="field:'companyName',align:'center'">公司名称</th>
        <th data-options="field:'createTime',align:'center',width:'180px'">操作时间</th>
        <th data-options="field:'usdAmount',align:'center',width:'80px'">金额</th>
        <th data-options="field:'changeDesc',align:'center'">账户</th>
    </thead>
</table>

<div id="toolbar_rechargeRecord" style=" height: 22px; padding: 3px 11px; background: #fafafa;">


    <div style="float: left;">
        <a href="#" class="easyui-linkbutton" plain="true" icon="icon-reload" onclick="updateRechargeRecordList()">获取最新清零记录</a>
    </div>


    <div id="search_account_system" style="float: right;">
        <form id="searchRechargeRecord">

            账户：<input class="easyui-textbox" id="search_changeDesc" name="changeDesc"
                      style="width:150px; vertical-align: middle;"/>
            后台名称：<input class="easyui-textbox" id="search_companyName" name="companyName"
                      style="width:150px; vertical-align: middle;"/>

            <a href="#" class="easyui-linkbutton" plain="true" icon="icon-search" onclick="searchRechargeRecord()">查询</a>
            <a href="#" class="easyui-linkbutton" plain="true" icon="icon-reload" onclick="rechargeRecord_reload()">清除</a>
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


    function searchRechargeRecord() {
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
        const rechargeRecordList = $("#rechargeRecordList");
        var changeDesc = $("#search_changeDesc").val().trim();
        var companyName = $("#search_companyName").val().trim();

        rechargeRecordList.datagrid('options').url = "walletRechargeRecord/find?changeDesc=" + changeDesc + "&companyName="+companyName;
        rechargeRecordList.datagrid('reload');


    }

    function rechargeRecord_reload() {

        const rechargeRecordList = $("#rechargeRecordList");
        $("#search_changeDesc").textbox("setValue", "");
        $("#search_companyName").textbox("setValue", "");

        rechargeRecordList.datagrid('options').url = "walletRechargeRecord/find";
        rechargeRecordList.datagrid("reload");
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


    function updateRechargeRecordList(){

        $.ajax({
            type: "GET",
            url: "walletRechargeRecord/update",
            success: function (){
                rechargeRecord_reload();
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
            const changeDesc = $("#search_changeDesc").val();
            const companyName = $("#search_companyName").val();

            if (changeDesc !== '' || companyName !== '' ) {
                searchRechargeRecord();
            }
        }

    });

</script>