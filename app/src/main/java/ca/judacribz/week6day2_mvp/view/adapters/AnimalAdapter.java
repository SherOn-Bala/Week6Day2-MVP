package ca.judacribz.week6day2_mvp.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ca.judacribz.week6day2_mvp.R;
import ca.judacribz.week6day2_mvp.model.animal.Animal;
import ca.judacribz.week6day2_mvp.view.activities.animal_details.AnimalDetails;

public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.ViewHolder> {
    private ArrayList<Animal> animals;
    private Context context;

    Map<String, Bitmap> aniPics;

    public AnimalAdapter(Context context, ArrayList<Animal> animals) {
        this.context = context;
        this.animals = animals;
        aniPics = new HashMap<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_animal,
                parent,
                false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setAnimalData(animals.get(position));
    }

    @Override
    public int getItemCount() {
        return animals.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public static final String EXTRA_ANIMAL =
                "ca.judacribz.week2weekend.animals.EXTRA_ANIMAL";

        TextView
                tvAnimalName,
                tvScientificName,
                tvDiet,
                tvStatus,
                tvRange;

        ImageView ivAnimalImage;

        String aniName;

        ViewHolder(@NonNull final View itemView) {
            super(itemView);

            tvAnimalName = itemView.findViewById(R.id.tvAnimalName);
            tvScientificName = itemView.findViewById(R.id.tvScientificName);
            tvDiet = itemView.findViewById(R.id.tvDiet);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvRange = itemView.findViewById(R.id.tvRange);

            ivAnimalImage = itemView.findViewById(R.id.ivAnimalImage);

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), AnimalDetails.class);
                    Bundle bundle = new Bundle();

                    bundle.putParcelable(EXTRA_ANIMAL, animals.get(getAdapterPosition()));
                    intent.putExtras(bundle);

                    v.getContext().startActivity(intent);
                }
            });
        }


        void setAnimalData(final Animal animal) {
            tvAnimalName.setText(aniName = animal.getName());
            tvScientificName.setText(animal.getScientificName());
            tvDiet.setText(animal.getDiet());
            tvStatus.setText(animal.getStatus());

            if (!aniPics.containsKey(aniName)) {
                Glide
                        .with(context)
                        .asBitmap()
                        .load(animal.getImgUrl())
                        .into(new CustomTarget<Bitmap>() {

                            @Override
                            public void onResourceReady(
                                    @NonNull Bitmap bitmap,
                                    @Nullable Transition<? super Bitmap> trans) {

                                aniPics.put(aniName, bitmap);
                                setAnimalImage(bitmap);
                            }

                            @Override
                            public void onLoadCleared(@Nullable Drawable placeholder) {
                            }
                        });
            } else {
                setAnimalImage(aniPics.get(aniName));
            }
        }

        private void setAnimalImage(@Nullable Bitmap bitmap) {
            ivAnimalImage.setImageBitmap(bitmap);
        }
    }
}
