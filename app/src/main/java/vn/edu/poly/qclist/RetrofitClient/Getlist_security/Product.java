package vn.edu.poly.qclist.RetrofitClient.Getlist_security;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Product implements Serializable {
    @SerializedName("product")
    @Expose
    private String product;
    @SerializedName("approx_quantity")
    @Expose
    private String approx_quantity;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("mc")
    @Expose
    private String mc;
    @SerializedName("odor")
    @Expose
    private String odor;
    @SerializedName("supplier_name")
    @Expose
    private String supplier_name;
    @SerializedName("warehouse_id")
    @Expose
    private String warehouse_id;
    @SerializedName("burn")
    @Expose
    private String burn;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("moldy")
    @Expose
    private String moldy;
    @SerializedName("estimated_bags")
    @Expose
    private String estimated_bags;
    @SerializedName("vehicle")
    @Expose
    private String vehicle;
    @SerializedName("remarks")
    @Expose
    private String remarks;
    @SerializedName("warehouse_name")
    @Expose
    private String warehouse_name;
    @SerializedName("id")
    @Expose
    private String id;

    public Product(String product, String approx_quantity, String name, String mc, String odor, String supplier_name, String warehouse_id, String burn, String state, String moldy, String estimated_bags, String vehicle, String remarks, String warehouse_name, String id) {
        this.product = product;
        this.approx_quantity = approx_quantity;
        this.name = name;
        this.mc = mc;
        this.odor = odor;
        this.supplier_name = supplier_name;
        this.warehouse_id = warehouse_id;
        this.burn = burn;
        this.state = state;
        this.moldy = moldy;
        this.estimated_bags = estimated_bags;
        this.vehicle = vehicle;
        this.remarks = remarks;
        this.warehouse_name = warehouse_name;
        this.id = id;
    }

    public Product() {
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getApprox_quantity() {
        return approx_quantity;
    }

    public void setApprox_quantity(String approx_quantity) {
        this.approx_quantity = approx_quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getOdor() {
        return odor;
    }

    public void setOdor(String odor) {
        this.odor = odor;
    }

    public String getSupplier_name() {
        return supplier_name;
    }

    public void setSupplier_name(String supplier_name) {
        this.supplier_name = supplier_name;
    }

    public String getWarehouse_id() {
        return warehouse_id;
    }

    public void setWarehouse_id(String warehouse_id) {
        this.warehouse_id = warehouse_id;
    }

    public String getBurn() {
        return burn;
    }

    public void setBurn(String burn) {
        this.burn = burn;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMoldy() {
        return moldy;
    }

    public void setMoldy(String moldy) {
        this.moldy = moldy;
    }

    public String getEstimated_bags() {
        return estimated_bags;
    }

    public void setEstimated_bags(String estimated_bags) {
        this.estimated_bags = estimated_bags;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getWarehouse_name() {
        return warehouse_name;
    }

    public void setWarehouse_name(String warehouse_name) {
        this.warehouse_name = warehouse_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
