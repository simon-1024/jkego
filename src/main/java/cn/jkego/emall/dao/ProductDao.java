package cn.jkego.emall.dao;

import cn.jkego.emall.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ProductDao extends JpaRepository<Product,String>, JpaSpecificationExecutor<Product> {


    /**
     * 查询热门商品
     * @return
     */
    @Query(nativeQuery=true,value = "select * from product where is_hot=1 limit 0,9")
    public List<Product> findHotProduct();

    /**
     * 查询最新商品
     * @return
     */
    @Query(nativeQuery=true,value = "select * from product order by pdate desc limit 0,9")
    public List<Product> findNewProduct();

    /**
     * 查询该分类下有多少条商品
     * @param cid
     * @return
     */
    @Query(nativeQuery=true,value = "select count(*) from product where cid=?1")
    public int getTotalCountByCid(String cid);


    /**
     * 分页查询商品
     * @param cid
     * @param index
     * @param currentCount
     * @return
     */
    @Query(nativeQuery = true,value = "select * from product where cid=?1 limit ?2,?3")
    List<Product> findProductByPage(String cid, int index, int currentCount);


    /**
     * 查询商品详情
     * @param pid
     * @return
     */
    public Product findByPid(String pid);


}
