package conexasaude.com.domain.utils;

import conexasaude.com.domain.entity.DoctorUser;
import conexasaude.com.config.security.DoctorUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

public class Utils {
    public static String removeNonNumericCharacters(String input) {
        if (input == null) {
            return null;
        }
        return input.replaceAll("\\D", "");
    }

    public static DoctorUser getLoggedInUser() {
        return ((DoctorUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getDoctorUser();
    }
}
