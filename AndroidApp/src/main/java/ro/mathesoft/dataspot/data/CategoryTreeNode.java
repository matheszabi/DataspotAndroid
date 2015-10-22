package ro.mathesoft.dataspot.data;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by matheszabi on Oct/22/2015 0022.
 */
public class CategoryTreeNode {
    private Category category; // user object
    private ArrayList<CategoryTreeNode> childs;
    private boolean hasPromo;

    public CategoryTreeNode(Category category){
        this.category = category;
        childs = new ArrayList<CategoryTreeNode>();
    }

    public Category getCategory() {
        return category;
    }

    public ArrayList<CategoryTreeNode> getChilds() {
        return childs;
    }

    public int getChildsCount(){
        return childs.size();
    }

    public boolean hasPromo() {
        return hasPromo;
    }

    public static CategoryTreeNode create(ArrayList<Category> listCategory, ArrayList<Integer> catIDWithPromo) {


        ArrayList<Category> parentCategories = new ArrayList<Category>();// this are the bold ones

        for(int i = 0; i < listCategory.size();  i++){
            Category curCategory = listCategory.get(i);
            if(curCategory.getParentID() == null){
                parentCategories.add(curCategory);
            }
        }
        CategoryTreeNode root = new CategoryTreeNode(new Category()); // never visible

        for(Category category : parentCategories) {
            // add parent categories to root:
            CategoryTreeNode boldCategoryNode = new CategoryTreeNode(category);
            root.childs.add(boldCategoryNode);
            //  this doesn't have promo ever, but lets check it..
            if(catIDWithPromo.contains(category.getCatID())){
                 boldCategoryNode.hasPromo = true;
            }

            // add level 1 child:
            for (Category category1 : listCategory) {
                if(category1.getParentID() != null && category1.getParentID().intValue() == category.getCatID()){
                    CategoryTreeNode categoryNodeLevel1 = new CategoryTreeNode(category1);
                    boldCategoryNode.childs.add(categoryNodeLevel1);
                    if(catIDWithPromo.contains(category1.getCatID())){
                        categoryNodeLevel1.hasPromo = true;
                        boldCategoryNode.hasPromo = true;
                    }

                    // add level 2 categories
                    for (Category category2 : listCategory){
                        if(category2.getParentID() != null && category2.getParentID().intValue()  == category1.getCatID()) {
                            CategoryTreeNode categoryNodeLevel2 = new CategoryTreeNode(category2);
                            categoryNodeLevel1.childs.add(categoryNodeLevel2);
                            if(catIDWithPromo.contains(category2.getCatID())){
                                categoryNodeLevel2.hasPromo = true;
                                categoryNodeLevel1.hasPromo = true;
                                boldCategoryNode.hasPromo = true;
                            }
                        }
                    }
                }
            }
        }
        return root;
    }
}
