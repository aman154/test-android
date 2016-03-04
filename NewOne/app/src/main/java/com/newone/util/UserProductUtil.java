package com.newone.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newone.model.MainResultsRoot;
import com.newone.model.ProductResults;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aman on 27/11/15.
 */
public class UserProductUtil {

    private static final String LOG_TAG = "USER_PRODUCT";

    public static double calculateProductsBill(Context context){
        double userBill = 0.0;
        List<ProductResults> productResultsList;
        productResultsList  = getUserProducts(context);
            if(productResultsList != null && productResultsList.size() > 0){
                for(int i=0; i<productResultsList.size(); i++){
                    userBill = userBill + (productResultsList.get(i).getQnt() * productResultsList.get(i).getPmrp());
                }
        }
        return userBill;
    }

    public static int getLocalProductCount(Context context){
        int count = 0;
        List<String> proCodeList;
        try{
            proCodeList  = getProCodeList(context);
            if(proCodeList != null && proCodeList.size() >0)
                count = proCodeList.size();
        }catch (Exception e){

        }
        return count;
    }

    public static boolean isProductBought(Context context,String proCode){
        boolean buoght =false;
        List<String> proCodeList;
        try{
            proCodeList  = getProCodeList(context);
            if(proCodeList != null && proCodeList.size() >0)
            buoght = (proCode != null && proCodeList.contains(proCode));
        }catch (Exception e){

        }
      return buoght;
    }

    public static int getProductQunt(Context context,String proCode){
        int qunt = 0;
        List<ProductResults> productResultsList;
        try{
            productResultsList  = getUserProducts(context);
            if(productResultsList != null && productResultsList.size() >0)
                for(int i=0; i<productResultsList.size(); i++) {
                  if(proCode != null && productResultsList.get(i).getPc().equals(proCode)){
                      qunt = productResultsList.get(i).getQnt();
                  }
                }
        }catch (Exception e){

        }
        return qunt;
    }

    public static List<String> getProCodeList(Context context){
        List<String> proCodeList = null;
        List<ProductResults> productResultsList;
        productResultsList = getUserProducts(context);
        if(productResultsList != null && productResultsList.size() > 0){
            proCodeList = new ArrayList<>();
            for(int i =0; i<productResultsList.size(); i++){
                proCodeList.add(productResultsList.get(i).getPc());
            }
        }
        if(proCodeList == null){
            proCodeList = new ArrayList<>();
        }
        return proCodeList;
    }


    public static void incrProductQunt(Context context,String proCode){
        Log.i(LOG_TAG,"incr product of cart");
        List<ProductResults> localProductList;
        try {
            if(proCode != null && proCode.length() > 1) {
                localProductList = getUserProducts(context);
                if (localProductList != null && localProductList.size() > 0) {
                    for (int i = 0; i < localProductList.size(); i++) {

                        if (proCode.equals(localProductList.get(i).getPc())) {

                            int qunt = localProductList.get(i).getQnt();
                            localProductList.get(i).setQnt(++qunt);
                        }
                    }

                }
                saveProduct(context, localProductList);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void decrProductQunt(Context context,String proCode){
        Log.i(LOG_TAG,"decr product of cart");
        List<ProductResults> localProductList;
        try {
            if(proCode != null && proCode.length() > 1) {
                localProductList = getUserProducts(context);
                if (localProductList != null && localProductList.size() > 0) {
                    for (int i = 0; i < localProductList.size(); i++) {

                        if (proCode.equals(localProductList.get(i).getPc())) {

                            int qunt = localProductList.get(i).getQnt();
                            localProductList.get(i).setQnt(--qunt);
                        }
                    }

                }
                saveProduct(context, localProductList);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    public static void addProductToCart(Context context, ProductResults productResult){
        Log.i(LOG_TAG,"start adding product to cart");
        List<ProductResults> localProductList;
        try {
            if(productResult != null && productResult.getPc() != null) {
                String proCode = productResult.getPc();
                Log.i(LOG_TAG,"adding product code - "+proCode);
                localProductList = getUserProducts(context);
                if(!isProductBought(context,proCode)){
                  /*  Gson gson = new Gson();
                    String resultJson = gson.toJson(productResult);
                    ProductResults copiedResult = gson.fromJson(resultJson, ProductResults.class);*/
                    //Add to localProduct list
                    localProductList.add(0, productResult);
                    //Save
                    saveProduct(context, localProductList);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void removeProductFromCart(Context context, String proCode){
        List<ProductResults> localProductList;
        try {
            if(proCode != null && proCode.length() > 1) {
                localProductList = getUserProducts(context);
                if (localProductList != null && localProductList.size() > 0) {
                    for (int i = 0; i < localProductList.size(); i++) {

                        if (proCode.equals(localProductList.get(i).getPc())) {

                            localProductList.remove(i);
                            Log.i(LOG_TAG, "removeProductFromCart proCode - "+proCode);
                        }
                    }

                }
                saveProduct(context, localProductList);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void saveProduct(Context context, List<ProductResults> localProList){

        try {
            if (localProList != null) {
                //Trim
                /*int count = getUserProducts(context).size();
                if (localProList.size() > count) {
                    localProList = localProList.subList(0, count);
                }*/
                //Convert
                Gson gson = new Gson();
                String json = gson.toJson(localProList);
                //Save
                Log.i(LOG_TAG,"user product saving json - "+json);
                saveUserProductsPref(context, AppConstants.KEY_LOCAL_USER_PRODUCT, json);
            }
        } catch (Exception e) {
        }
    }

    public static List<ProductResults> getUserProducts(Context context){
        List<ProductResults> proList = null;
        try{
            String value = getUserProductsPref(context,AppConstants.KEY_LOCAL_USER_PRODUCT);


            if(value == null && value.isEmpty()) {
                proList = new ArrayList<>();
            }else {
                Gson gson = new Gson();
                Type type = new TypeToken<List<ProductResults>>() {
                }.getType();
                proList = gson.fromJson(value, type);

            }
        }catch(Exception e){
            e.printStackTrace();
        }

        if(proList == null){
            proList = new ArrayList<>();
        }

        return proList;
    }


    public static String getUserProductsPref(Context context, String key) {
        return (context.getSharedPreferences(AppConstants.SAVE_PREF_FILE_NAME, Context.MODE_PRIVATE)).getString(key, AppConstants.SHARED_PREF_DEFAULT_VAL_STRING);
    }

    public static boolean saveUserProductsPref(Context context, String key, String value) {
        SharedPreferences sharedPref = context.getSharedPreferences(AppConstants.SAVE_PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        boolean saved = editor.commit();
        return saved;
    }
}
