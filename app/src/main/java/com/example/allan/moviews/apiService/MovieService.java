package com.example.allan.moviews.apiService;

import com.example.allan.moviews.model.MovieResponse;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Allan Pana on 19/02/18.
 *
 */

public class MovieService {

    private static final String BASE_URL = "http://api.themoviedb.org/3/";
    public static final String API_KEY = "67bd9236cf89d9b21b9fa775586c7453";

    /**
     * Interface containing the Endpoint method used to query the MovieDB API
     */
    public interface MovieApi {
        /**
         *
         * @param url end point url (ie. movie/top_rated)
         * @param apiKey API key from MovieDB
         * @return
         */
        @GET()
        Call<MovieResponse> getMovieResponse(@Url String url, @Query("api_key") String apiKey);
    }


    /**
     *
     * @return the MovieApi that will provide the response data from http(GET) call
     */
    public MovieApi getMovieApi(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(MovieApi.class);
    }

}
