//package com.nnk.poseidon;
//
//import java.util.List;
//import java.util.Optional;
//
//import org.assertj.core.api.Assert;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import com.nnk.springboot.domains.BidList;
//import com.nnk.springboot.repositories.BidListRepository;
//
//@SpringBootTest
//public class BidTests {
//
//	@Autowired
//	private BidListRepository bidListRepository;
//
//	@Test
//	public void bidListTest() {
//		BidList bid = new BidList("Account Test", "Type Test", 10d);
//
//		// Save
//		bid = bidListRepository.save(bid);
//		Assert.assertNotNull(bid.getBidListId());
//		Assert.assertEquals(bid.getBidQuantity(), 10d, 10d);
//
//		// Update
//		bid.setBidQuantity(20d);
//		bid = bidListRepository.save(bid);
//		Assert.assertEquals(bid.getBidQuantity(), 20d, 20d);
//
//		// Find
//		List<BidList> listResult = bidListRepository.findAll();
//		Assert.assertTrue(listResult.size() > 0);
//
//		// Delete
//		Integer id = bid.getBidListId();
//		bidListRepository.delete(bid);
//		Optional<BidList> bidList = bidListRepository.findById(id);
//		Assert.assertFalse(bidList.isPresent());
//	}
//}