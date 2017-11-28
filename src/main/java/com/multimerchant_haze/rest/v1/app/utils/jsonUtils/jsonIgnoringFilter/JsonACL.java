package com.multimerchant_haze.rest.v1.app.utils.jsonUtils.jsonIgnoringFilter;

/**
 * Created by zorzis on 6/13/2017.
 */
public class JsonACL
{
    public interface PublicView {}
    public interface ClientsView extends PublicView{}
    public interface ProducersView extends ClientsView {}
    public interface AdminsView extends ProducersView {}

    public interface SearchByProductsPublicView extends PublicView {}
    public interface SearchByProducersPublicView extends PublicView {}

    public interface OrderListBelongingToClient extends ClientsView {}
    public interface OrderListBelongingToProducer extends ProducersView {}
}
