package com.demo;

import org.springframework.batch.item.ItemProcessor;

import com.demo.model.DbModel;

public class CustomItemProcessor implements ItemProcessor<DbModel, DbModel>{

	@Override
	public DbModel process(DbModel item) throws Exception {
		
		return item;
	}

}
