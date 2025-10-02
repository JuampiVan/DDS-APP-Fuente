package ar.edu.utn.dds.k3003.controller;


import ar.edu.utn.dds.k3003.DTOs.HechoDTO;
import ar.edu.utn.dds.k3003.DTOs.PdIDTOEnviado;
import ar.edu.utn.dds.k3003.DTOs.PdiDTORecibido;
import ar.edu.utn.dds.k3003.app.Fachada;
import ar.edu.utn.dds.k3003.model.Hecho;
import ar.edu.utn.dds.k3003.repository.HechoRepository;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(value = "/hecho", produces = MediaType.APPLICATION_JSON_VALUE)
public class HechoController {

  private final Fachada fachadaFuente;
  private final HechoRepository hechoRepository;

  @Autowired
  public HechoController(Fachada fachadaFuente, HechoRepository hechoRepository) {
    this.fachadaFuente = fachadaFuente;
    this.hechoRepository = hechoRepository;
  }


  @PostMapping
  @Counted(value = "http.post.hecho.count", description = "Cantidad de requests POST a /hecho")
  @Timed(value = "http.post.hecho.latency", description = "Tiempo de ejecución de requests POST a /hecho")
  public ResponseEntity<HechoDTO> crearHecho(@RequestBody HechoDTO hecho) {
    return ResponseEntity.ok(fachadaFuente.agregar(hecho));
  }

  @GetMapping("/{id}")
  @Counted(value = "http.get.hecho.count", description = "Cantidad de requests GET a /hecho/{id}")
  @Timed(value = "http.get.hecho.latency", description = "Tiempo de ejecución de requests GET a /hecho/{id}")
  public ResponseEntity<HechoDTO> listarHechos(@PathVariable String id) {
    return ResponseEntity.ok(fachadaFuente.buscarHechoXId(id));
  }

  @PostMapping("/pdi")
  public ResponseEntity<PdiDTORecibido> agregarPdiAHecho(@RequestBody PdIDTOEnviado pdIDTO) throws IOException, IOException {
      return ResponseEntity.ok(fachadaFuente.agregar(pdIDTO));
  }

  @PatchMapping("/{id}")
  @Counted(value = "http.patch.hecho.count", description = "Cantidad de requests PATCH a /hecho/{id}")
  @Timed(value = "http.patch.hecho.latency", description = "Tiempo de ejecución de requests PATCH a /hecho/{id}")
  public ResponseEntity<Hecho> modificarHecho(@PathVariable String id, @RequestBody HechoDTO patch) {

    return hechoRepository.findById(id)
        .map(hecho -> {
          if (patch.getTitulo() != null) {
            hecho.setTitulo(patch.getTitulo());
          }
          if (patch.getEtiquetas() != null) {
            hecho.setEtiquetas(patch.getEtiquetas());
          }
          if (patch.getCategoria() != null) {
            hecho.setCategoria(patch.getCategoria());
          }
          if (patch.getUbicacion() != null) {
            hecho.setUbicacion(patch.getUbicacion());
          }
          if (patch.getFecha() != null) {
            hecho.setFecha(patch.getFecha());
          }
          if (patch.getOrigen() != null) {
            hecho.setOrigen(patch.getOrigen());
          }
          return ResponseEntity.ok(hechoRepository.save(hecho));
        })
        .orElse(ResponseEntity.notFound().build());
  }

}
