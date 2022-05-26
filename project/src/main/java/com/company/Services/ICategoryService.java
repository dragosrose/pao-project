package com.company.Services;

import com.company.Entities.Category;
import java.util.Set;

public interface ICategoryService {

    void addCategory(Category category);
    Set<Category> getCategories();
    Category getCategoryById(int id);
    void updateCategory(String name, int id);
    void deleteCategoryById(int id);


}
