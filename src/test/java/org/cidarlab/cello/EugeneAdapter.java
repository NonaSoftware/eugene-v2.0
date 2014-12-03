package org.cidarlab.cello;

import java.io.File;

/*
 * Eugene imports
 */
import org.cidarlab.eugene.Eugene;
import org.cidarlab.eugene.dom.NamedElement;
import org.cidarlab.eugene.dom.imp.container.EugeneCollection;

/**
 * The EugeneAdapter class demonstrates the programmatic 
 * utilization of Eugene. 
 * 
 * @author Ernst Oberortner
 *
 */
public class EugeneAdapter {

	public static void main(String[] args) 
			throws Exception {

		/*
		 * STEP I:
		 * Instantiating Eugene
		 */
		Eugene e = new Eugene();
		
		/*
		 * STEP II:
		 * Executing a Eugene script
		 */
		EugeneCollection ec = e.executeFile(new File("/path/to/my.eug"));
		
		/*
		 * STEP III:
		 * Processing the results 
		 */
		for(NamedElement ne : ec.getElements()) {
			// here, we only print each NamedElement object
			// to the console
			System.out.println(ne);
		}

	}

}
