// $ANTLR 3.5.1 /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g 2014-11-06 14:28:29

/*
Copyright (c) 2012 Boston University.
All rights reserved.
Permission is hereby granted, without written agreement and without
license or royalty fees, to use, copy, modify, and distribute this
software and its documentation for any purpose, provided that the above
copyright notice and the following two paragraphs appear in all copies
of this software.

IN NO EVENT SHALL BOSTON UNIVERSITY BE LIABLE TO ANY PARTY
FOR DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES
ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
BOSTON UNIVERSITY HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

BOSTON UNIVERSITY SPECIFICALLY DISCLAIMS ANY WARRANTIES,
INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE
PROVIDED HEREUNDER IS ON AN "AS IS" BASIS, AND BOSTON UNIVERSITY HAS
NO OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES,
ENHANCEMENTS, OR MODIFICATIONS.
*/

package org.cidarlab.eugene.parser;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;
import java.util.Set;
import java.util.Iterator;
import java.util.Random;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.UUID;

import org.cidarlab.eugene.constants.EugeneConstants;
import org.cidarlab.eugene.dom.*;
import org.cidarlab.eugene.constants.Orientation;
//import org.cidarlab.eugene.data.genbank.*;
//import org.cidarlab.eugene.data.registry.*;
import org.cidarlab.eugene.data.sbol.*;
import org.cidarlab.eugene.exception.EugeneException;
import org.cidarlab.eugene.exception.EugeneReturnException;
import org.cidarlab.eugene.interp.Interp;
import org.cidarlab.eugene.dom.rules.*;
import org.cidarlab.eugene.dom.interaction.Interaction;
import org.cidarlab.eugene.dom.rules.exp.*;
import org.cidarlab.eugene.dom.imp.*;
import org.cidarlab.eugene.dom.imp.container.*;
import org.cidarlab.eugene.dom.imp.loops.Loop;
import org.cidarlab.eugene.dom.imp.functions.*;
import org.cidarlab.eugene.dom.imp.branches.ConditionalBranch;
import org.cidarlab.eugene.constants.EugeneConstants.ParsingPhase;

import org.antlr.runtime.*;

 
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;

/*
 *  HELPER LIBRARIES
 */
import org.apache.commons.lang3.ArrayUtils;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

import org.antlr.runtime.tree.*;


@SuppressWarnings("all")
public class EugeneParser extends Parser {
	public static final String[] tokenNames = new String[] {
		"<invalid>", "<EOR>", "<DOWN>", "<UP>", "ADDPROPS", "AMP", "ARRAY", "ARROW", 
		"ASSERT", "BOOL", "BOOLEAN", "COLLECTION", "COLON", "COMMA", "CREATE_LC", 
		"CREATE_UC", "DELETE_LC", "DELETE_UC", "DEVICE", "DIGIT", "DIV", "DOLLAR", 
		"DOT", "DOTDOT", "EQUALS", "EXIT_LC", "EXIT_UC", "EXPORT_LC", "EXPORT_UC", 
		"FALSE_LC", "FALSE_UC", "FLEXIBLE", "GENBANK", "GEQUAL", "GRAMMAR", "GTHAN", 
		"HASHMARK", "ID", "IMAGE", "IMPORT_LC", "IMPORT_UC", "INCLUDE_LC", "INCLUDE_UC", 
		"INTERACTION", "LC_AND", "LC_ELSE", "LC_ELSEIF", "LC_FOR", "LC_FORALL", 
		"LC_IF", "LC_INDUCES", "LC_NOT", "LC_ON", "LC_OR", "LC_REPRESSES", "LC_WHILE", 
		"LEFTCUR", "LEFTP", "LEFTSBR", "LEQUAL", "LINE_COMMENT", "LOG_AND", "LOG_OR", 
		"LTHAN", "MINUS", "ML_COMMENT", "MULT", "NEQUAL", "NEWLINE", "NOTE", "NUM", 
		"NUMBER", "OP_NOT", "PART", "PART_TYPE", "PERMUTE", "PIPE", "PLUS", "PRINTLN_LC", 
		"PRINTLN_UC", "PRINT_LC", "PRINT_UC", "PRODUCT", "PROPERTY", "RANDOM_LC", 
		"RANDOM_UC", "READ_LC", "READ_UC", "REAL", "REF", "REGISTRY", "RETURN_LC", 
		"RETURN_UC", "RIGHTCUR", "RIGHTP", "RIGHTSBR", "RULE", "SAVE_LC", "SAVE_UC", 
		"SBOL", "SEMIC", "SIZEOF_LC", "SIZEOF_UC", "SIZE_LC", "SIZE_UC", "STORE_LC", 
		"STORE_UC", "STRICT", "STRING", "TRUE_LC", "TRUE_UC", "TXT", "TYPE", "UC_AND", 
		"UC_ELSE", "UC_ELSEIF", "UC_FOR", "UC_FORALL", "UC_IF", "UC_INDUCES", 
		"UC_NOT", "UC_ON", "UC_OR", "UC_REPRESSES", "UC_WHILE", "UNDERS", "UPDATE_LC", 
		"UPDATE_UC", "VISUALIZE_LC", "VISUALIZE_UC", "WS", "'AFTER'", "'ALL_AFTER'", 
		"'ALL_BEFORE'", "'ALL_FORWARD'", "'ALL_NEXTTO'", "'ALL_REVERSE'", "'ALL_SAME_ORIENTATION'", 
		"'ALTERNATE_ORIENTATION'", "'ALWAYS_NEXTTO'", "'BEFORE'", "'CONTAINS'", 
		"'DRIVES'", "'ENDSWITH'", "'EQUALS'", "'EXACTLY'", "'FORWARD'", "'MATCHES'", 
		"'MORETHAN'", "'NEXTTO'", "'NOTCONTAINS'", "'NOTEQUALS'", "'NOTEXACTLY'", 
		"'NOTMATCHES'", "'NOTMORETHAN'", "'NOTTHEN'", "'NOTWITH'", "'REVERSE'", 
		"'SAME_COUNT'", "'SAME_ORIENTATION'", "'SOME_AFTER'", "'SOME_BEFORE'", 
		"'SOME_FORWARD'", "'SOME_NEXTTO'", "'SOME_REVERSE'", "'SOME_SAME_ORIENTATION'", 
		"'SOUNDSLIKE'", "'STARTSWITH'", "'THEN'", "'WITH'", "'after'", "'all_after'", 
		"'all_before'", "'all_forward'", "'all_nextto'", "'all_reverse'", "'all_same_orientation'", 
		"'alternate_orientation'", "'always_nextto'", "'before'", "'contains'", 
		"'drives'", "'endswith'", "'equals'", "'exactly'", "'forward'", "'matches'", 
		"'morethan'", "'nextto'", "'notcontains'", "'notequals'", "'notexactly'", 
		"'notmatches'", "'notmorethan'", "'notthen'", "'notwith'", "'reverse'", 
		"'same_count'", "'same_orientation'", "'some_after'", "'some_before'", 
		"'some_forward'", "'some_nextto'", "'some_reverse'", "'some_same_orientation'", 
		"'soundslike'", "'startswith'", "'then'", "'with'"
	};
	public static final int EOF=-1;
	public static final int T__131=131;
	public static final int T__132=132;
	public static final int T__133=133;
	public static final int T__134=134;
	public static final int T__135=135;
	public static final int T__136=136;
	public static final int T__137=137;
	public static final int T__138=138;
	public static final int T__139=139;
	public static final int T__140=140;
	public static final int T__141=141;
	public static final int T__142=142;
	public static final int T__143=143;
	public static final int T__144=144;
	public static final int T__145=145;
	public static final int T__146=146;
	public static final int T__147=147;
	public static final int T__148=148;
	public static final int T__149=149;
	public static final int T__150=150;
	public static final int T__151=151;
	public static final int T__152=152;
	public static final int T__153=153;
	public static final int T__154=154;
	public static final int T__155=155;
	public static final int T__156=156;
	public static final int T__157=157;
	public static final int T__158=158;
	public static final int T__159=159;
	public static final int T__160=160;
	public static final int T__161=161;
	public static final int T__162=162;
	public static final int T__163=163;
	public static final int T__164=164;
	public static final int T__165=165;
	public static final int T__166=166;
	public static final int T__167=167;
	public static final int T__168=168;
	public static final int T__169=169;
	public static final int T__170=170;
	public static final int T__171=171;
	public static final int T__172=172;
	public static final int T__173=173;
	public static final int T__174=174;
	public static final int T__175=175;
	public static final int T__176=176;
	public static final int T__177=177;
	public static final int T__178=178;
	public static final int T__179=179;
	public static final int T__180=180;
	public static final int T__181=181;
	public static final int T__182=182;
	public static final int T__183=183;
	public static final int T__184=184;
	public static final int T__185=185;
	public static final int T__186=186;
	public static final int T__187=187;
	public static final int T__188=188;
	public static final int T__189=189;
	public static final int T__190=190;
	public static final int T__191=191;
	public static final int T__192=192;
	public static final int T__193=193;
	public static final int T__194=194;
	public static final int T__195=195;
	public static final int T__196=196;
	public static final int T__197=197;
	public static final int T__198=198;
	public static final int T__199=199;
	public static final int T__200=200;
	public static final int T__201=201;
	public static final int T__202=202;
	public static final int T__203=203;
	public static final int T__204=204;
	public static final int T__205=205;
	public static final int T__206=206;
	public static final int T__207=207;
	public static final int T__208=208;
	public static final int ADDPROPS=4;
	public static final int AMP=5;
	public static final int ARRAY=6;
	public static final int ARROW=7;
	public static final int ASSERT=8;
	public static final int BOOL=9;
	public static final int BOOLEAN=10;
	public static final int COLLECTION=11;
	public static final int COLON=12;
	public static final int COMMA=13;
	public static final int CREATE_LC=14;
	public static final int CREATE_UC=15;
	public static final int DELETE_LC=16;
	public static final int DELETE_UC=17;
	public static final int DEVICE=18;
	public static final int DIGIT=19;
	public static final int DIV=20;
	public static final int DOLLAR=21;
	public static final int DOT=22;
	public static final int DOTDOT=23;
	public static final int EQUALS=24;
	public static final int EXIT_LC=25;
	public static final int EXIT_UC=26;
	public static final int EXPORT_LC=27;
	public static final int EXPORT_UC=28;
	public static final int FALSE_LC=29;
	public static final int FALSE_UC=30;
	public static final int FLEXIBLE=31;
	public static final int GENBANK=32;
	public static final int GEQUAL=33;
	public static final int GRAMMAR=34;
	public static final int GTHAN=35;
	public static final int HASHMARK=36;
	public static final int ID=37;
	public static final int IMAGE=38;
	public static final int IMPORT_LC=39;
	public static final int IMPORT_UC=40;
	public static final int INCLUDE_LC=41;
	public static final int INCLUDE_UC=42;
	public static final int INTERACTION=43;
	public static final int LC_AND=44;
	public static final int LC_ELSE=45;
	public static final int LC_ELSEIF=46;
	public static final int LC_FOR=47;
	public static final int LC_FORALL=48;
	public static final int LC_IF=49;
	public static final int LC_INDUCES=50;
	public static final int LC_NOT=51;
	public static final int LC_ON=52;
	public static final int LC_OR=53;
	public static final int LC_REPRESSES=54;
	public static final int LC_WHILE=55;
	public static final int LEFTCUR=56;
	public static final int LEFTP=57;
	public static final int LEFTSBR=58;
	public static final int LEQUAL=59;
	public static final int LINE_COMMENT=60;
	public static final int LOG_AND=61;
	public static final int LOG_OR=62;
	public static final int LTHAN=63;
	public static final int MINUS=64;
	public static final int ML_COMMENT=65;
	public static final int MULT=66;
	public static final int NEQUAL=67;
	public static final int NEWLINE=68;
	public static final int NOTE=69;
	public static final int NUM=70;
	public static final int NUMBER=71;
	public static final int OP_NOT=72;
	public static final int PART=73;
	public static final int PART_TYPE=74;
	public static final int PERMUTE=75;
	public static final int PIPE=76;
	public static final int PLUS=77;
	public static final int PRINTLN_LC=78;
	public static final int PRINTLN_UC=79;
	public static final int PRINT_LC=80;
	public static final int PRINT_UC=81;
	public static final int PRODUCT=82;
	public static final int PROPERTY=83;
	public static final int RANDOM_LC=84;
	public static final int RANDOM_UC=85;
	public static final int READ_LC=86;
	public static final int READ_UC=87;
	public static final int REAL=88;
	public static final int REF=89;
	public static final int REGISTRY=90;
	public static final int RETURN_LC=91;
	public static final int RETURN_UC=92;
	public static final int RIGHTCUR=93;
	public static final int RIGHTP=94;
	public static final int RIGHTSBR=95;
	public static final int RULE=96;
	public static final int SAVE_LC=97;
	public static final int SAVE_UC=98;
	public static final int SBOL=99;
	public static final int SEMIC=100;
	public static final int SIZEOF_LC=101;
	public static final int SIZEOF_UC=102;
	public static final int SIZE_LC=103;
	public static final int SIZE_UC=104;
	public static final int STORE_LC=105;
	public static final int STORE_UC=106;
	public static final int STRICT=107;
	public static final int STRING=108;
	public static final int TRUE_LC=109;
	public static final int TRUE_UC=110;
	public static final int TXT=111;
	public static final int TYPE=112;
	public static final int UC_AND=113;
	public static final int UC_ELSE=114;
	public static final int UC_ELSEIF=115;
	public static final int UC_FOR=116;
	public static final int UC_FORALL=117;
	public static final int UC_IF=118;
	public static final int UC_INDUCES=119;
	public static final int UC_NOT=120;
	public static final int UC_ON=121;
	public static final int UC_OR=122;
	public static final int UC_REPRESSES=123;
	public static final int UC_WHILE=124;
	public static final int UNDERS=125;
	public static final int UPDATE_LC=126;
	public static final int UPDATE_UC=127;
	public static final int VISUALIZE_LC=128;
	public static final int VISUALIZE_UC=129;
	public static final int WS=130;

	// delegates
	public Parser[] getDelegates() {
		return new Parser[] {};
	}

	// delegators


	public EugeneParser(TokenStream input) {
		this(input, new RecognizerSharedState());
	}
	public EugeneParser(TokenStream input, RecognizerSharedState state) {
		super(input, state);
	}

	protected TreeAdaptor adaptor = new CommonTreeAdaptor();

	public void setTreeAdaptor(TreeAdaptor adaptor) {
		this.adaptor = adaptor;
	}
	public TreeAdaptor getTreeAdaptor() {
		return adaptor;
	}
	@Override public String[] getTokenNames() { return EugeneParser.tokenNames; }
	@Override public String getGrammarFileName() { return "/Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g"; }


	/*
	 *  member variables
	 */
	// the interpreter
	private Interp interp;

	// the number of the parser's run
	private ParsingPhase PARSING_PHASE;

	// IMPORTERS
	//private SBOLRegistryImporter registryImporter;

	// the logger
	private final static Logger LOGGER = Logger.getLogger(EugeneParser.class.getName()); 

	// a writer for writing the output (print, println)
	private BufferedWriter writer = null;
	private final static String LINE_FEED = System.getProperty("line.separator");

	String typeList = "";
	boolean debug = false;
	ArrayList<Variable> propertyValuesHolder = new ArrayList<Variable>();
	ArrayList<String> propertyListHolder = new ArrayList<String>();

	// the name of the file to be parsed
	String filename = null;

	public void init(Interp interp, BufferedWriter writer, ParsingPhase PARSING_PHASE) {
	    this.interp = interp;
	    
	    if(null != writer) {
	    	this.writer = writer;
	    } else {
	        try {
	            // init the writer too
	            this.writer = new BufferedWriter(
	                              new OutputStreamWriter(
	                                  new FileOutputStream(java.io.FileDescriptor.out), "ASCII"), 512);
	        } catch(Exception e) {
	            printError(e.getLocalizedMessage());
	        }
	    }
	    
	    this.PARSING_PHASE = PARSING_PHASE;
	    
	}

	/**
	 *  The cleanUp/0 method cleans up all the mess that
	 *  Eugene has caused.
	 */
	public void cleanUp() {
	    // clean up the mess of the interpreter
	    if(null != this.interp) {
	        this.interp.cleanUp();
	        this.interp = null;
	    }
	}

	public EugeneCollection getAllElements() 
	        throws EugeneException {
	    try {
	        return this.interp.getAllElements();
	    } catch(EugeneException ee) {
	        throw new EugeneException(ee.getMessage());
	    }
	}

	public void setFilename(String filename) {
	    this.filename = filename;
	}

	// for testing purpose
	public void printFunctions() {
	    this.interp.printFunctions();
	}

	@Override
	public void reportError(RecognitionException re) {
	    printError(re.getLocalizedMessage());
	}

	//method used to collect individual members in declaration, used by grammar rule list
	public void addToListPrim(Variable p, Variable listPrim) {
	    if (p.type.equals(typeList)) {
	        if (typeList.equals(EugeneConstants.TXT)) {
	            listPrim.txtList.add(p.txt);
	        } else if(typeList.equals(EugeneConstants.NUM)) {
	            listPrim.numList.add(p.num);
	        }
	    } else {
	        printError("Type mismatch. List type is " + typeList + " and instantiated Variable type is " + p.type);
	    }
	}

	// for tokenization of rules
	String[] tokens = null;
	private void addToken(String token) {
	    if(null != tokens) {
	        tokens = ArrayUtils.add(tokens, token);
	    } else {
	        tokens = new String[1];
	        tokens[0] = token;
	    }
	}

	/*
	 *   ASSIGNMENTS AND EXPRESSIONS
	 */
	NamedElement root = null;
	NamedElement parent = null;
	NamedElement child = null;  

	/*
	 *    VARIBABLE DECLARATIONS
	 */
	//checks if the name has been defined in program
	public boolean checkIfAlreadyDeclared(String name, boolean all) {
	    try {
	        if (this.interp.checkIfDeclaredInScope(name)) {
	            printError(name + " has already been defined.");
	            return true;
	        } 
	    } catch(EugeneException ee) {
	        printError(ee.getLocalizedMessage());
	    }
	    return false;
	}

	 
		//declares Variable without instantiating it to any value
		public void declareVariableNoValue(String name, String type) {
			if (!checkIfAlreadyDeclared(name, true)) {
				Variable p = new Variable(name , type);
				try {
	   			    this.interp.put(name, p);
				} catch(EugeneException ee) {
				    printError(ee.getLocalizedMessage());
				}
			}
		}

		//declares and instantiates a num Variable with a value
		public void declareVariableWithValueNum(String name, Variable prim, int index) {
			if (!checkIfAlreadyDeclared(name, true)) {
				if (prim.type.equals(EugeneConstants.NUM)) {
					Variable p = new Variable(name, EugeneConstants.NUM);
					p.num = prim.num;
					try {
					    this.interp.put(name, p);
				        } catch(EugeneException ee) {
				            printError(ee.getLocalizedMessage());
				        }
				} else if (prim.type.equals(EugeneConstants.NUMLIST) && prim.index != -1) {
					Variable p = new Variable(name, EugeneConstants.NUM);
					p.num = prim.numList.get(prim.index);
				} else {
					printError("Type mismatch. Type is " + prim.type + " but must be num.");
				}
			}
		}

		//declares and instantiates a txt Variable with a value
		public void declareVariableWithValueTxt(String name, Variable prim, int index) {
			if (!checkIfAlreadyDeclared(name, true)) {
				if (prim.type.equals(EugeneConstants.TXT)) {
					Variable p = new Variable(name, EugeneConstants.TXT);
					p.txt = prim.txt;
					try {
					    this.interp.put(name, p);
				        } catch(EugeneException ee) {
				            printError(ee.getLocalizedMessage());
				        }
				} else if (prim.type.equals(EugeneConstants.TXTLIST) && index != -1) {
					Variable p = new Variable(name, EugeneConstants.TXT);
					p.txt = prim.txtList.get(index);
				} else {
					printError("Type mismatch. Type is " + prim.type + " but must be txt.");
				}
			}
		}

		//declares and instantiates a txtList Variable with a txt value list
		public void declareVariableWithValueTxtList(String name, Variable prim) {
			if (!checkIfAlreadyDeclared(name, true)) {
				if (prim.type.equals(EugeneConstants.TXTLIST)) {
					Variable p = new Variable(name, EugeneConstants.TXTLIST);
					p.txtList.addAll(prim.txtList);
					try {
					    this.interp.put(name, p);
				        } catch(EugeneException ee) {
				            printError(ee.getLocalizedMessage());
				        }
				} else {
					printError("Type mismatch. Type is " + prim.type + " but must be txtList.");
				}
			}
		}

		//declares and instantiates a numList Variable with a num value list
		public void declareVariableWithValueNumList(String name, Variable prim) {
			if (!checkIfAlreadyDeclared(name, true)) {
				if (prim.type.equals(EugeneConstants.NUMLIST)) {
					Variable p = new Variable(name, EugeneConstants.NUMLIST);
					p.numList.addAll(prim.numList);
					try {
					    this.interp.put(name, p);
				        } catch(EugeneException ee) {
				            printError(ee.getLocalizedMessage());
				        }
				} else {
					printError("Type mismatch. Type is " + prim.type + " but must be numList.");
				}
			}
		}

		//declares and instantiates a boolean Variable with a boolean value
		public void declareVariableWithValueBool(String name, Variable prim) {
			if (!checkIfAlreadyDeclared(name, true)) {
				if (prim.type.equals(EugeneConstants.BOOLEAN)) {
					Variable p = new Variable(name, EugeneConstants.BOOLEAN);
					p.bool = prim.bool;
					try {
					    this.interp.put(name, p);
				        } catch(EugeneException ee) {
				            printError(ee.getLocalizedMessage());
				        }
				} else {
					printError("Type mismatch. Type is " + prim.type + " but must be numList.");
				}
			}
		}
		
		//copies the values of a primitive to a newly created Primitive
		public Variable copyVariable(Variable source) {
			Variable destination = new Variable(source.getName(), source.getType());
			destination.index = source.index;
			if (EugeneConstants.NUM.equals(source.getType())) {
				destination.type = EugeneConstants.NUM;
				destination.num = source.num;
			} else if (EugeneConstants.TXT.equals(source.getType())) {
				destination.type = EugeneConstants.TXT;
				destination.txt = source.txt;
			} else if (EugeneConstants.NUMLIST.equals(source.getType())) {
				destination.type = EugeneConstants.NUMLIST;
				destination.numList.clear();
				destination.numList.addAll(source.numList);
			} else if (EugeneConstants.TXTLIST.equals(source.getType())) {
				destination.type = EugeneConstants.TXTLIST;
				destination.txtList.clear();
				destination.txtList.addAll(source.txtList);
			} else if (EugeneConstants.BOOLEAN.equals(source.getType())) {
				destination.type = EugeneConstants.BOOLEAN;
				destination.bool = source.bool;
			}
			return destination;
		}
		
		
	    public void addToPropertyListHolder(String prop) 
	            throws EugeneException {
	        propertyListHolder.add(prop);
	    }
		
	    //adds values to the corresponding property
	    public void addToPropertyValuesHolder(String prop, Variable p, int index) 
	            throws EugeneException {
	        propertyValuesHolder.add(p);
	    }
	    	
	/*---------------------------------------------------------------------
	 * METHODS FOR ERROR REPORTING
	 *---------------------------------------------------------------------*/
	public void printDebug(Object message) {
	    if (debug) {
	        int line = input.LT(-1).getLine();
	        System.err.println("@Debug Line " + line + ": " + message);
	    }
	}

	private static final String NL = System.getProperty("line.separator");

	public void printError(Object message) {
	    int line = input.LT(-1).getLine();
	    int pos = input.LT(-1).getCharPositionInLine();
	    
	    StringBuilder sb = new StringBuilder();
	    sb.append("@Error!").append(NL);
	    if(null != this.filename) {
	        sb.append("File: ").append(this.filename).append(NL);
	    }
	    sb.append("Line ").append(line).append(" Position ").append(pos).append(NL);
	    sb.append(message).append(NL);
	    throw new IllegalArgumentException(sb.toString());
	}


	/*---------------------------------------------------------------------
	 * IMPERATIVE LANGUAGE STATEMENTS
	 *---------------------------------------------------------------------*/
	public Object exec(String rule, int tokenIndex) 
	        throws EugeneReturnException, EugeneException {
	    Object rv = null;
	    int oldPosition = this.input.index(); // save current location
	    this.input.seek(tokenIndex); // seek to place to start execution
	    
	    try { // which rule are we executing?
	        if("variableDeclaration".equals(rule)) {
	            rv = this.variableDeclaration(false);
	        } else if("logical_condition".equals(rule)) {
	            rv = this.logical_condition(false);
	        } else if("list_of_statements".equals(rule)) {
	            rv = this.list_of_statements(false);
	        } else if("assignment".equals(rule)) {
	            rv = this.assignment(false);
	        } 
	        
	    } catch (EugeneReturnException ere) {
	        // restore location
	        this.input.seek(oldPosition); 
	        // and throw exception
	        throw new EugeneReturnException(ere.getReturnValue());    
	    } catch (Exception e) {
	        // restore location
	        this.input.seek(oldPosition); 
	        // and throw exception
	        throw new EugeneException(e.getLocalizedMessage());
	    }

	    // restore location
	    this.input.seek(oldPosition); 
	    // and return the returned object	    
	    return rv;
	}
	 
	    /**
	     *   The exec_branch/1 method executes the branch of an IF-ELSE statement
	     *   depending on the condition.
	     *   The condition is evaluated during the Parsing process by invoking the Interp.evaluateCondition()
	     *   method. Depending on this, the parser invokes the exec_branch method.
	     *   (see if_elseif_else rule)
	     */
	    public void exec_branch(int tokenIndex) 
	        throws EugeneException, EugeneReturnException {

	        // push it!
	        this.interp.push(new ConditionalBranch());
	        try {
	            this.exec("list_of_statements", tokenIndex);
	        } catch (EugeneReturnException ere) {
	            // pop the if statement from the stack
	            this.interp.pop();
	            // and throw the exception
	            throw new EugeneReturnException(ere.getReturnValue());    
	        } catch (Exception e) {
	            // pop the if statement from the stack
	            this.interp.pop();
	            // and throw the exception
	            throw new EugeneException(e.getLocalizedMessage());
	        }
	        
	        // pop the if statement from the stack
	        this.interp.pop();
	    }
	    
	    public void execute_loop(Token initStart, Token condStart, Token incStart, Token loopStart) 
	            throws EugeneException, EugeneReturnException {

	        /*
	         * save the current parsing position
	         * (i.e. after the for loop)
	         */
	        int oldPosition = this.input.index(); 

	        /*
	         * execute the declaration statement
	         */ 
	        Loop l = new Loop();
	        if(null != initStart) {
	            variableDeclaration_return vdr = 
	                 (variableDeclaration_return)this.exec(
	                     "variableDeclaration", 
	                     initStart.getTokenIndex());    

	            /*
	             * create a new ForLoop object
	             */
	            if(null != vdr) {
	                l.setVarname(vdr.varname);
	            }
	        } else {
	            l.setVarname(UUID.randomUUID().toString());
	        }
	        
	        /*
	         * evaluate the condition
	         */
	        // first, parse the condition
	        logical_condition_return ret = 
	               (logical_condition_return)this.exec(
	                                           "logical_condition",         
	                                           condStart.getTokenIndex());
	        /*
	         * while the condition is satisfied
	         */
	        while(ret.b) {

	            /*
	             * push the ForLoop object onto the stack
	             * (for scoping)
	             */
	            this.interp.push(l);
	                
	            /*
	             *   execute the statements
	             */
	            this.exec(
	                "list_of_statements", 
	                loopStart.getTokenIndex()); 
	             
	            /*
	             * pop the ForLoop statement
	             */
	            this.interp.pop();
	            
	            /*
	             *   do the assignment statement
	             */
	            if(null != incStart) { 
	                this.exec(
	                    "assignment", 
	                    incStart.getTokenIndex()); 
	            }
	            
	            /*
	             *    evaluate the condition again
	             */ 
	            ret = (logical_condition_return)this.exec(
	                                   "logical_condition",         
	                                   condStart.getTokenIndex());  
	        }
	        
	        
	        if(null != l.getVarname()) {
	            /*
	             * lastly, we need to get rid of the 
	             * iteration variable (e.g. num i)
	             */
	            this.interp.removeVariable(
	                    l.getVarname());
	        }
	        
	        /*
	         * and continue parsing/interpreting 
	         * (i.e. after the for loop)
	         */
	        this.input.seek(oldPosition);
	    }
	    
	    public NamedElement call_function(String name, List<NamedElement> lopv)
	        throws EugeneException {
	    
	        // first, we ask the interpreter if the function exists
	        // and let it return the function object
	        FunctionInstance fi = this.interp.instantiateFunction(name, lopv);
	        if(null == fi) {
	            printError("The function " + name + " is not defined!");
	        }
	        
	        // we remember the current position in the script
	        int oldPosition = this.input.index();
	        // and we hold a temporary reference to the current
	        // stream of tokens
	        TokenStream tmp = this.input;
	        
	        // we put the function onto the stack
	        // SCOPING !
	        this.interp.push(fi);
	        
	        
	        NamedElement ret_el = null;
	        String error_msg = null;
	        try {
	            // we point the current input token stream to the 
	            // token stream from that we've read the function
	            this.input = fi.getPrototype().getTokenStream();
	            
	            // since a function can be defined "ahead" 
	            // of the current position in the script,
	            // we need to stretch the input
	            // and that's how we do it
	            this.input.toString();

	            // then, we let the input point to the first statement
	            // of the function
	            this.input.seek(
	                 fi.getPrototype().getStatements().getTokenIndex());
	            
	            // we execute all statements of the function
	            this.list_of_statements(false);
	            
	        } catch(RecognitionException re) {
	            // if a syntax error was detected, then
	            // we store the error message in a string
	            error_msg = re.getLocalizedMessage();	

	        } catch(EugeneReturnException ere) {
	            // a return statement has been encountered
	            ret_el = ere.getReturnValue();
	        }
	        
	        // and we need to cleanup the function's stack
	        this.interp.cleanupFunction(fi);

	        // finally we restore everything to what it was 
	        // before the function call and 
	        // jump to the original position        
	        this.input = tmp;
	        this.input.seek(oldPosition);

	        // if an error occured (e.g. syntax error),
	        // then we print the error message and
	        // exit
	        if(error_msg != null) {
	            printError(error_msg);
	        }
	        
	        return ret_el;

	        /*
	         * FOR TESTING PURPOSE:
	         * - we simulate the function's return value
	         */
	//        return f.simulateReturnValue();
	    }



	public static class prog_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "prog"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:855:1: prog[boolean defer] : ( statement[defer] | function_definition[true] )* EOF ;
	public final EugeneParser.prog_return prog(boolean defer) throws RecognitionException, EugeneReturnException {
		EugeneParser.prog_return retval = new EugeneParser.prog_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token EOF3=null;
		ParserRuleReturnScope statement1 =null;
		ParserRuleReturnScope function_definition2 =null;

		Object EOF3_tree=null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:862:2: ( ( statement[defer] | function_definition[true] )* EOF )
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:862:4: ( statement[defer] | function_definition[true] )* EOF
			{
			root_0 = (Object)adaptor.nil();


			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:862:4: ( statement[defer] | function_definition[true] )*
			loop1:
			while (true) {
				int alt1=3;
				switch ( input.LA(1) ) {
				case ARRAY:
				case COLLECTION:
				case CREATE_LC:
				case CREATE_UC:
				case DEVICE:
				case EXIT_LC:
				case EXIT_UC:
				case GENBANK:
				case GRAMMAR:
				case HASHMARK:
				case IMPORT_LC:
				case IMPORT_UC:
				case INCLUDE_LC:
				case INCLUDE_UC:
				case INTERACTION:
				case LC_FOR:
				case LC_FORALL:
				case LC_IF:
				case LC_WHILE:
				case PART:
				case PART_TYPE:
				case PERMUTE:
				case PRINTLN_LC:
				case PRINTLN_UC:
				case PRINT_LC:
				case PRINT_UC:
				case PRODUCT:
				case PROPERTY:
				case RANDOM_LC:
				case RANDOM_UC:
				case REGISTRY:
				case RETURN_LC:
				case RETURN_UC:
				case RULE:
				case SAVE_LC:
				case SAVE_UC:
				case SBOL:
				case SIZEOF_LC:
				case SIZEOF_UC:
				case SIZE_LC:
				case SIZE_UC:
				case STORE_LC:
				case STORE_UC:
				case TYPE:
				case UC_FOR:
				case UC_FORALL:
				case UC_IF:
				case UC_WHILE:
					{
					alt1=1;
					}
					break;
				case NUM:
					{
					int LA1_3 = input.LA(2);
					if ( (LA1_3==LEFTSBR) ) {
						int LA1_7 = input.LA(3);
						if ( (LA1_7==RIGHTSBR) ) {
							int LA1_13 = input.LA(4);
							if ( (LA1_13==ID) ) {
								int LA1_17 = input.LA(5);
								if ( (LA1_17==COMMA||LA1_17==EQUALS||LA1_17==SEMIC) ) {
									alt1=1;
								}
								else if ( (LA1_17==LEFTP) ) {
									alt1=2;
								}

							}

						}

					}
					else if ( (LA1_3==ID) ) {
						int LA1_8 = input.LA(3);
						if ( (LA1_8==COMMA||LA1_8==EQUALS||LA1_8==SEMIC) ) {
							alt1=1;
						}
						else if ( (LA1_8==LEFTP) ) {
							alt1=2;
						}

					}

					}
					break;
				case TXT:
					{
					int LA1_4 = input.LA(2);
					if ( (LA1_4==LEFTSBR) ) {
						int LA1_9 = input.LA(3);
						if ( (LA1_9==RIGHTSBR) ) {
							int LA1_15 = input.LA(4);
							if ( (LA1_15==ID) ) {
								int LA1_18 = input.LA(5);
								if ( (LA1_18==COMMA||LA1_18==EQUALS||LA1_18==SEMIC) ) {
									alt1=1;
								}
								else if ( (LA1_18==LEFTP) ) {
									alt1=2;
								}

							}

						}

					}
					else if ( (LA1_4==ID) ) {
						int LA1_10 = input.LA(3);
						if ( (LA1_10==COMMA||LA1_10==EQUALS||LA1_10==SEMIC) ) {
							alt1=1;
						}
						else if ( (LA1_10==LEFTP) ) {
							alt1=2;
						}

					}

					}
					break;
				case BOOL:
				case BOOLEAN:
					{
					int LA1_5 = input.LA(2);
					if ( (LA1_5==ID) ) {
						int LA1_11 = input.LA(3);
						if ( (LA1_11==COMMA||LA1_11==EQUALS||LA1_11==SEMIC) ) {
							alt1=1;
						}
						else if ( (LA1_11==LEFTP) ) {
							alt1=2;
						}

					}

					}
					break;
				case ID:
					{
					int LA1_6 = input.LA(2);
					if ( (LA1_6==LEFTP) ) {
						switch ( input.LA(3) ) {
						case DOLLAR:
						case FALSE_LC:
						case FALSE_UC:
						case ID:
						case LEFTP:
						case LEFTSBR:
						case MINUS:
						case NUMBER:
						case PERMUTE:
						case PRODUCT:
						case RANDOM_LC:
						case RANDOM_UC:
						case REAL:
						case SIZEOF_LC:
						case SIZEOF_UC:
						case SIZE_LC:
						case SIZE_UC:
						case STRING:
						case TRUE_LC:
						case TRUE_UC:
							{
							alt1=1;
							}
							break;
						case RIGHTP:
							{
							int LA1_16 = input.LA(4);
							if ( (LA1_16==LEFTCUR) ) {
								alt1=2;
							}
							else if ( (LA1_16==SEMIC) ) {
								alt1=1;
							}

							}
							break;
						case BOOL:
						case BOOLEAN:
						case NUM:
						case TXT:
							{
							alt1=2;
							}
							break;
						}
					}
					else if ( ((LA1_6 >= DOLLAR && LA1_6 <= DOT)||LA1_6==EQUALS||LA1_6==ID||LA1_6==LC_INDUCES||LA1_6==LC_REPRESSES||LA1_6==LEFTSBR||LA1_6==UC_INDUCES||LA1_6==UC_REPRESSES) ) {
						alt1=1;
					}

					}
					break;
				}
				switch (alt1) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:862:5: statement[defer]
					{
					pushFollow(FOLLOW_statement_in_prog1070);
					statement1=statement(defer);
					state._fsp--;

					adaptor.addChild(root_0, statement1.getTree());

					}
					break;
				case 2 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:862:24: function_definition[true]
					{
					pushFollow(FOLLOW_function_definition_in_prog1075);
					function_definition2=function_definition(true);
					state._fsp--;

					adaptor.addChild(root_0, function_definition2.getTree());

					}
					break;

				default :
					break loop1;
				}
			}

			EOF3=(Token)match(input,EOF,FOLLOW_EOF_in_prog1080); 
			EOF3_tree = (Object)adaptor.create(EOF3);
			adaptor.addChild(root_0, EOF3_tree);

			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "prog"


	public static class statement_return extends ParserRuleReturnScope {
		public NamedElement objReturnValue;
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "statement"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:866:1: statement[boolean defer] returns [NamedElement objReturnValue] : ( includeStatement[defer] ( SEMIC )? | declarationStatement[defer] SEMIC | printStatement[defer] SEMIC | assignment[defer] SEMIC |de= dataExchange[defer] SEMIC | imperativeStatements[defer] | function_call[defer] SEMIC |bif= built_in_function[defer] SEMIC | stand_alone_function[defer] SEMIC | return_statement[defer] SEMIC );
	public final EugeneParser.statement_return statement(boolean defer) throws RecognitionException, EugeneReturnException {
		EugeneParser.statement_return retval = new EugeneParser.statement_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token SEMIC5=null;
		Token SEMIC7=null;
		Token SEMIC9=null;
		Token SEMIC11=null;
		Token SEMIC12=null;
		Token SEMIC15=null;
		Token SEMIC16=null;
		Token SEMIC18=null;
		Token SEMIC20=null;
		ParserRuleReturnScope de =null;
		ParserRuleReturnScope bif =null;
		ParserRuleReturnScope includeStatement4 =null;
		ParserRuleReturnScope declarationStatement6 =null;
		ParserRuleReturnScope printStatement8 =null;
		ParserRuleReturnScope assignment10 =null;
		ParserRuleReturnScope imperativeStatements13 =null;
		ParserRuleReturnScope function_call14 =null;
		ParserRuleReturnScope stand_alone_function17 =null;
		ParserRuleReturnScope return_statement19 =null;

		Object SEMIC5_tree=null;
		Object SEMIC7_tree=null;
		Object SEMIC9_tree=null;
		Object SEMIC11_tree=null;
		Object SEMIC12_tree=null;
		Object SEMIC15_tree=null;
		Object SEMIC16_tree=null;
		Object SEMIC18_tree=null;
		Object SEMIC20_tree=null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:869:2: ( includeStatement[defer] ( SEMIC )? | declarationStatement[defer] SEMIC | printStatement[defer] SEMIC | assignment[defer] SEMIC |de= dataExchange[defer] SEMIC | imperativeStatements[defer] | function_call[defer] SEMIC |bif= built_in_function[defer] SEMIC | stand_alone_function[defer] SEMIC | return_statement[defer] SEMIC )
			int alt3=10;
			switch ( input.LA(1) ) {
			case HASHMARK:
			case INCLUDE_LC:
			case INCLUDE_UC:
				{
				alt3=1;
				}
				break;
			case ARRAY:
			case BOOL:
			case BOOLEAN:
			case COLLECTION:
			case DEVICE:
			case GRAMMAR:
			case INTERACTION:
			case NUM:
			case PART:
			case PART_TYPE:
			case PROPERTY:
			case RULE:
			case TXT:
			case TYPE:
				{
				alt3=2;
				}
				break;
			case ID:
				{
				switch ( input.LA(2) ) {
				case LEFTP:
					{
					alt3=7;
					}
					break;
				case DOLLAR:
				case ID:
				case LC_INDUCES:
				case LC_REPRESSES:
				case UC_INDUCES:
				case UC_REPRESSES:
					{
					alt3=2;
					}
					break;
				case DOT:
				case EQUALS:
				case LEFTSBR:
					{
					alt3=4;
					}
					break;
				default:
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 3, 3, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}
				}
				break;
			case PRINTLN_LC:
			case PRINTLN_UC:
			case PRINT_LC:
			case PRINT_UC:
				{
				alt3=3;
				}
				break;
			case GENBANK:
			case IMPORT_LC:
			case IMPORT_UC:
			case REGISTRY:
			case SBOL:
				{
				alt3=5;
				}
				break;
			case LC_FOR:
			case LC_FORALL:
			case LC_IF:
			case LC_WHILE:
			case UC_FOR:
			case UC_FORALL:
			case UC_IF:
			case UC_WHILE:
				{
				alt3=6;
				}
				break;
			case PERMUTE:
			case PRODUCT:
			case RANDOM_LC:
			case RANDOM_UC:
			case SIZEOF_LC:
			case SIZEOF_UC:
			case SIZE_LC:
			case SIZE_UC:
				{
				alt3=8;
				}
				break;
			case CREATE_LC:
			case CREATE_UC:
			case EXIT_LC:
			case EXIT_UC:
			case SAVE_LC:
			case SAVE_UC:
			case STORE_LC:
			case STORE_UC:
				{
				alt3=9;
				}
				break;
			case RETURN_LC:
			case RETURN_UC:
				{
				alt3=10;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 3, 0, input);
				throw nvae;
			}
			switch (alt3) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:870:3: includeStatement[defer] ( SEMIC )?
					{
					root_0 = (Object)adaptor.nil();


					pushFollow(FOLLOW_includeStatement_in_statement1107);
					includeStatement4=includeStatement(defer);
					state._fsp--;

					adaptor.addChild(root_0, includeStatement4.getTree());

					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:870:27: ( SEMIC )?
					int alt2=2;
					int LA2_0 = input.LA(1);
					if ( (LA2_0==SEMIC) ) {
						alt2=1;
					}
					switch (alt2) {
						case 1 :
							// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:870:28: SEMIC
							{
							SEMIC5=(Token)match(input,SEMIC,FOLLOW_SEMIC_in_statement1111); 
							SEMIC5_tree = (Object)adaptor.create(SEMIC5);
							adaptor.addChild(root_0, SEMIC5_tree);

							}
							break;

					}

					}
					break;
				case 2 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:871:4: declarationStatement[defer] SEMIC
					{
					root_0 = (Object)adaptor.nil();


					pushFollow(FOLLOW_declarationStatement_in_statement1118);
					declarationStatement6=declarationStatement(defer);
					state._fsp--;

					adaptor.addChild(root_0, declarationStatement6.getTree());

					SEMIC7=(Token)match(input,SEMIC,FOLLOW_SEMIC_in_statement1121); 
					SEMIC7_tree = (Object)adaptor.create(SEMIC7);
					adaptor.addChild(root_0, SEMIC7_tree);

					}
					break;
				case 3 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:872:4: printStatement[defer] SEMIC
					{
					root_0 = (Object)adaptor.nil();


					pushFollow(FOLLOW_printStatement_in_statement1127);
					printStatement8=printStatement(defer);
					state._fsp--;

					adaptor.addChild(root_0, printStatement8.getTree());

					SEMIC9=(Token)match(input,SEMIC,FOLLOW_SEMIC_in_statement1130); 
					SEMIC9_tree = (Object)adaptor.create(SEMIC9);
					adaptor.addChild(root_0, SEMIC9_tree);

					}
					break;
				case 4 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:873:4: assignment[defer] SEMIC
					{
					root_0 = (Object)adaptor.nil();


					pushFollow(FOLLOW_assignment_in_statement1135);
					assignment10=assignment(defer);
					state._fsp--;

					adaptor.addChild(root_0, assignment10.getTree());

					SEMIC11=(Token)match(input,SEMIC,FOLLOW_SEMIC_in_statement1138); 
					SEMIC11_tree = (Object)adaptor.create(SEMIC11);
					adaptor.addChild(root_0, SEMIC11_tree);

					}
					break;
				case 5 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:874:4: de= dataExchange[defer] SEMIC
					{
					root_0 = (Object)adaptor.nil();


					pushFollow(FOLLOW_dataExchange_in_statement1145);
					de=dataExchange(defer);
					state._fsp--;

					adaptor.addChild(root_0, de.getTree());

					SEMIC12=(Token)match(input,SEMIC,FOLLOW_SEMIC_in_statement1148); 
					SEMIC12_tree = (Object)adaptor.create(SEMIC12);
					adaptor.addChild(root_0, SEMIC12_tree);


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    try {
					        // iff there's no assignment to a LHS element,
					        // then we store the imported data into the 
					        // current scope's symbol tables
					        if(null != (de!=null?((EugeneParser.dataExchange_return)de).e:null)) {
					            this.interp.recursiveStoringOf((de!=null?((EugeneParser.dataExchange_return)de).e:null));
					        }
					    } catch(EugeneException ee) {
					        printError(ee.getLocalizedMessage());
					    }
					}	
						
					}
					break;
				case 6 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:888:4: imperativeStatements[defer]
					{
					root_0 = (Object)adaptor.nil();


					pushFollow(FOLLOW_imperativeStatements_in_statement1155);
					imperativeStatements13=imperativeStatements(defer);
					state._fsp--;

					adaptor.addChild(root_0, imperativeStatements13.getTree());

					}
					break;
				case 7 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:889:4: function_call[defer] SEMIC
					{
					root_0 = (Object)adaptor.nil();


					pushFollow(FOLLOW_function_call_in_statement1161);
					function_call14=function_call(defer);
					state._fsp--;

					adaptor.addChild(root_0, function_call14.getTree());

					SEMIC15=(Token)match(input,SEMIC,FOLLOW_SEMIC_in_statement1164); 
					SEMIC15_tree = (Object)adaptor.create(SEMIC15);
					adaptor.addChild(root_0, SEMIC15_tree);

					}
					break;
				case 8 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:890:4: bif= built_in_function[defer] SEMIC
					{
					root_0 = (Object)adaptor.nil();


					pushFollow(FOLLOW_built_in_function_in_statement1171);
					bif=built_in_function(defer);
					state._fsp--;

					adaptor.addChild(root_0, bif.getTree());

					SEMIC16=(Token)match(input,SEMIC,FOLLOW_SEMIC_in_statement1174); 
					SEMIC16_tree = (Object)adaptor.create(SEMIC16);
					adaptor.addChild(root_0, SEMIC16_tree);

					  
					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    try {
					        // iff there's no assignment to a LHS element,
					        // then we store the imported data into the 
					        // current scope's symbol tables
					        if(null != (bif!=null?((EugeneParser.built_in_function_return)bif).element:null)) {
					            this.interp.recursiveStoringOf((de!=null?((EugeneParser.dataExchange_return)de).e:null));
					        }
					    } catch(EugeneException ee) {
					        printError(ee.getLocalizedMessage());
					    }
					}	
						
					}
					break;
				case 9 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:905:4: stand_alone_function[defer] SEMIC
					{
					root_0 = (Object)adaptor.nil();


					pushFollow(FOLLOW_stand_alone_function_in_statement1183);
					stand_alone_function17=stand_alone_function(defer);
					state._fsp--;

					adaptor.addChild(root_0, stand_alone_function17.getTree());

					SEMIC18=(Token)match(input,SEMIC,FOLLOW_SEMIC_in_statement1186); 
					SEMIC18_tree = (Object)adaptor.create(SEMIC18);
					adaptor.addChild(root_0, SEMIC18_tree);

					}
					break;
				case 10 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:906:4: return_statement[defer] SEMIC
					{
					root_0 = (Object)adaptor.nil();


					pushFollow(FOLLOW_return_statement_in_statement1193);
					return_statement19=return_statement(defer);
					state._fsp--;

					adaptor.addChild(root_0, return_statement19.getTree());

					SEMIC20=(Token)match(input,SEMIC,FOLLOW_SEMIC_in_statement1196); 
					SEMIC20_tree = (Object)adaptor.create(SEMIC20);
					adaptor.addChild(root_0, SEMIC20_tree);

					}
					break;

			}
			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "statement"


	public static class declarationStatement_return extends ParserRuleReturnScope {
		public String name;
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "declarationStatement"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:915:1: declarationStatement[boolean defer] returns [String name] : (v= variableDeclaration[defer] | containerDeclaration[defer] | propertyDeclaration[defer] | typeDeclaration[defer] | instantiation[defer] | interactionDeclaration[defer] | ruleDeclaration[defer] | grammarDeclaration[defer] | deviceDeclaration[defer] );
	public final EugeneParser.declarationStatement_return declarationStatement(boolean defer) throws RecognitionException {
		EugeneParser.declarationStatement_return retval = new EugeneParser.declarationStatement_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		ParserRuleReturnScope v =null;
		ParserRuleReturnScope containerDeclaration21 =null;
		ParserRuleReturnScope propertyDeclaration22 =null;
		ParserRuleReturnScope typeDeclaration23 =null;
		ParserRuleReturnScope instantiation24 =null;
		ParserRuleReturnScope interactionDeclaration25 =null;
		ParserRuleReturnScope ruleDeclaration26 =null;
		ParserRuleReturnScope grammarDeclaration27 =null;
		ParserRuleReturnScope deviceDeclaration28 =null;


		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:917:2: (v= variableDeclaration[defer] | containerDeclaration[defer] | propertyDeclaration[defer] | typeDeclaration[defer] | instantiation[defer] | interactionDeclaration[defer] | ruleDeclaration[defer] | grammarDeclaration[defer] | deviceDeclaration[defer] )
			int alt4=9;
			switch ( input.LA(1) ) {
			case BOOL:
			case BOOLEAN:
			case NUM:
			case TXT:
				{
				alt4=1;
				}
				break;
			case ARRAY:
			case COLLECTION:
				{
				alt4=2;
				}
				break;
			case PROPERTY:
				{
				alt4=3;
				}
				break;
			case PART:
			case PART_TYPE:
			case TYPE:
				{
				alt4=4;
				}
				break;
			case ID:
				{
				int LA4_5 = input.LA(2);
				if ( (LA4_5==DOLLAR||LA4_5==ID) ) {
					alt4=5;
				}
				else if ( (LA4_5==LC_INDUCES||LA4_5==LC_REPRESSES||LA4_5==UC_INDUCES||LA4_5==UC_REPRESSES) ) {
					alt4=6;
				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 4, 5, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

				}
				break;
			case INTERACTION:
				{
				alt4=6;
				}
				break;
			case RULE:
				{
				alt4=7;
				}
				break;
			case GRAMMAR:
				{
				alt4=8;
				}
				break;
			case DEVICE:
				{
				alt4=9;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 4, 0, input);
				throw nvae;
			}
			switch (alt4) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:917:4: v= variableDeclaration[defer]
					{
					root_0 = (Object)adaptor.nil();


					pushFollow(FOLLOW_variableDeclaration_in_declarationStatement1217);
					v=variableDeclaration(defer);
					state._fsp--;

					adaptor.addChild(root_0, v.getTree());


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    retval.name = (v!=null?((EugeneParser.variableDeclaration_return)v).varname:null);
					}	
						
					}
					break;
				case 2 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:922:4: containerDeclaration[defer]
					{
					root_0 = (Object)adaptor.nil();


					pushFollow(FOLLOW_containerDeclaration_in_declarationStatement1225);
					containerDeclaration21=containerDeclaration(defer);
					state._fsp--;

					adaptor.addChild(root_0, containerDeclaration21.getTree());

					}
					break;
				case 3 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:923:4: propertyDeclaration[defer]
					{
					root_0 = (Object)adaptor.nil();


					pushFollow(FOLLOW_propertyDeclaration_in_declarationStatement1231);
					propertyDeclaration22=propertyDeclaration(defer);
					state._fsp--;

					adaptor.addChild(root_0, propertyDeclaration22.getTree());

					}
					break;
				case 4 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:924:4: typeDeclaration[defer]
					{
					root_0 = (Object)adaptor.nil();


					pushFollow(FOLLOW_typeDeclaration_in_declarationStatement1237);
					typeDeclaration23=typeDeclaration(defer);
					state._fsp--;

					adaptor.addChild(root_0, typeDeclaration23.getTree());

					}
					break;
				case 5 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:925:4: instantiation[defer]
					{
					root_0 = (Object)adaptor.nil();


					pushFollow(FOLLOW_instantiation_in_declarationStatement1243);
					instantiation24=instantiation(defer);
					state._fsp--;

					adaptor.addChild(root_0, instantiation24.getTree());

					}
					break;
				case 6 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:926:4: interactionDeclaration[defer]
					{
					root_0 = (Object)adaptor.nil();


					pushFollow(FOLLOW_interactionDeclaration_in_declarationStatement1249);
					interactionDeclaration25=interactionDeclaration(defer);
					state._fsp--;

					adaptor.addChild(root_0, interactionDeclaration25.getTree());

					}
					break;
				case 7 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:927:4: ruleDeclaration[defer]
					{
					root_0 = (Object)adaptor.nil();


					pushFollow(FOLLOW_ruleDeclaration_in_declarationStatement1255);
					ruleDeclaration26=ruleDeclaration(defer);
					state._fsp--;

					adaptor.addChild(root_0, ruleDeclaration26.getTree());

					}
					break;
				case 8 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:928:4: grammarDeclaration[defer]
					{
					root_0 = (Object)adaptor.nil();


					pushFollow(FOLLOW_grammarDeclaration_in_declarationStatement1261);
					grammarDeclaration27=grammarDeclaration(defer);
					state._fsp--;

					adaptor.addChild(root_0, grammarDeclaration27.getTree());

					}
					break;
				case 9 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:929:4: deviceDeclaration[defer]
					{
					root_0 = (Object)adaptor.nil();


					pushFollow(FOLLOW_deviceDeclaration_in_declarationStatement1267);
					deviceDeclaration28=deviceDeclaration(defer);
					state._fsp--;

					adaptor.addChild(root_0, deviceDeclaration28.getTree());

					}
					break;

			}
			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "declarationStatement"


	public static class variableDeclaration_return extends ParserRuleReturnScope {
		public String varname;
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "variableDeclaration"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:932:1: variableDeclaration[boolean defer] returns [String varname] : ( NUM n= numdecl[defer] | TXT t= txtdecl[defer] | TXT LEFTSBR RIGHTSBR tl= txtlistdecl[defer] | NUM LEFTSBR RIGHTSBR nl= numlistdecl[defer] | ( BOOLEAN | BOOL ) b= booldecl[defer] );
	public final EugeneParser.variableDeclaration_return variableDeclaration(boolean defer) throws RecognitionException {
		EugeneParser.variableDeclaration_return retval = new EugeneParser.variableDeclaration_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token NUM29=null;
		Token TXT30=null;
		Token TXT31=null;
		Token LEFTSBR32=null;
		Token RIGHTSBR33=null;
		Token NUM34=null;
		Token LEFTSBR35=null;
		Token RIGHTSBR36=null;
		Token set37=null;
		ParserRuleReturnScope n =null;
		ParserRuleReturnScope t =null;
		ParserRuleReturnScope tl =null;
		ParserRuleReturnScope nl =null;
		ParserRuleReturnScope b =null;

		Object NUM29_tree=null;
		Object TXT30_tree=null;
		Object TXT31_tree=null;
		Object LEFTSBR32_tree=null;
		Object RIGHTSBR33_tree=null;
		Object NUM34_tree=null;
		Object LEFTSBR35_tree=null;
		Object RIGHTSBR36_tree=null;
		Object set37_tree=null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:934:2: ( NUM n= numdecl[defer] | TXT t= txtdecl[defer] | TXT LEFTSBR RIGHTSBR tl= txtlistdecl[defer] | NUM LEFTSBR RIGHTSBR nl= numlistdecl[defer] | ( BOOLEAN | BOOL ) b= booldecl[defer] )
			int alt5=5;
			switch ( input.LA(1) ) {
			case NUM:
				{
				int LA5_1 = input.LA(2);
				if ( (LA5_1==LEFTSBR) ) {
					alt5=4;
				}
				else if ( (LA5_1==ID) ) {
					alt5=1;
				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 5, 1, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

				}
				break;
			case TXT:
				{
				int LA5_2 = input.LA(2);
				if ( (LA5_2==LEFTSBR) ) {
					alt5=3;
				}
				else if ( (LA5_2==ID) ) {
					alt5=2;
				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 5, 2, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

				}
				break;
			case BOOL:
			case BOOLEAN:
				{
				alt5=5;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 5, 0, input);
				throw nvae;
			}
			switch (alt5) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:934:4: NUM n= numdecl[defer]
					{
					root_0 = (Object)adaptor.nil();


					NUM29=(Token)match(input,NUM,FOLLOW_NUM_in_variableDeclaration1285); 
					NUM29_tree = (Object)adaptor.create(NUM29);
					adaptor.addChild(root_0, NUM29_tree);

					pushFollow(FOLLOW_numdecl_in_variableDeclaration1289);
					n=numdecl(defer);
					state._fsp--;

					adaptor.addChild(root_0, n.getTree());


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    retval.varname = (n!=null?((EugeneParser.numdecl_return)n).varname:null);
					}	
						
					}
					break;
				case 2 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:939:4: TXT t= txtdecl[defer]
					{
					root_0 = (Object)adaptor.nil();


					TXT30=(Token)match(input,TXT,FOLLOW_TXT_in_variableDeclaration1300); 
					TXT30_tree = (Object)adaptor.create(TXT30);
					adaptor.addChild(root_0, TXT30_tree);

					pushFollow(FOLLOW_txtdecl_in_variableDeclaration1304);
					t=txtdecl(defer);
					state._fsp--;

					adaptor.addChild(root_0, t.getTree());


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    retval.varname = (t!=null?((EugeneParser.txtdecl_return)t).varname:null);
					}	
						
					}
					break;
				case 3 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:944:4: TXT LEFTSBR RIGHTSBR tl= txtlistdecl[defer]
					{
					root_0 = (Object)adaptor.nil();


					TXT31=(Token)match(input,TXT,FOLLOW_TXT_in_variableDeclaration1315); 
					TXT31_tree = (Object)adaptor.create(TXT31);
					adaptor.addChild(root_0, TXT31_tree);

					LEFTSBR32=(Token)match(input,LEFTSBR,FOLLOW_LEFTSBR_in_variableDeclaration1317); 
					LEFTSBR32_tree = (Object)adaptor.create(LEFTSBR32);
					adaptor.addChild(root_0, LEFTSBR32_tree);

					RIGHTSBR33=(Token)match(input,RIGHTSBR,FOLLOW_RIGHTSBR_in_variableDeclaration1319); 
					RIGHTSBR33_tree = (Object)adaptor.create(RIGHTSBR33);
					adaptor.addChild(root_0, RIGHTSBR33_tree);

					pushFollow(FOLLOW_txtlistdecl_in_variableDeclaration1323);
					tl=txtlistdecl(defer);
					state._fsp--;

					adaptor.addChild(root_0, tl.getTree());


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    retval.varname = (tl!=null?((EugeneParser.txtlistdecl_return)tl).varname:null);
					}	
						
					}
					break;
				case 4 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:949:4: NUM LEFTSBR RIGHTSBR nl= numlistdecl[defer]
					{
					root_0 = (Object)adaptor.nil();


					NUM34=(Token)match(input,NUM,FOLLOW_NUM_in_variableDeclaration1334); 
					NUM34_tree = (Object)adaptor.create(NUM34);
					adaptor.addChild(root_0, NUM34_tree);

					LEFTSBR35=(Token)match(input,LEFTSBR,FOLLOW_LEFTSBR_in_variableDeclaration1336); 
					LEFTSBR35_tree = (Object)adaptor.create(LEFTSBR35);
					adaptor.addChild(root_0, LEFTSBR35_tree);

					RIGHTSBR36=(Token)match(input,RIGHTSBR,FOLLOW_RIGHTSBR_in_variableDeclaration1338); 
					RIGHTSBR36_tree = (Object)adaptor.create(RIGHTSBR36);
					adaptor.addChild(root_0, RIGHTSBR36_tree);

					pushFollow(FOLLOW_numlistdecl_in_variableDeclaration1342);
					nl=numlistdecl(defer);
					state._fsp--;

					adaptor.addChild(root_0, nl.getTree());


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    retval.varname = (nl!=null?((EugeneParser.numlistdecl_return)nl).varname:null);
					}	
						
					}
					break;
				case 5 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:954:4: ( BOOLEAN | BOOL ) b= booldecl[defer]
					{
					root_0 = (Object)adaptor.nil();


					set37=input.LT(1);
					if ( (input.LA(1) >= BOOL && input.LA(1) <= BOOLEAN) ) {
						input.consume();
						adaptor.addChild(root_0, (Object)adaptor.create(set37));
						state.errorRecovery=false;
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						throw mse;
					}
					pushFollow(FOLLOW_booldecl_in_variableDeclaration1361);
					b=booldecl(defer);
					state._fsp--;

					adaptor.addChild(root_0, b.getTree());


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    retval.varname = (b!=null?((EugeneParser.booldecl_return)b).varname:null);
					}	
						
					}
					break;

			}
			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "variableDeclaration"


	public static class numdecl_return extends ParserRuleReturnScope {
		public String varname;
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "numdecl"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:961:1: numdecl[boolean defer] returns [String varname] : ( ID ( COMMA numdecl[defer] )? | ID EQUALS (ex= expr[defer] ) ( COMMA numdecl[defer] )? );
	public final EugeneParser.numdecl_return numdecl(boolean defer) throws RecognitionException {
		EugeneParser.numdecl_return retval = new EugeneParser.numdecl_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token ID38=null;
		Token COMMA39=null;
		Token ID41=null;
		Token EQUALS42=null;
		Token COMMA43=null;
		ParserRuleReturnScope ex =null;
		ParserRuleReturnScope numdecl40 =null;
		ParserRuleReturnScope numdecl44 =null;

		Object ID38_tree=null;
		Object COMMA39_tree=null;
		Object ID41_tree=null;
		Object EQUALS42_tree=null;
		Object COMMA43_tree=null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:963:2: ( ID ( COMMA numdecl[defer] )? | ID EQUALS (ex= expr[defer] ) ( COMMA numdecl[defer] )? )
			int alt8=2;
			int LA8_0 = input.LA(1);
			if ( (LA8_0==ID) ) {
				int LA8_1 = input.LA(2);
				if ( (LA8_1==EQUALS) ) {
					alt8=2;
				}
				else if ( (LA8_1==COMMA||LA8_1==RIGHTP||LA8_1==SEMIC) ) {
					alt8=1;
				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 8, 1, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 8, 0, input);
				throw nvae;
			}

			switch (alt8) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:963:4: ID ( COMMA numdecl[defer] )?
					{
					root_0 = (Object)adaptor.nil();


					ID38=(Token)match(input,ID,FOLLOW_ID_in_numdecl1384); 
					ID38_tree = (Object)adaptor.create(ID38);
					adaptor.addChild(root_0, ID38_tree);


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    declareVariableNoValue((ID38!=null?ID38.getText():null), EugeneConstants.NUM);
					    retval.varname = (ID38!=null?ID38.getText():null);
					}
						
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:968:5: ( COMMA numdecl[defer] )?
					int alt6=2;
					int LA6_0 = input.LA(1);
					if ( (LA6_0==COMMA) ) {
						int LA6_1 = input.LA(2);
						if ( (LA6_1==ID) ) {
							alt6=1;
						}
					}
					switch (alt6) {
						case 1 :
							// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:968:6: COMMA numdecl[defer]
							{
							COMMA39=(Token)match(input,COMMA,FOLLOW_COMMA_in_numdecl1390); 
							COMMA39_tree = (Object)adaptor.create(COMMA39);
							adaptor.addChild(root_0, COMMA39_tree);

							pushFollow(FOLLOW_numdecl_in_numdecl1392);
							numdecl40=numdecl(defer);
							state._fsp--;

							adaptor.addChild(root_0, numdecl40.getTree());

							}
							break;

					}

					}
					break;
				case 2 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:969:4: ID EQUALS (ex= expr[defer] ) ( COMMA numdecl[defer] )?
					{
					root_0 = (Object)adaptor.nil();


					ID41=(Token)match(input,ID,FOLLOW_ID_in_numdecl1400); 
					ID41_tree = (Object)adaptor.create(ID41);
					adaptor.addChild(root_0, ID41_tree);

					EQUALS42=(Token)match(input,EQUALS,FOLLOW_EQUALS_in_numdecl1402); 
					EQUALS42_tree = (Object)adaptor.create(EQUALS42);
					adaptor.addChild(root_0, EQUALS42_tree);

					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:969:14: (ex= expr[defer] )
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:969:15: ex= expr[defer]
					{
					pushFollow(FOLLOW_expr_in_numdecl1407);
					ex=expr(defer);
					state._fsp--;

					adaptor.addChild(root_0, ex.getTree());

					}


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    declareVariableWithValueNum((ID41!=null?ID41.getText():null), (ex!=null?((EugeneParser.expr_return)ex).p:null), (ex!=null?((EugeneParser.expr_return)ex).index:0));
					    retval.varname = (ID41!=null?ID41.getText():null);
					}
						
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:974:5: ( COMMA numdecl[defer] )?
					int alt7=2;
					int LA7_0 = input.LA(1);
					if ( (LA7_0==COMMA) ) {
						int LA7_1 = input.LA(2);
						if ( (LA7_1==ID) ) {
							alt7=1;
						}
					}
					switch (alt7) {
						case 1 :
							// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:974:6: COMMA numdecl[defer]
							{
							COMMA43=(Token)match(input,COMMA,FOLLOW_COMMA_in_numdecl1415); 
							COMMA43_tree = (Object)adaptor.create(COMMA43);
							adaptor.addChild(root_0, COMMA43_tree);

							pushFollow(FOLLOW_numdecl_in_numdecl1417);
							numdecl44=numdecl(defer);
							state._fsp--;

							adaptor.addChild(root_0, numdecl44.getTree());

							}
							break;

					}

					}
					break;

			}
			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "numdecl"


	public static class txtdecl_return extends ParserRuleReturnScope {
		public String varname;
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "txtdecl"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:977:1: txtdecl[boolean defer] returns [String varname] : ( ID ( COMMA txtdecl[defer] )? |var= ID EQUALS let= expr[defer] ( COMMA txtdecl[defer] )? );
	public final EugeneParser.txtdecl_return txtdecl(boolean defer) throws RecognitionException {
		EugeneParser.txtdecl_return retval = new EugeneParser.txtdecl_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token var=null;
		Token ID45=null;
		Token COMMA46=null;
		Token EQUALS48=null;
		Token COMMA49=null;
		ParserRuleReturnScope let =null;
		ParserRuleReturnScope txtdecl47 =null;
		ParserRuleReturnScope txtdecl50 =null;

		Object var_tree=null;
		Object ID45_tree=null;
		Object COMMA46_tree=null;
		Object EQUALS48_tree=null;
		Object COMMA49_tree=null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:979:2: ( ID ( COMMA txtdecl[defer] )? |var= ID EQUALS let= expr[defer] ( COMMA txtdecl[defer] )? )
			int alt11=2;
			int LA11_0 = input.LA(1);
			if ( (LA11_0==ID) ) {
				int LA11_1 = input.LA(2);
				if ( (LA11_1==EQUALS) ) {
					alt11=2;
				}
				else if ( (LA11_1==COMMA||LA11_1==RIGHTP||LA11_1==SEMIC) ) {
					alt11=1;
				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 11, 1, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 11, 0, input);
				throw nvae;
			}

			switch (alt11) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:979:4: ID ( COMMA txtdecl[defer] )?
					{
					root_0 = (Object)adaptor.nil();


					ID45=(Token)match(input,ID,FOLLOW_ID_in_txtdecl1437); 
					ID45_tree = (Object)adaptor.create(ID45);
					adaptor.addChild(root_0, ID45_tree);


							if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
								declareVariableNoValue((ID45!=null?ID45.getText():null), EugeneConstants.TXT);
								retval.varname = (ID45!=null?ID45.getText():null);
							}
							
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:985:5: ( COMMA txtdecl[defer] )?
					int alt9=2;
					int LA9_0 = input.LA(1);
					if ( (LA9_0==COMMA) ) {
						int LA9_1 = input.LA(2);
						if ( (LA9_1==ID) ) {
							alt9=1;
						}
					}
					switch (alt9) {
						case 1 :
							// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:985:6: COMMA txtdecl[defer]
							{
							COMMA46=(Token)match(input,COMMA,FOLLOW_COMMA_in_txtdecl1444); 
							COMMA46_tree = (Object)adaptor.create(COMMA46);
							adaptor.addChild(root_0, COMMA46_tree);

							pushFollow(FOLLOW_txtdecl_in_txtdecl1446);
							txtdecl47=txtdecl(defer);
							state._fsp--;

							adaptor.addChild(root_0, txtdecl47.getTree());

							}
							break;

					}

					}
					break;
				case 2 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:987:4: var= ID EQUALS let= expr[defer] ( COMMA txtdecl[defer] )?
					{
					root_0 = (Object)adaptor.nil();


					var=(Token)match(input,ID,FOLLOW_ID_in_txtdecl1457); 
					var_tree = (Object)adaptor.create(var);
					adaptor.addChild(root_0, var_tree);

					EQUALS48=(Token)match(input,EQUALS,FOLLOW_EQUALS_in_txtdecl1459); 
					EQUALS48_tree = (Object)adaptor.create(EQUALS48);
					adaptor.addChild(root_0, EQUALS48_tree);

					pushFollow(FOLLOW_expr_in_txtdecl1463);
					let=expr(defer);
					state._fsp--;

					adaptor.addChild(root_0, let.getTree());


							if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
								declareVariableWithValueTxt((var!=null?var.getText():null), (let!=null?((EugeneParser.expr_return)let).p:null), (let!=null?((EugeneParser.expr_return)let).index:0));
								retval.varname = (var!=null?var.getText():null);
							}
							
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:993:5: ( COMMA txtdecl[defer] )?
					int alt10=2;
					int LA10_0 = input.LA(1);
					if ( (LA10_0==COMMA) ) {
						int LA10_1 = input.LA(2);
						if ( (LA10_1==ID) ) {
							alt10=1;
						}
					}
					switch (alt10) {
						case 1 :
							// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:993:6: COMMA txtdecl[defer]
							{
							COMMA49=(Token)match(input,COMMA,FOLLOW_COMMA_in_txtdecl1471); 
							COMMA49_tree = (Object)adaptor.create(COMMA49);
							adaptor.addChild(root_0, COMMA49_tree);

							pushFollow(FOLLOW_txtdecl_in_txtdecl1473);
							txtdecl50=txtdecl(defer);
							state._fsp--;

							adaptor.addChild(root_0, txtdecl50.getTree());

							}
							break;

					}

					}
					break;

			}
			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "txtdecl"


	public static class txtlistdecl_return extends ParserRuleReturnScope {
		public String varname;
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "txtlistdecl"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:996:1: txtlistdecl[boolean defer] returns [String varname] : ( ID ( COMMA txtlistdecl[defer] )? |var= ID EQUALS let= expr[defer] ( COMMA txtlistdecl[defer] )? );
	public final EugeneParser.txtlistdecl_return txtlistdecl(boolean defer) throws RecognitionException {
		EugeneParser.txtlistdecl_return retval = new EugeneParser.txtlistdecl_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token var=null;
		Token ID51=null;
		Token COMMA52=null;
		Token EQUALS54=null;
		Token COMMA55=null;
		ParserRuleReturnScope let =null;
		ParserRuleReturnScope txtlistdecl53 =null;
		ParserRuleReturnScope txtlistdecl56 =null;

		Object var_tree=null;
		Object ID51_tree=null;
		Object COMMA52_tree=null;
		Object EQUALS54_tree=null;
		Object COMMA55_tree=null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:998:2: ( ID ( COMMA txtlistdecl[defer] )? |var= ID EQUALS let= expr[defer] ( COMMA txtlistdecl[defer] )? )
			int alt14=2;
			int LA14_0 = input.LA(1);
			if ( (LA14_0==ID) ) {
				int LA14_1 = input.LA(2);
				if ( (LA14_1==EQUALS) ) {
					alt14=2;
				}
				else if ( (LA14_1==COMMA||LA14_1==RIGHTP||LA14_1==SEMIC) ) {
					alt14=1;
				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 14, 1, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 14, 0, input);
				throw nvae;
			}

			switch (alt14) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:998:4: ID ( COMMA txtlistdecl[defer] )?
					{
					root_0 = (Object)adaptor.nil();


					ID51=(Token)match(input,ID,FOLLOW_ID_in_txtlistdecl1493); 
					ID51_tree = (Object)adaptor.create(ID51);
					adaptor.addChild(root_0, ID51_tree);


							if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
								declareVariableNoValue((ID51!=null?ID51.getText():null), EugeneConstants.TXTLIST);
								retval.varname = (ID51!=null?ID51.getText():null);
							}
							
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1004:5: ( COMMA txtlistdecl[defer] )?
					int alt12=2;
					int LA12_0 = input.LA(1);
					if ( (LA12_0==COMMA) ) {
						int LA12_1 = input.LA(2);
						if ( (LA12_1==ID) ) {
							alt12=1;
						}
					}
					switch (alt12) {
						case 1 :
							// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1004:6: COMMA txtlistdecl[defer]
							{
							COMMA52=(Token)match(input,COMMA,FOLLOW_COMMA_in_txtlistdecl1500); 
							COMMA52_tree = (Object)adaptor.create(COMMA52);
							adaptor.addChild(root_0, COMMA52_tree);

							pushFollow(FOLLOW_txtlistdecl_in_txtlistdecl1502);
							txtlistdecl53=txtlistdecl(defer);
							state._fsp--;

							adaptor.addChild(root_0, txtlistdecl53.getTree());

							}
							break;

					}

					}
					break;
				case 2 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1005:4: var= ID EQUALS let= expr[defer] ( COMMA txtlistdecl[defer] )?
					{
					root_0 = (Object)adaptor.nil();


					var=(Token)match(input,ID,FOLLOW_ID_in_txtlistdecl1512); 
					var_tree = (Object)adaptor.create(var);
					adaptor.addChild(root_0, var_tree);

					EQUALS54=(Token)match(input,EQUALS,FOLLOW_EQUALS_in_txtlistdecl1514); 
					EQUALS54_tree = (Object)adaptor.create(EQUALS54);
					adaptor.addChild(root_0, EQUALS54_tree);

					typeList = EugeneConstants.TXT;
					pushFollow(FOLLOW_expr_in_txtlistdecl1520);
					let=expr(defer);
					state._fsp--;

					adaptor.addChild(root_0, let.getTree());


							if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
								declareVariableWithValueTxtList((var!=null?var.getText():null), (let!=null?((EugeneParser.expr_return)let).p:null));
								retval.varname = (var!=null?var.getText():null);
							}
							
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1011:5: ( COMMA txtlistdecl[defer] )?
					int alt13=2;
					int LA13_0 = input.LA(1);
					if ( (LA13_0==COMMA) ) {
						int LA13_1 = input.LA(2);
						if ( (LA13_1==ID) ) {
							alt13=1;
						}
					}
					switch (alt13) {
						case 1 :
							// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1011:6: COMMA txtlistdecl[defer]
							{
							COMMA55=(Token)match(input,COMMA,FOLLOW_COMMA_in_txtlistdecl1528); 
							COMMA55_tree = (Object)adaptor.create(COMMA55);
							adaptor.addChild(root_0, COMMA55_tree);

							pushFollow(FOLLOW_txtlistdecl_in_txtlistdecl1530);
							txtlistdecl56=txtlistdecl(defer);
							state._fsp--;

							adaptor.addChild(root_0, txtlistdecl56.getTree());

							}
							break;

					}

					}
					break;

			}
			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "txtlistdecl"


	public static class numlistdecl_return extends ParserRuleReturnScope {
		public String varname;
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "numlistdecl"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1014:1: numlistdecl[boolean defer] returns [String varname] : ( ID ( COMMA numlistdecl[defer] )? |var= ID EQUALS let= expr[defer] ( COMMA numlistdecl[defer] )? );
	public final EugeneParser.numlistdecl_return numlistdecl(boolean defer) throws RecognitionException {
		EugeneParser.numlistdecl_return retval = new EugeneParser.numlistdecl_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token var=null;
		Token ID57=null;
		Token COMMA58=null;
		Token EQUALS60=null;
		Token COMMA61=null;
		ParserRuleReturnScope let =null;
		ParserRuleReturnScope numlistdecl59 =null;
		ParserRuleReturnScope numlistdecl62 =null;

		Object var_tree=null;
		Object ID57_tree=null;
		Object COMMA58_tree=null;
		Object EQUALS60_tree=null;
		Object COMMA61_tree=null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1016:2: ( ID ( COMMA numlistdecl[defer] )? |var= ID EQUALS let= expr[defer] ( COMMA numlistdecl[defer] )? )
			int alt17=2;
			int LA17_0 = input.LA(1);
			if ( (LA17_0==ID) ) {
				int LA17_1 = input.LA(2);
				if ( (LA17_1==EQUALS) ) {
					alt17=2;
				}
				else if ( (LA17_1==COMMA||LA17_1==RIGHTP||LA17_1==SEMIC) ) {
					alt17=1;
				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 17, 1, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 17, 0, input);
				throw nvae;
			}

			switch (alt17) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1016:4: ID ( COMMA numlistdecl[defer] )?
					{
					root_0 = (Object)adaptor.nil();


					ID57=(Token)match(input,ID,FOLLOW_ID_in_numlistdecl1550); 
					ID57_tree = (Object)adaptor.create(ID57);
					adaptor.addChild(root_0, ID57_tree);


							if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
								declareVariableNoValue((ID57!=null?ID57.getText():null), EugeneConstants.NUMLIST);
								retval.varname = (ID57!=null?ID57.getText():null);
							}
							
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1022:5: ( COMMA numlistdecl[defer] )?
					int alt15=2;
					int LA15_0 = input.LA(1);
					if ( (LA15_0==COMMA) ) {
						int LA15_1 = input.LA(2);
						if ( (LA15_1==ID) ) {
							alt15=1;
						}
					}
					switch (alt15) {
						case 1 :
							// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1022:6: COMMA numlistdecl[defer]
							{
							COMMA58=(Token)match(input,COMMA,FOLLOW_COMMA_in_numlistdecl1557); 
							COMMA58_tree = (Object)adaptor.create(COMMA58);
							adaptor.addChild(root_0, COMMA58_tree);

							pushFollow(FOLLOW_numlistdecl_in_numlistdecl1559);
							numlistdecl59=numlistdecl(defer);
							state._fsp--;

							adaptor.addChild(root_0, numlistdecl59.getTree());

							}
							break;

					}

					}
					break;
				case 2 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1023:4: var= ID EQUALS let= expr[defer] ( COMMA numlistdecl[defer] )?
					{
					root_0 = (Object)adaptor.nil();


					var=(Token)match(input,ID,FOLLOW_ID_in_numlistdecl1569); 
					var_tree = (Object)adaptor.create(var);
					adaptor.addChild(root_0, var_tree);

					EQUALS60=(Token)match(input,EQUALS,FOLLOW_EQUALS_in_numlistdecl1571); 
					EQUALS60_tree = (Object)adaptor.create(EQUALS60);
					adaptor.addChild(root_0, EQUALS60_tree);

					 typeList = EugeneConstants.NUM;
					pushFollow(FOLLOW_expr_in_numlistdecl1576);
					let=expr(defer);
					state._fsp--;

					adaptor.addChild(root_0, let.getTree());


							if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
								declareVariableWithValueNumList((var!=null?var.getText():null), (let!=null?((EugeneParser.expr_return)let).p:null));
								retval.varname = (var!=null?var.getText():null);
							}
							
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1029:5: ( COMMA numlistdecl[defer] )?
					int alt16=2;
					int LA16_0 = input.LA(1);
					if ( (LA16_0==COMMA) ) {
						int LA16_1 = input.LA(2);
						if ( (LA16_1==ID) ) {
							alt16=1;
						}
					}
					switch (alt16) {
						case 1 :
							// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1029:6: COMMA numlistdecl[defer]
							{
							COMMA61=(Token)match(input,COMMA,FOLLOW_COMMA_in_numlistdecl1584); 
							COMMA61_tree = (Object)adaptor.create(COMMA61);
							adaptor.addChild(root_0, COMMA61_tree);

							pushFollow(FOLLOW_numlistdecl_in_numlistdecl1586);
							numlistdecl62=numlistdecl(defer);
							state._fsp--;

							adaptor.addChild(root_0, numlistdecl62.getTree());

							}
							break;

					}

					}
					break;

			}
			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "numlistdecl"


	public static class booldecl_return extends ParserRuleReturnScope {
		public String varname;
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "booldecl"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1032:1: booldecl[boolean defer] returns [String varname] : ( ID ( COMMA booldecl[defer] )? |var= ID EQUALS let= expr[defer] );
	public final EugeneParser.booldecl_return booldecl(boolean defer) throws RecognitionException {
		EugeneParser.booldecl_return retval = new EugeneParser.booldecl_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token var=null;
		Token ID63=null;
		Token COMMA64=null;
		Token EQUALS66=null;
		ParserRuleReturnScope let =null;
		ParserRuleReturnScope booldecl65 =null;

		Object var_tree=null;
		Object ID63_tree=null;
		Object COMMA64_tree=null;
		Object EQUALS66_tree=null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1034:2: ( ID ( COMMA booldecl[defer] )? |var= ID EQUALS let= expr[defer] )
			int alt19=2;
			int LA19_0 = input.LA(1);
			if ( (LA19_0==ID) ) {
				int LA19_1 = input.LA(2);
				if ( (LA19_1==EQUALS) ) {
					alt19=2;
				}
				else if ( (LA19_1==COMMA||LA19_1==RIGHTP||LA19_1==SEMIC) ) {
					alt19=1;
				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 19, 1, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 19, 0, input);
				throw nvae;
			}

			switch (alt19) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1034:4: ID ( COMMA booldecl[defer] )?
					{
					root_0 = (Object)adaptor.nil();


					ID63=(Token)match(input,ID,FOLLOW_ID_in_booldecl1606); 
					ID63_tree = (Object)adaptor.create(ID63);
					adaptor.addChild(root_0, ID63_tree);


							if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
								declareVariableNoValue((ID63!=null?ID63.getText():null), EugeneConstants.BOOLEAN);
								retval.varname = (ID63!=null?ID63.getText():null);
							}
							
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1040:5: ( COMMA booldecl[defer] )?
					int alt18=2;
					int LA18_0 = input.LA(1);
					if ( (LA18_0==COMMA) ) {
						int LA18_1 = input.LA(2);
						if ( (LA18_1==ID) ) {
							alt18=1;
						}
					}
					switch (alt18) {
						case 1 :
							// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1040:6: COMMA booldecl[defer]
							{
							COMMA64=(Token)match(input,COMMA,FOLLOW_COMMA_in_booldecl1613); 
							COMMA64_tree = (Object)adaptor.create(COMMA64);
							adaptor.addChild(root_0, COMMA64_tree);

							pushFollow(FOLLOW_booldecl_in_booldecl1615);
							booldecl65=booldecl(defer);
							state._fsp--;

							adaptor.addChild(root_0, booldecl65.getTree());

							}
							break;

					}

					}
					break;
				case 2 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1041:4: var= ID EQUALS let= expr[defer]
					{
					root_0 = (Object)adaptor.nil();


					var=(Token)match(input,ID,FOLLOW_ID_in_booldecl1625); 
					var_tree = (Object)adaptor.create(var);
					adaptor.addChild(root_0, var_tree);

					EQUALS66=(Token)match(input,EQUALS,FOLLOW_EQUALS_in_booldecl1627); 
					EQUALS66_tree = (Object)adaptor.create(EQUALS66);
					adaptor.addChild(root_0, EQUALS66_tree);

					pushFollow(FOLLOW_expr_in_booldecl1631);
					let=expr(defer);
					state._fsp--;

					adaptor.addChild(root_0, let.getTree());


							if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
								declareVariableWithValueBool((var!=null?var.getText():null), (let!=null?((EugeneParser.expr_return)let).p:null));
								retval.varname = (var!=null?var.getText():null);
							}
							
					}
					break;

			}
			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "booldecl"


	public static class propertyDeclaration_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "propertyDeclaration"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1050:1: propertyDeclaration[boolean defer] : PROPERTY nameToken= ID LEFTP typeToken= propertyType RIGHTP ;
	public final EugeneParser.propertyDeclaration_return propertyDeclaration(boolean defer) throws RecognitionException {
		EugeneParser.propertyDeclaration_return retval = new EugeneParser.propertyDeclaration_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token nameToken=null;
		Token PROPERTY67=null;
		Token LEFTP68=null;
		Token RIGHTP69=null;
		ParserRuleReturnScope typeToken =null;

		Object nameToken_tree=null;
		Object PROPERTY67_tree=null;
		Object LEFTP68_tree=null;
		Object RIGHTP69_tree=null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1051:2: ( PROPERTY nameToken= ID LEFTP typeToken= propertyType RIGHTP )
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1051:4: PROPERTY nameToken= ID LEFTP typeToken= propertyType RIGHTP
			{
			root_0 = (Object)adaptor.nil();


			PROPERTY67=(Token)match(input,PROPERTY,FOLLOW_PROPERTY_in_propertyDeclaration1649); 
			PROPERTY67_tree = (Object)adaptor.create(PROPERTY67);
			adaptor.addChild(root_0, PROPERTY67_tree);

			nameToken=(Token)match(input,ID,FOLLOW_ID_in_propertyDeclaration1653); 
			nameToken_tree = (Object)adaptor.create(nameToken);
			adaptor.addChild(root_0, nameToken_tree);

			LEFTP68=(Token)match(input,LEFTP,FOLLOW_LEFTP_in_propertyDeclaration1655); 
			LEFTP68_tree = (Object)adaptor.create(LEFTP68);
			adaptor.addChild(root_0, LEFTP68_tree);

			pushFollow(FOLLOW_propertyType_in_propertyDeclaration1659);
			typeToken=propertyType();
			state._fsp--;

			adaptor.addChild(root_0, typeToken.getTree());

			RIGHTP69=(Token)match(input,RIGHTP,FOLLOW_RIGHTP_in_propertyDeclaration1661); 
			RIGHTP69_tree = (Object)adaptor.create(RIGHTP69);
			adaptor.addChild(root_0, RIGHTP69_tree);


			if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
			    try {
			        interp.createProperty(
			            (nameToken!=null?nameToken.getText():null), 
			            (typeToken!=null?((EugeneParser.propertyType_return)typeToken).type:null));
			    } catch(EugeneException ee) {
			        printError(ee.getMessage());
			    }
			}
			        
			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "propertyDeclaration"


	public static class propertyType_return extends ParserRuleReturnScope {
		public String type;
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "propertyType"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1064:1: propertyType returns [String type] : ( TXT | TXT LEFTSBR RIGHTSBR | NUM | NUM LEFTSBR RIGHTSBR | ( BOOLEAN | BOOL ) );
	public final EugeneParser.propertyType_return propertyType() throws RecognitionException {
		EugeneParser.propertyType_return retval = new EugeneParser.propertyType_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token TXT70=null;
		Token TXT71=null;
		Token LEFTSBR72=null;
		Token RIGHTSBR73=null;
		Token NUM74=null;
		Token NUM75=null;
		Token LEFTSBR76=null;
		Token RIGHTSBR77=null;
		Token set78=null;

		Object TXT70_tree=null;
		Object TXT71_tree=null;
		Object LEFTSBR72_tree=null;
		Object RIGHTSBR73_tree=null;
		Object NUM74_tree=null;
		Object NUM75_tree=null;
		Object LEFTSBR76_tree=null;
		Object RIGHTSBR77_tree=null;
		Object set78_tree=null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1066:2: ( TXT | TXT LEFTSBR RIGHTSBR | NUM | NUM LEFTSBR RIGHTSBR | ( BOOLEAN | BOOL ) )
			int alt20=5;
			switch ( input.LA(1) ) {
			case TXT:
				{
				int LA20_1 = input.LA(2);
				if ( (LA20_1==LEFTSBR) ) {
					alt20=2;
				}
				else if ( (LA20_1==RIGHTP) ) {
					alt20=1;
				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 20, 1, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

				}
				break;
			case NUM:
				{
				int LA20_2 = input.LA(2);
				if ( (LA20_2==LEFTSBR) ) {
					alt20=4;
				}
				else if ( (LA20_2==RIGHTP) ) {
					alt20=3;
				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 20, 2, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

				}
				break;
			case BOOL:
			case BOOLEAN:
				{
				alt20=5;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 20, 0, input);
				throw nvae;
			}
			switch (alt20) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1066:4: TXT
					{
					root_0 = (Object)adaptor.nil();


					TXT70=(Token)match(input,TXT,FOLLOW_TXT_in_propertyType1680); 
					TXT70_tree = (Object)adaptor.create(TXT70);
					adaptor.addChild(root_0, TXT70_tree);


					retval.type = EugeneConstants.TXT;	
						
					}
					break;
				case 2 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1069:4: TXT LEFTSBR RIGHTSBR
					{
					root_0 = (Object)adaptor.nil();


					TXT71=(Token)match(input,TXT,FOLLOW_TXT_in_propertyType1687); 
					TXT71_tree = (Object)adaptor.create(TXT71);
					adaptor.addChild(root_0, TXT71_tree);

					LEFTSBR72=(Token)match(input,LEFTSBR,FOLLOW_LEFTSBR_in_propertyType1689); 
					LEFTSBR72_tree = (Object)adaptor.create(LEFTSBR72);
					adaptor.addChild(root_0, LEFTSBR72_tree);

					RIGHTSBR73=(Token)match(input,RIGHTSBR,FOLLOW_RIGHTSBR_in_propertyType1691); 
					RIGHTSBR73_tree = (Object)adaptor.create(RIGHTSBR73);
					adaptor.addChild(root_0, RIGHTSBR73_tree);


					retval.type = EugeneConstants.TXTLIST;	
						
					}
					break;
				case 3 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1072:4: NUM
					{
					root_0 = (Object)adaptor.nil();


					NUM74=(Token)match(input,NUM,FOLLOW_NUM_in_propertyType1698); 
					NUM74_tree = (Object)adaptor.create(NUM74);
					adaptor.addChild(root_0, NUM74_tree);


					retval.type = EugeneConstants.NUM;	
						
					}
					break;
				case 4 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1075:4: NUM LEFTSBR RIGHTSBR
					{
					root_0 = (Object)adaptor.nil();


					NUM75=(Token)match(input,NUM,FOLLOW_NUM_in_propertyType1706); 
					NUM75_tree = (Object)adaptor.create(NUM75);
					adaptor.addChild(root_0, NUM75_tree);

					LEFTSBR76=(Token)match(input,LEFTSBR,FOLLOW_LEFTSBR_in_propertyType1708); 
					LEFTSBR76_tree = (Object)adaptor.create(LEFTSBR76);
					adaptor.addChild(root_0, LEFTSBR76_tree);

					RIGHTSBR77=(Token)match(input,RIGHTSBR,FOLLOW_RIGHTSBR_in_propertyType1710); 
					RIGHTSBR77_tree = (Object)adaptor.create(RIGHTSBR77);
					adaptor.addChild(root_0, RIGHTSBR77_tree);


					retval.type = EugeneConstants.NUMLIST;	
						
					}
					break;
				case 5 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1078:4: ( BOOLEAN | BOOL )
					{
					root_0 = (Object)adaptor.nil();


					set78=input.LT(1);
					if ( (input.LA(1) >= BOOL && input.LA(1) <= BOOLEAN) ) {
						input.consume();
						adaptor.addChild(root_0, (Object)adaptor.create(set78));
						state.errorRecovery=false;
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						throw mse;
					}

					retval.type = EugeneConstants.BOOLEAN;	
						
					}
					break;

			}
			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "propertyType"


	public static class typeDeclaration_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "typeDeclaration"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1087:1: typeDeclaration[boolean defer] : ( partTypeDeclaration[defer] | ( TYPE ) nameToken= ID ( LEFTP (lstToken= listOfIDs[defer] )? RIGHTP )? );
	public final EugeneParser.typeDeclaration_return typeDeclaration(boolean defer) throws RecognitionException {
		EugeneParser.typeDeclaration_return retval = new EugeneParser.typeDeclaration_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token nameToken=null;
		Token TYPE80=null;
		Token LEFTP81=null;
		Token RIGHTP82=null;
		ParserRuleReturnScope lstToken =null;
		ParserRuleReturnScope partTypeDeclaration79 =null;

		Object nameToken_tree=null;
		Object TYPE80_tree=null;
		Object LEFTP81_tree=null;
		Object RIGHTP82_tree=null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1088:2: ( partTypeDeclaration[defer] | ( TYPE ) nameToken= ID ( LEFTP (lstToken= listOfIDs[defer] )? RIGHTP )? )
			int alt23=2;
			int LA23_0 = input.LA(1);
			if ( ((LA23_0 >= PART && LA23_0 <= PART_TYPE)) ) {
				alt23=1;
			}
			else if ( (LA23_0==TYPE) ) {
				alt23=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 23, 0, input);
				throw nvae;
			}

			switch (alt23) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1088:4: partTypeDeclaration[defer]
					{
					root_0 = (Object)adaptor.nil();


					pushFollow(FOLLOW_partTypeDeclaration_in_typeDeclaration1739);
					partTypeDeclaration79=partTypeDeclaration(defer);
					state._fsp--;

					adaptor.addChild(root_0, partTypeDeclaration79.getTree());

					}
					break;
				case 2 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1089:4: ( TYPE ) nameToken= ID ( LEFTP (lstToken= listOfIDs[defer] )? RIGHTP )?
					{
					root_0 = (Object)adaptor.nil();


					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1089:4: ( TYPE )
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1089:5: TYPE
					{
					TYPE80=(Token)match(input,TYPE,FOLLOW_TYPE_in_typeDeclaration1746); 
					TYPE80_tree = (Object)adaptor.create(TYPE80);
					adaptor.addChild(root_0, TYPE80_tree);

					}

					nameToken=(Token)match(input,ID,FOLLOW_ID_in_typeDeclaration1751); 
					nameToken_tree = (Object)adaptor.create(nameToken);
					adaptor.addChild(root_0, nameToken_tree);

					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1089:24: ( LEFTP (lstToken= listOfIDs[defer] )? RIGHTP )?
					int alt22=2;
					int LA22_0 = input.LA(1);
					if ( (LA22_0==LEFTP) ) {
						alt22=1;
					}
					switch (alt22) {
						case 1 :
							// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1089:25: LEFTP (lstToken= listOfIDs[defer] )? RIGHTP
							{
							LEFTP81=(Token)match(input,LEFTP,FOLLOW_LEFTP_in_typeDeclaration1754); 
							LEFTP81_tree = (Object)adaptor.create(LEFTP81);
							adaptor.addChild(root_0, LEFTP81_tree);

							// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1089:31: (lstToken= listOfIDs[defer] )?
							int alt21=2;
							int LA21_0 = input.LA(1);
							if ( (LA21_0==ID) ) {
								alt21=1;
							}
							switch (alt21) {
								case 1 :
									// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1089:32: lstToken= listOfIDs[defer]
									{
									pushFollow(FOLLOW_listOfIDs_in_typeDeclaration1759);
									lstToken=listOfIDs(defer);
									state._fsp--;

									adaptor.addChild(root_0, lstToken.getTree());

									}
									break;

							}

							RIGHTP82=(Token)match(input,RIGHTP,FOLLOW_RIGHTP_in_typeDeclaration1764); 
							RIGHTP82_tree = (Object)adaptor.create(RIGHTP82);
							adaptor.addChild(root_0, RIGHTP82_tree);

							}
							break;

					}


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    try {
					        interp.createType(
					            (nameToken!=null?nameToken.getText():null), 
					            (lstToken!=null?((EugeneParser.listOfIDs_return)lstToken).lstElements:null));
					    } catch(EugeneException ee) {
					        printError(ee.getMessage());
					    }
					}
					        
					}
					break;

			}
			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "typeDeclaration"


	public static class partTypeDeclaration_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "partTypeDeclaration"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1102:1: partTypeDeclaration[boolean defer] : ( PART | PART_TYPE ) nameToken= ID ( LEFTP (lstToken= listOfIDs[defer] )? RIGHTP )? ;
	public final EugeneParser.partTypeDeclaration_return partTypeDeclaration(boolean defer) throws RecognitionException {
		EugeneParser.partTypeDeclaration_return retval = new EugeneParser.partTypeDeclaration_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token nameToken=null;
		Token set83=null;
		Token LEFTP84=null;
		Token RIGHTP85=null;
		ParserRuleReturnScope lstToken =null;

		Object nameToken_tree=null;
		Object set83_tree=null;
		Object LEFTP84_tree=null;
		Object RIGHTP85_tree=null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1103:2: ( ( PART | PART_TYPE ) nameToken= ID ( LEFTP (lstToken= listOfIDs[defer] )? RIGHTP )? )
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1103:4: ( PART | PART_TYPE ) nameToken= ID ( LEFTP (lstToken= listOfIDs[defer] )? RIGHTP )?
			{
			root_0 = (Object)adaptor.nil();


			set83=input.LT(1);
			if ( (input.LA(1) >= PART && input.LA(1) <= PART_TYPE) ) {
				input.consume();
				adaptor.addChild(root_0, (Object)adaptor.create(set83));
				state.errorRecovery=false;
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				throw mse;
			}
			nameToken=(Token)match(input,ID,FOLLOW_ID_in_partTypeDeclaration1792); 
			nameToken_tree = (Object)adaptor.create(nameToken);
			adaptor.addChild(root_0, nameToken_tree);

			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1103:35: ( LEFTP (lstToken= listOfIDs[defer] )? RIGHTP )?
			int alt25=2;
			int LA25_0 = input.LA(1);
			if ( (LA25_0==LEFTP) ) {
				alt25=1;
			}
			switch (alt25) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1103:36: LEFTP (lstToken= listOfIDs[defer] )? RIGHTP
					{
					LEFTP84=(Token)match(input,LEFTP,FOLLOW_LEFTP_in_partTypeDeclaration1795); 
					LEFTP84_tree = (Object)adaptor.create(LEFTP84);
					adaptor.addChild(root_0, LEFTP84_tree);

					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1103:42: (lstToken= listOfIDs[defer] )?
					int alt24=2;
					int LA24_0 = input.LA(1);
					if ( (LA24_0==ID) ) {
						alt24=1;
					}
					switch (alt24) {
						case 1 :
							// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1103:43: lstToken= listOfIDs[defer]
							{
							pushFollow(FOLLOW_listOfIDs_in_partTypeDeclaration1800);
							lstToken=listOfIDs(defer);
							state._fsp--;

							adaptor.addChild(root_0, lstToken.getTree());

							}
							break;

					}

					RIGHTP85=(Token)match(input,RIGHTP,FOLLOW_RIGHTP_in_partTypeDeclaration1805); 
					RIGHTP85_tree = (Object)adaptor.create(RIGHTP85);
					adaptor.addChild(root_0, RIGHTP85_tree);

					}
					break;

			}


			if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
			    try {
			        interp.createPartType(
			            (nameToken!=null?nameToken.getText():null), 
			            (lstToken!=null?((EugeneParser.listOfIDs_return)lstToken).lstElements:null));
			    } catch(EugeneException ee) {
			        printError(ee.getMessage());
			    }
			}
			        
			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "partTypeDeclaration"


	public static class containerDeclaration_return extends ParserRuleReturnScope {
		public NamedElement ne;
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "containerDeclaration"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1119:1: containerDeclaration[boolean defer] returns [NamedElement ne] : (c= COLLECTION | (a= ARRAY ( LEFTSBR RIGHTSBR )? ) ) name= ID ( LEFTP ( list_of_declarations[defer] )? RIGHTP )? ;
	public final EugeneParser.containerDeclaration_return containerDeclaration(boolean defer) throws RecognitionException {
		EugeneParser.containerDeclaration_return retval = new EugeneParser.containerDeclaration_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token c=null;
		Token a=null;
		Token name=null;
		Token LEFTSBR86=null;
		Token RIGHTSBR87=null;
		Token LEFTP88=null;
		Token RIGHTP90=null;
		ParserRuleReturnScope list_of_declarations89 =null;

		Object c_tree=null;
		Object a_tree=null;
		Object name_tree=null;
		Object LEFTSBR86_tree=null;
		Object RIGHTSBR87_tree=null;
		Object LEFTP88_tree=null;
		Object RIGHTP90_tree=null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1121:2: ( (c= COLLECTION | (a= ARRAY ( LEFTSBR RIGHTSBR )? ) ) name= ID ( LEFTP ( list_of_declarations[defer] )? RIGHTP )? )
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1121:4: (c= COLLECTION | (a= ARRAY ( LEFTSBR RIGHTSBR )? ) ) name= ID ( LEFTP ( list_of_declarations[defer] )? RIGHTP )?
			{
			root_0 = (Object)adaptor.nil();


			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1121:4: (c= COLLECTION | (a= ARRAY ( LEFTSBR RIGHTSBR )? ) )
			int alt27=2;
			int LA27_0 = input.LA(1);
			if ( (LA27_0==COLLECTION) ) {
				alt27=1;
			}
			else if ( (LA27_0==ARRAY) ) {
				alt27=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 27, 0, input);
				throw nvae;
			}

			switch (alt27) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1121:5: c= COLLECTION
					{
					c=(Token)match(input,COLLECTION,FOLLOW_COLLECTION_in_containerDeclaration1832); 
					c_tree = (Object)adaptor.create(c);
					adaptor.addChild(root_0, c_tree);

					}
					break;
				case 2 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1121:20: (a= ARRAY ( LEFTSBR RIGHTSBR )? )
					{
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1121:20: (a= ARRAY ( LEFTSBR RIGHTSBR )? )
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1121:21: a= ARRAY ( LEFTSBR RIGHTSBR )?
					{
					a=(Token)match(input,ARRAY,FOLLOW_ARRAY_in_containerDeclaration1839); 
					a_tree = (Object)adaptor.create(a);
					adaptor.addChild(root_0, a_tree);

					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1121:29: ( LEFTSBR RIGHTSBR )?
					int alt26=2;
					int LA26_0 = input.LA(1);
					if ( (LA26_0==LEFTSBR) ) {
						alt26=1;
					}
					switch (alt26) {
						case 1 :
							// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1121:30: LEFTSBR RIGHTSBR
							{
							LEFTSBR86=(Token)match(input,LEFTSBR,FOLLOW_LEFTSBR_in_containerDeclaration1842); 
							LEFTSBR86_tree = (Object)adaptor.create(LEFTSBR86);
							adaptor.addChild(root_0, LEFTSBR86_tree);

							RIGHTSBR87=(Token)match(input,RIGHTSBR,FOLLOW_RIGHTSBR_in_containerDeclaration1844); 
							RIGHTSBR87_tree = (Object)adaptor.create(RIGHTSBR87);
							adaptor.addChild(root_0, RIGHTSBR87_tree);

							}
							break;

					}

					}

					}
					break;

			}

			name=(Token)match(input,ID,FOLLOW_ID_in_containerDeclaration1852); 
			name_tree = (Object)adaptor.create(name);
			adaptor.addChild(root_0, name_tree);


			if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
			    if(null != c) {
			        retval.ne = new EugeneCollection((name!=null?name.getText():null));
			    } else if(null != a) {
			        retval.ne = new EugeneArray((name!=null?name.getText():null));
			    }

			    /*
			     * push it on the stack
			     */
			    this.interp.push((StackElement)retval.ne);
			    
			}	
				
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1135:4: ( LEFTP ( list_of_declarations[defer] )? RIGHTP )?
			int alt29=2;
			int LA29_0 = input.LA(1);
			if ( (LA29_0==LEFTP) ) {
				alt29=1;
			}
			switch (alt29) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1135:5: LEFTP ( list_of_declarations[defer] )? RIGHTP
					{
					LEFTP88=(Token)match(input,LEFTP,FOLLOW_LEFTP_in_containerDeclaration1857); 
					LEFTP88_tree = (Object)adaptor.create(LEFTP88);
					adaptor.addChild(root_0, LEFTP88_tree);

					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1135:11: ( list_of_declarations[defer] )?
					int alt28=2;
					int LA28_0 = input.LA(1);
					if ( (LA28_0==ARRAY||(LA28_0 >= BOOL && LA28_0 <= COLLECTION)||LA28_0==DEVICE||LA28_0==DOLLAR||(LA28_0 >= FALSE_LC && LA28_0 <= FALSE_UC)||LA28_0==GRAMMAR||LA28_0==ID||LA28_0==INTERACTION||(LA28_0 >= LEFTP && LA28_0 <= LEFTSBR)||LA28_0==MINUS||(LA28_0 >= NUM && LA28_0 <= NUMBER)||(LA28_0 >= PART && LA28_0 <= PERMUTE)||(LA28_0 >= PRODUCT && LA28_0 <= RANDOM_UC)||LA28_0==REAL||LA28_0==RULE||(LA28_0 >= SIZEOF_LC && LA28_0 <= SIZE_UC)||(LA28_0 >= STRING && LA28_0 <= TYPE)) ) {
						alt28=1;
					}
					switch (alt28) {
						case 1 :
							// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1135:12: list_of_declarations[defer]
							{
							pushFollow(FOLLOW_list_of_declarations_in_containerDeclaration1860);
							list_of_declarations89=list_of_declarations(defer);
							state._fsp--;

							adaptor.addChild(root_0, list_of_declarations89.getTree());

							}
							break;

					}

					RIGHTP90=(Token)match(input,RIGHTP,FOLLOW_RIGHTP_in_containerDeclaration1865); 
					RIGHTP90_tree = (Object)adaptor.create(RIGHTP90);
					adaptor.addChild(root_0, RIGHTP90_tree);

					}
					break;

			}


			if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
			    try {
			        /*
			         *   after all declarations are done, we 
			         *   pop the collection from the stack.
			         */
			        this.interp.pop();
			    } catch(EugeneException ee) {
			        printError(ee.getLocalizedMessage());
			    }
			}	
				
			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "containerDeclaration"


	public static class list_of_declarations_return extends ParserRuleReturnScope {
		public List<NamedElement> elements;
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "list_of_declarations"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1150:1: list_of_declarations[boolean defer] returns [List<NamedElement> elements] : (ds= declarationStatement[defer] |exp= expr[defer] ) ( COMMA lod= list_of_declarations[defer] )? ;
	public final EugeneParser.list_of_declarations_return list_of_declarations(boolean defer) throws RecognitionException {
		EugeneParser.list_of_declarations_return retval = new EugeneParser.list_of_declarations_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token COMMA91=null;
		ParserRuleReturnScope ds =null;
		ParserRuleReturnScope exp =null;
		ParserRuleReturnScope lod =null;

		Object COMMA91_tree=null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1152:2: ( (ds= declarationStatement[defer] |exp= expr[defer] ) ( COMMA lod= list_of_declarations[defer] )? )
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1152:4: (ds= declarationStatement[defer] |exp= expr[defer] ) ( COMMA lod= list_of_declarations[defer] )?
			{
			root_0 = (Object)adaptor.nil();


			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1152:4: (ds= declarationStatement[defer] |exp= expr[defer] )
			int alt30=2;
			switch ( input.LA(1) ) {
			case ARRAY:
			case BOOL:
			case BOOLEAN:
			case COLLECTION:
			case DEVICE:
			case GRAMMAR:
			case INTERACTION:
			case NUM:
			case PART:
			case PART_TYPE:
			case PROPERTY:
			case RULE:
			case TXT:
			case TYPE:
				{
				alt30=1;
				}
				break;
			case ID:
				{
				int LA30_2 = input.LA(2);
				if ( (LA30_2==COMMA||LA30_2==DIV||LA30_2==DOT||(LA30_2 >= LEFTP && LA30_2 <= LEFTSBR)||LA30_2==MINUS||LA30_2==MULT||LA30_2==PLUS||LA30_2==RIGHTP) ) {
					alt30=2;
				}
				else if ( (LA30_2==DOLLAR||LA30_2==ID||LA30_2==LC_INDUCES||LA30_2==LC_REPRESSES||LA30_2==UC_INDUCES||LA30_2==UC_REPRESSES) ) {
					alt30=1;
				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 30, 2, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

				}
				break;
			case DOLLAR:
			case FALSE_LC:
			case FALSE_UC:
			case LEFTP:
			case LEFTSBR:
			case MINUS:
			case NUMBER:
			case PERMUTE:
			case PRODUCT:
			case RANDOM_LC:
			case RANDOM_UC:
			case REAL:
			case SIZEOF_LC:
			case SIZEOF_UC:
			case SIZE_LC:
			case SIZE_UC:
			case STRING:
			case TRUE_LC:
			case TRUE_UC:
				{
				alt30=2;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 30, 0, input);
				throw nvae;
			}
			switch (alt30) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1152:6: ds= declarationStatement[defer]
					{
					pushFollow(FOLLOW_declarationStatement_in_list_of_declarations1898);
					ds=declarationStatement(defer);
					state._fsp--;

					adaptor.addChild(root_0, ds.getTree());

					}
					break;
				case 2 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1152:39: exp= expr[defer]
					{
					pushFollow(FOLLOW_expr_in_list_of_declarations1905);
					exp=expr(defer);
					state._fsp--;

					adaptor.addChild(root_0, exp.getTree());


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    try {
					        if(null != (exp!=null?((EugeneParser.expr_return)exp).element:null)) {
					            this.interp.addToContainer((exp!=null?((EugeneParser.expr_return)exp).element:null));
					        } else if(null != (exp!=null?((EugeneParser.expr_return)exp).p:null)) {
					            this.interp.addToContainer((exp!=null?((EugeneParser.expr_return)exp).p:null));
					        } else {
					            throw new EugeneException("Cannot add " + (exp!=null?input.toString(exp.start,exp.stop):null) + " to a Eugene container!");
					        }
					    } catch(EugeneException ee) {
					        printError(ee.getLocalizedMessage());
					    }
					}	
						
					}
					break;

			}

			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1166:5: ( COMMA lod= list_of_declarations[defer] )?
			int alt31=2;
			int LA31_0 = input.LA(1);
			if ( (LA31_0==COMMA) ) {
				alt31=1;
			}
			switch (alt31) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1166:7: COMMA lod= list_of_declarations[defer]
					{
					COMMA91=(Token)match(input,COMMA,FOLLOW_COMMA_in_list_of_declarations1913); 
					COMMA91_tree = (Object)adaptor.create(COMMA91);
					adaptor.addChild(root_0, COMMA91_tree);

					pushFollow(FOLLOW_list_of_declarations_in_list_of_declarations1917);
					lod=list_of_declarations(defer);
					state._fsp--;

					adaptor.addChild(root_0, lod.getTree());

					}
					break;

			}

			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "list_of_declarations"


	public static class instantiation_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "instantiation"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1172:1: instantiation[boolean defer] : t= ID n= dynamic_naming[defer] ( LEFTP (dotToken= listOfDotValues[defer] |valueToken= listOfValues[defer, (ComponentType)type] )? RIGHTP )? ;
	public final EugeneParser.instantiation_return instantiation(boolean defer) throws RecognitionException {
		EugeneParser.instantiation_return retval = new EugeneParser.instantiation_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token t=null;
		Token LEFTP92=null;
		Token RIGHTP93=null;
		ParserRuleReturnScope n =null;
		ParserRuleReturnScope dotToken =null;
		ParserRuleReturnScope valueToken =null;

		Object t_tree=null;
		Object LEFTP92_tree=null;
		Object RIGHTP93_tree=null;


		NamedElement type = null;
		String instance_name = null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1177:2: (t= ID n= dynamic_naming[defer] ( LEFTP (dotToken= listOfDotValues[defer] |valueToken= listOfValues[defer, (ComponentType)type] )? RIGHTP )? )
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1177:4: t= ID n= dynamic_naming[defer] ( LEFTP (dotToken= listOfDotValues[defer] |valueToken= listOfValues[defer, (ComponentType)type] )? RIGHTP )?
			{
			root_0 = (Object)adaptor.nil();


			t=(Token)match(input,ID,FOLLOW_ID_in_instantiation1945); 
			t_tree = (Object)adaptor.create(t);
			adaptor.addChild(root_0, t_tree);


			if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
			    try {
			        type = this.interp.get((t!=null?t.getText():null));
			    } catch(EugeneException ee) {
			        printError(ee.getMessage());
			    }
			    
			    if(null==type) {
			        printError("I don't know anything about "+ (t!=null?t.getText():null) + "!");
			    }         
			             
			    if(!(type instanceof ComponentType)) {
			        printError((t!=null?t.getText():null)+" is not a type!");
			    }                  
			}	
				
			pushFollow(FOLLOW_dynamic_naming_in_instantiation1951);
			n=dynamic_naming(defer);
			state._fsp--;

			adaptor.addChild(root_0, n.getTree());


			if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
			    instance_name = (n!=null?((EugeneParser.dynamic_naming_return)n).name:null);	
			}
				
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1197:4: ( LEFTP (dotToken= listOfDotValues[defer] |valueToken= listOfValues[defer, (ComponentType)type] )? RIGHTP )?
			int alt33=2;
			int LA33_0 = input.LA(1);
			if ( (LA33_0==LEFTP) ) {
				alt33=1;
			}
			switch (alt33) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1197:6: LEFTP (dotToken= listOfDotValues[defer] |valueToken= listOfValues[defer, (ComponentType)type] )? RIGHTP
					{
					LEFTP92=(Token)match(input,LEFTP,FOLLOW_LEFTP_in_instantiation1958); 
					LEFTP92_tree = (Object)adaptor.create(LEFTP92);
					adaptor.addChild(root_0, LEFTP92_tree);

					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1197:12: (dotToken= listOfDotValues[defer] |valueToken= listOfValues[defer, (ComponentType)type] )?
					int alt32=3;
					int LA32_0 = input.LA(1);
					if ( (LA32_0==DOT) ) {
						alt32=1;
					}
					else if ( (LA32_0==DOLLAR||(LA32_0 >= FALSE_LC && LA32_0 <= FALSE_UC)||LA32_0==ID||(LA32_0 >= LEFTP && LA32_0 <= LEFTSBR)||LA32_0==MINUS||LA32_0==NUMBER||LA32_0==PERMUTE||LA32_0==PRODUCT||(LA32_0 >= RANDOM_LC && LA32_0 <= RANDOM_UC)||LA32_0==REAL||(LA32_0 >= SIZEOF_LC && LA32_0 <= SIZE_UC)||(LA32_0 >= STRING && LA32_0 <= TRUE_UC)) ) {
						alt32=2;
					}
					switch (alt32) {
						case 1 :
							// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1197:13: dotToken= listOfDotValues[defer]
							{
							pushFollow(FOLLOW_listOfDotValues_in_instantiation1963);
							dotToken=listOfDotValues(defer);
							state._fsp--;

							adaptor.addChild(root_0, dotToken.getTree());

							}
							break;
						case 2 :
							// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1197:45: valueToken= listOfValues[defer, (ComponentType)type]
							{
							pushFollow(FOLLOW_listOfValues_in_instantiation1968);
							valueToken=listOfValues(defer, (ComponentType)type);
							state._fsp--;

							adaptor.addChild(root_0, valueToken.getTree());

							}
							break;

					}

					RIGHTP93=(Token)match(input,RIGHTP,FOLLOW_RIGHTP_in_instantiation1973); 
					RIGHTP93_tree = (Object)adaptor.create(RIGHTP93);
					adaptor.addChild(root_0, RIGHTP93_tree);

					}
					break;

			}


			if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
			    try {
			        if(null != dotToken) {
			            this.interp.instantiate(
			                (ComponentType)type, instance_name,
			                this.propertyListHolder,
			                this.propertyValuesHolder);    	
			        } else if (null != valueToken) {
			            this.interp.instantiate(
			                (ComponentType)type, instance_name,
			                this.propertyListHolder,
			                this.propertyValuesHolder);    	
			        } else {
			            this.interp.instantiate(
			                (ComponentType)type, instance_name,
			                new ArrayList<String>(),
			                new ArrayList<Variable>());    	
			        }
			        
			        // clear the "holder"-lists
			        this.propertyValuesHolder.clear();
			        this.propertyListHolder.clear();
			    } catch(EugeneException ee) {
			        printError(ee.getMessage());
			    }
			}                
			        
			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "instantiation"


	public static class listOfDotValues_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "listOfDotValues"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1231:1: listOfDotValues[boolean defer] : DOT prop= ID LEFTP v1= expr[defer] RIGHTP ( COMMA DOT p= ID LEFTP v2= expr[defer] RIGHTP )* ;
	public final EugeneParser.listOfDotValues_return listOfDotValues(boolean defer) throws RecognitionException {
		EugeneParser.listOfDotValues_return retval = new EugeneParser.listOfDotValues_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token prop=null;
		Token p=null;
		Token DOT94=null;
		Token LEFTP95=null;
		Token RIGHTP96=null;
		Token COMMA97=null;
		Token DOT98=null;
		Token LEFTP99=null;
		Token RIGHTP100=null;
		ParserRuleReturnScope v1 =null;
		ParserRuleReturnScope v2 =null;

		Object prop_tree=null;
		Object p_tree=null;
		Object DOT94_tree=null;
		Object LEFTP95_tree=null;
		Object RIGHTP96_tree=null;
		Object COMMA97_tree=null;
		Object DOT98_tree=null;
		Object LEFTP99_tree=null;
		Object RIGHTP100_tree=null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1232:2: ( DOT prop= ID LEFTP v1= expr[defer] RIGHTP ( COMMA DOT p= ID LEFTP v2= expr[defer] RIGHTP )* )
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1232:4: DOT prop= ID LEFTP v1= expr[defer] RIGHTP ( COMMA DOT p= ID LEFTP v2= expr[defer] RIGHTP )*
			{
			root_0 = (Object)adaptor.nil();


			DOT94=(Token)match(input,DOT,FOLLOW_DOT_in_listOfDotValues1996); 
			DOT94_tree = (Object)adaptor.create(DOT94);
			adaptor.addChild(root_0, DOT94_tree);

			prop=(Token)match(input,ID,FOLLOW_ID_in_listOfDotValues2000); 
			prop_tree = (Object)adaptor.create(prop);
			adaptor.addChild(root_0, prop_tree);


			if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {		
			    try {
			        addToPropertyListHolder((prop!=null?prop.getText():null));
			    } catch(EugeneException ee) {
			        printError(ee.getMessage());
			    }				
			}			
					
			LEFTP95=(Token)match(input,LEFTP,FOLLOW_LEFTP_in_listOfDotValues2006); 
			LEFTP95_tree = (Object)adaptor.create(LEFTP95);
			adaptor.addChild(root_0, LEFTP95_tree);

			pushFollow(FOLLOW_expr_in_listOfDotValues2010);
			v1=expr(defer);
			state._fsp--;

			adaptor.addChild(root_0, v1.getTree());


			if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {			
			    try {
			        addToPropertyValuesHolder((prop!=null?prop.getText():null), (v1!=null?((EugeneParser.expr_return)v1).p:null), (v1!=null?((EugeneParser.expr_return)v1).index:0));
			    } catch(EugeneException ee) {
			        printError(ee.getMessage());
			    }				
			}				
						
			RIGHTP96=(Token)match(input,RIGHTP,FOLLOW_RIGHTP_in_listOfDotValues2018); 
			RIGHTP96_tree = (Object)adaptor.create(RIGHTP96);
			adaptor.addChild(root_0, RIGHTP96_tree);

			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1250:13: ( COMMA DOT p= ID LEFTP v2= expr[defer] RIGHTP )*
			loop34:
			while (true) {
				int alt34=2;
				int LA34_0 = input.LA(1);
				if ( (LA34_0==COMMA) ) {
					alt34=1;
				}

				switch (alt34) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1250:14: COMMA DOT p= ID LEFTP v2= expr[defer] RIGHTP
					{
					COMMA97=(Token)match(input,COMMA,FOLLOW_COMMA_in_listOfDotValues2021); 
					COMMA97_tree = (Object)adaptor.create(COMMA97);
					adaptor.addChild(root_0, COMMA97_tree);

					DOT98=(Token)match(input,DOT,FOLLOW_DOT_in_listOfDotValues2023); 
					DOT98_tree = (Object)adaptor.create(DOT98);
					adaptor.addChild(root_0, DOT98_tree);

					p=(Token)match(input,ID,FOLLOW_ID_in_listOfDotValues2027); 
					p_tree = (Object)adaptor.create(p);
					adaptor.addChild(root_0, p_tree);


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {			
					    try {
					        addToPropertyListHolder((p!=null?p.getText():null));
					    } catch(EugeneException ee) {
					        printError(ee.getMessage());
					    }				
					}				
									
					LEFTP99=(Token)match(input,LEFTP,FOLLOW_LEFTP_in_listOfDotValues2035); 
					LEFTP99_tree = (Object)adaptor.create(LEFTP99);
					adaptor.addChild(root_0, LEFTP99_tree);

					pushFollow(FOLLOW_expr_in_listOfDotValues2039);
					v2=expr(defer);
					state._fsp--;

					adaptor.addChild(root_0, v2.getTree());


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {			
					    try {
					        addToPropertyValuesHolder((p!=null?p.getText():null), (v2!=null?((EugeneParser.expr_return)v2).p:null), (v2!=null?((EugeneParser.expr_return)v2).index:0));
					    } catch(EugeneException ee) {
					        printError(ee.getMessage());
					    }				
					}				
										
					RIGHTP100=(Token)match(input,RIGHTP,FOLLOW_RIGHTP_in_listOfDotValues2049); 
					RIGHTP100_tree = (Object)adaptor.create(RIGHTP100);
					adaptor.addChild(root_0, RIGHTP100_tree);

					}
					break;

				default :
					break loop34;
				}
			}

			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "listOfDotValues"


	public static class listOfValues_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "listOfValues"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1271:1: listOfValues[boolean defer, ComponentType pt] :val1= expr[defer] ( COMMA val2= expr[defer] )* ;
	public final EugeneParser.listOfValues_return listOfValues(boolean defer, ComponentType pt) throws RecognitionException {
		EugeneParser.listOfValues_return retval = new EugeneParser.listOfValues_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token COMMA101=null;
		ParserRuleReturnScope val1 =null;
		ParserRuleReturnScope val2 =null;

		Object COMMA101_tree=null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1272:2: (val1= expr[defer] ( COMMA val2= expr[defer] )* )
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1273:3: val1= expr[defer] ( COMMA val2= expr[defer] )*
			{
			root_0 = (Object)adaptor.nil();



			if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {	

			    List<Property> propertyList = pt.getProperties();            					
			    if(propertyList.size() < 1) {
			        printError("Invalid number of property values.");
			    }
			    try {	
			        NamedElement ne = this.interp.get(propertyList.get(propertyValuesHolder.size()).getName());
			        if(null != ne && ne instanceof Property) {
			            if (((Property)ne).getType().equals(EugeneConstants.TXTLIST)) {
			                typeList = EugeneConstants.TXT;
			            } else if (((Property)ne).getType().equals(EugeneConstants.NUMLIST)) {
			                typeList = EugeneConstants.NUM;
			            }
			        }
			    } catch(EugeneException ee) {
			        printError(ee.getMessage());
			    }				
			}
					
			pushFollow(FOLLOW_expr_in_listOfValues2070);
			val1=expr(defer);
			state._fsp--;

			adaptor.addChild(root_0, val1.getTree());


			if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
			    propertyValuesHolder.add((val1!=null?((EugeneParser.expr_return)val1).p:null));
			}				
						
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1297:6: ( COMMA val2= expr[defer] )*
			loop35:
			while (true) {
				int alt35=2;
				int LA35_0 = input.LA(1);
				if ( (LA35_0==COMMA) ) {
					alt35=1;
				}

				switch (alt35) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1297:7: COMMA val2= expr[defer]
					{
					COMMA101=(Token)match(input,COMMA,FOLLOW_COMMA_in_listOfValues2076); 
					COMMA101_tree = (Object)adaptor.create(COMMA101);
					adaptor.addChild(root_0, COMMA101_tree);


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {	

					    List<Property> propertyList = pt.getProperties();            					
					    if(propertyList.size() <= propertyValuesHolder.size()) {
					        printError("Invalid number of property values.");
					    }
					    
					    try {
					        NamedElement ne = this.interp.get(propertyList.get(propertyValuesHolder.size()).getName());
					             					
					        if(null != ne && ne instanceof Property) {
					            if (((Property)ne).getType().equals(EugeneConstants.TXTLIST)) {
					                typeList = EugeneConstants.TXT;
					            } else if (((Property)ne).getType().equals(EugeneConstants.NUMLIST)) {
					                typeList = EugeneConstants.NUM;
					            }
					        }
					    } catch(EugeneException ee) {
					        printError(ee.getMessage());
					    }				

					}
					               
					pushFollow(FOLLOW_expr_in_listOfValues2082);
					val2=expr(defer);
					state._fsp--;

					adaptor.addChild(root_0, val2.getTree());


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    propertyValuesHolder.add((val2!=null?((EugeneParser.expr_return)val2).p:null));
					}				
							
					}
					break;

				default :
					break loop35;
				}
			}

			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "listOfValues"


	public static class deviceDeclaration_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "deviceDeclaration"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1327:1: deviceDeclaration[boolean defer] : DEVICE n= ID ( LEFTP (dcs= deviceComponents[defer] )? RIGHTP )? ;
	public final EugeneParser.deviceDeclaration_return deviceDeclaration(boolean defer) throws RecognitionException {
		EugeneParser.deviceDeclaration_return retval = new EugeneParser.deviceDeclaration_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token n=null;
		Token DEVICE102=null;
		Token LEFTP103=null;
		Token RIGHTP104=null;
		ParserRuleReturnScope dcs =null;

		Object n_tree=null;
		Object DEVICE102_tree=null;
		Object LEFTP103_tree=null;
		Object RIGHTP104_tree=null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1328:2: ( DEVICE n= ID ( LEFTP (dcs= deviceComponents[defer] )? RIGHTP )? )
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1328:4: DEVICE n= ID ( LEFTP (dcs= deviceComponents[defer] )? RIGHTP )?
			{
			root_0 = (Object)adaptor.nil();


			DEVICE102=(Token)match(input,DEVICE,FOLLOW_DEVICE_in_deviceDeclaration2101); 
			DEVICE102_tree = (Object)adaptor.create(DEVICE102);
			adaptor.addChild(root_0, DEVICE102_tree);

			n=(Token)match(input,ID,FOLLOW_ID_in_deviceDeclaration2105); 
			n_tree = (Object)adaptor.create(n);
			adaptor.addChild(root_0, n_tree);

			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1328:16: ( LEFTP (dcs= deviceComponents[defer] )? RIGHTP )?
			int alt37=2;
			int LA37_0 = input.LA(1);
			if ( (LA37_0==LEFTP) ) {
				alt37=1;
			}
			switch (alt37) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1328:17: LEFTP (dcs= deviceComponents[defer] )? RIGHTP
					{
					LEFTP103=(Token)match(input,LEFTP,FOLLOW_LEFTP_in_deviceDeclaration2108); 
					LEFTP103_tree = (Object)adaptor.create(LEFTP103);
					adaptor.addChild(root_0, LEFTP103_tree);

					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1328:23: (dcs= deviceComponents[defer] )?
					int alt36=2;
					int LA36_0 = input.LA(1);
					if ( (LA36_0==ID||LA36_0==LEFTSBR||LA36_0==MINUS||LA36_0==PLUS) ) {
						alt36=1;
					}
					switch (alt36) {
						case 1 :
							// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1328:24: dcs= deviceComponents[defer]
							{
							pushFollow(FOLLOW_deviceComponents_in_deviceDeclaration2113);
							dcs=deviceComponents(defer);
							state._fsp--;

							adaptor.addChild(root_0, dcs.getTree());

							}
							break;

					}

					RIGHTP104=(Token)match(input,RIGHTP,FOLLOW_RIGHTP_in_deviceDeclaration2118); 
					RIGHTP104_tree = (Object)adaptor.create(RIGHTP104);
					adaptor.addChild(root_0, RIGHTP104_tree);

					}
					break;

			}


			if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
			    try {
			        interp.createDevice(
			            (n!=null?n.getText():null), 
			            (dcs!=null?((EugeneParser.deviceComponents_return)dcs).lstComponents:null),
			            (dcs!=null?((EugeneParser.deviceComponents_return)dcs).lstOrientations:null));
			    } catch(Exception e) {
			        printError(e.getMessage());
			    }
			}	
				
			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "deviceDeclaration"


	public static class deviceComponents_return extends ParserRuleReturnScope {
		public List<List<NamedElement>> lstComponents;
		public List<List<Orientation>> lstOrientations;
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "deviceComponents"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1342:1: deviceComponents[boolean defer] returns [List<List<NamedElement>> lstComponents, List<List<Orientation>> lstOrientations] : s= selection[defer] ( ',' dcs= deviceComponents[defer] )? ;
	public final EugeneParser.deviceComponents_return deviceComponents(boolean defer) throws RecognitionException {
		EugeneParser.deviceComponents_return retval = new EugeneParser.deviceComponents_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token char_literal105=null;
		ParserRuleReturnScope s =null;
		ParserRuleReturnScope dcs =null;

		Object char_literal105_tree=null;


		retval.lstComponents = new ArrayList<List<NamedElement>>();
		retval.lstOrientations = new ArrayList<List<Orientation>>();

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1348:2: (s= selection[defer] ( ',' dcs= deviceComponents[defer] )? )
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1348:4: s= selection[defer] ( ',' dcs= deviceComponents[defer] )?
			{
			root_0 = (Object)adaptor.nil();


			pushFollow(FOLLOW_selection_in_deviceComponents2149);
			s=selection(defer);
			state._fsp--;

			adaptor.addChild(root_0, s.getTree());


			if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
			    retval.lstComponents.add((s!=null?((EugeneParser.selection_return)s).components:null));
			    retval.lstOrientations.add((s!=null?((EugeneParser.selection_return)s).orientations:null));
			}	
				
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1353:4: ( ',' dcs= deviceComponents[defer] )?
			int alt38=2;
			int LA38_0 = input.LA(1);
			if ( (LA38_0==COMMA) ) {
				alt38=1;
			}
			switch (alt38) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1353:5: ',' dcs= deviceComponents[defer]
					{
					char_literal105=(Token)match(input,COMMA,FOLLOW_COMMA_in_deviceComponents2155); 
					char_literal105_tree = (Object)adaptor.create(char_literal105);
					adaptor.addChild(root_0, char_literal105_tree);

					pushFollow(FOLLOW_deviceComponents_in_deviceComponents2159);
					dcs=deviceComponents(defer);
					state._fsp--;

					adaptor.addChild(root_0, dcs.getTree());


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    retval.lstComponents.addAll((dcs!=null?((EugeneParser.deviceComponents_return)dcs).lstComponents:null));
					    retval.lstOrientations.addAll((dcs!=null?((EugeneParser.deviceComponents_return)dcs).lstOrientations:null));
					}	
						
					}
					break;

			}

			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "deviceComponents"


	public static class selection_return extends ParserRuleReturnScope {
		public List<NamedElement> components;
		public List<Orientation> orientations;
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "selection"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1361:1: selection[boolean defer] returns [List<NamedElement> components, List<Orientation> orientations] : ( LEFTSBR sl= selection_list[defer] RIGHTSBR |dc= device_component[defer] );
	public final EugeneParser.selection_return selection(boolean defer) throws RecognitionException {
		EugeneParser.selection_return retval = new EugeneParser.selection_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token LEFTSBR106=null;
		Token RIGHTSBR107=null;
		ParserRuleReturnScope sl =null;
		ParserRuleReturnScope dc =null;

		Object LEFTSBR106_tree=null;
		Object RIGHTSBR107_tree=null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1363:2: ( LEFTSBR sl= selection_list[defer] RIGHTSBR |dc= device_component[defer] )
			int alt39=2;
			int LA39_0 = input.LA(1);
			if ( (LA39_0==LEFTSBR) ) {
				alt39=1;
			}
			else if ( (LA39_0==ID||LA39_0==MINUS||LA39_0==PLUS) ) {
				alt39=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 39, 0, input);
				throw nvae;
			}

			switch (alt39) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1363:4: LEFTSBR sl= selection_list[defer] RIGHTSBR
					{
					root_0 = (Object)adaptor.nil();


					LEFTSBR106=(Token)match(input,LEFTSBR,FOLLOW_LEFTSBR_in_selection2183); 
					LEFTSBR106_tree = (Object)adaptor.create(LEFTSBR106);
					adaptor.addChild(root_0, LEFTSBR106_tree);

					pushFollow(FOLLOW_selection_list_in_selection2187);
					sl=selection_list(defer);
					state._fsp--;

					adaptor.addChild(root_0, sl.getTree());

					RIGHTSBR107=(Token)match(input,RIGHTSBR,FOLLOW_RIGHTSBR_in_selection2190); 
					RIGHTSBR107_tree = (Object)adaptor.create(RIGHTSBR107);
					adaptor.addChild(root_0, RIGHTSBR107_tree);


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    retval.components = (sl!=null?((EugeneParser.selection_list_return)sl).components:null);
					    retval.orientations = (sl!=null?((EugeneParser.selection_list_return)sl).orientations:null);
					}	
						
					}
					break;
				case 2 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1369:4: dc= device_component[defer]
					{
					root_0 = (Object)adaptor.nil();


					pushFollow(FOLLOW_device_component_in_selection2199);
					dc=device_component(defer);
					state._fsp--;

					adaptor.addChild(root_0, dc.getTree());


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    retval.components = new ArrayList<NamedElement>(1);
					    retval.components.add((dc!=null?((EugeneParser.device_component_return)dc).component:null));
					    
					    retval.orientations = new ArrayList<Orientation>();
					    retval.orientations.add((dc!=null?((EugeneParser.device_component_return)dc).orientation:null));
					}	
						
					}
					break;

			}
			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "selection"


	public static class selection_list_return extends ParserRuleReturnScope {
		public List<NamedElement> components;
		public List<Orientation> orientations;
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "selection_list"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1380:1: selection_list[boolean defer] returns [List<NamedElement> components, List<Orientation> orientations] : dc= device_component[defer] ( PIPE sl= selection_list[defer] )? ;
	public final EugeneParser.selection_list_return selection_list(boolean defer) throws RecognitionException {
		EugeneParser.selection_list_return retval = new EugeneParser.selection_list_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token PIPE108=null;
		ParserRuleReturnScope dc =null;
		ParserRuleReturnScope sl =null;

		Object PIPE108_tree=null;


		retval.components = new ArrayList<NamedElement>();
		retval.orientations = new ArrayList<Orientation>();

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1386:2: (dc= device_component[defer] ( PIPE sl= selection_list[defer] )? )
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1386:4: dc= device_component[defer] ( PIPE sl= selection_list[defer] )?
			{
			root_0 = (Object)adaptor.nil();


			pushFollow(FOLLOW_device_component_in_selection_list2228);
			dc=device_component(defer);
			state._fsp--;

			adaptor.addChild(root_0, dc.getTree());


			if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
			    retval.components.add((dc!=null?((EugeneParser.device_component_return)dc).component:null));
			    retval.orientations.add((dc!=null?((EugeneParser.device_component_return)dc).orientation:null));
			}	
				
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1391:4: ( PIPE sl= selection_list[defer] )?
			int alt40=2;
			int LA40_0 = input.LA(1);
			if ( (LA40_0==PIPE) ) {
				alt40=1;
			}
			switch (alt40) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1391:5: PIPE sl= selection_list[defer]
					{
					PIPE108=(Token)match(input,PIPE,FOLLOW_PIPE_in_selection_list2234); 
					PIPE108_tree = (Object)adaptor.create(PIPE108);
					adaptor.addChild(root_0, PIPE108_tree);

					pushFollow(FOLLOW_selection_list_in_selection_list2238);
					sl=selection_list(defer);
					state._fsp--;

					adaptor.addChild(root_0, sl.getTree());


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    retval.components.addAll((sl!=null?((EugeneParser.selection_list_return)sl).components:null));
					    retval.orientations.addAll((sl!=null?((EugeneParser.selection_list_return)sl).orientations:null));
					}	
						
					}
					break;

			}

			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "selection_list"


	public static class device_component_return extends ParserRuleReturnScope {
		public NamedElement component;
		public Orientation orientation;
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "device_component"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1399:1: device_component[boolean defer] returns [NamedElement component, Orientation orientation] : (directionToken= ( MINUS | PLUS ) )? idToken= ID ;
	public final EugeneParser.device_component_return device_component(boolean defer) throws RecognitionException {
		EugeneParser.device_component_return retval = new EugeneParser.device_component_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token directionToken=null;
		Token idToken=null;

		Object directionToken_tree=null;
		Object idToken_tree=null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1401:2: ( (directionToken= ( MINUS | PLUS ) )? idToken= ID )
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1401:4: (directionToken= ( MINUS | PLUS ) )? idToken= ID
			{
			root_0 = (Object)adaptor.nil();


			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1401:4: (directionToken= ( MINUS | PLUS ) )?
			int alt41=2;
			int LA41_0 = input.LA(1);
			if ( (LA41_0==MINUS||LA41_0==PLUS) ) {
				alt41=1;
			}
			switch (alt41) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1401:5: directionToken= ( MINUS | PLUS )
					{
					directionToken=input.LT(1);
					if ( input.LA(1)==MINUS||input.LA(1)==PLUS ) {
						input.consume();
						adaptor.addChild(root_0, (Object)adaptor.create(directionToken));
						state.errorRecovery=false;
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						throw mse;
					}
					}
					break;

			}

			idToken=(Token)match(input,ID,FOLLOW_ID_in_device_component2274); 
			idToken_tree = (Object)adaptor.create(idToken);
			adaptor.addChild(root_0, idToken_tree);


			if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
			    try {
			        NamedElement ne = interp.get((idToken!=null?idToken.getText():null));
			        if(null == ne) {
			           printError((idToken!=null?idToken.getText():null)+" is not declared.");
			        }
			    
			        if(ne instanceof Component) { 
			            retval.component = (Component)ne;
			        } else if(ne instanceof ComponentType) {
			            retval.component = (ComponentType)ne;        
			        } else {
			            printError((idToken!=null?idToken.getText():null)+" is neither a Device, Part, nor Part Type.");
			        }

			        if(directionToken != null) {        	        
			            if("-".equals((directionToken!=null?directionToken.getText():null))) {            
			                retval.orientation = Orientation.REVERSE;
			            } else {
			                retval.orientation = Orientation.FORWARD;
			            }
			        } else {
			            retval.orientation = Orientation.UNDEFINED;
			        }
			    } catch(EugeneException ee) {
			        printError(ee.getMessage());
			    }				
			}	
				
			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "device_component"


	public static class assignment_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "assignment"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1449:1: assignment[boolean defer] : lhs= lhs_assignment[defer] EQUALS (a= AMP )? rhs= rhs_assignment[defer] ;
	public final EugeneParser.assignment_return assignment(boolean defer) throws RecognitionException {
		EugeneParser.assignment_return retval = new EugeneParser.assignment_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token a=null;
		Token EQUALS109=null;
		ParserRuleReturnScope lhs =null;
		ParserRuleReturnScope rhs =null;

		Object a_tree=null;
		Object EQUALS109_tree=null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1450:2: (lhs= lhs_assignment[defer] EQUALS (a= AMP )? rhs= rhs_assignment[defer] )
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1450:4: lhs= lhs_assignment[defer] EQUALS (a= AMP )? rhs= rhs_assignment[defer]
			{
			root_0 = (Object)adaptor.nil();


			pushFollow(FOLLOW_lhs_assignment_in_assignment2294);
			lhs=lhs_assignment(defer);
			state._fsp--;

			adaptor.addChild(root_0, lhs.getTree());

			EQUALS109=(Token)match(input,EQUALS,FOLLOW_EQUALS_in_assignment2297); 
			EQUALS109_tree = (Object)adaptor.create(EQUALS109);
			adaptor.addChild(root_0, EQUALS109_tree);

			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1450:37: (a= AMP )?
			int alt42=2;
			int LA42_0 = input.LA(1);
			if ( (LA42_0==AMP) ) {
				alt42=1;
			}
			switch (alt42) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1450:38: a= AMP
					{
					a=(Token)match(input,AMP,FOLLOW_AMP_in_assignment2302); 
					a_tree = (Object)adaptor.create(a);
					adaptor.addChild(root_0, a_tree);

					}
					break;

			}

			pushFollow(FOLLOW_rhs_assignment_in_assignment2308);
			rhs=rhs_assignment(defer);
			state._fsp--;

			adaptor.addChild(root_0, rhs.getTree());


			if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
			    try {

			        /* --- NEW VERSION: Updated Oct 15th, 2014 
			               by Ernst Oberortner
			               
			           in the new version, we do the parsing of the 
			           LHS in the interpreter.
			           this influences the efficiency though (2x parsing and non-recursive interpretation).
			           however, it works better, more stable, and is easier to understand and improve.
			           
			           To my offspring: Please improve!

			           In general, the Eugene interpreter needs an Abstract Syntax Tree (AST)
			           Example: 
			           this.interp.assignment((lhs!=null?((Object)lhs.getTree()):null), (rhs!=null?((EugeneParser.rhs_assignment_return)rhs).e:null));
			         */
			        this.interp.assignment(
			                        (lhs!=null?input.toString(lhs.start,lhs.stop):null), 
			                        (rhs!=null?((EugeneParser.rhs_assignment_return)rhs).e:null));
			        
			    } catch(EugeneException ee) {
			        printError(ee.getLocalizedMessage());    
			    }
			}	
				
			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "assignment"


	public static class lhs_assignment_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "lhs_assignment"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1479:1: lhs_assignment[boolean defer] : ID lhs_access[defer] ;
	public final EugeneParser.lhs_assignment_return lhs_assignment(boolean defer) throws RecognitionException {
		EugeneParser.lhs_assignment_return retval = new EugeneParser.lhs_assignment_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token ID110=null;
		ParserRuleReturnScope lhs_access111 =null;

		Object ID110_tree=null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1480:2: ( ID lhs_access[defer] )
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1480:4: ID lhs_access[defer]
			{
			root_0 = (Object)adaptor.nil();


			ID110=(Token)match(input,ID,FOLLOW_ID_in_lhs_assignment2323); 
			ID110_tree = (Object)adaptor.create(ID110);
			adaptor.addChild(root_0, ID110_tree);

			pushFollow(FOLLOW_lhs_access_in_lhs_assignment2325);
			lhs_access111=lhs_access(defer);
			state._fsp--;

			adaptor.addChild(root_0, lhs_access111.getTree());

			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "lhs_assignment"


	public static class lhs_access_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "lhs_access"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1483:1: lhs_access[boolean defer] : (| ( DOT i= ID | LEFTSBR ( ID | NUMBER ) RIGHTSBR ) lhs_access[defer] );
	public final EugeneParser.lhs_access_return lhs_access(boolean defer) throws RecognitionException {
		EugeneParser.lhs_access_return retval = new EugeneParser.lhs_access_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token i=null;
		Token DOT112=null;
		Token LEFTSBR113=null;
		Token set114=null;
		Token RIGHTSBR115=null;
		ParserRuleReturnScope lhs_access116 =null;

		Object i_tree=null;
		Object DOT112_tree=null;
		Object LEFTSBR113_tree=null;
		Object set114_tree=null;
		Object RIGHTSBR115_tree=null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1484:2: (| ( DOT i= ID | LEFTSBR ( ID | NUMBER ) RIGHTSBR ) lhs_access[defer] )
			int alt44=2;
			int LA44_0 = input.LA(1);
			if ( (LA44_0==EQUALS) ) {
				alt44=1;
			}
			else if ( (LA44_0==DOT||LA44_0==LEFTSBR) ) {
				alt44=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 44, 0, input);
				throw nvae;
			}

			switch (alt44) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1485:2: 
					{
					root_0 = (Object)adaptor.nil();


					}
					break;
				case 2 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1485:4: ( DOT i= ID | LEFTSBR ( ID | NUMBER ) RIGHTSBR ) lhs_access[defer]
					{
					root_0 = (Object)adaptor.nil();


					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1485:4: ( DOT i= ID | LEFTSBR ( ID | NUMBER ) RIGHTSBR )
					int alt43=2;
					int LA43_0 = input.LA(1);
					if ( (LA43_0==DOT) ) {
						alt43=1;
					}
					else if ( (LA43_0==LEFTSBR) ) {
						alt43=2;
					}

					else {
						NoViableAltException nvae =
							new NoViableAltException("", 43, 0, input);
						throw nvae;
					}

					switch (alt43) {
						case 1 :
							// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1485:5: DOT i= ID
							{
							DOT112=(Token)match(input,DOT,FOLLOW_DOT_in_lhs_access2345); 
							DOT112_tree = (Object)adaptor.create(DOT112);
							adaptor.addChild(root_0, DOT112_tree);

							i=(Token)match(input,ID,FOLLOW_ID_in_lhs_access2349); 
							i_tree = (Object)adaptor.create(i);
							adaptor.addChild(root_0, i_tree);

							}
							break;
						case 2 :
							// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1485:16: LEFTSBR ( ID | NUMBER ) RIGHTSBR
							{
							LEFTSBR113=(Token)match(input,LEFTSBR,FOLLOW_LEFTSBR_in_lhs_access2353); 
							LEFTSBR113_tree = (Object)adaptor.create(LEFTSBR113);
							adaptor.addChild(root_0, LEFTSBR113_tree);

							set114=input.LT(1);
							if ( input.LA(1)==ID||input.LA(1)==NUMBER ) {
								input.consume();
								adaptor.addChild(root_0, (Object)adaptor.create(set114));
								state.errorRecovery=false;
							}
							else {
								MismatchedSetException mse = new MismatchedSetException(null,input);
								throw mse;
							}
							RIGHTSBR115=(Token)match(input,RIGHTSBR,FOLLOW_RIGHTSBR_in_lhs_access2361); 
							RIGHTSBR115_tree = (Object)adaptor.create(RIGHTSBR115);
							adaptor.addChild(root_0, RIGHTSBR115_tree);

							}
							break;

					}

					pushFollow(FOLLOW_lhs_access_in_lhs_access2364);
					lhs_access116=lhs_access(defer);
					state._fsp--;

					adaptor.addChild(root_0, lhs_access116.getTree());

					}
					break;

			}
			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "lhs_access"


	public static class rhs_assignment_return extends ParserRuleReturnScope {
		public NamedElement e;
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "rhs_assignment"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1496:1: rhs_assignment[boolean defer] returns [NamedElement e] : (de= dataExchange[defer] |exp= expr[defer] );
	public final EugeneParser.rhs_assignment_return rhs_assignment(boolean defer) throws RecognitionException {
		EugeneParser.rhs_assignment_return retval = new EugeneParser.rhs_assignment_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		ParserRuleReturnScope de =null;
		ParserRuleReturnScope exp =null;


		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1498:2: (de= dataExchange[defer] |exp= expr[defer] )
			int alt45=2;
			int LA45_0 = input.LA(1);
			if ( (LA45_0==GENBANK||(LA45_0 >= IMPORT_LC && LA45_0 <= IMPORT_UC)||LA45_0==REGISTRY||LA45_0==SBOL) ) {
				alt45=1;
			}
			else if ( (LA45_0==DOLLAR||(LA45_0 >= FALSE_LC && LA45_0 <= FALSE_UC)||LA45_0==ID||(LA45_0 >= LEFTP && LA45_0 <= LEFTSBR)||LA45_0==MINUS||LA45_0==NUMBER||LA45_0==PERMUTE||LA45_0==PRODUCT||(LA45_0 >= RANDOM_LC && LA45_0 <= RANDOM_UC)||LA45_0==REAL||(LA45_0 >= SIZEOF_LC && LA45_0 <= SIZE_UC)||(LA45_0 >= STRING && LA45_0 <= TRUE_UC)) ) {
				alt45=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 45, 0, input);
				throw nvae;
			}

			switch (alt45) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1498:4: de= dataExchange[defer]
					{
					root_0 = (Object)adaptor.nil();


					pushFollow(FOLLOW_dataExchange_in_rhs_assignment2391);
					de=dataExchange(defer);
					state._fsp--;

					adaptor.addChild(root_0, de.getTree());


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    retval.e = (de!=null?((EugeneParser.dataExchange_return)de).e:null);
					}	
						
					}
					break;
				case 2 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1503:4: exp= expr[defer]
					{
					root_0 = (Object)adaptor.nil();


					pushFollow(FOLLOW_expr_in_rhs_assignment2401);
					exp=expr(defer);
					state._fsp--;

					adaptor.addChild(root_0, exp.getTree());


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    if((exp!=null?((EugeneParser.expr_return)exp).element:null) != null) {
					        retval.e = (exp!=null?((EugeneParser.expr_return)exp).element:null);
					    } else {
					        retval.e = (exp!=null?((EugeneParser.expr_return)exp).p:null);
					    }
					}	
						
					}
					break;

			}
			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "rhs_assignment"


	public static class listOfIDs_return extends ParserRuleReturnScope {
		public List<NamedElement> lstElements;
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "listOfIDs"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1514:1: listOfIDs[boolean defer] returns [List<NamedElement> lstElements] : idToken= ID ( ',' lstToken= listOfIDs[defer] )? ;
	public final EugeneParser.listOfIDs_return listOfIDs(boolean defer) throws RecognitionException {
		EugeneParser.listOfIDs_return retval = new EugeneParser.listOfIDs_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token idToken=null;
		Token char_literal117=null;
		ParserRuleReturnScope lstToken =null;

		Object idToken_tree=null;
		Object char_literal117_tree=null;


		retval.lstElements =new ArrayList<NamedElement>();

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1519:2: (idToken= ID ( ',' lstToken= listOfIDs[defer] )? )
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1519:4: idToken= ID ( ',' lstToken= listOfIDs[defer] )?
			{
			root_0 = (Object)adaptor.nil();


			idToken=(Token)match(input,ID,FOLLOW_ID_in_listOfIDs2429); 
			idToken_tree = (Object)adaptor.create(idToken);
			adaptor.addChild(root_0, idToken_tree);

			 
			if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING)
			    try {
			        NamedElement ne = interp.get((idToken!=null?idToken.getText():null));
			        if(null == ne) {
			           printError((idToken!=null?idToken.getText():null)+" is not declared.");
			        }
			        retval.lstElements.add(ne);
			    } catch(EugeneException ee) {
			        printError(ee.getMessage());
			    }

			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1531:4: ( ',' lstToken= listOfIDs[defer] )?
			int alt46=2;
			int LA46_0 = input.LA(1);
			if ( (LA46_0==COMMA) ) {
				alt46=1;
			}
			switch (alt46) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1531:5: ',' lstToken= listOfIDs[defer]
					{
					char_literal117=(Token)match(input,COMMA,FOLLOW_COMMA_in_listOfIDs2438); 
					char_literal117_tree = (Object)adaptor.create(char_literal117);
					adaptor.addChild(root_0, char_literal117_tree);

					pushFollow(FOLLOW_listOfIDs_in_listOfIDs2442);
					lstToken=listOfIDs(defer);
					state._fsp--;

					adaptor.addChild(root_0, lstToken.getTree());


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING){
					    retval.lstElements.addAll((lstToken!=null?((EugeneParser.listOfIDs_return)lstToken).lstElements:null));
					}
					        
					}
					break;

			}

			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "listOfIDs"


	public static class ruleDeclaration_return extends ParserRuleReturnScope {
		public Rule rule;
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "ruleDeclaration"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1538:1: ruleDeclaration[boolean defer] returns [Rule rule] : RULE name= ID LEFTP ( ( ( LC_ON | UC_ON ) device= ID COLON )? cnf= cnf_rule[defer] ) RIGHTP ;
	public final EugeneParser.ruleDeclaration_return ruleDeclaration(boolean defer) throws RecognitionException {
		EugeneParser.ruleDeclaration_return retval = new EugeneParser.ruleDeclaration_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token name=null;
		Token device=null;
		Token RULE118=null;
		Token LEFTP119=null;
		Token set120=null;
		Token COLON121=null;
		Token RIGHTP122=null;
		ParserRuleReturnScope cnf =null;

		Object name_tree=null;
		Object device_tree=null;
		Object RULE118_tree=null;
		Object LEFTP119_tree=null;
		Object set120_tree=null;
		Object COLON121_tree=null;
		Object RIGHTP122_tree=null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1540:2: ( RULE name= ID LEFTP ( ( ( LC_ON | UC_ON ) device= ID COLON )? cnf= cnf_rule[defer] ) RIGHTP )
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1540:4: RULE name= ID LEFTP ( ( ( LC_ON | UC_ON ) device= ID COLON )? cnf= cnf_rule[defer] ) RIGHTP
			{
			root_0 = (Object)adaptor.nil();


			RULE118=(Token)match(input,RULE,FOLLOW_RULE_in_ruleDeclaration2466); 
			RULE118_tree = (Object)adaptor.create(RULE118);
			adaptor.addChild(root_0, RULE118_tree);

			name=(Token)match(input,ID,FOLLOW_ID_in_ruleDeclaration2470); 
			name_tree = (Object)adaptor.create(name);
			adaptor.addChild(root_0, name_tree);

			LEFTP119=(Token)match(input,LEFTP,FOLLOW_LEFTP_in_ruleDeclaration2472); 
			LEFTP119_tree = (Object)adaptor.create(LEFTP119);
			adaptor.addChild(root_0, LEFTP119_tree);

			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1540:23: ( ( ( LC_ON | UC_ON ) device= ID COLON )? cnf= cnf_rule[defer] )
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1540:25: ( ( LC_ON | UC_ON ) device= ID COLON )? cnf= cnf_rule[defer]
			{
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1540:25: ( ( LC_ON | UC_ON ) device= ID COLON )?
			int alt47=2;
			int LA47_0 = input.LA(1);
			if ( (LA47_0==LC_ON||LA47_0==UC_ON) ) {
				alt47=1;
			}
			switch (alt47) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1540:26: ( LC_ON | UC_ON ) device= ID COLON
					{
					set120=input.LT(1);
					if ( input.LA(1)==LC_ON||input.LA(1)==UC_ON ) {
						input.consume();
						adaptor.addChild(root_0, (Object)adaptor.create(set120));
						state.errorRecovery=false;
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						throw mse;
					}
					device=(Token)match(input,ID,FOLLOW_ID_in_ruleDeclaration2485); 
					device_tree = (Object)adaptor.create(device);
					adaptor.addChild(root_0, device_tree);

					COLON121=(Token)match(input,COLON,FOLLOW_COLON_in_ruleDeclaration2487); 
					COLON121_tree = (Object)adaptor.create(COLON121);
					adaptor.addChild(root_0, COLON121_tree);

					}
					break;

			}


			if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
			    try {
			        if(null == device) {
			            retval.rule = interp.createRule((name!=null?name.getText():null), null);
			        } else {
			            retval.rule = interp.createRule((name!=null?name.getText():null), (device!=null?device.getText():null));
			        }
			    } catch(Exception e) {
			        printError(e.getMessage());
			    }
			}		
				
			pushFollow(FOLLOW_cnf_rule_in_ruleDeclaration2495);
			cnf=cnf_rule(defer);
			state._fsp--;

			adaptor.addChild(root_0, cnf.getTree());

			}

			RIGHTP122=(Token)match(input,RIGHTP,FOLLOW_RIGHTP_in_ruleDeclaration2500); 
			RIGHTP122_tree = (Object)adaptor.create(RIGHTP122);
			adaptor.addChild(root_0, RIGHTP122_tree);


			if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
			    retval.rule.setLogicalAnd((cnf!=null?((EugeneParser.cnf_rule_return)cnf).lAnd:null));

			    /*
			     *  ONLY FOR TESTING
			     */    
			//    this.interp.executeRule(retval.rule); 
			}
				
			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "ruleDeclaration"


	public static class ruleOperator_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "ruleOperator"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1564:1: ruleOperator[boolean defer] : ruleOperators ;
	public final EugeneParser.ruleOperator_return ruleOperator(boolean defer) throws RecognitionException {
		EugeneParser.ruleOperator_return retval = new EugeneParser.ruleOperator_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		ParserRuleReturnScope ruleOperators123 =null;


		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1565:2: ( ruleOperators )
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1565:4: ruleOperators
			{
			root_0 = (Object)adaptor.nil();


			pushFollow(FOLLOW_ruleOperators_in_ruleOperator2514);
			ruleOperators123=ruleOperators();
			state._fsp--;

			adaptor.addChild(root_0, ruleOperators123.getTree());

			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "ruleOperator"


	public static class ruleOperators_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "ruleOperators"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1569:1: ruleOperators : ( ( 'CONTAINS' | 'contains' ) | ( 'EXACTLY' | 'exactly' ) | ( 'MORETHAN' | 'morethan' ) | ( 'SAME_COUNT' | 'same_count' ) | ( 'WITH' | 'with' ) | ( 'THEN' | 'then' ) | ( 'STARTSWITH' | 'startswith' ) | ( 'ENDSWITH' | 'endswith' ) | ( 'BEFORE' | 'before' ) | ( 'ALL_BEFORE' | 'all_before' ) | ( 'SOME_BEFORE' | 'some_before' ) | ( 'AFTER' | 'after' ) | ( 'ALL_AFTER' | 'all_after' ) | ( 'SOME_AFTER' | 'some_after' ) | ( 'NEXTTO' | 'nextto' ) | ( 'ALL_NEXTTO' | 'all_nextto' ) | ( 'SOME_NEXTTO' | 'some_nextto' ) | ( 'ALWAYS_NEXTTO' | 'always_nextto' ) | ( 'EQUALS' | 'equals' ) | ( 'MATCHES' | 'matches' ) | ( 'FORWARD' | 'forward' ) | ( 'ALL_FORWARD' | 'all_forward' ) | ( 'SOME_FORWARD' | 'some_forward' ) | ( 'REVERSE' | 'reverse' ) | ( 'ALL_REVERSE' | 'all_reverse' ) | ( 'SOME_REVERSE' | 'some_reverse' ) | ( 'SAME_ORIENTATION' | 'same_orientation' ) | ( 'ALL_SAME_ORIENTATION' | 'all_same_orientation' ) | ( 'SOME_SAME_ORIENTATION' | 'some_same_orientation' ) | ( 'REPRESSES' | 'represses' ) | ( 'INDUCES' | 'induces' ) | ( 'DRIVES' | 'drives' ) | ( 'ALTERNATE_ORIENTATION' | 'alternate_orientation' ) | ( 'NOTCONTAINS' | 'notcontains' ) | ( 'NOTEXACTLY' | 'notexactly' ) | ( 'NOTMORETHAN' | 'notmorethan' ) | ( 'NOTWITH' | 'notwith' ) | ( 'NOTTHEN' | 'notthen' ) | ( 'NOTEQUALS' | 'notequals' ) | ( 'NOTMATCHES' | 'notmatches' ) );
	public final EugeneParser.ruleOperators_return ruleOperators() throws RecognitionException {
		EugeneParser.ruleOperators_return retval = new EugeneParser.ruleOperators_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token set124=null;

		Object set124_tree=null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1570:2: ( ( 'CONTAINS' | 'contains' ) | ( 'EXACTLY' | 'exactly' ) | ( 'MORETHAN' | 'morethan' ) | ( 'SAME_COUNT' | 'same_count' ) | ( 'WITH' | 'with' ) | ( 'THEN' | 'then' ) | ( 'STARTSWITH' | 'startswith' ) | ( 'ENDSWITH' | 'endswith' ) | ( 'BEFORE' | 'before' ) | ( 'ALL_BEFORE' | 'all_before' ) | ( 'SOME_BEFORE' | 'some_before' ) | ( 'AFTER' | 'after' ) | ( 'ALL_AFTER' | 'all_after' ) | ( 'SOME_AFTER' | 'some_after' ) | ( 'NEXTTO' | 'nextto' ) | ( 'ALL_NEXTTO' | 'all_nextto' ) | ( 'SOME_NEXTTO' | 'some_nextto' ) | ( 'ALWAYS_NEXTTO' | 'always_nextto' ) | ( 'EQUALS' | 'equals' ) | ( 'MATCHES' | 'matches' ) | ( 'FORWARD' | 'forward' ) | ( 'ALL_FORWARD' | 'all_forward' ) | ( 'SOME_FORWARD' | 'some_forward' ) | ( 'REVERSE' | 'reverse' ) | ( 'ALL_REVERSE' | 'all_reverse' ) | ( 'SOME_REVERSE' | 'some_reverse' ) | ( 'SAME_ORIENTATION' | 'same_orientation' ) | ( 'ALL_SAME_ORIENTATION' | 'all_same_orientation' ) | ( 'SOME_SAME_ORIENTATION' | 'some_same_orientation' ) | ( 'REPRESSES' | 'represses' ) | ( 'INDUCES' | 'induces' ) | ( 'DRIVES' | 'drives' ) | ( 'ALTERNATE_ORIENTATION' | 'alternate_orientation' ) | ( 'NOTCONTAINS' | 'notcontains' ) | ( 'NOTEXACTLY' | 'notexactly' ) | ( 'NOTMORETHAN' | 'notmorethan' ) | ( 'NOTWITH' | 'notwith' ) | ( 'NOTTHEN' | 'notthen' ) | ( 'NOTEQUALS' | 'notequals' ) | ( 'NOTMATCHES' | 'notmatches' ) )
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:
			{
			root_0 = (Object)adaptor.nil();


			set124=input.LT(1);
			if ( input.LA(1)==LC_INDUCES||input.LA(1)==LC_REPRESSES||input.LA(1)==UC_INDUCES||input.LA(1)==UC_REPRESSES||(input.LA(1) >= 131 && input.LA(1) <= 165)||(input.LA(1) >= 167 && input.LA(1) <= 204)||(input.LA(1) >= 206 && input.LA(1) <= 208) ) {
				input.consume();
				adaptor.addChild(root_0, (Object)adaptor.create(set124));
				state.errorRecovery=false;
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				throw mse;
			}
			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "ruleOperators"


	public static class relationalOperators_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "relationalOperators"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1613:1: relationalOperators : ( EQUALS EQUALS | NEQUAL | LTHAN | GTHAN | LEQUAL | GEQUAL | ( 'CONTAINS' | 'contains' ) | ( 'NOTCONTAINS' | 'notcontains' ) | ( 'MATCHES' | 'matches' ) | ( 'NOTMATCHES' | 'notmatches' ) | ( 'STARTSWITH' | 'startswith' ) | ( 'ENDSWITH' | 'endswith' ) | ( 'EQUALS' | 'equals' ) | ( 'NOTEQUALS' | 'notequals' ) | ( 'SOUNDSLIKE' | 'soundslike' ) );
	public final EugeneParser.relationalOperators_return relationalOperators() throws RecognitionException {
		EugeneParser.relationalOperators_return retval = new EugeneParser.relationalOperators_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token EQUALS125=null;
		Token EQUALS126=null;
		Token NEQUAL127=null;
		Token LTHAN128=null;
		Token GTHAN129=null;
		Token LEQUAL130=null;
		Token GEQUAL131=null;
		Token set132=null;
		Token set133=null;
		Token set134=null;
		Token set135=null;
		Token set136=null;
		Token set137=null;
		Token set138=null;
		Token set139=null;
		Token set140=null;

		Object EQUALS125_tree=null;
		Object EQUALS126_tree=null;
		Object NEQUAL127_tree=null;
		Object LTHAN128_tree=null;
		Object GTHAN129_tree=null;
		Object LEQUAL130_tree=null;
		Object GEQUAL131_tree=null;
		Object set132_tree=null;
		Object set133_tree=null;
		Object set134_tree=null;
		Object set135_tree=null;
		Object set136_tree=null;
		Object set137_tree=null;
		Object set138_tree=null;
		Object set139_tree=null;
		Object set140_tree=null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1614:2: ( EQUALS EQUALS | NEQUAL | LTHAN | GTHAN | LEQUAL | GEQUAL | ( 'CONTAINS' | 'contains' ) | ( 'NOTCONTAINS' | 'notcontains' ) | ( 'MATCHES' | 'matches' ) | ( 'NOTMATCHES' | 'notmatches' ) | ( 'STARTSWITH' | 'startswith' ) | ( 'ENDSWITH' | 'endswith' ) | ( 'EQUALS' | 'equals' ) | ( 'NOTEQUALS' | 'notequals' ) | ( 'SOUNDSLIKE' | 'soundslike' ) )
			int alt48=15;
			switch ( input.LA(1) ) {
			case EQUALS:
				{
				alt48=1;
				}
				break;
			case NEQUAL:
				{
				alt48=2;
				}
				break;
			case LTHAN:
				{
				alt48=3;
				}
				break;
			case GTHAN:
				{
				alt48=4;
				}
				break;
			case LEQUAL:
				{
				alt48=5;
				}
				break;
			case GEQUAL:
				{
				alt48=6;
				}
				break;
			case 141:
			case 180:
				{
				alt48=7;
				}
				break;
			case 150:
			case 189:
				{
				alt48=8;
				}
				break;
			case 147:
			case 186:
				{
				alt48=9;
				}
				break;
			case 153:
			case 192:
				{
				alt48=10;
				}
				break;
			case 167:
			case 206:
				{
				alt48=11;
				}
				break;
			case 143:
			case 182:
				{
				alt48=12;
				}
				break;
			case 144:
			case 183:
				{
				alt48=13;
				}
				break;
			case 151:
			case 190:
				{
				alt48=14;
				}
				break;
			case 166:
			case 205:
				{
				alt48=15;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 48, 0, input);
				throw nvae;
			}
			switch (alt48) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1614:4: EQUALS EQUALS
					{
					root_0 = (Object)adaptor.nil();


					EQUALS125=(Token)match(input,EQUALS,FOLLOW_EQUALS_in_relationalOperators2893); 
					EQUALS125_tree = (Object)adaptor.create(EQUALS125);
					adaptor.addChild(root_0, EQUALS125_tree);

					EQUALS126=(Token)match(input,EQUALS,FOLLOW_EQUALS_in_relationalOperators2895); 
					EQUALS126_tree = (Object)adaptor.create(EQUALS126);
					adaptor.addChild(root_0, EQUALS126_tree);

					}
					break;
				case 2 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1615:4: NEQUAL
					{
					root_0 = (Object)adaptor.nil();


					NEQUAL127=(Token)match(input,NEQUAL,FOLLOW_NEQUAL_in_relationalOperators2900); 
					NEQUAL127_tree = (Object)adaptor.create(NEQUAL127);
					adaptor.addChild(root_0, NEQUAL127_tree);

					}
					break;
				case 3 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1616:4: LTHAN
					{
					root_0 = (Object)adaptor.nil();


					LTHAN128=(Token)match(input,LTHAN,FOLLOW_LTHAN_in_relationalOperators2905); 
					LTHAN128_tree = (Object)adaptor.create(LTHAN128);
					adaptor.addChild(root_0, LTHAN128_tree);

					}
					break;
				case 4 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1617:4: GTHAN
					{
					root_0 = (Object)adaptor.nil();


					GTHAN129=(Token)match(input,GTHAN,FOLLOW_GTHAN_in_relationalOperators2910); 
					GTHAN129_tree = (Object)adaptor.create(GTHAN129);
					adaptor.addChild(root_0, GTHAN129_tree);

					}
					break;
				case 5 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1618:4: LEQUAL
					{
					root_0 = (Object)adaptor.nil();


					LEQUAL130=(Token)match(input,LEQUAL,FOLLOW_LEQUAL_in_relationalOperators2915); 
					LEQUAL130_tree = (Object)adaptor.create(LEQUAL130);
					adaptor.addChild(root_0, LEQUAL130_tree);

					}
					break;
				case 6 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1619:4: GEQUAL
					{
					root_0 = (Object)adaptor.nil();


					GEQUAL131=(Token)match(input,GEQUAL,FOLLOW_GEQUAL_in_relationalOperators2920); 
					GEQUAL131_tree = (Object)adaptor.create(GEQUAL131);
					adaptor.addChild(root_0, GEQUAL131_tree);

					}
					break;
				case 7 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1620:4: ( 'CONTAINS' | 'contains' )
					{
					root_0 = (Object)adaptor.nil();


					set132=input.LT(1);
					if ( input.LA(1)==141||input.LA(1)==180 ) {
						input.consume();
						adaptor.addChild(root_0, (Object)adaptor.create(set132));
						state.errorRecovery=false;
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						throw mse;
					}
					}
					break;
				case 8 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1621:4: ( 'NOTCONTAINS' | 'notcontains' )
					{
					root_0 = (Object)adaptor.nil();


					set133=input.LT(1);
					if ( input.LA(1)==150||input.LA(1)==189 ) {
						input.consume();
						adaptor.addChild(root_0, (Object)adaptor.create(set133));
						state.errorRecovery=false;
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						throw mse;
					}
					}
					break;
				case 9 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1622:4: ( 'MATCHES' | 'matches' )
					{
					root_0 = (Object)adaptor.nil();


					set134=input.LT(1);
					if ( input.LA(1)==147||input.LA(1)==186 ) {
						input.consume();
						adaptor.addChild(root_0, (Object)adaptor.create(set134));
						state.errorRecovery=false;
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						throw mse;
					}
					}
					break;
				case 10 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1623:4: ( 'NOTMATCHES' | 'notmatches' )
					{
					root_0 = (Object)adaptor.nil();


					set135=input.LT(1);
					if ( input.LA(1)==153||input.LA(1)==192 ) {
						input.consume();
						adaptor.addChild(root_0, (Object)adaptor.create(set135));
						state.errorRecovery=false;
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						throw mse;
					}
					}
					break;
				case 11 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1624:4: ( 'STARTSWITH' | 'startswith' )
					{
					root_0 = (Object)adaptor.nil();


					set136=input.LT(1);
					if ( input.LA(1)==167||input.LA(1)==206 ) {
						input.consume();
						adaptor.addChild(root_0, (Object)adaptor.create(set136));
						state.errorRecovery=false;
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						throw mse;
					}
					}
					break;
				case 12 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1625:4: ( 'ENDSWITH' | 'endswith' )
					{
					root_0 = (Object)adaptor.nil();


					set137=input.LT(1);
					if ( input.LA(1)==143||input.LA(1)==182 ) {
						input.consume();
						adaptor.addChild(root_0, (Object)adaptor.create(set137));
						state.errorRecovery=false;
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						throw mse;
					}
					}
					break;
				case 13 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1626:4: ( 'EQUALS' | 'equals' )
					{
					root_0 = (Object)adaptor.nil();


					set138=input.LT(1);
					if ( input.LA(1)==144||input.LA(1)==183 ) {
						input.consume();
						adaptor.addChild(root_0, (Object)adaptor.create(set138));
						state.errorRecovery=false;
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						throw mse;
					}
					}
					break;
				case 14 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1627:4: ( 'NOTEQUALS' | 'notequals' )
					{
					root_0 = (Object)adaptor.nil();


					set139=input.LT(1);
					if ( input.LA(1)==151||input.LA(1)==190 ) {
						input.consume();
						adaptor.addChild(root_0, (Object)adaptor.create(set139));
						state.errorRecovery=false;
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						throw mse;
					}
					}
					break;
				case 15 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1628:4: ( 'SOUNDSLIKE' | 'soundslike' )
					{
					root_0 = (Object)adaptor.nil();


					set140=input.LT(1);
					if ( input.LA(1)==166||input.LA(1)==205 ) {
						input.consume();
						adaptor.addChild(root_0, (Object)adaptor.create(set140));
						state.errorRecovery=false;
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						throw mse;
					}
					}
					break;

			}
			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "relationalOperators"


	public static class cnf_rule_return extends ParserRuleReturnScope {
		public LogicalAnd lAnd;
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "cnf_rule"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1631:1: cnf_rule[boolean defer] returns [LogicalAnd lAnd] : (c= or_predicate[defer] ) ( ( LC_AND | UC_AND | LOG_AND ) cnf= cnf_rule[defer] )? ;
	public final EugeneParser.cnf_rule_return cnf_rule(boolean defer) throws RecognitionException {
		EugeneParser.cnf_rule_return retval = new EugeneParser.cnf_rule_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token set141=null;
		ParserRuleReturnScope c =null;
		ParserRuleReturnScope cnf =null;

		Object set141_tree=null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1633:2: ( (c= or_predicate[defer] ) ( ( LC_AND | UC_AND | LOG_AND ) cnf= cnf_rule[defer] )? )
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1633:4: (c= or_predicate[defer] ) ( ( LC_AND | UC_AND | LOG_AND ) cnf= cnf_rule[defer] )?
			{
			root_0 = (Object)adaptor.nil();


			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1633:4: (c= or_predicate[defer] )
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1633:5: c= or_predicate[defer]
			{
			pushFollow(FOLLOW_or_predicate_in_cnf_rule3021);
			c=or_predicate(defer);
			state._fsp--;

			adaptor.addChild(root_0, c.getTree());


			if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
			    if(null == retval.lAnd) {
			        retval.lAnd = new LogicalAnd();
			    }
			    
			    retval.lAnd.getPredicates().add((c!=null?((EugeneParser.or_predicate_return)c).p:null));
			}	
				
			}

			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1641:5: ( ( LC_AND | UC_AND | LOG_AND ) cnf= cnf_rule[defer] )?
			int alt49=2;
			int LA49_0 = input.LA(1);
			if ( (LA49_0==LC_AND||LA49_0==LOG_AND||LA49_0==UC_AND) ) {
				alt49=1;
			}
			switch (alt49) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1641:7: ( LC_AND | UC_AND | LOG_AND ) cnf= cnf_rule[defer]
					{
					set141=input.LT(1);
					if ( input.LA(1)==LC_AND||input.LA(1)==LOG_AND||input.LA(1)==UC_AND ) {
						input.consume();
						adaptor.addChild(root_0, (Object)adaptor.create(set141));
						state.errorRecovery=false;
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						throw mse;
					}
					pushFollow(FOLLOW_cnf_rule_in_cnf_rule3039);
					cnf=cnf_rule(defer);
					state._fsp--;

					adaptor.addChild(root_0, cnf.getTree());


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    retval.lAnd.union((cnf!=null?((EugeneParser.cnf_rule_return)cnf).lAnd:null));
					}	
						
					}
					break;

			}

			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "cnf_rule"


	public static class or_predicate_return extends ParserRuleReturnScope {
		public Predicate p;
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "or_predicate"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1648:1: or_predicate[boolean defer] returns [Predicate p] : n1= negated_predicate[defer] ( ( LC_OR | UC_OR | LOG_OR ) n2= negated_predicate[defer] )* ;
	public final EugeneParser.or_predicate_return or_predicate(boolean defer) throws RecognitionException {
		EugeneParser.or_predicate_return retval = new EugeneParser.or_predicate_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token set142=null;
		ParserRuleReturnScope n1 =null;
		ParserRuleReturnScope n2 =null;

		Object set142_tree=null;


		LogicalOr lor = null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1653:2: (n1= negated_predicate[defer] ( ( LC_OR | UC_OR | LOG_OR ) n2= negated_predicate[defer] )* )
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1653:4: n1= negated_predicate[defer] ( ( LC_OR | UC_OR | LOG_OR ) n2= negated_predicate[defer] )*
			{
			root_0 = (Object)adaptor.nil();


			pushFollow(FOLLOW_negated_predicate_in_or_predicate3069);
			n1=negated_predicate(defer);
			state._fsp--;

			adaptor.addChild(root_0, n1.getTree());


			if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
			    retval.p = (n1!=null?((EugeneParser.negated_predicate_return)n1).p:null);
			}	
				
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1657:4: ( ( LC_OR | UC_OR | LOG_OR ) n2= negated_predicate[defer] )*
			loop50:
			while (true) {
				int alt50=2;
				int LA50_0 = input.LA(1);
				if ( (LA50_0==LC_OR||LA50_0==LOG_OR||LA50_0==UC_OR) ) {
					alt50=1;
				}

				switch (alt50) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1657:5: ( LC_OR | UC_OR | LOG_OR ) n2= negated_predicate[defer]
					{
					set142=input.LT(1);
					if ( input.LA(1)==LC_OR||input.LA(1)==LOG_OR||input.LA(1)==UC_OR ) {
						input.consume();
						adaptor.addChild(root_0, (Object)adaptor.create(set142));
						state.errorRecovery=false;
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						throw mse;
					}
					pushFollow(FOLLOW_negated_predicate_in_or_predicate3085);
					n2=negated_predicate(defer);
					state._fsp--;

					adaptor.addChild(root_0, n2.getTree());


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    try {
					        if(null == lor) {
					            lor = this.interp.logicalOr((n1!=null?((EugeneParser.negated_predicate_return)n1).p:null), (n2!=null?((EugeneParser.negated_predicate_return)n2).p:null));
					        } else {
					            lor = this.interp.logicalOr(lor, (n2!=null?((EugeneParser.negated_predicate_return)n2).p:null)); 
					        }
					    } catch(EugeneException ee) {
					        printError(ee.getMessage());
					    }
					}	
						
					}
					break;

				default :
					break loop50;
				}
			}


			if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
			    if(null != lor) {
			        retval.p = lor;
			    }
			}	
				
			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "or_predicate"


	public static class negated_predicate_return extends ParserRuleReturnScope {
		public Predicate p;
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "negated_predicate"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1678:1: negated_predicate[boolean defer] returns [Predicate p] : ( ( UC_NOT | LC_NOT | OP_NOT ) c= predicate[defer] |c= predicate[defer] ) ;
	public final EugeneParser.negated_predicate_return negated_predicate(boolean defer) throws RecognitionException {
		EugeneParser.negated_predicate_return retval = new EugeneParser.negated_predicate_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token set143=null;
		ParserRuleReturnScope c =null;

		Object set143_tree=null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1680:2: ( ( ( UC_NOT | LC_NOT | OP_NOT ) c= predicate[defer] |c= predicate[defer] ) )
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1680:4: ( ( UC_NOT | LC_NOT | OP_NOT ) c= predicate[defer] |c= predicate[defer] )
			{
			root_0 = (Object)adaptor.nil();


			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1680:4: ( ( UC_NOT | LC_NOT | OP_NOT ) c= predicate[defer] |c= predicate[defer] )
			int alt51=2;
			int LA51_0 = input.LA(1);
			if ( (LA51_0==LC_NOT||LA51_0==OP_NOT||LA51_0==UC_NOT) ) {
				alt51=1;
			}
			else if ( (LA51_0==ID||LA51_0==LC_INDUCES||LA51_0==LC_REPRESSES||(LA51_0 >= LEFTP && LA51_0 <= LEFTSBR)||LA51_0==MINUS||LA51_0==NUMBER||LA51_0==REAL||LA51_0==STRING||LA51_0==UC_INDUCES||LA51_0==UC_REPRESSES||(LA51_0 >= 131 && LA51_0 <= 165)||(LA51_0 >= 167 && LA51_0 <= 204)||(LA51_0 >= 206 && LA51_0 <= 208)) ) {
				alt51=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 51, 0, input);
				throw nvae;
			}

			switch (alt51) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1680:5: ( UC_NOT | LC_NOT | OP_NOT ) c= predicate[defer]
					{
					set143=input.LT(1);
					if ( input.LA(1)==LC_NOT||input.LA(1)==OP_NOT||input.LA(1)==UC_NOT ) {
						input.consume();
						adaptor.addChild(root_0, (Object)adaptor.create(set143));
						state.errorRecovery=false;
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						throw mse;
					}
					pushFollow(FOLLOW_predicate_in_negated_predicate3123);
					c=predicate(defer);
					state._fsp--;

					adaptor.addChild(root_0, c.getTree());


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    try {
					        retval.p = this.interp.negate((c!=null?((EugeneParser.predicate_return)c).p:null));
					    } catch(Exception e) {
					        printError(e.getMessage());
					    }
					}
						
					}
					break;
				case 2 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1689:4: c= predicate[defer]
					{
					pushFollow(FOLLOW_predicate_in_negated_predicate3133);
					c=predicate(defer);
					state._fsp--;

					adaptor.addChild(root_0, c.getTree());


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    retval.p = (c!=null?((EugeneParser.predicate_return)c).p:null);
					}	
						
					}
					break;

			}

			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "negated_predicate"


	public static class predicate_return extends ParserRuleReturnScope {
		public Predicate p;
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "predicate"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1696:1: predicate[boolean defer] returns [Predicate p] : ( (lhs= operand[defer] )? op= ruleOperator[defer] (rhs= operand[defer] )? |i= ID |exp= expressionRule[defer] );
	public final EugeneParser.predicate_return predicate(boolean defer) throws RecognitionException {
		EugeneParser.predicate_return retval = new EugeneParser.predicate_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token i=null;
		ParserRuleReturnScope lhs =null;
		ParserRuleReturnScope op =null;
		ParserRuleReturnScope rhs =null;
		ParserRuleReturnScope exp =null;

		Object i_tree=null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1698:2: ( (lhs= operand[defer] )? op= ruleOperator[defer] (rhs= operand[defer] )? |i= ID |exp= expressionRule[defer] )
			int alt54=3;
			switch ( input.LA(1) ) {
			case ID:
				{
				switch ( input.LA(2) ) {
				case DIV:
				case DOT:
				case EQUALS:
				case GEQUAL:
				case GTHAN:
				case LEFTSBR:
				case LEQUAL:
				case LTHAN:
				case MINUS:
				case MULT:
				case NEQUAL:
				case PLUS:
				case 166:
				case 205:
					{
					alt54=3;
					}
					break;
				case 141:
				case 180:
					{
					switch ( input.LA(3) ) {
					case ID:
						{
						alt54=1;
						}
						break;
					case LC_AND:
					case LC_OR:
					case LEFTSBR:
					case LOG_AND:
					case LOG_OR:
					case NUMBER:
					case RIGHTP:
					case UC_AND:
					case UC_OR:
						{
						alt54=1;
						}
						break;
					case LEFTP:
					case MINUS:
					case REAL:
					case STRING:
						{
						alt54=3;
						}
						break;
					default:
						int nvaeMark = input.mark();
						try {
							for (int nvaeConsume = 0; nvaeConsume < 3 - 1; nvaeConsume++) {
								input.consume();
							}
							NoViableAltException nvae =
								new NoViableAltException("", 54, 5, input);
							throw nvae;
						} finally {
							input.rewind(nvaeMark);
						}
					}
					}
					break;
				case LC_AND:
				case LC_OR:
				case LOG_AND:
				case LOG_OR:
				case RIGHTP:
				case UC_AND:
				case UC_OR:
					{
					alt54=2;
					}
					break;
				case 150:
				case 189:
					{
					switch ( input.LA(3) ) {
					case ID:
						{
						alt54=1;
						}
						break;
					case LC_AND:
					case LC_OR:
					case LEFTSBR:
					case LOG_AND:
					case LOG_OR:
					case NUMBER:
					case RIGHTP:
					case UC_AND:
					case UC_OR:
						{
						alt54=1;
						}
						break;
					case LEFTP:
					case MINUS:
					case REAL:
					case STRING:
						{
						alt54=3;
						}
						break;
					default:
						int nvaeMark = input.mark();
						try {
							for (int nvaeConsume = 0; nvaeConsume < 3 - 1; nvaeConsume++) {
								input.consume();
							}
							NoViableAltException nvae =
								new NoViableAltException("", 54, 7, input);
							throw nvae;
						} finally {
							input.rewind(nvaeMark);
						}
					}
					}
					break;
				case 147:
				case 186:
					{
					switch ( input.LA(3) ) {
					case ID:
						{
						alt54=1;
						}
						break;
					case LC_AND:
					case LC_OR:
					case LEFTSBR:
					case LOG_AND:
					case LOG_OR:
					case NUMBER:
					case RIGHTP:
					case UC_AND:
					case UC_OR:
						{
						alt54=1;
						}
						break;
					case LEFTP:
					case MINUS:
					case REAL:
					case STRING:
						{
						alt54=3;
						}
						break;
					default:
						int nvaeMark = input.mark();
						try {
							for (int nvaeConsume = 0; nvaeConsume < 3 - 1; nvaeConsume++) {
								input.consume();
							}
							NoViableAltException nvae =
								new NoViableAltException("", 54, 8, input);
							throw nvae;
						} finally {
							input.rewind(nvaeMark);
						}
					}
					}
					break;
				case 153:
				case 192:
					{
					switch ( input.LA(3) ) {
					case ID:
						{
						alt54=1;
						}
						break;
					case LC_AND:
					case LC_OR:
					case LEFTSBR:
					case LOG_AND:
					case LOG_OR:
					case NUMBER:
					case RIGHTP:
					case UC_AND:
					case UC_OR:
						{
						alt54=1;
						}
						break;
					case LEFTP:
					case MINUS:
					case REAL:
					case STRING:
						{
						alt54=3;
						}
						break;
					default:
						int nvaeMark = input.mark();
						try {
							for (int nvaeConsume = 0; nvaeConsume < 3 - 1; nvaeConsume++) {
								input.consume();
							}
							NoViableAltException nvae =
								new NoViableAltException("", 54, 9, input);
							throw nvae;
						} finally {
							input.rewind(nvaeMark);
						}
					}
					}
					break;
				case 167:
				case 206:
					{
					switch ( input.LA(3) ) {
					case ID:
						{
						alt54=1;
						}
						break;
					case LC_AND:
					case LC_OR:
					case LEFTSBR:
					case LOG_AND:
					case LOG_OR:
					case NUMBER:
					case RIGHTP:
					case UC_AND:
					case UC_OR:
						{
						alt54=1;
						}
						break;
					case LEFTP:
					case MINUS:
					case REAL:
					case STRING:
						{
						alt54=3;
						}
						break;
					default:
						int nvaeMark = input.mark();
						try {
							for (int nvaeConsume = 0; nvaeConsume < 3 - 1; nvaeConsume++) {
								input.consume();
							}
							NoViableAltException nvae =
								new NoViableAltException("", 54, 10, input);
							throw nvae;
						} finally {
							input.rewind(nvaeMark);
						}
					}
					}
					break;
				case 143:
				case 182:
					{
					switch ( input.LA(3) ) {
					case ID:
						{
						alt54=1;
						}
						break;
					case LC_AND:
					case LC_OR:
					case LEFTSBR:
					case LOG_AND:
					case LOG_OR:
					case NUMBER:
					case RIGHTP:
					case UC_AND:
					case UC_OR:
						{
						alt54=1;
						}
						break;
					case LEFTP:
					case MINUS:
					case REAL:
					case STRING:
						{
						alt54=3;
						}
						break;
					default:
						int nvaeMark = input.mark();
						try {
							for (int nvaeConsume = 0; nvaeConsume < 3 - 1; nvaeConsume++) {
								input.consume();
							}
							NoViableAltException nvae =
								new NoViableAltException("", 54, 11, input);
							throw nvae;
						} finally {
							input.rewind(nvaeMark);
						}
					}
					}
					break;
				case 144:
				case 183:
					{
					switch ( input.LA(3) ) {
					case ID:
						{
						alt54=1;
						}
						break;
					case LC_AND:
					case LC_OR:
					case LEFTSBR:
					case LOG_AND:
					case LOG_OR:
					case NUMBER:
					case RIGHTP:
					case UC_AND:
					case UC_OR:
						{
						alt54=1;
						}
						break;
					case LEFTP:
					case MINUS:
					case REAL:
					case STRING:
						{
						alt54=3;
						}
						break;
					default:
						int nvaeMark = input.mark();
						try {
							for (int nvaeConsume = 0; nvaeConsume < 3 - 1; nvaeConsume++) {
								input.consume();
							}
							NoViableAltException nvae =
								new NoViableAltException("", 54, 12, input);
							throw nvae;
						} finally {
							input.rewind(nvaeMark);
						}
					}
					}
					break;
				case 151:
				case 190:
					{
					switch ( input.LA(3) ) {
					case ID:
						{
						alt54=1;
						}
						break;
					case LC_AND:
					case LC_OR:
					case LEFTSBR:
					case LOG_AND:
					case LOG_OR:
					case NUMBER:
					case RIGHTP:
					case UC_AND:
					case UC_OR:
						{
						alt54=1;
						}
						break;
					case LEFTP:
					case MINUS:
					case REAL:
					case STRING:
						{
						alt54=3;
						}
						break;
					default:
						int nvaeMark = input.mark();
						try {
							for (int nvaeConsume = 0; nvaeConsume < 3 - 1; nvaeConsume++) {
								input.consume();
							}
							NoViableAltException nvae =
								new NoViableAltException("", 54, 13, input);
							throw nvae;
						} finally {
							input.rewind(nvaeMark);
						}
					}
					}
					break;
				case LC_INDUCES:
				case LC_REPRESSES:
				case UC_INDUCES:
				case UC_REPRESSES:
				case 131:
				case 132:
				case 133:
				case 134:
				case 135:
				case 136:
				case 137:
				case 138:
				case 139:
				case 140:
				case 142:
				case 145:
				case 146:
				case 148:
				case 149:
				case 152:
				case 154:
				case 155:
				case 156:
				case 157:
				case 158:
				case 159:
				case 160:
				case 161:
				case 162:
				case 163:
				case 164:
				case 165:
				case 168:
				case 169:
				case 170:
				case 171:
				case 172:
				case 173:
				case 174:
				case 175:
				case 176:
				case 177:
				case 178:
				case 179:
				case 181:
				case 184:
				case 185:
				case 187:
				case 188:
				case 191:
				case 193:
				case 194:
				case 195:
				case 196:
				case 197:
				case 198:
				case 199:
				case 200:
				case 201:
				case 202:
				case 203:
				case 204:
				case 207:
				case 208:
					{
					alt54=1;
					}
					break;
				default:
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 54, 1, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}
				}
				break;
			case NUMBER:
				{
				switch ( input.LA(2) ) {
				case 141:
				case 180:
					{
					switch ( input.LA(3) ) {
					case ID:
						{
						alt54=1;
						}
						break;
					case LC_AND:
					case LC_OR:
					case LEFTSBR:
					case LOG_AND:
					case LOG_OR:
					case NUMBER:
					case RIGHTP:
					case UC_AND:
					case UC_OR:
						{
						alt54=1;
						}
						break;
					case LEFTP:
					case MINUS:
					case REAL:
					case STRING:
						{
						alt54=3;
						}
						break;
					default:
						int nvaeMark = input.mark();
						try {
							for (int nvaeConsume = 0; nvaeConsume < 3 - 1; nvaeConsume++) {
								input.consume();
							}
							NoViableAltException nvae =
								new NoViableAltException("", 54, 5, input);
							throw nvae;
						} finally {
							input.rewind(nvaeMark);
						}
					}
					}
					break;
				case DIV:
				case EQUALS:
				case GEQUAL:
				case GTHAN:
				case LEQUAL:
				case LTHAN:
				case MINUS:
				case MULT:
				case NEQUAL:
				case PLUS:
				case 166:
				case 205:
					{
					alt54=3;
					}
					break;
				case 150:
				case 189:
					{
					switch ( input.LA(3) ) {
					case ID:
						{
						alt54=1;
						}
						break;
					case LC_AND:
					case LC_OR:
					case LEFTSBR:
					case LOG_AND:
					case LOG_OR:
					case NUMBER:
					case RIGHTP:
					case UC_AND:
					case UC_OR:
						{
						alt54=1;
						}
						break;
					case LEFTP:
					case MINUS:
					case REAL:
					case STRING:
						{
						alt54=3;
						}
						break;
					default:
						int nvaeMark = input.mark();
						try {
							for (int nvaeConsume = 0; nvaeConsume < 3 - 1; nvaeConsume++) {
								input.consume();
							}
							NoViableAltException nvae =
								new NoViableAltException("", 54, 7, input);
							throw nvae;
						} finally {
							input.rewind(nvaeMark);
						}
					}
					}
					break;
				case 147:
				case 186:
					{
					switch ( input.LA(3) ) {
					case ID:
						{
						alt54=1;
						}
						break;
					case LC_AND:
					case LC_OR:
					case LEFTSBR:
					case LOG_AND:
					case LOG_OR:
					case NUMBER:
					case RIGHTP:
					case UC_AND:
					case UC_OR:
						{
						alt54=1;
						}
						break;
					case LEFTP:
					case MINUS:
					case REAL:
					case STRING:
						{
						alt54=3;
						}
						break;
					default:
						int nvaeMark = input.mark();
						try {
							for (int nvaeConsume = 0; nvaeConsume < 3 - 1; nvaeConsume++) {
								input.consume();
							}
							NoViableAltException nvae =
								new NoViableAltException("", 54, 8, input);
							throw nvae;
						} finally {
							input.rewind(nvaeMark);
						}
					}
					}
					break;
				case 153:
				case 192:
					{
					switch ( input.LA(3) ) {
					case ID:
						{
						alt54=1;
						}
						break;
					case LC_AND:
					case LC_OR:
					case LEFTSBR:
					case LOG_AND:
					case LOG_OR:
					case NUMBER:
					case RIGHTP:
					case UC_AND:
					case UC_OR:
						{
						alt54=1;
						}
						break;
					case LEFTP:
					case MINUS:
					case REAL:
					case STRING:
						{
						alt54=3;
						}
						break;
					default:
						int nvaeMark = input.mark();
						try {
							for (int nvaeConsume = 0; nvaeConsume < 3 - 1; nvaeConsume++) {
								input.consume();
							}
							NoViableAltException nvae =
								new NoViableAltException("", 54, 9, input);
							throw nvae;
						} finally {
							input.rewind(nvaeMark);
						}
					}
					}
					break;
				case 167:
				case 206:
					{
					switch ( input.LA(3) ) {
					case ID:
						{
						alt54=1;
						}
						break;
					case LC_AND:
					case LC_OR:
					case LEFTSBR:
					case LOG_AND:
					case LOG_OR:
					case NUMBER:
					case RIGHTP:
					case UC_AND:
					case UC_OR:
						{
						alt54=1;
						}
						break;
					case LEFTP:
					case MINUS:
					case REAL:
					case STRING:
						{
						alt54=3;
						}
						break;
					default:
						int nvaeMark = input.mark();
						try {
							for (int nvaeConsume = 0; nvaeConsume < 3 - 1; nvaeConsume++) {
								input.consume();
							}
							NoViableAltException nvae =
								new NoViableAltException("", 54, 10, input);
							throw nvae;
						} finally {
							input.rewind(nvaeMark);
						}
					}
					}
					break;
				case 143:
				case 182:
					{
					switch ( input.LA(3) ) {
					case ID:
						{
						alt54=1;
						}
						break;
					case LC_AND:
					case LC_OR:
					case LEFTSBR:
					case LOG_AND:
					case LOG_OR:
					case NUMBER:
					case RIGHTP:
					case UC_AND:
					case UC_OR:
						{
						alt54=1;
						}
						break;
					case LEFTP:
					case MINUS:
					case REAL:
					case STRING:
						{
						alt54=3;
						}
						break;
					default:
						int nvaeMark = input.mark();
						try {
							for (int nvaeConsume = 0; nvaeConsume < 3 - 1; nvaeConsume++) {
								input.consume();
							}
							NoViableAltException nvae =
								new NoViableAltException("", 54, 11, input);
							throw nvae;
						} finally {
							input.rewind(nvaeMark);
						}
					}
					}
					break;
				case 144:
				case 183:
					{
					switch ( input.LA(3) ) {
					case ID:
						{
						alt54=1;
						}
						break;
					case LC_AND:
					case LC_OR:
					case LEFTSBR:
					case LOG_AND:
					case LOG_OR:
					case NUMBER:
					case RIGHTP:
					case UC_AND:
					case UC_OR:
						{
						alt54=1;
						}
						break;
					case LEFTP:
					case MINUS:
					case REAL:
					case STRING:
						{
						alt54=3;
						}
						break;
					default:
						int nvaeMark = input.mark();
						try {
							for (int nvaeConsume = 0; nvaeConsume < 3 - 1; nvaeConsume++) {
								input.consume();
							}
							NoViableAltException nvae =
								new NoViableAltException("", 54, 12, input);
							throw nvae;
						} finally {
							input.rewind(nvaeMark);
						}
					}
					}
					break;
				case 151:
				case 190:
					{
					switch ( input.LA(3) ) {
					case ID:
						{
						alt54=1;
						}
						break;
					case LC_AND:
					case LC_OR:
					case LEFTSBR:
					case LOG_AND:
					case LOG_OR:
					case NUMBER:
					case RIGHTP:
					case UC_AND:
					case UC_OR:
						{
						alt54=1;
						}
						break;
					case LEFTP:
					case MINUS:
					case REAL:
					case STRING:
						{
						alt54=3;
						}
						break;
					default:
						int nvaeMark = input.mark();
						try {
							for (int nvaeConsume = 0; nvaeConsume < 3 - 1; nvaeConsume++) {
								input.consume();
							}
							NoViableAltException nvae =
								new NoViableAltException("", 54, 13, input);
							throw nvae;
						} finally {
							input.rewind(nvaeMark);
						}
					}
					}
					break;
				case LC_INDUCES:
				case LC_REPRESSES:
				case UC_INDUCES:
				case UC_REPRESSES:
				case 131:
				case 132:
				case 133:
				case 134:
				case 135:
				case 136:
				case 137:
				case 138:
				case 139:
				case 140:
				case 142:
				case 145:
				case 146:
				case 148:
				case 149:
				case 152:
				case 154:
				case 155:
				case 156:
				case 157:
				case 158:
				case 159:
				case 160:
				case 161:
				case 162:
				case 163:
				case 164:
				case 165:
				case 168:
				case 169:
				case 170:
				case 171:
				case 172:
				case 173:
				case 174:
				case 175:
				case 176:
				case 177:
				case 178:
				case 179:
				case 181:
				case 184:
				case 185:
				case 187:
				case 188:
				case 191:
				case 193:
				case 194:
				case 195:
				case 196:
				case 197:
				case 198:
				case 199:
				case 200:
				case 201:
				case 202:
				case 203:
				case 204:
				case 207:
				case 208:
					{
					alt54=1;
					}
					break;
				default:
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 54, 2, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}
				}
				break;
			case LC_INDUCES:
			case LC_REPRESSES:
			case LEFTSBR:
			case UC_INDUCES:
			case UC_REPRESSES:
			case 131:
			case 132:
			case 133:
			case 134:
			case 135:
			case 136:
			case 137:
			case 138:
			case 139:
			case 140:
			case 141:
			case 142:
			case 143:
			case 144:
			case 145:
			case 146:
			case 147:
			case 148:
			case 149:
			case 150:
			case 151:
			case 152:
			case 153:
			case 154:
			case 155:
			case 156:
			case 157:
			case 158:
			case 159:
			case 160:
			case 161:
			case 162:
			case 163:
			case 164:
			case 165:
			case 167:
			case 168:
			case 169:
			case 170:
			case 171:
			case 172:
			case 173:
			case 174:
			case 175:
			case 176:
			case 177:
			case 178:
			case 179:
			case 180:
			case 181:
			case 182:
			case 183:
			case 184:
			case 185:
			case 186:
			case 187:
			case 188:
			case 189:
			case 190:
			case 191:
			case 192:
			case 193:
			case 194:
			case 195:
			case 196:
			case 197:
			case 198:
			case 199:
			case 200:
			case 201:
			case 202:
			case 203:
			case 204:
			case 206:
			case 207:
			case 208:
				{
				alt54=1;
				}
				break;
			case LEFTP:
			case MINUS:
			case REAL:
			case STRING:
				{
				alt54=3;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 54, 0, input);
				throw nvae;
			}
			switch (alt54) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1698:4: (lhs= operand[defer] )? op= ruleOperator[defer] (rhs= operand[defer] )?
					{
					root_0 = (Object)adaptor.nil();


					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1698:4: (lhs= operand[defer] )?
					int alt52=2;
					int LA52_0 = input.LA(1);
					if ( (LA52_0==ID||LA52_0==LEFTSBR||LA52_0==NUMBER) ) {
						alt52=1;
					}
					switch (alt52) {
						case 1 :
							// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1698:5: lhs= operand[defer]
							{
							pushFollow(FOLLOW_operand_in_predicate3160);
							lhs=operand(defer);
							state._fsp--;

							adaptor.addChild(root_0, lhs.getTree());


							addToken((lhs!=null?input.toString(lhs.start,lhs.stop):null));	
								
							}
							break;

					}

					pushFollow(FOLLOW_ruleOperator_in_predicate3170);
					op=ruleOperator(defer);
					state._fsp--;

					adaptor.addChild(root_0, op.getTree());


					addToken((op!=null?input.toString(op.start,op.stop):null));	
						
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1702:5: (rhs= operand[defer] )?
					int alt53=2;
					int LA53_0 = input.LA(1);
					if ( (LA53_0==ID||LA53_0==LEFTSBR||LA53_0==NUMBER) ) {
						alt53=1;
					}
					switch (alt53) {
						case 1 :
							// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1702:6: rhs= operand[defer]
							{
							pushFollow(FOLLOW_operand_in_predicate3179);
							rhs=operand(defer);
							state._fsp--;

							adaptor.addChild(root_0, rhs.getTree());


							addToken((rhs!=null?input.toString(rhs.start,rhs.stop):null));	
								
							}
							break;

					}



					try {
					    retval.p = this.interp.createPredicate((lhs!=null?((EugeneParser.operand_return)lhs).o:null), (op!=null?input.toString(op.start,op.stop):null), (rhs!=null?((EugeneParser.operand_return)rhs).o:null));
					//    retval.p = this.interp.createPredicate(this.tokens);
					} catch(EugeneException ee) {
					    printError(ee.getMessage());
					}

					// reset the global token array
					this.tokens = null;
						
					}
					break;
				case 2 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1716:4: i= ID
					{
					root_0 = (Object)adaptor.nil();


					i=(Token)match(input,ID,FOLLOW_ID_in_predicate3193); 
					i_tree = (Object)adaptor.create(i);
					adaptor.addChild(root_0, i_tree);


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    try {
					        NamedElement ne = this.interp.get((i!=null?i.getText():null));
					        if(null == ne) {
					            printError((i!=null?i.getText():null)+" is not defined.");
					        }
					        if(!(ne instanceof Rule)) {
					            printError((i!=null?i.getText():null)+" is not a Rule.");
					        }
					    
					        retval.p = ((Rule)ne);
					    } catch(EugeneException ee) {
					        printError(ee.getMessage());
					    }
					}		
						
					}
					break;
				case 3 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1733:4: exp= expressionRule[defer]
					{
					root_0 = (Object)adaptor.nil();


					pushFollow(FOLLOW_expressionRule_in_predicate3202);
					exp=expressionRule(defer);
					state._fsp--;

					adaptor.addChild(root_0, exp.getTree());


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    retval.p = (exp!=null?((EugeneParser.expressionRule_return)exp).p:null);
					}	
						
					}
					break;

			}
			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "predicate"


	public static class operand_return extends ParserRuleReturnScope {
		public ArrangementOperand o;
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "operand"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1740:1: operand[boolean defer] returns [ArrangementOperand o] : (i= ID |n= NUMBER | '[' n= NUMBER ']' ) ;
	public final EugeneParser.operand_return operand(boolean defer) throws RecognitionException {
		EugeneParser.operand_return retval = new EugeneParser.operand_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token i=null;
		Token n=null;
		Token char_literal144=null;
		Token char_literal145=null;

		Object i_tree=null;
		Object n_tree=null;
		Object char_literal144_tree=null;
		Object char_literal145_tree=null;


		NamedElement element = null;
		int constant = -1;
		int index = -1;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1747:2: ( (i= ID |n= NUMBER | '[' n= NUMBER ']' ) )
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1747:4: (i= ID |n= NUMBER | '[' n= NUMBER ']' )
			{
			root_0 = (Object)adaptor.nil();


			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1747:4: (i= ID |n= NUMBER | '[' n= NUMBER ']' )
			int alt55=3;
			switch ( input.LA(1) ) {
			case ID:
				{
				alt55=1;
				}
				break;
			case NUMBER:
				{
				alt55=2;
				}
				break;
			case LEFTSBR:
				{
				alt55=3;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 55, 0, input);
				throw nvae;
			}
			switch (alt55) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1747:5: i= ID
					{
					i=(Token)match(input,ID,FOLLOW_ID_in_operand3233); 
					i_tree = (Object)adaptor.create(i);
					adaptor.addChild(root_0, i_tree);


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    try {
					        if(!this.interp.contains((i!=null?i.getText():null))) {
					            printError((i!=null?i.getText():null)+" not defined.");
					        }
					        element = this.interp.get((i!=null?i.getText():null));
					    } catch(EugeneException ee) {
					        printError(ee.getMessage());
					    }
					}
						
					}
					break;
				case 2 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1759:4: n= NUMBER
					{
					n=(Token)match(input,NUMBER,FOLLOW_NUMBER_in_operand3242); 
					n_tree = (Object)adaptor.create(n);
					adaptor.addChild(root_0, n_tree);


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    constant = Integer.parseInt((n!=null?n.getText():null));
					}	
						
					}
					break;
				case 3 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1764:4: '[' n= NUMBER ']'
					{
					char_literal144=(Token)match(input,LEFTSBR,FOLLOW_LEFTSBR_in_operand3249); 
					char_literal144_tree = (Object)adaptor.create(char_literal144);
					adaptor.addChild(root_0, char_literal144_tree);

					n=(Token)match(input,NUMBER,FOLLOW_NUMBER_in_operand3253); 
					n_tree = (Object)adaptor.create(n);
					adaptor.addChild(root_0, n_tree);

					char_literal145=(Token)match(input,RIGHTSBR,FOLLOW_RIGHTSBR_in_operand3255); 
					char_literal145_tree = (Object)adaptor.create(char_literal145);
					adaptor.addChild(root_0, char_literal145_tree);


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    index = Integer.parseInt((n!=null?n.getText():null));
					}	
						
					}
					break;

			}


			if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
			    try {
			        retval.o = new ArrangementOperand(element, constant, index);
			    } catch(EugeneException ee) {
			        printError(ee.getMessage());
			    }
			}
				
			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "operand"


	public static class expressionRule_return extends ParserRuleReturnScope {
		public Predicate p;
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "expressionRule"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1783:1: expressionRule[boolean defer] returns [Predicate p] : lhs= expression[defer] op= exp_op[defer] rhs= expression[defer] ;
	public final EugeneParser.expressionRule_return expressionRule(boolean defer) throws RecognitionException {
		EugeneParser.expressionRule_return retval = new EugeneParser.expressionRule_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		ParserRuleReturnScope lhs =null;
		ParserRuleReturnScope op =null;
		ParserRuleReturnScope rhs =null;


		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1785:2: (lhs= expression[defer] op= exp_op[defer] rhs= expression[defer] )
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1785:4: lhs= expression[defer] op= exp_op[defer] rhs= expression[defer]
			{
			root_0 = (Object)adaptor.nil();


			pushFollow(FOLLOW_expression_in_expressionRule3281);
			lhs=expression(defer);
			state._fsp--;

			adaptor.addChild(root_0, lhs.getTree());

			pushFollow(FOLLOW_exp_op_in_expressionRule3286);
			op=exp_op(defer);
			state._fsp--;

			adaptor.addChild(root_0, op.getTree());

			pushFollow(FOLLOW_expression_in_expressionRule3291);
			rhs=expression(defer);
			state._fsp--;

			adaptor.addChild(root_0, rhs.getTree());


			if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
			    try {
			        retval.p = new ExpressionConstraint((lhs!=null?((EugeneParser.expression_return)lhs).exp:null), (op!=null?input.toString(op.start,op.stop):null), (rhs!=null?((EugeneParser.expression_return)rhs).exp:null));
			    } catch(EugeneException ee) {
			        printError(ee.getLocalizedMessage());
			    }
			}
				
			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "expressionRule"


	public static class expression_return extends ParserRuleReturnScope {
		public Expression exp;
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "expression"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1796:1: expression[boolean defer] returns [Expression exp] : (lhs= exp_operand[defer] (expop= exp_operator[defer] rhs= expression[defer] )? | LEFTP expression[defer] RIGHTP );
	public final EugeneParser.expression_return expression(boolean defer) throws RecognitionException {
		EugeneParser.expression_return retval = new EugeneParser.expression_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token LEFTP146=null;
		Token RIGHTP148=null;
		ParserRuleReturnScope lhs =null;
		ParserRuleReturnScope expop =null;
		ParserRuleReturnScope rhs =null;
		ParserRuleReturnScope expression147 =null;

		Object LEFTP146_tree=null;
		Object RIGHTP148_tree=null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1798:2: (lhs= exp_operand[defer] (expop= exp_operator[defer] rhs= expression[defer] )? | LEFTP expression[defer] RIGHTP )
			int alt57=2;
			int LA57_0 = input.LA(1);
			if ( (LA57_0==ID||LA57_0==MINUS||LA57_0==NUMBER||LA57_0==REAL||LA57_0==STRING) ) {
				alt57=1;
			}
			else if ( (LA57_0==LEFTP) ) {
				alt57=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 57, 0, input);
				throw nvae;
			}

			switch (alt57) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1798:4: lhs= exp_operand[defer] (expop= exp_operator[defer] rhs= expression[defer] )?
					{
					root_0 = (Object)adaptor.nil();


					pushFollow(FOLLOW_exp_operand_in_expression3315);
					lhs=exp_operand(defer);
					state._fsp--;

					adaptor.addChild(root_0, lhs.getTree());


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    retval.exp = new Expression((lhs!=null?((EugeneParser.exp_operand_return)lhs).eop:null), null, null);
					}
						
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1802:4: (expop= exp_operator[defer] rhs= expression[defer] )?
					int alt56=2;
					int LA56_0 = input.LA(1);
					if ( (LA56_0==DIV||LA56_0==MINUS||LA56_0==MULT||LA56_0==PLUS) ) {
						alt56=1;
					}
					switch (alt56) {
						case 1 :
							// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1802:6: expop= exp_operator[defer] rhs= expression[defer]
							{
							pushFollow(FOLLOW_exp_operator_in_expression3324);
							expop=exp_operator(defer);
							state._fsp--;

							adaptor.addChild(root_0, expop.getTree());

							pushFollow(FOLLOW_expression_in_expression3329);
							rhs=expression(defer);
							state._fsp--;

							adaptor.addChild(root_0, rhs.getTree());


							if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
							    retval.exp = new Expression((lhs!=null?((EugeneParser.exp_operand_return)lhs).eop:null), (expop!=null?((EugeneParser.exp_operator_return)expop).op:null), (rhs!=null?((EugeneParser.expression_return)rhs).exp:null));
							}	
								
							}
							break;

					}

					}
					break;
				case 2 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1807:4: LEFTP expression[defer] RIGHTP
					{
					root_0 = (Object)adaptor.nil();


					LEFTP146=(Token)match(input,LEFTP,FOLLOW_LEFTP_in_expression3341); 
					LEFTP146_tree = (Object)adaptor.create(LEFTP146);
					adaptor.addChild(root_0, LEFTP146_tree);

					pushFollow(FOLLOW_expression_in_expression3343);
					expression147=expression(defer);
					state._fsp--;

					adaptor.addChild(root_0, expression147.getTree());

					RIGHTP148=(Token)match(input,RIGHTP,FOLLOW_RIGHTP_in_expression3346); 
					RIGHTP148_tree = (Object)adaptor.create(RIGHTP148);
					adaptor.addChild(root_0, RIGHTP148_tree);


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    	
					}	
						
					}
					break;

			}
			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "expression"


	public static class exp_operator_return extends ParserRuleReturnScope {
		public Expression.ExpOp op;
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "exp_operator"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1814:1: exp_operator[boolean defer] returns [Expression.ExpOp op] : ( PLUS | MINUS | MULT | DIV );
	public final EugeneParser.exp_operator_return exp_operator(boolean defer) throws RecognitionException {
		EugeneParser.exp_operator_return retval = new EugeneParser.exp_operator_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token PLUS149=null;
		Token MINUS150=null;
		Token MULT151=null;
		Token DIV152=null;

		Object PLUS149_tree=null;
		Object MINUS150_tree=null;
		Object MULT151_tree=null;
		Object DIV152_tree=null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1816:2: ( PLUS | MINUS | MULT | DIV )
			int alt58=4;
			switch ( input.LA(1) ) {
			case PLUS:
				{
				alt58=1;
				}
				break;
			case MINUS:
				{
				alt58=2;
				}
				break;
			case MULT:
				{
				alt58=3;
				}
				break;
			case DIV:
				{
				alt58=4;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 58, 0, input);
				throw nvae;
			}
			switch (alt58) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1816:4: PLUS
					{
					root_0 = (Object)adaptor.nil();


					PLUS149=(Token)match(input,PLUS,FOLLOW_PLUS_in_exp_operator3365); 
					PLUS149_tree = (Object)adaptor.create(PLUS149);
					adaptor.addChild(root_0, PLUS149_tree);


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {	
					    retval.op = Expression.ExpOp.PLUS;	
					}
						
					}
					break;
				case 2 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1821:4: MINUS
					{
					root_0 = (Object)adaptor.nil();


					MINUS150=(Token)match(input,MINUS,FOLLOW_MINUS_in_exp_operator3373); 
					MINUS150_tree = (Object)adaptor.create(MINUS150);
					adaptor.addChild(root_0, MINUS150_tree);


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {	
					    retval.op = Expression.ExpOp.MINUS;	
					}
						
					}
					break;
				case 3 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1826:4: MULT
					{
					root_0 = (Object)adaptor.nil();


					MULT151=(Token)match(input,MULT,FOLLOW_MULT_in_exp_operator3380); 
					MULT151_tree = (Object)adaptor.create(MULT151);
					adaptor.addChild(root_0, MULT151_tree);


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {	
					    retval.op = Expression.ExpOp.MULT;	
					}
						
					}
					break;
				case 4 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1831:4: DIV
					{
					root_0 = (Object)adaptor.nil();


					DIV152=(Token)match(input,DIV,FOLLOW_DIV_in_exp_operator3387); 
					DIV152_tree = (Object)adaptor.create(DIV152);
					adaptor.addChild(root_0, DIV152_tree);


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {	
					    retval.op = Expression.ExpOp.DIV;	
					}
						
					}
					break;

			}
			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "exp_operator"


	public static class exp_operand_return extends ParserRuleReturnScope {
		public ExpressionOperand eop;
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "exp_operand"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1838:1: exp_operand[boolean defer] returns [ExpressionOperand eop] : ( (i1= ID DOT )* i2= ID ( LEFTSBR n= NUMBER RIGHTSBR )* |n= NUMBER | MINUS n= NUMBER |r= REAL | MINUS r= REAL |s= STRING );
	public final EugeneParser.exp_operand_return exp_operand(boolean defer) throws RecognitionException {
		EugeneParser.exp_operand_return retval = new EugeneParser.exp_operand_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token i1=null;
		Token i2=null;
		Token n=null;
		Token r=null;
		Token s=null;
		Token DOT153=null;
		Token LEFTSBR154=null;
		Token RIGHTSBR155=null;
		Token MINUS156=null;
		Token MINUS157=null;

		Object i1_tree=null;
		Object i2_tree=null;
		Object n_tree=null;
		Object r_tree=null;
		Object s_tree=null;
		Object DOT153_tree=null;
		Object LEFTSBR154_tree=null;
		Object RIGHTSBR155_tree=null;
		Object MINUS156_tree=null;
		Object MINUS157_tree=null;


		List<NamedElement> elements = null;
		NamedElement ne = null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1844:2: ( (i1= ID DOT )* i2= ID ( LEFTSBR n= NUMBER RIGHTSBR )* |n= NUMBER | MINUS n= NUMBER |r= REAL | MINUS r= REAL |s= STRING )
			int alt61=6;
			switch ( input.LA(1) ) {
			case ID:
				{
				alt61=1;
				}
				break;
			case NUMBER:
				{
				alt61=2;
				}
				break;
			case MINUS:
				{
				int LA61_3 = input.LA(2);
				if ( (LA61_3==NUMBER) ) {
					alt61=3;
				}
				else if ( (LA61_3==REAL) ) {
					alt61=5;
				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 61, 3, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

				}
				break;
			case REAL:
				{
				alt61=4;
				}
				break;
			case STRING:
				{
				alt61=6;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 61, 0, input);
				throw nvae;
			}
			switch (alt61) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1844:4: (i1= ID DOT )* i2= ID ( LEFTSBR n= NUMBER RIGHTSBR )*
					{
					root_0 = (Object)adaptor.nil();


					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1844:4: (i1= ID DOT )*
					loop59:
					while (true) {
						int alt59=2;
						int LA59_0 = input.LA(1);
						if ( (LA59_0==ID) ) {
							int LA59_1 = input.LA(2);
							if ( (LA59_1==DOT) ) {
								alt59=1;
							}

						}

						switch (alt59) {
						case 1 :
							// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1844:5: i1= ID DOT
							{
							i1=(Token)match(input,ID,FOLLOW_ID_in_exp_operand3417); 
							i1_tree = (Object)adaptor.create(i1);
							adaptor.addChild(root_0, i1_tree);

							DOT153=(Token)match(input,DOT,FOLLOW_DOT_in_exp_operand3419); 
							DOT153_tree = (Object)adaptor.create(DOT153);
							adaptor.addChild(root_0, DOT153_tree);


							if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
							    try {
							        if(ne == null) {
							            ne = this.interp.get((i1!=null?i1.getText():null));
							            if(null == ne) {
							                printError((i1!=null?i1.getText():null)+" is not defined.");
							            }
							        } else {
							            if(!(ne instanceof Device)) {
							                printError((i1!=null?i1.getText():null)+" is not a Device.");
							            }
							        
							            String name = ne.getName();
							            try {
							                ne = ((Device)ne).getComponent((i1!=null?i1.getText():null));
							            } catch(EugeneException ee) {
							                printError(ee.getMessage());
							            }
							            if(null == ne) {
							                printError(name+" does not contain "+(i1!=null?i1.getText():null)+".");
							            }
							        }
							    } catch(EugeneException ee) {
							        printError(ee.getMessage());
							    }
							    
							    if(elements == null) {
							        elements = new ArrayList<NamedElement>();
							    }

							    elements.add(ne);
							}	
								
							}
							break;

						default :
							break loop59;
						}
					}

					i2=(Token)match(input,ID,FOLLOW_ID_in_exp_operand3428); 
					i2_tree = (Object)adaptor.create(i2);
					adaptor.addChild(root_0, i2_tree);


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    try {
					        NamedElement property = null;

					        if(ne == null) {
					            property = this.interp.get((i2!=null?i2.getText():null));
					            if(null == property) {
					                printError((i2!=null?i2.getText():null)+" is not defined.");
					            }
					        } else {
					            if(!(ne instanceof Component) && !(ne instanceof ComponentType)) {
					                printError((i2!=null?i2.getText():null)+" is not a Device, Part, nor Part Type.");
					            }
					        
					            if(ne instanceof ComponentType) {
					                property = ((ComponentType)ne).getProperty((i2!=null?i2.getText():null));
					            } else if(ne instanceof Component) {
					                property = ((Component)ne).getProperty((i2!=null?i2.getText():null));
					            }
					            if(null == property) {
					                printError(ne.getName()+" does not contain "+(i2!=null?i2.getText():null)+".");
					            }
					        }

					        if(!(property instanceof Property)) {
					            printError((i2!=null?i2.getText():null)+" is not a Property.");
					        }     
					    
					        retval.eop = new ExpressionOperand(elements, (Property)property);

					    } catch(EugeneException ee) {
					        printError(ee.getMessage());
					    }
					}	
						
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1912:5: ( LEFTSBR n= NUMBER RIGHTSBR )*
					loop60:
					while (true) {
						int alt60=2;
						int LA60_0 = input.LA(1);
						if ( (LA60_0==LEFTSBR) ) {
							alt60=1;
						}

						switch (alt60) {
						case 1 :
							// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1912:6: LEFTSBR n= NUMBER RIGHTSBR
							{
							LEFTSBR154=(Token)match(input,LEFTSBR,FOLLOW_LEFTSBR_in_exp_operand3434); 
							LEFTSBR154_tree = (Object)adaptor.create(LEFTSBR154);
							adaptor.addChild(root_0, LEFTSBR154_tree);

							n=(Token)match(input,NUMBER,FOLLOW_NUMBER_in_exp_operand3438); 
							n_tree = (Object)adaptor.create(n);
							adaptor.addChild(root_0, n_tree);

							RIGHTSBR155=(Token)match(input,RIGHTSBR,FOLLOW_RIGHTSBR_in_exp_operand3440); 
							RIGHTSBR155_tree = (Object)adaptor.create(RIGHTSBR155);
							adaptor.addChild(root_0, RIGHTSBR155_tree);


							if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
							    if(null != retval.eop) {
							        try {
							            retval.eop.addIndex((n!=null?n.getText():null));
							        } catch(EugeneException ee) {
							            printError(ee.getMessage());
							        }
							    }
							}	
								
							}
							break;

						default :
							break loop60;
						}
					}

					}
					break;
				case 2 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1923:4: n= NUMBER
					{
					root_0 = (Object)adaptor.nil();


					n=(Token)match(input,NUMBER,FOLLOW_NUMBER_in_exp_operand3452); 
					n_tree = (Object)adaptor.create(n);
					adaptor.addChild(root_0, n_tree);


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    Variable v = new Variable(null, EugeneConstants.NUM);
					    v.num = Double.parseDouble((n!=null?n.getText():null));
					    retval.eop = new ExpressionOperand(v);
					}	
						
					}
					break;
				case 3 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1930:4: MINUS n= NUMBER
					{
					root_0 = (Object)adaptor.nil();


					MINUS156=(Token)match(input,MINUS,FOLLOW_MINUS_in_exp_operand3459); 
					MINUS156_tree = (Object)adaptor.create(MINUS156);
					adaptor.addChild(root_0, MINUS156_tree);

					n=(Token)match(input,NUMBER,FOLLOW_NUMBER_in_exp_operand3463); 
					n_tree = (Object)adaptor.create(n);
					adaptor.addChild(root_0, n_tree);


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    Variable v = new Variable(null, EugeneConstants.NUM);
					    v.num = Double.parseDouble((n!=null?n.getText():null)) * -1;
					    retval.eop = new ExpressionOperand(v);
					}	
						
					}
					break;
				case 4 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1937:4: r= REAL
					{
					root_0 = (Object)adaptor.nil();


					r=(Token)match(input,REAL,FOLLOW_REAL_in_exp_operand3472); 
					r_tree = (Object)adaptor.create(r);
					adaptor.addChild(root_0, r_tree);


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    Variable v = new Variable(null, EugeneConstants.NUM);
					    v.num = Double.parseDouble((r!=null?r.getText():null));
					    retval.eop = new ExpressionOperand(v);
					}	
						
					}
					break;
				case 5 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1944:4: MINUS r= REAL
					{
					root_0 = (Object)adaptor.nil();


					MINUS157=(Token)match(input,MINUS,FOLLOW_MINUS_in_exp_operand3479); 
					MINUS157_tree = (Object)adaptor.create(MINUS157);
					adaptor.addChild(root_0, MINUS157_tree);

					r=(Token)match(input,REAL,FOLLOW_REAL_in_exp_operand3483); 
					r_tree = (Object)adaptor.create(r);
					adaptor.addChild(root_0, r_tree);


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    Variable v = new Variable(null, EugeneConstants.NUM);
					    v.num = Double.parseDouble((r!=null?r.getText():null)) * -1.0;
					    retval.eop = new ExpressionOperand(v);
					}	
						
					}
					break;
				case 6 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1951:4: s= STRING
					{
					root_0 = (Object)adaptor.nil();


					s=(Token)match(input,STRING,FOLLOW_STRING_in_exp_operand3492); 
					s_tree = (Object)adaptor.create(s);
					adaptor.addChild(root_0, s_tree);


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    Variable v = new Variable(null, EugeneConstants.TXT);
					    v.txt = (s!=null?s.getText():null);
					    retval.eop = new ExpressionOperand(v);
					}		
						
					}
					break;

			}
			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "exp_operand"


	public static class regexp_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "regexp"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1961:1: regexp[boolean defer] :;
	public final EugeneParser.regexp_return regexp(boolean defer) throws RecognitionException {
		EugeneParser.regexp_return retval = new EugeneParser.regexp_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1962:2: ()
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1963:2: 
			{
			root_0 = (Object)adaptor.nil();


			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "regexp"


	public static class exp_op_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "exp_op"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1965:1: exp_op[boolean defer] : relationalOperators ;
	public final EugeneParser.exp_op_return exp_op(boolean defer) throws RecognitionException {
		EugeneParser.exp_op_return retval = new EugeneParser.exp_op_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		ParserRuleReturnScope relationalOperators158 =null;


		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1966:2: ( relationalOperators )
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1966:4: relationalOperators
			{
			root_0 = (Object)adaptor.nil();


			pushFollow(FOLLOW_relationalOperators_in_exp_op3519);
			relationalOperators158=relationalOperators();
			state._fsp--;

			adaptor.addChild(root_0, relationalOperators158.getTree());

			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "exp_op"


	public static class grammarDeclaration_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "grammarDeclaration"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1974:1: grammarDeclaration[boolean defer] : GRAMMAR n= ID LEFTP list_of_production_rules[defer] RIGHTP ;
	public final EugeneParser.grammarDeclaration_return grammarDeclaration(boolean defer) throws RecognitionException {
		EugeneParser.grammarDeclaration_return retval = new EugeneParser.grammarDeclaration_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token n=null;
		Token GRAMMAR159=null;
		Token LEFTP160=null;
		Token RIGHTP162=null;
		ParserRuleReturnScope list_of_production_rules161 =null;

		Object n_tree=null;
		Object GRAMMAR159_tree=null;
		Object LEFTP160_tree=null;
		Object RIGHTP162_tree=null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1975:2: ( GRAMMAR n= ID LEFTP list_of_production_rules[defer] RIGHTP )
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1976:3: GRAMMAR n= ID LEFTP list_of_production_rules[defer] RIGHTP
			{
			root_0 = (Object)adaptor.nil();


			GRAMMAR159=(Token)match(input,GRAMMAR,FOLLOW_GRAMMAR_in_grammarDeclaration3538); 
			GRAMMAR159_tree = (Object)adaptor.create(GRAMMAR159);
			adaptor.addChild(root_0, GRAMMAR159_tree);

			n=(Token)match(input,ID,FOLLOW_ID_in_grammarDeclaration3542); 
			n_tree = (Object)adaptor.create(n);
			adaptor.addChild(root_0, n_tree);

			LEFTP160=(Token)match(input,LEFTP,FOLLOW_LEFTP_in_grammarDeclaration3544); 
			LEFTP160_tree = (Object)adaptor.create(LEFTP160);
			adaptor.addChild(root_0, LEFTP160_tree);

			pushFollow(FOLLOW_list_of_production_rules_in_grammarDeclaration3546);
			list_of_production_rules161=list_of_production_rules(defer);
			state._fsp--;

			adaptor.addChild(root_0, list_of_production_rules161.getTree());

			RIGHTP162=(Token)match(input,RIGHTP,FOLLOW_RIGHTP_in_grammarDeclaration3549); 
			RIGHTP162_tree = (Object)adaptor.create(RIGHTP162);
			adaptor.addChild(root_0, RIGHTP162_tree);

			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "grammarDeclaration"


	public static class list_of_production_rules_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "list_of_production_rules"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1979:1: list_of_production_rules[boolean defer] : production_rule[defer] SEMIC ( list_of_production_rules[defer] )? ;
	public final EugeneParser.list_of_production_rules_return list_of_production_rules(boolean defer) throws RecognitionException {
		EugeneParser.list_of_production_rules_return retval = new EugeneParser.list_of_production_rules_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token SEMIC164=null;
		ParserRuleReturnScope production_rule163 =null;
		ParserRuleReturnScope list_of_production_rules165 =null;

		Object SEMIC164_tree=null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1980:2: ( production_rule[defer] SEMIC ( list_of_production_rules[defer] )? )
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1980:4: production_rule[defer] SEMIC ( list_of_production_rules[defer] )?
			{
			root_0 = (Object)adaptor.nil();


			pushFollow(FOLLOW_production_rule_in_list_of_production_rules3561);
			production_rule163=production_rule(defer);
			state._fsp--;

			adaptor.addChild(root_0, production_rule163.getTree());

			SEMIC164=(Token)match(input,SEMIC,FOLLOW_SEMIC_in_list_of_production_rules3564); 
			SEMIC164_tree = (Object)adaptor.create(SEMIC164);
			adaptor.addChild(root_0, SEMIC164_tree);

			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1980:33: ( list_of_production_rules[defer] )?
			int alt62=2;
			int LA62_0 = input.LA(1);
			if ( (LA62_0==ID) ) {
				alt62=1;
			}
			switch (alt62) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1980:34: list_of_production_rules[defer]
					{
					pushFollow(FOLLOW_list_of_production_rules_in_list_of_production_rules3567);
					list_of_production_rules165=list_of_production_rules(defer);
					state._fsp--;

					adaptor.addChild(root_0, list_of_production_rules165.getTree());

					}
					break;

			}

			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "list_of_production_rules"


	public static class production_rule_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "production_rule"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1983:1: production_rule[boolean defer] : lhs= ID ARROW right_hand_side[defer] ;
	public final EugeneParser.production_rule_return production_rule(boolean defer) throws RecognitionException {
		EugeneParser.production_rule_return retval = new EugeneParser.production_rule_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token lhs=null;
		Token ARROW166=null;
		ParserRuleReturnScope right_hand_side167 =null;

		Object lhs_tree=null;
		Object ARROW166_tree=null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1984:2: (lhs= ID ARROW right_hand_side[defer] )
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1984:4: lhs= ID ARROW right_hand_side[defer]
			{
			root_0 = (Object)adaptor.nil();


			lhs=(Token)match(input,ID,FOLLOW_ID_in_production_rule3587); 
			lhs_tree = (Object)adaptor.create(lhs);
			adaptor.addChild(root_0, lhs_tree);


			if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
			    // ID denotes a non-terminal of the grammar
			}	
				
			ARROW166=(Token)match(input,ARROW,FOLLOW_ARROW_in_production_rule3591); 
			ARROW166_tree = (Object)adaptor.create(ARROW166);
			adaptor.addChild(root_0, ARROW166_tree);

			pushFollow(FOLLOW_right_hand_side_in_production_rule3593);
			right_hand_side167=right_hand_side(defer);
			state._fsp--;

			adaptor.addChild(root_0, right_hand_side167.getTree());

			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "production_rule"


	public static class right_hand_side_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "right_hand_side"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1991:1: right_hand_side[boolean defer] : (i= ID ( COMMA right_hand_side[defer] )? | interaction[defer, \"some_string\"] );
	public final EugeneParser.right_hand_side_return right_hand_side(boolean defer) throws RecognitionException {
		EugeneParser.right_hand_side_return retval = new EugeneParser.right_hand_side_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token i=null;
		Token COMMA168=null;
		ParserRuleReturnScope right_hand_side169 =null;
		ParserRuleReturnScope interaction170 =null;

		Object i_tree=null;
		Object COMMA168_tree=null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1992:2: (i= ID ( COMMA right_hand_side[defer] )? | interaction[defer, \"some_string\"] )
			int alt64=2;
			int LA64_0 = input.LA(1);
			if ( (LA64_0==ID) ) {
				int LA64_1 = input.LA(2);
				if ( (LA64_1==COMMA||LA64_1==SEMIC) ) {
					alt64=1;
				}
				else if ( (LA64_1==LC_INDUCES||LA64_1==LC_REPRESSES||LA64_1==UC_INDUCES||LA64_1==UC_REPRESSES) ) {
					alt64=2;
				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 64, 1, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 64, 0, input);
				throw nvae;
			}

			switch (alt64) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1992:4: i= ID ( COMMA right_hand_side[defer] )?
					{
					root_0 = (Object)adaptor.nil();


					i=(Token)match(input,ID,FOLLOW_ID_in_right_hand_side3609); 
					i_tree = (Object)adaptor.create(i);
					adaptor.addChild(root_0, i_tree);


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    // ID must be either a terminal (i.e. a part)
					    // or a non-terminal defined within the grammar
					}	
						
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1997:4: ( COMMA right_hand_side[defer] )?
					int alt63=2;
					int LA63_0 = input.LA(1);
					if ( (LA63_0==COMMA) ) {
						alt63=1;
					}
					switch (alt63) {
						case 1 :
							// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1997:5: COMMA right_hand_side[defer]
							{
							COMMA168=(Token)match(input,COMMA,FOLLOW_COMMA_in_right_hand_side3614); 
							COMMA168_tree = (Object)adaptor.create(COMMA168);
							adaptor.addChild(root_0, COMMA168_tree);

							pushFollow(FOLLOW_right_hand_side_in_right_hand_side3616);
							right_hand_side169=right_hand_side(defer);
							state._fsp--;

							adaptor.addChild(root_0, right_hand_side169.getTree());

							}
							break;

					}

					}
					break;
				case 2 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:1998:4: interaction[defer, \"some_string\"]
					{
					root_0 = (Object)adaptor.nil();


					pushFollow(FOLLOW_interaction_in_right_hand_side3624);
					interaction170=interaction(defer, "some_string");
					state._fsp--;

					adaptor.addChild(root_0, interaction170.getTree());

					}
					break;

			}
			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "right_hand_side"


	public static class interactionDeclaration_return extends ParserRuleReturnScope {
		public Interaction ia;
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "interactionDeclaration"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2005:1: interactionDeclaration[boolean defer] returns [Interaction ia] : (i1= interaction[defer, null] | INTERACTION name= ID LEFTP i2= interaction[defer, $name.text] RIGHTP );
	public final EugeneParser.interactionDeclaration_return interactionDeclaration(boolean defer) throws RecognitionException {
		EugeneParser.interactionDeclaration_return retval = new EugeneParser.interactionDeclaration_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token name=null;
		Token INTERACTION171=null;
		Token LEFTP172=null;
		Token RIGHTP173=null;
		ParserRuleReturnScope i1 =null;
		ParserRuleReturnScope i2 =null;

		Object name_tree=null;
		Object INTERACTION171_tree=null;
		Object LEFTP172_tree=null;
		Object RIGHTP173_tree=null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2007:2: (i1= interaction[defer, null] | INTERACTION name= ID LEFTP i2= interaction[defer, $name.text] RIGHTP )
			int alt65=2;
			int LA65_0 = input.LA(1);
			if ( (LA65_0==ID) ) {
				alt65=1;
			}
			else if ( (LA65_0==INTERACTION) ) {
				alt65=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 65, 0, input);
				throw nvae;
			}

			switch (alt65) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2007:4: i1= interaction[defer, null]
					{
					root_0 = (Object)adaptor.nil();


					pushFollow(FOLLOW_interaction_in_interactionDeclaration3649);
					i1=interaction(defer, null);
					state._fsp--;

					adaptor.addChild(root_0, i1.getTree());


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    retval.ia = (i1!=null?((EugeneParser.interaction_return)i1).ia:null);
					}

					}
					break;
				case 2 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2012:4: INTERACTION name= ID LEFTP i2= interaction[defer, $name.text] RIGHTP
					{
					root_0 = (Object)adaptor.nil();


					INTERACTION171=(Token)match(input,INTERACTION,FOLLOW_INTERACTION_in_interactionDeclaration3657); 
					INTERACTION171_tree = (Object)adaptor.create(INTERACTION171);
					adaptor.addChild(root_0, INTERACTION171_tree);

					name=(Token)match(input,ID,FOLLOW_ID_in_interactionDeclaration3661); 
					name_tree = (Object)adaptor.create(name);
					adaptor.addChild(root_0, name_tree);

					LEFTP172=(Token)match(input,LEFTP,FOLLOW_LEFTP_in_interactionDeclaration3663); 
					LEFTP172_tree = (Object)adaptor.create(LEFTP172);
					adaptor.addChild(root_0, LEFTP172_tree);

					pushFollow(FOLLOW_interaction_in_interactionDeclaration3667);
					i2=interaction(defer, (name!=null?name.getText():null));
					state._fsp--;

					adaptor.addChild(root_0, i2.getTree());

					RIGHTP173=(Token)match(input,RIGHTP,FOLLOW_RIGHTP_in_interactionDeclaration3670); 
					RIGHTP173_tree = (Object)adaptor.create(RIGHTP173);
					adaptor.addChild(root_0, RIGHTP173_tree);


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    retval.ia = (i2!=null?((EugeneParser.interaction_return)i2).ia:null);
					}

					}
					break;

			}
			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "interactionDeclaration"


	public static class interaction_return extends ParserRuleReturnScope {
		public Interaction ia;
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "interaction"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2019:1: interaction[boolean defer, String name] returns [Interaction ia] : (lhs1= ID t1= interactionType[defer] rhs1= ID |lhs2= ID t2= interactionType[defer] LEFTP rhs2= interaction[defer, name] RIGHTP );
	public final EugeneParser.interaction_return interaction(boolean defer, String name) throws RecognitionException {
		EugeneParser.interaction_return retval = new EugeneParser.interaction_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token lhs1=null;
		Token rhs1=null;
		Token lhs2=null;
		Token LEFTP174=null;
		Token RIGHTP175=null;
		ParserRuleReturnScope t1 =null;
		ParserRuleReturnScope t2 =null;
		ParserRuleReturnScope rhs2 =null;

		Object lhs1_tree=null;
		Object rhs1_tree=null;
		Object lhs2_tree=null;
		Object LEFTP174_tree=null;
		Object RIGHTP175_tree=null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2021:2: (lhs1= ID t1= interactionType[defer] rhs1= ID |lhs2= ID t2= interactionType[defer] LEFTP rhs2= interaction[defer, name] RIGHTP )
			int alt66=2;
			int LA66_0 = input.LA(1);
			if ( (LA66_0==ID) ) {
				int LA66_1 = input.LA(2);
				if ( (LA66_1==LC_REPRESSES||LA66_1==UC_REPRESSES) ) {
					int LA66_2 = input.LA(3);
					if ( (LA66_2==ID) ) {
						alt66=1;
					}
					else if ( (LA66_2==LEFTP) ) {
						alt66=2;
					}

					else {
						int nvaeMark = input.mark();
						try {
							for (int nvaeConsume = 0; nvaeConsume < 3 - 1; nvaeConsume++) {
								input.consume();
							}
							NoViableAltException nvae =
								new NoViableAltException("", 66, 2, input);
							throw nvae;
						} finally {
							input.rewind(nvaeMark);
						}
					}

				}
				else if ( (LA66_1==LC_INDUCES||LA66_1==UC_INDUCES) ) {
					int LA66_3 = input.LA(3);
					if ( (LA66_3==ID) ) {
						alt66=1;
					}
					else if ( (LA66_3==LEFTP) ) {
						alt66=2;
					}

					else {
						int nvaeMark = input.mark();
						try {
							for (int nvaeConsume = 0; nvaeConsume < 3 - 1; nvaeConsume++) {
								input.consume();
							}
							NoViableAltException nvae =
								new NoViableAltException("", 66, 3, input);
							throw nvae;
						} finally {
							input.rewind(nvaeMark);
						}
					}

				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 66, 1, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 66, 0, input);
				throw nvae;
			}

			switch (alt66) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2021:4: lhs1= ID t1= interactionType[defer] rhs1= ID
					{
					root_0 = (Object)adaptor.nil();


					lhs1=(Token)match(input,ID,FOLLOW_ID_in_interaction3693); 
					lhs1_tree = (Object)adaptor.create(lhs1);
					adaptor.addChild(root_0, lhs1_tree);

					pushFollow(FOLLOW_interactionType_in_interaction3697);
					t1=interactionType(defer);
					state._fsp--;

					adaptor.addChild(root_0, t1.getTree());

					rhs1=(Token)match(input,ID,FOLLOW_ID_in_interaction3702); 
					rhs1_tree = (Object)adaptor.create(rhs1);
					adaptor.addChild(root_0, rhs1_tree);


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    try {
					        retval.ia = this.interp.createInteraction(name, (lhs1!=null?lhs1.getText():null), (t1!=null?((EugeneParser.interactionType_return)t1).type:null), (rhs1!=null?rhs1.getText():null));
					    } catch(EugeneException ee) {
					        printError(ee.getLocalizedMessage());
					    }
					}	
						
					}
					break;
				case 2 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2030:4: lhs2= ID t2= interactionType[defer] LEFTP rhs2= interaction[defer, name] RIGHTP
					{
					root_0 = (Object)adaptor.nil();


					lhs2=(Token)match(input,ID,FOLLOW_ID_in_interaction3711); 
					lhs2_tree = (Object)adaptor.create(lhs2);
					adaptor.addChild(root_0, lhs2_tree);

					pushFollow(FOLLOW_interactionType_in_interaction3715);
					t2=interactionType(defer);
					state._fsp--;

					adaptor.addChild(root_0, t2.getTree());

					LEFTP174=(Token)match(input,LEFTP,FOLLOW_LEFTP_in_interaction3718); 
					LEFTP174_tree = (Object)adaptor.create(LEFTP174);
					adaptor.addChild(root_0, LEFTP174_tree);

					pushFollow(FOLLOW_interaction_in_interaction3722);
					rhs2=interaction(defer, name);
					state._fsp--;

					adaptor.addChild(root_0, rhs2.getTree());

					RIGHTP175=(Token)match(input,RIGHTP,FOLLOW_RIGHTP_in_interaction3725); 
					RIGHTP175_tree = (Object)adaptor.create(RIGHTP175);
					adaptor.addChild(root_0, RIGHTP175_tree);


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    try {
					        retval.ia = this.interp.createInteraction(name, (lhs2!=null?lhs2.getText():null), (t2!=null?((EugeneParser.interactionType_return)t2).type:null), (rhs2!=null?((EugeneParser.interaction_return)rhs2).ia:null));
					    } catch(EugeneException ee) {
					        printError(ee.getLocalizedMessage());
					    }
					}	
						
					}
					break;

			}
			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "interaction"


	public static class interactionType_return extends ParserRuleReturnScope {
		public Interaction.InteractionType type;
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "interactionType"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2041:1: interactionType[boolean defer] returns [Interaction.InteractionType type] : ( ( UC_REPRESSES | LC_REPRESSES ) | ( UC_INDUCES | LC_INDUCES ) );
	public final EugeneParser.interactionType_return interactionType(boolean defer) throws RecognitionException {
		EugeneParser.interactionType_return retval = new EugeneParser.interactionType_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token set176=null;
		Token set177=null;

		Object set176_tree=null;
		Object set177_tree=null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2043:2: ( ( UC_REPRESSES | LC_REPRESSES ) | ( UC_INDUCES | LC_INDUCES ) )
			int alt67=2;
			int LA67_0 = input.LA(1);
			if ( (LA67_0==LC_REPRESSES||LA67_0==UC_REPRESSES) ) {
				alt67=1;
			}
			else if ( (LA67_0==LC_INDUCES||LA67_0==UC_INDUCES) ) {
				alt67=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 67, 0, input);
				throw nvae;
			}

			switch (alt67) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2043:4: ( UC_REPRESSES | LC_REPRESSES )
					{
					root_0 = (Object)adaptor.nil();


					set176=input.LT(1);
					if ( input.LA(1)==LC_REPRESSES||input.LA(1)==UC_REPRESSES ) {
						input.consume();
						adaptor.addChild(root_0, (Object)adaptor.create(set176));
						state.errorRecovery=false;
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						throw mse;
					}

					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    retval.type = Interaction.InteractionType.REPRESSES;
					}	
						
					}
					break;
				case 2 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2048:4: ( UC_INDUCES | LC_INDUCES )
					{
					root_0 = (Object)adaptor.nil();


					set177=input.LT(1);
					if ( input.LA(1)==LC_INDUCES||input.LA(1)==UC_INDUCES ) {
						input.consume();
						adaptor.addChild(root_0, (Object)adaptor.create(set177));
						state.errorRecovery=false;
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						throw mse;
					}

					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    retval.type = Interaction.InteractionType.INDUCES;
					}	
						
					}
					break;

			}
			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "interactionType"


	public static class printStatement_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "printStatement"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2060:1: printStatement[boolean defer] : ( ( PRINTLN_LC | PRINTLN_UC ) LEFTP tp= toPrint[defer] RIGHTP | ( PRINT_LC | PRINT_UC ) LEFTP tp= toPrint[defer] RIGHTP );
	public final EugeneParser.printStatement_return printStatement(boolean defer) throws RecognitionException {
		EugeneParser.printStatement_return retval = new EugeneParser.printStatement_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token set178=null;
		Token LEFTP179=null;
		Token RIGHTP180=null;
		Token set181=null;
		Token LEFTP182=null;
		Token RIGHTP183=null;
		ParserRuleReturnScope tp =null;

		Object set178_tree=null;
		Object LEFTP179_tree=null;
		Object RIGHTP180_tree=null;
		Object set181_tree=null;
		Object LEFTP182_tree=null;
		Object RIGHTP183_tree=null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2061:2: ( ( PRINTLN_LC | PRINTLN_UC ) LEFTP tp= toPrint[defer] RIGHTP | ( PRINT_LC | PRINT_UC ) LEFTP tp= toPrint[defer] RIGHTP )
			int alt68=2;
			int LA68_0 = input.LA(1);
			if ( ((LA68_0 >= PRINTLN_LC && LA68_0 <= PRINTLN_UC)) ) {
				alt68=1;
			}
			else if ( ((LA68_0 >= PRINT_LC && LA68_0 <= PRINT_UC)) ) {
				alt68=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 68, 0, input);
				throw nvae;
			}

			switch (alt68) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2061:4: ( PRINTLN_LC | PRINTLN_UC ) LEFTP tp= toPrint[defer] RIGHTP
					{
					root_0 = (Object)adaptor.nil();


					set178=input.LT(1);
					if ( (input.LA(1) >= PRINTLN_LC && input.LA(1) <= PRINTLN_UC) ) {
						input.consume();
						adaptor.addChild(root_0, (Object)adaptor.create(set178));
						state.errorRecovery=false;
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						throw mse;
					}
					LEFTP179=(Token)match(input,LEFTP,FOLLOW_LEFTP_in_printStatement3790); 
					LEFTP179_tree = (Object)adaptor.create(LEFTP179);
					adaptor.addChild(root_0, LEFTP179_tree);

					pushFollow(FOLLOW_toPrint_in_printStatement3794);
					tp=toPrint(defer);
					state._fsp--;

					adaptor.addChild(root_0, tp.getTree());

					RIGHTP180=(Token)match(input,RIGHTP,FOLLOW_RIGHTP_in_printStatement3797); 
					RIGHTP180_tree = (Object)adaptor.create(RIGHTP180);
					adaptor.addChild(root_0, RIGHTP180_tree);


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    if(null != (tp!=null?((EugeneParser.toPrint_return)tp).sb:null)) {
					        try {
					            this.writer.write((tp!=null?((EugeneParser.toPrint_return)tp).sb:null).toString());
					            this.writer.newLine();
					            this.writer.flush();
					        } catch(IOException ioe) {
					            printError(ioe.getMessage());
					        }
					    }
					}	
						
					}
					break;
				case 2 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2074:4: ( PRINT_LC | PRINT_UC ) LEFTP tp= toPrint[defer] RIGHTP
					{
					root_0 = (Object)adaptor.nil();


					set181=input.LT(1);
					if ( (input.LA(1) >= PRINT_LC && input.LA(1) <= PRINT_UC) ) {
						input.consume();
						adaptor.addChild(root_0, (Object)adaptor.create(set181));
						state.errorRecovery=false;
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						throw mse;
					}
					LEFTP182=(Token)match(input,LEFTP,FOLLOW_LEFTP_in_printStatement3810); 
					LEFTP182_tree = (Object)adaptor.create(LEFTP182);
					adaptor.addChild(root_0, LEFTP182_tree);

					pushFollow(FOLLOW_toPrint_in_printStatement3814);
					tp=toPrint(defer);
					state._fsp--;

					adaptor.addChild(root_0, tp.getTree());

					RIGHTP183=(Token)match(input,RIGHTP,FOLLOW_RIGHTP_in_printStatement3817); 
					RIGHTP183_tree = (Object)adaptor.create(RIGHTP183);
					adaptor.addChild(root_0, RIGHTP183_tree);


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    if(null != (tp!=null?((EugeneParser.toPrint_return)tp).sb:null)) {
					        try {
					            this.writer.write((tp!=null?((EugeneParser.toPrint_return)tp).sb:null).toString());
					            this.writer.flush();
					        } catch(IOException ioe) {
					            printError(ioe.getMessage());
					        }
					    }
					}	
						
					}
					break;

			}
			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "printStatement"


	public static class toPrint_return extends ParserRuleReturnScope {
		public StringBuilder sb;
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "toPrint"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2088:1: toPrint[boolean defer] returns [StringBuilder sb] : exp= expr[defer] tpp= toPrint_prime[defer] ;
	public final EugeneParser.toPrint_return toPrint(boolean defer) throws RecognitionException {
		EugeneParser.toPrint_return retval = new EugeneParser.toPrint_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		ParserRuleReturnScope exp =null;
		ParserRuleReturnScope tpp =null;


		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2090:2: (exp= expr[defer] tpp= toPrint_prime[defer] )
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2090:4: exp= expr[defer] tpp= toPrint_prime[defer]
			{
			root_0 = (Object)adaptor.nil();


			pushFollow(FOLLOW_expr_in_toPrint3838);
			exp=expr(defer);
			state._fsp--;

			adaptor.addChild(root_0, exp.getTree());

			pushFollow(FOLLOW_toPrint_prime_in_toPrint3843);
			tpp=toPrint_prime(defer);
			state._fsp--;

			adaptor.addChild(root_0, tpp.getTree());


			if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
			    retval.sb = new StringBuilder();
			    if(null != (exp!=null?((EugeneParser.expr_return)exp).element:null)) {
			        retval.sb.append((exp!=null?((EugeneParser.expr_return)exp).element:null));
			    } else {
			        retval.sb.append((exp!=null?((EugeneParser.expr_return)exp).p:null));
			    }
			    retval.sb.append((tpp!=null?((EugeneParser.toPrint_prime_return)tpp).sb:null));
			}	
				
			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "toPrint"


	public static class toPrint_prime_return extends ParserRuleReturnScope {
		public StringBuilder sb;
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "toPrint_prime"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2103:1: toPrint_prime[boolean defer] returns [StringBuilder sb] : (| COMMA tp= toPrint[defer] );
	public final EugeneParser.toPrint_prime_return toPrint_prime(boolean defer) throws RecognitionException {
		EugeneParser.toPrint_prime_return retval = new EugeneParser.toPrint_prime_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token COMMA184=null;
		ParserRuleReturnScope tp =null;

		Object COMMA184_tree=null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2105:2: (| COMMA tp= toPrint[defer] )
			int alt69=2;
			int LA69_0 = input.LA(1);
			if ( (LA69_0==RIGHTP) ) {
				alt69=1;
			}
			else if ( (LA69_0==COMMA) ) {
				alt69=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 69, 0, input);
				throw nvae;
			}

			switch (alt69) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2105:4: 
					{
					root_0 = (Object)adaptor.nil();



					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    retval.sb = new StringBuilder();
					}
						
					}
					break;
				case 2 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2110:4: COMMA tp= toPrint[defer]
					{
					root_0 = (Object)adaptor.nil();


					COMMA184=(Token)match(input,COMMA,FOLLOW_COMMA_in_toPrint_prime3869); 
					COMMA184_tree = (Object)adaptor.create(COMMA184);
					adaptor.addChild(root_0, COMMA184_tree);

					pushFollow(FOLLOW_toPrint_in_toPrint_prime3873);
					tp=toPrint(defer);
					state._fsp--;

					adaptor.addChild(root_0, tp.getTree());


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    retval.sb = new StringBuilder();
					    retval.sb.append((tp!=null?((EugeneParser.toPrint_return)tp).sb:null));
					}	
						
					}
					break;

			}
			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "toPrint_prime"


	public static class imperativeStatements_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "imperativeStatements"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2122:1: imperativeStatements[boolean defer] : ( if_elseif_else[defer] | forall_iterator[defer] | for_loop[defer] | while_loop[defer] );
	public final EugeneParser.imperativeStatements_return imperativeStatements(boolean defer) throws RecognitionException, EugeneReturnException {
		EugeneParser.imperativeStatements_return retval = new EugeneParser.imperativeStatements_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		ParserRuleReturnScope if_elseif_else185 =null;
		ParserRuleReturnScope forall_iterator186 =null;
		ParserRuleReturnScope for_loop187 =null;
		ParserRuleReturnScope while_loop188 =null;


		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2124:2: ( if_elseif_else[defer] | forall_iterator[defer] | for_loop[defer] | while_loop[defer] )
			int alt70=4;
			switch ( input.LA(1) ) {
			case LC_IF:
			case UC_IF:
				{
				alt70=1;
				}
				break;
			case LC_FORALL:
			case UC_FORALL:
				{
				alt70=2;
				}
				break;
			case LC_FOR:
			case UC_FOR:
				{
				alt70=3;
				}
				break;
			case LC_WHILE:
			case UC_WHILE:
				{
				alt70=4;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 70, 0, input);
				throw nvae;
			}
			switch (alt70) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2124:4: if_elseif_else[defer]
					{
					root_0 = (Object)adaptor.nil();


					pushFollow(FOLLOW_if_elseif_else_in_imperativeStatements3898);
					if_elseif_else185=if_elseif_else(defer);
					state._fsp--;

					adaptor.addChild(root_0, if_elseif_else185.getTree());

					}
					break;
				case 2 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2125:4: forall_iterator[defer]
					{
					root_0 = (Object)adaptor.nil();


					pushFollow(FOLLOW_forall_iterator_in_imperativeStatements3904);
					forall_iterator186=forall_iterator(defer);
					state._fsp--;

					adaptor.addChild(root_0, forall_iterator186.getTree());

					}
					break;
				case 3 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2126:4: for_loop[defer]
					{
					root_0 = (Object)adaptor.nil();


					pushFollow(FOLLOW_for_loop_in_imperativeStatements3910);
					for_loop187=for_loop(defer);
					state._fsp--;

					adaptor.addChild(root_0, for_loop187.getTree());

					}
					break;
				case 4 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2127:4: while_loop[defer]
					{
					root_0 = (Object)adaptor.nil();


					pushFollow(FOLLOW_while_loop_in_imperativeStatements3916);
					while_loop188=while_loop(defer);
					state._fsp--;

					adaptor.addChild(root_0, while_loop188.getTree());

					}
					break;

			}
			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "imperativeStatements"


	public static class if_elseif_else_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "if_elseif_else"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2130:1: if_elseif_else[boolean defer] : ( UC_IF | LC_IF ) LEFTP co= logical_condition[defer] RIGHTP LEFTCUR stmts= list_of_statements[true] RIGHTCUR ( ( UC_ELSEIF | LC_ELSEIF ) LEFTP co= logical_condition[defer] RIGHTP LEFTCUR stmts= list_of_statements[true] RIGHTCUR )* ( ( UC_ELSE | LC_ELSE ) LEFTCUR stmts= list_of_statements[true] RIGHTCUR )? ;
	public final EugeneParser.if_elseif_else_return if_elseif_else(boolean defer) throws RecognitionException, EugeneReturnException {
		EugeneParser.if_elseif_else_return retval = new EugeneParser.if_elseif_else_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token set189=null;
		Token LEFTP190=null;
		Token RIGHTP191=null;
		Token LEFTCUR192=null;
		Token RIGHTCUR193=null;
		Token set194=null;
		Token LEFTP195=null;
		Token RIGHTP196=null;
		Token LEFTCUR197=null;
		Token RIGHTCUR198=null;
		Token set199=null;
		Token LEFTCUR200=null;
		Token RIGHTCUR201=null;
		ParserRuleReturnScope co =null;
		ParserRuleReturnScope stmts =null;

		Object set189_tree=null;
		Object LEFTP190_tree=null;
		Object RIGHTP191_tree=null;
		Object LEFTCUR192_tree=null;
		Object RIGHTCUR193_tree=null;
		Object set194_tree=null;
		Object LEFTP195_tree=null;
		Object RIGHTP196_tree=null;
		Object LEFTCUR197_tree=null;
		Object RIGHTCUR198_tree=null;
		Object set199_tree=null;
		Object LEFTCUR200_tree=null;
		Object RIGHTCUR201_tree=null;


		boolean bExecuted = false;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2136:2: ( ( UC_IF | LC_IF ) LEFTP co= logical_condition[defer] RIGHTP LEFTCUR stmts= list_of_statements[true] RIGHTCUR ( ( UC_ELSEIF | LC_ELSEIF ) LEFTP co= logical_condition[defer] RIGHTP LEFTCUR stmts= list_of_statements[true] RIGHTCUR )* ( ( UC_ELSE | LC_ELSE ) LEFTCUR stmts= list_of_statements[true] RIGHTCUR )? )
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2140:3: ( UC_IF | LC_IF ) LEFTP co= logical_condition[defer] RIGHTP LEFTCUR stmts= list_of_statements[true] RIGHTCUR ( ( UC_ELSEIF | LC_ELSEIF ) LEFTP co= logical_condition[defer] RIGHTP LEFTCUR stmts= list_of_statements[true] RIGHTCUR )* ( ( UC_ELSE | LC_ELSE ) LEFTCUR stmts= list_of_statements[true] RIGHTCUR )?
			{
			root_0 = (Object)adaptor.nil();


			set189=input.LT(1);
			if ( input.LA(1)==LC_IF||input.LA(1)==UC_IF ) {
				input.consume();
				adaptor.addChild(root_0, (Object)adaptor.create(set189));
				state.errorRecovery=false;
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				throw mse;
			}
			LEFTP190=(Token)match(input,LEFTP,FOLLOW_LEFTP_in_if_elseif_else3954); 
			LEFTP190_tree = (Object)adaptor.create(LEFTP190);
			adaptor.addChild(root_0, LEFTP190_tree);

			pushFollow(FOLLOW_logical_condition_in_if_elseif_else3958);
			co=logical_condition(defer);
			state._fsp--;

			adaptor.addChild(root_0, co.getTree());

			RIGHTP191=(Token)match(input,RIGHTP,FOLLOW_RIGHTP_in_if_elseif_else3961); 
			RIGHTP191_tree = (Object)adaptor.create(RIGHTP191);
			adaptor.addChild(root_0, RIGHTP191_tree);

			LEFTCUR192=(Token)match(input,LEFTCUR,FOLLOW_LEFTCUR_in_if_elseif_else3963); 
			LEFTCUR192_tree = (Object)adaptor.create(LEFTCUR192);
			adaptor.addChild(root_0, LEFTCUR192_tree);

			pushFollow(FOLLOW_list_of_statements_in_if_elseif_else3971);
			stmts=list_of_statements(true);
			state._fsp--;

			adaptor.addChild(root_0, stmts.getTree());

			RIGHTCUR193=(Token)match(input,RIGHTCUR,FOLLOW_RIGHTCUR_in_if_elseif_else3974); 
			RIGHTCUR193_tree = (Object)adaptor.create(RIGHTCUR193);
			adaptor.addChild(root_0, RIGHTCUR193_tree);


			if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
			    // evaluate the condition
			    if((co!=null?((EugeneParser.logical_condition_return)co).b:false)) {
			        // if true => execute the statements
			        // and ignore the rest of the ifStatement
			        
			        try {
			            this.exec_branch((stmts!=null?(stmts.start):null).getTokenIndex());
			        } catch(EugeneReturnException ere) {
			            throw new EugeneReturnException(ere.getReturnValue());
			        } catch(EugeneException ee) {
			            printError(ee.getLocalizedMessage());
			        }
			        bExecuted = true;
			    }
			}			
					
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2163:3: ( ( UC_ELSEIF | LC_ELSEIF ) LEFTP co= logical_condition[defer] RIGHTP LEFTCUR stmts= list_of_statements[true] RIGHTCUR )*
			loop71:
			while (true) {
				int alt71=2;
				int LA71_0 = input.LA(1);
				if ( (LA71_0==LC_ELSEIF||LA71_0==UC_ELSEIF) ) {
					alt71=1;
				}

				switch (alt71) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2163:5: ( UC_ELSEIF | LC_ELSEIF ) LEFTP co= logical_condition[defer] RIGHTP LEFTCUR stmts= list_of_statements[true] RIGHTCUR
					{
					set194=input.LT(1);
					if ( input.LA(1)==LC_ELSEIF||input.LA(1)==UC_ELSEIF ) {
						input.consume();
						adaptor.addChild(root_0, (Object)adaptor.create(set194));
						state.errorRecovery=false;
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						throw mse;
					}
					LEFTP195=(Token)match(input,LEFTP,FOLLOW_LEFTP_in_if_elseif_else3995); 
					LEFTP195_tree = (Object)adaptor.create(LEFTP195);
					adaptor.addChild(root_0, LEFTP195_tree);

					pushFollow(FOLLOW_logical_condition_in_if_elseif_else3999);
					co=logical_condition(defer);
					state._fsp--;

					adaptor.addChild(root_0, co.getTree());

					RIGHTP196=(Token)match(input,RIGHTP,FOLLOW_RIGHTP_in_if_elseif_else4002); 
					RIGHTP196_tree = (Object)adaptor.create(RIGHTP196);
					adaptor.addChild(root_0, RIGHTP196_tree);

					LEFTCUR197=(Token)match(input,LEFTCUR,FOLLOW_LEFTCUR_in_if_elseif_else4004); 
					LEFTCUR197_tree = (Object)adaptor.create(LEFTCUR197);
					adaptor.addChild(root_0, LEFTCUR197_tree);

					pushFollow(FOLLOW_list_of_statements_in_if_elseif_else4012);
					stmts=list_of_statements(true);
					state._fsp--;

					adaptor.addChild(root_0, stmts.getTree());

					RIGHTCUR198=(Token)match(input,RIGHTCUR,FOLLOW_RIGHTCUR_in_if_elseif_else4015); 
					RIGHTCUR198_tree = (Object)adaptor.create(RIGHTCUR198);
					adaptor.addChild(root_0, RIGHTCUR198_tree);


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING && !bExecuted) {
					    // evaluate the condition
					    if((co!=null?((EugeneParser.logical_condition_return)co).b:false)) {
					        // if true => execute the statements
					        // and ignore the rest of the ifStatement
					        try {        
					            this.exec_branch((stmts!=null?(stmts.start):null).getTokenIndex());
					        } catch(EugeneReturnException ere) {
					            throw new EugeneReturnException(ere.getReturnValue());
					        } catch(EugeneException ee) {
					            printError(ee.getLocalizedMessage());
					        }
					        
					        bExecuted = true;
					    }
					}			
							
					}
					break;

				default :
					break loop71;
				}
			}

			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2186:3: ( ( UC_ELSE | LC_ELSE ) LEFTCUR stmts= list_of_statements[true] RIGHTCUR )?
			int alt72=2;
			int LA72_0 = input.LA(1);
			if ( (LA72_0==LC_ELSE||LA72_0==UC_ELSE) ) {
				alt72=1;
			}
			switch (alt72) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2186:4: ( UC_ELSE | LC_ELSE ) LEFTCUR stmts= list_of_statements[true] RIGHTCUR
					{
					set199=input.LT(1);
					if ( input.LA(1)==LC_ELSE||input.LA(1)==UC_ELSE ) {
						input.consume();
						adaptor.addChild(root_0, (Object)adaptor.create(set199));
						state.errorRecovery=false;
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						throw mse;
					}
					LEFTCUR200=(Token)match(input,LEFTCUR,FOLLOW_LEFTCUR_in_if_elseif_else4037); 
					LEFTCUR200_tree = (Object)adaptor.create(LEFTCUR200);
					adaptor.addChild(root_0, LEFTCUR200_tree);

					pushFollow(FOLLOW_list_of_statements_in_if_elseif_else4045);
					stmts=list_of_statements(true);
					state._fsp--;

					adaptor.addChild(root_0, stmts.getTree());

					RIGHTCUR201=(Token)match(input,RIGHTCUR,FOLLOW_RIGHTCUR_in_if_elseif_else4048); 
					RIGHTCUR201_tree = (Object)adaptor.create(RIGHTCUR201);
					adaptor.addChild(root_0, RIGHTCUR201_tree);


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING && !bExecuted) {
					    try {
					        this.exec_branch((stmts!=null?(stmts.start):null).getTokenIndex());        
					    } catch(EugeneReturnException ere) {
					        throw new EugeneReturnException(ere.getReturnValue());
					    } catch(EugeneException ee) {
					        printError(ee.getLocalizedMessage());
					    }
					    
					    bExecuted = true;
					}						
						
					}
					break;

			}

			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "if_elseif_else"


	public static class forall_iterator_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "forall_iterator"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2203:1: forall_iterator[boolean defer] : ( UC_FORALL | LC_FORALL ) (it= ID COLON )? i= ID LEFTCUR los= list_of_statements[defer] RIGHTCUR ;
	public final EugeneParser.forall_iterator_return forall_iterator(boolean defer) throws RecognitionException, EugeneReturnException {
		EugeneParser.forall_iterator_return retval = new EugeneParser.forall_iterator_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token it=null;
		Token i=null;
		Token set202=null;
		Token COLON203=null;
		Token LEFTCUR204=null;
		Token RIGHTCUR205=null;
		ParserRuleReturnScope los =null;

		Object it_tree=null;
		Object i_tree=null;
		Object set202_tree=null;
		Object COLON203_tree=null;
		Object LEFTCUR204_tree=null;
		Object RIGHTCUR205_tree=null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2205:2: ( ( UC_FORALL | LC_FORALL ) (it= ID COLON )? i= ID LEFTCUR los= list_of_statements[defer] RIGHTCUR )
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2205:4: ( UC_FORALL | LC_FORALL ) (it= ID COLON )? i= ID LEFTCUR los= list_of_statements[defer] RIGHTCUR
			{
			root_0 = (Object)adaptor.nil();


			set202=input.LT(1);
			if ( input.LA(1)==LC_FORALL||input.LA(1)==UC_FORALL ) {
				input.consume();
				adaptor.addChild(root_0, (Object)adaptor.create(set202));
				state.errorRecovery=false;
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				throw mse;
			}
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2205:26: (it= ID COLON )?
			int alt73=2;
			int LA73_0 = input.LA(1);
			if ( (LA73_0==ID) ) {
				int LA73_1 = input.LA(2);
				if ( (LA73_1==COLON) ) {
					alt73=1;
				}
			}
			switch (alt73) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2205:27: it= ID COLON
					{
					it=(Token)match(input,ID,FOLLOW_ID_in_forall_iterator4079); 
					it_tree = (Object)adaptor.create(it);
					adaptor.addChild(root_0, it_tree);

					COLON203=(Token)match(input,COLON,FOLLOW_COLON_in_forall_iterator4081); 
					COLON203_tree = (Object)adaptor.create(COLON203);
					adaptor.addChild(root_0, COLON203_tree);

					}
					break;

			}

			i=(Token)match(input,ID,FOLLOW_ID_in_forall_iterator4087); 
			i_tree = (Object)adaptor.create(i);
			adaptor.addChild(root_0, i_tree);

			LEFTCUR204=(Token)match(input,LEFTCUR,FOLLOW_LEFTCUR_in_forall_iterator4089); 
			LEFTCUR204_tree = (Object)adaptor.create(LEFTCUR204);
			adaptor.addChild(root_0, LEFTCUR204_tree);

			pushFollow(FOLLOW_list_of_statements_in_forall_iterator4096);
			los=list_of_statements(defer);
			state._fsp--;

			adaptor.addChild(root_0, los.getTree());


			if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
			    try {
			        this.exec("list_of_statements", (los!=null?(los.start):null).getTokenIndex());
			    } catch(EugeneReturnException ere) {
			        throw new EugeneReturnException(ere.getReturnValue());
			    } catch(EugeneException ee) {
			        printError(ee.getLocalizedMessage());
			    }
			}			
				
			RIGHTCUR205=(Token)match(input,RIGHTCUR,FOLLOW_RIGHTCUR_in_forall_iterator4103); 
			RIGHTCUR205_tree = (Object)adaptor.create(RIGHTCUR205);
			adaptor.addChild(root_0, RIGHTCUR205_tree);

			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "forall_iterator"


	public static class for_loop_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "for_loop"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2220:1: for_loop[boolean defer] : ( UC_FOR | LC_FOR ) LEFTP ds= variableDeclaration[true] SEMIC co= logical_condition[true] SEMIC (as= assignment[true] )? RIGHTP LEFTCUR stmts= list_of_statements[true] RIGHTCUR ;
	public final EugeneParser.for_loop_return for_loop(boolean defer) throws RecognitionException, EugeneReturnException {
		EugeneParser.for_loop_return retval = new EugeneParser.for_loop_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token set206=null;
		Token LEFTP207=null;
		Token SEMIC208=null;
		Token SEMIC209=null;
		Token RIGHTP210=null;
		Token LEFTCUR211=null;
		Token RIGHTCUR212=null;
		ParserRuleReturnScope ds =null;
		ParserRuleReturnScope co =null;
		ParserRuleReturnScope as =null;
		ParserRuleReturnScope stmts =null;

		Object set206_tree=null;
		Object LEFTP207_tree=null;
		Object SEMIC208_tree=null;
		Object SEMIC209_tree=null;
		Object RIGHTP210_tree=null;
		Object LEFTCUR211_tree=null;
		Object RIGHTCUR212_tree=null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2222:2: ( ( UC_FOR | LC_FOR ) LEFTP ds= variableDeclaration[true] SEMIC co= logical_condition[true] SEMIC (as= assignment[true] )? RIGHTP LEFTCUR stmts= list_of_statements[true] RIGHTCUR )
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2222:4: ( UC_FOR | LC_FOR ) LEFTP ds= variableDeclaration[true] SEMIC co= logical_condition[true] SEMIC (as= assignment[true] )? RIGHTP LEFTCUR stmts= list_of_statements[true] RIGHTCUR
			{
			root_0 = (Object)adaptor.nil();


			set206=input.LT(1);
			if ( input.LA(1)==LC_FOR||input.LA(1)==UC_FOR ) {
				input.consume();
				adaptor.addChild(root_0, (Object)adaptor.create(set206));
				state.errorRecovery=false;
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				throw mse;
			}
			LEFTP207=(Token)match(input,LEFTP,FOLLOW_LEFTP_in_for_loop4126); 
			LEFTP207_tree = (Object)adaptor.create(LEFTP207);
			adaptor.addChild(root_0, LEFTP207_tree);

			pushFollow(FOLLOW_variableDeclaration_in_for_loop4130);
			ds=variableDeclaration(true);
			state._fsp--;

			adaptor.addChild(root_0, ds.getTree());

			SEMIC208=(Token)match(input,SEMIC,FOLLOW_SEMIC_in_for_loop4133); 
			SEMIC208_tree = (Object)adaptor.create(SEMIC208);
			adaptor.addChild(root_0, SEMIC208_tree);

			pushFollow(FOLLOW_logical_condition_in_for_loop4137);
			co=logical_condition(true);
			state._fsp--;

			adaptor.addChild(root_0, co.getTree());

			SEMIC209=(Token)match(input,SEMIC,FOLLOW_SEMIC_in_for_loop4140); 
			SEMIC209_tree = (Object)adaptor.create(SEMIC209);
			adaptor.addChild(root_0, SEMIC209_tree);

			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2222:94: (as= assignment[true] )?
			int alt74=2;
			int LA74_0 = input.LA(1);
			if ( (LA74_0==ID) ) {
				alt74=1;
			}
			switch (alt74) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2222:95: as= assignment[true]
					{
					pushFollow(FOLLOW_assignment_in_for_loop4145);
					as=assignment(true);
					state._fsp--;

					adaptor.addChild(root_0, as.getTree());

					}
					break;

			}

			RIGHTP210=(Token)match(input,RIGHTP,FOLLOW_RIGHTP_in_for_loop4150); 
			RIGHTP210_tree = (Object)adaptor.create(RIGHTP210);
			adaptor.addChild(root_0, RIGHTP210_tree);

			LEFTCUR211=(Token)match(input,LEFTCUR,FOLLOW_LEFTCUR_in_for_loop4152); 
			LEFTCUR211_tree = (Object)adaptor.create(LEFTCUR211);
			adaptor.addChild(root_0, LEFTCUR211_tree);

			pushFollow(FOLLOW_list_of_statements_in_for_loop4160);
			stmts=list_of_statements(true);
			state._fsp--;

			adaptor.addChild(root_0, stmts.getTree());


			if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
			    try {
			        if(null != as) {
			            this.execute_loop(
			                (ds!=null?(ds.start):null),      // init
			                (co!=null?(co.start):null),      // condition
			                (as!=null?(as.start):null),      // increment
			                (stmts!=null?(stmts.start):null));  // loop-body
			        } else {
			            this.execute_loop(
			                (ds!=null?(ds.start):null),      // init
			                (co!=null?(co.start):null),      // condition
			                null,           // increment
			                (stmts!=null?(stmts.start):null));  // loop-body
			        }
			    } catch(EugeneReturnException ere) {
			        throw new EugeneReturnException(ere.getReturnValue());
			    } catch(Exception ee) {
			        printError(ee.getLocalizedMessage());
			    }
			}			
					
			RIGHTCUR212=(Token)match(input,RIGHTCUR,FOLLOW_RIGHTCUR_in_for_loop4167); 
			RIGHTCUR212_tree = (Object)adaptor.create(RIGHTCUR212);
			adaptor.addChild(root_0, RIGHTCUR212_tree);

			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "for_loop"


	public static class while_loop_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "while_loop"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2249:1: while_loop[boolean defer] : ( UC_WHILE | LC_WHILE ) LEFTP co= logical_condition[true] RIGHTP LEFTCUR stmts= list_of_statements[true] RIGHTCUR ;
	public final EugeneParser.while_loop_return while_loop(boolean defer) throws RecognitionException, EugeneReturnException {
		EugeneParser.while_loop_return retval = new EugeneParser.while_loop_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token set213=null;
		Token LEFTP214=null;
		Token RIGHTP215=null;
		Token LEFTCUR216=null;
		Token RIGHTCUR217=null;
		ParserRuleReturnScope co =null;
		ParserRuleReturnScope stmts =null;

		Object set213_tree=null;
		Object LEFTP214_tree=null;
		Object RIGHTP215_tree=null;
		Object LEFTCUR216_tree=null;
		Object RIGHTCUR217_tree=null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2251:2: ( ( UC_WHILE | LC_WHILE ) LEFTP co= logical_condition[true] RIGHTP LEFTCUR stmts= list_of_statements[true] RIGHTCUR )
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2251:4: ( UC_WHILE | LC_WHILE ) LEFTP co= logical_condition[true] RIGHTP LEFTCUR stmts= list_of_statements[true] RIGHTCUR
			{
			root_0 = (Object)adaptor.nil();


			set213=input.LT(1);
			if ( input.LA(1)==LC_WHILE||input.LA(1)==UC_WHILE ) {
				input.consume();
				adaptor.addChild(root_0, (Object)adaptor.create(set213));
				state.errorRecovery=false;
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				throw mse;
			}
			LEFTP214=(Token)match(input,LEFTP,FOLLOW_LEFTP_in_while_loop4192); 
			LEFTP214_tree = (Object)adaptor.create(LEFTP214);
			adaptor.addChild(root_0, LEFTP214_tree);

			pushFollow(FOLLOW_logical_condition_in_while_loop4196);
			co=logical_condition(true);
			state._fsp--;

			adaptor.addChild(root_0, co.getTree());

			RIGHTP215=(Token)match(input,RIGHTP,FOLLOW_RIGHTP_in_while_loop4199); 
			RIGHTP215_tree = (Object)adaptor.create(RIGHTP215);
			adaptor.addChild(root_0, RIGHTP215_tree);

			LEFTCUR216=(Token)match(input,LEFTCUR,FOLLOW_LEFTCUR_in_while_loop4201); 
			LEFTCUR216_tree = (Object)adaptor.create(LEFTCUR216);
			adaptor.addChild(root_0, LEFTCUR216_tree);

			pushFollow(FOLLOW_list_of_statements_in_while_loop4209);
			stmts=list_of_statements(true);
			state._fsp--;

			adaptor.addChild(root_0, stmts.getTree());


			if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
			    try {
			        this.execute_loop(
			            null,           // init
			            (co!=null?(co.start):null),      // condition
			            null,           // increment
			            (stmts!=null?(stmts.start):null));  // loop-body
			    } catch(EugeneReturnException ere) {
			        throw new EugeneReturnException(ere.getReturnValue());
			    } catch(Exception ee) {
			        printError(ee.getLocalizedMessage());
			    }
			}			
				
			RIGHTCUR217=(Token)match(input,RIGHTCUR,FOLLOW_RIGHTCUR_in_while_loop4216); 
			RIGHTCUR217_tree = (Object)adaptor.create(RIGHTCUR217);
			adaptor.addChild(root_0, RIGHTCUR217_tree);

			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "while_loop"


	public static class logical_condition_return extends ParserRuleReturnScope {
		public boolean b;
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "logical_condition"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2275:1: logical_condition[boolean defer] returns [boolean b] : loc= logical_or_condition[defer] ;
	public final EugeneParser.logical_condition_return logical_condition(boolean defer) throws RecognitionException {
		EugeneParser.logical_condition_return retval = new EugeneParser.logical_condition_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		ParserRuleReturnScope loc =null;


		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2277:2: (loc= logical_or_condition[defer] )
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2277:4: loc= logical_or_condition[defer]
			{
			root_0 = (Object)adaptor.nil();


			pushFollow(FOLLOW_logical_or_condition_in_logical_condition4242);
			loc=logical_or_condition(defer);
			state._fsp--;

			adaptor.addChild(root_0, loc.getTree());


			if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
			    retval.b = (loc!=null?((EugeneParser.logical_or_condition_return)loc).b:false);
			}	
				
			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "logical_condition"


	public static class logical_not_condition_return extends ParserRuleReturnScope {
		public boolean b;
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "logical_not_condition"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2284:1: logical_not_condition[boolean defer] returns [boolean b] : loc= logical_or_condition[defer] ;
	public final EugeneParser.logical_not_condition_return logical_not_condition(boolean defer) throws RecognitionException {
		EugeneParser.logical_not_condition_return retval = new EugeneParser.logical_not_condition_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		ParserRuleReturnScope loc =null;


		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2286:2: (loc= logical_or_condition[defer] )
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2286:4: loc= logical_or_condition[defer]
			{
			root_0 = (Object)adaptor.nil();


			pushFollow(FOLLOW_logical_or_condition_in_logical_not_condition4267);
			loc=logical_or_condition(defer);
			state._fsp--;

			adaptor.addChild(root_0, loc.getTree());


			if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
			    retval.b = (loc!=null?((EugeneParser.logical_or_condition_return)loc).b:false);
			}	
				
			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "logical_not_condition"


	public static class logical_or_condition_return extends ParserRuleReturnScope {
		public boolean b;
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "logical_or_condition"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2293:1: logical_or_condition[boolean defer] returns [boolean b] : lac= logical_and_condition[defer] ( ( LC_OR | UC_OR | LOG_OR | PIPE ( PIPE )? ) loc= logical_or_condition[defer] )* ;
	public final EugeneParser.logical_or_condition_return logical_or_condition(boolean defer) throws RecognitionException {
		EugeneParser.logical_or_condition_return retval = new EugeneParser.logical_or_condition_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token LC_OR218=null;
		Token UC_OR219=null;
		Token LOG_OR220=null;
		Token PIPE221=null;
		Token PIPE222=null;
		ParserRuleReturnScope lac =null;
		ParserRuleReturnScope loc =null;

		Object LC_OR218_tree=null;
		Object UC_OR219_tree=null;
		Object LOG_OR220_tree=null;
		Object PIPE221_tree=null;
		Object PIPE222_tree=null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2295:2: (lac= logical_and_condition[defer] ( ( LC_OR | UC_OR | LOG_OR | PIPE ( PIPE )? ) loc= logical_or_condition[defer] )* )
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2295:4: lac= logical_and_condition[defer] ( ( LC_OR | UC_OR | LOG_OR | PIPE ( PIPE )? ) loc= logical_or_condition[defer] )*
			{
			root_0 = (Object)adaptor.nil();


			pushFollow(FOLLOW_logical_and_condition_in_logical_or_condition4292);
			lac=logical_and_condition(defer);
			state._fsp--;

			adaptor.addChild(root_0, lac.getTree());


			if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
			    retval.b = (lac!=null?((EugeneParser.logical_and_condition_return)lac).b:false);
			}	
				
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2299:4: ( ( LC_OR | UC_OR | LOG_OR | PIPE ( PIPE )? ) loc= logical_or_condition[defer] )*
			loop77:
			while (true) {
				int alt77=2;
				switch ( input.LA(1) ) {
				case LC_OR:
					{
					alt77=1;
					}
					break;
				case UC_OR:
					{
					alt77=1;
					}
					break;
				case LOG_OR:
					{
					alt77=1;
					}
					break;
				case PIPE:
					{
					alt77=1;
					}
					break;
				}
				switch (alt77) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2299:5: ( LC_OR | UC_OR | LOG_OR | PIPE ( PIPE )? ) loc= logical_or_condition[defer]
					{
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2299:5: ( LC_OR | UC_OR | LOG_OR | PIPE ( PIPE )? )
					int alt76=4;
					switch ( input.LA(1) ) {
					case LC_OR:
						{
						alt76=1;
						}
						break;
					case UC_OR:
						{
						alt76=2;
						}
						break;
					case LOG_OR:
						{
						alt76=3;
						}
						break;
					case PIPE:
						{
						alt76=4;
						}
						break;
					default:
						NoViableAltException nvae =
							new NoViableAltException("", 76, 0, input);
						throw nvae;
					}
					switch (alt76) {
						case 1 :
							// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2299:6: LC_OR
							{
							LC_OR218=(Token)match(input,LC_OR,FOLLOW_LC_OR_in_logical_or_condition4299); 
							LC_OR218_tree = (Object)adaptor.create(LC_OR218);
							adaptor.addChild(root_0, LC_OR218_tree);

							}
							break;
						case 2 :
							// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2299:12: UC_OR
							{
							UC_OR219=(Token)match(input,UC_OR,FOLLOW_UC_OR_in_logical_or_condition4301); 
							UC_OR219_tree = (Object)adaptor.create(UC_OR219);
							adaptor.addChild(root_0, UC_OR219_tree);

							}
							break;
						case 3 :
							// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2299:18: LOG_OR
							{
							LOG_OR220=(Token)match(input,LOG_OR,FOLLOW_LOG_OR_in_logical_or_condition4303); 
							LOG_OR220_tree = (Object)adaptor.create(LOG_OR220);
							adaptor.addChild(root_0, LOG_OR220_tree);

							}
							break;
						case 4 :
							// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2299:25: PIPE ( PIPE )?
							{
							PIPE221=(Token)match(input,PIPE,FOLLOW_PIPE_in_logical_or_condition4305); 
							PIPE221_tree = (Object)adaptor.create(PIPE221);
							adaptor.addChild(root_0, PIPE221_tree);

							// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2299:30: ( PIPE )?
							int alt75=2;
							int LA75_0 = input.LA(1);
							if ( (LA75_0==PIPE) ) {
								alt75=1;
							}
							switch (alt75) {
								case 1 :
									// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2299:31: PIPE
									{
									PIPE222=(Token)match(input,PIPE,FOLLOW_PIPE_in_logical_or_condition4308); 
									PIPE222_tree = (Object)adaptor.create(PIPE222);
									adaptor.addChild(root_0, PIPE222_tree);

									}
									break;

							}

							}
							break;

					}

					pushFollow(FOLLOW_logical_or_condition_in_logical_or_condition4315);
					loc=logical_or_condition(defer);
					state._fsp--;

					adaptor.addChild(root_0, loc.getTree());


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    System.out.println("-> b: " + retval.b + " || " + (loc!=null?((EugeneParser.logical_or_condition_return)loc).b:false) + " -> " + (retval.b||(loc!=null?((EugeneParser.logical_or_condition_return)loc).b:false)));
					    retval.b = retval.b || (loc!=null?((EugeneParser.logical_or_condition_return)loc).b:false);
					}		
						
					}
					break;

				default :
					break loop77;
				}
			}

			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "logical_or_condition"


	public static class logical_and_condition_return extends ParserRuleReturnScope {
		public boolean b;
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "logical_and_condition"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2307:1: logical_and_condition[boolean defer] returns [boolean b] : ac= atomic_condition[defer] ( ( LC_AND | UC_AND | LOG_AND | AMP ( AMP )? ) lac= logical_and_condition[defer] )* ;
	public final EugeneParser.logical_and_condition_return logical_and_condition(boolean defer) throws RecognitionException {
		EugeneParser.logical_and_condition_return retval = new EugeneParser.logical_and_condition_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token LC_AND223=null;
		Token UC_AND224=null;
		Token LOG_AND225=null;
		Token AMP226=null;
		Token AMP227=null;
		ParserRuleReturnScope ac =null;
		ParserRuleReturnScope lac =null;

		Object LC_AND223_tree=null;
		Object UC_AND224_tree=null;
		Object LOG_AND225_tree=null;
		Object AMP226_tree=null;
		Object AMP227_tree=null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2309:2: (ac= atomic_condition[defer] ( ( LC_AND | UC_AND | LOG_AND | AMP ( AMP )? ) lac= logical_and_condition[defer] )* )
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2309:4: ac= atomic_condition[defer] ( ( LC_AND | UC_AND | LOG_AND | AMP ( AMP )? ) lac= logical_and_condition[defer] )*
			{
			root_0 = (Object)adaptor.nil();


			pushFollow(FOLLOW_atomic_condition_in_logical_and_condition4340);
			ac=atomic_condition(defer);
			state._fsp--;

			adaptor.addChild(root_0, ac.getTree());


			if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
			    retval.b = (ac!=null?((EugeneParser.atomic_condition_return)ac).b:false);
			}	
				
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2313:4: ( ( LC_AND | UC_AND | LOG_AND | AMP ( AMP )? ) lac= logical_and_condition[defer] )*
			loop80:
			while (true) {
				int alt80=2;
				switch ( input.LA(1) ) {
				case LC_AND:
					{
					alt80=1;
					}
					break;
				case UC_AND:
					{
					alt80=1;
					}
					break;
				case LOG_AND:
					{
					alt80=1;
					}
					break;
				case AMP:
					{
					alt80=1;
					}
					break;
				}
				switch (alt80) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2313:5: ( LC_AND | UC_AND | LOG_AND | AMP ( AMP )? ) lac= logical_and_condition[defer]
					{
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2313:5: ( LC_AND | UC_AND | LOG_AND | AMP ( AMP )? )
					int alt79=4;
					switch ( input.LA(1) ) {
					case LC_AND:
						{
						alt79=1;
						}
						break;
					case UC_AND:
						{
						alt79=2;
						}
						break;
					case LOG_AND:
						{
						alt79=3;
						}
						break;
					case AMP:
						{
						alt79=4;
						}
						break;
					default:
						NoViableAltException nvae =
							new NoViableAltException("", 79, 0, input);
						throw nvae;
					}
					switch (alt79) {
						case 1 :
							// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2313:6: LC_AND
							{
							LC_AND223=(Token)match(input,LC_AND,FOLLOW_LC_AND_in_logical_and_condition4347); 
							LC_AND223_tree = (Object)adaptor.create(LC_AND223);
							adaptor.addChild(root_0, LC_AND223_tree);

							}
							break;
						case 2 :
							// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2313:13: UC_AND
							{
							UC_AND224=(Token)match(input,UC_AND,FOLLOW_UC_AND_in_logical_and_condition4349); 
							UC_AND224_tree = (Object)adaptor.create(UC_AND224);
							adaptor.addChild(root_0, UC_AND224_tree);

							}
							break;
						case 3 :
							// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2313:20: LOG_AND
							{
							LOG_AND225=(Token)match(input,LOG_AND,FOLLOW_LOG_AND_in_logical_and_condition4351); 
							LOG_AND225_tree = (Object)adaptor.create(LOG_AND225);
							adaptor.addChild(root_0, LOG_AND225_tree);

							}
							break;
						case 4 :
							// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2313:28: AMP ( AMP )?
							{
							AMP226=(Token)match(input,AMP,FOLLOW_AMP_in_logical_and_condition4353); 
							AMP226_tree = (Object)adaptor.create(AMP226);
							adaptor.addChild(root_0, AMP226_tree);

							// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2313:32: ( AMP )?
							int alt78=2;
							int LA78_0 = input.LA(1);
							if ( (LA78_0==AMP) ) {
								alt78=1;
							}
							switch (alt78) {
								case 1 :
									// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2313:33: AMP
									{
									AMP227=(Token)match(input,AMP,FOLLOW_AMP_in_logical_and_condition4356); 
									AMP227_tree = (Object)adaptor.create(AMP227);
									adaptor.addChild(root_0, AMP227_tree);

									}
									break;

							}

							}
							break;

					}

					pushFollow(FOLLOW_logical_and_condition_in_logical_and_condition4363);
					lac=logical_and_condition(defer);
					state._fsp--;

					adaptor.addChild(root_0, lac.getTree());


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    System.out.println("-> b: " + retval.b + " && " + (lac!=null?((EugeneParser.logical_and_condition_return)lac).b:false) + " -> " + (retval.b||(lac!=null?((EugeneParser.logical_and_condition_return)lac).b:false)));
					    retval.b = retval.b && (lac!=null?((EugeneParser.logical_and_condition_return)lac).b:false);
					}	
						
					}
					break;

				default :
					break loop80;
				}
			}

			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "logical_and_condition"


	public static class atomic_condition_return extends ParserRuleReturnScope {
		public boolean b;
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "atomic_condition"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2321:1: atomic_condition[boolean defer] returns [boolean b] : (lhs= expr[defer] ro= relationalOperators rhs= expr[defer] | ( LC_NOT | UC_NOT | OP_NOT ) LEFTP lac= atomic_condition[defer] RIGHTP );
	public final EugeneParser.atomic_condition_return atomic_condition(boolean defer) throws RecognitionException {
		EugeneParser.atomic_condition_return retval = new EugeneParser.atomic_condition_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token set228=null;
		Token LEFTP229=null;
		Token RIGHTP230=null;
		ParserRuleReturnScope lhs =null;
		ParserRuleReturnScope ro =null;
		ParserRuleReturnScope rhs =null;
		ParserRuleReturnScope lac =null;

		Object set228_tree=null;
		Object LEFTP229_tree=null;
		Object RIGHTP230_tree=null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2323:2: (lhs= expr[defer] ro= relationalOperators rhs= expr[defer] | ( LC_NOT | UC_NOT | OP_NOT ) LEFTP lac= atomic_condition[defer] RIGHTP )
			int alt81=2;
			int LA81_0 = input.LA(1);
			if ( (LA81_0==DOLLAR||(LA81_0 >= FALSE_LC && LA81_0 <= FALSE_UC)||LA81_0==ID||(LA81_0 >= LEFTP && LA81_0 <= LEFTSBR)||LA81_0==MINUS||LA81_0==NUMBER||LA81_0==PERMUTE||LA81_0==PRODUCT||(LA81_0 >= RANDOM_LC && LA81_0 <= RANDOM_UC)||LA81_0==REAL||(LA81_0 >= SIZEOF_LC && LA81_0 <= SIZE_UC)||(LA81_0 >= STRING && LA81_0 <= TRUE_UC)) ) {
				alt81=1;
			}
			else if ( (LA81_0==LC_NOT||LA81_0==OP_NOT||LA81_0==UC_NOT) ) {
				alt81=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 81, 0, input);
				throw nvae;
			}

			switch (alt81) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2323:4: lhs= expr[defer] ro= relationalOperators rhs= expr[defer]
					{
					root_0 = (Object)adaptor.nil();


					pushFollow(FOLLOW_expr_in_atomic_condition4391);
					lhs=expr(defer);
					state._fsp--;

					adaptor.addChild(root_0, lhs.getTree());

					pushFollow(FOLLOW_relationalOperators_in_atomic_condition4396);
					ro=relationalOperators();
					state._fsp--;

					adaptor.addChild(root_0, ro.getTree());

					pushFollow(FOLLOW_expr_in_atomic_condition4400);
					rhs=expr(defer);
					state._fsp--;

					adaptor.addChild(root_0, rhs.getTree());


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    try {
					        if(null != (lhs!=null?((EugeneParser.expr_return)lhs).element:null)) {
					            if(null != (rhs!=null?((EugeneParser.expr_return)rhs).element:null)) {
					                // comparing two NamedElements against each other
					                // e.g. p.prop = q.prop
					                retval.b = this.interp.evaluateCondition(
					                             (lhs!=null?((EugeneParser.expr_return)lhs).element:null), 
					                             (ro!=null?input.toString(ro.start,ro.stop):null), 
					                             (rhs!=null?((EugeneParser.expr_return)rhs).element:null));
					            } else if(null != (rhs!=null?((EugeneParser.expr_return)rhs).p:null)) {
					                // comparing a LHS NamedElement against a Variable
					                // that could be either a variable or constant
					                // e.g. p.prop = i
					                retval.b = this.interp.evaluateCondition(
					                             (lhs!=null?((EugeneParser.expr_return)lhs).element:null), 
					                             (ro!=null?input.toString(ro.start,ro.stop):null), 
					                             (rhs!=null?((EugeneParser.expr_return)rhs).p:null));
					            }
					        } else {
					        
					            if(null != (rhs!=null?((EugeneParser.expr_return)rhs).element:null)) {
					                // comparing a LHS variable against a RHS 
					                // NamedElement
					                // e.g. i == q.prop
					                retval.b = this.interp.evaluateCondition(
					                             (lhs!=null?((EugeneParser.expr_return)lhs).p:null), 
					                             (ro!=null?input.toString(ro.start,ro.stop):null), 
					                             (rhs!=null?((EugeneParser.expr_return)rhs).element:null));
					            } else if(null != (rhs!=null?((EugeneParser.expr_return)rhs).p:null)) {
					                // comparing a LHS Variable against a Variable
					                // that could be either a variable or constant
					                // e.g. i == j
					                retval.b = this.interp.evaluateCondition(
					                             (lhs!=null?((EugeneParser.expr_return)lhs).p:null), 
					                             (ro!=null?input.toString(ro.start,ro.stop):null), 
					                             (rhs!=null?((EugeneParser.expr_return)rhs).p:null));
					            } else {
					                throw new EugeneException("Invalid condition!");
					            }
					        }
					    } catch(EugeneException ee) {
					        printError(ee.getLocalizedMessage());
					    }
					}	
						
					}
					break;
				case 2 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2370:4: ( LC_NOT | UC_NOT | OP_NOT ) LEFTP lac= atomic_condition[defer] RIGHTP
					{
					root_0 = (Object)adaptor.nil();


					set228=input.LT(1);
					if ( input.LA(1)==LC_NOT||input.LA(1)==OP_NOT||input.LA(1)==UC_NOT ) {
						input.consume();
						adaptor.addChild(root_0, (Object)adaptor.create(set228));
						state.errorRecovery=false;
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						throw mse;
					}
					LEFTP229=(Token)match(input,LEFTP,FOLLOW_LEFTP_in_atomic_condition4416); 
					LEFTP229_tree = (Object)adaptor.create(LEFTP229);
					adaptor.addChild(root_0, LEFTP229_tree);

					pushFollow(FOLLOW_atomic_condition_in_atomic_condition4420);
					lac=atomic_condition(defer);
					state._fsp--;

					adaptor.addChild(root_0, lac.getTree());

					RIGHTP230=(Token)match(input,RIGHTP,FOLLOW_RIGHTP_in_atomic_condition4423); 
					RIGHTP230_tree = (Object)adaptor.create(RIGHTP230);
					adaptor.addChild(root_0, RIGHTP230_tree);


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    retval.b = !((lac!=null?((EugeneParser.atomic_condition_return)lac).b:false));
					}
						
					}
					break;

			}
			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "atomic_condition"


	public static class expr_return extends ParserRuleReturnScope {
		public Variable p;
		public String instance;
		public int index;
		public String listAddress;
		public Variable primVariable;
		public NamedElement element;
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "expr"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2381:1: expr[boolean defer] returns [Variable p, String instance, int index, String listAddress, Variable primVariable, NamedElement element] : e= multExpr[defer] (op= ( PLUS | MINUS ) e= multExpr[defer] )* ;
	public final EugeneParser.expr_return expr(boolean defer) throws RecognitionException {
		EugeneParser.expr_return retval = new EugeneParser.expr_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token op=null;
		ParserRuleReturnScope e =null;

		Object op_tree=null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2383:2: (e= multExpr[defer] (op= ( PLUS | MINUS ) e= multExpr[defer] )* )
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2383:4: e= multExpr[defer] (op= ( PLUS | MINUS ) e= multExpr[defer] )*
			{
			root_0 = (Object)adaptor.nil();


			pushFollow(FOLLOW_multExpr_in_expr4450);
			e=multExpr(defer);
			state._fsp--;

			adaptor.addChild(root_0, e.getTree());


			if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
			    if(null != (e!=null?((EugeneParser.multExpr_return)e).p:null)) {
			        retval.p = copyVariable((e!=null?((EugeneParser.multExpr_return)e).p:null));
			    
			        retval.instance = (e!=null?((EugeneParser.multExpr_return)e).instance:null);
			        retval.index = (e!=null?((EugeneParser.multExpr_return)e).index:0);
			        if ((e!=null?((EugeneParser.multExpr_return)e).listAddress:null) != null) {
			            retval.listAddress = (e!=null?((EugeneParser.multExpr_return)e).listAddress:null);
			        }
			        retval.primVariable = (e!=null?((EugeneParser.multExpr_return)e).primVariable:null);

			    } else if((e!=null?((EugeneParser.multExpr_return)e).element:null) != null && !((e!=null?((EugeneParser.multExpr_return)e).element:null) instanceof Variable) && !((e!=null?((EugeneParser.multExpr_return)e).element:null) instanceof PropertyValue)) { 			
			        retval.element = (e!=null?((EugeneParser.multExpr_return)e).element:null);
			    } else {
			        retval.element = null;
			    }
			}
				
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2401:5: (op= ( PLUS | MINUS ) e= multExpr[defer] )*
			loop82:
			while (true) {
				int alt82=2;
				int LA82_0 = input.LA(1);
				if ( (LA82_0==MINUS||LA82_0==PLUS) ) {
					alt82=1;
				}

				switch (alt82) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2401:6: op= ( PLUS | MINUS ) e= multExpr[defer]
					{
					op=input.LT(1);
					if ( input.LA(1)==MINUS||input.LA(1)==PLUS ) {
						input.consume();
						adaptor.addChild(root_0, (Object)adaptor.create(op));
						state.errorRecovery=false;
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						throw mse;
					}
					pushFollow(FOLLOW_multExpr_in_expr4467);
					e=multExpr(defer);
					state._fsp--;

					adaptor.addChild(root_0, e.getTree());


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    try {
					        if(null != retval.element) {
					            if(null != (e!=null?((EugeneParser.multExpr_return)e).element:null)) {
					                retval.element = this.interp.doMinPlusOp((e!=null?((EugeneParser.multExpr_return)e).element:null), retval.element, (op!=null?op.getText():null));                
					            } else if(null != (e!=null?((EugeneParser.multExpr_return)e).p:null)) {
					                retval.element = this.interp.doMinPlusOp((e!=null?((EugeneParser.multExpr_return)e).p:null), retval.element, (op!=null?op.getText():null));
					            }
					            retval.p = null;            
					        } else {        
					            if(null != (e!=null?((EugeneParser.multExpr_return)e).element:null)) {
					                this.interp.doMinPlusOp((e!=null?((EugeneParser.multExpr_return)e).element:null), retval.p, (op!=null?op.getText():null));
					            } else {
					                this.interp.doMinPlusOp((e!=null?((EugeneParser.multExpr_return)e).p:null), retval.p, (op!=null?op.getText():null)); 
					            }
					            retval.element = null;
					        }
					    } catch(EugeneException ee) {
					        printError(ee.getLocalizedMessage());
					    }
					}
						
					}
					break;

				default :
					break loop82;
				}
			}

			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "expr"


	public static class multExpr_return extends ParserRuleReturnScope {
		public Variable p;
		public String instance;
		public int index;
		public String listAddress;
		public Variable primVariable;
		public NamedElement element;
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "multExpr"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2440:1: multExpr[boolean defer] returns [Variable p, String instance, int index, String listAddress, Variable primVariable, NamedElement element] : e= atom[defer] ( (mul= MULT |div= DIV ) e= atom[defer] )* ;
	public final EugeneParser.multExpr_return multExpr(boolean defer) throws RecognitionException {
		EugeneParser.multExpr_return retval = new EugeneParser.multExpr_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token mul=null;
		Token div=null;
		ParserRuleReturnScope e =null;

		Object mul_tree=null;
		Object div_tree=null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2442:2: (e= atom[defer] ( (mul= MULT |div= DIV ) e= atom[defer] )* )
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2442:4: e= atom[defer] ( (mul= MULT |div= DIV ) e= atom[defer] )*
			{
			root_0 = (Object)adaptor.nil();


			pushFollow(FOLLOW_atom_in_multExpr4497);
			e=atom(defer);
			state._fsp--;

			adaptor.addChild(root_0, e.getTree());


			if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
			    if( null != (e!=null?((EugeneParser.atom_return)e).p:null)) {
			        retval.p = copyVariable((e!=null?((EugeneParser.atom_return)e).p:null));
			        if ((e!=null?((EugeneParser.atom_return)e).instance:null) != null) {
			            retval.instance = (e!=null?((EugeneParser.atom_return)e).instance:null);
			        }
			    
			        retval.index = (e!=null?((EugeneParser.atom_return)e).index:0);
			        if ((e!=null?((EugeneParser.atom_return)e).listAddress:null) != null) {
			            retval.listAddress = (e!=null?((EugeneParser.atom_return)e).listAddress:null);
			        }
			        retval.primVariable = (e!=null?((EugeneParser.atom_return)e).primVariable:null);

			    } else if((e!=null?((EugeneParser.atom_return)e).element:null) != null && !((e!=null?((EugeneParser.atom_return)e).element:null) instanceof Variable) && !((e!=null?((EugeneParser.atom_return)e).element:null) instanceof PropertyValue)) { 			
			        retval.element = (e!=null?((EugeneParser.atom_return)e).element:null);
			    } else {
			        retval.element = null;
			    }
			}	
				
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2462:5: ( (mul= MULT |div= DIV ) e= atom[defer] )*
			loop84:
			while (true) {
				int alt84=2;
				int LA84_0 = input.LA(1);
				if ( (LA84_0==DIV||LA84_0==MULT) ) {
					alt84=1;
				}

				switch (alt84) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2462:7: (mul= MULT |div= DIV ) e= atom[defer]
					{
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2462:7: (mul= MULT |div= DIV )
					int alt83=2;
					int LA83_0 = input.LA(1);
					if ( (LA83_0==MULT) ) {
						alt83=1;
					}
					else if ( (LA83_0==DIV) ) {
						alt83=2;
					}

					else {
						NoViableAltException nvae =
							new NoViableAltException("", 83, 0, input);
						throw nvae;
					}

					switch (alt83) {
						case 1 :
							// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2462:8: mul= MULT
							{
							mul=(Token)match(input,MULT,FOLLOW_MULT_in_multExpr4508); 
							mul_tree = (Object)adaptor.create(mul);
							adaptor.addChild(root_0, mul_tree);

							}
							break;
						case 2 :
							// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2462:17: div= DIV
							{
							div=(Token)match(input,DIV,FOLLOW_DIV_in_multExpr4512); 
							div_tree = (Object)adaptor.create(div);
							adaptor.addChild(root_0, div_tree);

							}
							break;

					}

					pushFollow(FOLLOW_atom_in_multExpr4517);
					e=atom(defer);
					state._fsp--;

					adaptor.addChild(root_0, e.getTree());


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    try {
					        if ((mul!=null?mul.getText():null) != null) {
					            this.interp.doMultDivOp((e!=null?((EugeneParser.atom_return)e).p:null), retval.p, (mul!=null?mul.getText():null));
					        } else {
					            this.interp.doMultDivOp((e!=null?((EugeneParser.atom_return)e).p:null), retval.p, (div!=null?div.getText():null));
					        }
					    } catch(EugeneException ee) {
					        printError(ee.getLocalizedMessage());
					    }
					    
					    retval.element = null;
					}
						
					}
					break;

				default :
					break loop84;
				}
			}

			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "multExpr"


	public static class atom_return extends ParserRuleReturnScope {
		public Variable p = new Variable();
		public String instance;
		public int index = -1;
		public String listAddress;
		public Variable primVariable;
		public NamedElement element;
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "atom"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2479:1: atom[boolean defer] returns [Variable p = new Variable(), String instance, int index = -1, String listAddress, Variable primVariable, NamedElement element] : ( (n= NUMBER |n= REAL ) | MINUS (n= NUMBER |n= REAL ) | (t= ( TRUE_LC | TRUE_UC ) |f= ( FALSE_LC | FALSE_UC ) ) |dn= dynamic_naming[defer] oc= object_access[defer, $element] | STRING | '(' expr[defer] ')' | LEFTSBR list[defer] RIGHTSBR |bif= built_in_function[defer] |fc= function_call[defer] );
	public final EugeneParser.atom_return atom(boolean defer) throws RecognitionException {
		EugeneParser.atom_return retval = new EugeneParser.atom_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token n=null;
		Token t=null;
		Token f=null;
		Token MINUS231=null;
		Token STRING232=null;
		Token char_literal233=null;
		Token char_literal235=null;
		Token LEFTSBR236=null;
		Token RIGHTSBR238=null;
		ParserRuleReturnScope dn =null;
		ParserRuleReturnScope oc =null;
		ParserRuleReturnScope bif =null;
		ParserRuleReturnScope fc =null;
		ParserRuleReturnScope expr234 =null;
		ParserRuleReturnScope list237 =null;

		Object n_tree=null;
		Object t_tree=null;
		Object f_tree=null;
		Object MINUS231_tree=null;
		Object STRING232_tree=null;
		Object char_literal233_tree=null;
		Object char_literal235_tree=null;
		Object LEFTSBR236_tree=null;
		Object RIGHTSBR238_tree=null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2481:2: ( (n= NUMBER |n= REAL ) | MINUS (n= NUMBER |n= REAL ) | (t= ( TRUE_LC | TRUE_UC ) |f= ( FALSE_LC | FALSE_UC ) ) |dn= dynamic_naming[defer] oc= object_access[defer, $element] | STRING | '(' expr[defer] ')' | LEFTSBR list[defer] RIGHTSBR |bif= built_in_function[defer] |fc= function_call[defer] )
			int alt88=9;
			switch ( input.LA(1) ) {
			case NUMBER:
			case REAL:
				{
				alt88=1;
				}
				break;
			case MINUS:
				{
				alt88=2;
				}
				break;
			case FALSE_LC:
			case FALSE_UC:
			case TRUE_LC:
			case TRUE_UC:
				{
				alt88=3;
				}
				break;
			case ID:
				{
				int LA88_4 = input.LA(2);
				if ( (LA88_4==LEFTP) ) {
					alt88=9;
				}
				else if ( (LA88_4==EOF||LA88_4==AMP||LA88_4==COMMA||LA88_4==DIV||LA88_4==DOT||LA88_4==EQUALS||LA88_4==GEQUAL||LA88_4==GTHAN||LA88_4==LC_AND||LA88_4==LC_OR||(LA88_4 >= LEFTSBR && LA88_4 <= LEQUAL)||(LA88_4 >= LOG_AND && LA88_4 <= MINUS)||(LA88_4 >= MULT && LA88_4 <= NEQUAL)||(LA88_4 >= PIPE && LA88_4 <= PLUS)||(LA88_4 >= RIGHTCUR && LA88_4 <= RIGHTSBR)||LA88_4==SEMIC||LA88_4==UC_AND||LA88_4==UC_OR||LA88_4==141||(LA88_4 >= 143 && LA88_4 <= 144)||LA88_4==147||(LA88_4 >= 150 && LA88_4 <= 151)||LA88_4==153||(LA88_4 >= 166 && LA88_4 <= 167)||LA88_4==180||(LA88_4 >= 182 && LA88_4 <= 183)||LA88_4==186||(LA88_4 >= 189 && LA88_4 <= 190)||LA88_4==192||(LA88_4 >= 205 && LA88_4 <= 206)) ) {
					alt88=4;
				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 88, 4, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

				}
				break;
			case DOLLAR:
				{
				alt88=4;
				}
				break;
			case STRING:
				{
				alt88=5;
				}
				break;
			case LEFTP:
				{
				alt88=6;
				}
				break;
			case LEFTSBR:
				{
				alt88=7;
				}
				break;
			case PERMUTE:
			case PRODUCT:
			case RANDOM_LC:
			case RANDOM_UC:
			case SIZEOF_LC:
			case SIZEOF_UC:
			case SIZE_LC:
			case SIZE_UC:
				{
				alt88=8;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 88, 0, input);
				throw nvae;
			}
			switch (alt88) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2481:4: (n= NUMBER |n= REAL )
					{
					root_0 = (Object)adaptor.nil();


					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2481:4: (n= NUMBER |n= REAL )
					int alt85=2;
					int LA85_0 = input.LA(1);
					if ( (LA85_0==NUMBER) ) {
						alt85=1;
					}
					else if ( (LA85_0==REAL) ) {
						alt85=2;
					}

					else {
						NoViableAltException nvae =
							new NoViableAltException("", 85, 0, input);
						throw nvae;
					}

					switch (alt85) {
						case 1 :
							// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2481:5: n= NUMBER
							{
							n=(Token)match(input,NUMBER,FOLLOW_NUMBER_in_atom4544); 
							n_tree = (Object)adaptor.create(n);
							adaptor.addChild(root_0, n_tree);

							}
							break;
						case 2 :
							// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2481:16: n= REAL
							{
							n=(Token)match(input,REAL,FOLLOW_REAL_in_atom4550); 
							n_tree = (Object)adaptor.create(n);
							adaptor.addChild(root_0, n_tree);

							}
							break;

					}


							if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
								retval.p.num = Double.parseDouble((n!=null?n.getText():null));
								retval.p.type = EugeneConstants.NUM;
								
								retval.element = null;
							}
							
					}
					break;
				case 2 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2490:4: MINUS (n= NUMBER |n= REAL )
					{
					root_0 = (Object)adaptor.nil();


					MINUS231=(Token)match(input,MINUS,FOLLOW_MINUS_in_atom4560); 
					MINUS231_tree = (Object)adaptor.create(MINUS231);
					adaptor.addChild(root_0, MINUS231_tree);

					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2490:10: (n= NUMBER |n= REAL )
					int alt86=2;
					int LA86_0 = input.LA(1);
					if ( (LA86_0==NUMBER) ) {
						alt86=1;
					}
					else if ( (LA86_0==REAL) ) {
						alt86=2;
					}

					else {
						NoViableAltException nvae =
							new NoViableAltException("", 86, 0, input);
						throw nvae;
					}

					switch (alt86) {
						case 1 :
							// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2490:11: n= NUMBER
							{
							n=(Token)match(input,NUMBER,FOLLOW_NUMBER_in_atom4565); 
							n_tree = (Object)adaptor.create(n);
							adaptor.addChild(root_0, n_tree);

							}
							break;
						case 2 :
							// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2490:22: n= REAL
							{
							n=(Token)match(input,REAL,FOLLOW_REAL_in_atom4571); 
							n_tree = (Object)adaptor.create(n);
							adaptor.addChild(root_0, n_tree);

							}
							break;

					}


							if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
								retval.p.num = Double.parseDouble((n!=null?n.getText():null)) * -1.0;
								retval.p.type = EugeneConstants.NUM;

								retval.element = null;
							}
							
					}
					break;
				case 3 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2499:4: (t= ( TRUE_LC | TRUE_UC ) |f= ( FALSE_LC | FALSE_UC ) )
					{
					root_0 = (Object)adaptor.nil();


					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2499:4: (t= ( TRUE_LC | TRUE_UC ) |f= ( FALSE_LC | FALSE_UC ) )
					int alt87=2;
					int LA87_0 = input.LA(1);
					if ( ((LA87_0 >= TRUE_LC && LA87_0 <= TRUE_UC)) ) {
						alt87=1;
					}
					else if ( ((LA87_0 >= FALSE_LC && LA87_0 <= FALSE_UC)) ) {
						alt87=2;
					}

					else {
						NoViableAltException nvae =
							new NoViableAltException("", 87, 0, input);
						throw nvae;
					}

					switch (alt87) {
						case 1 :
							// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2499:5: t= ( TRUE_LC | TRUE_UC )
							{
							t=input.LT(1);
							if ( (input.LA(1) >= TRUE_LC && input.LA(1) <= TRUE_UC) ) {
								input.consume();
								adaptor.addChild(root_0, (Object)adaptor.create(t));
								state.errorRecovery=false;
							}
							else {
								MismatchedSetException mse = new MismatchedSetException(null,input);
								throw mse;
							}
							}
							break;
						case 2 :
							// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2499:27: f= ( FALSE_LC | FALSE_UC )
							{
							f=input.LT(1);
							if ( (input.LA(1) >= FALSE_LC && input.LA(1) <= FALSE_UC) ) {
								input.consume();
								adaptor.addChild(root_0, (Object)adaptor.create(f));
								state.errorRecovery=false;
							}
							else {
								MismatchedSetException mse = new MismatchedSetException(null,input);
								throw mse;
							}
							}
							break;

					}


							if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
								retval.p.type = EugeneConstants.BOOLEAN;
								if (t != null) {
									retval.p.bool = true;
								} else {
									retval.p.bool = false;
								}

								retval.element = null;
							}
							
					}
					break;
				case 4 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2512:4: dn= dynamic_naming[defer] oc= object_access[defer, $element]
					{
					root_0 = (Object)adaptor.nil();


					pushFollow(FOLLOW_dynamic_naming_in_atom4610);
					dn=dynamic_naming(defer);
					state._fsp--;

					adaptor.addChild(root_0, dn.getTree());


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    try {
					        retval.element = this.interp.get((dn!=null?((EugeneParser.dynamic_naming_return)dn).name:null));
										
					        if(null != retval.element) {
					            if(retval.element instanceof Variable) {
					                retval.p = copyVariable((Variable)retval.element);
					                retval.primVariable = (Variable)retval.element;
					            }
					        } else {
					            throw new EugeneException((dn!=null?((EugeneParser.dynamic_naming_return)dn).name:null) + " is not declared.");
					        }
					    } catch(EugeneException ee) {
					        printError(ee.getLocalizedMessage());
					    }
					}
						
					pushFollow(FOLLOW_object_access_in_atom4617);
					oc=object_access(defer, retval.element);
					state._fsp--;

					adaptor.addChild(root_0, oc.getTree());


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    retval.element = (oc!=null?((EugeneParser.object_access_return)oc).child:null);
					    
					    if(retval.element instanceof Variable) {
					        retval.p = (Variable)retval.element;
					        retval.element = null;
					    } else if(retval.element instanceof PropertyValue) {
					        retval.p = this.interp.convertPropertyValueToVariable((PropertyValue)retval.element);
					        retval.element = null;
					    } else {
					        retval.p = null;
					    }
					}		
						
					}
					break;
				case 5 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2544:4: STRING
					{
					root_0 = (Object)adaptor.nil();


					STRING232=(Token)match(input,STRING,FOLLOW_STRING_in_atom4626); 
					STRING232_tree = (Object)adaptor.create(STRING232);
					adaptor.addChild(root_0, STRING232_tree);


							if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
								retval.p.type = EugeneConstants.TXT;
								retval.p.txt = (STRING232!=null?STRING232.getText():null).substring(1, (STRING232!=null?STRING232.getText():null).length()-1);

								retval.element = null;
							}
						
					}
					break;
				case 6 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2553:4: '(' expr[defer] ')'
					{
					root_0 = (Object)adaptor.nil();


					char_literal233=(Token)match(input,LEFTP,FOLLOW_LEFTP_in_atom4634); 
					char_literal233_tree = (Object)adaptor.create(char_literal233);
					adaptor.addChild(root_0, char_literal233_tree);

					pushFollow(FOLLOW_expr_in_atom4636);
					expr234=expr(defer);
					state._fsp--;

					adaptor.addChild(root_0, expr234.getTree());

					char_literal235=(Token)match(input,RIGHTP,FOLLOW_RIGHTP_in_atom4639); 
					char_literal235_tree = (Object)adaptor.create(char_literal235);
					adaptor.addChild(root_0, char_literal235_tree);


							if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
								retval.p = (expr234!=null?((EugeneParser.expr_return)expr234).p:null);
								retval.primVariable = (expr234!=null?((EugeneParser.expr_return)expr234).primVariable:null);
								retval.element = (expr234!=null?((EugeneParser.expr_return)expr234).element:null);
							}
						
					}
					break;
				case 7 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2561:5: LEFTSBR list[defer] RIGHTSBR
					{
					root_0 = (Object)adaptor.nil();


					LEFTSBR236=(Token)match(input,LEFTSBR,FOLLOW_LEFTSBR_in_atom4648); 
					LEFTSBR236_tree = (Object)adaptor.create(LEFTSBR236);
					adaptor.addChild(root_0, LEFTSBR236_tree);

					pushFollow(FOLLOW_list_in_atom4650);
					list237=list(defer);
					state._fsp--;

					adaptor.addChild(root_0, list237.getTree());

					RIGHTSBR238=(Token)match(input,RIGHTSBR,FOLLOW_RIGHTSBR_in_atom4653); 
					RIGHTSBR238_tree = (Object)adaptor.create(RIGHTSBR238);
					adaptor.addChild(root_0, RIGHTSBR238_tree);


							if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
								retval.p = (list237!=null?((EugeneParser.list_return)list237).listPrim:null);
								retval.primVariable = (list237!=null?((EugeneParser.list_return)list237).listPrim:null);
								typeList="";
							}
						
					}
					break;
				case 8 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2569:4: bif= built_in_function[defer]
					{
					root_0 = (Object)adaptor.nil();


					pushFollow(FOLLOW_built_in_function_in_atom4663);
					bif=built_in_function(defer);
					state._fsp--;

					adaptor.addChild(root_0, bif.getTree());


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING && null != (bif!=null?((EugeneParser.built_in_function_return)bif).element:null)) {
					    if((bif!=null?((EugeneParser.built_in_function_return)bif).element:null) instanceof Variable) {
					        retval.p = (Variable)(bif!=null?((EugeneParser.built_in_function_return)bif).element:null);
					        retval.element = null;
					    } else {
					        retval.element = (bif!=null?((EugeneParser.built_in_function_return)bif).element:null);
					        retval.p = null;
					    }
					}		
						
					}
					break;
				case 9 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2580:4: fc= function_call[defer]
					{
					root_0 = (Object)adaptor.nil();


					pushFollow(FOLLOW_function_call_in_atom4673);
					fc=function_call(defer);
					state._fsp--;

					adaptor.addChild(root_0, fc.getTree());


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING && null != (fc!=null?((EugeneParser.function_call_return)fc).e:null)) {
					    if((fc!=null?((EugeneParser.function_call_return)fc).e:null) instanceof Variable) {
					        retval.p = (Variable)(fc!=null?((EugeneParser.function_call_return)fc).e:null);
					        retval.element = null;
					    } else {
					        retval.element = (fc!=null?((EugeneParser.function_call_return)fc).e:null);
					        retval.p = null;
					    }
					}		
						
					}
					break;

			}
			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "atom"


	public static class list_return extends ParserRuleReturnScope {
		public Variable listPrim;
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "list"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2593:1: list[boolean defer] returns [Variable listPrim] : str1= expr[defer] ( COMMA str2= expr[defer] )* ;
	public final EugeneParser.list_return list(boolean defer) throws RecognitionException {
		EugeneParser.list_return retval = new EugeneParser.list_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token COMMA239=null;
		ParserRuleReturnScope str1 =null;
		ParserRuleReturnScope str2 =null;

		Object COMMA239_tree=null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2595:2: (str1= expr[defer] ( COMMA str2= expr[defer] )* )
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2595:4: str1= expr[defer] ( COMMA str2= expr[defer] )*
			{
			root_0 = (Object)adaptor.nil();


			pushFollow(FOLLOW_expr_in_list4696);
			str1=expr(defer);
			state._fsp--;

			adaptor.addChild(root_0, str1.getTree());


			if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING){
			    if (null != (str1!=null?((EugeneParser.expr_return)str1).p:null)) {
			        retval.listPrim = new Variable();
			        if(EugeneConstants.NUM.equals(((str1!=null?((EugeneParser.expr_return)str1).p:null)).getType())) {
			            retval.listPrim.type =  EugeneConstants.NUMLIST;
			            typeList = EugeneConstants.NUM;
			        } else if(EugeneConstants.TXT.equals(((str1!=null?((EugeneParser.expr_return)str1).p:null)).getType())) {
			            retval.listPrim.type =  EugeneConstants.TXTLIST;
			            typeList = EugeneConstants.TXT;
			        } else {
			            printError("Only arrays of num and txt primitives are supported!");
			        }
			        
			        addToListPrim((str1!=null?((EugeneParser.expr_return)str1).p:null), retval.listPrim);
			    } else {
			        printError("Only arrays of num and txt primitives are supported!");
			    }
			}
				
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2614:5: ( COMMA str2= expr[defer] )*
			loop89:
			while (true) {
				int alt89=2;
				int LA89_0 = input.LA(1);
				if ( (LA89_0==COMMA) ) {
					alt89=1;
				}

				switch (alt89) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2614:6: COMMA str2= expr[defer]
					{
					COMMA239=(Token)match(input,COMMA,FOLLOW_COMMA_in_list4703); 
					COMMA239_tree = (Object)adaptor.create(COMMA239);
					adaptor.addChild(root_0, COMMA239_tree);

					pushFollow(FOLLOW_expr_in_list4707);
					str2=expr(defer);
					state._fsp--;

					adaptor.addChild(root_0, str2.getTree());


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    if(null != (str2!=null?((EugeneParser.expr_return)str2).p:null)) {
					        addToListPrim((str2!=null?((EugeneParser.expr_return)str2).p:null), retval.listPrim);
					    } else {
					        printError("Only arrays of num and txt primitives are supported!");
					    }
					}				
						
					}
					break;

				default :
					break loop89;
				}
			}

			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "list"


	public static class built_in_function_return extends ParserRuleReturnScope {
		public NamedElement element;
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "built_in_function"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2632:1: built_in_function[boolean defer] returns [NamedElement element] : ( ( SIZEOF_LC | SIZEOF_UC | SIZE_LC | SIZE_UC ) LEFTP e= expr[defer] RIGHTP | ( RANDOM_LC | RANDOM_UC ) LEFTP rg= range[defer] RIGHTP | (pr= PRODUCT |pe= PERMUTE ) LEFTP idToken= ID RIGHTP );
	public final EugeneParser.built_in_function_return built_in_function(boolean defer) throws RecognitionException {
		EugeneParser.built_in_function_return retval = new EugeneParser.built_in_function_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token pr=null;
		Token pe=null;
		Token idToken=null;
		Token set240=null;
		Token LEFTP241=null;
		Token RIGHTP242=null;
		Token set243=null;
		Token LEFTP244=null;
		Token RIGHTP245=null;
		Token LEFTP246=null;
		Token RIGHTP247=null;
		ParserRuleReturnScope e =null;
		ParserRuleReturnScope rg =null;

		Object pr_tree=null;
		Object pe_tree=null;
		Object idToken_tree=null;
		Object set240_tree=null;
		Object LEFTP241_tree=null;
		Object RIGHTP242_tree=null;
		Object set243_tree=null;
		Object LEFTP244_tree=null;
		Object RIGHTP245_tree=null;
		Object LEFTP246_tree=null;
		Object RIGHTP247_tree=null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2634:2: ( ( SIZEOF_LC | SIZEOF_UC | SIZE_LC | SIZE_UC ) LEFTP e= expr[defer] RIGHTP | ( RANDOM_LC | RANDOM_UC ) LEFTP rg= range[defer] RIGHTP | (pr= PRODUCT |pe= PERMUTE ) LEFTP idToken= ID RIGHTP )
			int alt91=3;
			switch ( input.LA(1) ) {
			case SIZEOF_LC:
			case SIZEOF_UC:
			case SIZE_LC:
			case SIZE_UC:
				{
				alt91=1;
				}
				break;
			case RANDOM_LC:
			case RANDOM_UC:
				{
				alt91=2;
				}
				break;
			case PERMUTE:
			case PRODUCT:
				{
				alt91=3;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 91, 0, input);
				throw nvae;
			}
			switch (alt91) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2634:4: ( SIZEOF_LC | SIZEOF_UC | SIZE_LC | SIZE_UC ) LEFTP e= expr[defer] RIGHTP
					{
					root_0 = (Object)adaptor.nil();


					set240=input.LT(1);
					if ( (input.LA(1) >= SIZEOF_LC && input.LA(1) <= SIZE_UC) ) {
						input.consume();
						adaptor.addChild(root_0, (Object)adaptor.create(set240));
						state.errorRecovery=false;
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						throw mse;
					}
					LEFTP241=(Token)match(input,LEFTP,FOLLOW_LEFTP_in_built_in_function4745); 
					LEFTP241_tree = (Object)adaptor.create(LEFTP241);
					adaptor.addChild(root_0, LEFTP241_tree);

					pushFollow(FOLLOW_expr_in_built_in_function4749);
					e=expr(defer);
					state._fsp--;

					adaptor.addChild(root_0, e.getTree());

					RIGHTP242=(Token)match(input,RIGHTP,FOLLOW_RIGHTP_in_built_in_function4752); 
					RIGHTP242_tree = (Object)adaptor.create(RIGHTP242);
					adaptor.addChild(root_0, RIGHTP242_tree);


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    try {
					        if(null != (e!=null?((EugeneParser.expr_return)e).element:null)) {
					            retval.element = this.interp.getSizeOf((e!=null?((EugeneParser.expr_return)e).element:null));
					        } else if(null != (e!=null?((EugeneParser.expr_return)e).p:null)) {
					            retval.element = this.interp.getSizeOf((e!=null?((EugeneParser.expr_return)e).p:null));
					        }
					    } catch(EugeneException ee) {
					        printError(ee.getLocalizedMessage());
					    }
					}	
						
					}
					break;
				case 2 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2647:4: ( RANDOM_LC | RANDOM_UC ) LEFTP rg= range[defer] RIGHTP
					{
					root_0 = (Object)adaptor.nil();


					set243=input.LT(1);
					if ( (input.LA(1) >= RANDOM_LC && input.LA(1) <= RANDOM_UC) ) {
						input.consume();
						adaptor.addChild(root_0, (Object)adaptor.create(set243));
						state.errorRecovery=false;
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						throw mse;
					}
					LEFTP244=(Token)match(input,LEFTP,FOLLOW_LEFTP_in_built_in_function4765); 
					LEFTP244_tree = (Object)adaptor.create(LEFTP244);
					adaptor.addChild(root_0, LEFTP244_tree);

					pushFollow(FOLLOW_range_in_built_in_function4769);
					rg=range(defer);
					state._fsp--;

					adaptor.addChild(root_0, rg.getTree());

					RIGHTP245=(Token)match(input,RIGHTP,FOLLOW_RIGHTP_in_built_in_function4772); 
					RIGHTP245_tree = (Object)adaptor.create(RIGHTP245);
					adaptor.addChild(root_0, RIGHTP245_tree);


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    try {
					        retval.element = this.interp.getRandom(
					                          (rg!=null?((EugeneParser.range_return)rg).sor:null), 
					                          (rg!=null?((EugeneParser.range_return)rg).eor:null));
					    } catch(EugeneException ee) {
					        printError(ee.getLocalizedMessage());
					    }
					}	
						
					}
					break;
				case 3 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2658:4: (pr= PRODUCT |pe= PERMUTE ) LEFTP idToken= ID RIGHTP
					{
					root_0 = (Object)adaptor.nil();


					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2658:4: (pr= PRODUCT |pe= PERMUTE )
					int alt90=2;
					int LA90_0 = input.LA(1);
					if ( (LA90_0==PRODUCT) ) {
						alt90=1;
					}
					else if ( (LA90_0==PERMUTE) ) {
						alt90=2;
					}

					else {
						NoViableAltException nvae =
							new NoViableAltException("", 90, 0, input);
						throw nvae;
					}

					switch (alt90) {
						case 1 :
							// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2658:5: pr= PRODUCT
							{
							pr=(Token)match(input,PRODUCT,FOLLOW_PRODUCT_in_built_in_function4782); 
							pr_tree = (Object)adaptor.create(pr);
							adaptor.addChild(root_0, pr_tree);

							}
							break;
						case 2 :
							// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2658:16: pe= PERMUTE
							{
							pe=(Token)match(input,PERMUTE,FOLLOW_PERMUTE_in_built_in_function4786); 
							pe_tree = (Object)adaptor.create(pe);
							adaptor.addChild(root_0, pe_tree);

							}
							break;

					}

					LEFTP246=(Token)match(input,LEFTP,FOLLOW_LEFTP_in_built_in_function4789); 
					LEFTP246_tree = (Object)adaptor.create(LEFTP246);
					adaptor.addChild(root_0, LEFTP246_tree);

					idToken=(Token)match(input,ID,FOLLOW_ID_in_built_in_function4793); 
					idToken_tree = (Object)adaptor.create(idToken);
					adaptor.addChild(root_0, idToken_tree);

					RIGHTP247=(Token)match(input,RIGHTP,FOLLOW_RIGHTP_in_built_in_function4795); 
					RIGHTP247_tree = (Object)adaptor.create(RIGHTP247);
					adaptor.addChild(root_0, RIGHTP247_tree);


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    try {
					        if(pr != null) {
					            retval.element = this.interp.product((idToken!=null?idToken.getText():null));
					        } else {
					            retval.element = this.interp.permute((idToken!=null?idToken.getText():null));
					        }
					    } catch(Exception ee) {
					        printError(ee.getLocalizedMessage());
					    }
					}	
						
					}
					break;

			}
			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "built_in_function"


	public static class stand_alone_function_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "stand_alone_function"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2679:1: stand_alone_function[boolean defer] : ( ( SAVE_LC | SAVE_UC | STORE_LC | STORE_UC | CREATE_LC | CREATE_UC ) LEFTP e= expr[defer] RIGHTP | ( EXIT_LC | EXIT_UC ) ( LEFTP p= toPrint[defer] RIGHTP )? );
	public final EugeneParser.stand_alone_function_return stand_alone_function(boolean defer) throws RecognitionException {
		EugeneParser.stand_alone_function_return retval = new EugeneParser.stand_alone_function_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token set248=null;
		Token LEFTP249=null;
		Token RIGHTP250=null;
		Token set251=null;
		Token LEFTP252=null;
		Token RIGHTP253=null;
		ParserRuleReturnScope e =null;
		ParserRuleReturnScope p =null;

		Object set248_tree=null;
		Object LEFTP249_tree=null;
		Object RIGHTP250_tree=null;
		Object set251_tree=null;
		Object LEFTP252_tree=null;
		Object RIGHTP253_tree=null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2680:2: ( ( SAVE_LC | SAVE_UC | STORE_LC | STORE_UC | CREATE_LC | CREATE_UC ) LEFTP e= expr[defer] RIGHTP | ( EXIT_LC | EXIT_UC ) ( LEFTP p= toPrint[defer] RIGHTP )? )
			int alt93=2;
			int LA93_0 = input.LA(1);
			if ( ((LA93_0 >= CREATE_LC && LA93_0 <= CREATE_UC)||(LA93_0 >= SAVE_LC && LA93_0 <= SAVE_UC)||(LA93_0 >= STORE_LC && LA93_0 <= STORE_UC)) ) {
				alt93=1;
			}
			else if ( ((LA93_0 >= EXIT_LC && LA93_0 <= EXIT_UC)) ) {
				alt93=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 93, 0, input);
				throw nvae;
			}

			switch (alt93) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2680:4: ( SAVE_LC | SAVE_UC | STORE_LC | STORE_UC | CREATE_LC | CREATE_UC ) LEFTP e= expr[defer] RIGHTP
					{
					root_0 = (Object)adaptor.nil();


					set248=input.LT(1);
					if ( (input.LA(1) >= CREATE_LC && input.LA(1) <= CREATE_UC)||(input.LA(1) >= SAVE_LC && input.LA(1) <= SAVE_UC)||(input.LA(1) >= STORE_LC && input.LA(1) <= STORE_UC) ) {
						input.consume();
						adaptor.addChild(root_0, (Object)adaptor.create(set248));
						state.errorRecovery=false;
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						throw mse;
					}
					LEFTP249=(Token)match(input,LEFTP,FOLLOW_LEFTP_in_stand_alone_function4826); 
					LEFTP249_tree = (Object)adaptor.create(LEFTP249);
					adaptor.addChild(root_0, LEFTP249_tree);

					pushFollow(FOLLOW_expr_in_stand_alone_function4830);
					e=expr(defer);
					state._fsp--;

					adaptor.addChild(root_0, e.getTree());

					RIGHTP250=(Token)match(input,RIGHTP,FOLLOW_RIGHTP_in_stand_alone_function4833); 
					RIGHTP250_tree = (Object)adaptor.create(RIGHTP250);
					adaptor.addChild(root_0, RIGHTP250_tree);


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    try  {
					        if(null != (e!=null?((EugeneParser.expr_return)e).element:null)) {
					            this.interp.storeIntoLibrary((e!=null?((EugeneParser.expr_return)e).element:null));
					        } else {
					            throw new EugeneException("Cannot store " + (e!=null?input.toString(e.start,e.stop):null) + " into the library!");
					        }
					    } catch(EugeneException ee) {
					        printError(ee.getLocalizedMessage());
					    }
					}	
						
					}
					break;
				case 2 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2693:4: ( EXIT_LC | EXIT_UC ) ( LEFTP p= toPrint[defer] RIGHTP )?
					{
					root_0 = (Object)adaptor.nil();


					set251=input.LT(1);
					if ( (input.LA(1) >= EXIT_LC && input.LA(1) <= EXIT_UC) ) {
						input.consume();
						adaptor.addChild(root_0, (Object)adaptor.create(set251));
						state.errorRecovery=false;
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						throw mse;
					}
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2693:24: ( LEFTP p= toPrint[defer] RIGHTP )?
					int alt92=2;
					int LA92_0 = input.LA(1);
					if ( (LA92_0==LEFTP) ) {
						alt92=1;
					}
					switch (alt92) {
						case 1 :
							// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2693:25: LEFTP p= toPrint[defer] RIGHTP
							{
							LEFTP252=(Token)match(input,LEFTP,FOLLOW_LEFTP_in_stand_alone_function4849); 
							LEFTP252_tree = (Object)adaptor.create(LEFTP252);
							adaptor.addChild(root_0, LEFTP252_tree);

							pushFollow(FOLLOW_toPrint_in_stand_alone_function4853);
							p=toPrint(defer);
							state._fsp--;

							adaptor.addChild(root_0, p.getTree());

							RIGHTP253=(Token)match(input,RIGHTP,FOLLOW_RIGHTP_in_stand_alone_function4856); 
							RIGHTP253_tree = (Object)adaptor.create(RIGHTP253);
							adaptor.addChild(root_0, RIGHTP253_tree);

							}
							break;

					}


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    if(null == p) {
					        printError("exiting...");
					    } else {
					        printError((p!=null?((EugeneParser.toPrint_return)p).sb:null));
					    }
					}
						
					}
					break;

			}
			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "stand_alone_function"


	public static class range_return extends ParserRuleReturnScope {
		public Variable sor;
		public Variable eor;
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "range"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2704:1: range[boolean defer] returns [Variable sor, Variable eor] : s= expr[defer] COMMA e= expr[defer] ;
	public final EugeneParser.range_return range(boolean defer) throws RecognitionException {
		EugeneParser.range_return retval = new EugeneParser.range_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token COMMA254=null;
		ParserRuleReturnScope s =null;
		ParserRuleReturnScope e =null;

		Object COMMA254_tree=null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2706:2: (s= expr[defer] COMMA e= expr[defer] )
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2706:4: s= expr[defer] COMMA e= expr[defer]
			{
			root_0 = (Object)adaptor.nil();


			pushFollow(FOLLOW_expr_in_range4880);
			s=expr(defer);
			state._fsp--;

			adaptor.addChild(root_0, s.getTree());

			COMMA254=(Token)match(input,COMMA,FOLLOW_COMMA_in_range4883); 
			COMMA254_tree = (Object)adaptor.create(COMMA254);
			adaptor.addChild(root_0, COMMA254_tree);

			pushFollow(FOLLOW_expr_in_range4887);
			e=expr(defer);
			state._fsp--;

			adaptor.addChild(root_0, e.getTree());


			if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {

			    if(null != (s!=null?((EugeneParser.expr_return)s).p:null)) {
			        if(!EugeneConstants.NUM.equals((s!=null?((EugeneParser.expr_return)s).p:null).getType())) {
			            printError("Invalid start of range!");
			        }
			        if((s!=null?((EugeneParser.expr_return)s).p:null).getNum() % 1 != 0) {
			            printError("The start of the range is not an integer!");
			        }    
			        retval.sor = (s!=null?((EugeneParser.expr_return)s).p:null);
			    }  
			   
			    if(null != (e!=null?((EugeneParser.expr_return)e).p:null)) {
			        if(!EugeneConstants.NUM.equals((e!=null?((EugeneParser.expr_return)e).p:null).getType())) {
			            printError("Invalid end of range!");
			        }
			        if((e!=null?((EugeneParser.expr_return)e).p:null).getNum() % 1 != 0) {
			            printError("The end of the range is not an integer!");
			        }    
			        
			        retval.eor = (e!=null?((EugeneParser.expr_return)e).p:null);
			    }
			    
			    if(retval.sor.num > retval.eor.num) {
			        printError("Invalid range!");
			    }
			}	
				
			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "range"


	public static class object_access_return extends ParserRuleReturnScope {
		public NamedElement child;
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "object_access"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2759:1: object_access[boolean defer, NamedElement parent] returns [NamedElement child] : (| ( DOT (id= ID | ( SIZE_UC | SIZE_LC ) ( LEFTP RIGHTP )? ) | LEFTSBR (exp= expr[defer] ) RIGHTSBR ) o= object_access[defer, $child] );
	public final EugeneParser.object_access_return object_access(boolean defer, NamedElement parent) throws RecognitionException {
		EugeneParser.object_access_return retval = new EugeneParser.object_access_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token id=null;
		Token DOT255=null;
		Token set256=null;
		Token LEFTP257=null;
		Token RIGHTP258=null;
		Token LEFTSBR259=null;
		Token RIGHTSBR260=null;
		ParserRuleReturnScope exp =null;
		ParserRuleReturnScope o =null;

		Object id_tree=null;
		Object DOT255_tree=null;
		Object set256_tree=null;
		Object LEFTP257_tree=null;
		Object RIGHTP258_tree=null;
		Object LEFTSBR259_tree=null;
		Object RIGHTSBR260_tree=null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2761:2: (| ( DOT (id= ID | ( SIZE_UC | SIZE_LC ) ( LEFTP RIGHTP )? ) | LEFTSBR (exp= expr[defer] ) RIGHTSBR ) o= object_access[defer, $child] )
			int alt97=2;
			int LA97_0 = input.LA(1);
			if ( (LA97_0==EOF||LA97_0==AMP||LA97_0==COMMA||LA97_0==DIV||LA97_0==EQUALS||LA97_0==GEQUAL||LA97_0==GTHAN||LA97_0==LC_AND||LA97_0==LC_OR||LA97_0==LEQUAL||(LA97_0 >= LOG_AND && LA97_0 <= MINUS)||(LA97_0 >= MULT && LA97_0 <= NEQUAL)||(LA97_0 >= PIPE && LA97_0 <= PLUS)||(LA97_0 >= RIGHTCUR && LA97_0 <= RIGHTSBR)||LA97_0==SEMIC||LA97_0==UC_AND||LA97_0==UC_OR||LA97_0==141||(LA97_0 >= 143 && LA97_0 <= 144)||LA97_0==147||(LA97_0 >= 150 && LA97_0 <= 151)||LA97_0==153||(LA97_0 >= 166 && LA97_0 <= 167)||LA97_0==180||(LA97_0 >= 182 && LA97_0 <= 183)||LA97_0==186||(LA97_0 >= 189 && LA97_0 <= 190)||LA97_0==192||(LA97_0 >= 205 && LA97_0 <= 206)) ) {
				alt97=1;
			}
			else if ( (LA97_0==DOT||LA97_0==LEFTSBR) ) {
				alt97=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 97, 0, input);
				throw nvae;
			}

			switch (alt97) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2762:2: 
					{
					root_0 = (Object)adaptor.nil();



					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    retval.child = parent;
					}	
						
					}
					break;
				case 2 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2767:4: ( DOT (id= ID | ( SIZE_UC | SIZE_LC ) ( LEFTP RIGHTP )? ) | LEFTSBR (exp= expr[defer] ) RIGHTSBR ) o= object_access[defer, $child]
					{
					root_0 = (Object)adaptor.nil();


					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2767:4: ( DOT (id= ID | ( SIZE_UC | SIZE_LC ) ( LEFTP RIGHTP )? ) | LEFTSBR (exp= expr[defer] ) RIGHTSBR )
					int alt96=2;
					int LA96_0 = input.LA(1);
					if ( (LA96_0==DOT) ) {
						alt96=1;
					}
					else if ( (LA96_0==LEFTSBR) ) {
						alt96=2;
					}

					else {
						NoViableAltException nvae =
							new NoViableAltException("", 96, 0, input);
						throw nvae;
					}

					switch (alt96) {
						case 1 :
							// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2767:5: DOT (id= ID | ( SIZE_UC | SIZE_LC ) ( LEFTP RIGHTP )? )
							{
							DOT255=(Token)match(input,DOT,FOLLOW_DOT_in_object_access4923); 
							DOT255_tree = (Object)adaptor.create(DOT255);
							adaptor.addChild(root_0, DOT255_tree);

							// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2767:9: (id= ID | ( SIZE_UC | SIZE_LC ) ( LEFTP RIGHTP )? )
							int alt95=2;
							int LA95_0 = input.LA(1);
							if ( (LA95_0==ID) ) {
								alt95=1;
							}
							else if ( ((LA95_0 >= SIZE_LC && LA95_0 <= SIZE_UC)) ) {
								alt95=2;
							}

							else {
								NoViableAltException nvae =
									new NoViableAltException("", 95, 0, input);
								throw nvae;
							}

							switch (alt95) {
								case 1 :
									// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2767:10: id= ID
									{
									id=(Token)match(input,ID,FOLLOW_ID_in_object_access4928); 
									id_tree = (Object)adaptor.create(id);
									adaptor.addChild(root_0, id_tree);


									if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
									    try {
									    
									        retval.child = parent.getElement((id!=null?id.getText():null));

									        if(null == retval.child) {
									            throw new EugeneException(parent.getName() + " does not contain " + (id!=null?id.getText():null));
									        }

									    } catch(EugeneException ee) {
									        printError(ee.getLocalizedMessage());
									    }
									}	
										
									}
									break;
								case 2 :
									// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2781:6: ( SIZE_UC | SIZE_LC ) ( LEFTP RIGHTP )?
									{
									set256=input.LT(1);
									if ( (input.LA(1) >= SIZE_LC && input.LA(1) <= SIZE_UC) ) {
										input.consume();
										adaptor.addChild(root_0, (Object)adaptor.create(set256));
										state.errorRecovery=false;
									}
									else {
										MismatchedSetException mse = new MismatchedSetException(null,input);
										throw mse;
									}
									// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2781:24: ( LEFTP RIGHTP )?
									int alt94=2;
									int LA94_0 = input.LA(1);
									if ( (LA94_0==LEFTP) ) {
										alt94=1;
									}
									switch (alt94) {
										case 1 :
											// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2781:25: LEFTP RIGHTP
											{
											LEFTP257=(Token)match(input,LEFTP,FOLLOW_LEFTP_in_object_access4941); 
											LEFTP257_tree = (Object)adaptor.create(LEFTP257);
											adaptor.addChild(root_0, LEFTP257_tree);

											RIGHTP258=(Token)match(input,RIGHTP,FOLLOW_RIGHTP_in_object_access4943); 
											RIGHTP258_tree = (Object)adaptor.create(RIGHTP258);
											adaptor.addChild(root_0, RIGHTP258_tree);

											}
											break;

									}


									if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
									    try {
									        retval.child = this.interp.getSizeOf(parent);
									    } catch(EugeneException ee) {
									        printError(ee.getLocalizedMessage());
									    }
									}	
										
									}
									break;

							}

							}
							break;
						case 2 :
							// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2790:4: LEFTSBR (exp= expr[defer] ) RIGHTSBR
							{
							LEFTSBR259=(Token)match(input,LEFTSBR,FOLLOW_LEFTSBR_in_object_access4953); 
							LEFTSBR259_tree = (Object)adaptor.create(LEFTSBR259);
							adaptor.addChild(root_0, LEFTSBR259_tree);

							// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2790:12: (exp= expr[defer] )
							// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2790:13: exp= expr[defer]
							{
							pushFollow(FOLLOW_expr_in_object_access4958);
							exp=expr(defer);
							state._fsp--;

							adaptor.addChild(root_0, exp.getTree());

							}

							RIGHTSBR260=(Token)match(input,RIGHTSBR,FOLLOW_RIGHTSBR_in_object_access4962); 
							RIGHTSBR260_tree = (Object)adaptor.create(RIGHTSBR260);
							adaptor.addChild(root_0, RIGHTSBR260_tree);


							if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
							    try {
							        if(null != (exp!=null?((EugeneParser.expr_return)exp).p:null) && EugeneConstants.NUM.equals((exp!=null?((EugeneParser.expr_return)exp).p:null).getType())) {
							        
							            if((exp!=null?((EugeneParser.expr_return)exp).p:null).getNum() % 1 != 0 && (exp!=null?((EugeneParser.expr_return)exp).p:null).getNum() < 0) {
							                throw new EugeneException("Invalid index " + (exp!=null?((EugeneParser.expr_return)exp).p:null) + "!");
							            }
							            
							            retval.child = parent.getElement((int)((exp!=null?((EugeneParser.expr_return)exp).p:null).getNum()));
							            
							            if(null == retval.child) {
							                throw new EugeneException(parent.getName() + " does not contain " + (id!=null?id.getText():null));
							            }
							            
							        } else {
							            throw new EugeneException("Invalid index " + (exp!=null?((EugeneParser.expr_return)exp).p:null) + "!");
							        }
							    } catch(EugeneException ee) {
							        printError(ee.getLocalizedMessage());
							    }
							}	
								
							}
							break;

					}

					pushFollow(FOLLOW_object_access_in_object_access4969);
					o=object_access(defer, retval.child);
					state._fsp--;

					adaptor.addChild(root_0, o.getTree());


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    retval.child = (o!=null?((EugeneParser.object_access_return)o).child:null);
					}	
						
					}
					break;

			}
			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "object_access"


	public static class dynamic_naming_return extends ParserRuleReturnScope {
		public String name;
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "dynamic_naming"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2822:1: dynamic_naming[boolean defer] returns [String name] : (i= ID | DOLLAR LEFTCUR e= expr[defer] RIGHTCUR );
	public final EugeneParser.dynamic_naming_return dynamic_naming(boolean defer) throws RecognitionException {
		EugeneParser.dynamic_naming_return retval = new EugeneParser.dynamic_naming_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token i=null;
		Token DOLLAR261=null;
		Token LEFTCUR262=null;
		Token RIGHTCUR263=null;
		ParserRuleReturnScope e =null;

		Object i_tree=null;
		Object DOLLAR261_tree=null;
		Object LEFTCUR262_tree=null;
		Object RIGHTCUR263_tree=null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2824:2: (i= ID | DOLLAR LEFTCUR e= expr[defer] RIGHTCUR )
			int alt98=2;
			int LA98_0 = input.LA(1);
			if ( (LA98_0==ID) ) {
				alt98=1;
			}
			else if ( (LA98_0==DOLLAR) ) {
				alt98=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 98, 0, input);
				throw nvae;
			}

			switch (alt98) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2824:4: i= ID
					{
					root_0 = (Object)adaptor.nil();


					i=(Token)match(input,ID,FOLLOW_ID_in_dynamic_naming4994); 
					i_tree = (Object)adaptor.create(i);
					adaptor.addChild(root_0, i_tree);


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    retval.name = (i!=null?i.getText():null);
					}	
						
					}
					break;
				case 2 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2829:4: DOLLAR LEFTCUR e= expr[defer] RIGHTCUR
					{
					root_0 = (Object)adaptor.nil();


					DOLLAR261=(Token)match(input,DOLLAR,FOLLOW_DOLLAR_in_dynamic_naming5001); 
					DOLLAR261_tree = (Object)adaptor.create(DOLLAR261);
					adaptor.addChild(root_0, DOLLAR261_tree);

					LEFTCUR262=(Token)match(input,LEFTCUR,FOLLOW_LEFTCUR_in_dynamic_naming5003); 
					LEFTCUR262_tree = (Object)adaptor.create(LEFTCUR262);
					adaptor.addChild(root_0, LEFTCUR262_tree);

					pushFollow(FOLLOW_expr_in_dynamic_naming5007);
					e=expr(defer);
					state._fsp--;

					adaptor.addChild(root_0, e.getTree());

					RIGHTCUR263=(Token)match(input,RIGHTCUR,FOLLOW_RIGHTCUR_in_dynamic_naming5010); 
					RIGHTCUR263_tree = (Object)adaptor.create(RIGHTCUR263);
					adaptor.addChild(root_0, RIGHTCUR263_tree);


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    if((e!=null?((EugeneParser.expr_return)e).p:null) == null) {
					        printError("Invalid name!");
					    } else if(!EugeneConstants.TXT.equals(((e!=null?((EugeneParser.expr_return)e).p:null)).getType())) {
					        printError("The name must be of type txt!");
					    }
					    
					    retval.name = ((e!=null?((EugeneParser.expr_return)e).p:null)).getTxt();
					}	
						
					}
					break;

			}
			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "dynamic_naming"


	public static class dataExchange_return extends ParserRuleReturnScope {
		public NamedElement e;
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "dataExchange"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2846:1: dataExchange[boolean defer] returns [NamedElement e] : (s= sbolStatement[defer] |i= importStatement[defer] |g= genbankStatement[defer] |r= registryStatement[defer] );
	public final EugeneParser.dataExchange_return dataExchange(boolean defer) throws RecognitionException {
		EugeneParser.dataExchange_return retval = new EugeneParser.dataExchange_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		ParserRuleReturnScope s =null;
		ParserRuleReturnScope i =null;
		ParserRuleReturnScope g =null;
		ParserRuleReturnScope r =null;


		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2848:2: (s= sbolStatement[defer] |i= importStatement[defer] |g= genbankStatement[defer] |r= registryStatement[defer] )
			int alt99=4;
			switch ( input.LA(1) ) {
			case SBOL:
				{
				alt99=1;
				}
				break;
			case IMPORT_LC:
			case IMPORT_UC:
				{
				alt99=2;
				}
				break;
			case GENBANK:
				{
				alt99=3;
				}
				break;
			case REGISTRY:
				{
				alt99=4;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 99, 0, input);
				throw nvae;
			}
			switch (alt99) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2848:4: s= sbolStatement[defer]
					{
					root_0 = (Object)adaptor.nil();


					pushFollow(FOLLOW_sbolStatement_in_dataExchange5035);
					s=sbolStatement(defer);
					state._fsp--;

					adaptor.addChild(root_0, s.getTree());


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    retval.e = (s!=null?((EugeneParser.sbolStatement_return)s).e:null);
					}	
						
					}
					break;
				case 2 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2853:4: i= importStatement[defer]
					{
					root_0 = (Object)adaptor.nil();


					pushFollow(FOLLOW_importStatement_in_dataExchange5045);
					i=importStatement(defer);
					state._fsp--;

					adaptor.addChild(root_0, i.getTree());


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    retval.e = (i!=null?((EugeneParser.importStatement_return)i).e:null);
					}	
						
					}
					break;
				case 3 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2858:4: g= genbankStatement[defer]
					{
					root_0 = (Object)adaptor.nil();


					pushFollow(FOLLOW_genbankStatement_in_dataExchange5055);
					g=genbankStatement(defer);
					state._fsp--;

					adaptor.addChild(root_0, g.getTree());


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    retval.e = (g!=null?((EugeneParser.genbankStatement_return)g).e:null);
					}	
						
						
					}
					break;
				case 4 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2864:4: r= registryStatement[defer]
					{
					root_0 = (Object)adaptor.nil();


					pushFollow(FOLLOW_registryStatement_in_dataExchange5065);
					r=registryStatement(defer);
					state._fsp--;

					adaptor.addChild(root_0, r.getTree());


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    retval.e = (r!=null?((EugeneParser.registryStatement_return)r).e:null);
					}		
						
					}
					break;

			}
			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "dataExchange"


	public static class includeStatement_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "includeStatement"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2872:1: includeStatement[boolean defer] : ( HASHMARK )? ( INCLUDE_LC | INCLUDE_UC ) file= STRING ;
	public final EugeneParser.includeStatement_return includeStatement(boolean defer) throws RecognitionException {
		EugeneParser.includeStatement_return retval = new EugeneParser.includeStatement_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token file=null;
		Token HASHMARK264=null;
		Token set265=null;

		Object file_tree=null;
		Object HASHMARK264_tree=null;
		Object set265_tree=null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2873:2: ( ( HASHMARK )? ( INCLUDE_LC | INCLUDE_UC ) file= STRING )
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2873:4: ( HASHMARK )? ( INCLUDE_LC | INCLUDE_UC ) file= STRING
			{
			root_0 = (Object)adaptor.nil();


			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2873:4: ( HASHMARK )?
			int alt100=2;
			int LA100_0 = input.LA(1);
			if ( (LA100_0==HASHMARK) ) {
				alt100=1;
			}
			switch (alt100) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2873:5: HASHMARK
					{
					HASHMARK264=(Token)match(input,HASHMARK,FOLLOW_HASHMARK_in_includeStatement5084); 
					HASHMARK264_tree = (Object)adaptor.create(HASHMARK264);
					adaptor.addChild(root_0, HASHMARK264_tree);

					}
					break;

			}

			set265=input.LT(1);
			if ( (input.LA(1) >= INCLUDE_LC && input.LA(1) <= INCLUDE_UC) ) {
				input.consume();
				adaptor.addChild(root_0, (Object)adaptor.create(set265));
				state.errorRecovery=false;
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				throw mse;
			}
			file=(Token)match(input,STRING,FOLLOW_STRING_in_includeStatement5096); 
			file_tree = (Object)adaptor.create(file);
			adaptor.addChild(root_0, file_tree);


			if(this.PARSING_PHASE == ParsingPhase.PRE_PROCESSING) {
			    try {
			        this.interp.includeFile((file!=null?file.getText():null), ParsingPhase.PRE_PROCESSING);
			    } catch(EugeneException ee) {
			        printError(ee.getLocalizedMessage());
			    }
			} else if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
			    try {
			        this.interp.includeFile((file!=null?file.getText():null), ParsingPhase.INTERPRETING);
			    } catch(EugeneException ee) {
			        printError(ee.getLocalizedMessage());
			    }
			}
				
			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "includeStatement"


	public static class importStatement_return extends ParserRuleReturnScope {
		public NamedElement e;
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "importStatement"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2891:1: importStatement[boolean defer] returns [NamedElement e] : ( IMPORT_LC | IMPORT_UC ) LEFTP file= STRING RIGHTP ;
	public final EugeneParser.importStatement_return importStatement(boolean defer) throws RecognitionException {
		EugeneParser.importStatement_return retval = new EugeneParser.importStatement_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token file=null;
		Token set266=null;
		Token LEFTP267=null;
		Token RIGHTP268=null;

		Object file_tree=null;
		Object set266_tree=null;
		Object LEFTP267_tree=null;
		Object RIGHTP268_tree=null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2893:2: ( ( IMPORT_LC | IMPORT_UC ) LEFTP file= STRING RIGHTP )
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2893:4: ( IMPORT_LC | IMPORT_UC ) LEFTP file= STRING RIGHTP
			{
			root_0 = (Object)adaptor.nil();


			set266=input.LT(1);
			if ( (input.LA(1) >= IMPORT_LC && input.LA(1) <= IMPORT_UC) ) {
				input.consume();
				adaptor.addChild(root_0, (Object)adaptor.create(set266));
				state.errorRecovery=false;
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				throw mse;
			}
			LEFTP267=(Token)match(input,LEFTP,FOLLOW_LEFTP_in_importStatement5123); 
			LEFTP267_tree = (Object)adaptor.create(LEFTP267);
			adaptor.addChild(root_0, LEFTP267_tree);

			file=(Token)match(input,STRING,FOLLOW_STRING_in_importStatement5127); 
			file_tree = (Object)adaptor.create(file);
			adaptor.addChild(root_0, file_tree);


			if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
			    try {
			        retval.e = this.interp.importFile((file!=null?file.getText():null));
			    } catch(EugeneException ee) {
			        printError(ee.getLocalizedMessage());
			    }
			}
				
			RIGHTP268=(Token)match(input,RIGHTP,FOLLOW_RIGHTP_in_importStatement5131); 
			RIGHTP268_tree = (Object)adaptor.create(RIGHTP268);
			adaptor.addChild(root_0, RIGHTP268_tree);

			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "importStatement"


	public static class sbolStatement_return extends ParserRuleReturnScope {
		public NamedElement e;
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "sbolStatement"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2905:1: sbolStatement[boolean defer] returns [NamedElement e] : SBOL DOT ( sbolExportStatement[defer] |i= sbolImportStatement[defer] | sbolVisualStatement[defer] ) ;
	public final EugeneParser.sbolStatement_return sbolStatement(boolean defer) throws RecognitionException {
		EugeneParser.sbolStatement_return retval = new EugeneParser.sbolStatement_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token SBOL269=null;
		Token DOT270=null;
		ParserRuleReturnScope i =null;
		ParserRuleReturnScope sbolExportStatement271 =null;
		ParserRuleReturnScope sbolVisualStatement272 =null;

		Object SBOL269_tree=null;
		Object DOT270_tree=null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2907:2: ( SBOL DOT ( sbolExportStatement[defer] |i= sbolImportStatement[defer] | sbolVisualStatement[defer] ) )
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2907:4: SBOL DOT ( sbolExportStatement[defer] |i= sbolImportStatement[defer] | sbolVisualStatement[defer] )
			{
			root_0 = (Object)adaptor.nil();


			SBOL269=(Token)match(input,SBOL,FOLLOW_SBOL_in_sbolStatement5153); 
			SBOL269_tree = (Object)adaptor.create(SBOL269);
			adaptor.addChild(root_0, SBOL269_tree);

			DOT270=(Token)match(input,DOT,FOLLOW_DOT_in_sbolStatement5155); 
			DOT270_tree = (Object)adaptor.create(DOT270);
			adaptor.addChild(root_0, DOT270_tree);

			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2907:13: ( sbolExportStatement[defer] |i= sbolImportStatement[defer] | sbolVisualStatement[defer] )
			int alt101=3;
			switch ( input.LA(1) ) {
			case EXPORT_LC:
			case EXPORT_UC:
				{
				alt101=1;
				}
				break;
			case IMPORT_LC:
			case IMPORT_UC:
				{
				alt101=2;
				}
				break;
			case VISUALIZE_LC:
			case VISUALIZE_UC:
				{
				alt101=3;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 101, 0, input);
				throw nvae;
			}
			switch (alt101) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2907:14: sbolExportStatement[defer]
					{
					pushFollow(FOLLOW_sbolExportStatement_in_sbolStatement5158);
					sbolExportStatement271=sbolExportStatement(defer);
					state._fsp--;

					adaptor.addChild(root_0, sbolExportStatement271.getTree());

					}
					break;
				case 2 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2907:43: i= sbolImportStatement[defer]
					{
					pushFollow(FOLLOW_sbolImportStatement_in_sbolStatement5165);
					i=sbolImportStatement(defer);
					state._fsp--;

					adaptor.addChild(root_0, i.getTree());


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    retval.e = (i!=null?((EugeneParser.sbolImportStatement_return)i).e:null);
					}	
						
					}
					break;
				case 3 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2911:7: sbolVisualStatement[defer]
					{
					pushFollow(FOLLOW_sbolVisualStatement_in_sbolStatement5173);
					sbolVisualStatement272=sbolVisualStatement(defer);
					state._fsp--;

					adaptor.addChild(root_0, sbolVisualStatement272.getTree());


						
						
					}
					break;

			}

			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "sbolStatement"


	public static class sbolExportStatement_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "sbolExportStatement"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2916:1: sbolExportStatement[boolean defer] : ( EXPORT_LC | EXPORT_UC ) LEFTP idToken= ID COMMA filenameToken= STRING RIGHTP ;
	public final EugeneParser.sbolExportStatement_return sbolExportStatement(boolean defer) throws RecognitionException {
		EugeneParser.sbolExportStatement_return retval = new EugeneParser.sbolExportStatement_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token idToken=null;
		Token filenameToken=null;
		Token set273=null;
		Token LEFTP274=null;
		Token COMMA275=null;
		Token RIGHTP276=null;

		Object idToken_tree=null;
		Object filenameToken_tree=null;
		Object set273_tree=null;
		Object LEFTP274_tree=null;
		Object COMMA275_tree=null;
		Object RIGHTP276_tree=null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2917:2: ( ( EXPORT_LC | EXPORT_UC ) LEFTP idToken= ID COMMA filenameToken= STRING RIGHTP )
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2917:4: ( EXPORT_LC | EXPORT_UC ) LEFTP idToken= ID COMMA filenameToken= STRING RIGHTP
			{
			root_0 = (Object)adaptor.nil();


			set273=input.LT(1);
			if ( (input.LA(1) >= EXPORT_LC && input.LA(1) <= EXPORT_UC) ) {
				input.consume();
				adaptor.addChild(root_0, (Object)adaptor.create(set273));
				state.errorRecovery=false;
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				throw mse;
			}
			LEFTP274=(Token)match(input,LEFTP,FOLLOW_LEFTP_in_sbolExportStatement5196); 
			LEFTP274_tree = (Object)adaptor.create(LEFTP274);
			adaptor.addChild(root_0, LEFTP274_tree);

			idToken=(Token)match(input,ID,FOLLOW_ID_in_sbolExportStatement5200); 
			idToken_tree = (Object)adaptor.create(idToken);
			adaptor.addChild(root_0, idToken_tree);

			COMMA275=(Token)match(input,COMMA,FOLLOW_COMMA_in_sbolExportStatement5202); 
			COMMA275_tree = (Object)adaptor.create(COMMA275);
			adaptor.addChild(root_0, COMMA275_tree);

			filenameToken=(Token)match(input,STRING,FOLLOW_STRING_in_sbolExportStatement5206); 
			filenameToken_tree = (Object)adaptor.create(filenameToken);
			adaptor.addChild(root_0, filenameToken_tree);

			RIGHTP276=(Token)match(input,RIGHTP,FOLLOW_RIGHTP_in_sbolExportStatement5208); 
			RIGHTP276_tree = (Object)adaptor.create(RIGHTP276);
			adaptor.addChild(root_0, RIGHTP276_tree);


			if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
			    try {
			        this.interp.exportToSBOL(
			            (idToken!=null?idToken.getText():null), 
			            (filenameToken!=null?filenameToken.getText():null).substring(1, (filenameToken!=null?filenameToken.getText():null).length()-1));
			    } catch(EugeneException ee) {
			        printError(ee.getMessage());
			    }
			}	
				
			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "sbolExportStatement"


	public static class sbolImportStatement_return extends ParserRuleReturnScope {
		public NamedElement e;
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "sbolImportStatement"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2930:1: sbolImportStatement[boolean defer] returns [NamedElement e] : ( IMPORT_LC | IMPORT_UC ) LEFTP fileToken= STRING RIGHTP ;
	public final EugeneParser.sbolImportStatement_return sbolImportStatement(boolean defer) throws RecognitionException {
		EugeneParser.sbolImportStatement_return retval = new EugeneParser.sbolImportStatement_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token fileToken=null;
		Token set277=null;
		Token LEFTP278=null;
		Token RIGHTP279=null;

		Object fileToken_tree=null;
		Object set277_tree=null;
		Object LEFTP278_tree=null;
		Object RIGHTP279_tree=null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2932:2: ( ( IMPORT_LC | IMPORT_UC ) LEFTP fileToken= STRING RIGHTP )
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2932:4: ( IMPORT_LC | IMPORT_UC ) LEFTP fileToken= STRING RIGHTP
			{
			root_0 = (Object)adaptor.nil();


			set277=input.LT(1);
			if ( (input.LA(1) >= IMPORT_LC && input.LA(1) <= IMPORT_UC) ) {
				input.consume();
				adaptor.addChild(root_0, (Object)adaptor.create(set277));
				state.errorRecovery=false;
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				throw mse;
			}
			LEFTP278=(Token)match(input,LEFTP,FOLLOW_LEFTP_in_sbolImportStatement5237); 
			LEFTP278_tree = (Object)adaptor.create(LEFTP278);
			adaptor.addChild(root_0, LEFTP278_tree);

			fileToken=(Token)match(input,STRING,FOLLOW_STRING_in_sbolImportStatement5241); 
			fileToken_tree = (Object)adaptor.create(fileToken);
			adaptor.addChild(root_0, fileToken_tree);

			RIGHTP279=(Token)match(input,RIGHTP,FOLLOW_RIGHTP_in_sbolImportStatement5243); 
			RIGHTP279_tree = (Object)adaptor.create(RIGHTP279);
			adaptor.addChild(root_0, RIGHTP279_tree);


			if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
			    try {
			        retval.e = this.interp.importSBOL((fileToken!=null?fileToken.getText():null));
			    } catch(EugeneException ee) {
			        printError(ee.getMessage());
			    }
			}	
				
			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "sbolImportStatement"


	public static class sbolVisualStatement_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "sbolVisualStatement"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2943:1: sbolVisualStatement[boolean defer] : ( VISUALIZE_LC | VISUALIZE_UC ) LEFTP idToken= ID RIGHTP ;
	public final EugeneParser.sbolVisualStatement_return sbolVisualStatement(boolean defer) throws RecognitionException {
		EugeneParser.sbolVisualStatement_return retval = new EugeneParser.sbolVisualStatement_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token idToken=null;
		Token set280=null;
		Token LEFTP281=null;
		Token RIGHTP282=null;

		Object idToken_tree=null;
		Object set280_tree=null;
		Object LEFTP281_tree=null;
		Object RIGHTP282_tree=null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2944:2: ( ( VISUALIZE_LC | VISUALIZE_UC ) LEFTP idToken= ID RIGHTP )
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2944:4: ( VISUALIZE_LC | VISUALIZE_UC ) LEFTP idToken= ID RIGHTP
			{
			root_0 = (Object)adaptor.nil();


			set280=input.LT(1);
			if ( (input.LA(1) >= VISUALIZE_LC && input.LA(1) <= VISUALIZE_UC) ) {
				input.consume();
				adaptor.addChild(root_0, (Object)adaptor.create(set280));
				state.errorRecovery=false;
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				throw mse;
			}
			LEFTP281=(Token)match(input,LEFTP,FOLLOW_LEFTP_in_sbolVisualStatement5265); 
			LEFTP281_tree = (Object)adaptor.create(LEFTP281);
			adaptor.addChild(root_0, LEFTP281_tree);

			idToken=(Token)match(input,ID,FOLLOW_ID_in_sbolVisualStatement5269); 
			idToken_tree = (Object)adaptor.create(idToken);
			adaptor.addChild(root_0, idToken_tree);

			RIGHTP282=(Token)match(input,RIGHTP,FOLLOW_RIGHTP_in_sbolVisualStatement5271); 
			RIGHTP282_tree = (Object)adaptor.create(RIGHTP282);
			adaptor.addChild(root_0, RIGHTP282_tree);


			if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
			    try {
			        this.interp.visualSBOL((idToken!=null?idToken.getText():null));
			    } catch(EugeneException ee) {
			        printError(ee.getMessage());
			    }
			}		
				
			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "sbolVisualStatement"


	public static class genbankStatement_return extends ParserRuleReturnScope {
		public NamedElement e;
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "genbankStatement"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2958:1: genbankStatement[boolean defer] returns [NamedElement e] : GENBANK DOT (i= genbankImportStatement[defer] | genbankExportStatement[defer] ) ;
	public final EugeneParser.genbankStatement_return genbankStatement(boolean defer) throws RecognitionException {
		EugeneParser.genbankStatement_return retval = new EugeneParser.genbankStatement_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token GENBANK283=null;
		Token DOT284=null;
		ParserRuleReturnScope i =null;
		ParserRuleReturnScope genbankExportStatement285 =null;

		Object GENBANK283_tree=null;
		Object DOT284_tree=null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2960:2: ( GENBANK DOT (i= genbankImportStatement[defer] | genbankExportStatement[defer] ) )
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2960:4: GENBANK DOT (i= genbankImportStatement[defer] | genbankExportStatement[defer] )
			{
			root_0 = (Object)adaptor.nil();


			GENBANK283=(Token)match(input,GENBANK,FOLLOW_GENBANK_in_genbankStatement5296); 
			GENBANK283_tree = (Object)adaptor.create(GENBANK283);
			adaptor.addChild(root_0, GENBANK283_tree);

			DOT284=(Token)match(input,DOT,FOLLOW_DOT_in_genbankStatement5298); 
			DOT284_tree = (Object)adaptor.create(DOT284);
			adaptor.addChild(root_0, DOT284_tree);

			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2960:16: (i= genbankImportStatement[defer] | genbankExportStatement[defer] )
			int alt102=2;
			int LA102_0 = input.LA(1);
			if ( ((LA102_0 >= IMPORT_LC && LA102_0 <= IMPORT_UC)) ) {
				alt102=1;
			}
			else if ( ((LA102_0 >= EXPORT_LC && LA102_0 <= EXPORT_UC)) ) {
				alt102=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 102, 0, input);
				throw nvae;
			}

			switch (alt102) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2960:17: i= genbankImportStatement[defer]
					{
					pushFollow(FOLLOW_genbankImportStatement_in_genbankStatement5303);
					i=genbankImportStatement(defer);
					state._fsp--;

					adaptor.addChild(root_0, i.getTree());


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    retval.e = (i!=null?((EugeneParser.genbankImportStatement_return)i).e:null);
					}	
						
					}
					break;
				case 2 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2965:4: genbankExportStatement[defer]
					{
					pushFollow(FOLLOW_genbankExportStatement_in_genbankStatement5311);
					genbankExportStatement285=genbankExportStatement(defer);
					state._fsp--;

					adaptor.addChild(root_0, genbankExportStatement285.getTree());

					}
					break;

			}


			if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
			    retval.e = null;
			}	
				
			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "genbankStatement"


	public static class genbankExportStatement_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "genbankExportStatement"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2972:1: genbankExportStatement[boolean defer] : ( EXPORT_UC | EXPORT_LC ) LEFTP RIGHTP ;
	public final EugeneParser.genbankExportStatement_return genbankExportStatement(boolean defer) throws RecognitionException {
		EugeneParser.genbankExportStatement_return retval = new EugeneParser.genbankExportStatement_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token set286=null;
		Token LEFTP287=null;
		Token RIGHTP288=null;

		Object set286_tree=null;
		Object LEFTP287_tree=null;
		Object RIGHTP288_tree=null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2973:2: ( ( EXPORT_UC | EXPORT_LC ) LEFTP RIGHTP )
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2973:4: ( EXPORT_UC | EXPORT_LC ) LEFTP RIGHTP
			{
			root_0 = (Object)adaptor.nil();


			set286=input.LT(1);
			if ( (input.LA(1) >= EXPORT_LC && input.LA(1) <= EXPORT_UC) ) {
				input.consume();
				adaptor.addChild(root_0, (Object)adaptor.create(set286));
				state.errorRecovery=false;
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				throw mse;
			}
			LEFTP287=(Token)match(input,LEFTP,FOLLOW_LEFTP_in_genbankExportStatement5336); 
			LEFTP287_tree = (Object)adaptor.create(LEFTP287);
			adaptor.addChild(root_0, LEFTP287_tree);

			RIGHTP288=(Token)match(input,RIGHTP,FOLLOW_RIGHTP_in_genbankExportStatement5338); 
			RIGHTP288_tree = (Object)adaptor.create(RIGHTP288);
			adaptor.addChild(root_0, RIGHTP288_tree);

			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "genbankExportStatement"


	public static class genbankImportStatement_return extends ParserRuleReturnScope {
		public NamedElement e;
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "genbankImportStatement"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2976:1: genbankImportStatement[boolean defer] returns [NamedElement e] : ( ( IMPORT_LC | IMPORT_UC ) LEFTP f= STRING RIGHTP | ( IMPORT_LC | IMPORT_UC ) LEFTP typeToken= ID COMMA partToken= STRING RIGHTP );
	public final EugeneParser.genbankImportStatement_return genbankImportStatement(boolean defer) throws RecognitionException {
		EugeneParser.genbankImportStatement_return retval = new EugeneParser.genbankImportStatement_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token f=null;
		Token typeToken=null;
		Token partToken=null;
		Token set289=null;
		Token LEFTP290=null;
		Token RIGHTP291=null;
		Token set292=null;
		Token LEFTP293=null;
		Token COMMA294=null;
		Token RIGHTP295=null;

		Object f_tree=null;
		Object typeToken_tree=null;
		Object partToken_tree=null;
		Object set289_tree=null;
		Object LEFTP290_tree=null;
		Object RIGHTP291_tree=null;
		Object set292_tree=null;
		Object LEFTP293_tree=null;
		Object COMMA294_tree=null;
		Object RIGHTP295_tree=null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2978:2: ( ( IMPORT_LC | IMPORT_UC ) LEFTP f= STRING RIGHTP | ( IMPORT_LC | IMPORT_UC ) LEFTP typeToken= ID COMMA partToken= STRING RIGHTP )
			int alt103=2;
			int LA103_0 = input.LA(1);
			if ( ((LA103_0 >= IMPORT_LC && LA103_0 <= IMPORT_UC)) ) {
				int LA103_1 = input.LA(2);
				if ( (LA103_1==LEFTP) ) {
					int LA103_2 = input.LA(3);
					if ( (LA103_2==STRING) ) {
						alt103=1;
					}
					else if ( (LA103_2==ID) ) {
						alt103=2;
					}

					else {
						int nvaeMark = input.mark();
						try {
							for (int nvaeConsume = 0; nvaeConsume < 3 - 1; nvaeConsume++) {
								input.consume();
							}
							NoViableAltException nvae =
								new NoViableAltException("", 103, 2, input);
							throw nvae;
						} finally {
							input.rewind(nvaeMark);
						}
					}

				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 103, 1, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 103, 0, input);
				throw nvae;
			}

			switch (alt103) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2978:4: ( IMPORT_LC | IMPORT_UC ) LEFTP f= STRING RIGHTP
					{
					root_0 = (Object)adaptor.nil();


					set289=input.LT(1);
					if ( (input.LA(1) >= IMPORT_LC && input.LA(1) <= IMPORT_UC) ) {
						input.consume();
						adaptor.addChild(root_0, (Object)adaptor.create(set289));
						state.errorRecovery=false;
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						throw mse;
					}
					LEFTP290=(Token)match(input,LEFTP,FOLLOW_LEFTP_in_genbankImportStatement5370); 
					LEFTP290_tree = (Object)adaptor.create(LEFTP290);
					adaptor.addChild(root_0, LEFTP290_tree);

					f=(Token)match(input,STRING,FOLLOW_STRING_in_genbankImportStatement5374); 
					f_tree = (Object)adaptor.create(f);
					adaptor.addChild(root_0, f_tree);

					RIGHTP291=(Token)match(input,RIGHTP,FOLLOW_RIGHTP_in_genbankImportStatement5376); 
					RIGHTP291_tree = (Object)adaptor.create(RIGHTP291);
					adaptor.addChild(root_0, RIGHTP291_tree);


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    try {
					        retval.e = this.interp.importGenbank((f!=null?f.getText():null));
					    } catch(EugeneException ee) {
					        printError(ee.getLocalizedMessage());
					    } 
					}
						
					}
					break;
				case 2 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2987:4: ( IMPORT_LC | IMPORT_UC ) LEFTP typeToken= ID COMMA partToken= STRING RIGHTP
					{
					root_0 = (Object)adaptor.nil();


					set292=input.LT(1);
					if ( (input.LA(1) >= IMPORT_LC && input.LA(1) <= IMPORT_UC) ) {
						input.consume();
						adaptor.addChild(root_0, (Object)adaptor.create(set292));
						state.errorRecovery=false;
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						throw mse;
					}
					LEFTP293=(Token)match(input,LEFTP,FOLLOW_LEFTP_in_genbankImportStatement5389); 
					LEFTP293_tree = (Object)adaptor.create(LEFTP293);
					adaptor.addChild(root_0, LEFTP293_tree);

					typeToken=(Token)match(input,ID,FOLLOW_ID_in_genbankImportStatement5393); 
					typeToken_tree = (Object)adaptor.create(typeToken);
					adaptor.addChild(root_0, typeToken_tree);

					COMMA294=(Token)match(input,COMMA,FOLLOW_COMMA_in_genbankImportStatement5395); 
					COMMA294_tree = (Object)adaptor.create(COMMA294);
					adaptor.addChild(root_0, COMMA294_tree);

					partToken=(Token)match(input,STRING,FOLLOW_STRING_in_genbankImportStatement5399); 
					partToken_tree = (Object)adaptor.create(partToken);
					adaptor.addChild(root_0, partToken_tree);

					RIGHTP295=(Token)match(input,RIGHTP,FOLLOW_RIGHTP_in_genbankImportStatement5401); 
					RIGHTP295_tree = (Object)adaptor.create(RIGHTP295);
					adaptor.addChild(root_0, RIGHTP295_tree);


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    // TODO!!!
					}	
						
					}
					break;

			}
			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "genbankImportStatement"


	public static class registryStatement_return extends ParserRuleReturnScope {
		public NamedElement e;
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "registryStatement"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:2998:1: registryStatement[boolean defer] returns [NamedElement e] : REGISTRY DOT ( IMPORT_LC | IMPORT_UC ) LEFTP n= STRING RIGHTP ;
	public final EugeneParser.registryStatement_return registryStatement(boolean defer) throws RecognitionException {
		EugeneParser.registryStatement_return retval = new EugeneParser.registryStatement_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token n=null;
		Token REGISTRY296=null;
		Token DOT297=null;
		Token set298=null;
		Token LEFTP299=null;
		Token RIGHTP300=null;

		Object n_tree=null;
		Object REGISTRY296_tree=null;
		Object DOT297_tree=null;
		Object set298_tree=null;
		Object LEFTP299_tree=null;
		Object RIGHTP300_tree=null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:3000:2: ( REGISTRY DOT ( IMPORT_LC | IMPORT_UC ) LEFTP n= STRING RIGHTP )
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:3000:4: REGISTRY DOT ( IMPORT_LC | IMPORT_UC ) LEFTP n= STRING RIGHTP
			{
			root_0 = (Object)adaptor.nil();


			REGISTRY296=(Token)match(input,REGISTRY,FOLLOW_REGISTRY_in_registryStatement5425); 
			REGISTRY296_tree = (Object)adaptor.create(REGISTRY296);
			adaptor.addChild(root_0, REGISTRY296_tree);

			DOT297=(Token)match(input,DOT,FOLLOW_DOT_in_registryStatement5427); 
			DOT297_tree = (Object)adaptor.create(DOT297);
			adaptor.addChild(root_0, DOT297_tree);

			set298=input.LT(1);
			if ( (input.LA(1) >= IMPORT_LC && input.LA(1) <= IMPORT_UC) ) {
				input.consume();
				adaptor.addChild(root_0, (Object)adaptor.create(set298));
				state.errorRecovery=false;
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				throw mse;
			}
			LEFTP299=(Token)match(input,LEFTP,FOLLOW_LEFTP_in_registryStatement5435); 
			LEFTP299_tree = (Object)adaptor.create(LEFTP299);
			adaptor.addChild(root_0, LEFTP299_tree);

			n=(Token)match(input,STRING,FOLLOW_STRING_in_registryStatement5439); 
			n_tree = (Object)adaptor.create(n);
			adaptor.addChild(root_0, n_tree);

			RIGHTP300=(Token)match(input,RIGHTP,FOLLOW_RIGHTP_in_registryStatement5441); 
			RIGHTP300_tree = (Object)adaptor.create(RIGHTP300);
			adaptor.addChild(root_0, RIGHTP300_tree);


			if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {

			    try {
			        String name = (n!=null?n.getText():null);
			        name = name.substring(1, name.length()-2);
			        retval.e = this.interp.importRegistry(name);
			    } catch(EugeneException ee) {
			        printError(ee.getLocalizedMessage());    
			    }
			}		
				
			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "registryStatement"


	public static class testStatements_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "testStatements"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:3015:1: testStatements[boolean defer] : (| ASSERT LEFTP id= ID DOT ( SIZE_UC | SIZE_LC ) LEFTP RIGHTP EQUALS EQUALS n= NUMBER RIGHTP | NOTE LEFTP id= ID DOT ( SIZE_UC | SIZE_LC ) LEFTP RIGHTP EQUALS EQUALS n= NUMBER RIGHTP );
	public final EugeneParser.testStatements_return testStatements(boolean defer) throws RecognitionException {
		EugeneParser.testStatements_return retval = new EugeneParser.testStatements_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token id=null;
		Token n=null;
		Token ASSERT301=null;
		Token LEFTP302=null;
		Token DOT303=null;
		Token set304=null;
		Token LEFTP305=null;
		Token RIGHTP306=null;
		Token EQUALS307=null;
		Token EQUALS308=null;
		Token RIGHTP309=null;
		Token NOTE310=null;
		Token LEFTP311=null;
		Token DOT312=null;
		Token set313=null;
		Token LEFTP314=null;
		Token RIGHTP315=null;
		Token EQUALS316=null;
		Token EQUALS317=null;
		Token RIGHTP318=null;

		Object id_tree=null;
		Object n_tree=null;
		Object ASSERT301_tree=null;
		Object LEFTP302_tree=null;
		Object DOT303_tree=null;
		Object set304_tree=null;
		Object LEFTP305_tree=null;
		Object RIGHTP306_tree=null;
		Object EQUALS307_tree=null;
		Object EQUALS308_tree=null;
		Object RIGHTP309_tree=null;
		Object NOTE310_tree=null;
		Object LEFTP311_tree=null;
		Object DOT312_tree=null;
		Object set313_tree=null;
		Object LEFTP314_tree=null;
		Object RIGHTP315_tree=null;
		Object EQUALS316_tree=null;
		Object EQUALS317_tree=null;
		Object RIGHTP318_tree=null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:3016:2: (| ASSERT LEFTP id= ID DOT ( SIZE_UC | SIZE_LC ) LEFTP RIGHTP EQUALS EQUALS n= NUMBER RIGHTP | NOTE LEFTP id= ID DOT ( SIZE_UC | SIZE_LC ) LEFTP RIGHTP EQUALS EQUALS n= NUMBER RIGHTP )
			int alt104=3;
			switch ( input.LA(1) ) {
			case EOF:
				{
				alt104=1;
				}
				break;
			case ASSERT:
				{
				alt104=2;
				}
				break;
			case NOTE:
				{
				alt104=3;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 104, 0, input);
				throw nvae;
			}
			switch (alt104) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:3016:5: 
					{
					root_0 = (Object)adaptor.nil();


					}
					break;
				case 2 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:3016:7: ASSERT LEFTP id= ID DOT ( SIZE_UC | SIZE_LC ) LEFTP RIGHTP EQUALS EQUALS n= NUMBER RIGHTP
					{
					root_0 = (Object)adaptor.nil();


					ASSERT301=(Token)match(input,ASSERT,FOLLOW_ASSERT_in_testStatements5460); 
					ASSERT301_tree = (Object)adaptor.create(ASSERT301);
					adaptor.addChild(root_0, ASSERT301_tree);

					LEFTP302=(Token)match(input,LEFTP,FOLLOW_LEFTP_in_testStatements5462); 
					LEFTP302_tree = (Object)adaptor.create(LEFTP302);
					adaptor.addChild(root_0, LEFTP302_tree);

					id=(Token)match(input,ID,FOLLOW_ID_in_testStatements5466); 
					id_tree = (Object)adaptor.create(id);
					adaptor.addChild(root_0, id_tree);

					DOT303=(Token)match(input,DOT,FOLLOW_DOT_in_testStatements5468); 
					DOT303_tree = (Object)adaptor.create(DOT303);
					adaptor.addChild(root_0, DOT303_tree);

					set304=input.LT(1);
					if ( (input.LA(1) >= SIZE_LC && input.LA(1) <= SIZE_UC) ) {
						input.consume();
						adaptor.addChild(root_0, (Object)adaptor.create(set304));
						state.errorRecovery=false;
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						throw mse;
					}
					LEFTP305=(Token)match(input,LEFTP,FOLLOW_LEFTP_in_testStatements5476); 
					LEFTP305_tree = (Object)adaptor.create(LEFTP305);
					adaptor.addChild(root_0, LEFTP305_tree);

					RIGHTP306=(Token)match(input,RIGHTP,FOLLOW_RIGHTP_in_testStatements5478); 
					RIGHTP306_tree = (Object)adaptor.create(RIGHTP306);
					adaptor.addChild(root_0, RIGHTP306_tree);

					EQUALS307=(Token)match(input,EQUALS,FOLLOW_EQUALS_in_testStatements5480); 
					EQUALS307_tree = (Object)adaptor.create(EQUALS307);
					adaptor.addChild(root_0, EQUALS307_tree);

					EQUALS308=(Token)match(input,EQUALS,FOLLOW_EQUALS_in_testStatements5482); 
					EQUALS308_tree = (Object)adaptor.create(EQUALS308);
					adaptor.addChild(root_0, EQUALS308_tree);

					n=(Token)match(input,NUMBER,FOLLOW_NUMBER_in_testStatements5486); 
					n_tree = (Object)adaptor.create(n);
					adaptor.addChild(root_0, n_tree);

					RIGHTP309=(Token)match(input,RIGHTP,FOLLOW_RIGHTP_in_testStatements5488); 
					RIGHTP309_tree = (Object)adaptor.create(RIGHTP309);
					adaptor.addChild(root_0, RIGHTP309_tree);


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    try {
					        NamedElement el = this.interp.get((id!=null?id.getText():null));
					        if(null != el) {
					//            if(el instanceof EugeneCollection) {
					//                if(((EugeneCollection)el).getElements().size() != Integer.parseInt((n!=null?n.getText():null))) {
					//                    printError("TEST NOT PASSED!");
					//                }
					//            }
					    }
					    } catch(EugeneException ee) {
					        printError(ee.getMessage());
					    }
					}	
						
					}
					break;
				case 3 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:3032:5: NOTE LEFTP id= ID DOT ( SIZE_UC | SIZE_LC ) LEFTP RIGHTP EQUALS EQUALS n= NUMBER RIGHTP
					{
					root_0 = (Object)adaptor.nil();


					NOTE310=(Token)match(input,NOTE,FOLLOW_NOTE_in_testStatements5496); 
					NOTE310_tree = (Object)adaptor.create(NOTE310);
					adaptor.addChild(root_0, NOTE310_tree);

					LEFTP311=(Token)match(input,LEFTP,FOLLOW_LEFTP_in_testStatements5498); 
					LEFTP311_tree = (Object)adaptor.create(LEFTP311);
					adaptor.addChild(root_0, LEFTP311_tree);

					id=(Token)match(input,ID,FOLLOW_ID_in_testStatements5502); 
					id_tree = (Object)adaptor.create(id);
					adaptor.addChild(root_0, id_tree);

					DOT312=(Token)match(input,DOT,FOLLOW_DOT_in_testStatements5504); 
					DOT312_tree = (Object)adaptor.create(DOT312);
					adaptor.addChild(root_0, DOT312_tree);

					set313=input.LT(1);
					if ( (input.LA(1) >= SIZE_LC && input.LA(1) <= SIZE_UC) ) {
						input.consume();
						adaptor.addChild(root_0, (Object)adaptor.create(set313));
						state.errorRecovery=false;
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						throw mse;
					}
					LEFTP314=(Token)match(input,LEFTP,FOLLOW_LEFTP_in_testStatements5512); 
					LEFTP314_tree = (Object)adaptor.create(LEFTP314);
					adaptor.addChild(root_0, LEFTP314_tree);

					RIGHTP315=(Token)match(input,RIGHTP,FOLLOW_RIGHTP_in_testStatements5514); 
					RIGHTP315_tree = (Object)adaptor.create(RIGHTP315);
					adaptor.addChild(root_0, RIGHTP315_tree);

					EQUALS316=(Token)match(input,EQUALS,FOLLOW_EQUALS_in_testStatements5516); 
					EQUALS316_tree = (Object)adaptor.create(EQUALS316);
					adaptor.addChild(root_0, EQUALS316_tree);

					EQUALS317=(Token)match(input,EQUALS,FOLLOW_EQUALS_in_testStatements5518); 
					EQUALS317_tree = (Object)adaptor.create(EQUALS317);
					adaptor.addChild(root_0, EQUALS317_tree);

					n=(Token)match(input,NUMBER,FOLLOW_NUMBER_in_testStatements5522); 
					n_tree = (Object)adaptor.create(n);
					adaptor.addChild(root_0, n_tree);

					RIGHTP318=(Token)match(input,RIGHTP,FOLLOW_RIGHTP_in_testStatements5524); 
					RIGHTP318_tree = (Object)adaptor.create(RIGHTP318);
					adaptor.addChild(root_0, RIGHTP318_tree);


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					}

					}
					break;

			}
			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "testStatements"


	public static class function_definition_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "function_definition"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:3041:1: function_definition[boolean defer] : (rt= type_specification[true] )? n= ID LEFTP (lop= list_of_parameters[true] )? RIGHTP LEFTCUR stmts= list_of_statements[true] RIGHTCUR ;
	public final EugeneParser.function_definition_return function_definition(boolean defer) throws RecognitionException, EugeneReturnException {
		EugeneParser.function_definition_return retval = new EugeneParser.function_definition_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token n=null;
		Token LEFTP319=null;
		Token RIGHTP320=null;
		Token LEFTCUR321=null;
		Token RIGHTCUR322=null;
		ParserRuleReturnScope rt =null;
		ParserRuleReturnScope lop =null;
		ParserRuleReturnScope stmts =null;

		Object n_tree=null;
		Object LEFTP319_tree=null;
		Object RIGHTP320_tree=null;
		Object LEFTCUR321_tree=null;
		Object RIGHTCUR322_tree=null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:3043:2: ( (rt= type_specification[true] )? n= ID LEFTP (lop= list_of_parameters[true] )? RIGHTP LEFTCUR stmts= list_of_statements[true] RIGHTCUR )
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:3043:4: (rt= type_specification[true] )? n= ID LEFTP (lop= list_of_parameters[true] )? RIGHTP LEFTCUR stmts= list_of_statements[true] RIGHTCUR
			{
			root_0 = (Object)adaptor.nil();


			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:3043:4: (rt= type_specification[true] )?
			int alt105=2;
			int LA105_0 = input.LA(1);
			if ( ((LA105_0 >= BOOL && LA105_0 <= BOOLEAN)||LA105_0==NUM||LA105_0==TXT) ) {
				alt105=1;
			}
			switch (alt105) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:3043:5: rt= type_specification[true]
					{
					pushFollow(FOLLOW_type_specification_in_function_definition5549);
					rt=type_specification(true);
					state._fsp--;

					adaptor.addChild(root_0, rt.getTree());

					}
					break;

			}

			n=(Token)match(input,ID,FOLLOW_ID_in_function_definition5556); 
			n_tree = (Object)adaptor.create(n);
			adaptor.addChild(root_0, n_tree);

			LEFTP319=(Token)match(input,LEFTP,FOLLOW_LEFTP_in_function_definition5558); 
			LEFTP319_tree = (Object)adaptor.create(LEFTP319);
			adaptor.addChild(root_0, LEFTP319_tree);

			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:3043:46: (lop= list_of_parameters[true] )?
			int alt106=2;
			int LA106_0 = input.LA(1);
			if ( ((LA106_0 >= BOOL && LA106_0 <= BOOLEAN)||LA106_0==NUM||LA106_0==TXT) ) {
				alt106=1;
			}
			switch (alt106) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:3043:47: lop= list_of_parameters[true]
					{
					pushFollow(FOLLOW_list_of_parameters_in_function_definition5563);
					lop=list_of_parameters(true);
					state._fsp--;

					adaptor.addChild(root_0, lop.getTree());

					}
					break;

			}

			RIGHTP320=(Token)match(input,RIGHTP,FOLLOW_RIGHTP_in_function_definition5568); 
			RIGHTP320_tree = (Object)adaptor.create(RIGHTP320);
			adaptor.addChild(root_0, RIGHTP320_tree);

			LEFTCUR321=(Token)match(input,LEFTCUR,FOLLOW_LEFTCUR_in_function_definition5570); 
			LEFTCUR321_tree = (Object)adaptor.create(LEFTCUR321);
			adaptor.addChild(root_0, LEFTCUR321_tree);

			pushFollow(FOLLOW_list_of_statements_in_function_definition5578);
			stmts=list_of_statements(true);
			state._fsp--;

			adaptor.addChild(root_0, stmts.getTree());

			RIGHTCUR322=(Token)match(input,RIGHTCUR,FOLLOW_RIGHTCUR_in_function_definition5584); 
			RIGHTCUR322_tree = (Object)adaptor.create(RIGHTCUR322);
			adaptor.addChild(root_0, RIGHTCUR322_tree);


			if(defer && this.PARSING_PHASE == ParsingPhase.PRE_PROCESSING) {  // FUNCTION DEFINITION 
			    try {
			        // let's check if a return type is specified
			        String return_type = null;
			        if(null != rt) {
			            return_type = (rt!=null?((EugeneParser.type_specification_return)rt).t:null);
			        }
			        
			        // let's check if parameters are specified
			        List<NamedElement> parameters = null;
			        if(null != lop) {
			            parameters = (lop!=null?((EugeneParser.list_of_parameters_return)lop).parameters:null);
			        }
			        
			        // Function w/ parameters
			        this.interp.defineFunction(
			                return_type,     // return type
			                (n!=null?n.getText():null),         // the name of the function
			                parameters,      // list of parameters
			                (stmts!=null?(stmts.start):null),    // list of statements
			                this.input);     // the token stream
			                
			    } catch(EugeneException ee) {
			        printError(ee.getLocalizedMessage());
			    }
			}	
				
			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "function_definition"


	public static class type_specification_return extends ParserRuleReturnScope {
		public String t;
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "type_specification"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:3075:1: type_specification[boolean defer] returns [String t] : ( NUM | TXT | NUM LEFTSBR RIGHTSBR | TXT LEFTSBR RIGHTSBR | ( BOOL | BOOLEAN ) );
	public final EugeneParser.type_specification_return type_specification(boolean defer) throws RecognitionException {
		EugeneParser.type_specification_return retval = new EugeneParser.type_specification_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token NUM323=null;
		Token TXT324=null;
		Token NUM325=null;
		Token LEFTSBR326=null;
		Token RIGHTSBR327=null;
		Token TXT328=null;
		Token LEFTSBR329=null;
		Token RIGHTSBR330=null;
		Token set331=null;

		Object NUM323_tree=null;
		Object TXT324_tree=null;
		Object NUM325_tree=null;
		Object LEFTSBR326_tree=null;
		Object RIGHTSBR327_tree=null;
		Object TXT328_tree=null;
		Object LEFTSBR329_tree=null;
		Object RIGHTSBR330_tree=null;
		Object set331_tree=null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:3077:2: ( NUM | TXT | NUM LEFTSBR RIGHTSBR | TXT LEFTSBR RIGHTSBR | ( BOOL | BOOLEAN ) )
			int alt107=5;
			switch ( input.LA(1) ) {
			case NUM:
				{
				int LA107_1 = input.LA(2);
				if ( (LA107_1==LEFTSBR) ) {
					alt107=3;
				}
				else if ( (LA107_1==ID) ) {
					alt107=1;
				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 107, 1, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

				}
				break;
			case TXT:
				{
				int LA107_2 = input.LA(2);
				if ( (LA107_2==LEFTSBR) ) {
					alt107=4;
				}
				else if ( (LA107_2==ID) ) {
					alt107=2;
				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 107, 2, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

				}
				break;
			case BOOL:
			case BOOLEAN:
				{
				alt107=5;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 107, 0, input);
				throw nvae;
			}
			switch (alt107) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:3077:4: NUM
					{
					root_0 = (Object)adaptor.nil();


					NUM323=(Token)match(input,NUM,FOLLOW_NUM_in_type_specification5604); 
					NUM323_tree = (Object)adaptor.create(NUM323);
					adaptor.addChild(root_0, NUM323_tree);


					if(defer && this.PARSING_PHASE == ParsingPhase.PRE_PROCESSING) {
					    retval.t = EugeneConstants.NUM;
					}	
						
					}
					break;
				case 2 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:3082:4: TXT
					{
					root_0 = (Object)adaptor.nil();


					TXT324=(Token)match(input,TXT,FOLLOW_TXT_in_type_specification5611); 
					TXT324_tree = (Object)adaptor.create(TXT324);
					adaptor.addChild(root_0, TXT324_tree);


					if(defer && this.PARSING_PHASE == ParsingPhase.PRE_PROCESSING) {
					    retval.t = EugeneConstants.TXT;
					}	
						
					}
					break;
				case 3 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:3087:4: NUM LEFTSBR RIGHTSBR
					{
					root_0 = (Object)adaptor.nil();


					NUM325=(Token)match(input,NUM,FOLLOW_NUM_in_type_specification5618); 
					NUM325_tree = (Object)adaptor.create(NUM325);
					adaptor.addChild(root_0, NUM325_tree);

					LEFTSBR326=(Token)match(input,LEFTSBR,FOLLOW_LEFTSBR_in_type_specification5620); 
					LEFTSBR326_tree = (Object)adaptor.create(LEFTSBR326);
					adaptor.addChild(root_0, LEFTSBR326_tree);

					RIGHTSBR327=(Token)match(input,RIGHTSBR,FOLLOW_RIGHTSBR_in_type_specification5622); 
					RIGHTSBR327_tree = (Object)adaptor.create(RIGHTSBR327);
					adaptor.addChild(root_0, RIGHTSBR327_tree);


					if(defer && this.PARSING_PHASE == ParsingPhase.PRE_PROCESSING) {
					    retval.t = EugeneConstants.NUMLIST;
					}	
						
					}
					break;
				case 4 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:3092:4: TXT LEFTSBR RIGHTSBR
					{
					root_0 = (Object)adaptor.nil();


					TXT328=(Token)match(input,TXT,FOLLOW_TXT_in_type_specification5629); 
					TXT328_tree = (Object)adaptor.create(TXT328);
					adaptor.addChild(root_0, TXT328_tree);

					LEFTSBR329=(Token)match(input,LEFTSBR,FOLLOW_LEFTSBR_in_type_specification5631); 
					LEFTSBR329_tree = (Object)adaptor.create(LEFTSBR329);
					adaptor.addChild(root_0, LEFTSBR329_tree);

					RIGHTSBR330=(Token)match(input,RIGHTSBR,FOLLOW_RIGHTSBR_in_type_specification5633); 
					RIGHTSBR330_tree = (Object)adaptor.create(RIGHTSBR330);
					adaptor.addChild(root_0, RIGHTSBR330_tree);


					if(defer && this.PARSING_PHASE == ParsingPhase.PRE_PROCESSING) {
					    retval.t = EugeneConstants.TXTLIST;
					}	
						
					}
					break;
				case 5 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:3097:4: ( BOOL | BOOLEAN )
					{
					root_0 = (Object)adaptor.nil();


					set331=input.LT(1);
					if ( (input.LA(1) >= BOOL && input.LA(1) <= BOOLEAN) ) {
						input.consume();
						adaptor.addChild(root_0, (Object)adaptor.create(set331));
						state.errorRecovery=false;
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						throw mse;
					}

					if(defer && this.PARSING_PHASE == ParsingPhase.PRE_PROCESSING) {
					    retval.t = EugeneConstants.BOOLEAN;
					}	
						
					}
					break;

			}
			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "type_specification"


	public static class list_of_parameters_return extends ParserRuleReturnScope {
		public List<NamedElement> parameters;
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "list_of_parameters"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:3104:1: list_of_parameters[boolean defer] returns [List<NamedElement> parameters] : pt= type_specification[defer] n= ID ( COMMA lop= list_of_parameters[defer] )? ;
	public final EugeneParser.list_of_parameters_return list_of_parameters(boolean defer) throws RecognitionException {
		EugeneParser.list_of_parameters_return retval = new EugeneParser.list_of_parameters_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token n=null;
		Token COMMA332=null;
		ParserRuleReturnScope pt =null;
		ParserRuleReturnScope lop =null;

		Object n_tree=null;
		Object COMMA332_tree=null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:3106:2: (pt= type_specification[defer] n= ID ( COMMA lop= list_of_parameters[defer] )? )
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:3106:4: pt= type_specification[defer] n= ID ( COMMA lop= list_of_parameters[defer] )?
			{
			root_0 = (Object)adaptor.nil();


			pushFollow(FOLLOW_type_specification_in_list_of_parameters5666);
			pt=type_specification(defer);
			state._fsp--;

			adaptor.addChild(root_0, pt.getTree());

			n=(Token)match(input,ID,FOLLOW_ID_in_list_of_parameters5671); 
			n_tree = (Object)adaptor.create(n);
			adaptor.addChild(root_0, n_tree);


			if(defer && this.PARSING_PHASE == ParsingPhase.PRE_PROCESSING) {
			    if(null == retval.parameters) {
			        retval.parameters = new ArrayList<NamedElement>();
			    }
			    
			    try {
			        retval.parameters.add(
			            this.interp.createFunctionParameter(
			                (pt!=null?((EugeneParser.type_specification_return)pt).t:null),        // type of the parameter
			                (n!=null?n.getText():null)));    // name of the parameter
			    } catch(EugeneException ee) {
			        printError(ee.getLocalizedMessage());
			    }
			}	
				
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:3121:4: ( COMMA lop= list_of_parameters[defer] )?
			int alt108=2;
			int LA108_0 = input.LA(1);
			if ( (LA108_0==COMMA) ) {
				alt108=1;
			}
			switch (alt108) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:3121:5: COMMA lop= list_of_parameters[defer]
					{
					COMMA332=(Token)match(input,COMMA,FOLLOW_COMMA_in_list_of_parameters5676); 
					COMMA332_tree = (Object)adaptor.create(COMMA332);
					adaptor.addChild(root_0, COMMA332_tree);

					pushFollow(FOLLOW_list_of_parameters_in_list_of_parameters5680);
					lop=list_of_parameters(defer);
					state._fsp--;

					adaptor.addChild(root_0, lop.getTree());


					if(defer && this.PARSING_PHASE == ParsingPhase.PRE_PROCESSING) {
					    retval.parameters.addAll((lop!=null?((EugeneParser.list_of_parameters_return)lop).parameters:null));    
					}	
						
					}
					break;

			}

			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "list_of_parameters"


	public static class list_of_statements_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "list_of_statements"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:3128:1: list_of_statements[boolean defer] : statement[defer] ( statement[defer] )* ;
	public final EugeneParser.list_of_statements_return list_of_statements(boolean defer) throws RecognitionException, EugeneReturnException {
		EugeneParser.list_of_statements_return retval = new EugeneParser.list_of_statements_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		ParserRuleReturnScope statement333 =null;
		ParserRuleReturnScope statement334 =null;


		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:3130:2: ( statement[defer] ( statement[defer] )* )
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:3130:4: statement[defer] ( statement[defer] )*
			{
			root_0 = (Object)adaptor.nil();


			pushFollow(FOLLOW_statement_in_list_of_statements5704);
			statement333=statement(defer);
			state._fsp--;

			adaptor.addChild(root_0, statement333.getTree());

			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:3130:21: ( statement[defer] )*
			loop109:
			while (true) {
				int alt109=2;
				int LA109_0 = input.LA(1);
				if ( (LA109_0==ARRAY||(LA109_0 >= BOOL && LA109_0 <= COLLECTION)||(LA109_0 >= CREATE_LC && LA109_0 <= CREATE_UC)||LA109_0==DEVICE||(LA109_0 >= EXIT_LC && LA109_0 <= EXIT_UC)||LA109_0==GENBANK||LA109_0==GRAMMAR||(LA109_0 >= HASHMARK && LA109_0 <= ID)||(LA109_0 >= IMPORT_LC && LA109_0 <= INTERACTION)||(LA109_0 >= LC_FOR && LA109_0 <= LC_IF)||LA109_0==LC_WHILE||LA109_0==NUM||(LA109_0 >= PART && LA109_0 <= PERMUTE)||(LA109_0 >= PRINTLN_LC && LA109_0 <= RANDOM_UC)||(LA109_0 >= REGISTRY && LA109_0 <= RETURN_UC)||(LA109_0 >= RULE && LA109_0 <= SBOL)||(LA109_0 >= SIZEOF_LC && LA109_0 <= STORE_UC)||(LA109_0 >= TXT && LA109_0 <= TYPE)||(LA109_0 >= UC_FOR && LA109_0 <= UC_IF)||LA109_0==UC_WHILE) ) {
					alt109=1;
				}

				switch (alt109) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:3130:22: statement[defer]
					{
					pushFollow(FOLLOW_statement_in_list_of_statements5708);
					statement334=statement(defer);
					state._fsp--;

					adaptor.addChild(root_0, statement334.getTree());

					}
					break;

				default :
					break loop109;
				}
			}

			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "list_of_statements"


	public static class return_statement_return extends ParserRuleReturnScope {
		public NamedElement el;
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "return_statement"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:3133:1: return_statement[boolean defer] returns [NamedElement el] : ( RETURN_LC | RETURN_UC ) e= expr[defer] ;
	public final EugeneParser.return_statement_return return_statement(boolean defer) throws RecognitionException, EugeneReturnException {
		EugeneParser.return_statement_return retval = new EugeneParser.return_statement_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token set335=null;
		ParserRuleReturnScope e =null;

		Object set335_tree=null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:3136:2: ( ( RETURN_LC | RETURN_UC ) e= expr[defer] )
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:3136:4: ( RETURN_LC | RETURN_UC ) e= expr[defer]
			{
			root_0 = (Object)adaptor.nil();


			set335=input.LT(1);
			if ( (input.LA(1) >= RETURN_LC && input.LA(1) <= RETURN_UC) ) {
				input.consume();
				adaptor.addChild(root_0, (Object)adaptor.create(set335));
				state.errorRecovery=false;
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				throw mse;
			}
			pushFollow(FOLLOW_expr_in_return_statement5743);
			e=expr(defer);
			state._fsp--;

			adaptor.addChild(root_0, e.getTree());


			if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {

			    if(!this.interp.isReturnAllowed()) {
			        printError("a return statement is not allowed at this position!");
			    }

			    if(null != (e!=null?((EugeneParser.expr_return)e).element:null)) {
			        retval.el = (e!=null?((EugeneParser.expr_return)e).element:null);
			    } else {
			        retval.el = (e!=null?((EugeneParser.expr_return)e).p:null);
			    }
			    
			    throw new EugeneReturnException(retval.el);
			}
				
			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "return_statement"


	public static class function_call_return extends ParserRuleReturnScope {
		public NamedElement e;
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "function_call"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:3163:1: function_call[boolean defer] returns [NamedElement e] : udf= call_user_defined_function[defer] ;
	public final EugeneParser.function_call_return function_call(boolean defer) throws RecognitionException {
		EugeneParser.function_call_return retval = new EugeneParser.function_call_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		ParserRuleReturnScope udf =null;


		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:3165:2: (udf= call_user_defined_function[defer] )
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:3165:4: udf= call_user_defined_function[defer]
			{
			root_0 = (Object)adaptor.nil();


			pushFollow(FOLLOW_call_user_defined_function_in_function_call5774);
			udf=call_user_defined_function(defer);
			state._fsp--;

			adaptor.addChild(root_0, udf.getTree());


			if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
			    retval.e = (udf!=null?((EugeneParser.call_user_defined_function_return)udf).e:null);
			}	
				
			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "function_call"


	public static class call_user_defined_function_return extends ParserRuleReturnScope {
		public NamedElement e;
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "call_user_defined_function"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:3173:1: call_user_defined_function[boolean defer] returns [NamedElement e] : f= ID LEFTP (loe= list_of_expressions[defer] )? RIGHTP ;
	public final EugeneParser.call_user_defined_function_return call_user_defined_function(boolean defer) throws RecognitionException {
		EugeneParser.call_user_defined_function_return retval = new EugeneParser.call_user_defined_function_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token f=null;
		Token LEFTP336=null;
		Token RIGHTP337=null;
		ParserRuleReturnScope loe =null;

		Object f_tree=null;
		Object LEFTP336_tree=null;
		Object RIGHTP337_tree=null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:3175:2: (f= ID LEFTP (loe= list_of_expressions[defer] )? RIGHTP )
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:3175:4: f= ID LEFTP (loe= list_of_expressions[defer] )? RIGHTP
			{
			root_0 = (Object)adaptor.nil();


			f=(Token)match(input,ID,FOLLOW_ID_in_call_user_defined_function5799); 
			f_tree = (Object)adaptor.create(f);
			adaptor.addChild(root_0, f_tree);

			LEFTP336=(Token)match(input,LEFTP,FOLLOW_LEFTP_in_call_user_defined_function5801); 
			LEFTP336_tree = (Object)adaptor.create(LEFTP336);
			adaptor.addChild(root_0, LEFTP336_tree);

			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:3175:15: (loe= list_of_expressions[defer] )?
			int alt110=2;
			int LA110_0 = input.LA(1);
			if ( (LA110_0==DOLLAR||(LA110_0 >= FALSE_LC && LA110_0 <= FALSE_UC)||LA110_0==ID||(LA110_0 >= LEFTP && LA110_0 <= LEFTSBR)||LA110_0==MINUS||LA110_0==NUMBER||LA110_0==PERMUTE||LA110_0==PRODUCT||(LA110_0 >= RANDOM_LC && LA110_0 <= RANDOM_UC)||LA110_0==REAL||(LA110_0 >= SIZEOF_LC && LA110_0 <= SIZE_UC)||(LA110_0 >= STRING && LA110_0 <= TRUE_UC)) ) {
				alt110=1;
			}
			switch (alt110) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:3175:16: loe= list_of_expressions[defer]
					{
					pushFollow(FOLLOW_list_of_expressions_in_call_user_defined_function5806);
					loe=list_of_expressions(defer);
					state._fsp--;

					adaptor.addChild(root_0, loe.getTree());

					}
					break;

			}

			RIGHTP337=(Token)match(input,RIGHTP,FOLLOW_RIGHTP_in_call_user_defined_function5811); 
			RIGHTP337_tree = (Object)adaptor.create(RIGHTP337);
			adaptor.addChild(root_0, RIGHTP337_tree);


			if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
			    try {
			        retval.e = this.call_function((f!=null?f.getText():null), (loe!=null?((EugeneParser.list_of_expressions_return)loe).parameter_values:null));
			    } catch(EugeneException ee) {
			        printError(ee.getLocalizedMessage());
			    }
			}	
				
			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "call_user_defined_function"


	public static class list_of_expressions_return extends ParserRuleReturnScope {
		public List<NamedElement> parameter_values;
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "list_of_expressions"
	// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:3186:1: list_of_expressions[boolean defer] returns [List<NamedElement> parameter_values] : e= expr[defer] ( COMMA loe= list_of_expressions[defer] )? ;
	public final EugeneParser.list_of_expressions_return list_of_expressions(boolean defer) throws RecognitionException {
		EugeneParser.list_of_expressions_return retval = new EugeneParser.list_of_expressions_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token COMMA338=null;
		ParserRuleReturnScope e =null;
		ParserRuleReturnScope loe =null;

		Object COMMA338_tree=null;

		try {
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:3188:2: (e= expr[defer] ( COMMA loe= list_of_expressions[defer] )? )
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:3188:4: e= expr[defer] ( COMMA loe= list_of_expressions[defer] )?
			{
			root_0 = (Object)adaptor.nil();


			pushFollow(FOLLOW_expr_in_list_of_expressions5833);
			e=expr(defer);
			state._fsp--;

			adaptor.addChild(root_0, e.getTree());


			if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
			    if(null == retval.parameter_values) {
			        retval.parameter_values = new ArrayList<NamedElement>();
			    }
			    
			    if(null == (e!=null?((EugeneParser.expr_return)e).p:null)) { 
			        retval.parameter_values.add((e!=null?((EugeneParser.expr_return)e).element:null));
			    } else {
			        retval.parameter_values.add((e!=null?((EugeneParser.expr_return)e).p:null));
			    }
			    
			}	
				
			// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:3201:5: ( COMMA loe= list_of_expressions[defer] )?
			int alt111=2;
			int LA111_0 = input.LA(1);
			if ( (LA111_0==COMMA) ) {
				alt111=1;
			}
			switch (alt111) {
				case 1 :
					// /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v2.0/grammar/Eugene.g:3201:6: COMMA loe= list_of_expressions[defer]
					{
					COMMA338=(Token)match(input,COMMA,FOLLOW_COMMA_in_list_of_expressions5840); 
					COMMA338_tree = (Object)adaptor.create(COMMA338);
					adaptor.addChild(root_0, COMMA338_tree);

					pushFollow(FOLLOW_list_of_expressions_in_list_of_expressions5844);
					loe=list_of_expressions(defer);
					state._fsp--;

					adaptor.addChild(root_0, loe.getTree());


					if(!defer && this.PARSING_PHASE == ParsingPhase.INTERPRETING) {
					    retval.parameter_values.addAll((loe!=null?((EugeneParser.list_of_expressions_return)loe).parameter_values:null));
					}	
						
					}
					break;

			}

			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "list_of_expressions"

	// Delegated rules



	public static final BitSet FOLLOW_statement_in_prog1070 = new BitSet(new long[]{0x00838FB50604CE40L,0x107187EF1C3FCE40L});
	public static final BitSet FOLLOW_function_definition_in_prog1075 = new BitSet(new long[]{0x00838FB50604CE40L,0x107187EF1C3FCE40L});
	public static final BitSet FOLLOW_EOF_in_prog1080 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_includeStatement_in_statement1107 = new BitSet(new long[]{0x0000000000000002L,0x0000001000000000L});
	public static final BitSet FOLLOW_SEMIC_in_statement1111 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_declarationStatement_in_statement1118 = new BitSet(new long[]{0x0000000000000000L,0x0000001000000000L});
	public static final BitSet FOLLOW_SEMIC_in_statement1121 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_printStatement_in_statement1127 = new BitSet(new long[]{0x0000000000000000L,0x0000001000000000L});
	public static final BitSet FOLLOW_SEMIC_in_statement1130 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_assignment_in_statement1135 = new BitSet(new long[]{0x0000000000000000L,0x0000001000000000L});
	public static final BitSet FOLLOW_SEMIC_in_statement1138 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_dataExchange_in_statement1145 = new BitSet(new long[]{0x0000000000000000L,0x0000001000000000L});
	public static final BitSet FOLLOW_SEMIC_in_statement1148 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_imperativeStatements_in_statement1155 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_function_call_in_statement1161 = new BitSet(new long[]{0x0000000000000000L,0x0000001000000000L});
	public static final BitSet FOLLOW_SEMIC_in_statement1164 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_built_in_function_in_statement1171 = new BitSet(new long[]{0x0000000000000000L,0x0000001000000000L});
	public static final BitSet FOLLOW_SEMIC_in_statement1174 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_stand_alone_function_in_statement1183 = new BitSet(new long[]{0x0000000000000000L,0x0000001000000000L});
	public static final BitSet FOLLOW_SEMIC_in_statement1186 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_return_statement_in_statement1193 = new BitSet(new long[]{0x0000000000000000L,0x0000001000000000L});
	public static final BitSet FOLLOW_SEMIC_in_statement1196 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_variableDeclaration_in_declarationStatement1217 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_containerDeclaration_in_declarationStatement1225 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_propertyDeclaration_in_declarationStatement1231 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_typeDeclaration_in_declarationStatement1237 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_instantiation_in_declarationStatement1243 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_interactionDeclaration_in_declarationStatement1249 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ruleDeclaration_in_declarationStatement1255 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_grammarDeclaration_in_declarationStatement1261 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_deviceDeclaration_in_declarationStatement1267 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_NUM_in_variableDeclaration1285 = new BitSet(new long[]{0x0000002000000000L});
	public static final BitSet FOLLOW_numdecl_in_variableDeclaration1289 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_TXT_in_variableDeclaration1300 = new BitSet(new long[]{0x0000002000000000L});
	public static final BitSet FOLLOW_txtdecl_in_variableDeclaration1304 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_TXT_in_variableDeclaration1315 = new BitSet(new long[]{0x0400000000000000L});
	public static final BitSet FOLLOW_LEFTSBR_in_variableDeclaration1317 = new BitSet(new long[]{0x0000000000000000L,0x0000000080000000L});
	public static final BitSet FOLLOW_RIGHTSBR_in_variableDeclaration1319 = new BitSet(new long[]{0x0000002000000000L});
	public static final BitSet FOLLOW_txtlistdecl_in_variableDeclaration1323 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_NUM_in_variableDeclaration1334 = new BitSet(new long[]{0x0400000000000000L});
	public static final BitSet FOLLOW_LEFTSBR_in_variableDeclaration1336 = new BitSet(new long[]{0x0000000000000000L,0x0000000080000000L});
	public static final BitSet FOLLOW_RIGHTSBR_in_variableDeclaration1338 = new BitSet(new long[]{0x0000002000000000L});
	public static final BitSet FOLLOW_numlistdecl_in_variableDeclaration1342 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_set_in_variableDeclaration1353 = new BitSet(new long[]{0x0000002000000000L});
	public static final BitSet FOLLOW_booldecl_in_variableDeclaration1361 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ID_in_numdecl1384 = new BitSet(new long[]{0x0000000000002002L});
	public static final BitSet FOLLOW_COMMA_in_numdecl1390 = new BitSet(new long[]{0x0000002000000000L});
	public static final BitSet FOLLOW_numdecl_in_numdecl1392 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ID_in_numdecl1400 = new BitSet(new long[]{0x0000000001000000L});
	public static final BitSet FOLLOW_EQUALS_in_numdecl1402 = new BitSet(new long[]{0x0600002060200000L,0x000071E001340881L});
	public static final BitSet FOLLOW_expr_in_numdecl1407 = new BitSet(new long[]{0x0000000000002002L});
	public static final BitSet FOLLOW_COMMA_in_numdecl1415 = new BitSet(new long[]{0x0000002000000000L});
	public static final BitSet FOLLOW_numdecl_in_numdecl1417 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ID_in_txtdecl1437 = new BitSet(new long[]{0x0000000000002002L});
	public static final BitSet FOLLOW_COMMA_in_txtdecl1444 = new BitSet(new long[]{0x0000002000000000L});
	public static final BitSet FOLLOW_txtdecl_in_txtdecl1446 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ID_in_txtdecl1457 = new BitSet(new long[]{0x0000000001000000L});
	public static final BitSet FOLLOW_EQUALS_in_txtdecl1459 = new BitSet(new long[]{0x0600002060200000L,0x000071E001340881L});
	public static final BitSet FOLLOW_expr_in_txtdecl1463 = new BitSet(new long[]{0x0000000000002002L});
	public static final BitSet FOLLOW_COMMA_in_txtdecl1471 = new BitSet(new long[]{0x0000002000000000L});
	public static final BitSet FOLLOW_txtdecl_in_txtdecl1473 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ID_in_txtlistdecl1493 = new BitSet(new long[]{0x0000000000002002L});
	public static final BitSet FOLLOW_COMMA_in_txtlistdecl1500 = new BitSet(new long[]{0x0000002000000000L});
	public static final BitSet FOLLOW_txtlistdecl_in_txtlistdecl1502 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ID_in_txtlistdecl1512 = new BitSet(new long[]{0x0000000001000000L});
	public static final BitSet FOLLOW_EQUALS_in_txtlistdecl1514 = new BitSet(new long[]{0x0600002060200000L,0x000071E001340881L});
	public static final BitSet FOLLOW_expr_in_txtlistdecl1520 = new BitSet(new long[]{0x0000000000002002L});
	public static final BitSet FOLLOW_COMMA_in_txtlistdecl1528 = new BitSet(new long[]{0x0000002000000000L});
	public static final BitSet FOLLOW_txtlistdecl_in_txtlistdecl1530 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ID_in_numlistdecl1550 = new BitSet(new long[]{0x0000000000002002L});
	public static final BitSet FOLLOW_COMMA_in_numlistdecl1557 = new BitSet(new long[]{0x0000002000000000L});
	public static final BitSet FOLLOW_numlistdecl_in_numlistdecl1559 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ID_in_numlistdecl1569 = new BitSet(new long[]{0x0000000001000000L});
	public static final BitSet FOLLOW_EQUALS_in_numlistdecl1571 = new BitSet(new long[]{0x0600002060200000L,0x000071E001340881L});
	public static final BitSet FOLLOW_expr_in_numlistdecl1576 = new BitSet(new long[]{0x0000000000002002L});
	public static final BitSet FOLLOW_COMMA_in_numlistdecl1584 = new BitSet(new long[]{0x0000002000000000L});
	public static final BitSet FOLLOW_numlistdecl_in_numlistdecl1586 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ID_in_booldecl1606 = new BitSet(new long[]{0x0000000000002002L});
	public static final BitSet FOLLOW_COMMA_in_booldecl1613 = new BitSet(new long[]{0x0000002000000000L});
	public static final BitSet FOLLOW_booldecl_in_booldecl1615 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ID_in_booldecl1625 = new BitSet(new long[]{0x0000000001000000L});
	public static final BitSet FOLLOW_EQUALS_in_booldecl1627 = new BitSet(new long[]{0x0600002060200000L,0x000071E001340881L});
	public static final BitSet FOLLOW_expr_in_booldecl1631 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_PROPERTY_in_propertyDeclaration1649 = new BitSet(new long[]{0x0000002000000000L});
	public static final BitSet FOLLOW_ID_in_propertyDeclaration1653 = new BitSet(new long[]{0x0200000000000000L});
	public static final BitSet FOLLOW_LEFTP_in_propertyDeclaration1655 = new BitSet(new long[]{0x0000000000000600L,0x0000800000000040L});
	public static final BitSet FOLLOW_propertyType_in_propertyDeclaration1659 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000000L});
	public static final BitSet FOLLOW_RIGHTP_in_propertyDeclaration1661 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_TXT_in_propertyType1680 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_TXT_in_propertyType1687 = new BitSet(new long[]{0x0400000000000000L});
	public static final BitSet FOLLOW_LEFTSBR_in_propertyType1689 = new BitSet(new long[]{0x0000000000000000L,0x0000000080000000L});
	public static final BitSet FOLLOW_RIGHTSBR_in_propertyType1691 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_NUM_in_propertyType1698 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_NUM_in_propertyType1706 = new BitSet(new long[]{0x0400000000000000L});
	public static final BitSet FOLLOW_LEFTSBR_in_propertyType1708 = new BitSet(new long[]{0x0000000000000000L,0x0000000080000000L});
	public static final BitSet FOLLOW_RIGHTSBR_in_propertyType1710 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_set_in_propertyType1717 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_partTypeDeclaration_in_typeDeclaration1739 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_TYPE_in_typeDeclaration1746 = new BitSet(new long[]{0x0000002000000000L});
	public static final BitSet FOLLOW_ID_in_typeDeclaration1751 = new BitSet(new long[]{0x0200000000000002L});
	public static final BitSet FOLLOW_LEFTP_in_typeDeclaration1754 = new BitSet(new long[]{0x0000002000000000L,0x0000000040000000L});
	public static final BitSet FOLLOW_listOfIDs_in_typeDeclaration1759 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000000L});
	public static final BitSet FOLLOW_RIGHTP_in_typeDeclaration1764 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_set_in_partTypeDeclaration1783 = new BitSet(new long[]{0x0000002000000000L});
	public static final BitSet FOLLOW_ID_in_partTypeDeclaration1792 = new BitSet(new long[]{0x0200000000000002L});
	public static final BitSet FOLLOW_LEFTP_in_partTypeDeclaration1795 = new BitSet(new long[]{0x0000002000000000L,0x0000000040000000L});
	public static final BitSet FOLLOW_listOfIDs_in_partTypeDeclaration1800 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000000L});
	public static final BitSet FOLLOW_RIGHTP_in_partTypeDeclaration1805 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_COLLECTION_in_containerDeclaration1832 = new BitSet(new long[]{0x0000002000000000L});
	public static final BitSet FOLLOW_ARRAY_in_containerDeclaration1839 = new BitSet(new long[]{0x0400002000000000L});
	public static final BitSet FOLLOW_LEFTSBR_in_containerDeclaration1842 = new BitSet(new long[]{0x0000000000000000L,0x0000000080000000L});
	public static final BitSet FOLLOW_RIGHTSBR_in_containerDeclaration1844 = new BitSet(new long[]{0x0000002000000000L});
	public static final BitSet FOLLOW_ID_in_containerDeclaration1852 = new BitSet(new long[]{0x0200000000000002L});
	public static final BitSet FOLLOW_LEFTP_in_containerDeclaration1857 = new BitSet(new long[]{0x0600082460240E40L,0x0001F1E1413C0EC1L});
	public static final BitSet FOLLOW_list_of_declarations_in_containerDeclaration1860 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000000L});
	public static final BitSet FOLLOW_RIGHTP_in_containerDeclaration1865 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_declarationStatement_in_list_of_declarations1898 = new BitSet(new long[]{0x0000000000002002L});
	public static final BitSet FOLLOW_expr_in_list_of_declarations1905 = new BitSet(new long[]{0x0000000000002002L});
	public static final BitSet FOLLOW_COMMA_in_list_of_declarations1913 = new BitSet(new long[]{0x0600082460240E40L,0x0001F1E1013C0EC1L});
	public static final BitSet FOLLOW_list_of_declarations_in_list_of_declarations1917 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ID_in_instantiation1945 = new BitSet(new long[]{0x0000002000200000L});
	public static final BitSet FOLLOW_dynamic_naming_in_instantiation1951 = new BitSet(new long[]{0x0200000000000002L});
	public static final BitSet FOLLOW_LEFTP_in_instantiation1958 = new BitSet(new long[]{0x0600002060600000L,0x000071E041340881L});
	public static final BitSet FOLLOW_listOfDotValues_in_instantiation1963 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000000L});
	public static final BitSet FOLLOW_listOfValues_in_instantiation1968 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000000L});
	public static final BitSet FOLLOW_RIGHTP_in_instantiation1973 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_DOT_in_listOfDotValues1996 = new BitSet(new long[]{0x0000002000000000L});
	public static final BitSet FOLLOW_ID_in_listOfDotValues2000 = new BitSet(new long[]{0x0200000000000000L});
	public static final BitSet FOLLOW_LEFTP_in_listOfDotValues2006 = new BitSet(new long[]{0x0600002060200000L,0x000071E001340881L});
	public static final BitSet FOLLOW_expr_in_listOfDotValues2010 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000000L});
	public static final BitSet FOLLOW_RIGHTP_in_listOfDotValues2018 = new BitSet(new long[]{0x0000000000002002L});
	public static final BitSet FOLLOW_COMMA_in_listOfDotValues2021 = new BitSet(new long[]{0x0000000000400000L});
	public static final BitSet FOLLOW_DOT_in_listOfDotValues2023 = new BitSet(new long[]{0x0000002000000000L});
	public static final BitSet FOLLOW_ID_in_listOfDotValues2027 = new BitSet(new long[]{0x0200000000000000L});
	public static final BitSet FOLLOW_LEFTP_in_listOfDotValues2035 = new BitSet(new long[]{0x0600002060200000L,0x000071E001340881L});
	public static final BitSet FOLLOW_expr_in_listOfDotValues2039 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000000L});
	public static final BitSet FOLLOW_RIGHTP_in_listOfDotValues2049 = new BitSet(new long[]{0x0000000000002002L});
	public static final BitSet FOLLOW_expr_in_listOfValues2070 = new BitSet(new long[]{0x0000000000002002L});
	public static final BitSet FOLLOW_COMMA_in_listOfValues2076 = new BitSet(new long[]{0x0600002060200000L,0x000071E001340881L});
	public static final BitSet FOLLOW_expr_in_listOfValues2082 = new BitSet(new long[]{0x0000000000002002L});
	public static final BitSet FOLLOW_DEVICE_in_deviceDeclaration2101 = new BitSet(new long[]{0x0000002000000000L});
	public static final BitSet FOLLOW_ID_in_deviceDeclaration2105 = new BitSet(new long[]{0x0200000000000002L});
	public static final BitSet FOLLOW_LEFTP_in_deviceDeclaration2108 = new BitSet(new long[]{0x0400002000000000L,0x0000000040002001L});
	public static final BitSet FOLLOW_deviceComponents_in_deviceDeclaration2113 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000000L});
	public static final BitSet FOLLOW_RIGHTP_in_deviceDeclaration2118 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_selection_in_deviceComponents2149 = new BitSet(new long[]{0x0000000000002002L});
	public static final BitSet FOLLOW_COMMA_in_deviceComponents2155 = new BitSet(new long[]{0x0400002000000000L,0x0000000000002001L});
	public static final BitSet FOLLOW_deviceComponents_in_deviceComponents2159 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_LEFTSBR_in_selection2183 = new BitSet(new long[]{0x0000002000000000L,0x0000000000002001L});
	public static final BitSet FOLLOW_selection_list_in_selection2187 = new BitSet(new long[]{0x0000000000000000L,0x0000000080000000L});
	public static final BitSet FOLLOW_RIGHTSBR_in_selection2190 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_device_component_in_selection2199 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_device_component_in_selection_list2228 = new BitSet(new long[]{0x0000000000000002L,0x0000000000001000L});
	public static final BitSet FOLLOW_PIPE_in_selection_list2234 = new BitSet(new long[]{0x0000002000000000L,0x0000000000002001L});
	public static final BitSet FOLLOW_selection_list_in_selection_list2238 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_set_in_device_component2264 = new BitSet(new long[]{0x0000002000000000L});
	public static final BitSet FOLLOW_ID_in_device_component2274 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_lhs_assignment_in_assignment2294 = new BitSet(new long[]{0x0000000001000000L});
	public static final BitSet FOLLOW_EQUALS_in_assignment2297 = new BitSet(new long[]{0x060001A160200020L,0x000071E805340881L});
	public static final BitSet FOLLOW_AMP_in_assignment2302 = new BitSet(new long[]{0x060001A160200000L,0x000071E805340881L});
	public static final BitSet FOLLOW_rhs_assignment_in_assignment2308 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ID_in_lhs_assignment2323 = new BitSet(new long[]{0x0400000000400000L});
	public static final BitSet FOLLOW_lhs_access_in_lhs_assignment2325 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_DOT_in_lhs_access2345 = new BitSet(new long[]{0x0000002000000000L});
	public static final BitSet FOLLOW_ID_in_lhs_access2349 = new BitSet(new long[]{0x0400000000400000L});
	public static final BitSet FOLLOW_LEFTSBR_in_lhs_access2353 = new BitSet(new long[]{0x0000002000000000L,0x0000000000000080L});
	public static final BitSet FOLLOW_set_in_lhs_access2355 = new BitSet(new long[]{0x0000000000000000L,0x0000000080000000L});
	public static final BitSet FOLLOW_RIGHTSBR_in_lhs_access2361 = new BitSet(new long[]{0x0400000000400000L});
	public static final BitSet FOLLOW_lhs_access_in_lhs_access2364 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_dataExchange_in_rhs_assignment2391 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_expr_in_rhs_assignment2401 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ID_in_listOfIDs2429 = new BitSet(new long[]{0x0000000000002002L});
	public static final BitSet FOLLOW_COMMA_in_listOfIDs2438 = new BitSet(new long[]{0x0000002000000000L});
	public static final BitSet FOLLOW_listOfIDs_in_listOfIDs2442 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_RULE_in_ruleDeclaration2466 = new BitSet(new long[]{0x0000002000000000L});
	public static final BitSet FOLLOW_ID_in_ruleDeclaration2470 = new BitSet(new long[]{0x0200000000000000L});
	public static final BitSet FOLLOW_LEFTP_in_ruleDeclaration2472 = new BitSet(new long[]{0x065C002000000000L,0x0B80100001000181L,0xFFFFFFBFFFFFFFF8L,0x000000000001DFFFL});
	public static final BitSet FOLLOW_set_in_ruleDeclaration2477 = new BitSet(new long[]{0x0000002000000000L});
	public static final BitSet FOLLOW_ID_in_ruleDeclaration2485 = new BitSet(new long[]{0x0000000000001000L});
	public static final BitSet FOLLOW_COLON_in_ruleDeclaration2487 = new BitSet(new long[]{0x064C002000000000L,0x0980100001000181L,0xFFFFFFBFFFFFFFF8L,0x000000000001DFFFL});
	public static final BitSet FOLLOW_cnf_rule_in_ruleDeclaration2495 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000000L});
	public static final BitSet FOLLOW_RIGHTP_in_ruleDeclaration2500 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ruleOperators_in_ruleOperator2514 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_EQUALS_in_relationalOperators2893 = new BitSet(new long[]{0x0000000001000000L});
	public static final BitSet FOLLOW_EQUALS_in_relationalOperators2895 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_NEQUAL_in_relationalOperators2900 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_LTHAN_in_relationalOperators2905 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_GTHAN_in_relationalOperators2910 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_LEQUAL_in_relationalOperators2915 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_GEQUAL_in_relationalOperators2920 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_set_in_relationalOperators2925 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_set_in_relationalOperators2934 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_set_in_relationalOperators2943 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_set_in_relationalOperators2952 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_set_in_relationalOperators2961 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_set_in_relationalOperators2970 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_set_in_relationalOperators2979 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_set_in_relationalOperators2988 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_set_in_relationalOperators2997 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_or_predicate_in_cnf_rule3021 = new BitSet(new long[]{0x2000100000000002L,0x0002000000000000L});
	public static final BitSet FOLLOW_set_in_cnf_rule3029 = new BitSet(new long[]{0x064C002000000000L,0x0980100001000181L,0xFFFFFFBFFFFFFFF8L,0x000000000001DFFFL});
	public static final BitSet FOLLOW_cnf_rule_in_cnf_rule3039 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_negated_predicate_in_or_predicate3069 = new BitSet(new long[]{0x4020000000000002L,0x0400000000000000L});
	public static final BitSet FOLLOW_set_in_or_predicate3075 = new BitSet(new long[]{0x064C002000000000L,0x0980100001000181L,0xFFFFFFBFFFFFFFF8L,0x000000000001DFFFL});
	public static final BitSet FOLLOW_negated_predicate_in_or_predicate3085 = new BitSet(new long[]{0x4020000000000002L,0x0400000000000000L});
	public static final BitSet FOLLOW_set_in_negated_predicate3113 = new BitSet(new long[]{0x0644002000000000L,0x0880100001000081L,0xFFFFFFBFFFFFFFF8L,0x000000000001DFFFL});
	public static final BitSet FOLLOW_predicate_in_negated_predicate3123 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_predicate_in_negated_predicate3133 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_operand_in_predicate3160 = new BitSet(new long[]{0x0044000000000000L,0x0880000000000000L,0xFFFFFFBFFFFFFFF8L,0x000000000001DFFFL});
	public static final BitSet FOLLOW_ruleOperator_in_predicate3170 = new BitSet(new long[]{0x0400002000000002L,0x0000000000000080L});
	public static final BitSet FOLLOW_operand_in_predicate3179 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ID_in_predicate3193 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_expressionRule_in_predicate3202 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ID_in_operand3233 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_NUMBER_in_operand3242 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_LEFTSBR_in_operand3249 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000080L});
	public static final BitSet FOLLOW_NUMBER_in_operand3253 = new BitSet(new long[]{0x0000000000000000L,0x0000000080000000L});
	public static final BitSet FOLLOW_RIGHTSBR_in_operand3255 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_expression_in_expressionRule3281 = new BitSet(new long[]{0x8800000A01000000L,0x0000000000000008L,0x64D000C002C9A000L,0x0000000000006001L});
	public static final BitSet FOLLOW_exp_op_in_expressionRule3286 = new BitSet(new long[]{0x0200002000000000L,0x0000100001000081L});
	public static final BitSet FOLLOW_expression_in_expressionRule3291 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_exp_operand_in_expression3315 = new BitSet(new long[]{0x0000000000100002L,0x0000000000002005L});
	public static final BitSet FOLLOW_exp_operator_in_expression3324 = new BitSet(new long[]{0x0200002000000000L,0x0000100001000081L});
	public static final BitSet FOLLOW_expression_in_expression3329 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_LEFTP_in_expression3341 = new BitSet(new long[]{0x0200002000000000L,0x0000100001000081L});
	public static final BitSet FOLLOW_expression_in_expression3343 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000000L});
	public static final BitSet FOLLOW_RIGHTP_in_expression3346 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_PLUS_in_exp_operator3365 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_MINUS_in_exp_operator3373 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_MULT_in_exp_operator3380 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_DIV_in_exp_operator3387 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ID_in_exp_operand3417 = new BitSet(new long[]{0x0000000000400000L});
	public static final BitSet FOLLOW_DOT_in_exp_operand3419 = new BitSet(new long[]{0x0000002000000000L});
	public static final BitSet FOLLOW_ID_in_exp_operand3428 = new BitSet(new long[]{0x0400000000000002L});
	public static final BitSet FOLLOW_LEFTSBR_in_exp_operand3434 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000080L});
	public static final BitSet FOLLOW_NUMBER_in_exp_operand3438 = new BitSet(new long[]{0x0000000000000000L,0x0000000080000000L});
	public static final BitSet FOLLOW_RIGHTSBR_in_exp_operand3440 = new BitSet(new long[]{0x0400000000000002L});
	public static final BitSet FOLLOW_NUMBER_in_exp_operand3452 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_MINUS_in_exp_operand3459 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000080L});
	public static final BitSet FOLLOW_NUMBER_in_exp_operand3463 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_REAL_in_exp_operand3472 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_MINUS_in_exp_operand3479 = new BitSet(new long[]{0x0000000000000000L,0x0000000001000000L});
	public static final BitSet FOLLOW_REAL_in_exp_operand3483 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_STRING_in_exp_operand3492 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_relationalOperators_in_exp_op3519 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_GRAMMAR_in_grammarDeclaration3538 = new BitSet(new long[]{0x0000002000000000L});
	public static final BitSet FOLLOW_ID_in_grammarDeclaration3542 = new BitSet(new long[]{0x0200000000000000L});
	public static final BitSet FOLLOW_LEFTP_in_grammarDeclaration3544 = new BitSet(new long[]{0x0000002000000000L});
	public static final BitSet FOLLOW_list_of_production_rules_in_grammarDeclaration3546 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000000L});
	public static final BitSet FOLLOW_RIGHTP_in_grammarDeclaration3549 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_production_rule_in_list_of_production_rules3561 = new BitSet(new long[]{0x0000000000000000L,0x0000001000000000L});
	public static final BitSet FOLLOW_SEMIC_in_list_of_production_rules3564 = new BitSet(new long[]{0x0000002000000002L});
	public static final BitSet FOLLOW_list_of_production_rules_in_list_of_production_rules3567 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ID_in_production_rule3587 = new BitSet(new long[]{0x0000000000000080L});
	public static final BitSet FOLLOW_ARROW_in_production_rule3591 = new BitSet(new long[]{0x0000002000000000L});
	public static final BitSet FOLLOW_right_hand_side_in_production_rule3593 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ID_in_right_hand_side3609 = new BitSet(new long[]{0x0000000000002002L});
	public static final BitSet FOLLOW_COMMA_in_right_hand_side3614 = new BitSet(new long[]{0x0000002000000000L});
	public static final BitSet FOLLOW_right_hand_side_in_right_hand_side3616 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_interaction_in_right_hand_side3624 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_interaction_in_interactionDeclaration3649 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_INTERACTION_in_interactionDeclaration3657 = new BitSet(new long[]{0x0000002000000000L});
	public static final BitSet FOLLOW_ID_in_interactionDeclaration3661 = new BitSet(new long[]{0x0200000000000000L});
	public static final BitSet FOLLOW_LEFTP_in_interactionDeclaration3663 = new BitSet(new long[]{0x0000002000000000L});
	public static final BitSet FOLLOW_interaction_in_interactionDeclaration3667 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000000L});
	public static final BitSet FOLLOW_RIGHTP_in_interactionDeclaration3670 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ID_in_interaction3693 = new BitSet(new long[]{0x0044000000000000L,0x0880000000000000L});
	public static final BitSet FOLLOW_interactionType_in_interaction3697 = new BitSet(new long[]{0x0000002000000000L});
	public static final BitSet FOLLOW_ID_in_interaction3702 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ID_in_interaction3711 = new BitSet(new long[]{0x0044000000000000L,0x0880000000000000L});
	public static final BitSet FOLLOW_interactionType_in_interaction3715 = new BitSet(new long[]{0x0200000000000000L});
	public static final BitSet FOLLOW_LEFTP_in_interaction3718 = new BitSet(new long[]{0x0000002000000000L});
	public static final BitSet FOLLOW_interaction_in_interaction3722 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000000L});
	public static final BitSet FOLLOW_RIGHTP_in_interaction3725 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_set_in_interactionType3745 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_set_in_interactionType3758 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_set_in_printStatement3784 = new BitSet(new long[]{0x0200000000000000L});
	public static final BitSet FOLLOW_LEFTP_in_printStatement3790 = new BitSet(new long[]{0x0600002060200000L,0x000071E001340881L});
	public static final BitSet FOLLOW_toPrint_in_printStatement3794 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000000L});
	public static final BitSet FOLLOW_RIGHTP_in_printStatement3797 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_set_in_printStatement3804 = new BitSet(new long[]{0x0200000000000000L});
	public static final BitSet FOLLOW_LEFTP_in_printStatement3810 = new BitSet(new long[]{0x0600002060200000L,0x000071E001340881L});
	public static final BitSet FOLLOW_toPrint_in_printStatement3814 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000000L});
	public static final BitSet FOLLOW_RIGHTP_in_printStatement3817 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_expr_in_toPrint3838 = new BitSet(new long[]{0x0000000000002000L});
	public static final BitSet FOLLOW_toPrint_prime_in_toPrint3843 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_COMMA_in_toPrint_prime3869 = new BitSet(new long[]{0x0600002060200000L,0x000071E001340881L});
	public static final BitSet FOLLOW_toPrint_in_toPrint_prime3873 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_if_elseif_else_in_imperativeStatements3898 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_forall_iterator_in_imperativeStatements3904 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_for_loop_in_imperativeStatements3910 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_while_loop_in_imperativeStatements3916 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_set_in_if_elseif_else3948 = new BitSet(new long[]{0x0200000000000000L});
	public static final BitSet FOLLOW_LEFTP_in_if_elseif_else3954 = new BitSet(new long[]{0x0608002060200000L,0x010071E001340981L});
	public static final BitSet FOLLOW_logical_condition_in_if_elseif_else3958 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000000L});
	public static final BitSet FOLLOW_RIGHTP_in_if_elseif_else3961 = new BitSet(new long[]{0x0100000000000000L});
	public static final BitSet FOLLOW_LEFTCUR_in_if_elseif_else3963 = new BitSet(new long[]{0x00838FB50604CE40L,0x107187EF1C3FCE40L});
	public static final BitSet FOLLOW_list_of_statements_in_if_elseif_else3971 = new BitSet(new long[]{0x0000000000000000L,0x0000000020000000L});
	public static final BitSet FOLLOW_RIGHTCUR_in_if_elseif_else3974 = new BitSet(new long[]{0x0000600000000002L,0x000C000000000000L});
	public static final BitSet FOLLOW_set_in_if_elseif_else3989 = new BitSet(new long[]{0x0200000000000000L});
	public static final BitSet FOLLOW_LEFTP_in_if_elseif_else3995 = new BitSet(new long[]{0x0608002060200000L,0x010071E001340981L});
	public static final BitSet FOLLOW_logical_condition_in_if_elseif_else3999 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000000L});
	public static final BitSet FOLLOW_RIGHTP_in_if_elseif_else4002 = new BitSet(new long[]{0x0100000000000000L});
	public static final BitSet FOLLOW_LEFTCUR_in_if_elseif_else4004 = new BitSet(new long[]{0x00838FB50604CE40L,0x107187EF1C3FCE40L});
	public static final BitSet FOLLOW_list_of_statements_in_if_elseif_else4012 = new BitSet(new long[]{0x0000000000000000L,0x0000000020000000L});
	public static final BitSet FOLLOW_RIGHTCUR_in_if_elseif_else4015 = new BitSet(new long[]{0x0000600000000002L,0x000C000000000000L});
	public static final BitSet FOLLOW_set_in_if_elseif_else4031 = new BitSet(new long[]{0x0100000000000000L});
	public static final BitSet FOLLOW_LEFTCUR_in_if_elseif_else4037 = new BitSet(new long[]{0x00838FB50604CE40L,0x107187EF1C3FCE40L});
	public static final BitSet FOLLOW_list_of_statements_in_if_elseif_else4045 = new BitSet(new long[]{0x0000000000000000L,0x0000000020000000L});
	public static final BitSet FOLLOW_RIGHTCUR_in_if_elseif_else4048 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_set_in_forall_iterator4070 = new BitSet(new long[]{0x0000002000000000L});
	public static final BitSet FOLLOW_ID_in_forall_iterator4079 = new BitSet(new long[]{0x0000000000001000L});
	public static final BitSet FOLLOW_COLON_in_forall_iterator4081 = new BitSet(new long[]{0x0000002000000000L});
	public static final BitSet FOLLOW_ID_in_forall_iterator4087 = new BitSet(new long[]{0x0100000000000000L});
	public static final BitSet FOLLOW_LEFTCUR_in_forall_iterator4089 = new BitSet(new long[]{0x00838FB50604CE40L,0x107187EF1C3FCE40L});
	public static final BitSet FOLLOW_list_of_statements_in_forall_iterator4096 = new BitSet(new long[]{0x0000000000000000L,0x0000000020000000L});
	public static final BitSet FOLLOW_RIGHTCUR_in_forall_iterator4103 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_set_in_for_loop4120 = new BitSet(new long[]{0x0200000000000000L});
	public static final BitSet FOLLOW_LEFTP_in_for_loop4126 = new BitSet(new long[]{0x0000000000000600L,0x0000800000000040L});
	public static final BitSet FOLLOW_variableDeclaration_in_for_loop4130 = new BitSet(new long[]{0x0000000000000000L,0x0000001000000000L});
	public static final BitSet FOLLOW_SEMIC_in_for_loop4133 = new BitSet(new long[]{0x0608002060200000L,0x010071E001340981L});
	public static final BitSet FOLLOW_logical_condition_in_for_loop4137 = new BitSet(new long[]{0x0000000000000000L,0x0000001000000000L});
	public static final BitSet FOLLOW_SEMIC_in_for_loop4140 = new BitSet(new long[]{0x0000002000000000L,0x0000000040000000L});
	public static final BitSet FOLLOW_assignment_in_for_loop4145 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000000L});
	public static final BitSet FOLLOW_RIGHTP_in_for_loop4150 = new BitSet(new long[]{0x0100000000000000L});
	public static final BitSet FOLLOW_LEFTCUR_in_for_loop4152 = new BitSet(new long[]{0x00838FB50604CE40L,0x107187EF1C3FCE40L});
	public static final BitSet FOLLOW_list_of_statements_in_for_loop4160 = new BitSet(new long[]{0x0000000000000000L,0x0000000020000000L});
	public static final BitSet FOLLOW_RIGHTCUR_in_for_loop4167 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_set_in_while_loop4186 = new BitSet(new long[]{0x0200000000000000L});
	public static final BitSet FOLLOW_LEFTP_in_while_loop4192 = new BitSet(new long[]{0x0608002060200000L,0x010071E001340981L});
	public static final BitSet FOLLOW_logical_condition_in_while_loop4196 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000000L});
	public static final BitSet FOLLOW_RIGHTP_in_while_loop4199 = new BitSet(new long[]{0x0100000000000000L});
	public static final BitSet FOLLOW_LEFTCUR_in_while_loop4201 = new BitSet(new long[]{0x00838FB50604CE40L,0x107187EF1C3FCE40L});
	public static final BitSet FOLLOW_list_of_statements_in_while_loop4209 = new BitSet(new long[]{0x0000000000000000L,0x0000000020000000L});
	public static final BitSet FOLLOW_RIGHTCUR_in_while_loop4216 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_logical_or_condition_in_logical_condition4242 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_logical_or_condition_in_logical_not_condition4267 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_logical_and_condition_in_logical_or_condition4292 = new BitSet(new long[]{0x4020000000000002L,0x0400000000001000L});
	public static final BitSet FOLLOW_LC_OR_in_logical_or_condition4299 = new BitSet(new long[]{0x0608002060200000L,0x010071E001340981L});
	public static final BitSet FOLLOW_UC_OR_in_logical_or_condition4301 = new BitSet(new long[]{0x0608002060200000L,0x010071E001340981L});
	public static final BitSet FOLLOW_LOG_OR_in_logical_or_condition4303 = new BitSet(new long[]{0x0608002060200000L,0x010071E001340981L});
	public static final BitSet FOLLOW_PIPE_in_logical_or_condition4305 = new BitSet(new long[]{0x0608002060200000L,0x010071E001341981L});
	public static final BitSet FOLLOW_PIPE_in_logical_or_condition4308 = new BitSet(new long[]{0x0608002060200000L,0x010071E001340981L});
	public static final BitSet FOLLOW_logical_or_condition_in_logical_or_condition4315 = new BitSet(new long[]{0x4020000000000002L,0x0400000000001000L});
	public static final BitSet FOLLOW_atomic_condition_in_logical_and_condition4340 = new BitSet(new long[]{0x2000100000000022L,0x0002000000000000L});
	public static final BitSet FOLLOW_LC_AND_in_logical_and_condition4347 = new BitSet(new long[]{0x0608002060200000L,0x010071E001340981L});
	public static final BitSet FOLLOW_UC_AND_in_logical_and_condition4349 = new BitSet(new long[]{0x0608002060200000L,0x010071E001340981L});
	public static final BitSet FOLLOW_LOG_AND_in_logical_and_condition4351 = new BitSet(new long[]{0x0608002060200000L,0x010071E001340981L});
	public static final BitSet FOLLOW_AMP_in_logical_and_condition4353 = new BitSet(new long[]{0x0608002060200020L,0x010071E001340981L});
	public static final BitSet FOLLOW_AMP_in_logical_and_condition4356 = new BitSet(new long[]{0x0608002060200000L,0x010071E001340981L});
	public static final BitSet FOLLOW_logical_and_condition_in_logical_and_condition4363 = new BitSet(new long[]{0x2000100000000022L,0x0002000000000000L});
	public static final BitSet FOLLOW_expr_in_atomic_condition4391 = new BitSet(new long[]{0x8800000A01000000L,0x0000000000000008L,0x64D000C002C9A000L,0x0000000000006001L});
	public static final BitSet FOLLOW_relationalOperators_in_atomic_condition4396 = new BitSet(new long[]{0x0600002060200000L,0x000071E001340881L});
	public static final BitSet FOLLOW_expr_in_atomic_condition4400 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_set_in_atomic_condition4408 = new BitSet(new long[]{0x0200000000000000L});
	public static final BitSet FOLLOW_LEFTP_in_atomic_condition4416 = new BitSet(new long[]{0x0608002060200000L,0x010071E001340981L});
	public static final BitSet FOLLOW_atomic_condition_in_atomic_condition4420 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000000L});
	public static final BitSet FOLLOW_RIGHTP_in_atomic_condition4423 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_multExpr_in_expr4450 = new BitSet(new long[]{0x0000000000000002L,0x0000000000002001L});
	public static final BitSet FOLLOW_set_in_expr4459 = new BitSet(new long[]{0x0600002060200000L,0x000071E001340881L});
	public static final BitSet FOLLOW_multExpr_in_expr4467 = new BitSet(new long[]{0x0000000000000002L,0x0000000000002001L});
	public static final BitSet FOLLOW_atom_in_multExpr4497 = new BitSet(new long[]{0x0000000000100002L,0x0000000000000004L});
	public static final BitSet FOLLOW_MULT_in_multExpr4508 = new BitSet(new long[]{0x0600002060200000L,0x000071E001340881L});
	public static final BitSet FOLLOW_DIV_in_multExpr4512 = new BitSet(new long[]{0x0600002060200000L,0x000071E001340881L});
	public static final BitSet FOLLOW_atom_in_multExpr4517 = new BitSet(new long[]{0x0000000000100002L,0x0000000000000004L});
	public static final BitSet FOLLOW_NUMBER_in_atom4544 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_REAL_in_atom4550 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_MINUS_in_atom4560 = new BitSet(new long[]{0x0000000000000000L,0x0000000001000080L});
	public static final BitSet FOLLOW_NUMBER_in_atom4565 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_REAL_in_atom4571 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_set_in_atom4584 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_set_in_atom4594 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_dynamic_naming_in_atom4610 = new BitSet(new long[]{0x0400000000400000L});
	public static final BitSet FOLLOW_object_access_in_atom4617 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_STRING_in_atom4626 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_LEFTP_in_atom4634 = new BitSet(new long[]{0x0600002060200000L,0x000071E001340881L});
	public static final BitSet FOLLOW_expr_in_atom4636 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000000L});
	public static final BitSet FOLLOW_RIGHTP_in_atom4639 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_LEFTSBR_in_atom4648 = new BitSet(new long[]{0x0600002060200000L,0x000071E001340881L});
	public static final BitSet FOLLOW_list_in_atom4650 = new BitSet(new long[]{0x0000000000000000L,0x0000000080000000L});
	public static final BitSet FOLLOW_RIGHTSBR_in_atom4653 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_built_in_function_in_atom4663 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_function_call_in_atom4673 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_expr_in_list4696 = new BitSet(new long[]{0x0000000000002002L});
	public static final BitSet FOLLOW_COMMA_in_list4703 = new BitSet(new long[]{0x0600002060200000L,0x000071E001340881L});
	public static final BitSet FOLLOW_expr_in_list4707 = new BitSet(new long[]{0x0000000000002002L});
	public static final BitSet FOLLOW_set_in_built_in_function4735 = new BitSet(new long[]{0x0200000000000000L});
	public static final BitSet FOLLOW_LEFTP_in_built_in_function4745 = new BitSet(new long[]{0x0600002060200000L,0x000071E001340881L});
	public static final BitSet FOLLOW_expr_in_built_in_function4749 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000000L});
	public static final BitSet FOLLOW_RIGHTP_in_built_in_function4752 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_set_in_built_in_function4759 = new BitSet(new long[]{0x0200000000000000L});
	public static final BitSet FOLLOW_LEFTP_in_built_in_function4765 = new BitSet(new long[]{0x0600002060200000L,0x000071E001340881L});
	public static final BitSet FOLLOW_range_in_built_in_function4769 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000000L});
	public static final BitSet FOLLOW_RIGHTP_in_built_in_function4772 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_PRODUCT_in_built_in_function4782 = new BitSet(new long[]{0x0200000000000000L});
	public static final BitSet FOLLOW_PERMUTE_in_built_in_function4786 = new BitSet(new long[]{0x0200000000000000L});
	public static final BitSet FOLLOW_LEFTP_in_built_in_function4789 = new BitSet(new long[]{0x0000002000000000L});
	public static final BitSet FOLLOW_ID_in_built_in_function4793 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000000L});
	public static final BitSet FOLLOW_RIGHTP_in_built_in_function4795 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_set_in_stand_alone_function4812 = new BitSet(new long[]{0x0200000000000000L});
	public static final BitSet FOLLOW_LEFTP_in_stand_alone_function4826 = new BitSet(new long[]{0x0600002060200000L,0x000071E001340881L});
	public static final BitSet FOLLOW_expr_in_stand_alone_function4830 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000000L});
	public static final BitSet FOLLOW_RIGHTP_in_stand_alone_function4833 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_set_in_stand_alone_function4840 = new BitSet(new long[]{0x0200000000000002L});
	public static final BitSet FOLLOW_LEFTP_in_stand_alone_function4849 = new BitSet(new long[]{0x0600002060200000L,0x000071E001340881L});
	public static final BitSet FOLLOW_toPrint_in_stand_alone_function4853 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000000L});
	public static final BitSet FOLLOW_RIGHTP_in_stand_alone_function4856 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_expr_in_range4880 = new BitSet(new long[]{0x0000000000002000L});
	public static final BitSet FOLLOW_COMMA_in_range4883 = new BitSet(new long[]{0x0600002060200000L,0x000071E001340881L});
	public static final BitSet FOLLOW_expr_in_range4887 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_DOT_in_object_access4923 = new BitSet(new long[]{0x0000002000000000L,0x0000018000000000L});
	public static final BitSet FOLLOW_ID_in_object_access4928 = new BitSet(new long[]{0x0400000000400000L});
	public static final BitSet FOLLOW_set_in_object_access4934 = new BitSet(new long[]{0x0600000000400000L});
	public static final BitSet FOLLOW_LEFTP_in_object_access4941 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000000L});
	public static final BitSet FOLLOW_RIGHTP_in_object_access4943 = new BitSet(new long[]{0x0400000000400000L});
	public static final BitSet FOLLOW_LEFTSBR_in_object_access4953 = new BitSet(new long[]{0x0600002060200000L,0x000071E001340881L});
	public static final BitSet FOLLOW_expr_in_object_access4958 = new BitSet(new long[]{0x0000000000000000L,0x0000000080000000L});
	public static final BitSet FOLLOW_RIGHTSBR_in_object_access4962 = new BitSet(new long[]{0x0400000000400000L});
	public static final BitSet FOLLOW_object_access_in_object_access4969 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ID_in_dynamic_naming4994 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_DOLLAR_in_dynamic_naming5001 = new BitSet(new long[]{0x0100000000000000L});
	public static final BitSet FOLLOW_LEFTCUR_in_dynamic_naming5003 = new BitSet(new long[]{0x0600002060200000L,0x000071E001340881L});
	public static final BitSet FOLLOW_expr_in_dynamic_naming5007 = new BitSet(new long[]{0x0000000000000000L,0x0000000020000000L});
	public static final BitSet FOLLOW_RIGHTCUR_in_dynamic_naming5010 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_sbolStatement_in_dataExchange5035 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_importStatement_in_dataExchange5045 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_genbankStatement_in_dataExchange5055 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_registryStatement_in_dataExchange5065 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_HASHMARK_in_includeStatement5084 = new BitSet(new long[]{0x0000060000000000L});
	public static final BitSet FOLLOW_set_in_includeStatement5088 = new BitSet(new long[]{0x0000000000000000L,0x0000100000000000L});
	public static final BitSet FOLLOW_STRING_in_includeStatement5096 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_set_in_importStatement5117 = new BitSet(new long[]{0x0200000000000000L});
	public static final BitSet FOLLOW_LEFTP_in_importStatement5123 = new BitSet(new long[]{0x0000000000000000L,0x0000100000000000L});
	public static final BitSet FOLLOW_STRING_in_importStatement5127 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000000L});
	public static final BitSet FOLLOW_RIGHTP_in_importStatement5131 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_SBOL_in_sbolStatement5153 = new BitSet(new long[]{0x0000000000400000L});
	public static final BitSet FOLLOW_DOT_in_sbolStatement5155 = new BitSet(new long[]{0x0000018018000000L,0x0000000000000000L,0x0000000000000003L});
	public static final BitSet FOLLOW_sbolExportStatement_in_sbolStatement5158 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_sbolImportStatement_in_sbolStatement5165 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_sbolVisualStatement_in_sbolStatement5173 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_set_in_sbolExportStatement5190 = new BitSet(new long[]{0x0200000000000000L});
	public static final BitSet FOLLOW_LEFTP_in_sbolExportStatement5196 = new BitSet(new long[]{0x0000002000000000L});
	public static final BitSet FOLLOW_ID_in_sbolExportStatement5200 = new BitSet(new long[]{0x0000000000002000L});
	public static final BitSet FOLLOW_COMMA_in_sbolExportStatement5202 = new BitSet(new long[]{0x0000000000000000L,0x0000100000000000L});
	public static final BitSet FOLLOW_STRING_in_sbolExportStatement5206 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000000L});
	public static final BitSet FOLLOW_RIGHTP_in_sbolExportStatement5208 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_set_in_sbolImportStatement5231 = new BitSet(new long[]{0x0200000000000000L});
	public static final BitSet FOLLOW_LEFTP_in_sbolImportStatement5237 = new BitSet(new long[]{0x0000000000000000L,0x0000100000000000L});
	public static final BitSet FOLLOW_STRING_in_sbolImportStatement5241 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000000L});
	public static final BitSet FOLLOW_RIGHTP_in_sbolImportStatement5243 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_set_in_sbolVisualStatement5259 = new BitSet(new long[]{0x0200000000000000L});
	public static final BitSet FOLLOW_LEFTP_in_sbolVisualStatement5265 = new BitSet(new long[]{0x0000002000000000L});
	public static final BitSet FOLLOW_ID_in_sbolVisualStatement5269 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000000L});
	public static final BitSet FOLLOW_RIGHTP_in_sbolVisualStatement5271 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_GENBANK_in_genbankStatement5296 = new BitSet(new long[]{0x0000000000400000L});
	public static final BitSet FOLLOW_DOT_in_genbankStatement5298 = new BitSet(new long[]{0x0000018018000000L});
	public static final BitSet FOLLOW_genbankImportStatement_in_genbankStatement5303 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_genbankExportStatement_in_genbankStatement5311 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_set_in_genbankExportStatement5330 = new BitSet(new long[]{0x0200000000000000L});
	public static final BitSet FOLLOW_LEFTP_in_genbankExportStatement5336 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000000L});
	public static final BitSet FOLLOW_RIGHTP_in_genbankExportStatement5338 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_set_in_genbankImportStatement5364 = new BitSet(new long[]{0x0200000000000000L});
	public static final BitSet FOLLOW_LEFTP_in_genbankImportStatement5370 = new BitSet(new long[]{0x0000000000000000L,0x0000100000000000L});
	public static final BitSet FOLLOW_STRING_in_genbankImportStatement5374 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000000L});
	public static final BitSet FOLLOW_RIGHTP_in_genbankImportStatement5376 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_set_in_genbankImportStatement5383 = new BitSet(new long[]{0x0200000000000000L});
	public static final BitSet FOLLOW_LEFTP_in_genbankImportStatement5389 = new BitSet(new long[]{0x0000002000000000L});
	public static final BitSet FOLLOW_ID_in_genbankImportStatement5393 = new BitSet(new long[]{0x0000000000002000L});
	public static final BitSet FOLLOW_COMMA_in_genbankImportStatement5395 = new BitSet(new long[]{0x0000000000000000L,0x0000100000000000L});
	public static final BitSet FOLLOW_STRING_in_genbankImportStatement5399 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000000L});
	public static final BitSet FOLLOW_RIGHTP_in_genbankImportStatement5401 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_REGISTRY_in_registryStatement5425 = new BitSet(new long[]{0x0000000000400000L});
	public static final BitSet FOLLOW_DOT_in_registryStatement5427 = new BitSet(new long[]{0x0000018000000000L});
	public static final BitSet FOLLOW_set_in_registryStatement5429 = new BitSet(new long[]{0x0200000000000000L});
	public static final BitSet FOLLOW_LEFTP_in_registryStatement5435 = new BitSet(new long[]{0x0000000000000000L,0x0000100000000000L});
	public static final BitSet FOLLOW_STRING_in_registryStatement5439 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000000L});
	public static final BitSet FOLLOW_RIGHTP_in_registryStatement5441 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ASSERT_in_testStatements5460 = new BitSet(new long[]{0x0200000000000000L});
	public static final BitSet FOLLOW_LEFTP_in_testStatements5462 = new BitSet(new long[]{0x0000002000000000L});
	public static final BitSet FOLLOW_ID_in_testStatements5466 = new BitSet(new long[]{0x0000000000400000L});
	public static final BitSet FOLLOW_DOT_in_testStatements5468 = new BitSet(new long[]{0x0000000000000000L,0x0000018000000000L});
	public static final BitSet FOLLOW_set_in_testStatements5470 = new BitSet(new long[]{0x0200000000000000L});
	public static final BitSet FOLLOW_LEFTP_in_testStatements5476 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000000L});
	public static final BitSet FOLLOW_RIGHTP_in_testStatements5478 = new BitSet(new long[]{0x0000000001000000L});
	public static final BitSet FOLLOW_EQUALS_in_testStatements5480 = new BitSet(new long[]{0x0000000001000000L});
	public static final BitSet FOLLOW_EQUALS_in_testStatements5482 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000080L});
	public static final BitSet FOLLOW_NUMBER_in_testStatements5486 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000000L});
	public static final BitSet FOLLOW_RIGHTP_in_testStatements5488 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_NOTE_in_testStatements5496 = new BitSet(new long[]{0x0200000000000000L});
	public static final BitSet FOLLOW_LEFTP_in_testStatements5498 = new BitSet(new long[]{0x0000002000000000L});
	public static final BitSet FOLLOW_ID_in_testStatements5502 = new BitSet(new long[]{0x0000000000400000L});
	public static final BitSet FOLLOW_DOT_in_testStatements5504 = new BitSet(new long[]{0x0000000000000000L,0x0000018000000000L});
	public static final BitSet FOLLOW_set_in_testStatements5506 = new BitSet(new long[]{0x0200000000000000L});
	public static final BitSet FOLLOW_LEFTP_in_testStatements5512 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000000L});
	public static final BitSet FOLLOW_RIGHTP_in_testStatements5514 = new BitSet(new long[]{0x0000000001000000L});
	public static final BitSet FOLLOW_EQUALS_in_testStatements5516 = new BitSet(new long[]{0x0000000001000000L});
	public static final BitSet FOLLOW_EQUALS_in_testStatements5518 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000080L});
	public static final BitSet FOLLOW_NUMBER_in_testStatements5522 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000000L});
	public static final BitSet FOLLOW_RIGHTP_in_testStatements5524 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_type_specification_in_function_definition5549 = new BitSet(new long[]{0x0000002000000000L});
	public static final BitSet FOLLOW_ID_in_function_definition5556 = new BitSet(new long[]{0x0200000000000000L});
	public static final BitSet FOLLOW_LEFTP_in_function_definition5558 = new BitSet(new long[]{0x0000000000000600L,0x0000800040000040L});
	public static final BitSet FOLLOW_list_of_parameters_in_function_definition5563 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000000L});
	public static final BitSet FOLLOW_RIGHTP_in_function_definition5568 = new BitSet(new long[]{0x0100000000000000L});
	public static final BitSet FOLLOW_LEFTCUR_in_function_definition5570 = new BitSet(new long[]{0x00838FB50604CE40L,0x107187EF1C3FCE40L});
	public static final BitSet FOLLOW_list_of_statements_in_function_definition5578 = new BitSet(new long[]{0x0000000000000000L,0x0000000020000000L});
	public static final BitSet FOLLOW_RIGHTCUR_in_function_definition5584 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_NUM_in_type_specification5604 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_TXT_in_type_specification5611 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_NUM_in_type_specification5618 = new BitSet(new long[]{0x0400000000000000L});
	public static final BitSet FOLLOW_LEFTSBR_in_type_specification5620 = new BitSet(new long[]{0x0000000000000000L,0x0000000080000000L});
	public static final BitSet FOLLOW_RIGHTSBR_in_type_specification5622 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_TXT_in_type_specification5629 = new BitSet(new long[]{0x0400000000000000L});
	public static final BitSet FOLLOW_LEFTSBR_in_type_specification5631 = new BitSet(new long[]{0x0000000000000000L,0x0000000080000000L});
	public static final BitSet FOLLOW_RIGHTSBR_in_type_specification5633 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_set_in_type_specification5640 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_type_specification_in_list_of_parameters5666 = new BitSet(new long[]{0x0000002000000000L});
	public static final BitSet FOLLOW_ID_in_list_of_parameters5671 = new BitSet(new long[]{0x0000000000002002L});
	public static final BitSet FOLLOW_COMMA_in_list_of_parameters5676 = new BitSet(new long[]{0x0000000000000600L,0x0000800000000040L});
	public static final BitSet FOLLOW_list_of_parameters_in_list_of_parameters5680 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_statement_in_list_of_statements5704 = new BitSet(new long[]{0x00838FB50604CE42L,0x107187EF1C3FCE40L});
	public static final BitSet FOLLOW_statement_in_list_of_statements5708 = new BitSet(new long[]{0x00838FB50604CE42L,0x107187EF1C3FCE40L});
	public static final BitSet FOLLOW_set_in_return_statement5733 = new BitSet(new long[]{0x0600002060200000L,0x000071E001340881L});
	public static final BitSet FOLLOW_expr_in_return_statement5743 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_call_user_defined_function_in_function_call5774 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ID_in_call_user_defined_function5799 = new BitSet(new long[]{0x0200000000000000L});
	public static final BitSet FOLLOW_LEFTP_in_call_user_defined_function5801 = new BitSet(new long[]{0x0600002060200000L,0x000071E041340881L});
	public static final BitSet FOLLOW_list_of_expressions_in_call_user_defined_function5806 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000000L});
	public static final BitSet FOLLOW_RIGHTP_in_call_user_defined_function5811 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_expr_in_list_of_expressions5833 = new BitSet(new long[]{0x0000000000002002L});
	public static final BitSet FOLLOW_COMMA_in_list_of_expressions5840 = new BitSet(new long[]{0x0600002060200000L,0x000071E001340881L});
	public static final BitSet FOLLOW_list_of_expressions_in_list_of_expressions5844 = new BitSet(new long[]{0x0000000000000002L});
}
