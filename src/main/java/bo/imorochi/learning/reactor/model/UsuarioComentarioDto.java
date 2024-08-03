package bo.imorochi.learning.reactor.model;

public class UsuarioComentarioDto {
    private final Usuario usuario;
    private final Comentarios comentarios;

    public UsuarioComentarioDto(Usuario usuario,
                                Comentarios comentarios) {
        this.usuario = usuario;
        this.comentarios = comentarios;
    }

    @Override
    public String toString() {
        return "UsuarioComentarioDto{" +
                "usuario=" + usuario +
                ", comentario=" + comentarios +
                '}';
    }

}
