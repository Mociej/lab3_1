package pl.com.bottega.ecommerce.sales.domain.productscatalog;

import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;
import pl.com.bottega.ecommerce.sharedkernel.Money;

import java.util.Date;

public class ProductDataBuilder {
    ProductData productData;

//    public ProductDataBuilder(Id id, Money money,String name, ProductType type, Date date ){
//        productData=new ProductData(id,money,name,type,date);
//    }

    public ProductData init(Id id, Money money,String name, ProductType type, Date date ){
        return  productData=new ProductData(id,money,name,type,date);
    }
}
