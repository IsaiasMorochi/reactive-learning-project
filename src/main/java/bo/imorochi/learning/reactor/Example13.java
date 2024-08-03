package bo.imorochi.learning.reactor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class Example13 implements ReactorExample {

    private static final Logger logger = LoggerFactory.getLogger(Example12.class);

    @Override
    public void run() {

        Flux<Integer> rango = Flux.range(1, 12);
        Flux<Long> retraso = Flux.interval(Duration.ofSeconds(1));

        //se ejecuta en 2do plano pero no se visualiza,
        rango.zipWith(retraso, (ra, re) -> ra)
                .doOnNext(i -> logger.info("doOnNext #1 - {}", i.toString()))
                .subscribe();

        //no se recomienda pero con fines de aprendizaje lo visualizamos con bloqueo
        rango.zipWith(retraso, (ra, re) -> ra)
                .doOnNext(i -> logger.info("doOnNext #2 - {}", i.toString()))
                .blockLast();
    }
}
