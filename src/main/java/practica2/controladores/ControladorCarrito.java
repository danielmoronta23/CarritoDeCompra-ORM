package practica2.controladores;

import io.javalin.Javalin;
import practica2.encapsulacion.*;
import practica2.util.ControladorBase;

import java.util.*;

import static io.javalin.apibuilder.ApiBuilder.path;

public class ControladorCarrito extends ControladorBase {

    public ControladorCarrito(Javalin app) {
        super(app);
    }

    @Override
    public void aplicarRutas() {
        app.routes(() -> {
            path("/", () ->{ //Reune un grupo de Endpoint para que funcionen en un mismo path o ruta
                app.get("/", ctx -> {
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

        app.post("/borrarProducroCarrio", ctx -> {
            System.out.print("\nEntrando por metodo POST para borrar producto del carrito");
            String id = ctx.formParam("idBorrar") ;
            System.out.print("\nID PRODUCTO A BORRAR DE CARRITO: "+id);
            ((CarroCompra) ctx.sessionAttribute("carrito")).eliminarProducto(id);
            System.out.print("\nTamano="+((CarroCompra) ctx.sessionAttribute("carrito")).getListaProducto().size());
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
                    System.out.print("\n Cantidad de items agregado: "+((CarroCompra) ctx.sessionAttribute("carrito")).getListaProducto().size());
                    ArrayList<ProductoCarrito> aux = (ArrayList<ProductoCarrito>) (((CarroCompra) ctx.sessionAttribute("carrito")).getListaProducto());
                    for (ProductoCarrito a:aux)
                    {
                        System.out.println("\n");
                        System.out.println("ID-PRODUCTO AGREGADO: "+a.getIdProductoCarrito());
                        System.out.println("\n");
                        System.out.println("ID-PRODUCTO : "+a.getProducto().getId());
                        System.out.println("\n");
                    }
                    System.out.print("\n Cantidad de items agregado: "+ aux.size());

                    Date fecha = new Date();
                    VentasProductos auxVenta = new VentasProductos(fecha, nombreCliente, aux);
                    Controladora.getInstance().agregarVenta(auxVenta);

                    ((CarroCompra) ctx.sessionAttribute("carrito")).limpiarCarrito();
                    ctx.redirect("/carrito");
                    System.out.print("\n Cantidad de items agregado: "+ aux.size());
                }else{
                    ctx.redirect("/carrito");
                    System.out.print("\n Debe agregar item para realizar compra");
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

    }

}
