package info.androidhive.retrofit.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.List;

import info.androidhive.retrofit.R;
import info.androidhive.retrofit.adapter.ItemAdapter;
import info.androidhive.retrofit.adapter.RecipeAdapter;
import info.androidhive.retrofit.model.Item;
import info.androidhive.retrofit.model.ItemResponse;
import info.androidhive.retrofit.model.Recipe;
import info.androidhive.retrofit.model.RecipeResponse;
import info.androidhive.retrofit.rest.ApiClient;
import info.androidhive.retrofit.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeItemsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recipe_item_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Call<ItemResponse> call = apiService.getRecipeItems(String.valueOf(extras.getInt("recipe_id")));
            call.enqueue(new Callback<ItemResponse>() {
                @Override
                public void onResponse(Call<ItemResponse> call, Response<ItemResponse> response) {
                    int statusCode = response.code();
                    List<Item> items = response.body().getRecipe();
                    ItemAdapter mAdapter =new ItemAdapter(items, R.layout.recipe_item_layout, getApplicationContext());
                    recyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<ItemResponse> call, Throwable t) {
                    Log.e("Hello", t.toString());
                }
            });
        }
    }

    @Override
    public void finish(){
        overridePendingTransition(R.transition.stay, R.transition.slide_down);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
