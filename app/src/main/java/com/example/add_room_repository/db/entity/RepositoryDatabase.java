package com.example.add_room_repository.db.entity;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.add_room_repository.db.RepositoryDao;

@Database(entities = {RepositoryModel.class}, version = 1)
public abstract class RepositoryDatabase extends RoomDatabase {

    public abstract RepositoryDao getRepositoryDao();

}
