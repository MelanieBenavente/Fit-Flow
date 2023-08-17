package app.fit.fitndflow.domain.model;

import java.util.List;

public class ItemModel {
    private String name;
    private List<ItemModel> exerciseList;


    public ItemModel(String name, List<ItemModel> exerciseList) {
        this.name = name;
        this.exerciseList = exerciseList;
    }

    public ItemModel(String name) {
        this.name = name;
    }
    public List<ItemModel> getExerciseList() {
        return exerciseList;
    }
    public void setExerciseList(List<ItemModel> exerciseList) {
        this.exerciseList = exerciseList;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
