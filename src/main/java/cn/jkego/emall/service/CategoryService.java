package cn.jkego.emall.service;


import cn.jkego.emall.dao.CategoryDao;
import cn.jkego.emall.utils.IdWorker;
import com.google.gson.Gson;
import cn.jkego.emall.domain.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoryService  implements Serializable {
    
    @Autowired
    private CategoryDao categoryDao;
    
    @Autowired
    private Jedis jedis;

    @Autowired
    private Gson gson;

    @Autowired
    private IdWorker idWorker;


    /**
     * 查询所以商品分类
     * 以json格式数据返回
     * @return
     */
    public String getCategoryList() {

        //先从Redis缓存中找数据
        String categoryListJson = jedis.get("categoryList");

        //缓存为空
        if (categoryListJson==null){
            List<Category> categoryList = categoryDao.findAll();
            categoryListJson = gson.toJson(categoryList);
            jedis.set("categoryListJson",categoryListJson);
        }

        return categoryListJson;
    }




    //查询所有商品分类信息
    //返回给前台list
    public List<Category> findAllCategories() {

        List<Category> all = categoryDao.findAll();
        return all;

    }






    //后台添加商品种类
    public void saveCategory(Category category) {

        category.setCid(idWorker.nextId());
        categoryDao.save(category);

    }


    //后台删除商品分类
    public void delCategory(String cid) {

        categoryDao.deleteById(cid);

    }


    //根据cid查询到商品分类信息
    public Category findCategoryByCid(String cid) {

        Optional<Category> category = categoryDao.findById(cid);

        return  category.get();

    }


    //根据cid修改分类信息
    public void editCategory(Category category) {

        categoryDao.save(category);

    }
}
