<%-- 
    Document   : index
    Created on : Nov 28, 2023, 1:49:40 PM
    Author     : XUAN CUONG
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Control Pump</title>
    <script src="https://cdn.jsdelivr.net/npm/mqtt/dist/mqtt.min.js"></script>
</head>
<link rel="stylesheet" type="text/css" href="CSS/IOT.css"/>

<body>
    <h2>Quản lý máy bơm</h2>
    <p>Khoảng cách: <span id="distance">-</span> cm</p>
<!--    <p>Tỷ lệ: <span id="percent">-</span> %</p>-->
    <div class="container">
        <div class="progress" id="progress" name="progress"></div>
        <div class="percentage" id="percentage" name="percentage">0%</div>
    </div>
    Tỷ lệ nước
    <p>Trạng thái máy bơm: <span id="state">-</span></p>
    <button onclick="buttonPressed('1')">Bật máy bơm</button>
    <button onclick="buttonPressed('0')">Tắt máy bơm</button>
    <div class="Table">
    <table border="1">
        <tr>
            <th>Ngày bơm</th>
            <th>Lượng nước đã bơm</th>
            <th>Đơn vị</th>
        </tr>
        <c:forEach items="${requestScope.Water}" var="x">
            <tr>
                <td>${x.getDate()}</td>
                <td>${x.getWaterused()}</td>
                <td>cm³</td>
            </tr>
        </c:forEach>
    </table>
    </div>
    <button class="btntk" onclick="window.location.href = '/BTL_IOT2/IOT'">Cập nhật thống kê</button>
    <button onclick="window.location.href='/BTL_IOT2/Parameter'">Chỉnh sửa thông số</button>
    
    <form id="hiddenForm" action="IOT" method="Post" style="display: none;">
        <input type="hidden" id="distanceInput" name="distance" value="">
    </form>
    
    <c:set var="p" value="${requestScope.parameter}"/>
    <input type="hidden" id="Min" name="Min" value="${p.getMin()}">
    <input type="hidden" id="Max" name="Max" value="${p.getMax()}">
    <input type="hidden" id="Height" name="Height" value="${p.getHeight()}">
    <input type="hidden" id="Radius" name="Radius" value="${p.getRadius()}">
    
</body>
<script src="JS/JVScript.js">
    </script>
</html>

