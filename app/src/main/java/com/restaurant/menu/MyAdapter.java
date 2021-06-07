package com.restaurant.menu;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.restaurant.menu.models.Model;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyAdapter extends FirebaseRecyclerAdapter<Model, MyAdapter.myviewholder> {

    public MyAdapter(@NonNull FirebaseRecyclerOptions<Model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, final int position, @NonNull Model model) {
        holder.hotelName.setText(model.getName());
        holder.hotelEmail.setText(model.getEmail());
        holder.hotelMob.setText(model.getMob());
        holder.hotelLocation.setText(model.getLocation());
        holder.hotelRating.setText(model.getRating());

        holder.imvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.hotelEmail.getContext())
                        .setContentHolder(new ViewHolder(R.layout.dialog_content))
                        .setExpanded(true, 1000)
                        .create();

                View myView = dialogPlus.getHolderView();
                EditText name = myView.findViewById(R.id.hotelName);
                EditText location = myView.findViewById(R.id.hotelLocation);
                EditText email = myView.findViewById(R.id.hotelEmail);
                EditText mob = myView.findViewById(R.id.hotelMob);
                EditText rating = myView.findViewById(R.id.hotelRating);
                Button btnUpdate = myView.findViewById(R.id.update_btn);

                name.setText(model.getName());
                location.setText(model.getLocation());
                email.setText(model.getEmail());
                mob.setText(model.getMob());
                rating.setText(model.getRating());

                dialogPlus.show();

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("name", name.getText().toString());
                        map.put("location", location.getText().toString());
                        map.put("email", email.getText().toString());
                        map.put("mob", mob.getText().toString());
                        map.put("rating", rating.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("hotels")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });
            }
        });

        holder.imvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.hotelEmail.getContext());
                builder.setTitle("Delete Hotel");
                builder.setMessage("Are you sure to delete");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("hotels")
                                .child(getRef(position).getKey())
                                .removeValue();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.show();

            }
        });
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_list, parent, false);
        return new myviewholder(view);

    }

    class myviewholder extends RecyclerView.ViewHolder {
        TextView hotelName, hotelEmail, hotelMob, hotelLocation, hotelRating;
        ImageView imvEdit, imvDelete;

        public myviewholder(@NonNull View itemView) {
            super(itemView);

            hotelName = (TextView) itemView.findViewById(R.id.tvHotelName);
            hotelEmail = (TextView) itemView.findViewById(R.id.tvHotelID);
            hotelMob = (TextView) itemView.findViewById(R.id.tvHotelPhNo);
            hotelLocation = (TextView) itemView.findViewById(R.id.tvHotelAddress);
            hotelRating = (TextView)itemView.findViewById(R.id.tvHotelRating);
            imvEdit = (ImageView) itemView.findViewById(R.id.imvEdit);
            imvDelete = (ImageView) itemView.findViewById(R.id.imvDelete);

        }
    }
}
