package practica2.encapsulacion;

import practica2.services.ServicioProducto;
import practica2.services.ServicioVenta;
import practica2.services.servicioUsuario;

public class GeneradorID {
    private String cadena ="";
    private boolean estado = false;

    public String generarID(String inicia, Usuario usuario){

        while(estado!=true){
            cadena="";
            cadena+=inicia;
            cadena+= (long) (Math.random() * Long.MAX_VALUE);
            if(servicioUsuario.buscarUsuario(cadena)==null){
                estado=true;
            }
        }
        return cadena;
    }
    /**
    public void generarID(String inicia, VentasProductos ventasProductos);
     **/
    public String generarID(String inicia, Producto producto){

        while(estado!=true){
            cadena="";
            cadena+=inicia;
            cadena+= (long) (Math.random() * Long.MAX_VALUE);
            if(ServicioProducto.buscaProudcto(cadena)==null){
                estado=true;
            }
        }
        return cadena;
    }
    public String generarID(String inicia, VentasProductos producto){

        while(estado!=true){
            cadena="";
            cadena+=inicia;
            cadena+= (long) (Math.random() * Long.MAX_VALUE);
            if(ServicioVenta.buscarVentaByID(cadena)==null){
                estado=true;
            }
        }
        return cadena;
    }


}
