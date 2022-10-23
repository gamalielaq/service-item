package SpringBoot.app.item.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import SpringBoot.app.item.models.Item;
import SpringBoot.app.item.models.Producto;
import SpringBoot.app.item.services.IItemService;

@RestController
@RequestMapping("/api")
public class ItemController {
    
    private final Logger log =  LoggerFactory.getLogger(ItemController.class);
    
    @Autowired
    private CircuitBreakerFactory cbFactory; 
    
    
    @Autowired
    @Qualifier("serviceRestTemplate")
    private IItemService itemService;

    @GetMapping("/listar")
    public List<Item> listar(@RequestParam(name = "nombre", required = false) String nombre, @RequestHeader(name = "token-request", required = false) String token) {
        return this.itemService.findAll();
    }


    // @HystrixCommand(fallbackMethod = "metodoAlternativo") // Manejamos tolerancia de fallos
    @GetMapping("/listar/{id}/cantidad/{cantidad}")
    public Item detail(@PathVariable Long id, @PathVariable Integer cantidad) {
        return this.cbFactory.create("items").run(() ->  this.itemService.findById(id, cantidad),e -> metodoAlternativo(id, cantidad, e));
    }

    @GetMapping("/test")
    public String test() {
        return "Funciona tu pinche microservicio";
    }
    
    public Item metodoAlternativo(Long id, Integer cantidad, Throwable e) {
        
        log.info(e.getMessage());
        
        Item item = new Item();
        Producto producto= new Producto();
        
        item.setCantidad(cantidad);
        producto.setId(id);
        producto.setNombre("Camara Sony");
        producto.setPrecio(500.00);
        item.setProducto(producto);
        return item;
    }
    
    
    
}
