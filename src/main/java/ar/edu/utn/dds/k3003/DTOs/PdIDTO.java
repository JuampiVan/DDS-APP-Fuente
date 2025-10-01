package ar.edu.utn.dds.k3003.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public record PdIDTO(
        String id,
        String hechoId,
        String descripcion,
        String lugar,
        String momento,
        String contenido,
        String urlImagen,
        String ocrResultado,
        List<String> etiquetas
) {

    public PdIDTO(String id,String hechoId) {
        this(id,hechoId, null, null, null, null, null, null, Collections.emptyList());
    }
}
