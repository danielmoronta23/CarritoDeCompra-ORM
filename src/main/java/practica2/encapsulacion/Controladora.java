package practica2.encapsulacion;

import org.thymeleaf.standard.processor.StandardAltTitleTagProcessor;
import practica2.services.ServicioProducto;
import practica2.services.ServicioVenta;
import practica2.services.servicioUsuario;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class Controladora {
    private static  Controladora controladora = null;

    /**
     *Implementando el patron Singleton
     */
    public Controladora() {
        BigDecimal aux = new BigDecimal(10);

        if(servicioUsuario.buscarUsuario("admin")==null) {
            if (agregarUsuario(new Usuario("admin", "admin")) == true) {
                System.out.println("Usuario por defecto agregado de forma correcta\n");
            }
        }


        //misUsuarios.add(new Usuario("001","admin","admin"));
        // miProducto.add(new Producto("01","Pizza",aux));
    }

    public static Controladora getInstance() {
        if (controladora == null) {
            controladora = new Controladora();
        }
        return controladora;
    }

    public static Controladora getControladora() {
        return controladora;
    }

    /**
     * Agreando controladores para la conexion de BD
     * @param usuario
     * @return
     */
    public static boolean agregarUsuario(Usuario usuario){
            GeneradorID a = new GeneradorID();
            usuario.setId( a.generarID("C", usuario));
        return servicioUsuario.crearUsuario(usuario);
    }
    public static Usuario autenticarUsuario(String usuario, String password){
        return servicioUsuario.autenticarUsuario(usuario, password);
    }
    //GRUD PRODUCTO
    public void crearProducto(String name, BigDecimal precio){
        Producto aux = null;
        GeneradorID a = new GeneradorID();
        ServicioProducto.crarProducto(new Producto(
                a.generarID("P",aux),
                name,
                precio));
    }
    public boolean borrarProducto(String ID){
        return ServicioProducto.borrarProducto(ID);
    }
    public boolean actulizarProducto(String ID, String nombre, BigDecimal precio){
        return ServicioProducto.actualizarProducto( new Producto(ID,nombre,precio));
    }

    //Funciones adicionales: lista producto y buscada de producto
    public List<Producto> getMiProducto() {
        return ServicioProducto.listaProducto();
    }
    public Producto buscarProducto(String ID){
        return ServicioProducto.buscaProudcto(ID);
    }

    //Funciones para venta
    public void agregarVenta(VentasProductos venta){
        GeneradorID a = new GeneradorID();
        venta.setId( a.generarID("V", venta));
        ServicioVenta.realizarVenta(venta);
    }
    public List<VentasProductos> getMisVentasProducto() {

        List<VentasProductos> aux = new ArrayList<>();
        aux = ServicioVenta.listaVenta();
        for (VentasProductos a: aux) {

            a.setListaProducto(ServicioVenta.listaProductoVenta(a.getId()));


        }
        return aux;
    }


    /**
     * Proyecto antiguo, se esta actualizando para que sea compatible con BD
     */

    private List<CarroCompra> miCarroComprea = new ArrayList<>();
    public List<CarroCompra> getMiCarroComprea() {
        return miCarroComprea;
    }
    public void setMiCarroComprea(List<CarroCompra> miCarroComprea) {
        this.miCarroComprea = miCarroComprea;
    }


    private int contID = 0;
}