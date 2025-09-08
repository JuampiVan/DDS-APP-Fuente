package ar.edu.utn.dds.k3003.controller;

import ar.edu.utn.dds.k3003.facades.FachadaFuente;
import ar.edu.utn.dds.k3003.facades.dtos.ColeccionDTO;
import ar.edu.utn.dds.k3003.facades.dtos.HechoDTO;
import ar.edu.utn.dds.k3003.model.Hecho;
import ar.edu.utn.dds.k3003.repository.HechoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/hecho")
public class HechoController {

  private final FachadaFuente fachadaFuente;
  private final HechoRepository hechoRepository;

  @Autowired
  public HechoController(FachadaFuente fachadaFuente, HechoRepository hechoRepository) {
    this.fachadaFuente = fachadaFuente;
    this.hechoRepository = hechoRepository;
  }

  @PostMapping
  public ResponseEntity<HechoDTO> crearHecho(@RequestBody HechoDTO hecho) {
    return ResponseEntity.ok(fachadaFuente.agregar(hecho));
  }

  @GetMapping("/{id}")
  public ResponseEntity<HechoDTO> listarHechos(@PathVariable String id) {
    return ResponseEntity.ok(fachadaFuente.buscarHechoXId(id));
  }

  @PatchMapping("/{id}")
  public ResponseEntity<Hecho> modificarHecho(@PathVariable String id, @RequestBody HechoDTO patch) {

    return hechoRepository.findById(id)
        .map(hecho -> {
          if (patch.titulo() != null) {
            hecho.setTitulo(patch.titulo());
          }
          if (patch.etiquetas() != null) {
            hecho.setEtiquetas(patch.etiquetas());
          }
          if (patch.categoria() != null) {
            hecho.setCategoria(patch.categoria());
          }
          if (patch.ubicacion() != null) {
            hecho.setUbicacion(patch.ubicacion());
          }
          if (patch.fecha() != null) {
            hecho.setFecha(patch.fecha());
          }
          if (patch.origen() != null) {
            hecho.setOrigen(patch.origen());
          }
          return ResponseEntity.ok(hechoRepository.save(hecho));
        })
        .orElse(ResponseEntity.notFound().build());
  }

}
