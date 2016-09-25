package UserToDb;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.security.cert.Certificate;
import java.text.DecimalFormat;
import java.util.LinkedList;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import Generate_Random.Generate;




public class ExcelToJava {

	public static LinkedList<User> extractUser(String nomFichier , int startDay , int endDay)
			 {
		System.out.println("------------------ From Fichier :"+nomFichier);
		try {
			LinkedList<User> li = new LinkedList<User>();
		String Profession[] = { " Nurse " , " Doctor " , "Teacher " , "Trainer " , "Student " , "Journalist " , "Designer " , "Data Scientist " , "Architect " , "Astronomer " , "Biologist " , "Cleaner " , "Dancer " , "Dentist " , "Fashion designer " , "Flight engineer " , "Musician " , "Police Officer " , "Private Detective " , "Guide du Tourisme" } ;

		
        LinkedList<String> liOfBirthday = Generate.getTooManyDate(1000 ,startDay , endDay);
		FileInputStream fis = new FileInputStream(new File(nomFichier));
		XSSFWorkbook wb = new XSSFWorkbook(fis);

		// Create a sheet object to retrive the sheet
		XSSFSheet sheet = wb.getSheetAt(0);

		// that is for evaluate the cell type
		FormulaEvaluator Type = wb.getCreationHelper().createFormulaEvaluator();
		int i = 0;
		User U;
		int nbre_profession = Profession.length;
		String nom = "", prenom = "";
	//	while (nbre_dec-- > 0) {
		for (Row row : sheet) {

			for (Cell cell : row) {

				if (cell.getColumnIndex() == 0)
					nom = cell.getStringCellValue();
				if (cell.getColumnIndex() == 1)
					prenom = cell.getStringCellValue();

			}
		
		//	U = new User(nom,nom+'.'+prenom+"@gmail.com",liOfBirthday.get(Generate.Between(0,999)),Profession[Generate.Between(0,nbre_profession-1)] ,Math.random() > 0.85 ? "female" : "male");
			U = new User(nom,nom+'.'+prenom+"@gmail.com",liOfBirthday.get(Generate.Between(0,999)),Profession[Generate.Between(0,nbre_profession-1)] ,to_db.getGender(prenom));
			li.add(U);

		}
	//	}
		for (User k : li)
		System.out.println(" N " + i++ + " User  :" + k);
		fis.close();
		wb.close();

	 	return li;
		}catch(Exception e) {  e.printStackTrace(); }


		return null; 
		
		
			 }
	
	
/*	public String getGender(String prenom) throws Exception
	{

		NameGender gender = api.getGender("John");
		System.out.println("John is: "+gender.getGender());
		System.out.println("is John male? " + gender.isMale());
		System.out.println("Who? " + gender.getName());
		return gender; 
	}*/
	
}
