package pe.grupobbva.muro.service.impl;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.grupobbva.muro.dao.ContratoDAO;
import pe.grupobbva.muro.dao.Dao;
import pe.grupobbva.muro.dao.NotariaContratoDAO;
import pe.grupobbva.muro.dao.NotariaDAO;
import pe.grupobbva.muro.dao.PlazaDAO;
import pe.grupobbva.muro.entity.Contrato;
import pe.grupobbva.muro.entity.Notaria;
import pe.grupobbva.muro.entity.NotariaContrato;
import pe.grupobbva.muro.entity.NotariaContratoId;
import pe.grupobbva.muro.entity.Plaza;
import pe.grupobbva.muro.model.UploadedFile;
import pe.grupobbva.muro.service.NotariaManager;

@Service
public class NotariaManagerImpl extends ServiceImpl<Notaria> implements NotariaManager {

	private static final Logger logger = Logger.getLogger(NotariaManagerImpl.class);
	
	@Autowired
    private NotariaDAO  notariaDAO;
	
	@Autowired
    private NotariaContratoDAO   notariaContratoDAO;
	
	@Autowired
    private ContratoDAO   contratoDAO;
	
	@Autowired
    private PlazaDAO   plazaDAO;
	
	@Override
	protected Dao<Notaria> getDAO() {
		return notariaDAO;
	}
	
	public List<Notaria> buscarNotaria(String nombre){
		
		return notariaDAO.buscarNotaria(nombre);
	}

	
	@Override
	@Transactional
	public boolean eliminarNotaria(Notaria notaria) {
		try{
			notaria = notariaDAO.findById(notaria.getIdnotaria());
		
			notaria.setEstado("I".charAt(0));
			
			notariaDAO.update(notaria);
			
		}catch(Exception ex){
			logger.error("eliminarNotaria -->"+ex);
			return false;
		}
		return true;
	}
	
	@Override
	@Transactional
	public Notaria getNotaria(Notaria notaria) {
		try{
			notaria = notariaDAO.findById(notaria.getIdnotaria());
			notaria.getPlaza().setNotarias(null);
			notaria.getPlaza().setOficinas(null);
			notaria.getNotariaContratos().size();
			List<NotariaContrato> lista = notaria.getNotariaContratos();
			
			for(NotariaContrato nc:lista){
				logger.debug(nc.getContrato().getIdcontrato());
				logger.debug(nc.getContrato().getDescripcion());
				logger.debug(nc.getContrato().getGastos().replace("\"", "\\\""));
				logger.debug(nc.getNotaria().getNombre().replace("\"", "\\\""));
			}
			
		}catch(Exception ex){
			logger.error("eliminarNotaria -->"+ex);
			return notaria;
		}
		return notaria;
	}

	@Override
	@Transactional
	public boolean agregarNotariaContrato(Notaria notaria, List<Contrato> contratoList) {
		try{
			for(Contrato contrato:contratoList){
				if(contrato.getIdcontrato()==0){
					contrato.setIdcontrato(null);
					contrato.setEstado("A".charAt(0));
					contrato.setFechacreacion(new Date());
					contratoDAO.add(contrato);
				}else{
					Contrato contratoTemp = contratoDAO.findById(contrato.getIdcontrato());
					if(!contratoTemp.getDescripcion().trim().equals(contrato.getDescripcion().trim())||
							!contratoTemp.getGastos().trim().equals(contrato.getGastos().trim())){
						contrato.setIdcontrato(null);
						contrato.setEstado("A".charAt(0));
						contrato.setFechacreacion(new Date());
						contratoDAO.add(contrato);
					}
				}
				notariaContratoDAO.add(new NotariaContrato(new NotariaContratoId(notaria.getIdnotaria(), contrato.getIdcontrato())));
			}
			return true;
		}catch(Exception ex){
			logger.error("agregarNotariaContrato -->"+ex);
			return false;
		}
	}
	
	@Override
	@Transactional
	public boolean deleteNotariaContrato(List<NotariaContrato> contratoList) {
		try{
			
			for(NotariaContrato ncTemp: contratoList){
				notariaContratoDAO.deleteFisico(ncTemp);
			}
			return true;
		}catch(Exception ex){
			logger.error("agregarNotariaContrato -->"+ex);
			return false;
		}
	}

	@Override
	@Transactional
	public boolean subirArchivoNotarias(UploadedFile uploadedFile) {

		InputStream inputStream;
		String fileName = uploadedFile.getFile().getOriginalFilename();
		
		try {
			inputStream = uploadedFile.getFile().getInputStream();
			
			Workbook wb = null;
            
			if(fileName.toLowerCase().endsWith("xlsx")){
                wb = new XSSFWorkbook(inputStream);
            }else if(fileName.toLowerCase().endsWith("xls")){
                wb = new HSSFWorkbook(inputStream);
            }
			
			Notaria notariaAnterior = new Notaria();
			
			for (int k = 0; k < wb.getNumberOfSheets(); k++) {
				Sheet sheet = wb.getSheetAt(k);
				int rows = sheet.getPhysicalNumberOfRows();
 				for (int r = 1; r < rows; r++) {
					Row row = sheet.getRow(r);
					if (row == null) {
						continue;
					}
					
					String nombreNotaria = getCellValue(row,0);
					String nombrePlaza = getCellValue(row,5);
					
					if(notariaAnterior.getNombre()!=null&&
						notariaAnterior.getNombre().trim().toUpperCase().equals(nombreNotaria.trim().toUpperCase())&&
						notariaAnterior.getPlaza().getNombre().trim().toUpperCase().equals(nombrePlaza.trim().toUpperCase())){
						
						
						
					}else{
						Notaria notariaTemp = new Notaria();
						
						notariaTemp.setNombre(nombreNotaria);
						Plaza plazaTemp = plazaDAO.findByNombre(nombrePlaza);		
						if(plazaTemp==null){
							plazaTemp = new Plaza();
							plazaTemp.setNombre(getCellValue(row,5));
							plazaTemp.setEstado("A".charAt(0));
							plazaTemp.setFechacreacion(new Date());
							plazaDAO.add(plazaTemp);
						}	
						notariaTemp.setPlaza(plazaTemp);
						notariaTemp.setDireccion(getCellValue(row,1));
						notariaTemp.setTelefono1(getCellValue(row,2));
						notariaTemp.setPaginaweb(getCellValue(row,3));
						notariaTemp.setEmail(getCellValue(row,4));
						notariaTemp.setEstado("A".charAt(0));
						notariaTemp.setFechacreacion(new Date());
						notariaDAO.add(notariaTemp);
						notariaAnterior = notariaTemp;
					}
					
					String descripcion = getCellValue(row,6);
					String gastos = getCellValue(row,7);
					
					Contrato contrato = contratoDAO.findByNombreGastos(descripcion,gastos);
					if(contrato==null){
						contrato = new Contrato();
						contrato.setDescripcion(descripcion);
						contrato.setGastos(gastos);
						contrato.setEstado("A".charAt(0));
						contrato.setFechacreacion(new Date());
						
						contratoDAO.add(contrato);
					}
					try{
					notariaContratoDAO.add(new NotariaContrato(new NotariaContratoId(notariaAnterior.getIdnotaria(), contrato.getIdcontrato())));
					}catch(Exception e){
						logger.error("Error Subiendo notarias"+e);
					}
				}
			}
			return true;
		} catch (Exception e) { 
			logger.error("Error subiendo archivo " +e);
			e.printStackTrace();
			return false;
		}
	}
	
	public String getCellValue(Row row, int posicion){
		
		Cell cell = row.getCell(posicion);
		String value = null;

		switch (cell.getCellType()) {
			case Cell.CELL_TYPE_NUMERIC:
				value = "" + cell.getNumericCellValue();
				break;
			case Cell.CELL_TYPE_STRING:
				value = "" + cell.getStringCellValue();
				break;
			default:
				value="";
				break;
		}
		
		return value;
	}
	
}