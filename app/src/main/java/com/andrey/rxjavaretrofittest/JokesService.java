package com.andrey.rxjavaretrofittest;

import android.support.annotation.NonNull;


import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by sts on 18.09.17.
 */

public interface JokesService {

    @GET("jokes/{number}")
    Observable<Joke> randomJokes(@Path("number") String number); //The following code makes a GET request to the Github URL and returns an Observable.
}



