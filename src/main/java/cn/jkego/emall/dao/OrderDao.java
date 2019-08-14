package cn.jkego.emall.dao;

import cn.jkego.emall.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
        import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface OrderDao extends JpaRepository<Order,String>, JpaSpecificationExecutor<Order> {


    public List<Order> findOrderByUserUid(String  uid);


    @Query(nativeQuery = true,value = "select p.pimage,p.pname,p.shop_price,i.count,i.subtotal from orderitem i,product p where i.pid=p.pid and i.oid=?1")
    List<Map<String, Object>> findOrderInfoByOid(String oid);
}
