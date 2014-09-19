package eu.factorx.awac.models.code;

// ATTENTION: always APPEND new values, never INSERT!!
// It's because ValueSelectionQuestion persists codeList values in an ordinal way... (this is to fix)
public enum CodeList {

	// FORMS

	ActivityCategory,
	ActivitySubCategory,
	ActivityType,
	ActivitySource,
	IndicatorCategory,
	SECTEURPRINCIPAL,
	SECTEURPRIMAIRE,
	SECTEURSECONDAIRE,
	SECTEURTERTIAIRE,
	SECTEURTYPE,
	COMBUSTIBLE,
	ENERGIEVAPEUR,
	GES,
	FRIGORIGENE,
	MOTIFDEPLACEMENT,
	CARBURANT,
	TYPEVEHICULE,
	TYPEVOL,
	CATEGORIEVOL,
	FRIGORIGENEBASE,
	PROVENANCESIMPLIFIEE,
	TYPEDECHET,
	TRAITEMENTDECHET,
	TRAITEUREAU,
	ORIGINEEAUUSEE,
	TRAITEMENTEAU,
	TYPEACHAT,
	ACHATMETAL,
	ACHATPLASTIQUE,
	ACHATPAPIER,
	ACHATVERRE,
	ACHATCHIMIQUE,
	ACHATROUTE,
	ACHATAGRO,
	ACHATSERVICE,
	INFRASTRUCTURE,
	TYPEPRODUIT,
	GESSIMPLIFIE,
	POURCENTSIMPLIFIE,


		/*

    HEATING_FUEL_TYPE,
    SCOPE_TYPE,
    QUESTION,
    INDICATOR_SCOPE,
    SITE_SECTORS,
    PUBLIC_PRIVATE,
    NACE_CODES_1,
    NACE_CODES_2,
    NACE_CODES_3,
    FUEL,
    INDICATOR_TYPE,
    INDICATOR_CATEGORY,
    INDICATOR_ISO_SCOPE,
    ACTIVITY_CATEGORY,
    ACTIVITY_SUB_CATEGORY,
    ACTIVITY_TYPE,
    ACTIVITY_SOURCE,
    UNIT,
    BASE_ACTIVITY_DATA,
    SECTEURPRINCIPAL,
    SECTEURPRIMAIRE,
    SECTEURSECONDAIRE,
    SECTEURTERTIAIRE,
    SECTEURTYPE,
    COMBUSTIBLE,
    ENERGIEVAPEUR,
    GES,
    FRIGORIGENE,
    MOTIFDEPLACEMENT,
    CARBURANT,
    TYPEVEHICULE,
    TYPEVOL,
    CATEGORIEVOL,
    FRIGORIGENEBASE,
    PROVENANCESIMPLIFIEE,
    TYPEDECHET,
    TRAITEMENTDECHET,
    TRAITEUREAU,
    ORIGINEEAUUSEE,
    TYPEACHAT,
    ACHATMETAL,
    ACHATPLASTIQUE,
    ACHATPAPIER,
    ACHATVERRE,
    ACHATCHIMIQUE,
    ACHATROUTE,
    ACHATAGRO,
    ACHATSERVICE,
    INFRASTRUCTURE,
    TYPEPRODUIT,
    GESSIMPLIFIE,
    POURCENTSIMPLIFIE,
	TRAITEMENTEAU,
	*/


	// BAD
	BASE_ACTIVITY_DATA,
	INDICATOR_TYPE,
	INDICATOR_ISO_SCOPE,

	// FOR TRANSLATIONS
	LANGUAGE,
	TRANSLATIONS_SURVEY,
	TRANSLATIONS_INTERFACE,
	QUESTION,
	SCOPE_TYPE,
	PERIOD,
	TRANSLATIONS_ERROR_MESSAGES,
	UNIT,
	UNIT_CATEGORY,

	// MUNICIPALITIY
	SERVICECOMMUNAL,
	GESTIONBATIMENT,
	COMBUSTIBLESIMPLECOMMUNE,
	POSSESSIONVEHICULECOMMUNAL,
	TYPEVEHICULECOMMUNE,
	MOTIFDEPLACEMENTHORSDDT,
	INTERFACE_TYPE,
	TRANSLATIONS_EMAIL_MESSAGE,
	BASE_INDICATOR,
	INDICATOR,
	REPORT

}
