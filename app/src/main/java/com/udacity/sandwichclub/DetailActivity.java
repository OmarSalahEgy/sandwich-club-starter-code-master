package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    ImageView image ;
    TextView alsoKnowTextView ;
    TextView placeOfOriginTextView ;
    TextView descriptionTextView;
    TextView ingredientsTextView ;


    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        image = (ImageView) findViewById(R.id.image_iv);
        alsoKnowTextView = (TextView)findViewById(R.id.also_known_tv);
        placeOfOriginTextView = (TextView)findViewById(R.id.origin_tv);
        descriptionTextView = (TextView)findViewById(R.id.description_tv);
        ingredientsTextView = (TextView)findViewById(R.id.ingredients_tv);

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
        }else

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(image);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        // set Text to alsoKnownTv
        if (sandwich.getAlsoKnownAs() != null && sandwich.getAlsoKnownAs().size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(sandwich.getAlsoKnownAs().get(0));

            for (int i = 1; i < sandwich.getAlsoKnownAs().size(); i++) {
                stringBuilder.append(", ");
                stringBuilder.append(sandwich.getAlsoKnownAs().get(i));
            }
            alsoKnowTextView.setText(stringBuilder.toString());
        } else {
            alsoKnowTextView.setVisibility(View.GONE);
        }

        // set Text to originTv
        if (sandwich.getPlaceOfOrigin().isEmpty()) {
            placeOfOriginTextView.setVisibility(View.GONE);
            placeOfOriginTextView.setVisibility(View.GONE);
        } else {
            placeOfOriginTextView.setText(sandwich.getPlaceOfOrigin());
        }

        // set Text to descriptionTv
        descriptionTextView.setText(sandwich.getDescription());

        // set Text to ingredientTv
        if (sandwich.getIngredients() != null && sandwich.getIngredients().size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("\u2022");
            stringBuilder.append(sandwich.getIngredients().get(0));

            for (int i = 1; i < sandwich.getIngredients().size(); i++) {
                stringBuilder.append("\n");
                stringBuilder.append("\u2022");
                stringBuilder.append(sandwich.getIngredients().get(i));
            }
            ingredientsTextView.setText(stringBuilder.toString());
        }

        // display the image
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(image);
    }
    }

