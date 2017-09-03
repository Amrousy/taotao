package com.taotao.content.service;

import java.util.List;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

public interface ContentService {
	EasyUIDataGridResult getContentList(Long id, Integer page, Integer size);

	TaotaoResult addContent(TbContent tbContent);

	TaotaoResult updateContent(TbContent tbContent);

	TaotaoResult deleteContent(String[] ids);

	List<TbContent> getContentList(Long cid);
}
