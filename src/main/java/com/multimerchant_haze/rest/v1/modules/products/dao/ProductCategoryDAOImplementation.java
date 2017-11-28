package com.multimerchant_haze.rest.v1.modules.products.dao;

import com.multimerchant_haze.rest.v1.modules.products.model.ProductCategory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by zorzis on 7/1/2017.
 */
@Repository("productCategoryDAO")
public class ProductCategoryDAOImplementation implements ProductCategoryDAO
{
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<ProductCategory> getAllProductsCategories()
    {
        try
        {
            String qlString = "SELECT productCategory " +
                    "FROM ProductCategory productCategory";
            Query query = entityManager.createQuery(qlString, ProductCategory.class);
            List<ProductCategory> productCategoriesList = (List<ProductCategory>)query.getResultList();
            return productCategoriesList;
        } catch (NoResultException e)
        {
            entityManager.clear();
            entityManager.close();
            return null;
        }
    }

    @Override
    public ProductCategory getCategoryByCategoryID(String categoryID)
    {
        try
        {
            String qlString = "SELECT productCategory " +
                    "FROM ProductCategory productCategory " +
                    "WHERE productCategory.categoryID = ?1";
            Query query = entityManager.createQuery(qlString, ProductCategory.class);
            query.setParameter(1, categoryID);
            ProductCategory productCategory = (ProductCategory)query.getSingleResult();
            return productCategory;
        } catch (NoResultException e)
        {
            entityManager.clear();
            entityManager.close();
            return null;
        }
    }

    @Override
    public ProductCategory getCategoryByCategoryName(String categoryName)
    {
        try
        {
            String qlString = "SELECT productCategory " +
                    "FROM ProductCategory productCategory " +
                    "WHERE productCategory.categoryName = ?1";
            Query query = entityManager.createQuery(qlString, ProductCategory.class);
            query.setParameter(1, categoryName);
            ProductCategory productCategory = (ProductCategory)query.getSingleResult();
            return productCategory;
        } catch (NoResultException e)
        {
            entityManager.clear();
            entityManager.close();
            return null;
        }
    }
}
