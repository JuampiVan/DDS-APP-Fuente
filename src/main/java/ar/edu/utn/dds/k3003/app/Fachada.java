package ar.edu.utn.dds.k3003.app;

import ar.edu.utn.dds.k3003.facades.FachadaFuente;
import ar.edu.utn.dds.k3003.facades.FachadaProcesadorPdI;
import ar.edu.utn.dds.k3003.facades.dtos.ColeccionDTO;
import ar.edu.utn.dds.k3003.facades.dtos.HechoDTO;
import ar.edu.utn.dds.k3003.facades.dtos.PdIDTO;
import ar.edu.utn.dds.k3003.model.Coleccion;
import ar.edu.utn.dds.k3003.model.Hecho;
import ar.edu.utn.dds.k3003.model.Pdi;
import ar.edu.utn.dds.k3003.model.exceptions.HechoExistException;
import ar.edu.utn.dds.k3003.repository.ColeccionRepository;
import ar.edu.utn.dds.k3003.repository.GenericRepository;
import ar.edu.utn.dds.k3003.repository.HechoRepository;
import ar.edu.utn.dds.k3003.repository.InMemoryColeccionRepo;
import ar.edu.utn.dds.k3003.repository.InMemoryHechoRepo;
import ar.edu.utn.dds.k3003.repository.InMemoryPdiRepo;
import ar.edu.utn.dds.k3003.repository.PdiRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class Fachada implements FachadaFuente {

  private ColeccionRepository coleccionRepository;
  private HechoRepository hechoRepository;
  private FachadaProcesadorPdI fachadaProcesadorPdI;
  private PdiRepository pdIRepository;

   protected Fachada() {
    this.coleccionRepository = new InMemoryColeccionRepo();
    this.hechoRepository = new InMemoryHechoRepo();
    this.pdIRepository = new InMemoryPdiRepo();
  }

  @Autowired
  public Fachada(ColeccionRepository coleccionRepository, HechoRepository hechoRepository, PdiRepository pdIRepository) {
    this.coleccionRepository = coleccionRepository;
    this.hechoRepository = hechoRepository;
    this.pdIRepository = pdIRepository;
  }

  @Override
  public ColeccionDTO agregar(ColeccionDTO coleccionDTO) {
    if (this.coleccionRepository.findById(coleccionDTO.nombre()).isPresent()){
      throw new IllegalArgumentException(coleccionDTO.nombre() + "ya existe");
    }
    Coleccion coleccion = new Coleccion(coleccionDTO.nombre(), coleccionDTO.descripcion());
    this.coleccionRepository.save(coleccion);
    return new ColeccionDTO(coleccion.getNombre(),coleccion.getDescripcion());
  }

  @Override
  public ColeccionDTO buscarColeccionXId(String s) throws NoSuchElementException {
    val coleccionOptional = this.coleccionRepository.findById(s);
    if ( coleccionOptional.isEmpty()){
      throw new NoSuchElementException(s + "no existe");
    }
    Coleccion coleccion = (Coleccion) coleccionOptional.get();
    return new ColeccionDTO(coleccion.getNombre(),coleccion.getDescripcion());
  }

  @Override
  public HechoDTO agregar(HechoDTO hechoDTO) {
    if (this.hechoRepository.findById(hechoDTO.id()).isPresent()){
      throw new HechoExistException(hechoDTO.id() + "ya existe");
    }
    ColeccionDTO coleccionDTO = buscarColeccionXId(hechoDTO.nombreColeccion());
    Coleccion coleccion = new Coleccion(coleccionDTO.nombre(), coleccionDTO.descripcion());
    Hecho hecho = new Hecho(hechoDTO.id(),coleccion,hechoDTO.titulo());
    this.hechoRepository.save(hecho);
    return new HechoDTO(hecho.getId(),hecho.getColeccion().getNombre(),hechoDTO.titulo());
  }

  @Override
  public HechoDTO buscarHechoXId(String s) throws NoSuchElementException {
    val hechoOptional = this.hechoRepository.findById(s);
    if ( hechoOptional.isEmpty()){
      throw new NoSuchElementException(s + " no existe");
    }
    Hecho hecho = (Hecho) hechoOptional.get();
    return new HechoDTO(hecho.getId(),hecho.getColeccion().getNombre(),hecho.getTitulo());
  }

  @Override
  public List<HechoDTO> buscarHechosXColeccion(String s) throws NoSuchElementException {
    ColeccionDTO coleccionDTO = buscarColeccionXId(s);
    Coleccion coleccion = new Coleccion(coleccionDTO.nombre(), coleccionDTO.descripcion());
    if (this.hechoRepository.findByColeccion(coleccion).isEmpty()){
      throw new HechoExistException("No existen hechos para la coleccion " + coleccion.getNombre());
    }
    return this.hechoRepository.findByColeccion(coleccion).stream().map(hecho -> new HechoDTO(hecho.getId(),hecho.getColeccion().getNombre(),hecho.getTitulo())).toList();
  }

  @Override
  public void setProcesadorPdI(FachadaProcesadorPdI fachadaProcesadorPdI) {
    this.fachadaProcesadorPdI = fachadaProcesadorPdI;
  }

  @Override
  public PdIDTO agregar(PdIDTO pdIDTO) throws IllegalStateException {
    PdIDTO pdIDTO1 = fachadaProcesadorPdI.procesar(pdIDTO);
    if (this.pdIRepository.findById(pdIDTO1.id()).isPresent()){
      throw new HechoExistException(pdIDTO1.id() + "ya existe");
    }
    Hecho hecho = (Hecho) this.hechoRepository.findById(pdIDTO1.hechoId()).get();
    this.pdIRepository.save(new Pdi(pdIDTO1.id(),hecho));
    return new PdIDTO(pdIDTO1.id(),hecho.getId());
  }

  @Override
  public List<ColeccionDTO> colecciones() {
    return this.coleccionRepository.findAll().stream()
        .map(coleccion -> new ColeccionDTO(coleccion.getNombre(), coleccion.getDescripcion()))
        .toList();
  }

}
