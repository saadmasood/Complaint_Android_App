package pk.com.ke.complaint.model.MaterialAddedResponse;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class D {

    @SerializedName("__metadata")
    @Expose
    private MetaData metadata;
    @SerializedName("OrderNo")
    @Expose
    private String orderNo;
    @SerializedName("NotifNo")
    @Expose
    private String notifNo;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("MaterialsSet")
    @Expose
    private MaterialsSet materialsSet;

    public MetaData getMetadata() {
        return metadata;
    }

    public void setMetadata(MetaData metadata) {
        this.metadata = metadata;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getNotifNo() {
        return notifNo;
    }

    public void setNotifNo(String notifNo) {
        this.notifNo = notifNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MaterialsSet getMaterialsSet() {
        return materialsSet;
    }

    public void setMaterialsSet(MaterialsSet materialsSet) {
        this.materialsSet = materialsSet;
    }

}
