/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chrysdev.maintenance;

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
    private String m_chemin_envoie_demande = "files/notifsAdmin.txt";
    private String m_chemin_reponse = "files/reponse.txt";

    //Methodes
    public Utilisateur(String nomU, boolean admin) {
        m_nomU = nomU;
        m_admin = admin;
    }
    
//    @Override
//    public void finalize(){
//        
//    }

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
                reader.close();
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
                reader.close();
                retour = 0;
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
                reader.close();
                retour = 0;
            } catch (IOException e) {
                retour = 1;
            }
        } else {
            retour = 1;
        }
        return retour;
    }

    public int envoiNotifC(String appareil, boolean decision) {
        if (m_admin) {
            if (decision) {
                try {
                    FileWriter fileWriter = new FileWriter(m_chemin_reponse, true);
                    BufferedWriter writer = new BufferedWriter(fileWriter);
                    writer.write(m_nomU + " accepte votre de demande: " + appareil);
                    writer.newLine();

                    writer.close();
                } catch (IOException e) {
                    System.out.println("Une erreur s'est produite lors de l'aces au fichier.");
                }
            } else {
                try {
                    FileWriter fileWriter = new FileWriter(m_chemin_reponse, true);
                    BufferedWriter writer = new BufferedWriter(fileWriter);
                    writer.write(m_nomU + " refuse votre de demande: " + appareil);
                    writer.newLine();

                    writer.close();
                } catch (IOException e) {
                    System.out.println("Une erreur s'est produite lors de l'aces au fichier.");
                }
            }
        } else {
            try {
                FileWriter fileWriter = new FileWriter(m_chemin_envoie_demande, true);
                BufferedWriter writer = new BufferedWriter(fileWriter);
                writer.write(m_nomU + " Demande a faire reparer son " + appareil);
                writer.newLine();

                writer.close();
            } catch (IOException e) {
                System.out.println("Une erreur s'est produite lors de l'aces au fichier.");
            }
        }
        return 0;
    }

    public void menu() throws FileNotFoundException {
        if (m_admin) {
            boolean continuer = true;
            while (continuer) {
                System.out.println("====== BIENVENUE AU MENU DES ADMINISTRATEURS ======");
                System.out.println("Voici une liste des demandes que vous avez recu:");
                System.out.println("Vous pouvez aussi entrer 50 pour sortir");
                ArrayList<String> appareils = new ArrayList<>();
                this.lireFichierC("VD", appareils);
                int comp = 1;
                for (String i : appareils) {
                    System.out.println(comp + ") " + i);
                    comp++;
                }
                boolean continuerC = true;
                while (continuerC) {
                    System.out.println();
                    System.out.print("Entrez le numero de choix ");
                    BufferedReader saisie = new BufferedReader(new InputStreamReader(System.in));
                    int choix = 0;
                    try {
                        choix = Integer.parseInt(saisie.readLine());
                    } catch (IOException | NumberFormatException e) {
                        System.out.println("Une erreur s'est produite, veuillez reessayer...");
                        continue;
                    }
                    if (choix > 0 && choix <= appareils.size()) {
                        boolean continuerD = true;
                        while (continuerD) {
                            System.out.println("Bien recu.");
                            System.out.print("Entrez 1 pour accepter cette demande et 2 pour refuser la demande: ");
                            saisie = new BufferedReader(new InputStreamReader(System.in));
                            int decision = 0;
                            try {
                                decision = Integer.parseInt(saisie.readLine());
                            } catch (IOException | NumberFormatException e) {
                                System.out.println("Une erreur s'est produite, veuillez reessayer...");
                                continue;
                            }
                            if (decision == 1) {
                                System.out.println("D'accord, votre reponse sera envoye au client");
                                choix -= 1;
                                String choixAp = appareils.get(choix);
                                this.envoiNotifC(choixAp, true);
                                continuerD = false;
                            } else if (decision == 2) {
                                System.out.println("D'accord, votre reponse sera envoye au client");
                                choix -= 1;
                                String choixAp = appareils.get(choix);
                                this.envoiNotifC(choixAp, false);
                                continuerD = false;
                            } else {
                                System.out.println("Erreur entrez 1 ou 2...");
                            }
                        }
                        continuerC = false;
                    } else if (choix == 50) {
                        System.out.println("SORTIE DU PROGRAMME...");
                        continuerC = false;
                    } else {
                        System.err.print("Erreur: Le nombre entre doit etre compris entre 1 et " + (appareils.size()));
                    }
                }
                continuer = false;
            }
            continuer = false;
        } else {
            boolean continuer = true;
            int choix = 0;
            while (continuer) {
                System.out.println("====== BIENVENUE AU MENU DES CLIENTS ======");
                System.out.println("Que voulez-vous faire?");
                System.out.println();
                System.out.println("1. Consulter les reponses des admins");
                System.out.println("2. Faire une demande de maintenance");
                System.out.println("3. Sortir du programme");
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
                if (choix < 0 && choix > 3) {
                    System.out.println("Votre choix doit etre compris entre 1 et 2");
                } else if (choix == 1) {
                    System.out.println("D'accord,  voici les reponses des admins");
                    ArrayList<String> appareils = new ArrayList<>();
                    this.lireFichierC("VR", appareils);
                    int comp = 1;
                    for (String i : appareils) {
                        System.out.println(comp + ") " + i);
                        comp++;
                    }
                    boolean continuerC = true;
                    while (continuerC) {
                        System.out.println();
                        System.out.print("Entrez 5 pour sortir: ");
                        saisie = new BufferedReader(new InputStreamReader(System.in));
                        choix = 0;
                        try {
                            choix = Integer.parseInt(saisie.readLine());
                        } catch (IOException | NumberFormatException e) {
                            System.out.println("Une erreur s'est produite, veuillez reessayer...");
                            continue;
                        }
                        if (choix == 5) {
                            System.out.println("D'accord, vous allez etre rediriger");
                            continuerC = false;
                        } else {
                            System.err.print("Erreur: Veuillez entrer 5 si vous souhaiter sortir");
                        }
                    }
                } else if (choix == 2) {
                    System.out.println("Voici la liste des appareils disponible a la reparation:");
                    ArrayList<String> appareils = new ArrayList<>();
                    this.lireFichierC("VM", appareils);
                    int comp = 1;
                    for (String i : appareils) {
                        System.out.println(comp + ") " + i);
                        comp++;
                    }
                    boolean continuerC = true;
                    while (continuerC) {
                        System.out.println();
                        System.out.print("Entrez le numero de votre Appareil: ");
                        saisie = new BufferedReader(new InputStreamReader(System.in));
                        choix = 0;
                        try {
                            choix = Integer.parseInt(saisie.readLine());
                        } catch (IOException | NumberFormatException e) {
                            System.out.println("Une erreur s'est produite, veuillez reessayer...");
                            continue;
                        }
                        if (choix > 0 && choix <= appareils.size()) {
                            System.out.println("D'accord, votre demande sera transmise au maintenancier.");
                            choix -= 1;
                            String choixAp = appareils.get(choix);
                            this.envoiNotifC(choixAp, false);
                            continuerC = false;
                        } else {
                            System.err.print("Erreur: Le nombre entre doit etre compris entre 1 et " + (appareils.size()));
                        }
                    }
                    continuer = false;
                } else if (choix == 3) {
                    System.out.println("SORTIE DU PROGRAMME...");
                    continuer = false;
                }
            }
        }
    }
}
