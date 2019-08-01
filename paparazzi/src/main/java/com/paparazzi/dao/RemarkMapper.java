package com.paparazzi.dao;

import com.paparazzi.domain.Remark;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RemarkMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Remark record);

    int insertSelective(Remark record);

    Remark selectByPrimaryKey(@Param("id") Integer id);

    int updateByPrimaryKeySelective(Remark record);

    int updateByPrimaryKeyWithBLOBs(Remark record);

    int updateByPrimaryKey(Remark record);


	//根据文章id 查询评论
	List<Remark> queryByArticleId(@Param("id") Integer articleId);
}