package ar.edi.itn.dds.k3003.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import ar.edu.utn.dds.k3003.app.Fachada;
import ar.edu.utn.dds.k3003.facades.FachadaProcesadorPdI;
import ar.edu.utn.dds.k3003.facades.dtos.CategoriaHechoEnum;
import ar.edu.utn.dds.k3003.facades.dtos.ColeccionDTO;
import ar.edu.utn.dds.k3003.facades.dtos.HechoDTO;
import ar.edu.utn.dds.k3003.facades.dtos.PdIDTO;
import ar.edu.utn.dds.k3003.model.Coleccion;
import ar.edu.utn.dds.k3003.model.Hecho;
import ar.edu.utn.dds.k3003.model.Pdi;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class FachadaTest {
  Fachada fachada;

  @Mock
  private FachadaProcesadorPdI fachadaProcesadorPdI;

  @BeforeEach
  public void setUp() {
    //fachada = new Fachada();
    fachada.setProcesadorPdI(fachadaProcesadorPdI);
  }

  @Test
  void testAgregarColeccion() {
    val coleccion = fachada.agregar(new ColeccionDTO("NombreColeccion", "coleccion"));

    val coleccionXId = fachada.buscarColeccionXId(coleccion.nombre());

    assertEquals("NombreColeccion", coleccionXId.nombre());
  }

  @Test
  @DisplayName("Agregar un pdi a un hecho")
  void testAgregarPdiAHecho() {

    when(fachadaProcesadorPdI.procesar(any(PdIDTO.class))).thenReturn(
        new PdIDTO("1", ""));

    fachada.agregar(new ColeccionDTO("NombreColeccion", "coleccion"));
    val hechoDTO = new HechoDTO("", "NombreColeccion", "TituloHecho", List.of(),
        CategoriaHechoEnum.ENTRETENIMIENTO,
        "bsas", LocalDateTime.now(), "celular");
    val hecho1 = fachada.agregar(hechoDTO);

    fachada.agregar(new PdIDTO("idPdi", hecho1.id()));

  }
}
