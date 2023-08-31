package app.fit.fitndflow.domain.model;

import java.io.Serializable;
import java.util.List;

public class CategoryModel implements Serializable {

    private int id;

    private String name;

    private List<ExcerciseModel> excerciseList;

    public CategoryModel(int id, String name, List<ExcerciseModel> excerciseList) {
        this.id = id;
        this.name = name;
        this.excerciseList = excerciseList;
    }

    public CategoryModel(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ExcerciseModel> getExcerciseList() {
        return excerciseList;
    }

    public void setExcerciseList(List<ExcerciseModel> excerciseList) {
        this.excerciseList = excerciseList;
    }
}
