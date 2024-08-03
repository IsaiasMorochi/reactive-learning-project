package bo.imorochi.learning.reactor;

import bo.imorochi.learning.reactor.model.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.List;

public class Example7 implements ReactorExample {

    private static final Logger logger = LoggerFactory.getLogger(Example7.class);

    /**
     * Creando Flux a partir de de un Iterable
     */
    @Override
    public void run() {

        List<String> usuariosList = List.of("Andres Cala", "Pedro Cala", "Diego Lopez", "Diego Cala", "Juan Cala");

        Flux<String> nombres = Flux.fromIterable(usuariosList);

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

        usuarios.subscribe(
                usuario -> logger.info("subscribe - {}", usuario.toString()),
                error -> logger.error(error.getMessage()),
                () -> logger.info("Ha finalizado la ejecucion del observable con exito!")
        );
    }
}
