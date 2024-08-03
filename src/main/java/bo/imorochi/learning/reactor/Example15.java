package bo.imorochi.learning.reactor;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class Example15 implements ReactorExample {

    private static final Logger logger = LoggerFactory.getLogger(Example15.class);

    /**
     * Contrapresion
     * El poder indicar el subcriptor al productor la cantidad de elementos que puede enviar por cada iteracion.
     * <p>
     * Es cuando el flujo subcriptor le puede solicitar al productor que el envie menos datos para evitar una
     * sobrecarga debido a una limitante de recursos etc.
     */
    @Override
    public void run() {


        Flux.range(1, 10)
                .log()
                .subscribe(new Subscriber<Integer>() {
                    private Subscription subscription;
                    private Integer limite = 2;
                    private Integer consumido = 0;

                    @Override
                    public void onSubscribe(Subscription subscription) {
                        this.subscription = subscription;
//                        subscription.request(Long.MAX_VALUE);
                        subscription.request(limite); //simulando procesamiento por lotes
                    }

                    @Override
                    public void onNext(Integer integer) {
                        logger.info(integer.toString());
                        consumido++;
                        if (consumido == limite) {
                            consumido = 0;
                            subscription.request(limite);
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        logger.info("onError");
                    }

                    @Override
                    public void onComplete() {
                        logger.info("onComplete");
                    }
                });

        logger.info("*************");

        Flux.range(1, 10)
                .log()
                .limitRate(2)
                .subscribe(i -> logger.info(i.toString()));

    }
}
