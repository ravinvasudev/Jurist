package in.co.jurist.action;

import in.co.jurist.model.Paragraph;
import in.co.jurist.service.ParagraphService;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionSupport;

public class ParagraphAction extends ActionSupport {

	private static final Logger logger = LoggerFactory
			.getLogger(ParagraphAction.class);

	private static final long serialVersionUID = 1L;

	/* Spring injected */
	private ParagraphService paragraphService;

	/* Input parameter. */
	private int actId;

	/* Input parameter. */
	private int paraId;

	/* Input parameter. */
	private Paragraph reqPara;

	/* Output parameter. */
	private Paragraph respPara;

	/* Output parameter in response to Load All Paragraphs. */
	private List<Paragraph> list;

	/* Output parameter in response to Add New Paragraph. */
	private int status;

	public ParagraphAction() {
		// logger.info("ParagraphAction constructor called.");
	}

	/* Get Paragraphs for a specific Act. */
	public String getParagraphHeaders() {
		setList(getParagraphService().filterParagraphHeaders(getActId()));
		return SUCCESS;
	}

	/* Get Paragraph Content for a specific Paragraph. */
	public String getParagraphContent() {
		setRespPara(getParagraphService().getParagraph(getParaId()));
		setParaId(0);
		return SUCCESS;
	}

	/* Add a new Paragraph. */
	public String addParagraph() {
		int sectionId = getParagraphService().addNewParagraph(getReqPara());
		setStatus(sectionId);
		setReqPara(null);
		return SUCCESS;
	}
	
	public String updateParagraphContent() {
		setStatus(getParagraphService().updateParagraphContent(getReqPara()));
		setReqPara(null);
		return SUCCESS;
	}
	
	public String updateTranslation() {
		setStatus(getParagraphService().updateTranslation(getReqPara()));
		setReqPara(null);
		return SUCCESS;
	}

	public int getActId() {
		return actId;
	}

	public void setActId(int actId) {
		this.actId = actId;
	}

	public List<Paragraph> getList() {
		return list;
	}

	public void setList(List<Paragraph> list) {
		this.list = list;
		if (list == null || list.isEmpty()) {
			logger.info("ParagraphAction: List is null.");
		}
	}

	public ParagraphService getParagraphService() {
		return paragraphService;
	}

	public void setParagraphService(ParagraphService paragraphService) {
		this.paragraphService = paragraphService;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getParaId() {
		return paraId;
	}

	public void setParaId(int paraId) {
		this.paraId = paraId;
	}

	public Paragraph getReqPara() {
		return reqPara;
	}

	public void setReqPara(Paragraph reqPara) {
		this.reqPara = reqPara;
	}

	public Paragraph getRespPara() {
		return respPara;
	}

	public void setRespPara(Paragraph respPara) {
		this.respPara = respPara;
	}

}