package pk.com.ke.complaint;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.common.api.Api;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import pk.com.ke.complaint.model.Material;
import pk.com.ke.complaint.model.MaterialAddedResponse.MaterialAddedResponse;
import pk.com.ke.complaint.model.MaterialRequestBody.D;
import pk.com.ke.complaint.model.MaterialRequestBody.MaterialRequestBody;
import pk.com.ke.complaint.model.MaterialRequestBody.MaterialsSet;
import pk.com.ke.complaint.rest.ApiInterface;

import org.json.JSONArray;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import okhttp3.ResponseBody;
import pk.com.ke.complaint.adapters.TicketRecyclerAdapter;
import pk.com.ke.complaint.model.CompleteNotification.CompleteTicket.CompleteTickets;
import pk.com.ke.complaint.model.CompleteNotification.UnassignTicket.UnassignTickets;
import pk.com.ke.complaint.model.Tickets.Result;
import pk.com.ke.complaint.model.Tickets.Tickets;
import pk.com.ke.complaint.model.UpdateStatus;
import pk.com.ke.complaint.rest.ApiClient;
import pk.com.ke.complaint.utils.MyActivity;
import pk.com.ke.complaint.utils.StringUtils;
import pk.com.ke.complaint.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static java.util.Collections.*;

public class CompleteNotificationActivity extends MyActivity implements TicketRecyclerAdapter.OnClickListener, AdapterView.OnItemSelectedListener{

    String[] fault_array, cause_array;
    String[] fCatUnique;
    //total array contains combined array of fault category, fault and fault sub category.
    List<Fault> total_fault_array, current_fault_array;
    String tickToClose = "", tickToUnAss = "";
    String tickNotClosed = "", getTickNotUnAssg = "";

    String faultCat, faultSubCat, causeCat, causeSubCat;

    private List<Result> items;
    private TicketRecyclerAdapter adapter;

    @BindView(R.id.ticket_list)
    RecyclerView recyclerView;

    @BindView(R.id.spin_dialog0)
    Spinner faultCatSp;

    @BindView(R.id.spin_dialog1)
    Spinner faultSp;

    @BindView(R.id.spin_dialog2)
    Spinner causeSp;


    @BindView(R.id.ticket_count)
    TextView tvTicketCount;

    @BindView(R.id.checkAll)
    CheckBox chkAll;

    @BindView(R.id.complete)
    AppCompatButton btnSubmit;

    private String notiNum = "";
    private String orderNum = "";
    private String statusCode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_notification);
        ButterKnife.bind(this);

        setTitle("Complete Notification");
        faultCatSp.setOnItemSelectedListener(this);

        notiNum = getIntent().getStringExtra("notification");
        orderNum = getIntent().getStringExtra("order");
        statusCode = getIntent().getStringExtra("statusCode");

//        notiNum = "000360000755";


        if (notiNum != null) {
            items = new ArrayList<>();
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);

            complete();

            getTickets(notiNum);

        } else {
            toast("Notification number is Null");
            finish();
        }
    }

    private void getTickets(String notiNum) {
//        Retrofit retrofit = ApiClient.getClient_JSON();

      //  showDialog("Testing 4");
        Retrofit retrofit = ApiClient.getInstance();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<Tickets> call = apiInterface.getTickets("'" + notiNum + "'", "json");
        call.enqueue(new Callback<Tickets>() {
            @Override
            public void onResponse(Call<Tickets> call, Response<Tickets> response) {
                log("CompleteNotif/getTickets/URL/onSuccess", call.request().url().toString());

                Tickets tickets = response.body();

                if (tickets != null) {
                    items = tickets.getD().getResults();
                    adapter = new TicketRecyclerAdapter
                            (
                                    CompleteNotificationActivity.this,
                                    items,
                                    CompleteNotificationActivity.this
                            );

                    recyclerView.setAdapter(adapter);

                    tvTicketCount.setText(Html.fromHtml("Tickets Count: <b><u>" + String.valueOf(items.size()) + "</u></b>"));

                } else {
                    String error = getErrorBody(response);
                    log(error);
                    String s;

                    if (error.length() > 200)
                        s = error.substring(0, 200);
                    else
                        s = error;

                    toast("Error:" + s);
                    tvTicketCount.setText(String.valueOf(-1));
                }
            }

            @Override
            public void onFailure(Call<Tickets> call, Throwable t) {
                log("CompleteNotif/getTickets/URL/onFailure", call.request().url().toString());
                String s;

                if (t.getMessage().length() > 200)
                    s = t.getMessage().substring(0, 200);
                else
                    s = t.getMessage();

                toast("Error:" + s);
                log(t.getMessage());
                tvTicketCount.setText(String.valueOf(-1));
            }
        });
    }


    void complete() {

        fault_array = getResources().getStringArray(R.array.fault);
        cause_array = getResources().getStringArray(R.array.cause);
        fCatUnique = getResources().getStringArray(R.array.fault_cat_unique);

        final String[] fCat = getResources().getStringArray(R.array.fault_cat);
        final String[] fsubCat = getResources().getStringArray(R.array.fault_subcat);

        //combine fCat,fault_array,fSubCat to make array of Faults.
        total_fault_array= combineFaultSubArrays(fCat,fault_array,fsubCat);
        if (total_fault_array == null){
            toast("Fault Arrays length mismatch");
            return;
        }
//        Collections.copy(current_fault_array,total_fault_array);
        current_fault_array = new ArrayList<>();
        copyArrayList(current_fault_array,total_fault_array);
//        current_fault_array = ArrayList.c total_fault_array.;

        final String[] cCat = getResources().getStringArray(R.array.cause_cat);
        final String[] csubCat = getResources().getStringArray(R.array.cause_subcat);



        ArrayAdapter<String> adapFaultCat = new ArrayAdapter<>(this,
                R.layout.simple_spinner_item, fCatUnique );
        adapFaultCat.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        faultCatSp.setAdapter(adapFaultCat);

        ArrayAdapter<Fault> adapFault = new ArrayAdapter<>(this,
                R.layout.simple_spinner_item, current_fault_array);
        adapFault.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        faultSp.setAdapter(adapFault);

        ArrayAdapter<String> adapCause = new ArrayAdapter<>(this,
                R.layout.simple_spinner_item, cause_array);
        adapCause.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        causeSp.setAdapter(adapCause);

       // showDialog("Testing1");

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int faultPos, causePos, faultCatPos;
            //    showDialog("Testing1");
                faultCatPos = faultCatSp.getSelectedItemPosition();
                faultPos = faultSp.getSelectedItemPosition();
                //Removing Cause on Business Request. Therefore, we are setting it to default i.e. 1. --Zahra 19th Feb,2020
                causePos = 1;
//                causePos = causeSp.getSelectedItemPosition();

                final String[] cCat = getResources().getStringArray(R.array.cause_cat);
                final String[] csubCat = getResources().getStringArray(R.array.cause_subcat);

//                faultCat = fCatUnique[faultCatPos];
                String faultCatTemp = current_fault_array.get(faultPos).fault_cat;
                //Split to get Fault category code not text --Zahra on 26th Nov 2020
                faultCat = faultCatTemp.split(" ")[0];


                faultSubCat = current_fault_array.get(faultPos).fault_subcat;

                causeCat = cCat[causePos];
                causeSubCat = csubCat[causePos];

//                closeTickets(faultCat, faultSubCat);
            //    showDialog("Testing1");
                if(statusCode == "COMP") {
                  updateMaterials(notiNum,orderNum);
                }  //Commented only for QA by Saad 9-Aug
                else {
                    completeNotification();
                }
            }
        });

    }

    private void copyArrayList(List<Fault> dest, List<Fault> source) {
        dest.clear();
        for (int i = 0; i < source.size(); i++) {
            dest.add(new Fault(source.get(i)));
        }
    }

    private List<Fault> combineFaultSubArrays(String[] fCat, String[] fault_array, String[] fSubCat){
        if(fCat.length != fault_array.length || fault_array.length != fSubCat.length){
            //there is some error in data (arrays.xml) therefore it mismatches
            return null;
        } else {
            List<Fault> faultList= new ArrayList<Fault>();

            for (int i =0; i <fault_array.length; i++){
                Fault f = new Fault(fCat[i],fault_array[i],fSubCat[i]);
                faultList.add(f);
            }

            return faultList;

        }


    }

    @OnCheckedChanged(R.id.checkAll)
    public void checkAll(CompoundButton v, boolean checked) {
//        if(!isClickedBySys) {
        if (items != null) {

            for (int i = 0; i < items.size(); i++) {
                items.get(i).setChecked(checked);
            }
            adapter.setItems(items);
        }
//        }else{
//            isClickedBySys = false;
//        }
    }


    void updateStatus  (String noti_num, final String statusSelected, final String faultCat, final String faultSubCat, String causeCat, final String causeSubCat) {

        if (faultCat.equalsIgnoreCase("-Please Select-")
                || faultSubCat.equalsIgnoreCase("-Please Select-")
                || causeCat.equalsIgnoreCase("-Please Select-")
                || causeSubCat.equalsIgnoreCase("-Please Select-")) {

            toast("Please select valid value");
            return;
        }


//        Retrofit retrofit = ApiClient.getClient();
        Retrofit retrofit = ApiClient.getInstance();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        showDialog("Updating...");

        Call<ResponseBody> call = apiInterface.updateStatus(
                "'UPD_STAT'",
                "'" + noti_num + "'",
                "'" + statusSelected + "'",
                "'" + faultCat + "'",
                "'" + faultSubCat + "'",
                "'" + causeCat + "'",
                "'" + causeSubCat + "'",
                "''"
        );
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                log("CompleteNotif/UpdateNotifStatus/URL/onSuccess", call.request().url().toString());

                dismissDialog();

                ResponseBody notificationDetails = response.body();
                String resp = null;

                try {
                    resp = notificationDetails.string();
                    log(resp);
                } catch (Exception e) {
                    e.printStackTrace();
                }


                if (notificationDetails != null) {
                    UpdateStatus.Properties property = null;

                    Serializer serializer = new Persister();
                    try {
                        property = serializer.read(UpdateStatus.class, resp, false).getContent().getProperties();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (property != null) {

                        if (statusSelected.equalsIgnoreCase("COMP")) {
                            if (checkCompResult(property.getMessage())) {


//                                if (property.getMessage().toLowerCase().contains("update Successful".toLowerCase())) {
//                                    toast("Updated!");
//                                    finish();


//                                closeTickets(faultCat, faultSubCat);

                                toast("Notification Closed");
                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                Utils.slideInRightAnim(CompleteNotificationActivity.this, i);
//                                finish();
//                                finish();


//                                }

                            } else {
                                warning("Notification Status", "Error while updating the status--" + property.getMessage());
                            }
                        } else {
                            warning("Notification Status", "Error while updating the status--" + property.getMessage());
                        }
                    } else {
                        warning("Notification Status", "Error while updating the status--null");
                    }


                } else {
                    String error = getErrorBody(response);

                    log("ComplainDetailsActivity/Api", "" + error);

                    if (error.length() > 200) {
                        error = error.substring(0, 200);
                    }


                    warning("Notification Status", "Error while updating the Status-" + error);


                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                log("CompleteNotif/UpdateNotifStatus/URL/onFailure", call.request().url().toString());

                dismissDialog();

                String full = stackTraceToString(t);

                log("CompleteNotifAct./OnFailure", full);

                if (full.length() > 200) {
                    full = full.substring(0, 200);
                }

                toast("Error:" + full);
            }
        });
    }


//        btnSubmit.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            String faultCat, faultSubCat, causeCat, causeSubCat;
//            String faultSelected, causeSelected;
//            int faultPos, causePos;
//
//            faultPos = faultSp.getSelectedItemPosition();
//            causePos = causeSp.getSelectedItemPosition();
//
//           faultCat = current_fault_array.get(faultPos).fault_cat;
//           faultSubCat = current_fault_array.get(faultPos).fault_cat;
////            faultCat = fCat[faultPos];
////            faultSubCat = fsubCat[faultPos];
//
//            causeCat = cCat[causePos];
//            causeSubCat = csubCat[causePos];
//
//            updateStatus("COMP", faultCat, faultSubCat, causeCat, causeSubCat);
//
//        }
//    });


    private void closeTickets(String faultCat, String faultSubCat) {

        List<Result> list = adapter.getTickList();

        tickToUnAss = "";
        tickToClose = "";

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isChecked()) {
                tickToClose += list.get(i).getTicketNum() + ",";
            } else {
                tickToUnAss += list.get(i).getTicketNum() + ",";
            }
        }

//        Retrofit retrofit = ApiClient.getClient_JSON();
        Retrofit retrofit = ApiClient.getInstance();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        if (tickToClose.length() > 1) {
            tickToClose = tickToClose.substring(0, tickToClose.length() - 1);



            Call<CompleteTickets> completeTicketsCall = apiInterface.completeTicket("'D'", "'" + tickToClose + "'", "'" + faultCat + "'", "'" + faultSubCat + "'", "json");
            completeTicketsCall.enqueue(new Callback<CompleteTickets>() {
                @Override
                public void onResponse(Call<CompleteTickets> call, Response<CompleteTickets> response) {
                    tickNotClosed = "";

                    log("CompleteNotifAct./CompleteTicket/URL/onSuccess", call.request().url().toString());

                    dismissDialog();

                    CompleteTickets ticketNotUpdated = response.body();

                    if (ticketNotUpdated != null) {
                        if (ticketNotUpdated.getD().getResults() != null && ticketNotUpdated.getD().getResults().size() > 0) {
                            for (int i = 0; i < ticketNotUpdated.getD().getResults().size(); i++) {
                                tickNotClosed += ticketNotUpdated.getD().getResults().get(i).getObjectId() + ",";
                            }


                            if (tickNotClosed != null)
                                warning("Notification Status!", "Tickets could not be closed:" + tickNotClosed);

                            unassignTick();

                        } else {
                            unassignTick();
                        }
                    } else {
                        unassignTick();
                    }

                }

                @Override
                public void onFailure(Call<CompleteTickets> call, Throwable t) {

                    dismissDialog();
                    log("CompleteNotif/CompleteTicket/URL/onFailure", call.request().url().toString());

                    String full = stackTraceToString(t);

                    log("CompleteNotifAct./CompleteTicket/OnFailure", full);

                    if (full.length() > 200) {
                        full = full.substring(0, 200);
                    }

                    toast("Error:" + full);

                }
            });
        } else {
            unassignTick();
        }

    }

    void unassignTick() {
        if (tickToUnAss.length() > 1) {
            tickToUnAss = tickToUnAss.substring(0, tickToUnAss.length() - 1);

//            Retrofit retrofit = ApiClient.getClient_JSON();
            Retrofit retrofit = ApiClient.getInstance();
            ApiInterface apiInterfacea = retrofit.create(ApiInterface.class);

            showDialog("Please wait...");

            Call<UnassignTickets> unassignTicketsCall = apiInterfacea.unassignTicket("'D'", "'" + tickToUnAss + "'", "json");
            unassignTicketsCall.enqueue(new Callback<UnassignTickets>() {
                @Override
                public void onResponse(Call<UnassignTickets> call, Response<UnassignTickets> response) {
                    tickNotClosed = "";

                    dismissDialog();
                    log("CompleteNotif/UnassignTick/URL/onSuccess", call.request().url().toString());

                    UnassignTickets ticketNotUpdated = response.body();

                    if (ticketNotUpdated != null) {
                        if (ticketNotUpdated.getD().getResults() != null && ticketNotUpdated.getD().getResults().size() > 0) {
                            for (int i = 0; i < ticketNotUpdated.getD().getResults().size(); i++) {
                                getTickNotUnAssg += ticketNotUpdated.getD().getResults().get(i).getObjectId() + ",";
                            }

                            warning("Notification Status!", "Tickets could not be unassigned:" + getTickNotUnAssg, "OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    finish();
                                    completeNotification();
                                }
                            });
                        } else {
//                            finish();
                            completeNotification();
                        }
                    } else {
//                        finish();
                        completeNotification();
                    }
                }

                @Override
                public void onFailure(Call<UnassignTickets> call, Throwable t) {

                    dismissDialog();
                    log("CompleteNotif/UnassignTick/URL/onFailure", call.request().url().toString());

                    String full = stackTraceToString(t);

                    log("CompleteNotifAct./UnassignTick/OnFailure", full);

                    if (full.length() > 200) {
                        full = full.substring(0, 200);
                    }

                    toast("Error:" + full);

                }
            });
        } else {
//            finish();
            completeNotification();
        }
    }

    void completeNotification() {
//        updateMaterials(notiNum,orderNum);
        //updateStatus(notiNum, "COMP", faultCat, faultSubCat, causeCat, causeSubCat);  // Comment out by Saad 9-Aug
        log("statusCode.toString()", statusCode.toString());
        updateStatus(notiNum, statusCode, faultCat, faultSubCat, causeCat, causeSubCat); // Added by Saad 9-Aug


    }

//    public JsonArray createJsonArrayFromList(List<MaterialsSet> list) {
//        JsonArray jsonArray = Json.createArrayBuilder();
//        for(MaterialsSet material : list) {
//            jsonArray.add(Json.createObjectBuilder()
//                    .add("NotifNo", material.getNotifNo())
//                    .add("Quantity", material.getQuantity())
//                    .add("Matnr", material.getMatnr()));
//        }
//        jsonArray.build();
//        return jsonArray;
//    }

    private void updateMaterials(String notiNum, String orderNum) {
        //PostData
//        Retrofit retrofit = ApiClient.getClient_JSON();


      //  showDialog("Testing");
        Retrofit retrofit = ApiClient.getInstance();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        List<Material> list = StringUtils.materialList.get(notiNum);
        ArrayList<MaterialsSet> listMaterialSet = new ArrayList<MaterialsSet>();
        //update List

        // Ali Sarfaraz
        List<MaterialsSet> materialsSetList = new ArrayList<>();
        int tempvar= list.size();
       /* if (tempvar==0){

            toast("Please select a Valid Material.");
            return;
        }
*/



        for(int i=0; i<list.size();i++){
            Material m= list.get(i);
            MaterialsSet temp= new MaterialsSet();
            temp.setNotifNo(notiNum);
            temp.setQuantity(Integer.toString(m.getQuantity()));
            temp.setMatnr(Integer.toString(m.getMaterial_code()));
            listMaterialSet.add(temp);
        }
        MaterialRequestBody materialRequestBody = new MaterialRequestBody();
        materialRequestBody.setD(new D());
        materialRequestBody.getD().setNotifNo(notiNum);
        materialRequestBody.getD().setOrderNo(orderNum);
        Gson gson = new Gson();
        JsonArray jsonElements =  gson.toJsonTree(listMaterialSet).getAsJsonArray();
//        JSONArray jsonElements = new JSONArray(listMaterialSet);
//        materialRequestBody.getD().setMaterialsSet(jsonElements);
        materialRequestBody.getD().setMaterialsSet(listMaterialSet);

        showDialog("Please wait...");
        Call<MaterialAddedResponse> call = apiInterface.postMaterials("XMLHttpRequest","application/json","application/json","",materialRequestBody);

        call.enqueue(new Callback<MaterialAddedResponse>() {
            @Override
            public void onResponse(Call<MaterialAddedResponse> call, Response<MaterialAddedResponse> response) {
                log("MaterialUpdated/URL/onSuccess", call.request().url().toString());

                MaterialAddedResponse responseObj = response.body();

                if (responseObj != null) {
                    if(responseObj.getD().getStatus().equals("S")){
                        closeTickets(faultCat, faultSubCat);
                    } else {
                        dismissDialog();
                        toast(responseObj.getD().getMessage());
                    }
//                    items = re.getD().getResults();
//                    adapter = new TicketRecyclerAdapter
//                            (
//                                    CompleteNotificationActivity.this,
//                                    items,
//                                    CompleteNotificationActivity.this
//                            );
//
//                    recyclerView.setAdapter(adapter);
//
//                    tvTicketCount.setText(Html.fromHtml("Tickets Count: <b><u>" + String.valueOf(items.size()) + "</u></b>"));


                } else {
                      dismissDialog();
                    toast(response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<MaterialAddedResponse> call, Throwable t) {
                log("MaterialUpdated/URL/onFailure", call.request().url().toString());


                dismissDialog();

                String full = stackTraceToString(t);

                log("MaterialUpdated./OnFailure", full);

                if (full.length() > 200) {
                    full = full.substring(0, 200);
                }

                toast("Error:" + full);
            }
        });

    }

    boolean isClickedBySys = false;

    @Override
    public void onItemsClick(int position, List<Result> ticketList) {
//        toast("Clicked : " + position);

//        if (ticketList.size() > 0) {
//            isClickedBySys = true;
//            for (int i = 0; i < ticketList.size(); i++) {
//                if (!ticketList.get(i).isChecked()) {
//                    chkAll.setChecked(false);
//                    return;
//                }
//            }
//            chkAll.setChecked(true);
//        }

    }

//    @Override
//    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//        if (adapterView.equals(faultCatSp)) {
//
//            // @TODO find a better way to do this.
//            // Doing this
//
//                int pos = faultCatSp.getSelectedItemPosition();
//
//                if (pos != 0) {
//                    // Something is Selected
//                    String faultCat = fCatUnique[pos];
//                    //update current_fault_array based on faultCat selected
//                    updateCurrentFaultArray(faultCat);
//
//
//                } else {
//                    //nothing is selected so return all options
//                    current_fault_array = total_fault_array;
//                }
//
//                ArrayAdapter<Fault> adapFault = new ArrayAdapter<>(this,
//                        R.layout.simple_spinner_item, current_fault_array);
//                adapFault.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
//                faultSp.setAdapter(adapFault);
//
//        }
//    }

    private void updateCurrentFaultArray(String faultCat){
        current_fault_array.clear();
            for (int i=1; i<total_fault_array.size();i++){
                if(total_fault_array.get(i).fault_cat.equalsIgnoreCase(faultCat)){
                    current_fault_array.add(total_fault_array.get(i));
                }
            }

            //current_fault_array is updated

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        if (adapterView.equals(faultCatSp)) {

            // @TODO find a better way to do this.
            // Doing this

            int pos = faultCatSp.getSelectedItemPosition();

            if (pos != 0) {
                // Something is Selected
                String faultCat = fCatUnique[pos];
                //update current_fault_array based on faultCat selected
                updateCurrentFaultArray(faultCat);


            } else {
                //nothing is selected so return all options
//                current_fault_array = total_fault_array;
//                current_fault_array = new ArrayList<>();
                copyArrayList(current_fault_array,total_fault_array);
            }

            ArrayAdapter<Fault> adapFault = new ArrayAdapter<>(this,
                    R.layout.simple_spinner_item, current_fault_array);
            adapFault.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
            faultSp.setAdapter(adapFault);

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private class Fault{
        String fault_cat;
        String fault;
        String fault_subcat;

        Fault(String fault_cat, String fault, String fault_subcat){
            this.fault_cat=fault_cat;
            this.fault = fault;
            this.fault_subcat= fault_subcat;
        }

        Fault(Fault f){
            this.fault_cat=f.fault_cat;
            this.fault = f.fault;
            this.fault_subcat= f.fault_subcat;
        }

        @Override
        public String toString() {
            return fault;
        }
    }


}
