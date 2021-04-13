package pl.com.bottega.ecommerce.sales.domain.invoicing;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.ClientData;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductData;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductDataBuilder;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductType;
import pl.com.bottega.ecommerce.sharedkernel.Money;

import java.util.Date;

@ExtendWith(MockitoExtension.class)
class BookKeeperTest {

    private static final String SAMPLE_CLIENT_NAME = "Nowak";
    private static final String SAMPLE_TAX_NAME = "tax1";
    private static final String SAMPLE_PRODUCTDATA_NAME = "test1";
    private static final Id ID_ONE = new Id("1");
    private static final ClientData CLIENT_DATA = new ClientData(ID_ONE, SAMPLE_CLIENT_NAME);
    private static final Tax TAX_ZERO = new Tax(new Money(0), SAMPLE_TAX_NAME);
    private static final Money MONEY_ZERO = new Money(0);
    private static final int QUANTITY_ONE = 1;
    private static final int RESULT_COUNT_ONE = 1;

    @Mock
    private InvoiceFactory invoiceFactory;
    @Mock
    private TaxPolicy taxPolicy;

    private BookKeeper bookKeeper;

    private ProductDataBuilder productDataBuilder;

    @BeforeEach
    void setUp() throws Exception {
        bookKeeper = new BookKeeper(invoiceFactory);
        productDataBuilder = new ProductDataBuilder();
    }

    @Test
    void InvokeInvoiceWithOneItemReturnInvoiceWithOneItem() {
        //Given
        InvoiceRequest invoiceRequest = new InvoiceRequest(CLIENT_DATA);

        ProductData productData = productDataBuilder.init(ID_ONE, MONEY_ZERO, SAMPLE_PRODUCTDATA_NAME, ProductType.STANDARD, new Date());

        RequestItem requestItem = new RequestItem(productData, QUANTITY_ONE, MONEY_ZERO);
        invoiceRequest.add(requestItem);

        when(taxPolicy.calculateTax(any(ProductType.class), any(Money.class))).thenReturn(TAX_ZERO);
        when(invoiceFactory.create(CLIENT_DATA)).thenReturn(new Invoice(ID_ONE, CLIENT_DATA));

        //When
        Invoice invoice = bookKeeper.issuance(invoiceRequest, taxPolicy);

        //Then
        assertNotEquals(null, invoice);
        assertEquals(RESULT_COUNT_ONE, invoice.getItems().size());
    }

    @Test
    void TestCase2() {
        //Given
        //When
        //Then
    }

    @Test
    void TestState1() {
        //Given
        //When
        //Then
    }

    @Test
    void TestState2() {
        //Given
        //When
        //Then
    }

    @Test
    void TestBehaviour1() {
        //Given
        //When
        //Then
    }

    @Test
    void TestBehaviour2() {
        //Given
        //When
        //Then
    }
}
