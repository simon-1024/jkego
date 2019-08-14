package cn.jkego.emall.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class CartItem implements Serializable {
	
	private Product product;
	private int buyNum;
	private double subtotal;

}
