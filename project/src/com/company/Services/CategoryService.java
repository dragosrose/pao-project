package com.company.Services;

import com.company.Entities.Category;
import com.company.Repositories.Repository;

import java.util.Set;

public class CategoryService implements ICategoryService{
    private static CategoryService instance;

    private Repository<Category> categoryRepository = new Repository<>();

    private CategoryService() {};

    public static CategoryService getInstance(){
        if(instance == null)
            instance = new CategoryService();
        return instance;
    }

    @Override
    public void addCategory(Category category) {
        this.categoryRepository.create(category);
    }

    @Override
    public Set<Category> getCategories() {
        return this.categoryRepository.read();
    }

    @Override
    public Category getCategoryById(int id) {
        Set<Category> category = getCategories();

        for(Category i : category){
            if(i.getId() == id)
                return i;
        }
        return null;
    }

    @Override
    public void deleteCategoryById(int id) {
        Set<Category> orders = getCategories();

        for(Category i : orders){
            if(i.getId() == id){
                this.categoryRepository.delete(id);
            }

        }
    }
}
