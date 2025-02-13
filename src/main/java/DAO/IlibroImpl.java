package DAO;

import Util.HibernateUtil;
import entities.Autor;
import entities.Libro;
import org.hibernate.Session;

import java.util.List;

public class IlibroImpl implements Ilibro {
    @Override
    public List<Libro> findAll() {
        //Abrir Sesion
        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();
        List<Libro> libros = session.createQuery("from Libro").list();

        session.getTransaction().commit();
        session.close();

        System.out.println("Tamaño de la lista de autores: " + libros.size());

        return libros;
    }

    @Override
    public List<Libro> findByTitulo(String titulo) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Libro> libros = session.createQuery( "from Libro where titulo like :titulo" , Libro.class)
                .setParameter("titulo", "%"+ titulo + "%")
                .list();
        session.close();
        return libros;
    }

    @Override
    public List<Libro> findByAutor(Integer Id_Autor) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Libro> Autores = session.createQuery( "from Libro where Id_Autor like :titulo " , Libro.class)
                .setParameter("Id_Autor", "%"+ Id_Autor + "%")
                .list();
        session.close();
        return Autores;
    }

    @Override
    public List<Libro> findByISBN(Integer isbn) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Libro> ISBN = session.createQuery( "from Libro where Id_Autor like :titulo " , Libro.class)
                .setParameter("Id_Autor", "%"+ isbn + "%")
                .list();
        session.close();
        return ISBN;


    }

    @Override
    public Libro save(Libro libro) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(libro);
        session.getTransaction().commit();
        session.close();
        return libro;
    }

    @Override
    public Libro update(Libro libro) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(libro);
        session.getTransaction().commit();
        session.close();
        return libro;
    }

    @Override
    public boolean delete(Libro libro) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(session.get(Libro.class, libro.getId()));
        session.getTransaction().commit();
        session.close();
        return true;
    }
}
