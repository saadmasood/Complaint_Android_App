package pk.com.ke.complaint;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import pk.com.ke.complaint.model.EstimateTimeResponse.EstimateTimeResponse;
import pk.com.ke.complaint.rest.ApiInterface;

import com.google.android.gms.common.api.Api;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import pk.com.ke.complaint.adapters.FaultRecyclerAdapter;
import pk.com.ke.complaint.model.Faults;
import pk.com.ke.complaint.model.NotificationDetails;
import pk.com.ke.complaint.model.SingleTicket.SingleTicketDetail;
import pk.com.ke.complaint.model.UpdateStatus;
import pk.com.ke.complaint.rest.ApiClient;
import pk.com.ke.complaint.utils.MyActivity;
import pk.com.ke.complaint.utils.StringUtils;
import pk.com.ke.complaint.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

//import com.isys.kecomplains2.model.Fault;

public class ComplainDetailActivity extends MyActivity implements AdapterView.OnItemSelectedListener{

    public static final String TAG = ComplainDetailActivity.class.getSimpleName();
    public static final String EXTRA_FAULT = "extra_fault";

    public static final ArrayList<String> STATUS_NOT_ALLOWED = new ArrayList<>();

    NotificationDetails.Properties property_new = null;

    static {
        STATUS_NOT_ALLOWED.add("RECJ");
        STATUS_NOT_ALLOWED.add("COMP");
        STATUS_NOT_ALLOWED.add("FORW");
    }

    Faults.entry fault;

    @BindView(R.id.tvNotification)
    TextView tvNotification;
    @BindView(R.id.tvAufner)
    TextView tvAufner;
    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.tvStatus)
    TextView tvStatus;
    @BindView(R.id.tvFeeder)
    TextView tvFeeder;
    @BindView(R.id.tvPMT)
    TextView tvPMT;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvPhone)
    TextView tvPhone;
    @BindView(R.id.tvTat)
    TextView tvTat;
    @BindView(R.id.tvFault)
    TextView tvFault;
    @BindView(R.id.tvAddress)
    TextView tvAddress;


    @BindView(R.id.relImage)
    RelativeLayout relImage;

    @BindView(R.id.TATview)
    LinearLayout TATview;

    @BindView(R.id.ivImage)
    ImageView ivImage;
    @BindView(R.id.ivDirection)
    ImageView ivDirection;
//    @BindView(R.id.reject)
//    Button reject;
//    @BindView(R.id.forward)
//    Button forward;
//    @BindView(R.id.hold)
//    Button hold;
//    @BindView(R.id.complete)
//    Button complete;
//    @BindView(R.id.inprogress)
//    Button inprogress;

    @BindView(R.id.spinner)
    Spinner sp_status;

    @BindView(R.id.spinnerHours)
    Spinner sp_hour;
    @BindView(R.id.spinnerMinutes)
    Spinner sp_min;


    String[] status_array, fault_array, cause_array, reject_reason;

    Faults.entry.Properties property;

    ArrayAdapter<String> sp_adap,sp_adap_hours, sp_adap_minutes;
    String notiNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fault_detail);
        ButterKnife.bind(this);

        status_array = getResources().getStringArray(R.array.status);
        fault_array = getResources().getStringArray(R.array.fault);
        cause_array = getResources().getStringArray(R.array.cause);
        reject_reason = getResources().getStringArray(R.array.reject_reasons);

        relImage.setVisibility(View.GONE);

        sp_status.setOnItemSelectedListener(this);

        if (getIntent().getParcelableExtra(EXTRA_FAULT) != null) {
            fault = Parcels.unwrap(getIntent().getParcelableExtra(EXTRA_FAULT));
            property = fault.getContent().getProperties();

             notiNum = getIntent().getStringExtra("not_num");

//            Retrofit retrofit = ApiClient.getClient();
            Retrofit retrofit = ApiClient.getInstance();
            ApiInterface apiInterface = retrofit.create(ApiInterface.class);

            showDialog("Working...");

            ivDirection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse("http://maps.google.com/maps?daddr=" + property_new.getLat() + "," + property_new.getLon()));
                    startActivity(intent);
                }
            });

            Call<ResponseBody> call = apiInterface.getDetails("'DETAIL'", "'" + notiNum + "'");
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    log("ComplainAct/GetNotificationDetails/URL/onSuccess", call.request().url().toString());

                    dismissDialog();

                    ResponseBody notificationDetails = response.body();

                    if (notificationDetails != null) {

                        Serializer serializer = new Persister();
                        try {
                            property_new = serializer.read(NotificationDetails.class, notificationDetails.string(), false).getContent().getProperties();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


//                        Retrofit retrofit = ApiClient.getClient_JSON();
                        Retrofit retrofit = ApiClient.getInstance();
                        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

                        Call<SingleTicketDetail> call2 = apiInterface.getSingleTicketDetail("'" + property.getNotification_num() + "'", "json");
                        call2.enqueue(new Callback<SingleTicketDetail>() {
                            @Override
                            public void onResponse(Call<SingleTicketDetail> call, Response<SingleTicketDetail> response) {

                                log("ComplainAct/SingleTicketDetail/URL/onSuccess", response.raw().request().url().toString());

                                try {
                                    SingleTicketDetail ticket = response.body();

                                    property_new.setAddress(ticket.getD().getAddress());
                                    property_new.setName(ticket.getD().getCustomerName());
                                    property_new.setPhone(ticket.getD().getCli());
                                    property_new.setLat(ticket.getD().getLat());
                                    property_new.setLon(ticket.getD().getLon());
                                    property_new.setComplaint(ticket.getD().getSubject());


                                    String tat = ticket.getD().getTat();
                                    String tatFormated = "";
                                    try {
                                        if (tat != null && !TextUtils.isEmpty(tat.trim())) {
                                            tat = tat.replaceAll(" ", "");

                                            if (tat.contains("."))
                                                tat = tat.split("\\.")[0];

                                            tatFormated = StringUtils.calculateTime(Integer.parseInt(tat));
                                            MyActivity.log("TAT FaultRec", "TAT:" + tatFormated);

                                        } else {
                                            MyActivity.log("TAT FaultRec", "TAT IS Null:" + tat);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        MyActivity.log("TAT FaultRec", "TAT Excep:" + e.getMessage());
                                    }


                                    property_new.setHittingWS(false);


                                    if (!isEmpty(property_new.getLon()) && !isEmpty(property_new.getLat())) {
                                        String imgUrl = "http://maps.googleapis.com/maps/api/staticmap?center=" + property_new.getLat() + "," + property_new.getLon() + "&zoom=18&size=500x200&key=AIzaSyDVgEAbO1FqtUVrzhdQWnE3I1UCB99aHZg";
                                        Picasso.with(getApplicationContext()).load(imgUrl).placeholder(R.color.app_dark_grey).into(ivImage);

                                        relImage.setVisibility(View.VISIBLE);
                                    } else {
                                        relImage.setVisibility(View.GONE);
                                    }

                                    try {
                                        tvTat.setText(Html.fromHtml(tatFormated));
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    tvNotification.setText("" + Integer.parseInt(property_new.getNotification_num()));
                                    try {
                                        tvAufner.setText("" + Integer.parseInt(property_new.getAufner()));
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    tvTime.setText(getDate(property_new.getDate()) + " " + getTime(property_new.getTime()));
                                    tvStatus.setText("" + getFullFaultText(property_new.getStatus().toUpperCase()));

                                    tvFeeder.setText(property_new.getFeeder());
                                    tvPMT.setText(property_new.getPmt());

                                    tvName.setText(property_new.getName());
                                    tvPhone.setText(property_new.getPhone());
                                    tvFault.setText(property_new.getComplaint());
                                    tvAddress.setText(property_new.getAddress());

                                    setProperStatus(property_new.getStatus());

                                } catch (Exception e) {
                                    e.printStackTrace();

                                    property_new.setAddress(" ");
                                    property_new.setName(" ");
                                    property_new.setPhone(" ");

                                    property_new.setHittingWS(false);

                                }
                            }

                            @Override
                            public void onFailure(Call<SingleTicketDetail> call, Throwable t) {
                                log("ComplainAct/SingleTicketDetail/URL/onFailure", call.request().url().toString());


                                property_new.setAddress(" ");
                                property_new.setName(" ");
                                property_new.setPhone(" ");

                                property_new.setHittingWS(false);

                            }
                        });

                    } else {
                        String error = getErrorBody(response);

                        log("ComplainDetailsActivity/Api", "" + error);
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    log("ComplainAct/GetNotificationDetails/URL/onFailure", call.request().url().toString());

                    dismissDialog();
                    log("MainActivity/OnFailure", stackTraceToString(t));
                    toast("Error in Connection!");
                }
            });

        }
    }

    private void setProperStatus(String status) {

        ArrayList<String> sp_statuses = new ArrayList<>();
        sp_statuses.add("--Please Select--");

        if (STATUS_NOT_ALLOWED.contains(status.toUpperCase())) {
            toast("You should not be here... Wrong status : " + status);
            log("ComplainDetailsActivity", "[Notification " + property.getNotification_num() + "] User should not be here with this status :" + status);
            finish();

        } else {
            if (status.equalsIgnoreCase("assg")) {
                // Assigned

                sp_statuses.add("Accept");
                sp_statuses.add("Reject");

//                reject.setEnabled(true);
//                hold.setEnabled(true);
//                inprogress.setEnabled(true);
//                forward.setEnabled(true);
//
//                complete.setEnabled(false);

            } else if (status.equalsIgnoreCase("accp")) {
                // Assigned

                sp_statuses.add("In-Progress");
                sp_statuses.add("Hold");
                sp_statuses.add("Forward");
                sp_statuses.add("Reject");
                sp_statuses.add("JPUS");

//                reject.setEnabled(true);
//                hold.setEnabled(true);
//                inprogress.setEnabled(true);
//                forward.setEnabled(true);
//
//                complete.setEnabled(false);

            } else if (status.equalsIgnoreCase("hold")) {
                // Hold

                sp_statuses.add("In-Progress");
//                sp_statuses.add("Forward");


//                inprogress.setEnabled(true);
//                forward.setEnabled(true);
//
//                reject.setEnabled(false);
//                hold.setEnabled(false);
//                complete.setEnabled(false);

            } else if (status.equalsIgnoreCase("inpg")) {
                // In progress

                sp_statuses.add("Hold");
//                sp_statuses.add("Forward");
                sp_statuses.add("Complete");
                //  Added on 9-Aug by Saad
                sp_statuses.add("pertain to UGM/SSM");
//            //  Ended on 9-Aug by Saad
//                  complete.setEnabled(true);
//
//                inprogress.setEnabled(false);
//                forward.setEnabled(false);
//                reject.setEnabled(false);
//                hold.setEnabled(false);

            } else if (status.equalsIgnoreCase("comp")
                    || status.equalsIgnoreCase("forw")
                    || status.equalsIgnoreCase("recj")) {
                // Complete, Forward, Reject
//                complete.setEnabled(false);
//                inprogress.setEnabled(false);
//                forward.setEnabled(false);
//                reject.setEnabled(false);
//                hold.setEnabled(false);


                sp_statuses.add("In-Progress");
                sp_statuses.add("Hold");
                sp_statuses.add("Forward");
                sp_statuses.add("Reject");


            }
        }

        sp_adap = new ArrayAdapter<String>(getApplicationContext(), R.layout.simple_spinner_item, sp_statuses);
        sp_adap.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        sp_status.setAdapter(sp_adap);

        ArrayList<String> sp_hours = new ArrayList<>();
        for(int i=0; i<=24; i++){
            sp_hours.add(""+i);
        }

        ArrayList<String> sp_mins = new ArrayList<>();
        for(int i=1; i<=60; i++){
            sp_mins.add(""+i);
        }


        sp_adap_hours = new ArrayAdapter<String>(getApplicationContext(), R.layout.simple_spinner_item, sp_hours);
        sp_adap_hours.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        sp_hour.setAdapter(sp_adap_hours);
        sp_adap_minutes = new ArrayAdapter<String>(getApplicationContext(), R.layout.simple_spinner_item, sp_mins);
        sp_adap_minutes.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        sp_min.setAdapter(sp_adap_minutes);


    }





    private String getFullFaultText(String status) {
        if (FaultRecyclerAdapter.STATUSES.containsKey(status)) {
            return FaultRecyclerAdapter.STATUSES.get(status);
        } else {
            return "Unknown";
        }
    }

    private String getDate(String date) {
        String h = date.substring(0, 4);
        String m = date.substring(4, 6);
        String s = date.substring(6, 8);


        return h + "/" + m + "/" + s;
    }

    private String getTime(String time) {
        String h = time.substring(0, 2);
        String m = time.substring(2, 4);
        String s = time.substring(4, 6);


        return h + ":" + m + ":" + s;
    }

    public static String getTatTime(String time) {
        String h = time.substring(0, 2);
        String m = time.substring(2, 4);
        String s = time.substring(4, 6);

        return h + "h " + m + "m " + s + "s";
    }

    void updateStatus(final String statusSelected, String faultCat, String faultSubCat, String causeCat, String causeSubCat) {

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
                "'" + property.getNotification_num() + "'",
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

                log("ComplainDetail/UpdateStatus/URL/onSuccess", call.request().url().toString());

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
//                        if (property.getMessage().contains("update successful")) {
                        if (statusSelected.equalsIgnoreCase("ACCP") || statusSelected.equalsIgnoreCase("INPG")) {
                            if (checkAcceptInpgResult(property.getMessage())) {
                                toast("Updated!");
                                finish();
                            } else {
                                warning("Notification Status", "Error while updating the status--" + property.getMessage());
                            }
                        } else if (statusSelected.equalsIgnoreCase("RECJ")
                                || statusSelected.equalsIgnoreCase("FORW")
                                || statusSelected.equalsIgnoreCase("HOLD")) {
                            if (checkRejForwHoldResult(property.getMessage())) {
                                toast("Updated!");
                                finish();
                            } else {
                                warning("Notification Status", "Error while updating the status--" + property.getMessage());
                            }
                        } else if (statusSelected.equalsIgnoreCase("COMP")) {
                            if (checkCompResult(property.getMessage())) {
                                toast("Updated!");
                                finish();
                            } else {
                                warning("Notification Status", "Error while updating the status--" + property.getMessage());
                            }
                        }
//                        } else {
//                            toast("Error while updating the status");
//                        }
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

                log("ComplainDetail/UpdateStatus/URL/onFailure", call.request().url().toString());

                dismissDialog();

                String full = stackTraceToString(t);

                log("MainActivity/OnFailure", full);

                if (full.length() > 200) {
                    full = full.substring(0, 200);
                }

                toast("Error:" + full);
            }
        });
    }

    void showRejectDialog() {

        final Dialog dialog = new Dialog(this);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_spinner);

        final Spinner spinStatus = (Spinner) dialog.findViewById(R.id.spin_dialog);

        Button btnSubmit = (Button) dialog.findViewById(R.id.submit);
        TextView header = (TextView) dialog.findViewById(R.id.header);

        header.setText("Reject");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                R.layout.simple_spinner_item, reject_reason);


        dataAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spinStatus.setAdapter(dataAdapter);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final String[] csubCat = getResources().getStringArray(R.array.reject_reason_code);
                int pos = spinStatus.getSelectedItemPosition();


                updateStatus("RECJ", "", "", "DN-LTRE", csubCat[pos]);

//                updateStatus("RECJ", "", "", "", "");

            }
        });

        dialog.show();
    }


    void showForwardDialog() {

        final Dialog dialog = new Dialog(this);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_spinner_2);

        Button btnSubmit = (Button) dialog.findViewById(R.id.submit);
        TextView header = (TextView) dialog.findViewById(R.id.header);
        TextView tvCause = (TextView) dialog.findViewById(R.id.tvCause);

        header.setText("Forward");

        Spinner spinDept = dialog.findViewById(R.id.spin_dialog1);

        final Spinner spinReason = dialog.findViewById(R.id.spin_dialog2);
        spinDept.setVisibility(View.VISIBLE);
        spinDept.setVisibility(View.GONE);
        tvCause.setVisibility(View.GONE);

//        String[] dept = getResources().getStringArray(R.array.dept);
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
//                R.layout.simple_spinner_item, dept);
//        dataAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
//        spinDept.setAdapter(dataAdapter);

        String[] reason = getResources().getStringArray(R.array.forward_reasons);
        ArrayAdapter<String> reasons = new ArrayAdapter<>(this,
                R.layout.simple_spinner_item, reason);
        reasons.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spinReason.setAdapter(reasons);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String[] csubCat = getResources().getStringArray(R.array.forward_reason_code);
                int pos = spinReason.getSelectedItemPosition();


                updateStatus("FORW", "", "", "DN-LTFO", csubCat[pos]);

            }
        });

        dialog.show();
    }

    void showJPUSDialog2() {


        Intent i = new Intent(getApplicationContext(), CompleteNotificationActivity.class);
        //Intent i = new Intent(getApplicationContext(), MaterialAddingActivity.class);
        i.putExtra("notification", property_new.getNotification_num() + "");
        i.putExtra("order", property_new.getAufner() + "");
        i.putExtra("statusCode", "JPUS"); // Added 9- Aug - BY Saad
        Utils.slideInRightAnim(ComplainDetailActivity.this, i);
        finish();
    }
    void showJPUSDialog() {

        final Dialog dialog = new Dialog(this);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_spinner_2);

        Button btnSubmit = (Button) dialog.findViewById(R.id.submit);
        TextView header = (TextView) dialog.findViewById(R.id.header);
        TextView tvCause = (TextView) dialog.findViewById(R.id.tvCause);

        header.setText("JPUS");

        Spinner spinDept = dialog.findViewById(R.id.spin_dialog1);

        final Spinner spinReason = dialog.findViewById(R.id.spin_dialog2);
        spinDept.setVisibility(View.VISIBLE);
        spinDept.setVisibility(View.GONE);
        tvCause.setVisibility(View.GONE);

//        String[] dept = getResources().getStringArray(R.array.dept);
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
//                R.layout.simple_spinner_item, dept);
//        dataAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
//        spinDept.setAdapter(dataAdapter);

        String[] reason = getResources().getStringArray(R.array.forward_reasons);
        ArrayAdapter<String> reasons = new ArrayAdapter<>(this,
                R.layout.simple_spinner_item, reason);
        reasons.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spinReason.setAdapter(reasons);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String[] csubCat = getResources().getStringArray(R.array.forward_reason_code);
                int pos = spinReason.getSelectedItemPosition();


                updateStatus("JPUS", "", "", "DN-LT", csubCat[pos]);

            }
        });

        dialog.show();
    }


    void showHoldDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_spinner);

        final Spinner spinStatus = (Spinner) dialog.findViewById(R.id.spin_dialog);

        Button btnSubmit = (Button) dialog.findViewById(R.id.submit);
        TextView header = (TextView) dialog.findViewById(R.id.header);

        header.setText("Hold");

        String[] hold_reason = getResources().getStringArray(R.array.hold_reason);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                R.layout.simple_spinner_item, hold_reason);

        dataAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spinStatus.setAdapter(dataAdapter);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String[] csubCat = getResources().getStringArray(R.array.hold_reason_code);
                int pos = spinStatus.getSelectedItemPosition();

                updateStatus("HOLD", "", "", "DN-LTHO", csubCat[pos]);

            }
        });

        dialog.show();
    }

    void showCompleteDialog() {


        Intent i = new Intent(getApplicationContext(), CompleteNotificationActivity.class);
        //Intent i = new Intent(getApplicationContext(), MaterialAddingActivity.class);
        i.putExtra("notification", property_new.getNotification_num() + "");
        i.putExtra("order", property_new.getAufner() + "");
        i.putExtra("statusCode", "COMP");  // Added 9- Aug - BY Saad
        Utils.slideInRightAnim(ComplainDetailActivity.this, i);
        finish();


//        final Dialog dialog = new Dialog(this);
//        dialog.setContentView(R.layout.dialog_spinner_2);
//
//        Button btnSubmit = (Button) dialog.findViewById(R.id.submit);
//        TextView header = (TextView) dialog.findViewById(R.id.header);
//
//        header.setText("Complete");
//
//        final Spinner faultSp = (Spinner) dialog.findViewById(R.id.spin_dialog1);
//        final Spinner causeSp = (Spinner) dialog.findViewById(R.id.spin_dialog2);
//
//        final String[] cCat = getResources().getStringArray(R.array.cause_cat);
//        final String[] csubCat = getResources().getStringArray(R.array.cause_subcat);
//
//        final String[] fCat = getResources().getStringArray(R.array.fault_cat);
//        final String[] fsubCat = getResources().getStringArray(R.array.fault_subcat);
//
//        ArrayAdapter<String> adapFault = new ArrayAdapter<>(this,
//                R.layout.simple_spinner_item, fault_array);
//        adapFault.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
//        faultSp.setAdapter(adapFault);
//
//        ArrayAdapter<String> adapCause = new ArrayAdapter<>(this,
//                R.layout.simple_spinner_item, cause_array);
//        adapCause.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
//        causeSp.setAdapter(adapCause);
//
//
//        btnSubmit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String faultCat, faultSubCat, causeCat, causeSubCat;
//                String faultSelected, causeSelected;
//                int faultPos, causePos;
//
//                faultPos = faultSp.getSelectedItemPosition();
//                causePos = causeSp.getSelectedItemPosition();
//
//                faultCat = fCat[faultPos];
//                faultSubCat = fsubCat[faultPos];
//
//                causeCat = cCat[causePos];
//                causeSubCat = csubCat[causePos];
//
//                updateStatus("COMP", faultCat, faultSubCat, causeCat, causeSubCat);
//
//            }
//        });
//
//        dialog.show();
    }


    List<String> listStatus;

//    @OnClick(R.id.inprogress)
//    public void OnClickInProgress(View v) {
//        updateStatus("INPG", "", "", "", "");
//    }

//    @OnClick(R.id.reject)
//    public void OnClickReject(View v) {
//        showRejectDialog();
//    }


//    @OnClick(R.id.forward)
//    public void OnClickForward(View v) {
//        showForwardDialog();
//    }

//    @OnClick(R.id.hold)
//    public void OnClickHold(View v) {
//        showHoldDialog();
//    }

    @OnClick(R.id.complete)
    public void OnClickComplete(View v) {

        String tempStatus = sp_status.getSelectedItem().toString();

        if (tempStatus.equalsIgnoreCase("In-Progress")) {
            postTATTime();


        } else if (tempStatus.equalsIgnoreCase("Hold")) {
            showHoldDialog();

        } else if (tempStatus.equalsIgnoreCase("Forward")) {
            showForwardDialog();

        } else if (tempStatus.equalsIgnoreCase("Reject")) {
            showRejectDialog();

        } else if (tempStatus.equalsIgnoreCase("Complete")) {
            showCompleteDialog();

        }
        else if (tempStatus.equalsIgnoreCase("pertain to UGM/SSM")) {
            showJPUSDialog2();

        }else {
            toast("Please select Valid status");
        }


    }

    private void postTATTime() {
        String hours = sp_hour.getSelectedItem().toString();
        String minutes = sp_min.getSelectedItem().toString();
        String url = getEstimateTimeURL(notiNum,hours,minutes);

        Retrofit retrofit = ApiClient.getInstance();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        showDialog("Please wait...");


        Call<EstimateTimeResponse> call = apiInterface.updateEstimatedTime(getEstimateTimeURL(notiNum,hours,minutes));
        call.enqueue(new Callback<EstimateTimeResponse>() {
                        @Override
                        public void onResponse(Call<EstimateTimeResponse> call, Response<EstimateTimeResponse> response) {

                            log("ComplainAct/UpdateEstimatedTime/URL/onSuccess", response.raw().request().url().toString());

                            dismissDialog();
                            try {
                                if(response.isSuccessful()){

                                    EstimateTimeResponse resp = response.body();
                                    if (resp != null) {
                                        if(resp.getD().getStatus().equals("S")){
                                            updateStatus("INPG", "", "", "", "");
                                        } else {
                                            dismissDialog();
                                            toast(resp.getD().getMessage());
                                        }

                                    } else {
                                        dismissDialog();
                                        toast(response.errorBody().toString());
                                    }
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                                toast(e.getMessage());

                            }
                        }

                        @Override
                        public void onFailure(Call<EstimateTimeResponse> call, Throwable t) {
                            log("UpdateEstimatedTime/URL/onFailure", call.request().url().toString());


                            dismissDialog();

                            String full = stackTraceToString(t);

                            log("UpdateEstimatedTime./OnFailure", full);

                            if (full.length() > 200) {
                                full = full.substring(0, 200);
                            }

                            toast("Error:" + full);

                        }
                    });
    }

    public static String getEstimateTimeURL(String notifNo, String hours, String minutes) {
        String baseUrl = Config.BASE_URL;

//        baseUrl += "/sap/opu/odata/sap/ZWS_GET_NOTI_TICKET_SRV/PostEstimateTimeSet(NotifNo='" + notifNo + "',Minutes='" + minutes + "')/?$format=json";

        baseUrl += "sap/opu/odata/sap/ZWS_GET_NOTI_TICKET_SRV/PostEstimateTimeSet(NotifNo='"+notifNo+"',Minutes='"+minutes+"',Hours='"+hours+"')/?$format=json";
        return baseUrl;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if (parent.equals(sp_status)) {

            // @TODO find a better way to do this.
            // Doing this

            String status = sp_status.getSelectedItem().toString();

            if (status.equalsIgnoreCase("In-Progress")) {
                // IN-PROGRESS
                TATview.setVisibility(View.VISIBLE);
            } else {
                //OTHERS
                TATview.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
