package parisdescartes.pjs4;

import java.util.List;
import java.util.Objects;


import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FieldMap;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;
/**
 * Created by Killian on 08/02/2016.
 */
public interface EraltionService {

    public static final String ENDPOINT = "http://192.168.1.89:8080";

    @POST("/api/connect")
    void connect(@Body RequestConnect requestConnect, Callback<Profil> callback);

}
