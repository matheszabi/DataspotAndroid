package ro.mathesoft.dataspot.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import ro.mathesoft.dataspot.MainActivity;
import ro.mathesoft.dataspot.R;
import ro.mathesoft.dataspot.data.CategoryTreeNode;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnCategoryItemSelectionListener}
 * interface.
 */
public class CategoryItemFragment extends Fragment implements AbsListView.OnItemClickListener {

    private OnCategoryItemSelectionListener mListener;

    /**
     * The fragment's ExpandableListView .
     */
    private ExpandableListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ExpandableListAdapter mAdapter;
    private MainActivity mMainActivity;
    private CategoryTreeNode rootCategoryTreeNode;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CategoryItemFragment() {
        // TODO load the saved data after rotation
    }

    public void setMainActivity(MainActivity mainActivity) {
        mMainActivity = mainActivity;
    }

    public void setCategoryTreeNode(CategoryTreeNode categoryTreeNode) {
        rootCategoryTreeNode = categoryTreeNode;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            // store the primitive params
        }

        mAdapter = new CategoryExpandableListAdapter(mMainActivity, rootCategoryTreeNode);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categoryitem_list, container, false);

        // Set the adapter
        mListView = (ExpandableListView) view.findViewById(android.R.id.list);
        mListView.setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnCategoryItemSelectionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnCategoryItemSelectionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.

            mListener.onItemSelected((int) id);
        }
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnCategoryItemSelectionListener {
        void onItemSelected(int catId);
    }

}
