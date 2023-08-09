package pk.com.ke.complaint.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pk.com.ke.complaint.R;
import pk.com.ke.complaint.model.Material;
import pk.com.ke.complaint.model.Tickets.Result;

public class MaterialRecyclerAdapter extends RecyclerView.Adapter<MaterialRecyclerAdapter.MyViewHolder> {

private List<Material> materialList;
public static InteractWithRecyclerView interact;


public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView title, year, genre;


    @BindView(R.id.cartProductName)
    TextView materialName;
    @BindView(R.id.cartProductPrice)
    TextView materialUnit;
    @BindView(R.id.tvMatNo)
    TextView materialCode;
    @BindView(R.id.cartEditTextQuantity)
    EditText quantity;
//    @BindView(R.id.tvStatus)
//    TextView tvStatus;
    @BindView(R.id.rootView)
    View rootView;

    @BindView(R.id.cartDeleteButton)
    ImageButton delete;
    @BindView(R.id.cartButtonQuantityRemove)
    ImageButton remove;
    @BindView(R.id.cartButtonQuantityAdd)
    ImageButton add;





        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

        Context context;

    public interface OnClickListener {
        void onItemsClick(int position, List<Material> ticketList);
    }

    public interface InteractWithRecyclerView{
        void selectedItem(Material material, String operation);
        void deleteItem(Material material);
    }


    public MaterialRecyclerAdapter(Context context, List<Material> list ) {
        this.materialList = list;
        this.context = context;
        this.interact = (InteractWithRecyclerView) context;
    }

    @Override
    public MaterialRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_material_added, parent, false);

        return new MaterialRecyclerAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MaterialRecyclerAdapter.MyViewHolder holder, final int position) {
        Material material = materialList.get(position);

        if (material != null) {

            holder.rootView.setTag(position);

            holder.materialName.setText(material.getMaterial_desc());
            holder.materialUnit.setText("Unit: "+String.valueOf(material.getUnit()));
            holder.quantity.setText(String.valueOf(material.getQuantity()));
            holder.materialCode.setText(String.valueOf(material.getMaterial_code()));

            if(material.getQuantity() >1){
                holder.remove.setEnabled(true);
            } else {
                holder.remove.setEnabled(false);
            }

            holder.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    holder.se.performClick();
//                    material.setQuantity(material.getQuantity()+1);
                    interact.selectedItem(material,"add");
                }
            });
            holder.remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    holder.se.performClick();
//                    material.setQuantity(material.getQuantity()-1);
                    interact.selectedItem(material,"remove");
                }
            });
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    materialList.remove(position);
                    interact.deleteItem(material);
                }
            });

//            holder.rootView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    int pos = (int) view.getTag();
//
//                    tickList.get(pos).setChecked(!tickList.get(pos).isChecked());
//                    notifyDataSetChanged();
//
//                    onClickListener.onItemsClick(pos, tickList);
//                }
//            });
        }
    }

    @Override
    public int getItemCount() {
        return materialList.size();
    }

    public void setItems(List<Material> materialList) {
        this.materialList = materialList;
        notifyDataSetChanged();
    }

    public List<Material> getMaterialList() {
        return materialList;
    }
}
