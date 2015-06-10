<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<script>
	var grid;
	var mydata = [];
	
	'<c:forEach items="${capituloList}" var="lista">';
		mydata.push({idcapitulo:"${lista.idcapitulo}", nombre:"${fn:replace(lista.nombre, '\"', '\\\"')}", orden:"${lista.orden}"} );
	'</c:forEach>';
	
	$(document).ready(function() {
		grid = $("#list");
		llenarTabla();
		validaBoton();
	});

	function llenarTabla() {

		grid.jqGrid({
			datatype : 'local',
			data : mydata,
			colNames : [ "ID","Nombre de Cap�tulo", "Orden", "Ver<br/>Sub-Cap�tulos", "Ver<br/>Rubros", "Ver<br/>Notas", "Editar", "Eliminar" ],
			colModel : [ {name : 'idcapitulo',index : 'name',sorttype : "int",align : "center",viewable : false, hidden : true}, 
			             {name : 'nombre',index : 'name',width : 220,sorttype : "string",align : "left"}, 
			             {name : 'orden',index : 'name',width : 80,align : "center"}, 
			             {name : 'subcap',index : 'tax',width : 120,align : "center",sorttype : "string"}, 
			             {name : 'rubros',index : 'tax',width : 60,align : "center",sorttype : "string"}, 
			             {name : 'notas',index : 'tax',width : 60,align : "center",sorttype : "string"}, 
			             {name : 'editar',index : 'total',width : 72,align : "center",sorttype : "date"}, 
			             {name : 'eliminar',index : 'total',width : 72,align : "center",sorttype : "float"}
			],
			rowNum : 10,
			rowList : [ 5, 10, 20 ],
			pager : '#pager',
			gridview : true,
			rownumbers : false,
			sortname : 'id',
			viewrecords : false,
			sortorder : 'desc',
			width : 673,
			height : '100%',

			gridComplete : function() {
				var ids = jQuery("#list").jqGrid('getDataIDs');
				for (var i = 0; i < ids.length; i++) {
					var cl = ids[i];
					be = "<div id='icon-edit'\" onclick=\"openEditForm("+$("#"+cl+" td").first().text()+")\"></div>";
					se = "<div id='icon-eliminar'\" onclick=\"eliminaCapitulo("+$("#"+cl+" td").first().text()+");\"></div>";
					de = 	"<div id='icon-order-up' onclick=\"return up("+$("#"+cl+" td").first().text()+");\"/>"+
							"<div id='icon-order-down' onclick=\"return down("+$("#"+cl+" td").first().text()+");\"/>";
					no = "<div id='icon-detail' onclick=\"return openNotasForm("+$("#"+cl+" td").first().text()+");\"/>";
					jQuery("#list").jqGrid('setRowData', ids[i], {editar : be});
					jQuery("#list").jqGrid('setRowData', ids[i], {eliminar : se});
					jQuery("#list").jqGrid('setRowData', ids[i], {orden : de});
					jQuery("#list").jqGrid('setRowData', ids[i], {notas : no});
				}

			}

		});
	}
	
	function up(id){
		$.post("../tarifario/capitulo/up.htm", {id:id}, function(data){
			if($.trim(data)=="true"){
				buscar();
			}
		});
		return false;
	}
	
	function down(id){
		$.post("../tarifario/capitulo/down.htm", {id:id}, function(data){
			if($.trim(data)=="true"){
				buscar();
			}
		});
		return false;
	}

	function buscar() {
		$.post("../tarifario/capitulo/buscar.htm", $("#buscaCapitulo").serialize(), function(data) {
			$("#list").jqGrid('clearGridData');
			mydata = eval(data), grid = $("#list");
			
			jQuery("#list").setGridParam({ data : mydata });
			jQuery("#list").trigger("reloadGrid");
			
			if(mydata.length<1){
				alert("No existen resultados a mostrar");
			}
		});
		return false;
	}
	
	function openForm(){
     	$("#capituloForm").load("../tarifario/capitulo/cargaForm.htm", $("#buscaCapitulo").serialize(), function(){
     		$('#overlay').fadeIn('fast',function(){
                $('#box').animate({'top':'5%'},500);
            });
     	});
     	return false;
	}
	
	function validaBoton(){
		if($("#productoList").val()!=""){
			$("#buscarCapitulo").removeAttr("disabled");
		}else{
			$("#buscarCapitulo").attr("disabled", "disabled");
		}
	}
	
	function openEditForm(id){
		
		$("#id").val(id);
		
		$("#capituloForm").load("../tarifario/capitulo/cargaForm.htm", $("#buscaCapitulo").serialize(), function(){
			$("#id").val("");
			$('#overlay').fadeIn('fast',function(){
                $('#box').animate({'top':'5%'},500);
            });
     	});
     	return false;
    }
	
	function eliminaCapitulo(id){
		
		if(confirm("�Est� seguro que desea eliminar el Cap�tulo?")){
			
			$.post("../tarifario/capitulo/eliminar.htm", {id:id}, function(data){
				if($.trim(data)=="true"){
					buscar();
				}else{
					alert($.trim(data));
				}
			});
			
		}else{
			return false;
		}
		
	}
	
	function llenaProductos(tipoCliente, combo){
		$.post("../tarifario/producto/combo.htm", {tipoCliente:tipoCliente}, function(data) {

			var datos = eval(data);
			$("#"+combo).empty();
			var option = $(document.createElement('option'));
            option.text("- Seleccionar opci�n -");
            option.val("");
            $("#"+combo).append(option);
            $(datos).each(function () {
                var option = $(document.createElement('option'));

                option.text(this.nombre);
                option.val(this.idproducto);

                $("#"+combo).append(option);
            });
            validaBoton();
		});
	}
	
	function openNotasForm(id){
     	$("#capituloForm").load("../tarifario/capitulo/cargaNotas.htm", {id:id}, function(){
     		$('#overlay').fadeIn('fast',function(){
                $('#box').animate({'top':'5%'},500);
            });
     	});
     	return false;
	}
	
</script>
</head>
<body>
	<div id="block-grid">
		<div id="line-body-sup"></div>
		<div id="back-title"><h1>Tarifarios > Productos > <span>Cap�tulos</span></h1></div>
		<div id="container-search">
			<form:form action="/tarifario/capitulo/buscar.htm" id="buscaCapitulo" onsubmit="return buscar();"
				method="post" modelAttribute="capitulo">
				<input type="hidden" id="id" name="id" />
				<ul>
					<li><h2>Tipo de Cliente:</h2></li>
					<li>
						<form:select path="producto.tipocliente" name="tipoCliente" id="tipoClienteList" 
						onchange="llenaProductos(this.value, 'productoList');" cssClass="select-box">
							<form:option value="">--Seleccionar opci�n--</form:option>
							<c:forEach items="${tipoCliente }" var="cliente">
								<form:option value="${cliente.codigo }">${cliente.valor }</form:option>
							</c:forEach>
						</form:select>
						<c:if test="${empty tipoCliente }">
							 - No existe datos de Tipo de Cliente
						</c:if>
					</li>
					<li><span></span></li>
					<li><h2>Producto:</h2></li>
					<li>
						<form:select path="producto.idproducto" id="productoList" onchange="validaBoton();" cssClass="select-box">
							<form:option value="">--Seleccionar opci�n--</form:option>
							<c:forEach items="${productoList }" var="productos">
								<form:option value="${productos.idproducto }">${productos.nombre }</form:option>
							</c:forEach>
						</form:select>
						<%-- <c:if test="${empty productoList }">
							 - No existe datos de Producto
						</c:if> --%>
					</li>
					<li><span></span></li>
					<li><h2>Cap�tulo:</h2></li>
					<li><form:input path="nombre" id="criterio" type="text" class="input-border"/></li>
					<li><span></span></li>
					<div class="middle-align-button"><input type="submit" value="Buscar" id="buscarCapitulo"
						class="button-image-complete"/></div>
						
				</ul>
				
			</form:form>
		</div>

		<div id="container-grid">
			<div class="table-grid">
				<div class="tr-grid">
					<div class="td-grid">
						<p>Resultados de la b�squeda...</p>
					</div>
					<div class="td-grid">
						<ul id="block-grid-right">
							<li>
								<a href="#" id="container-button-add" onclick="return openForm();">
									<div id="button-add-left"></div>
									<div id="button-add-center">
										<span class="icon-plus-button">Agregar Cap�tulo</span>
									</div>
									<div id="button-add-right"></div>
								</a>
							</li>
						</ul>
					</div>
				</div>
			</div>
			<!-- Grilla -->
			<table id="list"></table>
			<div id="pager"></div>
			<!-- Find Grilla -->
		</div>
	</div>
<div id="capituloForm"></div>
</body>
</html>