import { useState } from "react";
import type React from "react";
import type { Contact } from "../types/contact.ts";

interface AddContactProps {
  addContactHandler: (contact: Omit<Contact, "id">) => void;
}

const AddContact: React.FC<AddContactProps> = ({ addContactHandler }) => {
  const [name, setName] = useState<string>("");
  const [email, setEmail] = useState<string>("");
  const [error, setError] = useState<string>("");

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    // Validación según la guía tutorial: "All the fields are mandatory!"
    if (name.trim() === "" || email.trim() === "") {
      setError("¡Todos los campos son obligatorios!");
      return;
    }

    // Comunicación al componente padre App mediante callback prop
    addContactHandler({ name: name.trim(), email: email.trim() });

    // Limpieza de los campos del formulario tras el registro exitoso
    setName("");
    setEmail("");
    setError("");
  };

  return (
    <div className="ui segment" style={{ marginTop: "75px", padding: "20px" }}>
      <h3 className="ui dividing header" style={{ display: "flex", alignItems: "center", gap: "8px" }}>
        <i className="user plus icon text-primary"></i>
        Add Contact
      </h3>

      {error && (
        <div className="ui negative message" style={{ padding: "10px" }}>
          <i className="close icon" onClick={() => setError("")}></i>
          <div className="header">{error}</div>
        </div>
      )}

      <form className="ui form" onSubmit={handleSubmit}>
        <div className="field">
          <label>Nombre Completo</label>
          <div className="ui left icon input">
            <i className="user icon"></i>
            <input
              type="text"
              name="name"
              placeholder="Ej: Luciano Canovas"
              value={name}
              onChange={(e) => setName(e.target.value)}
            />
          </div>
        </div>

        <div className="field">
          <label>Correo Electrónico</label>
          <div className="ui left icon input">
            <i className="envelope icon"></i>
            <input
              type="email"
              name="email"
              placeholder="Ej: luciano@ejemplo.com"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />
          </div>
        </div>

        <button type="submit" className="ui button blue fluid" style={{ marginTop: "10px" }}>
          <i className="plus icon"></i>
          Add Contact
        </button>
      </form>
    </div>
  );
};

export default AddContact;
