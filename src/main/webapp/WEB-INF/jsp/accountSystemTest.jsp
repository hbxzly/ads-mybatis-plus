<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link href="js/kindeditor-4.1.10/themes/default/default.css" type="text/css" rel="stylesheet">
<script type="text/javascript" charset="utf-8" src="js/kindeditor-4.1.10/kindeditor-all-min.js"></script>
<script type="text/javascript" charset="utf-8" src="js/kindeditor-4.1.10/lang/zh_CN.js"></script>
<table id="dg" title="DataGrid - Expand Row"
       data-options="url:'accountSystem/find',
       singleSelect:'false',
       collapsible:true,
       pagination:'true',
       pageSize:20,
       rownumbers:true">
    <thead>
    <tr>
        <th data-options="field:'ck',checkbox:true"></th>
        <th data-options="field:'clientName',align:'center'">后台名称</th>
        <th data-options="field:'account',align:'center'">账号</th>
        <th data-options="field:'password',align:'center'">密码</th>
        <th data-options="field:'email',align:'center'">邮箱</th>
        <th data-options="field:'clientAlias',align:'center'">简称</th>
        <th data-options="field:'bdName',align:'center'">商务</th>
        <th data-options="field:'clientBalance',align:'center'">余额</th>
        <th data-options="field:'totalCount',align:'center'">总账户数</th>
        <th data-options="field:'clientStatus',align:'center' ">状态</th>
        <th data-options="field:'agency',align:'center'">代理</th>
        <th data-options="field:'note',align:'center'">备注</th>
        <th data-options="field:'lastSyncTime',align:'center'">同步时间</th>
    </tr>
    </thead>
</table>
<script>
    $('#dg').datagrid({
        view: detailview,
        detailFormatter:function(index,row){
            return '<div style="padding:2px"><table class="ddv"></table></div>';
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
                    {field:'adAccountId',title:'账户ID',width:100},
                    {field:'balance',title:'余额',width:100},
                    {field:'spendAmount',title:'7天消耗',width:100},
                    {field:'accountStatus',title:'7天消耗',width:100,formatter:formatAdAccountStatus}
                ]],
                onResize:function(){
                    $('#dg').datagrid('fixDetailRowHeight',index);
                },
                onLoadSuccess:function(){
                    setTimeout(function(){
                        $('#dg').datagrid('fixDetailRowHeight',index);
                    },0);
                }
            });
            $('#dg').datagrid('fixDetailRowHeight',index);
        }
    });

    function formatAdAccountStatus(value) {
        if (value == 1) {
            return '正常';
        } else if (value == 2) {
            return '<span style="color:red">' + '被封' + '</span>';
        }
    }
</script>