package carrito.controladores;

import io.javalin.Javalin;


import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import carrito.encapsulacion.Controladora;
import carrito.encapsulacion.Producto;
import carrito.encapsulacion.Usuario;
import carrito.encapsulacion.VentasProductos;
import carrito.util.ControladorBase;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.*;

public class ZonaAdmin extends ControladorBase {
    private static String mpCryptoPassword = "BornToFight";

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
                    //tratandodo de hacer con la cookie

                    if(usuario==null){
                        /**
                         * No existe, tiene que iniciar session
                         */
                        String nombre_Usuario = ctx.cookie("usuario");
                        String password_Encriptada = ctx.cookie("password");
                        if(nombre_Usuario != null  && password_Encriptada !=null){
                            //eso quiere decir que hay una cookie creada
                            StandardPBEStringEncryptor decryptor = new StandardPBEStringEncryptor();
                            decryptor.setPassword(mpCryptoPassword);
                            usuario  = Controladora.getInstance().autenticarUsuario(nombre_Usuario, decryptor.decrypt(password_Encriptada));
                           if(usuario != null){
                               ctx.sessionAttribute("usuario", usuario);
                            }

                        }
                        System.out.println("Verificando si es null");
                        ctx.redirect("/iniciarSession");

                    }else{
                        //System.out.println("Verificando si campeon");
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


            });

        });

        app.get("/logout", ctx -> {
            System.out.println("Eliminado Cookie: Usuario & Password\n");
            ctx.removeCookie("usuario");
            ctx.removeCookie("password");
            ctx.redirect("/inisioSesion");
            ctx.sessionAttribute("usuario", null);
        });
        app.get("/iniciarSession", ctx -> {
            ctx.render("/publico/inisioSesion/index.html");
        });
        app.post("/autenticar", ctx -> {
            //Ssi el checkbok es seleciona devuelve "on"
            String estadoRecodar= "";
            //creando cookie para recodar usuario y contrsenna

           // System.out.println("\nESTADO CHECKBOK-> " + ctx.formParam("Recordar"));
            String nombreUsuario = ctx.formParam("username");
            String password = ctx.formParam("password");
           // System.out.print("\n Nombre de usuario =" + nombreUsuario + "pass =" + password);
            /**
             * Validar usuario
             *
             */
            Usuario auxUsuario = null;
            auxUsuario = Controladora.getInstance().autenticarUsuario(nombreUsuario, password);
            Map<String, Object> modelo = new HashMap<>();
            // modelo.put("titulo", "Listado de producto");
            modelo.put("usuario", auxUsuario);
            //enviando al sistema de plantilla.


            //Se validad es usuario
            if (auxUsuario != null) {
                estadoRecodar =  ctx.formParam("Recordar");
                if(estadoRecodar!=null){
                    if(estadoRecodar.equalsIgnoreCase("ON")){
                        //Creando cookie para recordar usuario
                        System.out.println("Creando cookie...\n");
                        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
                        encryptor.setPassword(mpCryptoPassword);
                        encryptor.encrypt(auxUsuario.getPassword());

                        ctx.cookie("usuario", auxUsuario.getUsuario(), 604800);// se crea la cookie por una semana
                        ctx.cookie("password",   encryptor.encrypt(auxUsuario.getPassword()), 604800);
                        //System.out.println("Contrse;a desc--" +encryptor.decrypt( encryptor.encrypt(auxUsuario.getPassword())));
                    }
                }

                ctx.sessionAttribute("usuario", auxUsuario);
                ctx.redirect("/zonaAdmin");
            } else {
                modelo.put("Error", "Please check username & password! ");
                ctx.render("/publico/inisioSesion/index.html", modelo);
                System.out.print("\n Usuario no encontrado. Revise su nombre  de usuario y su password");
            }

            System.out.println("Entrando a inciar sesion");

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

                ctx.redirect("/iniciarSession");
            }else{

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
                System.err.println("\nNo se pudo guardar el producto");
            }
            ctx.redirect("/zonaAdmin");

        });
        app.post("/borrar", ctx -> {
            System.out.print("Entrando por metodo POST para borrar producto\n");
            String id = ctx.formParam("idBorrar") ;
            if(Controladora.getInstance().borrarProducto(id)==true){
                System.out.print("Producto Borrado con existo\n");
                ctx.redirect("/zonaAdmin");
            }else{
                System.out.print("El Producto No se Pudo Borrar\n");
            }

        });
        app.post("/administrado",ctx -> {
            System.out.print("Reciviendo por metodo POST, para editar producto\n");
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
