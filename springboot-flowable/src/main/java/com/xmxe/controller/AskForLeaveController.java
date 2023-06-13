package com.xmxe.controller;

import com.xmxe.entity.ApproveRejectVO;
import com.xmxe.entity.AskForLeaveVO;
import com.xmxe.entity.RespBean;
import com.xmxe.service.AskForLeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AskForLeaveController {

	@Autowired
	AskForLeaveService askForLeaveService;

	/**
	 * 请假申请
	 */
	@PostMapping("/ask_for_leave")
	public RespBean askForLeave(@RequestBody AskForLeaveVO askForLeaveVO) {
		return askForLeaveService.askForLeave(askForLeaveVO);
	}

	/**
	 * 任务接口
	 * @param identity 用户身份标识
	 */
	@GetMapping("/list")
	public RespBean leaveList(String identity) {
		return askForLeaveService.leaveList(identity);
	}

	/**
	 * 请假审批处理
	 */
	@PostMapping("/handler")
	public RespBean askForLeaveHandler(@RequestBody ApproveRejectVO approveRejectVO) {
		return askForLeaveService.askForLeaveHandler(approveRejectVO);
	}

	/**
	 * 结果查询
	 */
	@GetMapping("/search")
	public RespBean searchResult(String name) {
		return askForLeaveService.searchResult(name);
	}
}