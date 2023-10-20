package com.nnk.poseidon.util;

import java.sql.Timestamp;

import com.nnk.poseidon.domain.Bid;
import com.nnk.poseidon.domain.CurvePoint;
import com.nnk.poseidon.domain.Rating;
import com.nnk.poseidon.domain.RuleName;
import com.nnk.poseidon.domain.Trade;
import com.nnk.poseidon.domain.User;

public class DomainObjectBuilders {

	public static Bid createBid(Integer id, String account, String type, Double bidQuantity, Double askQuantity,
			Double bid, Double ask, String benchmark, Timestamp bidListDate, String commentary, String security,
			String status, String trader, String book, String creationName, Timestamp creationDate, String revisionName,
			Timestamp revisionDate, String dealName, String dealType, String sourceListId, String side) {
		Bid bidList = new Bid();
		bidList.setId(id);
		bidList.setAccount(account);
		bidList.setType(type);
		bidList.setBidQuantity(bidQuantity);
		bidList.setAskQuantity(askQuantity);
		bidList.setBid(bid);
		bidList.setAsk(ask);
		bidList.setBenchmark(benchmark);
		bidList.setBidListDate(bidListDate);
		bidList.setCommentary(commentary);
		bidList.setSecurity(security);
		bidList.setStatus(status);
		bidList.setTrader(trader);
		bidList.setBook(book);
		bidList.setCreationName(creationName);
		bidList.setCreationDate(creationDate);
		bidList.setRevisionName(revisionName);
		bidList.setRevisionDate(revisionDate);
		bidList.setDealName(dealName);
		bidList.setDealType(dealType);
		bidList.setSourceListId(sourceListId);
		bidList.setSide(side);

		return bidList;
	}

	public static CurvePoint createCurvePoint(Integer id, Integer curveId, Timestamp asOfDate, Double term,
			Double value, Timestamp creationDate) {
		CurvePoint curvePoint = new CurvePoint();
		curvePoint.setId(id);
		curvePoint.setCurveId(curveId);
		curvePoint.setAsOfDate(asOfDate);
		curvePoint.setTerm(term);
		curvePoint.setValue(value);
		curvePoint.setCreationDate(creationDate);

		return curvePoint;
	}

	public static Rating createRating(Integer id, String moodysRating, String sandPRating, String fitchRating,
			Integer orderNumber) {
		Rating rating = new Rating();
		rating.setId(id);
		rating.setMoodysRating(moodysRating);
		rating.setSandPRating(sandPRating);
		rating.setFitchRating(fitchRating);
		rating.setOrderNumber(orderNumber);

		return rating;
	}

	public static RuleName createRuleName(Integer id, String name, String description, String json, String template,
			String sqlStr, String sqlPart) {
		RuleName ruleName = new RuleName();
		ruleName.setId(id);
		ruleName.setName(name);
		ruleName.setDescription(description);
		ruleName.setJson(json);
		ruleName.setTemplate(template);
		ruleName.setSqlStr(sqlStr);
		ruleName.setSqlPart(sqlPart);

		return ruleName;
	}

	public static Trade createTrade(Integer tradeId, String account, String type, Double buyQuantity,
			Double sellQuantity, Double buyPrice, Double sellPrice, String benchmark, Timestamp tradeDate,
			String security, String status, String trader, String book, String creationName, Timestamp creationDate,
			String revisionName, Timestamp revisionDate, String dealName, String dealType, String sourceListId,
			String side) {
		Trade trade = new Trade();
		trade.setId(tradeId);
		trade.setAccount(account);
		trade.setType(type);
		trade.setBuyQuantity(buyQuantity);
		trade.setSellQuantity(sellQuantity);
		trade.setBuyPrice(buyPrice);
		trade.setSellPrice(sellPrice);
		trade.setBenchmark(benchmark);
		trade.setTradeDate(tradeDate);
		trade.setSecurity(security);
		trade.setStatus(status);
		trade.setTrader(trader);
		trade.setBook(book);
		trade.setCreationName(creationName);
		trade.setCreationDate(creationDate);
		trade.setRevisionName(revisionName);
		trade.setRevisionDate(revisionDate);
		trade.setDealName(dealName);
		trade.setDealType(dealType);
		trade.setSourceListId(sourceListId);
		trade.setSide(side);

		return trade;
	}

	public static User createUser(Integer id, String username, String password, String fullname, String role) {
		User user = new User();
		user.setId(id);
		user.setUsername(username);
		user.setPassword(password);
		user.setFullname(fullname);
		user.setRole(role);

		return user;
	}
}