package pk.com.ke.complaint.model;

import pk.com.ke.complaint.CompleteNotificationActivity;

public class Material {
        String material_cat;
        String material_subcat;
        String material_desc;
        String unit;
        int material_code;
        int quantity;
        String notiNum;
        String orderNum;

        public Material(String material_cat, String material_subcat, String material_desc, String unit, int material_code,int quantity, String notiNum, String orderNum){
            this.material_cat=material_cat;
            this.material_subcat = material_subcat;
            this.material_desc= material_desc;
            this.unit = unit;
            this.material_code = material_code;
            this.quantity = quantity;
            this.notiNum = notiNum;
            this.orderNum = orderNum;
        }

    public Material(Material f){
            this.material_cat=f.material_cat;
            this.material_subcat = f.material_subcat;
            this.material_desc= f.material_desc;
            this.unit = f.unit;
            this.material_code = f.material_code;
            this.quantity = f.quantity;
            this.notiNum = notiNum;
            this.orderNum = orderNum;
        }

        public String getMaterial_cat(){
            return material_cat;
        }
        public void setMaterial_cat(String Material_cat){
            this.material_cat = Material_cat;
        }

    public String getMaterial_subcat(){
        return material_subcat;
    }
    public void setMaterial_subcat(String material_subcat){
        this.material_subcat = material_subcat;
    }

    public String getMaterial_desc(){
        return material_desc;
    }
    public void setMaterial_desc(String material_desc){
        this.material_desc = material_desc;
    }

    public String getUnit(){
        return unit;
    }
    public void setUnit(String unit){
        this.unit = unit;
    }

    public int getMaterial_code(){
        return material_code;
    }
    public void setMaterial_code(int material_code){
        this.material_code = material_code;
    }

    public int getQuantity(){
        return quantity;
    }
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

        @Override
        public String toString() {
            return material_desc;
        }

    @Override
    public boolean equals(Object obj) {
        Material other=(Material) obj;
        return this.getMaterial_subcat()==other.getMaterial_subcat();
    }

    @Override
    public int hashCode() {
        return getMaterial_subcat().hashCode();
    }
}

