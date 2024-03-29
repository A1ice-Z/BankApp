package core;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

public class BillTest {
    private Profile profile1;
    private Profile profile2;
    private Account acc1;
    private Account acc2;

    @Before
    @DisplayName("setting up the different profiles")
    public void setUp() {
        profile1 = new Profile("jayan tayan", "jayantayan@ntnu.no", "98765432", "passord111");
        profile2 = new Profile("klein ken", "kleinken@ntnu.no", "99997722", "idioteple6");
        acc1 = new Account("Payer", profile1);
        acc2 = new Account("Seller", profile2);
        profile1.addAccount(acc1);
        profile2.addAccount(acc2);
    }

    @Test
    @DisplayName("Testing the constructor")
    public void testConstructor(){
        Bill bill = new Bill(100, "Leie", "Sit", profile2.getAccounts().get(0), profile1.getAccounts().get(0), profile1);
        assertNotNull(bill);
        assertThrows(IllegalArgumentException.class, () -> new Bill(-100, "Leie", "Sit", profile1.getAccounts().get(0), profile2.getAccounts().get(0), profile1));
        assertEquals(bill.getBillName(), "Leie");
        assertEquals(bill.getSellerName(), "Sit");
        assertEquals(bill.getAmount(), 100);
        assertEquals(bill.getProfile(), profile1);
        assertEquals(bill.getSellerAccount(), acc2);
        assertEquals(bill.getPayerAccount(), acc1);
        assertFalse(bill.isPaid());
    }

    @Test
    @DisplayName("Test paying")
    public void testPay(){
        Bill bill = new Bill(100, "Leie", "Sit", profile2.getAccounts().get(0), profile1.getAccounts().get(0), profile1);
        Account payer = profile1.getAccounts().get(0);
        Account seller = profile2.getAccounts().get(0);
        profile1.addBill(bill);
        payer.add(100);
        bill.pay();
        assertTrue(bill.isPaid());
        assertEquals(payer.getBalance(),0);
        assertEquals(seller.getBalance(),100);
    }
}
