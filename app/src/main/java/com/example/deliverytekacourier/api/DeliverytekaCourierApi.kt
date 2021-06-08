package com.example.deliverytekacourier.api


import com.example.deliverytekacourier.data.models.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface DeliverytekaCourierApi {

    @POST("login.php")
    suspend fun login(@Body requestAccess: RequestAccess): CourierShortInfo

    @GET("start_work_shift.php")
    suspend fun startShift(@Query("courier_id") courierId: Int, @Query("hours") hours: Int): Shift

    @GET("check_online.php ")
    suspend fun checkOnline(@Query("courier_id") courierId: Int): Shift

    @GET("get_orders.php")
    suspend fun getOrders(@Query("courier_id") courierId: Int): Orders

    @GET("get_order_content.php")
    suspend fun getOrderContent(@Query("order_id") orderId: String): Medicine

    @GET("accept_order.php")
    suspend fun acceptOrder(@Query("courier_id") courierId: Int,@Query("order_id") orderId: String)


    @GET("get_courier.php")
    suspend fun getCourier(@Query("courier_id") courierId: Int):Courier

}