package practica2.controladores;

import io.javalin.Javalin;
import io.javalin.plugin.rendering.JavalinRenderer;
import io.javalin.plugin.rendering.template.JavalinThymeleaf;
import org.attoparser.ParsingXmlDeclarationMarkupUtil;
import practica2.encapsulacion.Controladora;
import practica2.encapsulacion.Producto;
import practica2.encapsulacion.Usuario;
import practica2.util.ControladorBase;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.*;

public class ControladorInicioSesion extends ControladorBase {
    //List<Usuario> users = Controladora.getControladora().getMisUsuarios();

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
//                    ctx.cookie("usuario", "nombre de usuario"); //permite crear una cookie
                    ctx.render("/publico/inisioSesion/index.html"); //ctx es el contexto que usa Javalin para manejar los endpoint
                });
                //Los mismo, puedes usar incluso en el doc HTML un <a ref="dasd" href="/Admin/inicio.html> y javalin lo reconocera
                /*
                app.get("/inicio.html", ctx -> {

                 //   ctx.redirect("/401.html");
                    //Hacer modelo de datos para enviar
                    Map<String, Object> modelo = new HashMap<>();
                    modelo.put("usuario","Daniel Peña");
                        //Agregar producto
                    try {
                        String name = ctx.queryParam("nombre");//permite obtener lo que se ingre  en parrametro <nomnbre>
                        String p = "0.00";
                        p =  ctx.queryParam("precio");
                        BigDecimal precio= new BigDecimal(p);
                        System.out.println("EL PRECIO ES: "+precio);
                        Producto miProducto = new Producto("ID-01",name, precio);
                        Controladora.getInstance().crearProducto(miProducto);

                    }catch (Exception e){
                        System.out.println("No se pudo guardar el producto");
                    }
                    List<Producto> auxProducto = Controladora.getInstance().getMiProducto();
                    modelo.put("titulo", "Listado de producto");
                    modelo.put("lista", auxProducto);
                    //que mas ??
                    //enviando al sistema de plantilla.
                    ctx.render("/publico/Admin/inicio.html", modelo);

                });
*/
            });
        });
        app.post("/autenticar", ctx -> {
            //Obteniendo la informacion de la petion. Pendiente validar los parametros.
            String nombreUsuario = ctx.formParam("username");
            String password = ctx.formParam("password");
            System.out.print("Nombre de usuario =" + nombreUsuario + "pass =" + password);
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


                System.out.print("Usuario no encontrado. Revise su nombre  de usuario y su password");
            }
          //  ctx.render("/", modelo);
            //redireccionando la vista con autorizacion.
            // ctx.redirect("/zona-admin-clasica/");
        });
        app.get("/administrado", ctx -> {
            ctx.render("/publico/Admin/inicio.html");
        });
        /**
         * Redirige a la ventana administravtiva
         */
    }
}
