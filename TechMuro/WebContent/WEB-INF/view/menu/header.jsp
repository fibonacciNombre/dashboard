<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>:: Muro Tecnológico ::</title>
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<title>Header</title>
</head>
<body>

<div id="logo"></div>
                
        <div class="table-header-title">
            <div class="tr-header-title">
                <div class="td-header-title">
                    <div id="icon-title-header"></div>
                    <h2>Muro Tecnológico</h2>
                </div>
                <div class="td-header-title">
                    <ul id="block-header-right">
                        <li>
                            <a href="../login/logout.htm" id="container-button-close"><div id="button-close-left"></div><div id="button-close-center">Salir</div><div id="button-close-right"></div></a>
                        </li>
                        <li>
                            <a href="#" id="container-button-user"><div id="button-user-left"></div><div id="button-user-center">Usuario : <c:out value="${sessionScope['usuario'].nombres}" /></div><div id="button-user-right"></div></a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
</body>
</html>