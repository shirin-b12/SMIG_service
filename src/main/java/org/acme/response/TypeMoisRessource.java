package org.acme.response;

public class TypeMoisRessource {

        private String nom;
        private int nombre;
        private String mois;

        public String getMois() {
            return mois;
        }

        public void setMois(String mois) {
            this.mois = mois;
        }

        public TypeMoisRessource(String nom, int nombre, String mois) {
            this.nom = nom;
            this.nombre = nombre;
            this.mois = mois;
        }
        public String getNom() {
            return nom;
        }
        public void setNom(String nom) {
            this.nom = nom;
        }
        public int getNombre() {
            return nombre;
        }
        public void setNombre(int nombre) {
            this.nombre = nombre;
        }

}
