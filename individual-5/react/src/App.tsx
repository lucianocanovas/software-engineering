import { useState, useEffect } from "react";
import { v4 as uuid } from "uuid";
import "./App.css";
import Header from "./components/Header.tsx";
import AddContact from "./components/AddContact.tsx";
import ContactList from "./components/ContactList.tsx";
import type { Contact } from "./types/contact.ts";

function App() {
  const LOCAL_STORAGE_KEY = "contacts_manager_app";

  // Contactos de demostración iniciales (si no hay datos previos en localStorage)
  const initialContacts: Contact[] = [
    {
      id: "1",
      name: "Dipesh Malvia",
      email: "dipesh@reacttutorial.com",
    },
    {
      id: "2",
      name: "Luciano Canovas",
      email: "luciano@techstore.com",
    },
    {
      id: "3",
      name: "Alan Turing",
      email: "alan.turing@computer.org",
    },
  ];

  // Inicialización perezosa del estado desde LocalStorage
  const [contacts, setContacts] = useState<Contact[]>(() => {
    try {
      const storedContacts = localStorage.getItem(LOCAL_STORAGE_KEY);
      if (storedContacts) {
        const parsed = JSON.parse(storedContacts);
        if (Array.isArray(parsed) && parsed.length > 0) {
          return parsed;
        }
      }
    } catch (e) {
      console.error("Error al leer de localStorage:", e);
    }
    return initialContacts;
  });

  // Handler para agregar un nuevo contacto
  const addContactHandler = (contact: Omit<Contact, "id">) => {
    const newContact: Contact = {
      id: uuid(),
      ...contact,
    };
    // Actualización inmutable del estado
    setContacts((prevContacts) => [...prevContacts, newContact]);
  };

  // Handler para eliminar un contacto por su identificador único
  const removeContactHandler = (id: string) => {
    const updatedContacts = contacts.filter((contact) => contact.id !== id);
    setContacts(updatedContacts);
  };

  // Efecto secundario: persiste los contactos en LocalStorage cada vez que cambia el estado
  useEffect(() => {
    try {
      localStorage.setItem(LOCAL_STORAGE_KEY, JSON.stringify(contacts));
    } catch (e) {
      console.error("Error al guardar en localStorage:", e);
    }
  }, [contacts]);

  return (
    <div className="app-container" style={{ minHeight: "100vh", backgroundColor: "#f8fafc", paddingBottom: "40px" }}>
      {/* Barra de cabecera fija */}
      <Header />

      <div className="ui container" style={{ maxWidth: "800px", margin: "0 auto", padding: "0 15px" }}>
        {/* Formulario de creación de contacto */}
        <AddContact addContactHandler={addContactHandler} />

        {/* Lista de contactos y búsqueda */}
        <ContactList contacts={contacts} getContactId={removeContactHandler} />

      </div>
    </div>
  );
}

export default App;
