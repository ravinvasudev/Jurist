package in.co.jurist.action;

import in.co.jurist.model.Keyword;
import in.co.jurist.model.TagItem;
import in.co.jurist.service.KeywordService;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionSupport;

public class KeywordAction extends ActionSupport implements Serializable {

	private static final Logger logger = LoggerFactory
			.getLogger(KeywordAction.class);

	private static final long serialVersionUID = 1L;

	/* Spring injected */
	private KeywordService keywordService;

	/* Input parameter. */
	private Keyword keyword;

	/* Input parameter. */
	private int actId;

	/* Input parameter. */
	private int paraId;

	/* Input parameter. */
	private int amendmentId;

	/* Output parameter in response to Load All Acts. */
	private List<Keyword> list;

	/* Output parameter in response to Add New Keyword. */
	private int status;

	private List<TagItem> tagItems;

	public KeywordAction() {

	}

	public String getKeywords() {
		setList(getKeywordService().filterKeywords(getActId(), getParaId(),
				getAmendmentId()));
		return SUCCESS;
	}

	public String addKeyword() {
		int keywordId = getKeywordService().addNewKeyword(getKeyword());
		setStatus(keywordId);
		setKeyword(null);
		return SUCCESS;
	}

	public String deleteKeyword() {
		setStatus(getKeywordService().deleteKeyworText(getTagItems()));
		setTagItems(null);
		return SUCCESS;
	}

	public KeywordService getKeywordService() {
		return keywordService;
	}

	public void setKeywordService(KeywordService keywordService) {
		this.keywordService = keywordService;
	}

	public Keyword getKeyword() {
		return keyword;
	}

	public void setKeyword(Keyword keyword) {

		this.keyword = keyword;
	}

	public int getParaId() {
		return paraId;
	}

	public void setParaId(int paraId) {
		this.paraId = paraId;
	}

	public int getAmendmentId() {
		return amendmentId;
	}

	public void setAmendmentId(int amendmentId) {
		this.amendmentId = amendmentId;
	}

	public List<Keyword> getList() {
		return list;
	}

	public void setList(List<Keyword> list) {
		this.list = list;
		if (list == null || list.isEmpty()) {
			logger.info("KeywordAction: List is null.");
		}
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getActId() {
		return actId;
	}

	public void setActId(int actId) {
		this.actId = actId;
	}

	public List<TagItem> getTagItems() {
		return tagItems;
	}

	public void setTagItems(List<TagItem> tagItems) {
		this.tagItems = tagItems;
	}

}
