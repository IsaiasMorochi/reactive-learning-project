package bo.imorochi.learning.reactor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class Example12 implements ReactorExample {

    private static final Logger logger = LoggerFactory.getLogger(Example12.class);

    /**
     * Operador Range
     */
    @Override
    public void run() {

        Flux<Integer> rangos = Flux.range(0, 4);

        Flux.just(1, 2, 3, 4)
                .map(i -> (i * 2))
                .zipWith(rangos, (uno, dos) -> String.format("1er flux: %d - 2do flux: %d", uno, dos))
                .subscribe(texto -> logger.info("subscribe - {}", texto));

    }

}
