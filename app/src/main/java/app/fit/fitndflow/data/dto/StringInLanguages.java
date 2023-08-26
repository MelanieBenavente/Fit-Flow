package app.fit.fitndflow.data.dto;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StringInLanguages implements Serializable {

        @SerializedName("es")
        public String spanish;

        @SerializedName("en")
        public String english;

}
