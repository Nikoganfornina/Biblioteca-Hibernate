package DAO;

import Util.HibernateUtil;
import entities.Autor;
import entities.Libro;
import org.hibernate.Session;

import java.util.List;

public class IAutorImpl implements IAutor {
    @Override
    public List<Autor> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        List<Autor> autores = session.createQuery("from Autor").list();
        session.close();
        return autores;
    }

    @Override
    public List<Autor> findByNombre(String Nombre) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Autor> autores = session.createQuery( "from Autor where nombre like : nombre" , Autor.class)
                .setParameter("nombre", "%"+ Nombre + "%")
                .list();
        session.close();
        return autores;
    }

    @Override
    public Autor save(Autor autores) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(autores);
        session.getTransaction().commit();
        session.close();
        return autores;

    }

    @Override
    public Autor update(Autor Autor) {
        return null;
    }

    @Override
    public boolean delete(Autor Autor) {
        return false;
    }
}
