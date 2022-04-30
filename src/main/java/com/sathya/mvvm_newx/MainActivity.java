package com.sathya.mvvm_newx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
// https://developer.android.com/codelabs/android-room-with-a-view#0
/*


        prerequisite : SQL( ROOM ) - Recycler view - REST - MVC

        Model View Controller

        MVVM

        M model POJO CLASS
        V Main Activity
        vm deals with the live data

        CORE : Provider -> we got the handle to observe the latest data ( return SQL/ REST )
        Model : POJO
        Repository : fetch the data from API or from DB
        : Will handshake between  V - VM  ( V - R - VM )
        ViewModel - extend ViewModel  to get Livedata from repository
        Activity : Observe ViewModel for changes ( He displays the data )


        androidX - JetPack lib..  Codelabs

        https://developer.android.com/codelabs/android-room-with-a-view#0


        */
public class MainActivity extends AppCompatActivity {

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    private WordViewModel mWordViewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        final WordListAdapter adapter = new WordListAdapter(new WordListAdapter.WordDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        mWordViewModel = new ViewModelProvider(this).get(WordViewModel.class);
       // mWordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);


        mWordViewModel.getAllWords().observe(this, words -> {
            // Update the cached copy of the words in the adapter.
            adapter.submitList(words);
        });

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener( view -> {
            Intent intent = new Intent(MainActivity.this, NewWordActivity.class);
            startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
        });

    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Word word = new Word(data.getStringExtra(NewWordActivity.EXTRA_REPLY));
            mWordViewModel.insert(word);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }
}