package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;

/**
 * 商品管理Controller
 */
@Controller
public class ItemController {

	@Autowired
	private ItemService itemService;

	@RequestMapping("/item/list")
	@ResponseBody
	public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
		EasyUIDataGridResult result = itemService.getItemList(page, rows);
		return result;
	}

	@RequestMapping("/item/save")
	@ResponseBody
	public TaotaoResult addItem(TbItem item, String desc) {
		TaotaoResult result = itemService.addItem(item, desc);
		return result;
	}

	@RequestMapping("/rest/item/delete")
	@ResponseBody
	public TaotaoResult deleteItem(String[] ids) {
		TaotaoResult result = itemService.deleteItem(ids);
		return result;
	}

	@RequestMapping("/rest/item/query/item/desc/{id}")
	@ResponseBody
	public TaotaoResult queryItemDesc(@PathVariable String id) {
		TaotaoResult taotaoResult = itemService.selectItemDesc(id);
		return taotaoResult;
	}
}
