package pk.com.ke.complaint.model.MaterialRequestBody;

import com.google.gson.JsonArray;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class D {

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
    private List<MaterialsSet> materialsSet = null;

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

    public List<MaterialsSet> getMaterialsSet() {
        return materialsSet;
    }

    public void setMaterialsSet(List<MaterialsSet> materialsSet) {
        this.materialsSet = materialsSet;
    }

}
