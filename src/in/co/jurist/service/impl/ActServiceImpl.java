package in.co.jurist.service.impl;

import in.co.jurist.dao.ActDao;
import in.co.jurist.model.Act;
import in.co.jurist.service.ActService;
import in.co.jurist.util.Constants.Status;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActServiceImpl implements ActService {

	private static final Logger logger = LoggerFactory
			.getLogger(ActServiceImpl.class);

	// Spring injected
	private ActDao actDao;

	private List<Act> list;

	public ActServiceImpl() {
		// logger.info("ActService constructor called!");
	}

	@PostConstruct
	private void init() {
		// logger.info("Act Post construct called.");
		setList(getActDao().loadAll());
	}

	@Override
	public int addNewAct(Act act) {
		if (!isActExists(act)) {
			int newActId = getActDao().createAct(act);
			if (newActId == Status.INITIAL.getStatus()) {
				logger.info("Unable to add {}", act.toString());
			} else {
				act.setId(newActId);
				if (this.list == null) {
					this.list = new ArrayList<Act>();
				}
				getList().add(act);
			}
			return newActId;
		} else {
			logger.info("{} alreay exists.", act.toString());
			return Status.RECORD_EXISTS.getStatus();
		}
	}

	@Override
	public boolean isActExists(Act act) {
		NameCompare nameCompare = new NameCompare();
		NumberCompare numberCompare = new NumberCompare();

		if (getList() != null && !getList().isEmpty()) {
			for (Act a : getList()) {
				if (nameCompare.compare(a, act) == 0
						|| numberCompare.compare(a, act) == 0) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean isActExists(int actId) {
		if (getList() != null && !getList().isEmpty()) {
			for (Act a : getList()) {
				if (a.getId() == actId) {
					return true;
				}
			}
		}
		return false;
	}

	public ActDao getActDao() {
		return actDao;
	}

	public void setActDao(ActDao actDao) {
		this.actDao = actDao;
	}

	public List<Act> getList() {
		return list;
	}

	public void setList(List<Act> list) {
		this.list = list;
		if (list != null && !list.isEmpty()) {
			/* Remove in deployment. */
			for (Act act : getList()) {
				logger.info(act.toString());
			}
		} else {
			logger.info("ActService: List is null.");
		}
	}

	@Override
	public Act getAct(int actId) {
		if (getList() != null && !getList().isEmpty()) {

			for (Act act : getList()) {
				if (actId == act.getId()) {
					return act;
				}
			}
		}
		return null;
	}

	@Override
	public String getActName(int actId) {
		Act act = getAct(actId);
		if (act != null) {
			return act.getName();
		}
		return null;
	}

	@Override
	public List<Act> getAllActs() {
		return getList();
	}

	class NameCompare implements Comparator<Act> {
		@Override
		public int compare(Act a1, Act a2) {
			return a1.getName().trim().compareToIgnoreCase(a2.getName().trim());
		}
	}

	class NumberCompare implements Comparator<Act> {
		@Override
		public int compare(Act a1, Act a2) {
			return a1.getNumber().compareToIgnoreCase(a2.getNumber());
		}
	}

	@Override
	public int updateActInfo(Act act) {
		if (isActExists(act.getId())) {
			int status = getActDao().updateActInfo(act);
			if (status == Status.SUCCESS.getStatus()) {
				/* Update Act in Loaded list. */
				for (Act a : getList()) {
					if (a.getId() == act.getId()) {
						a.setName(act.getName());
						a.setNumber(act.getNumber());
						a.setDescription(act.getDescription());
						a.setAnnouncedDate(act.getAnnouncedDate());
					}
				}
			} else {
				logger.info("Unable to update {}", act.toString());
			}
			return status;
		} else {
			logger.info("{} does not exists.", act.toString());
			return Status.NO_SUCH_RECORD.getStatus();
		}
	}

}
