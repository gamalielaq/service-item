package SpringBoot.app.item.services;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import SpringBoot.app.item.models.Item;
import SpringBoot.app.item.models.Producto;

@Service("serviceRestTemplate") // registar esta clase como un componente de spring(beans de spring)
public class ItemServiceImp implements IItemService {
	
	 private final Logger log = LoggerFactory.getLogger(ItemServiceFeing.class);

    @Autowired
    public RestTemplate http;

    @Override
    public List<Item> findAll() {
    	   log.info("Entraste metodo findAll de rest template");
        List<Producto> productos = Arrays.asList(
            this.http.getForObject("http://servicio-productos/api/listar", Producto[].class)
        );

        return productos.stream().map((producto) -> new Item(producto, 1)).collect(Collectors.toList());
    }

    @Override
    public Item findById(Long id, Integer cantidad) {
        Map<String, String> map = new HashMap<>();

        map.put("id", id.toString());

        Producto producto = this.http.getForObject(
            "http://servicio-productos/api/listar/{id}", Producto.class, map
        );

        return new Item(producto, cantidad);
    }
    
}
