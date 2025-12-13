package ar.edu.utn.dds.k3003.repository.jpa;

import ar.edu.utn.dds.k3003.model.Pdi;
import ar.edu.utn.dds.k3003.repository.PdiRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile("test")
public interface JpaPdiRepository extends JpaRepository<Pdi, String>, PdiRepository {
} 