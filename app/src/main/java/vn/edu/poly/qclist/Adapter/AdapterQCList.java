package vn.edu.poly.qclist.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import vn.edu.poly.qclist.R;
import vn.edu.poly.qclist.RetrofitClient.Getlist_security.Product;

public class AdapterQCList extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    ArrayList<Product> arrayList;

    public AdapterQCList(Context context, ArrayList<Product> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.custom_listview,null);
            viewHolder.txt_barcode = convertView.findViewById(R.id.txt_barcode);
            viewHolder.btn_details = convertView.findViewById(R.id.btn_details);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Product product = arrayList.get(position);
        viewHolder.txt_barcode.setText(product.getName());
        return convertView;
    }

    class ViewHolder{
        ImageView btn_details;
        TextView txt_barcode;
    }
}
