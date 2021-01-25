package lk.ijse.pos.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.pos.bo.BoFactory;
import lk.ijse.pos.bo.custom.ItemBo;
import lk.ijse.pos.dto.ItemDTO;
import lk.ijse.pos.view.tm.ItemTM;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class ItemFormController {
    public AnchorPane root;
    public TextField txtItemCode;
    public TextField txtDescription;
    public TextField txtQtyOnHand;
    public TextField txtPrice;
    public TableView<ItemTM> tableItem;
    public TableColumn colCode;
    public TableColumn colDescription;
    public TableColumn colqty;
    public TableColumn colPrice;
    public TableColumn colOperate;
    public TextField txtSearch;

    private ItemBo bo;

    public void initialize() throws Exception {
        bo = BoFactory.getInstance().getBo(BoFactory.BOType.ITEM);






        tableItem.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    setData(newValue);
                });

      
    }

    private void setData(ItemTM tm) {
        txtItemCode.setText(tm.getCode());
        txtDescription.setText(tm.getDescription());
        txtPrice.setText(tm.getUnitPrice()+"");
        txtQtyOnHand.setText(tm.getQtyOnHand()+"");
    }


    ObservableList<ItemTM> tmList = FXCollections.observableArrayList();

    private void loadAllItems() throws Exception {
        tmList.clear();
        List<ItemDTO> allItems = bo.getAllItems();
        for (ItemDTO item : allItems
        ) {
            Button btn = new Button("Delete");
            ItemTM tm = new ItemTM(
                    item.getCode(), item.getDescription(),
                    item.getPrice(), item.getQtyOnHand(), btn
            );
            tmList.add(tm);
            btn.setOnAction((e) -> {
                try {

                    ButtonType ok = new ButtonType("OK",
                            ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("NO",
                            ButtonBar.ButtonData.CANCEL_CLOSE);

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                            "Are You Sure ?", ok, no);
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.orElse(no) == ok) {
                        if (bo.deleteItem(tm.getCode())) {
                            new Alert(Alert.AlertType.CONFIRMATION,
                                    "Deleted", ButtonType.OK).show();
                            loadAllItems();
                            return;
                        }
                        new Alert(Alert.AlertType.WARNING,
                                "Try Again", ButtonType.OK).show();
                    } else {
                        //no
                    }


                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            });
        }
        tableItem.setItems(tmList);
    }


    public void newOnAction(ActionEvent actionEvent) {
    }

    public void backToHome(MouseEvent mouseEvent) throws IOException {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/lk/ijse/pos/view/MainForm.fxml"))));
    }

    public void saveOnAction(ActionEvent actionEvent) throws Exception {
        boolean isSaved = bo.saveItem(
                new ItemDTO(txtItemCode.getText(),
                        txtDescription.getText(),
                        Double.parseDouble(txtPrice.getText()),
                        Integer.parseInt(txtQtyOnHand.getText())
                ));

        System.out.println(isSaved);
    }

    public void getDataOnAction(ActionEvent actionEvent) throws Exception {

        ItemDTO item = bo.getItem(txtSearch.getText());
        if (item != null) {
            txtItemCode.setText(item.getCode());
            txtDescription.setText(item.getDescription());
            txtPrice.setText(item.getPrice() + "");
            txtQtyOnHand.setText(item.getQtyOnHand() + "");
        }
    }
}
