/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imprimir;

import entity.Detalle;
import entity.Empresa;
import entity.Factura;
import facade.DetalleFacade;
import facade.EmpresaFacade;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author David
 */
public class Imprimir {

    private Long iva;

    public void imprimir(Factura factura) throws Exception {
        EmpresaFacade empresaFacade = new EmpresaFacade();
        Empresa empresa = empresaFacade.getEmpresa();

        Date date = new Date();
        SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat hora = new SimpleDateFormat("hh:mm:ss aa");
        Ticket ticket = new Ticket();
        ticket.AddCabecera(empresa.getNombreEmpresa());
        ticket.AddCabecera(ticket.DarEspacio());
        ticket.AddCabecera("EXPEDIDO EN: " + empresa.getCiudad());
        ticket.AddCabecera(ticket.DarEspacio());
        ticket.AddCabecera(empresa.getDireccion());
        ticket.AddCabecera(ticket.DarEspacio());
        ticket.AddCabecera(ticket.DibujarLinea(29));
        ticket.AddCabecera(ticket.DarEspacio());
        ticket.AddCabecera(empresa.getPaginaWeb());
        ticket.AddCabecera(ticket.DarEspacio());
//        ticket.AddCabecera("RFC: CSI-020226-MV4");
//        ticket.AddCabecera(ticket.DarEspacio());
        ticket.AddSubCabecera(ticket.DarEspacio());
        ticket.AddSubCabecera("FACTURA NRO. " + factura.getNum_factura());
        ticket.AddSubCabecera(ticket.DarEspacio());
        ticket.AddSubCabecera("LO ATENDIO: ...");
        ticket.AddSubCabecera(ticket.DarEspacio());
        ticket.AddSubCabecera("" + fecha.format(date) + " " + hora.format(date));
        ticket.AddSubCabecera(ticket.DarEspacio());
        ticket.AddSubCabecera(ticket.DibujarLinea(29));
        ticket.AddSubCabecera(ticket.DarEspacio());
        ticket.AddSubCabecera("DETALLES FACTURA");
        ticket.AddSubCabecera(ticket.DarEspacio());
        ticket.AddSubCabecera(ticket.DibujarLinea(29));
        ticket.AddSubCabecera(ticket.DarEspacio());
        ticket.AddItem("CANTIDAD", "PRODUCTO", "TOTAL");
        ticket.AddItem("", "", ticket.DarEspacio());
        DetalleFacade detalleFacade = new DetalleFacade();
        List<Detalle> lstDetalle = detalleFacade.getAllDetallesFactura(factura);
        for (Detalle detalle : lstDetalle) {
            ticket.AddItem(detalle.getCantidad() + "", detalle.getProducto().getNombre(), detalle.getPrecio() + "");
            ticket.AddItem("", "", ticket.DarEspacio());
        }
        //DETALLE DE LA FACTURA
        ticket.AddTotal("", ticket.DibujarLinea(29));
        ticket.AddTotal("", ticket.DarEspacio());

        ticket.AddTotal("SUBTOTAL", subTotal(factura.getTotal()) + "");
        ticket.AddTotal("", ticket.DarEspacio());
        ticket.AddTotal("IVA", iva + "");
        ticket.AddTotal("", ticket.DarEspacio());
        ticket.AddTotal("TOTAL", factura.getTotal() + "");
        ticket.AddTotal("", ticket.DarEspacio());
        ticket.AddTotal("", ticket.DarEspacio());
        ticket.AddTotal("RECIBIDO", factura.getTotalRecibido() + "");
        ticket.AddTotal("", ticket.DarEspacio());
        ticket.AddTotal("CAMBIO", "" + (factura.getTotalRecibido() - factura.getTotal()));
        ticket.AddTotal("", ticket.DarEspacio());
        ticket.AddTotal("", ticket.DarEspacio());
        ticket.AddPieLinea(ticket.DibujarLinea(29));
        ticket.AddPieLinea(ticket.DarEspacio());
//        ticket.AddPieLinea("EL xxx ES NUESTRA PASION...");
//        ticket.AddPieLinea(ticket.DarEspacio());
//        ticket.AddPieLinea("VIVE LA EXPERIENCIA EN xxx");
//        ticket.AddPieLinea(ticket.DarEspacio());
        ticket.AddPieLinea("Gracias por su visita");
        ticket.AddPieLinea(ticket.DarEspacio());
        ticket.ImprimirDocumento();
    }

    public Long subTotal(Long total) {
        Long subtotal;
        iva = (total * 16) / 100;
        subtotal = total - iva;
        return subtotal;
    }
}
