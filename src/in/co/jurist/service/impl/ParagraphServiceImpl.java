package in.co.jurist.service.impl;

import in.co.jurist.dao.ParagraphDao;
import in.co.jurist.model.Paragraph;
import in.co.jurist.service.ParagraphService;
import in.co.jurist.util.Constants.Status;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParagraphServiceImpl implements ParagraphService {

	private static final Logger logger = LoggerFactory
			.getLogger(ParagraphServiceImpl.class);

	private ParagraphDao paragraphDao;

	private List<Paragraph> list;

	public ParagraphServiceImpl() {
		// logger.info("ParagraphService constructor called!");
	}

	@PostConstruct
	private void init() {
		// logger.info("Paragraph Post construct called.");
		setList(getParagraphDao().loadParagraphHeaders());
	}

	@Override
	public List<Paragraph> filterParagraphHeaders(int actId) {
		List<Paragraph> localList = null;
		if (getList() != null && !getList().isEmpty()) {
			localList = new ArrayList<Paragraph>();
			for (Paragraph para : getList()) {
				if (actId == para.getActId()) {
					localList.add(para);
				}
			}
		}
		return localList;
	}

	@Override
	public int addNewParagraph(Paragraph paragraph) {
		if (!isParagraphExists(paragraph)) {
			int newSectionId = getParagraphDao().createParagraph(paragraph);
			if (newSectionId == Status.INITIAL.getStatus()) {
				logger.info("Unable to add {}", paragraph.toString());
			} else {
				paragraph.setId(newSectionId);
				if (this.list == null) {
					this.list = new ArrayList<Paragraph>();
				}
				getList().add(paragraph);
			}
			return newSectionId;
		} else {
			logger.info("{} alreay exists.", paragraph.toString());
			return Status.RECORD_EXISTS.getStatus();
		}
	}

	@Override
	public boolean isParagraphExists(Paragraph paragraph) {
		if (getList() != null && !getList().isEmpty()) {
			logger.info("Header to match:" + paragraph.getHeader());
			logger.info("Act ID to match:" + paragraph.getActId());
			for (Paragraph para : getList()) {
				logger.info(para.getHeader() + ":" + para.getActId());
				if (para.getHeader().equalsIgnoreCase(paragraph.getHeader())
						&& para.getActId() == paragraph.getActId()) {
					return true;
				}
			}
		}
		return false;
	}

	public ParagraphDao getParagraphDao() {
		return paragraphDao;
	}

	public void setParagraphDao(ParagraphDao paragraphDao) {
		this.paragraphDao = paragraphDao;
	}

	public List<Paragraph> getList() {
		return list;
	}

	public void setList(List<Paragraph> list) {
		this.list = list;
		if (list != null && !list.isEmpty()) {
			/* Remove in deployment. */
			for (Paragraph para : this.list) {
				logger.info(para.toString());
			}
		} else {
			logger.info("ParagraphService: List is null.");
		}
	}

	@Override
	public Paragraph getParagraph(int paraId) {
		if (getList() != null && !getList().isEmpty()) {
			for (Paragraph para : getList()) {
				if (para.getId() == paraId) {
					Paragraph p = getParagraphDao().get(paraId);
					para.setAbstractText(p.getAbstractText());
					para.setFullText(p.getFullText());
					return para;
				}
			}
		}
		return null;
	}

	@Override
	public int getParagraphCount(int actId) {
		int count = 0;
		if (getList() != null && !getList().isEmpty()) {
			for (Paragraph para : getList()) {
				if (actId == para.getActId()) {
					count++;
				}
			}
		}
		return count;
	}

	@Override
	public int updateParagraphContent(Paragraph para) {
		if (para.getId() <= 0 || para.getFullText() == null
				|| para.getFullText().isEmpty()) {
			return Status.INPUT_MISSING.getStatus();
		}
		return getParagraphDao().updateParagraphContent(para);

	}

	@Override
	public int updateTranslation(Paragraph para) {
		if (para.getId() <= 0 || para.getAbstractText() == null
				|| para.getAbstractText().isEmpty()) {
			return Status.INPUT_MISSING.getStatus();
		}
		return getParagraphDao().updateTranslation(para);
	}

}
