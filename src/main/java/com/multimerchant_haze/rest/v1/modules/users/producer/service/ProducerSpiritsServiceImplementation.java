package com.multimerchant_haze.rest.v1.modules.users.producer.service;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.products.dao.ProductCategoryDAO;
import com.multimerchant_haze.rest.v1.modules.products.dto.ProductDTO;
import com.multimerchant_haze.rest.v1.modules.products.model.ProductCategory;
import com.multimerchant_haze.rest.v1.modules.users.producer.dto.ProducerDTO;
import com.multimerchant_haze.rest.v1.modules.users.producer.model.Producer;
import com.multimerchant_haze.rest.v1.modules.products.dao.ProductsDAO;
import com.multimerchant_haze.rest.v1.modules.products.model.Product;
import com.multimerchant_haze.rest.v1.modules.products.model.ProductDetails;
import com.multimerchant_haze.rest.v1.modules.products.model.ProductStock;
import com.multimerchant_haze.rest.v1.modules.users.producer.dao.ProducerDAO;
import com.multimerchant_haze.rest.v1.modules.users.userAbstract.service.UserServiceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by zorzis on 6/29/2017.
 */

@Service
public class ProducerSpiritsServiceImplementation implements ProducerSpiritsService
{
    @Autowired
    ProductsDAO productsDAO;

    @Autowired
    ProducerDAO producerDAO;

    @Autowired
    ProductCategoryDAO productCategoryDAO;

    @Override
    public List<ProductDTO> getAllProductsByProducerEmail(String email) throws AppException
    {
        Producer producerByEmaail = this.producerDAO.getProducerByEmailOnlyFromProducersTable(email);

        // Check if producer indeed exists
        ProducerDTO producerDTO = UserServiceHelper.createProducerDTOIfProducerEntityExists(producerByEmaail,"Email", email);

        List<Product> producerSpirits = this.productsDAO.getAllProductsBasedOnProducer(email);

        ProductDTO sampleProductDTO = new ProductDTO();

        List<ProductDTO> productDTOSet = sampleProductDTO.mapProductsDTOsFromProductsEntities(producerSpirits);

        return productDTOSet;

    }

    @Override
    @Transactional("transactionManager")
    public String addNewProductByProducerEmail(ProducerDTO producerDTO, ProductDTO productDTO) throws AppException
    {
        //verify existence of producer in the db (email must be unique)
        Producer producerByEmail = producerDAO.getProducerByEmailOnlyFromProducersTable(producerDTO.getEmail());
        // Check if user already exists else throw AppException and stop process
        ProducerDTO producerWhoAddsTheProduct = UserServiceHelper.createProducerDTOIfProducerEntityExists(producerByEmail,"Email", producerDTO.getEmail());


        // make sure all required data for spirit addition is present in the request
        this.validateNotNullInputForProducerSpiritForStringValues(productDTO.getProductName(), "productName");
        this.validateNotNullInputForProducerSpiritForStringValues(productDTO.getProductCategory().getCategoryID(), "categoryID");
        this.validateNotNullInputForProducerSpiritForFloatValues(productDTO.getQuantity(), "quantity");
        this.validateNotNullInputForProducerSpiritForStringValues(productDTO.getProductDetails().getProductDescription(), "productDescription");
        this.validateNotNullInputForProducerSpiritForFloatValues(productDTO.getProductDetails().getPrice(), "price");
        this.validateNotNullInputForProducerSpiritForStringValues(productDTO.getProductDetails().getDateDistilled().toString(), "dateDistilled");
        this.validateNotNullInputForProducerSpiritForBooleanValues(productDTO.getProductDetails().isHasAniseed(), "hasAniseed");
        this.validateNotNullInputForProducerSpiritForFloatValues(productDTO.getProductDetails().getAlcoholVolume(), "alcoholVolume");
        //this.validateNotNullInputForProducerSpiritForStringValues(productDTO.getProductDetails().getGovernmentDistillApprovalID(), "governmentDistillApprovalID");


        Product productToBeAdded = new Product(productDTO);
        productToBeAdded.setCreatedAt(new Date());
        productToBeAdded.setProductID(this.generateProductUniqueID());
        productToBeAdded.getProductDetails().setProduct(productToBeAdded);

        ProductCategory productCategoryToBeCheckedIfExists = this.verifyProductCategoryExistsByCategoryID(productDTO.getProductCategory().getCategoryID());
        productToBeAdded.setProductCategory(productCategoryToBeCheckedIfExists);

        ProductStock productStockToBeAdded = new ProductStock();
        productStockToBeAdded.setProducer(producerByEmail);
        productStockToBeAdded.setProduct(productToBeAdded);
        productStockToBeAdded.setQuantity(productDTO.getQuantity());
        productStockToBeAdded.setCreatedAt(new Date());

        productToBeAdded.setProductStockSet(productStockToBeAdded);
        return productsDAO.addNewProduct(productToBeAdded);
    }


    @Override
    @Transactional("transactionManager")
    public String updateProductByProducerEmailProductID(ProducerDTO producerDTO, ProductDTO productDTO) throws AppException
    {
        //verify existence of producer in the db (email must be unique)
        Producer producerByEmail = producerDAO.getProducerByEmailOnlyFromProducersTable(producerDTO.getEmail());
        // Check if user already exists else throw AppException and stop process
        UserServiceHelper.createProducerDTOIfProducerEntityExists(producerByEmail,"Email", producerDTO.getEmail());


        // make sure all required data for spirit addition is present in the request
        this.validateNotNullInputForProducerSpiritForStringValues(productDTO.getProductName(), "productName");
        this.validateNotNullInputForProducerSpiritForStringValues(productDTO.getProductCategory().getCategoryID(), "categoryID");
        this.validateNotNullInputForProducerSpiritForFloatValues(productDTO.getQuantity(), "quantity");
        this.validateNotNullInputForProducerSpiritForStringValues(productDTO.getProductDetails().getProductDescription(), "productDescription");
        this.validateNotNullInputForProducerSpiritForFloatValues(productDTO.getProductDetails().getPrice(), "price");
        this.validateNotNullInputForProducerSpiritForStringValues(productDTO.getProductDetails().getDateDistilled().toString(), "dateDistilled");
        this.validateNotNullInputForProducerSpiritForBooleanValues(productDTO.getProductDetails().isHasAniseed(), "hasAniseed");
        this.validateNotNullInputForProducerSpiritForFloatValues(productDTO.getProductDetails().getAlcoholVolume(), "alcoholVolume");
        //this.validateNotNullInputForProducerSpiritForStringValues(productDTO.getProductDetails().getGovernmentDistillApprovalID(), "governmentDistillApprovalID");

        Product productToBeCheckedThatExists = productsDAO.getSingleProductBasedOnProducerAndProductID(producerDTO.getEmail(), productDTO.getProductID());

        // verify product belongs to Producer and Exists
        if(productToBeCheckedThatExists == null)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("Product");
            sb.append(" ");
            sb.append("[");
            sb.append(productDTO.getProductID());
            sb.append("]");
            sb.append(" ");
            sb.append("not found at Stock of Producer with email:");
            sb.append("[");
            sb.append(producerByEmail.getEmail());
            sb.append("]");
            sb.append(".");
            String errorMessage = sb.toString();

            AppException appException = new AppException(errorMessage);
            appException.setHttpStatus(HttpStatus.NOT_FOUND);
            appException.setAppErrorCode(HttpStatus.NOT_FOUND.value());
            appException.setDevelopersMessageExtraInfoAsSingleReason("Product not found to specific Producer Stock.");
            throw appException;
        }



        Product productToBeUpdated = new Product();
        productToBeUpdated.setProductID(productToBeCheckedThatExists.getProductID());
        productToBeUpdated.setProductName(productDTO.getProductName());
        productToBeUpdated.setCreatedAt(productToBeCheckedThatExists.getCreatedAt());
        productToBeUpdated.setUpdatedAt(new Date());

        ProductDetails productDetailsToBeUpdated = new ProductDetails();
        productDetailsToBeUpdated.setProductDetailsID(productToBeCheckedThatExists.getProductDetails().getProductDetailsID());
        productDetailsToBeUpdated.setPrice(productDTO.getProductDetails().getPrice());
        productDetailsToBeUpdated.setProductDescription(productDTO.getProductDetails().getProductDescription());
        productDetailsToBeUpdated.setHasAniseed(productDTO.getProductDetails().isHasAniseed());
        productDetailsToBeUpdated.setAlcoholVolume(productDTO.getProductDetails().getAlcoholVolume());
        productDetailsToBeUpdated.setGovernmentDistillApprovalID(productDTO.getProductDetails().getGovernmentDistillApprovalID());
        productDetailsToBeUpdated.setDateDistilled(productDTO.getProductDetails().getDateDistilled());

        productDetailsToBeUpdated.setProduct(productToBeUpdated);

        ProductCategory productCategoryToBeCheckedIfExists = this.verifyProductCategoryExistsByCategoryID(productDTO.getProductCategory().getCategoryID());
        productToBeUpdated.setProductCategory(productCategoryToBeCheckedIfExists);

        ProductStock productStockToBeUpdated = new ProductStock();
        productStockToBeUpdated.setProductStockID(productToBeCheckedThatExists.getProductStockSet().getProductStockID());
        productStockToBeUpdated.setProducer(producerByEmail);
        productStockToBeUpdated.setProduct(productToBeUpdated);
        productStockToBeUpdated.setQuantity(productDTO.getQuantity());
        productStockToBeUpdated.setCreatedAt(productToBeCheckedThatExists.getProductStockSet().getCreatedAt());
        productStockToBeUpdated.setUpdatedAt(new Date());

        productToBeUpdated.setProductStockSet(productStockToBeUpdated);
        productToBeUpdated.setProductDetails(productDetailsToBeUpdated);


        return productsDAO.updateProduct(productToBeUpdated);
    }

    @Override
    @Transactional("transactionManager")
    public String deleteProductByProducerEmailProductID(ProducerDTO producerDTO, ProductDTO productDTO) throws AppException
    {
        //verify existence of producer in the db (email must be unique)
        Producer producerByEmail = producerDAO.getProducerByEmailOnlyFromProducersTable(producerDTO.getEmail());
        // Check if user already exists else throw AppException and stop process
        UserServiceHelper.createProducerDTOIfProducerEntityExists(producerByEmail,"Email", producerDTO.getEmail());


        Product productToBeCheckedThatExists = productsDAO.getSingleProductBasedOnProducerAndProductID(producerDTO.getEmail(), productDTO.getProductID());

        // verify product belongs to Producer and Exists
        if(productToBeCheckedThatExists == null)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("Product");
            sb.append(" ");
            sb.append("[");
            sb.append(productDTO.getProductID());
            sb.append("]");
            sb.append(" ");
            sb.append("not found at Stock of Producer with email:");
            sb.append("[");
            sb.append(producerByEmail.getEmail());
            sb.append("]");
            sb.append(".");
            String errorMessage = sb.toString();

            AppException appException = new AppException(errorMessage);
            appException.setHttpStatus(HttpStatus.NOT_FOUND);
            appException.setAppErrorCode(HttpStatus.NOT_FOUND.value());
            appException.setDevelopersMessageExtraInfoAsSingleReason("Product not found to specific Producer Stock.");
            throw appException;
        }



        System.out.println("Product For Deletion inside Service is:");
        System.out.println("------------------------------------------------");
        System.out.println(productToBeCheckedThatExists.toString());

       /* ProductDetails productDetailsToBeDeleted = new ProductDetails();
        productDetailsToBeDeleted.setProductDetailsID(productToBeCheckedThatExists.getProductDetails().getProductDetailsID());
        productDetailsToBeDeleted.setProduct(productToBeCheckedThatExists);
        productDetailsToBeDeleted.setProductDescription(productToBeCheckedThatExists.getProductDetails().getProductDescription());
        productDetailsToBeDeleted.setPrice(productToBeCheckedThatExists.getProductDetails().getPrice());

        productToBeCheckedThatExists.setProductDetails(productDetailsToBeDeleted);

        ProductStock productStockToBeDeleted = new ProductStock();
        productStockToBeDeleted.setProductStockID(productToBeCheckedThatExists.getProductStockSet().getProductStockID());
        productStockToBeDeleted.setProducer(producerByEmail);
        productStockToBeDeleted.setProduct(productToBeCheckedThatExists);

        productToBeCheckedThatExists.setProductStockSet(productStockToBeDeleted);*/

        return productsDAO.deleteProduct(productToBeCheckedThatExists);
    }


    public ProductDTO getSingleProducerSpiritBasedOnProducerEmailAndProductID(ProducerDTO producerDTO, ProductDTO productDTO) throws AppException
    {
        //verify existence of producer in the db (email must be unique)
        Producer producerByEmail = producerDAO.getProducerByEmailOnlyFromProducersTable(producerDTO.getEmail());
        // Check if user already exists else throw AppException and stop process
        UserServiceHelper.createProducerDTOIfProducerEntityExists(producerByEmail,"Email", producerDTO.getEmail());

        Product product = productsDAO.getSingleProductBasedOnProducerAndProductID(producerDTO.getEmail(), productDTO.getProductID());

        if(product == null)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("Product");
            sb.append(" ");
            sb.append("[");
            sb.append(productDTO.getProductID());
            sb.append("]");
            sb.append(" ");
            sb.append("not found at Stock of Producer with email:");
            sb.append("[");
            sb.append(producerByEmail.getEmail());
            sb.append("]");
            sb.append(".");
            String errorMessage = sb.toString();

            AppException appException = new AppException(errorMessage);
            appException.setHttpStatus(HttpStatus.NOT_FOUND);
            appException.setAppErrorCode(HttpStatus.NOT_FOUND.value());
            appException.setDevelopersMessageExtraInfoAsSingleReason("Product not found to specific Producer Stock.");
            throw appException;
        }
        return new ProductDTO(product);
    }

    private ProductCategory verifyProductCategoryExistsByCategoryID(String productCategoryID)
    {
        ProductCategory productCategory = this.productCategoryDAO.getCategoryByCategoryID(productCategoryID);

        if(productCategory == null)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("Process Failed!!!");
            sb.append("Failed to assign Product Category with categoryID");
            sb.append(":");
            sb.append(" ");
            sb.append("[");
            sb.append(productCategoryID);
            sb.append("]");
            sb.append(" ");
            sb.append("to Product because Category does not exists to our system.");
            String errorMessage = sb.toString();

            AppException appException = new AppException(errorMessage);
            appException.setHttpStatus(HttpStatus.CONFLICT);
            appException.setAppErrorCode(HttpStatus.CONFLICT.value());
            appException.setDevelopersMessageExtraInfoAsSingleReason("Category does not exists to our system.System cannot complete category assignment to product");
            throw appException;
        }

        return productCategory;
    }



    private void validateNotNullInputForProducerSpiritForBooleanValues(Boolean booleanValueToBeChecked, String keyToBeChecked) throws AppException
    {
        if (booleanValueToBeChecked == null)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("Boolean");
            sb.append(" ");
            sb.append(keyToBeChecked);
            sb.append(" ");
            sb.append("is missing!!!");
            sb.append("Please provide the Spirit");
            sb.append(" ");
            sb.append(keyToBeChecked);
            sb.append(" ");
            sb.append("data in order to complete Spirit Addition.");
            String errorMessage = sb.toString();

            AppException appException = new AppException(errorMessage);
            appException.setHttpStatus(HttpStatus.BAD_REQUEST);
            appException.setAppErrorCode(HttpStatus.BAD_REQUEST.value());
            appException.setDevelopersMessageExtraInfoAsSingleReason("Required Spirit" + keyToBeChecked + " has a NULL value or value is Empty. Please remake the request providing the required data.");
            throw appException;
        }
    }

    private void validateNotNullInputForProducerSpiritForStringValues(String valueToBeChecked, String keyToBeChecked) throws AppException
    {
        if (valueToBeChecked == null || valueToBeChecked.isEmpty())
        {
            StringBuilder sb = new StringBuilder();
            sb.append("String");
            sb.append(" ");
            sb.append(keyToBeChecked);
            sb.append(" ");
            sb.append("is missing!!!");
            sb.append("Please provide the Spirit");
            sb.append(" ");
            sb.append(keyToBeChecked);
            sb.append(" ");
            sb.append("data in order to complete Spirit Addition.");
            String errorMessage = sb.toString();

            AppException appException = new AppException(errorMessage);
            appException.setHttpStatus(HttpStatus.BAD_REQUEST);
            appException.setAppErrorCode(HttpStatus.BAD_REQUEST.value());
            appException.setDevelopersMessageExtraInfoAsSingleReason("Required Spirit" + keyToBeChecked + " has a NULL value or value is Empty. Please remake the request providing the required data.");
            throw appException;
        }
    }

    // Check if user Register credentials are not NULL
    private void validateNotNullInputForProducerSpiritForFloatValues(Float valueToBeChecked, String keyToBeChecked) throws AppException
    {
        if (valueToBeChecked == null || valueToBeChecked.isNaN())
        {
            StringBuilder sb = new StringBuilder();
            sb.append("Float");
            sb.append(" ");
            sb.append(keyToBeChecked);
            sb.append(" ");
            sb.append("is missing!!!");
            sb.append("Please provide the Spirit");
            sb.append(" ");
            sb.append(keyToBeChecked);
            sb.append(" ");
            sb.append("data in order to complete Spirit Addition.");
            String errorMessage = sb.toString();

            AppException appException = new AppException(errorMessage);
            appException.setHttpStatus(HttpStatus.BAD_REQUEST);
            appException.setAppErrorCode(HttpStatus.BAD_REQUEST.value());
            appException.setDevelopersMessageExtraInfoAsSingleReason("Required Spirit" + keyToBeChecked + " has a NULL value or value is Empty. Please remake the request providing the required data.");
            throw appException;
        }
    }

    // Creates a unique UserID with a prefix
    public final String generateProductUniqueID()
    {
        String productUniqueID = null;

        String generatedID = UUID.randomUUID().toString().replaceAll("-", "");

        StringBuilder sb = new StringBuilder();
        sb.append("SPIRIT-");
        sb.append(generatedID);

        productUniqueID = sb.toString();

        return productUniqueID;
    }

}
