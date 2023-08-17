package app.fit.fitndflow.domain.model;

import java.util.List;

public class ItemModel {
    private String name;
    private List<ItemModel> itemModelList;


    public ItemModel(String name, List<ItemModel> itemModelList) {
        this.name = name;
        this.itemModelList = itemModelList;
    }

    public ItemModel(String name) {
        this.name = name;
    }
    public List<ItemModel> getItemModelList() {
        return itemModelList;
    }
    public void setItemModelList(List<ItemModel> itemModelList) {
        this.itemModelList = itemModelList;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
