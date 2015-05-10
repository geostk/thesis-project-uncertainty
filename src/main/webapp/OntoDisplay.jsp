<%-- 
    Document   : OntoDisplay
    Created on : 27 mars 2015, 10:02:38
    Author     : Fadhela
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html><head><title>ontology hierarchy</title>
        <SCRIPT LANGUAGE="JavaScript">
            //document.write("On va afficher un message d'alerte")

            function afficher()
            {
            alert("Veuillez vérifié l'encodage de votre navigateur.\n" +
                    "- Sur Chrome: Menu => Plus d\'outils => Codage, Cocher Unicode (UTF-8).\n" +
                    "- Sur Firefox: Affichage => Encodage de caractères, Cocher Unicode. \n  Merci.")
            }
        </SCRIPT>
        <link rel="stylesheet" href="Style.css" type="text/css" media="all" />
        <script type="text/javascript" src="js/CollapsibleLists.js"></script><link type="text/css" href="css/list.css" rel="stylesheet"/></head>
    <body onload="initialise()">
        <div id="navigation">
            <ul>
                <li><a href="index.jsp">HOME</a></li>
                <li><a href="LoginCheck">User information</a></li>
                <li><a href="ShowText">Texts</a></li>
                <li><a href="OntoDisplay.jsp">Ontology</a></li>
                <li><a href="About">ABOUT</a></li>
                <li><a href="#">CONTACT</a></li>
            </ul>
            <div class=\"cl\">&nbsp;</div>
        </div>
        <br>
        <p align="JUSTIFY">
            Si les caratères non latins ne s'affichent pas, <a href="javascript:afficher()">cliquez ici</a>.</p>

        <table><tr><td>
                    <ul class="treeView"><li>Thing
                            <ul class="collapsibleList">  
                                <li onclick="displayProp(event, 'instrument_Object	action-agent_Agent	onDate_Date	actionPlace_Loc', 'action-cause_ 	action-name_string', 'null	fr:Action	en:Action	zh:行为	ar:null')">Action 
                                    <ul> 
                                        <li onclick="displayProp(event, 'eventDeclared_Event	sender_Agent	receiver_Agent', 'messageTitle_string	content_string', 'null	fr:Transmission de messages	en:Emission	zh:信息传送	ar:null')">Emission 
                                            <ul>
                                                <li onclick="displayProp(event, 'accordingTo_Agent', 'null', 'null	fr:null	en:null	zh:null	ar:null')">ReportedSpeech</li> 
                                            </ul>

                                        </li>
                                        <li onclick="displayProp(event, 'locend_Loc	transferLoc_Loc	locbeg_Loc	displaced_PhysicalEntity	transfer-dateEnd_Date	transfer-dateBeg_Date', 'transfer-type_string	mOfT_string', 'null	fr:Déplacement	en:Displacement	zh:移动	ar:null')">Transfer</li> 
                                        <li onclick="displayProp(event, 'result_Object	component_Object	making-instrument_Object', 'making-type_string', 'null	fr:Fabrication d'un objet	en:Making	zh:制造	ar:null')">Making</li> 
                                        <li onclick="displayProp(event, 'amount_MonetaryMes', 'null', 'null	fr:transaction bancaire	en:Banking transaction	zh:银行交易	ar:null')">BankingTransaction <ul>
                                                <li onclick="displayProp(event, 'hasOrder_Order	bought_Entity', 'paymentType_string', 'null	fr:Achat d'un bien	en:Purchase	zh:购置物	ar:null')">Purchase</li>
                                                <li onclick="displayProp(event, 'transmitter_Agent	beneficiary_Agent', 'null', 'null	fr:Echange d'argent	en:Money trading	zh:金钱交易	ar:null')">MoneyTransaction</li>
                                                <li onclick="displayProp(event, 'hasInvestor_Investor	totalFundraising_Percentage', 'round_ ', 'null	fr:Levée de fond	en:Fundraising	zh:募款	ar:null')">Fundraising</li>
                                                <li onclick="displayProp(event, 'boughtBy_Buyer	orgEarned_Organization	acquisition-date_Date	soldBy_Seller', 'null', 'null	fr:Achat d'une entreprise	en:Acquisition of an organization	zh:收购企业	ar:null')">Acquisition</li>
                                            </ul></li>
                                        <li onclick="displayProp(event, 'null', 'medicalAct-type_ ', 'null	fr:Action médicale	en:Medical action	zh:医疗事件	ar:null')">MedicalAct</li>
                                        <li onclick="displayProp(event, 'hasWitness_Persons	target_PhysicalEntity	victNum_GroupPers	violentAct-instrument_Object	sponsor_Agent	hasVictim_Victim', 'violentAct-type_string', 'null	fr:Acte de violence	en:Violent act	zh:暴力行为	ar:null')">ViolentAct</li>
                                    </ul></li>  <li onclick="displayProp(event, 'dateRef_Date', 'dtend_ 	day_string', 'null	fr:Date	en:Date	zh:日期	ar:null')">Date</li>  <li onclick="displayProp(event, 'dateend_Date	duration_Mes	datebeg_Date	dateInterval_Date', 'null', 'null	fr:null	en:null	zh:null	ar:null')">Dates <ul>
                                        <li onclick="displayProp(event, 'nbVictims_GroupPers', 'desease-name_ ', 'null	fr:Maladie	en:Disease	zh:疾病	ar:null')">Disease</li>
                                        <li onclick="displayProp(event, 'event-relatedTo_EventRelation	event-duration_FixedOrInterval	envolve_Entity	eventPlace_Loc	event-cause_Thing	event-date_Date', 'event-type_ 	cause_', 'null	fr:Evénement	en:Event	zh:事件	ar:null')">Event <ul>
                                                <li onclick="displayProp(event, 'orgConcerned_Organization	decidedBy_Agent', 'null', 'null	fr:null	en:null	zh:null	ar:null')">OrganizationEvent <ul>
                                                        <li onclick="displayProp(event, 'null', 'newName_ 	oldTicker_string', 'null	fr:Changement de nom	en:Name change	zh:名称更换	ar:null')">NameChange</li>
                                                        <li onclick="displayProp(event, 'hasInvestor_Investor	totalFundraising_Percentage', 'round_ ', 'null	fr:Levée de fond	en:Fundraising	zh:募款	ar:null')">Fundraising</li>
                                                        <li onclick="displayProp(event, 'null', 'null', 'null	fr:Fermeture	en:Closure	zh:倒闭	ar:null')">OrgClosure</li>
                                                        <li onclick="displayProp(event, 'receivership-admin_Person', 'null', 'null	fr:Redressement judiciaire	en:Receivership	zh:破产管理	ar:null')">Receivership</li>
                                                        <li onclick="displayProp(event, 'creator_Agent', 'null', 'null	fr:Création d'une entreprise	en:Creation of an organization	zh:公司成立	ar:null')">OrgCreation</li>
                                                        <li onclick="displayProp(event, 'personsConcerned_Persons	hoursByDay_Mes', 'null', 'null	fr:Chomâge partiel	en:Partial unemployment	zh:半工	ar:null')">PartialUnemployment</li>
                                                        <li onclick="displayProp(event, 'null', 'null', 'null	fr:Dépôt de bilan	en:Voluntary liquidation	zh:破产呈报	ar:null')">VoluntaryLiquidation</li>
                                                        <li onclick="displayProp(event, 'liquidator_Person	employeeRepresentative_Person	bankruptcy-judge_Person', 'null', 'null	fr:Liquidation judiciaire	en:Compulsory liquidation	zh:司法清盘	ar:null')">CompulsoryLiquidation</li>
                                                    </ul></li>
                                                <li onclick="displayProp(event, 'wasPresent_Agent', 'comType_string', 'null	fr:Rencontre / contact	en:Meeting/Contact	zh:会面 / 接洽	ar:null')">Meeting</li>
                                                <li onclick="displayProp(event, 'personConcerned_Persons', 'null', 'null	fr:Evénement lié à la vie personne	en:Personal event	zh:人生经历	ar:null')">PersonalEvent <ul>
                                                        <li onclick="displayProp(event, 'partner1_Person	partner2_Person', 'null', 'null	fr:null	en:null	zh:null	ar:null')">UnionDisunion <ul>
                                                                <li onclick="displayProp(event, 'union-agent_Agent	union-witness_Persons	union-guest_Persons', 'union-type_ ', 'null	fr:Union / Mariage	en:Union	zh:结合 / Mariage	ar:null')">Union</li>
                                                                <li onclick="displayProp(event, 'disunion-agent_Agent	disunion-witness_Person', 'disunion-type_ 	disunionType_string', 'null	fr:Séparation / Divorce	en:Disunion	zh:分手 / 离婚	ar:null')">Disunion</li>
                                                            </ul></li>
                                                        <li onclick="displayProp(event, 'decisionMaker_Agent', 'null', 'null	fr:Fin de l'occupation d'un poste	en:Experience End	zh:离职	ar:null')">ExperienceEnd</li>
                                                        <li onclick="displayProp(event, 'death-place_Loc	ageOfDeath_Mes	celebrate_Funerals	death-date_Date	deceased_Persons', 'null', 'null	fr:Mort	en:Death	zh:死亡	ar:null')">Death</li>
                                                        <li onclick="displayProp(event, 'birth-agent_Person	birth-date_Date	birth-place_Loc', 'null', 'null	fr:Naissance	en:Birth	zh:出生	ar:null')">Birth</li>
                                                        <li onclick="displayProp(event, 'appointedTo_Experience', 'null', 'null	fr:Nomination	en:Appointment	zh:约会	ar:null')">Appointment</li>
                                                    </ul></li>
                                                <li onclick="displayProp(event, 'null', 'null', 'null	fr:null	en:null	zh:null	ar:null')">GeneralEvent <ul>
                                                        <li onclick="displayProp(event, 'null', 'null', 'null	fr:null	en:null	zh:null	ar:null')">SportEvent</li>
                                                        <li onclick="displayProp(event, 'null', 'null', 'null	fr:null	en:null	zh:null	ar:null')">SocialEvent</li>
                                                        <li onclick="displayProp(event, 'null', 'null', 'null	fr:null	en:null	zh:null	ar:null')">PoliticalEvent</li>
                                                    </ul></li>
                                                <li onclick="displayProp(event, 'locend_Loc	transferLoc_Loc	locbeg_Loc	displaced_PhysicalEntity	transfer-dateEnd_Date	transfer-dateBeg_Date', 'transfer-type_string	mOfT_string', 'null	fr:Déplacement	en:Displacement	zh:移动	ar:null')">Transfer</li>
                                                <li onclick="displayProp(event, 'funerals-guest_Persons', 'null', 'null	fr:Funérailles	en:Funerals	zh:葬礼	ar:null')">Funerals</li>
                                                <li onclick="displayProp(event, 'isCharged_Persons', 'null', 'null	fr:Justice	en:Justice	zh:司法	ar:null')">LegalProceedings <ul>
                                                        <li onclick="displayProp(event, 'judge_Person	pronounce_JudicialDecision', 'null', 'null	fr:Jugement	en:Judgment	zh:裁判	ar:null')">Judgment</li>
                                                        <li onclick="displayProp(event, 'present_Agent	chairedBy_Agent', 'null', 'null	fr:Audience au tribunal de justice	en:Court hearing	zh:庭审	ar:null')">CourtHearing</li>
                                                        <li onclick="displayProp(event, 'agent-policeCustody_Agent', 'null', 'null	fr:Garde à vue	en:Police custody	zh:拘留	ar:null')">PoliceCustody</li>
                                                        <li onclick="displayProp(event, 'receivership-admin_Person', 'null', 'null	fr:Redressement judiciaire	en:Receivership	zh:破产管理	ar:null')">Receivership</li>
                                                        <li onclick="displayProp(event, 'agent-release_Agent', 'releaseType_string', 'null	fr:Libération	en:Release	zh:释放	ar:null')">Release</li>
                                                        <li onclick="displayProp(event, 'agent-arrest_ ', 'null', 'null	fr:Arrestation	en:Arrest	zh:逮捕	ar:null')">Arrest</li>
                                                        <li onclick="displayProp(event, 'agent-indictment_ ', 'null', 'null	fr:Mise en examen	en:Indictment	zh:起诉	ar:null')">Indictment</li>
                                                        <li onclick="displayProp(event, 'liquidator_Person	employeeRepresentative_Person	bankruptcy-judge_Person', 'null', 'null	fr:Liquidation judiciaire	en:Compulsory liquidation	zh:司法清盘	ar:null')">CompulsoryLiquidation</li>
                                                        <li onclick="displayProp(event, 'agent-policeCheck_Agent', 'null', 'null	fr:Contrôle de police	en:Police check	zh:警察检查	ar:null')">PoliceCheck</li>
                                                        <li onclick="displayProp(event, 'null', 'null', 'null	fr:Incarceration	en:Incarceration	zh:null	ar:null')">Incarceration</li>
                                                        <li onclick="displayProp(event, 'null', 'null', 'null	fr:Dépôt de bilan	en:Voluntary liquidation	zh:破产呈报	ar:null')">VoluntaryLiquidation</li>
                                                    </ul></li>
                                            </ul></li>
                                        <li onclick="displayProp(event, 'accountServiceHomepage_Website', 'accountName_string', 'null	fr:Compte en ligne	en:Online account	zh:在线账户	ar:null')">OnlineAccount <ul>
                                                <li onclick="displayProp(event, 'null', 'mailAddress_ ', 'null	fr:Adresse mail	en:Email address	zh:电邮地址	ar:null')">MailAddress</li>
                                            </ul></li>
                                        <li onclick="displayProp(event, 'experience-duration_Mes	hasColleague_Agent	worksIn_Loc	expEnd_ExperienceEnd	worksAt_Organization	expBeg_Appointment	employer_Agent	hasSuperior_Agent', 'role_string', 'null	fr:Expérience professionnelle	en:Professional experience	zh:职业经验	ar:null')">Experience</li>
                                        <li onclick="displayProp(event, 'pers1_Persons	pers2_Persons', 'familyRelType_string', 'null	fr:Relation familiale	en:Family Relation	zh:null	ar:null')">Family</li>
                                        <li onclick="displayProp(event, 'page_ElectronicDocument	foundedBy_Agent	project-member_Agent', 'project-name_ 	theme_string', 'null	fr:Projet	en:Project	zh:项目	ar:null')">Project</li>
                                    </ul></li>  <li onclick="displayProp(event, 'nbVictims_GroupPers', 'desease-name_ ', 'null	fr:Maladie	en:Disease	zh:疾病	ar:null')">Disease</li>  <li onclick="displayProp(event, 'documentOwner_Agent	maker_Agent', 'document-name_ ', 'null	fr:Document	en:Document	zh:文件	ar:null')">Document <ul>
                                        <li onclick="displayProp(event, 'hosted_Server', 'image-link_ ', 'null	fr:null	en:null	zh:null	ar:null')">Image</li>
                                        <li onclick="displayProp(event, 'null', 'linkAdress_ ', 'null	fr:Document électronique	en:Electronic document	zh:电子文档	ar:null')">ElectronicDocument</li>
                                    </ul></li>  <li onclick="displayProp(event, 'null', 'null', 'null	fr:null	en:null	zh:null	ar:null')">Economics <ul>
                                        <li onclick="displayProp(event, 'null', 'null', 'null	fr:Bourse	en:Stock exchange	zh:股票交易	ar:null')">StockExchange <ul>
                                                <li onclick="displayProp(event, 'marketConcerned_MarketPlace', 'ticker-name_string', 'null	fr:null	en:null	zh:null	ar:null')">Ticker</li>
                                                <li onclick="displayProp(event, 'shares-price_MonetaryMes	total-raised_MonetaryMes	nbShares_Number	shares-percent_Percentage	hasShare_Share	total-estimated_MonetaryMes', 'null', 'null	fr:Ensemble de parts	en:Share set	zh:null	ar:null')">ShareSet</li>
                                                <li onclick="displayProp(event, 'hasPrice_MonetaryMes	valuation-amount_FixedOrInterval	hasChanged_PriceChange	dateCalcul-priceShare_Date', 'null', 'null	fr:Action	en:Share	zh:null	ar:null')">Share</li>
                                                <li onclick="displayProp(event, 'coveragePercent_Mes	geographicScope_Location	percentageOfMarket-dateCal_Date	percentageOfMarket-comp_Company', 'areaOfActivity_ ', 'null	fr:Pourcentage du marché	en:Percentage of the market	zh:市场份额	ar:null')">PercentageOfMarket</li>
                                                <li onclick="displayProp(event, 'statusDate_Date', 'statusIPO_ ', 'null	fr:null	en:null	zh:null	ar:null')">IPO-Step</li>
                                                <li onclick="displayProp(event, 'hasUnderwriter_Company	compIntroduced_Company	hasStatus_IPO-Step	date-IPO_Date', 'ipo-type_ ', 'null	fr:Entrée en bourse	en:IPO	zh:null	ar:null')">IPO</li>
                                            </ul></li>
                                        <li onclick="displayProp(event, 'orderDate_Date	price_Mes	supplier_Agent	client_Agent	deliveryDate_Date', 'product_ ', 'null	fr:Commande	en:Order 	zh:订货单	ar:null')">Order</li>
                                        <li onclick="displayProp(event, 'proposedBy_Agent	offer-dateBeg_Date	offer-dateEnd_Date	offer-price_FixedOrInterval	product-offer_Entity', 'offer-terms_ ', 'null	fr:Offre de vente	en:Offer	zh:报价	ar:null')">Offer</li>
                                        <li onclick="displayProp(event, 'capital-value_MonetaryMes	compConcerned_Company	ebit-value_MonetaryMes	turnover-value_MonetaryMes	calculation-date_Date	loss-value_MonetaryMes', 'benefit-value_ ', 'null	fr:主要性能指标	en:Key Performance Indicators	zh:null	ar:null')">KPI</li>
                                    </ul></li>  <li onclick="displayProp(event, 'null', 'label_string	entity-name_string', 'null	fr:Entité nommée	en:Named entity	zh:实体名词	ar:null')">Entity <ul>
                                        <li onclick="displayProp(event, 'hasAward_Award', 'null', 'null	fr:null	en:null	zh:null	ar:null')">PhysicalEntity <ul>
                                                <li onclick="displayProp(event, 'hasAddress_Loc	nsOwner_DNS	worksOn_Project	homepage_Website	mboxPro_MailAddress	mbox_MailAddress	workTel_string	hasObject_Object	resides_LocOccupation', 'religious_affiliation_ 	nat_string	workTel_string	nick_string', 'null	fr:Agent	en:Agent	zh:代理人	ar:null')">Agent <ul>
                                                        <li onclick="displayProp(event, 'leader_Persons	activityDuration_Mes	nbEmployees_GroupPers	founder_Agent	logo_Image	orgOwner_Agent', 'organization-aim_string	organization-type_string	businessSector_string', 'null	fr:null	en:null	zh:null	ar:null')">Organizations <ul>
                                                                <li onclick="displayProp(event, 'null', 'null', 'null	fr:Groupe d'organisations	en:Group of oganizations	zh:企业集团	ar:null')">GroupOrg</li>
                                                                <li onclick="displayProp(event, 'isPartOf_Organization	closed_OrgClosure	wasCreated_OrgCreation', 'organization-name_string', 'null	fr:Organisme	en:Organization	zh:组织	ar:null')">Organization <ul>
                                                                        <li onclick="displayProp(event, 'null', 'null', 'null	fr:Place de marché	en:Market	zh:null	ar:null')">MarketPlace</li>
                                                                        <li onclick="displayProp(event, 'hasShareholder_Buyer	hasTicker_Ticker	hasKPI_KPI', 'rcNumber_ ', 'null	fr:Entreprise	en:Company	zh:企业	ar:null')">Company</li>
                                                                        <li onclick="displayProp(event, 'null', 'siren_ ', 'null	fr:Organisme à but non lucratif	en:Non profit organization	zh:非盈利机构	ar:null')">NPO</li>
                                                                    </ul></li>
                                                            </ul></li>
                                                        <li onclick="displayProp(event, 'hasMember_Agent	nbMembers_Number', 'nbMemberMax_ 	group-type_string', 'null	fr:Groupe	en:Group	zh:群体	ar:null')">Group <ul>
                                                                <li onclick="displayProp(event, 'null', 'null', 'null	fr:Groupe de personnes	en:Group of persons	zh:团队	ar:null')">GroupPers</li>
                                                                <li onclick="displayProp(event, 'null', 'null', 'null	fr:Groupe d'organisations	en:Group of oganizations	zh:企业集团	ar:null')">GroupOrg</li>
                                                            </ul></li>
                                                        <li onclick="displayProp(event, 'hasMedicalAct_MedicalAct	works_Experience	studies_Education	age_Mes	hasDisease_Disease	tradeunion_membership_Organization	familyRel_Persons', 'gender_null	familyName_string', 'null	fr:Classe abstraite	en:Abstract class	zh:虚类	ar:null')">Persons <ul>
                                                                <li onclick="displayProp(event, 'null', 'null', 'null	fr:Groupe de personnes	en:Group of persons	zh:团队	ar:null')">GroupPers</li>
                                                                <li onclick="displayProp(event, 'son_Person	account_OnlineAccount	nbSibling_GroupPers	nbChildren_GroupPers	deathAct_Death	birthAct_Birth	mboxPers_MailAddress	knows_Person', 'birthAdditionalName_ 	additional-name_string	givenName_string	honorific-prefix_string	honorific-suffix_string', 'null	fr:Personne	en:Person	zh:人	ar:null')">Person</li>
                                                            </ul></li>
                                                    </ul></li>
                                                <li onclick="displayProp(event, 'null', 'null', 'null	fr:null	en:null	zh:null	ar:null')">Objects <ul>
                                                        <li onclick="displayProp(event, 'measure_Mes', 'unity_ ', 'null	fr:Quantité d'objets	en:objects quantity	zh:物件数量	ar:null')">Quantity</li>
                                                        <li onclick="displayProp(event, 'objectOwner_Agent	object-producer_Agent	composed_Object	object-price_MonetaryMes', 'object-color_string	object-origin_string	object-name_string', 'null	fr:Objet	en:Object	zh:物件	ar:null')">Object <ul>
                                                                <li onclick="displayProp(event, 'null', 'brand_ ', 'null	fr:Produit	en:Product	zh:物品	ar:null')">Product <ul>
                                                                        <li onclick="displayProp(event, 'null', 'null', 'null	fr:null	en:null	zh:null	ar:null')">Vehicle</li>
                                                                    </ul></li>
                                                            </ul></li>
                                                    </ul></li>
                                            </ul></li>
                                        <li onclick="displayProp(event, 'null', 'lat_long	long_long	loc-detail_string', 'null	fr:null	en:null	zh:null	ar:null')">Loc <ul>
                                                <li onclick="displayProp(event, 'num_Number	locality_Location	hasCountry_Country	districtNum_Number', 'postal-code_int	extended-address_string	strName_string	post-office-box_string	cedex_string	lieudit_string', 'null	fr:Adresse	en:Address	zh:地址	ar:null')">Address</li>
                                                <li onclick="displayProp(event, 'null', 'building-name_string', 'null	fr:Bâtiment	en:Building	zh:建筑	ar:null')">Building</li>
                                                <li onclick="displayProp(event, 'hasCapital_City	locatedIn_Location	altitude_Mes	hasLocation_Location	capitalOf_Location', 'location-name_string', 'null	fr:Lieu	en:Location	zh:地点	ar:null')">Location <ul>
                                                        <li onclick="displayProp(event, 'null', 'null', 'null	fr:Village	en:Village	zh:村庄	ar:null')">Village</li>
                                                        <li onclick="displayProp(event, 'null', 'null', 'null	fr:Région	en:Region	zh:地区	ar:null')">Region</li>
                                                        <li onclick="displayProp(event, 'null', 'country-name_string', 'null	fr:Pays	en:Country	zh:国家	ar:null')">Country</li>
                                                        <li onclick="displayProp(event, 'null', 'null', 'null	fr:Continent	en:Continent	zh:洲	ar:null')">Continent</li>
                                                        <li onclick="displayProp(event, 'null', 'null', 'null	fr:Ville	en:City	zh:城市	ar:null')">City</li>
                                                    </ul></li>
                                            </ul></li>
                                    </ul></li>  <li onclick="displayProp(event, 'event-relatedTo_EventRelation	event-duration_FixedOrInterval	envolve_Entity	eventPlace_Loc	event-cause_Thing	event-date_Date', 'event-type_ 	event-cause_Thing', 'null	fr:Evénement	en:Event	zh:事件	ar:null')">Event <ul>
                                        <li onclick="displayProp(event, 'orgConcerned_Organization	decidedBy_Agent', 'null', 'null	fr:null	en:null	zh:null	ar:null')">OrganizationEvent <ul>
                                                <li onclick="displayProp(event, 'null', 'newName_ 	oldTicker_string', 'null	fr:Changement de nom	en:Name change	zh:名称更换	ar:null')">NameChange</li>
                                                <li onclick="displayProp(event, 'hasInvestor_Investor	totalFundraising_Percentage', 'round_ ', 'null	fr:Levée de fond	en:Fundraising	zh:募款	ar:null')">Fundraising</li>
                                                <li onclick="displayProp(event, 'null', 'null', 'null	fr:Fermeture	en:Closure	zh:倒闭	ar:null')">OrgClosure</li>
                                                <li onclick="displayProp(event, 'receivership-admin_Person', 'null', 'null	fr:Redressement judiciaire	en:Receivership	zh:破产管理	ar:null')">Receivership</li>
                                                <li onclick="displayProp(event, 'creator_Agent', 'null', 'null	fr:Création d'une entreprise	en:Creation of an organization	zh:公司成立	ar:null')">OrgCreation</li>
                                                <li onclick="displayProp(event, 'personsConcerned_Persons	hoursByDay_Mes', 'null', 'null	fr:Chomâge partiel	en:Partial unemployment	zh:半工	ar:null')">PartialUnemployment</li>
                                                <li onclick="displayProp(event, 'null', 'null', 'null	fr:Dépôt de bilan	en:Voluntary liquidation	zh:破产呈报	ar:null')">VoluntaryLiquidation</li>
                                                <li onclick="displayProp(event, 'liquidator_Person	employeeRepresentative_Person	bankruptcy-judge_Person', 'null', 'null	fr:Liquidation judiciaire	en:Compulsory liquidation	zh:司法清盘	ar:null')">CompulsoryLiquidation</li>
                                            </ul></li>
                                        <li onclick="displayProp(event, 'wasPresent_Agent', 'comType_string', 'null	fr:Rencontre / contact	en:Meeting/Contact	zh:会面 / 接洽	ar:null')">Meeting</li>
                                        <li onclick="displayProp(event, 'personConcerned_Persons', 'null', 'null	fr:Evénement lié à la vie personne	en:Personal event	zh:人生经历	ar:null')">PersonalEvent <ul>
                                                <li onclick="displayProp(event, 'partner1_Person	partner2_Person', 'null', 'null	fr:null	en:null	zh:null	ar:null')">UnionDisunion <ul>
                                                        <li onclick="displayProp(event, 'union-agent_Agent	union-witness_Persons	union-guest_Persons', 'union-type_ ', 'null	fr:Union / Mariage	en:Union	zh:结合 / Mariage	ar:null')">Union</li>
                                                        <li onclick="displayProp(event, 'disunion-agent_Agent	disunion-witness_Person', 'disunion-type_ 	disunionType_string', 'null	fr:Séparation / Divorce	en:Disunion	zh:分手 / 离婚	ar:null')">Disunion</li>
                                                    </ul></li>
                                                <li onclick="displayProp(event, 'decisionMaker_Agent', 'null', 'null	fr:Fin de l'occupation d'un poste	en:Experience End	zh:离职	ar:null')">ExperienceEnd</li>
                                                <li onclick="displayProp(event, 'death-place_Loc	ageOfDeath_Mes	celebrate_Funerals	death-date_Date	deceased_Persons', 'null', 'null	fr:Mort	en:Death	zh:死亡	ar:null')">Death</li>
                                                <li onclick="displayProp(event, 'birth-agent_Person	birth-date_Date	birth-place_Loc', 'null', 'null	fr:Naissance	en:Birth	zh:出生	ar:null')">Birth</li>
                                                <li onclick="displayProp(event, 'appointedTo_Experience', 'null', 'null	fr:Nomination	en:Appointment	zh:约会	ar:null')">Appointment</li>
                                            </ul></li>
                                        <li onclick="displayProp(event, 'null', 'null', 'null	fr:null	en:null	zh:null	ar:null')">GeneralEvent <ul>
                                                <li onclick="displayProp(event, 'null', 'null', 'null	fr:null	en:null	zh:null	ar:null')">SportEvent</li>
                                                <li onclick="displayProp(event, 'null', 'null', 'null	fr:null	en:null	zh:null	ar:null')">SocialEvent</li>
                                                <li onclick="displayProp(event, 'null', 'null', 'null	fr:null	en:null	zh:null	ar:null')">PoliticalEvent</li>
                                            </ul></li>
                                        <li onclick="displayProp(event, 'locend_Loc	transferLoc_Loc	locbeg_Loc	displaced_PhysicalEntity	transfer-dateEnd_Date	transfer-dateBeg_Date', 'transfer-type_string	mOfT_string', 'null	fr:Déplacement	en:Displacement	zh:移动	ar:null')">Transfer</li>
                                        <li onclick="displayProp(event, 'funerals-guest_Persons', 'null', 'null	fr:Funérailles	en:Funerals	zh:葬礼	ar:null')">Funerals</li>
                                        <li onclick="displayProp(event, 'isCharged_Persons', 'null', 'null	fr:Justice	en:Justice	zh:司法	ar:null')">LegalProceedings <ul>
                                                <li onclick="displayProp(event, 'judge_Person	pronounce_JudicialDecision', 'null', 'null	fr:Jugement	en:Judgment	zh:裁判	ar:null')">Judgment</li>
                                                <li onclick="displayProp(event, 'present_Agent	chairedBy_Agent', 'null', 'null	fr:Audience au tribunal de justice	en:Court hearing	zh:庭审	ar:null')">CourtHearing</li>
                                                <li onclick="displayProp(event, 'agent-policeCustody_Agent', 'null', 'null	fr:Garde à vue	en:Police custody	zh:拘留	ar:null')">PoliceCustody</li>
                                                <li onclick="displayProp(event, 'receivership-admin_Person', 'null', 'null	fr:Redressement judiciaire	en:Receivership	zh:破产管理	ar:null')">Receivership</li>
                                                <li onclick="displayProp(event, 'agent-release_Agent', 'releaseType_string', 'null	fr:Libération	en:Release	zh:释放	ar:null')">Release</li>
                                                <li onclick="displayProp(event, 'agent-arrest_ ', 'null', 'null	fr:Arrestation	en:Arrest	zh:逮捕	ar:null')">Arrest</li>
                                                <li onclick="displayProp(event, 'agent-indictment_ ', 'null', 'null	fr:Mise en examen	en:Indictment	zh:起诉	ar:null')">Indictment</li>
                                                <li onclick="displayProp(event, 'liquidator_Person	employeeRepresentative_Person	bankruptcy-judge_Person', 'null', 'null	fr:Liquidation judiciaire	en:Compulsory liquidation	zh:司法清盘	ar:null')">CompulsoryLiquidation</li>
                                                <li onclick="displayProp(event, 'agent-policeCheck_Agent', 'null', 'null	fr:Contrôle de police	en:Police check	zh:警察检查	ar:null')">PoliceCheck</li>
                                                <li onclick="displayProp(event, 'null', 'null', 'null	fr:Incarceration	en:Incarceration	zh:null	ar:null')">Incarceration</li>
                                                <li onclick="displayProp(event, 'null', 'null', 'null	fr:Dépôt de bilan	en:Voluntary liquidation	zh:破产呈报	ar:null')">VoluntaryLiquidation</li>
                                            </ul></li>
                                    </ul></li>  <li onclick="displayProp(event, 'offset_FixedOrInterval', 'eventRelation-type_ ', 'null	fr:null	en:null	zh:null	ar:null')">EventRelation</li>  <li onclick="displayProp(event, 'null', 'null', 'null	fr:null	en:null	zh:null	ar:null')">JudicialDecision <ul>
                                        <li onclick="displayProp(event, 'stcePlace_Loc	stceMes_Mes	stceBeg_Date	stceEnd_Date', 'stceType_string', 'null	fr:Peine	en:Sentence	zh:判决	ar:null')">Stce</li>
                                    </ul></li>  <li onclick="displayProp(event, 'null', 'null', 'null	fr:null	en:null	zh:null	ar:null')">LocSpecificity <ul>
                                        <li onclick="displayProp(event, 'to_Loc	from_Loc', 'direction_ ', 'null	fr:Direction/Situation	en:Direction/Situation	zh:方位	ar:null')">LocSituations</li>
                                        <li onclick="displayProp(event, 'position1_Loc	position2_Loc', 'orientation-type_string', 'null	fr:Orientation	en:Orientation	zh:null	ar:null')">LocOrientation</li>
                                        <li onclick="displayProp(event, 'occupationDuration_FixedOrInterval	leaveOn_Date	moveInto_Loc	occupedBy_Agent	moveIntoOn_Date', 'occupation-type_ ', 'null	fr:Occupation du lieu	en:Housting	zh:null	ar:null')">LocOccupation</li>
                                        <li onclick="displayProp(event, 'loc1_Loc	loc2_Loc	distMes_Mes', 'null', 'null	fr:Distance entre deux lieux	en:Distance	zh:距离	ar:null')">Distance</li>
                                    </ul></li>  <li onclick="displayProp(event, 'objectQuantified_Object	hasValue_Number', 'mes-indication_ ', 'null	fr:null	en:null	zh:null	ar:null')">Measurement <ul>
                                        <li onclick="displayProp(event, 'null', 'null', 'null	fr:null	en:null	zh:null	ar:null')">FixedOrInterval <ul>
                                                <li onclick="displayProp(event, 'measure_Mes', 'unity_ ', 'null	fr:Quantité d'objets	en:objects quantity	zh:物件数量	ar:null')">Quantity</li>
                                                <li onclick="displayProp(event, 'null', 'unit_string', 'null	fr:Mesure	en:Measure	zh:度量	ar:null')">Mes <ul>
                                                        <li onclick="displayProp(event, 'null', 'null', 'null	fr:Pourcentage	en:Percentage	zh:份额	ar:null')">Percentage</li>
                                                        <li onclick="displayProp(event, 'null', 'null', 'null	fr:Mesure monétaire	en:Monetary measure	zh:货币单位	ar:null')">MonetaryMes</li>
                                                    </ul></li>
                                                <li onclick="displayProp(event, 'null', 'interval-max_ ', 'null	fr:Intervalle	en:Interval	zh:区间	ar:null')">Interval</li>
                                            </ul></li>
                                    </ul></li>  <li onclick="displayProp(event, 'null', 'null', 'null	fr:null	en:null	zh:null	ar:null')">MesSpecificity <ul>
                                        <li onclick="displayProp(event, 'mesCompare-date_Date	mes2_Mes	mes1_Mes', 'compIndication_ ', 'null	fr:Comparaison de mesures	en:Measure comparer	zh:测量对比	ar:null')">MesCompare</li>
                                        <li onclick="displayProp(event, 'conversion-mes2_Mes	conversion-mes1_Mes	conversion-date_Date', 'conversion-rate_ ', 'null	fr:Comparaison de mesures	en:Measure comparer	zh:测量对比	ar:null')">Conversion</li>
                                    </ul></li>  <li onclick="displayProp(event, 'null', 'number-value_ ', 'null	fr:null	en:null	zh:null	ar:null')">Number</li>  <li onclick="displayProp(event, 'relation-dateEnd_Date	orgRelation-duration_FixedOrInterval	org1_Agent	relation-dateBeg_Date	org2_Agent', 'relation-type_ ', 'null	fr:Relation	en:Relation	zh:关系	ar:null')">OrgRelation</li> 
                                <li onclick="displayProp(event, 'null', 'null', 'null	fr:null	en:null	zh:null	ar:null')">ParticularAgents <ul>
                                        <li onclick="displayProp(event, 'isVictim_Persons', 'physicalState_string', 'null	fr:Victime	en:Victim	zh:伤亡人员	ar:null')">Victim</li>
                                        <li onclick="displayProp(event, 'purchasePercentSold_Percentage	isSeller_Agent', 'null', 'null	fr:Vendeur	en:Seller	zh:卖家	ar:null')">Seller</li>
                                        <li onclick="displayProp(event, 'invest-amount_MonetaryMes	percentageFundraising_Percentage	isInvestor_Agent', 'null', 'null	fr:Investisseur	en:Investor	zh:投资者	ar:null')">Investor</li>
                                        <li onclick="displayProp(event, 'isBuyer_Agent	percentageAcquired_Percentage	purchasePercent_Percentage', 'buyer-type_ ', 'null	fr:Acheteur	en:Buyer	zh:买家	ar:null')">Buyer</li>
                                    </ul></li>  <li onclick="displayProp(event, 'null', 'null', 'null	fr:null	en:null	zh:null	ar:null')">PersonalLife <ul>
                                        <li onclick="displayProp(event, 'pers1_Persons	pers2_Persons', 'familyRelType_string', 'null	fr:Relation familiale	en:Family Relation	zh:null	ar:null')">Family</li>
                                        <li onclick="displayProp(event, 'experience-duration_Mes	hasColleague_Agent	worksIn_Loc	expEnd_ExperienceEnd	worksAt_Organization	expBeg_Appointment	employer_Agent	hasSuperior_Agent', 'role_string', 'null	fr:Expérience professionnelle	en:Professional experience	zh:职业经验	ar:null')">Experience</li>
                                        <li onclick="displayProp(event, 'studiesAt_Organization	studiesIn_Loc	educationBeg_Date	education-duration_Mes	getDiploma_Diploma	educationEnd_Date', 'null', 'null	fr:Etudes suivies	en:Education	zh:所受教育	ar:null')">Education</li>
                                        <li onclick="displayProp(event, 'diplomaObtention-date_Date', 'level_ 	title_string', 'null	fr:Diplôme	en:Diploma	zh:文凭	ar:null')">Diploma</li>
                                    </ul></li>  <li onclick="displayProp(event, 'newPrice_MonetaryMes	changePercent_Percentage	oldPrice_MonetaryMes', 'type-change_ ', 'null	fr:null	en:null	zh:null	ar:null')">PriceChange</li>  <li onclick="displayProp(event, 'page_ElectronicDocument	foundedBy_Agent	project-member_Agent', 'project-name_ 	theme_string', 'null	fr:Projet	en:Project	zh:项目	ar:null')">Project</li>  <li onclick="displayProp(event, 'null', 'null', 'null	fr:Classe abstraite	en:Abstract class	zh:虚类	ar:null')">PunctDates <ul>
                                        <li onclick="displayProp(event, 'agent-indictment_ ', 'null', 'null	fr:Mise en examen	en:Indictment	zh:起诉	ar:null')">Indictment</li>
                                        <li onclick="displayProp(event, 'diplomaObtention-date_Date', 'level_ 	title_string', 'null	fr:Diplôme	en:Diploma	zh:文凭	ar:null')">Diploma</li>
                                        <li onclick="displayProp(event, 'partner1_Person	partner2_Person', 'null', 'null	fr:null	en:null	zh:null	ar:null')">UnionDisunion <ul> 
                                                <li onclick="displayProp(event, 'union-agent_Agent	union-witness_Persons	union-guest_Persons', 'union-type_ ', 'null	fr:Union / Mariage	en:Union	zh:结合 / Mariage	ar:null')">Union</li> 
                                                <li onclick="displayProp(event, 'disunion-agent_Agent	disunion-witness_Person', 'disunion-type_ 	disunionType_string', 'null	fr:Séparation / Divorce	en:Disunion	zh:分手 / 离婚	ar:null')">Disunion</li> 
                                            </ul>

                                        </li> 
                                        <li onclick="displayProp(event, 'decisionMaker_Agent', 'null', 'null	fr:Fin de l'occupation d'un poste	en:Experience End	zh:离职	ar:null')">ExperienceEnd</li> 
                                        <li onclick="displayProp(event, 'funerals-guest_Persons', 'null', 'null	fr:Funérailles	en:Funerals	zh:葬礼	ar:null')">Funerals</li> 
                                        <li onclick="displayProp(event, 'agent-arrest_ ', 'null', 'null	fr:Arrestation	en:Arrest	zh:逮捕	ar:null')">Arrest</li> 
                                        <li onclick="displayProp(event, 'hasOrder_Order	bought_Entity', 'paymentType_string', 'null	fr:Achat d'un bien	en:Purchase	zh:购置物	ar:null')">Purchase</li> 
                                        <li onclick="displayProp(event, 'agent-policeCheck_Agent', 'null', 'null	fr:Contrôle de police	en:Police check	zh:警察检查	ar:null')">PoliceCheck</li>
                                        <li onclick="displayProp(event, 'award-event_Event	isAwarded_PhysicalEntity	award-date_Date', 'domain_ ', 'null	fr:Récompense	en:Award	zh:奖赏	ar:null')">Award</li> 
                                        <li onclick="displayProp(event, 'eventDeclared_Event	sender_Agent	receiver_Agent', 'messageTitle_string	content_string', 'null	fr:Transmission de messages	en:Emission	zh:信息传送	ar:null')">Emission <ul> 
                                                <li onclick="displayProp(event, 'accordingTo_Agent', 'null', 'null	fr:null	en:null	zh:null	ar:null')">ReportedSpeech</li>
                                            </ul></li>
                                        <li onclick="displayProp(event, 'judge_Person	pronounce_JudicialDecision', 'null', 'null	fr:Jugement	en:Judgment	zh:裁判	ar:null')">Judgment</li> 
                                        <li onclick="displayProp(event, 'hasWitness_Persons	target_PhysicalEntity	victNum_GroupPers	violentAct-instrument_Object	sponsor_Agent	hasVictim_Victim', 'violentAct-type_string', 'null	fr:Acte de violence	en:Violent act	zh:暴力行为	ar:null')">ViolentAct</li> 
                                        <li onclick="displayProp(event, 'agent-release_Agent', 'releaseType_string', 'null	fr:Libération	en:Release	zh:释放	ar:null')">Release</li> 
                                    </ul>

                                </li>  
                                <li onclick="displayProp(event, 'null', 'null', 'null	fr:null	en:null	zh:null	ar:null')">TextualStructure <ul> 
                                        <li onclick="displayProp(event, 'hasPar_Par	hasSubTitle_SubTitle', 'null', 'null	fr:Titre	en:Title	zh:标题	ar:null')">Title</li> 
                                        <li onclick="displayProp(event, 'hasTitle_Title', 'null', 'null	fr:null	en:null	zh:null	ar:null')">Text</li> 
                                        <li onclick="displayProp(event, 'null', 'null', 'null	fr:Sous-titre	en:SubTitle	zh:副标题	ar:null')">SubTitle
                                        </li> 
                                        <li onclick="displayProp(event, 'null', 'null', 'null	fr:null	en:null	zh:null	ar:null')">Sent
                                        </li> 
                                        <li onclick="displayProp(event, 'hasSent_Sent', 'null', 'null	fr:null	en:null	zh:null	ar:null')">Par
                                        </li> 
                                    </ul>

                                </li>  
                                <li onclick="displayProp(event, 'isUncertain_Thing', 'weight_ ', 'null	fr:null	en:null	zh:null	ar:null')">Uncertainty 
                                    <ul> 
                                        <li onclick="displayProp(event, 'accordingTo_Agent', 'null', 'null	fr:null	en:null	zh:null	ar:null')">ReportedSpeech
                                        </li> 
                                        <li onclick="displayProp(event, 'null', 'null', 'null	fr:null	en:null	zh:null	ar:null')">AuthorUncertainty
                                        </li> 
                                        <li onclick="displayProp(event, 'null', 'null', 'null	fr:null	en:null	zh:null	ar:null')">AlignmentUncertainty
                                        </li> 
                                    </ul>

                                </li>  
                                <li onclick="displayProp(event, 'null', 'null', 'null	fr:null	en:null	zh:null	ar:null')">Web 
                                    <ul> 
                                        <li onclick="displayProp(event, 'accountServiceHomepage_Website', 'accountName_string', 'null	fr:Compte en ligne	en:Online account	zh:在线账户	ar:null')">OnlineAccount 
                                            <ul> 
                                                <li onclick="displayProp(event, 'null', 'mailAddress_ ', 'null	fr:Adresse mail	en:Email address	zh:电邮地址	ar:null')">MailAddress
                                                </li> 
                                            </ul>

                                        </li> 
                                        <li onclick="displayProp(event, 'null', 'websiteAddress_string', 'null	fr:URL du site web	en:Website URL	zh:网站网址	ar:null')">Website
                                        </li> 
                                        <li onclick="displayProp(event, 'server-place_Loc', 'server-name_ ', 'null	fr:Serveur	en:Server	zh:服务器	ar:null')">Server
                                        </li> 
                                        <li onclick="displayProp(event, 'ipRelatedToServer_Server', 'ipAdress_ ', 'null	fr:Adresse IP	en:IP Address	zh:IP地址	ar:null')">IP
                                        </li> 
                                        <li onclick="displayProp(event, 'expirationDate_Date	creationDate_Date	dnsRelatedToIP_IP	updatedDate_Date', 'dns-name_string', 'null	fr:Système de noms de domaine	en:Domain Name System	zh:网域名称系统	ar:null')">DNS
                                        </li> 
                                    </ul>

                                </li>

                        </li>
                    </ul>
                    </ul>
                </td>
                <td id="properties"  style="vertical-align: top">

                </td>
            </tr>
        </table>
    </body>
</html>
