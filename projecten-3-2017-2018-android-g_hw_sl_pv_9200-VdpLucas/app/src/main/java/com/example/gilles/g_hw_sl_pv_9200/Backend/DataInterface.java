package com.example.gilles.g_hw_sl_pv_9200.Backend;

import com.example.gilles.g_hw_sl_pv_9200.model.Activiteit;
import com.example.gilles.g_hw_sl_pv_9200.model.ChatMessage;
import com.example.gilles.g_hw_sl_pv_9200.model.Gebruiker;
import com.example.gilles.g_hw_sl_pv_9200.model.Gesprek;
import com.example.gilles.g_hw_sl_pv_9200.model.Gezin;
import com.example.gilles.g_hw_sl_pv_9200.model.Kost;
import com.example.gilles.g_hw_sl_pv_9200.model.User;

import java.util.List;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * dataInterface met alle post en getmethodes voor de backend
 * Created by Lucas on 6-12-2017.
 */

public interface DataInterface {

    //String BASE_URL="https://tranquil-meadow-65977.herokuapp.com/";
     static final String BASE_URL = "http://192.168.0.239:3000/";

    @GET("/API/activiteit/{id}")
    Call<List<Activiteit>> getActiviteiten(@Path("id") String userId);


    @POST("/API/users/registreer")
    Call<User> registreer(@Body User user);

    @POST("/API/kost/{id}")
    Call<Kost> voegKostToe(@Path("id") String userId, @Body Kost kost);

    @FormUrlEncoded
    @POST("/API/users/login")
    Call<User> getUser(@Field("username") String username, @Field("password") String password);

    @GET("/API/gebruiker/{id}")
    Call<Gebruiker> getGebruiker(@Path("id")String userId);

    @GET("/API/gesprek/{id}/android")
    Call<List<Gesprek>> getGesprekken(@Path("id") String userId);

    @GET("/API/gezin/{id}")
    Call<Gezin> getHuidigGezin(@Path("id") String userId);

    @GET("/API/gesprek/gesprek/{id}/berichten")
    Call<List<ChatMessage>> getBerichtenVanGesprek(@Path("id")String gesprekId);

    @GET("/API/kost/{id}")
    Call<List<Kost>> getKosten(@Path("id") String userId);

    @PUT("/API/kost/keurgoed/{id}/{kostId}")
    Call<Kost> keurKostGoed(@Path("id") String userId,@Path("kostId") String kostId);

    @GET("/API/kost/{id}/{maand}/{jaar}")
    Call<List<Kost>> getKostenMaand(@Path("id") String userId, @Path("maand") int maand, @Path("jaar") int jaar);

    @POST("/API/gesprek/{gesprekId}/bericht")
    Call<ChatMessage> postBericht(@Path("gesprekId") String gesprekId,@Body ChatMessage bericht);


//    public void login(View view) {
//        String json = "{"name": "" + nameInput.getText() + "", "password":"" + passwordInput.getText() + ""}";
//        networkHelper.post("http://10.0.3.2:8000/smarthome/_session", json, new Callback() {
//            @Override
//            public void onFailure(Request request, IOException e) {
//            }
//            @Override
//            public void onResponse(Response response) throws IOException {
//                String responseStr = response.body().string();
//                final String messageText = "Status code : " + response.code() +
//                        "n" +
//                        "Response body : " + responseStr;
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(getApplicationContext(), messageText, Toast.LENGTH_LONG).show();
//                    }
//                });
//            }
//        });
//    }






}

