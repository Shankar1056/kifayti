package apextechies.kifayti.model;

import com.google.gson.annotations.SerializedName;

public class StopDataModel {

    public String getId() {
        return id;
    }

    public int getStatus() {
        return status;
    }

    @SerializedName("id")
    String id;
    @SerializedName("status")
    int status;
}
