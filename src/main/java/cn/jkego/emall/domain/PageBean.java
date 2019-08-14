package cn.jkego.emall.domain;

import lombok.Data;

import java.util.List;

@Data
public class PageBean<T> {

	//当前是第几页
	private int currentPage;
	//每页多少条记录
	private int currentCount;
	//总记录数
	private int totalCount;
	//总页数
	private int totalPage;
	//当前页的数据集合
	private List<T> list;

}
