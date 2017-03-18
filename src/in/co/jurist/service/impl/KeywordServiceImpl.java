package in.co.jurist.service.impl;

import in.co.jurist.dao.KeywordDao;
import in.co.jurist.model.Keyword;
import in.co.jurist.model.TagItem;
import in.co.jurist.service.KeywordService;
import in.co.jurist.util.Constants;
import in.co.jurist.util.Constants.Status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KeywordServiceImpl implements KeywordService {

	private static final Logger logger = LoggerFactory
			.getLogger(KeywordServiceImpl.class);

	// Spring injected
	private KeywordDao keywordDao;

	private List<Keyword> list;

	public KeywordServiceImpl() {
		// logger.info("ActService constructor called!");
	}

	@PostConstruct
	private void init() {
		setList(getKeywordDao().loadAll());
	}

	@Override
	public List<Keyword> filterKeywords(int actId, int paraId, int amendmentId) {
		List<Keyword> keywords = null;
		if (getList() != null && !getList().isEmpty()) {
			keywords = new ArrayList<Keyword>();
			if (amendmentId != 0) {
				for (Keyword keyword : getList()) {
					if (amendmentId == keyword.getAmendmentId()) {
						logger.info("AmendmentId: " + amendmentId
								+ " keyword: " + keyword);
						keywords.add(keyword);
					}
				}
				return keywords;
			} else if (paraId != 0) {
				for (Keyword keyword : getList()) {
					if (paraId == keyword.getParaId()) {
						logger.info("ParaId: " + paraId + " keyword: "
								+ keyword);
						keywords.add(keyword);
					}
				}
				return keywords;
			} else if (actId != 0) {
				for (Keyword keyword : getList()) {
					if (actId == keyword.getActId() && keyword.getParaId() == 0
							&& keyword.getAmendmentId() == 0) {
						logger.info("ActId: " + actId + " keyword: " + keyword);
						keywords.add(keyword);
					}
				}
				return keywords;
			}
		}
		return keywords;
	}

	@Override
	public int addNewKeyword(Keyword keyword) {
		if (!isKeywordExist(keyword)) {
			int newTagId = getKeywordDao().createKeyword(keyword);
			if (newTagId == Status.INITIAL.getStatus()) {
				logger.info("Unable to add {}", keyword.toString());
			} else {
				keyword.setId(newTagId);
				if (this.list == null) {
					this.list = new ArrayList<Keyword>();
				}
				getList().add(keyword);
			}
			return newTagId;
		} else {
			logger.info("Sibling Keyword alreay exists. Append {}",
					keyword.toString());
			if (getList() != null && !getList().isEmpty()) {
				for (Keyword k : getList()) {
					/* Traverse till Matched Keyword. */
					if (k.getActId() == keyword.getActId()
							&& k.getParaId() == keyword.getParaId()
							&& k.getAmendmentId() == keyword.getAmendmentId()) {

						String textToAppend = null;
						if (!k.getText().trim().isEmpty()) {
							for (String token : keyword.getText().trim()
									.split(Constants.COMMA_DELIMITER)) {
								if (!k.getText().contains(token)) {
									if (textToAppend != null && !textToAppend.isEmpty()) {
										textToAppend += Constants.COMMA_DELIMITER;
									}
									if(textToAppend == null) {
										textToAppend = "";
									}
									textToAppend += token;
								}
							}
							if (textToAppend == null || textToAppend.isEmpty()) {
								return Status.RECORD_EXISTS.getStatus();
							}
							textToAppend = k.getText().trim()
									+ Constants.COMMA_DELIMITER + textToAppend;
							keyword.setText(textToAppend);
						}
						int status = getKeywordDao().addKeywordText(keyword);
						if (status == Status.SUCCESS.getStatus()) {
							k.setText(keyword.getText());
							return k.getId();
						} else {
							logger.info("Unable to update {}",
									keyword.toString());
						}
					}
				}
			}
			return Status.INITIAL.getStatus();
		}
	}

	@Override
	public int deleteKeyworText(List<TagItem> tagItems) {
		if (tagItems == null || tagItems.isEmpty() || getList() == null
				|| getList().isEmpty()) {
			return Status.INPUT_MISSING.getStatus();
		}
		int id = tagItems.get(0).getId();
		for (Keyword keyword : getList()) {
			if (keyword.getId() == id) {
				for (TagItem tagItem : tagItems) {
					for (String token : keyword.getText().split(
							Constants.COMMA_DELIMITER)) {
						if (token.trim().equalsIgnoreCase(
								tagItem.getTag().trim())) {
							String tokenToDelete = token;
							if (keyword.getText().length() > token.length()) {
								int commaPosition = keyword.getText().indexOf(
										token)
										+ token.length();
								Character ch = keyword.getText().charAt(
										commaPosition);
								if (ch.toString().equalsIgnoreCase(
										Constants.COMMA_DELIMITER)) {
									tokenToDelete += Constants.COMMA_DELIMITER;
								}
							}
							keyword.setText(keyword.getText().replace(
									tokenToDelete, Constants.BLANK_STRING));
							break;
						}
					}
				}
				return getKeywordDao().deleteKeyworText(id, keyword.getText());
			}
		}
		return Status.INPUT_MISSING.getStatus();
	}

	public KeywordDao getKeywordDao() {
		return keywordDao;
	}

	public void setKeywordDao(KeywordDao keywordDao) {
		this.keywordDao = keywordDao;
	}

	public List<Keyword> getList() {
		return list;
	}

	public void setList(List<Keyword> list) {
		this.list = list;
		if (list != null && !list.isEmpty()) {
			/* Remove in deployment. */
			for (Keyword keyword : getList()) {
				logger.info(keyword.toString());
			}
		} else {
			logger.info("KeywordService: List is null.");
		}
	}

	@Override
	public List<Keyword> getAll() {
		return getList();
	}

	@Override
	public List<Integer> getResultKeywordMapping(String text) {
		List<Integer> resultMapping = null;
		List<String> keywordTokenList = getKeywordDao()
				.getResultKeywordMapping(text);
		if (keywordTokenList != null && !keywordTokenList.isEmpty()) {
			resultMapping = new ArrayList<Integer>();
			for (String keywordTokens : keywordTokenList) {
				String[] keywordToken = keywordTokens
						.split(Constants.COMMA_DELIMITER);
				for (String str : Arrays.asList(keywordToken)) {
					resultMapping.add(Integer.parseInt(str));
				}
			}
		}
		return resultMapping;
	}

	@Override
	public void addResultKeywordMapping(List<Integer> keywordIdList, String text) {
		String keywordToken = "";
		for (int id : keywordIdList) {
			keywordToken += Integer.toString(id) + Constants.COMMA_DELIMITER;
		}
		getKeywordDao().addResultKeywordMapping(keywordToken, text);
	}

	@Override
	public boolean isKeywordExist(Keyword keyword) {
		if (getList() != null && !getList().isEmpty()) {
			for (Keyword k : getList()) {
				if (k.getActId() == keyword.getActId()
						&& k.getParaId() == keyword.getParaId()
						&& k.getAmendmentId() == keyword.getAmendmentId()) {
					return true;
				}
			}
		}
		return false;
	}

}
