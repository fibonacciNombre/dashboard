package pe.grupobbva.muro.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pe.grupobbva.muro.entity.VariablesGenerales;
import pe.grupobbva.muro.service.NotariaManager;
import pe.grupobbva.muro.service.PlazaManager;
import pe.grupobbva.muro.service.VariableManager;
import pe.grupobbva.muro.util.Constants;



@Controller
public class VariableController {
	
	private static final Logger logger = Logger.getLogger(VariableController.class);
	
	@Autowired
	private VariableManager variableManager;
	
	@Autowired
	private NotariaManager notariaManager;
	
	@Autowired
	private PlazaManager plazaManager;
	
	
	
	@RequestMapping(value = "/variables.htm", method = RequestMethod.GET)
	public String cargaVariables(ModelMap map, HttpServletRequest request) throws Exception 
	{
		map.addAttribute("ta", variableManager.finById(Constants.TIEMPOACTUALIZACION).getValor());
		
		map.addAttribute("tpu", variableManager.finById(Constants.TIEMPOPOPUP).getValor());
		
		map.addAttribute("da", variableManager.finById(Constants.DIRECCIONAFICHES).getValor());
		
		map.addAttribute("tdv", variableManager.finById(Constants.TIEMPOVIDEO).getValor());
		
		map.addAttribute("mpoa", variableManager.finById(Constants.PRODUCTOSALFABETICAMENTE).getValor());
		
		logger.debug("Variables --> ");
		
		return "variables/variables";
	}

	@RequestMapping(value = "/etiquetas.htm", method = RequestMethod.GET)
	public String cargaEtiquetas(ModelMap map, HttpServletRequest request) throws Exception 
	{
		
		return "variables/etiquetas";
	}
	
	@RequestMapping(value = "/actualizarVariables.htm", method = RequestMethod.POST)
	public @ResponseBody String buscaProducto(ModelMap map, HttpServletRequest request) throws Exception {
		
		logger.debug("datos --> " +request.getParameter("ta"));
		
		String ta = request.getParameter("ta");
		VariablesGenerales taTemp = variableManager.finById(Constants.TIEMPOACTUALIZACION);
		
		if(ta==null || ta==""){
			return "error";
		}else{
			
			taTemp.setValor(ta);
			
		}
		
		String tpu = request.getParameter("tpu");
		VariablesGenerales tpuTemp = variableManager.finById(Constants.TIEMPOPOPUP);
		
		if(tpu==null || tpu==""){
			return "error";
		}else{
			
			tpuTemp.setValor(tpu);
			
		}
		
		String da = request.getParameter("da");
		VariablesGenerales daTemp = variableManager.finById(Constants.DIRECCIONAFICHES);
		
		if(da==null || da ==""){
			return "error";
		}else{
			
			daTemp.setValor(da);
			
		}
		
		String tdv = request.getParameter("tdv");
		VariablesGenerales tdvTemp = variableManager.finById(Constants.TIEMPOVIDEO);
		
		if(tdv==null || tdv==""){
			return "error";
		}else{
			
			tdvTemp.setValor(tdv);
			
		}
		
		String mpoa = request.getParameter("mpoa");
		VariablesGenerales mpoaTemp = variableManager.finById(Constants.PRODUCTOSALFABETICAMENTE);
		
		if(mpoa==null || mpoa==""){
			mpoaTemp.setValor("no");
			
		}else{
			mpoaTemp.setValor("si");
			
		}
		variableManager.update(tdvTemp);
		variableManager.update(mpoaTemp);
		variableManager.update(daTemp);
		variableManager.update(tpuTemp);
		variableManager.update(taTemp);
		
		return "go";

	}
	
}
