package com.multimerchant_haze.rest.v1.modules.products.dao;

import com.multimerchant_haze.rest.v1.modules.products.model.Product;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by zorzis on 6/14/2017.
 */
@Repository("productsDAO")
public class ProductsDAOImplementation implements ProductsDAO
{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Product> getAllProducts()
    {
        try
        {
            String qlString = "SELECT product FROM Product product " +
                    "LEFT JOIN FETCH product.productCategory productCategory " +
                    "LEFT JOIN FETCH product.productDetails productDetails " +
                    "LEFT JOIN FETCH product.productStockSet productStockSet " +
                    "LEFT JOIN FETCH productStockSet.producer producer";
            Query query = entityManager.createQuery(qlString, Product.class);
            List<Product> productsList = (List<Product>)query.getResultList();

            return productsList;

        } catch (NoResultException e)
        {
            entityManager.clear();
            entityManager.close();
            return null;
        }
    }

    @Override
    public List<Product> getAllProductsBasedOnCategory(String categoryID)
    {
        return null;
    }

    @Override
    public List<Product> getAllProducetsBasedOnCategoryName(String categoryName)
    {
        return null;
    }

    @Override
    public List<Product> getAllProductsBasedOnProducer(String producerEmail)
    {
        try
        {
            String qlString = "SELECT product FROM Product product " +
                    "LEFT JOIN FETCH product.productCategory productCategory " +
                    "LEFT JOIN FETCH product.productDetails productDetails " +
                    "LEFT JOIN FETCH product.productStockSet productStockSet " +
                    "LEFT JOIN FETCH productStockSet.producer producer " +
                    "WHERE producer.email= ?1";
            Query query = entityManager.createQuery(qlString, Product.class);
            query.setParameter(1, producerEmail);
            List<Product> productsList = (List<Product>)query.getResultList();

            return productsList;

        } catch (NoResultException e)
        {
            entityManager.clear();
            entityManager.close();
            return null;
        }
    }

    public Product getSingleProductBasedOnProducerAndProductID(String producerEmail, String productID)
    {
        try
        {
            String qlString = "SELECT product FROM Product product " +
                    "LEFT JOIN FETCH product.productCategory productCategory " +
                    "LEFT JOIN FETCH product.productDetails productDetails " +
                    "LEFT JOIN FETCH product.productStockSet productStockSet " +
                    "LEFT JOIN FETCH productStockSet.producer producer " +
                    "WHERE producer.email= ?1 AND product.productID= ?2";
            Query query = entityManager.createQuery(qlString, Product.class);
            query.setParameter(1, producerEmail);
            query.setParameter(2, productID);
            Product product = (Product)query.getSingleResult();
            return product;
        } catch (NoResultException e)
        {
            entityManager.clear();
            entityManager.close();
            return null;
        }
    }

    @Override
    public String addNewProduct(Product product)
    {
        entityManager.persist(product.getProductStockSet());
        entityManager.persist(product.getProductDetails());
        return product.toString();
    }

    @Override
    public String updateProduct(Product product)
    {
        entityManager.merge(product.getProductStockSet());
        entityManager.merge(product.getProductDetails());
        return product.toString();
    }

    @Override
    public String deleteProduct(Product product)
    {

        Query qDeleteProduct = entityManager.createQuery("delete from Product product where product.productID in (?1)");
        qDeleteProduct.setParameter(1, product.getProductID());
        qDeleteProduct.executeUpdate();

        Query qDeleteProductDetails = entityManager.createQuery("delete from ProductDetails productDetails where productDetails.productDetailsID in (?1)");
        qDeleteProductDetails.setParameter(1, Long.valueOf(product.getProductDetails().getProductDetailsID()));
        qDeleteProductDetails.executeUpdate();

        Query qDeleteProductStock = entityManager.createQuery("delete from ProductStock productStock where productStock.productStockID in (?1)");
        qDeleteProductStock.setParameter(1, Long.valueOf(product.getProductStockSet().getProductStockID()));
        qDeleteProductStock.executeUpdate();

        return product.toString();



    }
}
