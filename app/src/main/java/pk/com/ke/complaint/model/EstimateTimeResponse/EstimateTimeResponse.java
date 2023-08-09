package pk.com.ke.complaint.model.EstimateTimeResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EstimateTimeResponse {
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
