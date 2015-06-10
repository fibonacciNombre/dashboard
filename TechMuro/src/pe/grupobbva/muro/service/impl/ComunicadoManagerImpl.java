package pe.grupobbva.muro.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import pe.grupobbva.muro.dao.ComunicadoDAO;
import pe.grupobbva.muro.dao.Dao;
import pe.grupobbva.muro.entity.Comunicado;
import pe.grupobbva.muro.service.ComunicadoManager;

@Service
public class ComunicadoManagerImpl extends ServiceImpl<Comunicado> implements ComunicadoManager{

	@Autowired
	private ComunicadoDAO comunicadoDAO;
	
	@Override
	protected Dao<Comunicado> getDAO() {
	
		return comunicadoDAO;
	}
	
	@Override
	@Transactional
	public byte[] byId(Integer id) throws Exception{
		
		String filename = comunicadoDAO.findById(id).getUrl().substring(13);

		BufferedImage image = ImageIO.read(new File("D:/comunicados"+filename));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(image, "jpg", baos);
		//byte[] res=baos.toByteArray();
		String encodedImage = Base64.encode(baos.toByteArray());
		byte[] t = Base64.decode(encodedImage);
		return t;
	}

}
