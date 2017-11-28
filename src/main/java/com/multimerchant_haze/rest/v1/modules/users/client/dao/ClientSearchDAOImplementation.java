package com.multimerchant_haze.rest.v1.modules.users.client.dao;

import com.multimerchant_haze.rest.v1.modules.users.producer.model.Producer;
import com.multimerchant_haze.rest.v1.modules.users.producer.model.ProducerHasPaymentMethod;
import com.multimerchant_haze.rest.v1.modules.users.producer.model.ProducerProfile;
import com.multimerchant_haze.rest.v1.modules.payments.model.PaymentMethod;
import com.multimerchant_haze.rest.v1.modules.products.dto.ProductCategoryDTO;
import com.multimerchant_haze.rest.v1.modules.products.model.Product;
import com.multimerchant_haze.rest.v1.modules.products.model.ProductCategory;
import com.multimerchant_haze.rest.v1.modules.products.model.ProductDetails;
import com.multimerchant_haze.rest.v1.modules.products.model.ProductStock;
import com.multimerchant_haze.rest.v1.modules.users.client.dto.SearchFilterCriteriaDTO;
import com.multimerchant_haze.rest.v1.modules.users.producer.model.ProducerAddress;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zorzis on 10/29/2017.
 */
@Repository("clientSearchDAO")
public class ClientSearchDAOImplementation implements ClientSearchDAO
{
    @PersistenceContext
    private EntityManager entityManager;



    @Override
    public List<Producer> searchProducersBasedOnFilterCriteria(SearchFilterCriteriaDTO params)
    {
        CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(Producer.class);

        Root<Producer> producerRoot = criteriaQuery.from(Producer.class);
        Join<Producer, ProducerProfile> producerProfile = producerRoot.join("producerProfile", JoinType.LEFT);
        Join<ProducerProfile, ProducerAddress> producerAddress = producerProfile.join("producerAddress", JoinType.LEFT);
        SetJoin<Producer, ProducerHasPaymentMethod> producerHasPaymentMethodSet = (SetJoin)producerRoot.fetch("producerHasPaymentMethodSet", JoinType.LEFT);
        Join<PaymentMethod, ProducerHasPaymentMethod> paymentMethod = producerHasPaymentMethodSet.join("paymentMethod", JoinType.LEFT);
        SetJoin<Producer, ProductStock> producerHasProducts = (SetJoin)producerRoot.fetch("producerHasProducts", JoinType.LEFT);
        Join<ProductStock, Product> product = producerHasProducts.join("product", JoinType.LEFT);
        Join<Product, ProductDetails> productDetails = product.join("productDetails", JoinType.LEFT);
        Join<Product, ProductCategory> productCategory = product.join("productCategory", JoinType.LEFT);

        Predicate requiredPred;
        List<Predicate> whereClausePredicateList = new ArrayList();


        // Check if producer account is enabled
        Predicate predicateIsAccountEnabled = criteriaBuilder.equal(producerRoot.get("isEnabled"), true);
        // Count Products Predicate gonna be used with MySQL HAVING clause not Where as COUNT work only with HAVING Clause imo ;O
        Predicate predicateCountProducts = criteriaBuilder.greaterThan(criteriaBuilder.count(producerRoot.join("producerHasProducts")), (long)0);
        // Count Payment Methods. Producer must have at least 1
        Predicate predicateCountPaymentMethods = criteriaBuilder.greaterThan(criteriaBuilder.count(producerRoot.join("producerHasPaymentMethodSet")), (long)0);
        // Check if product has enough quantity to serve a client minimul acceptable order limit
        Predicate predicateIsProductStockQuantityEnoughForSale = criteriaBuilder.greaterThan(producerRoot.join("producerHasProducts").get("quantity"), (long)0.49);
        // Check if payment method is not Deactivated
        Predicate predicateIsProducerPaymentMethodNotDeactivated = criteriaBuilder.equal(producerRoot.join("producerHasPaymentMethodSet")
                .get("isDeactivated"), false);
        // Check if payment method is not Terminated
        Predicate predicateIsProducerPaymentMethodNotTerminated = criteriaBuilder.equal(producerRoot.join("producerHasPaymentMethodSet")
                .get("isTerminated"), false);
        Predicate predicatePaymentMethodsRestrictions = criteriaBuilder.and(predicateIsProducerPaymentMethodNotDeactivated, predicateIsProducerPaymentMethodNotTerminated);

        if(!(params.isHasAniseed() == null))
        {
            Predicate predicateHasAniseed = criteriaBuilder.equal(
                    producerRoot.join("producerHasProducts")
                            .join("product")
                            .join("productDetails").get("hasAniseed"), params.isHasAniseed());
            requiredPred = criteriaBuilder.and(predicateIsAccountEnabled, predicateHasAniseed, predicateIsProductStockQuantityEnoughForSale, predicatePaymentMethodsRestrictions);
        }
        else
        {
            requiredPred = criteriaBuilder.and(predicateIsAccountEnabled);
        }


        Predicate newPredicate;
        if((params.getProductsCategories() != null) && !params.getProductsCategories().isEmpty())
        {
            for(ProductCategoryDTO productCategoryDTO : params.getProductsCategories())
            {

                System.out.println("Category From For Loop is: " + productCategoryDTO.toString());

                Predicate predicateCategory = criteriaBuilder.equal(
                        producerRoot
                                .join("producerHasProducts")
                                .join("product")
                                .join("productCategory"), new ProductCategory(productCategoryDTO));

                newPredicate = criteriaBuilder.and(requiredPred, predicateCategory);
                whereClausePredicateList.add(newPredicate);
            }
        }
        else
        {
            newPredicate = requiredPred;
            whereClausePredicateList.add(newPredicate);
        }

        Predicate havingClausePredicate = criteriaBuilder.and(predicateCountProducts, predicateCountPaymentMethods);

        TypedQuery<Producer> typedQuery = entityManager.createQuery(
                criteriaQuery
                .select(producerRoot)
                .where(criteriaBuilder.or(whereClausePredicateList.toArray(new Predicate[] {})))
                .having(havingClausePredicate)
                .orderBy(criteriaBuilder.desc(predicateCountProducts))
                .orderBy(criteriaBuilder.asc(producerRoot.get("producerProfile")))
                .distinct(true)
        );

        List<Producer> result;
        try
        {
            result = typedQuery.getResultList();
            return result;
        } catch (NoResultException e)
        {
            entityManager.clear();
            entityManager.close();
            return null;
        }

    }


}
