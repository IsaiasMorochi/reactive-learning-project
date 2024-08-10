package bo.imorochi.learning.web.flux.services;

import bo.imorochi.learning.web.flux.models.documents.Producto;
import bo.imorochi.learning.web.flux.models.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductoServiceAdapter implements ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoServiceAdapter(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    public Flux<Producto> findAll() {
        return productoRepository.findAll();
    }

    @Override
    public Flux<Producto> findAllWithNameUpperCase() {
        return productoRepository.findAll()
                .map(producto -> {
                    producto.setNombre(producto.getNombre().toUpperCase());
                    return producto;
                });
    }

    @Override
    public Flux<Producto> findAllWithNameUpperCaseRepeat() {
        return findAllWithNameUpperCase().repeat(500);
    }

    @Override
    public Mono<Producto> findById(String id) {
        return productoRepository.findById(id);
    }

    @Override
    public Mono<Producto> save(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return productoRepository.deleteById(id);
    }

}
