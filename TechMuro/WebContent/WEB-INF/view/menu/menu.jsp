<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>:: Muro Tecnológico ::</title>
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<script type="text/javascript">

function enviarPeticion(url){

	$('#body-section').load(url);

	return false;
}

</script>
</head>
<body>
<c:if  test="${!empty sessionScope['usuario'].perfil.perfilPrivilegios}">
	<ul id="main-primary">
		<c:forEach items="${sessionScope['usuario'].perfil.perfilPrivilegios}" var="privilegios">
			<li><a href="${privilegios.privilegio.url}" onclick="return enviarPeticion('${privilegios.privilegio.url}');">
			<div class="${privilegios.privilegio.icono}"></div>${privilegios.privilegio.descripcion}</a>
				<c:if test="${privilegios.privilegio.idprivilegio==2 }">
					<ul>
						<li><a href="../tarifario/producto/list.htm" onclick="return enviarPeticion('../tarifario/producto/list.htm');"><div class="producto-icon"></div>Producto</a></li>
						<li><a href="../tarifario/capitulo/list.htm" onclick="return enviarPeticion('../tarifario/capitulo/list.htm');"><div class="capitulo-icon"></div>Capítulo</a></li>
						<li><a href="#"><div class="subcapitulo-icon"></div>Sub Capítulo</a></li>
						<li><a href="#"><div class="rubros-icon"></div>Rubros</a></li>
						<li><a href="#"><div class="categorias-icon"></div>Categorias</a></li>
						<li><a href="#"><div class="transacciones-icon"></div>Transacciones</a></li>
						<li><a href="#"><div class="avisos-icon"></div>Avisos Importantes</a></li>
					</ul>
				</c:if>
				<c:if test="${privilegios.privilegio.idprivilegio==12 }">
					<ul>
						<li><a href="../variables.htm"  onclick="return enviarPeticion('../variables.htm');"><div class="variables-icon"></div>Variables</a></li>
						<li><a href="../etiquetas.htm"  onclick="return enviarPeticion('../etiquetas.htm');"><div class="etiquetas-icon"></div>Etiquetas</a></li>
					</ul>
				</c:if>
			</li>
			
		</c:forEach>
	</ul>

</c:if>
</body>
</html>