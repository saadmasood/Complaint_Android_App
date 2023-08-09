package pk.com.ke.complaint.model.MaterialAddedResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MaterialAddedResponse {

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
