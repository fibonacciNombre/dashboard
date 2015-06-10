<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<script>
	var grid;
	var mydata = [];
	$(document).ready(function() {
		mydata = [], grid = $("#list");
		llenarTabla();
	});

	function llenarTabla() {

		grid.jqGrid({
			datatype : 'local',
			data : mydata,
			colNames : [ "ID","Nombre", "Orden", "Ver<br/>Capítulos", "Editar", "Eliminar" ],
			colModel : [ {name : 'idproducto',index : 'name',sorttype : "int",align : "center",viewable : false, hidden : true}, 
			             {name : 'nombre',index : 'name',width : 320,sorttype : "string",align : "left"}, 
			             {name : 'orden',index : 'name',width : 72,align : "center"}, 
			             {name : 'cap',index : 'tax',width : 72,align : "center",sorttype : "string"}, 
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
					be = "<div id='icon-edit' onclick=\"openEditForm("+$("#"+cl+" td").first().text()+")\"></div>";
					se = "<div id='icon-eliminar' onclick=\"eliminaProducto("+$("#"+cl+" td").first().text()+")\"></div>";
					de = 	"<div id='icon-order-up' onclick=\"return up("+$("#"+cl+" td").first().text()+");\"/>"+
							"<div id='icon-order-down' onclick=\"return down("+$("#"+cl+" td").first().text()+");\"/>";
					ver= "<div id='icon-detail' onclick=\"return verCapitulos("+$("#"+cl+" td").first().text()+");\"/>";
					
					jQuery("#list").jqGrid('setRowData', ids[i], {editar : be});
					jQuery("#list").jqGrid('setRowData', ids[i], {eliminar : se});
					jQuery("#list").jqGrid('setRowData', ids[i], {orden : de});
					jQuery("#list").jqGrid('setRowData', ids[i], {cap : ver});
				}
			}
		});
	}
	
	function up(id){
		$.post("../tarifario/producto/up.htm", {id:id}, function(data){
			if($.trim(data)=="true"){
				buscar();
			}
		});
		return false;
	}
	
	function down(id){
		$.post("../tarifario/producto/down.htm", {id:id}, function(data){
			if($.trim(data)=="true"){
				buscar();
			}
		});
		return false;
	}

	function buscar() {
		$.post("../tarifario/producto/buscar.htm", $("#buscaProducto").serialize(), function(data) {
			$("#list").jqGrid('clearGridData');
			mydata = eval(data), grid = $("#list");
			jQuery("#list").setGridParam({ data : mydata });
			jQuery("#list").trigger("reloadGrid");
			
			$("#resultadosProductos").html("Resultados de la búsqueda...");
			
			if(mydata.length<1){
				alert("No existen resultados a mostrar");
			}
			
		});
		return false;
	}
	
	function openForm(){
     	$("#productoForm").load("../tarifario/producto/cargaForm.htm", {id:""}, function(){
     		$('#overlay').fadeIn('fast',function(){
                $('#box').animate({'top':'5%'},500);
            });
     	});
     	return false;
     }
	
	function validaTipoCliente(){
		if($("#tipoClienteList").val()!=""){
			$("#buscarProducto").removeAttr("disabled");
		}else{
			$("#buscarProducto").attr("disabled", "disabled");
		}
		
	}
	function openEditForm(id){
		$("#productoForm").load("../tarifario/producto/cargaForm.htm", {id:id}, function(){
     		$('#overlay').fadeIn('fast',function(){
                $('#box').animate({'top':'5%'},500);
            });
     	});
      	return false;
      }
	
	function eliminaProducto(id){
		
		if(confirm("¿Está seguro que desea eliminar el Producto?")){
			
			$.post("../tarifario/producto/eliminar.htm", {id:id}, function(data){
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
	
	function verCapitulos(id){
		
		$('#body-section').load("../tarifario/capitulo/list.htm", {id:id}, function(data){
			
		});

	}
	
</script>
</head>
<body>
	<div id="block-grid">
		<div id="line-body-sup"></div>
		<div id="back-title"><h1>Tarifarios > <span>Productos</span></h1></div>
		<div id="container-search">
			<form:form action="/tarifario/producto/cargaForm.htm" id="buscaProducto" onsubmit="return buscar();"
				method="post" modelAttribute="producto">
				<ul>
					<li><h2>Tipo de Cliente:</h2></li>
					<li id="right-li">
						<form:select path="tipocliente" id="tipoClienteList" onchange="validaTipoCliente();" cssClass="select-box">
							<form:option value="">- Seleccionar opción -</form:option>
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
					<li id="right-li"><form:input path="nombre" id="criterio" type="text" cssClass="input-border"/></li>
					<li><span></span></li>
					<div class="middle-align-button"><input type="submit" value="Buscar" id="buscarProducto"
						class="button-image-complete" disabled="disabled"></div>
				</ul>
			</form:form>
		</div>

		<div id="container-grid">
			<div class="table-grid">
				<div class="tr-grid">
					<div class="td-grid">
						<p id="resultadosProductos"> </p>
					</div>
					<div class="td-grid">
						<ul id="block-grid-right">
							<li>
								<a href="#" id="container-button-add" onclick="return openForm();">
									<div id="button-add-left"></div>
									<div id="button-add-center">
										<span class="icon-plus-button" >Agregar Producto</span>
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


<div id="productoForm"></div>
</body>
</html>