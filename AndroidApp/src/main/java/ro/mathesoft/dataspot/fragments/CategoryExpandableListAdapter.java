package ro.mathesoft.dataspot.fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import ro.mathesoft.dataspot.R;
import ro.mathesoft.dataspot.data.CategoryTreeNode;

/**
 * This support Category level 1 and level 2.
 * Need to add support for level 3
 * Created by matheszabi on Oct/30/2015 0030.
 */
public class CategoryExpandableListAdapter extends BaseExpandableListAdapter {

    private Activity mActivity;
    private CategoryTreeNode root;

    CategoryExpandableListAdapter(Activity activity, CategoryTreeNode categoryTreeNode) {
        this.mActivity = activity;
        root = categoryTreeNode;
    }

    @Override
    public int getGroupCount() {
        if (root == null || root.getChilds() == null) {
            return 0;
        }
        return root.getChildsCount();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (root == null || root.getChilds() == null) {
            return 0;
        }
        CategoryTreeNode childNode = root.getChilds().get(groupPosition);
        return childNode.getChildsCount();
    }

    @Override
    public Object getGroup(int groupPosition) {
        if (root == null || root.getChilds() == null) {
            return null;
        }
        CategoryTreeNode childNode = root.getChilds().get(groupPosition);
        return childNode.getCategory().getCatName();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if (root == null || root.getChilds() == null) {
            return null;
        }
        CategoryTreeNode childNode = root.getChilds().get(groupPosition);
        CategoryTreeNode grandChildNode = childNode.getChilds().get(childPosition);
        return grandChildNode.getCategory().getCatName();
    }

    @Override
    public long getGroupId(int groupPosition) {
        CategoryTreeNode childNode = root.getChilds().get(groupPosition);
        return childNode.getCategory().getCatID();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        CategoryTreeNode childNode = root.getChilds().get(groupPosition);
        CategoryTreeNode grandChildNode = childNode.getChilds().get(childPosition);
        return grandChildNode.getCategory().getCatID();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.category_list_group, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        TextView textView = new TextView(mActivity);
        textView.setText(getChild(groupPosition, childPosition).toString());
        return textView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
