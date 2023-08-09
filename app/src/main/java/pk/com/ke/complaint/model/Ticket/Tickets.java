
package pk.com.ke.complaint.model.Ticket;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Tickets implements Serializable, Parcelable
{

    @SerializedName("d")
    @Expose
    private D d;
    public final static Creator<Tickets> CREATOR = new Creator<Tickets>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Tickets createFromParcel(Parcel in) {
            return new Tickets(in);
        }

        public Tickets[] newArray(int size) {
            return (new Tickets[size]);
        }

    }
    ;
    private final static long serialVersionUID = -3873056660844170769L;

    protected Tickets(Parcel in) {
        this.d = ((D) in.readValue((D.class.getClassLoader())));
    }

    public Tickets() {
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
