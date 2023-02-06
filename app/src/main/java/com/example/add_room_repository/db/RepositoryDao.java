package com.example.add_room_repository.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.add_room_repository.db.entity.RepositoryModel;

import java.util.List;

@Dao
public interface RepositoryDao {

    @Insert
    public long addRepository(RepositoryModel repositoryModel);

    @Query("select * from repositories")
    public List<RepositoryModel> getRepository();

    @Query("select * from repositories where repository_id == :repoID")
    public RepositoryModel getRepository(long repoID);

}
