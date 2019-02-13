package com.codingwithmitch.foodrecipes.persistence;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.codingwithmitch.foodrecipes.AppExecutors;
import com.codingwithmitch.foodrecipes.models.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeCacheClient {

    private static final String TAG = "RecipeCacheClient";

    private static RecipeCacheClient instance;
    private MutableLiveData<List<Recipe>> mRecipes = new MutableLiveData<>();

    public static RecipeCacheClient getInstance(){
        if(instance == null){
            instance = new RecipeCacheClient();
        }
        return instance;
    }

    public LiveData<List<Recipe>> getRecipes(){
        return mRecipes;
    }

    public void searchLocalCache(final RecipeDao recipeDao, final String query, final int pageNumber){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                List<Recipe> list = new ArrayList<>();
                for(String word: query.split(" ")){
                    Log.d(TAG, "run: word: " + word);
                    list.addAll(recipeDao.searchRecipes(word, pageNumber));
                }
                mRecipes.postValue(list);
            }
        });
    }

}













