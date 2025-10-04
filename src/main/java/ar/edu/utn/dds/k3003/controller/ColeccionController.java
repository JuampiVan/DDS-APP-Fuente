package ar.edu.utn.dds.k3003.controller;

import ar.edu.utn.dds.k3003.DTOs.ColeccionDTO;
import ar.edu.utn.dds.k3003.DTOs.HechoDTO;
import ar.edu.utn.dds.k3003.app.Fachada;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping(value = "/colecciones", produces = MediaType.APPLICATION_JSON_VALUE)
public class ColeccionController {

  private final Fachada fachadaFuente;
  private final MeterRegistry registry;

  @Autowired
  public ColeccionController(Fachada fachadaFuente, MeterRegistry registry) {
      this.fachadaFuente = fachadaFuente;
      this.registry = registry;
  }

  @GetMapping()
  @Timed(value = "coleccion.get.time", description = "Tiempo de respuesta de GET /coleccion")
  public ResponseEntity<List<ColeccionDTO>> listarColecciones() {
    registry.counter("coleccion.get.count").increment();
    return ResponseEntity.ok(fachadaFuente.colecciones());
  }

  @GetMapping("/{nombre}")
  public ResponseEntity<ColeccionDTO> obtenerColeccion(@PathVariable String nombre) {
    return ResponseEntity.ok(fachadaFuente.buscarColeccionXId(nombre));
  }

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  @Timed(value = "coleccion.post.time", description = "Tiempo de respuesta de POST /coleccion")
  public ResponseEntity<ColeccionDTO> crearColeccion(@RequestBody ColeccionDTO coleccion) {
    registry.counter("coleccion.post.count").increment();
    return ResponseEntity.ok(fachadaFuente.agregar(coleccion));
  }

  @GetMapping("/{nombre}/hechos")
  @Counted(value = "http.get.coleccionhechos.count", description = "Cantidad de requests GET a /coleccion/hechos")
  @Timed(value = "http.get.coleccionhechos.latency", description = "Tiempo de ejecución de requests GET a /coleccion/hechos")
  public ResponseEntity<List<HechoDTO>> obtenerHechosXColeccion(@PathVariable String nombre) {
    return ResponseEntity.ok(fachadaFuente.buscarHechosXColeccion(nombre));
  }


}
