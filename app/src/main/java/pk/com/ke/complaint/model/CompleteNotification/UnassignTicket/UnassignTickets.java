
package pk.com.ke.complaint.model.CompleteNotification.UnassignTicket;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UnassignTickets implements Serializable, Parcelable
{

    @SerializedName("d")
    @Expose
    private D d;
    public final static Creator<UnassignTickets> CREATOR = new Creator<UnassignTickets>() {


        @SuppressWarnings({
            "unchecked"
        })
        public UnassignTickets createFromParcel(Parcel in) {
            return new UnassignTickets(in);
        }

        public UnassignTickets[] newArray(int size) {
            return (new UnassignTickets[size]);
        }

    }
    ;
    private final static long serialVersionUID = 6618254741619654461L;

    protected UnassignTickets(Parcel in) {
        this.d = ((D) in.readValue((D.class.getClassLoader())));
    }

    public UnassignTickets() {
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
