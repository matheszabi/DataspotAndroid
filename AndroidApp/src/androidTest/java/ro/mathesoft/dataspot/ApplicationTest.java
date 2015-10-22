package ro.mathesoft.dataspot;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.text.TextUtils;

import org.json.JSONException;

import java.util.concurrent.CountDownLatch;

import ro.mathesoft.dataspot.data.CategoryTreeNode;
import ro.mathesoft.dataspot.jsonparser.CategoriesJsonParser;
import ro.mathesoft.dataspot.networktask.GetCategoriesTask;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {


    CategoryTreeNode mRootCategoryTreeNode;
    Exception mError = null;
    CountDownLatch signal = null;


    public ApplicationTest() {
        super(Application.class);
    }

    @Override
    protected void setUp() throws Exception {
        signal = new CountDownLatch(1);
    }

    @Override
    protected void tearDown() throws Exception {
        signal.countDown();
    }

    // InterruptedException: signal.await();
    public void testGetCategories() throws InterruptedException {
        GetCategoriesTask task = new GetCategoriesTask();
        task.setListener(new GetCategoriesTask.GetCategoriesTaskListener() {
            @Override
            public void onComplete(CategoryTreeNode rootCategoryTreeNode, Exception e) {
                mRootCategoryTreeNode = rootCategoryTreeNode;
                mError = e;
                signal.countDown();
            }
        });
        // development, not for live:
        task.execute("http://vps.mathesoft.ro/dataspot_webshop/");

        signal.await();


        assertNull(mError);
        assertNotNull(mRootCategoryTreeNode);
        assertTrue(mRootCategoryTreeNode.getChilds().size() > 0);

    }

}
