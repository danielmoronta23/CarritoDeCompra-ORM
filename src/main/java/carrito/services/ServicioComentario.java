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
        //ARREGLAR QUERY
        /**
        Query comentario = entityManager.createQuery("SELECT Comentario FROM Comentario WHERE Id_Comentario = :id");
        comentario.setParameter("id", id+"%");
        Query fecha = entityManager.createQuery("SELECT Fecha FROM Comentario WHERE Id_Comentario = :id");
        fecha.setParameter("id", id+"%");
        List<String> comentarios = comentario.getResultList();
        List<String> fechas = fecha.getResultList();
         **/
        /**
        for (int i = 0; i < comentarios.size(); i++) {
            try {
                comentarioList.add(new Comentario(producto, comentarios.get(i), new SimpleDateFormat("dd/MM/yyyy").parse(fechas.get(i))));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
         **/
        return lista;
    }

}
