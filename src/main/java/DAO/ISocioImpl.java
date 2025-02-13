package DAO;

import Util.HibernateUtil;
import entities.Socio;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class ISocioImpl implements ISocio {

    @Override
    public void agregarSocio(Socio socio) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(socio);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void actualizarSocio(Socio socio) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(socio);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void eliminarSocio(int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Socio socio = session.get(Socio.class, id);
            if (socio != null) {
                session.remove(socio);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public Socio obtenerSocio(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Socio.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Socio> obtenerTodosLosSocios() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Socios", Socio.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Socio> findByNombre(String nombre) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Socios WHERE nombre LIKE :nombre";
            Query<Socio> query = session.createQuery(hql, Socio.class);
            query.setParameter("nombre", "%" + nombre + "%");
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public List<Socio> findByTelefono(int telefono) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Socios WHERE telefono = :telefono";
            Query<Socio> query = session.createQuery(hql, Socio.class);
            query.setParameter("telefono", telefono);  // Usamos el parámetro de tipo int
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    public List<Socio> findAll() {
        return obtenerTodosLosSocios();
    }
}
