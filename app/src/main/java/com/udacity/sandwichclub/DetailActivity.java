package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private TextView mNameTextView;
    private TextView mAlsoKnownAsTextView;
    private TextView mPlaceOfOriginTextView;
    private TextView mDescriptionTextView;
    private TextView mIngridentsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        mNameTextView = findViewById(R.id.name_tv);
        mAlsoKnownAsTextView = findViewById(R.id.also_known_tv);
        mPlaceOfOriginTextView = findViewById(R.id.origin_tv);
        mDescriptionTextView = findViewById(R.id.description_tv);
        mIngridentsTextView = findViewById(R.id.ingredients_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        mNameTextView.setText(sandwich.getMainName());
        String alsoKnownAs = TextUtils.join(" | ", sandwich.getAlsoKnownAs());
        // If the alsoKnownAs is null then hide the field for better UI experience
        if(alsoKnownAs.isEmpty()) {
            findViewById(R.id.also_known_label_tv).setVisibility(View.GONE);
            mAlsoKnownAsTextView.setVisibility(View.GONE);
        } else {
            mAlsoKnownAsTextView.setText(alsoKnownAs);
        }
        // If the placeOfOrigin is null then hide the field for better UI experience
        if(sandwich.getPlaceOfOrigin().isEmpty()) {
            findViewById(R.id.origin_label_tv).setVisibility(View.GONE);
            mPlaceOfOriginTextView.setVisibility(View.GONE);
        } else {
            mPlaceOfOriginTextView.setText(sandwich.getPlaceOfOrigin());
        }
        mDescriptionTextView.setText(sandwich.getDescription());
        String ingredients = TextUtils.join(" | ", sandwich.getIngredients());
        mIngridentsTextView.setText(ingredients);
    }
}
