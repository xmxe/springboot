package com.xmxe.entity;

/**
 * 审批
 */
public class ApproveRejectVO {
	// 任务id
	private String taskId;
	// approve为true表示申请通过，false表示申请被拒绝。
	private Boolean approve;
	// 用户
	private String name;

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public Boolean getApprove() {
		return approve;
	}

	public void setApprove(Boolean approve) {
		this.approve = approve;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}