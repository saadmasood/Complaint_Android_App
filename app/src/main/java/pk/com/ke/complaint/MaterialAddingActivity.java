package pk.com.ke.complaint;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.simpleframework.xml.convert.Convert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import pk.com.ke.complaint.adapters.MaterialRecyclerAdapter;
import pk.com.ke.complaint.adapters.TicketRecyclerAdapter;
import pk.com.ke.complaint.model.Material;
import pk.com.ke.complaint.model.MaterialListResponse.MaterialList;
import pk.com.ke.complaint.model.MaterialListResponse.Result;
import pk.com.ke.complaint.model.MaterialRequestBody.MaterialsSet;
import pk.com.ke.complaint.model.Tickets.Tickets;
import pk.com.ke.complaint.rest.ApiClient;
import pk.com.ke.complaint.rest.ApiInterface;
import pk.com.ke.complaint.utils.MyActivity;
import pk.com.ke.complaint.utils.StringUtils;
import pk.com.ke.complaint.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MaterialAddingActivity extends MyActivity implements  AdapterView.OnItemSelectedListener, MaterialRecyclerAdapter.InteractWithRecyclerView {

//    String[] material_array, cause_array;
    List<String> mCatUnique, mSubCatUnique;
    //total array contains combined array of fault category, fault and fault sub category.
    List<Material> total_material_array, current_material_array;
    List<String> current_material_sub_cat;

    private List<Material> materialList;
    private MaterialRecyclerAdapter adapter;

    @BindView(R.id.material_list)
    RecyclerView recyclerView;


    @BindView(R.id.addMaterial)
    Button addMaterial;

    @BindView(R.id.skipMaterial)
    Button btnskipMaterial;

    @BindView(R.id.btn_submit_material)
    AppCompatButton btnSubmitMaterial;

    @BindView(R.id.spin_category)
    Spinner materialCatSp;

    @BindView(R.id.spin_dialog1)
    Spinner materialSubCatSp;

    @BindView(R.id.spin_dialog2)
    Spinner materialSp;

    private String notiNum = "";
    private String orderNum = "";
    private String vskipMaterial = "0";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_adding);
        ButterKnife.bind(this);
        setTitle("Material Consumption");
//        faultCatSp.setOnItemSelectedListener(this);

        notiNum = getIntent().getStringExtra("notification");
        orderNum = getIntent().getStringExtra("order");
//        notiNum = "000360000755";

        materialCatSp.setOnItemSelectedListener(this);
        materialSubCatSp.setOnItemSelectedListener(this);
        log("vskipMaterial", vskipMaterial);
        if (vskipMaterial == "0") {
        if (notiNum != null) {
            materialList = new ArrayList<>();
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);

            getMaterial(notiNum);
            getMaterialList();
//            complete();

        } else {
            toast("Notification number is Null");
            finish();
        }
        }

    }

    private void getMaterial(String notiNum) {
//        Material temp = new Material("Material","BOLT & NUT","BOLT & NUT; G.I 6\"X1/2\".","EA",10021274,1);
//        materialList.add(temp);
//        materialList.add(temp);
        List<Material> temp = StringUtils.materialList.get(notiNum);
        if(temp != null){
            materialList = temp;
        }
                    adapter = new MaterialRecyclerAdapter
                            (
                                    MaterialAddingActivity.this,
                                    materialList
                            );

                    recyclerView.setAdapter(adapter);
    }


    void complete() {

//        material_array = getResources().getStringArray(R.array.material_desc);
//        mCatUnique = getResources().getStringArray(R.array.material_cat_unique);
//        mSubCatUnique = getResources().getStringArray(R.array.material_subcat_unique);


//        final String[] mCat = getResources().getStringArray(R.array.material_cat);
//        final String[] mSubCat = getResources().getStringArray(R.array.material_subcat);
//        final String[] mUnit = getResources().getStringArray(R.array.material_unit);
//        final int[] mCode = getResources().getIntArray(R.array.material_code);
//
//        //combine fCat,fault_array,fSubCat to make array of Faults.
//        total_material_array= combineMaterialSubArrays(mCat,material_array,mSubCat,mUnit,mCode);

        total_material_array = setList();
        if (total_material_array == null || total_material_array.size()==0){
            toast("Error occured fetching the list.");
            return;
        }
//        Collections.copy(current_fault_array,total_fault_array);
        current_material_array = new ArrayList<>();
        copyArrayList(current_material_array,total_material_array);
        current_material_sub_cat = new ArrayList<>();
        copySubCatList(current_material_sub_cat,mSubCatUnique);
//        current_fault_array = ArrayList.c total_fault_array.;



        ArrayAdapter<String> adapmaterialCat = new ArrayAdapter<>(this,
                R.layout.simple_spinner_item, mCatUnique );
        adapmaterialCat.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        materialCatSp.setAdapter(adapmaterialCat);

        ArrayAdapter<String> adapMaterialSubCat = new ArrayAdapter<>(this,
                R.layout.simple_spinner_item, mSubCatUnique );
        adapMaterialSubCat.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        materialSubCatSp.setAdapter(adapMaterialSubCat);

        ArrayAdapter<Material> adapFault = new ArrayAdapter<>(this,
                R.layout.simple_spinner_item, current_material_array);
        adapFault.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        materialSp.setAdapter(adapFault);

        addMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int materialPos = materialSp.getSelectedItemPosition();
                Material materialTemp = current_material_array.get(materialPos);
                if(!materialTemp.getMaterial_desc().equalsIgnoreCase("-Please Select-")){
                    if(materialList.contains(materialTemp)){
                        toast("Material Already Added.");
                    } else{
                        materialList.add(materialTemp);
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    toast("Please select a Valid Material.");
                }


            }
        });

        btnSubmitMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                List<MaterialsSet> materialsSetList = new ArrayList<>();
               int tempvar= materialList.size();
               if (tempvar==0){

                   toast("Please select a Valid Material.");
                   return;
               }


                putMaterialListinHashMap();
                Intent i = new Intent(getApplicationContext(), CompleteNotificationActivity.class);
                i.putExtra("notification", notiNum + "");
                i.putExtra("order",orderNum);
                Utils.slideInRightAnim(MaterialAddingActivity.this, i);
//                finish();
            }
        });

        btnskipMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int tempvar= materialList.size();
                if (tempvar>0){

                    toast("Please click on Next Button.");
                    return;
                }

                putMaterialListinHashMap();

                Intent i = new Intent(getApplicationContext(), CompleteNotificationActivity.class);
                i.putExtra("notification", notiNum + "");
                i.putExtra("order",orderNum);
                vskipMaterial="1";
                Utils.slideInRightAnim(MaterialAddingActivity.this, i);
             //   finish();
            }
        });


//        btnSubmit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int faultPos, causePos, faultCatPos;
//
//                faultCatPos = faultCatSp.getSelectedItemPosition();
//                faultPos = faultSp.getSelectedItemPosition();
//                //Removing Cause on Business Request. Therefore, we are setting it to default i.e. 1. --Zahra 19th Feb,2020
//                causePos = 1;
////                causePos = causeSp.getSelectedItemPosition();
//
//                final String[] cCat = getResources().getStringArray(R.array.cause_cat);
//                final String[] csubCat = getResources().getStringArray(R.array.cause_subcat);
//
////                faultCat = fCatUnique[faultCatPos];
//                String faultCatTemp = current_fault_array.get(faultPos).fault_cat;
//                //Split to get Fault category code not text --Zahra on 26th Nov 2020
//                faultCat = faultCatTemp.split(" ")[0];
//
//
//                faultSubCat = current_fault_array.get(faultPos).fault_subcat;
//
//                causeCat = cCat[causePos];
//                causeSubCat = csubCat[causePos];
//
//                closeTickets(faultCat, faultSubCat);
//
//            }
//        });

    }

    private void getMaterialList(){
//        Retrofit retrofit = ApiClient.getClient_JSON();
        Retrofit retrofit = ApiClient.getInstance();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<MaterialList> call = apiInterface.allMaterialList("json");
        call.enqueue(new Callback<MaterialList>() {
            @Override
            public void onResponse(Call<MaterialList> call, Response<MaterialList> response) {

                log("ComplainAct/GetNotificationDetails/URL/onSuccess", call.request().url().toString());

//                dismissDialog();

                MaterialList Response = response.body();

                if (Response != null) {
                    List<Result> list = Response.getD().getResults();
                    StringUtils.total_material_list = list;
                    complete();
                } else {
                    String error = getErrorBody(response);

                    log("ComplainDetailsActivity/Api", "" + error);
                }
            }

            @Override
            public void onFailure(Call<MaterialList> call, Throwable t) {

                log("ComplainAct/GetNotificationDetails/URL/onFailure", call.request().url().toString());

//                dismissDialog();
                log("MainActivity/OnFailure", stackTraceToString(t));
//                toast("Error in Connection!");
            }
        });
    }

    private void putMaterialListinHashMap() {
//        List<MaterialsSet> materialsSetList = new ArrayList<>();
//        for(int i=0; i<materialList.size(); i++){
//            MaterialsSet temp = new MaterialsSet();
//            temp.setMatnr(Integer.toString(materialList.get(i).getMaterial_code()));
//            temp.setQuantity(Integer.toString(materialList.get(i).getQuantity()));
//            temp.setNotifNo(notiNum);
//            temp.setOrderNo(orderNum);
//            materialsSetList.add(temp);
//        }
        StringUtils.materialList.put(notiNum,materialList);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.equals(materialCatSp)) {

            // @TODO find a better way to do this.
            // Doing this

            int pos = materialCatSp.getSelectedItemPosition();

            if (pos != 0) {
                // Something is Selected
                String materialCat = mCatUnique.get(pos);
                //update current_fault_array based on faultCat selected
                updateCurrentMaterialArray(materialCat);

                //update Material Sub Category
                current_material_sub_cat = getSubCatArray(materialCat);





            } else {
                //nothing is selected so return all options
//                current_fault_array = total_fault_array;
//                current_fault_array = new ArrayList<>();
                copyArrayList(current_material_array,total_material_array);
                copySubCatList(current_material_sub_cat,mSubCatUnique);
            }

            ArrayAdapter<String> adapMaterialSubCat = new ArrayAdapter<>(this,
                    R.layout.simple_spinner_item, current_material_sub_cat );
            adapMaterialSubCat.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
            materialSubCatSp.setAdapter(adapMaterialSubCat);

            ArrayAdapter<Material> adapMaterial = new ArrayAdapter<>(this,
                    R.layout.simple_spinner_item, current_material_array);
            adapMaterial.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
            materialSp.setAdapter(adapMaterial);

        }
        else if (parent.equals(materialSubCatSp)) {

            // @TODO find a better way to do this.
            // Doing this

            int pos = materialSubCatSp.getSelectedItemPosition();

            if (pos != 0) {
                // Something is Selected
                String materialSubCat = current_material_sub_cat.get(pos);
                //update current_fault_array based on faultCat selected
                updateCurrentMaterialArrayThroughSubCat(materialSubCat);

            } else {
                //nothing is selected so return all options
//                current_fault_array = total_fault_array;
//                current_fault_array = new ArrayList<>();
                copyArrayList(current_material_array,total_material_array);
            }

            ArrayAdapter<Material> adapMaterial = new ArrayAdapter<>(this,
                    R.layout.simple_spinner_item, current_material_array);
            adapMaterial.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
            materialSp.setAdapter(adapMaterial);

        }

    }

    private List<String> getSubCatArray(String materialCat) {
        List<String> subCat_filtered = new ArrayList<>();
        subCat_filtered.add("-Please Select-");
        for (int i=1; i<total_material_array.size();i++){
            if(!subCat_filtered.contains(total_material_array.get(i).getMaterial_subcat()) && total_material_array.get(i).getMaterial_cat().equalsIgnoreCase(materialCat)){
                subCat_filtered.add(total_material_array.get(i).getMaterial_subcat());
            }
        }
        return subCat_filtered;

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void copyArrayList(List<Material> dest, List<Material> source) {
        dest.clear();
        for (int i = 0; i < source.size(); i++) {
            dest.add(new Material(source.get(i)));
        }
    }
    private void copySubCatList(List<String> dest, List<String> source) {

        dest.clear();
        for (int i = 0; i < source.size(); i++) {
            dest.add(source.get(i));
        }
    }


    private List<Material> combineMaterialSubArrays(String[] mCat, String[] material_array, String[] mSubCat, String[] mUnit, int[] mCode ){
        if(mCat.length != material_array.length || material_array.length != mSubCat.length || material_array.length != mUnit.length || material_array.length != mCode.length || material_array.length != material_array.length){
            //there is some error in data (arrays.xml) therefore it mismatches
            return null;
        } else {
            List<Material> materialList= new ArrayList<Material>();

            for (int i =0; i <material_array.length; i++){
                Material f = new Material(mCat[i],mSubCat[i],material_array[i], mUnit[i], mCode[i],1,notiNum,orderNum);
                materialList.add(f);
            }

            return materialList;

        }


    }
    private List<Material> setList(){
        mCatUnique = new ArrayList<>();
        mSubCatUnique = new ArrayList<>();
        List<Material> materialList= new ArrayList<Material>();
        for(int i=0;i<StringUtils.total_material_list.size();i++){
            Result r = StringUtils.total_material_list.get(i);
            Material temp = new Material(r.getCategory(),r.getSubcategory(),r.getMaktx(),r.getUnit(),Integer.parseInt(r.getMatnr()),1,notiNum,orderNum);
            materialList.add(temp);

            if(!mCatUnique.contains(r.getCategory())){
                mCatUnique.add(r.getCategory());
            }
            if(!mSubCatUnique.contains(r.getSubcategory())){
                mSubCatUnique.add(r.getSubcategory());
            }
        }

        return materialList;
    }




    private void updateCurrentMaterialArray(String materialCat){
        current_material_array.clear();
        for (int i=1; i<total_material_array.size();i++){
            if(total_material_array.get(i).getMaterial_cat().equalsIgnoreCase(materialCat)){
                current_material_array.add(total_material_array.get(i));
            }
        }

        //current_fault_array is updated

    }
    private void updateCurrentMaterialArrayThroughSubCat(String materialSubCat){
        current_material_array.clear();
        for (int i=1; i<total_material_array.size();i++){
            if(total_material_array.get(i).getMaterial_subcat().equalsIgnoreCase(materialSubCat)){
                current_material_array.add(total_material_array.get(i));
            }
        }

        //current_fault_array is updated

    }


    @Override
    public void selectedItem(Material material, String operation) {
        if(operation.equals("add")){
            //adding a quantity and then have to call the total

            material.setQuantity(material.getQuantity()+1);

        }else if(operation.equals("remove")){
            //removing a quantity and then have to call the total again

            material.setQuantity(material.getQuantity()-1);

        }

//        adapter.setItems(materialList);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void deleteItem(Material material) {

        materialList.remove(material);
//        adapter.setItems(materialList);
        adapter.notifyDataSetChanged();

    }
}