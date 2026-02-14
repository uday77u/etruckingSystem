package utilities;

import java.util.Random;

public class RandomTestData {

    private static final Random random = new Random();

    // ==================================================
    // DROPDOWN OPTIONS
    // ==================================================

    private static final String[] YEARS_OF_EXPERIENCE = {
        "0-1 years",
        "2-3 years",
        "3-5 years",
        "5-7 years",
        "Above 7 years"
    };

    private static final String[] LICENSE_CLASSES = {
        "Class A",
        "Class B",
        "Class C"
    };

    private static final String[] JOB_TYPES = {
        "Dry Van",
        "Flatbed",
        "Tanker hauler",
        "Reefer"
    };

    private static final String[] DRIVER_TYPES = {
        "Solo",
        "Team"
    };

    private static final String[] INTERESTS = {
        "Local",
        "Regional",
        "OTR"
    };

    private static final String[] LEAD_SOURCES = {
        "Facebook",
        "Google",
        "Referral",
        "Indeed"
    };

    // ==================================================
    // RADIO BUTTON OPTIONS
    // ==================================================

    private static final String[] EMPLOYMENT_TYPES = {
        "Full-time",
        "Part-time"
    };

    private static final String[] PAY_TYPES = {
        "Per Hours",
        "Per Miles",
        "By Percentage"
    };

    // ==================================================
    // RANDOM PICKERS
    // ==================================================

    private static String pick(String[] values) {
        return values[random.nextInt(values.length)];
    }

    public static String randomYearsOfExperience() {
        return pick(YEARS_OF_EXPERIENCE);
    }

    public static String randomLicenseClass() {
        return pick(LICENSE_CLASSES);
    }

    public static String randomJobType() {
        return pick(JOB_TYPES);
    }

    public static String randomDriverType() {
        return pick(DRIVER_TYPES);
    }

    public static String randomInterest() {
        return pick(INTERESTS);
    }

    public static String randomLeadSource() {
        return pick(LEAD_SOURCES);
    }

    public static String randomEmploymentType() {
        return pick(EMPLOYMENT_TYPES);
    }

    public static String randomPayType() {
        return pick(PAY_TYPES);
    }
}
