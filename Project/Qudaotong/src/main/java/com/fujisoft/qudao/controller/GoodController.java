package com.fujisoft.qudao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fujisoft.qudao.domain.Customer;
import com.fujisoft.qudao.domain.Good;
import com.fujisoft.qudao.service.GoodService;

@Controller
public class GoodController {
	@Autowired
	private GoodService goodService;

	// 查询货物的信息
	@GetMapping("/Goodlist")
	String goodlist(ModelMap modelMap, Model model, @RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "5") Integer size) {
		Page<Good> datas = goodService.getGoodList(page, size);
		modelMap.addAttribute("datas", datas);
		return "html/GoodManagement/GoodManagement";
	}

	// 去添加货物信息界面
	@RequestMapping("/toGoodAdd")
	public String toAdd() {
		return "html/GoodManagement/GoodManagementAdd";
	}

	// 添加保存货物信息
	@RequestMapping("/Goodadd")
	public String add(Good good) {
		goodService.save(good);
		return "redirect:/Goodlist";
	}

	// 去编辑货物信息界面
	@RequestMapping("/toGoodEdit")
	public String toEdit(Model model, Integer id) {
		List<Good> good = goodService.findGoodById(id);
		model.addAttribute("good", good);
		return "html/GoodManagement/GoodManagementEdit";
	}

	// 编辑货物信息
	@RequestMapping("/Goodedit")
	public String edit(Good good) {
		goodService.edit(good);
		return "redirect:/Goodlist";
	}

	// 删除客户信息
	@RequestMapping("/Gooddelete")
	public String delete(Integer id) {
		goodService.delete(id);
		return "redirect:/Goodlist";
	}

}
