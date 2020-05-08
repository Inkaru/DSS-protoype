package bth.dss.group2.backend.customAssert.service;

import bth.dss.group2.backend.model.User;
import org.assertj.core.api.AbstractAssert;

public class UserAssert extends AbstractAssert<UserAssert, User> {

    public UserAssert(User user) {
        super(user, UserAssert.class);
    }

    static UserAssert assertThat(User actual) {
        return new UserAssert(actual);
    }

    /* Example bellow. FIXME : Not yet usefull, but as project grows it would be nice
    UserAssert resultIstNotEmpty() {
        isNotNull();
        if (actual.getRegistrationDate() == null) {
            failWithMessage(
                    "Expected user to have a registration date, but it was null");
        }
    }*/

}
