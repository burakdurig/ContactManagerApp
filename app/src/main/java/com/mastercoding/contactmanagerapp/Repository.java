package com.mastercoding.contactmanagerapp;

import android.app.Application;
import android.os.Looper;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import android.os.Handler;

public class Repository {
    // The Available Data Sources:
    // - ROOM Database

    // if we have more than 1 datasource's the repository isn't required.

    // this repo contacts an ADD, Delete, and List which need to be identical to the DAO

    private final ContactDAO  contactDAO;

    ExecutorService executor;
    Handler handler;

    public Repository(Application application) {
        // because of the use for LiveData we need to change the above inputs
        // from the ContactDOA to Application
        ContactDatabase contactDatabase = ContactDatabase.getInstance(application);
        this.contactDAO = contactDatabase.getContactDAO();

        // the executor service is a thread pool executor help
        // offload the repository services to background threads
        // you keep the UI thread free for user interactions.
        // the database operation will be operated sequential in a background thread
        // used for background database operations
        executor = Executors.newSingleThreadExecutor();

        //The Handler is required because you need to execute the background services
        // the getMainLooper will ensure the code gets executed on main thread

        // used for updating the UI
        handler = new Handler(Looper.getMainLooper());
    }

    // we need to reference all of the items from the DAO
    public void addContact(Contacts contact){
        // Runnable; executing Tasks on Separate Thread
        executor.execute(new Runnable() {
            @Override
            public void run() {
                // Execute this code asynchronously
                // on separate thread
                contactDAO.insert(contact);
            }
        });

    }

    public void deleteContact(Contacts contact){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                contactDAO.delete(contact);
            }
        });
    }

    public LiveData<List<Contacts>> getAllContacts(){
        // we need to use live data for the below to facilitate observation of database changes
        // then provide teh changes in real time to UI
        // you need to add the LiveData to the DOA
        return contactDAO.getAllContacts();
    }

}
