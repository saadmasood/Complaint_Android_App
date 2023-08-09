package pk.com.ke.complaint.adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import pk.com.ke.complaint.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pk.com.ke.complaint.model.Tickets.Result;

public class TicketRecyclerAdapter extends RecyclerView.Adapter<TicketRecyclerAdapter.MyViewHolder> {

    private List<Result> tickList;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre;

        @BindView(R.id.ivImage)
        ImageView ivImage;
        @BindView(R.id.tvAddress)
        TextView tvAddress;
        @BindView(R.id.tvFault)
        TextView tvFault;
        @BindView(R.id.tvNotificationNum)
        TextView tvNotiNum;
        @BindView(R.id.tvStatus)
        TextView tvStatus;
        @BindView(R.id.rootView)
        View rootView;

        @BindView(R.id.chkBox)
        CheckBox chkBox;


        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    Context context;

    public interface OnClickListener {
        void onItemsClick(int position, List<Result> ticketList);
    }

    OnClickListener onClickListener;

    public TicketRecyclerAdapter(Context context, List<Result> list, OnClickListener onClickListener) {
        this.tickList = list;
        this.context = context;
        this.onClickListener = onClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_tickets, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Result fault = tickList.get(position);

        if (fault != null) {

            holder.rootView.setTag(position);

            holder.tvAddress.setText(fault.getAddress());
            holder.tvFault.setText(fault.getSubject());

            if (fault.getStatus() != null && fault.getStatus().length() > 0) {
                holder.tvStatus.setText(fault.getStatus());
                holder.tvNotiNum.setText("" + fault.getTicketNum());

            }

//            if(tickList.get(position).isChecked()){
            holder.chkBox.setChecked(tickList.get(position).isChecked());

            holder.chkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.rootView.performClick();
                }
            });
            holder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = (int) view.getTag();

                    tickList.get(pos).setChecked(!tickList.get(pos).isChecked());
                    notifyDataSetChanged();

                    onClickListener.onItemsClick(pos, tickList);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return tickList.size();
    }

    public void setItems(List<Result> listFaults) {
        this.tickList = listFaults;
        notifyDataSetChanged();
    }

    public List<Result> getTickList() {
        return tickList;
    }
}
