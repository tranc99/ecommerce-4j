/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import cart.ShoppingCart;
import cart.ShoppingCartItem;
import entity.Customer;
import entity.CustomerOrder;
import entity.OrderedProduct;
import entity.OrderedProductPK;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author user
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class OrderManager {
    
    @PersistenceContext(unitName = "AffableBeanPU")
    private EntityManager em;
    
    @Resource
    private SessionContext context;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public int placeOrder(String name, String email, String phone, String address, String cityRegion, String ccNumber, ShoppingCart cart) {
        
        try {
            
     
            Customer customer =  addCustomer(name, email, phone, address, cityRegion, ccNumber);
            CustomerOrder order = addOrder(customer, cart);
            addOrderedItems(order, cart);
            return order.getId();
        } catch (Exception e) {
            context.setRollbackOnly();
            return 0;
        }
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    private void addOrderedItems(CustomerOrder order, ShoppingCart cart) {
  
        em.flush();
        
        List<ShoppingCartItem> items = cart.getItems();

        //iterate through shopping cart and create OrderedProducts
        for (ShoppingCartItem scItem : items) {
            
            int productId = scItem.getProduct().getId();
            
            //set up primary key object
            
            OrderedProductPK orderedProductPK = new OrderedProductPK();
            orderedProductPK.setCustomerOrderId(order.getId());
            orderedProductPK.setProductId(productId);
            
            //create ordered item using PK object
            OrderedProduct orderedItem = new OrderedProduct(orderedProductPK);
            
            
            //set quantity
            orderedItem.setQuantity(scItem.getQuantity());
            
            em.persist(orderedItem);
        }
    
    }

    private CustomerOrder addOrder(Customer customer, ShoppingCart cart) {
        
        em.flush();
        
        //set up customer order
        CustomerOrder order = new CustomerOrder();
        order.setCustomerId(customer);
        order.setAmount(BigDecimal.valueOf(cart.getTotal()));

        //create confirmation number
        Random random = new Random();
        int i = random.nextInt(999999);
        order.setConfirmationNumber(i);

        em.persist(order);
        return order;
    }

    private Customer addCustomer(String name, String email, String phone, String address, String cityRegion, String ccNumber) {
       
        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(email);
        customer.setPhone(phone);
        customer.setAddress(address);
        customer.setCityRegion(cityRegion);
        customer.setCcNumber(ccNumber);
        
        em.persist(customer);
        return customer;
    }

}
