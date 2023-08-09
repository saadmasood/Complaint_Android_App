
package pk.com.ke.complaint.model.login;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Login implements Serializable, Parcelable
{

    @SerializedName("d")
    @Expose
    private D d;
    public final static Creator<Login> CREATOR = new Creator<Login>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Login createFromParcel(Parcel in) {
            return new Login(in);
        }

        public Login[] newArray(int size) {
            return (new Login[size]);
        }

    }
    ;
    private final static long serialVersionUID = -8380483727005149718L;

    protected Login(Parcel in) {
        this.d = ((D) in.readValue((D.class.getClassLoader())));
    }

    public Login() {
    }

    public D getD() {
        return d;
    }

    public void setD(D d) {
        this.d = d;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(d);
    }

    public int describeContents() {
        return  0;
    }

}
