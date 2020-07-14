package carrito.services;

import carrito.encapsulacion.Comentario;
import carrito.encapsulacion.Producto;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ServicioComentario extends ManejadorBD<Comentario>{
    public ServicioComentario() {
        super(Comentario.class);
    }

    public List<Comentario> getComentarios(Producto producto){
        String id = producto.getId();
        List<Comentario> comentarioList = new ArrayList<>();
        EntityManager entityManager = getEntityManager();
        EntityManager em = getEntityManager();
        Query query = em.createNativeQuery("select * from Comentario  WHERE Comentario.PRODUCTO_ID LIKE :id", Comentario.class);
        query.setParameter("id", id);
        List<Comentario> lista = query.getResultList();
        return lista;
    }
    /**
    public void eliminarComentarios(Producto producto){
        System.out.println("BORRANDO LOS COMENTARIOS DEL PRODUCTO\n\n\n");
        String id = producto.getId();
        EntityManager entityManager = getEntityManager();
        //entityManager.persist(producto);
        entityManager.createNativeQuery("DELETE from Comentario  WHERE Comentario.PRODUCTO_ID LIKE :id")
                .setParameter("id", producto.getId());
                //.executeUpdate();
        //assertThat(entityManager.find(Foo.class, foo.getId()), nullValue());

    }
    **/
}
