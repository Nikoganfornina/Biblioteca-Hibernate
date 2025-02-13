package DAO;

import entities.Socio;

import java.util.List;

public interface ISocio {
    void agregarSocio(Socio socio);
    void actualizarSocio(Socio socio);
    void eliminarSocio(int id);
    Socio obtenerSocio(int id);
    List<Socio> obtenerTodosLosSocios();
}
