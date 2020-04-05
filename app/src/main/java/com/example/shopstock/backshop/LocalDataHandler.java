package com.example.shopstock.backshop;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class LocalDataHandler {
    private static final String TAG = "LocalDataHandler";
    private static final String ITEMS_FILENAME = "Items.json";
    private static final String STORE_CAT_FILENAME = "StoreCategories.json";
    private static final String STORE_CHAIN_FILENAME = "StoreChains.json";

    /*  Get the items list from local storage
        Returns null if the file was not found
     */
    public static Item[] getItemsFromLocal(Context context){
        String json = readStringFromFile(ITEMS_FILENAME, context);
        return ShopstockAPIHandler.parseIntoItems(json);
    }

    // TODO: Make methods to access categories and chains from local memory

    /* Save the json for the list of items into local storage
       Returns true if the save to storage was successful
     */
    public static boolean saveItemsToLocal(Context context, String json){
        Log.i(TAG, "Attempting to save the items list to internal storage");
        return saveStringToFile(json, ITEMS_FILENAME, context);
    }

    /* Save the json string for the list of store categories
        Returns true if the save to internal storage was successful
     */
    public static boolean saveStoreCategoriesToLocal(Context context, String json){
        Log.i(TAG, "Attempting to save the store categories to internal storage");
        return saveStringToFile(json, STORE_CAT_FILENAME, context);
    }

    /* Save the json string for the list of store categories
        Returns true if the save to internal storage was successful
     */
    public static boolean saveChainsToLocal(Context context, String json){
        Log.i(TAG, "Attempting to save the store chains to internal storage");
        return saveStringToFile(json, STORE_CHAIN_FILENAME, context);
    }

    /* Saves a string to the passed file name in internal storage
     */
    private static boolean saveStringToFile(String str, String filename, Context context){
        try{
            FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
            fos.write(str.getBytes("UTF-8"));
            Log.i(TAG, "File write to "+filename+" was a success");
            return true;
        }catch (Exception e){
            Log.i(TAG, "File write to "+filename+" was a failure");
            return false;
        }
    }

    /* Method to read the contents of a file and return a string
     */
    private static String readStringFromFile(String filename, Context context){
        // Setup of all things needed to read the file
        FileInputStream inputStream;
        InputStreamReader inputStreamReader;
        try {
            inputStream = context.openFileInput(filename);
            inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
        }catch(FileNotFoundException e){
            Log.e(TAG, filename+" was not found");
            return null;
        }catch (UnsupportedEncodingException e){
            Log.e(TAG, "Error. Unsupported character encoding");
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();

        // Reading the contents of the file
        String contents;
        try{
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null){
                stringBuilder.append(line).append('\n');
                line = reader.readLine();
            }
        }catch (IOException e){
            Log.e(TAG, "Error occurred in trying to open the contents of"+filename);
            return null;
        }finally {
            contents = stringBuilder.toString();
            return contents;
        }
    }
}
