/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import com.csvreader.CsvReader;
import entity.Imagen;
import entity.Producto;
import facade.CategoriaFacade;
import facade.ImagenFacade;
import facade.ProductoFacade;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import utility.ImagenesUtil;

/**
 * @Fecha 16/11/2015
 * @author David
 */
@ManagedBean
@ViewScoped
public class ProductosBean implements Serializable {

    private Producto producto;
    private Producto productoSelect;
    private final ProductoFacade productoFacade;
    private List<Producto> listaProductos;
    private List<Producto> listaProductosFiltrados;
    private String crearHeader = "Crear Producto";
    private String crear = "Crear";
    private String cancelar = "Limpiar";
    private UploadedFile uploadedFile;
    private final CategoriaFacade categoriaFacade;
    private final ImagenesUtil imagenesUtil;
    private final ImagenFacade imagenFacade;
    private Imagen selected;

    public ProductosBean() {
        selected = new Imagen();
        imagenFacade = new ImagenFacade();
        imagenesUtil = new ImagenesUtil();
        producto = new Producto();
        productoSelect = new Producto();
        listaProductosFiltrados = new ArrayList<>();
        productoFacade = new ProductoFacade();
        categoriaFacade = new CategoriaFacade();
    }

    public StreamedContent getImagen(Imagen i) throws Exception {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the HTML. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        } else {
            // So, browser is requesting the image. Return a real StreamedContent with the image bytes.
            int id = Integer.parseInt(context.getExternalContext().getRequestParameterMap().get("image_id"));
            Imagen imagen = imagenFacade.buscarImagen(i.getId());
            return new DefaultStreamedContent(new ByteArrayInputStream(imagen.getArchivo()));
        }
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Producto getProductoSelect() {
        return productoSelect;
    }

    public void setProductoSelect(Producto productoSelect) {
        this.productoSelect = productoSelect;
    }

    public String getCrearHeader() {
        return crearHeader;
    }

    public void setCrearHeader(String crearHeader) {
        this.crearHeader = crearHeader;
    }

    public String getCrear() {
        return crear;
    }

    public void setCrear(String crear) {
        this.crear = crear;
    }

    public String getCancelar() {
        return cancelar;
    }

    public void setCancelar(String cancelar) {
        this.cancelar = cancelar;
    }

    public List<Producto> getListaProductos() {
        try {
            listaProductos = productoFacade.getAllProductos();
            return listaProductos;
        } catch (Exception ex) {
            Logger.getLogger(ProductosBean.class.getName()).log(Level.SEVERE, null, ex);
            listaProductos = new ArrayList<>();
            return listaProductos;
        }
    }

    public void setListaProductos(List<Producto> listaProductos) {
        this.listaProductos = listaProductos;
    }

    public List<Producto> getListaProductosFiltrados() {
        return listaProductosFiltrados;
    }

    public void setListaProductosFiltrados(List<Producto> listaProductosFiltrados) {
        this.listaProductosFiltrados = listaProductosFiltrados;
    }

    public void crearProducto(Imagen i) throws Exception {
        FacesMessage msg = null;
        try {
            System.out.println(producto);
            if (productoFacade.buscarProducto(producto.getId_producto()) == null) {
                selected.setArchivo(i.getArchivo());
                selected.setExt(i.getExt());
                selected.setNombre(producto.getNombre());
                selected = imagenFacade.crearImagen(selected);
                producto.setImagen(selected);
                productoFacade.crearProducto(producto);
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Producto Creado");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } else {
                selected = imagenFacade.buscarImagen(producto.getImagen().getId());
                selected.setArchivo(i.getArchivo());
                selected.setExt(i.getExt());
                selected.setNombre(producto.getNombre());
                imagenFacade.updateImagen(selected);
                productoFacade.updateProducto(producto);
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Producto Editado");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.toString());
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo completar la operacion");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        producto = new Producto();
        selected = new Imagen();
        listaProductos = getListaProductos();
        crear = "Crear";
        crearHeader = "Crear Producto";
        cancelar = "Limpiar";
    }

    public void eliminarProducto(Producto p) {
        FacesMessage msg = null;
        try {
            System.out.println(p.toString());
            productoFacade.borrarProducto(p.getId_producto());
            imagenFacade.borrarImagen(p.getImagen().getId());
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Producto Eliminado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception ex) {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ex.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void onRowSelect() {
        producto = productoSelect;
        crear = "Editar";
        crearHeader = "Editar Producto";
        if (crear.equals("Crear")) {
            cancelar = "Limpiar";
        } else {
            cancelar = "Cancelar";
        }
    }

    public void onChange() {
        System.out.println(producto.getId_categoria().toString());
    }

    public void cancelar() {
        producto = new Producto();
        crear = "Crear";
        crearHeader = "Crear Producto";
        if (crear.equals("Crear")) {
            cancelar = "Limpiar";
        } else {
            cancelar = "Cancelar";
        }
    }

    public void handleFileUpload(FileUploadEvent event) {
        FacesMessage msg = null;
        try {
            CsvReader cvs;
            uploadedFile = event.getFile();
            cvs = new CsvReader(new InputStreamReader(uploadedFile.getInputstream()));
            cvs.readHeaders();
            while (cvs.readRecord()) {
                producto = new Producto();
                producto.setId_categoria(categoriaFacade.buscarCategoria(Integer.parseInt(cvs.get(1))));
                producto.setNombre(cvs.get(2));
                producto.setPrecio(Float.parseFloat(cvs.get(3)));
                if (productoFacade.buscarProducto(producto.getId_producto()) == null) {
                    productoFacade.crearProducto(producto);
                } else {
                    productoFacade.updateProducto(producto);
                }
            }
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Se han importado todos los datos");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception ex) {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ex.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void seleccionarImagen(FileUploadEvent event) {
        uploadedFile = event.getFile();
        FacesMessage message = new FacesMessage("Informacion", "imagen seleccionada:" + event.getFile().getFileName());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
