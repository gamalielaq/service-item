package SpringBoot.app.item.services;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SpringBoot.app.item.Integrations.clientes.ProductoClienteRest;
import SpringBoot.app.item.models.Item;

@Service("serviceFeing") // ItemServiceFeing-> nombre del servicio
//@Primary // @Primary --> Se Utiliza para indicarle al controlador que es este es el servicio primario que deve inyectar
public class ItemServiceFeing implements IItemService {


    private final Logger log = LoggerFactory.getLogger(ItemServiceFeing.class);

    @Autowired
    private ProductoClienteRest clienteRest;

    @Override
    public List<Item> findAll() {
        log.info("Entraste metodo findAll feing");
        return this.clienteRest
            .listar()
            .stream()
            .map((producto) ->  new Item(producto, 1)).collect(Collectors.toList()
        );
    }

    @Override
    public Item findById(Long id, Integer cantidad) {
        log.info("Entraste metodo findById feing");
        return new Item(this.clienteRest.getById(id), cantidad);
    }
    
}
