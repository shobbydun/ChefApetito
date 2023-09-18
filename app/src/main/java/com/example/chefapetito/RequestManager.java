package com.example.chefapetito;

import android.content.Context;

import com.example.chefapetito.Listeners.RandomRecipeResponseListener;
import com.example.chefapetito.Listeners.RecipeDetailListener;
import com.example.chefapetito.Listeners.SimilarRecipeListener;
import com.example.chefapetito.Models.RandomRecipeApiResponse;
import com.example.chefapetito.Models.RecipeDetailsResponse;
import com.example.chefapetito.Models.SimilarRecipeResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class RequestManager {
    Context context;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public RequestManager(Context context) {
        this.context = context;
    }

    public void getRandomRecipes(RandomRecipeResponseListener listener, List<String> tags){
        CallRandomRecipe callRandomRecipe = retrofit.create(CallRandomRecipe.class);
        Call<RandomRecipeApiResponse> call = callRandomRecipe.callRandomRecipe(context.getString(R.string.api_key), "25",tags);
        call.enqueue(new Callback<RandomRecipeApiResponse>() {
            @Override
            public void onResponse(Call<RandomRecipeApiResponse> call, Response<RandomRecipeApiResponse> response) {
                if (!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<RandomRecipeApiResponse> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    public void getRecipeDetails(RecipeDetailListener listener, int id){
        CallRecipeDetails callRecipeDetails = retrofit.create(CallRecipeDetails.class);
        Call<RecipeDetailsResponse> call = callRecipeDetails.callRecipeDetails(id, context.getString(R.string.api_key));
        call.enqueue(new Callback<RecipeDetailsResponse>() {
            @Override
            public void onResponse(Call<RecipeDetailsResponse> call, Response<RecipeDetailsResponse> response) {
                if (!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(),response.message());
            }

            @Override
            public void onFailure(Call<RecipeDetailsResponse> call, Throwable t) {
                listener.didError(t.getMessage());

            }
        });
    }

    public void getSimilarRecipes(SimilarRecipeListener listener, int id){
        CallsSimilarRecipes callsSimilarRecipes = retrofit.create(CallsSimilarRecipes.class);
        Call<List<SimilarRecipeResponse>> call = callsSimilarRecipes.callSimilarRecipe(id, "8", context.getString(R.string.api_key));
        call.enqueue(new Callback<List<SimilarRecipeResponse>>() {
            @Override
            public void onResponse(Call<List<SimilarRecipeResponse>> call, Response<List<SimilarRecipeResponse>> response) {
              if(!response.isSuccessful()){
                  listener.didError(response.message());
                  return;
              }
              listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<List<SimilarRecipeResponse>> call, Throwable t) {

                listener.didError(t.getMessage());
            }
        });
    }

    private interface CallRandomRecipe{

        @GET("recipes/random")
        Call<RandomRecipeApiResponse> callRandomRecipe(
                @Query("apiKey") String apiKey,
                @Query("number") String number,
                @Query("tags") List<String> tags
        );
    }


    private interface CallRecipeDetails{
        @GET("recipes/{id}/information")
        Call<RecipeDetailsResponse> callRecipeDetails(
                @Path("id") int id,
                @Query("apiKey") String apiKey
        );
    }

    private interface CallsSimilarRecipes{
        @GET("recipes/{id}/information")
        Call<List<SimilarRecipeResponse>> callSimilarRecipe(
               @Path("id") int id,
               @Query("number") String number,
               @Query("apiKey") String apiKey

        );
    }

}
