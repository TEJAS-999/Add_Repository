package com.example.add_room_repository.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "repositories")
public class RepositoryModel {
    @ColumnInfo(name = "repository_name")
    private String name;
    @ColumnInfo(name = "repository_description")
    private String description;
    @ColumnInfo(name = "repository_html_url")
    private String html_url;
    @ColumnInfo(name = "repository_login")
    private String login;
    @ColumnInfo(name = "repository_id")
    @PrimaryKey(autoGenerate = true)
    private int id;

    @Ignore
    public RepositoryModel(String repoName, String repoDescription, String html_url, String login) {

    }

    public RepositoryModel(String name, String description, String html_url, String login, int id) {
        this.name = name;
        this.description = description;
        this.html_url = html_url;
        this.login = login;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
