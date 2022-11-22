<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<link href="js/kindeditor-4.1.10/themes/default/default.css" type="text/css" rel="stylesheet">

<link href="css/uploadfile.css" rel="stylesheet">
<script src="js/jquery.uploadfile.js"></script>

<script type="text/javascript" charset="utf-8" src="js/kindeditor-4.1.10/kindeditor-all-min.js"></script>
<script type="text/javascript" charset="utf-8" src="js/kindeditor-4.1.10/lang/zh_CN.js"></script>
<div style="padding:10px 10px 10px 10px">
    <form id="editAccountSystemForm" class="accountForm" method="post">
        <table cellpadding="5">
            <tr>
                <td>后台名称:
                    <input class="easyui-textbox" type="text" id="clientName" name="clientName" readonly="readonly" data-options="required:true"/>
                </td>
            </tr>
            <tr>
                <td>账号:
                    <input class="easyui-textbox" type="text" id="account" name="account" readonly="readonly" data-options="required:true"/>
                </td>
            </tr>
            <tr>
                <td>密码:
                    <input class="easyui-textbox" type="text" id="password" name="password" data-options="required:true"/>
                </td>
            </tr>
            <tr>
                <td>简称:
                    <input class="easyui-textbox" type="text" id="clientAlias" name="clientAlias" data-options="required:true"/>
                </td>
            </tr>
            <tr>
                <td>代理:
                    <input class="easyui-textbox" type="text" id="agency" name="agency" data-options="required:true"/>
                </td>
            </tr>
            <tr>
                <td>是否在用:
                    <select class="easyui-combobox" id="isAble" name="isAble"
                            style="width:100px; vertical-align: middle;">
                        <option value="1">在用</option>
                        <option value="2">不用</option></select>
                </td>
            </tr>
            <tr>
                <td>备注:
                    <input class="easyui-textbox" type="text" id="note" name="note" data-options="required:true"/>
                </td>
            </tr>

        </table>
    </form>
    <div style="padding:5px">
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitEditAccountSystemForm()">提交</a>
    </div>
</div>
<script type="text/javascript">

    $.fn.serializeObject = function() {
        var o = {};
        var a = this.serializeArray();
        $.each(a, function() {
            if (o[this.name] !== undefined) {
                if (!o[this.name].push) {
                    o[this.name] = [o[this.name]];
                }
                o[this.name].push(this.value || '');
            } else {
                o[this.name] = this.value || '';
            }
        });
        return o;
    };


    function submitEditAccountSystemForm() {

        var accountSystemTemp =  $("#editAccountSystemForm").serializeObject();
        var accountSystem = JSON.stringify(accountSystemTemp);
        console.log(accountSystem);
        $.ajax({
            type: "POST",
            url: "accountSystem/editAccountSystem",
            contentType: 'application/json; charset=UTF-8',
            data: accountSystem,
            success: function (){
                $.messager.alert('提示', '账户编辑成功', undefined, function(){
                    $("#accountSystemEditWindow").window("close");
                    $("#accountSystemList").datagrid("reload");
                })
            },
            error: function (){
                $.messager.alert('报错', '账户编辑失败', undefined, function(){
                    $("#accountSystemEditWindow").window("close");
                })
            }
        });

    }
</script>
