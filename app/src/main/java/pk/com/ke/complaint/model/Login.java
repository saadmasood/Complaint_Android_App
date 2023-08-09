package pk.com.ke.complaint.model;

import pk.com.ke.complaint.Config;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Root;

/**
 * Created by iqbal.nadeem on 06-Feb-18.
 */

@Root(name = "entry", strict = false)
@NamespaceList({
        @Namespace(reference = Config.BASE_URL + "sap/opu/odata/sap/ZFM_PM_GANG_ODATA_SRV/", prefix = "base"),
        @Namespace(reference = "http://www.w3.org/2005/Atom"),
        @Namespace(reference = "http://schemas.microsoft.com/ado/2007/08/dataservices/metadata", prefix = "m"),
        @Namespace(reference = "http://schemas.microsoft.com/ado/2007/08/dataservices", prefix = "d")
})
public class Login {
    public Login() {
    } // empty constructor required

    @Element(data = true, required = false)
    private String updatedDate;

    @Element(data = true, required = false)
    private String title;

    @Element(data = true, required = false)
    private String category;

    @Element(name = "content", data = true)
    private Content content;

    public static class Content {

        public Content() {
        } // empty constructor required


        @Element(name = "properties")
        @Namespace(prefix = "m", reference = "http://schemas.microsoft.com/ado/2007/08/dataservices/metadata")
        private Properties properties;

        public Properties getProperties() {
            return properties;
        }
    }

    public static class Properties {


        public Properties() {
        } // empty constructor required


        @Element(name = "EX_VALID", required = false)
        @Namespace(prefix = "d", reference = "http://schemas.microsoft.com/ado/2007/08/dataservices")
        private String ex_valid;


        public String getEx_valid() {
            return ex_valid;
        }

        public void setEx_valid(String ex_valid) {
            this.ex_valid = ex_valid;
        }
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public Content getContent() {
        return content;
    }
}
