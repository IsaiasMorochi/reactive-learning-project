package bo.imorochi.learning.reactor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;

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

        try {
            intervalDelay();

            intervalInfinite();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void intervalDelay() throws InterruptedException {
        Flux<Integer> rango = Flux.range(1, 12)
                .delayElements(Duration.ofSeconds(1))
                .doOnNext(i -> logger.info(i.toString()));

        rango.subscribe();
        Thread.sleep(13000); //con fin de visualizar, retrasamos la finalizacion del programa

        //rango.blockLast();//bloqueo solo para visualizar como se emite los eventos

    }

    private void intervalInfinite() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Flux.interval(Duration.ofSeconds(1))
                .doOnTerminate(() -> latch.countDown()) //se ejecuta falle o no falle
                .flatMap(i -> {
                    if (i >= 5) {
                        return Flux.error(new InterruptedException("Solo hasta 5!"));
                    }
                    return Flux.just(i);
                })
                .map(i -> "Hola " + i)
                .retry(2)
                .subscribe(
                        s -> logger.info(s),
                        e -> logger.error(e.getMessage()));

        latch.await(); //libera cuando el latch decremente
    }

}
