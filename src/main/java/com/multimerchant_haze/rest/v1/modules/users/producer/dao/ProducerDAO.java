package com.multimerchant_haze.rest.v1.modules.users.producer.dao;

import com.multimerchant_haze.rest.v1.modules.users.producer.model.Producer;
import com.multimerchant_haze.rest.v1.modules.users.producer.model.ProducerAddress;
import com.multimerchant_haze.rest.v1.modules.users.producer.model.ProducerHasAuthority;

import java.util.List;

/**
 * Created by zorzis on 3/2/2017.
 */
public interface ProducerDAO
{
    public List<Producer> getAllProducersNoFiltering();

    // get a Producer
    public Producer getProducerByEmailOnlyFromProducersTable(String producerEmail);

    public Producer getProducerByProducerIDOnlyFromProducersTable(String producerID);

    // get a Producer Fetching Profile
    public Producer getProducerByProducerEmailFetchingProfile(String producerEmail);

    // get Producer Addresses fetching also Profile
    public Producer getProducerByEmailFetchingAddressFetchingProfile(String producerEmail);

    // get a Producer Fetching Profile Fetching UserAuthorities
    public Producer getProducerByEmailFetchingProfileFetchingAuthoritiesFetchingAddress(String producerEmail);


    // get a Producer Entity by ProducerID Fetching Products
    public Producer getProducerByProducerIDFetchingAddressFetchingProducts(String producerID);


    // create a new Producer
    public String registerNewProducer(Producer producer);

    // delete a Producer
    public String deleteProducer(Producer producer);

    // delete Producer ProducerAuthority
    public String deleteProducerHasAuthority(ProducerHasAuthority producerHasAuthority);

    // delete Producer Address
    public String deleteProducerAddress(ProducerAddress producerAddress);


    // add Producer ProducerAuthority
    public String addProducerHasAuthority(ProducerHasAuthority producerHasAuthority);

    // add Producer ProducerAddress
    public String addProducerAddress(ProducerAddress producerAddress);

    // update Producer ProducerAddress
    public String updateProducerAddress(ProducerAddress producerAddress);

    // update a Producer
    public String updateProducer(Producer producer);

}
