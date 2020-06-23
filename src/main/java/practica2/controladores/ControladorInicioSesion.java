package practica2.controladores;

import io.javalin.Javalin;
import io.javalin.plugin.rendering.JavalinRenderer;
import io.javalin.plugin.rendering.template.JavalinThymeleaf;
import org.attoparser.ParsingXmlDeclarationMarkupUtil;
import practica2.encapsulacion.*;
import practica2.util.ControladorBase;

import java.math.BigDecimal;
import java.util.*;

import static io.javalin.apibuilder.ApiBuilder.*;

public class ControladorInicioSesion extends ControladorBase {
    //List<Usuario> users = Controladora.getControladora().getMisUsuarios();
    //private Stati List<ProductoCarrito>  micaro = new ArrayList<>();


    public ControladorInicioSesion(Javalin app) {
        super(app);
        registrandoPlantillas();
    }
    private void registrandoPlantillas(){
        //registrando los sistemas de plantilla.
        System.out.println("Registrando plantillas");
        JavalinRenderer.register(JavalinThymeleaf.INSTANCE, ".html");

    }

    @Override
    public void aplicarRutas() {

        /**
         * app.route es basicamente para indicar la mayoria de las rutas que javalin estara manejando
         */

        app.routes(() -> {
            path("/", () ->{ //Reune un grupo de Endpoint para que funcionen en un mismo path o ruta
                app.get("/", ctx -> {
                    //render es la funcion para renderizar (crear vista) de la pagina que queremos PROBEMOS!
                    //ctx.cookie("usuario", "nombre de usuario"); //permite crear una cookie
                    //ctx.render("/publico/inisioSesion/index.html"); //ctx es el contexto que usa Javalin para manejar los endpoint
                    Map<String, Object> modelo = new HashMap<>();
                    List<Producto> auxProducto = Controladora.getInstance().getMiProducto();
                    modelo.put("titulo", "Lista de producto disponible:");
                    modelo.put("lista", auxProducto);
                  //  modelo.put("lista",
                   //         (((CarroCompra) ctx.sessionAttribute("carrito")).getCont()));


                    String a = "("+   (((CarroCompra) ctx.sessionAttribute("carrito")).getCont()) +")";
                    modelo.put("cantCarrito", a);
                    ctx.render("/publico/principal/principal.html",modelo);
                });
                //Los mismo, puedes usar incluso en el doc HTML un <a ref="dasd" href="/Admin/principal.html> y javalin lo reconocera
            });
        });

        app.post("/autenticar", ctx -> {
            //Obteniendo la informacion de la petion. Pendiente validar los parametros.
            String nombreUsuario = ctx.formParam("username");
            String password = ctx.formParam("password");
            System.out.print("\n Nombre de usuario =" + nombreUsuario + "pass =" + password);
            /**
             * Validar usuario
             *
             */
            Usuario auxUsuario = null;
            auxUsuario = Controladora.getInstance().buscarUsuario(nombreUsuario, password);
            Map<String, Object> modelo = new HashMap<>();
            // modelo.put("titulo", "Listado de producto");
            modelo.put("usuario", auxUsuario);
            //enviando al sistema de plantilla.
            ctx.render("/publico/Admin/inicio.html", modelo);
            if (auxUsuario != null) {
                ctx.redirect("/administrado");
            } else {
                modelo.put("Error", "Please check username & password! ");
                //ctx.redirect("/");//colcoar luego la ruta de login
                ctx.render("/publico/inisioSesion/index.html", modelo);
                System.out.print("\n Usuario no encontrado. Revise su nombre  de usuario y su password");
            }

        });

        app.get("/lista-Venta", ctx -> {
            Map<String, Object> modelo = new HashMap<>();
            modelo.put("usuario", "Daniel P. Moronta");
            modelo.put("titulo", "Producto en el carrito");
            try {
                List<VentasProductos> aux = new ArrayList<>();
                aux  =  Controladora.getControladora().getMisVentasProducto();
                modelo.put("lista", aux);
                System.out.print("/SIZE: "+aux.size());
            }catch (Exception e){
                System.out.print(e);
            }
            ctx.render("/publico/Admin/ventas.html", modelo);

        });

        app.get("/carrito", ctx -> {
            Map<String, Object> modelo = new HashMap<>();
            modelo.put("usuario", "Daniel P. Moronta");
            modelo.put("titulo", "Producto en el carrito");
            ArrayList<ProductoCarrito> aux = (ArrayList<ProductoCarrito>) (((CarroCompra) ctx.sessionAttribute("carrito")).getListaProducto());
            String a = "("+   (((CarroCompra) ctx.sessionAttribute("carrito")).getCont()) +")";
            modelo.put("cantCarrito", a);
            modelo.put("lista", aux);
            modelo.put("total", (((CarroCompra) ctx.sessionAttribute("carrito")).calcularTotal()));
            //  modelo.put("Lista")
            //que mas ??
            //enviando al sistema de plantilla.
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
            //que mas ??
            //enviando al sistema de plantilla.
            /**
             * Como hacer que si el usuario no se ha identificado que no entre en esta parte?
             */
            ctx.render("/publico/Admin/inicio.html",modelo);

        });

        /**
         * Agregar producto y redrigir a ventra admin
         */
        app.post("/Menu", ctx -> {
            Map<String, Object> modelo = new HashMap<>();
            modelo.put("usuario","Daniel Peña");
            //Agregar producto
            System.out.print(ctx.formParam("addNombre"));
            try {
                String name = ctx.formParam("addNombre");//permite obtener lo que se ingre  en parrametro <nomnbre>
                String p = "0.00";
                p =  ctx.formParam("addPrecio");
                BigDecimal precio= new BigDecimal(p);
               // System.out.println("EL PRECIO ES: "+precio);
                //Producto miProducto = new Producto("ID-01",name, precio);
                Controladora.getInstance().crearProducto(name,precio);
            }catch (Exception e){
                System.out.println("\nNo se pudo guardar el producto");
            }
            ctx.redirect("/administrado");

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
         //   System.out.print("Entrado para annadir al carrito\n");
             String idProducto = ctx.formParam("x");
             String cantProducto = ctx.formParam("cant");
             Producto producto =  Controladora.getInstance().buscarProducto(idProducto);
             if(producto!=null){

                  int cantidad = Integer.parseInt(cantProducto);
                 ((CarroCompra) ctx.sessionAttribute("carrito")).agregarProducto(producto,
                         cantidad);
               //   carro.agregarProducto(producto,cantidad);
                 System.out.print("\n Agregando correctamente");
            }
             System.out.print("\nCantidad: "+ctx.formParam("cant")); //CANTIDAD PARA AGREGAR AL CARRITO
             System.out.print("\nID: "+ctx.formParam("x")); //ID PARA AGREGAR AL CARRITO
            ctx.redirect("/");//rendrijiendo a pagina principal y pasando render
        });

        /**
         * Actualizar producto
         */
        app.post("/administrado",ctx -> {
            System.out.print("\n Reciviendo por metodo POST, para editar producto");
            Map<String, Object> modelo = new HashMap<>();
            modelo.put("usuario","Daniel Peña");
            //Editando producto
                String id = ctx.formParam("id");//Le pasa como paramento dentro de un formulario
                String name = ctx.formParam("nombre");//permite obtener lo que se ingre  en parrametro <nomnbre>
                String p = "0.00";
                p =  ctx.formParam("precio");
                BigDecimal precio= new BigDecimal(p);
                System.out.println("\nID="+id+"PRECIO: "+p+"\n"+"Nombre="+name);
                System.out.print(Controladora.getInstance().actulizarProducto(id,name,precio));
                if(true==Controladora.getInstance().actulizarProducto(id,name,precio)){
                    System.out.print("Actualizado");
                }
            List<Producto> auxProducto = Controladora.getInstance().getMiProducto();
            modelo.put("titulo", "Listado de producto");
            modelo.put("lista", auxProducto);
            ctx.render("/publico/Admin/inicio.html",modelo);

        });

        /**
         * Funcion para borrar producto
         */
        app.post("/borrar", ctx -> {
            System.out.print("\n Entrando por metodo POST para borrar producto");
            String id = ctx.formParam("idBorrar") ;
            if(Controladora.getInstance().borrarProducto(id)==true){
                System.out.print("\n Producto Borrado con existo");
                ctx.redirect("/administrado");
            }else{
                System.out.print("\n El Producto No se Pudo Borrar");
            }

        });

        app.post("/borrarProducroCarrio", ctx -> {
            System.out.print("\nEntrando por metodo POST para borrar producto del carrito");
            String id = ctx.formParam("idBorrar") ;
            System.out.print("\nID PRODUCTO A BORRAR DE CARRITO: "+id);
            ((CarroCompra) ctx.sessionAttribute("carrito")).eliminarProducto(id);
          //  System.out.print("\nTamano="+((CarroCompra) ctx.sessionAttribute("carrito")).getListaProducto().size());
            ctx.redirect("/carrito");

           // carro.bor
        });

        /**
         * Permite realizar la compra y colocar el nombre al cliente respetibamente
         */
        app.post("/agregarCliente", ctx -> {
            String nombreCliente = ctx.formParam("nombre");
            System.out.print("\n Realizando compra*************\n");
            try {
                if( ((CarroCompra) ctx.sessionAttribute("carrito")).getCont()>0) {
                    System.out.print("\nSe ha registrado la compra");
                    System.out.print("\n Haciendo compra...");
                    System.out.print("\n Cantidad de items agregado: "+ ((CarroCompra) ctx.sessionAttribute("carrito")).getListaProducto().size());
                    ArrayList<ProductoCarrito> aux = (ArrayList<ProductoCarrito>) (((CarroCompra) ctx.sessionAttribute("carrito")).getListaProducto());

                  // List<ProductoCarrito> auxProducto =  carro.getListaProducto();
                    System.out.print("\n Cantidad de items agregado: "+ aux.size());

                    Date fecha = new Date();
                    VentasProductos auxVenta = new VentasProductos(fecha, nombreCliente, aux);
                    Controladora.getInstance().agregarVenta(auxVenta);
                    //carro = new CarroCompra(); //para limpiar

                    ((CarroCompra) ctx.sessionAttribute("carrito")).limpiarCarrito();
                    ctx.redirect("/carrito");
                    System.out.print("\n Cantidad de items agregado: "+ aux.size());
                }else{
                    ctx.redirect("/carrito");
                    System.out.print("\n Debe agregar item para realizar compra");
                }

            }catch (Exception e){

            }

           // carro.limpiarCarrito();
        });

        app.post("/limpiarCarro", ctx -> {
            ((CarroCompra) ctx.sessionAttribute("carrito")).limpiarCarrito();
            ctx.redirect("/carrito");

        });
        /**
         * Creando session
         */
        app.before(ctx -> {
            CarroCompra carrito = ctx.sessionAttribute("carrito");

            if (carrito == null) {
                carrito = new CarroCompra();
                ctx.sessionAttribute("carrito", carrito);
            }
        });




    }


}
