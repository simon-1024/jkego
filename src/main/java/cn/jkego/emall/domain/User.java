package cn.jkego.emall.domain;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "user")
public class User implements Serializable  {

	@Id
	private String uid;
	private String username;
	private String password;
	private String name;
	private String email;
	private String telephone;
	private int role;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthday;
	private String sex;
	private int state;//是否激活
	private String code;//激活码

}
