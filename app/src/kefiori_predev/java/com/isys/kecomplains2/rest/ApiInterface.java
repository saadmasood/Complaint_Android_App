package pk.com.ke.complaint.rest;

/**
 * Created by Nadeem Iqbal on 10/24/17.
 */

import com.isys.kecomplains2.model.CompleteNotification.CompleteTicket.CompleteTickets;
import com.isys.kecomplains2.model.CompleteNotification.UnassignTicket.UnassignTickets;
import com.isys.kecomplains2.model.Count.Counts;
import com.isys.kecomplains2.model.Count.D;
import com.isys.kecomplains2.model.Login;
import com.isys.kecomplains2.model.SingleTicket.SingleTicketDetail;
import com.isys.kecomplains2.model.Ticket.Tickets;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiInterface {
//    @GET("/v2/59ef33842e000017181c5e13")
//    Call<ComplainResponse> getComplains();


    @GET("sap/opu/odata/sap/ZFM_PM_GANG_ODATA_SRV/GetAuthorization")
    Call<Login> login(@Query("IM_MODE") String mode, @Query("IM_EMPID") String empId,
                      @Query("IM_PWD") String pwd);


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
    Call<com.isys.kecomplains2.model.Tickets.Tickets> getTickets(@Query("IV_NOTIF") String notification,
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

}
