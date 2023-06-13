package com.xmxe.config;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

/**
 * 批准或者拒绝之后的自定义逻辑
 */
public class Approve implements JavaDelegate {
	@Override
	public void execute(DelegateExecution execution) {
		System.out.println("申请通过:"+execution.getVariables());
	}
}