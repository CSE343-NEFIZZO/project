package com.example.nefizzo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private List<Recipe> list;
    private Context context;
    private String username;

    public SearchAdapter(List<Recipe> list, Context context, String username) {
        this.list = list;
        this.context = context;
        this.username = username;
    }

    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_search_adapter, parent, false);
        return new SearchAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipe currentRecipe = list.get(position);
        holder.recipeName.setText(currentRecipe.getFoodName());

        if (currentRecipe.getPreparationHour() == 0) {
            holder.prepTime.setText("Prep: " + currentRecipe.getPreparationMin() + "m");
        } else if ((currentRecipe.getPreparationHour() > 0)
                && (currentRecipe.getPreparationMin() == 0)) {
            holder.prepTime.setText("Prep: " + currentRecipe.getPreparationHour() + "h");
        } else {
            holder.prepTime.setText("Prep: " + currentRecipe.getPreparationHour() + "h "
                    + currentRecipe.getPreparationMin() + "m");
        }

        if (currentRecipe.getCookingHour() == 0) {
            holder.cookTime.setText("Cook: " + currentRecipe.getCookingMin() + "m");
        } else if ((currentRecipe.getCookingHour() > 0 )
                && (currentRecipe.getCookingMin() == 0)) {
            holder.cookTime.setText("Cook: " + currentRecipe.getCookingHour() + "h");
        } else {
            holder.cookTime.setText("Cook: " + currentRecipe.getCookingHour() + "h "
                    + currentRecipe.getCookingMin() + "m");
        }

        holder.servingNumber.setText("Servings: " + currentRecipe.getServingNumber());

        Picasso.get()
                .load(currentRecipe.getItemImage())
                .fit()
                .centerCrop()
                .into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), RecipeDetailActivity.class);
                intent.putExtra("recipe", (Serializable) currentRecipe);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void filteredList(ArrayList<Recipe> filterList) {
        list = filterList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView recipeName;
        ImageView imageView;
        TextView prepTime;
        TextView cookTime;
        TextView servingNumber;
        TextView user;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            recipeName = itemView.findViewById(R.id.recipe_name);
            imageView = itemView.findViewById(R.id.image_view_upload);
            prepTime = itemView.findViewById(R.id.prep_time);
            cookTime = itemView.findViewById(R.id.cook_time);
            servingNumber = itemView.findViewById(R.id.serving_num);
            user = itemView.findViewById(R.id.inst_text);
        }
    }
}
