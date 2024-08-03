package bo.imorochi.learning.reactor;

import bo.imorochi.learning.reactor.model.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class Example5 implements ReactorExample {

    private static final Logger logger = LoggerFactory.getLogger(Example5.class);

    /**
     * Operador Filter
     * Evalua una expresion booleana
     */
    @Override
    public void run() {

        Flux<Usuario> nombres = Flux.just("Andres Cala", "Pedro Cala", "Diego Lopez", "Diego Cala", "Juan Cala")
                .map(nombre -> new Usuario(nombre.split(" ")[0].toUpperCase(), nombre.split(" ")[1].toUpperCase()))
                .filter(usuario -> usuario.getNombre().equalsIgnoreCase("Diego"))
                .doOnNext(usuario -> {
                    if (usuario == null)
                        throw new RuntimeException("Nombres no pueden ser vacios.");
                    logger.warn("doOnNext - {}", usuario.getNombre().concat(" ").concat(usuario.getApellido()));
                })
                .map(usuario -> {
                    usuario.setNombre(usuario.getNombre().toLowerCase());
                    return usuario;
                });

        nombres.subscribe(usuario -> logger.info("subscribe - {}", usuario.getNombre().concat(" ").concat(usuario.getApellido())),
                error -> logger.error(error.getMessage()),
                () -> logger.info("Ha finalizado la ejecucion del observable con exito!")
        );

    }
}
