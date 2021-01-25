package lk.ijse.pos.bo;

import lk.ijse.pos.bo.custom.impl.CustomerBoImpl;
import lk.ijse.pos.bo.custom.impl.ItemBOImpl;

public class BoFactory {
    //1st Step
    private static BoFactory boFactory;

    //2nd Step
    private BoFactory() {
    }

    //3rd Step
    public static BoFactory getInstance() {
        return (boFactory == null) ?
                (boFactory = new BoFactory()) : (boFactory);
    }

    //4th Step
    public enum BOType {
        CUSTOMER, ITEM, ORDER, ORDER_DETAIL
    }

    //5th Step
    public <T extends SuperBo> T getBo(BOType type) {
        switch (type) {
            case CUSTOMER:
                return (T) new CustomerBoImpl();
            case ITEM:
                return (T) new ItemBOImpl();
            case ORDER:
                return null;
            case ORDER_DETAIL:
                return null;
            default:
                return null;
        }
    }

}
