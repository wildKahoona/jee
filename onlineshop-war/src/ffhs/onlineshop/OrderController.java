package ffhs.onlineshop;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;
 
//@Named
//@SessionScoped
//public class OrderController implements Serializable{
//
//	private static final long serialVersionUID = 1L;
//	private Order selectedOrder;
//
//	private static ArrayList<Order> orderList = 
//		new ArrayList<Order>(Arrays.asList(
//		
//		new Order("A0001", "Intel CPU", 
//				new BigDecimal("700.00"), 1),
//		new Order("A0002", "Harddisk 10TB", 
//				new BigDecimal("500.00"), 2),
//		new Order("A0003", "Dell Laptop", 
//				new BigDecimal("11600.00"), 8),
//		new Order("A0004", "Samsung LCD", 
//				new BigDecimal("5200.00"), 3),
//		new Order("A0005", "A4Tech Mouse", 
//				new BigDecimal("100.00"), 10)
//	));
//	 
//    @PostConstruct
//    public void init() {
//    	System.out.println("INIT !!!!!!!");
//    	orderList = 
//    			new ArrayList<Order>(Arrays.asList(
//    			
//    			new Order("A0001", "Intel CPU", 
//    					new BigDecimal("700.00"), 1),
//    			new Order("A0002", "Harddisk 10TB", 
//    					new BigDecimal("500.00"), 2),
//    			new Order("A0003", "Dell Laptop", 
//    					new BigDecimal("11600.00"), 8),
//    			new Order("A0004", "Samsung LCD", 
//    					new BigDecimal("5200.00"), 3),
//    			new Order("A0005", "A4Tech Mouse", 
//    					new BigDecimal("100.00"), 10)
//    		));
//    }
//    
//	public ArrayList<Order> getOrderList() {
//		return orderList;
//	}
//	
//	public String saveAction() {
//		
//		//get all existing value but set "editable" to false 
//		for (Order order : orderList){
//			order.setEditable(false);
//			System.out.println("Value: " + order.productName);
//		}
//		
//		if(selectedOrder != null)
//			System.out.println("selectedOrder: " + selectedOrder.productName);
//		
//		//return to current page
//		return null;
//		
//	}
//	
//	public String editAction(Order order) {
//		selectedOrder = order;
//		order.setEditable(true);
//		return null;
//	}
// 
//	public Order getSelectedOrder() {
//		return selectedOrder;
//	}
//
//	public void setSelectedOrder(Order selectedOrder) {
//		this.selectedOrder = selectedOrder;
//	}
//
//	public static class Order{
//		
//		String orderNo;
//		String productName;
//		BigDecimal price;
//		int qty;
//		boolean editable;
//
//		public Order(String orderNo, String productName, BigDecimal price, int qty) {
//			this.orderNo = orderNo;
//			this.productName = productName;
//			this.price = price;
//			this.qty = qty;
//		}
//		
//		public boolean isEditable() {
//			return editable;
//		}
//		public void setEditable(boolean editable) {
//			this.editable = editable;
//		}
//		public String getOrderNo() {
//			return orderNo;
//		}
//		public void setOrderNo(String orderNo) {
//			this.orderNo = orderNo;
//		}
//		public String getProductName() {
//			return productName;
//		}
//		public void setProductName(String productName) {
//			this.productName = productName;
//		}
//		public BigDecimal getPrice() {
//			return price;
//		}
//		public void setPrice(BigDecimal price) {
//			this.price = price;
//		}
//		public int getQty() {
//			return qty;
//		}
//		public void setQty(int qty) {
//			this.qty = qty;
//		}
//	}
//}