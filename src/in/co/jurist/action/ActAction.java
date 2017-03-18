package in.co.jurist.action;

import in.co.jurist.model.Act;
import in.co.jurist.service.ActService;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionSupport;

public class ActAction extends ActionSupport {

	private static final Logger logger = LoggerFactory
			.getLogger(ActAction.class);

	private static final long serialVersionUID = 1L;

	/* Spring injected */
	private ActService actService;

	/* Input parameter. */
	private Act reqAct;

	/* Output parameter in response to Load All Acts. */
	private List<Act> list;

	/* Output parameter in response to Add New Act. */
	private int status;

	public ActAction() {
	}

	/* Get all Acts */
	public String getActs() {
		setList(getActService().getAllActs());
		return SUCCESS;
	}

	/* Adds new Act in database. */
	public String addAct() {
		int actId = getActService().addNewAct(getReqAct());
		setStatus(actId);
		setReqAct(null);
		return SUCCESS;
	}

	/* Update Act info. */
	public String updateActInfo() {
		int status = getActService().updateActInfo(getReqAct());
		setStatus(status);
		setReqAct(null);
		return SUCCESS;
	}

	public ActService getActService() {
		return actService;
	}

	public void setActService(ActService actService) {
		this.actService = actService;
	}

	public List<Act> getList() {
		return list;
	}

	public void setList(List<Act> list) {
		this.list = list;
		if (list == null || list.isEmpty()) {
			logger.info("ActAction: List is null.");
		}
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Act getReqAct() {
		return reqAct;
	}

	public void setReqAct(Act reqAct) {
		this.reqAct = reqAct;
	}

}
