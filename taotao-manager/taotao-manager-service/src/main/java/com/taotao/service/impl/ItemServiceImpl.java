package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.IDUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.pojo.TbItemExample.Criteria;
import com.taotao.service.ItemService;

/**
 * 商品管理Service
 * <p>
 * Title: ItemServiceImpl
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Company: www.itcast.cn
 * </p>
 * 
 * @version 1.0
 */

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemDescMapper itemDescMapper;

	@Override
	public EasyUIDataGridResult getItemList(int page, int rows) {

		// 分页处理
		PageHelper.startPage(page, rows);
		// 执行查询
		TbItemExample example = new TbItemExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andStatusNotEqualTo((byte) 3);
		List<TbItem> list = itemMapper.selectByExample(example);
		// 创建返回结果对象
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setRows(list);
		// 取总记录数
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		result.setTotal(pageInfo.getTotal());

//		TbItem item = itemMapper.selectByPrimaryKey(150384793636614L);
//		item.setStatus((byte) 3);
//		itemMapper.updateByPrimaryKeySelective(item);

		return result;
	}

	@Override
	public TaotaoResult addItem(TbItem item, String desc) {

		// 生成商品id
		long itemId = IDUtils.genItemId();
		item.setId(itemId);
		// 1-正常，2-下架，3-删除
		item.setStatus((byte) 1);
		Date date = new Date();
		item.setCreated(date);
		item.setUpdated(date);
		// 插入到商品表
		itemMapper.insert(item);
		// 商品描述
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setItemId(itemId);
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(date);
		itemDesc.setUpdated(date);
		// 插入商品描述
		itemDescMapper.insert(itemDesc);

		return TaotaoResult.ok();
	}

	// 删除商品
	@Override
	public TaotaoResult deleteItem(String[] ids) {
		if (null == ids || 0 == ids.length) {
			return TaotaoResult.build(500, "删除失败,请务必选择一个商品!!!");
		}
		for (String id : ids) {
			TbItem item = itemMapper.selectByPrimaryKey(Long.parseLong(id));
			item.setStatus((byte) 3);
			itemMapper.updateByPrimaryKeySelective(item);
			return TaotaoResult.ok();
		}
		return TaotaoResult.build(500, "删除失败,服务器出错!!!");
	}

	// 编辑商品
	@Override
	public TaotaoResult selectItemDesc(String id) {
		TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(Long.parseLong(id));
		return TaotaoResult.build(200, null, itemDesc);
	}

}
