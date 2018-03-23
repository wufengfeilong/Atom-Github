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
import com.fujisoft.qudao.domain.Customer;
import com.fujisoft.qudao.repository.CustomerRepository;
import com.fujisoft.qudao.repository.PositionRepository;
import com.fujisoft.qudao.service.CustomerService;
import com.fujisoft.qudao.service.PositionService;

@Controller
public class CustomerController {
	@Autowired
	private CustomerService customerService;
	@Autowired
	private PositionService positionService;
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	PositionRepository positionRepository;

	// 查询客户信息
	@GetMapping("/Customerlist")
	public String index(ModelMap modelMap, Model model, @RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "5") Integer size) {
		Page<Customer> datas = customerService.queryForPage(page, size);
		modelMap.addAttribute("datas", datas);
		return "html/CustomerManagement/CustomerManagement";
	}

	// 去添加客户信息界面
	@RequestMapping("/toCustomerAdd")
	public String toAdd(Model model) {
		model.addAttribute("positions", positionService.getPositionList());
		return "html/CustomerManagement/CustomerManagementAdd";
	}

	// 添加客户信息
	@RequestMapping("/Customeradd")
	public String add(Customer customer, Model model) {
		customerService.save(customer);
		return "redirect:/Customerlist";
	}

	// 去编辑客户信息界面
	@RequestMapping("/toCustomerEdit")
	public String toEdit(Model model, Integer id) {
		List<Customer> customer = customerService.findCustomerById(id);
		model.addAttribute("customer", customer);
		model.addAttribute("customerid", positionRepository.findOne(id));
		model.addAttribute("positions", positionService.getPositionList());
		return "html/CustomerManagement/CustomerManagementEdit";
	}

	// 编辑客户信息
	@RequestMapping("/Customeredit")
	public String edit(Customer customer) {
		customerService.edit(customer);
		return "redirect:/Customerlist";
	}

	// 删除客户信息
	@RequestMapping("/Customerdelete")
	public String delete(Integer id) {
		customerService.delete(id);
		return "redirect:/Customerlist";
	}

}