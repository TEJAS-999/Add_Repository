package com.example.add_room_repository;


import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import com.example.add_room_repository.adapter.RepositoryAdapter;
import com.example.add_room_repository.api.GithubApi;
import com.example.add_room_repository.api.RetrofitClient;
import com.example.add_room_repository.db.entity.RepositoryDatabase;
import com.example.add_room_repository.db.entity.RepositoryModel;
import java.util.ArrayList;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private final ArrayList<RepositoryModel> repositoryModelArrayList = new ArrayList<>();
    private RepositoryAdapter repositoryAdapter;
    private RepositoryDatabase repositoryDatabase;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        recyclerView = findViewById(R.id.recycler);

        repositoryDatabase = Room.databaseBuilder(getApplicationContext(), RepositoryDatabase.class, "RepositoryDB").allowMainThreadQueries().build();
        repositoryModelArrayList.addAll(repositoryDatabase.getRepositoryDao().getRepository());

        repositoryAdapter = new RepositoryAdapter(this, repositoryModelArrayList, MainActivity.this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(repositoryAdapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.add){
            addFetchedRepository();
        }
        return super.onOptionsItemSelected(item);
    }

    private void addFetchedRepository() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setTitle("Add Repository");

        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
        View view = inflater.inflate(R.layout.dialog_add_repository,null);

        final EditText ownerName = view.findViewById(R.id.input_owner);
        final EditText repositoryName = view.findViewById(R.id.input_name);
        alertDialogBuilder.setView(view);

        alertDialogBuilder.setCancelable(true);
        alertDialogBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String RepoName = repositoryName.getText().toString();
                String OwnerName = ownerName.getText().toString();
                    Retrofit retrofit = RetrofitClient.getClient();
                    GithubApi githubApi = retrofit.create(GithubApi.class);
                    Call<RepositoryModel> call = githubApi.getRepository(OwnerName, RepoName);
                    call.enqueue(new Callback<RepositoryModel>() {
                        @Override
                        public void onResponse(Call<RepositoryModel> call, Response<RepositoryModel> response) {
                            RepositoryModel repositoryModel = response.body();
                            if (repositoryModel != null) {
                                long id = repositoryDatabase.getRepositoryDao().addRepository(new RepositoryModel(
                                        repositoryModel.getName(),
                                        repositoryModel.getDescription(),
                                        repositoryModel.getHtml_url(),
                                        repositoryModel.getLogin(),
                                        0));
                                RepositoryModel repositoryModel1 = repositoryDatabase.getRepositoryDao().getRepository(id);
                                if (repositoryModel1 != null) {
                                    repositoryModelArrayList.add(0 , repositoryModel1);
                                    repositoryAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<RepositoryModel> call, Throwable t) {
                            Toast.makeText(MainActivity.this, "Failed adding to Database", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}