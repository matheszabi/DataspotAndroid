package ro.mathesoft.dataspot.jsonparser;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ro.mathesoft.dataspot.data.Category;
import ro.mathesoft.dataspot.data.CategoryTreeNode;

/**
 * Created by matheszabi on Oct/21/2015 0021.
 */
public class CategoriesJsonParser {

    public static final String EXPECTED_ROOT_ELEMENT = "categories";

    public static ArrayList<Category> parse(String jsonString) throws JSONException {
        JSONObject objRoot = new JSONObject(jsonString);
        if (!objRoot.has(EXPECTED_ROOT_ELEMENT)) {
            return null; // some error code / message here.
        }
        JSONArray rootValue = (JSONArray) objRoot.get(EXPECTED_ROOT_ELEMENT);
        ArrayList<Category> listCategory = new ArrayList<Category>(rootValue.length());
        for(int i=0; i < rootValue.length(); i++){
            JSONObject objCategory = (JSONObject) rootValue.get(i);
            Category category = Category.fromJson(objCategory);
            listCategory.add(category);
        }
        return listCategory;
    }
}
