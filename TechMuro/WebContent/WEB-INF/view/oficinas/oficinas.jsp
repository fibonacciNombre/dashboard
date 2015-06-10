<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=8" />

<!-- Bordes para IE7
	<script>
        DD_roundies.addRule('input', '3px');
        DD_roundies.addRule('#container-search', '5px');
    </script>  
Fin Bordes para IE7 -->


	<script>
	
		grid = $("#list");
	 	var tudata = [];
	
		$(document).ready(function () {
		
			$("#buscarAfi").attr('disabled','true');
			
			var hh = [];
			'<c:forEach items="${oficinas}" var="a">';
				hh.push({idplaza:'${a.plaza.idplaza}',idoficina:'${a.idoficina}',codigo:'${a.codigo}',nombre:'${a.nombre}',plaza: '${a.plaza.nombre}'} );
		    '</c:forEach>';
			llenarGrid(hh);
			
			$('#subirArchivo').ajaxForm({
		    beforeSend: function(data) {
		    },
		    uploadProgress: function(event, position, total, percentComplete) {
		    	
		    },
		    success: function(data) {
		    },
			complete: function(xhr) {
				if($.trim(xhr.responseText)=="true"){
					alert("Se subió el archivo correctamente");
		    		enviarPeticion("../oficinas.htm");
				}else{
					alert("No se realizó cargo de archivo por inconsistencia de datos o no cumple con el formato de archivo");
				}
			}
			});
					
			$("#criterioAfi").blur(function(){
							if($.trim($("#criterioAfi").val()).length == 0){
									$("#buscarAfi").attr('disabled','true');
									$.post("../buscarOficina.htm",$("#buscaAfiche").serialize(),function(data){
										$("#list").jqGrid('clearGridData');
										jQuery("#list").setGridParam({
											data : eval(data)
										});
										jQuery("#list").trigger("reloadGrid");
									});
							    }
							    else{
							    	$("#buscarAfi").removeAttr('disabled');
							    }
			});
		});
	
		function myF(n,id){
		
			if(confirm("Está seguro que desea eliminar la oficina?")){
				
				$.get("../deleteOficina/"+n+".htm",function(data){

						tudata = eval(data);

						var pageSize = jQuery("#list").getGridParam("rowNum");
			            var totalRecords = jQuery("#list").getGridParam('records');
			            var totalPages = Math.ceil(totalRecords/pageSize);
			            var currentPage = jQuery("#list").getGridParam('page');
						
						if(Number(totalRecords)-1<=(currentPage-1)*pageSize){
							$("#list").jqGrid('clearGridData');
							jQuery("#list").setGridParam({data : tudata,page:Number(totalPages)-1}).trigger("reloadGrid");
						}else{
							$("#list").jqGrid('clearGridData');
						
							jQuery("#list").setGridParam({data : tudata,page:currentPage});
							jQuery("#list").trigger("reloadGrid");
						}
					
				});
			}else{
				return false;
			}
		}
		
		function buscarOficina(){
				
				$.post("../buscarOficina.htm",$("#buscaAfiche").serialize(),function(data){
					$("#ResultadoBusqueda p").attr("style","color:#07508f;");
					$("#list").jqGrid('clearGridData');
					jQuery("#list").setGridParam({
						data : eval(data)
					});
					jQuery("#list").trigger("reloadGrid");
					if(data.length == 2){
						alert("No se encontraron datos a listar");
					}
				});
				
				return false;
		}
	        
	    function linkUp(){
	     	$("#up").load("../upAgregarOficina.htm", function(){
				$('#overlay').fadeIn('fast',function(){
					$('#box').animate({'top':'100px'},500);
					$('#title-header-popup').text("Agregar Oficina");
				});  
	     	});
	     	return false;
	     }
	     
	     function myEdit(t,n){
	     	$("#up").load("../upEditarOficina/"+n+".htm", function(){
	     			$('#overlay').fadeIn('fast',function(){
	                            $('#box').animate({'top':'100px'},500);
	                            $('#title-header-popup').text("Editar Oficina");
	                        });
	     	});
	     	return false;
	     }
	        
	     function llenarGrid(Json){
			
	            grid.jqGrid({
	                datatype:'local',
	                data: Json,
	               	colNames: ["idplaza","idoficina","Código de Oficina","Nombre de Oficina", "Plaza","Editar", "Eliminar"],
					colModel:[ { name : 'idplaza', index : 'name', sorttype : "int", align : "center", viewable : false, hidden : true},
						{name : 'idoficina', index : 'name', width:100,sorttype : "int", align : "center", viewable : false, hidden : true},
						{name:'codigo',index:'name', width:100, align:"center"},
						{name:'nombre',index:'name', width:290, align:"center"},
						{name:'plaza',index:'name', width:72, align:"center"},
						{name:'editar',index:'total', width:72,align:"center", sorttype:"date"},
						{name:'eliminar',index:'total', width:72,align:"center",sorttype:"float"},
					],
					
	                rowNum:10,
	                rowList:[5,10,20],
	                pager: '#pager',
	                gridview: true,
	                rownumbers:false,
	                sortname: 'idoficina',
	                viewrecords: false,
	                sortorder: 'asc',
					
					width: 673,
	                height: '100%',
					
					gridComplete: function(){
						var ids = jQuery("#list").jqGrid('getDataIDs');
						for(var i=0;i < ids.length;i++){
							var cl = ids[i];
							be = "<div id='icon-edit' onclick=\"myEdit("+$("#"+cl+" td").first().text()+"  ,"+$("#"+cl+" td").first().next().text()+")\"\"></div>";
							se = "<div id='icon-eliminar' onclick=\"myF("+$("#"+cl+" td").first().next().text()+","+cl+")\"\"></div>";
	
							jQuery("#list").jqGrid('setRowData',ids[i],{editar:be});
							jQuery("#list").jqGrid('setRowData',ids[i],{eliminar:se});
							
						}
					}

	            });
	
	      }
		
	</script>
	

<!--[if lt IE 9]>
<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->

</head>

<body>

    	<div id="block-grid">
            <div id="line-body-sup"></div>
            <div id="back-title">
                <h1>Oficinas</h1>
            </div>
            
            <div id="container-upload">
            	<form:form action="../oficina/subirArchivo.htm" method="post" id="subirArchivo" cssClass="list-margin-center" 
            		enctype="multipart/form-data" modelAttribute="uploadedFile">
                    <ul>
                        <li><h2>Archivo :</h2></li> 
                        <li class="container-input-file">
                        	<div id="loadtextbotton-search" onclick="$('#file').click()"></div>
                        	<input id="textisselectedanyfile" type="text" value="Seleccionar archivo" class="input-file-2"/>
                        	<input name="file" id="file" type="file" style="display: none;" onchange="updateMessage()"/>
                        </li>
                        <li><input type="submit" value="Subir archivo" id="button-image-complete-load"/></li>
                    </ul>
                    
                    <p><span>*</span> Solo formatos excel (extensión .xls o .xlsx).</p>
                </form:form>
                
            </div>
            
            <div id="container-search">
	            <form action="" id="buscaAfiche" onsubmit="return buscarOficina();" method="post">
	            	<ul style="width: 525px;">
	                	<li><h2>Nombre:</h2></li>
	                    <li><input id="criterioAfi" name="criterio" type="text" class="input-border"></li>
	                    <li><input id="buscarAfi" type="submit" value="Buscar" class="button-image-complete">
	                    </li>
	                </ul>
	             </form>
            </div>
            
            
            <div id="container-grid">    
                <div class="table-grid">
                    <div class="tr-grid">
                        <div id="ResultadoBusqueda" class="td-grid" >
                            <p style="color:#f7f7f7">Resultados de la búsqueda...</p>
                        </div>
                        <div class="td-grid">
                            <ul id="block-grid-right">
                                <li>
                                    <a  onclick = "linkUp() " id="container-button-add"><div id="button-add-left"></div><div id="button-add-center"><span class="icon-plus-button">Agregar Oficina</span></div><div id="button-add-right"></div></a>
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
        
        <div id="up" class="up"></div>
        


</body>
</html>
