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
		
			if("${mpoa}" == "si"){
				$("#ejemplo-1").attr('checked','checked');
			}
				
		});
		
		function actualizarVariables(){
				
				$.post("../actualizarVariables.htm",$("#subirAfiche").serialize(),function(data){
					console.log(data);
					if(data == "go"){
						alert("Se actualizo");
						enviarPeticion("../variables.htm");
					}else{
						alert("Todos los datos deben ser ingresados");
						enviarPeticion("../variables.htm");
					}
				});
				return false;
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
                <h1>Variables Generales</h1>
            </div>
            <div id="box-var">
	            <form method="post" id="subirAfiche"  onsubmit="return actualizarVariables();" class="scrollbar">
	            	<h1>Variables Configuracion de Afiches</h1>
					<div class="formu">
						<ul id="body-var">
                           	<li>Tiempo de Auto Actualización <span>(segs):</span></li>
                            <li id="right-li"><input class="input-white" name="ta" value="${ta}" type="text" id="url" class="input-popup-2" /></li>
                            <div></div>
                            <li>Tiempo de espera de Pop Ups: <span>(segs):</span></li>
                            <li id="right-li"><input class="input-white" name="tpu" value="${tpu}" type="text" id="url" class="input-popup-2" /></li>
							<div></div>
							<li>Ruta de Afiches:</li>
                            <li id="right-li"><input class="input-white" name="da" value="${da}" type="text" id="url" class="input-popup-2" /></li>
                            <div></div>
                            <li>Tiempo de duración del Video <span>(segs):</span></li>
                            <li id="right-li"><input class="input-white" name="tdv" value="${tdv}" type="text" id="url" class="input-popup-2" /></li>
						</ul>
					</div>
		 					
					<h1>Variables Configuración de Tasas y Tarifas</h1>
					<div class="formu">
                         <ul id="body-var"> 
                          	<li class="li-margin-bottom">
	                    		<div class="checkbox-style">
		                            <input type="checkbox" id="ejemplo-1" value="${mpoa}" name="mpoa" />
		                            <label for="ejemplo-1" ></label>
								</div>
                    		</li>
                    		<li class="li-margin-left">Mostrar Productos en orden alfabético</li>
						</ul>
					</div>
					<div class="center-imput">
	                    	<input type="submit" id="guardar" value="Guardar" class="button-image-complete-gren" >
					</div>
				</form>
			</div>
		</div>
		
</body>
</html>




