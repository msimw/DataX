package com.alibaba.datax.core.scheduler.standalone;

import com.alibaba.datax.common.exception.DataXException;
import com.alibaba.datax.core.container.SlaveContainer;
import com.alibaba.datax.core.util.FrameworkErrorCode;
import com.alibaba.datax.core.util.State;

/**
 * Created by jingxing on 14-8-28.
 */
public class SlaveContainerRunner implements Runnable {

	private SlaveContainer slaveContainer;

	private State state;

	public SlaveContainerRunner(SlaveContainer slave) {
		this.slaveContainer = slave;
		this.state = State.SUCCESS;
	}

	@Override
	public void run() {
		try {
            Thread.currentThread().setName(
                    String.format("slaveContainer-%d", this.slaveContainer.getSlaveContainerId()));
            this.slaveContainer.start();
			this.state = State.SUCCESS;
		} catch (Throwable e) {
			this.state = State.FAIL;
			throw DataXException.asDataXException(
					FrameworkErrorCode.RUNTIME_ERROR, e);
		}
	}

	public SlaveContainer getSlaveContainer() {
		return slaveContainer;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
}
