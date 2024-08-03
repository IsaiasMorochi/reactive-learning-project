package bo.imorochi.learning.reactor;

import bo.imorochi.learning.reactor.model.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.List;

public class Example9 implements ReactorExample {

    private static final Logger logger = LoggerFactory.getLogger(Example9.class);

    @Override
    public void run() {
        List<Usuario> usuariosList = List.of(
                new Usuario("Andres", "Cala"),
                new Usuario("Pedro", "Cala"),
                new Usuario("Diego", "Lopez"),
                new Usuario("Diego", "Cala"),
                new Usuario("Juan", "Cala")
        );

        // lista completa
        Flux.fromIterable(usuariosList)
                .collectList()
                .subscribe(list -> logger.info(list.toString()));

        // lista iterable
        Flux.fromIterable(usuariosList)
                .collectList()
                .subscribe(list -> list.forEach(item -> logger.info(item.toString())));
    }
}
