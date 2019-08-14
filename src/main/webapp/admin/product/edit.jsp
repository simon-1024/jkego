<%@ page language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<HTML>
<HEAD>
    <meta http-equiv="Content-Language" content="zh-cn">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <LINK href="${pageContext.request.contextPath}/css/Style1.css" type="text/css" rel="stylesheet">
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.11.3.min.js"></script>

    <script type="text/javascript">
        $(function () {
            $.post(
                "${pageContext.request.contextPath}/category/categoryList",
                function (data) {
                    var content = "";
                    for (var i = 0; i < data.length; i++) {
                        content += "<option value='" + data[i].cid + "'>" + data[i].cname + "</option>";
                    }
                    $("#cid").html(content);
                },
                "json"
            );
        });
    </script>
</HEAD>

<body>
<!--  -->
<form id="userAction_save_do" name="Form1" action="${pageContext.request.contextPath}/admin/addProduct"
      method="post" enctype="multipart/form-data">
    <input type="hidden" name="pid" value="${productByPid.pid}">
    <table cellSpacing="1" cellPadding="5" width="100%" align="center" bgColor="#eeeeee"
           style="border: 1px solid #8ba7e3" border="0">
        <tr>
            <td class="ta_01" align="center" bgColor="#afd1f3" colSpan="4"
                height="26">
                <strong><STRONG>编辑商品</STRONG>
                </strong>
            </td>
        </tr>

        <tr>
            <td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
                商品名称：
            </td>
            <td class="ta_01" bgColor="#ffffff">
                <input type="text" name="pname" value="${productByPid.pname}" id="userAction_save_do_logonName"
                       class="bg"/>
            </td>
            <td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
                是否热门：
            </td>
            <td class="ta_01" bgColor="#ffffff">

                <select name="is_hot">
                    <c:if test="${productByPid.is_hot==1}">
                        <option value="1" selected="selected">是</option>
                        <option value="0">否</option>
                    </c:if>
                    <c:if test="${productByPid.is_hot==0}">
                        <option value="1" >是</option>
                        <option value="0" selected="selected">否</option>
                    </c:if>
                </select>
            </td>
        </tr>
        <tr>
            <td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
                市场价格：
            </td>
            <td class="ta_01" bgColor="#ffffff">
                <input type="text" name="market_price" value="${productByPid.market_price}"
                       id="userAction_save_do_logonName" class="bg"/>
            </td>
            <td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
                商城价格：
            </td>
            <td class="ta_01" bgColor="#ffffff">
                <input type="text" name="shop_price" value="${productByPid.shop_price}"
                       id="userAction_save_do_logonName" class="bg"/>
            </td>
        </tr>
        <tr>
            <td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
                商品图片：
            </td>
            <td class="ta_01" bgColor="#ffffff" colspan="3">
                <input type="hidden" name="pimage" value="${productByPid.pimage}" >
                <input type="file" name="file" />
            </td>
        </tr>
        <tr>
            <td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
                所属分类：
            </td>
            <td class="ta_01" bgColor="#ffffff" colspan="3">
                <select name="cid" id="cid">

                </select>
            </td>
        </tr>
        <tr>
            <td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
                商品描述：
            </td>
            <td class="ta_01" bgColor="#ffffff" colspan="3">
                <textarea name="pdesc" rows="5" cols="30" >${productByPid.pdesc}</textarea>
            </td>
        </tr>
        <tr>
            <td class="ta_01" style="WIDTH: 100%" align="center"
                bgColor="#f5fafe" colSpan="4">
                <button type="submit" id="userAction_save_do_submit" value="确定" class="button_ok">
                    &#30830;&#23450;
                </button>

                <FONT face="宋体">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</FONT>
                <button type="reset" value="重置" class="button_cancel">&#37325;&#32622;</button>

                <FONT face="宋体">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</FONT>
                <INPUT class="button_ok" type="button" onclick="history.go(-1)" value="返回"/>
                <span id="Label1"></span>
            </td>
        </tr>
    </table>
</form>
</body>
</HTML>