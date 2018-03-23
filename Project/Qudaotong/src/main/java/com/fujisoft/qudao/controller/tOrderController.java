package com.fujisoft.qudao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.fujisoft.qudao.domain.tOrder;
import com.fujisoft.qudao.repository.CustomerRepository;
import com.fujisoft.qudao.repository.GoodRepository;
import com.fujisoft.qudao.service.CustomerService;
import com.fujisoft.qudao.service.GoodService;
import com.fujisoft.qudao.service.tOrderService;

@Controller
public class tOrderController {
	@Autowired
	private tOrderService torderService;
	@Autowired
	private GoodService goodService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	GoodRepository goodRepository;

	// 查询订单信息
	@GetMapping("/Orderlist")
	String mylist(ModelMap modelMap, Model model, @RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "5") Integer size) {
		Page<tOrder> datas = torderService.getOrderList(page, size);
		modelMap.addAttribute("datas", datas);
		return "html/OrderManagement/OrderManagement";
	}

	// 去添加订单信息界面
	@RequestMapping("/toOrderAdd")
	public String toAdd(Model model) {
		model.addAttribute("companylists", customerService.findCompanylist());
		model.addAttribute("goodlists", goodService.findGoodlist());
		return "html/OrderManagement/OrderManagementAdd";
	}

	// 添加订单信息
	@RequestMapping("/Orderadd")
	public String orderadd(tOrder torder) {
		torderService.save(torder);
		return "redirect:/Orderlist";
	}

	// 去编辑订单信息界面
	@RequestMapping("/toOrderEdit")
	public String toEdit(Model model, Integer id) {
		List<tOrder> torder = torderService.findOrderById(id);
		model.addAttribute("torder", torder);
		return "html/OrderManagement/OrderManagementEdit";
	}

	// 编辑订单信息
	@RequestMapping("/Orderedit")
	public String orderedit(tOrder torder) {
		torderService.edit(torder);
		return "redirect:/Orderlist";
	}

	// 删除订单信息
	@RequestMapping("/Orderdelete")
	public String delete(Integer id) {
		torderService.delete(id);
		return "redirect:/Orderlist";
	}
}