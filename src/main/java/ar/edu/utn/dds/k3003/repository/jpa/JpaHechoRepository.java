package ar.edu.utn.dds.k3003.repository.jpa;

import ar.edu.utn.dds.k3003.model.Hecho;
import ar.edu.utn.dds.k3003.repository.HechoRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile("test")
public interface JpaHechoRepository extends JpaRepository<Hecho, String>, HechoRepository {
} 