package pe.grupobbva.muro.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class VideoController {
	
	private static final Logger logger = Logger.getLogger(VideoController.class);
	
	public static final String BASE_PATH = "D:/afiches";

    @RequestMapping(value = "/video/{fileName}.htm" , method = RequestMethod.GET) 
    public @ResponseBody ResponseEntity<FileSystemResource> getFile(@PathVariable("fileName") String fileName) {
    	logger.debug("ARchivo Video 1---> "+ fileName);
        FileSystemResource resource = new FileSystemResource(new File(BASE_PATH, fileName));
        logger.debug("ARchivo Video 2---> "+ resource);
        ResponseEntity<FileSystemResource> responseEntity = new ResponseEntity<FileSystemResource>(resource, HttpStatus.OK);
        logger.debug("ARchivo Video 3---> "+ responseEntity);
        return responseEntity;
    }
    
    
    @RequestMapping(value = "/video/{id}/preview2.htm", method = RequestMethod.GET)
    @ResponseBody public void getPreview2(@PathVariable("id") String id, HttpServletResponse response) {
        try {
            
            File file = new File("D:/afiches"+id);
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setHeader("Content-Disposition", "attachment; filename="+file.getName().replace(" ", "_"));
            InputStream iStream = new FileInputStream(file);
            IOUtils.copy(iStream, response.getOutputStream());
            response.flushBuffer();
        } catch (Exception e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
        } 
    }

}
