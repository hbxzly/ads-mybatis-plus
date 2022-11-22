<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link href="js/kindeditor-4.1.10/themes/default/default.css" type="text/css" rel="stylesheet">
<script type="text/javascript" charset="utf-8" src="js/kindeditor-4.1.10/kindeditor-all-min.js"></script>
<script type="text/javascript" charset="utf-8" src="js/kindeditor-4.1.10/lang/zh_CN.js"></script>
<table class="easyui-datagrid" id="adAccountRechargeRecordList" title="账户充值记录"
       data-options="singleSelect:false,
                collapsible:true,
				url:'adAccountRechargeRecord/find',
				method:'get',
				rownumbers:true,
				pagination:true,
				pageSize:20,
                onDblClickRow: onDblClickRow,
		toolbar:toolbar_adAccountRechargeRecord">
    <thead>
    <tr>
        <th data-options="field:'ck',checkbox:true"></th>
        <th data-options="field:'tid',align:'center'">订单</th>
        <th data-options="field:'accountSystem',align:'center'">后台</th>
        <th data-options="field:'accountSystemIsSell',align:'center',formatter:formatAccountSystemIsSell">是否卖出</th>
        <th data-options="field:'channelAccountId',align:'center',formatter:formatAccountId">广告账户</th>
        <th data-options="field:'usdAmount',align:'center'">充值金额</th>
        <th data-options="field:'payMethod',align:'center',formatter:formatPayMethod">支付方式</th>
        <th data-options="field:'createTime',align:'center'">创建时间</th>
        <th data-options="field:'payTime',align:'center'">支付时间</th>
        <th data-options="field:'tradeDetailTypeDesc',align:'center'">充值类型</th>
        <th data-options="field:'tradeStatusName',align:'center'">状态</th>
        <th data-options="field:'payCard',align:'center',width:'80px',editor:{
                                                                    type:'combobox',
                                                                    options:{
                                                                        valueField:'id',
								                                        textField:'name',
                                                                         data:[{id:'客户充值',name:'客户充值'},
                                                                               {id:'储蓄7591',name:'储蓄7591'},
                                                                               {id:'交通9286',name:'交通9286'},
                                                                               {id:'招商9599',name:'招商9599'}]
                                                                    }
                                                          }">支付卡</th>
        <th data-options="field:'receiver',align:'center',width:'70px',editor:{
                                                                    type:'combobox',
                                                                    options:{
                                                                        valueField:'id',
								                                        textField:'name',
                                                                         data:[{id:'卢子月',name:'卢子月'},
                                                                               {id:'黄颖',name:'黄颖'},
                                                                               {id:'欧秀英',name:'欧秀英'},
                                                                               {id:'李金',name:'李金'},
                                                                               {id:'龙桂芳',name:'龙桂芳'},
                                                                               {id:'甘丽瑶',name:'甘丽瑶'}]
                                                                    }
                                                          }">运营</th>
        <th data-options="field:'note',align:'center',editor:'text',width:'400px'">备注</th>
    </thead>
</table>

<div id="toolbar_adAccountRechargeRecord" style=" height: 22px; padding: 3px 11px; background: #fafafa;">


    <div style="float: left;">
        <a href="#" class="easyui-linkbutton" plain="true" icon="icon-reload" onclick="getAdAccountRechargeRecord()">获取充值记录</a>
    </div>


    <div id="search_adAccount_recharge_record" style="float: right;">
        <form id="searchAdAccountRechargeRecord">

            广告账户：<input class="easyui-textbox" id="adAccount" name="adAccount"
                      style="width:150px; vertical-align: middle;"/>
            后台：<input class="easyui-textbox" id="accountSystem" name="accountSystem"
                      style="width:150px; vertical-align: middle;"/>

            <a href="#" class="easyui-linkbutton" plain="true" icon="icon-search" onclick="searchAdAccountRechargeRecord()">查询</a>
            <a href="#" class="easyui-linkbutton" plain="true" icon="icon-reload" onclick="adAccountRechargeRecordReload()">清除</a>
            <a href="#" class="easyui-linkbutton" plain="true" icon="icon-reload" onclick="submitAdAccountRechargeRecordChange()">保存</a>
        </form>

    </div>


</div>


<script>

    var editIndex = undefined;
    function endEditing(){
        if (editIndex == undefined){return true}
        if ($('#adAccountRechargeRecordList').datagrid('validateRow', editIndex)){
            $('#adAccountRechargeRecordList').datagrid('endEdit', editIndex);
            editIndex = undefined;
            return true;
        } else {
            return false;
        }
    }
    function onDblClickRow(index){

        if (editIndex != index){
            if (endEditing()){
                $('#adAccountRechargeRecordList').datagrid('selectRow', index)
                    .datagrid('beginEdit', index);
                editIndex = index;
            } else {
                $('#adAccountRechargeRecordList').datagrid('selectRow', editIndex);
            }
        }
    }
    function submitAdAccountRechargeRecordChange() {

        if (endEditing()) {

            var $dg = $('#adAccountRechargeRecordList');
            if ($dg.datagrid('getChanges').length) {
                // var inserted = $dg.datagrid('getChanges', "inserted"); //获取添加状态的行
                // var deleted = $dg.datagrid('getChanges', "deleted");//获取删除状态的行
                var updated = $dg.datagrid('getChanges', "updated");//获取修改状态的行
                // var effectRow = new Object();
                var adAccountRechargeRecordList = JSON.stringify(updated);
                console.log(adAccountRechargeRecordList);
                $.ajax({
                    type:"POST",
                    url:"adAccountRechargeRecord/updateAdAccountRechargeRecord",
                    contentType: 'application/json; charset=UTF-8',
                    data:adAccountRechargeRecordList,
                    success:function (){
                        $.messager.alert("提示","更新完成");
                        $dg.datagrid("reload");
                    }
                });
            }
        }
    }

    function formatPayMethod(value){

        if (value == "weixin"){
            return "微信";
        }
        if (value == "alipay"){
            return "支付宝";
        }
        if (value == ""){
            return "钱包";
        }
        return value;

    }

    function formatAccountSystemIsSell(value){
        if (value=="1"){
            return "是";
        }
        return value;
    }

    function formatAccountId(value) {
        if (value != null){
            if(value.indexOf("act_") != -1){
                return value.replace("act_","");
            }
            return value;
        }
        return value;
    }



    function searchAdAccountRechargeRecord() {
        var record  = $("#adAccountRechargeRecordList").datagrid("getSelections");
        var id = record[0].channelAccountId;
        console.log( id.replace("act_",""));
    }

    function adAccountRechargeRecordReload() {
        $("#adAccount").textbox("setValue","");
        $("#accountSystem").textbox("setValue","");
        $("#adAccountRechargeRecordList").datagrid("reload");
    }


    $(document).keydown(function (event) {
        if (event.keyCode === 13) {
            const account = $("#search_account").val();
            if ( account !== '' ) {
                searchAdAccountRechargeRecord();
            }
        }

    });

</script>