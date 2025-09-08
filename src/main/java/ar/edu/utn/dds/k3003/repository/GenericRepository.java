package ar.edu.utn.dds.k3003.repository;

import ar.edu.utn.dds.k3003.model.Coleccion;
import java.util.List;
import java.util.Optional;

public interface GenericRepository<T> {

  Optional<T> findById(String id);
  T save(T coleccion);
  List<T> findByColeccion(Coleccion coleccion);
}
