package apextechies.kifayti.retrofitnetwork;


import apextechies.kifayti.model.StopModel;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiRetrofitService {


    @GET("start_stop_app")
    Call<StopModel> stopApp();


}
