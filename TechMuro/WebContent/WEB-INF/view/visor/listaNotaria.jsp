<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    	<div class="container-float">
            <article class="container-body-popup">
                <h2>Estimado Cliente</h2>
                <p>A continuación ponemos a su disposición la relación de Notarios, la misma que se publica de acuerdo a lo establecido por la Resolución SBS N° 8181-2012 <sup style="font-size: 10px">1</sup></p>
                <table cellpadding="0" cellspacing="0" class="table-notarias">
                    <tbody>
                    <tr>
                        <td class="header-grid-popup">Contrato</td>
                        <td class="header-grid-popup">Gastos Notariales</td>
                        <td class="header-grid-popup">Notaría</td>
                    </tr>
                    
                    <c:set var="notariaAnt" value="false" scope="page"></c:set>
                    <c:forEach items="${listaContrato}" var="listaContratos" varStatus="i" >
	            		
	            		<c:choose>
	            			<c:when test="${notariaAnt eq listaContratos.contrato.descripcion }">
	            				<p><a href="#" onclick="return verNotaria('${listaContratos.notaria.idnotaria}');">${listaContratos.notaria.nombre }</a></p>
	            			</c:when>
	            			
	            			<c:when test="${notariaAnt eq 'false'}">
	            				<tr>
			                        <td>${listaContratos.contrato.descripcion }</td>
			                        <td>${listaContratos.contrato.gastos }</td>
			                        <td>
			                        	<p><a href="#" onclick="return verNotaria('${listaContratos.notaria.idnotaria}');">${listaContratos.notaria.nombre }</a></p>
	            			</c:when>
	            			<c:otherwise>
	            					</td>
	            				</tr>
	            				<tr>
			                        <td>${listaContratos.contrato.descripcion }</td>
			                        <td>${listaContratos.contrato.gastos }</td>
			                        <td>
			                        	<p><a href="#" onclick="return verNotaria('${listaContratos.notaria.idnotaria}');">${listaContratos.notaria.nombre }</a></p>
	            			</c:otherwise>
	            		</c:choose>
	            		<%-- <tr>
	                        <td>${listaContratos.contrato.descripcion }</td>
	                        <td>${listaContratos.contrato.gastos }</td> --%>
	                        
	                        <%-- <c:if test="${listRows[i.count-1] > 0 }">
	                        	<td rowspan="${listRows[i.count-1]}">	
		                        	<c:forEach items="${contrato.notariaContratos }" var="notarias">
		                        		<p><a href="#" onclick="return verNotaria('${notarias.notaria.idnotaria}');">${notarias.notaria.nombre }</a></p>
		                        	</c:forEach>
	                        	</td>
	                        </c:if> --%>
	                    <c:set var="notariaAnt" value="${listaContratos.contrato.descripcion }" scope="page"></c:set>
					</c:forEach>
					</tbody>
                </table>
                <p><sup style="font-size: 10px">1</sup> La información puede estar sujeta a ajustes previstos por cada Notario. </p>
				<p>No incluye gastos registrales, los cuales son establecidos por la Superintendencia Nacional de los Registros Públicos.
</p>
            </article>
		</div>