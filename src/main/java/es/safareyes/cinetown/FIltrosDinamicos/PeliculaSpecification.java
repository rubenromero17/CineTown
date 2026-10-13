package es.safareyes.cinetown.FIltrosDinamicos;

import es.safareyes.cinetown.modelos.Pelicula;
import org.springframework.data.jpa.domain.Specification;

public class PeliculaSpecification {
    public static Specification<Pelicula> conTitulo(String titulo) {
        return (root, query, cb) ->
                (titulo == null || titulo.isBlank())
                        ? cb.conjunction()
                        : cb.like(cb.lower(root.get("titulo")), "%" + titulo.toLowerCase() + "%");
    }

    public static Specification<Pelicula> conClasificacion(String clasificacion) {
        return (root, query, cb) ->
                (clasificacion == null || clasificacion.isBlank())
                        ? cb.conjunction()
                        : cb.equal(root.get("clasificacionEdad"), clasificacion);
    }

    public static Specification<Pelicula> estaPublicada(Boolean publicada) {
        return (root, query, cb) ->
                (publicada == null)
                        ? cb.conjunction()
                        : cb.equal(root.get("publicada"), publicada);
    }

    public static Specification<Pelicula> conGenero(Long idGenero) {
        return (root, query, cb) -> {
            if (idGenero == null) return cb.conjunction();
            query.distinct(true);
            return cb.equal(root.join("generos").get("idGenero"), idGenero);
        };
    }
}
