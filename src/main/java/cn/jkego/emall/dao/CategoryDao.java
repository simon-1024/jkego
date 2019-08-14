package cn.jkego.emall.dao;

import cn.jkego.emall.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CategoryDao extends JpaRepository<Category, String>, JpaSpecificationExecutor<Category> {


}
