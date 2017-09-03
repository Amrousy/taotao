package com.taotao.content.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentService;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.pojo.TbContentExample.Criteria;

@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper tbContentMapper;

	@Override
	public EasyUIDataGridResult getContentList(Long id, Integer page, Integer rows) {
		// 分页处理
		PageHelper.startPage(page, rows);
		// 执行查询
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(id);
		List<TbContent> list = tbContentMapper.selectByExampleWithBLOBs(example);
		// 创建返回结果对象
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setRows(list);
		PageInfo<TbContent> pageInfo = new PageInfo<>(list);
		result.setTotal(pageInfo.getTotal());
		return result;
	}

	@Override
	public TaotaoResult addContent(TbContent tbContent) {
		try {
			tbContent.setCreated(new Date());
			tbContent.setUpdated(new Date());
			tbContentMapper.insert(tbContent);
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, "添加失败...请联系管理员！！！");
		}
		return TaotaoResult.build(200, "添加成功!!!");
	}

	@Override
	public TaotaoResult updateContent(TbContent tbContent) {
		try {
			tbContent.setUpdated(new Date());
			tbContentMapper.updateByPrimaryKeySelective(tbContent);
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, "更新失败...请联系管理员！！！");
		}
		return TaotaoResult.build(200, "更新成功!!!");
	}

	@Override
	public TaotaoResult deleteContent(String[] ids) {
		if (null == ids || 0 == ids.length) {
			return TaotaoResult.build(500, "删除失败...请联系管理员！！！");
		}
		for (String id : ids) {
			tbContentMapper.deleteByPrimaryKey(Long.parseLong(id));
		}
		return TaotaoResult.ok(null);
	}

	@Override
	public List<TbContent> getContentList(Long cid) {
		// 根据分类id查询内容列表
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(cid);
		List<TbContent> list = tbContentMapper.selectByExample(example);
		return list;
	}

}
