-- phpMyAdmin SQL Dump
-- version 3.4.11.1deb1
-- http://www.phpmyadmin.net
--
-- Client: localhost
-- Généré le: Ven 09 Janvier 2015 à 18:10
-- Version du serveur: 5.5.37
-- Version de PHP: 5.4.6-1ubuntu1.8

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données: `TrustSource`
--

-- --------------------------------------------------------

--
-- Structure de la table `Queries`
--

CREATE TABLE IF NOT EXISTS `Queries` (
  `text_id` varchar(100) NOT NULL,
  `query_id` varchar(100) NOT NULL,
  `query_text` varchar(1000) NOT NULL,
  `query_sparql` varchar(1000) NOT NULL,
  PRIMARY KEY (`text_id`,`query_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `Queries`
--

INSERT INTO `Queries` (`text_id`, `query_id`, `query_text`, `query_sparql`) VALUES
('001', '001', 'Quand est morte la victime?', 'SELECT ?start ?end WHERE { ?death rdf:type gs:Death. ?death gs:event-date ?date. ?date v:dtstart ?start. ?date v:dtend ?end. }'),
('001', '002', 'Où est morte la victime?', 'SELECT ?loc  WEHERE { 	?death gs:eventPlace ?place. 	?place v:location-name ?loc. }'),
('001', '003', 'Quelle identité de la victime?', 'Select ?family-name ?given-name ?name  WHERE { 	?death gs:deceaded ?p. 	?p foaf:family-name ?family-name. 	?p foaf:given-name ?given-name. }'),
('001', '004', 'Quel est l''âge de la victime?', 'SELECT ?age  WHERE { 	?death gs:deceaded ?p. 	?p foaf:age ?age. }'),
('001', '005', 'Avec quel instrument été tué la victime?', 'SELECT ?instrument  WHERE { 	?d gs:cause ?v. 	?v gs:instrument ?o. 	?o gs:object-name ?instruement. }'),
('001', '006', 'Qui a déclaré la mort de l''homme?', 'SELECT ?name ?person WHERE { 	?declaration gs:content ?death. 	?death rdf:type gs:Death. 	?declaration gs:isAgent ?person. 	?person faof:name ?name. }'),
('001', '007', 'Comment est morte la victime?', 'Select ?cause  WHERE { 	?death gs:deceaded ?person. 	?death gs:event-cause ?v. 	?v gs:violentActType ?cause. }'),
('002', '001', 'Où se situe la Papouasie?', 'Select ?loc  WHERE { 	?p gs:location-name “Papouasie”. 	?loc gs:locatedIn ?p. }'),
('002', '002', 'Quelle la capitale de la Papouasie?', 'SELECT ?cap  WHERE { 	?place v:location-name “Papouasie”. 	?place gs:hasCapital ?cap. }'),
('002', '003', 'Que ont fait les Français? ', 'Select ?action  WHERE { 	?a gs:isAgent ?agent. 	?agent gs:nat “Français”. 	?a rdf:type ?action }'),
('002', '004', 'Qui est l''avocat des Français?', 'Select ?name  WHERE { 	?works gs:role “avocat”. 	?agent gs:works ?works. 	?agent foaf:name ?name. }'),
('002', '005', 'Quelle est la nationalité des détenus?', 'SELECT ?nat  WHERE { 	?arrest gs:charged ?person. 	?person gs:nat ?nat. }'),
('002', '006', 'Quelle est la cause de la détention?', 'SELECT ?cause  WHERE { 	?arrest gs:cause ?action. 	?action rdf:type ?cause. }'),
('003', '001', 'Qui a trouvé de nouvelles preuves de la mort de Yasser Arafat?', 'SELECT ?name  WHERE { 	?declaration gs:isAgent ?person.  	?person foaf:name ?name. 	?declaration gs:content ?death. 	?death gs:deceaded ?p. 	?p foaf:name “Yasser Arafat”. }'),
('003', '002', 'Qu''est-ce qui arrive à Yasser Arafat? ', 'SELECT ?event  WHERE { 	?action gs:undergoer ?person. 	?person foaf:name “Yasser Arafat”. 	?action rdf:type ?action. }'),
('003', '003', 'Qui est Yasser Arafat?', 'DESCRIBE ?person  WHERE { 	?person foaf:name “Yasser Arafat”. }'),
('003', '004', 'Quelle est la cause de décès de Yasser Arafat?', 'Select ?cause  WHERE { 	?death gs:deceaded ?person. 	?person foaf:name “Yasser Arafat”. 	?death gs:event-cause ?a. 	?a rdf:type ?cause. }'),
('003', '005', 'Quel est le nom du Palestinian leader''s?', 'SELECT ?name  WHERE { 	?person gs:works ?works. 	?works gs:role “leader”. 	?person gs:nat “Palestine”. 	?person foaf:name ?name. }'),
('004', '001', 'Quelle est la source de la nouvelle de l''IPO de Twitter Inc?', 'SELECT ?name   WHERE { 	?declaration gs:content ?ipo. 	?ipo rdf:type gs:IPO. 	?declaration gs:isAgent ?agent. 	?agent foaf:name ?name. }'),
('004', '002', 'Que fait Twitter Inc?', 'SELECT ?action-name  WHERE { 	?action gs:isAgent ?agent. 	?agent foaf:name “Twitter”. 	?action rdf:type ?action-name. }'),
('004', '003', 'Que report Quartz dimanche?', 'SELECT ?content  WHERE { 	?decl gs:isAgent ?agent. 	?agent foaf:name “Quartz”. 	?decl gs:content ?content. }'),
('005', '001', 'Où a été tué le cameraman?', 'SELECT ?p ?place  WHERE { 	?death gs:deceaded ?p. 	?p gs:works ?works. 	?works gs:role “cameraman”. 	?death gs:event-place ?p. 	?p v:location-name ?place. }'),
('005', '002', 'Quelle identité le cameraman?', 'SELECT ?name ?p  WHERE { 	?p gs:works ?works. 	?works gs:role “cameraman”. 	?p foaf:name ?name. }');

-- --------------------------------------------------------

--
-- Structure de la table `Source`
--

CREATE TABLE IF NOT EXISTS `Source` (
  `source_name` varchar(255) NOT NULL,
  `website` varchar(255) NOT NULL,
  PRIMARY KEY (`source_name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `Source`
--

INSERT INTO `Source` (`source_name`, `website`) VALUES
('Aljazeera_en', 'http://www.aljazeera.com/'),
('Lefigaro', 'www.lefigaro.fr'),
('Lemonde', 'www.lemonde.fr'),
('Reuters', 'www.reuters.com'),
('Theguardian_uk', 'http://www.theguardian.com/uk'),
('TheNewYorkTimes', 'http://www.nytimes.com/');

-- --------------------------------------------------------

--
-- Structure de la table `Text`
--

CREATE TABLE IF NOT EXISTS `Text` (
  `textID` varchar(100) NOT NULL,
  `content` text NOT NULL,
  `rdf` longtext NOT NULL,
  `source` varchar(25) NOT NULL,
  `author` varchar(25) NOT NULL,
  `dateRef` date NOT NULL,
  PRIMARY KEY (`textID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `Text`
--

INSERT INTO `Text` (`textID`, `content`, `rdf`, `source`, `author`, `dateRef`) VALUES
('001', 'Un homme de 35 ans a été tué hier soir à l''arme blanche dans la rue à Besançon, dans des circonstances qui restent à éclaircir, a indiqué le parquet de Besançon. D''après les premiers éléments de l''enquête, il a probablement été victime d''un coup de couteau fatal à la carotide, a précisé à l''AFP la vice-procureure Margaret Parietti.', '<?xml version="1.0" encoding="UTF-8"?>\r\n<rdf:RDF xmlns:owl="http://www.w3.org/2002/07/owl#"\r\n  xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"\r\n  xmlns:gs="http://www.geolsemantics.com/onto#"\r\n  xmlns:ical="http://www.w3.org/2002/12/cal/icaltzd#"\r\n  xmlns:wn="http://www.w3.org/2006/03/wn/wn20/"\r\n  xmlns:foaf="http://xmlns.com/foaf/0.1/"\r\n  xmlns:rdfg ="http://www.w3.org/2004/03/trix/rdfg-1"\r\n  xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#"\r\n  xmlns:v="http://www.w3.org/2006/vcard/ns#"\r\n  xmlns:doac="http://ramonantonio.net/doac/0.1/"\r\n  xmlns:prov="http://www.w3.org/ns/prov#" >\r\n<rdfg:Graph rdf:nodeID="idGraph1">\r\n	<gs:hasText>\r\n		<gs:Text>\r\n		        <gs:hasTriple>\r\n				<gs:Declaration rdf:nodeID="idDeclaration11">		\r\n					<gs:hasAgent>\r\n						<foaf:Person rdf:about="www.example.com/Margaret_Parietti">\r\n							<foaf:name>Margaret Parietti</foaf:name>\r\n							<foaf:given-name>Margaret</foaf:given-name>\r\n							<foaf:family-name>Parietti</foaf:family-name>\r\n							<gs:works>\r\n								<gs:Experience>\r\n									<gs:role>vice-procureure</gs:role>\r\n								</gs:Experience>\r\n							</gs:works>\r\n						</foaf:Person>\r\n					</gs:hasAgent>						\r\n					<gs:toAgent>\r\n						<foaf:Organization rdf:about="www.example.com/AFP">\r\n							<foaf:name>Agence Française de Presse</foaf:name>\r\n							<foaf:nick-name>AFP</foaf:nick-name>\r\n						</foaf:Organization>\r\n					</gs:toAgent>\r\n					<gs:hasContent>\r\n						<gs:ReportedSpeech rdf:nodeID="Uncertainty2">\r\n							<gs:content rdf:nodeID="idViolentAct2"/>						\r\n							<gs:weight>0.9</gs:weight>\r\n						</gs:ReportedSpeech>\r\n					</gs:hasContent>\r\n				</gs:Declaration>		\r\n		        </gs:hasTriple>\r\n\r\n		        <gs:hasTriple>	\r\n				<gs:ViolentAct rdf:nodeID="idViolentAct1">\r\n					<gs:type>murder</gs:type>\r\n					<gs:onDate>\r\n						<gs:Date rdf:nodeID="idhier1">\r\n							<v:dtstart>2014-09-27</v:dtstart>\r\n							<v:dtend>2014-09-27</v:dtend>\r\n						</gs:Date>\r\n					</gs:onDate>\r\n					<gs:atPlace>\r\n						<v:Location rdf:about="www.example.com/Besancon">\r\n							<v:location-name>Besancon</v:location-name>\r\n						</v:Location>\r\n					</gs:atPlace>\r\n					<gs:instrument>arme blanche</gs:instrument>\r\n					<gs:hasVictim>\r\n						<foaf:Person rdf:nodeID="id1Person">\r\n							<foaf:age>35ans</foaf:age>\r\n							<gs:gender>male</gs:gender>\r\n							<gs:event-date>\r\n								<gs:Date rdf:nodeID="idhier1">\r\n									<v:dtstart>2014-09-27</v:dtstart>\r\n									<v:dtend>2014-09-27</v:dtend>\r\n								</gs:Date>\r\n							</gs:event-date>\r\n							<gs:eventPlace>\r\n								<v:Location rdf:about="www.example.com/Besancon">\r\n									<v:location-name>Besancon</v:location-name>\r\n								</v:Location>\r\n							</gs:eventPlace>\r\n						</foaf:Person>		\r\n					</gs:hasVictim>\r\n					<gs:hasUncertainProp>\r\n						<gs:AuthorUncertainty>\r\n							<gs:cause>\r\n								<gs:ViolentAct rdf:nodeID="idViolentAct2">\r\n									<gs:type>coup de couteau</gs:type>\r\n								</gs:ViolentAct>\r\n							</gs:cause>\r\n							<gs:weight>0.7</gs:weight>\r\n						</gs:AuthorUncertainty>\r\n					</gs:hasUncertainProp>\r\n				</gs:ViolentAct>\r\n		        </gs:hasTriple>\r\n		</gs:Text>\r\n	</gs:hasText>\r\n	<prov:startAtTime>2014-09-28T16:43:00Z</prov:startAtTime>\r\n	<prov:endAtTime>2014-09-28T16:43:59Z</prov:endAtTime>\r\n	<prov:wasAttributedTo>\r\n		<prov:Agent rdf:about="http://www.example.com/ELIAS_BIRYABAREMA">\r\n			<prov:OnBehalfOf rdf:resource="http://www.example.com/LeFigaro"/>\r\n		</prov:Agent>\r\n	</prov:wasAttributedTo>\r\n	<gs:hasTrust>0.8</gs:hasTrust>\r\n</rdfg:Graph>\r\n</rdf:RDF>', 'Lefigaro', '', '2014-09-28'),
('002', 'Détenus dans un centre de rétention à Jayapura, capitale de la Papouasie, les Français vont très probablement devoir comparaître devant un tribunal pour avoir triché sur la nature de leur visa, prévient leur avocat Aristo Pangaribuan.', '<?xml version="1.0" encoding="UTF-8"?>\r\n<rdf:RDF xmlns:owl="http://www.w3.org/2002/07/owl#"\r\n  xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"\r\n  xmlns:gs="http://www.geolsemantics.com/onto#"\r\n  xmlns:ical="http://www.w3.org/2002/12/cal/icaltzd#"\r\n  xmlns:wn="http://www.w3.org/2006/03/wn/wn20/"\r\n  xmlns:foaf="http://xmlns.com/foaf/0.1/"\r\n  xmlns:rdfg ="http://www.w3.org/2004/03/trix/rdfg-1"\r\n  xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#"\r\n  xmlns:v="http://www.w3.org/2006/vcard/ns#"\r\n  xmlns:doac="http://ramonantonio.net/doac/0.1/"\r\n  xmlns:prov="http://www.w3.org/ns/prov#" >\r\n<!-- Début du document -->\r\n<rdfg:Graph rdf:nodeID="idGraph1">\r\n	<gs:hasText>\r\n		<gs:Text>\r\n		        <gs:hasTriple>\r\n				<gs:Arrest rdf:nodeID="idArrest1">		\r\n					<gs:isCharged>\r\n						<foaf:Agent rdf:nodeID="idFrançais1">\r\n							<gs:nat>français></gs:nat>\r\n						</foaf:Agent>\r\n					</gs:isCharged>\r\n					<gs:eventPlace>\r\n						<v:Location rdf:nodeID="idJayapura1">\r\n							<v:location-name>Jayapura</v:location-name>\r\n							<v:locatedIn>\r\n								<v:Location rdf:nodeID="idPapouasie1">\r\n									<v:location-name>Papouasie</v:location-name>\r\n								</v:Location>\r\n							</v:locatedIn>\r\n						</v:Location>\r\n					</gs:eventPlace>\r\n				</gs:Arrest>		\r\n		        </gs:hasTriple>\r\n\r\n		        <gs:hasTriple>		\r\n				<gs:AuthorUncertainty rdf:nodeID="idUncertainty1">\r\n					<gs:isUncertain>\r\n						<gs:Conviction rdf:nodeID="idConviction1">\r\n							<gs:isCharged rdf:nodeID="idFrançais1"/>\r\n							<gs:isAgent rdf:nodeID="idTribunal1"/>\r\n							<gs:cause>Tricher sur le visa</gs:cause>\r\n							<gs:hasUncertainProp>\r\n								<gs:AlignementUncertainty rdf:nodeID="idUncertainty2">\r\n									<gs:eventPlace rdf:nodeID="idPapouasie1"/>\r\n									<gs:weight>0.4</gs:weight>\r\n								</gs:AlignementUncertainty>\r\n							</gs:hasUncertainProp>\r\n						</gs:Conviction>\r\n					</gs:isUncertain>\r\n					<gs:weight>0.6</gs:weight>\r\n				</gs:AuthorUncertainty>\r\n		        </gs:hasTriple>\r\n\r\n		        <gs:hasTriple>		\r\n				<gs:ReportedSpeech rdf:nodeID="idDeclaration1">\r\n					<gs:isAgent>\r\n						<foaf:Agent rdf:nodeID="idPangaribuan1">\r\n							<foaf:name>Aristo Pangaribuan</foaf:name>\r\n							<foaf:given-name>Aristo</foaf:given-name>\r\n							<foaf:family-name>Pangaribuan</foaf:family-name>\r\n						</foaf:Agent>\r\n					</gs:isAgent>\r\n					<gs:content rdf:nodeID="idConviction1"/>\r\n				</gs:ReportedSpeech>\r\n		        </gs:hasTriple>		\r\n		</gs:Text>\r\n	</gs:hasText>\r\n	<prov:startAtTime>2014-09-02T16:43:00Z</prov:startAtTime>\r\n	<prov:endAtTime>2014-09-02T16:43:59Z</prov:endAtTime>\r\n	<prov:wasAttributedTo>\r\n		<prov:Agent rdf:about="http://www.example.com/JeanneFremin">\r\n			<prov:OnBehalfOf rdf:resource="http://www.example.com/LeFigaro"/>\r\n		</prov:Agent>\r\n	</prov:wasAttributedTo>\r\n	<gs:hasTrust>0.8</gs:hasTrust>\r\n</rdfg:Graph>\r\n</rdf:RDF>', 'Lefigaro', 'Jeanne Fremin Du Sartel', '2014-09-02'),
('003', 'Swiss scientists have found evidence suggesting that Yasser Arafat may have been poisoned, adding new fuel to long-standing allegations about the Palestinian leader''s death, a TV station reported Wednesday.', '<?xml version="1.0" encoding="UTF-8"?>\r\n<rdf:RDF xmlns:owl="http://www.w3.org/2002/07/owl#"\r\n  xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"\r\n  xmlns:gs="http://www.geolsemantics.com/onto#"\r\n  xmlns:ical="http://www.w3.org/2002/12/cal/icaltzd#"\r\n  xmlns:wn="http://www.w3.org/2006/03/wn/wn20/"\r\n  xmlns:foaf="http://xmlns.com/foaf/0.1/"\r\n  xmlns:rdfg ="http://www.w3.org/2004/03/trix/rdfg-1"\r\n  xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#"\r\n  xmlns:v="http://www.w3.org/2006/vcard/ns#"\r\n  xmlns:doac="http://ramonantonio.net/doac/0.1/"\r\n  xmlns:prov="http://www.w3.org/ns/prov#" >\r\n<!-- Début du document -->\r\n<rdfg:Graph rdf:nodeID="idGraph2">\r\n	<gs:hasText>\r\n		<gs:Text rdf:nodeID="id1text">		\r\n		        <gs:hasTriple>\r\n				<gs:AuthorUncertainty rdf:nodeID="idUncertainty1">\r\n					<gs:isUncertain>	\r\n						<gs:ViolentAct rdf:nodeID="id1Posoning">\r\n							<gs:type>poisoning</gs:type>\r\n							<gs:instrument>polonium</gs:instrument>\r\n							<gs:hasVictim>\r\n								<foaf:Person rdf:about="www.example.com/id1Arafat">\r\n									<foaf:name>Yasser Arafat</foaf:name>\r\n								</foaf:Person>\r\n							</gs:hasVictim>\r\n							<gs:hasEvidence>tests</gs:hasEvidence>\r\n						</gs:ViolentAct>\r\n					</gs:isUncertain>	\r\n					<gs:weight>0,5</gs:weight>\r\n				</gs:AuthorUncertainty>\r\n		        </gs:hasTriple>		\r\n		        <gs:hasTriple>		\r\n				<gs:Death rdf:nodeID="idDeath1">\r\n					<gs:deceaded>\r\n						<foaf:Person rdf:nodeID="id1Person">\r\n							<gs:works>\r\n								<gs:Experience rdf:nodeID="id1Experience">\r\n									<gs:role>Leader</gs:role>\r\n									<gs:nat>Paletine</gs:nat>\r\n									<!--<gs:worksAt>\r\n										<foaf:Organization>\r\n	<foaf:name>Palestine</foaf:name>\r\n</foaf:Organization> -->\r\n									</gs:worksAt>\r\n								</gs:Experience>\r\n							</gs:works>\r\n							<gs:hasUncertainProp>\r\n								<gs:AlignementUncertainty rdf:nodeID="idUncertainty2">\r\n									<gs:sameAs rdf:resource="www.example.com/id1Arafat"/>\r\n									<gs:weight>0.4</gs:weight>\r\n								</gs:AlignementUncertainty>\r\n</gs:hasUncertainProp>							\r\n							<gs:sameAs rdf:resource="www.example.com/id1Arafat"/>\r\n						</foaf:Person>\r\n					</gs:deceaded>\r\n				</gs:Death>\r\n		        </gs:hasTriple>		\r\n		</gs:Text>\r\n	</gs:hasText>\r\n	<prov:startAtTime>2013-11-06T16:43:00Z</prov:startAtTime>\r\n	<prov:endAtTime>2013-11-06T16:43:59Z</prov:endAtTime>\r\n	<prov:wasAttributedTo>\r\n		<prov:Agent rdf:nodeID="idSource1">\r\n			<prov:OnBehalfOf rdf:resource="http://www.example.com/theguargdian"/>\r\n		</prov:Agent>\r\n	</prov:wasAttributedTo>\r\n	<gs:hasTrust>0.8</gs:hasTrust>	\r\n</rdfg:Graph>\r\n</rdf:RDF>', 'Al-Jazeera', 'Mohammed Daraghmeh', '2013-11-06'),
('004', 'Twitter Inc plans to make its IPO filing public this week, news website Quartz reported on Sunday, citing a person familiar with the social media network''s plan.', '<?xml version="1.0" encoding="UTF-8"?>\r\n<rdf:RDF xmlns:owl="http://www.w3.org/2002/07/owl#"\r\n  xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"\r\n  xmlns:gs="http://www.geolsemantics.com/onto#"\r\n  xmlns:ical="http://www.w3.org/2002/12/cal/icaltzd#"\r\n  xmlns:wn="http://www.w3.org/2006/03/wn/wn20/"\r\n  xmlns:foaf="http://xmlns.com/foaf/0.1/"\r\n  xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#"\r\n  xmlns:v="http://www.w3.org/2006/vcard/ns#"\r\n  xmlns:rdfg ="http://www.w3.org/2004/03/trix/rdfg-1"\r\n  xmlns:doac="http://ramonantonio.net/doac/0.1/">\r\n<!-- Début du document -->\r\n<rdfg:Graph rdf:about="http://www.example.com/Graph1">\r\n	<gs:hasText>\r\n<gs:Text rdf:nodeID="idtext1">		\r\n		<gs:hasTriple>\r\n			<gs:AuthorUncertainty rdf:nodeID="idU3">				\r\n				<gs:isUncertain rdf:nodeID="idThisWeek"/>\r\n				<gs:weight>0,8</gs:weight>	\r\n	 		</gs:AuthorUncertainty>\r\n		</gs:hasTriple>	\r\n		<gs:hasTriple>\r\n			<gs:IPO rdf:nodeID="IPO1">\r\n				<gs:hasUnderwriter> \r\n					<gs:Company rdf:about="http://www.twitter.com">\r\n						<foaf:name>Twitter</foaf:name>\r\n						<gs:orgType>Incorporated</gs:orgType>\r\n						<gs:hasUncetrainProp>\r\n							<gs:AlignementUncertainty rdf:nodeID="idU1">				\r\n								<gs:businessSector>social media network</gs:businessSector>\r\n								<gs:weight>0,7</gs:weight>							 \r\n							</gs:AlignementUncertainty>\r\n						</gs:hasUncetrainProp>\r\n					</gs:Company>\r\n				</gs:hasUnderwriter> \r\n				<gs:date-IPO>\r\n					<gs:Date rdf:nodeID="idThisWeek">\r\n						<v:dtstart>2013-09-30T00:00:00Z</v:dtstart>\r\n						<v:dtend>2013-09-30T23:59:59Z</v:dtend>\r\n					</gs:Date>\r\n				</gs:date-IPO>\r\n			</gs:IPO>			\r\n		</gs:hasTriple>						\r\n		<gs:hasTriple>\r\n			<gs:AuthorUncertainty rdf:nodeID="idU2">				\r\n				<gs:isUncertain rdf:nodeID="IPO1"/>\r\n				<gs:weight>0,3</gs:weight>							 		\r\n			</gs:AuthorUncertainty>\r\n		</gs:hasTriple>				\r\n</gs:Text>\r\n		</gs:hasText>\r\n	<gs:hasDateRef>\r\n		<gs:Date rdf:nodeID="idDate2">\r\n			<v:dtstart>2013-10-03T00:00:00Z</v:dtstart>\r\n			<v:dtend>2013-10-30T00:00:00Z</v:dtend>\r\n		</gs:Date>\r\n	</gs:hasDateRef>\r\n	<gs:hasSource>\r\n		<gs:Source rdf:nodeID="idSource1">\r\n			<gs:isSource rdf:resource="http://www.reuters.com"/>\r\n			<gs:hasTrust>0.8</gs:hasTrust>\r\n		</gs:Source>\r\n	</gs:hasSource>\r\n</rdfg:Graph>\r\n</rdf:RDF>', 'Reuters', 'Edwina Gibbs', '2013-09-30'),
('005', 'Investigators in Thailand now believe that troops may have been responsible for the shooting death of Reuters cameraman Hiro Muramoto, on April 10, according to a leaked preliminary state probe by Thailand''s Department of Special Investigation (DSI), Reuters reported from Bangkok today.', '<?xml version="1.0" encoding="UTF-8"?>\r\n<rdf:RDF xmlns:owl="http://www.w3.org/2002/07/owl#"\r\n  xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"\r\n  xmlns:gs="http://www.geolsemantics.com/onto#"\r\n  xmlns:ical="http://www.w3.org/2002/12/cal/icaltzd#"\r\n  xmlns:wn="http://www.w3.org/2006/03/wn/wn20/"\r\n  xmlns:foaf="http://xmlns.com/foaf/0.1/"\r\n  xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#"\r\n  xmlns:v="http://www.w3.org/2006/vcard/ns#"\r\n  xmlns:rdfg ="http://www.w3.org/2004/03/trix/rdfg-1"\r\n  xmlns:doac="http://ramonantonio.net/doac/0.1/">\r\n<!-- Début du document -->\r\n<rdfg:Graph rdf:about="http://www.example.com/Graph2">\r\n	<gs:hasText>\r\n<gs:Text rdf:about="http://www.example.com/idtext1">\r\n        <gs:hasSent>\r\n		<gs:Sentense rdf:about="http://www.example.com/idsent1"> \r\n<!--Déclaration des Agents -->\r\n	        <gs:hasTriple>\r\n			<foaf:Organization rdf:about="http://www.exemple.com/Invetigators">\r\n				<gs:organization-type>Investigator</gs:organization-type>\r\n				<gs:place rdf:resource="http://www.exemple.com/Thailand"/>\r\n			</foaf:Organization>			\r\n		</gs:hasTriple>					\r\n\r\n		<gs:hasTriple>\r\n			<foaf:Organization rdf:about="http://www.exemple.com/DSI">\r\n				<foaf:name>Department of Special Investigation</foaf:name>\r\n				<foaf:nick>DSI</foaf:nick>\r\n				<gs:hasAddress rdf:resource="http://www.exemple.com/Thailand"/>\r\n			</foaf:Organization>			\r\n		</gs:hasTriple>		\r\n		<gs:hasTriple>\r\n			<foaf:Organization rdf:about="http://www.exemple.com/Troops">\r\n				<gs:organization-type>troop</gs:organization-type>\r\n			</foaf:Organization>			\r\n		</gs:hasTriple>	\r\n<!--Déclaration des Persons -->\r\n\r\n		<gs:hasTriple>\r\n			<foaf:Person rdf:about="http://www.exemple.com/Hiro_Muramoto">\r\n				<foaf:name>Hiro Muramoto</foaf:name>\r\n				<foaf:given-name>Hiro</foaf:given-name>\r\n				<foaf:family-name>Muramoto</foaf:family-name>\r\n			</foaf:Person>\r\n		</gs:hasTriple>	\r\n\r\n<!--Déclaration des Locations -->\r\n		<gs:hasTriple>\r\n			<v:Location rdf:about="http://www.exemple.com/Thailand">\r\n				<v:Location-name>Thailand</v:Location-name>\r\n			</v:Location>			\r\n		</gs:hasTriple>	\r\n\r\n<!--Déclaration des Dates -->\r\n		<gs:hasTriple>\r\n			<gs:Date rdf:about="http://www.exemple.com/April_2010">\r\n				<v:dtstart>2010-04-01T00:00:00Z</v:dtstart>\r\n				<v:dtend>2010-04-30T23:59:59Z</v:dtend>\r\n			</gs:Date>\r\n		</gs:hasTriple>		\r\n\r\n<!-- Les actions et événements -->\r\n		<gs:hasTriple>\r\n			<gs:ReportedSpeech rdf:about="http://www.exemple.com/Declaration">\r\n				<gs:isAgent rdf:resource="http://www.exemple.com/Investigators"/>\r\n				<gs:actionPlace rdf:resource="http://www.exemple.com/Thailand"/>\r\n				<gs:accordingTo rdf:resource="http://www.exemple.com/DSI"/>\r\n				<gs:content rdf:resource="http://www.exemple.com/ViolentAct1"/>		\r\n<!-- content = rdf bag concernant le killing-->\r\n			</gs:ReportedSpeech>\r\n		</gs:hasTriple>\r\n		<gs:hasTriple>\r\n			<gs:ViolentAct rdf:about="http://www.exemple.com/ViolentAct1">\r\n				<gs:hasUncertainProp>\r\n					<gs:AuthorUncertainty rdf:about="http://www.exemple.com/idU3"><!--may have been-->								\r\n						<gs:isAgent rdf:resource="http://www.exemple.com/Troops"/>\r\n						<gs:weight>0,3</gs:weight>							 \r\n					</gs:AuthorUncertainty>\r\n				</gs:hasUncertainProp>\r\n				<gs:isVictim rdf:resource="http://www.exemple.com/Hiro_Muramoto"/>\r\n				<gs:date rdf:resource="http://www.exemple.com/April_2010"/>\r\n				<gs:type>killing</gs:type>\r\n			</gs:ViolentAct>\r\n        	</gs:hasTriple>	\r\n<!-- Les incertitudes -->\r\n		<gs:hasTriple>\r\n			<gs:Uncertainty rdf:about="http://www.exemple.com/idU1"><!--believe-->				\r\n				<gs:isUncertain rdf:nodeID="idBag1"/>				\r\n				<gs:weight>0,4 * 0,5 * 0,9</gs:weight>							 \r\n			</gs:Uncertainty>\r\n		</gs:hasTriple>					\r\n				\r\n		<gs:hasTriple>\r\n			<gs:Uncertainty rdf:about="http://www.exemple.com/idU2"><!--according to -->				\r\n				<gs:place rdf:resource="http://www.exemple.com/Thailand"/>\r\n				<gs:weight>0,2</gs:weight>	\r\n	 		</gs:Uncertainty>\r\n		</gs:hasTriple>\r\n\r\n							\r\n	</gs:Sentense>\r\n    </gs:hasSent>\r\n</gs:Text>\r\n		</gs:hasText>\r\n	<gs:hasDateRef>\r\n		<gs:Date rdf:nodeID="idDate2">\r\n			<v:dtstart>2010-12-10T00:00:00Z</v:dtstart>\r\n			<v:dtend>2010-12-10T00:00:00Z</v:dtend>\r\n		</gs:Date>\r\n	</gs:hasDateRef>\r\n	<gs:hasSource>\r\n		<gs:Source rdf:nodeID="idSource1">\r\n			<gs:isSource rdf:resource="http://www.reuters.com"/>\r\n			<gs:hasTrust>0,8</gs:hasTrust>\r\n		</gs:Source>\r\n	</gs:hasSource>\r\n\r\n</rdfg:Graph>\r\n</rdf:RDF>', 'Reuters', '', '2010-12-10'),
('006', 'Uganda''s military said on Monday a commander believed to be the deputy to Lord''s Resistance Army (LRA) leader Joseph Kony may have been killed last year in Central African Republic where an African Union force is hunting the insurgents.', '<?xml version="1.0" encoding="UTF-8"?>\r\n<rdf:RDF xmlns:owl="http://www.w3.org/2002/07/owl#"\r\n  xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"\r\n  xmlns:gs="http://www.geolsemantics.com/onto#"\r\n  xmlns:ical="http://www.w3.org/2002/12/cal/icaltzd#"\r\n  xmlns:wn="http://www.w3.org/2006/03/wn/wn20/"\r\n  xmlns:foaf="http://xmlns.com/foaf/0.1/"\r\n  xmlns:rdfg ="http://www.w3.org/2004/03/trix/rdfg-1"\r\n  xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#"\r\n  xmlns:v="http://www.w3.org/2006/vcard/ns#"\r\n  xmlns:doac="http://ramonantonio.net/doac/0.1/"\r\n  xmlns:prov="http://www.w3.org/ns/prov#" >\r\n<!-- Début du document -->\r\n<rdfg:Graph rdf:nodeID="idGraph1">\r\n	<gs:hasText>\r\n		<gs:Text>\r\n		        <gs:hasTriple>\r\n				<gs:ReportedSpeech rdf:nodeID="idDeclaration11">		\r\n					<gs:hasAgent>\r\n						<foaf:Agent rdf:nodeID="idMilitary1">\r\n							<gs:type>military</gs:type>\r\n							<gs:hasAddress>\r\n								<v:Location rdf:about="www.example.com/Uganda">\r\n									<v:location-name>Uganda</v:location-name>\r\n								</v:Location>\r\n							</gs:hasAddress>\r\n						</foaf:Agent>\r\n					</gs:hasAgent>\r\n					<gs:onDate>\r\n						<gs:Date rdf:nodeID="idMonday1">\r\n							<v:dtstart>17-12-2014</v:dtstart>\r\n							<v:dtend>17-12-2014</v:dtend>\r\n						</gs:Date>\r\n					</gs:onDate>\r\n					<gs:hasContent>\r\n						<gs:ViolentAct rdf:nodeID="idViolentAct1">\r\n							<gs:onDate>\r\n								<gs:Date rdf:about="idLastYear1">\r\n									<v:dtstart>01-01-2013</v:dtstart>\r\n									<v:dtend>31-12-2013</v:dtend>\r\n								</gs:Date>\r\n							</gs:onDate>\r\n							<gs:action-place>\r\n								<v:Location rdf:about="www.example.com/CentralAfric">\r\n									<v:location-name>Central Afric</v:location-name>\r\n								</v:Location>\r\n							</gs:action-place>\r\n							<gs:isVictim>\r\n								<foaf:Person rdf:about="www.exampls.com/Commander1">\r\n									<foaf:honorific-prefix>Commander</foaf:honorific-prefix>\r\n									<gs:hasUncertainProp>\r\n										<gs:AuthorUncertainty rdf:nodeID="idUncertainty2">\r\n											<gs:deputy_to>\r\n												<foaf:Person rdf:about="www.example.com/Kony">\r\n													<foaf:name>Joseph Kony</foaf:name>\r\n													<foaf:given-name>Joseph</foaf:given-name>\r\n													<foaf:family-name>Kony</foaf:family-name>\r\n													<gs:works>\r\n														<gs:Experience rdf:nodeID="idExperience1">\r\n														<gs:role>Leader</gs:role>\r\n														<gs:relatedTo>\r\n															<foaf:Organization rdf:about="www.example.com/LRA">\r\n																<foaf:name>Lord Resistance Army</foaf:name>\r\n																<foaf:nick-name>LRA</foaf:nick-name>\r\n												<!--<gs:hasLeader rdf:resource="www.example.com/Kony"/>-->\r\n															</foaf:Organization>\r\n														</gs:relatedTo>\r\n														</gs:Experience>\r\n													</gs:works>\r\n												</foaf:Person>\r\n											</gs:deputy_to>\r\n											<gs:weight>0.7</gs:weight>\r\n										</gs:AuthorUncertainty>\r\n									</gs:hasUncertainProp>\r\n								</foaf:Person>\r\n							</gs:isVictim>\r\n						</gs:ViolentAct>\r\n					</gs:hasContent>\r\n				</gs:ReportedSpeech>		\r\n		        </gs:hasTriple>\r\n\r\n		        <gs:hasTriple>		\r\n				<gs:AuthorUncertainty rdf:nodeID="idUncertainty1">\r\n					<gs:isUncertain rdf:nodeID="idViolentAct1"/>\r\n					<gs:weight>0.6</gs:weight>\r\n				</gs:AuthorUncertainty>\r\n		        </gs:hasTriple>\r\n		</gs:Text>\r\n	</gs:hasText>\r\n	<prov:startAtTime>2014-02-17T16:43:00Z</prov:startAtTime>\r\n	<prov:endAtTime>2014-02-17T16:43:59Z</prov:endAtTime>\r\n	<prov:wasAttributedTo>\r\n		<prov:Agent rdf:about="http://www.example.com/ELIAS_BIRYABAREMA">\r\n			<prov:OnBehalfOf rdf:resource="http://www.example.com/Reuters"/>\r\n		</prov:Agent>\r\n	</prov:wasAttributedTo>\r\n	<gs:hasTrust>0.8</gs:hasTrust>\r\n	\r\n</rdfg:Graph>\r\n</rdf:RDF>', 'Reuters', 'ELIAS BIRYABAREMA', '2014-02-17'),
('007', 'A Mexican mayor and his wife were "probable masterminds" behind the disappearance of 43 student-teachers last month in the restive southwest, the country''s attorney general said on Wednesday.', '<?xml version="1.0" encoding="UTF-8"?>\r\n<rdf:RDF xmlns:owl="http://www.w3.org/2002/07/owl#"\r\n  xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"\r\n  xmlns:gs="http://www.geolsemantics.com/onto#"\r\n  xmlns:ical="http://www.w3.org/2002/12/cal/icaltzd#"\r\n  xmlns:wn="http://www.w3.org/2006/03/wn/wn20/"\r\n  xmlns:foaf="http://xmlns.com/foaf/0.1/"\r\n  xmlns:rdfg ="http://www.w3.org/2004/03/trix/rdfg-1"\r\n  xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#"\r\n  xmlns:v="http://www.w3.org/2006/vcard/ns#"\r\n  xmlns:doac="http://ramonantonio.net/doac/0.1/"\r\n  xmlns:prov="http://www.w3.org/ns/prov#" >\r\n<!-- Début du document -->\r\n<rdfg:Graph rdf:nodeID="idGraph1">\r\n	<gs:hasText>\r\n		<gs:Text>\r\n		        <gs:hasTriple>\r\n				<gs:ViolentAct rdf:nodeID="idViolentAct1">		\r\n					<gs:hasUncertainProp>\r\n						<gs:AuthorUncertainty rdf:nodeID="Uncertainty1">\r\n							<gs:hasSleepinPertner>\r\n								<foaf:Person rdf:about="www.example.com/MexicanMayor">\r\n									<gs:works>\r\n										<gs:Experience>\r\n											<gs:role>Mayor</gs:role>\r\n										</gs:Experience>\r\n									</gs:works>\r\n									<gs:nat>Mexico</gs:nat>\r\n									<gs:hasWife rdf:nodeID="id2Person"/>\r\n								</foaf:Person>\r\n							</gs:hasSleepinPertner>\r\n							<gs:hasSleepinPertner rdf:nodeID="id2Person"/>\r\n							<gs:weight>0.7</gs:weight>\r\n						</gs:AuthorUncertainty>\r\n					</gs:hasUncertainProp>						\r\n					<gs:hasVictim>\r\n						<foaf:Group rdf:nodeID="id1Group">\r\n							<gs:type>student-teacher</gs:type>\r\n							<gs:nbMembers>35</gs:nbMembers>\r\n						</foaf:Group>\r\n					</gs:hasVictim>\r\n					<gs:onDate>\r\n						<gs:Date rdf:nodeID="idlastMonth1">\r\n							<v:dtstart>2014-09-01</v:dtstart>\r\n							<v:dtend>2014-09-30</v:dtend>\r\n						</gs:Date>\r\n					</gs:onDate>\r\n					<gs:actionPlace>\r\n						<v:Location rdf:nodeID="idLocation1">\r\n							<gs:country>Mexico</gs:country>\r\n							<gs:loc-indication>southwest</gs:loc-indication>\r\n						</v:Location>\r\n					</gs:actionPlace>\r\n				</gs:ViolentAct>		\r\n		        </gs:hasTriple>\r\n			\r\n		        <gs:hasTriple>\r\n				<gs:ReportedSpeech rdf:nodeID="idDeclaration1">		\r\n					<gs:hasContent>\r\n						<gs:ReportedSpeech rdf:nodeID="Uncertainty2">\r\n							<gs:hasContent rdf:nodeID="idViolentAct1"/>						\r\n							<gs:weight>0.9</gs:weight>\r\n						</gs:ReportedSpeech>\r\n					</gs:hasContent>\r\n					<gs:hasAgent>\r\n						<foaf:Person rdf:nodeID="idPerson1">\r\n							<gs:works>\r\n								<gs:Experience>\r\n									<gs:role>attorney general</gs:role>\r\n								</gs:Experience>\r\n							</gs:works>\r\n							<gs:place rdf:resource="http://www.exemple.com/Mexico"/>\r\n						</foaf:Person>\r\n					</gs:hasAgent>	\r\n					<gs:onDate>\r\n						<gs:Date rdf:nodeID="idWendnesday1">\r\n							<v:dtstart>2014-10-28</v:dtstart>\r\n							<v:dtend>2014-10-28</v:dtend>\r\n						</gs:Date>\r\n					</gs:onDate>\r\n				</gs:ReportedSpeech>	\r\n		        </gs:hasTriple>	\r\n			\r\n		</gs:Text>\r\n	</gs:hasText>\r\n	<prov:startAtTime>2014-09-28T16:43:00Z</prov:startAtTime>\r\n	<prov:endAtTime>2014-09-28T16:43:59Z</prov:endAtTime>\r\n	<prov:wasAttributedTo>\r\n		<prov:Agent rdf:about="http://www.example.com/GABRIEL_STARGARDTER">\r\n			<prov:OnBehalfOf rdf:resource="http://www.example.com/Reuters"/>\r\n		</prov:Agent>\r\n	</prov:wasAttributedTo>\r\n	<gs:hasTrust>0.8</gs:hasTrust>\r\n	\r\n</rdfg:Graph>\r\n</rdf:RDF>', 'Reuters', 'GABRIEL STARGARDTER', '2014-10-23');

-- --------------------------------------------------------

--
-- Structure de la table `Trustworthiness`
--

CREATE TABLE IF NOT EXISTS `Trustworthiness` (
  `user_source` varchar(30) NOT NULL DEFAULT '',
  `user_name` varchar(15) NOT NULL DEFAULT '',
  `source_name` varchar(15) NOT NULL DEFAULT '',
  `trust_value` decimal(3,2) DEFAULT NULL,
  PRIMARY KEY (`user_name`,`source_name`),
  KEY `source_name` (`source_name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `Trustworthiness`
--

INSERT INTO `Trustworthiness` (`user_source`, `user_name`, `source_name`, `trust_value`) VALUES
('Martin_Lefigaro', 'Anne_Martin', 'Lefigaro', 0.80),
('Martin_Reuters', 'Anne_Martin', 'Reuters', 0.70),
('Martin_TheNewYorkTimes', 'Anne_Martin', 'TheNewYorkTimes', 0.60),
('Fadhela_Test Aljazeera_en', 'Fadhela_Test', 'Aljazeera_en', 0.50),
('Fadhela_Test Lefigaro', 'Fadhela_Test', 'Lefigaro', 0.40),
('Smith_Aljazeera', 'Jack_Smith', 'Aljazeera_en', 0.40),
('Smith_Lefigaro', 'Jack_Smith', 'Lefigaro', 0.70),
('Smith_Lemonde', 'Jack_Smith', 'Lemonde', 0.50),
('Smith_Reuters', 'Jack_Smith', 'Reuters', 0.80),
('Smith_Theguardian_uk', 'Jack_Smith', 'Theguardian_uk', 0.60),
('Smith_TheNewYorkTimes', 'Jack_Smith', 'TheNewYorkTimes', 0.80),
('Williams_Lemonde', 'John_Williams', 'Lemonde', 0.70);

-- --------------------------------------------------------

--
-- Structure de la table `User`
--

CREATE TABLE IF NOT EXISTS `User` (
  `user_name` varchar(25) NOT NULL,
  `first_name` varchar(25) NOT NULL,
  `last_name` varchar(25) NOT NULL,
  `organization` varchar(40) NOT NULL,
  `home_page` varchar(40) NOT NULL,
  `password` varchar(15) NOT NULL,
  PRIMARY KEY (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `User`
--

INSERT INTO `User` (`user_name`, `first_name`, `last_name`, `organization`, `home_page`, `password`) VALUES
('Anne_Martin', 'Anne', 'Martin', 'Google', '', 'anneM'),
('Fadhela_Test', 'Fadhela', 'Test', 'MLV', '', 'fadhelaT'),
('Jack_Smith', 'Jack', 'Smith', 'Facebook', '', 'JackS'),
('John_Williams', 'John', 'Williams', 'Twitter', '', 'JohnW'),
('Marie_Dupont', 'Marie', 'Dupont', 'Yahoo', '', 'MarieD');

--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `Trustworthiness`
--
ALTER TABLE `Trustworthiness`
  ADD CONSTRAINT `Trustworthiness_ibfk_1` FOREIGN KEY (`user_name`) REFERENCES `User` (`user_name`),
  ADD CONSTRAINT `Trustworthiness_ibfk_2` FOREIGN KEY (`source_name`) REFERENCES `Source` (`source_name`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
