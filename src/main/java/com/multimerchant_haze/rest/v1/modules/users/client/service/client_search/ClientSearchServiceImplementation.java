package com.multimerchant_haze.rest.v1.modules.users.client.service.client_search;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.products.dto.ProductCategoryDTO;
import com.multimerchant_haze.rest.v1.modules.products.service.ProductsCategoriesService;
import com.multimerchant_haze.rest.v1.modules.users.client.dao.ClientSearchDAO;
import com.multimerchant_haze.rest.v1.modules.users.client.dto.SearchFilterCriteriaDTO;
import com.multimerchant_haze.rest.v1.modules.users.producer.dto.ProducerDTO;
import com.multimerchant_haze.rest.v1.modules.users.producer.model.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by zorzis on 10/28/2017.
 */
@Service
public class ClientSearchServiceImplementation implements ClientSearchService
{
    @Autowired
    private ProductsCategoriesService productsCategoriesService;

    @Autowired
    private ClientSearchDAO clientSearchDAO;

    @Override
    public List<ProducerDTO> searchProducers(SearchFilterCriteriaDTO searchFilterCriteria) throws AppException
    {
        // at first we check the received categories
        this.checkCategoriesExistOnDatabase(searchFilterCriteria.getProductsCategories());

        List<Producer> producers = this.clientSearchDAO.searchProducersBasedOnFilterCriteria(searchFilterCriteria);

        List<ProducerDTO> producerDTOList = new ArrayList<>(0);

        for(Producer producer: producers)
        {
            ProducerDTO producerDTO = new ProducerDTO(producer);

            // asign products entities to producerDTO
            producerDTO.mapProductsDTOsFromProductsEntities(producer.getProducerProductsEntities());

            // assign paymentMethods entities to producerDTO
            producerDTO.mapProducerPaymentMethodsDTOsFromProducerPaymentMethodsEntities(producer.getProducerHasPaymentMethodSet());

            producerDTOList.add(producerDTO);
        }

        return producerDTOList;
    }

    private void checkCategoriesExistOnDatabase(Set<ProductCategoryDTO> productCategoryDTOSet) throws AppException
    {
        //at first we check if Set of ProductCategories is not null
        if(!(productCategoryDTOSet == null) && !productCategoryDTOSet.isEmpty())
        {
            // then we just search for each category if exists on databases
            for(ProductCategoryDTO productCategoryDTO: productCategoryDTOSet)
            {
                this.productsCategoriesService.getProductCategoryByProductCategoryID(productCategoryDTO);
            }
        }

    }


}
