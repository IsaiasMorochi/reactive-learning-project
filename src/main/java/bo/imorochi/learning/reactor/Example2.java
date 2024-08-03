package bo.imorochi.learning.reactor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class Example2 implements ReactorExample {

    private static final Logger logger = LoggerFactory.getLogger(Example2.class);

    /**
     * subscribe puede consumir cualquier metodo que emite doOnNext
     * se suscribe en el flujo de evento doOnNext
     * logger::info forma abreviada para ejecutar un metodo del jdk8
     * Tomar en cuenta al generar una excepcion se termina la ejecucion del flujo
     */
    @Override
    public void run() {

        Flux<String> nombres = Flux.just("Andres", "Pedro", "Diego", "Juan")
                .doOnNext(logger::info);

        nombres.subscribe(elemento -> logger.info("Print to subscribe del metodo  doOnNext ->  {}", elemento));

        logger.info("****************");

        //3.errorConsumer
        Flux<String> nombres3 = Flux.just("Andres", "Pedro", "", "Diego", "Juan")
                .doOnNext(e -> {
                    if (e.isEmpty())
                        throw new RuntimeException("Nombres no pueden ser vacios.");
                    logger.warn(e);
                });

        nombres3.subscribe(
                logger::info,
                error -> logger.error(error.getMessage()
                )
        );
    }
}
