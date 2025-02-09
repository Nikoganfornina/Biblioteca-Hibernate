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
        session.beginTransaction();

        // Realizamos la consulta
        List<Autor> autores = session.createQuery("from Autor", Autor.class).list();

        session.getTransaction().commit();
        session.close();

        // Imprimir el tamaño de la lista para comprobar si contiene elementos
        System.out.println("Tamaño de la lista de autores: " + autores.size());

        return autores;
    }

    public List<Autor> findByNombre(String nombre) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Autor> autores = session.createQuery("from Autor where nombre like :nombre", Autor.class)
                .setParameter("nombre", "%" + nombre + "%") // Busca coincidencias parciales
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
    public Autor update(Autor autor) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        try {
            session.update(autor);  // Actualizamos el autor directamente
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback(); // Revertimos si hay un error
            e.printStackTrace();
        } finally {
            session.close();
        }

        return autor;
    }



    @Override
    public boolean delete(Autor Autor) {

            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(session.get(Autor.class, Autor.getId()));
            session.getTransaction().commit();
            session.close();
            return true;
        }
    }

