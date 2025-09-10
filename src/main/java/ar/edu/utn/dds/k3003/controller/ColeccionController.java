package ar.edu.utn.dds.k3003.controller;

import ar.edu.utn.dds.k3003.facades.FachadaFuente;
import ar.edu.utn.dds.k3003.facades.dtos.ColeccionDTO;
import ar.edu.utn.dds.k3003.facades.dtos.HechoDTO;
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

  private final FachadaFuente fachadaFuente;

  @Autowired
  public ColeccionController(FachadaFuente fachadaFuente) {this.fachadaFuente = fachadaFuente;}

  @GetMapping()
  public ResponseEntity<List<ColeccionDTO>> listarColecciones() {
    return ResponseEntity.ok(fachadaFuente.colecciones());
  }

  @GetMapping("/{nombre}")
  public ResponseEntity<ColeccionDTO> obtenerColeccion(@PathVariable String nombre) {
    return ResponseEntity.ok(fachadaFuente.buscarColeccionXId(nombre));
  }

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ColeccionDTO> crearColeccion(@RequestBody ColeccionDTO coleccion) {
    return ResponseEntity.ok(fachadaFuente.agregar(coleccion));
  }

  @GetMapping("/{nombre}/hechos")
  public ResponseEntity<List<HechoDTO>> obtenerHechosXColeccion(@PathVariable String nombre) {
    return ResponseEntity.ok(fachadaFuente.buscarHechosXColeccion(nombre));
  }


}
