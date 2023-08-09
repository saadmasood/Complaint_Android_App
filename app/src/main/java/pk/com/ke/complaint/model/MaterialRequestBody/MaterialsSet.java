package pk.com.ke.complaint.model.MaterialRequestBody;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MaterialsSet {


    @SerializedName("NotifNo")
    @Expose
    private String notifNo;
    @SerializedName("Matnr")
    @Expose
    private String matnr;
    @SerializedName("Quantity")
    @Expose
    private String quantity;

   public String getNotifNo() {
        return notifNo;
    }

    public void setNotifNo(String notifNo) {
        this.notifNo = notifNo;
    }

    public String getMatnr() {
        return matnr;
    }

    public void setMatnr(String matnr) {
        this.matnr = matnr;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

}
