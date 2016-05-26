<%-- 
    Document   : list
    Created on : 2016-5-25, 9:36:47
    Author     : jesse
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>秒杀列表</title>
        <link type="text/css" rel="stylesheet" href="${ctx}/static/bootstrap/css/bootstrap.min.css">
    </head>
    <body>
        <div class="container">
            <div class="panel panel-default">
                <div class="panel-heading text-center">
                    <h2>秒杀列表</h2>
                </div>
                <div class="panel-body">
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th>名称</th>
                                <th>库存</th> 
                                <th>开始时间</th>
                                <th>结束时间</th>
                                <th>创建时间</th>
                                <th>详情页</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${list}" var="sk">
                                <tr>
                                    <td>${sk.name}</td>
                                    <td>${sk.number}</td>
                                    <td>
                                        <fmt:formatDate value="${sk.startTime}" pattern="yyyy-MM-dd HH-mm-ss"></fmt:formatDate>
                                    </td>
                                    <td> 
                                        <fmt:formatDate value="${sk.endTime}" pattern="yyyy-MM-dd HH-mm-ss"></fmt:formatDate>                                
                                    </td>
                                    <td>
                                        <fmt:formatDate value="${sk.createTime}" pattern="yyyy-MM-dd HH-mm-ss"></fmt:formatDate>
                                    </td>
                                    <td>
                                        <a class="btn btn-info" href="${ctx}/seckill/${sk.seckillId}/detail" target="_blank">详情</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <script src="${ctx}/static/jquery/jquery-2.2.4.min.js"></script>
        <script src="${ctx}/static/bootstrap/js/bootstrap.min.js"></script>
    </body>
</html>
