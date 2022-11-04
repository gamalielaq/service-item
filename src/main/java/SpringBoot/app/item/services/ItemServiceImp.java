package SpringBoot.app.item.services;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import SpringBoot.app.item.models.Item;
import SpringBoot.app.item.models.Producto;

@Service("serviceRestTemplate") // registar esta clase como un componente de spring(beans de spring)
public class ItemServiceImp implements IItemService {
	
	 private final Logger log = LoggerFactory.getLogger(ItemServiceImp.class);

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

    @Override
    public Producto save(Producto producto) {
        HttpEntity<Producto> body = new HttpEntity<Producto>(producto);
        ResponseEntity<Producto> response = this.http.exchange("http://servicio-productos/api/crear", HttpMethod.POST, body, Producto.class);
        Producto productoResponse = response.getBody();
        return productoResponse;
    }

    @Override
    public Producto update(Producto producto, Long id) {
        HttpEntity<Producto> body = new HttpEntity<Producto>(producto);
        
        Map<String, String> map = new HashMap<>();
        map.put("id", id.toString());
        
        ResponseEntity<Producto> response = this.http.exchange("http://servicio-productos/api/editar/{id}",
                    HttpMethod.PUT, body, Producto.class, map);
        return response.getBody();
    }

    @Override
    public void delete(Long id) {
        Map<String, String> map = new HashMap<>();
        map.put("id", id.toString());
        
        this.http.delete("http://servicio-productos/api/eliminar/{id}", map);
    }
    
    
    
}
