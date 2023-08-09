
package pk.com.ke.complaint.model.CompleteNotification.CompleteTicket;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CompleteTickets implements Serializable, Parcelable
{

    @SerializedName("d")
    @Expose
    private D d;
    public final static Creator<CompleteTickets> CREATOR = new Creator<CompleteTickets>() {


        @SuppressWarnings({
            "unchecked"
        })
        public CompleteTickets createFromParcel(Parcel in) {
            return new CompleteTickets(in);
        }

        public CompleteTickets[] newArray(int size) {
            return (new CompleteTickets[size]);
        }

    }
    ;
    private final static long serialVersionUID = 5717555493605915209L;

    protected CompleteTickets(Parcel in) {
        this.d = ((D) in.readValue((D.class.getClassLoader())));
    }

    public CompleteTickets() {
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
