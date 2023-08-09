
package pk.com.ke.complaint.model.login;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class D implements Serializable, Parcelable {

    @SerializedName("__metadata")
    @Expose
    private Metadata metadata;
    @SerializedName("EX_MESSAGE")
    @Expose
    private String message;
    @SerializedName("EX_VALID")
    @Expose
    private String valid;
    @SerializedName("EX_USER_NAME")
    @Expose
    private String userName;
    public final static Creator<D> CREATOR = new Creator<D>() {


        @SuppressWarnings({
                "unchecked"
        })
        public D createFromParcel(Parcel in) {
            return new D(in);
        }

        public D[] newArray(int size) {
            return (new D[size]);
        }

    };
    private final static long serialVersionUID = -6443892529386594041L;

    protected D(Parcel in) {
        this.metadata = ((Metadata) in.readValue((Metadata.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.valid = ((String) in.readValue((String.class.getClassLoader())));
        this.userName = ((String) in.readValue((String.class.getClassLoader())));
    }

    public D() {
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(metadata);
        dest.writeValue(message);
        dest.writeValue(valid);
        dest.writeValue(userName);
    }

    public int describeContents() {
        return 0;
    }

}
