package pl.com.bottega.ecommerce.sales.domain.productscatalog;

import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;
import pl.com.bottega.ecommerce.sharedkernel.Money;

import java.util.Date;

public class ProductDataBuilder {
    Id id;
    Money money;
    String name;
    ProductType type;
    Date date;

    public ProductData build(){
        return  new ProductData(id,money,name,type,date);
    }
    public ProductDataBuilder withId(Id id){
        this.id=id;
        return this;
    }
    public ProductDataBuilder withMoney(Money money){
        this.money=money;
        return this;
    }
    public ProductDataBuilder withName(String name){
        this.name=name;
        return this;
    }
    public ProductDataBuilder withType(ProductType type){
        this.type=type;
        return this;
    }
    public ProductDataBuilder withDate(Date date){
        this.date=date;
        return this;
    }

}
