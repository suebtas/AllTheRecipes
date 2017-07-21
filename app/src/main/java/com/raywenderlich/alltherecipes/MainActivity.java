/*
 * Copyright (c) 2016 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.raywenderlich.alltherecipes;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import android.widget.*;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


public class MainActivity extends AppCompatActivity {

  public static final String TAG = MainActivity.class.getSimpleName();

  private ListView mListView;
  String url = "https://drive.google.com/open?id=0B3ce1t0Uk91Wdlc3LWw4Q002b28";

  ProgressDialog dialog;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);


    dialog = new ProgressDialog(this);
    dialog.setMessage("Loading....");
    dialog.show();

    StringRequest request = new StringRequest(url, new Response.Listener<String>() {
      @Override
      public void onResponse(String string) {
        parseJsonData(string);
      }
    }, new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError volleyError) {
        Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
        dialog.dismiss();
      }
    });

    RequestQueue rQueue = Volley.newRequestQueue(MainActivity.this);
    rQueue.add(request);
  }

  void parseJsonData(String jsonString) {
    try {
      final ArrayList<Recipe> recipeList = Recipe.getRecipesFromJSON(jsonString);

      // Create adapter
      RecipeAdapter adapter = new RecipeAdapter(this, recipeList);

      mListView = (ListView)findViewById(R.id.recipe_list_view);
      mListView.setAdapter(adapter);

    } catch (Exception e) {
      e.printStackTrace();
    }

    dialog.dismiss();
  }
  /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      final Context context = this;


      // Get data to display
      final ArrayList<Recipe> recipeList = Recipe.getRecipesFromFile("recipes.json", this);

      // Create adapter
      RecipeAdapter adapter = new RecipeAdapter(this, recipeList);

      // Create list view
      mListView = (ListView) findViewById(R.id.recipe_list_view);
      mListView.setAdapter(adapter);

      // Set what happens when a list view item is clicked
      mListView.setOnItemClickListener(new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
          Recipe selectedRecipe = recipeList.get(position);

          Intent detailIntent = new Intent(context, RecipeDetailActivity.class);
          detailIntent.putExtra("title", selectedRecipe.title);
          detailIntent.putExtra("url", selectedRecipe.instructionUrl);

          startActivity(detailIntent);
        }

      });
    }
*/

}
