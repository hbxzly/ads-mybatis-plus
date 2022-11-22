<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<link href="js/kindeditor-4.1.10/themes/default/default.css" type="text/css" rel="stylesheet">

<link href="css/uploadfile.css" rel="stylesheet">
<script src="js/jquery.uploadfile.js"></script>

<script type="text/javascript" charset="utf-8" src="js/kindeditor-4.1.10/kindeditor-all-min.js"></script>
<script type="text/javascript" charset="utf-8" src="js/kindeditor-4.1.10/lang/zh_CN.js"></script>
<div style="padding:10px 10px 10px 10px">
    <form id="addAccountSystemForm" class="accountForm" method="post">
        <table cellpadding="5">
            <tr>
                <td>后台名称:
                    <input class="easyui-textbox" type="text" id="clientName" name="clientName" data-options="required:true"/>
                </td>
            </tr>
            <tr>
                <td>账号:
                    <input class="easyui-textbox" type="text" id="account" name="account" data-options="required:true"/>
                </td>
            </tr>
            <tr>
                <td>密码:
                    <input class="easyui-textbox" type="text" id="password" name="password" data-options="required:true"/>
                </td>
            </tr>
            <tr>
                <td>邮箱:
                    <input class="easyui-textbox" type="text" id="email" name="email" data-options="required:true"/>
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
                <td>备注:
                    <input class="easyui-textbox" type="text" id="note" name="note" data-options="required:true"/>
                </td>
            </tr>

        </table>
    </form>
    <div style="padding:5px">
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitAddAccountSystemForm()">提交</a>
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


    function submitAddAccountSystemForm() {

        var accountSystemTemp =  $("#addAccountSystemForm").serializeObject();
        var accountSystem = JSON.stringify(accountSystemTemp);
        console.log(accountSystem);
        $.ajax({
            type: "POST",
            url: "accountSystem/addAccountSystem",
            contentType: 'application/json; charset=UTF-8',
            data: accountSystem ,
            success: function (){
                $.messager.alert('提示', '账户添加成功', undefined, function(){
                    $("#addAccountSystemAddWindow").window("close");
                    $("#accountSystemList").datagrid("reload");
                })
            },
            error: function (){
                $.messager.alert('报错', '账户添加失败', undefined, function(){
                    $("#addAccountSystemAddWindow").window("close");
                })
            }
        });

    }
</script>
