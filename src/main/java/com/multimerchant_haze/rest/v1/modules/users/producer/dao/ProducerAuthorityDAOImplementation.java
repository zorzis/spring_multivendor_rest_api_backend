package com.multimerchant_haze.rest.v1.modules.users.producer.dao;

import com.multimerchant_haze.rest.v1.modules.users.producer.model.ProducerAuthority;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Created by zorzis on 3/12/2017.
 */
@Repository("producerAuthorityDAO")
public class ProducerAuthorityDAOImplementation implements ProducerAuthorityDAO
{
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public ProducerAuthority getAuthorityByAuthorityName(String role)
    {
        try
        {
            String qlString = "SELECT producerAuthority FROM ProducerAuthority producerAuthority WHERE producerAuthority.role = ?1";
            Query query = entityManager.createQuery(qlString, ProducerAuthority.class);
            query.setParameter(1, role);
            ProducerAuthority producerAuthority = (ProducerAuthority)query.getSingleResult();
            return producerAuthority;
        } catch (NoResultException e)
        {
            entityManager.clear();
            entityManager.close();
            return null;
        }
    }

}
