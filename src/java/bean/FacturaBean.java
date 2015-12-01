/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import entity.AccesosUsuarios;
import entity.Cliente;
import entity.Detalle;
import entity.Factura;
import entity.Imagen;
import entity.Producto;
import facade.ClienteFacade;
import facade.DetalleFacade;
import facade.FacturaFacade;
import facade.ImagenFacade;
import facade.ProductoFacade;
import facade.UsuariosFacade;
import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseId;
import org.primefaces.context.RequestContext;
import org.primefaces.event.DragDropEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 * @Fecha 16/11/2015
 * @author David
 */
@ManagedBean
@SessionScoped
public class FacturaBean implements Serializable {

    private Detalle detalleSelect;
    private DetalleFacade detalleFacade;
    private List<Detalle> listaDetalle;
    private List<Producto> listaProducto;
    private FacturaFacade facturaFacade;
    private ImagenFacade imagenFacade;
    private ProductoFacade productoFacade;
    private ClienteFacade clienteFacade;
    private UsuariosFacade usuariosFacade;
    private int mesa;
    private Cliente cliente;
    private Factura factura;
    private String nombre;

    /**
     *
     */
    public FacturaBean() {
        try {
            clienteFacade = new ClienteFacade();
            usuariosFacade = new UsuariosFacade();
            productoFacade = new ProductoFacade();
            imagenFacade = new ImagenFacade();
            detalleSelect = new Detalle();
            detalleFacade = new DetalleFacade();
            listaDetalle = new ArrayList<>();
            listaProducto = new ArrayList<>();
            facturaFacade = new FacturaFacade();
            cliente = new Cliente();
            factura = new Factura();
        } catch (Exception ex) {
            Logger.getLogger(FacturaBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getMesa() {
        return mesa;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public void setMesa(int mesa) {
        this.mesa = mesa;
    }

    public Detalle getDetalleSelect() {
        return detalleSelect;
    }

    public void setDetalleSelect(Detalle detalleSelect) {
        this.detalleSelect = detalleSelect;
    }

    public List<Detalle> getListaDetalle() {
        return listaDetalle;
    }

    public void setListaDetalle(List<Detalle> listaDetalle) {
        this.listaDetalle = listaDetalle;
    }

    public List<Producto> getListaProducto() {
        try {
            listaProducto = productoFacade.getAllProductos();
            return listaProducto;
        } catch (Exception ex) {
            return listaProducto;
        }
    }

    public void setListaProducto(List<Producto> listaProducto) {
        this.listaProducto = listaProducto;
    }

    public StreamedContent getImagen() throws Exception {
        FacesContext context = FacesContext.getCurrentInstance();

        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the HTML. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        } else {
            int id = Integer.parseInt(context.getExternalContext().getRequestParameterMap().get("image_id"));
            Imagen imagen = imagenFacade.buscarImagen(id);
            return new DefaultStreamedContent(new ByteArrayInputStream(imagen.getArchivo()), imagen.getExt(), imagen.getNombre());
            // So, browser is requesting the image. Return a real StreamedContent with the image bytes.
        }
    }

    public void onProductDrop(DragDropEvent ddEvent) {
        Producto producto = ((Producto) ddEvent.getData());
        Detalle detalle = new Detalle();
        detalle.setCantidad(1);
        detalle.setProducto(producto);
        if (!listaDetalle.contains(detalle)) {
            listaDetalle.add(detalle);
        } else {
            for (Detalle detalle1 : listaDetalle) {
                if (detalle1.equals(detalle)) {
                    detalle1.setCantidad(detalle1.getCantidad() + detalle.getCantidad());
                }
            }
        }
    }

    public float total() {
        float total = 0;
        for (Detalle detalle : listaDetalle) {
            total = total + detalle.getPrecio();
        }
        return total;
    }

    public void eliminar(Detalle d) {
        try {
            Factura f = facturaFacade.buscarFactura(factura.getNum_factura());
            if (f == null) {
                listaDetalle.remove(d);
            } else {
                detalleFacade.borrarDetalle(d.getNum_detalle(), d.getFactura().getNum_factura());
                getDetalleFactura(f);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(FacturaBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void crear() {
        try {
            System.out.println("crear");
            if (facturaFacade.buscarFactura(factura.getNum_factura()) == null) {
                AccesosUsuarios a = (AccesosUsuarios) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
                factura.setUsuario(usuariosFacade.buscarUsuario(a.getAcCedu().getCedula()));
                factura.setPendiente(false);
                factura.setFecha(new Date(System.currentTimeMillis()));
                factura.setMesa(1);
                if ((factura.getNombre() == null || factura.getNombre().trim().equals("")) && factura.getCedula() == 0) {
                    factura = facturaFacade.crearFactura(factura);
                } else {
                    factura = facturaFacade.crearFacturaC(factura);
                }
                for (Detalle detalle : listaDetalle) {
                    detalle.setFactura(factura);
                    detalleFacade.crearDetalle(detalle);
                }
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Factura con numero :" + factura.getNum_factura() + " ha sido creada");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } else {
                factura.setPendiente(false);
                factura.setFecha(new Date(System.currentTimeMillis()));
                if ((factura.getNombre() == null || factura.getNombre().trim().equals("")) && factura.getCedula() == 0) {
                    facturaFacade.updateFactura(factura);
                } else {
                    facturaFacade.updateFacturaC(factura);
                }
                for (Detalle detalle : listaDetalle) {
                    detalle.setFactura(factura);
                    if (detalleFacade.buscarDetalle(detalle.getNum_detalle(), detalle.getProducto().getId_producto()) == null) {
                        detalleFacade.crearDetalle(detalle);
                    } else {
                        detalleFacade.updateDetalle(detalle);
                    }
                }
            }
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Factura con numero :" + factura.getNum_factura() + " ha sido creada");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al crear la factura: " + ex.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            init();
        }
    }

    public void guardar() {
        try {
            System.out.println("guardar");
            if (facturaFacade.buscarFactura(factura.getNum_factura()) == null) {
                AccesosUsuarios a = (AccesosUsuarios) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
                factura.setUsuario(usuariosFacade.buscarUsuario(a.getAcCedu().getCedula()));
                factura.setPendiente(true);
                factura.setMesa(1);
                if ((factura.getNombre() == null || factura.getNombre().trim().equals("")) && factura.getCedula() == 0) {
                    factura = facturaFacade.crearFactura(factura);
                } else {
                    factura = facturaFacade.crearFacturaC(factura);
                }
                for (Detalle detalle : listaDetalle) {
                    detalle.setFactura(factura);
                    detalleFacade.crearDetalle(detalle);
                }
                RequestContext rc = RequestContext.getCurrentInstance();
                rc.execute("PF('dlg2').hide()");
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Factura con numero :" + factura.getNum_factura() + " ha sido guardada");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } else {
                factura.setPendiente(true);
                factura.setFecha(new Date(System.currentTimeMillis()));
                factura.setMesa(1);
                if ((factura.getNombre() == null || factura.getNombre().trim().equals("")) && factura.getCedula() == 0) {
                    facturaFacade.updateFactura(factura);
                } else {
                    facturaFacade.updateFacturaC(factura);
                }
                for (Detalle detalle : listaDetalle) {
                    detalle.setFactura(factura);
                    if (detalleFacade.buscarDetalle(detalle.getNum_detalle(), detalle.getProducto().getId_producto()) == null) {
                        detalleFacade.crearDetalle(detalle);
                    } else {
                        detalleFacade.updateDetalle(detalle);
                    }
                }
                RequestContext rc = RequestContext.getCurrentInstance();
                rc.execute("PF('dlg2').hide()");
            }
            init();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al guardar la factura: " + ex.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void init() {
        clienteFacade = new ClienteFacade();
        usuariosFacade = new UsuariosFacade();
        productoFacade = new ProductoFacade();
        imagenFacade = new ImagenFacade();
        detalleSelect = new Detalle();
        detalleFacade = new DetalleFacade();
        listaDetalle = new ArrayList<>();
        listaProducto = new ArrayList<>();
        facturaFacade = new FacturaFacade();
        cliente = new Cliente();
        factura = new Factura();
    }

    public void getDetalleFactura(Factura f) {
        try {
            listaDetalle = detalleFacade.getAllDetallesFactura(f);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void productosFiltered() {
        try {
            List<Producto> listaf = new ArrayList<>();
            for (Producto producto : getListaProducto()) {
                if (producto.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                    listaf.add(producto);
                }
            }
            if (!listaf.isEmpty()) {
                listaProducto = listaf;
            } else {
                listaProducto = getListaProducto();
            }
            System.out.println(listaProducto.size());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<Factura> getAllfacturasPendientes() {
        try {
            return facturaFacade.getAllFacturasPendientes();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return new ArrayList<>();
        }
    }

    public void onRowSelect() {
        getDetalleFactura(factura);
    }

}
