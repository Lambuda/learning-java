package com.paparazzi.service.impl;

import com.paparazzi.dao.RemarkMapper;
import com.paparazzi.domain.Remark;
import com.paparazzi.service.RemarkService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Service
public class RemarkServiceImpl implements RemarkService {


	@Resource
	private RemarkMapper remarkMapper;

	@Transactional
	@Override
	public void createRemark(Remark remark) {
		remarkMapper.insert(remark);

	}

	@Transactional
	@Override
	public void deleteRemark(Integer id) {
		remarkMapper.deleteByPrimaryKey(id);
	}

	@Transactional
	@Override
	public void updateRemark(Remark remark) {
		remarkMapper.updateByPrimaryKey(remark);
	}

	@Override
	public List<Remark> queryByArticleId(Integer articleId) {
		return remarkMapper.queryByArticleId(articleId);
	}
}
