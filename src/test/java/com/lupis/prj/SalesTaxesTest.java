package com.lupis.prj;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;

public class SalesTaxesTest
{
	
	@Test
    public void testInput1(){
		String pathInputFile1 = "src/test/resources/input1.txt";
		String pathOutputFile1 = "src/test/resources/output1.txt";
		String pathTargetOutputFile = "src/test/resources/targetOutput1.txt";
		SalesTaxesService service = new SalesTaxesService();
		service.calculateReceipt(pathInputFile1, pathOutputFile1);
		boolean result = checkOutput(pathTargetOutputFile, pathOutputFile1);
    	assertTrue(result);
    }

	@Test
    public void testInput2(){
		String pathInputFile2 = "src/test/resources/input2.txt";
		String pathOutputFile2 = "src/test/resources/output2.txt";
		String pathTargetOutputFile = "src/test/resources/targetOutput2.txt";
		SalesTaxesService service = new SalesTaxesService();
		service.calculateReceipt(pathInputFile2, pathOutputFile2);
		boolean result = checkOutput(pathTargetOutputFile, pathOutputFile2);
    	assertTrue(result);
    }
	
	@Test
    public void testInput3(){
		String pathInputFile3 = "src/test/resources/input3.txt";
		String pathOutputFile3 = "src/test/resources/output3.txt";
		String pathTargetOutputFile = "src/test/resources/targetOutput3.txt";
		SalesTaxesService service = new SalesTaxesService();
		service.calculateReceipt(pathInputFile3, pathOutputFile3);
		boolean result = checkOutput(pathTargetOutputFile, pathOutputFile3);
    	assertTrue(result);
    }
	
	@Test
    public void testInputMario(){
		String pathInputFile1 = "src/test/resources/inputMario.txt";
		String pathOutputFile1 = "src/test/resources/outputMario.txt";
		String pathTargetOutputFile = "src/test/resources/targetOutputMario.txt";
		SalesTaxesService service = new SalesTaxesService();
		service.calculateReceipt(pathInputFile1, pathOutputFile1);
		boolean result = checkOutput(pathTargetOutputFile, pathOutputFile1);
    	assertTrue(result);
    }
	
	/**
	 * Utility method for checking actual output against the expected one.
	 * Spaces are not considered in the comparison
	 * 
	 * @param pathTargetOutputFile
	 * @param pathOutputFile
	 * @return true if the files content match
	 */
	private boolean checkOutput(String pathTargetOutputFile, String pathOutputFile) {
		boolean sameContent = false;
		try {
			Path targetPath = Paths.get(pathTargetOutputFile);
			Path outputPath = Paths.get(pathOutputFile);
			List<String> target = Files.readAllLines(targetPath,Charset.defaultCharset());
			List<String> output = Files.readAllLines(outputPath, Charset.defaultCharset());
		
			if(output.size() == target.size()){
				for(int i=0; i<output.size(); i++){
					String cleanedStringOutput = output.get(i).replaceAll("\\s", "");
					String cleanedStringTarget = target.get(i).replaceAll("\\s", "");
					if(cleanedStringOutput.equalsIgnoreCase(cleanedStringTarget)){
						sameContent = true;
					} else {
						sameContent = false;
						break;
					}
				}
			} 
		} catch (IOException e) {
			System.err.println("Error reading file");
			e.printStackTrace();
		}
		return sameContent;
	}
	
}
