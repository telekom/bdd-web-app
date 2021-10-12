package de.telekom.test.bddwebapp.frontend.steps.page;

import de.telekom.test.bddwebapp.frontend.page.Page;

public class WrongPage extends Page {

    /*
     * This is wrong
     */
    public WrongPage() {
        super(null);
    }

    @Override
    public String getURL() {
        return "https://github.com/telekom/bdd-web-app";
    }

}
