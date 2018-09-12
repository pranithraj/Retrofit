package info.androidhive.retrofit.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import info.androidhive.retrofit.R;
import info.androidhive.retrofit.activity.MainActivity;
import info.androidhive.retrofit.activity.RecipeItemsActivity;
import info.androidhive.retrofit.model.Recipe;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.MovieViewHolder> {
    private List<Recipe> recipes;
    private int rowLayout;
    private final Context context;

     public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        LinearLayout moviesLayout;
        Recipe mItem;
        TextView movieTitle;
        TextView data;
        TextView movieDescription;
        ImageView thumbnail;

        public MovieViewHolder(View v) {
            super(v);
            moviesLayout = (LinearLayout) v.findViewById(R.id.movies_layout);
            movieTitle = (TextView) v.findViewById(R.id.title);
            data = (TextView) v.findViewById(R.id.subtitle);
            movieDescription = (TextView) v.findViewById(R.id.description);
            thumbnail = (ImageView) v.findViewById(R.id.recipe_image);
            v.setOnClickListener(this);
        }

        public void setItem(Recipe item) {
            mItem = item;
            movieTitle.setText(item.getId().toString());
            data.setText(item.getName());
            movieDescription.setText(item.getImage());
            Glide.with(context)
                    .load(item.getImage())
                    .apply(RequestOptions.circleCropTransform())
                    .into(thumbnail);
        }

        @Override
        public void onClick(View view) {
            Log.i("Clicked", "onClick " + getPosition() + " " + mItem.getId());
            Intent intent= new Intent(context, RecipeItemsActivity.class);
            intent.putExtra("recipe_id", mItem.getId());
            context.startActivity(intent);
            // overridePendingTransition(R.transition.slide_up, R.transition.stay);
        }
    }

    public RecipeAdapter(List<Recipe> recipes, int rowLayout, Context context) {
        this.recipes = recipes;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public RecipeAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                listener.onRecipeSelected(recipes.get(position));
//            }
//        });
        return new MovieViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MovieViewHolder holder, final int position) {
         holder.setItem(recipes.get(position));

    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

}