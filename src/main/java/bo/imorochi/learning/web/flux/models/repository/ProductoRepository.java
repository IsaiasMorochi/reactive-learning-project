package bo.imorochi.learning.web.flux.models.repository;

import bo.imorochi.learning.web.flux.models.documents.Producto;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ProductoRepository extends ReactiveMongoRepository<Producto, String> {
}
