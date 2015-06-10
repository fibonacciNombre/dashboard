<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>:: Muro Tecnológico ::</title>
<link href="css/core.css" rel="stylesheet" type="text/css">
<script src="js/URL_DD_roundies.js"></script>
<!-- Bordes para IE7
	<script>
        DD_roundies.addRule('input', '3px');
        DD_roundies.addRule('#container-search', '5px');
    </script>  
Fin Bordes para IE7 -->
<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="js/grid.locale-es.js"></script>
<script type="text/javascript" src="js/jquery.jqGrid.min.js" ></script>

<!--[if lt IE 9]>
<script src="js/html5.js"></script>s
<![endif]-->

</head>

<body style="background:#000;">
<form:form method="post" action="login/login.htm" commandName="usuario" id="login"  >
<div id="container-popup-login">
	<div id="container-login">
    	<div id="logo-log"></div>
        <div id="block-center">
        		<div id="icon-muro-login"></div>
                <p>Muro Tecnológico Administrativo</p> 
                
            <ul id="register-user">
                <li>Usuario :
                </li>
                <li><form:input path="codigo" />
                </li>
                <li>Contraseña :
                </li>
                <li><form:password path="nombres" />
                </li>
            </ul>
            <input type="submit" id="button-image-complete-login" value="Acceder">
		</div>
    </div>
</div>
</form:form>
</body>
</html>