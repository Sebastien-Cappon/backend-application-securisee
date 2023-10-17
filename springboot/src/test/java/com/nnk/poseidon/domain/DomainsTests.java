package com.nnk.poseidon.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import com.nnk.poseidon.util.DomainObjectBuilders;

public class DomainsTests {

	private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private Timestamp timeStampTest = Timestamp
			.valueOf(LocalDate.parse("2023-10-02", dateTimeFormatter).atStartOfDay());

	@Test
	public void bidListToString_isNotBlank() {
		Bid bidList = DomainObjectBuilders.createBid(1, "Account", "Type", 1d, 2d, 3d, 4d, "Benchmark",
				timeStampTest, "Commentary", "Security", "Status", "Trader", "Book", "Creation name", timeStampTest,
				"Revision name", timeStampTest, "Deal name", "Deal type", "Source list id", "Side");
		assertThat(bidList.toString()).isNotBlank();
	}

	@Test
	public void curvePointToString_isNotBlank() {
		CurvePoint curvePoint = DomainObjectBuilders.createCurvePoint(1, 2, timeStampTest, 1d, 2d, timeStampTest);
		assertThat(curvePoint.toString()).isNotBlank();
	}

	@Test
	public void ratingToString_isNotBlank() {
		Rating rating = DomainObjectBuilders.createRating(1, "MoodysRating", "SandPRating", "FitchRating", 2);
		assertThat(rating.toString()).isNotBlank();
	}

	@Test
	public void ruleNameToString_isNotBlank() {
		RuleName ruleName = DomainObjectBuilders.createRuleName(1, "Name", "Description", "Json", "Template", "SqlStr", "SqlPart");
		assertThat(ruleName.toString()).isNotBlank();
	}

	@Test
	public void tradeToString_isNotBlank() {
		Trade trade = DomainObjectBuilders.createTrade(1, "Account", "Type", 1d, 2d, 3d, 4d, "Benchmark", timeStampTest,
				"Security", "Status", "Trader", "Book", "CreationName", timeStampTest, "RevisionName", timeStampTest,
				"DealName", "DealType", "SourceListId", "Side");
		assertThat(trade.toString()).isNotBlank();
	}
}