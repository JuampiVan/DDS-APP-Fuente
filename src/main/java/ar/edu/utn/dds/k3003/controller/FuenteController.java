package ar.edu.utn.dds.k3003.controller;

import ar.edu.utn.dds.k3003.app.Fachada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/fuente", produces = MediaType.APPLICATION_JSON_VALUE)
public class FuenteController {

    private final Fachada fachadaFuente;

    @Autowired
    public FuenteController(Fachada fachadaFuente) {
        this.fachadaFuente = fachadaFuente;
    }

    @PatchMapping("/suscribirse")
    public ResponseEntity<String> suscribirse(){
        fachadaFuente.setEstaSuscrito(true);
        return ResponseEntity.ok("Se suscribio a la mensajeria correctamente");
    }

    @PatchMapping("/desuscribirse")
    public ResponseEntity<String> desuscribirse(){
        fachadaFuente.setEstaSuscrito(false);
        return ResponseEntity.ok("Se desuscribio a la mensajeria correctamente");
    }

}
