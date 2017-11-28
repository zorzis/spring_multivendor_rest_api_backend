package com.multimerchant_haze.rest.v1.modules.users.client.dto;

import com.multimerchant_haze.rest.v1.modules.users.producer.dto.ProducerAddressDTO;
import com.multimerchant_haze.rest.v1.modules.products.dto.ProductCategoryDTO;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by zorzis on 10/28/2017.
 */
public class SearchFilterCriteriaDTO
{
    private Set<ProductCategoryDTO> productsCategories = new HashSet<>(0);

    private Boolean hasAniseed;

    private ProducerAddressDTO producerAddress;



    public SearchFilterCriteriaDTO() {

    }


/*    // we convert them to SearchCriteria
    public List<SearchCriteria> getSearchCriteriaList()
    {
        List<SearchCriteria> searchCriteriaArrayList = new ArrayList<>(0);


        for(ProductCategoryDTO productCategoryDTO : this.productsCategories)
        {
            SearchCriteria searchCriteriaForCategory = new SearchCriteria();
            searchCriteriaForCategory.setKey("categoryID");
            searchCriteriaForCategory.setOperation(":");
            searchCriteriaForCategory.setValue(productCategoryDTO.getCategoryID());

            searchCriteriaArrayList.add(searchCriteriaForCategory);
        }

        SearchCriteria searchCriteriaForHasAniseed = new SearchCriteria();
        searchCriteriaForHasAniseed.setKey("hasAniseed");
        searchCriteriaForHasAniseed.setOperation(":");
        searchCriteriaForHasAniseed.setValue(this.hasAniseed);
        searchCriteriaArrayList.add(searchCriteriaForHasAniseed);


        return searchCriteriaArrayList;
    }*/

    public void addNewProductCategory(ProductCategoryDTO productCategoryDTO) {
        this.productsCategories.add(productCategoryDTO);
    }


    public Set<ProductCategoryDTO> getProductsCategories()
    {
        return productsCategories;
    }

    public void setProductsCategories(Set<ProductCategoryDTO> productsCategories)
    {
        this.productsCategories = productsCategories;
    }

    public Boolean isHasAniseed()
    {
        return hasAniseed;
    }

    public void setHasAniseed(Boolean hasAniseed)
    {
        this.hasAniseed = hasAniseed;
    }

    public ProducerAddressDTO getProducerAddress()
    {
        return producerAddress;
    }

    public void setProducerAddress(ProducerAddressDTO producerAddress)
    {
        this.producerAddress = producerAddress;
    }

    @Override
    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SearchFilterCriteria DTO follows");
        stringBuilder.append(" ");
        stringBuilder.append("Has aniseed is: " + this.hasAniseed);
        stringBuilder.append(" ");


        if(this.productsCategories != null)
        {
            for(ProductCategoryDTO productCategoryDTO: this.productsCategories)
            {
                stringBuilder.append(productCategoryDTO.toString());
                stringBuilder.append(" ");

            }

        }


        return stringBuilder.toString();
    }

}
