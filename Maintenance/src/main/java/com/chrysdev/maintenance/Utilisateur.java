/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chrysdev.maintenance;

import static com.chrysdev.maintenance.Maintenance.connectUser;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 * @author Chrysippe
 */
public class Utilisateur {

    //Attributs
    private String m_nomU;
    private boolean m_admin;
    private String m_chemin_liste_machine = "files/listeRepair.txt";
    private String m_chemin_envoie_demande = "files/notifsAdmin";
    private String m_chemin_reponse = "files/reponse";

    //Methodes
    public Utilisateur(String nomU, boolean admin) {
        m_nomU = nomU;
        m_admin = admin;
    }

    public int lireFichierC(String demande, ArrayList appareils) throws FileNotFoundException {
        int retour = 0;
        if (demande.equals("VM")) {
            FileReader fileReader = new FileReader(m_chemin_liste_machine);
            BufferedReader reader = new BufferedReader(fileReader);
            String line = "";
            try {
                line = reader.readLine();
                while (line != null) {
                    appareils.add(line);
                    line = reader.readLine();
                }
                retour = 0;
            } catch (IOException e) {
                retour = 1;
            }

        } else if (demande.equalsIgnoreCase("VR")) {
            FileReader fileReader = new FileReader(m_chemin_reponse);
            BufferedReader reader = new BufferedReader(fileReader);
            String line = "";

            try {
                line = reader.readLine();

                while (line != null) {
                    appareils.add(line);
                    line = reader.readLine();
                }
                retour =  0;
            } catch (IOException e) {
                retour = 1;
            }

        } else if (demande.equalsIgnoreCase("VD") && m_admin == true) {
            FileReader fileReader = new FileReader(m_chemin_envoie_demande);
            BufferedReader reader = new BufferedReader(fileReader);
            String line = "";
            try {
                line = reader.readLine();

                while (line != null) {
                    appareils.add(line);
                    line = reader.readLine();
                }
                retour = 0;
            } catch (IOException e) {
                retour = 1;
            }
        }
        return retour;
    }

    public int envoiNotifC(String appareil) throws IOException {
        if (m_admin) {
            FileWriter fileWriter = new FileWriter(m_chemin_reponse, true);
            try ( BufferedWriter writer = new BufferedWriter(fileWriter)) {
                try {
                    writer.write(m_nomU + " Accepte de reparer votre " + appareil);
                    writer.newLine();
                } catch (IOException e) {
                    System.out.println("Une erreur s'est produite lors de l'aces au fichier.");
                }
            }
        } else {
            FileWriter fileWriter = new FileWriter(m_chemin_envoie_demande, true);
            try ( BufferedWriter writer = new BufferedWriter(fileWriter)) {
                try {
                    writer.write(m_nomU + " Demande a faire reparer son " + appareil);
                    writer.newLine();
                } catch (IOException e) {
                    System.out.println("Une erreur s'est produite lors de l'aces au fichier.");
                }
            }
        }
        return 0;
    }
    
    public void menu(){
        if(m_admin){
            System.out.println("====== BIENVENUE AU MENU DES ADMINISTRATEURS ======");
            System.out.println("Que voulez-vous faire?");
            System.out.println();
            System.out.println("1. Consulter les demandes de maintenance");
            System.out.println("2. ");
            
        } else {
            System.out.println("====== BIENVENUE AU MENU DES CLIENTS ======");
            System.out.println("Que voulez-vous faire?");
            System.out.println();
            System.out.println("1. Consulter les reponses des admins");
            System.out.println("2. Faire une demande de maintenance");
            boolean continuer = true;
            int choix = 0;
            while (continuer) {
                System.out.println();
                System.out.print("Entrez le numero de votre choix: ");
                BufferedReader saisie = new BufferedReader(new InputStreamReader(System.in));
                choix = 0;
                try {
                    choix = Integer.parseInt(saisie.readLine());
                } catch (IOException | NumberFormatException e) {
                    System.out.println("Une erreur s'est produite, veuillez reessayer...");
                    continue;
                }
                if (choix < 0 && choix > 2) {
                    System.out.println("Votre choix doit etre compris entre 1 et 2");
                } else if (choix == 1) {
                    System.out.println("D'accord,  vous allez etre rediriger vers le Menu client");
                    String nomU = "";
                    nomU = connectUser(true);
                    System.out.println("");
                }
            }
        }
    }
}
