<%-- 
    Document   : Parameter
    Created on : Dec 14, 2023, 12:52:00 PM
    Author     : XUAN CUONG
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Thông số ký thuật</title>
        <link rel="stylesheet" type="text/css" href="CSS/IOT.css"/>
    </head>
    <body>
        <h2>Thông số hiện tại</h2>
        <c:set var="p" value="${requestScope.parameter}"/>
        Phần trăm tối thiểu: <span>${p.getMin()}</span> % <br>
        Phần trăm tôi đa: <span>${p.getMax()}</span> % <br>
        Chiều cao: <span>${p.getHeight()}</span> cm <br>
        Bán kính: <span>${p.getRadius()}</span> cm <br>
        
        <h2>Chỉnh sửa thông số</h2>
    <form id="dimensionForm" action="Parameter" method="post">
        <label for="Min">Phần trăm tối thiểu:</label>
        <input type="number" id="Min" name="Min" required>
        
        <label for="Max">Phần trăm tối đa:</label>
        <input type="number" id="Max" name="Max" required>

        <label for="width">Chiều cao:</label>
        <input type="number" id="Height" name="Height" required>

        <label for="height">Bán kính:</label>
        <input type="number" id="Radius" name="Radius" required>

        <input type="submit" value="Cập nhật">
    </form><br>
        <button onclick="window.location.href = '/BTL_IOT2/IOT'">Quay về</button>
    </body>
</html>
