package practica2.controladores;

import io.javalin.Javalin;
import practica2.encapsulacion.Controladora;
import practica2.encapsulacion.Producto;
import practica2.encapsulacion.Usuario;
import practica2.encapsulacion.VentasProductos;
import practica2.util.ControladorBase;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.*;

public class ZonaAdmin extends ControladorBase {

    public ZonaAdmin(Javalin app) {
        super(app);
    }

    @Override
    public void aplicarRutas() {
        /*
        Primeros pasos
         */
        app.routes(() -> {
            path("/zonaAdmin", () -> {
                before("/",ctx-> {
                    /*
                    Verificar si incicio sesion
                     */
                    System.out.println("Entrando a before");
                    Usuario usuario = ctx.sessionAttribute("usuario");
                    if(usuario==null){
                        /**
                         * No existe, tiene que iniciar session
                         */
                        System.out.println("Verificando si es null");
                        ctx.redirect("/iniciarSession");
                    }else{
                        System.out.println("Verificando si campeon");
                    }
                });
                get(ctx -> {
                    Map<String, Object> modelo = new HashMap<>();
                    modelo.put("usuario","Daniel Peña");
                    List<Producto> auxProducto = Controladora.getInstance().getMiProducto();
                    modelo.put("titulo", "Listado de producto");
                    modelo.put("lista", auxProducto);
                    ctx.render("/publico/Admin/inicio.html",modelo);

                });

                get("/hola", ctx -> {
                    System.out.println("Hola mundo");
                });

            });

        });


        app.get("/iniciarSession", ctx -> {
            ctx.render("/publico/inisioSesion/index.html");

            /**
             *
             */
         //   ctx.render("/publico/inisioSesion/index.html");

        });
        app.post("/autenticar", ctx -> {
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

            if (auxUsuario != null) {

                ctx.sessionAttribute("usuario", auxUsuario);
                //  ctx.redirect("/"
                ctx.redirect("/zonaAdmin");
                //ctx.render("/publico/Admin/inicio.html", modelo);
            } else {
                modelo.put("Error", "Please check username & password! ");
                //ctx.redirect("/");//colcoar luego la ruta de login
                ctx.render("/publico/inisioSesion/index.html", modelo);
                System.out.print("\n Usuario no encontrado. Revise su nombre  de usuario y su password");
            }

            System.out.println("Entrnado a inciiar sesion");

        });
        app.get("/zonaAdmin/lista-Venta", ctx -> {
            System.out.println("Za");
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
        app.before("/zonaAdmin/lista-Venta", ctx -> {
            Usuario usuario = ctx.sessionAttribute("usuario");
            if(usuario==null){
                /**
                 * No existe, tiene que iniciar session
                 */
                System.out.println("Verificando si es null");
                ctx.redirect("/iniciarSession");
            }else{
                System.out.println("Verificando si campeon");
            }

        });
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
            ctx.redirect("/zonaAdmin");

        });
        app.post("/borrar", ctx -> {
            System.out.print("\n Entrando por metodo POST para borrar producto");
            String id = ctx.formParam("idBorrar") ;
            if(Controladora.getInstance().borrarProducto(id)==true){
                System.out.print("\n Producto Borrado con existo");
                ctx.redirect("/zonaAdmin");
            }else{
                System.out.print("\n El Producto No se Pudo Borrar");
            }

        });
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




    }
}
