package com.multimerchant_haze.rest.v1.modules.users.client.dao;

import com.multimerchant_haze.rest.v1.modules.users.client.model.ClientAuthority;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Created by zorzis on 5/8/2017.
 */
@Repository("clientAuthorityDAO")
public class ClientAuthorityDAOImplementation implements ClientAuthorityDAO
{
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public ClientAuthority getAuthorityByAuthorityName(String authorityName)
    {
        try
        {
            String qlString = "SELECT clientAuthority " +
                    "FROM ClientAuthority clientAuthority " +
                    "WHERE clientAuthority.role = ?1";
            Query query = entityManager.createQuery(qlString, ClientAuthority.class);
            query.setParameter(1, authorityName);
            ClientAuthority clientAuthority = (ClientAuthority)query.getSingleResult();
            return clientAuthority;
        } catch (NoResultException e)
        {
            entityManager.clear();
            entityManager.close();
            return null;
        }
    }
}
