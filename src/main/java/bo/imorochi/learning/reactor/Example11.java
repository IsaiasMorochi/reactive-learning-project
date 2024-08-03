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

        // Forma 1
        Mono<UsuarioComentarioDto> newUC1 = usuarioMono
                .zipWith(comentariosMono, (usuario, comentarios) -> new UsuarioComentarioDto(usuario, comentarios));

        newUC1.subscribe(uc -> logger.info("subscribe #1 -> {}", uc));

        // Forma 2
        Mono<UsuarioComentarioDto> newUC2 = usuarioMono
                .zipWith(comentariosMono)
                .map(tuple -> {
                    Usuario u = tuple.getT1();
                    Comentarios c = tuple.getT2();
                    return new UsuarioComentarioDto(u, c);
                });

        newUC2.subscribe(uc -> logger.info("subscribe #2 -> {}", uc));
    }
}
