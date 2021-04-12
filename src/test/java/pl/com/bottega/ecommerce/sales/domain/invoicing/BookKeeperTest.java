package pl.com.bottega.ecommerce.sales.domain.invoicing;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.ClientData;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductData;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductDataBuilder;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductType;
import pl.com.bottega.ecommerce.sharedkernel.Money;

import java.util.Date;

class BookKeeperTest {

    private static final String SAMPLE_CLIENT_NAME="Nowak";
    @Mock
    private InvoiceFactory invoiceFactory;
    private TaxPolicy taxPolicy;
    private BookKeeper bookKeeper;
    private InvoiceRequest invoiceRequest;
    private Invoice invoice;

    private RequestItem requestItem;
    private Money money;
    private Tax tax;
    private InvoiceLine invoiceLine;

    private Id id;
    private ClientData clientData;
    private ProductData productData;
    private ProductDataBuilder productDataBuilder;
    int quantity=1;




    @BeforeEach
    void setUp() throws Exception {
        bookKeeper=new BookKeeper(invoiceFactory);

        id=new Id("1");
        clientData=new ClientData(id,SAMPLE_CLIENT_NAME);
        invoice=new Invoice(id,clientData);
        money=new Money(0);
        productDataBuilder=new ProductDataBuilder();
        productData=productDataBuilder.init(id,money,"test1",ProductType.STANDARD,new Date());

        requestItem=new RequestItem(productData,quantity,money);
        tax=new Tax(money,"tax1");
        invoiceLine=new InvoiceLine(productData,quantity,money,tax);

    }

    @Test
    void InvoiceOneWithOne() {
    }

}
