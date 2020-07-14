package carrito.controladores;

import io.javalin.Javalin;
import carrito.encapsulacion.*;
import carrito.util.ControladorBase;

import java.util.*;

import static io.javalin.apibuilder.ApiBuilder.path;

public class ControladorCarrito extends ControladorBase {

    public ControladorCarrito(Javalin app) {
        super(app);
    }
    private static int alerta = -1;
    @Override
    public void aplicarRutas() {
        app.routes(() -> {
            path("/", () ->{ //Reune un grupo de Endpoint para que funcionen en un mismo path o ruta
                app.get("/", ctx -> {
                    alerta = -1;
                    Map<String, Object> modelo = new HashMap<>();
                    List<Producto> auxProducto = Controladora.getInstance().getMiProducto();
                    modelo.put("titulo", "Lista de producto disponible:");
                    modelo.put("lista", auxProducto);
                    String a = "("+   (((CarroCompra) ctx.sessionAttribute("carrito")).getCont()) +")";
                    modelo.put("cantCarrito", a);
                    ctx.render("/publico/principal/principal.html",modelo);
                });
            });
        });

        app.get("/carrito", ctx -> {
            Map<String, Object> modelo = new HashMap<>();
            modelo.put("usuario", "Daniel P. Moronta");
            modelo.put("titulo", "Producto en el carrito");
            modelo.put("valor", alerta);
            ArrayList<IteamVenta> aux = (ArrayList<IteamVenta>) (((CarroCompra) ctx.sessionAttribute("carrito")).getListaProducto());
            String a = "("+   (((CarroCompra) ctx.sessionAttribute("carrito")).getCont()) +")";
            modelo.put("cantCarrito", a);
            modelo.put("lista", aux);
            modelo.put("total", (((CarroCompra) ctx.sessionAttribute("carrito")).calcularTotal()));
            ctx.render("/publico/vistaCarro/vista.html", modelo);

        });

        /**
         * Redirige venta de carrito
         */
        app.get("/administrado",ctx -> {
            Map<String, Object> modelo = new HashMap<>();
            modelo.put("usuario","Daniel Peña");
            List<Producto> auxProducto = Controladora.getInstance().getMiProducto();
            modelo.put("titulo", "Listado de producto");
            modelo.put("lista", auxProducto);
            ctx.render("/publico/Admin/inicio.html",modelo);

        });

        /**
         * Annadir al carrito
         * Laa ruta update no tiene que ver nada con actualizar, solo que se la puse como referencia
         * a un archivo html, y para no cambiarlo lo deje igual. Aunque pronto pienseo cambiarlo.
         * Mas aun, esa ruta solo sirve para agregar al carrito.
         */
        app.post("/update", ctx -> {
            /**
             * Escribir aqui lo que se va agregar al carrito;
             */
            String idProducto = ctx.formParam("custId");
            String cantProducto = ctx.formParam("cant");
            Producto producto =  Controladora.getInstance().buscarProducto(idProducto);
            if(producto!=null){
                int cantidad = Integer.parseInt(cantProducto);
                ((CarroCompra) ctx.sessionAttribute("carrito")).agregarProducto(producto, cantidad);
            }

            ctx.redirect("/");//redirigiendo a pagina principal y pasando render
        });

        app.post("/borrarProducroCarrio", ctx -> {
            String id = ctx.formParam("idBorrar") ;
            ((CarroCompra) ctx.sessionAttribute("carrito")).eliminarProducto(id);
            ctx.redirect("/carrito");
        });

        /**
         * Permite realizar la compra y colocar el nombre al cliente respetibamente
         */
        app.post("/agregarCliente", ctx -> {
            String nombreCliente = ctx.formParam("nombre");
            System.out.print("\n Realizando compra*************\n");
            Map<String, Object> modelo = new HashMap<>();
            modelo.put("usuario","DANIEL P. MORONTA");
            List<Producto> auxProducto = Controladora.getInstance().getMiProducto();
            modelo.put("titulo", "Listado de producto");
            try {
                if( ((CarroCompra) ctx.sessionAttribute("carrito")).getCont()>0) {
                    ArrayList<IteamVenta> aux = (ArrayList<IteamVenta>) (((CarroCompra) ctx.sessionAttribute("carrito")).getListaProducto());

                    Date fecha = new Date();
                    VentasProductos auxVenta = new VentasProductos(fecha, nombreCliente, aux);
                    Controladora.getInstance().agregarVenta(auxVenta);
                    ((CarroCompra) ctx.sessionAttribute("carrito")).limpiarCarrito();
                    ctx.redirect("/carrito");
                    alerta = 0;
                }else{
                    ctx.redirect("/carrito");
                    alerta = 1;
                }
            }catch (Exception e){

            }

        });

        app.post("/limpiarCarro", ctx -> {
            ((CarroCompra) ctx.sessionAttribute("carrito")).limpiarCarrito();
            ctx.redirect("/carrito");

        });

        /**
         * Creando session
         * Ante de acceder a cualquier ruta, se verifica si existe un "Carrrio" sesion
         */
        app.before(ctx -> {
            CarroCompra carrito = ctx.sessionAttribute("carrito");

            if (carrito == null) {
                carrito = new CarroCompra();
                ctx.sessionAttribute("carrito", carrito);
            }
        });
        app.get("/agregarcomentario", ctx -> {
            String idProducto = ctx.queryParam("Id");

            System.out.println("El tipo de datos recibido: "+ctx.header("Comtemt")+ "Matricula:"+ctx.queryParam("Id"));

            Map<String, Object> modelo = new HashMap<>();
            modelo.put("usuario", "Daniel P. Moronta");
            modelo.put("titulo", "Producto en el carrito");
            System.out.println("Se quiere ver los detalles de "+idProducto);

            Producto aux = null;
            aux = Controladora.getControladora().buscarProducto(idProducto);
            if(aux!=null){
                List<Foto> a= aux.getFotoList();
                modelo.put("id", idProducto);
                modelo.put("fotoList", a);
                System.out.println("LA CANTIDAD DE IAMGENES ES: "+a.size());
                List<Comentario> lista = Controladora.getInstance().getComentarios(idProducto);
                modelo.put("listaComentario", lista);
                ctx.render("/publico/principal/comentarioVistaPrincipal.html", modelo);
            }else{
                ctx.result("Error al buscar la info solicitada");
            }
        });
        app.post("/agregarComentario",ctx->{
            String id = ctx.formParam("id");
            Producto aux = null;
            aux = Controladora.getControladora().buscarProducto(id);
            Map<String, Object> modelo = new HashMap<>();
            Date fecha = new Date();
            String comentario = ctx.formParam("inputMessage");
            Controladora.getInstance().agregarComentario(comentario,fecha,id);
            List<Comentario> lista = Controladora.getInstance().getComentarios(id);
            List<Foto> a= aux.getFotoList();
            modelo.put("id", id);
            modelo.put("fotoList", a);
            modelo.put("listaComentario", lista);
            ctx.render("/publico/principal/comentarioVistaPrincipal.html", modelo);

        });
        app.get("/eliminarComentario",ctx -> {
            String idComentario = ctx.queryParam("Id");
            String idProducto = Controladora.getInstance().buscarCOmentario(idComentario).getProducto().getId();
            //boolean a = Controladora.getInstance().borrarComentario(idComentario);
           ctx.redirect("view"+"?Id="+idProducto);
        });

    }

}
