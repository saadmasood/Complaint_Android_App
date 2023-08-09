package pk.com.ke.complaint.model;

import pk.com.ke.complaint.Config;

import org.parceler.Parcel;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Root;

/**
 * Created by iqbal.nadeem on 08-Feb-18.
 */

@Parcel
@Root(name = "entry", strict = false)
@NamespaceList({
        @Namespace(reference = Config.BASE_URL+"sap/opu/odata/sap/ZFM_PM_GANG_ODATA_SRV/", prefix = "base"),
        @Namespace(reference = "http://www.w3.org/2005/Atom"),
        @Namespace(reference = "http://schemas.microsoft.com/ado/2007/08/dataservices/metadata", prefix = "m"),
        @Namespace(reference = "http://schemas.microsoft.com/ado/2007/08/dataservices", prefix = "d")
})
public class NotificationDetails {

    public NotificationDetails() {
    }

    @Element(name = "content", data = true)
    Content content;

    public Content getContent() {
        return content;
    }

    @Parcel
    public static class Content {

        public Content() {
        } // empty constructor required


        @Element(name = "properties", required = false)
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


//       @Element(name = "ImEmpid", required = false)
//        @Namespace(prefix = "d", reference = "http://schemas.microsoft.com/ado/2007/08/dataservices")
//         String empId;

        @Element(name = "QMNUM", required = false)
        @Namespace(prefix = "d", reference = "http://schemas.microsoft.com/ado/2007/08/dataservices")
        String notification_num;

        @Element(name = "AUFNR", required = false)
        @Namespace(prefix = "d", reference = "http://schemas.microsoft.com/ado/2007/08/dataservices")
        String aufner;

        @Element(name = "QMTXT", required = false)
        @Namespace(prefix = "d", reference = "http://schemas.microsoft.com/ado/2007/08/dataservices")
        String complaint;

        @Element(name = "STRMN", required = false)
        @Namespace(prefix = "d", reference = "http://schemas.microsoft.com/ado/2007/08/dataservices")
        String date;

        @Element(name = "STRUR", required = false)
        @Namespace(prefix = "d", reference = "http://schemas.microsoft.com/ado/2007/08/dataservices")
        String time;

        @Element(name = "HOME_CITY", required = false)
        @Namespace(prefix = "d", reference = "http://schemas.microsoft.com/ado/2007/08/dataservices")
        String address;

        @Element(name = "STREET", required = false)
        @Namespace(prefix = "d", reference = "http://schemas.microsoft.com/ado/2007/08/dataservices")
        String pmt;

        @Element(name = "SORT1", required = false)
        @Namespace(prefix = "d", reference = "http://schemas.microsoft.com/ado/2007/08/dataservices")
        String dts;

        @Element(name = "STR_SUPPL1", required = false)
        @Namespace(prefix = "d", reference = "http://schemas.microsoft.com/ado/2007/08/dataservices")
        String feeder;

        @Element(name = "USER_ESTAT", required = false)
        @Namespace(prefix = "d", reference = "http://schemas.microsoft.com/ado/2007/08/dataservices")
        String user_estat;

        @Element(name = "USER_STATUS", required = false)
        @Namespace(prefix = "d", reference = "http://schemas.microsoft.com/ado/2007/08/dataservices")
        String status;


        @Element(name = "STAT_PROF", required = false)
        @Namespace(prefix = "d", reference = "http://schemas.microsoft.com/ado/2007/08/dataservices")
        String statProf;

        @Element(name = "TAT_DATE", required = false)
        @Namespace(prefix = "d", reference = "http://schemas.microsoft.com/ado/2007/08/dataservices")
        String tat_date;

        @Element(name = "TAT_TIME", required = false)
        @Namespace(prefix = "d", reference = "http://schemas.microsoft.com/ado/2007/08/dataservices")
        String tat_time;

        String name;
        String phone;
        boolean isHittingWS = false;

        private String lat;
        private String lon;

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

        public String getPmt() {
            return pmt;
        }

        public String getDts() {
            return dts;
        }

        public String getFeeder() {
            return feeder;
        }

        public String getUser_estat() {
            return user_estat;
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

        public void setTime(String time) {
            this.time = time;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public void setComplaint(String complaint){
            this.complaint=complaint;
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

        public boolean isHittingWS() {
            return isHittingWS;
        }

        public void setHittingWS(boolean hittingWS) {
            isHittingWS = hittingWS;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLon() {
            return lon;
        }

        public void setLon(String lon) {
            this.lon = lon;
        }
    }
}
