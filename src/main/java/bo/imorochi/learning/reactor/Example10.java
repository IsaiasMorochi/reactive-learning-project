package bo.imorochi.learning.reactor;

import bo.imorochi.learning.reactor.model.Comentarios;
import bo.imorochi.learning.reactor.model.Usuario;
import bo.imorochi.learning.reactor.model.UsuarioComentarioDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class Example10 implements ReactorExample {

    private static final Logger logger = LoggerFactory.getLogger(Example10.class);

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

        usuarioMono.flatMap(usuario -> comentariosMono
                        .map(comentario -> new UsuarioComentarioDto(usuario, comentario))
                )
                .subscribe(uc -> logger.info("subscribe - {}", uc));

    }
}
