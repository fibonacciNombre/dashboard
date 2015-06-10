<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<script type="text/javascript">

function cerrarPopup(){
	$('#box').animate({'top':'30%'},60,function(){
        $('#overlay').fadeOut('fast');
    });
	
	
	return false;	
}

</script>

</head>
<body>
<div class="box" id="box">
        <a class="boxclose" id="boxclose" onclick="return cerrarPopup();"></a>
            <div class="container-float">
                <ul id="header-popup-visor">
                    <li id="header-popup-notarios">Informaci�n de Notar�a</li>
                    <li id="header-color-2"></li>
                    <li id="header-color-3"></li>
                    <li id="header-color-4"></li>
                    <li id="header-color-5"></li>
                    <li id="header-color-6"></li>
                </ul>
                <article class="container-body-popup">
                    <h2>${notaria.nombre }</h2>
                    <table cellpadding="0" cellspacing="0" class="table-notarias-details">
                        <tbody>
                            <tr>
                                <td class="header-grid-popup-1">Notar�a</td>
                                <td class="header-grid-popup-2">Datos de contacto</td>
                            </tr>
                            <tr>
                                <td>${notaria.nombre }</td>
                                <td class="dates-contact">
                                    <p><strong>Direcci�n:</strong> ${notaria.direccion }</p>
                                    <p><strong>Tel�fono:</strong> ${notaria.telefono1 }</p>
                                    <p><strong>P�gina Web:</strong> ${notaria.paginaweb }</p>
                                    <p><strong>Email:</strong> ${notaria.email}</p>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </article>
                <div id="footer-popup-detalle"></div>
            </div>
		</div>
</body>
</html>