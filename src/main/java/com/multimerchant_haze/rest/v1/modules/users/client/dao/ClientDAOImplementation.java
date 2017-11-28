package com.multimerchant_haze.rest.v1.modules.users.client.dao;

import com.multimerchant_haze.rest.v1.modules.users.client.model.*;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.Set;

/**
 * Created by zorzis on 5/8/2017.
 */
@Repository("clientDAO")
public class ClientDAOImplementation implements ClientDAO
{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Client> getAllClientsNoFiltering()
    {
        String sqlString = null;
        sqlString = "SELECT client FROM Client client";
        TypedQuery<Client> query = entityManager.createQuery(sqlString, Client.class);
        return query.getResultList();
    }


    @Override
    public Client getClientByEmailOnlyFromClientsTable(String clientEmail)
    {
        try
        {
            String qlString = "SELECT client FROM Client client WHERE client.email = ?1";
            Query query = entityManager.createQuery(qlString, Client.class);
            query.setParameter(1, clientEmail);
            Client client = (Client)query.getSingleResult();
            return client;
        } catch (NoResultException e)
        {
            entityManager.clear();
            entityManager.close();
            return null;
        }
    }


    @Override
    public Client getClientByClientEmailFetchingProfileFetchVerificationToken(String clientEmail)
    {
        CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(Client.class);

        Root<Client> clientRoot = criteriaQuery.from(Client.class);
        Join<Client, ClientVerificationToken> clientVerificationTokenJoin = clientRoot.join("clientVerificationToken", JoinType.LEFT);
        Join<Client, ClientProfile> clientProfile = clientRoot.join("clientProfile", JoinType.LEFT);

        Predicate predicateClientEmailForSearch = criteriaBuilder.equal(clientRoot.get("email"), clientEmail);

        TypedQuery<Client> typedQuery = entityManager.createQuery(
                criteriaQuery
                        .select(clientRoot)
                        .where(predicateClientEmailForSearch)
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
    public Client getClientByEmailFetchingAddressesFetchingProfile(String clientEmail)
    {
        try
        {
            String qlString = "SELECT client FROM Client client " +
                    "LEFT JOIN FETCH client.clientProfile clientProfile " +
                    "LEFT JOIN FETCH client.clientHasAddresses clientHasAddresses " +
                    "LEFT JOIN FETCH clientHasAddresses.clientAddress clientAddress " +
                    "WHERE client.email= ?1";
            Query query = entityManager.createQuery(qlString, Client.class);
            query.setParameter(1, clientEmail);
            Client client = (Client)query.getSingleResult();
            return client;
        } catch (NoResultException e)
        {
            entityManager.clear();
            entityManager.close();
            return null;
        }
    }


    @Override
    public Client getClientByEmailFetchingProfileFetchingAuthoritiesFetchingAddresses(String clientEmail)
    {
        try
        {
            String qlString = "SELECT client FROM Client client " +
                    "LEFT JOIN FETCH client.clientProfile clientProfile " +
                    "LEFT JOIN FETCH client.clientHasAuthorities clientHasAuthorities " +
                    "LEFT JOIN FETCH clientHasAuthorities.clientAuthority clientAuthority " +
                    "LEFT JOIN FETCH client.clientHasAddresses clientHasAddresses " +
                    "LEFT JOIN FETCH clientHasAddresses.clientAddress clientAddress " +
                    "WHERE client.email= ?1";
            Query query = entityManager.createQuery(qlString, Client.class);
            query.setParameter(1, clientEmail);
            Client client = (Client)query.getSingleResult();
            return client;
        } catch (NoResultException e)
        {
            entityManager.clear();
            entityManager.close();
            return null;
        }
    }

    @Override
    public String createClient(Client client)
    {
        entityManager.persist(client);
        return client.getEmail();
    }

    @Override
    public Client registerNewClient(Client client)
    {
        // using the intermediate parent class ClientHasAuthority to persist both user and user authorities
        Set<ClientHasAuthority> clientHasAuthorities = client.getClientHasAuthorities();
        for(ClientHasAuthority clientHasAuthority : clientHasAuthorities)
        {
            entityManager.persist(clientHasAuthority);
        }
        return client;
    }

    @Override
    public String deleteClient(Client client)
    {
        Client clientToBeDeleted = entityManager.find(Client.class, client.getId());
        entityManager.remove(clientToBeDeleted);
        return client.getEmail();
    }

    @Override
    public String deleteClientHasAuthority(ClientHasAuthority clientHasAuthority)
    {
        ClientHasAuthority clientHasAuthorityRowToBeRemoved = entityManager.find(ClientHasAuthority.class, clientHasAuthority.getId());
        entityManager.remove(clientHasAuthorityRowToBeRemoved);
        return clientHasAuthority.getClientAuthority().getRole();
    }

    @Override
    public String deleteClientHasAddress(ClientHasAddress clientHasAddress)
    {
        ClientHasAddress clientHasAddressRowToBeRemoved = entityManager.find(ClientHasAddress.class, clientHasAddress.getId());
        ClientAddress clientAddressToBeRemoved = entityManager.find(ClientAddress.class, clientHasAddress.getClientAddress().getId());
        entityManager.remove(clientHasAddressRowToBeRemoved);
        entityManager.remove(clientAddressToBeRemoved);
        return clientHasAddress.getClientAddress().toString();
    }

    @Override
    public String addClientHasAuthority(ClientHasAuthority clientHasAuthority)
    {
        Client clientToBeMerged = entityManager.find(Client.class, clientHasAuthority.getClient().getId());
        clientHasAuthority.setClient(clientToBeMerged);

        entityManager.persist(clientHasAuthority);
        return clientHasAuthority.getClientAuthority().getRole();
    }

    @Override
    public String addClientHasAddress(ClientHasAddress clientHasAddress)
    {
        entityManager.persist(clientHasAddress);
        return clientHasAddress.getClientAddress().toString();
    }

    @Override
    public String updateClient(Client client)
    {
        //TODO think about partial update and full update
        entityManager.merge(client);

        return client.getEmail();
    }
}
