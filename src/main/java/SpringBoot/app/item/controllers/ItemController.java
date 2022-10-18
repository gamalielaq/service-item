package SpringBoot.app.item.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import SpringBoot.app.item.models.Item;
import SpringBoot.app.item.models.Producto;
import SpringBoot.app.item.services.IItemService;

@RestController
@RequestMapping("/api")
public class ItemController {
    
    @Autowired
    @Qualifier("serviceFeing")
    //@Qualifier("serviceRestTemplate")
    private IItemService itemService;

    @GetMapping("/listar")
    public List<Item> listar(@RequestParam(name = "nombre", required = false) String nombre, @RequestHeader(name = "token-request", required = false) String token) {
        return this.itemService.findAll();
    }


    @HystrixCommand(fallbackMethod = "metodoAlternativo") // Manejamos tolerancia de fallos
    @GetMapping("/listar/{id}/cantidad/{cantidad}")
    public Item detail(@PathVariable Long id, @PathVariable Integer cantidad) {
        return this.itemService.findById(id, cantidad);
    }

    @GetMapping("/test")
    public String test() {
        return "Funciona tu pinche microservicio";
    }
    
    public Item metodoAlternativo(Long id, Integer cantidad) {
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
