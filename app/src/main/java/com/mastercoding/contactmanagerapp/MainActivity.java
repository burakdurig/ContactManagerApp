package com.mastercoding.contactmanagerapp;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mastercoding.contactmanagerapp.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    // Data Source
    private ContactDatabase contactDatabase;
    private ArrayList<Contacts> contactsArrayList = new ArrayList<>();

    // Adapter
    private MyAdapter myAdapter;

    // Binding
    private ActivityMainBinding mainBinding;
    private MainActivityClickHandlers handlers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Data Binding
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        handlers = new MainActivityClickHandlers(this);

        mainBinding.setClickHandler(handlers);

        // RecyclerView: mainBinding = object
        RecyclerView recyclerView = mainBinding.recyclerview;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);



        // Data Source
        contactDatabase = ContactDatabase.getInstance(this);

        // View Model
        MyViewModel viewModel = new ViewModelProvider(this).get(MyViewModel.class);

        // Inserting a new Contact (Just for Testing):
        //Contacts c1 = new Contacts("Jack","jack@gmail.com");
        //viewModel.addNewContact(c1);

        // Loading the Data from ROOM DB
        viewModel.getAllContacts().observe(this,
                new Observer<List<Contacts>>() {
                    @Override
                    public void onChanged(List<Contacts> contacts) {

                        contactsArrayList.clear();

                        for (Contacts c: contacts){
                            Log.v("TAGY", c.getName());
                            contactsArrayList.add(c);
                        }


                        myAdapter.notifyDataSetChanged();
                    }
                });

        // myadapter
        myAdapter = new MyAdapter(contactsArrayList);

        // Linking the RecyclerView with teh Adapter
        recyclerView.setAdapter(myAdapter);

        // swipe to delete
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                // if you swipe the item to the left
                Contacts c = contactsArrayList.get(viewHolder.getAdapterPosition());

                viewModel.deleteContact(c);
            }
        }).attachToRecyclerView(recyclerView);

    }
}