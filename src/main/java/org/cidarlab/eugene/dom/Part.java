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

import java.util.Iterator;

import org.cidarlab.eugene.constants.EugeneConstants;
import org.cidarlab.eugene.exception.EugeneException;

public class Part 
	extends Component {
	
	private static final long serialVersionUID = 6002983190065461055L;
	
	public Part(PartType pt, String name) {
		super(pt, name);
	}

	public Part(String sName, PartType pt) {
		super(pt, sName);
	}


	/**
	 * In case of a part, we need to check if the part's part type 
	 * has the desired property.
	 */
	@Override
	public Property getProperty(String sPropertyName) {
		for (Property p : this.getType().getProperties()) {
			if (p.getName().equals(sPropertyName)) {
				return p;
			}
		}
		return (Property) null;
	}
	
	@Override
	public void setSequence(String seq) {
		// let's check if the part has a sequence property
		this.getPropertyValue(EugeneConstants.SEQUENCE_PROPERTY).setTxt(seq);
	}


	@Override
	public String toString() {
		
//		try {
//			return EugeneUtils.prettyPrint(this);
//		} catch(EugeneException ee) {
//			ee.printStackTrace();
//		}
//		return null;
		
		StringBuilder sb = new StringBuilder();

		if (this.getType() != null) {
			sb.append(this.getType().getName()).append(" ");
		} else {
			sb.append("Part ");
		}

		sb.append(this.getName()).append(" (");

		if (null != this.getPropertyValues() && !this.getPropertyValues().isEmpty()) {
			Iterator<String> it = this.getPropertyValues().keySet().iterator();
			while (it.hasNext()) {
				String sPropertyName = it.next();
				PropertyValue objValue = this.getPropertyValues().get(sPropertyName);
				sb.append(".").append(sPropertyName).append("(");

				if (objValue != null) {
					sb.append(objValue.toString());
				}
				sb.append(")");

				if (it.hasNext()) {
					sb.append(",");
				}
			}
		}

		sb.append(")");
		return sb.toString();
	}

	/*
	 * on parts, we retrieve the value of the property named 
	 * after name
	 */
	@Override
	public NamedElement getElement(String name) 
			throws EugeneException {
		
		/*
		 * does the property exist?
		 */
		if(null == this.getProperty(name)) {
			throw new EugeneException(
					"An instance of " + this.getTypeAsString() + 
					" does not contain a property named " + name);
		}
		
		
		PropertyValue pv = this.getPropertyValue(name);
		
		if(null == pv) {
			// i.e. the property value is not set
			Property prop = this.getProperty(name);
			pv = new PropertyValue(name, prop.getType());
		}

		return pv;
	}

	/*
	 * on parts, we retrieve the value of the idx-th property
	 */
	@Override
	public NamedElement getElement(int idx) 
			throws EugeneException {
		if(idx < 0 || idx > this.getProperties().size() - 1) {
			throw new EugeneException("The array index " + idx + " is out of bounds on the Part "+this.getName()+"!");
		}

		System.out.println("[Part.getElement] -> " + idx);

		// get the idx-th property
		Property p = this.getProperties().get(idx);		
		return this.getPropertyValue(p.getName());
	}
	
	@Override
	public int hashCode() {
		int hash = this.getName().hashCode();
		hash += ((PartType)this.getType()).hashCode();
		
		if(null != this.getPropertyValues() && !(this.getPropertyValues().isEmpty())) {
			for(String s : this.getPropertyValues().keySet()) {
				hash += this.getPropertyValues().get(s).hashCode();
			}
		}
		
		return hash;
	}

	@Override
	public boolean equals(Object o) {
		if(o == null) {
			return false;
		} else if(!(o instanceof Part)) {
			return false;
		}
		
		return this.hashCode() == ((Part)o).hashCode();
	}

	/**
	 * The hasSequence() method returns true if 
	 * the Part has a non-empty and non-null 
	 * sequence.
	 * 
	 * @return true ... if the sequence is non-empty and non-null
	 *        false ... otherwise
	 *        
	 * @throws EugeneException
	 */
	@Override
	public boolean hasSequence() 
			throws EugeneException {
		return (null != this.getPropertyValue(EugeneConstants.SEQUENCE_PROPERTY) && 
				!this.getPropertyValue(EugeneConstants.SEQUENCE_PROPERTY).getTxt().isEmpty());
	}

	@Override
	public String getSequence() {
		// first, we get the value of the sequence property
		PropertyValue seq = this.getPropertyValue(EugeneConstants.SEQUENCE_PROPERTY);
		
		// if the value is not null and not empty
		if(null != seq && seq.getTxt() != null && !seq.getTxt().isEmpty()) {
			// then, we return the value
			return seq.getTxt();
		}
		// otherwise, we return NULL
		return null;
	}

}
