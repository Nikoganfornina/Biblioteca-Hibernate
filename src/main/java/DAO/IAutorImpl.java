package DAO;

import entities.Autor;

import java.util.List;

public class IAutorImpl implements IAutor {
    @Override
    public List<Autor> findAll() {
        return List.of();
    }

    @Override
    public List<Autor> findByTitulo(String Nombre) {
        return List.of();
    }

    @Override
    public Autor save(Autor Autor) {
        return null;
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
