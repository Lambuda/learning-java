package com.paparazzi.service;

import com.paparazzi.domain.Remark;

import java.util.List;

public interface RemarkService {


	/**
	 * 创建评论时 必须先确认 文章在不在
	 * @param remark
	 */
	void createRemark(Remark remark);

	void deleteRemark(Integer id);

	void updateRemark(Remark remark);

	//根据文章id 查询评论
	List<Remark> queryByArticleId(Integer articleId);


}
