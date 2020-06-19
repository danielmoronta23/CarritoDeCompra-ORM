package practica2.encapsulacion;

import javax.sql.rowset.CachedRowSet;
import java.awt.image.AreaAveragingScaleFilter;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class Controladora {
    private static Controladora controladora = null;
    private List<Usuario> misUsuarios;
    private List<CarroCompra> miCarroComprea;
    private List<Producto> miProducto;
    private List<VentasProductos> misVentasProducto;
    /**
     *Implementando el patron Singleton
     */
    public Controladora() {
        misUsuarios = new ArrayList<>();
        miCarroComprea= new ArrayList<>();
        miProducto= new ArrayList<>();
        misVentasProducto = new ArrayList<>();
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

    public static void setControladora(Controladora controladora) {
        Controladora.controladora = controladora;
    }

    public List<Usuario> getMisUsuarios() {
        return misUsuarios;
    }

    public void setMisUsuarios(List<Usuario> misUsuarios) {
        this.misUsuarios = misUsuarios;
    }

    public List<CarroCompra> getMiCarroComprea() {
        return miCarroComprea;
    }

    public void setMiCarroComprea(List<CarroCompra> miCarroComprea) {
        this.miCarroComprea = miCarroComprea;
    }

    public List<Producto> getMiProducto() {
        return miProducto;
    }

    public void setMiProducto(List<Producto> miProducto) {
        this.miProducto = miProducto;
    }

    public List<VentasProductos> getMisVentasProducto() {
        return misVentasProducto;
    }

    public void setMisVentasProducto(List<VentasProductos> misVentasProducto) {
        this.misVentasProducto = misVentasProducto;
    }

    public void crearProducto(Producto producto){
        miProducto.add(producto);
    }
    public boolean actulizarProducto(String ID, String nombre, BigDecimal precio){
        boolean actualizar = false;
        Producto pro = buscarProducto(ID);
       if(pro!=null){
            pro.setId(ID);
            pro.setNombre(nombre);
            pro.setPrecio(precio);
            actualizar = true;
        }
       return actualizar;
    }
    public void borrarProducto(String ID){
        Producto aux = buscarProducto(ID);
        if(aux!=null){
            miProducto.remove(aux);
        }
    }
    public Producto buscarProducto(String ID){
        Producto pro = null;
        for (Producto aux : miProducto) {
            if (aux.getId()==ID){
                pro = aux;
            }
        }
        return pro;
    }
    public Usuario autheticarUsuario(String usuario, String password){
        //simulando la busqueda en la base de datos.
        return new Usuario(usuario, "Usuario "+usuario, password);
    }

}
