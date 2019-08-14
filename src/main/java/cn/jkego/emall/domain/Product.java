package cn.jkego.emall.domain;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "product")
public class Product implements Serializable {
	/*`pid` varchar(32) NOT NULL,
	  `pname` varchar(50) DEFAULT NULL,
	  `market_price` double DEFAULT NULL,
	  `shop_price` double DEFAULT NULL,
	  `pimage` varchar(200) DEFAULT NULL,
	  `pdate` date DEFAULT NULL,
	  `is_hot` int(11) DEFAULT NULL,
	  `pdesc` varchar(255) DEFAULT NULL,
	  `pflag` int(11) DEFAULT NULL,
	  `cid` varchar(32) DEFAULT NULL*/
	@Id
	private String pid;
	private String pname;
	private double market_price;
	private double shop_price;
	private String pimage;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date pdate;
	private int is_hot;
	private String pdesc;
	private int pflag;

	@OneToOne(targetEntity = Category.class)
	@JoinColumn(name = "cid",referencedColumnName = "cid")
	private Category category;


	public String toString(){

		return null;
	}

}
