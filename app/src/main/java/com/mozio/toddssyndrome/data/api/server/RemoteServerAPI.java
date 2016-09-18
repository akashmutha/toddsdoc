package com.mozio.toddssyndrome.data.api.server;

import com.mozio.toddssyndrome.model.SuccessBaseResponse;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by mutha on 18/09/16.
 */

//Not used, Part of the api task but don't have an exact end point
public interface RemoteServerAPI {

    String PATH_USER = "/user";
    String PATH_INSERT_INFO = "/insert_info.php";

    @FormUrlEncoded
    @POST("/" + PATH_USER + PATH_INSERT_INFO)
    Observable<SuccessBaseResponse> insertUserInfo(@Field("name") String name, @Field("id") String id,
                                                   @Field("sex") String sex, @Field("age") int age,
                                                   @Field("has_migraines") boolean hasMigraines,
                                                   @Field("has_used_h_drugs") boolean hasUsedDrugs);
}
