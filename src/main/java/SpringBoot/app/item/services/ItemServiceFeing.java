package SpringBoot.app.item.services;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SpringBoot.app.item.Integrations.clientes.ProductoClienteRest;
import SpringBoot.app.item.models.Item;
import SpringBoot.app.item.models.Producto;

@Service("serviceFeing") // ItemServiceFeing-> nombre del servicio
//@Primary // @Primary --> Se Utiliza para indicarle al controlador que es este es el servicio primario que deve inyectar
public class ItemServiceFeing implements IItemService {
    private final Logger log = LoggerFactory.getLogger(ItemServiceFeing.class);

    @Autowired  
    private ProductoClienteRest clienteFeing;

    @Override
    public List<Item> findAll() {
        log.info("Entraste metodo findAll feing");
        return this.clienteFeing
            .listar()
            .stream()
            .map((producto) ->  new Item(producto, 1)).collect(Collectors.toList()
        );
    }

    @Override
    public Item findById(Long id, Integer cantidad) {
        log.info("Entraste metodo findById feing");
        return new Item(this.clienteFeing.getById(id), cantidad);
    }

    @Override
    public Producto save(Producto producto) {
        return this.clienteFeing.create(producto);
    }

    @Override
    public Producto update(Producto producto, Long id) {
        return this.clienteFeing.update(producto, id);
    }

    @Override
    public void delete(Long id) {   
        this.clienteFeing.delete(id);
    }

}
