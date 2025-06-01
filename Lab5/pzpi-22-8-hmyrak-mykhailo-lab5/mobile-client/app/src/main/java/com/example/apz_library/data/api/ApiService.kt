package com.example.apz_library.data.api

import com.example.apz_library.data.models.LoginRequest
import com.example.apz_library.data.models.LoginResponse
import com.example.apz_library.data.models.RegisterRequest
import com.example.apz_library.data.models.RegisterResponse
import com.example.apz_library.data.models.Book
import com.example.apz_library.data.models.GiveBookRequest
import com.example.apz_library.data.models.Item
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import retrofit2.http.DELETE
import retrofit2.http.Path
import com.example.apz_library.data.models.Reader
import com.example.apz_library.data.models.Person
import com.example.apz_library.data.models.StatsResponse
import retrofit2.http.PUT
import com.example.apz_library.data.models.ReturnBookRequest

interface ApiService {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>

    @GET("books")
    suspend fun getBooks(@Header("Authorization") token: String): Response<List<Book>>

    @GET("books/search")
    suspend fun searchBooks(
        @Header("Authorization") token: String,
        @Query("title") title: String? = null,
        @Query("publish") publish: String? = null,
        @Query("categoryId") categoryId: Int? = null
    ): Response<List<Book>>

    @POST("books")
    suspend fun addBook(@Body book: Book): Book

    @DELETE("books/{id}")
    suspend fun deleteBook(
        @Header("Authorization") token: String,
        @Path("id") bookId: Int
    ): Response<Unit>

    @GET("readers")
    suspend fun getReaders(@Header("Authorization") token: String): retrofit2.Response<List<Reader>>

    @DELETE("readers/{id}")
    suspend fun deleteReader(
        @Header("Authorization") token: String,
        @Path("id") readerId: Int
    ): retrofit2.Response<Unit>

    @GET("persons")
    suspend fun getPersons(): List<Person>

    @GET("persons/{id}")
    suspend fun getPerson(@Path("id") id: Int): Person

    @GET("persons/search")
    suspend fun searchPersons(
        @Query("name") name: String? = null,
        @Query("dateOfBirth") dateOfBirth: String? = null,
        @Query("dateOfDeath") dateOfDeath: String? = null,
        @Query("country") country: String? = null,
        @Query("isReal") isReal: Boolean? = null
    ): List<Person>

    @DELETE("persons/{id}")
    suspend fun deletePerson(@Path("id") id: Int)

    @POST("readers")
    suspend fun addReader(@Body reader: Reader): Reader

    @POST("persons")
    suspend fun addPerson(@Body person: Person): Person

    @PUT("books/{id}")
    suspend fun updateBook(@Path("id") id: Int, @Body book: Book): Book

    @GET("books/{id}")
    suspend fun getBook(@Path("id") id: Int): Book

    @PUT("readers/{id}")
    suspend fun updateReader(@Path("id") id: Int, @Body reader: Reader): Reader

    @GET("readers/{id}")
    suspend fun getReader(@Path("id") id: Int): Reader

    @PUT("persons/{id}")
    suspend fun updatePerson(@Path("id") id: Int, @Body person: Person): Person

    @GET("stat")
    suspend fun getStats(): StatsResponse


    @GET("items")
    suspend fun getItems(): List<Item>

    @GET("items/{id}")
    suspend fun getItem(@Path("id") id: Int): Item

    @POST("items")
    suspend fun addItem(@Body item: Item): Item

    @PUT("items/{id}")
    suspend fun updateItem(@Path("id") id: Int, @Body item: Item): Item

    @DELETE("items/{id}")
    suspend fun deleteItem(@Path("id") id: Int)

    @POST("business/giveout")
    suspend fun giveBook(@Body request: GiveBookRequest): Int

    @POST("business/return")
    suspend fun returnBook(@Body request: ReturnBookRequest): Int

    @GET("readers")
    suspend fun getReaders(): List<Reader>



}