package com.sathya.mvvm_newx;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

class WordRepository {

    // Kotlin
    // Flow -> emit  -> Collect   will take care of the delay in passing or receiving the data

    private WordDao mWordDao;
    private LiveData<List<Word>> mAllWords;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    WordRepository(Application application) {
        WordRoomDatabase db = WordRoomDatabase.getDatabase(application);
        mWordDao = db.wordDao();
        mAllWords = mWordDao.getAlphabetizedWords();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<Word>> getAllWords() {
        return mAllWords;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    void insert(Word word) {
        WordRoomDatabase.databaseWriteExecutor.execute(() -> {
            mWordDao.insert(word);
        });
    }

//    xx(){
//        WordRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
//            @Override
//            public void run() {
//                mWordDao.insert(word);
//            }
//        });
//    }


    /*  // Legagcy App

    void insert(Word word) {
        new insertAsyncTask(mWordDao).execute(word);
    }

    //6. There is nothing magical about the AsyncTask, so here it is for you to copy.
    private static class insertAsyncTask extends AsyncTask<Word, Void, Void> {

        private WordDao mAsyncTaskDao;

        insertAsyncTask(WordDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Word... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

     */
}