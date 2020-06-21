package practica2.encapsulacion;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class Controladora {
    private static  Controladora controladora = null;
    private List<Usuario> misUsuarios = new ArrayList<>();
    private List<CarroCompra> miCarroComprea = new ArrayList<>();
    private List<Producto> miProducto = new ArrayList<>();
    private List<VentasProductos> misVentasProducto  = new ArrayList<>();
    private int contID = 0;

    /**
     *Implementando el patron Singleton
     */
    public Controladora() {
        BigDecimal aux = new BigDecimal(10);
        misUsuarios.add(new Usuario("001","admin","admin"));
        miProducto.add(new Producto("01","Pizza",aux));
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

    public void crearProducto(String name, BigDecimal precio){
        String id = "P" + contID;
        miProducto.add(new Producto(id,name,precio));
        contID++;
    }
    public boolean actulizarProducto(String ID, String nombre, BigDecimal precio){
        boolean actualizar = false;
        Producto pro = buscarProducto(ID);

        if(pro!=null){
            miProducto.remove(pro);
            miProducto.add(new Producto(ID, nombre, precio));
            actualizar = true;
        }
       return actualizar;
    }
    public boolean borrarProducto(String ID){

        Producto aux = buscarProducto(ID);
        if(aux!=null){
            miProducto.remove(aux);
            return true;
        }
        return false;
    }
    public Producto buscarProducto(String ID){
        Producto pro = null;
        for (Producto aux : miProducto) {
            if (aux.getId().equals(ID)){
                pro = aux;
            }
        }
        return pro;
    }
    public Usuario autheticarUsuario(String usuario, String password){
        //simulando la busqueda en la base de datos
        return new Usuario(usuario, "Usuario "+usuario, password);
    }
    public void agregarUsuario(Usuario usuario){
        misUsuarios.add(usuario);
    }

    public Usuario buscarUsuario(String name, String password){
        Usuario auxUsuario = null;
        for (Usuario a:misUsuarios) {
            if(a.getNombre().equals(name)&&a.getPassword().equals(password)){
                auxUsuario = a;
            }
        }
        return  auxUsuario;

}
    public void agregarVenta(VentasProductos venta){

        misVentasProducto.add(venta);
    }

}