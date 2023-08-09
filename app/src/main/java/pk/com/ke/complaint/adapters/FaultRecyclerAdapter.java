package pk.com.ke.complaint.adapters;

import android.app.Activity;
import android.app.Dialog;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.crystal.crystalpreloaders.widgets.CrystalPreloader;

import pk.com.ke.complaint.R;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pk.com.ke.complaint.model.Faults;
import pk.com.ke.complaint.model.SingleTicket.SingleTicketDetail;
import pk.com.ke.complaint.rest.ApiClient;
import pk.com.ke.complaint.rest.ApiInterface;
import pk.com.ke.complaint.utils.MyActivity;
import pk.com.ke.complaint.utils.StringUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FaultRecyclerAdapter extends RecyclerView.Adapter<FaultRecyclerAdapter.MyViewHolder> {

    private List<Faults.entry> listFaults;

    public static final HashMap<String, String> STATUSES = new HashMap<String, String>() {{
        put("ASSG", "Assigned");
        put("ACCP", "Accepted");
        put("RECJ", "Reject");
        put("INPG", "In Progress");
        put("FORW", "Forward");
        put("HOLD", "Hold");
        put("COMP", "Complete");
    }};


//    public static final HashMap<String, Integer> STATUS_COLORS = new HashMap<String, Integer>() {{
//        put("ACCP".toUpperCase(), R.color.colorGrey);
//        put("COMP".toUpperCase(), R.color.colorKeGreen);
//        put("HOLD".toUpperCase(), R.color.colorOpen);
//        put("RECJ".toUpperCase(), R.color.colorInProcess);
//        put("INPG".toUpperCase(), R.color.colorUGM);
//        put("FORW".toUpperCase(), R.color.colorGrey);
//
//        put("ASSG".toUpperCase(), R.color.colorSG);
//        put("Open".toUpperCase(), R.color.colorOpen);
//        put("Pertain to SG".toUpperCase(), R.color.colorSG);
//        put("Pertain to UGM".toUpperCase(), R.color.colorUGM);
//    }};


    public static final HashMap<String, Integer> STATUS_DRAWABLES = new HashMap<String, Integer>() {{
//        put("ACCP".toUpperCase(), R.color.colorGrey);
//        put("COMP".toUpperCase(), R.drawable.rounded_corner_green);
//        put("HOLD".toUpperCase(), R.drawable.rounded_corner_rectangle_red);
//        put("RECJ".toUpperCase(), R.drawable.rounded_corner_blue);
//        put("INPG".toUpperCase(), R.drawable.rounded_corner_orange);
//        put("FORW".toUpperCase(), R.drawable.rounded_corner_grey);
//
//        put("ASSG".toUpperCase(), R.drawable.rounded_corner_orange);
//        put("Open".toUpperCase(), R.drawable.rounded_corner_rectangle_red);
//        put("Pertain to SG".toUpperCase(), R.drawable.rounded_corner_orange);
//        put("Pertain to UGM".toUpperCase(), R.drawable.rounded_corner_orange);


//        put("ACCP", R.color.colorGrey);
//        put("COMP", R.color.app_dark_green);
//        put("HOLD", R.color.f_dark_orange);
//        put("INPG", R.color.blue_normal);
//        put("ASSG", R.color.red);


        put("ACCP", R.color.bg_colorGrey);
        put("COMP", R.color.bg_app_dark_green);
        put("HOLD", R.color.bg_f_dark_orange);
        put("INPG", R.color.bg_blue_normal);
        put("ASSG", R.color.bg_red);
    }};
    private Dialog d;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre;

        @BindView(R.id.ivImage)
        ImageView ivImage;
        @BindView(R.id.tvAddress)
        TextView tvAddress;
        @BindView(R.id.tvFaultDTS)
        TextView tvFault;
        @BindView(R.id.tvNotificationNum)
        TextView tvNotiNum;
        @BindView(R.id.tvStatus)
        TextView tvStatus;
        @BindView(R.id.rootView)
        View rootView;
        @BindView(R.id.cardView)
        View cardView;

        @BindView(R.id.labelAdd)
        TextView labelAdd;

        @BindView(R.id.loader)
        CrystalPreloader loader;

        @BindView(R.id.tvTAT)
        TextView tvTat;


        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    Activity context;

    public interface OnClickListener {
        void onItemClick(int position);
    }

    OnClickListener onClickListener;

    public FaultRecyclerAdapter(Activity context, List<Faults.entry> moviesList, OnClickListener onClickListener) {
        this.listFaults = moviesList;
        this.context = context;
        this.onClickListener = onClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_faults, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Faults.entry.Properties fault = listFaults.get(position).getContent().getProperties();

        if (fault != null) {
            holder.rootView.setTag(position);

            String add = fault.getAddress();

            int ecount=0;
            try{

                ecount = Integer.parseInt(fault.getEcount());
            } catch(Exception e){

            }

            if(ecount != 0){
                holder.tvStatus.setText("Emg/"+getFullFaultText(fault.getStatus()));
            } else {
                holder.tvStatus.setText(getFullFaultText(fault.getStatus()));
            }
            holder.tvNotiNum.setText(String.valueOf(Integer.parseInt(fault.getNotification_num())));

            if (add != null && !TextUtils.isEmpty(add)) {
                holder.tvAddress.setText(add);
                try {
                    holder.tvTat.setText(Html.fromHtml(fault.getTat_time()));
                } catch (Exception e) {
                    e.printStackTrace();
                    holder.tvTat.setText("-");
                }

                holder.tvFault.setText(fault.getComplaint());

                holder.labelAdd.setVisibility(View.VISIBLE);
                holder.tvAddress.setVisibility(View.VISIBLE);
                holder.tvTat.setVisibility(View.VISIBLE);
                holder.tvFault.setVisibility(View.VISIBLE);

                holder.loader.setVisibility(View.INVISIBLE);
            } else {
                if (!fault.isHittingWS()) {
                    holder.loader.setVisibility(View.VISIBLE);

                    holder.labelAdd.setVisibility(View.INVISIBLE);
                    holder.tvAddress.setVisibility(View.INVISIBLE);
                    holder.tvTat.setVisibility(View.INVISIBLE);
                    holder.tvFault.setVisibility(View.INVISIBLE);

                    // Hit address webservice
                    hitAddressWebservice(fault.getNotification_num(), position);
                } else {
                    // Wait for WS
                }
            }
            holder.tvFault.setText(fault.getComplaint());



            if (fault.getStatus() != null && fault.getStatus().length() > 0) {

                try {
                    holder.tvStatus.setBackgroundResource(STATUS_DRAWABLES.get(fault.getStatus()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            holder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = (int) view.getTag();
                    onClickListener.onItemClick(pos);
                }
            });
        }
    }

    private void hitAddressWebservice(String notification_num, final int position) {
//        Retrofit retrofit = ApiClient.getClient_JSON();
        Retrofit retrofit = ApiClient.getInstance();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Faults.entry.Properties properties = listFaults.get(position).getContent().getProperties();
        properties.setHittingWS(true);

        Call<SingleTicketDetail> call = apiInterface.getSingleTicketDetail("'" + notification_num + "'", "json");
        call.enqueue(new Callback<SingleTicketDetail>() {
            @Override
            public void onResponse(Call<SingleTicketDetail> call, Response<SingleTicketDetail> response) {

                MyActivity.log("FaultRecyclerAdap/SingleTicketDetailForAddress/URL/onSuccess", call.request().url().toString());

                try {
                    SingleTicketDetail ticket = response.body();

                    Faults.entry.Properties properties = listFaults.get(position).getContent().getProperties();

                    if (ticket.getD() != null) {
                        String add = ticket.getD().getAddress();
                        if (add != null && !TextUtils.isEmpty(add))
                            properties.setAddress(add);
                        else
                            properties.setAddress("-");

                        String dtsname = ticket.getD().getPmt();
                        if (dtsname != null && !TextUtils.isEmpty(dtsname))
                            properties.setComplaint(dtsname);
                        else
                            properties.setComplaint("-");

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

                        properties.setName(ticket.getD().getCustomerName());
                        properties.setPhone(ticket.getD().getCli());
                        properties.setTat_time(tatFormated);

                        properties.setHittingWS(false);

                        listFaults.get(position).getContent().setProperties(properties);

                        listFaults.set(position, listFaults.get(position));
                    } else {
                        properties = listFaults.get(position).getContent().getProperties();

                        properties.setAddress("-");
                        properties.setComplaint("-");
                        properties.setName("-");
                        properties.setPhone("-");
                        properties.setTat_time("-");

                        properties.setHittingWS(false);

                        listFaults.get(position).getContent().setProperties(properties);

                        listFaults.set(position, listFaults.get(position));

                    }
                    notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();

                    Faults.entry.Properties properties = listFaults.get(position).getContent().getProperties();

                    properties.setAddress("-");
                    properties.setName("-");
                    properties.setPhone("-");
                    properties.setTat_time("-");
                    properties.setComplaint("-");

                    properties.setHittingWS(false);

                    listFaults.get(position).getContent().setProperties(properties);

                    listFaults.set(position, listFaults.get(position));

                    notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<SingleTicketDetail> call, Throwable t) {

                MyActivity.log("FaultRecyclerAdap/SingleTicketDetailForAddress/URL/onFailure", call.request().url().toString());

                Faults.entry.Properties properties = listFaults.get(position).getContent().getProperties();

                properties.setAddress(" ");
                properties.setName(" ");
                properties.setPhone(" ");
                properties.setComplaint(" ");

                properties.setHittingWS(false);

                listFaults.get(position).getContent().setProperties(properties);

                listFaults.set(position, listFaults.get(position));

                notifyDataSetChanged();
            }
        });
    }

    private String getFullFaultText(String status) {
        if (STATUSES.containsKey(status)) {
            return STATUSES.get(status);
        } else {
            return "Unknown";
        }
    }

    @Override
    public int getItemCount() {
        return listFaults.size();
    }

    public void setItems(List<Faults.entry> listFaults) {
        this.listFaults = listFaults;
        notifyDataSetChanged();
    }

    void updateData(int position, Faults.entry fault) {
        listFaults.set(position, fault);
    }

    public List<Faults.entry> getListFaults() {
        return listFaults;
    }
}
