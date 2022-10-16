package SpringBoot.app.item.Integrations.clientes;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import SpringBoot.app.item.models.Producto;

/*
    servicio-productos --> Nombre del microservicio que se declara en el archivo appication.properies
    Clase que hace la integracion con el microservicio productos(servicio-productos)
*/
@FeignClient(name = "servicio-productos", url = "localhost:8001")
public interface ProductoClienteRest {

    @GetMapping("api/listar")
    public List<Producto> listar(); 

    @GetMapping("api/listar/{id}")
    public Producto getById(@PathVariable Long id);

}
