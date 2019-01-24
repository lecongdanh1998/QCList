package vn.edu.poly.qclist.RetrofitClient;

public class APIUtils {
    public static final  String Base_Url = "http://203.162.76.14:8080";
    public static DataClient getData(){
        return RetrofitClient.getClient(Base_Url).create(DataClient.class);
    }
}
