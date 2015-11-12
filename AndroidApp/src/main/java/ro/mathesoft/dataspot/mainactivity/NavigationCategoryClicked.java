package ro.mathesoft.dataspot.mainactivity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.widget.RelativeLayout;

import ro.mathesoft.dataspot.MainActivity;
import ro.mathesoft.dataspot.R;
import ro.mathesoft.dataspot.data.CategoryTreeNode;
import ro.mathesoft.dataspot.fragments.CategoryItemFragment;
import ro.mathesoft.dataspot.networktask.GetCategoriesTask;

/**
 * Remove code part from MainActivity to be readable and to have fewer lines
 * Created by matheszabi on Oct/30/2015 0030.
 */
public class NavigationCategoryClicked {

    public static void handleCategoryNavigationClick(MainActivity mainActivity) {
        GetCategoriesTask task = new GetCategoriesTask();
        task.setListener(new GetCategoriesTaskListenerImpl(mainActivity));
        // development, not for live:
        task.execute("http://vps.mmathesoft.ro/dataspot_webshop/");
    }


    private static class GetCategoriesTaskListenerImpl implements GetCategoriesTask.GetCategoriesTaskListener {
        private MainActivity mMainActivity;

        private GetCategoriesTaskListenerImpl(MainActivity mainActivity) {
            mMainActivity = mainActivity;
        }

        @Override
        public void onComplete(CategoryTreeNode rootCategoryTreeNode, Exception e) {

            RelativeLayout rlContentMain = (RelativeLayout) mMainActivity.findViewById(R.id.rlContentMain);

            FragmentManager fragmentManager = mMainActivity.getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            CategoryItemFragment categoryItemFragment = new CategoryItemFragment();
            categoryItemFragment.setMainActivity(mMainActivity);
            categoryItemFragment.setCategoryTreeNode(rootCategoryTreeNode);

            rlContentMain.removeAllViews();
            fragmentTransaction.add(R.id.rlContentMain, categoryItemFragment, "categoryItemFragment");
            fragmentTransaction.commit();
        }
    }
}
