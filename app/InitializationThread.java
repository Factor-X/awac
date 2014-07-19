import eu.factorx.awac.AwacDummyDataCreator;
import eu.factorx.awac.InMemoryData;
import eu.factorx.awac.dto.myrmex.get.TranslationsDTO;
import eu.factorx.awac.models.account.Administrator;
import eu.factorx.awac.models.account.Person;
import org.hibernate.Session;
import org.springframework.context.ApplicationContext;
import play.db.jpa.JPA;
import play.libs.F;
import play.libs.Yaml;

import java.util.List;
import java.util.Map;

public class InitializationThread extends Thread {

	private ApplicationContext ctx;

	public InitializationThread(ApplicationContext ctx) {
		this.ctx = ctx;
	}

	public void run() {
		JPA.withTransaction(new F.Callback0() {

			@Override
			public void invoke() throws Throwable {
				createInitialData(ctx);
			}
		});
		createInMemoryData();
	}


	private void createInMemoryData() {
		createInMemoryTranslations();
	}

	private void createInMemoryTranslations() {

		String language = "FR";

		TranslationsDTO dto = new TranslationsDTO(language);

		dto.put("FR", "Français");
		dto.put("NL", "Nederlands");
		dto.put("EN", "English");

		dto.put("SAVE_BUTTON", "Sauvegarder");

		dto.put("A1_TITLE", "AWAC - Entreprises");
		dto.put("A2_TITLE", "Année de référence pour comparaison du présent bilan GES");
		dto.put("A3_TITLE", "A quel secteur pricipal appartient votre site?");
		dto.put("A4_TITLE", "Quel est le code NACE principal de votre site?");
		dto.put("A5_TITLE", "Quel est le code NACE principal de votre site?");
		dto.put("A6_TITLE", "Quel est le code NACE principal de votre site?");
		dto.put("A7_TITLE", "Est-ce que votre activité est purement ou principalement de bureaux?");
		dto.put("A8_TITLE", "Etes-vous dans le secteur public ou privé?");
		dto.put("A9_TITLE", "Indiquez la surface totale du site:");
		dto.put("A10_TITLE", "Quelle est la surface des bureaux?");
		dto.put("A11_TITLE", "Etes-vous participant aux accords de branche de 2ème génération?");
		dto.put("A12_TITLE", "Quel est le nombre d'employés sur l'année du bilan?");
		dto.put("A13_TITLE", "Consommation de combustibles");
		dto.put("A14_TITLE", "Pièces documentaires liées aux consommations de combustible");
		dto.put("A15_TITLE", "Combustion de combustible par les sources statiques des sites de l'entreprise");
		dto.put("A16_TITLE", "Combustible");
		dto.put("A17_TITLE", "Quantité");
		dto.put("A18_TITLE", "Quel est la superficie de votre établissement ?");
		dto.put("A19_TITLE", "Quel vecteur énergétique principal utilisez-vous?");
		dto.put("A20_TITLE", "Electricité et vapeur achetées");
		dto.put("A21_TITLE", "Pièces documentaires liées aux achats d'électricité et de vapeur");
		dto.put("A22_TITLE", "Electricité");
		dto.put("A23_TITLE", "Consommation d'électricité verte");
		dto.put("A24_TITLE", "Consommation d'électricité grise");
		dto.put("A25_TITLE", "Vapeur");
		dto.put("A26_TITLE", "Energie primaire utilisée pour produire la vapeur:");
		dto.put("A27_TITLE", "Efficacité de la chaudière");
		dto.put("A28_TITLE", "Quantité achetée");
		dto.put("A29_TITLE", "Superficie de votre établissement");
		dto.put("A30_TITLE", "Achetez-vous votre électricité \"verte\" ?");
		dto.put("A31_TITLE", "GES des processus de production");
		dto.put("A32_TITLE", "Est-ce que vos activités impliquent des procédés chimiques et physiques émetteurs directs de gaz à effet de serre ?");
		dto.put("A33_TITLE", "Pièces documentaires liées aux GES des processus de production");
		dto.put("A34_TITLE", "Type de GES émis par la production");
		dto.put("A35_TITLE", "Type de GES");
		dto.put("A36_TITLE", "Quantité");
		dto.put("A37_TITLE", "Systèmes de refroidissement");
		dto.put("A38_TITLE", "Disposez-vous d’un système de froid nécessitant un apport ponctuel d’agent réfrigérant (p.e. les chillers, les climatiseurs à air et à eau glacée, les réfrigérateurs, bacs à surgelés, etc.)?");
		dto.put("A39_TITLE", "Pièces documentaires liées aux systèmes de refroidissement");
		dto.put("A40_TITLE", "Méthodes au choix");
		dto.put("A41_TITLE", "Estimation des émissions à partir des recharges de gaz");
		dto.put("A42_TITLE", "Listes des types de gaz réfrigérants utilisés");
		dto.put("A43_TITLE", "Type de gaz");
		dto.put("A44_TITLE", "Quantité de recharge nécessaire pour l'année");
		dto.put("A45_TITLE", "Estimation des émissions à partir de la puissance du groupe de froid");
		dto.put("A46_TITLE", "Quel est la puissance frigorifique des groupes froid?");
		dto.put("A47_TITLE", "Estimation des émissions à partir de la consommation électrique du site");
		dto.put("A48_TITLE", "Est-ce que votre entreprise produit du sucre ou des pâtes sèches?");
		dto.put("A49_TITLE", "Quel est le nombre d'heures de fonctionnement annuel du site?");
		dto.put("A50_TITLE", "Mobilité");
		dto.put("A51_TITLE", "Pièces documentaires liées à la mobilité");
		dto.put("A52_TITLE", "Transport routier (méthode au choix)");
		dto.put("A53_TITLE", "Calcul par les consommations");
		dto.put("A54_TITLE", "Véhicules de société ou détenus par l'entreprise");
		dto.put("A55_TITLE", "Consommation d'essence");
		dto.put("A56_TITLE", "Consommation de diesel");
		dto.put("A57_TITLE", "Consommation de gaz de pétrole liquéfié (GPL)");
		dto.put("A58_TITLE", "Autres véhicules: déplacements domicile-travail des employés");
		dto.put("A59_TITLE", "Consommation d'essence");
		dto.put("A60_TITLE", "Consommation de diesel");
		dto.put("A61_TITLE", "Consommation de gaz de pétrole liquéfié (GPL)");
		dto.put("A62_TITLE", "Autres véhicules: Déplacements professionnels & visiteurs");
		dto.put("A63_TITLE", "Consommation d'essence");
		dto.put("A64_TITLE", "Consommation de diesel");
		dto.put("A65_TITLE", "Consommation de gaz de pétrole liquéfié (GPL)");
		dto.put("A66_TITLE", "Calcul par les kilomètres");
		dto.put("A67_TITLE", "Créez autant de catégories de véhicules que souhaité");
		dto.put("A68_TITLE", "Catégorie de véhicule");
		dto.put("A69_TITLE", "S'agit-il d'une catégorie de véhicules appartenant ou sous le contrôle à la société ou pas?");
		dto.put("A70_TITLE", "Motif de déplacement");
		dto.put("A71_TITLE", "Quel type de carburant utilise-t-il ?");
		dto.put("A72_TITLE", "De quel type de véhicule s'agit-il?");
		dto.put("A73_TITLE", "Consommation moyenne (L/100km)");
		dto.put("A74_TITLE", "Consommation moyenne (L/100km)");
		dto.put("A75_TITLE", "Consommation moyenne (L/100km)");
		dto.put("A76_TITLE", "Quelle est le nombre de kilomètres parcourus par an?");
		dto.put("A77_TITLE", "Calcul par euros dépensés");
		dto.put("A78_TITLE", "Créez autant de catégories de véhicules que souhaité");
		dto.put("A79_TITLE", "Catégorie de véhicule");
		dto.put("A80_TITLE", "S'agit-il d'une catégorie de véhicules appartenant ou sous le contrôle à la société ou pas?");
		dto.put("A81_TITLE", "Motif de déplacement");
		dto.put("A83_TITLE", "Quel type de carburant utilise-t-il ?");
		dto.put("A88_TITLE", "Quel est le montant annuel de dépenses en carburant?");
		dto.put("A89_TITLE", "Prix moyen du litre d'essence");
		dto.put("A90_TITLE", "Prix moyen du litre de diesel");
		dto.put("A91_TITLE", "Prix moyen du litre de biodiesel");
		dto.put("A92_TITLE", "Prix moyen du litre de Gaz de Prétrole Liquéfié (GPL)");
		dto.put("A93_TITLE", "Transport en commun");
		dto.put("A94_TITLE", "Estimation par le détail des déplacements");
		dto.put("A95_TITLE", "Bus TEC pour déplacement domicile-travail des employés (en km.passagers)");
		dto.put("A96_TITLE", "Bus TEC pour déplacements professionnels & des visiteurs (en km.passagers)");
		dto.put("A97_TITLE", "Métro pour déplacement domicile-travail des employés (en km.passagers)");
		dto.put("A98_TITLE", "Métro pour déplacements professionnels & des visiteurs (en km.passagers)");
		dto.put("A99_TITLE", "Train national SNCB pour déplacement domicile-travail des employés (en km.passagers)");
		dto.put("A100_TITLE", "Train national SNCB pour déplacements professionnels & des visiteurs (en km.passagers)");
		dto.put("A101_TITLE", "Train international (TGV) pour déplacement domicile-travail des employés (en km.passagers)");
		dto.put("A102_TITLE", "Train international (TGV) pour déplacements professionnels & des visiteurs (en km.passagers)");
		dto.put("A103_TITLE", "Tram pour déplacement domicile-travail des employés (en km.passagers)");
		dto.put("A104_TITLE", "Tram pour déplacements professionnels & des visiteurs (en km.passagers)");
		dto.put("A105_TITLE", "Taxi pour déplacement domicile-travail des employés (en véhicules.km)");
		dto.put("A106_TITLE", "Taxi pour déplacements professionnels & des visiteurs (en véhicules.km)");
		dto.put("A107_TITLE", "Taxi pour déplacement domicile-travail des employés (en valeur)");
		dto.put("A108_TITLE", "Taxi pour déplacements professionnels & des visiteurs (en valeur)");
		dto.put("A109_TITLE", "Estimation par nombre d'employés");
		dto.put("A110_TITLE", "Etes-vous situés à proximité d'une gare (< 1 km)?");
		dto.put("A111_TITLE", "Etes-vous situés à proximité d'un arrêt de transport en commun (< 500 m)?");
		dto.put("A112_TITLE", "Etes-vous situés en Agglomération ?");
		dto.put("A113_TITLE", "Transport en avion (déplacements professionnels ou des visiteurs)");
		dto.put("A114_TITLE", "Méthode par le détail des vols");
		dto.put("A115_TITLE", "Créez autant de catégories de vol que nécessaire");
		dto.put("A116_TITLE", "Catégorie de vol");
		dto.put("A117_TITLE", "Type de vol");
		dto.put("A118_TITLE", "Classe du vol");
		dto.put("A119_TITLE", "Nombre de vols/an");
		dto.put("A120_TITLE", "Distance moyenne A/R (km)");
		dto.put("A121_TITLE", "Méthode des moyennes");
		dto.put("A122_TITLE", "% des employés qui réalisent des déplacements en avion");
		dto.put("A123_TITLE", "Connaissez-vous le nombre de km parcourus en avion?");
		dto.put("A124_TITLE", "Les voyages ont-ils lieu en Europe?");
		dto.put("A125_TITLE", "Km moyen assignés par employé voyageant");
		dto.put("A126_TITLE", "Km moyen assignés par employé voyageant");
		dto.put("A127_TITLE", "km moyen parcourus sur l'année:");
		dto.put("A128_TITLE", "Transport et distribution de marchandises amont");
		dto.put("A129_TITLE", "Pièces documentaires liées au transport et stockage amont");
		dto.put("A130_TITLE", "Transport amont");
		dto.put("A131_TITLE", "Transport avec des véhicules détenus par l'entreprise");
		dto.put("A132_TITLE", "Méthode par consommation de carburants");
		dto.put("A133_TITLE", "Consommation d'essence");
		dto.put("A134_TITLE", "Consommation de diesel");
		dto.put("A135_TITLE", "Consommation de gaz de pétrole liquéfié (GPL)");
		dto.put("A136_TITLE", "Est-ce les marchandises sont refrigérées durant le transport?");
		dto.put("A137_TITLE", "Type de Gaz");
		dto.put("A138_TITLE", "Connaissez-vous la quantité annuelle de recharge de ce gaz?");
		dto.put("A139_TITLE", "Quantité de recharge annuelle");
		dto.put("A139_TITLE", "Quantité de recharge annuelle");
		dto.put("A140_TITLE", "Transport effectué par des transporteurs");
		dto.put("A141_TITLE", "Méthode des kilomètres");
		dto.put("A142_TITLE", "Créez autant de marchandises que nécessaire");
		dto.put("A143_TITLE", "Marchandise");
		dto.put("A144_TITLE", "Est-ce la marchandise est refrigérée durant le transport?");
		dto.put("A145_TITLE", "Poids total transporté:");
		dto.put("A146_TITLE", "Distance totale entre le point de départ et le point d'arrivée de la marchandise:");
		dto.put("A147_TITLE", "% de distance effectuée par transport routier local par camion");
		dto.put("A148_TITLE", "% de distance effectuée par transport routier local par camionnette");
		dto.put("A149_TITLE", "% de distance effectuée par transport routier international");
		dto.put("A150_TITLE", "% de distance effectuée par voie ferroviaire");
		dto.put("A151_TITLE", "% de distance effectuée par voie maritime");
		dto.put("A152_TITLE", "% de distance effectuée par voie fluviale");
		dto.put("A153_TITLE", "% de distance effectuée par transport aérien court courrier (<1000 km)");
		dto.put("A154_TITLE", "% de distance effectuée par transport aérien moyen courrier (1000 à 4000 km)");
		dto.put("A155_TITLE", "% de distance effectuée par transport aérien long courrier (> 4000 km)");
		dto.put("A156_TITLE", "Total (supposé être égal à 100%)");
		dto.put("A157_TITLE", "Méthode des moyennes");
		dto.put("A158_TITLE", "Quel est le poids total des marchandises?");
		dto.put("A159_TITLE", "Quelle est la provenance ou destination des marchandises?");
		dto.put("A160_TITLE", "Km assignés en moyenne aux marchandises");
		dto.put("A161_TITLE", "Km assignés en moyenne aux marchandises");
		dto.put("A162_TITLE", "Km assignés en moyenne aux marchandises");
		dto.put("A163_TITLE", "Distribution amont: Energie et froid des entrepôts de stockage");
		dto.put("A164_TITLE", "Créez autant d'entrepôts de stockage que nécessaire");
		dto.put("A165_TITLE", "Entrepôt");
		dto.put("A166_TITLE", "Listez les totaux de combustibles utilisés en amont");
		dto.put("A167_TITLE", "Combustible utilisé en amont");
		dto.put("A168_TITLE", "Quantité");
		dto.put("A169_TITLE", "Electricité");
		dto.put("A170_TITLE", "Listez les gaz réfrigérants utilisés pour les marchandises amont");
		dto.put("A171_TITLE", "Type de gaz");
		dto.put("A172_TITLE", "Quantité de recharge nécessaire pour l'année");
		dto.put("A173_TITLE", "Déchets générés par les opérations");
		dto.put("A174_TITLE", "Pièces documentaires liées aux déchets");
		dto.put("A175_TITLE", "Listez vos différents postes de déchets");
		dto.put("A176_TITLE", "Poste de déchet");
		dto.put("A177_TITLE", "Type de déchet");
		dto.put("A178_TITLE", "Type de traitement");
		dto.put("A179_TITLE", "Quantité");
		dto.put("A180_TITLE", "Eaux usées");
		dto.put("A181_TITLE", "Eaux usées domestiques par grand type de bâtiments");
		dto.put("A182_TITLE", "Usine ou atelier");
		dto.put("A183_TITLE", "Nombre d'ouvriers");
		dto.put("A184_TITLE", "Nombre de jours de travail/an");
		dto.put("A185_TITLE", "Bureau");
		dto.put("A186_TITLE", "Nombre d'employés");
		dto.put("A187_TITLE", "Nombre de jours de travail/an");
		dto.put("A188_TITLE", "Hôtel, pension, hôpitaux, prison");
		dto.put("A189_TITLE", "Nombre de lits");
		dto.put("A190_TITLE", "Nombre de jours d'ouverture/an");
		dto.put("A191_TITLE", "Restaurant ou cantine");
		dto.put("A192_TITLE", "Nombre de couverts/jour");
		dto.put("A193_TITLE", "Nombre de jours d'ouverture/an");
		dto.put("A194_TITLE", "Eaux usées industrielles");
		dto.put("A196_TITLE", "Méthodes alternatives");
		dto.put("A197_TITLE", "Méthode par la quantité de m³ rejetés");
		dto.put("A195_TITLE", "Est-ce l'entreprise qui réalise le traitement ou est-il effectué par des tiers?");
		dto.put("A198_TITLE", "Source de rejetnull");
		dto.put("A199_TITLE", "Quantités de m³ rejetés");
		dto.put("A200_TITLE", "Méthode de traitement des eaux usées");
		dto.put("A201_TITLE", "Méthode par le poids de CO2 chimique des effluents rejetés");
		dto.put("A195_TITLE", "Est-ce l'entreprise qui réalise le traitement ou est-il effectué par des tiers?");
		dto.put("A202_TITLE", "Quantités de DCO rejetés");
		dto.put("A203_TITLE", "Quantités d'azote rejetés");
		dto.put("A204_TITLE", "Méthode de traitement des eaux usées");
		dto.put("A205_TITLE", "Achat de biens et services");
		dto.put("A206_TITLE", "Pièces documentaires liées aux achats");
		dto.put("A208_TITLE", "Méthode par détail des achats");
		dto.put("A209_TITLE", "Créez et nommez vos postes d'achats (et préciser la catégorie et le type de matériaux ensuite)");
		dto.put("A210_TITLE", "Poste d'achat");
		dto.put("A211_TITLE", "Catégorie");
		dto.put("A212_TITLE", "Type");
		dto.put("A213_TITLE", "Type");
		dto.put("A214_TITLE", "Type");
		dto.put("A215_TITLE", "Type");
		dto.put("A216_TITLE", "Type");
		dto.put("A217_TITLE", "Type");
		dto.put("A218_TITLE", "Type");
		dto.put("A219_TITLE", "Type");
		dto.put("A220_TITLE", "Taux de recyclé");
		dto.put("A221_TITLE", "Quantité");
		dto.put("A222_TITLE", "Quantité");
		dto.put("A223_TITLE", "Autres matériaux spécifiques pour lesquels l'entreprise dispose du facteur d'émissions cradle-to-gate");
		dto.put("A224_TITLE", "Créez et nommez vos postes d'achats spécifiques (et précisez ensuite la catégorie, le type de matériaux et le facteur d'émission cradle-to-gate spécifique)");
		dto.put("A225_TITLE", "Poste d'achat");
		dto.put("A226_TITLE", "Quantité");
		dto.put("A227_TITLE", "Unité dans laquelle s'exprime cette quantité");
		dto.put("A228_TITLE", "Facteur d'émission en tCO2e par unité ci-dessus");
		dto.put("A229_TITLE", "Infrastructures (achetées durant l'année de déclaration)");
		dto.put("A230_TITLE", "Pièces documentaires liées aux infrastructures");
		dto.put("A231_TITLE", "Créez et nommez vos postes d'infrastructure");
		dto.put("A232_TITLE", "Poste d'infrastructure");
		dto.put("A233_TITLE", "Type d'infrastructure");
		dto.put("A234_TITLE", "Quantité");
		dto.put("A235_TITLE", "Quantité");
		dto.put("A236_TITLE", "Quantité");
		dto.put("A237_TITLE", "Autres infrastructures spécifiques pour lesquels l'entreprise dispose du facteur d'émission cradle-to-gate");
		dto.put("A238_TITLE", "Créez et nommez vos postes d'infrastructure spécifiques");
		dto.put("A239_TITLE", "Poste d'infrastructure");
		dto.put("A240_TITLE", "Quantité");
		dto.put("A241_TITLE", "Unité dans laquelle s'exprime cette quantité");
		dto.put("A242_TITLE", "Facteur d'émission en tCO2e par unité ci-dessus");
		dto.put("A243_TITLE", "Transport & Distribution, Traitement, Utilisation et Fin de vie des produits vendus");
		dto.put("A244_TITLE", "Lister les différents produits ou groupes de produits vendus par l'entreprise");
		dto.put("A245_TITLE", "Nom du produit ou groupe de produits");
		dto.put("A246_TITLE", "Quantité vendue de ce produit");
		dto.put("A247_TITLE", "Unité dans laquelle s'exprime cette quantité");
		dto.put("A248_TITLE", "S'agit-il d'un produit (ou groupe de produits) final ou intermédiaire?");
		dto.put("A249_TITLE", "Connaissez-vous la ou les applications ultérieures?");
		dto.put("A250_TITLE", "Transport et distribution aval");
		dto.put("A251_TITLE", "Pièces documentaires liées au transport et stockage aval");
		dto.put("A252_TITLE", "Transport aval: choix de méthodes");
		dto.put("A253_TITLE", "Méthode par kilométrage");
		dto.put("A254_TITLE", "Poids total transporté:");
		dto.put("A255_TITLE", "Distance totale entre le point de vente et le client particulier ou entre le point de vente et le client professionnel");
		dto.put("A256_TITLE", "% de distance effectuée par transport routier local par camion");
		dto.put("A257_TITLE", "% de distance effectuée par transport routier local par camionnette");
		dto.put("A258_TITLE", "% de distance effectuée par transport routier international");
		dto.put("A259_TITLE", "% de distance effectuée par voie ferroviaire");
		dto.put("A260_TITLE", "% de distance effectuée par voie maritime");
		dto.put("A261_TITLE", "% de distance effectuée par voie fluviale");
		dto.put("A262_TITLE", "% de distance effectuée par transport aérien court courrier (<1000 km)");
		dto.put("A263_TITLE", "% de distance effectuée par transport aérien moyen courrier (1000 à 4000 km)");
		dto.put("A264_TITLE", "% de distance effectuée par transport aérien long courrier (> 4000 km)");
		dto.put("A265_TITLE", "Total (supposé être égal à 100%)");
		dto.put("A266_TITLE", "Méthode des moyennes");
		dto.put("A267_TITLE", "Poids total transporté:");
		dto.put("A268_TITLE", "Quelle est la destination géographique des produits vendus?");
		dto.put("A269_TITLE", "Km assignés en moyenne aux marchandises");
		dto.put("A270_TITLE", "Km assignés en moyenne aux marchandises");
		dto.put("A271_TITLE", "Km assignés en moyenne aux marchandises");
		dto.put("A272_TITLE", "Distribution avale: Energie et Froid des entrepôts de stockage");
		dto.put("A273_TITLE", "Créez autant d'entrepôts de stockage que nécessaire");
		dto.put("A274_TITLE", "Entrepôt");
		dto.put("A275_TITLE", "Listez les totaux de combustibles utilisés");
		dto.put("A276_TITLE", "Combustible utilisé");
		dto.put("A277_TITLE", "Quantité");
		dto.put("A278_TITLE", "Electricité");
		dto.put("A279_TITLE", "Listez les gaz réfrigérants");
		dto.put("A280_TITLE", "Type de gaz");
		dto.put("A281_TITLE", "Quantité de recharge nécessaire pour l'année");
		dto.put("A282_TITLE", "Traitement");
		dto.put("A283_TITLE", "Fournir ici les documents éventuels justifiant les données suivantes");
		dto.put("A284_TITLE", "Listez les totaux de combustibles");
		dto.put("A285_TITLE", "Combustible utilisé");
		dto.put("A286_TITLE", "Quantité");
		dto.put("A287_TITLE", "Electricité");
		dto.put("A288_TITLE", "Listez les gaz réfrigérants");
		dto.put("A289_TITLE", "Type de gaz");
		dto.put("A290_TITLE", "Quantité de recharge nécessaire pour l'année");
		dto.put("A291_TITLE", "Utilisation");
		dto.put("A292_TITLE", "Fournir ici les documents éventuels justifiant les données suivantes");
		dto.put("A293_TITLE", "Nombre total d'utilisations du produit ou groupe de produits sur toute sa durée de vie");
		dto.put("A294_TITLE", "Consommation de diesel par utilisation de produit");
		dto.put("A295_TITLE", "Consommation d'essence par utilisation de produit");
		dto.put("A296_TITLE", "Consommation d'électricité par utilisation de produit");
		dto.put("A297_TITLE", "Listez les gaz émis par utilisation de produit");
		dto.put("A298_TITLE", "Gaz émis");
		dto.put("A299_TITLE", "Quantité");
		dto.put("A300_TITLE", "Traitement de fin de vie");
		dto.put("A301_TITLE", "Fournir ici les documents éventuels justifiant les données suivantes");
		dto.put("A302_TITLE", "Poids total de produit vendu");
		dto.put("A303_TITLE", "Créez autant de catégories de déchet que nécessaire");
		dto.put("A304_TITLE", "Catégorie de déchet");
		dto.put("A305_TITLE", "Poids total de cette catégorie de déchet issu des produits vendus");
		dto.put("A306_TITLE", "Type principal de ce déchet:");
		dto.put("A307_TITLE", "Type de traitement du déchet");
		dto.put("A308_TITLE", "Proportion du déchet issu du produit, traité par la méthode précédemment renseignée");
		dto.put("A309_TITLE", "Actifs loués (aval)");
		dto.put("A310_TITLE", "Fournir ici les documents éventuels justifiant les données suivantes");
		dto.put("A311_TITLE", "Créez autant de catégories d'actifs loués que nécessaire");
		dto.put("A312_TITLE", "Catégorie d'actif loué");
		dto.put("A313_TITLE", "Listez les totaux de combustibles utilisés pour les actifs loués");
		dto.put("A314_TITLE", "Combustible utilisé");
		dto.put("A315_TITLE", "Quantité");
		dto.put("A316_TITLE", "Electricité");
		dto.put("A317_TITLE", "Listez les gaz réfrigérants et autres nécessaires à l'opération des actifs loués");
		dto.put("A318_TITLE", "Type de gaz");
		dto.put("A319_TITLE", "Quantité de recharge nécessaire pour l'année");
		dto.put("A320_TITLE", "Franchises");
		dto.put("A321_TITLE", "Fournir ici les documents éventuels justifiant les données suivantes");
		dto.put("A322_TITLE", "Créez autant de catégories de franchisés que nécessaire");
		dto.put("A323_TITLE", "Catégorie de franchisé");
		dto.put("A324_TITLE", "Nombre de franchisés");
		dto.put("A325_TITLE", "Listez les moyennes de combustibles utilisés par franchisé");
		dto.put("A326_TITLE", "Combustible utilisé");
		dto.put("A327_TITLE", "Quantité");
		dto.put("A328_TITLE", "Electricité (moyenne par franchisé)");
		dto.put("A329_TITLE", "Listez les gaz réfrigérants et autres utilisés en moyenne par franchisé");
		dto.put("A330_TITLE", "Type de gaz");
		dto.put("A331_TITLE", "Quantité de recharge nécessaire pour l'année");
		dto.put("A332_TITLE", "Activités d'investissement");
		dto.put("A333_TITLE", "Fournir ici les documents éventuels justifiant les données suivantes");
		dto.put("A334_TITLE", "Veuillez indiquer ici tous les projets dans lesquels votre entreprise investit");
		dto.put("A335_TITLE", "Nom du projet");
		dto.put("A336_TITLE", "Part d'investissements dans le projet");
		dto.put("A337_TITLE", "Emissions directes totales (tCO2e)");
		dto.put("A338_TITLE", "Emissions indirectes totales (tCO2e)");


		InMemoryData.translations.put("FR", dto);


	}


	private void createInitialData(ApplicationContext ctx) {
		// Get Hibernate session
		Session session = JPA.em().unwrap(Session.class);

		// Check if the database is empty
		@SuppressWarnings("unchecked")
		List<Administrator> administrators = session.createCriteria(Person.class).list();
		if (administrators.isEmpty()) {
			@SuppressWarnings("unchecked")
			Map<String, List<Object>> all = (Map<String, List<Object>>) Yaml.load("initial-data-awac.yml");
			// save data into DB in relevant order.

			System.out.println(all);
			for (Object entity : all.get("organizations")) {
				session.saveOrUpdate(entity);
			}
			for (Object entity : all.get("administrators")) {
				session.saveOrUpdate(entity);
			}
			for (Object entity : all.get("accounts")) {
				session.saveOrUpdate(entity);
			}

			AwacDummyDataCreator.createAwacDummyData(ctx, session);
		}
	}


}
