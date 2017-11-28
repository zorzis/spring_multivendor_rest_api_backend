package com.multimerchant_haze.rest.v1.modules.users.producer.service;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.products.dto.ProductDTO;
import com.multimerchant_haze.rest.v1.modules.users.producer.dto.ProducerDTO;

import java.util.List;

/**
 * Created by zorzis on 6/29/2017.
 */
public interface ProducerSpiritsService
{
    public List<ProductDTO> getAllProductsByProducerEmail(String email) throws AppException;

    public String addNewProductByProducerEmail(ProducerDTO producerDTO, ProductDTO productDTO) throws AppException;

    public String updateProductByProducerEmailProductID(ProducerDTO producerDTO, ProductDTO productDTO) throws AppException;

    public ProductDTO getSingleProducerSpiritBasedOnProducerEmailAndProductID(ProducerDTO producerDTO, ProductDTO productDTO) throws AppException;

    public String deleteProductByProducerEmailProductID(ProducerDTO producerDTO, ProductDTO productDTO) throws AppException;
}
