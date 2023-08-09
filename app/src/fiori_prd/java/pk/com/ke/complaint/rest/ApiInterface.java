package pk.com.ke.complaint.rest;

/**
 * Created by Nadeem Iqbal on 10/24/17.
 */

import pk.com.ke.complaint.model.CompleteNotification.CompleteTicket.CompleteTickets;
import pk.com.ke.complaint.model.CompleteNotification.UnassignTicket.UnassignTickets;
import pk.com.ke.complaint.model.Count.Counts;
import pk.com.ke.complaint.model.EstimateTimeResponse.EstimateTimeResponse;
import pk.com.ke.complaint.model.MaterialAddedResponse.MaterialAddedResponse;
import pk.com.ke.complaint.model.MaterialListResponse.MaterialList;
import pk.com.ke.complaint.model.MaterialRequestBody.MaterialRequestBody;
import pk.com.ke.complaint.model.login.Login;
import pk.com.ke.complaint.model.SingleTicket.SingleTicketDetail;

import okhttp3.ResponseBody;
import pk.com.ke.complaint.model.Tickets.Tickets;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;


public interface ApiInterface {
//    @GET("/v2/59ef33842e000017181c5e13")
//    Call<ComplainResponse> getComplains();


    @GET("sap/opu/odata/sap/ZFM_PM_GANG_ODATA_SRV/GetAuthorization")
    Call<Login> login(@Query("IM_MODE") String mode, @Query("IM_EMPID") String empId,
                      @Query("IM_PWD") String pwd, @Query("$format") String format);


    // New --> sap/opu/odata/sap/ZFM_PM_GANG_ODATA_SRV/TDetailSet
    // Old --> sap/opu/odata/sap/ZFM_PM_GANG_ODATA_SRV/JobSet
    @GET("sap/opu/odata/sap/ZFM_PM_GANG_ODATA_SRV/TDetailSet")
    Call<ResponseBody> faultList(@Query("$filter") String empId);

    //    ='DETAIL'&IM_QMNUM='000360000615'
    @GET("sap/opu/odata/sap/ZFM_PM_GANG_ODATA_SRV/GetNotificationDetails")
    Call<ResponseBody> getDetails(@Query("IM_MODE") String mode, @Query("IM_QMNUM") String notification_num);


    //    http://kefiori.ke.com.pk:8088/sap/opu/odata/sap/ZFM_PM_GANG_ODATA_SRV/UpdateNotificationStatus?
// IM_MODE='UPD_STAT'&IM_QMNUM='360000613'&IM_NEW_STAT='ASSG'&IM_FAULT='OH-FLTS'&IM_URGRP='B2-DRC'&IM_URCOD='0001'&IM_CODING='0002'
//UPD_STAT
    @GET("sap/opu/odata/sap/ZFM_PM_GANG_ODATA_SRV/UpdateNotificationStatus")
    Call<ResponseBody> updateStatus(@Query("IM_MODE") String mode, @Query("IM_QMNUM") String notificationNumber,
                                    @Query("IM_NEW_STAT") String newStatus, @Query("IM_FAULT") String faultCat,
                                    @Query("IM_CODING") String faultSubCat, @Query("IM_URGRP") String causeCat,
                                    @Query("IM_URCOD") String causeSubCat, @Query("IM_REQDT") String reqdt);


    //    http://172.16.100.155:8088/sap/opu/odata/sap/ZFM_PM_GANG_ODATA_SRV/NotifTicketCountSet?$filter=IM_EMP eq '088110'
    @GET("sap/opu/odata/sap/ZFM_PM_GANG_ODATA_SRV/NotifTicketCountSet")
    Call<Counts> getCounts(@Query("$filter") String userId,
                           @Query("$format") String format);

    // http://172.16.100.155:8088/sap/opu/odata/sap/ZWS_GET_NOTI_TICKET_SRV/getNotifiTicketSingle?IV_NOTIF=%27000360000424%27&$format=json
    @GET("sap/opu/odata/sap/ZWS_GET_NOTI_TICKET_SRV/getNotifiTicketSingle")
    Call<SingleTicketDetail> getSingleTicketDetail(@Query("IV_NOTIF") String notification_num,
                                                   @Query("$format") String format);

    // /sap/opu/odata/sap/ZWS_GET_NOTI_TICKET_SRV/getNotifiTicket?IV_NOTIF='000360000424'&$format=json
    @GET("/sap/opu/odata/sap/ZWS_GET_NOTI_TICKET_SRV/getNotifiTicket")
    Call<Tickets> getTickets(@Query("IV_NOTIF") String notification,
                             @Query("$format") String json);


    //    /sap/opu/odata/sap/ZWS_GET_NOTI_TICKET_SRV/ticketComplete?flag='D'&ticketNo='6000000123,600001202,6000023023'&reason='YR18'
    @GET("/sap/opu/odata/sap/ZWS_GET_NOTI_TICKET_SRV/ticketComplete")
    Call<CompleteTickets> completeTicket(@Query("flag") String flag, @Query("ticketNo") String tickets,
                                         @Query("cause") String fault, @Query("cause_code") String fault_code,
                                         @Query("$format") String json);

    //   /sap/opu/odata/sap/ZWS_GET_NOTI_TICKET_SRV/ticketDeassign?flag='D'&ticketNo='6000000123,600001202,6000023023'
    @GET("/sap/opu/odata/sap/ZWS_GET_NOTI_TICKET_SRV/ticketDeassign")
    Call<UnassignTickets> unassignTicket(@Query("flag") String flag, @Query("ticketNo") String tickets,
                                         @Query("$format") String json);

    // Adding by Zahra on 15 October 2021
    @POST("sap/opu/odata/sap/ZWS_GET_NOTI_TICKET_SRV/AddOrderCompSet")
    Call<MaterialAddedResponse> postMaterials(@Header("X-Requested-With") String request,
                                              @Header("Content-Type") String content,
                                              @Header("Accept") String accept,
                                              @Header("X-CSRF-Token") String csrf,
                                              @Body MaterialRequestBody requestBody);

    @GET
    Call<EstimateTimeResponse> updateEstimatedTime(@Url String url);

    @GET("sap/opu/odata/sap/ZWS_GET_NOTI_TICKET_SRV/ItMatSet")
    Call<MaterialList> allMaterialList(@Query("$format") String json);

}
