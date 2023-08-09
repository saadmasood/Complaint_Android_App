
package pk.com.ke.complaint.model.Ticket;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Result implements Serializable, Parcelable
{

    @SerializedName("__metadata")
    @Expose
    private Metadata metadata;
    @SerializedName("ticket_num")
    @Expose
    private String ticketNum;
    @SerializedName("PostingDate")
    @Expose
    private String postingDate;
    @SerializedName("pmt_id")
    @Expose
    private String pmtId;
    @SerializedName("feeder_id")
    @Expose
    private String feederId;
    @SerializedName("notification")
    @Expose
    private String notification;
    @SerializedName("Feeder")
    @Expose
    private String feeder;
    @SerializedName("pmt")
    @Expose
    private String pmt;
    @SerializedName("cli")
    @Expose
    private String cli;
    @SerializedName("ca")
    @Expose
    private String ca;
    @SerializedName("notif")
    @Expose
    private String notif;
    @SerializedName("customer_name")
    @Expose
    private String customerName;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("subject")
    @Expose
    private String subject;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("tat")
    @Expose
    private String tat;
    @SerializedName("lineman")
    @Expose
    private String lineman;
    @SerializedName("mtl")
    @Expose
    private String mtl;
    @SerializedName("sup")
    @Expose
    private String sup;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created_by")
    @Expose
    private String createdBy;
    @SerializedName("reopen")
    @Expose
    private String reopen;
    public final static Creator<Result> CREATOR = new Creator<Result>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Result createFromParcel(Parcel in) {
            return new Result(in);
        }

        public Result[] newArray(int size) {
            return (new Result[size]);
        }

    }
    ;
    private final static long serialVersionUID = 643225634640016967L;

    protected Result(Parcel in) {
        this.metadata = ((Metadata) in.readValue((Metadata.class.getClassLoader())));
        this.ticketNum = ((String) in.readValue((String.class.getClassLoader())));
        this.postingDate = ((String) in.readValue((String.class.getClassLoader())));
        this.pmtId = ((String) in.readValue((String.class.getClassLoader())));
        this.feederId = ((String) in.readValue((String.class.getClassLoader())));
        this.notification = ((String) in.readValue((String.class.getClassLoader())));
        this.feeder = ((String) in.readValue((String.class.getClassLoader())));
        this.pmt = ((String) in.readValue((String.class.getClassLoader())));
        this.cli = ((String) in.readValue((String.class.getClassLoader())));
        this.ca = ((String) in.readValue((String.class.getClassLoader())));
        this.notif = ((String) in.readValue((String.class.getClassLoader())));
        this.customerName = ((String) in.readValue((String.class.getClassLoader())));
        this.address = ((String) in.readValue((String.class.getClassLoader())));
        this.subject = ((String) in.readValue((String.class.getClassLoader())));
        this.createdAt = ((String) in.readValue((String.class.getClassLoader())));
        this.createdDate = ((String) in.readValue((String.class.getClassLoader())));
        this.tat = ((String) in.readValue((String.class.getClassLoader())));
        this.lineman = ((String) in.readValue((String.class.getClassLoader())));
        this.mtl = ((String) in.readValue((String.class.getClassLoader())));
        this.sup = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.createdBy = ((String) in.readValue((String.class.getClassLoader())));
        this.reopen = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Result() {
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public String getTicketNum() {
        return ticketNum;
    }

    public void setTicketNum(String ticketNum) {
        this.ticketNum = ticketNum;
    }

    public String getPostingDate() {
        return postingDate;
    }

    public void setPostingDate(String postingDate) {
        this.postingDate = postingDate;
    }

    public String getPmtId() {
        return pmtId;
    }

    public void setPmtId(String pmtId) {
        this.pmtId = pmtId;
    }

    public String getFeederId() {
        return feederId;
    }

    public void setFeederId(String feederId) {
        this.feederId = feederId;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getFeeder() {
        return feeder;
    }

    public void setFeeder(String feeder) {
        this.feeder = feeder;
    }

    public String getPmt() {
        return pmt;
    }

    public void setPmt(String pmt) {
        this.pmt = pmt;
    }

    public String getCli() {
        return cli;
    }

    public void setCli(String cli) {
        this.cli = cli;
    }

    public String getCa() {
        return ca;
    }

    public void setCa(String ca) {
        this.ca = ca;
    }

    public String getNotif() {
        return notif;
    }

    public void setNotif(String notif) {
        this.notif = notif;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getTat() {
        return tat;
    }

    public void setTat(String tat) {
        this.tat = tat;
    }

    public String getLineman() {
        return lineman;
    }

    public void setLineman(String lineman) {
        this.lineman = lineman;
    }

    public String getMtl() {
        return mtl;
    }

    public void setMtl(String mtl) {
        this.mtl = mtl;
    }

    public String getSup() {
        return sup;
    }

    public void setSup(String sup) {
        this.sup = sup;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getReopen() {
        return reopen;
    }

    public void setReopen(String reopen) {
        this.reopen = reopen;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(metadata);
        dest.writeValue(ticketNum);
        dest.writeValue(postingDate);
        dest.writeValue(pmtId);
        dest.writeValue(feederId);
        dest.writeValue(notification);
        dest.writeValue(feeder);
        dest.writeValue(pmt);
        dest.writeValue(cli);
        dest.writeValue(ca);
        dest.writeValue(notif);
        dest.writeValue(customerName);
        dest.writeValue(address);
        dest.writeValue(subject);
        dest.writeValue(createdAt);
        dest.writeValue(createdDate);
        dest.writeValue(tat);
        dest.writeValue(lineman);
        dest.writeValue(mtl);
        dest.writeValue(sup);
        dest.writeValue(status);
        dest.writeValue(createdBy);
        dest.writeValue(reopen);
    }

    public int describeContents() {
        return  0;
    }

}
