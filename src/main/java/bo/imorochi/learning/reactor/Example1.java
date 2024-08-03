package bo.imorochi.learning.reactor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class Example1 implements ReactorExample {

    private static final Logger logger = LoggerFactory.getLogger(Example1.class);

    /**
     * Flux es un publisher por lo tanto un observable
     * doOnNext se ejecuta cada ves que el observador notifica que llego un nuevo elemento y realiza una tarea
     */
    @Override
    public void run() {

        Flux<String> nombres = Flux.just("Andres", "Pedro", "Diego", "Juan")
                .doOnNext(elemento -> logger.info("Print to doOnNext -> {}", elemento));

        nombres.subscribe(elemento -> logger.info("Print to subscribe ->  {}", elemento));

        // ejecuta con  callable
        Flux<String> nombresOtros = Flux.just("Andres", "Pedro", "Diego", "Juan")
                .doOnNext(logger::info);

        nombresOtros.subscribe();

    }

}
