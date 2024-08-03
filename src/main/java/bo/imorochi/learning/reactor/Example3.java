package bo.imorochi.learning.reactor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class Example3 implements ReactorExample {

    private static final Logger logger = LoggerFactory.getLogger(Example3.class);

    /**
     * El metodo subscribe tiene otro manera para ejecutar una tarea cuando se completa la subscripcion y es con completeConsumer
     * Runnable un evento que se ejecuto en el doOnComplete utilizando hilos
     *
     * consumer
     * errorConsumer
     * completeConsumer
     */
    @Override
    public void run() {

        Flux<String> nombres4 = Flux.just("Andres", "Pedro", "Maria", "Diego", "Juan")
                .doOnNext(e -> {
                    if (e.isEmpty())
                        throw new RuntimeException("Nombres no pueden ser vacios.");
                    logger.warn("doOnNext - {}",e);
                });


        // implementacion clasica
        /*nombres4.subscribe(e -> logger.info(e),
                error -> logger.error(error.getMessage()),
                new Runnable() {
                    @Override
                    public void run() {
                        logger.info("Ha finalizado la ejecución del observable con exito!");
                    }
                }
        );*/

        // clase anonima
        nombres4.subscribe(
                logger::info,
                error -> logger.error(error.getMessage()),
                () -> logger.info("Ha finalizado la ejecución del observable con exito!")
        );

    }

}
