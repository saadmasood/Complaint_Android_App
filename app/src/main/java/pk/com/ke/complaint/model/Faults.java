package pk.com.ke.complaint.model;

import pk.com.ke.complaint.Config;

import org.parceler.Parcel;
import org.parceler.ParcelConverter;
import org.parceler.ParcelPropertyConverter;
import org.parceler.Parcels;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iqbal.nadeem on 06-Feb-18.
 */

@Parcel
@Root(name = "feed", strict = false)
@NamespaceList({
        @Namespace(reference = Config.BASE_URL+"sap/opu/odata/sap/ZFM_PM_GANG_ODATA_SRV/", prefix = "base"),
        @Namespace(reference = "http://www.w3.org/2005/Atom"),
        @Namespace(reference = "http://schemas.microsoft.com/ado/2007/08/dataservices/metadata", prefix = "m"),
        @Namespace(reference = "http://schemas.microsoft.com/ado/2007/08/dataservices", prefix = "d")
})
public class Faults {
    public Faults() {
    } // empty constructor required

    @ParcelPropertyConverter(ItemListParcelConverter.class)
    @ElementList(inline = true, required = false)
    List<entry> faults;

    public List<entry> getFaults() {
        return faults;
    }

    @Parcel
    public static class entry {
        public entry() {
        } // empty constructor required

        @Element(name = "content", data = true, required = false)
        Content content;

        public Content getContent() {
            return content;
        }

        @Parcel
        public static class Content {

            public Content() {
            } // empty constructor required


            @Element(name = "properties")
            @Namespace(prefix = "m", reference = "http://schemas.microsoft.com/ado/2007/08/dataservices/metadata")
            Properties properties;

            public Properties getProperties() {
                return properties;
            }

            public void setProperties(Properties properties) {
                this.properties = properties;
            }
        }

        @Parcel
        public static class Properties {
            public Properties() {
            } // empty constructor required


            @Element(name = "ImEmpid", required = false)
            @Namespace(prefix = "d", reference = "http://schemas.microsoft.com/ado/2007/08/dataservices")
            String empId;

            @Element(name = "Qmnum", required = false)
            @Namespace(prefix = "d", reference = "http://schemas.microsoft.com/ado/2007/08/dataservices")
            String notification_num;

            @Element(name = "Aufnr", required = false)
            @Namespace(prefix = "d", reference = "http://schemas.microsoft.com/ado/2007/08/dataservices")
            String aufner;

            @Element(name = "Qmtxt", required = false)
            @Namespace(prefix = "d", reference = "http://schemas.microsoft.com/ado/2007/08/dataservices")
            String complaint;

            @Element(name = "Strmn", required = false)
            @Namespace(prefix = "d", reference = "http://schemas.microsoft.com/ado/2007/08/dataservices")
            String date;

            @Element(name = "Strur", required = false)
            @Namespace(prefix = "d", reference = "http://schemas.microsoft.com/ado/2007/08/dataservices")
            String time;

            @Element(name = "HomeCity", required = false)
            @Namespace(prefix = "d", reference = "http://schemas.microsoft.com/ado/2007/08/dataservices")
            String address;

            @Element(name = "UserStatus", required = false)
            @Namespace(prefix = "d", reference = "http://schemas.microsoft.com/ado/2007/08/dataservices")
            String status;


            @Element(name = "StatProf", required = false)
            @Namespace(prefix = "d", reference = "http://schemas.microsoft.com/ado/2007/08/dataservices")
            String statProf;

            @Element(name = "TatDate", required = false)
            @Namespace(prefix = "d", reference = "http://schemas.microsoft.com/ado/2007/08/dataservices")
            String tat_date;

            @Element(name = "Ecount", required = false)
            @Namespace(prefix = "d", reference = "http://schemas.microsoft.com/ado/2007/08/dataservices")
            String ecount;

//            @Element(name = "TatTime", required = false)
//            @Namespace(prefix = "d", reference = "http://schemas.microsoft.com/ado/2007/08/dataservices")
            String tat_time;

            String name;
            String phone;
            boolean isHittingWS = false;


            public boolean isHittingWS() {
                return isHittingWS;
            }

            public void setHittingWS(boolean hittingWS) {
                isHittingWS = hittingWS;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getEmpId() {
                return empId;
            }

            public String getNotification_num() {
                return notification_num;
            }

            public String getAufner() {
                return aufner;
            }

            public String getComplaint() {
                return complaint;
            }

            public String getDate() {
                return date;
            }

            public String getTime() {
                return time;
            }

            public String getAddress() {
                return address;
            }

            public String getStatus() {
                return status;
            }

            public String getStatProf() {
                return statProf;
            }

            public String getTat_date() {
                return tat_date;
            }

            public String getTat_time() {
                return tat_time;
            }

            public String getEcount() {
                return ecount;
            }

            public void setStatus(String status) {
                this.status = status;
            }


            public void setEmpId(String empId) {
                this.empId = empId;
            }

            public void setNotification_num(String notification_num) {
                this.notification_num = notification_num;
            }

            public void setAufner(String aufner) {
                this.aufner = aufner;
            }

            public void setComplaint(String complaint) {
                this.complaint = complaint;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public void setStatProf(String statProf) {
                this.statProf = statProf;
            }

            public void setTat_date(String tat_date) {
                this.tat_date = tat_date;
            }

            public void setTat_time(String tat_time) {
                this.tat_time = tat_time;
            }

            public void setEcount(String ecount) {
                this.ecount = ecount;
            }
        }


    }


    public static class ItemListParcelConverter implements ParcelConverter<List<entry>> {
        @Override
        public void toParcel(List<entry> input, android.os.Parcel parcel) {
            if (input == null) {
                parcel.writeInt(-1);
            } else {
                parcel.writeInt(input.size());
                for (entry item : input) {
                    parcel.writeParcelable(Parcels.wrap(item), 0);
                }
            }
        }

        @Override
        public List<entry> fromParcel(android.os.Parcel parcel) {
            int size = parcel.readInt();
            if (size < 0) return null;
            List<entry> items = new ArrayList<entry>();
            for (int i = 0; i < size; ++i) {
                items.add((entry) Parcels.unwrap(parcel.readParcelable(entry.class.getClassLoader())));
            }
            return items;
        }
    }
}
