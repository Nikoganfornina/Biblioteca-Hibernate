package DAO;

import Util.HibernateUtil;
import entities.Libro;
import entities.Prestamos;
import entities.Socio;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class IPrestamosImpl implements IPrestamos {
    public List<Libro> getAllLibros() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Libros", Libro.class).list();
        }
    }


    public List<Socio> getAllSocios() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Socios", Socio.class).list();
        }
    }

    @Override
    public List<Prestamos> getAllPrestamos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Prestamos", Prestamos.class).list();
        }
    }

    @Override
    public List<Prestamos> getHistorialPrestamosPorSocio(Socio socio) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Prestamos WHERE socio = :socio", Prestamos.class)
                    .setParameter("socio", socio)
                    .list();
        }
    }

    @Override
    public void registrarPrestamo(Prestamos prestamo) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(prestamo);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
