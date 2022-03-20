import org.apache.commons.configuration.ConfigurationException;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

public class TestRunner {
    @org.testng.annotations.Test(priority = 0)
    public void doLogin() throws ConfigurationException, IOException {
        Customer customer=new Customer();
        customer.callingCustomerLoginApi();
    }
    @org.testng.annotations.Test(priority = 1)
    public void getCustomerList() throws ConfigurationException, IOException {
        Customer customer=new Customer();
        customer.callingCustomerListApi();
    }
    @org.testng.annotations.Test(priority = 2)
    public void searchCustomer() throws IOException {
        Customer customer=new Customer();
        customer.callingCustomerSearchApi();
    }
    @org.testng.annotations.Test(priority = 3)
    public void generateCustomerInfo() throws ConfigurationException, IOException {
        Customer customer=new Customer();
        customer.addCustomer();
    }
    @org.testng.annotations.Test(priority = 4)
    public void createCustomer() throws IOException, ConfigurationException {
        Customer customer=new Customer();
        customer.createCustomer();
    }
    @Test(priority = 5)
    public void updateCustomer() throws IOException {
        Customer customer=new Customer();
        customer.customerUpdate();
    }

}
