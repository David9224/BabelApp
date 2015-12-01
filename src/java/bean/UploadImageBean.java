/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import entity.Imagen;
import facade.ImagenFacade;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.Part;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import utility.ImagenesUtil;

@ManagedBean
@RequestScoped// Can be @ViewScoped, but caution should be taken with byte[] property. You don't want to save it in session.
public class UploadImageBean implements Serializable {

    private Part file;
    private byte[] content;
    private ImagenFacade imagenFacade;
    private ImagenesUtil imagenesUtil;
    private String fileContent;

    public UploadImageBean() {
        imagenesUtil = new ImagenesUtil();
        imagenFacade = new ImagenFacade();
    }

    public Imagen upload() throws Exception {
        Imagen i = new Imagen();
        try {
            i.setArchivo(imagenesUtil.convertirImagenBytes(file.getInputStream()));
            i.setExt(file.getContentType());
            i.setNombre(Integer.toString(file.hashCode()));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        file=null;
        return i;
    }

    public String getFileContent() {
        return fileContent;
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }

    public void validateFile(FacesContext ctx,
            UIComponent comp,
            Object value) {
        List<FacesMessage> msgs = new ArrayList<FacesMessage>();
        Part file = (Part) value;
        if (file.getSize() > 1024) {
            msgs.add(new FacesMessage("file too big"));
        }
        if (!"^image/.*$".equals(file.getContentType())) {
            msgs.add(new FacesMessage("not a image file"));
        }
        if (!msgs.isEmpty()) {
            throw new ValidatorException(msgs);
        }
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public void read() throws IOException {
        try {
            content = imagenesUtil.convertirImagenBytes(file.getInputStream());
            System.out.println(content.length);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public byte[] getContent() {
        return content;
    }

    public StreamedContent getImagen(Imagen i) throws Exception {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the HTML. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        } else {
            // So, browser is requesting the image. Return a real StreamedContent with the image bytes.
            int id = Integer.parseInt(context.getExternalContext().getRequestParameterMap().get("image_id"));
            Imagen imagen = imagenFacade.buscarImagen(id);
            return new DefaultStreamedContent(new ByteArrayInputStream(imagen.getArchivo()));
        }
    }

}
