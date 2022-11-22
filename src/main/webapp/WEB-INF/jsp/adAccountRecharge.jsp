<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>


<div style="padding:10px 10px 10px 10px">
    <table class="easyui-datagrid" id="adAccountRechargeList" title="广告账户充值"
           data-options="singleSelect:false,
                collapsible:true,
				method:'get',
				rownumbers:true,
				pagination:true,
				pageSize:20,
                onClickRow: onClickRow"
    >
        <thead>
        <tr>
            <th data-options="field:'ck',checkbox:true"></th>
            <th data-options="field:'adAccountSystem',align:'center'">后台简称</th>
            <th data-options="field:'id',align:'center'">账号</th>
            <th data-options="field:'rechargeAmount',align:'center',editor: 'text'">余额</th>
        </tr>
        </thead>
    </table>
    <div style="padding:5px">
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitAdAccountRecharge()">充值</a>
    </div>
</div>
<script type="text/javascript">


    var editIndex = undefined;
    function endEditing(){
        if (editIndex == undefined){return true}
        if ($('#adAccountRechargeList').datagrid('validateRow', editIndex)){
            $('#adAccountRechargeList').datagrid('endEdit', editIndex);
            editIndex = undefined;
            return true;
        } else {
            return false;
        }
    }
    function onClickRow(index){
        console.log("点击行"+index);
        if (editIndex != index){
            if (endEditing()){
                $('#adAccountRechargeList').datagrid('selectRow', index)
                    .datagrid('beginEdit', index);
                editIndex = index;
            } else {
                $('#adAccountRechargeList').datagrid('selectRow', editIndex);
            }
        }
    }
    function submitAdAccountRecharge() {
        if (endEditing()) {
            var rows = $('#adAccountRechargeList').datagrid('getChanges');
            var $dg = $('#adAccountRechargeList');
            if ($dg.datagrid('getChanges').length) {
                // var inserted = $dg.datagrid('getChanges', "inserted"); //获取添加状态的行
                // var deleted = $dg.datagrid('getChanges', "deleted");//获取删除状态的行
                var updated = $dg.datagrid('getChanges', "updated");//获取修改状态的行
                // var effectRow = new Object();
                if (updated.length) {
                   var adAccountRechargeList = JSON.stringify(updated);
                   $.ajax({
                       type:"POST",
                       url:"selenium/adAccountRecharge",
                       contentType: 'application/json; charset=UTF-8',
                       data:adAccountRechargeList,
                       success:function (){

                       }
                   });
                }
            }
        }
    }


</script>
