package com.example.add_room_repository.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.add_room_repository.MainActivity;
import com.example.add_room_repository.R;
import com.example.add_room_repository.db.entity.RepositoryModel;

import java.util.ArrayList;

public class RepositoryAdapter extends RecyclerView.Adapter<RepositoryAdapter.ViewHolder> {

    Context context;
    ArrayList<RepositoryModel> repositoryModelArrayList;
    MainActivity mainActivity;

    public RepositoryAdapter(Context context, ArrayList<RepositoryModel> repositoryModelArrayList, MainActivity mainActivity) {
        this.context = context;
        this.repositoryModelArrayList = repositoryModelArrayList;
        this.mainActivity = mainActivity;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nameTextView;
        TextView descriptionTextView;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.text_name);
            descriptionTextView = itemView.findViewById(R.id.text_description);

            }
    }

    @NonNull
    @Override
    public RepositoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_repository, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RepositoryAdapter.ViewHolder holder, int position) {
        RepositoryModel repositoryModel = repositoryModelArrayList.get(position);
        holder.nameTextView.setText(repositoryModel.getName());
        holder.descriptionTextView.setText(repositoryModel.getDescription());


        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                String sb = repositoryModel.getName() +
                        "\n" +
                        repositoryModel.getHtml_url() +
                        "\n\n";

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, sb);
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                v.getContext().startActivity(shareIntent);

                return true;
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(repositoryModel.getHtml_url()));
                view.getContext().startActivity(browserIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return repositoryModelArrayList.size();
    }
}
