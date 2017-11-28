package com.multimerchant_haze.rest.v1.modules.users.client.dao;

import com.multimerchant_haze.rest.v1.modules.users.client.model.Client;
import com.multimerchant_haze.rest.v1.modules.users.client.model.ClientVerificationToken;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;

/**
 * Created by zorzis on 11/13/2017.
 */

@Repository("clientVerificationTokenDAO")
public class ClientVerificationTokenDAOImplementation implements ClientVerificationTokenDAO
{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ClientVerificationToken getClientVerificationToken(String token)
    {
        CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(ClientVerificationToken.class);

        Root<ClientVerificationToken> clientVerificationTokenRoot = criteriaQuery.from(ClientVerificationToken.class);
        Join<ClientVerificationToken, Client> client = clientVerificationTokenRoot.join("client", JoinType.LEFT);

        Predicate predicateTokenForSearch = criteriaBuilder.equal(clientVerificationTokenRoot.get("token"), token);

        TypedQuery<ClientVerificationToken> typedQuery = entityManager.createQuery(
                criteriaQuery
                        .select(clientVerificationTokenRoot)
                        .where(predicateTokenForSearch)
                        .distinct(true)
        );

        ClientVerificationToken clientVerificationToken;

        try
        {
            clientVerificationToken = typedQuery.getSingleResult();

        } catch (NoResultException e)
        {
            entityManager.clear();
            entityManager.close();
            return null;
        }
        return clientVerificationToken;
    }

    @Override
    public Client getClientByVerificationToken(String token)
    {
        CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(Client.class);

        Root<Client> clientRoot = criteriaQuery.from(Client.class);
        Join<Client, ClientVerificationToken> clientVerificationTokenJoin = clientRoot.join("clientVerificationToken", JoinType.LEFT);

        Predicate predicateTokenForSearch = criteriaBuilder.equal(clientRoot.join("clientVerificationToken").get("token"), token);

        TypedQuery<Client> typedQuery = entityManager.createQuery(
                criteriaQuery
                        .select(clientRoot)
                        .where(predicateTokenForSearch)
                        .distinct(true)
        );

        Client client;

        try
        {
            client = typedQuery.getSingleResult();

        } catch (NoResultException e)
        {
            entityManager.clear();
            entityManager.close();
            return null;
        }
        return client;
    }

    @Override
    public Client persistClientVerificationToken(Client client)
    {
        this.entityManager.merge(client);
        return client;
    }
}
