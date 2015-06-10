<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<link rel="stylesheet" href="../css/validationEngine.jquery.css" type="text/css">

<!--[if lt IE 9]>
<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->

<style type="text/css">

	.error, .red{
	   color: red;
	   display:block;
	   font-family: "source-code-pro", Consolas, monospace !important;
	}
	
	.errorOFi{
	   position: absolute;
		top: 300px;
		left: 300px;
		display: block;
		cursor: pointer;
		z-index: 990;
	}
	
	#imagePreview {
	    width: 42px;
	    height:60px;
	    background: #BEE7FB;
		filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale); 
	}
	
	.blur-file {
		width: 154px;
		height: 27px;
		font-family: "Stag Sans Light", Arial, sans-serif;
		font-size: 14px;
		line-height: 27px;
		border-radius: 0 3px 3px 0;
		color: #555;
		background: #f7f7f7;
		border: 1px solid #e9e9e9;
		border-left: none;
		margin: 0;
		padding-left: 10px;
		position: absolute;
		top: 15px;
		right: -10px;
}

</style>
                      
            <script type="text/javascript">
            
				var k;
				
			    $(document).ready(function() {
			    
					window.prettyPrint() && prettyPrint();
					
					$('#plaza').multiselect({
						    includeSelectAllOption: true,
							enableCaseInsensitiveFiltering: true, 
							//buttonClass: 'validate[required] multiselect dropdown-toggle btn btn-default'
						});

                    if("${oficina.idoficina}"== ""){
                    
                    	jQuery("#subirAfiche").validationEngine({});
                    	
                    	jQuery("#buttonFile").click(function() {
					         jQuery("#imageInput").trigger('click');
					     });

						

                    }else
	                    {	
	                    	//$("#url").attr('disabled', 'disabled');
	                    	$("#body-popup").append('<li>Fecha de Actualización:</li> <li id="right-li"><fmt:formatDate value="${oficina.fechaactualizacion}" type="date" pattern="dd/MM/yyyy"/></li>');
	
	                    }
                    
			    });

				$(function() {
                    $('#boxclose').click(function(){
                        $('#box').animate({'top':'100px'},500,function(){
                            $('#overlay').fadeOut('fast');
                        });
                    });
                    
                    $('#cancel').click(function(){
                        $('#box').animate({'top':'100px'},500,function(){
                            $('#overlay').fadeOut('fast');
                        });
                    });

                    $('#guardar').click(function(){
                    
                    	jQuery("#subirAfiche").validationEngine('validate');
                    	var b = true;
                    	var v = true;
										
						 if(b && v){
						 	//cosnsole.log("test idafiche ","${afiche.idafiche}");
					 		$('#subirAfiche').ajaxForm({url:"../addOficina.htm",type:"post", success:function(data){
					 						
										$('#box').animate({'top':'100px'},500,function(){
				                            $('#overlay').fadeOut('fast');
				                            	tudata = eval(data);
				                            	
				                            	var pageSize = jQuery("#list").getGridParam("rowNum");
									            var totalRecords = jQuery("#list").getGridParam('records');
									            var totalPages = Math.ceil(totalRecords/pageSize);
									            var currentPage = jQuery("#list").getGridParam('page');
									            
				                            if($("#comunicadoHidden").val() == ""){
				                            
				                            jQuery("#list").setGridParam({data : tudata,page:totalPages}).trigger("reloadGrid");
				                            
				                            }else{
				                            	jQuery("#list").setGridParam({data : tudata,page:currentPage}).trigger("reloadGrid");
				                            }
			                        	});
		                    	}
		                    
		                    });
							
						 }else{
						 	return false;
						 }
                    });



                });
                
   
			</script>
</head>
<body>


                <div class="overlay" id="overlay" style="display:none;">
                	<div class="box" id="box">
                    <a class="boxclose" id="boxclose"></a>
                    	<div id="title-header-popup"></div>
                        <form:form method="post" id="subirAfiche"  class="scrollbar" enctype="multipart/form-data" modelAttribute="uploadedFile" commandName="oficina">
                            <ul id="body-popup">
                            	<li>Código de Oficina</li>
                                <li id="right-li"><form:input name="" value="" type="text" id="url" class="input-popup-2" path="codigo"/></li>
                                <li>Nombre de Oficina</li>
                                <li id="right-li"><form:input name="" value="" type="text" id="url" class="input-popup-2" path="nombre"/></li>

                              	<li>Plaza:</li>
								<li id="right-li">
									<form:select path="plaza.idplaza" id="plaza" >
										<form:option value="">-- Seleccionar Plaza --</form:option>
										<c:forEach items="${plazaList}" var="plazaList">
											<form:option value="${plazaList.idplaza }">${plazaList.nombre}</form:option>
										</c:forEach>
									</form:select>
								</li>
                                
                                <li>Fecha de Creación:</li>
                                <li id="right-li"><fmt:formatDate value="${oficina.fechacreacion}" type="date" pattern="dd/MM/yyyy"/></li>
								<form:input id="comunicadoHidden" path="idoficina" type="hidden" />
                            </ul>
                            <ul id="button-popup">
                            	<li><input type="submit" id="guardar"  value="Guardar" class="button-image-complete-gren" ></li>
                            	<li><input type="button" id="cancel" value="Cancelar" class="button-image-complete"><li>
                            </ul>
                        </form:form>

                    </div>
                </div>

</body>
</html>