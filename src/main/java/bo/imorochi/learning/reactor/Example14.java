package bo.imorochi.learning.reactor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.Timer;
import java.util.TimerTask;

public class Example14 implements ReactorExample {

    private static final Logger logger = LoggerFactory.getLogger(Example14.class);

    /**
     * Crear un observable flux
     * emitter
     */
    @Override
    public void run() {
        Flux.create(emitter -> {
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        private Integer contador = 0;

                        @Override
                        public void run() {
                            emitter.next(++contador);
                            if (contador == 10) {
                                timer.cancel();
                                emitter.complete();
                            }
                            if (contador == 5) {
                                timer.cancel();
                                emitter.error(new InterruptedException("Error, se ha detenido el flux en 5!"));
                            }
                        }
                    }, 1000, 1000);
                })
//                .doOnNext(next -> logger.info(next.toString()))
//                .doOnComplete(() -> logger.info("Hemos terminado!!"))
                .subscribe(next -> logger.info(next.toString()),
                        error -> logger.error(error.getMessage()),
                        () -> logger.info("Hemos terminado!!") //onComplete solo se ejecuta cuando finalizo con exito
                );
    }
}
