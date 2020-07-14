package carrito.encapsulacion;

import carrito.services.ServicioComentario;
import carrito.services.ServicioProducto;
import carrito.services.ServicioUsuario;
import carrito.services.ServicioVenta;

import javax.print.attribute.standard.PrinterMessageFromOperator;
import java.awt.desktop.SystemSleepEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Controladora {
    private static  Controladora controladora = null;
    private final ServicioProducto servicioProducto = new ServicioProducto();
    private final ServicioUsuario servicioUsuario = new ServicioUsuario();
    private final ServicioVenta servicioVenta = new ServicioVenta();
    private final ServicioComentario servicioComentario = new ServicioComentario();

    /**
     *Implementando el patron Singleton
     */
    public Controladora() {
        BigDecimal aux = new BigDecimal(10);

        //Usuario por defecto
        if(servicioProducto.buscar("admin")==null){
            servicioUsuario.crear(new Usuario("admin", "admin"));
        }
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
    public boolean agregarUsuario(Usuario usuario){

        return servicioUsuario.crear(usuario);
    }

    public  Usuario autenticarUsuario(String usuario, String password){
        Usuario auxUsuario = servicioUsuario.buscar(usuario);
        if(auxUsuario!=null){
            if (!auxUsuario.getPassword().equals(password)){
                System.out.println("No se pudo  autentificar el usuario de forma correcta! \n");
                return null;
            }
        }
        return auxUsuario;
    }

    //GRUD PRODUCTO
    public void crearProducto(String name, BigDecimal precio, String descripcion, List<Foto> fotoList){
        servicioProducto.crear(new Producto(name,precio, descripcion, fotoList));
    }
    /**
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

    public List<Producto> getMiProducto() {
        return servicioProducto.explorarTodo();
    }

    public Producto buscarProducto(String idProducto) {
        return servicioProducto.buscar(idProducto);
    }

    public void agregarVenta(VentasProductos auxVenta) {
        servicioVenta.crear(auxVenta);
    }

    public List<VentasProductos> getMisVentasProducto() {
        return servicioVenta.explorarTodo();
    }

    public boolean borrarProducto(String id) {
        Producto auxProducto = servicioProducto.buscar(id);
        /**
         * Esta no es la mejor opcion para borrar los comentarios de un producto.
         */
        List<Comentario> list = servicioComentario.getComentarios(auxProducto);
        for (Comentario co : list) {
            ///System.out.println("BORRANDO COMENTARIO\n");
            borrarComentario(co.getId());

        }
        return  servicioProducto.eliminar(auxProducto.getId());
    }

    public boolean actulizarProducto(String id, String name, BigDecimal precio) {
        Producto aux = servicioProducto.buscar(id);
        boolean estado = false;
        if(aux!=null){
            aux.setNombre(name);
            aux.setPrecio(precio);
             estado = servicioProducto.editar(aux);
        }
        return estado;
    }

    public boolean agregarComentario(String comentario, Date fecha, String idProducto){
        Producto auxProducto =  servicioProducto.buscar(idProducto);

        if(auxProducto!=null){
            return servicioComentario.crear(new Comentario(auxProducto, comentario, fecha));
        }
        return false;
    }
    public List<Comentario> getComentarios(String idProducto){
        return servicioComentario.getComentarios(servicioProducto.buscar(idProducto));
    }
    public boolean borrarComentario(String idComentario){

        return servicioComentario.eliminar(idComentario);
    }
    public Comentario buscarCOmentario(String idComentario){

        return servicioComentario.buscar(idComentario);
    }
    public List<Producto> paginacionProducto(int pagina) {
        return  servicioProducto.paginacionProducto(pagina);
    }
    public int cantProducto(){
        return servicioProducto.cantProducto();
    }

}