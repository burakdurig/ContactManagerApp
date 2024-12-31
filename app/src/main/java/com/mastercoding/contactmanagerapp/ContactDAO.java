package com.mastercoding.contactmanagerapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

// DAO stands for Data Access Object pattern which in Java is a design
// pattern that abstracts the details of database interactions from
// the rest of the application.

@Dao
public interface ContactDAO {
    @Insert
    void insert(Contacts contacts);

    @Delete
    void delete(Contacts contacts);

    @Query("SELECT * FROM contacts_table")
    LiveData<List<Contacts>> getAllContacts();
    // Always use livedata when using the select function.


}
