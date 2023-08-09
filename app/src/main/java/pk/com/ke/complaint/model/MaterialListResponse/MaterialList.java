package pk.com.ke.complaint.model.MaterialListResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MaterialList {
    @SerializedName("d")
    @Expose
    private D d;

    public D getD() {
        return d;
    }

    public void setD(D d) {
        this.d = d;
    }
}
