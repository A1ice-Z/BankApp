package bankapp.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class that makes a profile
 */

public class Profile {
    private String name;
    private String email;
    private String tlf;
    private String password;
    private List<Account> accounts = new ArrayList<>();
    private ArrayList<String> landcodes = new ArrayList<>(Arrays.asList("ad", "ae", "af", "ag", "ai", "al", "am", "ao",
            "aq", "ar", "as", "at", "au", "aw", "ax", "az", "ba", "bb", "bd", "be", "bf", "bg", "bh", "bi", "bj", "bl",
            "bm", "bn", "bo", "bq", "br", "bs", "bt", "bv", "bw", "by", "bz", "ca", "cc", "cd", "cf", "cg", "ch", "ci",
            "ck", "cl", "cm", "cn", "co", "cr", "cu", "cv", "cw", "cx", "cy", "cz", "de", "dj", "dk", "dm", "do", "dz",
            "ec", "ee", "eg", "eh", "er", "es", "et", "fi", "fj", "fk", "fm", "fo", "fr", "ga", "gb", "gd", "ge", "gf",
            "gg", "gh", "gi", "gl", "gm", "gn", "gp", "gq", "gr", "gs", "gt", "gu", "gw", "gy", "hk", "hm", "hn", "hr",
            "ht", "hu", "id", "ie", "il", "im", "in", "io", "iq", "ir", "is", "it", "je", "jm", "jo", "jp", "ke", "kg",
            "kh", "ki", "km", "kn", "kp", "kr", "kw", "ky", "kz", "la", "lb", "lc", "li", "lk", "lr", "ls", "lt", "lu",
            "lv", "ly", "ma", "mc", "md", "me", "mf", "mg", "mh", "mk", "ml", "mm", "mn", "mo", "mp", "mq", "mr", "ms",
            "mt", "mu", "mv", "mw", "mx", "my", "mz", "na", "nc", "ne", "nf", "ng", "ni", "nl", "no", "np", "nr", "nu",
            "nz", "om", "pa", "pe", "pf", "pg", "ph", "pk", "pl", "pm", "pn", "pr", "ps", "pt", "pw", "py", "qa", "re",
            "ro", "rs", "ru", "rw", "sa", "sb", "sc", "sd", "se", "sg", "sh", "si", "sj", "sk", "sl", "sm", "sn", "so",
            "sr", "ss", "st", "sv", "sx", "sy", "sz", "tc", "td", "tf", "tg", "th", "tj", "tk", "tl", "tm", "tn", "to",
            "tr", "tt", "tv", "tw", "tz", "ua", "ug", "um", "us", "uy", "uz", "va", "vc", "ve", "vg", "vi", "vn", "vu",
            "wf", "ws", "ye", "yt", "za", "zm", "zw", "com"));

    /**
     * Creates a new profile
     *
     * @param name     The profile name
     * @param email    The email connected to this profile
     * @param tlf      The telephone number connected to this profile
     * @param password The password to the profile
     * @throws IllegalArgumentException If the name, email, telephone number or
     *                                  password are not valid
     */
    public Profile(String name, String email, String tlf, String password) {
        if(!validName(name)) throw new IllegalArgumentException("Invalid name");
        this.name = name;
        if(!validEmail(email)) throw new IllegalArgumentException("Invalid email");
        if(!validPassword(password)) throw new IllegalArgumentException("Password must contain at least 8 characters");
        if(!validTlf(tlf)) throw new IllegalArgumentException("Invalid phonenumber");
        
        
        this.email = email;
        this.tlf = tlf;
        this.password = password;
        System.out.println("Your profile was made successfully!");
    }

    /**
     * @param email
     * Valid email is with format surnameLastname@mail.landcode
     * @return
     */
    private boolean validEmail(String email){
        if(!email.contains("@")) {
            System.out.println("must have @");
            //throw new IllegalArgumentException("Email must comtain '@'");
        }
        String[] splitAt = email.split("@");
        if(!email.contains(".")){ 
            System.out.println("must have .");
            //throw new IllegalArgumentException("Email must contain a dot");

        }
        String[] splitDot = splitAt[1].split("\\.");

        String surname = this.getName().split(" ")[0].toLowerCase();
        String lastName = this.getName().split(" ")[1].toLowerCase();
        System.out.println(splitDot[0] + "," + splitDot[1]);
        if(splitAt[0].toLowerCase().contains(surname) && splitAt[0].toLowerCase().contains(lastName) && landcodes.contains(splitDot[1])) {return true;}

        return false;
    }


    /**
     * @param password
     * A password is valid if it contains less than 8 characters, where at least 1 of the characters are a number
     * @return a boolean
     */
    private boolean validPassword(String password){
        int num = 0;
        for (int i = 0; i < password.length(); i++) {
            if(isNumeric(String.valueOf(password.charAt(i)))) num++;
        }

        return(password.length() > 8 && (num>0));
    }

    private boolean validTlf(String tlf){
        return (tlf.length() == 8 && isNumeric(tlf));
    }

    /**
     * @param name
     * A name is valid if it contains of a surname and a lastname, and all of the characters are letters
     * @return
     */
    private boolean validName(String name){
        boolean bool = false;
        if ((name.contains(" "))) {
            String[] splits = name.split(" ");
            System.out.println(splits);
            for (int i = 0; i < splits.length; i++) {
                for (int j = 0; j < splits[i].length(); j++) {
                    if (!Character.isLetter(splits[i].strip().charAt(j))) {
                        //throw new IllegalArgumentException("Name can only contain letters");
                        //return false;
                    }
                }
            }
        bool = true; 
        }
        return bool;
    }

    /**
     * Checks if the text is numeric
     * 
     * @param test The text that's gonne be checked
     * @return The boolean value true if the text is numeric, and false if not
     */
    private static boolean isNumeric(String test) {
        try {
            Double.parseDouble(test);
            return true;
        }

        catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Creates an account
     * 
     * @param name The name to the account
     */
    public void createAccount(String name) {
        accounts.add(new Account(name, this.name));
    }

    /**
     * Changes the password
     * 
     * @param password The new password
     */
    public void changePassword(String password) {
        if(!validPassword(password)) throw new IllegalArgumentException("Not valid password");
        this.password = password;
    }

    /**
     * Changes the telephone number
     * 
     * @param tlf The new telephone number
     */
    public void changeTlf(String tlf) {
        if(!validTlf(tlf)) throw new IllegalArgumentException("Not valid telephone number");
        this.tlf = tlf;
        
    }

    /**
     * Returns the email connected to this profile
     * 
     * @return The email connected to this profile
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns the name connected to this account
     * 
     * @return The name connected to this account
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the phone number connected to this account
     * 
     * @return The phone number connected to this account
     */
    public String getTlf() {
        return tlf;
    }

    /**
     * Returns the account password
     * 
     * @return The account password 
     */
    public String getPassword() {
        return password;
    }

    /**
     * 
     * @return list of all accounts
     */
    public List<Account> getAccounts() {
        return new ArrayList<>(accounts);
    }

}
