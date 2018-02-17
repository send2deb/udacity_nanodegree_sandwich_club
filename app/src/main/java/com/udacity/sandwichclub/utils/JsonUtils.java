package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private final static String LOG_TAG = JsonUtils.class.getSimpleName();

    public static Sandwich parseSandwichJson(String json) {

        Sandwich sandwich = new Sandwich();

        try {
            JSONObject mainObject = new JSONObject(json);
            JSONObject nameObject = mainObject.getJSONObject("name");

            String sandwichMainName = nameObject.getString("mainName");
            sandwich.setMainName(sandwichMainName);
            System.out.println("Name: "+sandwichMainName);

            JSONArray jsonAlsoKnownAsArray = nameObject.getJSONArray("alsoKnownAs");
            List<String> sandwichAlsoKnownAs = new ArrayList<>();
            for(int i = 0; i < jsonAlsoKnownAsArray.length() ; i++) {
                sandwichAlsoKnownAs.add(jsonAlsoKnownAsArray.getString(i));
            }
            sandwich.setAlsoKnownAs(sandwichAlsoKnownAs);
            System.out.println("sandwichAlsoKnownAs: "+sandwichAlsoKnownAs);

            String sandwichPlaceOfOrigin = mainObject.getString("placeOfOrigin");
            sandwich.setPlaceOfOrigin(sandwichPlaceOfOrigin);
            System.out.println("sandwichPlaceOfOrigin: "+sandwichPlaceOfOrigin);

            String sandwichDescription = mainObject.getString("description");
            sandwich.setDescription(sandwichDescription);
            System.out.println("sandwichDescription: "+sandwichDescription);


            String sandwichImage = mainObject.getString("image");
            sandwich.setImage(sandwichImage);
            System.out.println("sandwichImage: "+sandwichImage);


            JSONArray jsonIngredientsArray = mainObject.getJSONArray("ingredients");
            List<String> sandwichIngredients = new ArrayList<>();
            for(int i = 0; i < jsonIngredientsArray.length() ; i++) {
                sandwichIngredients.add(jsonIngredientsArray.getString(i));
            }
            sandwich.setIngredients(sandwichIngredients);
            System.out.println("sandwichIngredients: "+sandwichIngredients);


        } catch (JSONException e) {
            Log.e(LOG_TAG, "Json parsing error");
            e.printStackTrace();
        }

        return sandwich;
    }
}
