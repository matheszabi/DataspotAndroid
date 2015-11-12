package ro.mathesoft.dataspot.networktask;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import ro.mathesoft.dataspot.data.Category;
import ro.mathesoft.dataspot.data.CategoryTreeNode;
import ro.mathesoft.dataspot.jsonparser.CategoriesJsonParser;
import ro.mathesoft.dataspot.jsonparser.CategoriesPromoJsonParser;

/**
 * Created by matheszabi on Oct/21/2015 0021.
 */
public class GetCategoriesTask extends AsyncTask<String, Integer, CategoryTreeNode> {

    private static final String TAG = "GetCategoriesTask";

    private GetCategoriesTaskListener mListener = null;
    private Exception mError = null;

    // for debug:
    private String contentCategories, contentCategoriesPromo;
    private ArrayList<Category> listCategory;
    private ArrayList<Integer> catIDWithPromo;

    @Override
    protected CategoryTreeNode doInBackground(String... params) {

        CategoryTreeNode rootCategoryTreeNode = null;
        try {
            // get the JSON string from webservice:
            contentCategories = getJson(params[0] + "GetCategories.php");
            // parse the String to JSONArray and get the ArrayList<Category>:
            listCategory  = CategoriesJsonParser.parse(contentCategories);

            boolean logCategories = true;// switchj to false to not have the listing
            if (logCategories) {
                for (Category category : listCategory) {
                    Log.d("GetCategoriesTask", category.getCatName());
                }
            }


            // can be optimized the SQL at PHP to give all of the results, but not it is easy to switch between search

            // get the JSON string from webservice:
            contentCategoriesPromo = getJson(params[0] + "GetCategoriesPromo.php");
            // parse the String to JSONArray and get the ArrayList<Category>:
            catIDWithPromo  = CategoriesPromoJsonParser.parse(contentCategoriesPromo);


            // build a tree structure for expandable list model:
            rootCategoryTreeNode = CategoryTreeNode.create(listCategory, catIDWithPromo);
            // sort the categories by: preference, by promo, by last used, by most used...
        } catch (RuntimeException e) {
            mError = e;
        } catch (JSONException e) {
            mError = e;
        }


        return rootCategoryTreeNode;
    }

    public GetCategoriesTask setListener(GetCategoriesTaskListener listener) {
        this.mListener = listener;
        return this;
    }

    @Override
    protected void onPostExecute(CategoryTreeNode rootCategoryTreeNode) {
        if (this.mListener != null)
            this.mListener.onComplete(rootCategoryTreeNode, mError);
    }

    @Override
    protected void onCancelled() {
        if (this.mListener != null) {
            mError = new InterruptedException("AsyncTask cancelled");
            this.mListener.onComplete(null, mError);
        }
    }

    private String getJson(String address) {
        try {
            URL url = new URL(address);
            URLConnection conn = url.openConnection();


            StringBuffer sb = new StringBuffer();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            br.close();

            String jsonString =  sb.toString();

            return jsonString;

        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Invalid URL");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Network error");
        }
    }



    public interface GetCategoriesTaskListener {
         void onComplete(CategoryTreeNode rootCategoryTreeNode, Exception e);
    }
}
