package carrito.services;

import carrito.encapsulacion.Comentario;
import carrito.encapsulacion.Producto;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ServicioProducto extends ManejadorBD<Producto> {

    public ServicioProducto() {
        super(Producto.class);
    }
    //Funcion para hacer la paginacion de la lista de producto, solo se muestran 10 por cada pagina
    public List<Producto> paginacionProducto(int pagina){
        int pageSize = 10;
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createNativeQuery("SELECT * FROM Producto", Producto.class);
        query.setFirstResult((pagina - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }
    public int cantProducto(){
        EntityManager entityManager = getEntityManager();
        String countQ = "SELECT COUNT(P.id) FROM Producto P";
        Query countQuery = entityManager.createQuery(countQ);//consulta JQPL
        return ((Number) countQuery.getSingleResult()).intValue();
    }


}
