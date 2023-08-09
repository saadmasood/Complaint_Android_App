
package pk.com.ke.complaint.model.Count;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Counts implements Serializable, Parcelable
{

    @SerializedName("d")
    @Expose
    private D d;
    public final static Creator<Counts> CREATOR = new Creator<Counts>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Counts createFromParcel(Parcel in) {
            return new Counts(in);
        }

        public Counts[] newArray(int size) {
            return (new Counts[size]);
        }

    }
    ;
    private final static long serialVersionUID = 5562421979269843182L;

    protected Counts(Parcel in) {
        this.d = ((D) in.readValue((D.class.getClassLoader())));
    }

    public Counts() {
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
