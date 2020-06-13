package practica2.encapsulacion;

import javax.sql.rowset.CachedRowSet;
import java.awt.image.AreaAveragingScaleFilter;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;

public class Controladora {
    private static Controladora controladora = null;
    private ArrayList<Usuario> misUsuarios;
    private ArrayList<CarroCompra> miCarroComprea;
    private ArrayList<Producto> miProducto;
    private ArrayList<VentasProductos> misVentasProducto;
    /**
     *Implementando el patron Singleton
     */
    public Controladora() {
        misUsuarios = new ArrayList<>();
        miCarroComprea= new ArrayList<>();
        miProducto = new ArrayList<>();
        misVentasProducto = new ArrayList<>();
    }

    public static Controladora getInstance() {
        if (controladora == null) {
            controladora = new Controladora();
        }
        return controladora;
    }


    public ArrayList<Usuario> getMisUsuarios() {
        return misUsuarios;
    }

    public void setMisUsuarios(ArrayList<Usuario> misUsuarios) {
        this.misUsuarios = misUsuarios;
    }

    public ArrayList<CarroCompra> getMiCarroComprea() {
        return miCarroComprea;
    }

    public void setMiCarroComprea(ArrayList<CarroCompra> miCarroComprea) {
        this.miCarroComprea = miCarroComprea;
    }

    public ArrayList<Producto> getMiProducto() {
        return miProducto;
    }

    public void setMiProducto(ArrayList<Producto> miProducto) {
        this.miProducto = miProducto;
    }

    public ArrayList<VentasProductos> getMisVentasProducto() {
        return misVentasProducto;
    }

    public void setMisVentasProducto(ArrayList<VentasProductos> misVentasProducto) {
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

}
