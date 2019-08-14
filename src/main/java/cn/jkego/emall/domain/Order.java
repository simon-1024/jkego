package cn.jkego.emall.domain;

import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Order implements Serializable {

	@Id
	private String oid;//该订单的id

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date ordertime;//订单时间
	private double total;//订单总金额
	private int state;//订单支付状态 1代表已付款 0代表未付款


	private String address;//收货地址
	private String name;//收货人
	private String telephone;//收货人电话


	@ManyToOne(targetEntity=User.class,fetch = FetchType.LAZY)
	@JoinColumn(name="uid",referencedColumnName="uid")
	private User user;//该订单属于哪个用户


	//该订单中有多少订单项
	@OneToMany(targetEntity = OrderItem.class,mappedBy = "order")
	@NotFound(action= NotFoundAction.IGNORE)
	private List<OrderItem> orderItems = new ArrayList<OrderItem>();

	public String toString(){
		return  null;
	}


}
