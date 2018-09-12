package info.androidhive.retrofit.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecipeResponse {

    @SerializedName("recipe")
    @Expose
    private List<Recipe> recipe = null;

    public List<Recipe> getRecipe() {
        return recipe;
    }

    public void setRecipe(List<Recipe> recipe) {
        this.recipe = recipe;
    }

}
