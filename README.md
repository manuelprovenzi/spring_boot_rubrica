# Rubrica API Documentation

## Overview
[Rubrica] is a service that allows users to manage their address book upon registration. All functions of this service return a JSON containing:
- `status` ("ok" or "error")
- `result` (if the status is "ok")
- `message` (if the status is "error")

The result varies depending on the type of request.

## Functions
The service offers the following functions:

### 1. Registration
- **Endpoint:** `regist`
- **Description:** Allows users to register with a username and password.
- **Parameters (GET):**
  - `username` (string)
  - `password` (string)
- **Response:**
  - If the parameters are incorrect or the user already exists, an "error" is returned with the corresponding message.

### 2. Token Retrieval
- **Endpoint:** `getToken`
- **Description:** Allows users to retrieve the token for subsequent requests.
- **Parameters (GET):**
  - `username` (string)
  - `password` (string)
- **Response:**
  - If the parameters are incorrect or the username/password is wrong, an "error" is returned with the corresponding message.
- **Note:**
  - Each time a new token is generated, the previous token is deleted.

### 3. Add Contact
- **Endpoint:** `addContact`
- **Description:** Allows users to add a contact to the address book.
- **Parameters (GET):**
  - `token` (string)
  - `nome` (string)
  - `cognome` (string)
  - `numero` (string)
  - `gruppo` (string - OPTIONAL -> if the field is not provided, the contact will still be saved but not assigned to any group)
- **Response:**
  - If the parameters are incorrect, an "error" is returned with the corresponding message.

### 4. Delete Contact
- **Endpoint:** `deleteContact`
- **Description:** Allows users to delete a contact.
- **Parameters (GET):**
  - `token` (string)
  - `id` (int)
- **Note:**
  - The `id` field corresponds to the ID of the contact to be deleted (see `getContacts.php`).
- **Response:**
  - If the parameters are incorrect or the contact does not exist, an "error" is returned with the corresponding message.

### 5. Get Groups
- **Endpoint:** `getGroups`
- **Description:** Returns the list of all groups present in the address book. If two contacts have the same group, the group name will appear only once in this list.
- **Parameters (GET):**
  - `token` (string)
- **Response:**
  - If the parameters are incorrect, an "error" is returned with the corresponding message.

### 6. Get Contacts
- **Endpoint:** `getContacts`
- **Description:** Returns the list of all contacts present in the address book, with their respective IDs (necessary for deletion).
- **Parameters (GET):**
  - `token` (string)
- **Response:**
  - If the parameters are incorrect, an "error" is returned with the corresponding message.
