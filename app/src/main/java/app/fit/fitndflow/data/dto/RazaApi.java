package app.fit.fitndflow.data.dto;

import com.google.gson.annotations.SerializedName;

public class RazaApi {

    @SerializedName ("id")
    public int id;

    @SerializedName ("name")
    public StringInLanguages name;

    public class StringInLanguages{

        @SerializedName("es")
        public String spanish;

        @SerializedName("en")
        public String english;
    }
}
