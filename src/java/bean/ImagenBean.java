/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import entity.Imagen;
import facade.ImagenFacade;
import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import utility.ImagenesUtil;
import utility.JsfUtil;

@ManagedBean
@SessionScoped
public class ImagenBean implements Serializable {

    private Imagen selected;
    private StreamedContent streamedContent;
    private final ImagenesUtil imagenesUtil;
    private UploadedFile uploadedFile;
    private final ImagenFacade imagenFacade;

    public ImagenBean() {
        imagenesUtil = new ImagenesUtil();
        selected = new Imagen();
        imagenFacade = new ImagenFacade();
    }

    public StreamedContent getStreamedContent() throws Exception {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            return new DefaultStreamedContent();
        } else if (selected == null) {
            return new DefaultStreamedContent();
        } else {
            ByteArrayInputStream in = new ByteArrayInputStream(selected.getArchivo());
            return streamedContent = new DefaultStreamedContent(in, selected.getExt(), selected.getNombre());
        }
    }

    public void setStreamedContent(StreamedContent streamedContent) {
        this.streamedContent = streamedContent;
    }

    public List<Imagen> getItems() throws Exception {
        return imagenFacade.getAllImagenes();
    }

    public Imagen getSelected() {
        return selected;
    }

    public void setSelected(Imagen selected) {
        this.selected = selected;
    }

    public void crear() {
        try {
            imagenFacade.crearImagen(selected);
            JsfUtil.addSuccessMessage("Se ha creado la imagen");
            selected = new Imagen();
        } catch (Exception ex) {
            JsfUtil.addErrorMessage(ex, ex.getLocalizedMessage());
        }
    }

    public void editar() {
        try {
            imagenFacade.updateImagen(selected);
            JsfUtil.addSuccessMessage("Se ha editado la imagen");
            selected = new Imagen();
        } catch (Exception ex) {
            JsfUtil.addErrorMessage(ex, ex.getLocalizedMessage());
        }
    }

    public void delete() {
        try {
            imagenFacade.borrarImagen(selected.getId());
            JsfUtil.addSuccessMessage("Se ha eliminado el dato");
            selected = new Imagen();
        } catch (Exception ex) {
            JsfUtil.addErrorMessage(ex, ex.getLocalizedMessage());
        }
    }

    public void buscar(Integer id) {
        try {
            if (imagenFacade.buscarImagen(id) != null) {
                selected = imagenFacade.buscarImagen(id);
                JsfUtil.addSuccessMessage("Se ha encontrado la imagen");
                ByteArrayInputStream in = new ByteArrayInputStream(selected.getArchivo());
                streamedContent = new DefaultStreamedContent(in, selected.getExt(), selected.getNombre());
            } else {
                selected = new Imagen();
                JsfUtil.addErrorMessage("No se ha encontrado la imagen");
            }
        } catch (Exception ex) {
            JsfUtil.addErrorMessage(ex, ex.getLocalizedMessage());
        }
    }

}
