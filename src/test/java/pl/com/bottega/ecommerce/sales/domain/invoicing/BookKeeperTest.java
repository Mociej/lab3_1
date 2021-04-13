package pl.com.bottega.ecommerce.sales.domain.invoicing;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
    private static final Id ID_TWO = new Id("2");
    private static final Id ID_THREE = new Id("3");
    private static final ClientData CLIENT_DATA = new ClientData(ID_ONE, SAMPLE_CLIENT_NAME);
    private static final Tax TAX_ZERO = new Tax(new Money(0), SAMPLE_TAX_NAME);
    private static final Money MONEY_ZERO = new Money(0);
    private static final Money MONEY_SOME = new Money(110);
    private static final int QUANTITY_ONE = 1;
    private static final int RESULT_COUNT_ZERO = 0;
    private static final int RESULT_COUNT_ONE = 1;
    private static final int RESULT_COUNT_TWO = 2;
    private static final int RESULT_COUNT_THREE = 3;
    @Mock
    private InvoiceFactory invoiceFactory;
    @Mock
    private TaxPolicy taxPolicy;

    private BookKeeper bookKeeper;


    @BeforeEach
    void setUp() throws Exception {
        bookKeeper = new BookKeeper(invoiceFactory);
    }

    @Test
    void InvokeInvoiceWithOneItemReturnInvoiceWithOneItem() {
        //Given
        InvoiceRequest invoiceRequest = new InvoiceRequest(CLIENT_DATA);

        ProductData productData = new ProductDataBuilder()
                .withId(ID_ONE).withMoney(MONEY_ZERO)
                .withName(SAMPLE_PRODUCTDATA_NAME)
                .withType(ProductType.STANDARD)
                .withDate(new Date())
                .build();

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
    void InvokeInvoiceWithTwoItemsCallCalculateTaxTwoTimes() {
        //Given
        InvoiceRequest invoiceRequest = new InvoiceRequest(CLIENT_DATA);

        //ProductData productData = productDataBuilder.init(ID_ONE, MONEY_ZERO, SAMPLE_PRODUCTDATA_NAME, ProductType.STANDARD, new Date()).build();
        ProductData productData = new ProductDataBuilder()
                .withId(ID_ONE).withMoney(MONEY_ZERO)
                .withName(SAMPLE_PRODUCTDATA_NAME)
                .withType(ProductType.STANDARD)
                .withDate(new Date())
                .build();

        RequestItem requestItem = new RequestItem(productData, QUANTITY_ONE, MONEY_ZERO);
        invoiceRequest.add(requestItem);
        invoiceRequest.add(requestItem);

        when(taxPolicy.calculateTax(any(ProductType.class), any(Money.class))).thenReturn(TAX_ZERO);
        when(invoiceFactory.create(CLIENT_DATA)).thenReturn(new Invoice(ID_ONE, CLIENT_DATA));

        //When
        Invoice invoice = bookKeeper.issuance(invoiceRequest, taxPolicy);

        //Then
        assertEquals(RESULT_COUNT_TWO, invoice.getItems().size());
        verify(taxPolicy, times(2)).calculateTax(any(ProductType.class), any(Money.class));
    }

    @Test
    void InvokeInvoiceWhenZeroItems() {
        //Given
        InvoiceRequest invoiceRequest = new InvoiceRequest(CLIENT_DATA);
        when(invoiceFactory.create(CLIENT_DATA)).thenReturn(new Invoice(ID_ONE, CLIENT_DATA));

        //When
        Invoice invoice = bookKeeper.issuance(invoiceRequest, taxPolicy);

        //Then
        assertNotEquals(null, invoice);
        assertEquals(RESULT_COUNT_ZERO, invoice.getItems().size());
    }

    @Test
    void InvokeInvoiceWithTwoItemReturnInvoiceWithTwoItems() {
        //Given
        InvoiceRequest invoiceRequest = new InvoiceRequest(CLIENT_DATA);

        ProductData productData = new ProductDataBuilder()
                .withId(ID_ONE)
                .withMoney(MONEY_ZERO)
                .withName(SAMPLE_PRODUCTDATA_NAME)
                .withType(ProductType.STANDARD)
                .withDate(new Date())
                .build();

        RequestItem requestItem = new RequestItem(productData, QUANTITY_ONE, MONEY_ZERO);
        invoiceRequest.add(requestItem);

        ProductData productData2 = new ProductDataBuilder()
                .withId(ID_TWO)
                .withMoney(MONEY_ZERO)
                .withName(SAMPLE_PRODUCTDATA_NAME)
                .withType(ProductType.STANDARD)
                .withDate(new Date())
                .build();

        RequestItem requestItem2 = new RequestItem(productData2, 3, MONEY_SOME);
        invoiceRequest.add(requestItem2);

        when(taxPolicy.calculateTax(any(ProductType.class), any(Money.class))).thenReturn(TAX_ZERO);
        when(invoiceFactory.create(CLIENT_DATA)).thenReturn(new Invoice(ID_ONE, CLIENT_DATA));

        //When
        Invoice invoice = bookKeeper.issuance(invoiceRequest, taxPolicy);

        //Then
        assertNotEquals(null, invoice);
        assertEquals(RESULT_COUNT_TWO, invoice.getItems().size());
    }

    @Test
    void NoInvokeInvoiceWithZeroItemsNoCallCalculateTax() {
        //Given
        InvoiceRequest invoiceRequest = new InvoiceRequest(CLIENT_DATA);
        when(invoiceFactory.create(CLIENT_DATA)).thenReturn(new Invoice(ID_ONE, CLIENT_DATA));

        //When
        Invoice invoice = bookKeeper.issuance(invoiceRequest, taxPolicy);

        //Then
        assertEquals(RESULT_COUNT_ZERO, invoice.getItems().size());
        verifyNoInteractions(taxPolicy);
    }

    @Test
    void InvokeInvoiceWithThreeItemsCallCalculateTaxThreeTimes() {
        //Given
        InvoiceRequest invoiceRequest = new InvoiceRequest(CLIENT_DATA);

        ProductData productData = new ProductDataBuilder()
                .withId(ID_ONE)
                .withMoney(MONEY_ZERO)
                .withName(SAMPLE_PRODUCTDATA_NAME)
                .withType(ProductType.STANDARD)
                .withDate(new Date())
                .build();

        RequestItem requestItem = new RequestItem(productData, QUANTITY_ONE, MONEY_ZERO);
        invoiceRequest.add(requestItem);

        ProductData productData2 = new ProductDataBuilder()
                .withId(ID_TWO)
                .withMoney(MONEY_ZERO)
                .withName(SAMPLE_PRODUCTDATA_NAME)
                .withType(ProductType.STANDARD)
                .withDate(new Date())
                .build();

        RequestItem requestItem2 = new RequestItem(productData2, 3, MONEY_SOME);
        invoiceRequest.add(requestItem2);

        ProductData productData3 = new ProductDataBuilder()
                .withId(ID_THREE)
                .withMoney(MONEY_ZERO)
                .withName(SAMPLE_PRODUCTDATA_NAME)
                .withType(ProductType.STANDARD)
                .withDate(new Date())
                .build();
        RequestItem requestItem3 = new RequestItem(productData3, 2, MONEY_ZERO);
        invoiceRequest.add(requestItem3);


        when(taxPolicy.calculateTax(any(ProductType.class), any(Money.class))).thenReturn(TAX_ZERO);
        when(invoiceFactory.create(CLIENT_DATA)).thenReturn(new Invoice(ID_ONE, CLIENT_DATA));

        //When
        Invoice invoice = bookKeeper.issuance(invoiceRequest, taxPolicy);

        //Then
        assertEquals(RESULT_COUNT_THREE, invoice.getItems().size());
        verify(taxPolicy, times(3)).calculateTax(any(ProductType.class), any(Money.class));
    }
}
