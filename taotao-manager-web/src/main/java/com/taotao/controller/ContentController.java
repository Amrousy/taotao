package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentService;
import com.taotao.pojo.TbContent;

@Controller
public class ContentController {

	@Autowired
	private ContentService contentService;

	@RequestMapping("/content/query/list")
	@ResponseBody
	public EasyUIDataGridResult getContentList(@RequestParam(value = "categoryId", defaultValue = "0") Long id,
			Integer page, Integer rows) {
		EasyUIDataGridResult result = contentService.getContentList(id, page, rows);
		return result;
	}
	
	@RequestMapping("/content/save")
	@ResponseBody
	public TaotaoResult saveContent(TbContent tbContent) {
		TaotaoResult result = contentService.addContent(tbContent);
		return result;
	}
	
	@RequestMapping("/rest/content/edit")
	@ResponseBody
	public TaotaoResult updateContent(TbContent tbContent) {
		TaotaoResult result = contentService.updateContent(tbContent);
		return result;
	}
	
	@RequestMapping("/content/delete")
	@ResponseBody
	public TaotaoResult deleteContent(String[] ids) {
		TaotaoResult result = contentService.deleteContent(ids);
		return result;
	}
}
