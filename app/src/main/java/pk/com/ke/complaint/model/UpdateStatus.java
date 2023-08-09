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
public class UpdateStatus {

    public UpdateStatus() {

    }


    @Element(name = "content", data = true)
    public Content content;

    public Content getContent() {
        return content;
    }

    @Parcel
    public static class Content {

        public Content() {
        } // empty constructor required


        @Element(name = "properties", required = false)
        @Namespace(prefix = "m", reference = "http://schemas.microsoft.com/ado/2007/08/dataservices/metadata")
        public Properties properties;

        public Properties getProperties() {
            return properties;
        }
    }

    @Parcel
    public static class Properties {
        public Properties() {
        } // empty constructor required

        @Element(name = "EX_MESSAGE", required = true)
        @Namespace(prefix = "d", reference = "http://schemas.microsoft.com/ado/2007/08/dataservices")
        public String message;

        @Element(name = "EX_VALID", required = false)
        @Namespace(prefix = "d", reference = "http://schemas.microsoft.com/ado/2007/08/dataservices")
        public String valid;


        public String getMessage() {
            return message;
        }

        public String getValid() {
            return valid;
        }
    }


}
