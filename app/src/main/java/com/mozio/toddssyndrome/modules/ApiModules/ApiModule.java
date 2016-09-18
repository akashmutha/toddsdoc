package com.mozio.toddssyndrome.modules.ApiModules;

import android.content.Context;

import com.google.gson.Gson;
import com.mozio.toddssyndrome.BuildConfig;
import com.mozio.toddssyndrome.R;
import com.mozio.toddssyndrome.data.api.server.RemoteServerAPI;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Provides;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;


/**
 * Created by mutha on 18/09/16.
 */

//Unused part of the remote api code (don't have an end point)
public class ApiModule {

    @Provides
    @Singleton
    @Named("API")
    RestAdapter provideRestAdapter(Gson gson, Context context) {
        return new RestAdapter.Builder()
                .setEndpoint(context.getString(R.string.server_address))
                .setConverter(new GsonConverter(gson))
                .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
                .build();
    }

    @Provides
    @Singleton
    RemoteServerAPI provideServerAPI(@Named("API") RestAdapter restAdapter) {
        return restAdapter.create(RemoteServerAPI.class);
    }
}
