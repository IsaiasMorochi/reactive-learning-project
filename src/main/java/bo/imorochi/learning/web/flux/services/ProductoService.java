package bo.imorochi.learning.web.flux.services;

import bo.imorochi.learning.web.flux.models.documents.Producto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductoService {

    Flux<Producto> findAll();
    Flux<Producto> findAllWithNameUpperCase();
    Flux<Producto> findAllWithNameUpperCaseRepeat();
    Mono<Producto> findById(String id);
    Mono<Producto> save(Producto producto);
    Mono<Void> deleteById(String id);

}
