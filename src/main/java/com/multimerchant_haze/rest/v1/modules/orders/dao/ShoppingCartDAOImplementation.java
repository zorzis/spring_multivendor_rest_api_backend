package com.multimerchant_haze.rest.v1.modules.orders.dao;

import com.multimerchant_haze.rest.v1.modules.orders.model.ShoppingCart;
import com.multimerchant_haze.rest.v1.modules.orders.model.ShoppingCartProduct;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;

/**
 * Created by zorzis on 11/8/2017.
 */
@Repository("persistShoppingCartDAO")
public class ShoppingCartDAOImplementation implements ShoppingCartDAO
{
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public ShoppingCart getShoppingCartByID(String shoppingCartID)
    {
        CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(ShoppingCart.class);

        Root<ShoppingCart> shoppingCartRoot = criteriaQuery.from(ShoppingCart.class);
        SetJoin<ShoppingCart, ShoppingCartProduct> shoppingCartProducts = (SetJoin)shoppingCartRoot.fetch("shoppingCartProducts", JoinType.LEFT);
        Join<ShoppingCart, Order> order = shoppingCartRoot.join("order", JoinType.LEFT);
        Predicate predicateShoppingCartIDForSearch = criteriaBuilder.equal(shoppingCartRoot.get("shoppingCartID"), shoppingCartID);

        TypedQuery<ShoppingCart> typedQuery = entityManager.createQuery(
                criteriaQuery
                        .select(shoppingCartRoot)
                        .where(predicateShoppingCartIDForSearch)
                        .distinct(true)
        );

        ShoppingCart shoppingCart = typedQuery.getSingleResult();
        return shoppingCart;
    }

    @Override
    public String persistShoppingCart(ShoppingCart shoppingCart)
    {
        this.entityManager.persist(shoppingCart);

        for(ShoppingCartProduct shoppingCartProduct: shoppingCart.getShoppingCartProducts())
        {
            entityManager.persist(shoppingCartProduct);
        }

        return shoppingCart.getShoppingCartID();
    }
}
