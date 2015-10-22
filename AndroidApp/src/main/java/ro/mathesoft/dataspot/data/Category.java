package ro.mathesoft.dataspot.data;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by matheszabi on Oct/22/2015 0022.
 */
public class Category {
    private int catID;
    private String catName;
    private Integer parentID; // can be null

    public  Category(){}


    public static Category fromJson(JSONObject jsonObject) throws JSONException {
        if(jsonObject == null){
            return null;
        }
        Category category = new Category();
        category.catID = jsonObject.getInt("catID");
        category.catName = jsonObject.getString("catName");
        // main categories has null here:
        if( !jsonObject.isNull("parentID") ) {
            category.parentID = jsonObject.getInt("parentID");
        }

        return category;
    }

    public int getCatID() {
        return catID;
    }

    public String getCatName() {
        return catName;
    }

    public Integer getParentID() {
        return parentID;
    }
}
