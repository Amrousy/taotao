package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentCategoryService;

@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {

	@Autowired
	private ContentCategoryService contentCategoryService;

	@RequestMapping("/list")
	@ResponseBody
	public List<EasyUITreeNode> getCategoryList(@RequestParam(value = "id", defaultValue = "0") Long parentId) {
		List<EasyUITreeNode> categoryList = contentCategoryService.getContentCategoryList(parentId);
		return categoryList;
	}

	@RequestMapping("/create")
	@ResponseBody
	public TaotaoResult addContentCategory(Long parentId, @RequestParam(value = "name") String nodeName) {
		TaotaoResult result = contentCategoryService.addContentCategory(parentId, nodeName);
		return result;
	}

	@RequestMapping("/update")
	@ResponseBody
	public TaotaoResult updateContentCategory(Long id, String name) {
		TaotaoResult taotaoResult = contentCategoryService.updateContentCategory(id, name);
		return taotaoResult;
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public TaotaoResult deleteContentCategory(Long id) {
		TaotaoResult taotaoResult = contentCategoryService.deleteContentCategory(id);
		return taotaoResult;
	}
}