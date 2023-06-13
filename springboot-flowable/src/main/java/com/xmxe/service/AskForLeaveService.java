package com.xmxe.service;

import com.xmxe.entity.ApproveRejectVO;
import com.xmxe.entity.AskForLeaveVO;
import com.xmxe.entity.HistoryInfo;
import com.xmxe.entity.RespBean;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.variable.api.history.HistoricVariableInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class AskForLeaveService {

	@Autowired
	RuntimeService runtimeService;

	@Autowired
	TaskService taskService;

	@Autowired
	HistoryService historyService;


	@Transactional
	public RespBean askForLeave(AskForLeaveVO askForLeaveVO) {
		Map<String, Object> variables = new HashMap<>();
		variables.put("name", askForLeaveVO.getName());
		variables.put("days", askForLeaveVO.getDays());
		variables.put("reason", askForLeaveVO.getReason());
		try {
			// 开启一个流程，开启流程的时候一共传入了三个参数
			// 第一个参数表示流程引擎的名字，这就是我们刚才在流程的XML文件中定义的名字。
			// 第二个参数表示当前这个流程的key，我用了申请人的名字，将来我们可以通过申请人的名字查询这个人曾经提交的所有申请流程。
			// 第三个参数就是我们的变量了。
			runtimeService.startProcessInstanceByKey("holidayRequest", askForLeaveVO.getName(), variables);
			return RespBean.ok("已提交请假申请");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return RespBean.error("提交申请失败");
	}

	public RespBean leaveList(String identity) {
		// Task就是流程中要做的每一件事情
		// 查询出来这个用户需要处理的任务，例如前端前传来的是managers，那么这里就是查询所有需要由managers用户组处理的任务。
		List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup(identity).list();
		List<Map<String, Object>> list = new ArrayList<>();
		for (int i = 0; i < tasks.size(); i++) {
			Task task = tasks.get(i);
			Map<String, Object> variables = taskService.getVariables(task.getId());
			variables.put("id", task.getId());
			list.add(variables);
		}
		return RespBean.ok("加载成功", list);
	}

	public RespBean askForLeaveHandler(ApproveRejectVO approveRejectVO) {
		try {
			// approved为true，就会自动进入到审批通过的流程中，approved为false则会自动进入到拒绝流程中。
			boolean approved = approveRejectVO.getApprove();
			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("approved", approved);
			variables.put("employee", approveRejectVO.getName());
			// 通过taskService，结合taskId，从流程中查询出对应的task，然后调用taskService.complete方法传入taskId和变量，以使流程向下走
			Task task = taskService.createTaskQuery().taskId(approveRejectVO.getTaskId()).singleResult();
			taskService.complete(task.getId(), variables);
			if (approved) {
				// 如果是同意，还需要继续走一步
				// 从当前流程中查询出来需要执行的task，再调用complete继续走一步，此时就到了结束事件了，这个流程就结束了
				Task t = taskService.createTaskQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
				taskService.complete(t.getId());
			}
			return RespBean.ok("操作成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return RespBean.error("操作失败");
	}

	public RespBean searchResult(String name) {
		List<HistoryInfo> historyInfos = new ArrayList<>();
		List<HistoricProcessInstance> historicProcessInstances = historyService.createHistoricProcessInstanceQuery().processInstanceBusinessKey(name).finished().orderByProcessInstanceEndTime().desc().list();
		for (HistoricProcessInstance historicProcessInstance : historicProcessInstances) {
			HistoryInfo historyInfo = new HistoryInfo();
			Date startTime = historicProcessInstance.getStartTime();
			Date endTime = historicProcessInstance.getEndTime();
			List<HistoricVariableInstance> historicVariableInstances = historyService.createHistoricVariableInstanceQuery()
					.processInstanceId(historicProcessInstance.getId())
					.list();
			for (HistoricVariableInstance historicVariableInstance : historicVariableInstances) {
				String variableName = historicVariableInstance.getVariableName();
				Object value = historicVariableInstance.getValue();
				if ("reason".equals(variableName)) {
					historyInfo.setReason((String) value);
				} else if ("days".equals(variableName)) {
					historyInfo.setDays(Integer.parseInt(value.toString()));
				} else if ("approved".equals(variableName)) {
					historyInfo.setStatus((Boolean) value);
				} else if ("name".equals(variableName)) {
					historyInfo.setName((String) value);
				}
			}
			historyInfo.setStartTime(startTime);
			historyInfo.setEndTime(endTime);
			historyInfos.add(historyInfo);
		}
		return RespBean.ok("ok", historyInfos);
	}
}