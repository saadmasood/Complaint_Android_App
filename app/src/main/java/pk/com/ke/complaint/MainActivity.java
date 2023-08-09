/*
 * Copyright (c) 2016 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package pk.com.ke.complaint;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.OvershootInterpolator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import pk.com.ke.complaint.model.LocationCoordinates;
import pk.com.ke.complaint.rest.ApiInterface;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

import org.parceler.Parcels;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import pk.com.ke.com.processbutton.FlatButton;
import pk.com.ke.complaint.adapters.FaultRecyclerAdapter;
import pk.com.ke.complaint.model.Count.Counts;
import pk.com.ke.complaint.model.Faults;
import pk.com.ke.complaint.model.UpdateStatus;
import pk.com.ke.complaint.rest.ApiClient;
import pk.com.ke.complaint.utils.MyActivity;
import pk.com.ke.complaint.utils.StringUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends MyActivity implements FaultRecyclerAdapter.OnClickListener {

    ScheduledExecutorService scheduler;

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String EXTRA_RESULT_COMPLAIN = "extra_result_complain2";

    FaultRecyclerAdapter adapter;
    int clickedPosition;

    private int COMPALIN_SCREEN = 123;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.tvNum)
    TextView tvNum;

    @BindView(R.id.tvName)
    TextView tvName;

    @BindView(R.id.pb_noticount)
    ProgressBar pb_noti;

    @BindView(R.id.pb_emergencycount)
    ProgressBar pb_emergency;

    @BindView(R.id.pb_tickcount)
    ProgressBar pb_tick;

    @BindView(R.id.pb_list)
    ProgressBar pb_list;

    @BindView(R.id.count_ticket)
    TickerView count_ticket;

    @BindView(R.id.count_noti)
    TickerView count_noti;

    @BindView(R.id.count_emergency)
    TickerView count_emergency;

    List<Faults.entry> items;

    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout mySwipeRefreshLayout;

    @BindView(R.id.logout)
    AppCompatButton logout;

    String[] reject_grparray, reject_array, reject_codearray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        final Context context = this;

        logout.setVisibility(View.VISIBLE);

        reject_array = getResources().getStringArray(R.array.reject_reasons);
        reject_grparray = getResources().getStringArray(R.array.reject_grpcode);
        reject_codearray = getResources().getStringArray(R.array.reject_reason_code);

        initViews();


        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {

                        mySwipeRefreshLayout.setRefreshing(true);
                        log("LOG", "onRefresh called from SwipeRefreshLayout");

                        getCounts();
                        getData();

                    }
                }
        );

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                warning("KE - Complaint", "Are you sure you want to logout?", "Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        clearLoginPrefs();
                        startMyActivity(LoginActivity.class, true);
                    }
                }, "No");
            }
        });


    }


    @Override
    protected void onStart() {
        super.onStart();

//        scheduler = Executors.newSingleThreadScheduledExecutor();
//
//        scheduler.scheduleAtFixedRate
//                (new Runnable() {
//                    public void run() {
//                        // call service
//                        try {
//                            if (!isFinishing()) {
        getCounts();
        getData();
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, 0, 5, TimeUnit.MINUTES);

    }

    void countShowLoading(boolean showLoading) {
        pb_noti.setVisibility(showLoading ? View.VISIBLE : View.GONE);
        pb_tick.setVisibility(showLoading ? View.VISIBLE : View.GONE);
        pb_emergency.setVisibility(showLoading ? View.VISIBLE : View.GONE);

        count_ticket.setVisibility(showLoading ? View.GONE : View.VISIBLE);
        count_noti.setVisibility(showLoading ? View.GONE : View.VISIBLE);
        count_emergency.setVisibility(showLoading ? View.GONE : View.VISIBLE);
    }

    private void getCounts() {

//        Retrofit retrofit = ApiClient.getClient_JSON();
        Retrofit retrofit = ApiClient.getInstance();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        countShowLoading(true);

        Call<Counts> call = apiInterface.getCounts("IM_EMP eq '" + getUserId() + "'", "json");
        call.enqueue(new Callback<Counts>() {
            @Override
            public void onResponse(Call<Counts> call, final Response<Counts> response) {

                log("MainActivity/GetCounts/URL/onSuccess", call.request().url().toString());

                countShowLoading(false);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Counts mainObj = response.body();
                            if (mainObj != null) {
                                count_noti.setText(String.valueOf(Integer.parseInt(mainObj.getD().getResults().get(0).getQMNUM().trim())));
                                count_ticket.setText(String.valueOf(Integer.parseInt(mainObj.getD().getResults().get(0).getCOUNT().trim())));
                                count_emergency.setText(String.valueOf(Integer.parseInt(mainObj.getD().getResults().get(0).getECOUNT().trim())));
                            } else {
                                count_noti.setText(" ");
                                count_ticket.setText(" ");
                                count_emergency.setText(" ");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            count_noti.setText("0");
                            count_ticket.setText("0");
                            count_emergency.setText("0");
                        }
                    }
                }, 500);
            }

            @Override
            public void onFailure(Call<Counts> call, Throwable t) {

                log("MainActivity/GetCounts/URL/onFailure", call.request().url().toString());

                countShowLoading(false);

                count_noti.setText(" ");
                count_ticket.setText(" ");
                count_emergency.setText("");
            }
        });
    }


    void getData() {

//        Retrofit retrofit = ApiClient.getClient();
        Retrofit retrofit = ApiClient.getInstance();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

//        showDialog("Working...");

        pb_list.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        Call<ResponseBody> call = apiInterface.faultList("ImEmpid eq '" + getUserId() + "'");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                log("MainActivity/GetFaultList/URL/onSuccess", call.request().url().toString());

                pb_list.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

                mySwipeRefreshLayout.setRefreshing(false);

//                dismissDialog();
                String response_WS = null;
                try {
                    response_WS = response.body().string();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (response_WS != null) {
                    response_WS = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + response_WS;

                    Serializer serializer = new Persister();
                    try {
                        try {
                            items.clear();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        Faults faults = serializer.read(Faults.class, response_WS, false);

                        items = filterList(faults.getFaults());

                        adapter.setItems(items);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } else {
                    // Error
                    String error = getErrorBody(response);

                    log("Error", error);


                    if (error.length() > 200) {
                        error = error.substring(0, 200);
                    }
                    Toast.makeText(MainActivity.this, "Error:" + error, Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                log("MainActivity/GetFaultList/URL/onFailure", call.request().url().toString());

                pb_list.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

                mySwipeRefreshLayout.setRefreshing(false);
//                dismissDialog();
                log("MainActivity/OnFailure", stackTraceToString(t));
                toast("Error in Connection!");
            }
        });
    }

    private List<Faults.entry> filterList(List<Faults.entry> faults) {
        List<Faults.entry> filteredFaults = new ArrayList<>();
        int removedCount = 0;

        for (int i = 0; i < faults.size(); i++) {
            faults.get(i).getContent().getProperties().setAddress("");

            if (ComplainDetailActivity.STATUS_NOT_ALLOWED.contains(faults.get(i).getContent().getProperties().getStatus().toUpperCase())) {
                removedCount++;

            } else {
                filteredFaults.add(faults.get(i));

            }
        }

        log("REMOVED Count", removedCount + "");

        Collections.sort(filteredFaults, new Comparator<Faults.entry>() {
            @Override
            public int compare(Faults.entry o1, Faults.entry o2) {
//                return o1.getContent().getProperties().getEcount().compareTo(o2.getContent().getProperties().getEcount());
                if(Integer.parseInt(o1.getContent().getProperties().getEcount()) > Integer.parseInt(o2.getContent().getProperties().getEcount())) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });
        return filteredFaults;
    }

    void initViews() {
        items = new ArrayList<>();
        adapter = new FaultRecyclerAdapter(this, items, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);

        tvNum.setText(Html.fromHtml("Emp #: <b>" + getUserId() + "</b>"));

        tvName.setText(Html.fromHtml("Emp Name: <b>" + getString(StringUtils.PREF_USER_USER_NAME_AD) + "</b>"));

        count_noti.setCharacterLists(TickerUtils.provideNumberList());
        count_ticket.setCharacterLists(TickerUtils.provideNumberList());
        count_emergency.setCharacterLists(TickerUtils.provideNumberList());

        count_noti.setAnimationDuration(1000);
        count_noti.setAnimationInterpolator(new OvershootInterpolator());

        count_emergency.setAnimationDuration(1000);
        count_emergency.setAnimationInterpolator(new OvershootInterpolator());

        count_ticket.setAnimationDuration(1000);
        count_ticket.setAnimationInterpolator(new OvershootInterpolator());

        count_noti.setText(" ");
        count_ticket.setText(" ");
        count_emergency.setText(" ");


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == COMPALIN_SCREEN) {
            if (resultCode == RESULT_OK) {

//                if (data.getParcelableExtra(EXTRA_RESULT_COMPLAIN) != null) {
//                    Faults.entry fault = Parcels.unwrap(data.getParcelableExtra(EXTRA_RESULT_COMPLAIN));
//
//                    Log.v(TAG, "fault: " + new Gson().toJson(fault));
//
//                    adapter.updateData(clickedPosition, fault);
//                    adapter.notifyDataSetChanged();
//
//                }
            }
        }
    }

    @Override
    public void onItemClick(int position) {
        clickedPosition = position;
        items = adapter.getListFaults();
        Faults.entry selectedRecipe = items.get(position);

        if (selectedRecipe.getContent().getProperties().getStatus().equalsIgnoreCase("ASSG")) {
            showAcceptDialog(selectedRecipe);

        } else {
            Intent detailIntent = new Intent(MainActivity.this, ComplainDetailActivity.class);
            detailIntent.putExtra(ComplainDetailActivity.EXTRA_FAULT, Parcels.wrap(selectedRecipe));
            detailIntent.putExtra("not_num", selectedRecipe.getContent().getProperties().getNotification_num());
            startActivityForResult(detailIntent, COMPALIN_SCREEN);

        }
    }

    Dialog d;

    private void showAcceptDialog(final Faults.entry selectedRecipe) {
        //
        d = new Dialog(MainActivity.this);

        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setContentView(R.layout.dialog_accept);

        TextView tv = d.findViewById(R.id.tvID);

        tv.setText("Are you sure you want to accept this notification # " + selectedRecipe.getContent().getProperties().getNotification_num() + "?");

        FlatButton btnAccept = d.findViewById(R.id.btnAccept);
        FlatButton btnReject = d.findViewById(R.id.btnReject);

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateStatus(selectedRecipe.getContent().getProperties().getNotification_num(), "ACCP", "", "", "", "", d);

            }
        });

        btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                d.dismiss();
                showRejectDialog(selectedRecipe.getContent().getProperties().getNotification_num());
//                updateStatus(selectedRecipe.getContent().getProperties().getNotification_num(), "RECJ", "", "", "", "", d);

            }
        });

        d.show();
    }


    void updateStatus(String notification_num, final String statusSelected, String faultCat, String faultSubCat, String causeCat, String causeSubCat, final Dialog d) {

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
                "'" + notification_num + "'",
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
                log("MainActivity/UpdateStatus/URL/onSuccess", call.request().url().toString());

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
//                        if (property.getMessage().toLowerCase().contains("status update successful".toLowerCase())) {
                        if (statusSelected.equalsIgnoreCase("ACCP") || statusSelected.equalsIgnoreCase("INPG")) {
                            if (checkAcceptInpgResult(property.getMessage())) {
                                afterUpdate();

                            } else {
                                warning("Notification Status", "Error while updating the status--" + property.getMessage());

                            }
                        } else if (statusSelected.equalsIgnoreCase("RECJ")
                                || statusSelected.equalsIgnoreCase("FORW")
                                || statusSelected.equalsIgnoreCase("HOLD")) {

                            if (checkRejForwHoldResult(property.getMessage())) {
                                afterUpdate();

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

                    warning("Notification Status", "Error while updating the Status--null");

                    log("ComplainDetailsActivity/Api", "" + error);
                }
            }

            private void afterUpdate() {
                toast("Updated!");

                try {
                    if (d != null && d.isShowing())
                        d.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                getCounts();
                getData();

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                log("MainActivity/UpdateStatus/URL/onFailure", call.request().url().toString());

                dismissDialog();
                log("MainActivity/OnFailure", stackTraceToString(t));
                toast("Error in Connection!");
            }
        });
    }


    void showRejectDialog(final String notificationNum) {

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_spinner);

        final Spinner spinStatus = (Spinner) dialog.findViewById(R.id.spin_dialog);

        Button btnSubmit = (Button) dialog.findViewById(R.id.submit);
        TextView header = (TextView) dialog.findViewById(R.id.header);

        header.setText("Reject");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                R.layout.simple_spinner_item, reject_array);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinStatus.setAdapter(dataAdapter);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = spinStatus.getSelectedItemPosition();

                updateStatus(notificationNum, "RECJ", "", "", "DN-LTRE", reject_codearray[pos], dialog);
            }
        });

        dialog.show();
    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        try {
//            scheduler.shutdownNow();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
