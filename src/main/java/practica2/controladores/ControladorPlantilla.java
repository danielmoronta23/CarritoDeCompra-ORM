package practica2.controladores;

import io.javalin.Javalin;
import io.javalin.plugin.rendering.JavalinRenderer;
import io.javalin.plugin.rendering.template.JavalinFreemarker;
import io.javalin.plugin.rendering.template.JavalinThymeleaf;
import io.javalin.plugin.rendering.template.JavalinVelocity;
import practica2.encapsulacion.Controladora;
import practica2.encapsulacion.Producto;
import practica2.util.ControladorBase;

import javax.xml.xpath.XPath;

import java.math.BigDecimal;
import java.net.StandardSocketOptions;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.*;

public class ControladorPlantilla extends ControladorBase {

    public ControladorPlantilla(Javalin app) {
        super(app);
        registrandoPlantillas();
    }
    private void registrandoPlantillas(){
        //registrando los sistemas de plantilla.

        JavalinRenderer.register(JavalinThymeleaf.INSTANCE, ".html");

    }

    @Override
    public void aplicarRutas() {
        System.out.println("Entrado por ruta de controlador Plantilla");
     /*   app.get("/", ctx -> {
            List<String> salida = new ArrayList<>();
            salida.add("Mostrando todos los parametros enviados:");
            //listando la informacion.
            ctx.queryParamMap().forEach((key, lista) -> {
                salida.add(String.format("[%s] = [%s]", key, String.join(",", lista)));
                System.out.println( String.join(",", lista));
            });
            //
            ctx.result(String.join("\n", salida));
        });
*/
        app.routes(() ->{
            /**
             * Proceso para agregar un producto
             */
            get("/", ctx -> {
               // System.out.println("El tipo de datos recibido: "+ctx.header("Content-Type")+ "boorar:"+ctx.queryParam("Borrar"));

                //tomando el parametro utl y validando el tipo.
              //  System.out.println("El tipo de datos recibido: "+ctx.header("Content-Type")+ "nombbre:"+ctx.queryParam("nombre"));

            //    String name = ctx.queryParam("nombre");//permite obtener lo que se ingre  en parrametro <nomnbre>
            //    String p = "0.00";

             //   System.out.println("PRECIO: "+precio);
             //   Producto b = new Producto("01",name,precio);
             //  Controladora.getInstance().crearProducto(b);

                    try {
                        String name = ctx.queryParam("nombre");//permite obtener lo que se ingre  en parrametro <nomnbre>
                        String p = "0.00";
                        p =  ctx.queryParam("precio");
                        BigDecimal precio= new BigDecimal(p);
                        System.out.println("EL PRECIO ES: "+precio);
                     //   Producto miProducto = new Producto("ID-01",name, precio);
                        Controladora.getInstance().crearProducto(name,precio);

                    }catch (Exception e){
                        System.out.println("No se pudo guardar el precio");
                    }
    


                List<Producto> auxProducto = Controladora.getInstance().getMiProducto();
                //

              //  Producto a = new Producto("01","daniel",y);

              //  auxProducto.add(a);
               //System.out.println("Prubea borrar"+ctx.pathParam("Borrar"));

                Map<String, Object> modelo = new HashMap<>();
                modelo.put("titulo", "Listado de producto");
                modelo.put("lista", auxProducto);

                //enviando al sistema de plantilla.
                ctx.render("/publico/Admin/inicio.html", modelo);

                
            });

            /**
             * Proceso para editar un Producto.
             */


        });

    }
}
