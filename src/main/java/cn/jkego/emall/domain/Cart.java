package cn.jkego.emall.domain;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Cart {
	
	//购物车中存的n个购物项
	private Map<String,CartItem> cartItems = new HashMap<>();
	
	//总计金额
	private double total;

}
