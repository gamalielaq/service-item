package SpringBoot.app.item.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import SpringBoot.app.item.models.Item;
import SpringBoot.app.item.models.Producto;
import SpringBoot.app.item.services.IItemService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

/* @RefreshScope Permite actualizar los componentes, controladores, classes anotadas con componets o services
 es decir actualiza o se refresca el contexto vuleve a inyectar y se vuelve a inicializart el componente */
@RefreshScope  
@RestController
@RequestMapping("/api")
public class ItemController {
    
    private final Logger log =  LoggerFactory.getLogger(ItemController.class);
    
    @Autowired
    private Environment env;
    
    @Autowired
    private CircuitBreakerFactory cbFactory; 
    
    
    @Autowired
    @Qualifier("serviceFeing")
    //@Qualifier("serviceRestTemplate")
    private IItemService itemService;
    
    @Value("${configuracion.texto}")
    private String texto;
    

    @GetMapping("/listar")
    public List<Item> listar(@RequestParam(name = "nombre", required = false) String nombre, @RequestHeader(name = "token-request", required = false) String token) {
        return this.itemService.findAll();
    }

    // @HystrixCommand(fallbackMethod = "metodoAlternativo") // Manejamos tolerancia de fallos
    @GetMapping("/listar/{id}/cantidad/{cantidad}")
    public Item detail(@PathVariable Long id, @PathVariable Integer cantidad) {
        // creamos un corto circuito llamado items
        return this.cbFactory.create("items") 
                    .run(() ->  this.itemService.findById(id, cantidad),e -> metodoAlternativo(id, cantidad, e));
    }

    
    @CircuitBreaker(name = "items", fallbackMethod = "metodoAlternativo")
    @GetMapping("/listar/v1/{id}/cantidad/{cantidad}")
    public Item detail2(@PathVariable Long id, @PathVariable Integer cantidad) {
        return this.itemService.findById(id, cantidad);
                    
    }

    @CircuitBreaker(name = "items", fallbackMethod = "metodoAlternativo2")
    @TimeLimiter(name = "items")
    @GetMapping("/listar/v2/{id}/cantidad/{cantidad}")
    public CompletableFuture<Item> detail3(@PathVariable Long id, @PathVariable Integer cantidad) {
        // CompletableFuture envolvemos en una llamada futura asincrona
        return CompletableFuture.supplyAsync( () -> this.itemService.findById(id, cantidad) );               
    }
    
    @PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public Producto crear(@RequestBody Producto producto) {
        return this.itemService.save(producto);
    }
    
    @PutMapping("/editar/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Producto editar(@RequestBody Producto producto, @PathVariable Long id) {
        return this.itemService.update(producto, id);
    }
    
    @DeleteMapping("/eliminar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void editar(@PathVariable Long id) {
        this.itemService.delete(id);
    }
    

    @GetMapping("/test")
    public String test() {
        return "Funciona tu pinche microservicio";
    }
    
    public Item metodoAlternativo(Long id, Integer cantidad, Throwable e) {
        
        log.info("Mensage: " + e.getMessage());
        
        Item item = new Item();
        Producto producto= new Producto();
        
        item.setCantidad(cantidad);
        producto.setId(id);
        producto.setNombre("Camara Sony");
        producto.setPrecio(500.00);
        item.setProducto(producto);
        return item;
    }
    
    
    @GetMapping("/obtener-config")
    public ResponseEntity<?> obtenerConfiguracion(@Value("${server.port}") String port) {
        Map<String, String> json = new HashMap<>();
        json.put("texto", this.texto);
        json.put("puerto", port);
        
        if(env.getActiveProfiles().length > 0 && env.getActiveProfiles()[0].equals("dev")) {
            json.put("autor.nombre", env.getProperty(("configuracion.autor.nombre")));
            json.put("autor.email", env.getProperty(("configuracion.autor.email")));
        }
        
        return new ResponseEntity<Map<String, String>>(json, HttpStatus.OK); 
    }
    

    public CompletableFuture<Item> metodoAlternativo2(Long id, Integer cantidad, Throwable e) {
        
        log.info("Mensage: " + e.getMessage());
        
        Item item = new Item();
        Producto producto= new Producto();
        
        item.setCantidad(cantidad);
        producto.setId(id);
        producto.setNombre("Camara Sony");
        producto.setPrecio(500.00);
        item.setProducto(producto);
        return CompletableFuture.supplyAsync(() -> item);
    }
    
    
}