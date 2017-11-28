package com.multimerchant_haze.rest.v1.modules.users.producer.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.app.responseEntities.ResponseEntitySuccess;
import com.multimerchant_haze.rest.v1.app.utils.jsonUtils.jsonIgnoringFilter.JsonACL;
import com.multimerchant_haze.rest.v1.modules.products.dto.ProductDTO;
import com.multimerchant_haze.rest.v1.modules.users.producer.dto.ProducerDTO;
import com.multimerchant_haze.rest.v1.modules.users.producer.service.ProducerSpiritsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * Created by zorzis on 6/29/2017.
 */

@RestController
public class ProducersProductsController
{

    @Autowired
    private ProducerSpiritsService producerSpirtisService;

    @PreAuthorize("(hasAnyAuthority('ROLE_PRODUCER') and isOwner(#email)) or hasAnyAuthority('ROLE_ADMIN') ")
    @RequestMapping(value = "/producer/get_spirits", method = RequestMethod.POST)
    @JsonView(JsonACL.SearchByProducersPublicView.class)
    public ResponseEntity getProducerSpirits(@RequestParam(value = "email") String email)
            throws AppException
    {
        List<ProductDTO> getProducerSpirits = producerSpirtisService.getAllProductsByProducerEmail(email);
        return new ResponseEntity<List<ProductDTO>> (getProducerSpirits, HttpStatus.OK);
    }

    @PreAuthorize("(hasAnyAuthority('ROLE_PRODUCER') and isOwner(#email)) or hasAnyAuthority('ROLE_ADMIN') ")
    @RequestMapping(value = "/producer/get_producer_spirit", method = RequestMethod.POST)
    @JsonView(JsonACL.SearchByProducersPublicView.class)
    public ResponseEntity getSingleProducerSpirit(@RequestParam(value = "email") String email,
                                                  @RequestParam(value = "productID") String productID)
            throws AppException
    {
        ProducerDTO producerDTO = new ProducerDTO();
        producerDTO.setEmail(email);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductID(productID);

        ProductDTO getSingleProducerSpirit = producerSpirtisService.getSingleProducerSpiritBasedOnProducerEmailAndProductID(producerDTO, productDTO);
        return new ResponseEntity<ProductDTO> (getSingleProducerSpirit, HttpStatus.OK);
    }

    @PreAuthorize("(hasAnyAuthority('ROLE_PRODUCER') and isOwner(#email)) or hasAnyAuthority('ROLE_ADMIN') ")
    @RequestMapping(value = "/producer/add_spirit", method = RequestMethod.POST)
    public ResponseEntity addNewSpirit(@RequestParam(value = "email") String email,
                                        @RequestParam(value = "productName") String productName,
                                        @RequestParam(value = "productDescription") String productDescription,
                                        @RequestParam(value = "price") Float price,
                                        @RequestParam(value = "categoryID") String categoryID,
                                        @RequestParam(value = "quantity") Float quantity,
                                        @RequestParam(value = "hasAniseed") boolean hasAnissed,
                                        @RequestParam(value = "governmentDistillApprovalID") String governmentDistillApprovalID,
                                        @RequestParam(value = "dateDistilled") Date dateDistilled,
                                        @RequestParam(value = "alcoholVolume") Float alcoholVolume) throws AppException


    {
        ProducerDTO producerDTO = new ProducerDTO(email);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductName(productName);
        productDTO.getProductCategory().setCategoryID(categoryID);
        productDTO.setQuantity(quantity);

        productDTO.getProductDetails().setProductDescription(productDescription);
        productDTO.getProductDetails().setPrice(price);
        productDTO.getProductDetails().setHasAniseed(hasAnissed);
        productDTO.getProductDetails().setDateDistilled(dateDistilled);
        productDTO.getProductDetails().setGovernmentDistillApprovalID(governmentDistillApprovalID);
        productDTO.getProductDetails().setAlcoholVolume(alcoholVolume);


        String createdSpirit = producerSpirtisService.addNewProductByProducerEmail(producerDTO, productDTO);

        return new ResponseEntity<ResponseEntitySuccess>(new ResponseEntitySuccess(200,
                "Spirit: " + createdSpirit + " added successfully for Producer " + email), HttpStatus.OK);

    }

    @PreAuthorize("(hasAnyAuthority('ROLE_PRODUCER') and isOwner(#email)) or hasAnyAuthority('ROLE_ADMIN') ")
    @RequestMapping(value = "/producer/update_spirit", method = RequestMethod.POST)
    public ResponseEntity updateSpirit(@RequestParam(value = "email") String email,
                                       @RequestParam(value = "productID") String productID,
                                       @RequestParam(value = "productName") String productName,
                                       @RequestParam(value = "productDescription") String productDescription,
                                       @RequestParam(value = "price") Float price,
                                       @RequestParam(value = "categoryID") String categoryID,
                                       @RequestParam(value = "quantity") Float quantity,
                                       @RequestParam(value = "hasAniseed") boolean hasAnissed,
                                       @RequestParam(value = "governmentDistillApprovalID") String governmentDistillApprovalID,
                                       @RequestParam(value = "dateDistilled") Date dateDistilled,
                                       @RequestParam(value = "alcoholVolume") Float alcoholVolume) throws AppException


    {
        ProducerDTO producerDTO = new ProducerDTO(email);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductID(productID);
        productDTO.setProductName(productName);
        productDTO.getProductCategory().setCategoryID(categoryID);
        productDTO.setQuantity(quantity);

        productDTO.getProductDetails().setProductDescription(productDescription);
        productDTO.getProductDetails().setPrice(price);
        productDTO.getProductDetails().setAlcoholVolume(alcoholVolume);
        productDTO.getProductDetails().setDateDistilled(dateDistilled);
        productDTO.getProductDetails().setHasAniseed(hasAnissed);
        productDTO.getProductDetails().setGovernmentDistillApprovalID(governmentDistillApprovalID);

        String updatedSpirit = producerSpirtisService.updateProductByProducerEmailProductID(producerDTO, productDTO);

        return new ResponseEntity<ResponseEntitySuccess>(new ResponseEntitySuccess(200,
                "Spirit: " + updatedSpirit + " updated successfully for Producer " + email), HttpStatus.OK);

    }

    //@PreAuthorize("(hasAnyAuthority('ROLE_PRODUCER') and isOwner(#email)) or hasAnyAuthority('ROLE_ADMIN') ")
    @RequestMapping(value = "/producer/delete_spirit", method = RequestMethod.POST)
    public ResponseEntity deleteSpirit(@RequestParam(value = "email") String email,
                                       @RequestParam(value = "productID") String productID) throws AppException
    {

        ProducerDTO producerDTO = new ProducerDTO();
        producerDTO.setEmail(email);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductID(productID);

        String spiritForDeletion = producerSpirtisService.deleteProductByProducerEmailProductID(producerDTO, productDTO);


        return new ResponseEntity<ResponseEntitySuccess>(new ResponseEntitySuccess(200,
                "Spirit: " + spiritForDeletion + " deleted successfully for Producer " + email), HttpStatus.OK);

    }
}
