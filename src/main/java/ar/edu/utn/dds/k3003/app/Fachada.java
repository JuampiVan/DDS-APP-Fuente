package ar.edu.utn.dds.k3003.app;

import ar.edu.utn.dds.k3003.DTOs.ColeccionDTO;
import ar.edu.utn.dds.k3003.DTOs.HechoDTO;
import ar.edu.utn.dds.k3003.DTOs.PdIDTOEnviado;
import ar.edu.utn.dds.k3003.DTOs.PdiDTORecibido;
import ar.edu.utn.dds.k3003.model.Coleccion;
import ar.edu.utn.dds.k3003.model.Hecho;
import ar.edu.utn.dds.k3003.model.exceptions.HechoExistException;
import ar.edu.utn.dds.k3003.repository.ColeccionRepository;
import ar.edu.utn.dds.k3003.repository.HechoRepository;
import ar.edu.utn.dds.k3003.repository.InMemoryColeccionRepo;
import ar.edu.utn.dds.k3003.repository.InMemoryHechoRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class Fachada {

  private ColeccionRepository coleccionRepository;
  private HechoRepository hechoRepository;
  private ProcesadorPdiProxy procesadorPdI;
  private boolean estaSuscrito;

   protected Fachada() {
    this.coleccionRepository = new InMemoryColeccionRepo();
    this.hechoRepository = new InMemoryHechoRepo();
  }

  @Autowired
  public Fachada(ColeccionRepository coleccionRepository, HechoRepository hechoRepository) {
    this.coleccionRepository = coleccionRepository;
    this.hechoRepository = hechoRepository;
      this.procesadorPdI = new ProcesadorPdiProxy(new ObjectMapper());
  }

    public boolean isEstaSuscrito() {
        return estaSuscrito;
    }

    public void setEstaSuscrito(boolean estaSuscrito) {
        this.estaSuscrito = estaSuscrito;
    }

    public ColeccionDTO agregar(ColeccionDTO coleccionDTO) {
    if (this.coleccionRepository.findById(coleccionDTO.getNombre()).isPresent()){
      throw new IllegalArgumentException(coleccionDTO.getNombre() + "ya existe");
    }
    Coleccion coleccion = new Coleccion(coleccionDTO.getNombre(), coleccionDTO.getDescripcion());
    this.coleccionRepository.save(coleccion);
    return new ColeccionDTO(coleccion.getNombre(),coleccion.getDescripcion());
  }

  public ColeccionDTO buscarColeccionXId(String s) throws NoSuchElementException {
    Optional<Coleccion> coleccionOptional = this.coleccionRepository.findById(s);
    if ( coleccionOptional.isEmpty()){
      throw new NoSuchElementException(s + "no existe");
    }
    Coleccion coleccion = coleccionOptional.get();
    return new ColeccionDTO(coleccion.getNombre(),coleccion.getDescripcion());
  }

  public HechoDTO agregar(HechoDTO hechoDTO) {
    if (this.hechoRepository.findById(hechoDTO.getId()).isPresent()){
      throw new HechoExistException(hechoDTO.getId() + "ya existe");
    }
    ColeccionDTO coleccionDTO = buscarColeccionXId(hechoDTO.getNombreColeccion());
    Coleccion coleccion = new Coleccion(coleccionDTO.getNombre(), coleccionDTO.getDescripcion());
    Hecho hecho = new Hecho(hechoDTO.getId(),coleccion,hechoDTO.getTitulo());
    this.hechoRepository.save(hecho);
    return new HechoDTO(hecho.getId(),hecho.getColeccion().getNombre(),hechoDTO.getTitulo());
  }

  public HechoDTO buscarHechoXId(String s) throws NoSuchElementException {
    Optional<Hecho> hechoOptional = this.hechoRepository.findById(s);
    if ( hechoOptional.isEmpty()){
      throw new NoSuchElementException(s + " no existe");
    }
    Hecho hecho = hechoOptional.get();
    return new HechoDTO(hecho.getId(),hecho.getColeccion().getNombre(),hecho.getTitulo());
  }

  public List<HechoDTO> buscarHechosXColeccion(String s) throws NoSuchElementException {
    ColeccionDTO coleccionDTO = buscarColeccionXId(s);
    Coleccion coleccion = new Coleccion(coleccionDTO.getNombre(), coleccionDTO.getDescripcion());
    if (this.hechoRepository.findByColeccion(coleccion).isEmpty()){
      throw new HechoExistException("No existen hechos para la coleccion " + coleccion.getNombre());
    }
    return this.hechoRepository.findByColeccion(coleccion).stream().map(hecho -> new HechoDTO(hecho.getId(),hecho.getColeccion().getNombre(),hecho.getTitulo())).toList();
  }



  public PdiDTORecibido agregar(PdIDTOEnviado pdIDTOEnviado) throws IllegalStateException, IOException {
    PdiDTORecibido pdiPosteado = procesadorPdI.procesar(pdIDTOEnviado);

    Hecho hecho = (Hecho) this.hechoRepository.findById(pdiPosteado.getHechoId()).get();
    return new PdiDTORecibido(pdiPosteado.getId(),hecho.getId(),pdiPosteado.getDescripcion(),pdiPosteado.getLugar(),pdiPosteado.getMomento(),pdiPosteado.getContenido(),pdiPosteado.getUrlImagen(),pdiPosteado.getOcrResultado() ,pdiPosteado.getEtiquetas());
  }

  public List<ColeccionDTO> colecciones() {
    return this.coleccionRepository.findAll().stream()
        .map(coleccion -> new ColeccionDTO(coleccion.getNombre(), coleccion.getDescripcion()))
        .toList();
  }

  public void agregarHechoDesdeMensajeria(HechoDTO hechoDTO) {
       if (this.estaSuscrito){
           this.agregar(hechoDTO);
       }
  }

    public List<HechoDTO> hechos() {
        return this.hechoRepository.findAll().stream()
                .map(hecho -> new HechoDTO(hecho.getId(), hecho.getColeccion().getNombre(), hecho.getTitulo()))
                .toList();
    }

}
