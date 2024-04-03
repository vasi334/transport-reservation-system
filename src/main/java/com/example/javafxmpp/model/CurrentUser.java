package com.example.javafxmpp.model;

public class CurrentUser {
    private static CurrentUser single_instance = null;

    private String id;
    private String email;
    private String first_name;
    private String last_name;
    private String password;
    private String phone_number;

        private CurrentUser() {}

        public static CurrentUser getInstance()
        {
            if (single_instance == null)
                single_instance = new CurrentUser();

            return single_instance;
        }

        public void setFirstName(String firstName) {
            this.first_name = firstName;
        }

        public void setLastName(String lastName) {
            this.last_name = lastName;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setPhone_number(String phone_number) { this.phone_number = phone_number; }


        public String getId() {
            return id;
        }

        public String getFirstName() {
            return first_name;
        }

        public String getLastName() {
            return last_name;
        }

        public String getPassword() {
            return password;
        }

        public String getEmail() {
            return email;
        }

        public String getPhone_number() { return phone_number; }
    }
