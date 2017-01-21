package com.lupis.prj;

import java.math.BigDecimal;
import java.util.regex.Pattern;

interface SalesTaxesConstants {
	
	String INPUT_REGEX_PART_1 = "[0-9]* ";
	String INPUT_REGEX_PART_2 = "[a-zA-z ]*";
	String INPUT_REGEX_PART_3 = " at [0-9. ]*";
	String INPUT_REGEX = INPUT_REGEX_PART_1+INPUT_REGEX_PART_2+INPUT_REGEX_PART_3;

	Pattern INPUT_PATTERN_PART_1 = Pattern.compile(SalesTaxesConstants.INPUT_REGEX_PART_1);
	Pattern INPUT_PATTERN_PART_3 = Pattern.compile(SalesTaxesConstants.INPUT_REGEX_PART_3);
	
	String CLEAN_PRICE_REGEX = "[^0-9.]";
	String IMPORTED = "imported ";
	
	BigDecimal IMPORTED_GOOD_TAX_RATE = new BigDecimal(0.05);
	BigDecimal BASIC_SALE_TAX_RATE = new BigDecimal(0.1);
	
	BigDecimal ROUND = new BigDecimal(0.05);
}
