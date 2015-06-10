<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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
		
	
		});
	
	</script>
	
	<style type="text/css">
	
		#caja {
			max-width: 525px;
			height: auto;
			background: #fff;
			border: 1px solid #e9e9e9;
			border-radius: 5px;
			-webkit-border-radius: 5px;
			-moz-border-radius: 5px;
			-ms-border-radius: 5px;
			-o-border-radius: 5px;
			margin: 0 auto;
			padding: 7px 20px 20px 20px;
			list-style: none;
			display: block;
			float: left;
			font-family: "Stag Sans Book", Arial, sans-serif;
			margin-left: 100px;
		}
		
		.formu {
			max-width: 482px;
			height: auto;
			background: #e9e9e9;
			border: 1px solid #ddd;
			border-radius: 5px;
			-webkit-border-radius: 5px;
			-moz-border-radius: 5px;
			-ms-border-radius: 5px;
			-o-border-radius: 5px;
			margin: 0 auto;
			padding: 7px 20px 20px 20px;
			list-style: none;
			display: block;
			float: left;
			font-family: "Stag Sans Book", Arial, sans-serif;
			
		}
		
	</style>
	

<!--[if lt IE 9]>
<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->

</head>

<body>

    	<div id="block-grid">
            <div id="line-body-sup"></div>
            <div id="back-title">
                <h1>Etiquetas</h1>
            </div>
            <div id="caja">
            	<h1 style='font-family: "Stag Sans Book", Arial, sans-serif;font-size: 16px;'>Variables Configuración de Afiches</h1>
	            <form method="post" id="subirAfiche"  class="scrollbar "  >
	            		<div class="formu">
                            <ul id="body-popup" style="width: 430px;">
                            	<li>Tiempo de Auto Actualización (segs):</li>
                                <li id="right-li"><input style = "background: #fff; border: 1px solid #bbb;" name="" value="" type="text" id="url" class="input-popup-2" /></li>
                                <li>Tiempo de espera de Pop Ups:</li>
                                <li id="right-li"><input style = "background: #fff; border: 1px solid #bbb;" name="" value="" type="text" id="url" class="input-popup-2" /></li>
   								<li>Ruta de Afiches:</li>
                                <li id="right-li"><input style = "background: #fff; border: 1px solid #bbb;" name="" value="" type="text" id="url" class="input-popup-2" /></li>
                                <li>Tiempo de duración del Video</li>
                                <li id="right-li"><input style = "background: #fff; border: 1px solid #bbb;" name="" value="" type="text" id="url" class="input-popup-2" /></li>
   
                            </ul>
                          </div>
                          
                          <h1 style='font-family: "Stag Sans Book", Arial, sans-serif;font-size: 16px;'>Variables Configuración de Tasas y Tarifas</h1>
                          <div class="formu">
                           <ul id="body-popup" style="width: 430px;">
                            	<li>Tiempo de Auto Actualización (segs):</li>
                                <li id="right-li"><input style = "background: #fff; border: 1px solid #bbb;" name="" value="" type="text" id="url" class="input-popup-2" /></li>
                                
                            </ul>
                            </div>
                            <ul id="button-popup">
                            	<li></li>
                            	<li><input type="submit" id="guardar"  value="Editar" class="button-image-complete-gren" ></li>
                            	<li><input type="button" id="cancel" value="Guardar" class="button-image-complete"><li>
                            </ul>
                        </form>
            </div>
            
            
            
                
        </div>
        
        
        


</body>
</html>




