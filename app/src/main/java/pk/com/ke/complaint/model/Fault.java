//
//package com.isys.kecomplains2.model;
//
//import com.google.gson.annotations.Expose;
//import com.google.gson.annotations.SerializedName;
//
//import org.parceler.Parcel;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Parcel(analyze = Fault.class)
//public class Fault {
//
//    @SerializedName("NotificationNo ")
//    @Expose
//    private String notificationNo;
//    @SerializedName("PMT ")
//    @Expose
//    private String pMT;
//    @SerializedName("Fault ")
//    @Expose
//    private String fault;
//    @SerializedName("DTSId ")
//    @Expose
//    private String dTSId;
//    @SerializedName("NotificationStatus ")
//    @Expose
//    private String notificationStatus;
//    @SerializedName("Priority ")
//    @Expose
//    private String priority;
//    @SerializedName("x ")
//    @Expose
//    private String x;
//    @SerializedName("y ")
//    @Expose
//    private String y;
//    @SerializedName("Gang ")
//    @Expose
//    private String gang;
//    @SerializedName("Damage ")
//    @Expose
//    private String damage;
//    @SerializedName("Cause ")
//    @Expose
//    private String cause;
//    @SerializedName("Remarks ")
//    @Expose
//    private String remarks;
//    @SerializedName("Loggedinuser ")
//    @Expose
//    private String loggedinuser;
//    @SerializedName("Tickets")
//    @Expose
//    private List<Ticket> tickets = new ArrayList<>();
//
//    public String imageUrl = "";
//
//    public String getNotificationNo() {
//        return notificationNo;
//    }
//
//    public void setNotificationNo(String notificationNo) {
//        this.notificationNo = notificationNo;
//    }
//
//    public String getPMT() {
//        return pMT;
//    }
//
//    public void setPMT(String pMT) {
//        this.pMT = pMT;
//    }
//
//    public String getFault() {
//        return fault;
//    }
//
//    public void setFault(String fault) {
//        this.fault = fault;
//    }
//
//    public String getDTSId() {
//        return dTSId;
//    }
//
//    public void setDTSId(String dTSId) {
//        this.dTSId = dTSId;
//    }
//
//    public String getNotificationStatus() {
//        return notificationStatus;
//    }
//
//    public void setNotificationStatus(String notificationStatus) {
//        this.notificationStatus = notificationStatus;
//    }
//
//    public String getPriority() {
//        return priority;
//    }
//
//    public void setPriority(String priority) {
//        this.priority = priority;
//    }
//
//    public String getX() {
//        return x;
//    }
//
//    public void setX(String x) {
//        this.x = x;
//    }
//
//    public String getY() {
//        return y;
//    }
//
//    public void setY(String y) {
//        this.y = y;
//    }
//
//    public String getGang() {
//        return gang;
//    }
//
//    public void setGang(String gang) {
//        this.gang = gang;
//    }
//
//    public String getDamage() {
//        return damage;
//    }
//
//    public void setDamage(String damage) {
//        this.damage = damage;
//    }
//
//    public String getCause() {
//        return cause;
//    }
//
//    public void setCause(String cause) {
//        this.cause = cause;
//    }
//
//    public String getRemarks() {
//        return remarks;
//    }
//
//    public void setRemarks(String remarks) {
//        this.remarks = remarks;
//    }
//
//    public String getLoggedinuser() {
//        return loggedinuser;
//    }
//
//    public void setLoggedinuser(String loggedinuser) {
//        this.loggedinuser = loggedinuser;
//    }
//
//    public List<Ticket> getTickets() {
//        return tickets;
//    }
//
//    public void setTickets(List<Ticket> tickets) {
//        this.tickets = tickets;
//    }
//
//}
