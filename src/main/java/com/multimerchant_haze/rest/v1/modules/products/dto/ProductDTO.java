package com.multimerchant_haze.rest.v1.modules.products.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.multimerchant_haze.rest.v1.modules.users.producer.dto.ProducerDTO;
import com.multimerchant_haze.rest.v1.modules.users.producer.model.Producer;
import com.multimerchant_haze.rest.v1.app.utils.jsonUtils.customSerialDeserial.CustomDateDeserializer;
import com.multimerchant_haze.rest.v1.app.utils.jsonUtils.customSerialDeserial.CustomDateSerializer;
import com.multimerchant_haze.rest.v1.app.utils.jsonUtils.jsonIgnoringFilter.JsonACL;
import com.multimerchant_haze.rest.v1.modules.products.model.Product;
import com.multimerchant_haze.rest.v1.modules.products.model.ProductCategory;
import com.multimerchant_haze.rest.v1.modules.products.model.ProductDetails;

import java.util.*;

/**
 * Created by zorzis on 6/14/2017.
 */
public class ProductDTO
{
    @JsonView(JsonACL.PublicView.class)
    private String productID;

    @JsonView(JsonACL.PublicView.class)
    private String productName;

    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonView(JsonACL.AdminsView.class)
    private Date createdAt;

    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonView(JsonACL.AdminsView.class)
    private Date updatedAt;

    @JsonView(JsonACL.PublicView.class)
    private ProductDetailsDTO productDetails = new ProductDetailsDTO();

    @JsonView(JsonACL.PublicView.class)
    private ProductCategoryDTO productCategory = new ProductCategoryDTO();

    @JsonView({
            JsonACL.SearchByProductsPublicView.class,
            JsonACL.OrderListBelongingToClient.class,
            JsonACL.OrderListBelongingToProducer.class
    })
    private ProducerDTO producer = new ProducerDTO();

    @JsonView(JsonACL.PublicView.class)
    private Float quantity;

    public ProductDTO()
    {

    }

    public ProductDTO(Product product)
    {
        this.productID = product.getProductID();
        this.productName = product.getProductName();
        this.createdAt = product.getCreatedAt();
        this.updatedAt = product.getUpdatedAt();

        this.quantity = product.getProductStockSet().getQuantity();
        this.productDetails = this.mapProductDetailsToProductDetailsDTO(product.getProductDetails());
        this.productCategory = this.mapProductCategoryToProductCategoryDTO(product.getProductCategory());
        this.producer = this.mapProducerToProducerDTO(product.getProductStockSet().getProducer());

    }


    public String getProductID()
    {
        return productID;
    }

    public void setProductID(String productID)
    {
        this.productID = productID;
    }

    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public Date getCreatedAt()
    {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt)
    {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt()
    {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt)
    {
        this.updatedAt = updatedAt;
    }

    public ProductDetailsDTO getProductDetails()
    {
        return productDetails;
    }

    public void setProductDetails(ProductDetailsDTO productDetails)
    {
        this.productDetails = productDetails;
    }

    public ProductCategoryDTO getProductCategory()
    {
        return productCategory;
    }

    public void setProductCategory(ProductCategoryDTO productCategory)
    {
        this.productCategory = productCategory;
    }

    public ProducerDTO getProducer()
    {
        return producer;
    }

    public void setProducer(ProducerDTO producer)
    {
        this.producer = producer;
    }

    public Float getQuantity()
    {
        return quantity;
    }

    public void setQuantity(Float quantity)
    {
        this.quantity = quantity;
    }



    private ProductCategoryDTO mapProductCategoryToProductCategoryDTO(ProductCategory productCategory)
    {
        if(productCategory == null)
        {
            return new ProductCategoryDTO();
        }
        else
        {
            return new ProductCategoryDTO(productCategory);
        }
    }

    private ProductDetailsDTO mapProductDetailsToProductDetailsDTO(ProductDetails productDetails)
    {
        if(productDetails == null)
        {
            return new ProductDetailsDTO();
        }
        else
        {
            return new ProductDetailsDTO(productDetails);
        }
    }


    private ProducerDTO mapProducerToProducerDTO(Producer producer)
    {
        if(producer == null)
        {
            return new ProducerDTO();
        }
        else
        {
            return new ProducerDTO(producer);
        }
    }

    public List<ProductDTO> mapProductsDTOsFromProductsEntities(List<Product> producerProductsEntities)
    {
        List<ProductDTO> productDTOSet = new ArrayList(0);

        for(Product productEntity :producerProductsEntities)
        {
            productDTOSet.add(new ProductDTO(productEntity));
        }
        return productDTOSet;
    }
}
