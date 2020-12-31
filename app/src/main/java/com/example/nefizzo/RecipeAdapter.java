package com.example.nefizzo;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    private List<Recipe> list;
    private Context context;
    private String username;
    private DatabaseReference userRef;

    public RecipeAdapter(List<Recipe> list, Context context, String username, DatabaseReference userRef) {
        this.list = list;
        this.context = context;
        this.username = username;
        this.userRef = userRef;
    }

    @NonNull
    @Override
    public RecipeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recipe_list_layout, parent, false);
        return new RecipeAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipe currentRecipe = list.get(position);
        holder.recipeName.setText(currentRecipe.getFoodName());
        holder.user.setText(username);

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
                .load(currentRecipe.getItemImage().isEmpty() ? null : currentRecipe.getItemImage() )
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView recipeName;
        ImageView imageView;
        TextView prepTime;
        TextView cookTime;
        TextView servingNumber;
        TextView user;
        public ImageButton delete_button;
        int layoutPosition;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            recipeName = itemView.findViewById(R.id.recipe_name);
            imageView = itemView.findViewById(R.id.image_view_upload);
            prepTime = itemView.findViewById(R.id.prep_time);
            cookTime = itemView.findViewById(R.id.cook_time);
            servingNumber = itemView.findViewById(R.id.serving_num);
            user = itemView.findViewById(R.id.inst_text);
            delete_button = itemView.findViewById(R.id.delete_button);
            delete_button.setOnClickListener(this);
        }


        @Override
        public void onClick(View v)
        {
            if (v == delete_button) {
                AlertDialog.Builder adb = new AlertDialog.Builder(context);
                adb.setMessage("Are you sure?").setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteRecipe(getLayoutPosition());
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog dialog = adb.create();
                dialog.show();
            }
        }

        private void deleteRecipe(int position)
        {
            Recipe delete_rec = list.get(position);
            DatabaseReference drRecipe = userRef.child(delete_rec.getFoodName());
            drRecipe.removeValue();
            list.clear();

            Toast.makeText(context, "Recipe is deleted", Toast.LENGTH_LONG).show();
        }

        public void dialogEvent(View view, int position)
        {

        }
    }
}