package bo.imorochi.learning.reactor;

import bo.imorochi.learning.reactor.model.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class Example4 implements ReactorExample {

    private static final Logger logger = LoggerFactory.getLogger(Example4.class);

    /**
     * Operador Map
     * Todas las transformaciones realizadas con los operadores no modifican el flujo original, retornan
     * una nueva instancia de otro flux a partir del original con los datos modificados
     */
    @Override
    public void run() {

        // Importante el orden por que se respeta el orden de ejecucion
        /*Flux<String> nombres = Flux.just("Andres", "Pedro", "Maria", "Diego", "Juan")
                .map(String::toUpperCase)
                .doOnNext(e -> {
                    if (e.isEmpty())
                        throw new RuntimeException("Nombres no pueden ser vacios.");
                    logger.warn("doOnNext - {}", e);
                })
                .map(nombre -> nombre.toLowerCase());

        nombres.subscribe(
                e -> logger.info("subscribe - {}",e),
                error -> logger.error(error.getMessage()),
                () -> logger.info("Ha finalizado la ejecucion del observable con exito!")
        );*/

        Flux<Usuario> nombres5 = Flux.just("Andres", "Pedro", "Maria", "Diego", "Juan")
                .map(nombre -> new Usuario(nombre.toUpperCase(), null))
                .doOnNext(usuario -> {
                    if (usuario == null)
                        throw new RuntimeException("Nombres no pueden ser vacios.");
                    logger.warn("doOnNext - {}", usuario.getNombre());
                })
                .map(usuario -> {
                    usuario.setNombre(usuario.getNombre().toLowerCase());
                    return usuario;
                });

        nombres5.subscribe(
                e -> logger.info("subscribe - {}", e.getNombre()),
                error -> logger.error(error.getMessage()),
                () -> logger.info("Ha finalizado la ejecucion del observable con exito!")
        );

    }
}
