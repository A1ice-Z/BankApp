package core;

import org.junit.Before;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

public class ProfileTest {
    private Profile profile1;
    private Profile profile2;
    private Profile profile3;

    @Before
    @DisplayName("setting up the different profiles")
    public void setUp() {
        profile1 = new Profile("jayan tayan", "jayantayan@ntnu.no", "98765432", "passord111");
        profile2 = new Profile("klein ken", "kleinken@ntnu.no", "99997722", "JegElskerITP123");
        profile3 = new Profile("philipa muhammed", "philipamuhammed@ntnu.no", "92457233", "loneyku9");
    }

    @Test
    @DisplayName("Testing the constructor")
    public void testConstructor() {
        assertNotNull(profile1);
        assertNotNull(profile2);
        assertNotNull(profile3);
    }

    @Test
    @DisplayName("  Testing if the name is set correctl")
    public void testSetName() {
        assertEquals("jayan tayan", profile1.getName());
        assertEquals("klein ken", profile2.getName());
        assertEquals("philipa muhammed", profile3.getName());
        assertFalse(profile1.getName().equals(profile2.getName()));
        assertTrue(profile2.getName().equals("klein ken"));

        assertTrue(profile1.getName().split(" ").length >= 2);

        assertThrows(IllegalArgumentException.class,
                () -> new Profile("a2 loke", "a2loke@example.no", "12345678", "generiskPassord1"));
        assertThrows(IllegalArgumentException.class,
                () -> new Profile("lisa", "lisa@example.no", "12345678", "generiskPassord1"));

    }

    @Test
    @DisplayName("Testing if the email is set correct")
    public void testSetEmail() {
        // assertEquals("jayantayan@ntnu.no", profile1.getEmail());
        // assertEquals("philipamuhammed@ntu.no", profile3.getEmail());
        assertFalse(profile2.getEmail().equals(profile3.getEmail()));
        assertTrue(profile1.getEmail().equals("jayantayan@ntnu.no"));

        assertTrue(profile1.getEmail().split("@").length == 2);
        assertTrue(profile3.getEmail().split("\\.").length == 2);

        assertThrows(IllegalArgumentException.class,
                () -> new Profile("millie mons", "milliemonsntnu.no", "12345678", "generiskPassord1"));
        assertThrows(IllegalArgumentException.class,
                () -> new Profile("charlie geir", "charliegeir@ntnuno", "12345678", "generiskPassord1"));
    }

    @Test
    @DisplayName("Testing if the telefone number is set correct")
    public void testSetTlf() {
        assertEquals("98765432", profile1.getTlf());
        assertEquals("99997722", profile2.getTlf());
        assertEquals("92457233", profile3.getTlf());

        profile3.changeTlf("98658722");
        assertEquals("98658722", profile3.getTlf());

        assertTrue(profile1.getTlf().length() == 8);
        assertTrue(profile2.getTlf().length() == 8);

        assertThrows(IllegalArgumentException.class,
                () -> new Profile("millie mons", "milliemons@ntnu.no", "123456a8", "generiskPassord1"));
        assertThrows(IllegalArgumentException.class,
                () -> new Profile("millie mons", "milliemons@ntnu.no", "12348", "generiskPassord1"));
        assertThrows(IllegalArgumentException.class, () -> profile1.changeTlf("2323234f"));
    }

    @Test
    @DisplayName("Tesning if the password is set correct")
    public void testSetPassword() {
        assertEquals("passord111", profile1.getPassword());
        assertEquals("JegElskerITP123", profile2.getPassword());
        assertEquals("loneyku9", profile3.getPassword());

        profile2.changePassword("orangutang4");
        assertEquals("orangutang4", profile2.getPassword());

        assertTrue(profile2.getEmail().length() > 8);
        assertTrue(profile3.getEmail().length() > 8);

        assertThrows(IllegalArgumentException.class,
                () -> new Profile("millie mons", "milliemons@ntnu.no", "12345678", "generiskPassord"));
        assertThrows(IllegalArgumentException.class,
                () -> new Profile("millie mons", "milliemons@ntnu.no", "12345678", "generisk"));
        assertThrows(IllegalArgumentException.class, () -> profile1.changePassword("baba123"));

    }

    @Test
    @DisplayName("Testing if an account gets created correctly")
    public void testCreateAccount() {
        assertEquals(0, profile1.getAccounts().size());
        Account acc = new Account("Savings", profile1);
        profile1.addAccount(acc);
        assertEquals(1, profile1.getAccounts().size());
        assertEquals("Savings", acc.getName());
    }

    @Test
    @DisplayName("Test adding of bills to profile")
    public void testAddBill() {
        Account acc1 = new Account("Spending", profile1);
        profile1.addAccount(acc1);
        profile1.getAccounts().get(0).add(1000);
        Account acc2 = new Account("NTNU", profile2);
        Bill bill = new Bill(100, "billName", "NTNU", acc2, acc1,
                profile1);
        profile1.addBill(bill);
        assertTrue(profile1.getBills().contains(bill));
        assertThrows(IllegalArgumentException.class, () -> profile1.addBill(bill));
    }

    @Test
    @DisplayName("Test removal of bills from profile")
    public void testRemoveBill() {
        Account acc1 = new Account("Spending", profile1);
        profile1.addAccount(acc1);
        profile1.getAccounts().get(0).add(1000);
        Account acc2 = new Account("NTNU", profile2);
        profile2.addAccount(acc2);
        Bill bill = new Bill(100, "billName", "NTNU", acc2, acc1,
                profile1);
        profile1.addBill(bill);
        assertThrows(IllegalArgumentException.class, () -> profile1.removeBill(bill));
        bill.pay();
        assertFalse(profile1.getBills().contains(bill));
        assertThrows(IllegalArgumentException.class, () -> profile1.removeBill(bill));
    }

    @Test
    @DisplayName("Tests getting of total balance")
    public void testTotalBalance() {
        assertEquals(0, profile1.getTotalBalance());
        Account acc1 = new Account("Spending", profile1);
        Account acc2 = new Account("Savings", profile1);
        acc1.add(1000);
        acc2.add(1);
        profile1.addAccount(acc1);
        profile1.addAccount(acc2);
        assertEquals(1001, profile1.getTotalBalance());
    }
}
