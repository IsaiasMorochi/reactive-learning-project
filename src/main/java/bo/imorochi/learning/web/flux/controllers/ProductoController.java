package bo.imorochi.learning.web.flux.controllers;

import bo.imorochi.learning.web.flux.models.documents.Producto;
import bo.imorochi.learning.web.flux.services.ProductoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.thymeleaf.spring6.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Objects;

@SessionAttributes("producto")
@Controller
public class ProductoController {

    private static final Logger log = LoggerFactory.getLogger(ProductoController.class);

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping({"/listar", "/"})
    public String listar(Model model) {

        Flux<Producto> productos = productoService.findAllWithNameUpperCase();

        productos.subscribe(prod -> log.info(prod.getNombre()));

        model.addAttribute("productos", productos);
        model.addAttribute("titulo", "Listado de productos");
        return "listar";
    }

    @GetMapping("/form")
    public Mono<String> crear(Model model) {
        model.addAttribute("producto", new Producto());
        model.addAttribute("titulo", "Formulario de producto");
        model.addAttribute("boton", "Crear");
        return Mono.just("form");
    }

    @GetMapping("/form/{id}")
    public Mono<String> editar(@PathVariable String id, Model model) {

        Mono<Producto> producto = productoService.findById(id)
                .doOnNext(item -> log.info("Editando producto id: {}", item.getId()))
                .defaultIfEmpty(new Producto());

        model.addAttribute("producto", producto);
        model.addAttribute("titulo", "Editar Producto");
        model.addAttribute("boton", "Editar");

        return Mono.just("form");
    }

    /**
     * Manejando el flujo de error
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/form-v2/{id}")
    public Mono<String> editarV2(@PathVariable String id, Model model) {

        return productoService.findById(id)
                .doOnNext(item -> {
                    log.info("Editando producto id: {}", item.getId());
                    model.addAttribute("producto", item);
                    model.addAttribute("titulo", "Editar Producto");
                    model.addAttribute("boton", "Editar");
                })
                .defaultIfEmpty(new Producto())
                .flatMap(item -> {
                    if (Objects.isNull(item.getId())) {
                        return Mono.error(new InterruptedException("No existe el producto"));
                    }
                    return Mono.just(item);
                })
                .then(Mono.just("form"))
                .onErrorResume(ex -> Mono.just("redirect:/listar?error=no+existe+el+producto"));
    }

    /**
     * Registra el producto en BDD y redirige
     *
     * @param producto
     * @return
     */
    @PostMapping("/form")
    public Mono<String> guardar(Producto producto, SessionStatus status) {
        status.setComplete();
        return productoService.save(producto)
                .doOnNext(item -> log.info("Guardar producto: {} con Id: {}", item.getNombre(), item.getId()))
                .thenReturn("redirect:/listar");
    }

    @GetMapping("/listar-datadriver")
    public String listarDataDriver(Model model) {

        Flux<Producto> productos = productoService.findAllWithNameUpperCase()
                .delayElements(Duration.ofSeconds(1));

        productos.subscribe(prod -> log.info(prod.getNombre()));

        model.addAttribute("productos", new ReactiveDataDriverContextVariable(productos, 1));
        model.addAttribute("titulo", "Listado de productos");
        return "listar";
    }

    @GetMapping("/listar-full")
    public String listarFull(Model model) {

        Flux<Producto> productos = productoService.findAllWithNameUpperCaseRepeat();

        model.addAttribute("productos", productos);
        model.addAttribute("titulo", "Listado de productos");
        return "listar";
    }

    @GetMapping("/listar-chunked")
    public String listarChunked(Model model) {

        Flux<Producto> productos = productoService.findAllWithNameUpperCaseRepeat();

        model.addAttribute("productos", productos);
        model.addAttribute("titulo", "Listado de productos");
        return "listar-chunked";
    }

}
