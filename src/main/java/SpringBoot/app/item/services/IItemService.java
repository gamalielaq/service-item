package SpringBoot.app.item.services;

import java.util.List;

import com.springboot.app.commons.entity.Producto;

import SpringBoot.app.item.models.Item;

public interface IItemService {
    
    public List<Item> findAll();

    public Item findById(Long id, Integer cantidad);
    
    public Producto save(Producto producto);
    
    public Producto update(Producto producto, Long id);
    
    public void delete(Long id);
}
