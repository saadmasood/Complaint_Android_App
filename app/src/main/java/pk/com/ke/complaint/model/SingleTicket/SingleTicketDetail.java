
package pk.com.ke.complaint.model.SingleTicket;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SingleTicketDetail implements Serializable, Parcelable
{

    @SerializedName("d")
    @Expose
    private D d;
    public final static Creator<SingleTicketDetail> CREATOR = new Creator<SingleTicketDetail>() {


        @SuppressWarnings({
            "unchecked"
        })
        public SingleTicketDetail createFromParcel(Parcel in) {
            return new SingleTicketDetail(in);
        }

        public SingleTicketDetail[] newArray(int size) {
            return (new SingleTicketDetail[size]);
        }

    }
    ;
    private final static long serialVersionUID = 1998447776458379672L;

    protected SingleTicketDetail(Parcel in) {
        this.d = ((D) in.readValue((D.class.getClassLoader())));
    }

    public SingleTicketDetail() {
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
