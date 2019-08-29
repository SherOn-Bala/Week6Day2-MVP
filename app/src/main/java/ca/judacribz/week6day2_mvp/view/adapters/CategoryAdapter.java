package ca.judacribz.week6day2_mvp.view.adapters;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ca.judacribz.week6day2_mvp.R;
import ca.judacribz.week6day2_mvp.model.Category;
import ca.judacribz.week6day2_mvp.view.activities.animal_list.AnimalList;

import static ca.judacribz.week6day2_mvp.view.activities.category_list.Presenter.EXTRA_CATEGORY_NAME;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder> {

    private ArrayList<Category> categories;

    public CategoryAdapter(ArrayList<Category> categories) {
        this.categories = categories;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new CategoryHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_category,
                parent,
                false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
        holder.bindData(categories.get(position));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    class CategoryHolder extends RecyclerView.ViewHolder {

        TextView
                tvCategoryName,
                tvNumSpecies,
                tvDescription;

        CategoryHolder(@NonNull View itemView) {
            super(itemView);

            tvCategoryName = itemView.findViewById(R.id.tvCategoryName);
            tvNumSpecies = itemView.findViewById(R.id.tvNumSpecies);
            tvDescription = itemView.findViewById(R.id.tvDescription);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), AnimalList.class);
                    intent.putExtra(EXTRA_CATEGORY_NAME, categories.get(getAdapterPosition()).getName());
                    view.getContext().startActivity(intent);
                }
            });
        }

        void bindData(Category category) {
            tvCategoryName.setText(category.getName());
            tvNumSpecies.setText(String.valueOf(category.getNumSpecies()));
            tvDescription.setText(category.getDescription());
        }
    }
}
