package app.fit.fitndflow.domain.model;

import java.io.Serializable;

public class ExcerciseModel implements Serializable {

    private int id;

    private String name;

    private int primaryCategroy;

    public ExcerciseModel(int id, String name) {
        this.id = id;
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

    public int getPrimaryCategroy() {
        return primaryCategroy;
    }

    public void setPrimaryCategroy(int primaryCategroy) {
        this.primaryCategroy = primaryCategroy;
    }
}
