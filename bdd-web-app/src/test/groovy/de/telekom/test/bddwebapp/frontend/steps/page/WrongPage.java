package de.telekom.test.bddwebapp.frontend.steps.page;

import de.telekom.test.bddwebapp.frontend.page.Page;

/**
 * Unit test
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2020 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
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
