package com.andrey.rxjavaretrofittest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.Random;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.adapter.rxjava2.*;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    final String TAG = "mLog";

    TextView jokeTV;
    Button refreshButton;

    JokesService service;
    Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        service = createService();
        random = new Random();

        jokeTV = (TextView) findViewById(R.id.joke);
        refreshButton = (Button) findViewById(R.id.refresh);
        refreshButton.setOnClickListener(this);
        refreshButton.performClick();
    }


    private static JokesService createService()
    {
        return new Retrofit.Builder()
                .baseUrl("http://api.icndb.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(JokesService.class);
    }

    @Override
    public void onClick(View view) {
        //Log.d(TAG, "0");
        switch(view.getId())
        {
            case R.id.refresh:
             //   Log.d(TAG, "1");
                service.randomJokes(String.valueOf(random.nextInt(525)))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Joke>() {
                            @Override
                            public void accept(Joke joke) throws Exception {
                                Log.d(TAG, joke.getValue().getJoke());
                                jokeTV.setText(joke.getValue().getJoke());
                            }
                        });
                break;

        }
    }
}
