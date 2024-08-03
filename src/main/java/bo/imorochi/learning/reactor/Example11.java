package bo.imorochi.learning.reactor;

import bo.imorochi.learning.reactor.model.Comentarios;
import bo.imorochi.learning.reactor.model.Usuario;
import bo.imorochi.learning.reactor.model.UsuarioComentarioDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class Example11 implements ReactorExample {

    private static final Logger logger = LoggerFactory.getLogger(Example11.class);

    /**
     * Operador zipWith
     * Combina flujos
     */
    @Override
    public void run() {

        Mono<Usuario> usuarioMono = Mono.fromCallable(() -> new Usuario("Jhon", "Down"));

        Mono<Comentarios> comentariosMono = Mono.fromCallable(() -> {
            Comentarios comentarios = new Comentarios();
            comentarios.addComentario("Hola como..");
            comentarios.addComentario("Bye como..");
            comentarios.addComentario("No me gusta..");
            return comentarios;
        });

        Mono<UsuarioComentarioDto> newUC = usuarioMono
                .zipWith(comentariosMono, (usuario, comentarios) -> new UsuarioComentarioDto(usuario, comentarios));

        newUC.subscribe(uc -> logger.info("subscribe -> {}", uc));
    }
}
