package cn.jkego.emall.controller;

import cn.jkego.emall.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 获取商品类别列表
     */
    @PostMapping("/categoryList")
    @ResponseBody
    public String getCategoryList(){

        String categoryList = categoryService.getCategoryList();

        return  categoryList;
    }

}
