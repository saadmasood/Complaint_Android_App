package pk.com.ke.complaint.model.MaterialRequestBody;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MaterialRequestBody {

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