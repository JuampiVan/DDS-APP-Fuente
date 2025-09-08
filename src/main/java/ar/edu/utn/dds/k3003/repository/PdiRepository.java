package ar.edu.utn.dds.k3003.repository;

import ar.edu.utn.dds.k3003.model.Coleccion;
import ar.edu.utn.dds.k3003.model.Pdi;
import java.util.List;
import java.util.Optional;

public interface PdiRepository {

  Optional<Pdi> findById(String id);
  Pdi save(Pdi col);
  List<Pdi> findAll();

}
