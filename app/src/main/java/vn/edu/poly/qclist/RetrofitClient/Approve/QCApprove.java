package vn.edu.poly.qclist.RetrofitClient.Approve;

public class QCApprove {
    private String gate_id;
    private String mc;
    private String moldy;
    private String burn;
    private String odor;
    private String remarks;

    public QCApprove(String gate_id, String mc, String moldy, String burn, String odor, String remarks) {
        this.gate_id = gate_id;
        this.mc = mc;
        this.moldy = moldy;
        this.burn = burn;
        this.odor = odor;
        this.remarks = remarks;
    }

    public String getGate_id() {
        return gate_id;
    }

    public void setGate_id(String gate_id) {
        this.gate_id = gate_id;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getMoldy() {
        return moldy;
    }

    public void setMoldy(String moldy) {
        this.moldy = moldy;
    }

    public String getBurn() {
        return burn;
    }

    public void setBurn(String burn) {
        this.burn = burn;
    }

    public String getOdor() {
        return odor;
    }

    public void setOdor(String odor) {
        this.odor = odor;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
