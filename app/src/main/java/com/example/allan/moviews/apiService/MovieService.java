package com.example.allan.moviews.apiService;

import com.example.allan.moviews.model.MovieResponse;
import com.example.allan.moviews.model.Review;
import com.example.allan.moviews.model.ReviewResponse;
import com.example.allan.moviews.model.TrailerResponse;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Allan Pana on 19/02/18.
 * allan.pana74@gmail.com
 *
 */

public class MovieService {

    private static final String BASE_URL = "http://api.themoviedb.org/3/";

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

        /**
         *
         * @param id endpoint url path to get the Trailer response
         * @param apiKey API key from MovieDB
         * @return TrailerResponse
         */
        @GET("movie/{id}/videos")
        Call<TrailerResponse> getTrailerResponse(@Path("id") int id, @Query("api_key") String apiKey);

        /**
         *
         * @param id    endpoint url path to get the Review response
         * @param apiKey API key from MovieDB
         * @return ReviewResponse
         */
        @GET("movie/{id}/reviews")
        Call<ReviewResponse> getReviewResponse(@Path("id") int id, @Query("api_key") String apiKey);

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
