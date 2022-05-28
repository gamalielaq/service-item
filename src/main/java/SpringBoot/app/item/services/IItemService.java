package SpringBoot.app.item.services;

import java.util.List;

import SpringBoot.app.item.models.Item;

public interface IItemService {
    
    public List<Item> findAll();

    public Item findById(Long id, Integer cantidad);
    
}
