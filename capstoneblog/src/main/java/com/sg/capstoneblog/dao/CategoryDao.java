/**
 *@author Paul Delaney
 *date:
 *purpose:
 */
package com.sg.capstoneblog.dao;

import com.sg.capstoneblog.dto.Category;
import java.util.List;

public interface CategoryDao {

    Category getCategoryById(int id);

    List<Category> getAllCategories();

    Category addCategory(Category category);

    void editCategory(Category category);

    void deleteCategory(int categoryId);
}
