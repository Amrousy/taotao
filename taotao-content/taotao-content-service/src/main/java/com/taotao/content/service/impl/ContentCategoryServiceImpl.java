package com.taotao.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentCategoryService;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.pojo.TbContentCategoryExample.Criteria;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	private TbContentCategoryMapper tbContentCategoryMapper;

	@Override
	public List<EasyUITreeNode> getContentCategoryList(long parentId) {
		// 根据parentId查询
		TbContentCategoryExample example = new TbContentCategoryExample();
		// 设置查询条件
		Criteria createCriteria = example.createCriteria();
		createCriteria.andParentIdEqualTo(parentId);
		// 执行查询
		List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(example);
		List<EasyUITreeNode> resultList = new ArrayList<>();
		for (TbContentCategory tbContentCategory : list) {
			EasyUITreeNode easyUITreeNode = new EasyUITreeNode();
			easyUITreeNode.setId(tbContentCategory.getId());
			easyUITreeNode.setText(tbContentCategory.getName());
			easyUITreeNode.setState(tbContentCategory.getIsParent() ? "closed" : "open");
			// 添加列表
			resultList.add(easyUITreeNode);
		}
		return resultList;
	}

	@Override
	public TaotaoResult addContentCategory(long parentId, String nodeName) {
		// 创建一个TbContentCategory对象
		TbContentCategory contentCategory = new TbContentCategory();
		// 补全TbContentCategory信息
		contentCategory.setIsParent(false);
		contentCategory.setName(nodeName);
		contentCategory.setParentId(parentId);
		contentCategory.setSortOrder(1);
		contentCategory.setStatus(1);
		contentCategory.setUpdated(new Date());
		contentCategory.setCreated(new Date());
		// 执行插入操作
		tbContentCategoryMapper.insert(contentCategory);
		// 修改父节点信息
		TbContentCategory parentNode = tbContentCategoryMapper.selectByPrimaryKey(parentId);
		if (!parentNode.getIsParent()) {
			parentNode.setIsParent(true);
			// 更新父节点
			tbContentCategoryMapper.updateByPrimaryKeySelective(parentNode);
		}
		return TaotaoResult.ok(contentCategory);
	}

	@Override
	public TaotaoResult updateContentCategory(Long id, String name) {
		TbContentCategory contentCategory = tbContentCategoryMapper.selectByPrimaryKey(id);
		contentCategory.setName(name);
		tbContentCategoryMapper.updateByPrimaryKeySelective(contentCategory);
		return TaotaoResult.ok(contentCategory);
	}

	@Override
	public TaotaoResult deleteContentCategory(Long currentId) {
		// 查询当前节点
		TbContentCategory currentNode = tbContentCategoryMapper.selectByPrimaryKey(currentId);
		// 查询当前节点的父节点
		TbContentCategory parentNode = tbContentCategoryMapper.selectByPrimaryKey(currentNode.getParentId());
		// 如果当前节点为子节点直接删除，若为父节点递归删除
		if (!currentNode.getIsParent()) {
			tbContentCategoryMapper.deleteByPrimaryKey(currentId);
		} else {
			// 若当前节点为父节点，则取出此节点下的所有子节点
			List<TbContentCategory> nodeList = getNodeList(currentNode.getId());
			delCategory(nodeList, currentNode);
		}
		// 根据父节点查出是否还有子节点
		List<TbContentCategory> list = getNodeList(currentNode.getParentId());
		if (null == list || 0 == list.size()) {
			// 变更状态
			parentNode.setIsParent(false);
			tbContentCategoryMapper.updateByPrimaryKeySelective(parentNode);
		}
		return TaotaoResult.ok();
	}

	// 递归删除父节点以及其下的所有子节点
	private void delCategory(List<TbContentCategory> nodeList, TbContentCategory currentNode) {
		for (TbContentCategory tbContentCategory : nodeList) {
			Long id = tbContentCategory.getId();
			if (tbContentCategory.getIsParent()) {
				List<TbContentCategory> list = getNodeList(id);
				delCategory(list, tbContentCategory);
			} else {
				tbContentCategoryMapper.deleteByPrimaryKey(id);
			}
		}
		// 删除顶层父节点
		tbContentCategoryMapper.deleteByPrimaryKey(currentNode.getId());
	}

	// 根据节点id查询子节点列表
	private List<TbContentCategory> getNodeList(Long id) {
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(id);
		return tbContentCategoryMapper.selectByExample(example);
	}

}
