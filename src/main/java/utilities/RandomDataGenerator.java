package utilities;

import java.security.SecureRandom;
import java.time.Year;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomDataGenerator {

    private static final Random random = new Random();
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";

    /* ===================== LICENSE NUMBER ===================== */
    // Example: AN010130083278 (similar to your app)
    public static String getRandomLicenseNumber() {
        StringBuilder sb = new StringBuilder();

        // 2-letter state / prefix
        sb.append(ALPHABET.charAt(random.nextInt(26)));
        sb.append(ALPHABET.charAt(random.nextInt(26)));

        // 13 digits → total length = 15
        for (int i = 0; i < 13; i++) {
            sb.append(random.nextInt(10));
        }

        return sb.toString();
    }
    
    /* ===================== FIRST NAME ===================== */
    public static String getRandomFirstName() {
        return capitalize(generateName(5 + random.nextInt(4))); // 5–8 chars
    }

    /* ===================== LAST NAME ===================== */
    public static String getRandomLastName() {
        return capitalize(generateName(6 + random.nextInt(5))); // 6–10 chars
    }

    private static String generateName(int length) {
        StringBuilder sb = new StringBuilder();
        sb.append(LOWERCASE.charAt(random.nextInt(26))); // first char
        for (int i = 1; i < length; i++) {
            sb.append(LOWERCASE.charAt(random.nextInt(26)));
        }
        return sb.toString();
    }

    private static String capitalize(String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }

    /* ===================== EMAIL ===================== */
    public static String getRandomEmail() {
        return getRandomFirstName().toLowerCase()
                + "." + getRandomLastName().toLowerCase()
                + random.nextInt(1000)
                + "@test.com";
    }

    /* ===================== PHONE ===================== */
    public static String getRandomPhoneNumber(String country) {
        StringBuilder sb = new StringBuilder();
        switch (country.toLowerCase()) {
            case "india":
                sb.append("9");
                for (int i = 0; i < 9; i++) sb.append(random.nextInt(10));
                break;
            case "usa":
                for (int i = 0; i < 10; i++) sb.append(random.nextInt(10));
                break;
            case "australia":
                sb.append("04");
                for (int i = 0; i < 8; i++) sb.append(random.nextInt(10));
                break;
        }
        return sb.toString();
    }

    /* ===================== ZIP / POSTAL ===================== */
    public static String getRandomZip(String country) {
        switch (country.toLowerCase()) {
            case "india":
                return (1 + random.nextInt(9))
                        + String.format("%05d", random.nextInt(100000));
            case "usa":
                return String.format("%05d", random.nextInt(100000));
            case "australia":
                return String.format("%04d", random.nextInt(10000));
            default:
                return "00000";
        }
    }
    
    /* ===================== SSN (USA) ===================== */
    // Format: AAA-GG-SSSS (9 digits, no invalid ranges)
    public static String getRandomSSN() {

        int area;
        do {
            area = 100 + random.nextInt(900); // 100–999
        } while (area == 666 || area >= 900); // invalid SSN rules

        int group = 10 + random.nextInt(90);   // 10–99
        int serial = 1000 + random.nextInt(9000); // 1000–9999

        return String.format("%03d%02d%04d", area, group, serial);
    }
    
    /*==============PIN codes===================*/
    //real time existing pin codes
    public static String getValidIndianPinCode() {

        // Real & commonly used Indian PIN prefixes (by region)
        String[] validPinPrefixes = {
            "1100", // Delhi
            "5600", // Bangalore
            "4000", // Mumbai
            "6000", // Chennai
            "5000", // Hyderabad
            "7000", // Kolkata
            "3800", // Ahmedabad
            "3020", // Jaipur
            "6820", // Kochi
            "7510"  // Bhubaneswar
        };

        Random random = new Random();

        // Pick a valid prefix
        String prefix = validPinPrefixes[random.nextInt(validPinPrefixes.length)];

        // Generate remaining digits (2 digits)
        int suffix = random.nextInt(100); // 00–99

        return prefix + String.format("%02d", suffix);
    }

    public static String getValidUSZipCode() {

        // Real & commonly used US ZIP prefixes (by state/region)
        String[] validZipPrefixes = {
            "100", // New York, NY
            "303", // Atlanta, GA
            "331", // Miami, FL
            "606", // Chicago, IL
            "733", // Austin, TX
            "900", // Los Angeles, CA
            "941", // San Francisco, CA
            "981", // Seattle, WA
            "850", // Phoenix, AZ
            "021"  // Boston, MA
        };

        Random random = new Random();

        // Pick a valid prefix
        String prefix = validZipPrefixes[random.nextInt(validZipPrefixes.length)];

        // Generate remaining digits (2 digits)
        int suffix = random.nextInt(100); // 00–99

        return prefix + String.format("%02d", suffix);
    }

    //Random address generate
    private static final String[] STREET_NAMES = {
            "MG Road",
            "Park View Lane",
            "Green Valley Road",
            "Lake Side Avenue",
            "Sunrise Street",
            "Palm Grove Road",
            "Hill Top Lane",
            "Central Market Road",
            "Silver Oak Street",
            "Rose Garden Lane"
        };

        private static final String[] BUILDING_NAMES = {
            "Shree Balaji Residency",
            "Sai Krupa Apartments",
            "Green Heights",
            "Royal Enclave",
            "Palm Meadows",
            "Lake View Residency",
            "Sunshine Apartments",
            "Silver Oak Residency"
        };

        public static String randomStreetAddress() {

            int flatNo = random.nextInt(900) + 100; // 100–999
            String street = STREET_NAMES[random.nextInt(STREET_NAMES.length)];
            String building = BUILDING_NAMES[random.nextInt(BUILDING_NAMES.length)];

            return "Flat No " + flatNo + ", " + building + ", " + street;
        }

        public static String getDOTNumberUS() {
            // Generate 6 to 8 digit number (most realistic range)
            int length = ThreadLocalRandom.current().nextInt(6, 9);

            long min = (long) Math.pow(10, length - 1);
            long max = (long) Math.pow(10, length) - 1;

            long dot = ThreadLocalRandom.current().nextLong(min, max);

            return String.valueOf(dot);
        }
        
        //generate secure password
        private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
        private static final String NUMBERS = "0123456789";
        private static final String SPECIAL = "@#$%!&*";
        private static final String ALL = UPPER + LOWER + NUMBERS + SPECIAL;

        private static final SecureRandom randomS = new SecureRandom();

        public static String generatePassword(int length) {

            if (length < 8) {
                throw new IllegalArgumentException("Password length should be >= 8");
            }

            StringBuilder password = new StringBuilder(length);

            // Ensure at least one of each
            password.append(UPPER.charAt(randomS.nextInt(UPPER.length())));
            password.append(LOWER.charAt(randomS.nextInt(LOWER.length())));
            password.append(NUMBERS.charAt(randomS.nextInt(NUMBERS.length())));
            password.append(SPECIAL.charAt(randomS.nextInt(SPECIAL.length())));

            // Fill remaining
            for (int i = 4; i < length; i++) {
                password.append(ALL.charAt(randomS.nextInt(ALL.length())));
            }

            return shuffleString(password.toString());
        }

        private static String shuffleString(String input) {
            char[] chars = input.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                int randomIndex = random.nextInt(chars.length);
                char temp = chars[i];
                chars[i] = chars[randomIndex];
                chars[randomIndex] = temp;
            }
            return new String(chars);
        }
        
        //private static final SecureRandom random = new SecureRandom();

        private static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
       // private static final String NUMBERS = "0123456789";
        // ---------------- Truck Number ----------------
        public static String generateTruckNumber() {
            return "KA" +
                    random.nextInt(90) +
                    LETTERS.charAt(random.nextInt(LETTERS.length())) +
                    LETTERS.charAt(random.nextInt(LETTERS.length())) +
                    (1000 + random.nextInt(9000));
        }

        // ---------------- Model Name ----------------
        public static String generateModelName() {
            String[] models = {"Volvo", "Freightliner", "Kenworth", "Peterbilt", "Mack"};
            return models[random.nextInt(models.length)];
        }

        // ---------------- Model Year ----------------
        public static String generateModelYear() {
            int currentYear = Year.now().getValue();
            return String.valueOf(currentYear - random.nextInt(5)); // last 5 years
        }

        // ---------------- VIN Number ----------------
        public static String generateVIN() {
            String chars = LETTERS + NUMBERS;
            StringBuilder vin = new StringBuilder();

            for (int i = 0; i < 17; i++) {
                vin.append(chars.charAt(random.nextInt(chars.length())));
            }
            return vin.toString();
        }

        // ---------------- Insurance Policy ----------------
        public static String generateInsurancePolicy() {
            return "POL" + (100000 + random.nextInt(900000));
        }

        // ---------------- Gross Weight ----------------
        public static String generateGrossWeight() {
            return String.valueOf(10000 + random.nextInt(40000));
        }
        
        //-----------------TrailerNumber-------------------
        public static String generateTrailerNumber() {
            return "TRL" + System.currentTimeMillis() % 100000;
        }

        //------------------EngineHours-------------------
        public static String generateEngineHours() {
            return String.valueOf(50 + random.nextInt(500));
        }

        //------------------MaxCapacity--------------------
        public static String generateMaxCapacity() {
            return String.valueOf(10 + random.nextInt(40));
        }
}
