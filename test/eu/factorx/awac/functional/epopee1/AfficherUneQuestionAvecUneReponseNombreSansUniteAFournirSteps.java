package eu.factorx.awac.functional.epopee1;

import play.test.TestBrowser;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class AfficherUneQuestionAvecUneReponseNombreSansUniteAFournirSteps {
	@Inject
	private TestBrowser testBrowser;

	@Inject
	@Named("PORT")
	private Integer port;

	@Inject
	@Named("DOMAIN")
	private String domain;


	@Given("^j’ai une réponse nombre sans unité à fournir au sein d’un formulaire$")
	public void j_ai_une_réponse_nombre_sans_unité_à_fournir_au_sein_d_un_formulaire() throws Throwable {
		 // Express the Regexp above with the code you wish you had
		 //throw new PendingException();
	 }

	@Then("^MYRMEX affiche la question suivie d’un champ de nombre permettant d’y écrire la réponse sous forme de nombre uniquement$")
	public void MYRMEX_affiche_la_question_suivie_d_un_champ_de_nombre_permettant_d_y_écrire_la_réponse_sous_forme_de_nombre_uniquement() throws Throwable {
	     // Express the Regexp above with the code you wish you had
	     //throw new PendingException();
	}

} // class