package DAO;

import entities.Prestamos;
import entities.Socio;

import java.util.List;

public interface IPrestamos {
    List<Prestamos> getAllPrestamos();
    List<Prestamos> getHistorialPrestamosPorSocio(Socio socio);
    void registrarPrestamo(Prestamos prestamo);
}
