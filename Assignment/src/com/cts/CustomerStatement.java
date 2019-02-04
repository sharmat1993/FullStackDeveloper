package com.cts;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * This class will read, process and write XML and CSV files containing RABOBANK customer statement records.
 * @author Sharma
 *
 */
public class CustomerStatement {
	
	private final static Logger logger = Logger.getLogger(CustomerStatement.class.getName()); 

	public final static String COMMA_DELIMITER = ",";
	public final static String OUPUT_XML_FILE = "src/assignment/failedRecords.xml";
	public final static String INPUT_XML_FILE = "src/assignment/records.xml";
	public final static String INPUT_CSV_FILE = "src/assignment/records.csv";
	public final static String OUTPUT_CSV_FILE = "src/assignment/failedRecords.csv";
	public final static String CSV_FILE_HEADER = "Reference,Description";
	public final static String DECIMAL_FORMAT = ".##";
	public final static String SUCCESS_MESSAGE = "File records processed succesfully";

	/**
	 * Main method to handle the business operations.
	 * @param args
	 */
	public static void main(String[] args) {

		List<Record> failedRecords = new ArrayList<>();
		List<Record> failedXmlRecords = new ArrayList<>();

		readCsvFile(failedRecords);
		writeCsvFile(failedRecords);

		readXmlFile(failedXmlRecords);
		writeXmlFile(failedXmlRecords);
		logger.info(SUCCESS_MESSAGE);

	}

	/**
	 * This method will write the failed records of customer statement in XML format.
	 * @param failedXmlRecords
	 */
	private static void writeXmlFile(List<Record> failedXmlRecords) {
		
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Records.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			Records records = new Records();
			records.setRecords(failedXmlRecords);
			marshaller.marshal(records, new File(OUPUT_XML_FILE));
			logger.info("XML file is written successfully");
		} catch (JAXBException exception) {
			logger.info(exception.getMessage());
		}

	}

	/**
	 * This method will read the contents of XML file.
	 * @param failedXmlRecords
	 */
	private static void readXmlFile(List<Record> failedXmlRecords) {

		try {
			File xmlFile = new File(INPUT_XML_FILE);
			JAXBContext jaxbContext = JAXBContext.newInstance(Records.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			Records records = (Records) unmarshaller.unmarshal(xmlFile);

			processFileRecords(records.getRecords(), failedXmlRecords);
			logger.info("XML file is read successfully");
		} catch (JAXBException exception) {
			logger.info(exception.getMessage());
		}

	}

	/**
	 * This method will read the contents of CSV file. 
	 * @param failedRecords
	 */
	public static void readCsvFile(List<Record> failedRecords) {
		BufferedReader bufferedReader = null;
		try {
			String line;
			List<Record> originalRecords = new ArrayList<Record>();

			bufferedReader = new BufferedReader(new FileReader(INPUT_CSV_FILE));
			bufferedReader.readLine();
			Record record = null;
			String columns[] = null;
			while ((line = bufferedReader.readLine()) != null) {
				columns = line.split(COMMA_DELIMITER);
				record = new Record();
				record.setReference(Long.parseLong(columns[0]));
				record.setAccountNumber(columns[1]);
				record.setDescription(columns[2]);
				record.setStartBalance(Double.parseDouble(columns[3]));
				record.setMutation(Double.parseDouble(columns[4]));
				record.setEndBalance(Double.parseDouble(columns[5]));
				originalRecords.add(record);
			}
			processFileRecords(originalRecords, failedRecords);
			logger.info("CSV file is read successfully");
		} catch (FileNotFoundException exception) {
			logger.info(exception.getMessage());
		} catch (IOException e) {
			logger.info(e.getMessage());
		} finally {
			if(bufferedReader !=null) {
				try {
					bufferedReader.close();
				} catch (IOException exception) {
					logger.info(exception.getMessage());
				}
			}
		}
	}

	/**
	 * This method will process the file records of customer statement and populate the failed records.
	 * @param originalRecords
	 * @param failedRecords
	 */
	private static void processFileRecords(List<Record> originalRecords, List<Record> failedRecords) {
		DecimalFormat decimalFormat = new DecimalFormat(DECIMAL_FORMAT);
		List<Long> referenceNumbers = new ArrayList<Long>();
		double endBalance = 0;
		for (Record record : originalRecords) {
			endBalance = Double
					.parseDouble(decimalFormat.format(record.getStartBalance() + record.getMutation()));

			if (endBalance != record.getEndBalance()) {
				referenceNumbers.add(record.getReference());
				failedRecords.add(record);
			} else if (referenceNumbers.contains(record.getReference())) {
				failedRecords.add(record);
			} else {
				referenceNumbers.add(record.getReference());
			}
		}
	}

	/**
	 * This method will write the failed records of customer statement in CSV format.
	 * @param failedRecords
	 */
	public static void writeCsvFile(List<Record> failedRecords) {
		BufferedWriter bufferedWriter = null;
		try {
			bufferedWriter = new BufferedWriter(
					new FileWriter(OUTPUT_CSV_FILE));
			bufferedWriter.write(CSV_FILE_HEADER);
			bufferedWriter.write(System.lineSeparator());
			for (Record record : failedRecords) {
				bufferedWriter.write(record.getReference() + COMMA_DELIMITER);
				bufferedWriter.write(record.getDescription() + System.lineSeparator());
			}
			logger.info("CSV file is written successfully");
		} catch (IOException exception) {
			logger.info(exception.getMessage());
		} finally {
			if(bufferedWriter != null) {
				try {
					bufferedWriter.close();
				} catch (IOException exception) {
					logger.info(exception.getMessage());
				}
			}	
		}
	}
}
