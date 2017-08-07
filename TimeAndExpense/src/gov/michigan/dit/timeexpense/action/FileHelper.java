package gov.michigan.dit.timeexpense.action;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class FileHelper {
	
public static void fileRead (String root, String target, String userId){
	target = target + "/jsp/report/finishedReports";
	java.io.File dir = new java.io.File(root);

	//System.out.println(root1);

	String[] list = dir.list();

	if (list.length > 0) {

	for (int i = 0; i < list.length; i++) {
	  File file1 = new java.io.File(root + "/" + list[i]);
	  File file2 = new java.io.File(target + "/" + list[i]);
	//  Files.copy(root + "\\" + file, target + "\\" + file, REPLACE_EXISTING);
	  try {
		if (file1.getName().startsWith(userId)){
				FileUtils.copyFile(file1, file2, true);
		}
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	//  System.out.println(file);
	     }
	  }
}

}
