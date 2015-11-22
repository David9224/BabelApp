/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import com.csvreader.CsvReader;
import entity.Producto;
import entity.Usuarios;
import facade.ProductoFacade;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 * @Fecha 16/11/2015
 * @author David
 */
@ManagedBean
@ViewScoped
public class ProductosBean implements Serializable {

    private Producto producto;
    private Producto productoSelect;
    private ProductoFacade productoFacade;
    private List<Producto> listaProductos;
    private List<Producto> listaProductosFiltrados;
    private String crearHeader = "Crear Producto";
    private String crear = "Crear";
    private String cancelar = "Limpiar";
    private UploadedFile uploadedFile;

    public ProductosBean() {
        producto = new Producto();
        productoSelect = new Producto();
        listaProductosFiltrados = new ArrayList<>();
        productoFacade = new ProductoFacade();
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

    public void crearProducto() throws Exception {
        FacesMessage msg = null;
        try {
            if (productoFacade.buscarProducto(producto.getId_producto()) == null) {
                productoFacade.crearProducto(producto);
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Producto Creado");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } else {
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
        listaProductos = getListaProductos();
        crear = "Crear";
        crearHeader = "Crear Producto";
    }

    public void eliminarProducto(Producto p) {
        FacesMessage msg = null;
        try {
            productoFacade.borrarProducto(p.getId_producto());
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
                producto.setId_producto(Integer.parseInt(cvs.get(0)));
                producto.setId_categoria(Integer.parseInt(cvs.get(1)));
                producto.setNombre(cvs.get(2));
                producto.setPrecio(Float.parseFloat(cvs.get(3)));
                producto.setCantidadDisponible(Integer.parseInt(cvs.get(4)));
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
}
