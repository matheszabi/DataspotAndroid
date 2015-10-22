package ro.mathesoft.dataspot.jsonparser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ro.mathesoft.dataspot.data.Category;

/**
 * Created by matheszabi on Oct/22/2015 0022.
 */
public class CategoriesPromoJsonParser {

    public static final String EXPECTED_ROOT_ELEMENT = "CategoriesIDPromo";

    public static ArrayList<Integer> parse(String jsonString) throws JSONException
    {
        JSONObject objRoot = new JSONObject(jsonString);
        if (!objRoot.has(EXPECTED_ROOT_ELEMENT)) {
            return null; // some error code / message here.
        }
        JSONArray rootValue = (JSONArray) objRoot.get(EXPECTED_ROOT_ELEMENT);

        ArrayList<Integer> listCategoryId = new ArrayList<Integer>(rootValue.length());
        for(int i=0; i < rootValue.length(); i++){
            JSONObject objCategory = (JSONObject) rootValue.get(i);
            Integer catId = objCategory.getInt("catID");
            listCategoryId.add(catId);
        }
        return listCategoryId;
    }
}
