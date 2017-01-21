package com.lupis.prj;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class SalesTaxesService {

	private static List<String> basicSaletaxFreeList;

	static {
		/*
		 * TODO: list of tax free keywords should be located into an external file
		 * and loaded at the the startup
		 */
		basicSaletaxFreeList = new ArrayList<String>();
		basicSaletaxFreeList.add(".*book.*");
		basicSaletaxFreeList.add(".*headache\\s*pills.*");
		basicSaletaxFreeList.add(".*chocolate.*");
	}

	/**
	 * Starting from the list of good reported within the file located in <code>pathInputFile</code>
	 *  a receipt is produced within the output file located at the path
	 * defined in  <code>pathOutputFile</code>
	 * 
	 * @param pathInputFile
	 * @param pathOutputFile
	 */
	public void calculateReceipt(String pathInputFile, String pathOutputFile) {
		List<Good> basket = parseInput(pathInputFile);
		StringBuilder receipt = produceReceipt(basket);
		writeOutput(pathOutputFile, receipt);
	}

	/**
	 * Parses the file located in the path passed as input parameter and
	 * produces the corresponding list of goods
	 * 
	 * In order to produce a valid <code>Good</code> object, each line of the
	 * file has to follow the following pattern:
	 * <code>"[0-9]* [a-zA-z ]* at [0-9. ]*"</code>
	 * 
	 * @param pathInputFile
	 * @return list of goods
	 */
	private List<Good> parseInput(String pathInputFile) {
		List<Good> basket = new ArrayList<Good>();

		Path path = Paths.get(pathInputFile);
		try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				if (line.matches(SalesTaxesConstants.INPUT_REGEX)) {

					// Extracting quantity
					Matcher m1 = SalesTaxesConstants.INPUT_PATTERN_PART_1.matcher(line);
					m1.find();
					int quantityEnd = m1.end();
					String quantityRaw = line.substring(m1.start(), quantityEnd);
					int quantity = Integer.parseInt(quantityRaw.trim());

					// Extracting price
					Matcher m3 = SalesTaxesConstants.INPUT_PATTERN_PART_3.matcher(line);
					m3.find();
					int priceStart = m3.start();
					String priceRaw = line.substring(priceStart).replaceAll(SalesTaxesConstants.CLEAN_PRICE_REGEX, "");
					BigDecimal price = BigDecimal.valueOf(Double.parseDouble(priceRaw.trim()));

					// Extracting description
					String descriptionRaw = line.substring(quantityEnd, priceStart);

					// Defining if it's an imported good
					boolean imported = descriptionRaw.toLowerCase().contains(SalesTaxesConstants.IMPORTED);
					String description = descriptionRaw.replace(SalesTaxesConstants.IMPORTED, "").trim();

					// Defining if it's a tax-free good
					boolean taxFree = isTaxFree(line);

					Good good = new Good(description, price, quantity, imported, taxFree);
					basket.add(good);
				}
			}
		} catch (IOException e) {
			System.err.println("Error reading file");
		}

		return basket;
	}

	/**
	 * Defines if the good is tax free, checking if description contains at
	 * least one of the keywords for recognizing a tax free good
	 * 
	 * @param description
	 * @return true if good is tax free, false otherwise
	 */
	private static boolean isTaxFree(String description) {
		for (String item : basicSaletaxFreeList) {
			if (description.matches(item)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Produces a receipt starting from the list of goods contained by the basket.
	 * Receipt contains:
	 * - list of purchased goods
	 * - taxed price for each good
	 * - total amount of taxes 
	 * - total amount to pay
	 * 
	 * @param basket
	 * @return receipt well formatted
	 */
	private StringBuilder produceReceipt(List<Good> basket) {
		StringBuilder receipt = new StringBuilder();
		BigDecimal totalAmount = BigDecimal.ZERO;
		BigDecimal taxAmount = BigDecimal.ZERO;
		for (Good good : basket) {
			BigDecimal taxOnGood = calculateTaxAmount(good.getPrice(), good.isImported(), good.isBaseTaxFree());
			BigDecimal taxedPrice = good.getPrice().add(taxOnGood);
			totalAmount = totalAmount.add(taxedPrice);
			taxAmount = taxAmount.add(taxOnGood);
			receipt.append(printGood(good, taxedPrice)).append("\r\n");
		}
		receipt.append("Sales Taxes: ").append(roundAmount(taxAmount).setScale(2, RoundingMode.DOWN)).append("\r\n");
		receipt.append("Total: ").append(totalAmount.setScale(2, RoundingMode.DOWN));

		return receipt;
	}

	/**
	 * Utility method for writing content into the file located at the path passed as input parameter
	 * 
	 * @param pathOutputFile
	 * @param content
	 */
	private static void writeOutput(String pathOutputFile, StringBuilder content) {
		Path path = Paths.get(pathOutputFile);
		try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
			writer.write(content.toString());
		} catch (IOException e) {
			System.err.println("Error writing file");
		}
	}

	/**
	 * Utility method for rounding amount to the nearest 0.05 cent
	 * 
	 * @param amount to be rounded
	 * @return rounded amount
	 */
	private static BigDecimal roundAmount(BigDecimal amount) {
		BigDecimal roundedAmount = amount.divide(SalesTaxesConstants.ROUND, 0, RoundingMode.UP)
				.multiply(SalesTaxesConstants.ROUND);
		return roundedAmount;
	}

	/**
	 * Utility method for calculating tax amount.
	 * 
	 * @param price
	 * @param imported
	 * @param baseTaxFree
	 * @return tax amount
	 */
	private static BigDecimal calculateTaxAmount(BigDecimal price, boolean imported, boolean baseTaxFree){
		BigDecimal taxAmount = BigDecimal.ZERO;
		if(imported){
			BigDecimal tax = price.multiply(SalesTaxesConstants.IMPORTED_GOOD_TAX_RATE);
			tax = roundAmount(tax);
			taxAmount = taxAmount.add(tax);
			taxAmount = roundAmount(taxAmount);
		}
		if(baseTaxFree){
			BigDecimal tax = price.multiply(SalesTaxesConstants.BASIC_SALE_TAX_RATE);
			tax = roundAmount(tax);
			taxAmount = taxAmount.add(tax);
			taxAmount = roundAmount(taxAmount);
		}
		
		return roundAmount(taxAmount).setScale(2, RoundingMode.DOWN);
	}
	
	/**
	 * Utility method for printing out goods well formatted
	 * 
	 * @param good
	 * @param taxedPrice
	 * @return well formatted good
	 */
	private static StringBuilder printGood(Good good, BigDecimal taxedPrice){
		StringBuilder sb = new StringBuilder();

		sb.append(good.getQuantity()).append(" ");
		if(good.isImported()){
			sb.append("imported ");
		}
		sb.append(good.getDescription()).append(": ").append(taxedPrice);

		return sb;
	}

}
