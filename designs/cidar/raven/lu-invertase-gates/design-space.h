//PART PROPERTIES
Property Name(txt);
Property Sequence(txt);
Property Pigeon(txt);

Part Promoter(Name, Sequence, Pigeon);
Part RBS(Name, Sequence, Pigeon);
Part InvertaseSite(Name, Sequence, Pigeon);
Part Gene(Name, Sequence, Pigeon);
Part Reporter(Name, Sequence, Pigeon);
Part Terminator(Name, Sequence, Pigeon);

/*** PROMOTERS ***/
Promoter proA(
	.Name("proA"), 
	.Sequence("CACAGCTAACACCACGTCGTCCCTATCTGCTGCCCTAGGTCTATGAGTGGTTGCTGGATAACTTTACGGGCATGCATAAGGCTCGTAGGCTATATTCAGGGAGACCACAACGGTTTCCCTCTACAAATAATTTTGTTTAACTTT"), 
	.Pigeon("p proA 4"));

Promoter proC(
	.Name("proC"), 
	.Sequence("CACAGCTAACACCACGTCGTCCCTATCTGCTGCCCTAGGTCTATGAGTGGTTGCTGGATAACTTTACGGGCATGCATAAGGCTCGTATGATATATTCAGGGAGACCACAACGGTTTCCCTCTACAAATAATTTTGTTTAACTTT"), 
	.Pigeon("p proB 4"));

Promoter proD(
	.Name("proD"), 
	.Sequence("CACAGCTAACACCACGTCGTCCCTATCTGCTGCCCTAGGTCTATGAGTGGTTGCTGGATAACTTTACGGGCATGCATAAGGCTCGTATAATATATTCAGGGAGACCACAACGGTTTCCCTCTACAAATAATTTTGTTTAACTTT"),
	.Pigeon("p proD 4"));

/*** INVERTASE SITES ***/
InvertaseSite Bxb1_attB(
	.Name("Bxb1_attB"), 
	.Sequence("TCGGCCGGCTTGTCGACGACGGCGGTCTCCGTCGTCAGGATCATCCGGGC"),
	.Pigeon("> Bxb1_B 2"));
	
/**
InvertaseSite Bxb1_attP(
	.Name("Bxb1_attP"), 
	.Sequence("GTCGTGGTTTGTCTGGTCAACCACCGCGGTCTCAGTGGTGTACGGTACAAACCCCGAC"),
	.Pigeon("> Bxb1_P 2"));
 **/
 	
InvertaseSite phiC31_attB(
	.Name("phiC31_attB"), 
	.Sequence("TGCGGGTGCCAGGGCGTGCCCTTGGGCTCCCCGGGCGCGTACTCC"),
	.Pigeon("> C31_B 13"));

/**	
InvertaseSite phiC31_attP(
	.Name("phiC31_attP"), 
	.Sequence("GTGCCCCAACTGGGGTAACCTTTGAGTTCTCTCAGTTGGGGG"),
	.Pigeon("> C31_P 13"));
 **/
/** Relationships between invertase sites **/
//Bxb1_attB MATCHES Bxb1_attP;
//phiC31_attB MATCHES phiC31_attP;


/*** RBSs ***/	
RBS rbs(
	.Name("RBS"), 
	.Sequence("GTGTGGCCGTCG"),
	.Pigeon("r RBS 13"));
	
/*** GENEs ***/
Gene phiC31(
	.Name("phiC31"), 
	.Sequence("ATGACACAAGGGGTTGTGACCGGGGTGGACACGTACGCGGGTGCTTACGACCGTCAGTCGCGCGAGCGCGAGAATTCGAGCGCAGCAAGCCCAGTCCGCACCTGGGCAGCACGAAGGCACGTGCAACGTCAGCATGGCGGCACTCGACAAGTTCGTTGCGGAACGCATCTTCAACAAGATCAGGCACGCCGAAGGCGACGAAGAGACGTTGGCGCTTCTGTGGGAAGCCGCCCGACGCTTCGGCAAGCTCACTGAGGCGCCTGAGAAGAGCGGCGAACGGGCGAACCTTGTTGCGGAGCGCGCCGACGCCCTGAACGCCCTTGAAGAGCTGTACGAAGACCGCGCGGCAGGCGCGTACGACGGACCCGTTGGCAGGAAGCACTTCCGGAAGCAACAGGCAGCGCTGACGCTCCGGCAGCAAGGGGCGGAAGAGCGGCTTGCCGAACTTGAAGCCGCCGAAGCCCCGAAGCTTCCCCTTGACCAATGGTTCCCCGAAGACGCCGACGCTGACCCGACCGGCCCTAAGTCGTGGTGGGGGCGCGCGTCAGTAGACGACAAGCGCGTGTTCGTCGGGCTCTTCGTAGACAAGATCGTTGTCACGAAGTCGACTACGGGCAGGGGGCAGGGAACGCCCATCGAGAAGCGCGCTTCGATCACGTGGGCGAAGCCGCCGACCGACGACGACGAAGACGACGCCCAGGACGGCACGGAAGACGTAGCGGCGTAGCGACACAGCGTAGCGCCAACGAAGACAAGGCGGCCGACCTTCAGCGCGAAGTCGAGCGCGACGGGGGCCGGTTCAGGTTCGTCGGGCATTTCAGCGAAGCGCCGGGCACGTCGGCGTTCGGGACGGCGGAGCGCCCGGAGTTCGAACGCATCCTGAACGAATGCCGCGCCGGGCGGCTCAACATGATCATTGTCTATGACGTGTCGCGCTTCTCGCGCCTGAAGGTCATGGACGCGATTCCGATTGTCTCGGAATTGCTCGCCCTGGGCGTGACGATTGTTTCCACTCAGGAAGGCGTCTTCCGGCAGGGAAACGTCATGGACCTGATTCACCTGATTATGCGGCTCGACGCGTCGCACAAAGAATCTTCGCTGAAGTCGGCGAAGATTCTCGACACGAAGAACCTTCAGCGCGAATTGGGCGGGTACGTCGGCGGGAAGGCGCCTTACGGCTTCGAGCTTGTTTCGGAGACGAAGGAGATCACGCGCAACGGCCGAATGGTCAATGTCGTCATCAACAAGCTTGCGCACTCGACCACTCCCCTTACCGGACCCTTCGAGTTCGAGCCCGACGTAATCCGGTGGTGGTGGCGTGAGATCAAGACGCACAAACACCTTCCCTTCAAGCCGGGCAGTCAAGCCGCCATTCACCCGGGCAGCATCACGGGGCTTTGTAAGCGCATGGACGCTGACGCCGTGCCGACCCGGGGCGAGACGATTGGGAAGAAGACCGCTTCAAGCGCCTGGGACCCGGCAACCGTTATGCGAATCCTTCGGGACCCGCGTATTGCGGGCTTCGCCGCTGAGGTGATCTACAAGAAGAAGCCGGACGGCACGCCGACCACGAAGATTGAGGGTTACCGCATTCAGCGCGACCCGATCACGCTCCGGCCGGTCGAGCTTGATTGCGGACCGATCATCGAGCCCGCTGAGTGGTATGAGCTTCAGGCGTGGTTGGACGGCAGGGGGCGCGGCAAGGGGCTTTCCCGGGGGCAAGCCATTCTGTCCGCCATGGACAAGCTGTACTGCGAGTGTGGCGCCGTCATGACTTCGAAGCGCGGGGAAGAATCGATCAAGGACTCTTACCGCTGCCGTCGCCGGAAGGTGGTCGACCCG"), 
	.Pigeon("c C31"));
	
Gene Bxb1(
	.Name("Bxb1"), 
	.Sequence("ATGAGAGCCCTGGTAGTCATCCGCCTGTCCCGCGTCACCGATGCTACGACTTCACCGGAGCGTCAGCTGGAGTCTTGCCAGCAGCTCTGCGCCCAGGGCTCGGCAGCGTGGTCGAACGGCTACACACCGGGATGTCGTAGCGCGGCTGGGACGTCGTCGGGGTAGCGGAGGATCTGGACGTCTCCGGGGCGGTCGATCCGTTCGACCGGAAGCGCAGACCGAACCTGGCCCGGTGGCTAGCGTTCGAGGAGCAACCGTTCGACGTGATCGTGGCGTACCGGGTAGACCGGTTGACCCGATCGATCCGGCATCTGCAGCAGCTGGTCCACTGGGCCGAGGACCACAAGAAGCTGGTCGTCTCCGCGACCGAAGCGCACTTCGATACGACGACGCCGTTTGCGGCGGTCGTCATCGCGCTTATGGGAACGGTGGCGCAGATGGAATTAGAAGCGATCAAAGAGCGGAACCGTTCGGCTGCGCATTTCAATATCCGCGCCGGGAAATACCGAGGATCCCTGCCGCCGTGGGGATACCTGCCTACGCGCGTGGACGGGGAGTGGCGGCTGGTGCCGGACCCTGTGCAGCGAGAGCGCATCCTCGAGGTGTATCACCGCGTCGTCGACAACCACGAGCCGCTGCACCTGGTGGCCCACGACCTGAACCGGCGTGGTGTCCTGTCGCCGAAGGACTACTTCGCGCAGCTGCAAGGCCGCGAGCCGCAGGGCCGGGAGTGGTCGGCTACCGCGCTGAAGCGATCGATGATCTCCGAGGCGATGCTCGGGTACGCGACTCTGAACGGTAAGACCGTCCGAGACGACGACGGAGCCCCGCTGGTGCGGGCTGAGCCGATCCTGACCCGTGAGCAGCTGGAGGCGCTGCGCGCCGAGCTCGTGAAGACCTCCCGGGCGAAGCCCGCGGTGTCTACCCCGTCGCTGCTGCTGCGGGTGTTGTTCTGCGCGGTGTGCGGGGAGCCCGCGTACAAGTTCGCCGGGGGAGGACGTAAGCACCCGCGCTACCGCTGCCGCTCGATGGGGTTCCCGAAGCACTGCGGGAACGGCACGGTGGCGATGGCCGAGTGGGACGCGTTCTGCGAGGAGCAGGTGCTGGATCTGCTCGGGGACGCGGAGCGTCTGGAGAAAGTCTGGGTAGCCGGCTCGGACTCCGCGGTCGAACTCGCGGAGGTGAACGCGGAGCTGGTGGACCTGACGTCGCTGATCGGCTCCCCGGCCTACCGGGCCGGCTCTCCGCAGCGAGAAGCACTGGATGCCCGTATTGCGGCGCTGGCCGCGCGGCAAGAGGAGCTGGAGGGTCTAGAGGCTCGCCCGTCTGGCTGGGAGTGGCGCGAGACCGGGCAGCGGTTCGGGGACTGGTGGCGGGAGCAGGACACCGCGGCAAAGAACACCTGGCTTCGGTCGATGAACGTTCGGCTGACGTTCGACGTCCGCGGCGGGCTGACTCGCACGATCGACTTCGGGGATCTGCAGGAGTACGAGCAGCATCTCA"), 
	.Pigeon("c Bxb1"));


/*** REPORTERS ***/	
Reporter gfp(
	.Name("gfp"), 
	.Sequence("ATGCGTAAAGGAGAAGAACTTTTCACTGGAGTTGTCCCAATTCTTGTTGAATTAGATGGTGATGTTAATGGGCACAAATTTTCTGTCAGTGGAGAGGGTGAAGGTGATGCAACATACGGAAAACTTACCCTTAAATTTATTTGCACTACTGGAAAACTACCTGTTCCATGGCCAACACTTGTCACTACTTTCGGTTATGGTGTTCAATGCTTTGCGAGATACCCAGATCATATGAAACAGCATGACTTTTTCAAGAGTGCCATGCCCGAAGGTTATGTACAGGAAAGAACTATATTTTTCAAAGATGACGGGAACTACAAGACACGTGCTGAAGTCAAGTTTGAAGGTGATACCCTTGTTAATAGAATCGAGTTAAAAGGTATTGATTTTAAAGAAGATGGAAACATTCTTGGACACAAATTGGAATACAACTATAACTCACACAATGTATACATCATGGCAGACAAACAAAAGAATGGAATCAAAGTTAACTTCAAAATTAGACACAACATTGAAGATGGAAGCGTTCAACTAGCAGACCATTATCAACAAAATACTCCAATTGGCGATGGCCCTGTCCTTTTACCAGACAACCATTACCTGTCCACACAATCTGCCCTTTCGAAAGATCCCAACGAAAAGAGAGACCACATGGTCCTTCTTGAGTTTGTAACAGCTGCTGGGATTACACATGGCATGGATGAACTATACAAATAA"),
	.Pigeon("c GFP 12"));


/*** TERMINATORS ***/
Terminator T1(
	.Name("T1"), 
	.Sequence("GGCATCAAATAAAACGAAAGGCTCAGTCGAAAGACTGGGCCTTTCGTTTTATCTGTTGTTTGTCGGTGAACGCTCTCCTGAGTAGGACAAATCCGCCGCCCTAGA"),
	.Pigeon("t T1 8"));


