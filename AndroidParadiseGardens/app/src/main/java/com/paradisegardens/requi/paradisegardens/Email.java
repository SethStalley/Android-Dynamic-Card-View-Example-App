package com.paradisegardens.requi.paradisegardens;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;


/**
 * Class to handle emails to the comapanies emails
 * this is used by the Contact page to deliver comments/mgs.
 */
public class Email{
    Contact contact;

    final private String COMPANY_EMAIL = "paradisegardensapp@gmail.com";
    final private String EMAIL_PASS = "paradisegardens";
    final private String SUBJECT = "Comentario de ";

    public Email(Contact contact){
        this.contact = contact;
    }

    public boolean sendEmail(String senderEmail,String msg){
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{COMPANY_EMAIL});
        i.putExtra(Intent.EXTRA_SUBJECT, SUBJECT + senderEmail);
        i.putExtra(Intent.EXTRA_TEXT   , msg);

        try {
            contact.startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(contact, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }

        return true;
    }
}
