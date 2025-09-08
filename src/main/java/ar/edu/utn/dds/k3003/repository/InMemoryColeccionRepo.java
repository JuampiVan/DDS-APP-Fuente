package ar.edu.utn.dds.k3003.repository;

import ar.edu.utn.dds.k3003.model.Coleccion;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Profile("!test")
public class InMemoryColeccionRepo implements ColeccionRepository {

  private List<Coleccion> colecciones;

  public InMemoryColeccionRepo() {
    this.colecciones = new ArrayList<>();
  }

  @Override
  public Optional<Coleccion> findById(String id) {
    return this.colecciones.stream().filter(c -> c.getNombre().equals(id)).findFirst();
  }

  @Override
  public Coleccion save(Coleccion coleccion) {
    this.colecciones.add(coleccion);
    return coleccion;
  }

  @Override
  public List<Coleccion> findAll() {
    return new ArrayList<>(colecciones);
  }
}

