package bo.imorochi.learning.reactor;

import bo.imorochi.learning.reactor.model.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class Example6 implements ReactorExample {

    private static final Logger logger = LoggerFactory.getLogger(Example6.class);

    @Override
    public void run() {

        Flux<String> nombres = Flux.just("Andres Cala", "Pedro Cala", "Diego Lopez", "Diego Cala", "Juan Cala");

        //2d o genera un nuevo flujo, no modifica al 1e r flujo
        Flux<Usuario> usuarios = nombres.map(nombre -> new Usuario(nombre.split(" ")[0].toUpperCase(), nombre.split(" ")[1].toUpperCase()))
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

        //imprimir informacion del flujo, cada flujo es diferente
        usuarios.subscribe(
                usuario -> logger.info("subscribe - {}", usuario),
                error -> logger.error(error.getMessage()),
                () -> logger.info("Ha finalizado la ejecucion del observable con exito!")
        );

    }
}
