package lk.ijse.pos.dao;

import lk.ijse.pos.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.pos.dao.custom.impl.ItemDAOImpl;

public class DaoFactory {

    private static DaoFactory daoFactory;


    private DaoFactory() {
    }


    public static DaoFactory getInstance() {
        return (daoFactory == null) ?
                (daoFactory = new DaoFactory()) : (daoFactory);
    }

    public enum DAOType {
        CUSTOMER, ITEM, ORDER, ORDER_DETAIL
    }

    public <T> T getDao(DAOType type) {
        switch (type) {
            case CUSTOMER:
                return (T) new CustomerDAOImpl();
            case ITEM:
                return (T) new ItemDAOImpl();
            case ORDER:
                return null;
            case ORDER_DETAIL:
                return null;

            default:
                return null;
        }
    }

}
