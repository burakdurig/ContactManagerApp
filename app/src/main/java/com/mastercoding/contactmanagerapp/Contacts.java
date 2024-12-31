package com.mastercoding.contactmanagerapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// if no variable is assigned to @Entity, then it will automatically be assigned to class name
@Entity(tableName = "contacts_table")
public class Contacts {
    // the below @ColumnInfo will automatically assign table name without using the variable name
    @ColumnInfo(name = "contact_id")
    @PrimaryKey(autoGenerate = true)
    //The above is for SQLight
    private int id;
    @ColumnInfo(name = "contact_name")
    private String name;
    @ColumnInfo(name = "contact_email")
    private String email;

    public Contacts(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Contacts() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
