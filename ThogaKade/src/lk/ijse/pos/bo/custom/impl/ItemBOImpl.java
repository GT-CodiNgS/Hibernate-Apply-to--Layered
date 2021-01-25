package lk.ijse.pos.bo.custom.impl;

import lk.ijse.pos.bo.custom.ItemBo;
import lk.ijse.pos.dao.DaoFactory;
import lk.ijse.pos.dao.custom.ItemDAO;
import lk.ijse.pos.dto.ItemDTO;
import lk.ijse.pos.entity.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemBOImpl implements ItemBo {

    private ItemDAO dao= DaoFactory.getInstance()
            .getDao(DaoFactory.DAOType.ITEM);

    @Override
    public boolean saveItem(ItemDTO dto) throws Exception {
        return dao.add(new Item(dto.getCode(),
                dto.getDescription(),dto.getPrice(),dto.getQtyOnHand()));
    }

    @Override
    public boolean updateItem(ItemDTO dto) throws Exception {
        return dao.update(new Item(dto.getCode(),
                dto.getDescription(),dto.getPrice(),dto.getQtyOnHand()));
    }

    @Override
    public boolean deleteItem(String id) throws Exception {
        return dao.delete(id);
    }

    @Override
    public ItemDTO getItem(String id) throws Exception {
        Item i=dao.search(id);
        return new ItemDTO(i.getCode(),
                i.getDescription(),i.getPrice(),i.getQtyOnHand());
    }

    @Override
    public ArrayList<ItemDTO> getAllItems() throws Exception {
       return null;
    }
}
