package app.fit.fitndflow.data.dto.categories;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import app.fit.fitndflow.data.dto.StringInLanguages;


public class ExerciseDto implements Serializable {

    @SerializedName("id")
    public int id;
    @SerializedName("name")
    public StringInLanguages exerciseName;

    @SerializedName("primaryCategory")
    public int primaryCategory;

}
