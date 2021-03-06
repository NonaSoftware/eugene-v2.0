/* Copyright (c) 2015, Boston University
 * 
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, 
 * are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice, this list 
 * of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list 
 * of conditions and the following disclaimer in the documentation and/or other materials 
 * provided with the distribution.
 * 
 * 3. Neither the name of the copyright holder nor the names of its contributors may be 
 * used to endorse or promote products derived from this software without specific prior 
 * written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY 
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF 
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL 
 * THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, 
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE 
 * GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND 
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
 * POSSIBILITY OF SUCH DAMAGE.
 */

package org.cidarlab.eugene.dom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cidarlab.eugene.constants.Orientation;
import org.cidarlab.eugene.exception.DOMException;
import org.cidarlab.sparrow.constants.SparrowConstants;
import org.cidarlab.sparrow.exception.SparrowException;

public class SparrowBuilder {

	public static Property buildProperty(
			String sName, String sType) {
		return new Property(sName, sType);
	}
	
	public static PropertyValue buildPropertyValue(
			String name, String type, String value) 
				throws SparrowException {
		return buildPropertyValue(
				new PropertyValue(name, type), value);		
	}
	
	public static PropertyValue buildPropertyValue(
			Property property, String value) 
				throws SparrowException {
		PropertyValue pv = new PropertyValue(property);
		
		if(SparrowConstants.TXT.equalsIgnoreCase(property.getType())) {
			pv.setTxt(value);
		} else if(SparrowConstants.NUM.equalsIgnoreCase(property.getType())) {
			try {
				pv.setNum(Double.parseDouble(value));
			} catch(Exception e) {
				throw new SparrowException("Invalid NUM value for property value "+property.getName());
			}
		} else {
			throw new SparrowException(property.getType()+" is an invalid type!");
		}
		
		return pv;
	}
	

//	public static Device buildDevice(
//			String sName, List<Component> lstComponents, char[] directions)
//			throws SparrowException {
//		
////		System.out.println("[EugeneBuilder.buildDevice] -> "+sName+", "+Arrays.toString(directions));
//		
////		if(null != lstComponents) {
////			/* here, we iterate over the devices components and start building the device graph */
////			long[] components = new long[lstComponents.size()];
////			int i=0;
////			for(Component component : lstComponents) {
////				//System.out.println("[EugeneBuilder.buildDevice] -> "+
////				//		component.getName()+", "+directions[i]);
////				components[i++] = SymbolTables.getId(component.getName());
////			}
////		}
////		Device d = new Device(sName, lstComponents);
//		
//		Device device = new Device(sName, lstComponents, null, directions);
////		System.out.println("[EugeneBuilder.buildDevice] -> "+device);
//		return device;
//	}
//
//	public static Device buildDevice(
//			String sName, 
//			long[] lstComponents, 
//			char[] directions) 
//					throws SparrowException {
//		
////		System.out.println("[EugeneBuilder.buildDevice] -> "+sName+", "+Arrays.toString(lstComponents));
//		
//		Device device = new Device(sName);
//		if(null != lstComponents && lstComponents.length > 0) {
//			for(long component : lstComponents) {
//				NamedElement element = SymbolTables.getComponent(component);
//				device.add(element);
//			}
//		}
//		device.setDirections(directions);
//		
//		return device;
//	}
//
//	public static Device buildDevice(
//			String sName, 
//			List<Component> lstComponents, 
//			List<Property> lstProperties, 
//			char[] directions) 
//					throws SparrowException {
//		
//		if(null != directions) {
//			for(int i=0; i<directions.length; i++) {
//				if('-' == directions[i]) {
//					Component component = lstComponents.get(i);
//					
//					/*
//					 * if the current component is a device, 
//					 * then we reverse the device's elements
//					 */
//					if(component instanceof Device) {
//						((Device)component).reverseComponents();
//					}
//				}
//			}
//		}
//		return new Device(sName, lstComponents, lstProperties, directions);		
//	}

//	public static Rule buildRule(
//			String sName, Device objDevice, Token token, CommonTree tree) 
//					throws SparrowException {
//		
//		/** iterate over the tree and build the appropriate predicates **/
//		Predicate predicate = null;
//		if (null != tree) {
//			try {
//				predicate = RuleTreeParser.buildTree(tree);
//			} catch (Exception e) {
//				e.printStackTrace();
//				throw new SparrowException(e.getMessage());
//			}
//		} else {
//			throw new SparrowException("No rule tree provided!");
//		}
//
//		/** and finally assign the rules to the device **/
//		/** => making it later easier to retrieve one device's rules **/
//		
//		
//		if(null != predicate) { 
//			return new Rule(sName, objDevice, predicate);
//		}
//		return null;
//	}

	private static Map<String, PartType> hmPartTypes = null;
	
	private static void initPartTypesMap() {
		hmPartTypes = new HashMap<String, PartType>();
	}
	
	public static PartType buildPartType(
			String name, List<Property> properties) {
		
		// instantiate the part types hashmap
		// if it's NULL
		if(null == hmPartTypes) {
			initPartTypesMap();
		}
		
		// check if the part type has been declared already
		// if yes, then return it
		if(hmPartTypes.containsKey(name)) {
			return hmPartTypes.get(name);
		} 
		
		// the part type has not been declared
		// hence, create it and store it in the part types hashmap
		PartType objPartType = new PartType(name, properties);
		hmPartTypes.put(name, objPartType);
		
		return objPartType;
	}

	public static Part buildPart(
			String name, PartType parttype, List<PropertyValue> values) 
					throws SparrowException {

		Part part = new Part(name, parttype);

		if (null != values && !values.isEmpty()) {
				/*
				 *  assign the property values to the part
				 */
				for (PropertyValue value : values) {
					
					try {
						
						// first, check if the part's part type has the property						
						Property property = part.getType().getProperty(value.getName());
						
						if(null != property) {
							// here, we can also compare the types
							part.setPropertyValue(property, value);
						} else {
							// we need to create the property first
							part.setPropertyValue(
									SparrowBuilder.buildProperty(value.getName(), value.getType()), 
									value);
						}
						
					} catch(DOMException de) {
						throw new SparrowException(de.toString());
					}
				}
		}

		return part;
	}

	/**
	 * The buildDevice method instantiates a Eugene Device w/ the given information.
	 * 
	 * @param displayId   ... the displayId represents the name of the Device
	 * @param components  ... a list of the Device's components
	 * @param directions  ... a list of the components' orientations
	 * @return ... a Eugene Device object
	 * 
	 * @throws SparrowException ... iff the number of components does not match the number of orientations
	 */
	public static Device buildDevice(String displayId, List<Component> components, char[] directions) 
			throws SparrowException {
		
		// instantiate a Device w/ 
		// the provided displayId
		Device d = new Device(displayId);
		
		// components of the Device
		if(null != components && !components.isEmpty()) {
			
			// iterate over the given components
			for(Component c : components) {
				
				// store the current component in a list
				List<NamedElement> loc = new ArrayList<NamedElement>();

				// add the given component to the list of elements
				loc.add(c);
				
				// add the list of elements to the Device's list of elements
				d.getComponents().add(loc);
			}
			
		}
		
		if(null != directions) {
			// the number of provided orientations must match w/ the number 
			// of provided components
			if(directions.length == components.size()) {
				
				// iterate over the given orientations
				for(int i=0; i<directions.length; i++) {
					
					Orientation o = Orientation.FORWARD;
					if('-' == directions[i]) {
						o = Orientation.REVERSE;
					}
					
					// store the current orientation in a list
					List<Orientation> loo = new ArrayList<Orientation>();
					loo.add(o);
					
					// add the list to the device's list of orientations
					d.getOrientations().add(loo);
				}
			} else {
				throw new SparrowException("The number of components does not match the number of directions provided.");
			}
			
		}
		
		// lastly, we return the Eugene Device object
		return d;
	}

}
