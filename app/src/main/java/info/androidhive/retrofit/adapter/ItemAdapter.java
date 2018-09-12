package info.androidhive.retrofit.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import info.androidhive.retrofit.R;
import info.androidhive.retrofit.model.Item;
import info.androidhive.retrofit.model.ItemDetail;
import info.androidhive.retrofit.activity.NavigationActivity;
import info.androidhive.retrofit.rest.ApiClient;
import info.androidhive.retrofit.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MovieViewHolder> {

    private List<Item> items;
    private int rowLayout;
    private final Context context;

     public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        LinearLayout itemsLayout;
        Item mItem;
        TextView id, quantity, name, price, stock;
        Button button;

        public MovieViewHolder(View v) {
            super(v);
            itemsLayout = (LinearLayout) v.findViewById(R.id.item_layout);
            id = (TextView) v.findViewById(R.id.id);
            quantity = (TextView) v.findViewById(R.id.quantity);
            name = (TextView) v.findViewById(R.id.name);
            price = (TextView) v.findViewById(R.id.price);
            stock = (TextView) v.findViewById(R.id.stock);
            button = (Button) v.findViewById(R.id.navigate);
            v.setOnClickListener(this);
        }

        public void setItem(final Item item) {
            mItem = item;
            // Make another request to get Item
            id.setText(item.getItemId().toString());
            quantity.setText(item.getQuantity().toString() + " units.");

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<ItemDetail> call = apiService.getItemDetails(item.getItemId().toString());
            call.enqueue(new Callback<ItemDetail>() {
                @Override
                public void onResponse(Call<ItemDetail> call, Response<ItemDetail> response) {
                    int statusCode = response.code();
                    ItemDetail details = response.body();
                    name.setText(details.getName());
                    price.setText(details.getPrice().toString() + "/-");
                    stock.setText(details.getStock().toString() + " units left.");
                }

                @Override
                public void onFailure(Call<ItemDetail> call, Throwable t) {
                    Log.e("Hello", t.toString());
                }
            });
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i =new Intent(context, NavigationActivity.class);
                    context.startActivity(i);
                }
            });
        }

        @Override
        public void onClick(View view) {

            Log.i("Clicked", "onClick " + getPosition() + " " + mItem.getItemId());
        }
    }

    public ItemAdapter(List<Item> items, int rowLayout, Context context) {
        this.items = items;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public ItemAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MovieViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MovieViewHolder holder, final int position) {
         holder.setItem(items.get(position));

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}