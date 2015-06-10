package pe.grupobbva.muro.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pe.grupobbva.muro.entity.AficheOficina;
import pe.grupobbva.muro.entity.NotariaContrato;
import pe.grupobbva.muro.entity.Oficina;
import pe.grupobbva.muro.service.ComunicadoManager;
import pe.grupobbva.muro.service.ContratoManager;
import pe.grupobbva.muro.service.NotariaManager;
import pe.grupobbva.muro.service.OficinaManager;
import pe.grupobbva.muro.service.VariableManager;
import pe.grupobbva.muro.util.Constants;

@Controller
public class VisorController {
	
	private static final Logger logger = Logger.getLogger(AficheController.class);
	
	@Autowired
	private OficinaManager oficinaManager;
	
	@Autowired
	private ContratoManager contratoManager;
	
	@Autowired
	private NotariaManager notariaManager;
	
	@Autowired
	private VariableManager variableManager;
	
	@Autowired
	private ComunicadoManager comunicadoManager;
	
	
	@RequestMapping(value = "/visor/{codigoOficina}.htm", method = RequestMethod.GET)
	public String visor(@PathVariable("codigoOficina") String codigoOficina, ModelMap map) throws Exception {
		
		map.addAttribute("codigoOficina", codigoOficina);
		
		logger.debug("Visor!");
		
		return "visor/visor";
	}
	
	@RequestMapping(value = "/visor_index/{codigoOficina}.htm", method = RequestMethod.GET)
	public String index(@PathVariable("codigoOficina") String codigoOficina,ModelMap map) throws Exception {
		
		map.addAttribute("tiempoActualizacion", variableManager.finById(Constants.TIEMPOACTUALIZACION).getValor());
		
		map.addAttribute("codigoOficina", codigoOficina);
		
		int p = 0;
		
		int contAfiche = 0;
		
		Oficina o = oficinaManager.getOficinasByCodigoVisor(codigoOficina);

		Oficina todas = oficinaManager.getOficinasVisor(0);
		
		List<AficheOficina> lBigAfiche = new ArrayList<AficheOficina>();
		
		for(AficheOficina i: todas.getAficheOficinas()){
				lBigAfiche.add(i);
				contAfiche++;
			if((8 - o.getAficheOficinas().size()) == contAfiche){
				break;
			}
			
		}
		
		if((o.getAficheOficinas().size()+ todas.getAficheOficinas().size()) < 9){
			
			map.addAttribute("oficinaVisor", o.getAficheOficinas());
			
			map.addAttribute("todas", todas.getAficheOficinas());
		}else{
			if(o.getAficheOficinas().size() == 8){
				map.addAttribute("oficinaVisor", o.getAficheOficinas());
				
				map.addAttribute("todas", null);
			}else{
				map.addAttribute("oficinaVisor", o.getAficheOficinas());
				
				map.addAttribute("todas", lBigAfiche);
			}
		}
		

		
		Oficina r = oficinaManager.getOficinasByCodigoVisor(codigoOficina);
		
		List<AficheOficina> h = new ArrayList<AficheOficina>();
		
		for(AficheOficina i: r.getAficheOficinas()){
			
			i.getAfiche().getMiniatura();
			
			if(i.getAfiche().getMiniatura()=='S'){
				h.add(i);
			}
			
		}
		
		Integer numberMiniatura = h.size();

		
		
		Oficina miniaturaTodas = oficinaManager.getOficinasVisor(0);
		
		List<AficheOficina> z = new ArrayList<AficheOficina>();
		
		List<AficheOficina> l = new ArrayList<AficheOficina>();
		
		for(AficheOficina i: miniaturaTodas.getAficheOficinas()){
			
			i.getAfiche().getMiniatura();
			
			if(i.getAfiche().getMiniatura()=='S'){
				z.add(i);
			}
			
		}
		
		Integer numberMiniaturaTodas = z.size();
		
		for(AficheOficina i: miniaturaTodas.getAficheOficinas()){
			
			i.getAfiche().getMiniatura();
			
			if(i.getAfiche().getMiniatura()=='S'){
				l.add(i);
				p++;
			}
			
			if((8 - numberMiniatura) == p){
				break;
			}
			
		}
		
		if((numberMiniatura + numberMiniaturaTodas) < 9){
			
			map.addAttribute("miniatura", h);
			
			map.addAttribute("miniaturaTodas", z);
			
		}else{
			if (numberMiniatura == 8) {
				
				map.addAttribute("miniatura", h);
				
				map.addAttribute("miniaturaTodas", null);
			}else{
				map.addAttribute("miniatura", h);
				
				map.addAttribute("miniaturaTodas", l);
			}
		}
		
		Oficina oficinaComunicado = oficinaManager.getOficinasByCodigoVisor(codigoOficina);
		
		map.addAttribute("comunicados", oficinaComunicado.getComunicadoOficinas());
		
		return "visor/index";
	}
	
	@RequestMapping(value = "/visor/big/{idAfiche}.htm", method = RequestMethod.GET)
	public String bigVisor(@PathVariable("idAfiche") Integer idAfiche, ModelMap map) throws Exception {
		
		map.addAttribute("idAfiche", idAfiche);
		
		logger.debug(" Big Visor!");
		
		return "visor/BigImagen";
	}
	
	@RequestMapping(value = "/visor/afiche/{codigoOficina}.htm", method = RequestMethod.GET)
	public String verAfiches(@PathVariable("codigoOficina") String codigoOficina, ModelMap map) throws Exception {
		
		int p = 0;
		Oficina o = oficinaManager.getOficinasByCodigoVisor(codigoOficina);
		
		Oficina r = oficinaManager.getOficinasByCodigoVisor(codigoOficina);
		
		List<AficheOficina> h = new ArrayList<AficheOficina>();
		
		
		
		for(AficheOficina i: r.getAficheOficinas()){
			
			i.getAfiche().getMiniatura();
			
			if(i.getAfiche().getMiniatura()=='S'){
				h.add(i);
			}
			
		}
		
		Integer numberMiniatura = h.size();

		Oficina todas = oficinaManager.getOficinasVisor(0);
		
		Oficina miniaturaTodas = oficinaManager.getOficinasVisor(0);
		
		List<AficheOficina> z = new ArrayList<AficheOficina>();
		
		List<AficheOficina> l = new ArrayList<AficheOficina>();
		
		for(AficheOficina i: miniaturaTodas.getAficheOficinas()){
			
			i.getAfiche().getMiniatura();
			
			if(i.getAfiche().getMiniatura()=='S'){
				z.add(i);
			}
			
		}
		
		Integer numberMiniaturaTodas = z.size();
		
		for(AficheOficina i: miniaturaTodas.getAficheOficinas()){
			
			i.getAfiche().getMiniatura();
			
			if(i.getAfiche().getMiniatura()=='S'){
				l.add(i);
				p++;
			}
			
			if((8 - numberMiniatura) == p){
				break;
			}
			
		}
		
		if((numberMiniatura + numberMiniaturaTodas) < 9){
			
			map.addAttribute("miniatura", h);
			
			map.addAttribute("miniaturaTodas", z);
			
		}else{
			if (numberMiniatura == 8) {
				
				map.addAttribute("miniatura", h);
				
				map.addAttribute("miniaturaTodas", null);
			}else{
				map.addAttribute("miniatura", h);
				
				map.addAttribute("miniaturaTodas", l);
			}
		}
		
		map.addAttribute("oficinaVisor", o);
		
		map.addAttribute("todas", todas);
		
//		map.addAttribute("miniatura", h);
//		
//		map.addAttribute("miniaturaTodas", z);
//		
//		map.addAttribute("numberMiniatura", numberMiniatura);
//		
//		map.addAttribute("numberMiniaturaTodas", numberMiniaturaTodas);
		
		o.getAficheOficinas();
		
		return "visor/afiches";
	}
	
	@RequestMapping(value = "/visor/notarias/{codigoOficina}.htm", method = RequestMethod.GET)
	public String notarias(@PathVariable("codigoOficina") String codigoOficina, ModelMap map) throws Exception {
		
		map.addAttribute("codigoOficina", codigoOficina);
		
		List<NotariaContrato> contratos = contratoManager.findByCodigoOficina(codigoOficina);
		
		map.addAttribute("listaContrato",contratos);

		/*String tempAnt = "";
		int contador = 0;
		String tempNotaria  = "";
		String tempNotariaAnt = "";
		int[] rowspan = new int[contratos.size()];
		int count = 0;
		for(int i=0;i<contratos.size();i++){
			//rowspan[i] = 0;
			String temp=contratos.get(i).getContrato().getDescripcion();
			
			if(temp.equals(tempAnt)){
				//contador++;
				tempNotaria += contratos.get(i).getNotaria().getNombre();
			}else{
				if(!tempAnt.equals("")){
//					rowspan[i-contador] = contador;
//					contador=1;
					count++;
					
					
					if(tempNotariaAnt.equals(tempNotaria)){
						contador++;
						tempNotariaAnt = tempNotaria;
					}else{
						if(!tempNotariaAnt.equals("")){
							
							rowspan[count-contador] = contador;
							contador=1;
							
						}else{
							contador++;
						}
						tempNotariaAnt = tempNotaria;
						tempNotaria = "";
					}
					//tempNotariaAnt = tempNotaria;
					
					
				}else{
					//contador++;
					tempNotaria += contratos.get(i).getNotaria().getNombre();
				}
			}
			
			if(contratos.size()-1 == i){
				rowspan[contratos.size()-1] = contador;
			}
			tempAnt = temp;
			
		}
		
		map.addAttribute("listRows",rowspan);*/
		
		return "visor/listaNotaria";
	}
	@RequestMapping(value = "/visor/notaria/{id}.htm", method = RequestMethod.GET)
	public String verNotaria(@PathVariable("id") String id, ModelMap map) throws Exception {
		
		map.addAttribute("notaria", notariaManager.finById(Integer.parseInt(id)));
		
		logger.debug("Visor!");
		
		return "visor/notaria";
	}
	
	@RequestMapping(value = "/visor/comunicado/{id}.htm", method = RequestMethod.GET)
	public String verComunicado(@PathVariable("id") String id, ModelMap map) throws Exception {
		
		map.addAttribute("comunicado", comunicadoManager.finById(Integer.parseInt(id)));
		
		logger.debug("Visor!");
		
		return "visor/comunicado";
	}
	
	@RequestMapping(value = "/visor/comunicadoImagen/{id}.htm", method = RequestMethod.GET)
	public String verComunicadoImagen(@PathVariable("id") String id, ModelMap map) throws Exception {
		
		map.addAttribute("idComunicado", Integer.parseInt(id));
		
		logger.debug("Visor!");
		
		return "visor/comunicadoImagen";
	}
	
}
