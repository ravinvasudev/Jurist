package in.co.jurist.action;

import in.co.jurist.model.Amendment;
import in.co.jurist.service.AmendmentService;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionSupport;

public class AmendmentAction extends ActionSupport {

	private static final Logger logger = LoggerFactory
			.getLogger(AmendmentAction.class);

	private static final long serialVersionUID = 1L;

	/* Spring injected */
	private AmendmentService amendmentService;

	/* Input parameter. */
	private Amendment amendment;

	/* Input parameter. */
	private int paraId;

	/* Output parameter in response to Load All Paragraphs. */
	private List<Amendment> list;

	/* Output parameter in response to Add New Paragraph. */
	private int status;

	public AmendmentAction() {
		// logger.info("AmendmentAction constructor called.");
	}

	/* Get Amendments for a specific Paragraph */
	public String getAmendments() {
		setList(getAmendmentService().filterAmendments(getParaId()));
		return SUCCESS;
	}

	/* Add a new Amendment */
	public String addAmendment() {
		setStatus(getAmendmentService().addNewAmendment(getAmendment()));
		return SUCCESS;
	}

	public Amendment getAmendment() {
		return amendment;
	}

	public void setAmendment(Amendment amendment) {
		this.amendment = amendment;
	}

	public AmendmentService getAmendmentService() {
		return amendmentService;
	}

	public void setAmendmentService(AmendmentService amendmentService) {
		this.amendmentService = amendmentService;
	}

	public List<Amendment> getList() {
		return list;
	}

	public void setList(List<Amendment> list) {
		this.list = list;
		if (list == null || list.isEmpty()) {
			logger.info("AmendmentAction: List is null.");
		}
	}

	public int getParaId() {
		return paraId;
	}

	public void setParaId(int paraId) {
		this.paraId = paraId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
