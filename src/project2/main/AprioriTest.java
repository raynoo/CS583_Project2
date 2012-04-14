package project2.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import project2.database.DBConnection;

import weka.associations.Apriori;
import weka.associations.AssociationRule;
import weka.associations.AssociationRules;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;

public class AprioriTest {
	
	public static void main(String[] args) throws SQLException {
		
		AprioriTest test = new AprioriTest();
		Instances instances = test.loadArffFromCsv(args[0], args[1]);
//		Instances instances = test.loadArff(args[1]);
		System.out.println("Loaded file.");
		test.runApriori(instances);
		
		
	}
	
	public void getItemSet(){
		
	}
	
	public void runApriori(Instances instances){
		
		Apriori apriori = new Apriori();
		apriori.setLowerBoundMinSupport(0.001);
		apriori.setMinMetric(0.5);
		
		try {
			apriori.buildAssociations(instances);
			AssociationRules rules = apriori.getAssociationRules();
			List<AssociationRule> rulelist = rules.getRules();
			for(AssociationRule rule : rulelist){
				System.out.println("....." + rule);
				rule.getConsequence().size();
			}
			
			
			System.out.println("Built associations.");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Instances loadArff(String sourceArffFile){
		
		BufferedReader reader;
		Instances instances = null;
		
		try {
			reader = new BufferedReader(new FileReader(new File(sourceArffFile)));
			instances = new Instances(reader);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return instances;
	}
	
	public Instances loadArffFromCsv(String sourceCsvFile, String destArffFile){
		
		ArffSaver arffConverter = new ArffSaver();
		
		try {
			CSVLoader loader = new CSVLoader();
			loader.setSource(new File(sourceCsvFile));

			Instances csvInstance = loader.getDataSet();
			
			arffConverter.setInstances(csvInstance);
			arffConverter.setFile(new File(destArffFile));
			arffConverter.writeBatch();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return arffConverter.getInstances();
	}
}