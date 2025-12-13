package ar.edu.utn.dds.k3003.repository.mongo;

import ar.edu.utn.dds.k3003.model.Hecho;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoHechoRepository extends MongoRepository<Hecho, String> {
}
