package info.androidhive.retrofit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ItemResponse {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("recipe")
    @Expose
    private List<Item> recipe = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Item> getRecipe() {
        return recipe;
    }

    public void setRecipe(List<Item> recipe) {
        this.recipe = recipe;
    }

}
