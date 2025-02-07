package Util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    public static SessionFactory getSessionFactory() {
        // Crear configuración de Hibernate
        Configuration configuration = new Configuration();

        // Cargar configuración desde el archivo hibernate.cfg.xml
        configuration.configure();

        // Devolver la fábrica de sesiones configurada
        return configuration.buildSessionFactory();
    }
}
