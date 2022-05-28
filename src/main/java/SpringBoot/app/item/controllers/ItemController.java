package SpringBoot.app.item.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import SpringBoot.app.item.models.Item;
import SpringBoot.app.item.services.IItemService;

@RestController
@RequestMapping("/api")
public class ItemController {
    
    @Autowired
    @Qualifier("serviceRestTemplate") // Al tener 2 servicios que implementan la interfas IItemSercice, Qualifier indica que deve usar solamente el servicio o clase "serviceFeing"
    private IItemService itemService;

    @GetMapping("/listar")
    public List<Item> listar() {
        return this.itemService.findAll();
    }

    @GetMapping("/listar/{id}/cantidad/{cantidad}")
    public Item detail(@PathVariable Long id, @PathVariable Integer cantidad) {
        return this.itemService.findById(id, cantidad);
    }

    @GetMapping("/test")
    public String test() {
        return "Funciona tu pinche microservicio";
    }
}
