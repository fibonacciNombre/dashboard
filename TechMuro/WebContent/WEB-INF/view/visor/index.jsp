<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset='UTF-8' />

<!--[if lt IE 9]>
<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
<title>:: Muro Tecnológico ::</title>
  
<!-- must have -->
<link href="../css-visor/core.css" rel="stylesheet" type="text/css">
<link href="../css-visor/allinone_carousel.css" rel="stylesheet" type="text/css">


<script type="text/javascript" src="../js-visor/jquery/all.js" ></script>
<!-- <script type="text/javascript" src="../js-visor/jquery.min.js" ></script> -->
<script type="text/javascript" src="../js-visor/jquery-ui.min.js"></script>
<script type="text/javascript" src="../js-visor/jquery.ui.touch-punch.min.js" ></script>
<script type="text/javascript" src="../js-visor/allinone_carousel.js" ></script>
<!-- <script type="text/javascript" src="../js-visor/jquery.colorbox.js" ></script> -->

<!-- <script src="../js-visor/portBox.slimscroll.min.js"></script> -->
<script type="text/javascript" src="../js-visor/top_up-min.js"></script>


<script>
	$(document).ready(function(){
		
		var time = new Date().getTime();
		$(document.body).bind("mousemove keypress", function(e) {
		    time = new Date().getTime();
		});
		
		function auto_refresh() {
		    if(new Date().getTime() - time >= parseInt(30000))
		        window.location.reload(true);
		    else
		        setTimeout(auto_refresh, parseInt("${tiempoActualizacion}"));
		}
		
		setTimeout(auto_refresh, parseInt("${tiempoActualizacion}"));
		
		var text = "";
		
		var textMin = [];
		
		var textMinTodas = [];
		
		'<c:forEach items="${oficinaVisor}" var="a">';
		if("${a.afiche.tipoafiche}"=="v"){
			text += '<li data-title="${a.afiche.descripcion}"><a href="${a.afiche.video}"  toptions="title = Visualizador de Afiches, width = 853, height = 505, layout = quicklook, shaded = 1, group = 1, readAltText: 1"><img src="../pick/${a.afiche.idafiche}.htm" alt="" /></a></li>';
		}else{
			if("${a.afiche.tipoafiche}"=="y"){
				
				text += '<li data-title="${a.afiche.descripcion}"><a href="${a.afiche.video}"  toptions="title = Visualizador de Afiches, width = 853, height = 505, type = flash, layout = quicklook, shaded = 1, group = 1, readAltText: 1"><img src="../pick/${a.afiche.idafiche}.htm" alt="" /></a></li>';
			}else{
				text += '<li data-title="" ><a href="../visor/big/${a.afiche.idafiche}.htm" toptions="title = Visualizador de Afiches, width = auto, height = auto, effect = clip, layout = quicklook, shaded = 1, group = 1"><img src="../pick/${a.afiche.idafiche}.htm" alt="" /></a></li>';
			}
		}
		'</c:forEach>';
		
		
		
		'<c:forEach items="${todas}" var="t">';
		if("${t.afiche.tipoafiche}"=="v"){
			text += '<li data-title="${t.afiche.descripcion}" ><a href="${t.afiche.video}" toptions="title = Visualizador de Afiches, width = 853, height = 505, layout = quicklook, shaded = 1, shaded = 1, group = 1, readAltText: 1"><img src="../pick/${t.afiche.idafiche}.htm" alt="" /></a></li>';
		}else{
			if("${t.afiche.tipoafiche}"=="y"){
				text += '<li data-title="${t.afiche.descripcion}"><a href="${t.afiche.video}"  toptions="title = Visualizador de Afiches, width = 853, height = 505, type = flash, layout = quicklook, shaded = 1, group = 1, readAltText: 1"><img src="../pick/${t.afiche.idafiche}.htm" alt="" /></a></li>';
			}else{
				text += '<li data-title=""><a href="../visor/big/${t.afiche.idafiche}.htm" toptions="title = Visualizador de Afiches, width = auto, height = auto, effect = clip, layout = quicklook, shaded = 1, group = 1"><img src="../pick/${t.afiche.idafiche}.htm" alt="" /></a></li>';
			}
		}	
		'</c:forEach>';
		
		var countMin = 0;
		
		var countMinTodas = 0;
		
		'<c:forEach items="${miniaturaTodas}" var="a">';
		if("${a.afiche.tipoafiche}"=="v"){
			textMinTodas.push('<li data-title="${a.afiche.descripcion}"><a href="${a.afiche.video}"  toptions="title = Visualizador de Afiches, width = 853, height = 505, layout = quicklook, shaded = 1, group = 2, readAltText: 1"><img src="../pick/${a.afiche.idafiche}.htm" alt="" /></a></li>');
		}else{
			if("${a.afiche.tipoafiche}"=="y"){
				textMinTodas.push('<li data-title="${a.afiche.descripcion}"><a href="${a.afiche.video}"  toptions="title = Visualizador de Afiches, width = 853, height = 505, type = flash, layout = quicklook, shaded = 1, group = 2, readAltText: 1"><img src="../pick/${a.afiche.idafiche}.htm" alt="" /></a></li>');
			}else{
				textMinTodas.push('<li data-title="" ><a href="../visor/big/${a.afiche.idafiche}.htm" toptions="title = Visualizador de Afiches, width = auto, height = auto, effect = clip, layout = quicklook, shaded = 1, group = 2"><img src="../pick/${a.afiche.idafiche}.htm" alt="" /></a></li>');
			}
		}
		countMinTodas++;
		'</c:forEach>';
		
		
		'<c:forEach items="${miniatura}" var="t">';
		if("${t.afiche.tipoafiche}"=="v"){
			textMin.push('<li data-title="${t.afiche.descripcion}" ><a href="${t.afiche.video}" toptions="title = Visualizador de Afiches, width = 853, height = 505, layout = quicklook, shaded = 1, group = 2, readAltText: 1"><img src="../pick/${t.afiche.idafiche}.htm" alt="" /></a></li>');
		}else{
			if("${t.afiche.tipoafiche}"=="y"){
				textMin.push('<li data-title="${t.afiche.descripcion}"><a href="${t.afiche.video}"  toptions="title = Visualizador de Afiches, width = 853, height = 505, type = flash, layout = quicklook, shaded = 1, group = 2, readAltText: 1"><img src="../pick/${t.afiche.idafiche}.htm" alt="" /></a></li>');
			}else{
				textMin.push('<li data-title=""><a href="../visor/big/${t.afiche.idafiche}.htm" toptions="title = Visualizador de Afiches, width = auto, height = auto, effect = clip, layout = quicklook, shaded = 1, group = 2"><img src="../pick/${t.afiche.idafiche}.htm" alt="" /></a></li>');
			}
		}
		countMin++;
		'</c:forEach>';
		
		if((countMin + countMinTodas) <= 4){
			
			for (var i = 0; i < textMin.length; i++) {
				$("#container-nav-min-left #container-min-center").append(textMin[i]);
			}
			for (var j = 0; j < textMinTodas.length; j++) {
				$("#container-nav-min-left #container-min-center").append(textMinTodas[j]);
			}
			$("#container-nav-min-right").attr("style","display:none");
		}else{
			var iM=0;
			
			var iMT=0;
			
			for (var i = 0; i < textMin.length; i++) {
				$("#container-nav-min-left #container-min-center").append(textMin[i]);
				iM++;
				if(iM==4){
					break;
				}
			}
			
			if(countMin==4){
				for (var j = 0; j < textMinTodas.length; j++) {
					$("#container-nav-min-right #container-min-center").append(textMinTodas[j]);
				}
			}else{
				if(countMin>4){
					for (var i = 4; i < textMin.length; i++) {
						$("#container-nav-min-right #container-min-center").append(textMin[i]);
					}
					for (var j = 0; j < textMinTodas.length; j++) {
						$("#container-nav-min-right #container-min-center").append(textMinTodas[j]);
					}
				}else{
				
					for (var j = 0; j < textMinTodas.length; j++) {
						$("#container-nav-min-left #container-min-center").append(textMinTodas[j]);
						iMT++;
						if(iMT==(4-iM)){
							break;
						}
					}
					for (var j = 4-iM; j < textMinTodas.length; j++) {
						$("#container-nav-min-right #container-min-center").append(textMinTodas[j]);
					}
					
				}
			}
		}
		
		$(".allinone_carousel_list").append(text);
		
		var textComunicados = [];
		var countComunicado = 0;

		'<c:forEach items="${comunicados}" var="a">';
			countComunicado++;
			if(countComunicado % 2 == 0){
				if("${a.comunicado.tipocomunicado}" == "Imagen"){
					textComunicados.push('<li><a href="../visor/comunicadoImagen/${a.comunicado.idcomunicado}.htm" id="activator" class="clear-right " toptions="width = auto, height = auto, effect = clip, layout = quicklook, shaded = 1, group = 1"><p>${a.comunicado.titulo}</p><span>${a.comunicado.fechacreacion}</span></a></li>');
				}else{
					textComunicados.push('<li><a href="../visor/comunicado/${a.comunicado.idcomunicado}.htm" id="activator" class="clear-right " toptions="width = auto, height = auto, effect = clip, layout = quicklook, shaded = 1, group = 1"><p>${a.comunicado.titulo}</p><span>${a.comunicado.fechacreacion}</span></a></li>');
				}
			}else{
				if("${a.comunicado.tipocomunicado}" == "Imagen"){
					textComunicados.push('<li><a href="../visor/comunicadoImagen/${a.comunicado.idcomunicado}.htm" class=""  toptions="width = auto, height = auto, effect = clip, layout = quicklook, shaded = 1, group = 1"><p>${a.comunicado.titulo}</p><span>${a.comunicado.fechacreacion}</span></a></li>');
				}else{
					textComunicados.push('<li><a href="../visor/comunicado/${a.comunicado.idcomunicado}.htm" class="" toptions="width = auto, height = auto, effect = clip, layout = quicklook, shaded = 1, group = 1"><p> ${a.comunicado.titulo}</p><span>${a.comunicado.fechacreacion}</span></a></li>');
				}
			}
	 	'</c:forEach>';
	 	
	 	for (var d = 0; d < textComunicados.length; d++) {
			$("#marqueeComunicados").append(textComunicados[d]);
		}
	 	
		
		jQuery('#allinone_carousel_sweet').allinone_carousel({
		  skin: 'sweet',
		  width: 1920,
		  height: 458,
		  autoPlay: 5,
		  responsive:true,
		  resizeImages:true,
		  autoHideBottomNav:false,
		  //easing:'easeOutBounce',
		  numberOfVisibleItems:5,
		  elementsHorizontalSpacing:150,	
		  elementsVerticalSpacing:15,
		  verticalAdjustment:140,
		  animationTime:0.5,
		  circleLeftPositionCorrection:10,
		  circleTopPositionCorrection:10,
		  nextPrevMarginTop:30,
		  playMovieMarginTop:-15,
		  bottomNavMarginBottom:-25,
		  
		  showCircleTimer:false,
		  showPreviewThumbs:false,
		  showBottomNav:false
	  	});
		
		$('#boxclose').click(function(){
            $('#box').animate({'top':'5%'},60,function(){
                $('#overlay').fadeOut('fast');
            });
        });
		
	});
	
	function verComunicado(id){
		$("#overlay").load("../visor/comunicado/"+id+".htm", function(){
			$('#overlay').fadeIn('fast',function(){
				   //$('#box').animate({'top':'30%'},100);
				});
		});
		return false;
	}
	
</script>

</head>
<body>
<!------------------------------------------------------------------------------------>
<div id="container">  
	
    <header id="header">
    	<img src="../images-visor/logo-header.jpg" alt="" />
        <figure id="logo-responsive"></figure>
    </header>
	
	<div id="banner-container">
		<div id="allinone_carousel_sweet">
		            <div class="myloader"></div>
		            <!-- CONTENT -->
		            <ul class="allinone_carousel_list">
		           </ul>
        </div>  
		        
        <div id="container-nav-min-right">
        	<div id="container-min-left"></div>
                <ul id="container-min-center">
                </ul>
            <div id="container-min-right"></div>
        </div>
        
        <div id="container-nav-min-left">
        	<div id="container-min-left"></div>
                <ul id="container-min-center">
                </ul>
            <div id="container-min-right"></div>
        </div>
        
        
	</div>
	

	<!-------------------------------------------------------------------------------------> 	        

	<!--end Banner-->

	<div id="footer" class="clearBoth"></div>
	
    <section id="body">
		<article id="block-tasas-tarifas">
        	<div id="head-title-tasas-tarifas">
            	<h1>Tasas y Tarifas</h1>
            </div>
            <article id="shadow-tasas-tarifas"></article>
            <ul id="block-body-personas">
                <li id="border-left">
                	<div id="head-title-personas">
                        <h1>Persona Natural</h1>
                    </div>
                </li>
                <li id="border-right">
                	<div id="head-title-personas">
                        <h1>Persona Jurídica</h1>
                    </div>
                </li>
            </ul>
            <article id="shadow-body-tasas-tarifas"></article>
        </article>
        
        <article id="block-sub">
            <a href="../visor/notarias/${codigoOficina}.htm" class="button" toptions="title = Lista de Notarías, effect = clip, layout = quicklook, shaded = 1">
            	<div id="block-body-notarias">
            		<h1>Lista de Notarios</h1>
                </div>
			</a>
            <div id="block-body-comunicados">
                <div id="head-title-comunicados">
                    <h1>Comunicados</h1>
                </div>
                <ul class="list-comunicados">
	                <marquee direction="up" scrolldelay="5000" Behavior ="alternate" scrollamount="50" class="marquee" id="marqueeComunicados">
<!-- 	                    <li><a href="#" id="activator"><p>Ley de Atención Preferencial.</p><span>17/07/2013</span></a></li> -->
<!-- 	                    <li><a href="#" class="clear-right"><p>Comisión Gestión por Volumen de Monedas</p><span>17/07/2013</span></a></li> -->
<!-- 	                    <li><a href="#"><p>Novedades CTS.</p><span>17/07/2013</span></a></li> -->
<!-- 	                    <li><a href="#" class="clear-right"><p>Impacto de pago mínimo - Tarjetas de Crédito.</p><span>17/07/2013</span></a></li> -->
<!-- 	                    <li><a href="#"><p>Tarifario Continental Bolsa</p><span>17/07/2013</span></a></li> -->
<!-- 	                    <li><a href="#" class="clear-right"><p>Comisión Gestión por Volumen de Monedas</p><span>17/07/2013</span></a></li> -->
<!-- 	                    <li><a href="#"><p>Comisión Gestión por Volumen de Monedas</p><span>17/07/2013</span></a></li> -->
<!-- 	                    <li><a href="#" class="clear-right"><p>Ley de Atención Preferencial.</p><span>17/07/2013</span></a></li> -->
<!-- 	                    <li><a href="#"><p>Comisión Gestión por Volumen de Monedas</p><span>17/07/2013</span></a></li> -->
<!-- 	                    <li><a href="#" class="clear-right"><p>Novedades CTS.</p><span>17/07/2013</span></a></li> -->
<!-- 	                    <li><a href="#"><p>Ley de Atención Preferencial.</p><span>17/07/2013</span></a></li> -->
<!-- 	                    <li><a href="#" class="clear-right"><p>Comisión Gestión por Volumen de Monedas</p><span>17/07/2013</span></a></li> -->
<!-- 	                    <li><a href="#"><p>Novedades CTS.</p><span>17/07/2013</span></a></li> -->
	                    
	                    
                    </marquee>
                </ul>
			</div> 
<!--             <div id="block-body-canales"> -->
<!--                 <div id="head-title-canales"> -->
<!--                     <h1>Canales mas cercanos</h1> -->
<!--                 </div> -->
                
<!-- 			</div> -->
            <article id="shadow-body-notarias"></article>
            <article id="shadow-body-comunicados-max"></article>
<!--             <article id="shadow-body-comunicados"></article> -->
<!--             <article id="shadow-body-canales"></article> -->
        </article>
    </section>
    <!-- POPUP DETAILS -->
    <div id="details-notarias" class="portBox">
    </div>
        <div class="overlay" id="overlay">
		        
    	</div>
	<!-- FIN - POPUP DETAILS -->
</div>




</body>

</html>