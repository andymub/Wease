package com.mub.wease.wease_one.Adapter;
/**
 * Created by Andymub on 28/01/2018.
 *
 */

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mub.wease.wease_one.Connexion.Device;
import com.mub.wease.wease_one.Data.Constants;
import com.mub.wease.wease_one.R;
import com.mub.wease.wease_one.UI.OptionsEPSPActivity;
import com.mub.wease.wease_one.UI.Order_Item_Activity;

import static com.facebook.FacebookSdk.getApplicationContext;

public class CustomAdapterOptions extends BaseAdapter{

    public final static String selectedOption="selectedOption";
    String [] result;
    Context context;
    int [] imageId;
    String type_Exam;
    int pos;
    private static LayoutInflater inflater=null;
    public CustomAdapterOptions(OptionsEPSPActivity optionsEPSPActivity, String[] osNameList, int[] osImages) {
        // TODO Auto-generated constructor stub
        result=osNameList;
        context=optionsEPSPActivity;
        imageId=osImages;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        optionsEPSPActivity.introwView= pos;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }


    public class Holder
    {
//        TextView os_text;
//        ImageView os_img;
        public TextView os_text;
        public ImageView os_img;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.sample_gridlayout_options, null);
        holder.os_text = rowView.findViewById(R.id.os_texts_options);
        holder.os_img = rowView.findViewById(R.id.os_images_options);

        holder.os_text.setText(result[position]);
        holder.os_img.setImageResource(imageId[position]);

        rowView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // Check for Internet Connection
                if (Device.isConnected()) {
                   // Toast.makeText(getApplicationContext(), "Internet Connected", Toast.LENGTH_SHORT).show();
                    Constants.setDatabasePathUploads("CULTURE");
                    // Toast.makeText(context, "You Clicked "+result[position], Toast.LENGTH_SHORT).show();
                    Intent intentOder_Item_activity= new Intent(context, Order_Item_Activity.class);
                    String option = ""+result[position];
                    intentOder_Item_activity.putExtra(selectedOption,option);
                    context.startActivity(intentOder_Item_activity);
                } else {
                    Toast.makeText(getApplicationContext(), R.string.deviceNotOnline, Toast.LENGTH_SHORT).show();
                }

            }
        });

        return rowView;
    }
}