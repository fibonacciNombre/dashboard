<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<link rel="stylesheet" href="../css/validationEngine.jquery.css" type="text/css">

<script type="text/javascript">
	var mydataForm = [];
	
	$(document).ready(function() {
		window.prettyPrint() && prettyPrint();
		
		$('#producto').validationEngine({
			onValidationComplete: function(form, status){
					if(status==true){
				    	productoAgregar();
				    }else{
				    	alert("Se requiere llenar todos los campos obligatorios");
					}
				}
			}
		);
	});

	function validar(){
		return jQuery('#producto').validationEngine('validate');
	}
	
	function cerrar() {
		$('#box').animate({'top':'5%'},500,function(){
            $('#overlay').fadeOut('fast');
            $("#productoForm").innerHtml = "";
        });
	}
	
	function productoAgregar(){
		
		$.post("../tarifario/producto/agregar.htm", $("#producto").serialize(), function(data){
			if($.trim(data)=="true"){
				alert("insertado correctamente");
				$("#box").animate({"top":"5%"},500,function(){
					$("#overlay").fadeOut("fast"); 
					$("#productoForm").innerHtml = "";
			    });
				
				$("#tipoClienteList").val($("#tipocliente").val());
				$("#criterio").val("");
				
				$.post("../tarifario/producto/buscar.htm", $("#buscaProducto").serialize(), function(data) {
					$("#list").jqGrid('clearGridData');
					mydata = eval(data), grid = $("#list");
					$("#list").setGridParam({data:mydata}).trigger("reloadGrid");
					
					'<c:if test="${empty producto.idproducto}">';
						var pageSize = $("#list").getGridParam("rowNum");
						var totalRecords = $("#list").getGridParam('records');
						var totalPages = Math.ceil(totalRecords/pageSize);
						jQuery("#list").setGridParam({page:Number(totalPages)}).trigger("reloadGrid");
					'</c:if>';
				});
				
				return false;
			}else{
				alert($.trim(data));
				return false;
			}
		});	
		return false;
	}
	
</script>
</head>
<body>
	<div id="container">
		<div class="overlay" id="overlay" style="display: none;">
<!-- 			<div class="box" id="box"> -->
<!-- 				<a class="boxclose" id="boxclose" onclick="cerrar();"></a> -->
<%-- 				<c:choose> --%>
<%-- 				    <c:when test="${!empty producto.idproducto}"> --%>
<!-- 				        <div id="title-header-popup">Editar Producto</div> -->
<%-- 				    </c:when> --%>
<%-- 				    <c:otherwise> --%>
<!-- 				        <div id="title-header-popup">Agregar Producto</div> -->
<%-- 				    </c:otherwise> --%>
<%-- 				</c:choose> --%>
				
<%-- 				<form:form method="post" action="../tarifario/producto/agregar.htm" commandName="producto"  --%>
<%-- 					id="producto" class="scrollbar-2"> --%>
<%-- 					<form:hidden path="idproducto"/> --%>
<!-- 					<ul id="body-popup-2"> -->
<!-- 						<li>Nombre del Producto:</li> -->
<%-- 						<li id="right-li"><form:input path="nombre" id="nombre" type="text" --%>
<%-- 							class="validate[required] input-popup-2" maxlength="50"/></li> --%>
<!-- 						<li>Tipo de Cliente:</li> -->
<!-- 						<li id="right-li"> -->
<%-- 							<form:select path="tipocliente" id="tipocliente"  --%>
<%-- 							cssClass="validate[required] select-box"> --%>
<%-- 								<form:option value="">- Seleccionar opción -</form:option> --%>
<%-- 								<c:forEach items="${tipoCliente }" var="cliente"> --%>
<%-- 									<form:option value="${cliente.codigo }">${cliente.valor }</form:option> --%>
<%-- 								</c:forEach> --%>
<%-- 							</form:select> --%>
<!-- 						</li> -->
						
<!-- 						<li>Fecha de Creación:</li> -->
<%-- 						<li id="right-li"><fmt:formatDate value="${producto.fechacreacion}" type="date" pattern="dd/MM/yyyy"/></li> --%>
						
<%-- 						<c:if test="${!empty producto.idproducto}"> --%>
<!-- 							<li>Fecha de Actualización:</li> -->
<%-- 							<li id="right-li"><fmt:formatDate value="${null}" type="date" pattern="dd/MM/yyyy"/></li> --%>
<%-- 						</c:if> --%>

						
						
<!-- 					</ul> -->
<!-- 					<ul id="button-popup"> -->
<!-- 						<li><input type="submit" value="Guardar" -->
<!-- 							class="button-image-complete-gren"></li> -->
<!-- 						<li><input type="button" value="Cancelar" -->
<!-- 							class="button-image-complete" onclick="cerrar();"> -->
<!-- 						<li> -->
<!-- 					</ul> -->
<%-- 				</form:form> --%>
				
<!-- 			</div> -->
		</div>
		<div id="contratoForm"></div>
	</div>
</body>
</html>