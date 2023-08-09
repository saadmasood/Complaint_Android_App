
package pk.com.ke.complaint.model.CompleteNotification.UnassignTicket;

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
    @SerializedName("ObjectId")
    @Expose
    private String objectId;
    @SerializedName("Zexception")
    @Expose
    private String zexception;
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
    private final static long serialVersionUID = -1301240728559119381L;

    protected Result(Parcel in) {
        this.metadata = ((Metadata) in.readValue((Metadata.class.getClassLoader())));
        this.objectId = ((String) in.readValue((String.class.getClassLoader())));
        this.zexception = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Result() {
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getZexception() {
        return zexception;
    }

    public void setZexception(String zexception) {
        this.zexception = zexception;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(metadata);
        dest.writeValue(objectId);
        dest.writeValue(zexception);
    }

    public int describeContents() {
        return  0;
    }

}
