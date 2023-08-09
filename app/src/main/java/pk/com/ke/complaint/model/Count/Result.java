
package pk.com.ke.complaint.model.Count;

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
    @SerializedName("IM_EMP")
    @Expose
    private String iMEMP;
    @SerializedName("QMNUM")
    @Expose
    private String qMNUM;
    @SerializedName("COUNT")
    @Expose
    private String cOUNT;
    @SerializedName("ECOUNT")
    @Expose
    private String eCOUNT;
    public final static Parcelable.Creator<Result> CREATOR = new Creator<Result>() {


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
    private final static long serialVersionUID = -3038810669646071359L;

    protected Result(Parcel in) {
        this.metadata = ((Metadata) in.readValue((Metadata.class.getClassLoader())));
        this.iMEMP = ((String) in.readValue((String.class.getClassLoader())));
        this.qMNUM = ((String) in.readValue((String.class.getClassLoader())));
        this.cOUNT = ((String) in.readValue((String.class.getClassLoader())));
        this.eCOUNT = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Result() {
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public String getIMEMP() {
        return iMEMP;
    }

    public void setIMEMP(String iMEMP) {
        this.iMEMP = iMEMP;
    }

    public String getQMNUM() {
        return qMNUM;
    }

    public void setQMNUM(String qMNUM) {
        this.qMNUM = qMNUM;
    }

    public String getCOUNT() {
        return cOUNT;
    }

    public void setCOUNT(String cOUNT) {
        this.cOUNT = cOUNT;
    }

    public String getECOUNT() {
        return eCOUNT;
    }

    public void setECOUNT(String eCOUNT) {
        this.eCOUNT = eCOUNT;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(metadata);
        dest.writeValue(iMEMP);
        dest.writeValue(qMNUM);
        dest.writeValue(cOUNT);
        dest.writeValue(eCOUNT);
    }

    public int describeContents() {
        return 0;
    }

}
