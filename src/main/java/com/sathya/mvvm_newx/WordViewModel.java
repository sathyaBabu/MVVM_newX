package com.sathya.mvvm_newx;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

// Retrofit
public class WordViewModel extends AndroidViewModel {

    private WordRepository mRepository;

    private final LiveData<List<Word>> mAllWords;

    public WordViewModel (Application application) {
        super(application);
        mRepository = new WordRepository(application);
        mAllWords = mRepository.getAllWords();
    }




    // Main activity observes the changes in getAllWords() // Looking UP( From the diagram )

    LiveData<List<Word>> getAllWords() {
        return mAllWords;
    }

    // Writing the data via repository Looking Down( From the diagram )

    public void insert(Word word) {
        mRepository.insert(word);
    }
}