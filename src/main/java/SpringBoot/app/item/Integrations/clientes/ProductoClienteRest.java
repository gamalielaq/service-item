package SpringBoot.app.item.Integrations.clientes;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import SpringBoot.app.item.models.Producto;

/*
    servicio-productos --> Nombre del microservicio que se declara en el archivo appication.properies
    Clase que hace la integracion con el microservicio productos(servicio-productos)
*/
@FeignClient(name = "servicio-productos")
public interface ProductoClienteRest {

    @GetMapping("api/listar")
    public List<Producto> listar(); 

    @GetMapping("api/listar/{id}")
    public Producto getById(@PathVariable Long id);
    
    
    @PostMapping("api/crear")
    public Producto create(@RequestBody Producto Producto);
    
    @PutMapping("api/editar/{id}")
    public Producto update(@RequestBody Producto Producto, @PathVariable Long id);
    
    
    @DeleteMapping("api/eliminar/{id}")
    public void delete(@PathVariable Long id);
    

}
