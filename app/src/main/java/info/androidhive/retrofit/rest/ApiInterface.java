package info.androidhive.retrofit.rest;

import info.androidhive.retrofit.model.ItemDetail;
import info.androidhive.retrofit.model.ItemResponse;
import info.androidhive.retrofit.model.Message;
import info.androidhive.retrofit.model.RecipeResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiInterface {
    @GET("recipes")
    Call<RecipeResponse> getRecipes();

    @GET("getitems")
    Call<ItemResponse> getRecipeItems(@Query("recipe_id") String recipeName);

    @GET("getitem")
    Call<ItemDetail> getItemDetails(@Query("item_id") String itemID);

    @GET("login")
    Call<Message> login(@Query("u_id") String uid, @Query("pass") String pass, @Query("cart") String cart);

 //   @GET("additem")
   // Call


//    @GET("movie/{id}")
//    Call<RecipeResponse> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);
}
