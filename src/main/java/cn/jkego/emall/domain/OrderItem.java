package cn.jkego.emall.domain;

import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "orderitem")
public class OrderItem implements Serializable {
	

	@Id
	private String itemid;//订单项的id
	private int count;//订单项内购买商品的数量
	private double subtotal;//订单项内的小计



	@OneToOne(targetEntity = Product.class)
	@JoinColumn(name = "pid",referencedColumnName = "pid")
	private Product product;//订单项中的商品



	@ManyToOne(targetEntity = Order.class)
	@NotFound(action= NotFoundAction.IGNORE)
	@JoinColumn(name = "oid",referencedColumnName = "oid")
	private Order order;//订单项所属的订单


	public String toString(){
		return  null;
	}



}
