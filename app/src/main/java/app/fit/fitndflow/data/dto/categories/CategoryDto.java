package app.fit.fitndflow.data.dto.categories;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import app.fit.fitndflow.data.dto.StringInLanguages;

public class CategoryDto implements Serializable {

    @SerializedName("id")
    public int id;

    @SerializedName("name")
    public StringInLanguages name;

    @SerializedName("exercises")
    public List<ExerciseDto> exerciseDtoList;

}
