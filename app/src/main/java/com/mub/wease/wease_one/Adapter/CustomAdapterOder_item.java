package com.mub.wease.wease_one.Adapter;

/**
 * Created by Andymub on 25/01/2018.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mub.wease.wease_one.Background_HeavyWork.DownloadTask;
import com.mub.wease.wease_one.Connexion.Device;
import com.mub.wease.wease_one.Data.Constants;
import com.mub.wease.wease_one.R;
import com.mub.wease.wease_one.UI.Order_Item_Activity;
import com.mub.wease.wease_one.UI.WebViewDisplayActivity;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static com.facebook.FacebookSdk.getApplicationContext;

@SuppressWarnings("StatementWithEmptyBody")
public class CustomAdapterOder_item extends BaseAdapter{
    String [] result;
    String [] result_anne;
    String [] result_prix;
    String [] result_links;


    public CustomAdapterOder_item() {
    }

    String [] result_version;
    Context context;
    int [] imageId;
    private static LayoutInflater inflater=null;
     List <String> listeOfSelectedItem=new List<String>() {
         @Override
         public int size() {
             return 0;
         }

         @Override
         public boolean isEmpty() {
             return false;
         }

         @Override
         public boolean contains(Object o) {
             return false;
         }

         @NonNull
         @Override
         public Iterator<String> iterator() {
             return null;
         }

         @NonNull
         @Override
         public Object[] toArray() {
             return new Object[0];
         }

         @NonNull
         @Override
         public <T> T[] toArray(@NonNull T[] a) {
             return null;
         }

         @Override
         public boolean add(String s) {
             return false;
         }

         @Override
         public boolean remove(Object o) {
             return false;
         }

         @Override
         public boolean containsAll(@NonNull Collection<?> c) {
             return false;
         }

         @Override
         public boolean addAll(@NonNull Collection<? extends String> c) {
             return false;
         }

         @Override
         public boolean addAll(int index, @NonNull Collection<? extends String> c) {
             return false;
         }

         @Override
         public boolean removeAll(@NonNull Collection<?> c) {
             return false;
         }

         @Override
         public boolean retainAll(@NonNull Collection<?> c) {
             return false;
         }

         @Override
         public void clear() {

         }

         @Override
         public String get(int index) {
             return null;
         }

         @Override
         public String set(int index, String element) {
             return null;
         }

         @Override
         public void add(int index, String element) {

         }

         @Override
         public String remove(int index) {
             return null;
         }

         @Override
         public int indexOf(Object o) {
             return 0;
         }

         @Override
         public int lastIndexOf(Object o) {
             return 0;
         }

         @NonNull
         @Override
         public ListIterator<String> listIterator() {
             return null;
         }

         @NonNull
         @Override
         public ListIterator<String> listIterator(int index) {
             return null;
         }

         @NonNull
         @Override
         public List<String> subList(int fromIndex, int toIndex) {
             return null;
         }
     };

    public CustomAdapterOder_item(Order_Item_Activity order_Item_Activity, String[] osNameList, int[] osImages,String[] version,String[] Annee,String[] prix, String[] links) {
        // TODO Auto-generated constructor stub
        result=osNameList;
        context= order_Item_Activity;
        imageId=osImages;
        result_anne=Annee;
       // typeExam=item_exam_type;
        result_prix=prix;
        result_version=version;
        result_links=links;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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

        TextView os_text;
        ImageView os_img;
        TextView txt_Version;
        TextView txt_annee;
        TextView txt_prix;
        CheckBox chbxOderItem;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final Holder holder=new Holder();
        final View rowView;
        rowView = inflater.inflate(R.layout.sample_gridlayout, null);
        holder.os_text = rowView.findViewById(R.id.os_texts);
        holder.os_img = rowView.findViewById(R.id.os_images);
        holder.txt_Version = rowView.findViewById(R.id.txt_item_version);
        holder.txt_annee = rowView.findViewById(R.id.txt_item_annee);
        holder.txt_prix = rowView.findViewById(R.id.txtprix);
        holder.chbxOderItem=rowView.findViewById(R.id.checkBoxOderItem);

        holder.os_text.setText(result[position]);
        holder.txt_annee.setText(result_anne[position]);
        holder.txt_prix.setText(result_prix[position]);
        holder.txt_Version.setText(result_version[position]);
        holder.os_img.setImageResource(imageId[position]);


        rowView.setOnClickListener(new OnClickListener() {

            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (Device.isConnected())
                { Toast.makeText(context, "Downloading "+result[position]+"...", Toast.LENGTH_SHORT).show();

              //  else { v.setBackgroundColor(GRAY);}
                if (holder.chbxOderItem.isChecked()){
                    holder.chbxOderItem.setChecked(false);
                    listeOfSelectedItem.remove(result[position]);


                }else {
                    holder.chbxOderItem.setChecked(true);
                    listeOfSelectedItem.add(result[position]);

                    //Toast.makeText(context, "---- "+ dataListItemSelected.listeOfSelectedItemch.get(0).toString(), Toast.LENGTH_SHORT).show();

                }

                //go to a web view
//                Intent intent_display_inWebView= new Intent(context, WebViewDisplayActivity.class);
//                String link = ""+result_links[position];
//                intent_display_inWebView.putExtra(linkToLoad,link);
//                context.startActivity(intent_display_inWebView);

                //lounch an internet nav
                //Opening the upload file in browser using the upload url
                //CheckConnection checkConnection=new CheckConnection();
                new DownloadTask(context, result_links[position],result[position]+"_"+result_anne[position]+"_"+result_version[position]);
                    //Culture_Générale_2010_V1
                    String[] addUnderScre=result[position].split(" ");
                    Constants.setDatabaseFileName(addUnderScre[0]+"_"+addUnderScre[1]+"_"+result_anne[position]+"_"+result_version[position]);
                    //todo get url from order activity and send it to webact
                    Intent intentOder_Item_activity= new Intent(context, WebViewDisplayActivity.class);
                    context.startActivity(intentOder_Item_activity);
                    Toast.makeText(context, "This may take a few seconds ...", Toast.LENGTH_SHORT).show();

                }

                else {
                    Toast.makeText(getApplicationContext(), R.string.deviceNotOnline, Toast.LENGTH_SHORT).show();
                }
            }
        });





        return rowView;
    }


    public List<String> getSelectedItem ()
    {
        return  listeOfSelectedItem;
    }
    public void notifyDataSetChanged (){

    }
}
