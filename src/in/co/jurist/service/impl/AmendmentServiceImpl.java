package in.co.jurist.service.impl;

import in.co.jurist.dao.AmendmentDao;
import in.co.jurist.model.Amendment;
import in.co.jurist.service.AmendmentService;
import in.co.jurist.util.Constants.Status;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AmendmentServiceImpl implements AmendmentService {

	private static final Logger logger = LoggerFactory
			.getLogger(AmendmentServiceImpl.class);

	private AmendmentDao amendmentDao;

	private List<Amendment> list;

	public AmendmentServiceImpl() {
		// logger.info("AmendmentService constructor called!");
	}

	@PostConstruct
	private void init() {
		// logger.info("Amendment Post construct called.");
		setList(getAmendmentDao().loadAll());
	}

	@Override
	public List<Amendment> filterAmendments(int paraId) {
		List<Amendment> localList = null;
		if (getList() != null && !getList().isEmpty()) {
			localList = new ArrayList<Amendment>();
			for (Amendment amendment : getList()) {
				if (paraId == amendment.getParagraphId()) {
					localList.add(amendment);
				}
			}
		}
		return localList;
	}

	@Override
	public int addNewAmendment(Amendment amendment) {
		int status = 0;
		if (!isAmendmentExists(amendment)) {
			status = getAmendmentDao().createAmendment(amendment);
			if (status == Status.SUCCESS.getStatus()) {
				addToPreloadedList(amendment);
			} else {
				logger.info("Unable to add Amendment.");
			}
		} else {
			logger.info("Amendment: " + amendment.getHeader()
					+ " alreay exists.");
			status = Status.RECORD_EXISTS.getStatus();
		}
		return status;
	}

	private void addToPreloadedList(Amendment amendment) {
		if (this.list == null) {
			this.list = new ArrayList<Amendment>();
		}
		Amendment a = new Amendment();
		a.setId(getList().size() + 1);
		a.setHeader(amendment.getHeader());
		a.setAbstractText(amendment.getAbstractText());
		a.setFullText(amendment.getFullText());
		a.setAnnouncedDate(amendment.getAnnouncedDate());
		a.setAnnouncedDateString(amendment.getAnnouncedDateString());
		a.setParagraphId(amendment.getParagraphId());

		getList().add(a);
	}

	@Override
	public boolean isAmendmentExists(Amendment amendment) {
		if (getList() != null && !getList().isEmpty()) {
			for (Amendment amend : getList()) {
				if (amend.getHeader().equalsIgnoreCase(amendment.getHeader())) {
					if (amend.getParagraphId() == amendment.getParagraphId()) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public List<Amendment> getList() {
		return list;
	}

	public void setList(List<Amendment> list) {
		this.list = list;
		if (list != null && !list.isEmpty()) {
			/* Remove in deployment. */
			for (Amendment amendment : this.list) {
				logger.info(amendment.toString());
			}
		} else {
			logger.info("AmendmentService: List is null.");
		}
	}

	public AmendmentDao getAmendmentDao() {
		return amendmentDao;
	}

	public void setAmendmentDao(AmendmentDao amendmentDao) {
		this.amendmentDao = amendmentDao;
	}

	@Override
	public Amendment getAmendment(int amendmentId) {
		if (getList() != null && !getList().isEmpty()) {
			for (Amendment amendment : getList()) {
				if (amendmentId == amendment.getId()) {

					Amendment a = getAmendmentDao().get(amendmentId);
					amendment.setAbstractText(a.getAbstractText());
					amendment.setFullText(a.getFullText());
					return amendment;
				}
			}
		}
		return null;
	}

	@Override
	public int getAmendmentCount(int paraId) {
		// TODO Auto-generated method stub
		return 0;
	}

}
