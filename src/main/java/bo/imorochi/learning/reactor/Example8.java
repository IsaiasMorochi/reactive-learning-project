package bo.imorochi.learning.reactor;

import bo.imorochi.learning.reactor.model.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class Example8 implements ReactorExample{

    private static final Logger logger = LoggerFactory.getLogger(Example8.class);

    /**
     * Operador flatMap
     * parecido a map, pero map tiene datos normales y no observables como lo hace flapMap
     */
    @Override
    public void run() {

        List<String> usuariosList = List.of("Andres Cala", "Pedro Cala", "Diego Lopez", "Diego Cala", "Juan Cala");

        Flux.fromIterable(usuariosList)
                .map(nombre -> new Usuario(nombre.split(" ")[0].toUpperCase(), nombre.split(" ")[1].toUpperCase()))
                .flatMap(usuario -> {
                    if (usuario.getNombre().equalsIgnoreCase("diego")) {
                        return Mono.just(usuario);
                    } else {
                        return Mono.empty();
                    }
                })
                .map(usuario -> {
                    usuario.setNombre(usuario.getNombre().toLowerCase());
                    return usuario;
                })
                .subscribe(usuario -> logger.info("subscribe - {}", usuario)
                );
    }

}
