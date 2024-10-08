package bo.imorochi.learning;

import bo.imorochi.learning.web.flux.models.documents.Producto;
import bo.imorochi.learning.web.flux.models.repository.ProductoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import reactor.core.publisher.Flux;

import java.util.Date;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ReactiveMongoOperations reactiveMongoOperations;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {

		/*ReactorExample example = new Example15();
		example.run();*/

        reactiveMongoOperations.dropCollection("productos").subscribe();

        Flux.just(new Producto("TV Panasonic Pantalla LCD", 456.89),
                        new Producto("Sony Camara HD Digital", 177.89),
                        new Producto("Apple iPod", 46.89),
                        new Producto("Sony Notebook", 846.89),
                        new Producto("Hewlett Packard Multifuncional", 200.89),
                        new Producto("Bianchi Bicicleta", 70.89),
                        new Producto("HP Notebook Omen 17", 2500.89),
                        new Producto("Mica Cómoda 5 Cajones", 150.89),
                        new Producto("TV Sony Bravia OLED 4K Ultra HD", 2255.89)
                )
                .flatMap(producto -> {
                    producto.setCreateAt(new Date());
                    return productoRepository.save(producto);
                })
                .subscribe(producto -> logger.info("Insert: {} {}", producto.getId(), producto.getNombre()));

    }

}
