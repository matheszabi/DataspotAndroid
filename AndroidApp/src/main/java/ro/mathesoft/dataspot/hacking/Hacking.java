package ro.mathesoft.dataspot.hacking;

import android.support.design.internal.NavigationMenuPresenter;
import android.support.design.widget.NavigationView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.NoSuchElementException;

/**
 * Created by matheszabi on Oct/23/2015 0023.
 */
public class Hacking {
    public static TextView getTextViewFromNavigationViewHeader(NavigationView navigationView, int resourceID) {

        try {
            Field field_mPresenter = NavigationView.class.getDeclaredField("mPresenter");// NavigationMenuPresenter
            field_mPresenter.setAccessible(true);
            Field field_mHeaderLayout = NavigationMenuPresenter.class.getDeclaredField("mHeaderLayout"); // LinearLayout
            field_mHeaderLayout.setAccessible(true);

            Object obj_mPresenter = field_mPresenter.get(navigationView);
            Object obj_mHeaderLayout = field_mHeaderLayout.get(obj_mPresenter);

            LinearLayout ll_mHeaderLayout = (LinearLayout) obj_mHeaderLayout;
            for (int i = ll_mHeaderLayout.getChildCount() - 1; i >= 0; i--) {
                View curGroupView = ll_mHeaderLayout.getChildAt(i); // this is a viewgroup, the root of the nav_header_main.xml

                if (curGroupView instanceof ViewGroup) {
                    ViewGroup llNavHeaderMain = (ViewGroup) curGroupView;

                    for (int j = llNavHeaderMain.getChildCount() - 1; j >= 0; j--) {
                        View curView = llNavHeaderMain.getChildAt(j);
                        if (curView.getId() == resourceID) {
                            return (TextView) curView;
                        }
                    }
                }
            }
        } catch (Exception ex) {
            return null;
        }

        throw new NoSuchElementException("Resource not found with id:" + resourceID);
    }
}
