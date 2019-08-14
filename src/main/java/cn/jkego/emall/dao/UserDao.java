package cn.jkego.emall.dao;

import cn.jkego.emall.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface UserDao extends JpaRepository<User,String>, JpaSpecificationExecutor<User> {


    /**
     * 检查用户是否已经存在
     * @param name
     * @return
     */
    public User findByName(String name);

    /**
     * 用户激活
     * @param code
     * @return
     */
    @Modifying
    @Transactional
    @Query("update User set state = 1 where code =?1")
    int activeUser(@Param("code") String code);

    /**
     * 用户登录
     */
    public User findByUsername(String username);
}
