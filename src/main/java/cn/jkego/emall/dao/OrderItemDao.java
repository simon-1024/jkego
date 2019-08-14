package cn.jkego.emall.dao;

import cn.jkego.emall.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface OrderItemDao extends JpaRepository<OrderItem,String>, JpaSpecificationExecutor<OrderItem> {



    @Query(nativeQuery = true,value = "select i.count, i.subtotal,p.pimage,p.pname,p.shop_price from orderitem i,product p where i.pid=p.pid and i.oid=?1")
    public List<Map<String,Object>> findOrderItemsAndProductByOrderOid(String oid);


}
