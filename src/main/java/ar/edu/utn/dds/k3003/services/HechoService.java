package ar.edu.utn.dds.k3003.services;

import ar.edu.utn.dds.k3003.model.Hecho;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;

import java.util.List;

public class HechoService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Hecho> buscar(String texto) {
        TextCriteria criteria = TextCriteria.forDefaultLanguage().matching(texto);
        Query query = TextQuery.queryText(criteria);
        return mongoTemplate.find(query, Hecho.class);
    }

    public List<Hecho> buscarAvanzado(String texto, String etiqueta) {

        TextCriteria criteria = TextCriteria.forDefaultLanguage().matching(texto);
        Query query = new Query(criteria);

        if (etiqueta != null) {
            query.addCriteria(Criteria.where("etiquetas").is(etiqueta));
        }

        return mongoTemplate.find(query, Hecho.class);
    }
}
