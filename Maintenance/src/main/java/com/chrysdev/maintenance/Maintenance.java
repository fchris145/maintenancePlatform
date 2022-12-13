/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package com.chrysdev.maintenance;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.ArrayList;

/**
 *
 * @author Chrysippe
 */
public class Maintenance {

    private static HashMap<String, String> userAccount = new HashMap<>();

    public static void main(String[] args) throws FileNotFoundException {
        userAccount.put("junior", "1234");
        ArrayList<String> appareils = new ArrayList<>();
        boolean continuerP = true;
        while (continuerP) {
            boolean continuer = true;
            int choix = 0;
            System.out.println("=== Bienvenue dans mon programme de maintenance! ===");
            System.out.println();
            System.out.println("Veuillez choisir le type de compte sur lequel vous voulez vous conecter:");
            System.out.println("1. Compte Administrateur(Maintenancier)");
            System.out.println("2. Compte Client");
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
                } else if (choix == 2) {
                    System.out.println("D'accord,  vous allez etre rediriger vers le Menu client");
                    String nomU = "";
                    nomU = connectUser();
                    System.out.println("Voici la liste des appareils disponible a la reparation:");
                    String chemin = "files/listeRepair.txt";
                    appareils = lireFichier(chemin);
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
                        if(choix > 0 && choix < appareils.size()){
                            System.out.print("D'accord, votre demande sera transmise au maintenancier.");
                            continuerC = false;
                        } else {
                            System.err.print("Erreur: Le nombre entre doit etre compris entre 1 et " + (appareils.size() - 1));
                        }
                    }
                    continuer = false;
                }
            }

        }
    }

    public static String connectUser() {
        boolean continuer = true;
        String nomU = "";
        while (continuer) {
            System.out.println("=== Bienvenue dans l'interface reservee aux clients ===");
            System.out.println("Que voulez-vous faire?");
            System.out.println();
            System.out.println("1.Creer un nouveau compte");
            System.out.println("2. Ouvrir un compte existant");
            int choix = 0;
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
                boolean continuerB = true;
                while (continuerB) {
                    System.out.print("Veuillez entrer votre nom d'utilisateur (attention a la casse):");
                    saisie = new BufferedReader(new InputStreamReader(System.in));
                    try {
                        nomU = saisie.readLine();
                    } catch (IOException e) {
                        System.out.println("Une erreur s'est produite, veuillez reessayer...");
                        continue;
                    }
                    if (userAccount.get(nomU) != null) {
                        System.out.println("Ce nom d'utilisateur existe deja, veuillez en choisir un autre...");
                        continue;
                    } else {
                        System.out.println("Bien.");
                    }
                    System.out.print("Veuillez entrer votre mot de passe: ");
                    String mdp = "";
                    saisie = new BufferedReader(new InputStreamReader(System.in));
                    try {
                        mdp = saisie.readLine();
                    } catch (IOException e) {
                        System.out.println("Une erreur s'est produite, veuillez reessayer...");
                        continue;
                    }
                    System.out.println("Votre compte a bien ete ajoute Mr/Mme " + nomU);
                    System.out.println("Vous allez etre rediriger vers l'ecran d'accueil des clients");
                    userAccount.put(nomU, mdp);
                    continuerB = false;
                }
                continue;
            } else if (choix == 2) {
                boolean continuerB = true;
                nomU = "";
                System.out.println("=== CONNECTEZ-VOUS A VOTRE COMPTE ===");
                while (continuerB) {
                    System.out.println();
                    System.out.print("Veuillez entrer votre nom d'utilisateur (attention a la casse):");
                    saisie = new BufferedReader(new InputStreamReader(System.in));
                    try {
                        nomU = saisie.readLine();
                    } catch (IOException e) {
                        System.out.println("Une erreur s'est produite, veuillez reessayer...");
                        continue;
                    }
                    System.out.print("Veuillez entrer votre mot de passe: ");
                    String mdp = "";
                    saisie = new BufferedReader(new InputStreamReader(System.in));
                    try {
                        mdp = saisie.readLine();
                    } catch (IOException e) {
                        System.out.println("Une erreur s'est produite, veuillez reessayer...");
                        continue;
                    }
                    if (userAccount.get(nomU) == null ? mdp == null : userAccount.get(nomU).equals(mdp)) {
                        System.out.println("Bienvenue Mr/Mme " + nomU);
                        continuerB = false;
                    } else {
                        System.out.println("Le nom d'utilisateur et le mot de passe ne correspondent pas...");
                    }
                }
            }

            continuer = false;
        }
        return (userAccount.get(nomU));
    }

    public static ArrayList lireFichier(String chemin) throws FileNotFoundException {
        FileReader fileReader = new FileReader(chemin);
        BufferedReader reader = new BufferedReader(fileReader);
        String line = "";
        ArrayList<String> appareils = new ArrayList<>();

        try {
            line = reader.readLine();

            while (line != null) {
                appareils.add(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            System.out.println("Une erreur s'est produite lors de la lecture du fichier");
        }
        return appareils;
    }
}
