package in.co.jurist.service.impl;

import in.co.jurist.model.Act;
import in.co.jurist.model.Amendment;
import in.co.jurist.model.Keyword;
import in.co.jurist.model.Paragraph;
import in.co.jurist.model.SearchResult;
import in.co.jurist.service.ActService;
import in.co.jurist.service.AmendmentService;
import in.co.jurist.service.KeywordService;
import in.co.jurist.service.ParagraphService;
import in.co.jurist.service.SearchService;
import in.co.jurist.util.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SearchServiceImpl implements SearchService {

	private static final Logger logger = LoggerFactory
			.getLogger(SearchServiceImpl.class);

	private KeywordService keywordService;

	private AmendmentService amendmentService;

	private ParagraphService paragraphService;

	private ActService actService;

	public ParagraphService getParagraphService() {
		return paragraphService;
	}

	public void setParagraphService(ParagraphService paragraphService) {
		this.paragraphService = paragraphService;
	}

	public ActService getActService() {
		return actService;
	}

	public void setActService(ActService actService) {
		this.actService = actService;
	}

	public SearchServiceImpl() {
		// logger.info("SearchServiceImpl constructor called!");
	}

	@Override
	public List<SearchResult> find(String text) {
		List<SearchResult> searchResultList = null;
		boolean flag = false;
		if (!text.isEmpty()) {
			Map<String, List<Keyword>> keywordMap = null;
			
			/* Get Keyword Text/Result mapping for previous similar searches. */
			List<Integer> mappingList = getResultMapping(text);
			if (mappingList != null && !mappingList.isEmpty()) {
				keywordMap = findKeywords(mappingList);
			}
			if (keywordMap == null) {
				keywordMap = findKeywords(text);
				flag = true;
				/*
				 * 1. Get ids from Keyword list from each KeywordMap.
				 * 
				 * 2. Put user text with id in result keyword mapping table.
				 * 
				 * 3. ResultsKeywordsMapping table will be used ideally to get
				 * results.
				 */
			}

			if (keywordMap != null) {

				List<Integer> keywordIdList = null;

				searchResultList = new ArrayList<SearchResult>();

				List<Keyword> exactMatchedkeywords = null;
				List<Keyword> lessMatchedkeywords = null;

				if (keywordMap.containsKey(Constants.EXACT_MATCHED)) {
					exactMatchedkeywords = new ArrayList<Keyword>();
					exactMatchedkeywords = keywordMap
							.get(Constants.EXACT_MATCHED);
				}
				if (exactMatchedkeywords != null
						&& !exactMatchedkeywords.isEmpty()) {
					for (Keyword keyword : exactMatchedkeywords) {

						if (flag) {
							if (keywordIdList == null) {
								keywordIdList = new ArrayList<Integer>();
							}
							keywordIdList.add(keyword.getId());
						}

						if (keyword.getAmendmentId() != 0) {
							extractAmendment(searchResultList, keyword);
						} else if (keyword.getParaId() != 0) {
							extractParagraph(searchResultList, keyword);
						} else if (keyword.getActId() != 0) {
							extractAct(searchResultList, keyword);
						}
					}
				}
				if (keywordMap.containsKey(Constants.LESS_MATCHED)) {
					lessMatchedkeywords = new ArrayList<Keyword>();
					lessMatchedkeywords = keywordMap
							.get(Constants.LESS_MATCHED);
				}
				if (lessMatchedkeywords != null
						&& !lessMatchedkeywords.isEmpty()) {
					for (Keyword keyword : lessMatchedkeywords) {

						if (flag) {
							if (keywordIdList == null) {
								keywordIdList = new ArrayList<Integer>();
							}
							keywordIdList.add(keyword.getId());
						}

						if (keyword.getAmendmentId() != 0) {
							extractAmendment(searchResultList, keyword);
						} else if (keyword.getParaId() != 0) {
							extractParagraph(searchResultList, keyword);
						} else if (keyword.getActId() != 0) {
							extractAct(searchResultList, keyword);
						}
					}
				}

				/* save result for next time use. */
				if (flag && keywordIdList != null && !keywordIdList.isEmpty()) {
					getKeywordService().addResultKeywordMapping(keywordIdList,
							text);
				}

			} else {
				logger.info("No keywords matched.");
				// search direct paragraph text logic
			}
		} else {
			logger.info("Search input is empty.");
		}
		return searchResultList;
	}

	private Map<String, List<Keyword>> findKeywords(List<Integer> keywordIdList) {

		Map<String, List<Keyword>> keywordMap = null;
		List<Keyword> matchedKeyword = null;

		List<Keyword> keywords = getKeywordService().getAll();
		if (!keywords.isEmpty()) {

			matchedKeyword = new ArrayList<Keyword>();

			for (Integer i : keywordIdList) {
				logger.info("keywords.get(" + i + "): "
						+ keywords.get(i - 1).toString());
				matchedKeyword.add(keywords.get(i - 1));
			}
			if (!matchedKeyword.isEmpty()) {
				keywordMap = new HashMap<String, List<Keyword>>();
				keywordMap.put(Constants.EXACT_MATCHED, matchedKeyword);
			}
		}
		return keywordMap;

	}

	private List<Integer> getResultMapping(String text) {
		return getKeywordService().getResultKeywordMapping(text);
	}

	private void extractAct(List<SearchResult> searchResultList, Keyword keyword) {

		Act act = getActService().getAct(keyword.getActId());

		SearchResult result = new SearchResult();
		result.setResultId(act.getId());
		result.setResultType(Constants.ResultType.ACT);
		result.setActName(act.getName());
		result.setTitle(act.getName());
		result.setText(act.getDescription());
		result.setAnnouncedDate(act.getAnnouncedDateString());
		result.setTotalParagraphs(getParagraphService().getParagraphCount(
				act.getId()));
		result.setTotalAmendments(0);

		searchResultList.add(result);
	}

	private void extractParagraph(List<SearchResult> searchResultList,
			Keyword keyword) {

		Paragraph para = getParagraphService()
				.getParagraph(keyword.getParaId());

		String actName = getActService().getActName(para.getActId());

		SearchResult result = new SearchResult();
		result.setResultId(para.getId());
		result.setTitle(para.getHeader());
		result.setText(para.getAbstractText());
		result.setResultType(Constants.ResultType.PARAGRAPH);
		result.setActName(actName);

		searchResultList.add(result);
	}

	private void extractAmendment(List<SearchResult> searchResultList,
			Keyword keyword) {
		Amendment amendment = getAmendmentService().getAmendment(
				keyword.getAmendmentId());

		Paragraph para = getParagraphService().getParagraph(
				amendment.getParagraphId());

		String actName = getActService().getActName(para.getActId());

		SearchResult result = new SearchResult();
		result.setResultId(amendment.getId());
		result.setTitle(amendment.getHeader());
		result.setText(amendment.getAbstractText());
		result.setResultType(Constants.ResultType.AMENDMENT);
		result.setActName(actName);

		searchResultList.add(result);
	}

	public Map<String, List<Keyword>> findKeywords(String text) {
		Map<String, List<Keyword>> keywordMap = null;
		List<Keyword> matchedKeyword = null;
		List<Keyword> lessMatchedKeyword = null;

		List<Keyword> keywords = getKeywordService().getAll();
		if (!keywords.isEmpty()) {

			matchedKeyword = new ArrayList<Keyword>();
			lessMatchedKeyword = new ArrayList<Keyword>();

			for (Keyword keyword : keywords) {
				/* Check exact match - as it is, in same order */
				if (keyword.getText().equalsIgnoreCase(text)) {
					logger.info("Exact Match=> " + keyword.getText() + ":"
							+ text);
					matchedKeyword.add(keyword);
					// copy same keyword into user_search_text_keyword
				} else {
					String[] q = text.trim().split(Constants.SPACE_DELIMITER);
					int totalMatchCount = 0;
					/* Check word match. */
					for (String str : q) {
						if (keyword.getText().contains(str)) {
							logger.info("Matching=> " + keyword.getText() + ":"
									+ str);
							totalMatchCount++;
						}
					}
					if (totalMatchCount != 0) {
						/* All text matched in random order. */
						if ((totalMatchCount / q.length) == Constants.PERFECT_MATCH) {
							logger.info("All Matched=> " + keyword.getText()
									+ ":" + text);
							matchedKeyword.add(keyword);
						}
						/* More than half of the text matched. */
						else if ((totalMatchCount % q.length) > (q.length / Constants.HALF_LENGTH)) {
							logger.info("Half Matched=> " + keyword.getText()
									+ ":" + text);
							matchedKeyword.add(keyword);
						}
						/* Less than half of the text matched. */
						else {
							logger.info("Partial Matched=> "
									+ keyword.getText() + ":" + text);
							lessMatchedKeyword.add(keyword);
						}
					}
				}
			}
			if (!matchedKeyword.isEmpty()) {
				keywordMap = new HashMap<String, List<Keyword>>();
				keywordMap.put(Constants.EXACT_MATCHED, matchedKeyword);

				for (Keyword k : matchedKeyword) {
					logger.info("Exact Match:" + k);
				}
			}
			if (!lessMatchedKeyword.isEmpty()) {
				if (keywordMap == null) {
					keywordMap = new HashMap<String, List<Keyword>>();
				}
				keywordMap.put(Constants.LESS_MATCHED, lessMatchedKeyword);
				for (Keyword k : lessMatchedKeyword) {
					logger.info("Less Match:" + k.getText());
				}
			}
		}
		return keywordMap;
	}

	public KeywordService getKeywordService() {
		return keywordService;
	}

	public void setKeywordService(KeywordService keywordService) {
		this.keywordService = keywordService;
	}

	public AmendmentService getAmendmentService() {
		return amendmentService;
	}

	public void setAmendmentService(AmendmentService amendmentService) {
		this.amendmentService = amendmentService;
	}
}
