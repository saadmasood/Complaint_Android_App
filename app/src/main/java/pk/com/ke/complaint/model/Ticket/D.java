
package pk.com.ke.complaint.model.Ticket;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class D implements Serializable, Parcelable
{

    @SerializedName("results")
    @Expose
    private List<Result> results = null;
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

    }
    ;
    private final static long serialVersionUID = -3374976698897619963L;

    protected D(Parcel in) {
        in.readList(this.results, (Result.class.getClassLoader()));
    }

    public D() {
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(results);
    }

    public int describeContents() {
        return  0;
    }

}
