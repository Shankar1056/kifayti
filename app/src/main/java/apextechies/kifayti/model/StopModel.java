package apextechies.kifayti.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class StopModel {

    public String getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public ArrayList<StopDataModel> getData() {
        return data;
    }

    @SerializedName("status")
    String status;
    @SerializedName("msg")
    String msg;
    @SerializedName("data")
    ArrayList<StopDataModel> data;


}
