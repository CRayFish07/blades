package com.iusofts.blades.sys.service;

import com.iusofts.blades.sys.model.Dictionary;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.iusofts.blades.sys.common.util.Assert.assertNotNull;

public class DictionaryManagerTest extends BaseTests {

	@Autowired
	private DictionaryService dictionaryService;
	
	@Test
	public void testSave() {
	}

	@Test
	public void testRemove() {
	}

	@Test
	public void testUpdate() {
	}

	@Test
	public void testGet() {
		Dictionary d=this.dictionaryService.get("SDDHAHC4-DWA8LJ3GMP8V62JUJY0F2-ZLYOM1LI-0");
		assertNotNull(d);
	}

	@Test
	public void testGetList() {
	}

	@Test
	public void testGetListPage() {
	}

}
